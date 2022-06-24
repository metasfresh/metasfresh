package de.metas.calendar.util;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarDateRangeTest
{
	public static final ZonedDateTime refZDT = LocalDate.parse("2022-06-24").atStartOfDay(SystemTime.zoneId());

	private static ZonedDateTime zdt(int daysOffset) {return refZDT.plusDays(daysOffset);}

	private static CalendarDateRange allDay(int daysOffsetStart, int daysOffsetEnd)
	{
		return CalendarDateRange.builder()
				.startDate(zdt(daysOffsetStart))
				.endDate(zdt(daysOffsetEnd))
				.allDay(true)
				.build();
	}

	@Nested
	class computeDateRangeToFitAll
	{
		@Test
		void singleRange()
		{
			final CalendarDateRange range = allDay(3, 7);
			assertThat(CalendarDateRange.computeDateRangeToFitAll(ImmutableList.of(range)))
					.isSameAs(range);
		}

		@Test
		void standardTest()
		{
			assertThat(
					CalendarDateRange.computeDateRangeToFitAll(ImmutableList.of(
							allDay(3, 7),
							allDay(2, 3),
							allDay(1, 4),
							allDay(6, 8)
					))
			).isEqualTo(allDay(1, 8));
		}
	}

	@Nested
	class plus
	{
		@Test
		void plusOneDay()
		{
			assertThat(allDay(1, 2).plus(Duration.ofDays(1)))
					.isEqualTo(allDay(2, 3));
		}
	}

	@Nested
	class minus
	{
		@Test
		void minusOneDay()
		{
			assertThat(allDay(2, 3).minus(Duration.ofDays(1)))
					.isEqualTo(allDay(1, 2));
		}
	}
}