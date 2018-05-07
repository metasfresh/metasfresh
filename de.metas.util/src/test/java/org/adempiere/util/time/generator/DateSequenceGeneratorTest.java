package org.adempiere.util.time.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Test;

public class DateSequenceGeneratorTest
{
	@Test
	public void test_EachWeek_EachDayOfWeek()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8,
				/* week 1 */1, 2, 3,
				/* week 2 */4, 5, 6, 7, 8, 9, 10,
				/* week 3 */11, 12, 13, 14, 15, 16, 17,
				/* week 4 */18, 19, 20, 21, 22, 23, 24,
				/* week 5 */25, 26, 27, 28, 29, 30, 31);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.byDay()
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_EachDayOfWeek()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8,
				/* week 1 */1, 2, 3,
				/* week 2 */
				/* week 3 */11, 12, 13, 14, 15, 16, 17,
				/* week 4 */
				/* week 5 */25, 26, 27, 28, 29, 30, 31);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.incrementor(new WeekDayCalendarIncrementor(2, DayOfWeek.MONDAY))
				.exploder(DaysOfWeekExploder.ALL_DAYS_OF_WEEK)
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_OnlyMondayAndWednesday()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8,
				/* week 1 */
				/* week 2 */
				/* week 3 */11, 13,
				/* week 4 */
				/* week 5 */25, 27);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.byWeeks(2, DayOfWeek.MONDAY) // each 2 weeks, start on monday
				.exploder(DaysOfWeekExploder.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY))
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_OnlyTuesdayAndWednesday()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8,
				/* week 1 */
				/* week 2 */
				/* week 3 */12, 13,
				/* week 4 */
				/* week 5 */26, 27);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.byWeeks(2, DayOfWeek.MONDAY) // each 2 weeks, start on monday
				.exploder(DaysOfWeekExploder.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY))
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_OnlySunday()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8,
				/* week 1 */3,
				/* week 2 */
				/* week 3 */17,
				/* week 4 */
				/* week 5 */31);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.byWeeks(2, DayOfWeek.MONDAY) // each 2 weeks, start on monday
				.exploder(DaysOfWeekExploder.of(DayOfWeek.SUNDAY))
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_OnlySunday_MakeSureDateToIsNotExceeded()
	{
		final LocalDate date_2014_09_01 = LocalDate.of(2014, 9, 1);
		final LocalDate date_2014_09_30 = LocalDate.of(2014, 9, 30);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 9,
				/* week 1 */7
				/* week 2 */
				/* week 3 */, 21
		/* week 4 */
		/* week 5 */
		);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_09_01)
				.dateTo(date_2014_09_30)
				.byWeeks(2, DayOfWeek.MONDAY) // each 2 weeks, start on monday
				.exploder(DaysOfWeekExploder.of(DayOfWeek.SUNDAY))
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_EachMonth_Day15()
	{
		final LocalDate date_2014_01_01 = LocalDate.of(2014, 1, 1);
		final LocalDate date_2014_12_31 = LocalDate.of(2014, 12, 31);

		final List<LocalDate> expectedDates = Arrays.asList(
				LocalDate.of(2014, 1, 15),
				LocalDate.of(2014, 2, 15),
				LocalDate.of(2014, 3, 15),
				LocalDate.of(2014, 4, 15),
				LocalDate.of(2014, 5, 15),
				LocalDate.of(2014, 6, 15),
				LocalDate.of(2014, 7, 15),
				LocalDate.of(2014, 8, 15),
				LocalDate.of(2014, 9, 15),
				LocalDate.of(2014, 10, 15),
				LocalDate.of(2014, 11, 15),
				LocalDate.of(2014, 12, 15));

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_01_01)
				.dateTo(date_2014_12_31)
				.byMonths(1, 1) // each month, first day of month
				.exploder(DaysOfMonthExploder.of(15))
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_EachMonth_Day31()
	{
		final LocalDate date_2014_01_01 = LocalDate.of(2014, 1, 1);
		final LocalDate date_2014_12_31 = LocalDate.of(2014, 12, 31);

		final List<LocalDate> expectedDates = Arrays.asList(
				LocalDate.of(2014, 1, 31),
				LocalDate.of(2014, 2, 28),
				LocalDate.of(2014, 3, 31),
				LocalDate.of(2014, 4, 30),
				LocalDate.of(2014, 5, 31),
				LocalDate.of(2014, 6, 30),
				LocalDate.of(2014, 7, 31),
				LocalDate.of(2014, 8, 31),
				LocalDate.of(2014, 9, 30),
				LocalDate.of(2014, 10, 31),
				LocalDate.of(2014, 11, 30),
				LocalDate.of(2014, 12, 31));

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_01_01)
				.dateTo(date_2014_12_31)
				.byMonths(1, 1) // each month, first day of month
				.exploder(DaysOfMonthExploder.LAST_DAY)
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Month_Day31()
	{
		final LocalDate date_2014_01_01 = LocalDate.of(2014, 1, 1);
		final LocalDate date_2014_12_31 = LocalDate.of(2014, 12, 31);

		final List<LocalDate> expectedDates = Arrays.asList(
				LocalDate.of(2014, 1, 31),
				// LocalDate.of(2014, 2, 28),
				LocalDate.of(2014, 3, 31),
				// LocalDate.of(2014, 4, 30),
				LocalDate.of(2014, 5, 31),
				// LocalDate.of(2014, 6, 30),
				LocalDate.of(2014, 7, 31),
				// LocalDate.of(2014, 8, 31),
				LocalDate.of(2014, 9, 30),
				// LocalDate.of(2014, 10, 31),
				LocalDate.of(2014, 11, 30)
		// LocalDate.of(2014, 12, 31)
		);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_01_01)
				.dateTo(date_2014_12_31)
				.byMonths(2, 1) // each 2 months, first day of month
				.exploder(DaysOfMonthExploder.LAST_DAY)
				.build();

		testGenerator(generator, expectedDates);
	}

	@Test
	public void test_Each2Weeks_EachDayOfWeek_ShiftSaturdayNoNextMonday_NoSundaysShallBeIncludedInThatCase()
	{
		final LocalDate date_2014_08_01 = LocalDate.of(2014, 8, 1);
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final Set<LocalDate> expectedDates = createDatesForDays(2014, 8
		/* Aug - week 1 */, 1 // 2=Saturday => 3=Sunday shall be skipped too
		/* Aug - week 2 */, 4 // was shifted from 2
		/* Aug - week 3 */, 11, 12, 13, 14, 15 // 16=Saturday => 17=Sunday shall be skipped
		/* Aug - week 4 */, 18 // was shifted from 16
		/* Aug - week 5 */, 25, 26, 27, 28, 29 // 30=Saturday => 31=Sunday shall be skipped
		/* Sep - week 6 */// nothing because even if 30.Aug was shifted to 1.Sep we are skiping it because we enforce interval ending (see enforceDateToAfterShift)
		);

		DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(date_2014_08_01)
				.dateTo(date_2014_08_31)
				.incrementor(new WeekDayCalendarIncrementor(2, DayOfWeek.MONDAY))
				.exploder(DaysOfWeekExploder.ALL_DAYS_OF_WEEK)
				.enforceDateToAfterShift(true) // don't shift after end date
				.shifter(date -> {
					if (date.getDayOfWeek() == DayOfWeek.SATURDAY)
					{
						return date.plusDays(2);
					}
					else
					{
						return date;
					}
				})
				.build();
		testGenerator(generator, expectedDates);

		//
		// Test with "enforceDateToAfterShift" deactivated
		generator = generator.toBuilder()
				.enforceDateToAfterShift(false) // now we allow the shifted date to be after the "to()" date
				.build();
		expectedDates.add(LocalDate.of(2014, 9, 1)); // we expected 30.Aug(Sat) to be shifted to 1.Sep
		testGenerator(generator, expectedDates);
	}

	private void testGenerator(final DateSequenceGenerator generator, final Collection<LocalDate> expectedDates)
	{
		final Set<LocalDate> expectedDatesSet = new TreeSet<>(expectedDates);
		final Set<LocalDate> actualDatesSet = generator.generate();
		Assert.assertEquals("Invalid generator result: " + generator, expectedDatesSet, actualDatesSet);
	}

	private static final Set<LocalDate> createDatesForDays(final int year, final int month, int... days)
	{
		final Set<LocalDate> dates = new TreeSet<>();

		for (final int day : days)
		{
			final LocalDate date = LocalDate.of(year, month, day);
			dates.add(date);
		}

		return dates;
	}

	@SuppressWarnings("unused")
	private static final void dump(final Collection<LocalDate> dates)
	{
		for (final LocalDate date : dates)
		{
			System.out.println(date);
		}
	}

}
