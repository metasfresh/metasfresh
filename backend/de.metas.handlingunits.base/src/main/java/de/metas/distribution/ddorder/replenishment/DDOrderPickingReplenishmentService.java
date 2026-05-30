package de.metas.handlingunits.ddorder.replenishment;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.organization.OrgId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DDOrderPickingReplenishmentService
{
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_NetworkGap = AdMessageKey.of("DDOrderPickingReconcile_NetworkGap");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_MandatoryNetwork = AdMessageKey.of("DDOrderPickingReconcile_MandatoryNetwork");
	@VisibleForTesting
	static final AdMessageKey MSG_DDOrderPickingReplenishment_QtyZero = AdMessageKey.of("DDOrderPickingReconcile_QtyZero");

	// FQN trx-property key: avoids collisions with any other service that might register an
	// after-commit accumulator under a shorter, easier-to-clash name.
	private static final String TRX_PROPERTY_ScheduleReconcile = "de.metas.handlingunits.ddorder.replenishment.DDOrderPickingReplenishment";

	@NonNull private final DDOrderPickingReplenishmentRepository repository;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderReplenishmentEventPublisher reconciliationEventPublisher;
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	public void assertCanChange(@NonNull final I_M_ShipmentSchedule schedule)
	{
		if (!isOnAutoDistributionOrder(schedule))
		{
			return;
		}

		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final DDOrderId ddOrderId = repository.findActiveDDOrderForSchedule(scheduleId).orElse(null);
		if (ddOrderId == null)
		{
			return;
		}
		if (isPickerBusy(ddOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, ddOrderId);
		}
	}

	public void scheduleReconcileAfterCommit(@NonNull final I_M_ShipmentSchedule schedule)
	{
		if (!isOnAutoDistributionOrder(schedule))
		{
			return;
		}

		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());

		// Accumulate the schedule id per-trx: exactly ONE reconcile event per distinct id is published
		// after the current transaction commits (the collector deduplicates equal items).
		// If there is no active trx, the processor runs inline immediately.
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				Collections.singletonList(scheduleId),
				reconciliationEventPublisher::publishAll);
	}

	/**
	 * Re-reads schedule, classifies the action (NONE/CREATE/RECREATE/VOID), executes it.
	 *
	 * <p><b>No transaction boundary here.</b> The VOID-then-CREATE of the RECREATE branch is only atomic if
	 * the caller wraps this call in a transaction. The caller ({@code DDOrderReplenishmentEventHandler}) wraps
	 * this call in {@code trxManager.runInThreadInheritedTrx(...)} to provide a rollback boundary so that a
	 * create-failure rolls back the void.</p>
	 */
	public void reconcile(@NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(scheduleId);
		final DDOrderId existingDDOrderId = repository.findActiveDDOrderForSchedule(scheduleId).orElse(null);
		DDOrderReplenishmentAction action = classifyAction(schedule, existingDDOrderId);

		// Zero-qty soft no-op: if the schedule's effective QtyOrdered* (Override → Calculated) is <= 0
		// we must not create a DD_Order (no demand to plan). For CREATE we downgrade to NONE; for
		// RECREATE the existing DD_Order must be voided — downgrade to VOID. An informational entry
		// is written to the Event Log so operators can see why no DD_Order was produced.
		if (action == DDOrderReplenishmentAction.CREATE || action == DDOrderReplenishmentAction.RECREATE)
		{
			final BigDecimal effectiveQtyOrdered = shipmentScheduleEffectiveBL.computeQtyOrdered(schedule);
			if (effectiveQtyOrdered == null || effectiveQtyOrdered.signum() <= 0)
			{
				final boolean willVoidExisting = (action == DDOrderReplenishmentAction.RECREATE);
				Loggables.addLog(
						"{0}: effective QtyOrdered={1} for M_ShipmentSchedule_ID={2}; no DD_Order will be created{3}",
						MSG_DDOrderPickingReplenishment_QtyZero.toAD_Message(),
						effectiveQtyOrdered,
						scheduleId.getRepoId(),
						willVoidExisting ? " and the existing DD_Order will be voided" : "");
				action = willVoidExisting
						? DDOrderReplenishmentAction.VOID
						: DDOrderReplenishmentAction.NONE;
			}
		}

		switch (action)
		{
			case NONE:
				return;
			case CREATE:
				createDDOrderFor(schedule);
				return;
			case RECREATE:
				recreateDDOrderFor(schedule, existingDDOrderId);
				return;
			case VOID:
				voidDDOrderFor(existingDDOrderId);
				return;
			default:
				throw new AdempiereException("Unexpected action: " + action);
		}
	}

	/**
	 * Classifies the reconcile action based on the truth-table:
	 * <pre>
	 * warehouseIsAutoDistributionOrder | scheduleRelevant (*) | existingDDOrderId | action
	 * false              | *                    | *                 | NONE
	 * true               | false                | null              | NONE
	 * true               | false                | non-null          | VOID
	 * true               | true                 | null              | CREATE
	 * true               | true                 | non-null          | RECREATE
	 *
	 * (*) scheduleRelevant = IsActive=Y AND Processed=N AND IsClosed=N.
	 *     Processed=Y or IsClosed=Y is treated the same as IsActive=N.
	 * </pre>
	 *
	 * <p>Pure decision method — no DB queries. The caller is responsible for resolving
	 * {@code existingDDOrderId} exactly once before calling this method.</p>
	 */
	@VisibleForTesting
	DDOrderReplenishmentAction classifyAction(
			@NonNull final I_M_ShipmentSchedule schedule,
			@Nullable final DDOrderId existingDDOrderId)
	{
		if (!isOnAutoDistributionOrder(schedule))
		{
			return DDOrderReplenishmentAction.NONE;
		}

		final boolean hasExistingDDOrder = existingDDOrderId != null;
		final boolean scheduleActive = schedule.isActive();
		final boolean scheduleTerminated = scheduleActive && (schedule.isProcessed() || schedule.isClosed());
		final boolean scheduleRelevant = scheduleActive && !scheduleTerminated;

		if (!scheduleRelevant && !hasExistingDDOrder)
		{
			return DDOrderReplenishmentAction.NONE;
		}
		else if (!scheduleRelevant)
		{
			return DDOrderReplenishmentAction.VOID;
		}
		else if (!hasExistingDDOrder)
		{
			return DDOrderReplenishmentAction.CREATE;
		}
		else
		{
			return DDOrderReplenishmentAction.RECREATE;
		}
	}

	private boolean isOnAutoDistributionOrder(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return warehouse.isAutoDistributionOrder();
	}

	/**
	 * Builds exactly one Completed DD_Order for the given (active, packing-warehouse) shipment schedule.
	 * Resolves the source warehouse via the packing warehouse's distribution network;
	 * if no source can be resolved, throws the network-gap exception and creates nothing.
	 *
	 * <p>Warehouse resolution (locators, in-transit warehouse, doc-type) happens here in the Service;
	 * the repository ({@link DDOrderPickingReplenishmentRepository}) only handles the data persistence.</p>
	 */
	private void createDDOrderFor(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final OrgId orgId = OrgId.ofRepoId(schedule.getAD_Org_ID());
		final WarehouseId targetWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse targetWarehouse = warehouseDAO.getById(targetWarehouseId);
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoIdOrNull(targetWarehouse.getDD_NetworkDistribution_ID());

		final WarehouseId sourceWarehouseId = getFirstSourceWarehouseIdOrThrow(networkId, targetWarehouseId, productId);

		// Build the qty as a Quantity in the product's stock UOM (mirrors HUs2DDOrderProducer, which carries a Quantity).
		// Source: the effective QtyOrdered (QtyOrdered_Override → QtyOrdered_Calculated, per IShipmentScheduleEffectiveBL).
		// QtyOrdered_Calculated is set synchronously at schedule creation in OrderLineShipmentScheduleHandler, so it is
		// always populated by the time this reconcile runs. The zero/negative case is intercepted up in
		// {@link #reconcile} — if QtyOrdered is 0 we skip the create (or void the existing DD_Order) and write an
		// informational Event Log entry; the DD_Order code path is never reached with a non-positive qty.
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final BigDecimal qtyToMoveBD = shipmentScheduleEffectiveBL.computeQtyOrdered(schedule);
		final Quantity qty = Quantitys.of(qtyToMoveBD, stockUomId);

		// Resolve warehouse-level values: locators and in-transit warehouse.
		// Mirror HUs2DDOrderProducer: the DD_Order header warehouse is the IN-TRANSIT warehouse;
		// the source/target warehouses live on the line's locators (M_Warehouse_From/To on the header).
		final LocatorId locatorFromId = warehouseBL.getOrCreateDefaultLocatorId(sourceWarehouseId);
		final LocatorId locatorToId = warehouseBL.getOrCreateDefaultLocatorId(targetWarehouseId);
		final WarehouseId inTransitWarehouseId = warehouseBL.getInTransitWarehouseId(orgId);

		// Resolve Distribution Order document type — required by completeIt.
		final DocTypeId docTypeId = docTypeDAO.getDocTypeIdOrNull(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(Env.getAD_Client_ID())
						.adOrgId(orgId.getRepoId())
						.build());
		// Fail with a clear config-time error rather than letting a -1 doc-type surface during completeIt.
		Check.assumeNotNull(docTypeId, "Distribution Order doc-type must exist for orgId={}", orgId);

		final CreateDDOrderReplenishmentRequest request = CreateDDOrderReplenishmentRequest.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.sourceWarehouseId(sourceWarehouseId)
				.targetWarehouseId(targetWarehouseId)
				.inTransitWarehouseId(inTransitWarehouseId)
				.locatorFromId(locatorFromId)
				.locatorToId(locatorToId)
				.docTypeId(docTypeId)
				.productId(productId)
				.qty(qty)
				.orgId(orgId)
				.datePromised(SystemTime.asInstant())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(schedule.getC_BPartner_ID()))
				.build();

		// Repository saves the header + line; Service completes the document (document engine is not DAO).
		final I_DD_Order ddOrder = repository.saveDraftDDOrder(request);
		documentBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
	}

	private void voidDDOrderFor(@NonNull final DDOrderId existingDDOrderId)
	{
		if (isPickerBusy(existingDDOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
		}
		final I_DD_Order ddOrderRecord = repository.getById(existingDDOrderId);
		documentBL.processEx(ddOrderRecord, IDocument.ACTION_Void, IDocument.STATUS_Voided);
	}

	/**
	 * RECREATE: the schedule is still active but has changed (e.g. qty changed) while a live DD_Order exists.
	 * Picker-busy guard first: if busy, throw without touching anything.
	 * Then void the existing DD_Order and create a fresh one from the current schedule data.
	 *
	 * <p><b>No transaction boundary here.</b> The void + create is only atomic if the caller
	 * ({@code DDOrderReplenishmentEventHandler}) wraps {@link #reconcile(ShipmentScheduleId)} in
	 * {@code trxManager.runInThreadInheritedTrx(...)} so a create-failure rolls back the void.</p>
	 *
	 * <p>{@code existingDDOrderId} is resolved exactly once by the caller ({@link #reconcile}) — no re-query here.</p>
	 */
	private void recreateDDOrderFor(
			@NonNull final I_M_ShipmentSchedule schedule,
			@NonNull final DDOrderId existingDDOrderId)
	{
		// Picker-busy guard: checked ONCE up front, before any mutation
		if (isPickerBusy(existingDDOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
		}

		// Void the existing DD_Order (picker already checked — no double check needed)
		final I_DD_Order ddOrderRecord = repository.getById(existingDDOrderId);
		documentBL.processEx(ddOrderRecord, IDocument.ACTION_Void, IDocument.STATUS_Voided);

		// Create a fresh DD_Order from the current schedule data
		createDDOrderFor(schedule);
	}

	public void rebuildDrift()
	{
		final Set<WarehouseId> autoDistributionWarehouseIds = repository.getAutoDistributionWarehouseIds();

		// try-with-resources: close the DB cursor even if publishOne throws mid-stream.
		try (final Stream<ShipmentScheduleId> schedules = repository.streamSchedulesNeedingDDOrder(autoDistributionWarehouseIds))
		{
			schedules.forEach(reconciliationEventPublisher::publishOne);
		}
	}

	public void assertWarehouseConfigurationIsValid(@NonNull final I_M_Warehouse warehouse)
	{
		if (warehouse.isAutoDistributionOrder() && warehouse.getDD_NetworkDistribution_ID() <= 0)
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_MandatoryNetwork);
		}
	}

	public boolean isPickerBusy(@NonNull final DDOrderId ddOrderId)
	{
		final ShipmentScheduleId scheduleId = repository.getShipmentScheduleId(ddOrderId);
		return repository.existsPickingJobLineForSchedule(scheduleId);
	}

	/**
	 * Returns the source (stocking) warehouse ID for the given target warehouse from the distribution network,
	 * or throws a network-gap exception if no matching line is found.
	 *
	 * <p>This is the mandatory call site for {@link #createDDOrderFor}: when no source warehouse can be resolved
	 * the DD_Order must not be created and the caller receives a clear error instead of a silent no-op.</p>
	 */
	private WarehouseId getFirstSourceWarehouseIdOrThrow(
			@Nullable final DistributionNetworkId networkId,
			@NonNull final WarehouseId targetWarehouseId,
			@NonNull final ProductId productId)
	{
		return resolveSourceWarehouse(targetWarehouseId, productId, networkId)
				.orElseThrow(() -> new AdempiereException(MSG_DDOrderPickingReplenishment_NetworkGap, networkId, productId));
	}

	/**
	 * Resolves the source (stocking) warehouse for a product given the packing warehouse and distribution network.
	 * Returns the source warehouse from the highest-priority line (i.e. lowest {@link DistributionNetworkLine#getPriorityNo()})
	 * whose target is the packing warehouse.
	 * Lines are sorted ascending by {@code priorityNo} inside {@link de.metas.material.planning.ddorder.DistributionNetwork},
	 * so the first matching line is always the highest-priority one.
	 *
	 * <p>Note: {@code productId} is accepted for future per-product filtering but is not yet used,
	 * because {@link DistributionNetworkLine} does not carry product-level constraints.</p>
	 *
	 * @return the source warehouse of the highest-priority matching line,
	 *         or empty if {@code networkId} is null or no matching line exists
	 */
	@VisibleForTesting
	Optional<WarehouseId> resolveSourceWarehouse(
			@NonNull final WarehouseId targetWarehouseId,
			@NonNull final ProductId productId,
			@Nullable final DistributionNetworkId networkId)
	{
		if (networkId == null)
		{
			return Optional.empty();
		}

		final DistributionNetwork network = distributionNetworkRepository.getById(networkId);
		final List<DistributionNetworkLine> lines = network.getLinesByTargetWarehouse(targetWarehouseId);
		if (lines.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(lines.get(0).getSourceWarehouseId());
	}
}
