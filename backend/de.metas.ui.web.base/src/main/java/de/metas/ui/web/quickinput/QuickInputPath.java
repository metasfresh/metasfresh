package de.metas.ui.web.quickinput;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;


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

@Immutable
public final class QuickInputPath
{
	public static final QuickInputPath of(final String windowIdStr //
			, final String documentIdStr //
			, final String tabIdStr //
			, final String quickInputIdStr //
	)
	{
		final DocumentPath rootDocumentPath = DocumentPath.rootDocumentPath(WindowId.fromJson(windowIdStr), documentIdStr);
		final DetailId detailId = DetailId.fromJson(tabIdStr);
		final DocumentId quickInputId = DocumentId.of(quickInputIdStr);
		return new QuickInputPath(rootDocumentPath, detailId, quickInputId);
	}

	private final DocumentPath rootDocumentPath;
	private final DetailId detailId;
	private final DocumentId quickInputId;
	private transient Integer _hashCode;

	private QuickInputPath(final DocumentPath rootDocumentPath, final DetailId detailId, final DocumentId quickInputId)
	{
		this.rootDocumentPath = rootDocumentPath;
		this.detailId = detailId;
		this.quickInputId = quickInputId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(rootDocumentPath)
				.add("detailId", detailId)
				.add("quickInputId", quickInputId)
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashCode == null)
		{
			_hashCode = Objects.hash(rootDocumentPath, detailId, quickInputId);
		}
		return _hashCode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		else if (obj instanceof QuickInputPath)
		{
			final QuickInputPath other = (QuickInputPath)obj;
			return Objects.equals(rootDocumentPath, other.rootDocumentPath)
					&& Objects.equals(detailId, other.detailId)
					&& Objects.equals(quickInputId, other.quickInputId);
		}
		else
		{
			return false;
		}
	}

	public DocumentPath getRootDocumentPath()
	{
		return rootDocumentPath;
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public DocumentId getQuickInputId()
	{
		return quickInputId;
	}
}
