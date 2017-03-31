package de.metas.ui.web.view.event;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.DocumentId;

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
public final class JSONDocumentViewChanges implements Serializable
{
	public static JSONDocumentViewChanges of(final DocumentViewChanges changes)
	{
		return new JSONDocumentViewChanges(changes);
	}

	@JsonProperty("viewId")
	private final String viewId;
	@JsonProperty("windowId")
	private final int windowId;

	@JsonProperty("fullyChanged")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean fullyChanged;

	@JsonProperty("changedIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<String> changedIds;

	private JSONDocumentViewChanges(final DocumentViewChanges changes)
	{
		super();
		viewId = changes.getViewId();
		windowId = changes.getAD_Window_ID();

		changedIds = DocumentId.toStringSet(changes.getChangedDocumentIds());

		if (changes.isFullyChanged())
		{
			fullyChanged = Boolean.TRUE;
		}
		// FIXME: if there are any changed IDs consider as "fullyChanged". Take it out when "changedIds" support will be implemented on frontend side.
		else if (!changedIds.isEmpty())
		{
			fullyChanged = Boolean.TRUE;
		}
		else
		{
			fullyChanged = null;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("windowId", windowId)
				.add("fullyChanged", fullyChanged)
				.add("changedIds", changedIds)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public int getWindowId()
	{
		return windowId;
	}

	public Boolean getFullyChanged()
	{
		return fullyChanged;
	}
}
