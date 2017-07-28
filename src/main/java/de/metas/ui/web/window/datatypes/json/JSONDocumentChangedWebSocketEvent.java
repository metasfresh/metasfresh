package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.util.time.SystemTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketSender;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocument.JSONIncludedTabInfo;
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

/**
 * Document changed websocket event.
 * 
 * Event sent by backend when a document was changed.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentChangedWebSocketEvent
{
	public static final void extractAndSendWebsocketEvents(final Collection<JSONDocument> jsonDocumentEvents, final WebsocketSender websocketSender)
	{
		if (jsonDocumentEvents == null || jsonDocumentEvents.isEmpty())
		{
			return;
		}

		jsonDocumentEvents.stream()
				.map(JSONDocumentChangedWebSocketEvent::extractWebsocketEvent)
				.filter(wsEvent -> wsEvent != null)
				.forEach(wsEvent -> websocketSender.convertAndSend(wsEvent.getWebsocketEndpoint(), wsEvent));
	}

	/** @return websocket event or null */
	private static final JSONDocumentChangedWebSocketEvent extractWebsocketEvent(final JSONDocument event)
	{
		final WindowId windowId = event.getWindowId();
		if (windowId == null)
		{
			return null;
		}

		final Set<JSONIncludedTabInfo> tabInfos = event.getIncludedTabsInfos()
				.stream()
				.filter(JSONIncludedTabInfo::isStale)
				.collect(ImmutableSet.toImmutableSet());
		if (tabInfos.isEmpty())
		{
			return null;
		}

		final JSONDocumentChangedWebSocketEvent wsEvent = new JSONDocumentChangedWebSocketEvent(windowId, event.getId(), event.getTabId(), event.getRowId());
		tabInfos.forEach(wsEvent::addIncludedTabInfo);
		return wsEvent;
	}

	public static JSONDocumentChangedWebSocketEvent staleRootDocument(final WindowId windowId, final DocumentId documentId)
	{
		final String tabId = null;
		final DocumentId rowId = null;
		final JSONDocumentChangedWebSocketEvent event = new JSONDocumentChangedWebSocketEvent(windowId, documentId, tabId, rowId);
		event.setStale();
		return event;
	}

	public static JSONDocumentChangedWebSocketEvent staleTabs(final WindowId windowId, final DocumentId documentId, final Set<DetailId> tabIds)
	{
		final JSONDocumentChangedWebSocketEvent event = new JSONDocumentChangedWebSocketEvent(windowId, documentId, (String)null /* tabId */, (DocumentId)null/* rowId */);

		tabIds.stream()
				.map(tabId -> JSONIncludedTabInfo.staleTab(tabId))
				.forEach(event::addIncludedTabInfo);

		return event;
	}

	@JsonProperty("windowId")
	private final WindowId windowId;

	@JsonProperty("id")
	private final DocumentId id;

	@JsonProperty("tabId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String tabId;
	//
	@JsonProperty("tabid")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final String tabid;

	@JsonProperty("rowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final DocumentId rowId;

	/** Event's timestamp. */
	@JsonProperty("timestamp")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String timestamp;

	@JsonProperty("stale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean stale;

	/** {@link JSONIncludedTabInfo}s indexed by tabId */
	@JsonProperty("includedTabsInfo")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	// @JsonSerialize(using = JsonMapAsValuesListSerializer.class) // serialize as Map (see #288)
	private Map<String, JSONIncludedTabInfo> includedTabsInfo;

	private JSONDocumentChangedWebSocketEvent(
			@NonNull final WindowId windowId,
			@NonNull final DocumentId id,
			@Nullable final String tabId,
			@Nullable final DocumentId rowId)
	{
		this.windowId = windowId;
		this.id = id;
		this.tabId = tabId;
		this.tabid = tabId;
		this.rowId = rowId;

		this.timestamp = JSONDate.toJson(SystemTime.millis());
	}

	private void setStale()
	{
		this.stale = Boolean.TRUE;
	}

	public void addIncludedTabInfo(final JSONIncludedTabInfo tabInfo)
	{
		if (includedTabsInfo == null)
		{
			includedTabsInfo = new HashMap<>();
		}
		includedTabsInfo.put(tabInfo.getTabId(), tabInfo);
	}

	@JsonIgnore
	public String getWebsocketEndpoint()
	{
		return WebSocketConfig.buildDocumentTopicName(windowId, id);
	}
}
