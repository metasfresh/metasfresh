package de.metas.ui.web.window.model.lookup;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.logging.LogManager;
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

public class LocallyCachedLookupDataSource implements LookupDataSource
{
	public static final LocallyCachedLookupDataSource of(final LookupDataSourceFetcher fetcher)
	{
		return new LocallyCachedLookupDataSource(fetcher);
	}

	private static final Logger logger = LogManager.getLogger(LocallyCachedLookupDataSource.class);

	private final transient LoadingCache<LookupDataSourceContext, LookupValuesList> cacheByPartition = CacheBuilder.newBuilder().build(new CacheLoader<LookupDataSourceContext, LookupValuesList>()
	{
		@Override
		public LookupValuesList load(final LookupDataSourceContext evalCtx) throws Exception
		{
			return fetcher.retrieveEntities(evalCtx);
		}
	});

	private final transient LoadingCache<LookupDataSourceContext, LookupValue> cacheById = CacheBuilder.newBuilder().build(new CacheLoader<LookupDataSourceContext, LookupValue>()
	{
		@Override
		public LookupValue load(final LookupDataSourceContext evalCtx) throws Exception
		{
			return fetcher.retrieveLookupValueById(evalCtx);
		}
	});

	private final LookupDataSourceFetcher fetcher;

	private LocallyCachedLookupDataSource(final LookupDataSourceFetcher fetcher)
	{
		super();
		Check.assumeNotNull(fetcher, "Parameter fetcher is not null");
		this.fetcher = fetcher;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(fetcher)
				.toString();
	}

	@Override
	public boolean isNumericKey()
	{
		return fetcher.isNumericKey();
	}

	@Override
	public boolean setStaled(final String triggeringFieldName)
	{
		logger.trace("Marking {} as staled (triggeringFieldName={})", this, triggeringFieldName);

		cacheByPartition.asMap()
				.keySet()
				.stream()
				.filter(evalCtx -> evalCtx.has_Variable(triggeringFieldName))
				.forEach((evalCtx) -> cacheByPartition.invalidate(evalCtx));

		// TODO implement staled logic
		return true;
	}

	public void invalidateById(final Object idObj)
	{
		final Object idNormalized = LookupValue.normalizeId(idObj, fetcher.isNumericKey());
		if (idNormalized == null)
		{
			return;
		}

		//
		// We shall invalidating all partitions (and not only those which contain given key),
		// because it might be that some of them will contain the new ID on re-query
		cacheByPartition.invalidateAll();

		cacheById.asMap()
				.entrySet()
				.stream()
				.filter(evalCtxAndValues -> Objects.equals(idNormalized, evalCtxAndValues.getValue().getId()))
				.map(evalCtxAndValues -> evalCtxAndValues.getKey())
				.forEach((evalCtx) -> cacheById.invalidate(evalCtx));
	}

	@Override
	public boolean isStaled()
	{
		// TODO implement staled logic
		return false;
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

		try
		{
			return cacheByPartition.get(evalCtx);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
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
		try
		{
			final LookupValue lookupValue = cacheById.get(evalCtx);
			if (lookupValue == LookupDataSourceFetcher.LOOKUPVALUE_NULL)
			{
				return null;
			}
			return lookupValue;
		}
		catch (final ExecutionException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
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
	public LocallyCachedLookupDataSource copy()
	{
		// TODO: atm this is immutable so we can return it right away. But pls change it when we will add caching here
		return this;
	}
}
