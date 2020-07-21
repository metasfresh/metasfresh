package de.metas.error;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
@ToString
public class IssueCountersByCategory
{
	public static IssueCountersByCategory of(final Map<IssueCategory, Integer> map)
	{
		return !map.isEmpty()
				? new IssueCountersByCategory(map)
				: EMPTY;
	}

	private static final IssueCountersByCategory EMPTY = new IssueCountersByCategory();
	private final ImmutableMap<IssueCategory, Integer> map;

	private IssueCountersByCategory(@NonNull final Map<IssueCategory, Integer> map)
	{
		this.map = ImmutableMap.copyOf(map);
	}

	private IssueCountersByCategory()
	{
		map = ImmutableMap.of();
	}

	public ImmutableSet<IssueCategory> getCategories()
	{
		return map.keySet();
	}

	public int getCountOrZero(@NonNull final IssueCategory category)
	{
		final Integer count = map.get(category);
		return count != null ? count : 0;
	}
}
