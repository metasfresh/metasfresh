package de.metas.rfq.util;

import java.sql.Timestamp;

import org.compiere.util.TimeUtil;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class RfQUtils
{
	private RfQUtils()
	{
	}

	/**
	 * If needed, sets DateWorkStart or DateWorkComplete or DeliveryDays based on the other fields.
	 * 
	 * @param workDatesAware
	 */
	public static void updateWorkDates(final IRfQWorkDatesAware workDatesAware)
	{
		// Calculate Complete Date (also used to verify)
		if (workDatesAware.getDateWorkStart() != null && workDatesAware.getDeliveryDays() != 0)
		{
			final Timestamp dateWorkComplete = TimeUtil.addDays(workDatesAware.getDateWorkStart(), workDatesAware.getDeliveryDays());
			workDatesAware.setDateWorkComplete(dateWorkComplete);
		}
		// Calculate Delivery Days
		else if (workDatesAware.getDateWorkStart() != null && workDatesAware.getDeliveryDays() == 0 && workDatesAware.getDateWorkComplete() != null)
		{
			final int deliveryDays = TimeUtil.getDaysBetween(workDatesAware.getDateWorkStart(), workDatesAware.getDateWorkComplete());
			workDatesAware.setDeliveryDays(deliveryDays);
		}
		// Calculate Start Date
		else if (workDatesAware.getDateWorkStart() == null && workDatesAware.getDeliveryDays() != 0 && workDatesAware.getDateWorkComplete() != null)
		{
			final Timestamp dateWorkStart = TimeUtil.addDays(workDatesAware.getDateWorkComplete(), workDatesAware.getDeliveryDays() * -1);
			workDatesAware.setDateWorkStart(dateWorkStart);
		}
	}
}
