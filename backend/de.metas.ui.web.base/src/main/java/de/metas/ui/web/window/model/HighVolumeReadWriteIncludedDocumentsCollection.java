package de.metas.ui.web.window.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.Document.OnValidStatusChanged;
import lombok.AllArgsConstructor;
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

public class HighVolumeReadWriteIncludedDocumentsCollection implements IIncludedDocumentsCollection
{
	public static final HighVolumeReadWriteIncludedDocumentsCollection newInstance(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		final HighVolumeReadWriteIncludedDocumentsCollection col = new HighVolumeReadWriteIncludedDocumentsCollection(parentDocument, entityDescriptor);
		col.updateStatusFromParent();
		return col;
	}

	private static final transient Logger logger = LogManager.getLogger(HighVolumeReadWriteIncludedDocumentsCollection.class);

	private final DocumentEntityDescriptor entityDescriptor;
	private final Document parentDocument;
	private final DetailId detailId;
	private final DocumentPath parentDocumentPath;
	private final LinkedHashMap<DocumentId, Document> _documentsWithChanges;

	private final IncludedDocumentsCollectionActions actions;
	private final ActionsContext actionsContext = new ActionsContext();
	private DocumentReadonly parentReadonly = null;
	private boolean staled = false;

	private HighVolumeReadWriteIncludedDocumentsCollection(@NonNull final Document parentDocument, @NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		this.parentDocument = parentDocument;
		parentDocumentPath = parentDocument.getDocumentPath();
		detailId = entityDescriptor.getDetailId();
		this.entityDescriptor = entityDescriptor;

		_documentsWithChanges = new LinkedHashMap<>();

		actions = IncludedDocumentsCollectionActions.builder()
				.parentDocumentPath(parentDocumentPath)
				.detailId(detailId)
				.allowCreateNewLogic(entityDescriptor.getAllowCreateNewLogic())
				.allowDeleteLogic(entityDescriptor.getAllowDeleteLogic())
				.build();
		parentReadonly = null; // NOTE: don't fetch it from parentDocument because it's not needed now
		staled = false;
	}

	/** copy constructor */
	private HighVolumeReadWriteIncludedDocumentsCollection(
			@NonNull final HighVolumeReadWriteIncludedDocumentsCollection from,
			@NonNull final Document parentDocumentCopy,
			@NonNull final CopyMode copyMode)
	{
		parentDocument = parentDocumentCopy;
		parentDocumentPath = from.parentDocumentPath;
		detailId = from.detailId;
		entityDescriptor = from.entityDescriptor;

		_documentsWithChanges = new LinkedHashMap<>(Maps.transformValues(from._documentsWithChanges, includedDocumentOrig -> includedDocumentOrig.copy(parentDocumentCopy, copyMode)));

		actions = from.actions.copy();
		parentReadonly = from.parentReadonly;
		staled = from.staled;
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.add("parentDocumentPath", parentDocumentPath)
				.add("detailId", detailId)
				.toString();
	}

	private final void assertWritable()
	{
		parentDocument.assertWritable();
	}

	private final void addChangedDocument(final Document document)
	{
		final DocumentId documentId = document.getDocumentId();
		_documentsWithChanges.put(documentId, document);
	}

	private final void forgetChangedDocument(final DocumentId documentId)
	{
		_documentsWithChanges.remove(documentId);
	}

	private final Collection<Document> getChangedDocuments()
	{
		return _documentsWithChanges.values();
	}

	private final Document getChangedDocumentOrNull(final DocumentId documentId)
	{
		return _documentsWithChanges.get(documentId);
	}

	private final Map<DocumentId, Document> getInnerDocumentsWithChanges()
	{
		return _documentsWithChanges;
	}

	@Override
	public DetailId getDetailId()
	{
		return detailId;
	}

	@Override
	public IIncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new HighVolumeReadWriteIncludedDocumentsCollection(this, parentDocumentCopy, copyMode);
	}

	@Override
	public OrderedDocumentsList getDocuments(final DocumentQueryOrderByList orderBys)
	{
		final Map<DocumentId, Document> documentsWithChanges = new LinkedHashMap<>(getInnerDocumentsWithChanges());
		final OrderedDocumentsList documents = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setExistingDocumentsSupplier(documentsWithChanges::remove)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.setOrderBys(orderBys)
				.retriveDocuments();

		// Add the remaining documents with changes if any
		// i.e. those documents which are new and never saved in database.
		if (!documentsWithChanges.isEmpty())
		{
			documents.addDocuments(documentsWithChanges.values());
		}

		staled = false;

		return documents;
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
			final Map<DocumentId, Document> documentsWithChanges = new LinkedHashMap<>(getInnerDocumentsWithChanges());
			final SetView<DocumentId> documentIdsToLoad = Sets.difference(documentIds.toSet(), documentsWithChanges.keySet());

			final ImmutableMap<DocumentId, Document> loadedDocuments;
			if (!documentIdsToLoad.isEmpty())
			{
				loadedDocuments = DocumentQuery.builder(entityDescriptor)
						.setParentDocument(parentDocument)
						.setRecordIds(documentIdsToLoad)
						.setChangesCollector(NullDocumentChangesCollector.instance)
						.setOrderBys(DocumentQueryOrderByList.EMPTY)
						.retriveDocuments()
						.toImmutableMap();
			}
			else
			{
				loadedDocuments = ImmutableMap.of();
			}

			final OrderedDocumentsList result = OrderedDocumentsList.newEmpty();
			for (final DocumentId documentId : documentIds.toSet())
			{
				final Document documentWithChanges = documentsWithChanges.get(documentId);
				if (documentWithChanges != null)
				{
					result.addDocument(documentWithChanges);
					continue;
				}

				final Document loadedDocument = loadedDocuments.get(documentId);
				if (loadedDocument != null)
				{
					result.addDocument(loadedDocument);
					continue;
				}

				// No document found for documentId. Ignore it.
			}

			return result;
		}
	}

	@Override
	public Optional<Document> getDocumentById(@NonNull final DocumentId documentId)
	{
		// Try documents which are new and/or have changes in progress, but are not yet saved
		final Document documentWithChanges = getChangedDocumentOrNull(documentId);
		if (documentWithChanges != null)
		{
			return Optional.of(documentWithChanges);
		}

		// Retrieve from repository
		final Document documentRetrieved = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setRecordId(documentId)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.retriveDocumentOrNull();
		if (documentRetrieved == null)
		{
			return Optional.empty();
		}

		return Optional.of(documentRetrieved.copy(parentDocument, parentDocument.isWritable() ? CopyMode.CheckOutWritable : CopyMode.CheckInReadonly));
	}

	@Override
	public void updateStatusFromParent()
	{
		setParentReadonlyAndCollect(parentDocument.getReadonly());

		actions.updateAndGetAllowCreateNewDocument(actionsContext);
		actions.updateAndGetAllowDeleteDocument(actionsContext);
	}

	/**
	 * Sets parent's readonly logic.
	 *
	 * If parent readonly status changed then a "collectStaleDetail" will be issued.
	 * In that case, all documents from here are changed, so it's better invalidate the tab/detail.
	 *
	 * @param parentReadonlyNew
	 */
	private void setParentReadonlyAndCollect(final DocumentReadonly parentReadonlyNew)
	{
		final DocumentReadonly parentReadonlyOld = parentReadonly; // might be null
		parentReadonly = parentReadonlyNew;
		if (parentReadonlyOld == null || !parentReadonlyNew.equals(parentReadonlyOld))
		{
			parentDocument.getChangesCollector().collectStaleDetailId(parentDocumentPath, detailId);
		}

		getChangedDocuments().forEach(changedDocument -> changedDocument.setParentReadonly(parentReadonlyNew));
	}

	@Override
	public void assertNewDocumentAllowed()
	{
		actions.updateAndAssertAlowCreateNew(actionsContext);
	}

	@Override
	public LogicExpressionResult getAllowCreateNewDocument()
	{
		return actions.getAllowCreateNewDocument();
	}

	@Override
	public LogicExpressionResult getAllowDeleteDocument()
	{
		return actions.getAllowDeleteDocument();
	}

	@Override
	public Document createNewDocument()
	{
		assertWritable();
		assertNewDocumentAllowed();

		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		final IDocumentChangesCollector changesCollector = parentDocument.getChangesCollector();
		final Document document = documentsRepository.createNewDocument(entityDescriptor, parentDocument, changesCollector);

		// Collect all changes to make sure the whole document content will be provided to frontend
		changesCollector.collectFrom(document, () -> "new included document");

		addChangedDocument(document);

		return document;
	}

	@Override
	public void deleteDocuments(@NonNull final DocumentIdsSelection documentIds)
	{
		if (documentIds.isEmpty())
		{
			throw new AdempiereException("At least one rowId shall be specified when deleting included documents");
		}

		assertWritable();

		final List<Document> deletedDocuments = new ArrayList<>();
		for (final DocumentId documentId : documentIds.toSet())
		{
			final Document document = getDocumentById(documentId).orElse(null);
			if (document == null)
			{
				// SKIP if document was not found. It's pointless to fail here
				logger.warn("Skip deleting {} because document was not found", documentId);
				continue;
			}

			// assertDeleteDocumentAllowed(document);

			// Delete it from underlying repository (if it's present there)
			if (!document.isNew())
			{
				document.deleteFromRepository();
			}

			document.markAsDeleted();
			forgetChangedDocument(documentId);

			deletedDocuments.add(document);
		}

		// FIXME: workaround until https://github.com/metasfresh/metasfresh-webui-api/issues/19 is implemented
		// Case: an C_OrderLine is deleted and some other order lines are updated because of that.
		// We need to invalidate them...
		if (!deletedDocuments.isEmpty())
		{
			markStaleAll();
		}
	}

	@Override
	public DocumentValidStatus checkAndGetValidStatus(final OnValidStatusChanged onValidStatusChanged)
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

	@Override
	public void onChildSaved(final Document document)
	{
		if (!document.hasChangesRecursivelly())
		{
			forgetChangedDocument(document.getDocumentId());
		}
	}

	@Override
	public void onChildChanged(final Document document)
	{
		// NOTE: we assume the document has changes
		// if(document.hasChangesRecursivelly())

		addChangedDocument(document);
	}

	@Override
	public void markStaleAll()
	{
		staled = true;
		parentDocument.getChangesCollector().collectStaleDetailId(parentDocumentPath, detailId);
	}

	@Override
	public void markStale(@NonNull final DocumentIdsSelection rowIds)
	{
		// TODO: implement staling only given rowId
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
		final int lastLineNo = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setExistingDocumentsSupplier(this::getChangedDocumentOrNull)
				.setChangesCollector(NullDocumentChangesCollector.instance)
				.retrieveLastLineNo();
		final int nextLineNo = lastLineNo / 10 * 10 + 10;
		return nextLineNo;
	}

	//
	//
	//
	@AllArgsConstructor
	private final class ActionsContext implements IncludedDocumentsCollectionActionsContext
	{
		@Override
		public boolean isParentDocumentProcessed()
		{
			return parentDocument.isProcessed();
		}

		@Override
		public boolean isParentDocumentActive()
		{
			return parentDocument.isActive();
		}

		@Override
		public boolean isParentDocumentNew()
		{
			return parentDocument.isNew();
		}

		@Override
		public boolean isParentDocumentInvalid()
		{
			return !parentDocument.getValidStatus().isValid();
		}

		@Override
		public Collection<Document> getIncludedDocuments()
		{
			return getChangedDocuments();
		}

		@Override
		public Evaluatee toEvaluatee()
		{
			return parentDocument.asEvaluatee();
		}

		@Override
		public void collectAllowNew(final DocumentPath parentDocumentPath, final DetailId detailId, final LogicExpressionResult allowNew)
		{
			parentDocument.getChangesCollector().collectAllowNew(parentDocumentPath, detailId, allowNew);
		}

		@Override
		public void collectAllowDelete(final DocumentPath parentDocumentPath, final DetailId detailId, final LogicExpressionResult allowDelete)
		{
			parentDocument.getChangesCollector().collectAllowDelete(parentDocumentPath, detailId, allowDelete);
		}
	}
}
