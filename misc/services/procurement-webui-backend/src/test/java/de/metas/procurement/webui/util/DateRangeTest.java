package de.metas.procurement.webui.util;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.procurement.webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DateRangeTest
{
	@Test
	void test_createWeekInMonthForDay()
	{
		// Last day of year
		test_createWeekInMonthForDay(215, 12, 28, 31)
				.assertDaysBeetween(4)
				.assertDaysInCurrentMonth(31);
		// First week of year
		test_createWeekInMonthForDay(216, 1, 1, 3)
				.assertDaysBeetween(3)
				.assertDaysInCurrentMonth(31);

		// Straight forward test: complete week
		test_createWeekInMonthForDay(216, 1, 18, 24)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(31);
		test_createWeekInMonthForDay(216, 1, 25, 31)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(31);

		// Straight forward test: incomplete week, February
		test_createWeekInMonthForDay(216, 2, 22, 28)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(29);
		test_createWeekInMonthForDay(216, 2, 29, 29)
				.assertDaysBeetween(1)
				.assertDaysInCurrentMonth(29);

		// Straight forward test: incomplete week
		test_createWeekInMonthForDay(216, 3, 28, 31)
				.assertDaysBeetween(4)
				.assertDaysInCurrentMonth(31);
	}

	@Test
	void test_clone()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(216, 2, 22), DateUtils.toDayDate(216, 2, 29));
		final DateRange dateRangeClone = dateRange.clone();

		assertThat(dateRange)
				.isEqualTo(dateRangeClone)
				.isNotSameAs(dateRangeClone);
	}

	@Test
	void test_DateFrom_biggerThan_DateTo()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(216, 2, 29), DateUtils.toDayDate(216, 2, 22));
		final DateRange dateRangeExpected = DateRange.of(DateUtils.toDayDate(216, 2, 22), DateUtils.toDayDate(216, 2, 29));

		assertThat(dateRange).isEqualTo(dateRangeExpected);
	}

	private DateRangeExpectation test_createWeekInMonthForDay(final int year, final int month, final int weekFirstDay, final int weekLastDay)
	{
		final Date dateFrom = DateUtils.toDayDate(year, month, weekFirstDay);
		final Date dateTo = DateUtils.toDayDate(year, month, weekLastDay);
		final DateRange expectedDateRange = DateRange.of(dateFrom, dateTo);
		for (int day = weekFirstDay; day <= weekLastDay; day++)
		{
			final Date date = DateUtils.toDayDate(year, month, day);
			final DateRange dateRange = DateRange.createWeekInMonthForDay(date);

			assertThat(dateRange).as("DateRange for %s", date).isEqualTo(expectedDateRange);
		}

		return DateRangeExpectation.of(expectedDateRange);
	}

	@Test
	void test_DaysIterable()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(216, 2, 22), DateUtils.toDayDate(216, 2, 29));
		final List<Date> daysExpected = ImmutableList.of(
				DateUtils.toDayDate(216, 2, 22)
				, DateUtils.toDayDate(216, 2, 23)
				, DateUtils.toDayDate(216, 2, 24)
				, DateUtils.toDayDate(216, 2, 25)
				, DateUtils.toDayDate(216, 2, 26)
				, DateUtils.toDayDate(216, 2, 27)
				, DateUtils.toDayDate(216, 2, 28)
				, DateUtils.toDayDate(216, 2, 29)
		);
		final List<Date> daysActual = ImmutableList.copyOf(dateRange.daysIterable());

		assertThat(daysActual).isEqualTo(daysExpected);
	}

	@Test
	void test_DaysIterable_OneDay()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(216, 2, 22), DateUtils.toDayDate(216, 2, 22));
		final List<Date> daysExpected = ImmutableList.of(DateUtils.toDayDate(216, 2, 22));
		final List<Date> daysActual = ImmutableList.copyOf(dateRange.daysIterable());

		assertThat(daysActual).isEqualTo(daysExpected);
	}

	public static final class DateRangeExpectation
	{
		public static DateRangeExpectation of(final DateRange dateRange)
		{
			return new DateRangeExpectation(dateRange);
		}

		private final DateRange dateRange;

		private DateRangeExpectation(final DateRange dateRange)
		{
			super();
			this.dateRange = dateRange;
		}

		public DateRangeExpectation assertDaysBeetween(final int expectedDaysBetween)
		{
			final int daysBetween = dateRange.getDaysBetween();
			assertThat(daysBetween).as("DaysBetween for %s", dateRange).isEqualTo(expectedDaysBetween);
			return this;
		}

		public DateRangeExpectation assertDaysInCurrentMonth(final int expectedDaysInCurrentMonth)
		{
			final int daysInCurrentMonth = dateRange.getDaysInCurrentMonth();
			assertThat(daysInCurrentMonth).as("DaysInCurrentMonth for %s", dateRange).isEqualTo(expectedDaysInCurrentMonth);
			return this;
		}
	}
}
