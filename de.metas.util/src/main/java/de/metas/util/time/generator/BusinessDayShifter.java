package de.metas.util.time.generator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import de.metas.calendar.CompositeBusinessDayMatcher;
import de.metas.calendar.IBusinessDayMatcher;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

/*
 * #%L
 * de.metas.util
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
public class BusinessDayShifter implements IDateShifter
{
	public static enum OnNonBussinessDay
	{
		Cancel, MoveToClosestBusinessDay,
	}

	private final IBusinessDayMatcher businessDayMatcher;
	private final OnNonBussinessDay onNonBussinessDay;

	@Builder
	private BusinessDayShifter(
			@NonNull @Singular final List<IBusinessDayMatcher> businessDayMatchers,
			@NonNull final OnNonBussinessDay onNonBussinessDay)
	{
		this.businessDayMatcher = CompositeBusinessDayMatcher.compose(businessDayMatchers);
		this.onNonBussinessDay = onNonBussinessDay;
	}

	@Override
	public LocalDateTime shiftForward(final LocalDateTime date)
	{
		final boolean forward = true;
		return shift(date, forward);
	}

	@Override
	public LocalDateTime shiftBackward(final LocalDateTime date)
	{
		final boolean forward = false;
		return shift(date, forward);
	}

	private LocalDateTime shift(final LocalDateTime date, final boolean forward)
	{
		//
		// Case: we deal with a delivery date which is in a business day
		if (businessDayMatcher.isBusinessDay(date.toLocalDate()))
		{
			return date;
		}
		//
		// Case: our delivery date is not in a business day
		else
		{
			//
			// Case: we need to cancel because our delivery date it's not in a business day
			if (onNonBussinessDay == OnNonBussinessDay.Cancel)
			{
				// skip this delivery date
				return null;
			}
			//
			// Case: we need to move our delivery date to next/previous business day
			else if (onNonBussinessDay == OnNonBussinessDay.MoveToClosestBusinessDay)
			{
				if (forward)
				{
					final LocalDate nextBusinessDay = businessDayMatcher.getNextBusinessDay(date.toLocalDate());
					return LocalDateTime.of(nextBusinessDay, date.toLocalTime());
				}
				else
				{
					final LocalDate previousBusinessDay = businessDayMatcher.getPreviousBusinessDay(date.toLocalDate());
					return LocalDateTime.of(previousBusinessDay, date.toLocalTime());
				}
			}
			else
			{
				throw Check.fail("Unknown " + OnNonBussinessDay.class + ": " + onNonBussinessDay);
			}
		}
	}
}
