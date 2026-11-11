package de.metas.tourplanning.api;

import de.metas.util.calendar.ExcludeWeekendBusinessDayMatcher;
import de.metas.util.calendar.IBusinessDayMatcher;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Excel-WORKDAY semantics: start date is not counted. Which days count is defined by the supplied
 * {@link IBusinessDayMatcher} — here the Mon-Fri weekend matcher, plus a holiday-aware matcher.
 */
public class WorkdayCalculatorTest
{
	private static final IBusinessDayMatcher WEEKEND = ExcludeWeekendBusinessDayMatcher.DEFAULT;

	private static LocalDate d(final String iso)
	{
		return LocalDate.parse(iso);
	}

	@Test
	void forward()
	{
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-10"), 7, WEEKEND)).isEqualTo(d("2026-08-19"));
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-10"), 1, WEEKEND)).isEqualTo(d("2026-08-11"));
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-14"), 2, WEEKEND)).isEqualTo(d("2026-08-18"));
	}

	@Test
	void backward()
	{
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-12"), -1, WEEKEND)).isEqualTo(d("2026-08-11"));
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-28"), -6, WEEKEND)).isEqualTo(d("2026-08-20"));
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-17"), -1, WEEKEND)).isEqualTo(d("2026-08-14"));
	}

	@Test
	void zeroReturnsStartUnchanged()
	{
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-10"), 0, WEEKEND)).isEqualTo(d("2026-08-10"));
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-15"), 0, WEEKEND)).isEqualTo(d("2026-08-15")); // Saturday
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-16"), 0, WEEKEND)).isEqualTo(d("2026-08-16")); // Sunday
	}

	@Test
	void weekendStart()
	{
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-15"), 1, WEEKEND)).isEqualTo(d("2026-08-17")); // Sat
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-16"), 1, WEEKEND)).isEqualTo(d("2026-08-17")); // Sun
	}

	@Test
	void holidayIsSkipped()
	{
		// A matcher that treats Mon-Fri as business days EXCEPT Wednesday 2026-08-12 (a "holiday").
		final LocalDate holiday = d("2026-08-12");
		final IBusinessDayMatcher weekendAndHoliday = date ->
				date.getDayOfWeek() != DayOfWeek.SATURDAY
						&& date.getDayOfWeek() != DayOfWeek.SUNDAY
						&& !date.equals(holiday);

		// Forward from Mon 10.08: without the holiday +2 would land Wed 12.08; the holiday pushes it to Thu 13.08.
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-10"), 2, weekendAndHoliday)).isEqualTo(d("2026-08-13"));
		// Backward from Fri 14.08: -2 would land Wed 12.08; the holiday pushes it to Tue 11.08.
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-14"), -2, weekendAndHoliday)).isEqualTo(d("2026-08-11"));
		// Weekend matcher alone (no holiday) still lands on the Wednesday — proves the difference is the holiday.
		assertThat(WorkdayCalculator.addBusinessDays(d("2026-08-10"), 2, WEEKEND)).isEqualTo(d("2026-08-12"));
	}
}
