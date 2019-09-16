package de.metas.tourplanning.api.impl;

import java.time.ZonedDateTime;

import javax.annotation.Nullable;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipmentScheduleDeliveryDayBL implements IShipmentScheduleDeliveryDayBL
{
	@Override
	public IDeliveryDayAllocable asDeliveryDayAllocable(final I_M_ShipmentSchedule sched)
	{
		return ShipmentScheduleDeliveryDayHandler.INSTANCE.asDeliveryDayAllocable(sched);
	}

	@Override
	public <T extends I_M_ShipmentSchedule> T getShipmentScheduleOrNull(
			 @Nullable final IDeliveryDayAllocable deliveryDayAllocable,
			 @NonNull final Class<T> modelClass)
	{
		if (deliveryDayAllocable == null)
		{
			return null;
		}
		else if (deliveryDayAllocable instanceof ShipmentScheduleDeliveryDayAllocable)
		{
			final ShipmentScheduleDeliveryDayAllocable shipmentScheduleDeliveryDayAllocable = (ShipmentScheduleDeliveryDayAllocable)deliveryDayAllocable;
			final I_M_ShipmentSchedule shipmentSchedule = shipmentScheduleDeliveryDayAllocable.getM_ShipmentSchedule();
			return InterfaceWrapperHelper.create(shipmentSchedule, modelClass);
		}
		else
		{
			return null;
		}
	}

	@Override
	public final void updateDeliveryDayInfo(@NonNull final I_M_ShipmentSchedule sched)
	{
		// Get Delivery Day Allocation
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(sched);
		final IDeliveryDayAllocable deliveryDayAllocable = asDeliveryDayAllocable(sched);

		final I_M_DeliveryDay_Alloc deliveryDayAlloc = deliveryDayBL.getCreateDeliveryDayAlloc(context, deliveryDayAllocable);
		if (deliveryDayAlloc == null)
		{
			return;
		}

		InterfaceWrapperHelper.save(deliveryDayAlloc); // make sure is saved
	}

	@Override
	public ZonedDateTime getDeliveryDateCurrent(final I_M_ShipmentSchedule sched)
	{
		return Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryDate(sched);
	}
}
