package de.metas.tourplanning.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;

import javax.annotation.Nullable;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IShipmentScheduleDeliveryDayBL;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
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
	public Timestamp getDeliveryDateCurrent(final I_M_ShipmentSchedule sched)
	{
		final Timestamp deliveryDate = Services.get(IShipmentScheduleEffectiveBL.class).getDeliveryDate(sched);
		return deliveryDate;
	}
}
