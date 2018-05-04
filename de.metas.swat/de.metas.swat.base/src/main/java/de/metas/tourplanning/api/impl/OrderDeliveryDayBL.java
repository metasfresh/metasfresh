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
import java.util.Date;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_Order;
import org.compiere.util.Util;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.tourplanning.api.IDeliveryDayBL;
import de.metas.tourplanning.api.IOrderDeliveryDayBL;

public class OrderDeliveryDayBL implements IOrderDeliveryDayBL
{
	public static final String SYSCONFIG_Fallback_PreparationDate = "de.metas.tourplanning.api.impl.OrderDeliveryDay.Fallback_PreparationDate";

	private static final transient Logger logger = LogManager.getLogger(OrderDeliveryDayBL.class);

	@Override
	public boolean setPreparationDate(final I_C_Order order, final boolean fallbackToDatePromised)
	{
		Check.assumeNotNull(order, "order not null");

		// Don't touch processed orders
		if (order.isProcessed())
		{
			return false;
		}

		//
		// Extract parameters from order
		final int bpartnerLocationId = order.getC_BPartner_Location_ID();
		if (bpartnerLocationId <= 0)
		{
			return false;
		}

		final Timestamp datePromised = order.getDatePromised();
		if (datePromised == null)
		{
			return false;
		}

		final boolean isSOTrx = order.isSOTrx();

		boolean isUseFallback = fallbackToDatePromised;

		if (!isUseFallback)
		{
			// task 09254
			// Also use the fallback to the date promised if the sysconfig is set to true

			isUseFallback = Services.get(ISysConfigBL.class)
					.getBooleanValue(
							SYSCONFIG_Fallback_PreparationDate
							, true // default true
					);
		}
		final IDeliveryDayBL deliveryDayBL = Services.get(IDeliveryDayBL.class);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(order);

		// the date+time when the order was created
		final Timestamp dateOrdered = Util.coalesce(order.getCreated(), SystemTime.asTimestamp());
		final Timestamp preparationDate = deliveryDayBL.calculatePreparationDateOrNull(context, isSOTrx, dateOrdered, datePromised, bpartnerLocationId);

		//
		// Update order
		final Date systemTime = SystemTime.asDate();
		if (preparationDate != null && preparationDate.after(systemTime))
		{
			// task 08931: only set the date if it has not yet passed.
			// if it has, leave the field empty and let the user pick a new preparation date
			order.setPreparationDate(preparationDate);
			logger.debug("Setting PreparationDate={} for C_Order {} (fallbackToDatePromised={}, systemTime={})",
					new Object[] { preparationDate, order, isUseFallback, systemTime });
		}
		else if (isUseFallback)
		{
			order.setPreparationDate(order.getDatePromised());
			logger.debug(
					"Setting PreparationDate={} for C_Order {} from order's DatePromised value, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}).",
					order.getDatePromised(), order, preparationDate, isUseFallback, systemTime);
		}
		else
		{
			order.setPreparationDate(null);
			logger.info(
					"Setting PreparationDate={} for C_Order {}, because the computed PreparationDate={} is null or has already passed (fallbackToDatePromised={}, systemTime={}). Leaving it to the user to set a date manually.",
					preparationDate, order, preparationDate, isUseFallback, systemTime);
		}

		return true; // value set
	}
}
