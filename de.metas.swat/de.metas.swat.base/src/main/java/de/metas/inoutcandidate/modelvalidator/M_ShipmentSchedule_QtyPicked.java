package de.metas.inoutcandidate.modelvalidator;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked;
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
		final List<I_M_ShipmentSchedule> singletonList = ImmutableList.of(shipmentScheduleQtyPicked.getM_ShipmentSchedule());
		final String trxName = InterfaceWrapperHelper.getTrxName(shipmentScheduleQtyPicked);

		Services.get(IShipmentSchedulePA.class).invalidate(singletonList, trxName);
	}
}
