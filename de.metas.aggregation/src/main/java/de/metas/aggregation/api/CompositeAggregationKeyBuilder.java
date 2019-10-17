package de.metas.aggregation.api;

/*
 * #%L
 * de.metas.aggregation
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;

import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;

public class CompositeAggregationKeyBuilder<ModelType> implements IAggregationKeyBuilder<ModelType>
{
	private final String tableName;
	private final List<String> columnNames = new ArrayList<>();
	private final List<String> columnNamesRO = Collections.unmodifiableList(columnNames);

	private final CopyOnWriteArrayList<IAggregationKeyBuilder<ModelType>> aggregationKeyBuilders = new CopyOnWriteArrayList<>();

	public CompositeAggregationKeyBuilder(final Class<ModelType> modelClass)
	{
		super();

		this.tableName = InterfaceWrapperHelper.getTableName(modelClass);
	}

	public CompositeAggregationKeyBuilder<ModelType> addAggregationKeyBuilder(final IAggregationKeyBuilder<ModelType> aggregationKeyBuilder)
	{
		Check.assumeNotNull(aggregationKeyBuilder, "aggregationKeyBuilder not null");
		if (!aggregationKeyBuilders.addIfAbsent(aggregationKeyBuilder))
		{
			return this;
		}

		for (final String columnName : aggregationKeyBuilder.getDependsOnColumnNames())
		{
			if (columnNames.contains(columnName))
			{
				continue;
			}
			columnNames.add(columnName);
		}

		return this;
	}

	@Override
	public String getTableName()
	{
		return tableName;
	}

	@Override
	public List<String> getDependsOnColumnNames()
	{
		return columnNamesRO;
	}

	@Override
	public String buildKey(final ModelType model)
	{
		return buildAggregationKey(model)
				.getAggregationKeyString();
	}

	@Override
	public AggregationKey buildAggregationKey(final ModelType model)
	{
		if (aggregationKeyBuilders.isEmpty())
		{
			throw new AdempiereException("No aggregation key builders added");
		}

		final List<String> keyParts = new ArrayList<>();
		final Set<AggregationId> aggregationIds = new HashSet<>();
		for (final IAggregationKeyBuilder<ModelType> aggregationKeyBuilder : aggregationKeyBuilders)
		{
			final AggregationKey keyPart = aggregationKeyBuilder.buildAggregationKey(model);
			keyParts.add(keyPart.getAggregationKeyString());
			aggregationIds.add(keyPart.getAggregationId());
		}

		final AggregationId aggregationId = CollectionUtils.singleElementOrDefault(aggregationIds, null);
		return new AggregationKey(Util.mkKey(keyParts.toArray()), aggregationId);
	}

	@Override
	public boolean isSame(final ModelType model1, final ModelType model2)
	{
		if (model1 == model2)
		{
			return true;
		}
		final String key1 = buildKey(model1);
		final String key2 = buildKey(model2);

		return Objects.equals(key1, key2);
	}
}
