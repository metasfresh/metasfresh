package de.metas.ui.web.window.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.slf4j.Logger;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.Document.CopyMode;

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

/*package*/class IncludedDocumentsCollection
{
	private static final transient Logger logger = LogManager.getLogger(IncludedDocumentsCollection.class);

	private final DocumentEntityDescriptor entityDescriptor;
	private final Document parentDocument;

	private final LinkedHashMap<DocumentId, Document> _documents;

	// State
	private boolean _fullyLoaded;
	private final Set<DocumentId> _staleDocumentIds;

	/* package */ IncludedDocumentsCollection(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		super();
		this.parentDocument = Preconditions.checkNotNull(parentDocument);
		this.entityDescriptor = Preconditions.checkNotNull(entityDescriptor);

		// State
		_fullyLoaded = false;
		_staleDocumentIds = new HashSet<>();

		// Documents map
		_documents = new LinkedHashMap<>();
	}

	/** copy constructor */
	private IncludedDocumentsCollection(final IncludedDocumentsCollection from, final Document parentDocumentCopy, final CopyMode copyMode)
	{
		super();
		parentDocument = Preconditions.checkNotNull(parentDocumentCopy);
		entityDescriptor = from.entityDescriptor;

		// State
		_fullyLoaded = from._fullyLoaded;
		_staleDocumentIds = new HashSet<>(from._staleDocumentIds);

		// Deep-copy documents map
		this._documents = new LinkedHashMap<>(Maps.transformValues(from._documents, (includedDocumentOrig) -> includedDocumentOrig.copy(parentDocumentCopy, copyMode)));
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

	private final boolean isStale()
	{
		return !_staleDocumentIds.isEmpty();
	}

	private final boolean isStale(DocumentId documentId)
	{
		return _staleDocumentIds.contains(documentId);
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
		_staleDocumentIds.add(documentId);
	}

	public final void markStaleAll()
	{
		markNotFullyLoaded();
		_staleDocumentIds.addAll(_documents.keySet());

		Execution.getCurrentDocumentChangesCollectorOrNull()
				.collectStaleDetailId(parentDocument.getDocumentPath(), getDetailId());
	}

	private DocumentsRepository getDocumentsRepository()
	{
		return entityDescriptor.getDataBinding().getDocumentsRepository();
	}

	public DetailId getDetailId()
	{
		return entityDescriptor.getDetailId();
	}

	public synchronized Document getDocumentById(final DocumentId documentId)
	{
		if (documentId == null || documentId.isNew())
		{
			throw new InvalidDocumentPathException("Actual ID was expected instead of '" + documentId + "'");
		}

		//
		// Check loaded collection
		Document documentExisting = _documents.get(documentId);
		if (documentExisting != null)
		{
			if (isStale(documentId))
			{
				logger.trace("Found stale document with id '{}' in local documents. We need to reload it.");
				getDocumentsRepository().refresh(documentExisting);
				markNotStale(documentId);
			}

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
		// document = loadById(id);
		final Document documentNew = DocumentQuery.builder(entityDescriptor)
				.setRecordId(documentId)
				.setParentDocument(parentDocument)
				.retriveDocumentOrNull();
		if (documentNew == null)
		{
			throw new DocumentNotFoundException("No document found for id=" + documentId + " in " + this + "."
					+ "\n Parent document: " + parentDocument
					+ "\n Available document ids are: " + _documents.keySet());
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

	public synchronized List<Document> getDocuments()
	{
		return ImmutableList.copyOf(getInnerDocumentsFullyLoaded());
	}

	/**
	 * @return inner documents as they are now (no refresh, internal writable collection)
	 */
	private final Collection<Document> getInnerDocuments()
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
		}
		return _documents.values();
	}

	public synchronized Document createNewDocument()
	{
		assertWritable();
		assertNewDocumentAllowed();

		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		final Document document = documentsRepository.createNewDocument(entityDescriptor, parentDocument);

		final DocumentId documentId = document.getDocumentId();
		_documents.put(documentId, document);

		return document;
	}

	private void assertNewDocumentAllowed()
	{
		if (parentDocument.isProcessed())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot create included document because parent is processed");
		}

		final ILogicExpression allowCreateNewLogic = entityDescriptor.getAllowCreateNewLogic();
		if (allowCreateNewLogic.isConstantFalse())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot create included document because it's not allowed."
					+ "\n AllowCreateNewLogic: " + allowCreateNewLogic
					+ "\n EntityDescriptor: " + entityDescriptor);
		}

		final LogicExpressionResult allowCreateNew = allowCreateNewLogic.evaluateToResult(parentDocument.asEvaluatee(), OnVariableNotFound.ReturnNoResult);
		if (!allowCreateNew.booleanValue())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot create included document because it's not allowed."
					+ "\n AllowCreateNewLogic: " + allowCreateNewLogic + " => " + allowCreateNew
					+ "\n EntityDescriptor: " + entityDescriptor);
		}
	}

	private void assertDeleteDocumentAllowed(final Document document)
	{
		if (parentDocument.isProcessed())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot delete included document because parent is processed");
		}

		final ILogicExpression allowDeleteLogic = entityDescriptor.getAllowDeleteLogic();
		if (allowDeleteLogic.isConstantFalse())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot delete included document because it's never allowed");
		}

		final LogicExpressionResult allowDelete = allowDeleteLogic.evaluateToResult(parentDocument.asEvaluatee(), OnVariableNotFound.ReturnNoResult);
		if (!allowDelete.booleanValue())
		{
			throw new InvalidDocumentStateException(parentDocument, "Cannot delete included document because it's not allowed: " + allowDelete);
		}
	}

	private final void loadAll()
	{
		//
		// Retrieve the documents from repository
		final List<Document> documentsNew = DocumentQuery.builder(entityDescriptor)
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
		for (final Document document : documentsNew)
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

	/* package */IncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new IncludedDocumentsCollection(this, parentDocumentCopy, copyMode);
	}

	/* package */DocumentValidStatus checkAndGetValidStatus()
	{
		for (final Document document : getInnerDocuments())
		{
			final DocumentValidStatus validState = document.checkAndGetValidStatus();
			if (!validState.isValid())
			{
				logger.trace("Considering included documents collection {} as invalid for saving because {} is not valid: ", this, document, validState);
				return validState;
			}
		}

		return DocumentValidStatus.valid();
	}

	/* package */boolean hasChangesRecursivelly()
	{
		for (final Document document : getInnerDocuments())
		{
			if (document.hasChangesRecursivelly())
			{
				logger.trace("Considering included documents collection {} having changes because {} has changes", this, document);
				return true;
			}
		}

		return false; // no changes

	}

	/* package */void saveIfHasChanges()
	{
		for (final Document document : getInnerDocuments())
		{
			document.saveIfHasChanges();
			// TODO: if saved and refreshed, we shall mark it as not stale !!!
		}
	}

	public synchronized void deleteDocuments(final Set<DocumentId> documentIds)
	{
		if (documentIds == null || documentIds.isEmpty())
		{
			throw new IllegalArgumentException("At least one rowId shall be specified when deleting included documents");
		}

		assertWritable();

		for (final DocumentId documentId : documentIds)
		{
			final Document document = getDocumentById(documentId);
			assertDeleteDocumentAllowed(document);

			// Delete it from underlying repository (if it's present there)
			if (!document.isNew())
			{
				document.deleteFromRepository();
			}

			// Delete it from our documents map
			_documents.remove(documentId);
			markNotStale(documentId);
		}
	}

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
}
