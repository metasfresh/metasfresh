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


import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.generator.IDateShifter;

import de.metas.adempiere.service.IBusinessDayMatcher;
import de.metas.adempiere.service.ICalendarBL;
import de.metas.tourplanning.model.I_M_TourVersion;

public class TourVersionDeliveryDateShifter implements IDateShifter
{
	//
	// Services
	private ICalendarBL calendarBL = Services.get(ICalendarBL.class);

	// private final I_M_TourVersion tourVersion;
	private final IBusinessDayMatcher businessDayMatcher;

	private final boolean isCancelIfNotBusinessDay;
	private final boolean isMoveToNextBusinessDay;

	public TourVersionDeliveryDateShifter(final I_M_TourVersion tourVersion)
	{
		Check.assumeNotNull(tourVersion, "tourVersion not null");
		// this.tourVersion = tourVersion;
		this.businessDayMatcher = calendarBL.getBusinessDayMatcher();

		this.isCancelIfNotBusinessDay = tourVersion.isCancelDeliveryDay();
		this.isMoveToNextBusinessDay = tourVersion.isMoveDeliveryDay();
	}

	@Override
	public Date shift(final Date deliveryDate)
	{
		//
		// Case: we deal with a delivery date which is in a business day
		if (businessDayMatcher.isBusinessDay(deliveryDate))
		{
			return deliveryDate;
		}
		//
		// Case: our delivery date is not in a business day
		else
		{
			//
			// Case: we need to cancel because our delivery date it's not in a business day
			if (isCancelIfNotBusinessDay)
			{
				// skip this delivery date
				return null;
			}
			//
			// Case: we need to move our delivery date to next business day
			else if (isMoveToNextBusinessDay)
			{
				final Date deliveryDateNextBusinessDay = businessDayMatcher.getNextBusinessDay(deliveryDate);
				return deliveryDateNextBusinessDay;
			}
			//
			// Case: no option => do nothing, same as if we were asked for canceling
			else
			{
				return null;
			}
		}
	}

	/**
	 * Remove given Days of Week from the list of Days Of Week which are considered non-business days.
	 * 
	 * @param weekDays
	 */
	public void removeNonBusinessWeekDays(final Collection<Integer> weekDays)
	{
		Check.assumeNotEmpty(weekDays, "weekDays not empty");

		final Set<Integer> currentWeekDays = businessDayMatcher.getWeekendDays();
		currentWeekDays.removeAll(weekDays);
		businessDayMatcher.setWeekendDays(currentWeekDays);
	}
}
