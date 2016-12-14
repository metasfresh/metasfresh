package de.metas.ui.web.window.model.lookup;

import java.util.List;
import java.util.function.Predicate;

import org.adempiere.util.Check;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;

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

class FullyCachedLookupDataSource implements LookupDataSource
{
	public static final FullyCachedLookupDataSource of(final LookupDataSourceFetcher fetcher)
	{
		return new FullyCachedLookupDataSource(fetcher);
	}

	private static final String NAME = "OnePartition";

	private final LookupDataSourceFetcher fetcher;

	private final transient CCache<LookupDataSourceContext, LookupValuesList> cacheByPartition;

	private FullyCachedLookupDataSource(final LookupDataSourceFetcher fetcher)
	{
		super();
		Check.assumeNotNull(fetcher, "Parameter fetcher is not null");
		this.fetcher = fetcher;

		final String cachePrefix = fetcher.getCachePrefix();
		Check.assumeNotEmpty(cachePrefix, "cachePrefix is not empty");
		final int maxSize = 100;
		final int expireAfterMinutes = 60 * 2;
		cacheByPartition = CCache.newLRUCache(cachePrefix + "#" + NAME + "#LookupByPartition", maxSize, expireAfterMinutes);
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("fetcher", fetcher)
				.toString();
	}

	private LookupValuesList getLookupValuesList(final Evaluatee parentEvaluatee)
	{
		final LookupDataSourceContext evalCtx = fetcher.newContextForFetchingList()
				.setParentEvaluatee(parentEvaluatee)
				.putFilter(LookupDataSourceContext.FILTER_Any, FIRST_ROW, Integer.MAX_VALUE)
				.build();

		return cacheByPartition.getOrLoad(evalCtx, () -> fetcher.retrieveEntities(evalCtx));
	}

	@Override
	public LookupValuesList findEntities(final Evaluatee ctx, final String filter, final int firstRow, final int pageLength)
	{
		final LookupValuesList partition = getLookupValuesList(ctx);
		if (partition.isEmpty())
		{
			return partition;
		}

		final Predicate<LookupValue> filterPredicate = FilterPredicate.of(filter);
		if(filterPredicate == FilterPredicate.MATCH_ALL)
		{
			return partition.offsetAndLimit(firstRow, pageLength);
		}

		return partition.filter(filterPredicate, firstRow, pageLength);
	}

	@Override
	public LookupValuesList findEntities(final Evaluatee ctx, final int pageLength)
	{
		return getLookupValuesList(ctx).limit(pageLength);
	}

	@Override
	public LookupValue findById(final Object idObj)
	{
		final Object idNormalized = LookupValue.normalizeId(idObj, fetcher.isNumericKey());
		if (idNormalized == null)
		{
			return null;
		}

		final LookupValuesList partition = getLookupValuesList(Evaluatees.empty());
		return partition.getById(idNormalized);
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(cacheByPartition.stats());
	}

	private static final class FilterPredicate implements Predicate<LookupValue>
	{
		public static final Predicate<LookupValue> of(final String filter)
		{
			if (filter == null)
			{
				return MATCH_ALL;
			}

			final String filterNorm = filter.trim();
			if (filterNorm.isEmpty())
			{
				return MATCH_ALL;
			}

			return new FilterPredicate(filterNorm);
		}

		private static final Predicate<LookupValue> MATCH_ALL = new Predicate<LookupValue>()
		{
			@Override
			public String toString()
			{
				return "MatchAll";
			};

			@Override
			public boolean test(final LookupValue lookupValue)
			{
				return true;
			}
		};

		private final String filterLC;

		private FilterPredicate(final String filter)
		{
			super();
			filterLC = filter.toLowerCase();
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(filterLC)
					.toString();
		}

		@Override
		public boolean test(final LookupValue lookupValue)
		{
			if (lookupValue == null)
			{
				return false;
			}

			final String displayName = lookupValue.getDisplayName();
			if (displayName == null)
			{
				return false;
			}

			final String displayNameLC = displayName.toLowerCase();

			return displayNameLC.indexOf(filterLC) >= 0;
		}
	}
}
