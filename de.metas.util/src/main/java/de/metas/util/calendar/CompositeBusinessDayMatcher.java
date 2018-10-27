package de.metas.util.calendar;

import java.time.LocalDate;
import java.util.List;

import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
public class CompositeBusinessDayMatcher implements IBusinessDayMatcher
{
	public static final IBusinessDayMatcher compose(final List<IBusinessDayMatcher> matchers)
	{
		if (matchers.size() == 1)
		{
			return matchers.get(0);
		}
		else
		{
			return new CompositeBusinessDayMatcher(ImmutableList.copyOf(matchers));
		}
	}

	private final ImmutableList<IBusinessDayMatcher> matchers;

	@Builder
	private CompositeBusinessDayMatcher(@NonNull @Singular final ImmutableList<IBusinessDayMatcher> matchers)
	{
		this.matchers = matchers;
	}

	@Override
	public boolean isBusinessDay(final LocalDate date)
	{
		for (final IBusinessDayMatcher matcher : matchers)
		{
			if (!matcher.isBusinessDay(date))
			{
				return false;
			}
		}

		return true;
	}
}
