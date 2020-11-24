package de.metas.ui.web.view.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.WindowId;
import lombok.NonNull;

import java.io.Serializable;
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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONViewChanges implements Serializable
{
	public static JSONViewChanges of(@NonNull final ViewChanges changes)
	{
		return new JSONViewChanges(changes);
	}

	@JsonProperty("viewId")
	private final String viewId;
	@JsonProperty("windowId")
	private final WindowId windowId;

	@JsonProperty("fullyChanged")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean fullyChanged;

	@JsonProperty("changedIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<String> changedIds;

	@JsonProperty("headerPropertiesChanged")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean headerPropertiesChanged;

	private JSONViewChanges(@NonNull final ViewChanges changes)
	{
		super();

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
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("windowId", windowId)
				.add("fullyChanged", fullyChanged)
				.add("headerPropertiesChanged", headerPropertiesChanged)
				.add("changedIds", changedIds)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public WindowId getWindowId()
	{
		return windowId;
	}

	public Boolean getFullyChanged()
	{
		return fullyChanged;
	}
}
