package org.adempiere.util.time.generator;

import java.time.DayOfWeek;

import lombok.experimental.UtilityClass;

/**
 * Util class for creating some standard calendar incrementors.
 *
 * @author tsa
 *
 */
@UtilityClass
public final class CalendarIncrementors
{
	private static final ICalendarIncrementor DAY_BY_DAY = new DayCalendarIncrementor(1);

	public static ICalendarIncrementor dayByDay()
	{
		return DAY_BY_DAY;
	}

	public static ICalendarIncrementor eachNthDay(final int daysToAdd)
	{
		return new DayCalendarIncrementor(daysToAdd);
	}

	public static ICalendarIncrementor eachNthWeek(final int week, final DayOfWeek dayOfWeek)
	{
		return new WeekDayCalendarIncrementor(week, dayOfWeek);
	}

	public static ICalendarIncrementor eachNthMonth(final int months, final int dayOfMonth)
	{
		return new MonthDayCalendarIncrementor(months, dayOfMonth);
	}
}
