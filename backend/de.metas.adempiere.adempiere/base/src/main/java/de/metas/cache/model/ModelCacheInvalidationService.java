package de.metas.cache.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

/**
 * Service responsible for invalidating model caches.
 */
@Service
public class ModelCacheInvalidationService
{
	public static ModelCacheInvalidationService get()
	{
		if (Adempiere.isUnitTestMode())
		{
			final ModelCacheInvalidationService instance = SpringContextHolder.instance.getBeanOr(ModelCacheInvalidationService.class, null);
			if (instance != null)
			{
				return instance;
			}

			logger.warn("ModelCacheInvalidationService.get() called -> returning newInstanceForUnitTesting()");
			return newInstanceForUnitTesting();
		}

		return SpringContextHolder.instance.getBean(ModelCacheInvalidationService.class);
	}

	public static ModelCacheInvalidationService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new ModelCacheInvalidationService(Optional.empty());
	}

	private static final Logger logger = LogManager.getLogger(ModelCacheInvalidationService.class);
	private final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);

	private static final ImmutableSet<ModelCacheInvalidateRequestFactory> DEFAULT_REQUEST_FACTORIES = ImmutableSet.of(DirectModelCacheInvalidateRequestFactory.instance);

	private final ImmutableList<IModelCacheInvalidateRequestFactoryGroup> factoryGroups;

	public ModelCacheInvalidationService(final Optional<List<IModelCacheInvalidateRequestFactoryGroup>> factoryGroups)
	{
		this.factoryGroups = factoryGroups.map(ImmutableList::copyOf).orElseGet(ImmutableList::of);

		final CacheMgt cacheMgt = CacheMgt.get();
		this.factoryGroups.forEach(factoryGroup -> cacheMgt.enableRemoteCacheInvalidationForTableNamesGroup(factoryGroup.getTableNamesToEnableRemoveCacheInvalidation()));

		logger.info("Registered {}", this.factoryGroups); // calling it last to make sure cache was warmed up, so we have more info to show
	}

	public void invalidateForModel(@NonNull final ICacheSourceModel model, @NonNull final ModelCacheInvalidationTiming timing)
	{
		final CacheInvalidateMultiRequest request = createRequestOrNull(model, timing);
		if (request == null)
		{
			return;
		}

		invalidate(request, timing);
	}

	public void invalidate(@NonNull final CacheInvalidateMultiRequest request, @NonNull final ModelCacheInvalidationTiming timing)
	{
		//
		// Reset model cache
		// * only on AFTER event
		// * only if it's not NEW event because in case of NEW, the model was not already in cache, for sure
		if (timing.isAfter() && !timing.isNew())
		{
			modelCacheService.invalidate(request);
		}

		//
		// Reset cache
		// NOTE: we need to do it even for newly created records because there are some aggregates which are cached (e.g. all lines for a given document),
		// so in case a new record pops in, those caches shall be reset..
		CacheMgt.get().resetLocalNowAndBroadcastOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	public CacheInvalidateMultiRequest createRequestOrNull(
			@NonNull final ICacheSourceModel model,
			@NonNull final ModelCacheInvalidationTiming timing)
	{
		final String tableName = model.getTableName();

		final HashSet<CacheInvalidateRequest> requests = getRequestFactoriesByTableName(tableName, timing)
				.stream()
				.map(requestFactory -> requestFactory.createRequestsFromModel(model, timing))
				.filter(Objects::nonNull)
				.flatMap(List::stream)
				.filter(Objects::nonNull)
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

	private Set<ModelCacheInvalidateRequestFactory> getRequestFactoriesByTableName(@NonNull final String tableName, @NonNull final ModelCacheInvalidationTiming timing)
	{
		final Set<ModelCacheInvalidateRequestFactory> factories = factoryGroups.stream()
				.flatMap(factoryGroup -> factoryGroup.getFactoriesByTableName(tableName, timing).stream())
				.collect(ImmutableSet.toImmutableSet());
		return factories != null && !factories.isEmpty() ? factories : DEFAULT_REQUEST_FACTORIES;
	}
}
