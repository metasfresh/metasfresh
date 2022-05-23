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

package de.metas.calendar.standard;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.util.calendar.IBusinessDayMatcher;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;

import java.time.LocalDate;

@EqualsAndHashCode
public class CalendarNonBusinessDays implements IBusinessDayMatcher
{
	private final ImmutableList<NonBusinessDay> nonBusinessDays;

	@Builder
	private CalendarNonBusinessDays(
			@NonNull @Singular final ImmutableList<NonBusinessDay> nonBusinessDays)
	{
		this.nonBusinessDays = nonBusinessDays;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("nonBusinessDays.count", nonBusinessDays.size())
				.toString();
	}

	@Override
	public boolean isBusinessDay(@NonNull final LocalDate date)
	{
		return !isNonBusinessDay(date);
	}

	public boolean isNonBusinessDay(@NonNull final LocalDate date)
	{
		return nonBusinessDays
				.stream()
				.anyMatch(nonBusinessDay -> nonBusinessDay.isMatching(date));
	}
}
