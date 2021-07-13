package de.metas.ui.web.window.model;

import java.util.Optional;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
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
 * Copyright (C) 2019 metas GmbH
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

public class SingleRowDetailIncludedDocumentsCollection implements IIncludedDocumentsCollection
{

	private static final Logger logger = LogManager.getLogger(SingleRowDetailIncludedDocumentsCollection.class);

	private final Document parentDocument;
	private final DocumentEntityDescriptor entityDescriptor;

	private Document singleDocument;

	private boolean staled;

	private DocumentPath parentDocumentPath;

	private static final LogicExpressionResult RESULT_TabSingleRowDetail = LogicExpressionResult.namedConstant("Tab is single row", false);

	public SingleRowDetailIncludedDocumentsCollection(
			@NonNull final Document parentDocument,
			@NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		this.parentDocument = parentDocument;
		this.entityDescriptor = entityDescriptor;
		this.parentDocumentPath = parentDocument.getDocumentPath();
		this.staled = false;
		this.singleDocument = null;
	}

	/** copy constructor */
	private SingleRowDetailIncludedDocumentsCollection(
			@NonNull final SingleRowDetailIncludedDocumentsCollection from,
			@NonNull final Document parentDocumentCopy)
	{
		this.parentDocument = parentDocumentCopy;
		this.parentDocumentPath = from.parentDocumentPath;
		this.entityDescriptor = from.entityDescriptor;

		this.singleDocument = from.singleDocument;

		this.staled = from.staled;
	}

	@Override
	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}

	@Override
	public IIncludedDocumentsCollection copy(@NonNull final Document parentDocumentCopy, @NonNull final CopyMode copyMode_IGNORED)
	{
		return new SingleRowDetailIncludedDocumentsCollection(this, parentDocumentCopy);
	}

	@Override
	public OrderedDocumentsList getDocuments(@NonNull final DocumentQueryOrderByList orderBys)
	{
		final Document document = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.setOrderBys(orderBys)
				.retriveDocumentOrNull();
		setSingleDocument(document);

		return OrderedDocumentsList.of(ImmutableList.of(document), orderBys);
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
			final ImmutableMap<DocumentId, Document> loadedDocuments = getDocuments(DocumentQueryOrderByList.EMPTY).toImmutableMap();

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
	public Optional<Document> getDocumentById(@NonNull final DocumentId documentId_NOTUSED)
	{
		// Try documents which are new and/or have changes in progress, but are not yet saved
		final Document singleDocument = getSingleDocumentOrNull();
		if (singleDocument != null)
		{
			return Optional.of(singleDocument);
		}

		final Document document = DocumentQuery
				.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.retriveDocumentOrNull();
		setSingleDocument(document);

		return Optional.of(document);
	}

	private final Document getSingleDocumentOrNull()
	{
		return singleDocument;
	}

	private final void setSingleDocument(@NonNull final Document document)
	{
		singleDocument = document;
	}

	@Override
	public void updateStatusFromParent()
	{
		// nothing
	}

	@Override
	public void assertNewDocumentAllowed()
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabSingleRowDetail.getName());

	}

	@Override
	public LogicExpressionResult getAllowCreateNewDocument()
	{
		return RESULT_TabSingleRowDetail;
	}

	@Override
	public LogicExpressionResult getAllowDeleteDocument()
	{
		return RESULT_TabSingleRowDetail;
	}

	@Override
	public Document createNewDocument()
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabSingleRowDetail.getName());
	}

	@Override
	public void deleteDocuments(DocumentIdsSelection documentIds)
	{
		throw new InvalidDocumentStateException(parentDocument, RESULT_TabSingleRowDetail.getName());
	}

	@Override
	public DocumentValidStatus checkAndGetValidStatus(OnValidStatusChanged onValidStatusChanged)
	{
		if (singleDocument != null)
		{
			final DocumentValidStatus validState = singleDocument.checkAndGetValidStatus(onValidStatusChanged);
			if (!validState.isValid())
			{
				logger.trace("Considering included document as invalid for saving because it is not valid; validState={}; document={}",
						validState, singleDocument);
				return validState;
			}
		}
		return DocumentValidStatus.documentValid();
	}

	@Override
	public boolean hasChangesRecursivelly()
	{
		if (singleDocument == null)
		{
			return false;
		}
		return singleDocument.hasChangesRecursivelly();
	}

	@Override
	public void saveIfHasChanges()
	{
		if (singleDocument != null)
		{
			final DocumentSaveStatus saveStatus = singleDocument.saveIfHasChanges();
			if (saveStatus.isSaved())
			{
				forgetSingleDocument();
			}
			else if (saveStatus.isDeleted())
			{
				singleDocument.markAsDeleted();
				forgetSingleDocument();
			}
		}
	}

	private final void forgetSingleDocument()
	{
		singleDocument = null;
	}

	@Override
	public void markStaleAll()
	{
		staled = true;
		parentDocument.getChangesCollector().collectStaleDetailId(parentDocumentPath, getDetailId());
	}

	@Override
	public void markStale(final DocumentIdsSelection rowIds)
	{
		markStaleAll();
	}

	@Override
	public boolean isStale()
	{
		return staled;
	}

	@Override
	public int getNextLineNo()
	{
		return 0;
	}

}
