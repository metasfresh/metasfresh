/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.calendar.util;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@Value
public class CalendarDateRange
{
	@NonNull ZonedDateTime startDate;
	@NonNull ZonedDateTime endDate;
	boolean allDay;

	@Builder
	private CalendarDateRange(
			@NonNull final ZonedDateTime startDate,
			@NonNull final ZonedDateTime endDate,
			final boolean allDay)
	{
		ZonedDateTime startDateToUse = startDate;
		ZonedDateTime endDateToUse = endDate;

		if (allDay)
		{
			startDateToUse = startDateToUse.truncatedTo(ChronoUnit.DAYS);
			endDateToUse = endDateToUse.truncatedTo(ChronoUnit.DAYS);
		}

		if (startDateToUse.isAfter(endDateToUse))
		{
			final ZonedDateTime temp = startDateToUse;
			startDateToUse = endDateToUse;
			endDateToUse = temp;
		}

		this.allDay = allDay;
		this.startDate = startDateToUse;
		this.endDate = endDateToUse;
	}

	public Duration getDuration()
	{
		return Duration.between(startDate, endDate);
	}

}
