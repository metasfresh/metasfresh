package de.metas.calendar.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
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
	public static final Instant refInstant = LocalDate.parse("2022-06-01").atStartOfDay(ZoneId.of("Europe/Bucharest")).toInstant();

	private static Instant instant(int day) {return refInstant.plus(day - 1, ChronoUnit.DAYS);}

	private static CalendarDateRange allDay(int daysOffsetStart, int daysOffsetEnd)
	{
		return CalendarDateRange.builder()
				.startDate(instant(daysOffsetStart))
				.endDate(instant(daysOffsetEnd))
				.allDay(true)
				.build();
	}

	@Test
	void check_toRange_returns_a_closed_interval()
	{
		assertThat(allDay(1, 2).toRange())
				.isEqualTo(Range.closed(instant(1), instant(2)));
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
	class span
	{
		@Test
		void singleRange()
		{
			final CalendarDateRange range = allDay(3, 7);
			assertThat(CalendarDateRange.span(ImmutableList.of(range)))
					.isSameAs(range);
		}

		@Test
		void standardTest()
		{
			assertThat(
					CalendarDateRange.span(ImmutableList.of(
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

	@Nested
	class isConnectedTo
	{
		@Test
		void notIntersecting() {assertThat(allDay(2, 3).isConnectedTo(instant(4), instant(5))).isFalse();}

		@Test
		void adjacent() {assertThat(allDay(2, 3).isConnectedTo(instant(3), instant(4))).isTrue();}

		@Test
		void intersecting() {assertThat(allDay(2, 4).isConnectedTo(instant(3), instant(5))).isTrue();}

		@Test
		void including() {assertThat(allDay(2, 10).isConnectedTo(instant(3), instant(5))).isTrue();}

		@Test
		void included() {assertThat(allDay(2, 10).isConnectedTo(instant(1), instant(11))).isTrue();}

	}

	@Nested
	class isOverlappingWith
	{
		@Test
		void notIntersecting() {assertThat(allDay(2, 3).isOverlappingWith(allDay(4, 5))).isFalse();}

		@Test
		void adjacent() {assertThat(allDay(2, 3).isOverlappingWith(allDay(3, 4))).isFalse();}

		@Test
		void intersecting() {assertThat(allDay(2, 4).isOverlappingWith(allDay(3, 5))).isTrue();}

		@Test
		void including() {assertThat(allDay(2, 10).isOverlappingWith(allDay(3, 5))).isTrue();}

		@Test
		void included() {assertThat(allDay(2, 10).isOverlappingWith(allDay(1, 11))).isTrue();}
	}

}