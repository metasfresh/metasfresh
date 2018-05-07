/**
 * 
 */
package de.metas.tourplanning.api;

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
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_Tour;
import de.metas.tourplanning.model.I_M_Tour_Instance;

/**
 * {@link I_M_DeliveryDay} related DAO
 * 
 * @author cg
 * 
 */
public interface IDeliveryDayDAO extends ISingletonService
{
	/**
	 * Gets the closest delivery day by partner, location, before given date/time.
	 * 
	 * @param context
	 * @param params delivery day query parameters
	 * @return the most suitable delivery day or null
	 */
	I_M_DeliveryDay retrieveDeliveryDay(final IContextAware context, final IDeliveryDayQueryParams params);

	/**
	 * Retrieve all (active) delivery days for given date and tour.
	 * 
	 * @param deliveryDate the search is done with this value truncated to "day"
	 * @param tour
	 * @return all {@link I_M_DeliveryDay} records for given day date
	 */
	List<I_M_DeliveryDay> retrieveDeliveryDays(I_M_Tour tour, Timestamp deliveryDate);

	/**
	 * Retrieve all (active or not, processed or not) delivery days for given tour instance
	 * 
	 * @param tourInstance
	 * @return
	 */
	List<I_M_DeliveryDay> retrieveDeliveryDaysForTourInstance(I_M_Tour_Instance tourInstance);

	/**
	 * Retrieves Delivery Day Allocation for given model.
	 * 
	 * @param deliveryDayAllocable model
	 * @return {@link I_M_DeliveryDay_Alloc} or null
	 */
	I_M_DeliveryDay_Alloc retrieveDeliveryDayAllocForModel(final IContextAware context, final IDeliveryDayAllocable deliveryDayAllocable);

	/**
	 * Checks if given delivery day record is matching given parameters.
	 * 
	 * @param deliveryDay
	 * @param params
	 * @return true if matches
	 */
	boolean isDeliveryDayMatches(I_M_DeliveryDay deliveryDay, final IDeliveryDayQueryParams params);

	/**
	 * 
	 * @param deliveryDay
	 * @return true if given delivery day has any allocations
	 */
	boolean hasAllocations(I_M_DeliveryDay deliveryDay);
}
