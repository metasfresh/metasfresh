package de.metas.ui.web.window.model;

import java.util.Objects;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

final class DocumentKey
{
	public static final DocumentKey of(final int adWindowId, final DocumentId documentId)
	{
		return new DocumentKey(adWindowId, documentId);
	}

	private final int AD_Window_ID;
	private final DocumentId documentId;

	private Integer _hashcode = null;

	private DocumentKey(final int adWindowId, final DocumentId documentId)
	{
		super();
		AD_Window_ID = adWindowId;
		this.documentId = documentId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("AD_Window_ID", AD_Window_ID)
				.add("documentId", documentId)
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(AD_Window_ID, documentId);
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!(obj instanceof DocumentKey))
		{
			return false;
		}

		final DocumentKey other = (DocumentKey)obj;
		return AD_Window_ID == other.AD_Window_ID
				&& Objects.equals(documentId, other.documentId);
	}

	public int getAD_Window_ID()
	{
		return AD_Window_ID;
	}

	public DocumentId getDocumentId()
	{
		return documentId;
	}
}