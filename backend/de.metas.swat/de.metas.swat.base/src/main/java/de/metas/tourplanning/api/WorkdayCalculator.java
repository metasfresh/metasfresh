package de.metas.tourplanning.api;

import de.metas.util.calendar.IBusinessDayMatcher;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;

/**
 * Business-day shift with Excel {@code WORKDAY} semantics: the start date is not counted;
 * {@code businessDays == 0} returns the start date unchanged; a negative value goes backward.
 * <p>
 * Which days count as business days is defined entirely by the supplied {@link IBusinessDayMatcher} —
 * e.g. the weekend matcher (Mon-Fri) alone, or the weekend matcher composed with a calendar's
 * non-business days (Mon-Fri additionally skipping holidays).
 */
@UtilityClass
public class WorkdayCalculator
{
	public static LocalDate addBusinessDays(
			@NonNull final LocalDate start,
			final int businessDays,
			@NonNull final IBusinessDayMatcher businessDayMatcher)
	{
		final int step = businessDays > 0 ? 1 : -1;
		int remaining = Math.abs(businessDays);
		LocalDate date = start;
		while (remaining > 0)
		{
			date = date.plusDays(step);
			if (businessDayMatcher.isBusinessDay(date))
			{
				remaining--;
			}
		}
		return date;
	}
}
