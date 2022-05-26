/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.resource;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.product.ProductCategoryId;
import de.metas.uom.UomId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;

@Value
@Builder
public class ResourceType
{
	@NonNull ResourceTypeId id;

	@Getter(AccessLevel.PRIVATE) boolean active;

	@NonNull ProductCategoryId productCategoryId;

	@NonNull UomId durationUomId;
	@NonNull TemporalUnit durationUnit;

	@NonNull @Getter(AccessLevel.NONE) ImmutableSet<DayOfWeek> availableDaysOfWeek;

	boolean timeSlot;
	LocalTime timeSlotStart;
	LocalTime timeSlotEnd;

	/**
	 * Get how many hours/day are available.
	 * Minutes, seconds and millis are discarded.
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

	public boolean isDayAvailable(final Instant date)
	{
		return isActive()
				&& isDayAvailable(date.atZone(SystemTime.zoneId()).getDayOfWeek());
	}

	public int getAvailableDaysPerWeek()
	{
		return availableDaysOfWeek.size();
	}

	public boolean isDayAvailable(@NonNull final DayOfWeek dayOfWeek)
	{
		return availableDaysOfWeek.contains(dayOfWeek);
	}

	public Instant getDayStart(@NonNull final Instant date)
	{
		return getDayStart(date.atZone(SystemTime.zoneId())).toInstant();
	}

	public ZonedDateTime getDayStart(final ZonedDateTime date)
	{
		if (isTimeSlot())
		{
			return date.toLocalDate().atTime(getTimeSlotStart()).atZone(date.getZone());
		}
		else
		{
			return date.toLocalDate().atStartOfDay().atZone(date.getZone());
		}
	}

	public Instant getDayEnd(final Instant date)
	{
		return getDayEnd(date.atZone(SystemTime.zoneId())).toInstant();
	}

	public ZonedDateTime getDayEnd(final ZonedDateTime date)
	{
		if (isTimeSlot())
		{
			return date.toLocalDate().atTime(timeSlotEnd).atZone(date.getZone());
		}
		else
		{
			return date.toLocalDate().atTime(LocalTime.MAX).atZone(date.getZone());
		}
	}
}
