package de.metas.adempiere.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.service.IBusinessDayMatcher;
import lombok.NonNull;
import lombok.Value;

@Value
/* package */class BusinessDayMatcher implements IBusinessDayMatcher
{
	private static final ImmutableSet<DayOfWeek> DEFAULT_WEEKEND_DAYS = ImmutableSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	private final ImmutableSet<DayOfWeek> weekendDays;

	public BusinessDayMatcher()
	{
		this(DEFAULT_WEEKEND_DAYS);
	}

	private BusinessDayMatcher(@NonNull final ImmutableSet<DayOfWeek> weekendDays)
	{
		this.weekendDays = weekendDays;
	}

	@Override
	public BusinessDayMatcher changeWeekendDays(@NonNull final Set<DayOfWeek> weekendDays)
	{
		if (Objects.equals(this.weekendDays, weekendDays))
		{
			return this;
		}

		return new BusinessDayMatcher(ImmutableSet.copyOf(weekendDays));
	}

	@Override
	public Set<DayOfWeek> getWeekendDays()
	{
		return weekendDays;
	}

	@Override
	public boolean isBusinessDay(final LocalDate date)
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

	@Override
	public LocalDate getNextBusinessDay(@NonNull final LocalDate date)
	{
		LocalDate currentDate = date;
		while (!isBusinessDay(currentDate))
		{
			currentDate = currentDate.plusDays(1);
		}
		return currentDate;
	}

	private boolean isWeekend(final LocalDate date)
	{
		return weekendDays.contains(date.getDayOfWeek());
	}
}
