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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.Date;

import org.adempiere.util.ISingletonService;
import org.adempiere.util.lang.IContextAware;

import de.metas.tourplanning.model.I_M_DeliveryDay;
import de.metas.tourplanning.model.I_M_DeliveryDay_Alloc;
import de.metas.tourplanning.model.I_M_TourVersionLine;
import de.metas.tourplanning.spi.IDeliveryDayHandler;

public interface IDeliveryDayBL extends ISingletonService
{

	void registerDeliveryDayHandler(IDeliveryDayHandler updater);

	IDeliveryDayHandler getDeliveryDayHandlers();

	/**
	 * 
	 * @param tourVersionLine
	 * @param deliveryDate
	 * @param trxName
	 * @return created {@link I_M_DeliveryDay} or null
	 */
	I_M_DeliveryDay createDeliveryDay(I_M_TourVersionLine tourVersionLine, Date deliveryDate, String trxName);

	/**
	 * Get/Create current Delivery Day Allocation.
	 * 
	 * Following cases are handled:
	 * <ul>
	 * <li>if there is no allocation but we found a matching delivery day, a new allocation will be created on that delivery day
	 * <li>if there is an allocation but we found another delivery day which is matching, the old allocation will be deleted and a new one is created
	 * <li>if there is an allocation and delivery day is matching, that allocation will be kept
	 * </ul>
	 * 
	 * @param context
	 * @param model
	 * @return delivery day allocation or null
	 */
	I_M_DeliveryDay_Alloc getCreateDeliveryDayAlloc(final IContextAware context, final IDeliveryDayAllocable model);

	/**
	 * Mark given delivery day as no longer needed.
	 * 
	 * The implementation will decide if it will just set IsActive flag to <code>false</code> or it will also delete the record.
	 * 
	 * @param deliveryDay
	 * @param trxName
	 */
	void inactivate(I_M_DeliveryDay deliveryDay, String trxName);

	/**
	 * Invalidates given {@link I_M_DeliveryDay}.
	 * 
	 * As a effect, given delivery day will be revalidated (synchronously or asynchronously, depends on implementation).
	 * 
	 * @param deliveryDay
	 */
	void invalidate(I_M_DeliveryDay deliveryDay);

	/**
	 * Calculate the Preparation time based on a ContextAware object, a date promised, sotrx and a location ID
	 * 
	 * @param context - object from where the context is taken
	 * @param isSOTrx
	 * @param dateOrdered
	 * @param datePromised
	 * @param bpartnerLocationId
	 * @return
	 */
	Timestamp calculatePreparationDateOrNull(IContextAware context, boolean isSOTrx, Timestamp dateOrdered, Timestamp datePromised, int bpartnerLocationId);

	/**
	 * Sets DeliveryDateTimeMax = DeliveryDate + BufferHours.
	 * 
	 * @param deliveryDay
	 */
	void setDeliveryDateTimeMax(I_M_DeliveryDay deliveryDay);
}
