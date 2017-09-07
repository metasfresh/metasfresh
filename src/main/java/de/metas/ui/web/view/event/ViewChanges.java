package de.metas.ui.web.view.event;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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

public class ViewChanges
{
	private final ViewId viewId;

	private boolean fullyChanged;
	private Set<DocumentId> changedRowIds = null;

	/* package */ ViewChanges(final ViewId viewId)
	{
		super();
		this.viewId = viewId;
	}

	public void collectFrom(final ViewChanges changes)
	{
		if (changes.isFullyChanged())
		{
			fullyChanged = true;
		}

		if (changes.changedRowIds != null && !changes.changedRowIds.isEmpty())
		{
			if (changedRowIds == null)
			{
				changedRowIds = new HashSet<>();
			}
			changedRowIds.addAll(changes.changedRowIds);
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("fullyChanged", fullyChanged ? Boolean.TRUE : null)
				.add("changedRowIds", changedRowIds)
				.toString();
	}

	public ViewId getViewId()
	{
		return viewId;
	}

	public void setFullyChanged()
	{
		fullyChanged = true;
	}

	public boolean isFullyChanged()
	{
		return fullyChanged;
	}

	public boolean hasChanges()
	{
		if (fullyChanged)
		{
			return true;
		}

		return changedRowIds != null && !changedRowIds.isEmpty();
	}

	public void addChangedRowIds(final DocumentIdsSelection rowIds)
	{
		// Don't collect rowIds if this was already flagged as fully changed.
		if (fullyChanged)
		{
			return;
		}

		if (rowIds == null || rowIds.isEmpty())
		{
			return;
		}

		else if (rowIds.isAll())
		{
			fullyChanged = true;
			changedRowIds = null;
		}
		else
		{
			if (changedRowIds == null)
			{
				changedRowIds = new HashSet<>();
			}
			changedRowIds.addAll(rowIds.toSet());
		}
	}

	public void addChangedRowId(@NonNull final DocumentId rowId)
	{
		if (changedRowIds == null)
		{
			changedRowIds = new HashSet<>();
		}
		changedRowIds.add(rowId);
	}

	public DocumentIdsSelection getChangedRowIds()
	{
		final boolean fullyChanged = this.fullyChanged;
		final Set<DocumentId> changedRowIds = this.changedRowIds;

		if (fullyChanged)
		{
			return DocumentIdsSelection.ALL;
		}
		else if (changedRowIds == null || changedRowIds.isEmpty())
		{
			return DocumentIdsSelection.EMPTY;
		}
		else
		{
			return DocumentIdsSelection.of(changedRowIds);
		}
	}
}
