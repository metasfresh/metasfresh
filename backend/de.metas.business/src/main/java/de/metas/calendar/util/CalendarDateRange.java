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

import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

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

	public CalendarDateRange plus(@NonNull final Duration duration)
	{
		if (duration.isZero())
		{
			return this;
		}

		return builder()
				.startDate(startDate.plus(duration))
				.endDate(endDate.plus(duration))
				.allDay(allDay && DurationUtils.isCompleteDays(duration))
				.build();
	}

	public CalendarDateRange minus(@NonNull final Duration duration)
	{
		return plus(duration.negated());
	}

	public static CalendarDateRange computeDateRangeToFitAll(@NonNull List<CalendarDateRange> dateRanges)
	{
		if (dateRanges.isEmpty())
		{
			throw new AdempiereException("No date ranges provided");
		}
		else if (dateRanges.size() == 1)
		{
			return dateRanges.get(0);
		}
		else
		{
			final CalendarDateRange firstDateRange = dateRanges.get(0);
			ZonedDateTime minStartDate = firstDateRange.getStartDate();
			boolean minStartDate_isAllDay = firstDateRange.isAllDay();
			ZonedDateTime maxEndDate = firstDateRange.getEndDate();
			boolean maxEndDate_isAllDay = firstDateRange.isAllDay();

			for (int i = 1; i < dateRanges.size(); i++)
			{
				final CalendarDateRange dateRange = dateRanges.get(i);

				if (dateRange.getStartDate().isBefore(minStartDate))
				{
					minStartDate = dateRange.getStartDate();
					minStartDate_isAllDay = dateRange.isAllDay();
				}

				if (dateRange.getEndDate().isAfter(maxEndDate))
				{
					maxEndDate = dateRange.getEndDate();
					maxEndDate_isAllDay = dateRange.isAllDay();
				}
			}

			return builder()
					.startDate(minStartDate)
					.endDate(maxEndDate)
					.allDay(minStartDate_isAllDay && maxEndDate_isAllDay)
					.build();
		}
	}

}
