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


import de.metas.tourplanning.api.IDeliveryDayAllocable;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_Tour_Instance;

/**
 * Implements all methods of {@link IDeliveryDayHandler} but does nothing.
 * 
 * @author tsa
 *
 */
public abstract class DeliveryDayHandlerAdapter implements IDeliveryDayHandler
{

	@Override
	public void updateDeliveryDayWhenAllocationChanged(I_M_DeliveryDay deliveryDay,
			I_M_DeliveryDay_Alloc deliveryDayAlloc,
			I_M_DeliveryDay_Alloc deliveryDayAllocOld)
	{
		// nothing at this level
	}

	@Override
	public void updateTourInstanceWhenDeliveryDayChanged(final I_M_Tour_Instance tourInstance,
			final I_M_DeliveryDay deliveryDay,
			final I_M_DeliveryDay deliveryDayOld)
	{
		// nothing at this level
	}

	@Override
	public void updateDeliveryDayAllocFromModel(I_M_DeliveryDay_Alloc deliveryDayAlloc, IDeliveryDayAllocable deliveryDayAllocable)
	{
		// nothing at this level
	}
}
