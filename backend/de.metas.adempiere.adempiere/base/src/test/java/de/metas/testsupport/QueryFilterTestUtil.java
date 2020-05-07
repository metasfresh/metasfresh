package de.metas.testsupport;

import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.util.collections.ListUtils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public final class QueryFilterTestUtil
{
	private QueryFilterTestUtil()
	{
	}

	public static <T extends IQueryFilter> T extractSingleFilter(
			@NonNull final ICompositeQueryFilter dbFilter,
			@NonNull final Class<T> classOfFilterToExtract)
	{
		final List<T> extractedFilters = extractFilters(dbFilter, classOfFilterToExtract);
		return ListUtils.singleElement(extractedFilters);
	}

	public static <T extends IQueryFilter> List<T> extractFilters(
			@NonNull final ICompositeQueryFilter dbFilter,
			@NonNull final Class<T> classOfFilterToExtract)
	{

		final Builder<T> builder = ImmutableList.builder();

		final List<IQueryFilter> includedFilters = dbFilter.getFilters();
		for (final IQueryFilter includedFilter : includedFilters)
		{
			T castedOrNull = castOrNull(includedFilter, classOfFilterToExtract);
			if (castedOrNull != null)
			{
				builder.add(castedOrNull);
			}
		}
		return builder.build();
	}

	public static <T extends IQueryFilter> T castOrNull(final IQueryFilter filter, final Class<T> clazz)
	{
		final boolean filterIsInstanceOfClass = clazz.isAssignableFrom(filter.getClass());
		if (!filterIsInstanceOfClass)
		{
			return null;
		}
		return (T)filter;
	}
}
