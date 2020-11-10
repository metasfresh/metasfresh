package de.metas.ui.web.view.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.ui.web.websocket.WebsocketEndpointAware;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;
import lombok.Value;

import java.util.Set;

@Value
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONViewChanges implements WebsocketEndpointAware
{
	public static JSONViewChanges of(@NonNull final ViewChanges changes)
	{
		return new JSONViewChanges(changes);
	}

	@JsonProperty("viewId")
	final String viewId;
	@JsonProperty("windowId")
	final WindowId windowId;

	@JsonProperty("fullyChanged")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	final Boolean fullyChanged;

	@JsonProperty("changedIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	final Set<String> changedIds;

	@JsonProperty("headerPropertiesChanged")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	final Boolean headerPropertiesChanged;

	@JsonIgnore
	final WebsocketTopicName websocketEndpoint;

	private JSONViewChanges(@NonNull final ViewChanges changes)
	{
		viewId = changes.getViewId().getViewId();
		windowId = changes.getViewId().getWindowId();

		final DocumentIdsSelection changedRowIds = changes.getChangedRowIds();
		if (changedRowIds.isAll())
		{
			fullyChanged = Boolean.TRUE;
			this.changedIds = null;
		}
		else if (changedRowIds.isEmpty())
		{
			// TODO: shall we throw an exception in this case? ...because basically it's not valid!
			fullyChanged = null;
			changedIds = null;
		}
		else
		{
			fullyChanged = Boolean.FALSE;
			this.changedIds = changedRowIds.toJsonSet();
		}
		headerPropertiesChanged = changes.isHeaderPropertiesChanged() ? true : null;

		websocketEndpoint = WebsocketTopicNames.buildViewNotificationsTopicName(viewId);
	}
}

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
