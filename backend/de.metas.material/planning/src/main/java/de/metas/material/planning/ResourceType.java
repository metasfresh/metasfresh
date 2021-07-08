package de.metas.material.planning;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalUnit;

/*
 * #%L
 * metasfresh-material-planning
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
@Builder
public class ResourceType
{
	@Getter(AccessLevel.PRIVATE) boolean active;

	@NonNull ProductCategoryId productCategoryId;

	@NonNull UomId durationUomId;
	@NonNull TemporalUnit durationUnit;

	@NonNull @Getter(AccessLevel.NONE) ImmutableSet<DayOfWeek> availableDaysOfWeek;

	boolean timeSlot;
	LocalTime timeSlotStart;
	LocalTime timeSlotEnd;

	/**
	 * Get how many hours/day a is available.
	 * Minutes, secords and millis are discarded.
	 *
	 * @return available hours
	 */
	public int getTimeSlotInHours()
	{
		if (isTimeSlot())
		{
			return (int)Duration.between(timeSlotStart, timeSlotEnd).toHours();
		}
		else
		{
			return 24;
		}
	}

	public boolean isAvailable()
	{
		return isActive()
				&& getAvailableDaysPerWeek() > 0
				&& getTimeSlotInHours() > 0;
	}

	public boolean isDayAvailable(final LocalDate date)
	{
		return isActive()
				&& isDayAvailable(date.getDayOfWeek());
	}

	public int getAvailableDaysPerWeek()
	{
		return availableDaysOfWeek.size();
	}

	public boolean isDayAvailable(@NonNull final DayOfWeek dayOfWeek)
	{
		return availableDaysOfWeek.contains(dayOfWeek);
	}

	@Deprecated
	public Timestamp getDayStart(final Timestamp date)
	{
		final LocalDateTime dayStart = getDayStart(TimeUtil.asLocalDateTime(date));
		return TimeUtil.asTimestamp(dayStart);
	}

	public LocalDateTime getDayStart(final LocalDateTime date)
	{
		if (isTimeSlot())
		{
			return date.toLocalDate().atTime(getTimeSlotStart());
		}
		else
		{
			return date.toLocalDate().atStartOfDay();
		}
	}

	@Deprecated
	public Timestamp getDayEnd(final Timestamp date)
	{
		final LocalDateTime dayEnd = getDayEnd(TimeUtil.asLocalDateTime(date));
		return TimeUtil.asTimestamp(dayEnd);
	}

	public LocalDateTime getDayEnd(final LocalDateTime date)
	{
		if (isTimeSlot())
		{
			return date.toLocalDate().atTime(timeSlotEnd);
		}
		else
		{
			return date.toLocalDate().atTime(LocalTime.MAX);
		}
	}
}
