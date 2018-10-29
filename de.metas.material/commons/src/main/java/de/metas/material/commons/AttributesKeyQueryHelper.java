package de.metas.material.commons;

import lombok.NonNull;

import java.util.List;
import java.util.Objects;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.model.ModelColumn;

import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;

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
	public ICompositeQueryFilter<T> createORFilterForStorageAttributesKeys(@NonNull final List<AttributesKey> attributesKeys)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<T> orFilterForDifferentStorageAttributesKeys = queryBL
				.createCompositeQueryFilter(clazz)
				.setJoinOr();

		for (final AttributesKey attributesKey : attributesKeys)
		{
			final IQueryFilter<T> andFilterForCurrentStorageAttributesKey = createANDFilterForStorageAttributesKey(attributesKeys, attributesKey);
			orFilterForDifferentStorageAttributesKeys.addFilter(andFilterForCurrentStorageAttributesKey);
		}

		return orFilterForDifferentStorageAttributesKeys;
	}

	private ICompositeQueryFilter<T> createANDFilterForStorageAttributesKey(
			@NonNull final List<AttributesKey> allAttributesKeys,
			@NonNull final AttributesKey attributesKey)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<T> filterForCurrentStorageAttributesKey = queryBL.createCompositeQueryFilter(clazz)
				.setJoinAnd();

		if (Objects.equals(attributesKey, AttributesKey.OTHER))
		{
			addNotLikeFiltersForAttributesKeys(filterForCurrentStorageAttributesKey, allAttributesKeys);
		}
		else if (Objects.equals(attributesKey, AttributesKey.ALL))
		{
			// nothing to add to the initial productIds filters
		}
		else
		{
			addLikeFilterForAttributesKey(attributesKey, filterForCurrentStorageAttributesKey);
		}

		return filterForCurrentStorageAttributesKey;
	}

	private void addNotLikeFiltersForAttributesKeys(
			@NonNull final ICompositeQueryFilter<T> compositeFilter,
			@NonNull final List<AttributesKey> attributesKeys)
	{
		for (final AttributesKey storageAttributesKeyAgain : attributesKeys)
		{
			if (!Objects.equals(storageAttributesKeyAgain, AttributesKey.OTHER))
			{
				final String likeExpression = createLikeExpression(storageAttributesKeyAgain);
				compositeFilter.addStringNotLikeFilter(modelColumn, likeExpression, false);
			}
		}
	}

	private void addLikeFilterForAttributesKey(
			final AttributesKey attributesKey,
			final ICompositeQueryFilter<T> andFilterForCurrentStorageAttributesKey)
	{
		final String likeExpression = createLikeExpression(attributesKey);
		andFilterForCurrentStorageAttributesKey.addStringLikeFilter(modelColumn, likeExpression, false);
	}

	private static String createLikeExpression(@NonNull final AttributesKey attributesKey)
	{
		final String storageAttributesKeyLikeExpression = attributesKey.getSqlLikeString();
		return "%" + storageAttributesKeyLikeExpression + "%";
	}
}
