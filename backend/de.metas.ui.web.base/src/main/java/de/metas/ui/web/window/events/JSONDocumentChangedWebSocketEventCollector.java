package de.metas.ui.web.window.events;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONIncludedTabInfo;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.Set;

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
final class JSONDocumentChangedWebSocketEventCollector
{
	public static JSONDocumentChangedWebSocketEventCollector newInstance()
	{
		return new JSONDocumentChangedWebSocketEventCollector();
	}

	//@formatter:off
	@Value
	private static class EventKey { WindowId windowId; DocumentId documentId; }
	//@formatter:on

	@Nullable private LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> _events; // null means collector closed

	private JSONDocumentChangedWebSocketEventCollector()
	{
		_events = new LinkedHashMap<>();
	}

	public ImmutableList<JSONDocumentChangedWebSocketEvent> getEventsAndClose()
	{
		final ImmutableList<JSONDocumentChangedWebSocketEvent> events = getEvents();
		markAsClosed();
		return events;
	}

	public void markAsClosed()
	{
		this._events = null;
	}

	@VisibleForTesting
	ImmutableList<JSONDocumentChangedWebSocketEvent> getEvents()
	{
		final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> events = this._events;
		return events != null && !events.isEmpty() ? ImmutableList.copyOf(events.values()) : ImmutableList.of();
	}

	public boolean isEmpty()
	{
		final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> events = this._events;
		return events == null || events.isEmpty();
	}

	private JSONDocumentChangedWebSocketEvent getCreateEvent(@NonNull final WindowId windowId, @NonNull final DocumentId documentId)
	{
		final EventKey key = new EventKey(windowId, documentId);

		final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> events = this._events;
		if (events == null)
		{
			throw new AdempiereException("already closed: " + this);
		}

		return events.computeIfAbsent(key, this::createEvent);
	}

	private JSONDocumentChangedWebSocketEvent createEvent(final EventKey key)
	{
		return JSONDocumentChangedWebSocketEvent.rootDocument(key.getWindowId(), key.getDocumentId());
	}

	public void staleRootDocument(final WindowId windowId, final DocumentId documentId)
	{
		staleRootDocument(windowId, documentId, false);
	}

	public void staleRootDocument(final WindowId windowId, final DocumentId documentId, final boolean markActiveTabStaled)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.markRootDocumentAsStaled();
		if (markActiveTabStaled)
		{
			event.markActiveTabStaled();
		}
	}

	public void staleTabs(final WindowId windowId, final DocumentId documentId, final Set<DetailId> tabIds)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.staleTabs(tabIds);
	}

	public void staleIncludedDocuments(final WindowId windowId, final DocumentId documentId, final DetailId tabId, final DocumentIdsSelection rowIds)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.staleIncludedRows(tabId, rowIds);
	}

	public void mergeFrom(final WindowId windowId, final DocumentId documentId, final JSONIncludedTabInfo tabInfo)
	{
		final JSONDocumentChangedWebSocketEvent event = getCreateEvent(windowId, documentId);
		event.addIncludedTabInfo(tabInfo);
	}

	public void mergeFrom(final JSONDocumentChangedWebSocketEventCollector from)
	{
		final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> fromEvents = from._events;
		if (fromEvents == null || fromEvents.isEmpty())
		{
			return;
		}

		fromEvents.forEach(this::mergeFrom);
	}

	private void mergeFrom(final EventKey key, final JSONDocumentChangedWebSocketEvent from)
	{
		final LinkedHashMap<EventKey, JSONDocumentChangedWebSocketEvent> events = this._events;
		if (events == null)
		{
			throw new AdempiereException("already closed: " + this);
		}

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
