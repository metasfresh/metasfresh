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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.proxy.Cached;

import com.google.common.annotations.VisibleForTesting;

import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.IAggregationDAO;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.cache.annotation.CacheCtx;
import de.metas.util.Check;
import de.metas.util.Services;

public class AggregationDAO implements IAggregationDAO
{
	@Override
	public <ModelType> Aggregation retrieveDefaultAggregationOrNull(
			final Properties ctx,
			final Class<ModelType> modelClass,
			final Boolean isSOTrx,
			final String aggregationUsageLevel)
	{
		final int tableId = InterfaceWrapperHelper.getTableId(modelClass);
		return retrieveDefaultAggregationOrNull(ctx, tableId, isSOTrx, aggregationUsageLevel);
	}

	@Cached(cacheName = I_C_Aggregation.Table_Name + "#AD_Table_ID#IsDefault=true", expireMinutes = 0)
	// public to make sure it's cached
	public <ModelType> Aggregation retrieveDefaultAggregationOrNull(
			@CacheCtx final Properties ctx,
			final int tableId,
			final Boolean isSOTrx,
			final String aggregationUsageLevel)
	{
		//
		// Get first matching C_Aggregation_ID
		final int aggregationId = retrieveDefaultAggregationQuery(ctx, tableId, isSOTrx, aggregationUsageLevel)
				.create()
				.firstIdOnly();
		if (aggregationId < 0)
		{
			return null;
		}

		//
		// Load and return the aggregation
		final Aggregation aggregation = retrieveAggregation(ctx, aggregationId);
		return aggregation;
	}

	@VisibleForTesting
	protected final IQueryBuilder<I_C_Aggregation> retrieveDefaultAggregationQuery(final Properties ctx, final int tableId, final Boolean isSOTrx, final String aggregationUsageLevel)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_Aggregation> queryBuilder = queryBL.createQueryBuilder(I_C_Aggregation.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Aggregation.COLUMN_AD_Table_ID, tableId);

		//
		// Defaults filter and ordering
		final ICompositeQueryFilter<I_C_Aggregation> defaultsFilter = queryBL.createCompositeQueryFilter(I_C_Aggregation.class)
				.setJoinOr()
				.addEqualsFilter(I_C_Aggregation.COLUMN_IsDefault, true);
		if (isSOTrx == null)
		{
			// no SO/PO filter
		}
		// SO default
		else if (isSOTrx)
		{
			defaultsFilter.addEqualsFilter(I_C_Aggregation.COLUMN_IsDefaultSO, true);
			queryBuilder.orderBy()
					.addColumn(I_C_Aggregation.COLUMN_IsDefaultSO, Direction.Descending, Nulls.Last);
		}
		// PO default
		else
		{
			defaultsFilter.addEqualsFilter(I_C_Aggregation.COLUMN_IsDefaultPO, true);
			queryBuilder.orderBy()
					.addColumn(I_C_Aggregation.COLUMN_IsDefaultPO, Direction.Descending, Nulls.Last);
		}

		// Aggregation Usage Level
		{
			queryBuilder.addEqualsFilter(I_C_Aggregation.COLUMN_AggregationUsageLevel, aggregationUsageLevel);
			queryBuilder.orderBy()
					.addColumn(I_C_Aggregation.COLUMN_AggregationUsageLevel, Direction.Descending, Nulls.Last);
		}

		// Standard predictable order
		queryBuilder.orderBy()
				.addColumn(I_C_Aggregation.COLUMN_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Aggregation.COLUMN_C_Aggregation_ID, Direction.Ascending, Nulls.Last);

		return queryBuilder
				.filter(defaultsFilter);
	}

	@Override
	public IQueryBuilder<I_C_AggregationItem> retrieveAllItemsQuery(final I_C_Aggregation aggregation)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_C_AggregationItem> queryBuilder = queryBL
				.createQueryBuilder(I_C_AggregationItem.class, aggregation)
				.addEqualsFilter(I_C_AggregationItem.COLUMN_C_Aggregation_ID, aggregation.getC_Aggregation_ID())
		// .addOnlyActiveRecordsFilter() // NOTE: we are retrieving all
		;

		// we want to have a predictable order
		queryBuilder.orderBy()
				.addColumn(I_C_AggregationItem.COLUMN_C_AggregationItem_ID);

		return queryBuilder;
	}

	List<I_C_AggregationItem> retrieveAllItems(final I_C_Aggregation aggregation)
	{
		return retrieveAllItemsQuery(aggregation)
				.create()
				.list();
	}

	@Override
	@Cached(cacheName = I_C_Aggregation.Table_Name + "#IAggregation", expireMinutes = 0)
	public Aggregation retrieveAggregation(@CacheCtx final Properties ctx, final int aggregationId)
	{
		//
		// Load aggregation definition
		Check.assume(aggregationId > 0, "aggregationId > 0");
		final I_C_Aggregation aggregationDef = InterfaceWrapperHelper.create(ctx, aggregationId, I_C_Aggregation.class, ITrx.TRXNAME_None);
		if (aggregationDef == null)
		{
			throw new AdempiereException("@NotFound@ @C_Aggregation_ID@ (ID=" + aggregationId + ")");
		}

		return new C_Aggregation2AggregationBuilder(this)
				.setC_Aggregation(aggregationDef)
				.build();
	}

	@Override
	public void checkIncludedAggregationCycles(final I_C_AggregationItem aggregationItemDef)
	{
		final Map<Integer, I_C_Aggregation> trace = new LinkedHashMap<>();
		checkIncludedAggregationCycles(aggregationItemDef, trace);
	}

	private final void checkIncludedAggregationCycles(final I_C_AggregationItem aggregationItemDef, final Map<Integer, I_C_Aggregation> trace)
	{
		Check.assumeNotNull(aggregationItemDef, "aggregationItemDef not null");

		final String itemType = aggregationItemDef.getType();
		if (!X_C_AggregationItem.TYPE_IncludedAggregation.equals(itemType))
		{
			return;
		}

		final int includedAggregationId = aggregationItemDef.getIncluded_Aggregation_ID();
		if (includedAggregationId <= 0)
		{
			return;
		}

		if (trace.containsKey(includedAggregationId))
		{
			throw new AdempiereException("Cycle detected: " + trace.values());
		}
		final I_C_Aggregation includedAggregationDef = aggregationItemDef.getC_Aggregation();
		trace.put(includedAggregationId, includedAggregationDef);

		final List<I_C_AggregationItem> includedAggregationItemsDef = retrieveAllItems(includedAggregationDef);
		for (final I_C_AggregationItem includedAggregationItemDef : includedAggregationItemsDef)
		{
			checkIncludedAggregationCycles(includedAggregationItemDef, trace);
		}
	}
}
