package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DDOrderPickingReconcileService implements DDOrderPickingReconcileBL
{
	private static final AdMessageKey MSG_DDOrderPickingReconcile_PickerBusy = AdMessageKey.of("DDOrderPickingReconcile_PickerBusy");

	@NonNull private final DDOrderPickingReconcileRepository repository;
	@NonNull private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL;
	@NonNull private final IWarehouseDAO warehouseDAO;

	public DDOrderPickingReconcileService(@NonNull final DDOrderPickingReconcileRepository repository)
	{
		this.repository = repository;
		this.shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		this.warehouseDAO = Services.get(IWarehouseDAO.class);
	}

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
		final Optional<DDOrderId> existingDDOrderId = repository.findActiveDDOrderForSchedule(scheduleId);
		if (!existingDDOrderId.isPresent())
		{
			return;
		}

		final DDOrderId ddOrderId = existingDDOrderId.get();
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
}
