package de.metas.ui.web.window.datatypes;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;

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

	public static final DocumentPath rootDocumentPath(final DocumentType documentType, final int documentTypeId, final int id)
	{
		if (documentTypeId <= 0)
		{
			throw new IllegalArgumentException("documentTypeId < 0");
		}

		final DocumentId documentId = DocumentId.of(id);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}

		return new DocumentPath(documentType, documentTypeId, documentId);
	}
	
	public static final DocumentPath rootDocumentPath(final DocumentType documentType, final int documentTypeId, final String idStr)
	{
		if (documentTypeId <= 0)
		{
			throw new IllegalArgumentException("adWindowId < 0");
		}

		final DocumentId documentId = DocumentId.of(idStr);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}

		return new DocumentPath(documentType, documentTypeId, documentId);
	}
	
	public static final DocumentPath includedDocumentPath(final DocumentType documentType, final int documentTypeId, final String idStr, final String detailId, final String rowIdStr)
	{
		if(Check.isEmpty(detailId, true))
		{
			throw new IllegalArgumentException("No detailId provided");
		}
		if(Check.isEmpty(rowIdStr, true))
		{
			throw new IllegalArgumentException("No rowId provided");
		}
		
		return builder()
				.setDocumentType(documentType, documentTypeId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();
	}

	public static final DocumentPath includedDocumentPath(final DocumentType documentType, final int documentTypeId, final String idStr, final String detailId)
	{
		if(Check.isEmpty(detailId, true))
		{
			throw new IllegalArgumentException("No detailId provided");
		}
		
		return builder()
				.setDocumentType(documentType, documentTypeId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.allowNullRowId()
				.build();
	}

	/**
	 * Creates the path of a single document (root document or included document).
	 * 
	 * @param adWindowId
	 * @param idStr
	 * @param detailId
	 * @param rowIdStr
	 * @return single document path (root document or included document).
	 */
	public static final DocumentPath singleWindowDocumentPath(final int windowId, final String idStr, final String detailId, final String rowIdStr)
	{
		return builder()
				.setDocumentType(DocumentType.Window, windowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();
	}

	private final DocumentType documentType;
	private final int documentTypeId;
	private final DocumentId documentId;
	private final DetailId detailId;
	private final Set<DocumentId> rowIds;
	private final transient DocumentId singleRowId;

	private transient Integer _hashcode; // lazy
	private transient String _toString; // lazy

	/** Root document constructor */
	private DocumentPath(final DocumentType documentType, final int documentTypeId, final DocumentId documentId)
	{
		super();
		
		Preconditions.checkNotNull(documentType, "documentType shall not be null");
		this.documentType = documentType;
		this.documentTypeId = documentTypeId;
		this.documentId = documentId;
		
		detailId = null;
		rowIds = ImmutableSet.of();
		singleRowId = null;
	}

	/** Multiple rowIds constructor */
	private DocumentPath(final DocumentType documentType, final int adWindowId, final DocumentId documentId, final DetailId detailId, final Set<DocumentId> rowIds)
	{
		super();
		
		Preconditions.checkNotNull(documentType, "documentType shall not be null");
		this.documentType = documentType;
		this.documentTypeId = adWindowId;
		this.documentId = documentId;
		
		this.detailId = detailId;

		if (rowIds == null || rowIds.isEmpty())
		{
			this.rowIds = ImmutableSet.of();
			singleRowId = null;
		}
		else if (rowIds.size() == 1)
		{
			this.rowIds = ImmutableSet.copyOf(rowIds);
			singleRowId = this.rowIds.iterator().next();
		}
		else
		{
			this.rowIds = ImmutableSet.copyOf(rowIds);
			singleRowId = null;

		}
	}

	/** Single rowId constructor */
	private DocumentPath(final DocumentType documentType, final int adWindowId, final DocumentId documentId, final DetailId detailId, final DocumentId singleRowId)
	{
		super();
		
		Preconditions.checkNotNull(documentType, "documentType shall not be null");
		this.documentType = documentType;
		this.documentTypeId = adWindowId;
		this.documentId = documentId;
		
		this.detailId = detailId;
		this.singleRowId = singleRowId;
		rowIds = singleRowId == null ? ImmutableSet.of() : ImmutableSet.of(singleRowId);
	}

	@Override
	public String toString()
	{
		if (_toString == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append("/").append(documentType.getSymbol()).append(documentTypeId).append("/").append(documentId);
			if (detailId != null)
			{
				sb.append("/T").append(detailId);
			}

			if (singleRowId != null)
			{
				sb.append("/R").append(singleRowId);
			}
			else if (!rowIds.isEmpty())
			{
				sb.append("/R").append(rowIds);
			}

			_toString = sb.toString();
		}
		return _toString;
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = Objects.hash(documentType, documentTypeId, documentId, detailId, rowIds);
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
		return documentType == other.documentType
				&& DataTypes.equals(documentTypeId, other.documentTypeId)
				&& DataTypes.equals(documentId, other.documentId)
				&& DataTypes.equals(detailId, other.detailId)
				&& DataTypes.equals(rowIds, other.rowIds);
	}
	
	public DocumentType getDocumentType()
	{
		return documentType;
	}
	
	public int getDocumentTypeId()
	{
		return documentTypeId;
	}

	public int getAD_Window_ID()
	{
		Check.assume(documentType == DocumentType.Window, "Expect documentType={} for {}", DocumentType.Window, this);
		return documentTypeId;
	}

	public DocumentId getDocumentId()
	{
		return documentId;
	}

	public boolean isNewDocument()
	{
		return documentId != null && documentId.isNew();
	}

	public DetailId getDetailId()
	{
		return detailId;
	}

	public DocumentId getSingleRowId()
	{
		if (singleRowId == null)
		{
			throw new InvalidDocumentPathException(this, "There is no single rowId");
		}
		return singleRowId;
	}

	public Set<DocumentId> getRowIds()
	{
		return rowIds;
	}

	public boolean isRootDocument()
	{
		return detailId == null && rowIds.isEmpty();
	}

	public boolean isSingleIncludedDocument()
	{
		if (detailId == null)
		{
			return false;
		}

		return singleRowId != null && !singleRowId.isNew();
	}

	public boolean isSingleNewIncludedDocument()
	{
		if (detailId == null)
		{
			return false;
		}

		return singleRowId != null && singleRowId.isNew();
	}

	public boolean isAnyIncludedDocument()
	{
		if (detailId == null)
		{
			return false;
		}

		return rowIds.isEmpty();
	}

	public boolean hasIncludedDocuments()
	{
		if (detailId == null)
		{
			return false;
		}

		return !rowIds.isEmpty();
	}

	public DocumentPath createChildPath(final DetailId detailId, final int rowIdInt)
	{
		if (detailId == null)
		{
			throw new IllegalArgumentException("detailId must be not empty");
		}
		final DocumentId rowId = DocumentId.of(rowIdInt);
		if (rowId.isNew())
		{
			throw new IllegalArgumentException("new or null rowId is not accepted: " + rowId);
		}

		return new DocumentPath(documentType, documentTypeId, documentId, detailId, rowId);
	}

	public static final class Builder
	{
		private DocumentType documentType;
		private int documentTypeId;
		private DocumentId documentId;
		private boolean documentId_allowNew = false;
		private DetailId detailId;
		private final Set<DocumentId> rowIds = new LinkedHashSet<>();
		private boolean rowId_allowNull = false;
		private boolean rowId_allowNew = false;

		private static final Splitter SPLITTER_RowIds = Splitter.on(",").trimResults().omitEmptyStrings();

		private Builder()
		{
			super();
		}

		public DocumentPath build()
		{
			if(documentType == null)
			{
				throw new InvalidDocumentPathException("Invalid document type: " + documentType);
			}
			
			//
			// Validate documentTypeId
			if (documentTypeId <= 0)
			{
				throw new InvalidDocumentPathException("Invalid document type ID: " + documentTypeId);
			}

			//
			// Validate documentId
			if (documentId == null)
			{
				throw new InvalidDocumentPathException("id cannot be null");
			}
			if (!documentId_allowNew && documentId != null && documentId.isNew())
			{
				throw new InvalidDocumentPathException("id cannot be NEW");
			}

			//
			// Validate rowId
			if (!rowId_allowNull && detailId != null && rowIds.isEmpty())
			{
				throw new InvalidDocumentPathException("rowId cannot be null when detailId=" + detailId);
			}
			if (!rowId_allowNew)
			{
				for (final DocumentId rowId : rowIds)
				{
					if (rowId.isNew())
					{
						throw new InvalidDocumentPathException("rowId cannot be NEW");
					}
				}
			}

			//
			// Validate detailId
			if (detailId == null && !rowIds.isEmpty())
			{
				throw new InvalidDocumentPathException("detailId cannot be null when we have rowIds: " + rowIds);
			}

			//
			// Create & return the document path
			return new DocumentPath(documentType, documentTypeId, documentId, detailId, rowIds);
		}

		public Builder setAD_Window_ID(final int AD_Window_ID)
		{
			setDocumentType(DocumentType.Window, AD_Window_ID);
			return this;
		}
		
		public Builder setDocumentType(final DocumentType documentType, final int documentTypeId)
		{
			Preconditions.checkNotNull(documentType, "documentType not null");
			if (documentTypeId <= 0)
			{
				throw new IllegalArgumentException("Invalid document type ID: " + documentTypeId);
			}

			this.documentType = DocumentType.Window;
			this.documentTypeId = documentTypeId;
			return this;
		}

		public Builder setDocumentId(final String documentIdStr)
		{
			documentId = DocumentId.fromNullable(documentIdStr);
			return this;
		}

		public Builder allowNewDocumentId()
		{
			documentId_allowNew = true;
			return this;
		}

		public Builder setDetailId(final String detailIdStr)
		{
			this.detailId = DetailId.fromJson(detailIdStr);
			return this;
		}

		public Builder setRowId(final String rowIdStr)
		{
			final DocumentId rowId = DocumentId.fromNullable(rowIdStr);
			setRowId(rowId);
			return this;
		}

		public Builder setRowId(final DocumentId rowId)
		{
			rowIds.clear();
			if (rowId != null)
			{
				rowIds.add(rowId);
			}

			return this;
		}

		public Builder setRowIdsList(final String rowIdsListStr)
		{
			rowIds.clear();

			if (rowIdsListStr == null || rowIdsListStr.isEmpty())
			{
				return this;
			}

			for (final String rowIdStr : SPLITTER_RowIds.splitToList(rowIdsListStr))
			{
				final DocumentId rowId = DocumentId.fromNullable(rowIdStr);
				if (rowId == null)
				{
					continue;
				}

				rowIds.add(rowId);
			}

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
