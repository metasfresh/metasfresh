package de.metas.adempiere.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IBusinessDayMatcher;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
/* package */class BusinessDayMatcher implements IBusinessDayMatcher
{
	private static final ImmutableSet<DayOfWeek> DEFAULT_WEEKEND_DAYS = ImmutableSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	private final ImmutableSet<DayOfWeek> weekendDays;

	@Builder
	private BusinessDayMatcher(@NonNull final Set<DayOfWeek> excludeWeekendDays)
	{
		weekendDays = buildWeekendDays(excludeWeekendDays);
	}

	private static ImmutableSet<DayOfWeek> buildWeekendDays(final Set<DayOfWeek> excludeWeekendDays)
	{
		if (excludeWeekendDays.isEmpty())
		{
			return DEFAULT_WEEKEND_DAYS;
		}

		final Set<DayOfWeek> weekendDays = new HashSet<>(DEFAULT_WEEKEND_DAYS);
		weekendDays.removeAll(excludeWeekendDays);
		return ImmutableSet.copyOf(weekendDays);
	}

	@Override
	public boolean isBusinessDay(@NonNull final LocalDate date)
	{
		Check.assumeNotNull(date, "date not null");

		//
		// Exclude configured weekend days
		if (isWeekend(date))
		{
			return false;
		}

		// TODO: check C_NonBusinessDay table

		return true;
	}

	private boolean isWeekend(final LocalDate date)
	{
		return weekendDays.contains(date.getDayOfWeek());
	}
}
