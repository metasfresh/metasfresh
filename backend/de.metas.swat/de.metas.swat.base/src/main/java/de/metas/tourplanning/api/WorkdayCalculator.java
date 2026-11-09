package de.metas.tourplanning.api;

import de.metas.util.calendar.ExcludeWeekendBusinessDayMatcher;
import de.metas.util.calendar.IBusinessDayMatcher;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

/**
 * Pure Mon-Fri business-day shift with Excel WORKDAY semantics (no holidays):
 * the start date is not counted; {@code n == 0} returns the start date unchanged; negative {@code n} goes backward.
 */
@UtilityClass
public class WorkdayCalculator
{
	private static final IBusinessDayMatcher WEEKDAY_MATCHER = ExcludeWeekendBusinessDayMatcher.DEFAULT;

	public static LocalDate workday(@NonNull final LocalDate start, final int n)
	{
		final int step = n > 0 ? 1 : -1;
		int remaining = Math.abs(n);
		LocalDate date = start;
		while (remaining > 0)
		{
			date = date.plusDays(step);
			if (WEEKDAY_MATCHER.isBusinessDay(date))
			{
				remaining--;
			}
		}
		return date;
	}
}
