package de.metas.ui.web.view;

import java.util.Set;

import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;
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

@Value
public final class ViewRowIdsSelection
{
	public static ViewRowIdsSelection of(@NonNull final ViewId viewId, final DocumentIdsSelection rowIds)
	{
		return new ViewRowIdsSelection(viewId, rowIds);
	}

	public static ViewRowIdsSelection ofNullableStrings(final String viewIdStr, final String rowIdsListStr)
	{
		if (viewIdStr == null || viewIdStr.isEmpty())
		{
			return null;
		}

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final DocumentIdsSelection rowIds = DocumentIdsSelection.ofCommaSeparatedString(rowIdsListStr);
		return new ViewRowIdsSelection(viewId, rowIds);
	}

	public static ViewRowIdsSelection ofNullableStrings(String viewIdStr, Set<String> rowIdsStringSet)
	{
		if (viewIdStr == null || viewIdStr.isEmpty())
		{
			return null;
		}

		final ViewId viewId = ViewId.ofViewIdString(viewIdStr);
		final DocumentIdsSelection rowIds = DocumentIdsSelection.ofStringSet(rowIdsStringSet);
		return new ViewRowIdsSelection(viewId, rowIds);
	}

	private final ViewId viewId;
	private final DocumentIdsSelection rowIds;

	private ViewRowIdsSelection(@NonNull final ViewId viewId, final DocumentIdsSelection rowIds)
	{
		this.viewId = viewId;
		this.rowIds = rowIds != null ? rowIds : DocumentIdsSelection.EMPTY;
	}
}
