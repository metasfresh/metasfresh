package de.metas.procurement.webui.util;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableList;

import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;

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
	public void test_createWeekInMonthForDay()
	{
		// Last day of year
		test_createWeekInMonthForDay(2015, 12, 28, 31)
				.assertDaysBeetween(4)
				.assertDaysInCurrentMonth(31);
		// First week of year
		test_createWeekInMonthForDay(2016, 01, 01, 03)
				.assertDaysBeetween(3)
				.assertDaysInCurrentMonth(31);

		// Straight forward test: complete week
		test_createWeekInMonthForDay(2016, 01, 18, 24)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(31);
		test_createWeekInMonthForDay(2016, 01, 25, 31)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(31);

		// Straight forward test: incomplete week, February
		test_createWeekInMonthForDay(2016, 02, 22, 28)
				.assertDaysBeetween(7)
				.assertDaysInCurrentMonth(29);
		test_createWeekInMonthForDay(2016, 02, 29, 29)
				.assertDaysBeetween(1)
				.assertDaysInCurrentMonth(29);

		// Straight forward test: incomplete week
		test_createWeekInMonthForDay(2016, 03, 28, 31)
				.assertDaysBeetween(4)
				.assertDaysInCurrentMonth(31);
	}

	@Test
	public void test_clone() throws CloneNotSupportedException
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(2016, 02, 22), DateUtils.toDayDate(2016, 02, 29));
		final DateRange dateRangeClone = dateRange.clone();

		Assert.assertEquals(dateRange, dateRangeClone);
		Assert.assertNotSame(dateRange, dateRangeClone);
	}

	@Test
	public void test_DateFrom_biggerThan_DateTo()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(2016, 02, 29), DateUtils.toDayDate(2016, 02, 22));
		final DateRange dateRangeExpected = DateRange.of(DateUtils.toDayDate(2016, 02, 22), DateUtils.toDayDate(2016, 02, 29));
		Assert.assertEquals(dateRangeExpected, dateRange);
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
			Assert.assertEquals("DateRange for " + date, expectedDateRange, dateRange);
		}

		return DateRangeExpectation.of(expectedDateRange);
	}

	@Test
	public void test_DaysIterable()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(2016, 02, 22), DateUtils.toDayDate(2016, 02, 29));
		final List<Date> daysExpected = ImmutableList.of(
				DateUtils.toDayDate(2016, 02, 22)
				, DateUtils.toDayDate(2016, 02, 23)
				, DateUtils.toDayDate(2016, 02, 24)
				, DateUtils.toDayDate(2016, 02, 25)
				, DateUtils.toDayDate(2016, 02, 26)
				, DateUtils.toDayDate(2016, 02, 27)
				, DateUtils.toDayDate(2016, 02, 28)
				, DateUtils.toDayDate(2016, 02, 29)
		);
		final List<Date> daysActual = ImmutableList.copyOf(dateRange.daysIterable());
		Assert.assertEquals(daysExpected, daysActual);
	}

	@Test
	public void test_DaysIterable_OneDay()
	{
		final DateRange dateRange = DateRange.of(DateUtils.toDayDate(2016, 02, 22), DateUtils.toDayDate(2016, 02, 22));
		final List<Date> daysExpected = ImmutableList.of(DateUtils.toDayDate(2016, 02, 22));
		final List<Date> daysActual = ImmutableList.copyOf(dateRange.daysIterable());
		Assert.assertEquals(daysExpected, daysActual);
	}

	public static final class DateRangeExpectation
	{
		public static final DateRangeExpectation of(final DateRange dateRange)
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
			Assert.assertEquals("DaysBetween for " + dateRange, expectedDaysBetween, daysBetween);
			return this;
		}

		public DateRangeExpectation assertDaysInCurrentMonth(final int expectedDaysInCurrentMonth)
		{
			final int daysInCurrentMonth = dateRange.getDaysInCurrentMonth();
			Assert.assertEquals("DaysInCurrentMonth for " + dateRange, expectedDaysInCurrentMonth, daysInCurrentMonth);
			return this;
		}
	}
}
