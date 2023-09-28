package de.metas.inoutcandidate.shippingnotification;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.shippingnotification.ShippingNotification;
import de.metas.shippingnotification.ShippingNotificationListener;
import de.metas.util.Services;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ShipmentSchedule_ShippingNotification_Listener implements ShippingNotificationListener
{
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

	@Override
	public void onAfterComplete(final @NonNull ShippingNotification shippingNotification)
	{
		shipmentScheduleBL.setPhysicalClearanceDate(shippingNotification.getShipmentScheduleIds(), shippingNotification.getPhysicalClearanceDate());
	}

	@Override
	public void onAfterReverse(final @NonNull ShippingNotification reversal, final @NonNull ShippingNotification original)
	{
		shipmentScheduleBL.setPhysicalClearanceDate(reversal.getShipmentScheduleIds(), null);
	}
}
