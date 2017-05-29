package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.compiere.util.Evaluatee;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.Document.OnValidStatusChanged;
import lombok.AllArgsConstructor;
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
 * @author metas-dev <dev@metasfresh.com>
 *
 * @deprecated Scheduled to be deleted because we will no longer using it
 */
@Deprecated
public class IncludedDocumentsCollection implements IIncludedDocumentsCollection
{
	public static final IncludedDocumentsCollection newInstance(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		final IncludedDocumentsCollection col = new IncludedDocumentsCollection(parentDocument, entityDescriptor);
		col.updateStatusFromParent();
		return col;
	}

	private static final transient Logger logger = LogManager.getLogger(IncludedDocumentsCollection.class);

	private final DocumentEntityDescriptor entityDescriptor;
	private final Document parentDocument;

	private final LinkedHashMap<DocumentId, Document> _documents;

	// State
	private boolean _fullyLoaded;
	private final Set<DocumentId> _staleDocumentIds;
	private final IncludedDocumentsCollectionActions actions;
	private final ActionsContext actionsContext = new ActionsContext();

	private IncludedDocumentsCollection(@NonNull final Document parentDocument, @NonNull final DocumentEntityDescriptor entityDescriptor)
	{
		this.parentDocument = parentDocument;
		this.entityDescriptor = entityDescriptor;

		// State
		_fullyLoaded = false;
		_staleDocumentIds = new HashSet<>();
		actions = IncludedDocumentsCollectionActions.builder()
				.parentDocumentPath(parentDocument.getDocumentPath())
				.detailId(entityDescriptor.getDetailId())
				.allowCreateNewLogic(entityDescriptor.getAllowCreateNewLogic())
				.allowDeleteLogic(entityDescriptor.getAllowDeleteLogic())
				.build();

		// Documents map
		_documents = new LinkedHashMap<>();
	}

	/** copy constructor */
	private IncludedDocumentsCollection(@NonNull final IncludedDocumentsCollection from, @NonNull final Document parentDocumentCopy, @NonNull final CopyMode copyMode)
	{
		parentDocument = parentDocumentCopy;
		entityDescriptor = from.entityDescriptor;

		// State
		_fullyLoaded = from._fullyLoaded;
		_staleDocumentIds = new HashSet<>(from._staleDocumentIds);
		actions = from.actions.copy();

		// Deep-copy documents map
		_documents = new LinkedHashMap<>(Maps.transformValues(from._documents, includedDocumentOrig -> includedDocumentOrig.copy(parentDocumentCopy, copyMode)));
	}

	@Override
	public String toString()
	{
		// NOTE: keep it short
		return MoreObjects.toStringHelper(this)
				.add("detailId", entityDescriptor.getDetailId())
				.add("documentsCount", _documents.size())
				.toString();
	}

	private DocumentPath getParentDocumentPath()
	{
		return parentDocument.getDocumentPath();
	}

	private IDocumentChangesCollector getChangesCollector()
	{
		return parentDocument.getChangesCollector();
	}

	private final void assertWritable()
	{
		parentDocument.assertWritable();
	}

	private final boolean isFullyLoaded()
	{
		return _fullyLoaded;
	}

	private final void markFullyLoaded()
	{
		_fullyLoaded = true;
	}

	private final void markNotFullyLoaded()
	{
		_fullyLoaded = false;
	}

	@Override
	public final boolean isStale()
	{
		return !_staleDocumentIds.isEmpty();
	}

	private final boolean isStale(final DocumentId documentId)
	{
		if (_staleDocumentIds.contains(documentId))
		{
			return true;
		}

		return false;
	}

	private final void markNotStale()
	{
		_staleDocumentIds.clear();
	}

	private final void markNotStale(final DocumentId documentId)
	{
		if (documentId == null)
		{
			throw new NullPointerException("documentId cannot be null");
		}
		_staleDocumentIds.remove(documentId);
	}

	@Override
	public final void markStaleAll()
	{
		markNotFullyLoaded();
		_staleDocumentIds.addAll(_documents.keySet());

		final IDocumentChangesCollector changesCollector = getChangesCollector();
		changesCollector.collectStaleDetailId(getParentDocumentPath(), getDetailId());
	}

	@Override
	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}

	@Override
	public synchronized Document getDocumentById(final DocumentId documentId)
	{
		if (documentId == null || documentId.isNew())
		{
			throw new InvalidDocumentPathException("Actual ID was expected instead of '" + documentId + "'");
		}

		//
		// Check loaded collection
		final Document documentExisting = _documents.get(documentId);
		if (documentExisting != null)
		{
			refreshStaleDocumentIfPossible(documentExisting);
			return documentExisting;
		}
		else
		{
			if (logger.isTraceEnabled())
			{
				logger.trace("No document with id '{}' was found in local documents. \nAvailable IDs are: {}", documentId, _documents.keySet());
			}
		}

		//
		// Load from underlying repository
		final Document documentNew = DocumentQuery.builder(entityDescriptor)
				.setRecordId(documentId)
				.setParentDocument(parentDocument)
				.retriveDocumentOrNull();
		if (documentNew == null)
		{
			final DocumentPath documentPath = getParentDocumentPath()
					.createChildPath(getDetailId(), documentId);
			throw new DocumentNotFoundException(documentPath);
		}

		//
		// Put the document to our documents map
		// and update the status
		_documents.put(documentId, documentNew);
		markNotStale(documentId);
		// FullyLoaded: we just loaded and added a document to our collection
		// => for sure this was/is not fully loaded
		markNotFullyLoaded();

		// Done
		return documentNew;
	}

	private void refreshStaleDocumentIfPossible(final Document document)
	{
		final DocumentId documentId = document.getDocumentId();
		if (isStale(documentId))
		{
			logger.trace("Found stale document with id '{}' in local documents. We need to reload it.");
			document.refreshFromRepository();
			markNotStale(documentId);
		}
		else
		{
			document.refreshFromRepositoryIfStaled();
		}

		if (!document.isStaled())
		{
			markNotStale(documentId);
		}
	}

	@Override
	public synchronized OrderedDocumentsList getDocuments(final List<DocumentQueryOrderBy> orderBys)
	{
		// TODO: use orderBys
		return OrderedDocumentsList.of(getInnerDocumentsFullyLoaded(), ImmutableList.of());
	}

	/**
	 * @return inner documents as they are now (no refresh, internal writable collection)
	 */
	private final Collection<Document> getInnerDocumentsNoLoad()
	{
		return _documents.values();
	}

	/**
	 * @return inner documents (internal writable collection). If the documents were not fully loaded, it will load them now.
	 */
	private final Collection<Document> getInnerDocumentsFullyLoaded()
	{
		if (isStale() || !isFullyLoaded())
		{
			loadAll();
			return getInnerDocumentsNoLoad();
		}

		//
		// Refresh stale documents
		final Collection<Document> documents = getInnerDocumentsNoLoad();
		for (final Iterator<Document> it = documents.iterator(); it.hasNext();)
		{
			final Document document = it.next();
			try
			{
				refreshStaleDocumentIfPossible(document);
			}
			catch (final DocumentNotFoundException ex)
			{
				// Document was not found.
				// Re-throw the exception if is not about our current document
				ex.rethrowIfNotMatching(document.getDocumentPath());
				// Else, just remove the document from the inner collection.
				it.remove();
			}
		}

		return documents;
	}

	@Override
	public void updateStatusFromParent()
	{
		actions.updateAndGetAllowCreateNewDocument(actionsContext);
		actions.updateAndGetAllowDeleteDocument(actionsContext);
	}

	@Override
	public synchronized Document createNewDocument()
	{
		assertWritable();
		assertNewDocumentAllowed();

		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		final Document document = documentsRepository.createNewDocument(entityDescriptor, parentDocument, parentDocument.getChangesCollector());

		final DocumentId documentId = document.getDocumentId();
		_documents.put(documentId, document);

		actions.onNewDocument(document, actionsContext);

		return document;
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

	private final void loadAll()
	{
		//
		// Retrieve the documents from repository
		final OrderedDocumentsList documentsNew = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.retriveDocuments();

		final Map<DocumentId, Document> documents = _documents;

		//
		// Clear documents map, but keep the new ones because they were not pushed to repository
		{
			logger.trace("Removing all documents, except the new ones from {}", this);
			for (final Iterator<Document> it = documents.values().iterator(); it.hasNext();)
			{
				final Document document = it.next();

				// Skip new documents
				if (document.isNew())
				{
					continue;
				}

				it.remove();
				logger.trace("Removed document from internal map: {}", document);
			}
		}

		//
		// Put the new documents(from repository) into our documents map
		for (final Document document : documentsNew.toList())
		{
			final DocumentId documentId = document.getDocumentId();
			final Document documentExisting = documents.put(documentId, document);
			if (documentExisting != null)
			{
				logger.warn("loadAll: Replacing for documentId={}: {} with {}", documentId, documentExisting, document);
			}
		}

		//
		// Update status
		markNotStale();
		markFullyLoaded();
	}

	@Override
	public IncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new IncludedDocumentsCollection(this, parentDocumentCopy, copyMode);
	}

	@Override
	public DocumentValidStatus checkAndGetValidStatus(final OnValidStatusChanged onValidStatusChanged)
	{
		for (final Document document : getInnerDocumentsNoLoad())
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
		for (final Document document : getInnerDocumentsNoLoad())
		{
			if (document.hasChangesRecursivelly())
			{
				logger.trace("Considering included documents collection {} having changes because {} has changes", this, document);
				return true;
			}
		}

		return false; // no changes

	}

	@Override
	public void saveIfHasChanges()
	{
		for (final Document document : getInnerDocumentsNoLoad())
		{
			document.saveIfHasChanges();
			// TODO: if saved and refreshed, we shall mark it as not stale !!!
		}
	}

	@Override
	public synchronized void deleteDocuments(final DocumentIdsSelection documentIds)
	{
		if (documentIds.isEmpty())
		{
			throw new IllegalArgumentException("At least one rowId shall be specified when deleting included documents");
		}

		assertWritable();
		actions.assertDeleteDocumentAllowed(actionsContext);

		for (final DocumentId documentId : documentIds.toSet())
		{
			final Document document = getDocumentById(documentId);

			// Delete it from underlying repository (if it's present there)
			if (!document.isNew())
			{
				document.deleteFromRepository();
			}

			document.markAsDeleted();

			// Delete it from our documents map
			_documents.remove(documentId);
			markNotStale(documentId);
		}
	}

	@Override
	public int getNextLineNo()
	{
		final int lastLineNo = getLastLineNo();
		final int nextLineNo = lastLineNo / 10 * 10 + 10;
		return nextLineNo;
	}

	private int getLastLineNo()
	{
		int maxLineNo = 0;
		for (final Document document : getInnerDocumentsFullyLoaded())
		{
			final IDocumentFieldView lineNoField = document.getFieldView(WindowConstants.FIELDNAME_Line);
			final int lineNo = lineNoField.getValueAsInt(0);
			if (lineNo > maxLineNo)
			{
				maxLineNo = lineNo;
			}
		}

		return maxLineNo;
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
			return getInnerDocumentsNoLoad();
		}

		@Override
		public Evaluatee toEvaluatee()
		{
			return parentDocument.asEvaluatee();
		}

		@Override
		public void collectAllowNew(final DocumentPath parentDocumentPath, final DetailId detailId, final LogicExpressionResult allowNew)
		{
			final IDocumentChangesCollector changesCollector = getChangesCollector();
			changesCollector.collectAllowNew(parentDocumentPath, detailId, allowNew);
		}

		@Override
		public void collectAllowDelete(final DocumentPath parentDocumentPath, final DetailId detailId, final LogicExpressionResult allowDelete)
		{
			final IDocumentChangesCollector changesCollector = getChangesCollector();
			changesCollector.collectAllowDelete(parentDocumentPath, detailId, allowDelete);
		}
	}
}
