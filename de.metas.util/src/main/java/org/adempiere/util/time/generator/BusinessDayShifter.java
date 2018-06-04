package org.adempiere.util.time.generator;

import java.time.LocalDate;
import java.util.List;

import org.adempiere.util.Check;

import de.metas.calendar.CompositeBusinessDayMatcher;
import de.metas.calendar.IBusinessDayMatcher;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

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
	public LocalDate shiftForward(final LocalDate deliveryDate)
	{
		final boolean forward = true;
		return shift(deliveryDate, forward);
	}

	@Override
	public LocalDate shiftBackward(final LocalDate deliveryDate)
	{
		final boolean forward = false;
		return shift(deliveryDate, forward);
	}

	private LocalDate shift(final LocalDate deliveryDate, final boolean forward)
	{
		//
		// Case: we deal with a delivery date which is in a business day
		if (businessDayMatcher.isBusinessDay(deliveryDate))
		{
			return deliveryDate;
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
					return businessDayMatcher.getNextBusinessDay(deliveryDate);
				}
				else
				{
					return businessDayMatcher.getPreviousBusinessDay(deliveryDate);
				}
			}
			else
			{
				throw Check.fail("Unknown " + OnNonBussinessDay.class + ": " + onNonBussinessDay);
			}
		}
	}
}
