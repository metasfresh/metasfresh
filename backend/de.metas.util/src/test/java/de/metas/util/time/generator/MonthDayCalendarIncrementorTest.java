package de.metas.util.time.generator;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthDayCalendarIncrementorTest
{
	@Test
	public void test_year2014_1MonthIncrement_31DayOfMonth()
	{
		final List<LocalDateTime> expectedDates = Arrays.asList(
				LocalDateTime.of(2014, 1, 31, 0, 0),
				LocalDateTime.of(2014, 2, 28, 0, 0),
				LocalDateTime.of(2014, 3, 31, 0, 0),
				LocalDateTime.of(2014, 4, 30, 0, 0),
				LocalDateTime.of(2014, 5, 31, 0, 0),
				LocalDateTime.of(2014, 6, 30, 0, 0),
				LocalDateTime.of(2014, 7, 31, 0, 0),
				LocalDateTime.of(2014, 8, 31, 0, 0),
				LocalDateTime.of(2014, 9, 30, 0, 0),
				LocalDateTime.of(2014, 10, 31, 0, 0),
				LocalDateTime.of(2014, 11, 30, 0, 0),
				LocalDateTime.of(2014, 12, 31, 0, 0));

		final MonthDayCalendarIncrementor incrementor = new MonthDayCalendarIncrementor(1, 31);

		final List<LocalDateTime> actualDates = generate(incrementor::increment, LocalDateTime.of(2014, Month.JANUARY, 1, 0, 0), 12);
		assertThat(actualDates).isEqualTo(expectedDates);
	}

	@Test
	public void test_year2014_1MonthDecrement_31DayOfMonth()
	{
		final List<LocalDateTime> expectedDates = Arrays.asList(
				LocalDateTime.of(2014, 12, 31, 0, 0),
				LocalDateTime.of(2014, 11, 30, 0, 0),
				LocalDateTime.of(2014, 10, 31, 0, 0),
				LocalDateTime.of(2014, 9, 30, 0, 0),
				LocalDateTime.of(2014, 8, 31, 0, 0),
				LocalDateTime.of(2014, 7, 31, 0, 0),
				LocalDateTime.of(2014, 6, 30, 0, 0),
				LocalDateTime.of(2014, 5, 31, 0, 0),
				LocalDateTime.of(2014, 4, 30, 0, 0),
				LocalDateTime.of(2014, 3, 31, 0, 0),
				LocalDateTime.of(2014, 2, 28, 0, 0),
				LocalDateTime.of(2014, 1, 31, 0, 0));

		final MonthDayCalendarIncrementor incrementor = new MonthDayCalendarIncrementor(1, 31);

		final List<LocalDateTime> actualDates = generate(incrementor::decrement, LocalDateTime.of(2015, Month.JANUARY, 1, 0, 0), 12);
		assertThat(actualDates).isEqualTo(expectedDates);
	}

	private static List<LocalDateTime> generate(final Function<LocalDateTime, LocalDateTime> func, final LocalDateTime startDate, final int count)
	{
		final List<LocalDateTime> result = new ArrayList<>(count);

		LocalDateTime date = startDate;
		for (int i = 1; i <= count; i++)
		{
			date = func.apply(date);
			result.add(date);
		}

		return result;
	}
}
