package de.metas.inoutcandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.util.Services;
import lombok.NonNull;

@Validator(I_M_ShipmentSchedule_QtyPicked.class)
public class M_ShipmentSchedule_QtyPicked
{

	/**
	 * Invalidates the {@link I_M_ShipmentSchedule} referenced by the given <code>shipmentScheduleQtyPicked</code>.
	 * <p>
	 * Note that a change in an {@link I_M_ShipmentSchedule_QtyPicked} records indicates that some qty is moved from <code>QtyToDeliver</code> to <code>QtyPistList</code>. the qtys that are available
	 * to be allocated to other shipment schedules are not changes by this. therefore, we only need to invalidate the single schedule referenced by the given parameter value.
	 * <p>
	 * Also note that as of this task, it is important to invalidate the shipment schedule if a <code>M_ShipmentSchedule_QtyPicked</code> changes, because we no longer have a DB-function to compute
	 * the picked quantity. Instead, we have the picked qty added to {@link I_M_ShipmentSchedule#COLUMNNAME_QtyPickList QtyPickList} by the regular shipment schedule updating mechanism.
	 *
	 * @param shipmentScheduleQtyPicked
	 */
	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE,
			ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedule(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked)
	{
		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(shipmentScheduleQtyPicked.getM_ShipmentSchedule_ID());

		final IShipmentScheduleInvalidateBL invalidSchedulesService = Services.get(IShipmentScheduleInvalidateBL.class);
		invalidSchedulesService.flagForRecompute(shipmentScheduleId);
	}
}
