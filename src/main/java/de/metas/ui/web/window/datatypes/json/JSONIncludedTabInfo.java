package de.metas.ui.web.window.datatypes.json;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.window.datatypes.DocumentId;
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

//
//
//
//
//
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@ToString
@EqualsAndHashCode
public final class JSONIncludedTabInfo
{
	public static JSONIncludedTabInfo newInstance(final DetailId tabId)
	{
		return new JSONIncludedTabInfo(tabId);
	}

	@JsonProperty("tabid")
	private final String tabIdJson;
	@JsonIgnore
	private final DetailId tabId;

	@JsonProperty("stale")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean stale;

	@JsonProperty("staleRowIds")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Set<DocumentId> staleRowIds;

	@JsonProperty("allowCreateNew")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean allowCreateNew;
	@JsonProperty("allowCreateNewReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String allowCreateNewReason;

	@JsonProperty("allowDelete")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean allowDelete;
	@JsonProperty("allowDeleteReason")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String allowDeleteReason;

	private JSONIncludedTabInfo(@NonNull final DetailId tabId)
	{
		this.tabId = tabId;
		tabIdJson = DetailId.toJson(tabId);
		staleRowIds = new HashSet<>();
	}

	private JSONIncludedTabInfo(final JSONIncludedTabInfo from)
	{
		tabId = from.tabId;
		tabIdJson = from.tabIdJson;
		stale = from.stale;
		staleRowIds = new HashSet<>(from.staleRowIds);
		allowCreateNew = from.allowCreateNew;
		allowCreateNewReason = from.allowCreateNewReason;
		allowDelete = from.allowDelete;
		allowDeleteReason = from.allowDeleteReason;
	}

	public JSONIncludedTabInfo copy()
	{
		return new JSONIncludedTabInfo(this);
	}

	public DetailId getTabId()
	{
		return tabId;
	}

	public void setAllowCreateNew(final boolean allowCreateNew, final String reason)
	{
		this.allowCreateNew = allowCreateNew;
		allowCreateNewReason = reason;
	}

	public void setAllowDelete(final boolean allowDelete, final String reason)
	{
		this.allowDelete = allowDelete;
		allowDeleteReason = reason;
	}

	public void setStale()
	{
		stale = Boolean.TRUE;
	}

	public boolean isStale()
	{
		return stale != null && stale.booleanValue();
	}

	public void staleRow(@NonNull final DocumentId rowId)
	{
		staleRowIds.add(rowId);

		// FIXME: atm staling included rows is not supported on frontend, so we are invalidating the whole tab.
		setStale();
	}

	public void mergeFrom(JSONIncludedTabInfo from)
	{
		if (from.stale != null)
		{
			stale = from.stale;
		}

		staleRowIds.addAll(from.staleRowIds);

		if (from.allowCreateNew != null)
		{
			allowCreateNew = from.allowCreateNew;
			allowCreateNewReason = from.allowCreateNewReason;
		}

		if (from.allowDelete != null)
		{
			allowDelete = from.allowDelete;
			allowDeleteReason = from.allowDeleteReason;
		}
	}
}
