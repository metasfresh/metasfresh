package de.metas.cache.model.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.DirectModelCacheInvalidateRequestFactory;
import de.metas.cache.model.IModelCacheInvalidationService;
import de.metas.cache.model.IModelCacheService;
import de.metas.cache.model.ModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.logging.LogManager;
import de.metas.util.Services;
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
			modelCacheService.invalidate(request);
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
				.collect(Collectors.toCollection(HashSet::new));

		//
		final CacheInvalidateRequest request = createChildRecordInvalidateRequestUsingDynAttr(model);
		if (request != null)
		{
			requests.add(request);
		}

		//
		if (requests.isEmpty())
		{
			return null;
		}

		return CacheInvalidateMultiRequest.of(requests);
	}

	private CacheInvalidateRequest createChildRecordInvalidateRequestUsingDynAttr(final Object model)
	{
		final TableRecordReference rootRecordReference = ATTR_RootRecordReference.getValue(model);
		if (rootRecordReference == null)
		{
			return null;
		}

		final String modelTableName = InterfaceWrapperHelper.getModelTableName(model);
		final int modelRecordId = InterfaceWrapperHelper.getId(model);

		return CacheInvalidateRequest.builder()
				.rootRecord(rootRecordReference.getTableName(), rootRecordReference.getRecord_ID())
				.childRecord(modelTableName, modelRecordId)
				.build();
	}

	private Set<ModelCacheInvalidateRequestFactory> getRequestFactoriesForModel(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final Set<ModelCacheInvalidateRequestFactory> factories = requestFactoriesByTableName.get(tableName);
		return factories != null && !factories.isEmpty() ? factories : defaultRequestFactories;
	}
}
