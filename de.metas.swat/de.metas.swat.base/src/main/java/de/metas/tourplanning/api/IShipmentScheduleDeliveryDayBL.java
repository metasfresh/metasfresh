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

import org.adempiere.util.ISingletonService;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;

/**
 * Handles the relation between {@link de.metas.tourplanning.model.I_M_ShipmentSchedule} and {@link I_M_DeliveryDay}.
 * 
 * @author tsa
 *
 */
public interface IShipmentScheduleDeliveryDayBL extends ISingletonService
{
	/**
	 * Gets current delivery date.
	 * 
	 * If is not set directly in shipment schedule, it will try to fetch it from underlying documents (i.e. Order Line / Order).
	 * 
	 * @param sched
	 * @return delivery date; never null
	 */
	Timestamp getDeliveryDateCurrent(I_M_ShipmentSchedule sched);

	/**
	 * <ul>
	 * <li>create/update shipment schedule to {@link I_M_DeliveryDay} allocation (i.e. {@link I_M_DeliveryDay_Alloc})
	 * 
	 * @param sched
	 */
	void updateDeliveryDayInfo(I_M_ShipmentSchedule sched);

	/**
	 * 
	 * @param sched
	 * @return shipment schedule wrapped as {@link IDeliveryDayAllocable}; never return null
	 */
	IDeliveryDayAllocable asDeliveryDayAllocable(I_M_ShipmentSchedule sched);

	/**
	 * Gets underlying {@link I_M_ShipmentSchedule}.
	 * 
	 * @param deliveryDayAllocable
	 * @param shipment schedule's interface class to be used when returning
	 * @return underlying {@link I_M_ShipmentSchedule} or null if <code>deliveryDayAllocable</code> or not applicable.
	 */
	<T extends I_M_ShipmentSchedule> T getShipmentScheduleOrNull(IDeliveryDayAllocable deliveryDayAllocable, final Class<T> modelClass);
}
