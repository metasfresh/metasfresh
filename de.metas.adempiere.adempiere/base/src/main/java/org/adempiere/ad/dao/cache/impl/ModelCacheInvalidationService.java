package org.adempiere.ad.dao.cache.impl;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.dao.cache.CacheInvalidateMultiRequest;
import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.dao.cache.DirectModelCacheInvalidateRequestFactory;
import org.adempiere.ad.dao.cache.IModelCacheInvalidationService;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ModelCacheInvalidateRequestFactory;
import org.adempiere.ad.dao.cache.ModelCacheInvalidationTiming;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.CacheMgt;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import de.metas.logging.LogManager;
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

public class ModelCacheInvalidationService implements IModelCacheInvalidationService
{
	private static final Logger logger = LogManager.getLogger(ModelCacheInvalidationService.class);

	private final SetMultimap<String, ModelCacheInvalidateRequestFactory> requestFactoriesByTableName = Multimaps.newSetMultimap(new ConcurrentHashMap<>(), ConcurrentHashMap::newKeySet);
	private final Set<ModelCacheInvalidateRequestFactory> defaultRequestFactories = ImmutableSet.of(DirectModelCacheInvalidateRequestFactory.instance);

	public ModelCacheInvalidationService()
	{
	}

	@Override
	public void register(@NonNull final String tableName, @NonNull final ModelCacheInvalidateRequestFactory requestFactory)
	{
		requestFactoriesByTableName.put(tableName, requestFactory);
		logger.info("Registered for {}: {}", tableName, requestFactory);

		CacheMgt.get().enableRemoteCacheInvalidationForTableName(tableName);
	}

	@Override
	public void invalidate(@NonNull final CacheInvalidateMultiRequest request, @NonNull final ModelCacheInvalidationTiming timing)
	{
		//
		// Reset model cache
		if (timing != ModelCacheInvalidationTiming.NEW)
		{
			final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
			request.getRecordsEffective()
					.forEach(record -> modelCacheService.invalidate(record.getTableName(), record.getRecord_ID(), ITrx.TRXNAME_ThreadInherited));
		}

		//
		// Reset cache
		// NOTE: we need to do it even for newly created records because there are some aggregates which are cached (e.g. all lines for a given document),
		// so in case a new record pops in, those caches shall be reset..
		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	@Override
	public CacheInvalidateMultiRequest createRequest(@NonNull final Object model, @NonNull final ModelCacheInvalidationTiming timing)
	{
		final Set<CacheInvalidateRequest> requests = getRequestFactoriesForModel(model)
				.stream()
				.map(requestFactory -> requestFactory.createRequestFromModel(model, timing))
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
		if (requests.isEmpty())
		{
			return null;
		}

		return CacheInvalidateMultiRequest.of(requests);
	}

	private Set<ModelCacheInvalidateRequestFactory> getRequestFactoriesForModel(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final Set<ModelCacheInvalidateRequestFactory> factories = requestFactoriesByTableName.get(tableName);
		return factories != null && !factories.isEmpty() ? factories : defaultRequestFactories;
	}
}
