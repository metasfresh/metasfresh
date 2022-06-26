package de.metas.calendar.util;

import com.google.common.collect.ImmutableList;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class CalendarDateRangeTest
{
	public static final Instant refInstant = LocalDate.parse("2022-06-24").atStartOfDay(ZoneId.of("Europe/Bucharest")).toInstant();

	private static Instant instant(int daysOffset) {return refInstant.plus(daysOffset, ChronoUnit.DAYS);}

	private static CalendarDateRange allDay(int daysOffsetStart, int daysOffsetEnd)
	{
		return CalendarDateRange.builder()
				.startDate(instant(daysOffsetStart))
				.endDate(instant(daysOffsetEnd))
				.allDay(true)
				.build();
	}

	@Nested
	class constructor
	{
		@Test
		void equalStartAndEndDate()
		{
			final Instant instant = instant(1);
			Assertions.assertThatThrownBy(() -> CalendarDateRange.builder().startDate(instant).endDate(instant).build())
					.isInstanceOf(AdempiereException.class)
					.hasMessageStartingWith("Start date and end date shall not be equal");
		}
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