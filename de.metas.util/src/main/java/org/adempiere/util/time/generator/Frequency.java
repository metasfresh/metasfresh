package org.adempiere.util.time.generator;

import java.time.DayOfWeek;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
public class Frequency
{
	FrequencyType type;

	int everyNthWeek;
	ImmutableSet<DayOfWeek> onlyDaysOfWeek;

	int everyNthMonth;
	ImmutableSet<Integer> onlyDaysOfMonth;

	@Builder
	private Frequency(
			@NonNull final FrequencyType type,
			final int everyNthWeek,
			@Singular("onlyDayOfWeek") final Set<DayOfWeek> onlyDaysOfWeek,
			final int everyNthMonth,
			@Singular("onlyDayOfMonth") final Set<Integer> onlyDaysOfMonth)
	{
		this.type = type;

		if (type == FrequencyType.Weekly)
		{
			Check.assumeGreaterThanZero(everyNthWeek, "everyNthWeek");
			this.everyNthWeek = everyNthWeek;
			this.onlyDaysOfWeek = ImmutableSet.copyOf(onlyDaysOfWeek);

			this.everyNthMonth = 1;
			this.onlyDaysOfMonth = ImmutableSet.of();
		}
		else if (type == FrequencyType.Monthly)
		{
			this.everyNthWeek = 1;
			this.onlyDaysOfWeek = ImmutableSet.of();

			Check.assumeGreaterThanZero(everyNthMonth, "everyNthMonth");
			this.everyNthMonth = everyNthMonth;

			Check.assumeNotEmpty(onlyDaysOfMonth, "onlyDaysOfMonth is not empty");
			this.onlyDaysOfMonth = ImmutableSet.copyOf(onlyDaysOfMonth);
		}
		else
		{
			throw new IllegalArgumentException("Unknow " + FrequencyType.class + ": " + type);
		}

	}

	public boolean isWeekly()
	{
		return type == FrequencyType.Weekly;
	}

	public boolean isMonthly()
	{
		return type == FrequencyType.Monthly;
	}

	public boolean isOnlySomeDaysOfTheWeek()
	{
		return !onlyDaysOfWeek.isEmpty();
	}
}
