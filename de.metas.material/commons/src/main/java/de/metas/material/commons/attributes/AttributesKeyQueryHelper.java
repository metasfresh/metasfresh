package de.metas.material.commons.attributes;

import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.StringLikeFilter;
import org.adempiere.model.ModelColumn;

import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-commons
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

	/**
	 * @return an OR filter that matches any of the given {@code attributesKeys}.
	 */
	public IQueryFilter<T> createFilter(@NonNull final List<AttributesKey> attributesKeys)
	{
		final ICompositeQueryFilter<T> result = queryBL.createCompositeQueryFilter(clazz).setJoinOr();

		for (final AttributesKey attributesKey : attributesKeys)
		{
			final IQueryFilter<T> filter = createFilter(attributesKey, attributesKeys);
			if (filter != null)
			{
				result.addFilter(filter);
			}
		}

		return result;
	}

	private IQueryFilter<T> createFilter(
			@NonNull final AttributesKey attributesKey,
			@NonNull final List<AttributesKey> allAttributesKeys)
	{
		if (attributesKey.isOther())
		{
			return createOtherAttributesFilter(allAttributesKeys);
		}
		else if (attributesKey.isAll())
		{
			// nothing to add to the initial productIds filters
			return null;
		}
		else
		{
			final String likeExpression = createLikeExpression(attributesKey);
			final boolean ignoreCase = false;
			return new StringLikeFilter<>(modelColumn.getColumnName(), likeExpression, ignoreCase);
		}
	}

	private IQueryFilter<T> createOtherAttributesFilter(@NonNull final List<AttributesKey> attributesKeys)
	{
		ICompositeQueryFilter<T> filter = null;

		for (final AttributesKey attributeKey : attributesKeys)
		{
			if (attributeKey.isOther())
			{
				continue;
			}

			if (filter == null)
			{
				filter = queryBL.createCompositeQueryFilter(clazz).setJoinAnd();
			}

			final String likeExpression = createLikeExpression(attributeKey);
			filter.addStringNotLikeFilter(modelColumn, likeExpression, false);
		}

		return filter;
	}

	private static String createLikeExpression(@NonNull final AttributesKey attributesKey)
	{
		final String storageAttributesKeyLikeExpression = attributesKey.getSqlLikeString();
		return "%" + storageAttributesKeyLikeExpression + "%";
	}
}
