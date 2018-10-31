package de.metas.purchasecandidate;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.calendar.CalendarId;
import de.metas.util.Check;
import de.metas.util.time.generator.Frequency;
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

	@Getter
	private final Duration leadTimeOffset;

	@Getter
	private final BPartnerId bpartnerId;

	@Getter
	private final CalendarId nonBusinessDaysCalendarId;

	/** might be null, if the BPPurchaseSchedule wasn't stored yet */
	@Getter
	BPPurchaseScheduleId bpPurchaseScheduleId;

	@Builder(toBuilder = true)
	private BPPurchaseSchedule(
			final BPPurchaseScheduleId bpPurchaseScheduleId,
			final LocalDate validFrom,
			@NonNull final Frequency frequency,
			@Singular @NonNull final ImmutableMap<DayOfWeek, LocalTime> dailyPreparationTimes,
			@NonNull final Duration reminderTime,
			@NonNull final Duration leadTimeOffset,
			@NonNull final BPartnerId bpartnerId,
			@Nullable final CalendarId nonBusinessDaysCalendarId)
	{
		Check.assume(!reminderTime.isNegative(), "reminderTime shall be >= 0 but it was {}", reminderTime);
		Check.assume(!leadTimeOffset.isNegative(), "leadTimeOffset shall be >= 0 but it was {}", leadTimeOffset);

		this.validFrom = validFrom != null ? validFrom : DEFAULT_VALID_FROM;
		this.frequency = frequency;
		this.dailyPreparationTimes = dailyPreparationTimes;
		this.reminderTime = reminderTime;
		this.leadTimeOffset = leadTimeOffset;
		this.bpPurchaseScheduleId = bpPurchaseScheduleId;
		this.bpartnerId = bpartnerId;
		this.nonBusinessDaysCalendarId = nonBusinessDaysCalendarId;
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
