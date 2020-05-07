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


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.time.generator.DateSequenceGenerator;
import org.adempiere.util.time.generator.DaysOfMonthExploder;
import org.adempiere.util.time.generator.DaysOfWeekExploder;

import de.metas.tourplanning.api.ITourVersionRange;
import de.metas.tourplanning.model.I_M_TourVersion;

/* package */class TourVersionRange implements ITourVersionRange
{
	private final I_M_TourVersion tourVersion;
	private final Date validFrom;
	private final Date validTo;

	public TourVersionRange(final I_M_TourVersion tourVersion, final Date validFrom, final Date validTo)
	{
		super();

		Check.assumeNotNull(tourVersion, "tourVersion not null");
		this.tourVersion = tourVersion;

		Check.assumeNotNull(validFrom, "validFrom not null");
		this.validFrom = validFrom;

		Check.assumeNotNull(validTo, "validTo not null");
		this.validTo = validTo;

		Check.assume(validFrom.compareTo(validTo) <= 0, "ValidFrom({}) <= ValidTo({})", validFrom, validTo);
	}

	@Override
	public String toString()
	{
		return "TourVersionRange ["
				+ "tourVersion=" + tourVersion == null ? null : tourVersion.getName()
				+ ", validFrom=" + validFrom
				+ ", validTo=" + validTo
				+ "]";
	}

	@Override
	public I_M_TourVersion getM_TourVersion()
	{
		return tourVersion;
	}

	@Override
	public Date getValidFrom()
	{
		return validFrom;
	}

	@Override
	public Date getValidTo()
	{
		return validTo;
	}

	@Override
	public Set<Date> generateDeliveryDates()
	{
		//
		// Get and adjust the parameters
		final I_M_TourVersion tourVersion = getM_TourVersion();
		boolean isWeekly = tourVersion.isWeekly();
		int everyWeek = tourVersion.getEveryWeek();
		if (isWeekly)
		{
			everyWeek = 1;
		}
		else if (!isWeekly && everyWeek > 0)
		{
			isWeekly = true;
		}

		boolean isMonthly = tourVersion.isMonthly();
		int everyMonth = tourVersion.getEveryMonth();
		final int monthDay = tourVersion.getMonthDay();
		if (isMonthly)
		{
			everyMonth = 1;
		}
		else if (!isMonthly && everyMonth > 0)
		{
			isMonthly = true;
		}

		final TourVersionDeliveryDateShifter dateShifter = new TourVersionDeliveryDateShifter(tourVersion);

		final DateSequenceGenerator generator = new DateSequenceGenerator()
				.from(getValidFrom())
				.to(getValidTo())
				.shift(dateShifter);

		// task 08252: don't shift beyond getValidTo(), because there will probably be another version to create it's own delivery days at that date 
		final boolean disallowShiftingBeyondDateTo = true;
		generator.enforceDateToAfterShift(disallowShiftingBeyondDateTo);

		//
		// Case: generate delivery days each "everyWeek" weeks
		if (isWeekly)
		{
			generator.byWeeks(everyWeek, Calendar.MONDAY);

			final List<Integer> onlyDaysOfWeek = getWeekDays(tourVersion);
			if (!onlyDaysOfWeek.isEmpty())
			{
				generator.explode(new DaysOfWeekExploder(onlyDaysOfWeek));

				// If user explicitelly asked for a set of week days, don't consider them non-business days by default

				if (!tourVersion.isCancelDeliveryDay() && !tourVersion.isMoveDeliveryDay())
				{
					dateShifter.removeNonBusinessWeekDays(onlyDaysOfWeek);
				}
			}
			else
			{
				generator.explode(DaysOfWeekExploder.ALL_DAYS_OF_WEEK);
			}
		}
		//
		// Case: generate delivery days each "everyMonth" on "monthDay" day of the month
		else if (isMonthly)
		{
			generator.byMonths(everyMonth, 1); // every given month, 1st day
			generator.explode(new DaysOfMonthExploder(monthDay));
		}
		//
		// Case: no frequency set
		else
		{
			// do nothing
			return Collections.emptySet();
		}

		return generator.generate();
	}

	private List<Integer> getWeekDays(final I_M_TourVersion tourVersion)
	{
		Check.assumeNotNull(tourVersion, "tourVersion not null");

		final List<Integer> weekDays = new ArrayList<Integer>();
		if (tourVersion.isOnSunday())
		{
			weekDays.add(Calendar.SUNDAY);
		}
		if (tourVersion.isOnMonday())
		{
			weekDays.add(Calendar.MONDAY);
		}
		if (tourVersion.isOnTuesday())
		{
			weekDays.add(Calendar.TUESDAY);
		}
		if (tourVersion.isOnWednesday())
		{
			weekDays.add(Calendar.WEDNESDAY);
		}
		if (tourVersion.isOnThursday())
		{
			weekDays.add(Calendar.THURSDAY);
		}
		if (tourVersion.isOnFriday())
		{
			weekDays.add(Calendar.FRIDAY);
		}
		if (tourVersion.isOnSaturday())
		{
			weekDays.add(Calendar.SATURDAY);
		}

		return weekDays;
	}

}
