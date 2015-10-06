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
 * Implementations of this interface are responsible for updating tour planning's module records.
 * 
 * @author tsa
 *
 */
public interface IDeliveryDayHandler
{
	/**
	 * Method called when {@link I_M_DeliveryDay_Alloc} changed and {@link I_M_DeliveryDay} needs to be updated.
	 * 
	 * @param deliveryDay delivery day that needs to be updated
	 * @param deliveryDayAlloc new delivery day allocation (or null if it was deleted)
	 * @param deliveryDayAllocOld old delivery day allocation (or null if it just created)
	 */
	void updateDeliveryDayWhenAllocationChanged(final I_M_DeliveryDay deliveryDay,
			final I_M_DeliveryDay_Alloc deliveryDayAlloc,
			final I_M_DeliveryDay_Alloc deliveryDayAllocOld);

	/**
	 * Method called when {@link I_M_DeliveryDay} changed and {@link I_M_Tour_Instance} needs to be updated.
	 * 
	 * @param tourInstance
	 * @param deliveryDay new delivery day (or null if it was deleted)
	 * @param deliveryDayOld old delivery day (or null if it just created)
	 */
	void updateTourInstanceWhenDeliveryDayChanged(final I_M_Tour_Instance tourInstance,
			final I_M_DeliveryDay deliveryDay,
			final I_M_DeliveryDay deliveryDayOld);

	/**
	 * Method called when <code>deliveryDayAllocable</code> (i.e. the model) has changed and {@link I_M_DeliveryDay_Alloc} needs to be updated.
	 * 
	 * {@link IDeliveryDayAllocable} objects are provided by {@link IDeliveryDayCreateHandler}s.
	 * 
	 * @param deliveryDayAlloc
	 * @param deliveryDayAllocable
	 */
	void updateDeliveryDayAllocFromModel(final I_M_DeliveryDay_Alloc deliveryDayAlloc, IDeliveryDayAllocable deliveryDayAllocable);
}
