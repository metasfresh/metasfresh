package org.adempiere.util.time.generator;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collection;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Value;

@Value
public class DaysOfMonthExploder implements IDateSequenceExploder
{
	public static DaysOfMonthExploder of(final Collection<Integer> days)
	{
		final ImmutableSet<Integer> daysSet = ImmutableSet.copyOf(days);
		return new DaysOfMonthExploder(daysSet);
	}

	public static DaysOfMonthExploder of(final int... days)
	{
		return of(Arrays.stream(days).boxed().collect(ImmutableSet.toImmutableSet()));
	}

	public static final DaysOfMonthExploder LAST_DAY = new DaysOfMonthExploder(ImmutableSet.of(31));

	private final ImmutableSet<Integer> days;

	public DaysOfMonthExploder(final Collection<Integer> days)
	{
		Check.assumeNotNull(days, "days not null");
		this.days = ImmutableSet.copyOf(days);
	}

	@Override
	public Collection<LocalDate> explode(final LocalDate date)
	{
		return days.stream()
				.map(day -> withDayOfMonth(date, day))
				.filter(dayDate -> dayDate.compareTo(date) >= 0) // Skip all dates which are before our given date
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final LocalDate withDayOfMonth(final LocalDate date, final int dayOfMonth)
	{
		if (dayOfMonth >= 31)
		{
			return date.with(TemporalAdjusters.lastDayOfMonth());
		}
		else
		{
			return date.withDayOfMonth(dayOfMonth);
		}
	}

}
