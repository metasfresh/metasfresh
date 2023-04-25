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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Range;
import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.impl.DateIntervalIntersectionQueryFilter;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Value
public class CalendarDateRange
{
	@NonNull Instant startDate;
	@NonNull Instant endDate;
	boolean allDay;

	@Builder(toBuilder = true)
	private CalendarDateRange(
			@NonNull final Instant startDate,
			@NonNull final Instant endDate,
			final boolean allDay)
	{
		Instant startDateToUse = startDate;
		Instant endDateToUse = endDate;

		if (startDateToUse.isAfter(endDateToUse))
		{
			final Instant temp = startDateToUse;
			startDateToUse = endDateToUse;
			endDateToUse = temp;
		}
		else if (startDateToUse.equals(endDateToUse))
		{
			throw new AdempiereException("Start date and end date shall not be equal: " + startDateToUse);
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

	/**
	 * Returns the minimal range that encloses both this range and other.
	 */
	public static CalendarDateRange span(@NonNull List<CalendarDateRange> dateRanges)
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
			Instant minStartDate = firstDateRange.getStartDate();
			boolean minStartDate_isAllDay = firstDateRange.isAllDay();
			Instant maxEndDate = firstDateRange.getEndDate();
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

	@VisibleForTesting
<<<<<<< HEAD
	Range<Instant> toRange() {return DateIntervalIntersectionQueryFilter.range(this.startDate, this.endDate);}

	public boolean isConnectedTo(@Nullable final Instant otherRangeStart, @Nullable final Instant otherRangeEnd)
	{
		final Range<Instant> otherGuavaRange = DateIntervalIntersectionQueryFilter.range(otherRangeStart, otherRangeEnd);
		final Range<Instant> thisGuavaRange = this.toRange();
		return thisGuavaRange.isConnected(otherGuavaRange);
=======
	Range<Instant> toRange()
	{
		return DateIntervalIntersectionQueryFilter.closedOpenRange(this.startDate, this.endDate);
>>>>>>> fd0ecdea5cc (prototyping)
	}

	public boolean isOverlappingWith(@NonNull final CalendarDateRange other)
	{
		return DateIntervalIntersectionQueryFilter.isOverlapping(this.toRange(), other.toRange());
	}

	public boolean isOverlappingWith(@NonNull final Range<Instant> other)
	{
		return DateIntervalIntersectionQueryFilter.isOverlapping(this.toRange(), other);
	}

	public boolean isOverlappingWith(@Nullable final Instant startClosed, @Nullable final Instant endOpen)
	{
		return isOverlappingWith(DateIntervalIntersectionQueryFilter.closedOpenRange(startClosed, endOpen));
	}

}
