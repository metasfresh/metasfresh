package de.metas.util.time.generator;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;
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
	public Set<LocalDateTime> explodeForward(final LocalDateTime date)
	{
		return days.stream()
				.map(day -> withDayOfMonth(date, day))
				.filter(dayDate -> dayDate.compareTo(date) >= 0) // Skip all dates which are before our given date
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Set<LocalDateTime> explodeBackward(final LocalDateTime date)
	{
		return days.stream()
				.map(day -> withDayOfMonth(date, day))
				.filter(dayDate -> dayDate.compareTo(date) <= 0) // Skip all dates which are after our given date
				.collect(ImmutableSet.toImmutableSet());
	}

	private static final LocalDateTime withDayOfMonth(final LocalDateTime date, final int dayOfMonth)
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
