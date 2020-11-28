/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.LocalDate;
import java.util.ArrayList;

@Value
@Builder
public class LocalDateInterval
{
	@NonNull
	LocalDate startDate;

	@NonNull
	LocalDate endDate;

	public static LocalDateInterval of(@NonNull final LocalDate startDate,
			                           @NonNull final LocalDate endDate)
	{
		return new LocalDateInterval(startDate, endDate);
	}

	/**
	 *  Divides this interval in multiple intervals using the specified step.
	 *  startDate = LocalDate.of(2020, 4, 1);
	 *  endDate = LocalDate.of(2020, 4, 14);
	 *  LocalDateInterval dateInterval = LocalDateInterval.of(startDate, endDate);
	 *
	 *  dateInterval.divideUsingStep(7)
	 *   == [ (LocalDate.of(2020, 4, 1), LocalDate.of(2020, 4, 7)),
	 *        (LocalDate.of(2020, 4, 8), LocalDate.of(2020, 4, 14)) ]
	 *
	 * @param step the step to be used
	 * @return a list with the intervals obtained
	 */
	@NonNull
	public ImmutableList<LocalDateInterval> divideUsingStep(final int step)
	{
		final ArrayList<LocalDateInterval> intervals = new ArrayList<>();

		LocalDate intervalStartDate = startDate;
		LocalDate intervalEndDate = intervalStartDate.plusDays(step - 1);

		while (intervalEndDate.isBefore(endDate))
		{
			intervals.add(LocalDateInterval.of(intervalStartDate, intervalEndDate));

			intervalStartDate = intervalStartDate.plusDays(step);
			intervalEndDate = intervalEndDate.plusDays(step);
		}

		intervals.add(new LocalDateInterval(intervalStartDate, endDate));

		return ImmutableList.copyOf(intervals);
	}
}
