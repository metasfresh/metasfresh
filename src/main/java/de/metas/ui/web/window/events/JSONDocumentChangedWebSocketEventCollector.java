package de.metas.ui.web.window.events;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONIncludedTabInfo;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;

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

@ToString
class JSONDocumentChangedWebSocketEventCollector
{
	public static JSONDocumentChangedWebSocketEventCollector newInstance()
	{
		return new JSONDocumentChangedWebSocketEventCollector();
	}

	//@formatter:off
	@Value
	private static final class EventKey { WindowId windowId; DocumentId documentId; }
	//@formatter:on

	private final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> events = new LinkedHashMap<>();

	private JSONDocumentChangedWebSocketEventCollector()
	{
	}

	public List<JSONDocumentChangedWebSocketEvent> getEvents()
	{
		return ImmutableList.copyOf(events.values());
	}

	public List<JSONDocumentChangedWebSocketEvent> getEventsAndClear()
	{
		final List<JSONDocumentChangedWebSocketEvent> eventsList = ImmutableList.copyOf(events.values());
		events.clear();
		return eventsList;
	}
	
	public boolean isEmpty()
	{
		return events.isEmpty();
	}

	private JSONDocumentChangedWebSocketEvent getCreateEvent(@NonNull final WindowId windowId, @NonNull final DocumentId documentId)
	{
		final EventKey key = new EventKey(windowId, documentId);
		return events.computeIfAbsent(key, this::createEvent);
	}

	private JSONDocumentChangedWebSocketEvent createEvent(final EventKey key)
	{
		return JSONDocumentChangedWebSocketEvent.rootDocument(key.getWindowId(), key.getDocumentId());
	}

	public void staleByDocumentPath(final DocumentPath documentPath)
	{
		if (documentPath.isRootDocument())
		{
			staleRootDocument(documentPath.getWindowId(), documentPath.getDocumentId());
		}
		else if (documentPath.isAnyIncludedDocument())
		{
			staleTab(documentPath.getWindowId(), documentPath.getDocumentId(), documentPath.getDetailId());
		}
		else if (documentPath.isSingleIncludedDocument())
		{
			staleIncludedDocument(documentPath.getWindowId(), documentPath.getDocumentId(), documentPath.getDetailId(), documentPath.getSingleRowId());
		}
		else
		{
			throw new AdempiereException("Cannot convert " + documentPath + " to websocket staled event");
		}
	}

	public void staleRootDocument(final WindowId windowId, final DocumentId documentId)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.setStale();
	}

	public void staleTab(final WindowId windowId, final DocumentId documentId, final DetailId tabId)
	{
		staleTabs(windowId, documentId, ImmutableSet.of(tabId));
	}

	public void staleTabs(final WindowId windowId, final DocumentId documentId, final Set<DetailId> tabIds)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.staleTabs(tabIds);
	}

	public void staleIncludedDocument(final WindowId windowId, final DocumentId documentId, final DetailId tabId, final DocumentId rowId)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.staleIncludedRow(tabId, rowId);
	}

	public void mergeFrom(final WindowId windowId, final DocumentId documentId, final JSONIncludedTabInfo tabInfo)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.addIncludedTabInfo(tabInfo);
	}

	public void mergeFrom(final JSONDocumentChangedWebSocketEventCollector from)
	{
		from.events.forEach(this::mergeFrom);
	}

	private void mergeFrom(final EventKey key, final JSONDocumentChangedWebSocketEvent from)
	{
		events.compute(key, (k, existingEvent) -> {
			if (existingEvent == null)
			{
				return from.copy();
			}
			else
			{
				existingEvent.mergeFrom(from);
				return existingEvent;
			}
		});
	}
}
