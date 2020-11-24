package de.metas.ui.web.window.model;

import java.util.Optional;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.Document.OnValidStatusChanged;
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

public final class HighVolumeReadonlyIncludedDocumentsCollection implements IIncludedDocumentsCollection
{
	private final Document parentDocument;
	private final DocumentEntityDescriptor entityDescriptor;

	private static final LogicExpressionResult RESULT_TabReadOnly = LogicExpressionResult.namedConstant("Tab is readonly", false);

	public HighVolumeReadonlyIncludedDocumentsCollection(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		this.parentDocument = parentDocument;
		this.entityDescriptor = entityDescriptor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("detailId", entityDescriptor.getDetailId())
				.toString();
	}

	@Override
	public IIncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return this;
	}

	@Override
	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}

	@Override
	public OrderedDocumentsList getDocuments(final DocumentQueryOrderByList orderBys)
	{
		return DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.setOrderBys(orderBys)
				.retriveDocuments();
	}

	@Override
	public OrderedDocumentsList getDocumentsByIds(@NonNull final DocumentIdsSelection documentIds)
	{
		if (documentIds.isAll())
		{
			return getDocuments(DocumentQueryOrderByList.EMPTY);
		}
		else if (documentIds.isEmpty())
		{
			return OrderedDocumentsList.newEmpty();
		}
		else
		{
			final ImmutableMap<DocumentId, Document> loadedDocuments = DocumentQuery.builder(entityDescriptor)
					.setParentDocument(parentDocument)
					.setRecordIds(documentIds.toSet())
					.setChangesCollector(NullDocumentChangesCollector.instance)
					.setOrderBys(DocumentQueryOrderByList.EMPTY)
					.retriveDocuments()
					.toImmutableMap();

			final OrderedDocumentsList result = OrderedDocumentsList.newEmpty();
			for (final DocumentId documentId : documentIds.toSet())
			{
				final Document loadedDocument = loadedDocuments.get(documentId);
				if (loadedDocument != null)
				{
					result.addDocument(loadedDocument);
				}
				else
				{
					// No document found for documentId. Ignore it.
				}
			}

			return result;
		}
	}

	@Override
	public Optional<Document> getDocumentById(final DocumentId documentId)
	{
		final Document document = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setRecordId(documentId)
				.retriveDocumentOrNull();
		if (document == null)
		{
			return Optional.empty();
		}

		return Optional.of(document);
	}

	@Override
	public void updateStatusFromParent()
	{
		// nothing
	}

	@Override
	public void assertNewDocumentAllowed()
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabReadOnly.getName());
	}

	@Override
	public LogicExpressionResult getAllowCreateNewDocument()
	{
		return RESULT_TabReadOnly;
	}

	@Override
	public Document createNewDocument()
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabReadOnly.getName());
	}

	@Override
	public LogicExpressionResult getAllowDeleteDocument()
	{
		return RESULT_TabReadOnly;
	}

	@Override
	public void deleteDocuments(final DocumentIdsSelection documentIds)
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabReadOnly.getName());
	}

	@Override
	public DocumentValidStatus checkAndGetValidStatus(final OnValidStatusChanged onValidStatusChanged)
	{
		return DocumentValidStatus.documentValid();
	}

	@Override
	public boolean hasChangesRecursivelly()
	{
		return false;
	}

	@Override
	public void saveIfHasChanges()
	{
	}

	@Override
	public void markStaleAll()
	{
	}

	@Override
	public void markStale(final DocumentIdsSelection rowIds)
	{
	}

	@Override
	public boolean isStale()
	{
		return false;
	}

	@Override
	public int getNextLineNo()
	{
		throw new UnsupportedOperationException();
	}
}
