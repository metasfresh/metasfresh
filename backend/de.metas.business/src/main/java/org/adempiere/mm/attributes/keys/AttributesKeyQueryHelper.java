/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import com.google.common.collect.ImmutableList;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.model.ModelColumn;

import java.util.ArrayList;
import java.util.List;

public class AttributesKeyQueryHelper<T>
{
	/**
	 * @param modelColumn the attributesKey column to build the composite filter around.
	 * @return an instance that can create a composite filter for attributes keys.
	 */
	public static <T> AttributesKeyQueryHelper<T> createFor(@NonNull final ModelColumn<T, Object> modelColumn)
	{
		return new AttributesKeyQueryHelper<>(modelColumn);
	}

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final Class<T> clazz;
	private final ModelColumn<T, Object> modelColumn;

	private AttributesKeyQueryHelper(@NonNull final ModelColumn<T, Object> modelColumn)
	{
		this.clazz = modelColumn.getModelClass();
		this.modelColumn = modelColumn;
	}

	public IQueryFilter<T> createFilter(@NonNull final AttributesKeyPattern attributesKeyPattern)
	{
		return createFilter(ImmutableList.of(attributesKeyPattern));
	}

	/**
	 * @return an OR filter that matches any of the given {@code attributesKeys}.
	 */
	public IQueryFilter<T> createFilter(@NonNull final List<AttributesKeyPattern> attributesKeyPatterns)
	{
		Check.assumeNotEmpty(attributesKeyPatterns, "attributesKeyPatterns is not empty");

		if (attributesKeyPatterns.contains(AttributesKeyPattern.ALL))
		{
			return ConstantQueryFilter.of(true);
		}

		if (attributesKeyPatterns.contains(AttributesKeyPattern.OTHER))
		{
			return ConstantQueryFilter.of(true);
		}

		final ArrayList<IQueryFilter<T>> filters = new ArrayList<>();

		for (final AttributesKeyPattern attributesKeyPattern : attributesKeyPatterns)
		{
			final String likeExpression = attributesKeyPattern.getSqlLikeString();
			final boolean ignoreCase = false;
			final StringLikeFilter<T> filter = new StringLikeFilter<>(modelColumn.getColumnName(), likeExpression, ignoreCase);

			filters.add(filter);
		}

		if (filters.isEmpty())
		{
			return ConstantQueryFilter.of(true);
		}
		else if (filters.size() == 1)
		{
			return filters.get(0);
		}
		else
		{
			return queryBL.createCompositeQueryFilter(clazz)
					.setJoinOr()
					.addFilters(filters);
		}
	}
}
