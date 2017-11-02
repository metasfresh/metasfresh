package org.adempiere.ad.dao.cache.impl;

import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.dao.cache.IModelCacheInvalidationService;
import org.adempiere.ad.dao.cache.IModelCacheService;
import org.adempiere.ad.dao.cache.ModelCacheInvalidateRequestFactory;
import org.adempiere.ad.dao.cache.ModelCacheInvalidationTiming;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.CacheMgt;
import org.slf4j.Logger;

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

	private final ConcurrentHashMap<String, ModelCacheInvalidateRequestFactory> requestFactoriesByTableName = new ConcurrentHashMap<>();
	private final ModelCacheInvalidateRequestFactory defaultRequestFactory = new DefaultModelCacheInvalidateRequestFactory();

	public ModelCacheInvalidationService()
	{
		// FIXME hardcoded
		register(I_C_OrderLine.Table_Name, new OrderLineModelCacheInvalidateRequestFactory());
	}

	public void register(@NonNull final String tableName, @NonNull final ModelCacheInvalidateRequestFactory requestFactory)
	{
		requestFactoriesByTableName.put(tableName, requestFactory);
		logger.info("Registered for {}: {}", tableName, requestFactory);
		
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(tableName);
	}

	@Override
	public void invalidate(@NonNull final CacheInvalidateRequest request, @NonNull final ModelCacheInvalidationTiming timing)
	{
		//
		// Reset model cache
		if (timing != ModelCacheInvalidationTiming.NEW)
		{
			final TableRecordReference record = request.getRecordEffective();
			final IModelCacheService modelCacheService = Services.get(IModelCacheService.class);
			modelCacheService.invalidate(record.getTableName(), record.getRecord_ID(), ITrx.TRXNAME_ThreadInherited);
		}

		//
		// Reset cache
		// NOTE: we need to do it even for newly created records because there are some aggregates which are cached (e.g. all lines for a given document),
		// so in case a new record pops in, those caches shall be reset..
		CacheMgt.get().resetOnTrxCommit(ITrx.TRXNAME_ThreadInherited, request);
	}

	@Override
	public CacheInvalidateRequest createRequest(final Object model, final ModelCacheInvalidationTiming timing)
	{
		final ModelCacheInvalidateRequestFactory requestFactory = getRequestFactoryForModel(model);
		final CacheInvalidateRequest request = requestFactory.createRequestFromModel(model, timing);
		return request;
	}

	private ModelCacheInvalidateRequestFactory getRequestFactoryForModel(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		return requestFactoriesByTableName.getOrDefault(tableName, defaultRequestFactory);
	}

	private static final class DefaultModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
	{
		@Override
		public CacheInvalidateRequest createRequestFromModel(final Object model, final ModelCacheInvalidationTiming timing)
		{
			// TODO: in case of DELETE, make sure the m_oldId is picked
			final String tableName = InterfaceWrapperHelper.getModelTableName(model);
			final int recordId = InterfaceWrapperHelper.getId(model);
			return CacheInvalidateRequest.rootRecord(tableName, recordId);
		}
	}

	private static final class OrderLineModelCacheInvalidateRequestFactory implements ModelCacheInvalidateRequestFactory
	{
		@Override
		public CacheInvalidateRequest createRequestFromModel(final Object model, final ModelCacheInvalidationTiming timing)
		{
			// TODO: in case of DELETE, make sure the m_oldId is picked
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(model, I_C_OrderLine.class);
			return CacheInvalidateRequest.builder()
					.rootRecord(I_C_Order.Table_Name, orderLine.getC_Order_ID())
					.childRecord(I_C_OrderLine.Table_Name, orderLine.getC_OrderLine_ID())
					.build();
		}
	}
}
