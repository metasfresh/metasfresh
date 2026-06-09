package de.metas.distribution.ddorder.replenishment;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.replenishment.event.DDOrderReplenishmentEventPublisher;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.picking.job.repository.PickingJobRepository;
import de.metas.handlingunits.picking.job_schedule.service.PickingJobScheduleService;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_Picking_Job_Schedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.organization.OrgId;
import de.metas.picking.api.PickingJobScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.X_DD_Order;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DDOrderPickingReplenishmentService
{
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_NetworkGap = AdMessageKey.of("DDOrderPickingReconcile_NetworkGap");
	private static final AdMessageKey MSG_DDOrderPickingReplenishment_MandatoryNetwork = AdMessageKey.of("DDOrderPickingReconcile_MandatoryNetwork");
	@VisibleForTesting
	static final AdMessageKey MSG_DDOrderPickingReplenishment_QtyZero = AdMessageKey.of("DDOrderPickingReconcile_QtyZero");
	@VisibleForTesting
	static final AdMessageKey MSG_DDOrderPickingReplenishment_NoPickFromLocator = AdMessageKey.of("DDOrderPickingReconcile_NoPickFromLocator");

	// FQN trx-property key: avoids collisions with any other service that might register an
	// after-commit accumulator under a shorter, easier-to-clash name.
	private static final String TRX_PROPERTY_ScheduleReconcile = "de.metas.distribution.ddorder.replenishment.DDOrderPickingReplenishment";

	@NonNull private final PickingJobRepository pickingJobRepository;
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final DDOrderService ddOrderService;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderReplenishmentEventPublisher reconciliationEventPublisher;
	@NonNull private final PickingJobScheduleService pickingJobScheduleService;
	@NonNull private final WorkplaceService workplaceService;
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	public void assertCanChange(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		if (!isOnAutoDistributionOrder(jobSchedule))
		{
			return;
		}

		final PickingJobScheduleId jobScheduleId = PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID());
		final DDOrderId ddOrderId = ddOrderLowLevelDAO.findActiveDDOrderForPickingJobSchedule(jobScheduleId).orElse(null);
		if (ddOrderId == null)
		{
			return;
		}
		if (isPickerBusy(ddOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, ddOrderId);
		}
	}

	public void scheduleReconcileAfterCommit(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		// Wired to afterNew/afterChange only (the delete→void runs synchronously in-trx via
		// voidDDOrdersForDeletedAssignment, NOT through this after-commit path). We always schedule and let
		// reconcile() classify, so a non-packing assignment (or one with no DD_Order) simply no-ops.
		final PickingJobScheduleId jobScheduleId = PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID());

		// Accumulate the assignment id per-trx: exactly ONE reconcile event per distinct id is published
		// after the current transaction commits (the collector deduplicates equal items).
		// If there is no active trx, the processor runs inline immediately.
		trxManager.accumulateAndProcessAfterCommit(
				TRX_PROPERTY_ScheduleReconcile,
				Collections.singletonList(jobScheduleId),
				reconciliationEventPublisher::publishAll);
	}

	/**
	 * Re-reads the assignment, classifies the action (NONE/CREATE/RECREATE/VOID), executes it.
	 *
	 * <p>The trigger record is the workstation assignment ({@code M_Picking_Job_Schedule}). When the assignment
	 * was deleted (afterDelete reconcile) it no longer exists; that is treated as "not relevant" and any existing
	 * DD_Order linked to it is voided.</p>
	 *
	 * <p><b>No transaction boundary here.</b> The VOID-then-CREATE of the RECREATE branch is only atomic if
	 * the caller wraps this call in a transaction. The caller ({@code DDOrderReplenishmentEventHandler}) wraps
	 * this call in {@code trxManager.runInThreadInheritedTrx(...)} so a create-failure rolls back the void.</p>
	 */
	public void reconcile(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		final I_M_Picking_Job_Schedule jobSchedule = loadAssignmentOrNull(jobScheduleId);
		final DDOrderId existingDDOrderId = ddOrderLowLevelDAO.findActiveDDOrderForPickingJobSchedule(jobScheduleId).orElse(null);
		DDOrderReplenishmentAction action = classifyAction(jobSchedule, existingDDOrderId);

		// Zero-qty soft no-op: if the assignment's QtyToPick is <= 0 there is no demand to plan. For CREATE we
		// downgrade to NONE; for RECREATE the existing DD_Order must be voided — downgrade to VOID. An
		// informational entry is written to the Event Log so operators can see why no DD_Order was produced.
		if (action == DDOrderReplenishmentAction.CREATE || action == DDOrderReplenishmentAction.RECREATE)
		{
			final BigDecimal qtyToPick = jobSchedule != null ? jobSchedule.getQtyToPick() : null;
			if (qtyToPick == null || qtyToPick.signum() <= 0)
			{
				final boolean willVoidExisting = (action == DDOrderReplenishmentAction.RECREATE);
				Loggables.addLog(
						"{0}: QtyToPick={1} for M_Picking_Job_Schedule_ID={2}; no DD_Order will be created{3}",
						MSG_DDOrderPickingReplenishment_QtyZero.toAD_Message(),
						qtyToPick,
						jobScheduleId.getRepoId(),
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
				createDDOrderFor(jobScheduleId, jobSchedule);
				return;
			case RECREATE:
				recreateDDOrderFor(jobScheduleId, jobSchedule, existingDDOrderId);
				return;
			case VOID:
				voidDDOrderFor(existingDDOrderId);
				return;
			default:
				throw new AdempiereException("Unexpected action: " + action);
		}
	}

	@Nullable
	private I_M_Picking_Job_Schedule loadAssignmentOrNull(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		return InterfaceWrapperHelper.load(jobScheduleId.getRepoId(), I_M_Picking_Job_Schedule.class);
	}

	/**
	 * Classifies the reconcile action based on the truth-table:
	 * <pre>
	 * warehouseIsAutoDistributionOrder | assignmentRelevant (*) | existingDDOrderId | action
	 * false              | *                    | *                 | NONE
	 * true               | false                | null              | NONE
	 * true               | false                | non-null          | VOID
	 * true               | true                 | null              | CREATE
	 * true               | true                 | non-null          | RECREATE
	 *
	 * (*) assignmentRelevant = assignment exists AND IsActive=Y AND Processed=N.
	 *     A missing (deleted) or Processed=Y assignment is treated the same as IsActive=N.
	 * </pre>
	 *
	 * <p>Pure decision method — no DB queries. The caller resolves {@code existingDDOrderId} exactly once
	 * before calling this method.</p>
	 */
	@VisibleForTesting
	DDOrderReplenishmentAction classifyAction(
			@Nullable final I_M_Picking_Job_Schedule jobSchedule,
			@Nullable final DDOrderId existingDDOrderId)
	{
		final boolean hasExistingDDOrder = existingDDOrderId != null;

		if (jobSchedule == null || !isOnAutoDistributionOrder(jobSchedule))
		{
			// Not on a packing warehouse (or assignment gone): void any existing DD_Order, else no-op.
			return hasExistingDDOrder ? DDOrderReplenishmentAction.VOID : DDOrderReplenishmentAction.NONE;
		}

		final boolean assignmentActive = jobSchedule.isActive();
		final boolean assignmentTerminated = assignmentActive && jobSchedule.isProcessed();
		final boolean assignmentRelevant = assignmentActive && !assignmentTerminated;

		if (!assignmentRelevant && !hasExistingDDOrder)
		{
			return DDOrderReplenishmentAction.NONE;
		}
		else if (!assignmentRelevant)
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

	private boolean isOnAutoDistributionOrder(@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(ShipmentScheduleId.ofRepoId(jobSchedule.getM_ShipmentSchedule_ID()));
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse warehouse = warehouseBL.getById(warehouseId);
		return warehouse.isAutoDistributionOrder();
	}

	/**
	 * Builds exactly one Completed DD_Order for the given (active, packing-warehouse) workstation assignment.
	 * Demand qty = the assignment's {@code QtyToPick} (in the assignment's UOM); target locator = the
	 * workstation's pick-from locator; source warehouse via the packing warehouse's distribution network.
	 * If the workstation has no pick-from locator the DD_Order is skipped (informational log, no DD_Order).
	 */
	private void createDDOrderFor(
			@NonNull final PickingJobScheduleId jobScheduleId,
			@NonNull final I_M_Picking_Job_Schedule jobSchedule)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(ShipmentScheduleId.ofRepoId(jobSchedule.getM_ShipmentSchedule_ID()));

		final OrgId orgId = OrgId.ofRepoId(schedule.getAD_Org_ID());
		final WarehouseId targetWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse targetWarehouse = warehouseBL.getById(targetWarehouseId);
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());

		// Target locator = the workstation's configured pick-from locator. If unset there is nowhere to deliver
		// to → skip (informational log, no DD_Order). Mirrors the network-gap soft-fail.
		final WorkplaceId workplaceId = WorkplaceId.ofRepoId(jobSchedule.getC_Workplace_ID());
		final LocatorId locatorToId = workplaceService.getById(workplaceId).getPickFromLocatorId();
		if (locatorToId == null)
		{
			Loggables.addLog(
					"{0}: C_Workplace_ID={1} has no PickFrom_Locator_ID for M_Picking_Job_Schedule_ID={2}; no DD_Order will be created",
					MSG_DDOrderPickingReplenishment_NoPickFromLocator.toAD_Message(),
					jobSchedule.getC_Workplace_ID(),
					jobScheduleId.getRepoId());
			return;
		}

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoIdOrNull(targetWarehouse.getDD_NetworkDistribution_ID());
		final WarehouseId sourceWarehouseId = getFirstSourceWarehouseIdOrThrow(networkId, targetWarehouseId, productId);

		// Demand qty = the assignment's QtyToPick in the assignment's UOM. The zero/negative case is intercepted
		// up in #reconcile, so the DD_Order code path is never reached with a non-positive qty.
		final UomId qtyUomId = UomId.ofRepoId(jobSchedule.getC_UOM_ID());
		final Quantity qty = Quantitys.of(jobSchedule.getQtyToPick(), qtyUomId);

		// Single source locator: the source warehouse's default locator (multi-locator split is a later task).
		final LocatorId locatorFromId = warehouseBL.getOrCreateDefaultLocatorId(sourceWarehouseId);
		final WarehouseId inTransitWarehouseId = warehouseBL.getInTransitWarehouseId(orgId);

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(schedule.getAD_Client_ID())
						.adOrgId(orgId.getRepoId())
						.build());

		final I_DD_Order ddOrder = saveDraftDDOrder(CreateDDOrderReplenishmentRequest.builder()
				.pickingJobScheduleId(jobScheduleId)
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
				.build());
		ddOrderService.complete(DDOrderId.ofRepoId(ddOrder.getDD_Order_ID()));
	}

	/**
	 * Builds exactly one {@link I_DD_Order} (with a single {@link I_DD_OrderLine}) for the picking-reconcile flow,
	 * persists both records (header then line) via {@link DDOrderLowLevelDAO}, and returns the saved (Drafted) order.
	 */
	private I_DD_Order saveDraftDDOrder(@NonNull final CreateDDOrderReplenishmentRequest request)
	{
		final OrgId orgId = request.getOrgId();

		//
		// Header
		final I_DD_Order ddOrder = InterfaceWrapperHelper.newInstance(I_DD_Order.class);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		if (request.getBpartnerId() != null)
		{
			ddOrder.setC_BPartner_ID(request.getBpartnerId().getRepoId());
		}
		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(request.getDocTypeId()));
		ddOrder.setM_Warehouse_ID(request.getInTransitWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_From_ID(request.getSourceWarehouseId().getRepoId());
		ddOrder.setM_Warehouse_To_ID(request.getTargetWarehouseId().getRepoId());
		// PP_Plant from the target warehouse (mirrors HUs2DDOrderProducer) — the libero DD_OrderLine interceptor
		// derives PP_Plant_From_ID from it; without it that interceptor logs a benign "@NotFound@ @PP_Plant_ID@" WARN.
		warehouseBL.getPlantId(request.getTargetWarehouseId())
				.ifPresent(plantId -> ddOrder.setPP_Plant_ID(plantId.getRepoId()));
		ddOrder.setM_Picking_Job_Schedule_ID(request.getPickingJobScheduleId().getRepoId());
		ddOrder.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrder.setDateOrdered(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setIsSOTrx(false);
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrderLowLevelDAO.save(ddOrder);

		//
		// Line
		final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class);
		ddOrderLine.setDD_Order_ID(ddOrder.getDD_Order_ID());
		ddOrderLine.setAD_Org_ID(orgId.getRepoId());
		ddOrderLine.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderLine.setDatePromised(ddOrder.getDatePromised());
		ddOrderLine.setM_Product_ID(request.getProductId().getRepoId());
		ddOrderLine.setC_UOM_ID(request.getQty().getUomId().getRepoId());
		// This flow operates in the assignment's UOM (internal pick-to-packing move):
		// QtyEntered == QtyOrdered == TargetQty intentionally.
		ddOrderLine.setQtyEntered(request.getQty().toBigDecimal());
		ddOrderLine.setQtyOrdered(request.getQty().toBigDecimal());
		ddOrderLine.setTargetQty(request.getQty().toBigDecimal());
		ddOrderLine.setM_Locator_ID(request.getLocatorFromId().getRepoId());
		ddOrderLine.setM_LocatorTo_ID(request.getLocatorToId().getRepoId());
		ddOrderLine.setM_Picking_Job_Schedule_ID(request.getPickingJobScheduleId().getRepoId());
		ddOrderLine.setM_ShipmentSchedule_ID(request.getShipmentScheduleId().getRepoId());
		ddOrderLine.setIsInvoiced(false);
		ddOrderLowLevelDAO.save(ddOrderLine);

		return ddOrder;
	}

	private void voidDDOrderFor(@NonNull final DDOrderId existingDDOrderId)
	{
		if (isPickerBusy(existingDDOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
		}
		ddOrderService.voidIt(existingDDOrderId);

		// Null the back-reference to M_Picking_Job_Schedule on the (now voided) DD_Order header and its lines.
		// voidIt does NOT clear it, so when the void runs synchronously inside the assignment's delete transaction
		// (afterDelete -> voidDDOrdersForDeletedAssignment) the deferrable FK mpickingjobschedule_ddorder would still
		// point at the about-to-be-deleted assignment and fail at commit. Unlinking here makes that delete clean.
		final I_DD_Order ddOrder = ddOrderLowLevelDAO.getById(existingDDOrderId);
		ddOrder.setM_Picking_Job_Schedule_ID(-1);
		ddOrderLowLevelDAO.save(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLowLevelDAO.retrieveLines(ddOrder))
		{
			ddOrderLine.setM_Picking_Job_Schedule_ID(-1);
			ddOrderLowLevelDAO.save(ddOrderLine);
		}
	}

	/**
	 * Synchronous VOID + unlink for the delete→void path (afterDelete of {@link I_M_Picking_Job_Schedule}).
	 *
	 * <p>Unlike the create/update flow (which reconciles after-commit), the delete must void and unlink the linked
	 * DD_Order(s) <b>in the current (delete) transaction</b>, before the assignment row is flushed. Otherwise the
	 * deferrable FK {@code mpickingjobschedule_ddorder} (DD_Order/DD_OrderLine → M_Picking_Job_Schedule) still
	 * references the deleted assignment and fails at commit.</p>
	 *
	 * <p>No live DD_Order linked → clean no-op.</p>
	 */
	public void voidDDOrdersForDeletedAssignment(@NonNull final PickingJobScheduleId jobScheduleId)
	{
		final DDOrderId existingDDOrderId = ddOrderLowLevelDAO.findActiveDDOrderForPickingJobSchedule(jobScheduleId).orElse(null);
		if (existingDDOrderId == null)
		{
			return;
		}
		voidDDOrderFor(existingDDOrderId);
	}

	/**
	 * RECREATE: the assignment is still active but has changed (e.g. qty changed) while a live DD_Order exists.
	 * Picker-busy guard first: if busy, throw without touching anything. Then void the existing DD_Order and
	 * create a fresh one from the current assignment data.
	 */
	private void recreateDDOrderFor(
			@NonNull final PickingJobScheduleId jobScheduleId,
			@NonNull final I_M_Picking_Job_Schedule jobSchedule,
			@NonNull final DDOrderId existingDDOrderId)
	{
		if (isPickerBusy(existingDDOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReplenishment_PickerBusy, existingDDOrderId);
		}

		ddOrderService.voidIt(existingDDOrderId);
		createDDOrderFor(jobScheduleId, jobSchedule);
	}

	public void rebuildDrift()
	{
		// Republish a reconcile event for every active, not-processed assignment on a packing warehouse that has
		// no live (Completed) DD_Order linked — the watchdog's "drifted" assignments.
		try (final java.util.stream.Stream<PickingJobScheduleId> jobScheduleIds = streamAssignmentsNeedingDDOrder())
		{
			jobScheduleIds.forEach(reconciliationEventPublisher::publishOne);
		}
	}

	/**
	 * Streams the {@link PickingJobScheduleId}s of active, not-processed assignments that have NO live (Completed)
	 * {@link I_DD_Order} linked. Packing-warehouse relevance is decided downstream by {@link #classifyAction}
	 * (a non-packing assignment no-ops), so this scan does not pre-filter on the warehouse.
	 */
	private java.util.stream.Stream<PickingJobScheduleId> streamAssignmentsNeedingDDOrder()
	{
		final org.compiere.model.IQuery<I_DD_Order> liveDDOrderSubQuery = ddOrderLowLevelDAO.queryCompletedDDOrders();

		return queryBL
				.createQueryBuilder(I_M_Picking_Job_Schedule.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Picking_Job_Schedule.COLUMNNAME_Processed, false)
				.addNotInSubQueryFilter(
						I_M_Picking_Job_Schedule.COLUMNNAME_M_Picking_Job_Schedule_ID,
						I_DD_Order.COLUMNNAME_M_Picking_Job_Schedule_ID,
						liveDDOrderSubQuery)
				.create()
				.stream()
				.map(jobSchedule -> PickingJobScheduleId.ofRepoId(jobSchedule.getM_Picking_Job_Schedule_ID()));
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
		final ShipmentScheduleId scheduleId = ddOrderLowLevelDAO.getShipmentScheduleId(ddOrderId);
		return pickingJobRepository.existsActivePickingJobLineForSchedule(scheduleId);
	}

	private WarehouseId getFirstSourceWarehouseIdOrThrow(
			@Nullable final DistributionNetworkId networkId,
			@NonNull final WarehouseId targetWarehouseId,
			@NonNull final ProductId productId)
	{
		return resolveSourceWarehouse(targetWarehouseId, productId, networkId)
				.orElseThrow(() -> new AdempiereException(MSG_DDOrderPickingReplenishment_NetworkGap, networkId, productId));
	}

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
		return network.getFirstSourceWarehouseIdByTargetWarehouse(targetWarehouseId);
	}
}
