package de.metas.ui.web.window.events;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.websocket.WebsocketEndpointAware;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.DateTimeConverters;
import de.metas.ui.web.window.datatypes.json.JSONIncludedTabInfo;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.util.time.SystemTime;
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
@EqualsAndHashCode
@ToString
final class JSONDocumentChangedWebSocketEvent implements WebsocketEndpointAware
{
	public static JSONDocumentChangedWebSocketEvent rootDocument(final WindowId windowId, final DocumentId documentId)
	{
		return new JSONDocumentChangedWebSocketEvent(windowId, documentId);
	}

	@JsonProperty("windowId")
	private final WindowId windowId;

	@JsonProperty("id")
	private final DocumentId id;

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
	private Map<String, JSONIncludedTabInfo> includedTabsInfoByTabId;

	private JSONDocumentChangedWebSocketEvent(
			@NonNull final WindowId windowId,
			@NonNull final DocumentId id)
	{
		this.windowId = windowId;
		this.id = id;

		timestamp = DateTimeConverters.toJson(SystemTime.asInstant(), SystemTime.zoneId());
	}

	private JSONDocumentChangedWebSocketEvent(@NonNull final JSONDocumentChangedWebSocketEvent from)
	{
		windowId = from.windowId;
		id = from.id;
		timestamp = from.timestamp;

		stale = from.stale;

		if (from.includedTabsInfoByTabId != null)
		{
			includedTabsInfoByTabId = new HashMap<>();
			from.includedTabsInfoByTabId.forEach((key, tabInfo) -> includedTabsInfoByTabId.put(key, tabInfo.copy()));
		}
	}

	public JSONDocumentChangedWebSocketEvent copy()
	{
		return new JSONDocumentChangedWebSocketEvent(this);
	}

	void markRootDocumentAsStaled()
	{
		stale = Boolean.TRUE;
	}

	private Map<String, JSONIncludedTabInfo> getIncludedTabsInfo()
	{
		if (includedTabsInfoByTabId == null)
		{
			includedTabsInfoByTabId = new HashMap<>();
		}
		return includedTabsInfoByTabId;
	}

	private JSONIncludedTabInfo getIncludedTabInfo(final DetailId tabId)
	{
		return getIncludedTabsInfo().computeIfAbsent(tabId.toJson(), k -> JSONIncludedTabInfo.newInstance(tabId));
	}

	void addIncludedTabInfo(@NonNull final JSONIncludedTabInfo tabInfo)
	{
		getIncludedTabsInfo().compute(tabInfo.getTabId().toJson(), (tabId, existingTabInfo) -> {
			if (existingTabInfo == null)
			{
				return tabInfo.copy();
			}
			else
			{
				existingTabInfo.mergeFrom(tabInfo);
				return existingTabInfo;
			}
		});
	}

	@Override
	@JsonIgnore
	public WebsocketTopicName getWebsocketEndpoint()
	{
		return WebsocketTopicNames.buildDocumentTopicName(windowId, id);
	}

	public void staleTabs(@NonNull final Collection<DetailId> tabIds)
	{
		tabIds.stream().map(this::getIncludedTabInfo).forEach(JSONIncludedTabInfo::markAllRowsStaled);
	}

	public void staleIncludedRows(@NonNull final DetailId tabId, @NonNull final DocumentIdsSelection rowIds)
	{
		getIncludedTabInfo(tabId).staleRows(rowIds);
	}

	void mergeFrom(@NonNull final JSONDocumentChangedWebSocketEvent from)
	{
		if (!Objects.equals(windowId, from.windowId)
				|| !Objects.equals(id, from.id))
		{
			throw new AdempiereException("Cannot merge events because they are not matching")
					.setParameter("from", from)
					.setParameter("to", this)
					.appendParametersToMessage();
		}

		if (from.stale != null && from.stale)
		{
			stale = from.stale;
		}

		from.getIncludedTabsInfo().values().forEach(this::addIncludedTabInfo);
	}
}
