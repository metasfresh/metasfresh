package de.metas.ui.web.window.events;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.util.Services;
import org.springframework.stereotype.Component;

import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
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

@Component
public class DocumentChangedWebSocketEventsPublisher
{
	private final WebsocketSender websocketSender;

	public DocumentChangedWebSocketEventsPublisher(@NonNull final WebsocketSender websocketSender)
	{
		this.websocketSender = websocketSender;
	}

	private void forCollector(final Consumer<JSONDocumentChangedWebSocketEventCollector> consumer)
	{
		final JSONDocumentChangedWebSocketEventCollector collector;
		final boolean autoflush;

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		if (!trxManager.isNull(trx))
		{
			collector = trx.getProperty(JSONDocumentChangedWebSocketEventCollector.class.getName(), () -> createCollectorAndBind(trx, websocketSender));
			autoflush = false;
		}
		else
		{
			collector = JSONDocumentChangedWebSocketEventCollector.newInstance();
			autoflush = true;
		}

		consumer.accept(collector);

		if (autoflush)
		{
			sendAllAndClear(collector, websocketSender);
		}
	}

	private static JSONDocumentChangedWebSocketEventCollector createCollectorAndBind(final ITrx trx, final WebsocketSender websocketSender)
	{
		final JSONDocumentChangedWebSocketEventCollector collector = JSONDocumentChangedWebSocketEventCollector.newInstance();

		trx.getTrxListenerManager().onAfterCommit(() -> sendAllAndClear(collector, websocketSender));

		return collector;
	}

	private static void sendAllAndClear(final JSONDocumentChangedWebSocketEventCollector collector, final WebsocketSender websocketSender)
	{
		final List<JSONDocumentChangedWebSocketEvent> events = collector.getEventsAndClear();
		websocketSender.convertAndSend(events);
	}

	public void staleByDocumentPath(final DocumentPath documentPath)
	{
		forCollector(collector -> collector.staleByDocumentPath(documentPath));
	}

	public void staleRootDocument(final WindowId windowId, final DocumentId documentId)
	{
		forCollector(collector -> collector.staleRootDocument(windowId, documentId));
	}

	public void staleTab(final WindowId windowId, final DocumentId documentId, final DetailId tabId)
	{
		forCollector(collector -> collector.staleTab(windowId, documentId, tabId));
	}

	public void staleTabs(final WindowId windowId, final DocumentId documentId, final Set<DetailId> tabIds)
	{
		forCollector(collector -> collector.staleTabs(windowId, documentId, tabIds));
	}

	public void staleIncludedDocument(final WindowId windowId, final DocumentId documentId, final DetailId tabId, final DocumentId rowId)
	{
		forCollector(collector -> collector.staleIncludedDocument(windowId, documentId, tabId, rowId));
	}

}
