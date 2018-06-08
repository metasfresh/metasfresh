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


import java.util.Calendar;

/**
 * Util class for creating some standard calendar incrementors.
 * 
 * @author tsa
 *
 */
public final class CalendarIncrementors
{
	private static final ICalendarIncrementor DAY_BY_DAY = new SimpleCalendarIncrementor(Calendar.DAY_OF_MONTH, 1);

	public static ICalendarIncrementor dayByDay()
	{
		return DAY_BY_DAY;
	}

	public static ICalendarIncrementor eachNthDay(final int day)
	{
		return new SimpleCalendarIncrementor(Calendar.DAY_OF_MONTH, day);
	}

	public static ICalendarIncrementor eachNthWeek(int week, int dayOfWeek)
	{
		return new WeekDayCalendarIncrementor(week, dayOfWeek);
	}

	public static ICalendarIncrementor eachNthMonth(int months, int dayOfMonth)
	{
		return new MonthDayCalendarIncrementor(months, dayOfMonth);
	}
}
