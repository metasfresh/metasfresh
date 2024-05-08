package de.metas.cache.model.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CacheMgt;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.DirectModelCacheInvalidateRequestFactory;
import de.metas.cache.model.ICacheSourceModel;
import de.metas.cache.model.IModelCacheInvalidateRequestFactoryGroup;
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
	private final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);

	private static final ImmutableSet<ModelCacheInvalidateRequestFactory> DEFAULT_REQUEST_FACTORIES = ImmutableSet.of(DirectModelCacheInvalidateRequestFactory.instance);

	private final CopyOnWriteArrayList<IModelCacheInvalidateRequestFactoryGroup> factoryGroups = new CopyOnWriteArrayList<>();

	@Override
	public void registerFactoryGroup(@NonNull final IModelCacheInvalidateRequestFactoryGroup factoryGroup)
	{
		factoryGroups.add(factoryGroup);
		logger.info("Registered {}", factoryGroup);

		CacheMgt.get().enableRemoteCacheInvalidationForTableNamesGroup(factoryGroup.getTableNamesToEnableRemoveCacheInvalidation());
	}

	@Override
	public void invalidate(@NonNull final CacheInvalidateMultiRequest request, @NonNull final ModelCacheInvalidationTiming timing)
	{
		//
		// Reset model cache
		if (timing != ModelCacheInvalidationTiming.NEW)
		{
			modelCacheService.invalidate(request);
		}

		//
		// Reset cache
		// NOTE: we need to do it even for newly created records because there are some aggregates which are cached (e.g. all lines for a given document),
		// so in case a new record pops in, those caches shall be reset..
		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	@Override
	public CacheInvalidateMultiRequest createRequestOrNull(
			@NonNull final ICacheSourceModel model,
			@NonNull final ModelCacheInvalidationTiming timing)
	{
		final String tableName = model.getTableName();

		final HashSet<CacheInvalidateRequest> requests = getRequestFactoriesByTableName(tableName)
				.stream()
				.map(requestFactory -> requestFactory.createRequestsFromModel(model, timing))
				.filter(Predicates.notNull())
				.flatMap(List::stream)
				.filter(Predicates.notNull())
				.collect(Collectors.toCollection(HashSet::new));

		//
		final CacheInvalidateRequest request = createChildRecordInvalidateUsingRootRecordReference(model);
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

	private CacheInvalidateRequest createChildRecordInvalidateUsingRootRecordReference(final ICacheSourceModel model)
	{
		final TableRecordReference rootRecordReference = model.getRootRecordReferenceOrNull();
		if (rootRecordReference == null)
		{
			return null;
		}

		final String modelTableName = model.getTableName();
		final int modelRecordId = model.getRecordId();

		return CacheInvalidateRequest.builder()
				.rootRecord(rootRecordReference.getTableName(), rootRecordReference.getRecord_ID())
				.childRecord(modelTableName, modelRecordId)
				.build();
	}

	private Set<ModelCacheInvalidateRequestFactory> getRequestFactoriesByTableName(@NonNull final String tableName)
	{
		final Set<ModelCacheInvalidateRequestFactory> factories = factoryGroups.stream()
				.flatMap(factoryGroup -> factoryGroup.getFactoriesByTableName(tableName).stream())
				.collect(ImmutableSet.toImmutableSet());
		return factories != null && !factories.isEmpty() ? factories : DEFAULT_REQUEST_FACTORIES;
	}
}
