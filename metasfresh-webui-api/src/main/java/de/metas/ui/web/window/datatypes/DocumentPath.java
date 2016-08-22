package de.metas.ui.web.window.datatypes;

import java.util.Objects;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

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
@Immutable
public final class DocumentPath
{
	public static final Builder builder()
	{
		return new Builder();
	}

	public static final DocumentPath rootDocumentPath(final int adWindowId, final int id)
	{
		if (adWindowId <= 0)
		{
			throw new IllegalArgumentException("adWindowId < 0");
		}

		final DocumentId documentId = DocumentId.of(id);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}

		return new DocumentPath(adWindowId, documentId);
	}

	private final int adWindowId;
	private final DocumentId documentId;
	private final String detailId;
	private final DocumentId rowId;

	private transient Integer _hashcode; // lazy

	private DocumentPath(final int adWindowId, final DocumentId documentId)
	{
		super();
		this.adWindowId = adWindowId;
		this.documentId = documentId;
		detailId = null;
		rowId = null;
	}

	private DocumentPath(final int adWindowId, final DocumentId documentId, final String detailId, final DocumentId rowId)
	{
		super();
		this.adWindowId = adWindowId;
		this.documentId = documentId;
		this.detailId = detailId;
		this.rowId = rowId;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", adWindowId)
				.add("documentId", documentId)
				.add("detailId", detailId)
				.add("rowId", rowId)
				.toString();
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(adWindowId, documentId, detailId, rowId);
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
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof DocumentPath))
		{
			return false;
		}

		final DocumentPath other = (DocumentPath)obj;
		return DataTypes.equals(adWindowId, other.adWindowId)
				&& DataTypes.equals(documentId, other.documentId)
				&& DataTypes.equals(detailId, other.detailId)
				&& DataTypes.equals(rowId, other.rowId);
	}

	public int getAD_Window_ID()
	{
		return adWindowId;
	}

	public DocumentId getDocumentId()
	{
		return documentId;
	}
	
	public boolean isNewDocument()
	{
		return documentId != null && documentId.isNew();
	}

	public String getDetailId()
	{
		return detailId;
	}

	public DocumentId getRowId()
	{
		return rowId;
	}

	public boolean isIncludedDocument()
	{
		return rowId != null;
	}

	public boolean isNewIncludedDocument()
	{
		return rowId != null && rowId.isNew();
	}

	public boolean isAnyIncludedDocument()
	{
		return detailId != null && rowId == null;
	}

	public DocumentPath createChildPath(final String detailId, final int rowIdInt)
	{
		if (detailId == null || detailId.isEmpty())
		{
			throw new IllegalArgumentException("detailId must be not empty");
		}
		final DocumentId rowId = DocumentId.of(rowIdInt);
		if (rowId.isNew())
		{
			throw new IllegalArgumentException("new or null rowId is not accepted: " + rowId);
		}

		return new DocumentPath(adWindowId, documentId, detailId, rowId);
	}

	public static final class Builder
	{
		private int adWindowId;
		private DocumentId documentId;
		private boolean documentId_allowNull = false;
		private boolean documentId_allowNew = false;
		private String detailId;
		private DocumentId rowId;
		private boolean rowId_allowNull = false;
		private boolean rowId_allowNew = false;

		private Builder()
		{
			super();
		}

		public DocumentPath build()
		{
			//
			// Validate AD_Window_ID
			if (adWindowId <= 0)
			{
				// NOTE: in protocol description, the AD_Window_ID is called "type"
				throw new IllegalArgumentException("Invalid type: " + adWindowId);
			}

			//
			// Validate documentId
			if (!documentId_allowNull && documentId == null)
			{
				throw new IllegalArgumentException("id cannot be null");
			}
			if (!documentId_allowNew && documentId != null && documentId.isNew())
			{
				throw new IllegalArgumentException("id cannot be NEW");
			}

			//
			// Validate rowId
			if (!rowId_allowNull && detailId != null && rowId == null)
			{
				throw new IllegalArgumentException("rowId cannot be null when detailId=" + detailId);
			}
			if (!rowId_allowNew && rowId != null && rowId.isNew())
			{
				throw new IllegalArgumentException("rowId cannot be NEW");
			}

			//
			// Validate detailId
			if (rowId != null && detailId == null)
			{
				throw new IllegalArgumentException("rowId cannot be null when detailId=" + detailId + " (not null)");
			}

			//
			// Create & return the document path
			return new DocumentPath(adWindowId, documentId, detailId, rowId);
		}

		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			if (AD_Window_ID <= 0)
			{
				// NOTE: in protocol description, the AD_Window_ID is called "type"
				throw new IllegalArgumentException("Invalid type: " + adWindowId);
			}

			adWindowId = AD_Window_ID;
			return this;
		}

		public Builder setDocumentId(final String documentIdStr)
		{
			documentId = DocumentId.fromNullable(documentIdStr);
			return this;
		}

		public Builder allowNullDocumentId()
		{
			documentId_allowNull = true;
			return this;
		}

		public Builder allowNewDocumentId()
		{
			documentId_allowNew = true;
			return this;
		}

		public Builder setDetailId(final String detailId)
		{
			if (Check.isEmpty(detailId, true))
			{
				this.detailId = null;
			}
			else
			{
				this.detailId = detailId;
			}
			return this;
		}

		public Builder setRowId(final String rowIdStr)
		{
			rowId = DocumentId.fromNullable(rowIdStr);
			return this;
		}

		public Builder allowNullRowId()
		{
			rowId_allowNull = true;
			return this;
		}

		public Builder allowNewRowId()
		{
			rowId_allowNew = true;
			return this;
		}

	}
}
