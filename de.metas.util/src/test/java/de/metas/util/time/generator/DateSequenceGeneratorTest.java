package de.metas.util.time.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

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
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.assertj.core.api.ListAssert;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;

import de.metas.util.GuavaCollectors;
import de.metas.util.time.generator.DateSequenceGenerator;
import de.metas.util.time.generator.DaysOfMonthExploder;
import de.metas.util.time.generator.DaysOfWeekExploder;
import de.metas.util.time.generator.Frequency;
import de.metas.util.time.generator.FrequencyType;
import de.metas.util.time.generator.IDateShifter;
import de.metas.util.time.generator.WeekDayCalendarIncrementor;

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

		testGenerate(generator, expectedDates);
	}

	@Test
	public void test_EachWeek_EachDayOfWeek_generatePrevious()
	{
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final List<LocalDate> expectedDates = IntStream.rangeClosed(31, 1)
				.mapToObj(day -> LocalDate.of(2014, 8, day))
				.collect(ImmutableList.toImmutableList());

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				.byDay()
				.build();

		testGeneratePrevious(generator, date_2014_08_31, expectedDates);
	}

	@Test
	public void test_EachWeek_WedAndFri_generatePrevious()
	{
		final List<LocalDate> expectedDates = createDatesForDaysAsList(2018, 5,
				30, //
				25, 23, //
				18, 16, //
				11, 9, //
				4, 2 //
		);

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				.frequency(Frequency.builder()
						.type(FrequencyType.Weekly)
						.everyNthWeek(1)
						.onlyDayOfWeek(DayOfWeek.WEDNESDAY)
						.onlyDayOfWeek(DayOfWeek.FRIDAY)
						.build())
				.build();

		testGeneratePrevious(generator, LocalDate.of(2018, 5, 31), expectedDates);
	}

	@Test
	public void test_EachWeek_RandomDaysOfweek_generatePrevious()
	{
		for (int year = 2018; year <= 2025; year++)
		{
			for (final Month month : Month.values())
			{
				final ListMultimap<DayOfWeek, LocalDate> daysByDOW = generateDaysIndexByDOW(year, month);
				for (int i = 0; i < 100; i++)
				{
					final Set<DayOfWeek> dows = randomDayOfWeeks();
					test_EachWeek_generatePrevious(daysByDOW, dows);
				}
			}
		}
	}

	private void test_EachWeek_generatePrevious(final ListMultimap<DayOfWeek, LocalDate> daysByDOW, final Set<DayOfWeek> dows)
	{
		final List<LocalDate> expectedDates = dows.stream()
				.flatMap(dow -> daysByDOW.get(dow).stream())
				.sorted(Comparator.<LocalDate> naturalOrder().reversed())
				.collect(ImmutableList.toImmutableList());

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				.frequency(Frequency.builder()
						.type(FrequencyType.Weekly)
						.everyNthWeek(1)
						.onlyDaysOfWeek(dows)
						.build())
				.build();

		final LocalDate lastDate = expectedDates.stream()
				.max(Comparator.naturalOrder())
				.get()
				.with(TemporalAdjusters.lastDayOfMonth());

		assertThatGeneratePreviousDates(generator, lastDate, expectedDates.size())
				.as("DoWs considered: " + dows)
				.isEqualTo(expectedDates);
	}

	private static ImmutableListMultimap<DayOfWeek, LocalDate> generateDaysIndexByDOW(final int year, final Month month)
	{
		final YearMonth yearMonth = YearMonth.of(year, month);
		final LocalDate start = yearMonth.atDay(1);
		final LocalDate end = yearMonth.atEndOfMonth();

		final int days = (int)start.until(end, ChronoUnit.DAYS) + 1;

		return Stream.iterate(start, d -> d.plusDays(1))
				.limit(days)
				.collect(GuavaCollectors.toImmutableListMultimap(LocalDate::getDayOfWeek));
	}

	private final Random random = new Random(System.currentTimeMillis());

	private final Set<DayOfWeek> randomDayOfWeeks()
	{
		final int count = random.nextInt(7) + 1;
		return IntStream.range(0, count)
				.map(i -> random.nextInt(7) + 1) // random day of week number
				.mapToObj(DayOfWeek::of)
				.sorted()
				.collect(ImmutableSet.toImmutableSet());
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

		testGenerate(generator, expectedDates);
	}

	@Test
	@Ignore // known as not working
	public void test_Each2Weeks_EachDayOfWeek_generatePrevious()
	{
		final LocalDate date_2014_08_31 = LocalDate.of(2014, 8, 31);

		final List<LocalDate> expectedDates = createDatesForDaysAsList(2014, 8,
				/* week 1 */1, 2, 3,
				/* week 2 */
				/* week 3 */11, 12, 13, 14, 15, 16, 17,
				/* week 4 */
				/* week 5 */25, 26, 27, 28, 29, 30, 31)
						.reverse();

		final DateSequenceGenerator generator = DateSequenceGenerator.builder()
				.dateFrom(LocalDate.MIN)
				.dateTo(LocalDate.MAX)
				.incrementor(new WeekDayCalendarIncrementor(2, DayOfWeek.MONDAY))
				.exploder(DaysOfWeekExploder.ALL_DAYS_OF_WEEK)
				.build();

		testGeneratePrevious(generator, date_2014_08_31, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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

		testGenerate(generator, expectedDates);
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
				.shifter(new IDateShifter()
				{

					@Override
					public LocalDateTime shiftForward(final LocalDateTime date)
					{
						return date.getDayOfWeek() == DayOfWeek.SATURDAY ? date.plusDays(2) : date;
					}

					@Override
					public LocalDateTime shiftBackward(final LocalDateTime date)
					{
						throw new UnsupportedOperationException();
					}
				})
				.build();
		testGenerate(generator, expectedDates);
	}

	private void testGenerate(final DateSequenceGenerator generator, final Collection<LocalDate> expectedDates)
	{
		final Set<LocalDate> expectedDatesSet = new TreeSet<>(expectedDates);
		final Set<LocalDate> actualDatesSet = generator.generate();
		Assert.assertEquals("Invalid generator result: " + generator, expectedDatesSet, actualDatesSet);
	}

	private final void testGeneratePrevious(final DateSequenceGenerator generator, final LocalDate firstDate, final List<LocalDate> expectedDates)
	{
		assertThatGeneratePreviousDates(generator, firstDate, expectedDates.size())
				.isEqualTo(expectedDates);
	}

	private final ListAssert<LocalDate> assertThatGeneratePreviousDates(final DateSequenceGenerator generator, final LocalDate firstDate, final int count)
	{
		final List<LocalDate> actualDates = new ArrayList<>();

		LocalDate date = firstDate;
		while (actualDates.size() < count)
		{
			final LocalDate prevDate = generator.calculatePrevious(date).orElse(null);
			if (prevDate == null)
			{
				break;
			}

			actualDates.add(prevDate);

			date = prevDate.minusDays(1);
		}

		return assertThat(actualDates);
	}

	private static final Set<LocalDate> createDatesForDays(final int year, final int month, final int... days)
	{
		return IntStream.of(days)
				.mapToObj(day -> LocalDate.of(year, month, day))
				.collect(Collectors.toCollection(TreeSet::new));
	}

	private static final ImmutableList<LocalDate> createDatesForDaysAsList(final int year, final int month, final int... days)
	{
		return IntStream.of(days)
				.mapToObj(day -> LocalDate.of(year, month, day))
				.collect(ImmutableList.toImmutableList());
	}

	@SuppressWarnings("unused")
	private static final void dump(final Collection<LocalDate> dates)
	{
		for (final LocalDate date : dates)
		{
			System.out.println(date);
		}
	}

	public static void main(String[] args)
	{
		int everyNthWeek = 2;
		LocalDate dateFrom = LocalDate.of(2018, 5, 1);

		dateFrom = dateFrom.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		System.out.println("dateFrom=" + dateFrom);

		for (int day = 6; day <= 31; day++)
		{
			final LocalDate date = LocalDate.of(2018, 5, day);
			final long weeksBetween = ChronoUnit.WEEKS.between(dateFrom, date);
			final boolean isTheRightWeek = weeksBetween % everyNthWeek == 0;
			System.out.println("date=" + date + "=> weeks between=" + weeksBetween + "" + (isTheRightWeek ? " => OK" : ""));
		}

	}
}
