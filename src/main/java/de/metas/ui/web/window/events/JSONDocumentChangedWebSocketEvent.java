package de.metas.ui.web.window.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.util.time.SystemTime;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.websocket.WebSocketConfig;
import de.metas.ui.web.websocket.WebsocketEndpointAware;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocument.JSONIncludedTabInfo;
import de.metas.ui.web.window.descriptor.DetailId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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
@ToString
@EqualsAndHashCode
public final class JSONDocumentChangedWebSocketEvent implements WebsocketEndpointAware
{
	public static JSONDocumentChangedWebSocketEvent rootDocument(final WindowId windowId, final DocumentId documentId)
	{
		final String tabId = null;
		final DocumentId rowId = null;
		final JSONDocumentChangedWebSocketEvent event = new JSONDocumentChangedWebSocketEvent(windowId, documentId, tabId, rowId);
		return event;
	}

	public static final Stream<JSONDocumentChangedWebSocketEvent> extractWebsocketEvents(final Collection<JSONDocument> jsonDocumentEvents)
	{
		if (jsonDocumentEvents == null || jsonDocumentEvents.isEmpty())
		{
			return Stream.empty();
		}

		return jsonDocumentEvents.stream()
				.map(JSONDocumentChangedWebSocketEvent::extractWebsocketEventOrNull)
				.filter(wsEvent -> wsEvent != null);
	}

	private static final JSONDocumentChangedWebSocketEvent extractWebsocketEventOrNull(final JSONDocument event)
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

	void setStale()
	{
		this.stale = Boolean.TRUE;
	}

	private Map<String, JSONIncludedTabInfo> getIncludedTabsInfo()
	{
		if (includedTabsInfo == null)
		{
			includedTabsInfo = new HashMap<>();
		}
		return includedTabsInfo;
	}

	private JSONIncludedTabInfo getIncludedTabInfo(final DetailId tabId)
	{
		return getIncludedTabsInfo().computeIfAbsent(tabId.toJson(), k -> JSONIncludedTabInfo.newInstance(tabId));
	}

	private void addIncludedTabInfo(final JSONIncludedTabInfo tabInfo)
	{
		getIncludedTabsInfo().put(tabInfo.getTabId(), tabInfo);
	}

	@Override
	@JsonIgnore
	public String getWebsocketEndpoint()
	{
		return WebSocketConfig.buildDocumentTopicName(windowId, id);
	}

	public void staleTab(@NonNull final DetailId tabId)
	{
		getIncludedTabInfo(tabId).setStale();
	}

	public void staleTabs(@NonNull final Collection<DetailId> tabIds)
	{
		tabIds.stream().map(this::getIncludedTabInfo).forEach(JSONIncludedTabInfo::setStale);
	}

	public void staleIncludedRow(@NonNull final DetailId tabId, @NonNull final DocumentId rowId)
	{
		getIncludedTabInfo(tabId).staleRow(rowId);
	}

}
