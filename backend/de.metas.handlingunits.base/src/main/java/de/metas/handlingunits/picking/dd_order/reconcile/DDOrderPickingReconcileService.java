package de.metas.handlingunits.picking.dd_order.reconcile;

import com.google.common.annotations.VisibleForTesting;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.planning.ddorder.DistributionNetwork;
import de.metas.material.planning.ddorder.DistributionNetworkId;
import de.metas.material.planning.ddorder.DistributionNetworkLine;
import de.metas.material.planning.ddorder.DistributionNetworkRepository;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DDOrderPickingReconcileService implements DDOrderPickingReconcileBL
{
	private static final AdMessageKey MSG_DDOrderPickingReconcile_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");

	@NonNull private final DDOrderPickingReconcileRepository repository;
	@NonNull private final DistributionNetworkRepository distributionNetworkRepository;
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

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
		throw new UnsupportedOperationException("not implemented yet — Task T15");
	}

	@Override
	public void reconcile(@NonNull final ShipmentScheduleId scheduleId)
	{
		throw new UnsupportedOperationException("not implemented yet — Task T11+");
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
