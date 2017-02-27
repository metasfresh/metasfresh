package de.metas.ui.web.view.event;

import java.util.HashSet;
import java.util.Set;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

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

public class DocumentViewChanges
{
	private final String viewId;
	private final int adWindowId;

	private boolean fullyChanged;
	private Set<DocumentId> changedDocumentIds = null;

	/* package */ DocumentViewChanges(final String viewId, final int adWindowId)
	{
		super();
		this.viewId = viewId;
		this.adWindowId = adWindowId;
	}

	public void collectFrom(final DocumentViewChanges changes)
	{
		if (changes.isFullyChanged())
		{
			fullyChanged = true;
		}

		if (changes.changedDocumentIds != null && !changes.changedDocumentIds.isEmpty())
		{
			if (changedDocumentIds == null)
			{
				changedDocumentIds = new HashSet<>();
			}
			changedDocumentIds.addAll(changes.changedDocumentIds);
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("viewId", viewId)
				.add("AD_Window_ID", adWindowId)
				.add("fullyChanged", fullyChanged ? Boolean.TRUE : null)
				.add("changedDocumentIds", changedDocumentIds)
				.toString();
	}

	public String getViewId()
	{
		return viewId;
	}

	public int getAD_Window_ID()
	{
		return adWindowId;
	}

	public void setFullyChanged()
	{
		fullyChanged = true;
	}

	public boolean isFullyChanged()
	{
		return fullyChanged;
	}

	public void addChangedDocumentId(final DocumentId documentId)
	{
		Check.assumeNotNull(documentId, "Parameter documentId is not null");

		if (changedDocumentIds == null)
		{
			changedDocumentIds = new HashSet<>();
		}
		changedDocumentIds.add(documentId);
	}

	public boolean hasChangedDocumentIds()
	{
		return changedDocumentIds != null && !changedDocumentIds.isEmpty();
	}

	public Set<DocumentId> getChangedDocumentIds()
	{
		return changedDocumentIds == null ? ImmutableSet.of() : ImmutableSet.copyOf(changedDocumentIds);
	}
}
