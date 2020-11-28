package de.metas.util.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class ExcludeWeekendBusinessDayMatcher implements IBusinessDayMatcher
{
	private static final ImmutableSet<DayOfWeek> DEFAULT_WEEKEND_DAYS = ImmutableSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

	/** Matcher with Saturday and Sunday as non working days */
	public static final ExcludeWeekendBusinessDayMatcher DEFAULT = ExcludeWeekendBusinessDayMatcher.builder().build();

	private final ImmutableSet<DayOfWeek> weekendDays;

	@Builder
	private ExcludeWeekendBusinessDayMatcher(final Set<DayOfWeek> excludeWeekendDays)
	{
		weekendDays = buildWeekendDays(excludeWeekendDays);
	}

	private static ImmutableSet<DayOfWeek> buildWeekendDays(final Set<DayOfWeek> excludeWeekendDays)
	{
		if (excludeWeekendDays == null || excludeWeekendDays.isEmpty())
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
		return !weekendDays.contains(date.getDayOfWeek());
	}
}
