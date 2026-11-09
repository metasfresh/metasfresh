package de.metas.tourplanning.api;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Excel-WORKDAY semantics: start date is not counted, Mon-Fri only, no holidays.
 */
public class WorkdayCalculatorTest
{
	private static LocalDate d(final String iso)
	{
		return LocalDate.parse(iso);
	}

	@Test
	void forward()
	{
		assertThat(WorkdayCalculator.workday(d("2026-08-10"), 7)).isEqualTo(d("2026-08-19"));
		assertThat(WorkdayCalculator.workday(d("2026-08-10"), 1)).isEqualTo(d("2026-08-11"));
		assertThat(WorkdayCalculator.workday(d("2026-08-14"), 2)).isEqualTo(d("2026-08-18"));
	}

	@Test
	void backward()
	{
		assertThat(WorkdayCalculator.workday(d("2026-08-12"), -1)).isEqualTo(d("2026-08-11"));
		assertThat(WorkdayCalculator.workday(d("2026-08-28"), -6)).isEqualTo(d("2026-08-20"));
		assertThat(WorkdayCalculator.workday(d("2026-08-17"), -1)).isEqualTo(d("2026-08-14"));
	}

	@Test
	void zeroReturnsStartUnchanged()
	{
		assertThat(WorkdayCalculator.workday(d("2026-08-10"), 0)).isEqualTo(d("2026-08-10"));
		assertThat(WorkdayCalculator.workday(d("2026-08-15"), 0)).isEqualTo(d("2026-08-15")); // Saturday
		assertThat(WorkdayCalculator.workday(d("2026-08-16"), 0)).isEqualTo(d("2026-08-16")); // Sunday
	}

	@Test
	void weekendStart()
	{
		assertThat(WorkdayCalculator.workday(d("2026-08-15"), 1)).isEqualTo(d("2026-08-17")); // Sat
		assertThat(WorkdayCalculator.workday(d("2026-08-16"), 1)).isEqualTo(d("2026-08-17")); // Sun
	}
}
