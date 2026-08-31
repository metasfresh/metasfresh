package de.metas.inoutcandidate.modelvalidator;

import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Flags a shipper's unprocessed {@link de.metas.inoutcandidate.model.I_M_ShipmentSchedule}s for recompute
 * whenever the shipper's {@code PriorityRule} changes -- so that
 * {@code OrderLineShipmentScheduleHandler#updateShipmentScheduleFromOrder} re-derives their priority from the
 * (new) shipper value.
 */
@Interceptor(I_M_Shipper.class)
@Component
@RequiredArgsConstructor
public class M_Shipper_ShipmentSchedule
{
	@NonNull private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	@NonNull private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	@ModelChange(timings = {
			ModelValidator.TYPE_AFTER_NEW,
			ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = {
					I_M_Shipper.COLUMNNAME_PriorityRule })
	public void flagUnprocessedSchedulesForRecompute(final I_M_Shipper shipper)
	{
		final ShipperId shipperId = ShipperId.ofRepoId(shipper.getM_Shipper_ID());

		final Set<ShipmentScheduleId> unprocessedScheduleIds = shipmentSchedulePA.retrieveUnprocessedIdsByShipperId(shipperId);

		if (unprocessedScheduleIds.isEmpty())
		{
			return;
		}

		shipmentScheduleInvalidateBL.flagForRecompute(unprocessedScheduleIds);
	}
}
