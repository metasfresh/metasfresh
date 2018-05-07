package de.metas.purchasecandidate;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.adempiere.util.Check;
import org.adempiere.util.time.generator.FrequencyType;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

/*
 * #%L
 * de.metas.purchasecandidate.base
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

@ToString
public class BPPurchaseSchedule
{
	@Getter
	private final LocalDate validFrom;
	private static final LocalDate DEFAULT_VALID_FROM = LocalDate.of(1970, 1, 1);

	@Getter
	private final FrequencyType frequencyType;
	@Getter
	private final int frequency;
	@Getter
	private final int dayOfMonth;

	@Getter
	private final ImmutableSet<DayOfWeek> onlyDaysOfWeek;

	private static final LocalTime DEFAULT_PREPARATION_TIME = LocalTime.of(23, 59);
	private final ImmutableMap<DayOfWeek, LocalTime> dailyPreparationTimes;

	@Builder
	private BPPurchaseSchedule(
			final LocalDate validFrom,
			@NonNull final FrequencyType frequencyType,
			final int frequency,
			final int dayOfMonth,
			@Singular("onlyDayOfWeek") @NonNull final ImmutableSet<DayOfWeek> onlyDaysOfWeek,
			@Singular @NonNull final ImmutableMap<DayOfWeek, LocalTime> dailyPreparationTimes)
	{
		Check.assumeGreaterThanZero(frequency, "frequency");

		this.validFrom = validFrom != null ? validFrom : DEFAULT_VALID_FROM;
		this.frequencyType = frequencyType;
		this.frequency = frequency;
		this.dayOfMonth = dayOfMonth;
		this.onlyDaysOfWeek = onlyDaysOfWeek;
		this.dailyPreparationTimes = dailyPreparationTimes;
	}

	public LocalTime getPreparationType(final DayOfWeek dayOfWeek)
	{
		return dailyPreparationTimes.getOrDefault(dayOfWeek, DEFAULT_PREPARATION_TIME);
	}
}
