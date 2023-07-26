package de.metas.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
public class RecurrentNonBusinessDay implements NonBusinessDay
{
	@NonNull
	RecurrentNonBusinessDayFrequency frequency;

	@NonNull
	LocalDate startDate;
	@Nullable
	LocalDate endDate;

	@Nullable
	String name;

	@Override
	public boolean isMatching(@NonNull final LocalDate date)
	{
		if (!isInRange(date))
		{
			return false;
		}

		if (frequency == RecurrentNonBusinessDayFrequency.WEEKLY)
		{
			final DayOfWeek dayOfWeek = this.startDate.getDayOfWeek();
			return date.getDayOfWeek().equals(dayOfWeek);
		}
		else if (frequency == RecurrentNonBusinessDayFrequency.YEARLY)
		{
			LocalDate currentDate = startDate;
			while (isInRange(currentDate) && currentDate.compareTo(date) <= 0)
			{
				if (currentDate.equals(date))
				{
					return true;
				}

				currentDate = currentDate.plusYears(1);
			}

			return false;
		}
		else
		{
			throw new AdempiereException("Unknown frequency type: " + frequency);
		}
	}

	private boolean isInRange(@NonNull final LocalDate date)
	{
		// Before start date
		if (date.compareTo(startDate) < 0)
		{
			return false;
		}

		// After end date
		if (endDate != null && date.compareTo(endDate) > 0)
		{
			return false;
		}

		return true;
	}
}
