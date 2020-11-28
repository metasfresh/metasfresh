package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nullable;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.util.Check;
import lombok.NonNull;

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

/**
 * Document query.
 *
 * NOTE: this is not serializable, so please DO NOT CACHE IT
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class DocumentQuery
{
	public static Builder builder(final DocumentEntityDescriptor entityDescriptor)
	{
		return new Builder(entityDescriptor);
	}

	public static Builder ofRecordId(final DocumentEntityDescriptor entityDescriptor, final DocumentId recordId)
	{
		return builder(entityDescriptor)
				.setRecordId(recordId)
				.noSorting();
	}

	private final DocumentEntityDescriptor entityDescriptor;
	private final ImmutableSet<DocumentId> recordIds;
	private final Document parentDocument;

	private final DocumentFilterList filters;

	private final boolean noSorting;
	private final DocumentQueryOrderByList orderBys;

	private final int firstRow;
	private final int pageLength;

	private final Function<DocumentId, Document> existingDocumentsSupplier;

	private DocumentQuery(final Builder builder)
	{
		entityDescriptor = builder.entityDescriptor; // not null
		recordIds = ImmutableSet.copyOf(builder.recordIds);
		parentDocument = builder.parentDocument;

		filters = DocumentFilterList.ofList(builder.filters);

		noSorting = builder.isNoSorting();
		orderBys = builder.getOrderBysEffective();

		firstRow = builder.firstRow;
		pageLength = builder.pageLength;

		existingDocumentsSupplier = builder.existingDocumentsSupplier;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tableName", entityDescriptor.getTableNameOrNull())
				.add("recordIds", recordIds.isEmpty() ? null : recordIds)
				.add("parentDocument", parentDocument)
				.add("filters", filters.isEmpty() ? null : filters)
				.add("firstRow", firstRow > 0 ? firstRow : null)
				.add("pageLength", pageLength > 0 ? pageLength : null)
				.add("noSorting", noSorting ? Boolean.TRUE : null)
				.toString();
	}

	public DocumentEntityDescriptor getEntityDescriptor()
	{
		return entityDescriptor;
	}

	public ImmutableSet<DocumentId> getRecordIds()
	{
		return recordIds;
	}

	public Document getParentDocument()
	{
		return parentDocument;
	}

	public Integer getParentLinkIdAsInt()
	{
		return parentDocument == null ? null : parentDocument.getDocumentIdAsInt();
	}

	public boolean isParentLinkIdSet()
	{
		return parentDocument != null;
	}

	public DocumentFilterList getFilters()
	{
		return filters;
	}

	public boolean isNoSorting()
	{
		return noSorting;
	}

	public DocumentQueryOrderByList getOrderBys()
	{
		return orderBys;
	}

	public int getFirstRow()
	{
		return firstRow;
	}

	public int getPageLength()
	{
		return pageLength;
	}

	public Function<DocumentId, Document> getExistingDocumentsSupplier()
	{
		return existingDocumentsSupplier;
	}

	public static final class Builder
	{
		private final DocumentEntityDescriptor entityDescriptor;
		private Document parentDocument;
		private Set<DocumentId> recordIds = ImmutableSet.of();
		public ArrayList<DocumentFilter> filters = null;

		private boolean _noSorting = false; // always false by default
		public ArrayList<DocumentQueryOrderBy> _orderBys = null;

		private int firstRow = -1;
		private int pageLength = -1;

		private Function<DocumentId, Document> existingDocumentsSupplier = null;
		private IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull(); // TODO: for legacy reason we are calling Execution.getCurrent.. ... but this shall be removed!

		private Builder(final DocumentEntityDescriptor entityDescriptor)
		{
			this.entityDescriptor = Preconditions.checkNotNull(entityDescriptor);
		}

		public DocumentQuery build()
		{
			return new DocumentQuery(this);
		}

		public Document retriveDocumentOrNull()
		{
			final DocumentQuery query = build();
			final DocumentsRepository documentsRepository = getDocumentsRepository();
			return documentsRepository.retrieveDocument(query, changesCollector);
		}

		public OrderedDocumentsList retriveDocuments()
		{
			final DocumentQuery query = build();
			final DocumentsRepository documentsRepository = getDocumentsRepository();
			return documentsRepository.retrieveDocuments(query, changesCollector);
		}

		/**
		 * Retrieves parent's {@link DocumentId} for a child document identified by given query.
		 *
		 * @param parentEntityDescriptor parent descriptor
		 */
		public DocumentId retrieveParentDocumentId(final DocumentEntityDescriptor parentEntityDescriptor)
		{
			final DocumentQuery query = build();
			final DocumentsRepository documentsRepository = getDocumentsRepository();
			return documentsRepository.retrieveParentDocumentId(parentEntityDescriptor, query);
		}

		public int retrieveLastLineNo()
		{
			final DocumentQuery query = build();
			final DocumentsRepository documentsRepository = getDocumentsRepository();
			return documentsRepository.retrieveLastLineNo(query);
		}

		private DocumentsRepository getDocumentsRepository()
		{
			return entityDescriptor.getDataBinding().getDocumentsRepository();
		}

		public Builder setRecordId(@Nullable final DocumentId documentId)
		{
			recordIds = documentId != null
					? ImmutableSet.of(documentId)
					: ImmutableSet.of();

			return this;
		}

		public Builder setRecordIds(@Nullable final Collection<DocumentId> documentIds)
		{
			recordIds = documentIds != null
					? ImmutableSet.copyOf(documentIds)
					: ImmutableSet.of();

			return this;
		}

		public Builder setParentDocument(final Document parentDocument)
		{
			this.parentDocument = parentDocument;
			return this;
		}

		public Builder noSorting()
		{
			_noSorting = true;
			_orderBys = null;
			return this;
		}

		public boolean isNoSorting()
		{
			return _noSorting;
		}

		public Builder addOrderBy(@NonNull final DocumentQueryOrderBy orderBy)
		{
			Check.assume(!_noSorting, "sorting not disabled for {}", this);

			if (_orderBys == null)
			{
				_orderBys = new ArrayList<>();
			}
			_orderBys.add(orderBy);
			return this;
		}

		public Builder setOrderBys(final DocumentQueryOrderByList orderBys)
		{
			if (orderBys == null || orderBys.isEmpty())
			{
				_orderBys = null;
			}
			else
			{
				_orderBys = new ArrayList<>(orderBys.toList());
			}
			return this;
		}

		private DocumentQueryOrderByList getOrderBysEffective()
		{
			return _noSorting
					? DocumentQueryOrderByList.EMPTY
					: DocumentQueryOrderByList.ofList(_orderBys);
		}

		public Builder setFirstRow(final int firstRow)
		{
			this.firstRow = firstRow;
			return this;
		}

		public Builder setPageLength(final int pageLength)
		{
			this.pageLength = pageLength;
			return this;
		}

		public Builder setExistingDocumentsSupplier(final Function<DocumentId, Document> existingDocumentsSupplier)
		{
			this.existingDocumentsSupplier = existingDocumentsSupplier;
			return this;
		}

		public Builder setChangesCollector(IDocumentChangesCollector changesCollector)
		{
			this.changesCollector = changesCollector;
			return this;
		}
	}
}
