package de.metas.ui.web.window.datatypes;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Immutable
public final class DocumentPath
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static DocumentPath newWindowRecord(@NonNull final WindowId windowId)
	{
		return new DocumentPath(DocumentType.Window, windowId.toDocumentId(), DocumentId.NEW);
	}

	public static DocumentPath rootDocumentPath(@NonNull final AdWindowId adWindowId, final int documentIdInt)
	{
		return rootDocumentPath(WindowId.of(adWindowId), documentIdInt);
	}

	public static DocumentPath rootDocumentPath(@NonNull final WindowId windowId, final int documentIdInt)
	{
		final DocumentId documentId = DocumentId.of(documentIdInt);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}
		return new DocumentPath(DocumentType.Window, windowId.toDocumentId(), documentId);
	}

	public static DocumentPath rootDocumentPath(@NonNull final WindowId windowId, @NonNull final RepoIdAware documentRepoId)
	{
		return rootDocumentPath(windowId, documentRepoId.getRepoId());
	}

	public static DocumentPath rootDocumentPath(@NonNull final WindowId windowId, final String documentIdStr)
	{
		final DocumentId documentId = DocumentId.of(documentIdStr);
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}
		return new DocumentPath(DocumentType.Window, windowId.toDocumentId(), documentId);
	}

	public static DocumentPath rootDocumentPath(@NonNull final WindowId windowId, @NonNull final DocumentId documentId)
	{
		if (documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}
		return new DocumentPath(DocumentType.Window, windowId.toDocumentId(), documentId);
	}

	public static DocumentPath rootDocumentPath(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
	{
		if (documentId == null || documentId.isNew())
		{
			throw new IllegalArgumentException("new or null documentId is not accepted: " + documentId);
		}

		return new DocumentPath(documentType, documentTypeId, documentId);
	}

	public static List<DocumentPath> rootDocumentPathsList(final WindowId windowId, final String documentIdsListStr)
	{
		if (documentIdsListStr == null || documentIdsListStr.isEmpty())
		{
			return ImmutableList.of();
		}

		return DocumentIdsSelection.ofCommaSeparatedString(documentIdsListStr)
				.stream()
				.map(documentId -> rootDocumentPath(windowId, documentId))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static DocumentPath includedDocumentPath(@NonNull final WindowId windowId, final String idStr, final String detailId, final String rowIdStr)
	{
		if (Check.isEmpty(detailId, true))
		{
			throw new IllegalArgumentException("No detailId provided");
		}
		if (Check.isEmpty(rowIdStr, true))
		{
			throw new IllegalArgumentException("No rowId provided");
		}

		return builder()
				.setDocumentType(windowId)
				.setDocumentId(idStr)
				.setDetailId(detailId)
				.setRowId(rowIdStr)
				.build();
	}

	public static DocumentPath includedDocumentPath(@NonNull final WindowId windowId, @NonNull final DocumentId documentId, @NonNull final DetailId detailId, @NonNull final DocumentId rowId)
	{
		return builder()
				.setDocumentType(windowId)
				.setDocumentId(documentId)
				.setDetailId(detailId)
				.setRowId(rowId)
				.build();
	}

	public static DocumentPath includedDocumentPath(@NonNull final WindowId windowId, @NonNull final DocumentId documentId, @NonNull final DetailId detailId, @NonNull final DocumentIdsSelection rowIds)
	{
		return builder()
				.setDocumentType(windowId)
				.setDocumentId(documentId)
				.setDetailId(detailId)
				.setRowIds(rowIds)
				.build();
	}

	public static DocumentPath includedDocumentPath(@NonNull final WindowId windowId, @NonNull final DocumentId documentId, @NonNull final DetailId detailId)
	{
		return builder()
				.setDocumentType(windowId)
				.setDocumentId(documentId)
				.setDetailId(detailId)
				.allowNullRowId()
				.build();
	}

	/**
	 * Creates the path of a single document (root document or included document).
	 */
	public static DocumentPath singleWindowDocumentPath(@NonNull final WindowId windowId, final DocumentId id, final DetailId detailId, final DocumentId rowId)
	{
		return builder()
				.setDocumentType(windowId)
				.setDocumentId(id)
				.setDetailId(detailId)
				.setRowId(rowId)
				.build();
	}

	private final DocumentType documentType;
	private final DocumentId documentTypeId;
	private final DocumentId documentId;
	private final DetailId detailId;
	private final DocumentIdsSelection rowIds;

	private transient Integer _hashcode; // lazy
	private transient String _toString; // lazy

	/**
	 * Root document constructor
	 */
	private DocumentPath(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
	{
		super();

		Preconditions.checkNotNull(documentType, "documentType shall not be null");
		Preconditions.checkNotNull(documentTypeId, "documentTypeId shall not be null");

		this.documentType = documentType;
		this.documentTypeId = documentTypeId;
		this.documentId = documentId;

		detailId = null;
		rowIds = DocumentIdsSelection.EMPTY;
	}

	/**
	 * Multiple rowIds constructor
	 */
	private DocumentPath(@NonNull final DocumentType documentType, @NonNull final DocumentId documentTypeId, final DocumentId documentId, final DetailId detailId, @NonNull final DocumentIdsSelection rowIds)
	{
		this.documentType = documentType;
		this.documentTypeId = documentTypeId;
		this.documentId = documentId;

		this.detailId = detailId;

		this.rowIds = rowIds;
	}

	/**
	 * Single rowId constructor
	 */
	private DocumentPath(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId, final DetailId detailId, final DocumentId singleRowId)
	{
		super();

		Preconditions.checkNotNull(documentType, "documentType shall not be null");
		Preconditions.checkNotNull(documentTypeId, "documentTypeId shall not be null");

		this.documentType = documentType;
		this.documentTypeId = documentTypeId;
		this.documentId = documentId;

		this.detailId = detailId;
		rowIds = DocumentIdsSelection.fromNullable(singleRowId);
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

			if (rowIds.isSingleDocumentId())
			{
				sb.append("/R").append(rowIds.getSingleDocumentId());
			}
			else if (!rowIds.isEmpty())
			{
				sb.append("/R").append(rowIds.toCommaSeparatedString());
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
		return Objects.equals(documentType, other.documentType)
				&& Objects.equals(documentTypeId, other.documentTypeId)
				&& Objects.equals(documentId, other.documentId)
				&& Objects.equals(detailId, other.detailId)
				&& Objects.equals(rowIds, other.rowIds);
	}

	public DocumentType getDocumentType()
	{
		return documentType;
	}

	public DocumentId getDocumentTypeId()
	{
		return documentTypeId;
	}

	public WindowId getWindowId()
	{
		Check.assume(documentType == DocumentType.Window, "Expect documentType={} for {}", DocumentType.Window, this);
		return WindowId.of(documentTypeId);
	}

	public WindowId getWindowIdOrNull()
	{
		return documentType == DocumentType.Window ? WindowId.of(documentTypeId) : null;
	}

	public AdWindowId getAdWindowIdOrNull()
	{
		if (documentType == DocumentType.Window)
		{
			return AdWindowId.ofRepoIdOrNull(documentTypeId.toIntOr(-1));
		}
		else
		{
			return null;
		}

	}

	public DocumentId getDocumentId()
	{
		return documentId;
	}

	/**
	 * @return true if the {@link #getDocumentId()} is the "new document" marker.
	 * @see {@link #isSingleNewIncludedDocument()} - to check if this is an included new document please use
	 */
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
		return rowIds.getSingleDocumentId();
	}

	public DocumentIdsSelection getRowIds()
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

		if (!rowIds.isSingleDocumentId())
		{
			return false;
		}

		final DocumentId singleRowId = rowIds.getSingleDocumentId();
		if (singleRowId.isNew())
		{
			return false;
		}

		return true;
	}

	public boolean isSingleNewIncludedDocument()
	{
		if (detailId == null)
		{
			return false;
		}

		if (!rowIds.isSingleDocumentId())
		{
			return false;
		}

		final DocumentId singleRowId = rowIds.getSingleDocumentId();
		if (!singleRowId.isNew())
		{
			return false;
		}

		return true;
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

	public boolean isComposedKey()
	{
		if (detailId == null)
		{
			return documentId != null && documentId.isComposedKey();
		}
		else
		{
			return rowIds != null
					&& rowIds.isSingleDocumentId()
					&& rowIds.getSingleDocumentId().isComposedKey();
		}
	}

	public DocumentPath createChildPath(final DetailId detailId, final DocumentId rowId)
	{
		if (detailId == null)
		{
			throw new IllegalArgumentException("detailId must be not empty");
		}

		if (rowId == null || rowId.isNew())
		{
			throw new IllegalArgumentException("new or null rowId is not accepted: " + rowId);
		}

		return new DocumentPath(documentType, documentTypeId, documentId, detailId, rowId);
	}

	public DocumentPath getRootDocumentPath()
	{
		if (isRootDocument())
		{
			return this;
		}
		return rootDocumentPath(documentType, documentTypeId, documentId);
	}

	public DocumentPath withDocumentId(final DocumentId id)
	{
		if (isRootDocument())
		{
			final DocumentId rootDocumentIdNew = id;
			if (Objects.equals(documentId, rootDocumentIdNew))
			{
				return this;
			}
			else
			{
				return new DocumentPath(documentType, documentTypeId, rootDocumentIdNew);
			}
		}
		else
		{
			final DocumentId rowIdNew = id;
			final DocumentId rowId = getSingleRowId();
			if (Objects.equals(rowId, rowIdNew))
			{
				return this;
			}
			else
			{
				return new DocumentPath(documentType, documentTypeId, documentId, detailId, rowIdNew);
			}
		}
	}

	//
	//
	//
	//
	//
	public static final class Builder
	{
		private DocumentType documentType;
		private DocumentId documentTypeId;
		private DocumentId documentId;
		private boolean documentId_allowNew = false;
		private DetailId detailId;
		private final Set<DocumentId> rowIds = new LinkedHashSet<>();
		private boolean rowId_allowNull = false;
		private boolean rowId_allowNew = false;

		private Builder()
		{
			super();
		}

		public DocumentPath build()
		{
			if (documentType == null)
			{
				throw new InvalidDocumentPathException("Invalid document type: " + documentType);
			}

			//
			// Validate documentTypeId
			if (documentTypeId == null)
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
			return new DocumentPath(documentType, documentTypeId, documentId, detailId, DocumentIdsSelection.of(rowIds));
		}

		public Builder setDocumentType(@NonNull final WindowId windowId)
		{
			setDocumentType(DocumentType.Window, windowId.toDocumentId());
			return this;
		}

		public Builder setDocumentType(@NonNull final DocumentType documentType, @NonNull final DocumentId documentTypeId)
		{
			this.documentType = documentType;
			this.documentTypeId = documentTypeId;
			return this;
		}

		public Builder setDocumentId(final String documentIdStr)
		{
			setDocumentId(DocumentId.ofStringOrEmpty(documentIdStr));
			return this;
		}

		public Builder setDocumentId(final DocumentId documentId)
		{
			this.documentId = documentId;
			return this;
		}

		public Builder allowNewDocumentId()
		{
			documentId_allowNew = true;
			return this;
		}

		public Builder setDetailId(final String detailIdStr)
		{
			setDetailId(DetailId.fromJson(detailIdStr));
			return this;
		}

		public Builder setDetailId(final DetailId detailId)
		{
			this.detailId = detailId;
			return this;
		}

		public Builder setRowId(final String rowIdStr)
		{
			final DocumentId rowId = DocumentId.ofStringOrEmpty(rowIdStr);
			setRowId(rowId);
			return this;
		}

		public Builder setRowId(@Nullable final DocumentId rowId)
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
			return setRowIds(DocumentIdsSelection.ofCommaSeparatedString(rowIdsListStr));
		}

		public Builder setRowIds(final DocumentIdsSelection rowIds)
		{
			this.rowIds.clear();
			this.rowIds.addAll(rowIds.toSet());

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
