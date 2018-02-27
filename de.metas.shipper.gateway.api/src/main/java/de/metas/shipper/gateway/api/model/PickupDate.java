package de.metas.shipper.gateway.api.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.shipper.gateway.api
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class PickupDate
{
	LocalDate date;
	LocalTime timeFrom;
	LocalTime timeTo;

	@Builder
	private PickupDate(
			@NonNull final LocalDate date,
			@Nullable final LocalTime timeFrom,
			@Nullable final LocalTime timeTo)
	{
		this.date = date;
		this.timeFrom = timeFrom;
		this.timeTo = timeTo;
	}

	public LocalDateTime getDateTimeFrom()
	{
		return timeFrom != null ? date.atTime(timeFrom) : null;
	}

	public LocalDateTime getDateTimeTo()
	{
		return timeTo != null ? date.atTime(timeTo) : null;
	}
}
