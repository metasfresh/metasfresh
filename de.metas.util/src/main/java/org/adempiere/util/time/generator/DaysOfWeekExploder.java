package org.adempiere.util.time.generator;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Value;

@Value
public class DaysOfWeekExploder implements IDateSequenceExploder
{
	public static final DaysOfWeekExploder of(final Collection<Integer> weekDays)
	{
		final ImmutableSet<Integer> weekDaysSet = ImmutableSet.copyOf(weekDays);
		if (weekDaysSet.equals(ALL_DAYS_OF_WEEK_LIST))
		{
			return ALL_DAYS_OF_WEEK;
		}
		return new DaysOfWeekExploder(weekDaysSet);
	}

	public static final DaysOfWeekExploder of(final int... weekDays)
	{
		return of(Arrays.stream(weekDays).boxed().collect(ImmutableSet.toImmutableSet()));
	}

	private static final ImmutableSet<Integer> ALL_DAYS_OF_WEEK_LIST = ImmutableSet.of(
			Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY,
			Calendar.SATURDAY, Calendar.SUNDAY);

	public static final DaysOfWeekExploder ALL_DAYS_OF_WEEK = new DaysOfWeekExploder(ALL_DAYS_OF_WEEK_LIST);

	private final ImmutableSet<Integer> weekDays;
	private final int firstDayOfTheWeek;

	private DaysOfWeekExploder(final Collection<Integer> weekDays)
	{
		Check.assumeNotEmpty(weekDays, "weekDays not empty");
		this.weekDays = ImmutableSet.copyOf(weekDays);
		for (final Integer weekDay : this.weekDays)
		{
			Check.assume(ALL_DAYS_OF_WEEK_LIST.contains(weekDay), "Week day {} shall be valid", weekDay);
		}

		this.firstDayOfTheWeek = Calendar.MONDAY;
	}

	/**
	 * @return all dates which are in same week as <code>date</code> and are equal or after it
	 */
	@Override
	public Collection<Date> explode(final Date date)
	{
		final long dateMillis = date.getTime();

		final Set<Date> dates = new HashSet<>();
		for (final int dayOfWeek : weekDays)
		{
			final Calendar cal = new GregorianCalendar();
			cal.setTime(date);
			cal.setFirstDayOfWeek(firstDayOfTheWeek);
			cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);

			// Skip all dates which are before our given date
			if (dateMillis > cal.getTimeInMillis())
			{
				continue;
			}

			final Date dayOfWeekDate = cal.getTime();
			dates.add(dayOfWeekDate);
		}

		return dates;
	}

}
