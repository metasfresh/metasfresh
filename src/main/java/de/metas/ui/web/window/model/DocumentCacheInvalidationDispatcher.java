package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import de.metas.cache.CacheMgt;
import de.metas.cache.ICacheResetListener;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
 * This component listens to all cache invalidation events (see {@link CacheMgt}) and invalidates the right documents or included documents from {@link DocumentCollection}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class DocumentCacheInvalidationDispatcher implements ICacheResetListener
{
	private static final Logger logger = LogManager.getLogger(DocumentCacheInvalidationDispatcher.class);

	private static final String TRXPROP_Requests = DocumentCacheInvalidationDispatcher.class + ".CacheInvalidateMultiRequests";

	@Autowired
	private DocumentCollection documents;

	@Autowired
	private IViewsRepository viewsRepository;

	private final Executor async;

	public DocumentCacheInvalidationDispatcher()
	{
		final CustomizableThreadFactory asyncThreadFactory = new CustomizableThreadFactory(DocumentCacheInvalidationDispatcher.class.getSimpleName());
		asyncThreadFactory.setDaemon(true);

		async = Executors.newSingleThreadExecutor(asyncThreadFactory);
	}

	@PostConstruct
	private void postConstruct()
	{
		CacheMgt.get().addCacheResetListener(this);
	}

	@Override
	public long reset(@NonNull final CacheInvalidateMultiRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isActive(currentTrx))
		{
			final CacheInvalidateMultiRequestsCollector collector = currentTrx.getPropertyAndProcessAfterCommit(
					TRXPROP_Requests,
					() -> new CacheInvalidateMultiRequestsCollector(currentTrx.getTrxName()),
					CacheInvalidateMultiRequestsCollector::resetAsync);

			collector.collect(request);
		}
		else
		{
			resetAsync(request);
		}

		return 1; // not relevant
	}

	private void resetAsync(@Nullable final CacheInvalidateMultiRequest request)
	{
		if (request == null)
		{
			return;
		}

		logger.trace("resetAsync: {}", request);
		async.execute(() -> resetNow(request));
	}

	private void resetNow(@NonNull final CacheInvalidateMultiRequest request)
	{
		logger.trace("resetNow: {}", request);

		try (final IAutoCloseable c = documents.getWebsocketPublisher().temporaryCollectOnThisThread())
		{
			request.getRequests().forEach(this::resetDocumentNow);
		}

		//
		final TableRecordReferenceSet rootRecords = request.getRootRecords();
		viewsRepository.notifyRecordsChanged(rootRecords);
	}

	private void resetDocumentNow(@NonNull final CacheInvalidateRequest request)
	{
		logger.debug("resetDocumentNow: {}", request);

		final String rootTableName = request.getRootTableName();
		if (rootTableName == null)
		{
			logger.debug("Nothing to do, no rootTableName: {}", request);
			return;
		}

		final int rootRecordId = request.getRootRecordId();
		if (rootRecordId < 0)
		{
			logger.debug("Nothing to do, rootRecordId < 0: {}", request);
			return;
		}

		final String childTableName = request.getChildTableName();
		if (childTableName == null)
		{
			logger.debug("Invalidating the root document: {}", request);
			documents.invalidateDocumentByRecordId(rootTableName, rootRecordId);
		}
		else
		{
			logger.debug("Invalidating the included document: {}", request);
			final int childRecordId = request.getChildRecordId();
			documents.invalidateIncludedDocumentsByRecordId(rootTableName, rootRecordId, childTableName, childRecordId);

			// NOTE: as a workaround to solve the problem of https://github.com/metasfresh/metasfresh-webui-api/issues/851,
			// we are invalidating the whole root document to make sure that in case there were any virtual columns on header,
			// those get refreshed too.
			documents.invalidateDocumentByRecordId(rootTableName, rootRecordId);
		}
	}

	private final class CacheInvalidateMultiRequestsCollector
	{
		private final String name; // used for debugging
		private List<CacheInvalidateMultiRequest> multiRequests = new ArrayList<>();

		private CacheInvalidateMultiRequestsCollector(final String name)
		{
			this.name = name;
		}

		public void collect(@NonNull final CacheInvalidateMultiRequest multiRequest)
		{
			logger.trace("Collecting request on `{}`: {}", name, multiRequest);
			multiRequests.add(multiRequest);
		}

		public void resetAsync()
		{
			final List<CacheInvalidateMultiRequest> multiRequests = this.multiRequests;
			this.multiRequests = null; // just to prevent adding more events

			logger.trace("Flushing {} collected requests for on `{}`", multiRequests.size(), name);

			if (multiRequests.isEmpty())
			{
				return;
			}

			final CacheInvalidateMultiRequest multiRequestEffective = CacheInvalidateMultiRequest.ofMultiRequests(multiRequests);
			DocumentCacheInvalidationDispatcher.this.resetAsync(multiRequestEffective);
		}
	}
}
