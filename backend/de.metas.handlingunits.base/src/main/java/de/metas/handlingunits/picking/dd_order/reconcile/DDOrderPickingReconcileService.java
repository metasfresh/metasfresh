package de.metas.handlingunits.picking.dd_order.reconcile;

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.compiere.model.I_M_Warehouse;
import org.springframework.stereotype.Component;

@Component
public class DDOrderPickingReconcileService implements DDOrderPickingReconcileBL
{
	private final DDOrderPickingReconcileRepository repository;

	public DDOrderPickingReconcileService(@NonNull final DDOrderPickingReconcileRepository repository)
	{
		this.repository = repository;
	}

	@Override
	public void assertCanChange(@NonNull final I_M_ShipmentSchedule schedule)
	{
		throw new UnsupportedOperationException("not implemented yet — Task T9");
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
