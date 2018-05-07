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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.adempiere.util.Check;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.TimeUtil;

import de.metas.tourplanning.api.IDeliveryDayGenerator;
import de.metas.tourplanning.api.ITourBL;
import de.metas.tourplanning.model.I_M_TourVersion;

public class TourBL implements ITourBL
{
	/**
	 * Gets Preparation Time for given Day of the Week.
	 * 
	 * @param tourVersion
	 * @param dayOfWeek
	 * @return preparation time (hour/minute/sec/millis) or null if there is no preparation time for that day of the week
	 */
	private static Timestamp getPreparationTime(final I_M_TourVersion tourVersion, final int dayOfWeek)
	{
		Check.assumeNotNull(tourVersion, "tourVersion not null");

		final Timestamp preparationTime;
		if (dayOfWeek == Calendar.MONDAY)
		{
			preparationTime = tourVersion.getPreparationTime_1();
		}
		else if (dayOfWeek == Calendar.TUESDAY)
		{
			preparationTime = tourVersion.getPreparationTime_2();
		}
		else if (dayOfWeek == Calendar.WEDNESDAY)
		{
			preparationTime = tourVersion.getPreparationTime_3();
		}
		else if (dayOfWeek == Calendar.THURSDAY)
		{
			preparationTime = tourVersion.getPreparationTime_4();
		}
		else if (dayOfWeek == Calendar.FRIDAY)
		{
			preparationTime = tourVersion.getPreparationTime_5();
		}
		else if (dayOfWeek == Calendar.SATURDAY)
		{
			preparationTime = tourVersion.getPreparationTime_6();
		}
		else if (dayOfWeek == Calendar.SUNDAY)
		{
			preparationTime = tourVersion.getPreparationTime_7();
		}
		else
		{
			throw new IllegalArgumentException("Invalid day of week: " + dayOfWeek);
		}

		return preparationTime;
	}

	@Override
	public Timestamp getPreparationDateTime(final I_M_TourVersion tourVersion, final Date deliveryDate)
	{
		Check.assumeNotNull(tourVersion, "tourVersion not null");
		Check.assumeNotNull(deliveryDate, "deliveryDate not null");

		//
		// Get DeliveryDate's Day of the Week
		final GregorianCalendar deliveryDateCal = new GregorianCalendar();
		deliveryDateCal.setTimeInMillis(deliveryDate.getTime());
		final int deliveryDayOfWeek = deliveryDateCal.get(Calendar.DAY_OF_WEEK);

		final Timestamp preparationTime = getPreparationTime(tourVersion, deliveryDayOfWeek);
		if (preparationTime == null)
		{
			return null;
		}

		final Timestamp preparationDateTime = TimeUtil.getDayTime(deliveryDate, preparationTime);
		return preparationDateTime;
	}


	@Override
	public IDeliveryDayGenerator createDeliveryDayGenerator(final IContextAware context)
	{
		return new DeliveryDayGenerator(context);
	}
}
