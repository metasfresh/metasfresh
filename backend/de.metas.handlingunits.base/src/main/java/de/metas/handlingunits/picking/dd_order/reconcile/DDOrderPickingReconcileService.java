package de.metas.handlingunits.picking.dd_order.reconcile;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.handlingunits.picking.dd_order.reconcile.event.DDOrderReconciliationEventPublisher;
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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DDOrderPickingReconcileService implements DDOrderPickingReconcileBL
{
	private static final AdMessageKey MSG_DDOrderPickingReconcile_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");
	private static final AdMessageKey MSG_DDOrderPickingReconcile_NetworkGap = AdMessageKey.of("DDOrderPickingReconcile_NetworkGap");

	private static final String TRX_PROPERTY_ScheduleReconcile = "DDOrderPickingReconcile";

	@NonNull private final DDOrderPickingReconcileRepository repository;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final ITrxManager trxManager;
	@NonNull private final DDOrderReconciliationEventPublisher reconciliationEventPublisher;
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);

	@Override
	public void assertCanChange(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		if (!warehouse.isPackingWarehouse())
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
			throw new AdempiereException(MSG_DDOrderPickingReconcile_PickerBusy, ddOrderId);
		}
	}

	@Override
	public void scheduleReconcileAfterCommit(@NonNull final I_M_ShipmentSchedule schedule)
	{
		if (!isOnPackingWarehouse(schedule))
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

	@Override
	public void reconcile(@NonNull final ShipmentScheduleId scheduleId)
	{
		final I_M_ShipmentSchedule schedule = shipmentScheduleBL.getById(scheduleId);
		final DDOrderReconcileAction action = classifyAction(schedule);
		switch (action)
		{
			case NONE:
				return;
			case CREATE:
				createDDOrderFor(schedule);
				return;
			case RECREATE:
				recreateDDOrderFor(scheduleId, schedule);
				return;
			case VOID:
				voidDDOrderFor(scheduleId);
				return;
			default:
				throw new AdempiereException("Unexpected action: " + action);
		}
	}

	/**
	 * Classifies the reconcile action based on the truth-table:
	 * <pre>
	 * warehouseIsPacking | scheduleActive | existingDDOrder | action
	 * false              | *              | *               | NONE
	 * true               | false          | none            | NONE
	 * true               | false          | exists          | VOID
	 * true               | true           | none            | CREATE
	 * true               | true           | exists          | RECREATE
	 * </pre>
	 */
	@VisibleForTesting
	DDOrderReconcileAction classifyAction(@NonNull final I_M_ShipmentSchedule schedule)
	{
		if (!isOnPackingWarehouse(schedule))
		{
			return DDOrderReconcileAction.NONE;
		}

		final ShipmentScheduleId scheduleId = ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID());
		final boolean hasExistingDDOrder = repository.findActiveDDOrderForSchedule(scheduleId).isPresent();
		final boolean scheduleActive = schedule.isActive();

		if (!scheduleActive && !hasExistingDDOrder)
		{
			return DDOrderReconcileAction.NONE;
		}
		else if (!scheduleActive)
		{
			return DDOrderReconcileAction.VOID;
		}
		else if (!hasExistingDDOrder)
		{
			return DDOrderReconcileAction.CREATE;
		}
		else
		{
			return DDOrderReconcileAction.RECREATE;
		}
	}

	private boolean isOnPackingWarehouse(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId warehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return warehouse.isPackingWarehouse();
	}

	/**
	 * Builds exactly one Completed DD_Order for the given (active, packing-warehouse) shipment schedule.
	 * Resolves the source warehouse via the packing warehouse's distribution network;
	 * if no source can be resolved, throws the network-gap exception and creates nothing.
	 */
	private void createDDOrderFor(@NonNull final I_M_ShipmentSchedule schedule)
	{
		final WarehouseId packingWarehouseId = shipmentScheduleEffectiveBL.getWarehouseId(schedule);
		final I_M_Warehouse packingWarehouse = warehouseDAO.getById(packingWarehouseId);
		final ProductId productId = ProductId.ofRepoId(schedule.getM_Product_ID());

		final DistributionNetworkId networkId = DistributionNetworkId.ofRepoIdOrNull(packingWarehouse.getDD_NetworkDistribution_ID());

		final WarehouseId sourceWarehouseId = resolveSourceWarehouse(packingWarehouseId, productId, networkId)
				.orElseThrow(() -> new AdempiereException(MSG_DDOrderPickingReconcile_NetworkGap, networkId, productId));

		// Build the qty as a Quantity in the product's stock UOM (mirrors HUs2DDOrderProducer, which carries a Quantity).
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final Quantity qty = Quantitys.of(schedule.getQtyToDeliver(), stockUomId);

		final CreateDDOrderRequest request = CreateDDOrderRequest.builder()
				.shipmentScheduleId(ShipmentScheduleId.ofRepoId(schedule.getM_ShipmentSchedule_ID()))
				.sourceWarehouseId(sourceWarehouseId)
				.targetWarehouseId(packingWarehouseId)
				.productId(productId)
				.qty(qty)
				.orgId(OrgId.ofRepoId(schedule.getAD_Org_ID()))
				.datePromised(SystemTime.asInstant())
				.bpartnerId(BPartnerId.ofRepoIdOrNull(schedule.getC_BPartner_ID()))
				.build();

		repository.createCompletedDDOrder(request);
	}

	private void voidDDOrderFor(@NonNull final ShipmentScheduleId scheduleId)
	{
		final DDOrderId ddOrderId = repository.findActiveDDOrderForSchedule(scheduleId).orElse(null);
		if (ddOrderId == null)
		{
			return;
		}
		if (isPickerBusy(ddOrderId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReconcile_PickerBusy, ddOrderId);
		}
		repository.voidDDOrder(ddOrderId);
	}

	/**
	 * RECREATE: the schedule is still active but has changed (e.g. qty changed) while a live DD_Order exists.
	 * Picker-busy guard first: if busy, throw without touching anything.
	 * Then void the existing DD_Order and create a fresh one from the current schedule data.
	 */
	private void recreateDDOrderFor(
			@NonNull final ShipmentScheduleId scheduleId,
			@NonNull final I_M_ShipmentSchedule schedule)
	{
		final DDOrderId existingId = repository.findActiveDDOrderForSchedule(scheduleId)
				.orElse(null);
		if (existingId == null)
		{
			// should not happen under normal flow (classifyAction only returns RECREATE when one exists)
			createDDOrderFor(schedule);
			return;
		}

		// Picker-busy guard: checked ONCE up front, before any mutation
		if (isPickerBusy(existingId))
		{
			throw new AdempiereException(MSG_DDOrderPickingReconcile_PickerBusy, existingId);
		}

		// Void the existing DD_Order (picker already checked — no double check needed)
		repository.voidDDOrder(existingId);

		// Create a fresh DD_Order from the current schedule data
		createDDOrderFor(schedule);
	}

	@Override
	public void rebuildDrift()
	{
		throw new UnsupportedOperationException("not implemented yet — Task T16");
	}

	@Override
	public void assertWarehouseConfigurationIsValid(@NonNull final I_M_Warehouse warehouse)
	{
		throw new UnsupportedOperationException("not implemented yet — Task T21");
	}

	@Override
	public boolean isPickerBusy(@NonNull final DDOrderId ddOrderId)
	{
		return repository.existsPickingJobLineForDDOrder(ddOrderId);
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
			@NonNull final WarehouseId packingWarehouseId,
			@NonNull final ProductId productId,
			@Nullable final DistributionNetworkId networkId)
	{
		if (networkId == null)
		{
			return Optional.empty();
		}

		final DistributionNetwork network = distributionNetworkRepository.getById(networkId);
		final List<DistributionNetworkLine> lines = network.getLinesByTargetWarehouse(packingWarehouseId);
		if (lines.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(lines.get(0).getSourceWarehouseId());
	}
}
