package org.adempiere.util.time.generator;

/*
 * #%L
 * de.metas.util
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


import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.adempiere.util.Check;

import lombok.Value;

/**
 * Increment by a given amount of weeks and always just to a given week day.
 * 
 * @author tsa
 *
 */
@Value
public class WeekDayCalendarIncrementor implements ICalendarIncrementor
{
	private static final List<Integer> DAYS_OF_WEEK = Arrays.asList(Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY);

	private final int weeksAmount;
	private final int dayOfWeek;

	public WeekDayCalendarIncrementor(final int weeksAmount, final int dayOfWeek)
	{
		super();

		Check.assume(weeksAmount >= 0, "weeksAmount({}) >= 0", weeksAmount);
		this.weeksAmount = weeksAmount;

		Check.assume(DAYS_OF_WEEK.contains(dayOfWeek), "dayOfWeek({}) shall have one of the following values: {}", dayOfWeek, DAYS_OF_WEEK);
		this.dayOfWeek = dayOfWeek;
	}

	@Override
	public String toString()
	{
		return "WeekDayCalendarIncrement [weeksAmount=" + weeksAmount + ", dayOfWeek=" + dayOfWeek + "]";
	}

	@Override
	public void increment(final Calendar calendar)
	{
		final long millisBefore = calendar.getTimeInMillis();

		// Set Day of Week
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		// If we actually moved back or we did not move at all, we need to increment also
		if (millisBefore >= calendar.getTimeInMillis())
		{
			calendar.add(Calendar.WEEK_OF_MONTH, weeksAmount);
		}
	}

	@Override
	public void adjustToClosest(Calendar calendar)
	{
		final long millisBefore = calendar.getTimeInMillis();

		// Set Day of Week
		calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);

		// If we actually moved back, we need to increment also
		if (millisBefore > calendar.getTimeInMillis())
		{
			calendar.add(Calendar.WEEK_OF_MONTH, weeksAmount);
		}
	}
}
