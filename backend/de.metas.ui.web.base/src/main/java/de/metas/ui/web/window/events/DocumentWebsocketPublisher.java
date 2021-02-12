package de.metas.ui.web.window.events;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.springframework.stereotype.Component;

import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.descriptor.DetailId;
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
 * Publishes document related events to websocket endpoints.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class DocumentWebsocketPublisher
{
	private final ThreadLocal<JSONDocumentChangedWebSocketEventCollector> THREAD_LOCAL_COLLECTOR = new ThreadLocal<>();

	private final WebsocketSender websocketSender;

	public DocumentWebsocketPublisher(@NonNull final WebsocketSender websocketSender)
	{
		this.websocketSender = websocketSender;
	}

	private void forCollector(final Consumer<JSONDocumentChangedWebSocketEventCollector> consumer)
	{
		//
		// Get the collector to use
		final JSONDocumentChangedWebSocketEventCollector collector;
		final boolean autoflush;

		final JSONDocumentChangedWebSocketEventCollector threadLocalCollector = THREAD_LOCAL_COLLECTOR.get();
		if (threadLocalCollector != null)
		{
			collector = threadLocalCollector;
			autoflush = false;
		}
		else
		{
			final ITrxManager trxManager = Services.get(ITrxManager.class);
			final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
			if (trxManager.isActive(trx))
			{
				collector = trx.getPropertyAndProcessAfterCommit(
						JSONDocumentChangedWebSocketEventCollector.class.getName(),
						() -> JSONDocumentChangedWebSocketEventCollector.newInstance(),
						c -> {
							sendAllAndClear(c, websocketSender);
							c.markAsClosed();
						});
				autoflush = false;
			}
			else
			{
				collector = JSONDocumentChangedWebSocketEventCollector.newInstance();
				autoflush = true;
			}
		}

		//
		// Call the consumer
		consumer.accept(collector);

		//
		// Autoflush if needed
		if (autoflush)
		{
			sendAllAndClear(collector, websocketSender);
		}
	}

	private static void sendAllAndClear(final JSONDocumentChangedWebSocketEventCollector collector, final WebsocketSender websocketSender)
	{
		final List<JSONDocumentChangedWebSocketEvent> events = collector.getEventsAndClear();
		websocketSender.convertAndSend(events);
	}

	public void staleRootDocument(final WindowId windowId, final DocumentId documentId)
	{
		forCollector(collector -> collector.staleRootDocument(windowId, documentId));
	}

	public void staleTabs(final WindowId windowId, final DocumentId documentId, final Set<DetailId> tabIds)
	{
		forCollector(collector -> collector.staleTabs(windowId, documentId, tabIds));
	}

	public void staleIncludedDocuments(final WindowId windowId, final DocumentId documentId, final DetailId tabId, final DocumentIdsSelection rowIds)
	{
		forCollector(collector -> collector.staleIncludedDocuments(windowId, documentId, tabId, rowIds));
	}

	public void convertAndPublish(final List<JSONDocument> jsonDocumentEvents)
	{
		if (jsonDocumentEvents == null || jsonDocumentEvents.isEmpty())
		{
			return;
		}

		final JSONDocumentChangedWebSocketEventCollector collectorToMerge = JSONDocumentChangedWebSocketEventCollector.newInstance();
		for (final JSONDocument jsonDocumentEvent : jsonDocumentEvents)
		{
			collectFrom(collectorToMerge, jsonDocumentEvent);
		}
		if (collectorToMerge.isEmpty())
		{
			return;
		}

		forCollector(collector -> collector.mergeFrom(collectorToMerge));
	}

	private static final void collectFrom(final JSONDocumentChangedWebSocketEventCollector collector, final JSONDocument event)
	{
		final WindowId windowId = event.getWindowId();
		if (windowId == null)
		{
			return;
		}

		// Included document => nothing to publish about it
		if (event.getTabId() != null)
		{
			return;
		}

		final DocumentId documentId = event.getId();
		event.getIncludedTabsInfos().forEach(tabInfo -> collector.mergeFrom(windowId, documentId, tabInfo));
	}

	public IAutoCloseable temporaryCollectOnThisThread()
	{
		if (THREAD_LOCAL_COLLECTOR.get() != null)
		{
			throw new AdempiereException("A thread level collector was already set");
		}

		final JSONDocumentChangedWebSocketEventCollector collector = JSONDocumentChangedWebSocketEventCollector.newInstance();
		THREAD_LOCAL_COLLECTOR.set(collector);
		return new IAutoCloseable()
		{
			@Override
			public String toString()
			{
				return "AutoCloseable[" + collector + "]";
			}

			@Override
			public void close()
			{
				THREAD_LOCAL_COLLECTOR.set(null);
				sendAllAndClear(collector, websocketSender);
				collector.markAsClosed();
			}
		};
	}
}
