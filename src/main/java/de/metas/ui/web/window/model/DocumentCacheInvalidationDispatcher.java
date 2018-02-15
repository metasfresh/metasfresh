package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.adempiere.ad.dao.cache.CacheInvalidateMultiRequest;
import org.adempiere.ad.dao.cache.CacheInvalidateRequest;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.CacheMgt;
import org.compiere.util.ICacheResetListener;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.events.DocumentWebsocketPublisher;
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
	public int reset(@NonNull final CacheInvalidateMultiRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrx currentTrx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (trxManager.isNull(currentTrx))
		{
			async.execute(() -> resetNow(request));
		}
		else
		{
			final CacheInvalidateMultiRequestsCollector collector = currentTrx.getProperty(TRXPROP_Requests, trx -> {
				final CacheInvalidateMultiRequestsCollector c = new CacheInvalidateMultiRequestsCollector();
				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.AFTER_COMMIT)
						.registerHandlingMethod(innerTrx -> {
							final CacheInvalidateMultiRequest aggregatedRequest = c.aggregateOrNull();
							if (aggregatedRequest == null)
							{
								return;
							}
							async.execute(() -> resetNow(aggregatedRequest));
						});
				return c;
			});

			collector.add(request);
		}

		return 1; // not relevant
	}

	private void resetNow(final CacheInvalidateMultiRequest request)
	{
		final DocumentWebsocketPublisher websocketPublisher = documents.getWebsocketPublisher();
		try (final IAutoCloseable c = websocketPublisher.temporaryCollectOnThisThread())
		{
			request.getRequests().forEach(this::resetNow);
		}
	}

	private void resetNow(final CacheInvalidateRequest request)
	{
		logger.debug("Got {}", request);

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
			// NOTE: as a workaround to solve the problem of https://github.com/metasfresh/metasfresh-webui-api/issues/851,
			// we are invalidating the whole root document to make sure that in case there were any virtual columns on header,
			// those get refreshed too.
			logger.debug("Invalidating the included document: {}", request);
			documents.invalidateDocumentByRecordId(rootTableName, rootRecordId);
			// final int childRecordId = request.getChildRecordId();
			// documents.invalidateIncludedDocumentsByRecordId(rootTableName, rootRecordId, childTableName, childRecordId);
		}

		viewsRepository.notifyRecordChanged(rootTableName, rootRecordId);
	}

	private static final class CacheInvalidateMultiRequestsCollector
	{
		private final List<CacheInvalidateMultiRequest> multiRequests = new ArrayList<>();

		public void add(@NonNull CacheInvalidateMultiRequest multiRequest)
		{
			multiRequests.add(multiRequest);
		}

		public CacheInvalidateMultiRequest aggregateOrNull()
		{
			if (multiRequests.isEmpty())
			{
				return null;
			}

			final Set<CacheInvalidateRequest> requests = multiRequests.stream()
					.flatMap(multiRequest -> multiRequest.getRequests().stream())
					.collect(ImmutableSet.toImmutableSet());
			return CacheInvalidateMultiRequest.of(requests);
		}
	}
}
