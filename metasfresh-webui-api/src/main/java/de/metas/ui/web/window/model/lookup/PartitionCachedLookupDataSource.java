package de.metas.ui.web.window.model.lookup;

import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Evaluatee;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

class PartitionCachedLookupDataSource implements LookupDataSource
{
	public static final PartitionCachedLookupDataSource of(final LookupDataSourceFetcher fetcher)
	{
		return new PartitionCachedLookupDataSource(fetcher);
	}

	private static final String NAME = "PerPartition";

	private final transient CCache<LookupDataSourceContext, LookupValuesList> cacheByPartition;
	private final transient CCache<LookupDataSourceContext, LookupValue> cacheByKey;
	private final LookupDataSourceFetcher fetcher;

	private PartitionCachedLookupDataSource(final LookupDataSourceFetcher fetcher)
	{
		super();
		Check.assumeNotNull(fetcher, "Parameter fetcher is not null");
		this.fetcher = fetcher;

		final String cachePrefix = fetcher.getCachePrefix();
		Check.assumeNotEmpty(cachePrefix, "cachePrefix is not empty");
		final int maxSize = 100;
		final int expireAfterMinutes = 60 * 2;
		// NOTE: it's very important to have the lookupTableName as cache name prefix because we want the cache invalidation to happen for this table
		cacheByPartition = CCache.newLRUCache(cachePrefix + "#" + NAME + "#LookupByPartition", maxSize, expireAfterMinutes);
		cacheByKey = CCache.newLRUCache(cachePrefix + "#" + NAME + "#LookupByKey", maxSize, expireAfterMinutes);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(fetcher)
				.toString();
	}

	@Override
	public final LookupValuesList findEntities(final Evaluatee ctx, final int pageLength)
	{
		return findEntities(ctx, LookupDataSourceContext.FILTER_Any, FIRST_ROW, pageLength);
	}

	@Override
	public LookupValuesList findEntities(final Evaluatee ctx, final String filter, final int firstRow, final int pageLength)
	{
		if (!isValidFilter(filter))
		{
			throw new IllegalArgumentException("Invalid filter: " + filter);
		}

		final LookupDataSourceContext evalCtx = fetcher.newContextForFetchingList()
				.setParentEvaluatee(ctx)
				.putFilter(filter, firstRow, pageLength)
				.build();

		return cacheByPartition.getOrLoad(evalCtx, () -> fetcher.retrieveEntities(evalCtx));
	}

	@Override
	public LookupValue findById(final Object idObj)
	{
		if (idObj == null)
		{
			return null;
		}

		//
		// Normalize the ID to Integer/String
		final Object idNormalized = LookupValue.normalizeId(idObj, fetcher.isNumericKey());
		if (idNormalized == null)
		{
			return null;
		}

		//
		// Build the validation context
		final LookupDataSourceContext evalCtx = fetcher.newContextForFetchingById(idNormalized)
				.putShowInactive(true)
				.build();

		//
		// Get the lookup value
		final LookupValue lookupValue = cacheByKey.getOrLoad(evalCtx, () -> fetcher.retrieveLookupValueById(evalCtx));
		if (lookupValue == LookupDataSourceFetcher.LOOKUPVALUE_NULL)
		{
			return null;
		}
		return lookupValue;
	}

	private boolean isValidFilter(final String filter)
	{
		if (Check.isEmpty(filter, true))
		{
			return false;
		}

		if (filter == LookupDataSourceContext.FILTER_Any)
		{
			return true;
		}

		return true;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(cacheByPartition.stats(), cacheByKey.stats());
	}
}
