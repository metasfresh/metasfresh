package de.metas.tourplanning.spi;

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


import java.util.concurrent.CopyOnWriteArrayList;

import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_Tour_Instance;
import de.metas.util.Check;

public class CompositeDeliveryDayHandler implements IDeliveryDayHandler
{
	private final CopyOnWriteArrayList<IDeliveryDayHandler> handlers = new CopyOnWriteArrayList<>();

	public final void addDeliveryDayHandler(final IDeliveryDayHandler handler)
	{
		Check.assumeNotNull(handler, "handler not null");
		handlers.addIfAbsent(handler);
	}

	@Override
	public final void updateDeliveryDayAllocFromModel(final I_M_DeliveryDay_Alloc deliveryDayAlloc, final IDeliveryDayAllocable deliveryDayAllocable)
	{
		for (final IDeliveryDayHandler handler : handlers)
		{
			handler.updateDeliveryDayAllocFromModel(deliveryDayAlloc, deliveryDayAllocable);
		}
	}

	@Override
	public void updateDeliveryDayWhenAllocationChanged(I_M_DeliveryDay deliveryDay, I_M_DeliveryDay_Alloc deliveryDayAlloc, I_M_DeliveryDay_Alloc deliveryDayAllocOld)
	{
		for (final IDeliveryDayHandler handler : handlers)
		{
			handler.updateDeliveryDayWhenAllocationChanged(deliveryDay, deliveryDayAlloc, deliveryDayAllocOld);
		}
	}

	@Override
	public void updateTourInstanceWhenDeliveryDayChanged(I_M_Tour_Instance tourInstance, I_M_DeliveryDay deliveryDay, I_M_DeliveryDay deliveryDayOld)
	{
		for (final IDeliveryDayHandler handler : handlers)
		{
			handler.updateTourInstanceWhenDeliveryDayChanged(tourInstance, deliveryDay, deliveryDayOld);
		}
	}
}
