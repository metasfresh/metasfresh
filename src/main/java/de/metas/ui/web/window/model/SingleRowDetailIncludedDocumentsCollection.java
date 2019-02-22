package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

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

	private final LinkedHashMap<DocumentId, Document> documentsWithChanges;

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
		this.documentsWithChanges = new LinkedHashMap<>();
	}

	/** copy constructor */
	private SingleRowDetailIncludedDocumentsCollection(
			@NonNull final SingleRowDetailIncludedDocumentsCollection from,
			@NonNull final Document parentDocumentCopy,
			@NonNull final CopyMode copyMode)
	{
		this.parentDocument = parentDocumentCopy;
		this.parentDocumentPath = from.parentDocumentPath;
		this.entityDescriptor = from.entityDescriptor;

		this.documentsWithChanges = new LinkedHashMap<>(Maps.transformValues(from.documentsWithChanges, includedDocumentOrig -> includedDocumentOrig.copy(parentDocumentCopy, copyMode)));

		this.staled = from.staled;
	}

	@Override
	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}

	@Override
	public IIncludedDocumentsCollection copy(@NonNull final Document parentDocumentCopy, @NonNull final CopyMode copyMode)
	{
		return new SingleRowDetailIncludedDocumentsCollection(this, parentDocumentCopy, copyMode);
	}

	@Override
	public OrderedDocumentsList getDocuments(@NonNull final List<DocumentQueryOrderBy> orderBys)
	{
		final Document document = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.setOrderBys(orderBys)
				.retriveDocumentOrNull();

		if (document.isNew())
		{
			addChangedDocument(document);
		}

		return OrderedDocumentsList.of(ImmutableList.of(document), orderBys);
	}

	@Override
	public Document getDocumentById(@NonNull final DocumentId documentId)
	{
		// Try documents which are new and/or have changes in progress, but are not yet saved
		final Document documentWithChanges = getChangedDocumentOrNull(documentId);
		if (documentWithChanges != null)
		{
			return documentWithChanges;
		}
		final Document document = DocumentQuery
				.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setRecordId(documentId)
				.retriveDocumentOrNull();
		if (document.isNew())
		{
			addChangedDocument(document);
		}

		return document;
	}

	private final Document getChangedDocumentOrNull(@NonNull final DocumentId documentId)
	{
		return documentsWithChanges.get(documentId);
	}

	private final void addChangedDocument(@NonNull final Document document)
	{
		final DocumentId documentId = document.getDocumentId();
		documentsWithChanges.put(documentId, document);
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
		for (final Document document : getChangedDocuments())
		{
			final DocumentValidStatus validState = document.checkAndGetValidStatus(onValidStatusChanged);
			if (!validState.isValid())
			{
				logger.trace("Considering included documents collection {} as invalid for saving because {} is not valid", this, document, validState);
				return validState;
			}
		}
		return DocumentValidStatus.documentValid();
	}

	private final Collection<Document> getChangedDocuments()
	{
		return documentsWithChanges.values();
	}

	@Override
	public boolean hasChangesRecursivelly()
	{
		return getChangedDocuments()
				.stream()
				.anyMatch(document -> document.hasChangesRecursivelly());
	}

	@Override
	public void saveIfHasChanges()
	{
		final Set<DocumentId> savedOrDeletedDocumentIds = new HashSet<>();

		for (final Document document : getChangedDocuments())
		{
			final DocumentSaveStatus saveStatus = document.saveIfHasChanges();
			if (saveStatus.isSaved())
			{
				savedOrDeletedDocumentIds.add(document.getDocumentId());
			}
			else if (saveStatus.isDeleted())
			{
				document.markAsDeleted();
				savedOrDeletedDocumentIds.add(document.getDocumentId());
			}
		}

		if (!savedOrDeletedDocumentIds.isEmpty())
		{
			savedOrDeletedDocumentIds.forEach(this::forgetChangedDocument);
		}
	}

	private final void forgetChangedDocument(final DocumentId documentId)
	{
		documentsWithChanges.remove(documentId);
	}

	@Override
	public void markStaleAll()
	{
		staled = true;
		parentDocument.getChangesCollector().collectStaleDetailId(parentDocumentPath, getDetailId());
	}

	@Override
	public void markStale(DocumentId rowId)
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
