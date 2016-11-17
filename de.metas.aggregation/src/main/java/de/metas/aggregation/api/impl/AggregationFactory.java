package de.metas.aggregation.api.impl;

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


import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.aggregation.api.IAggregation;
import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.api.IAggregationKeyBuilder;

public class AggregationFactory implements IAggregationFactory
{
	/**
	 * Programatically registered default {@link IAggregationKeyBuilder}s.
	 *
	 * To create a a key for accessing them, please use {@link #mkDefaultAggregationKey(Class, String)}.
	 */
	private final Map<ArrayKey, IAggregationKeyBuilder<?>> defaultAggregationKeyBuilders = new ConcurrentHashMap<>();

	@Override
	public <ModelType> IAggregationKeyBuilder<ModelType> getAggregationKeyBuilder(final Properties ctx, final Class<ModelType> modelClass, final int aggregationId)
	{
		final IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);
		final IAggregation aggregation = aggregationDAO.retrieveAggregation(ctx, aggregationId);
		return createAggregationKeyBuilder(modelClass, aggregation);
	}

	@Override
	public <ModelType> IAggregationKeyBuilder<ModelType> getDefaultAggregationKeyBuilder(final Properties ctx, final Class<ModelType> modelClass, final Boolean isSOTrx,
			final String aggregationUsageLevel)
	{
		final IAggregationDAO aggregationDAO = Services.get(IAggregationDAO.class);

		//
		// Load it from database
		{
			final IAggregation aggregation = aggregationDAO.retrieveDefaultAggregationOrNull(ctx, modelClass, isSOTrx, aggregationUsageLevel);
			if (aggregation != null)
			{
				return createAggregationKeyBuilder(modelClass, aggregation);
			}
		}

		//
		// Check programmatically registered default
		{
			final ArrayKey key = mkDefaultAggregationKey(modelClass, aggregationUsageLevel);
			@SuppressWarnings("unchecked")
			final IAggregationKeyBuilder<ModelType> aggregationKeyBuilder = (IAggregationKeyBuilder<ModelType>)defaultAggregationKeyBuilders.get(key);
			if (aggregationKeyBuilder != null)
			{
				return aggregationKeyBuilder;
			}
		}

		throw new AdempiereException("@NotFound@ @C_Aggregation_ID@ (@IsDefault@, " + modelClass + ")");
	}

	@Override
	public <ModelType> void setDefaultAggregationKeyBuilder(
			final Class<? extends ModelType> modelClass,
			final String aggregationUsageLevel,
			final IAggregationKeyBuilder<ModelType> aggregationKeyBuilder)
	{
		Check.assumeNotNull(modelClass, "modelClass not null");
		Check.assumeNotEmpty(aggregationUsageLevel, "aggregationUsageLevel not empty");
		Check.assumeNotNull(aggregationKeyBuilder, "aggregationKeyBuilder not null");

		final ArrayKey key = mkDefaultAggregationKey(modelClass, aggregationUsageLevel);
		defaultAggregationKeyBuilders.put(key, aggregationKeyBuilder);
	}

	private final ArrayKey mkDefaultAggregationKey(final Class<?> modelClass, final String aggregationUsageLevel)
	{
		final String tableName = InterfaceWrapperHelper.getTableName(modelClass);
		return Util.mkKey(tableName, aggregationUsageLevel);
	}

	private final <ModelType> IAggregationKeyBuilder<ModelType> createAggregationKeyBuilder(final Class<ModelType> modelClass, final IAggregation aggregation)
	{
		final GenericAggregationKeyBuilder<ModelType> aggregationKeyBuilder = new GenericAggregationKeyBuilder<>(modelClass, aggregation);
		return aggregationKeyBuilder;
	}
}
