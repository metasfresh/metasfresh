package de.metas.purchasecandidate;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import org.adempiere.util.Check;
import org.adempiere.util.time.generator.Frequency;

import com.google.common.collect.ImmutableMap;

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
	private static final LocalDate DEFAULT_VALID_FROM = LocalDate.MIN;

	@Getter
	private final Frequency frequency;

	private static final LocalTime DEFAULT_PREPARATION_TIME = LocalTime.of(23, 59);
	private final ImmutableMap<DayOfWeek, LocalTime> dailyPreparationTimes;

	@Getter
	private final Duration reminderTime;

	@Builder
	private BPPurchaseSchedule(
			final LocalDate validFrom,
			@NonNull final Frequency frequency,
			@Singular @NonNull final ImmutableMap<DayOfWeek, LocalTime> dailyPreparationTimes,
			@NonNull final Duration reminderTime)
	{
		Check.assume(!reminderTime.isNegative(), "reminderTime shall be >= 0 but it was {}", reminderTime);

		this.validFrom = validFrom != null ? validFrom : DEFAULT_VALID_FROM;
		this.frequency = frequency;
		this.dailyPreparationTimes = dailyPreparationTimes;
		this.reminderTime = reminderTime;
	}

	public LocalTime getPreparationTime(final DayOfWeek dayOfWeek)
	{
		return dailyPreparationTimes.getOrDefault(dayOfWeek, DEFAULT_PREPARATION_TIME);
	}

	public LocalDateTime applyTimeTo(@NonNull final LocalDate date)
	{
		final LocalTime time = getPreparationTime(date.getDayOfWeek());
		return LocalDateTime.of(date, time);
	}

	public Optional<LocalDateTime> calculateReminderDateTime(final LocalDateTime purchaseDate)
	{
		if (reminderTime.isZero())
		{
			return Optional.empty();
		}

		return Optional.of(purchaseDate.minus(reminderTime));
	}
}
