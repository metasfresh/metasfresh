package de.metas.ui.web.window.model;

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

import de.metas.logging.LogManager;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
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

	private final Map<DocumentId, Document> documents = new LinkedHashMap<>();
	private boolean stale;
	private boolean fullyLoaded;

	/* package */ IncludedDocumentsCollection(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		super();
		this.parentDocument = Preconditions.checkNotNull(parentDocument);
		this.entityDescriptor = Preconditions.checkNotNull(entityDescriptor);

		stale = true;
		fullyLoaded = false;
	}

	/** copy constructor */
	private IncludedDocumentsCollection(final IncludedDocumentsCollection from, final Document parentDocumentCopy, final CopyMode copyMode)
	{
		super();
		parentDocument = Preconditions.checkNotNull(parentDocumentCopy);
		entityDescriptor = from.entityDescriptor;

		stale = from.stale;
		fullyLoaded = from.fullyLoaded;

		// Copy documents map
		for (final Map.Entry<DocumentId, Document> e : from.documents.entrySet())
		{
			final DocumentId includedDocumentId = e.getKey();
			final Document includedDocumentOrig = e.getValue();
			final Document includedDocumentCopy = includedDocumentOrig.copy(parentDocumentCopy, copyMode);
			documents.put(includedDocumentId, includedDocumentCopy);
		}
	}

	private final void assertWritable()
	{
		parentDocument.assertWritable();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("detailId", entityDescriptor.getDetailId())
				.add("documentsCount", documents.size())
				.toString();
	}

	public synchronized Document getDocumentById(final DocumentId id)
	{
		if (id == null || id.isNew())
		{
			throw new InvalidDocumentPathException("Actual ID was expected instead of '" + id + "'");
		}

		//
		// Reset if staled
		if (stale)
		{
			clearDocumentsExceptNewOnes();
		}

		//
		// Check loaded collection
		Document document = documents.get(id);
		if (document != null)
		{
			return document;
		}
		else
		{
			if (logger.isTraceEnabled())
			{
				logger.trace("No document with id '{}' was found in local documents. \nAvailable IDs are: {}", id, documents.keySet());
			}
		}

		//
		// Load from underlying repository
		document = loadById(id);

		if (document == null)
		{
			throw new DocumentNotFoundException("No document found for id=" + id + " in " + this + "."
					+ "\n Parent document: " + parentDocument
					+ "\n Available document ids are: " + documents.keySet());
		}

		return document;
	}

	public synchronized List<Document> getDocuments()
	{
		if (stale || !fullyLoaded)
		{
			loadAll();
		}

		return ImmutableList.copyOf(documents.values());
	}

	public synchronized Document createNewDocument()
	{
		assertWritable();
		assertNewDocumentAllowed();

		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
		final Document document = documentsRepository.createNewDocument(entityDescriptor, parentDocument);

		final DocumentId documentId = document.getDocumentId();
		documents.put(documentId, document);

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

	private final void clearDocumentsExceptNewOnes()
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
			logger.trace("Removed document: {}", document);
		}

		fullyLoaded = false;
		stale = true;
	}

	private final Document loadById(final DocumentId id)
	{
		if (id == null || id.isNew())
		{
			throw new InvalidDocumentPathException("Actual ID was expected instead of '" + id + "'");
		}

		final Document document = DocumentQuery.builder(entityDescriptor)
				.setRecordId(id.toInt())
				.setParentDocument(parentDocument)
				.retriveDocumentOrNull();
		if (document == null)
		{
			return null;
		}

		final Document documentOld = documents.put(document.getDocumentId(), document);
		stale = false;
		if (documentOld == null)
		{
			// we just loaded and added a document to our collection => for sure this was/is not fully loaded
			fullyLoaded = false;
		}
		return document;
	}

	private final void loadAll()
	{
		final List<Document> documentsNew = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.retriveDocuments();

		clearDocumentsExceptNewOnes();

		for (final Document document : documentsNew)
		{
			final DocumentId documentId = document.getDocumentId();
			final Document documentExisting = documents.put(documentId, document);
			if (documentExisting != null)
			{
				logger.warn("loadAll: Replacing for documentId={}: {} with {}", documentId, documentExisting, document);
			}
		}

		stale = false;
		fullyLoaded = true;
	}

	/* package */IncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return new IncludedDocumentsCollection(this, parentDocumentCopy, copyMode);
	}

	/* package */DocumentValidStatus checkAndGetValidStatus()
	{
		for (final Document document : documents.values())
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
		for (final Document document : documents.values())
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
		for (final Document document : documents.values())
		{
			document.saveIfHasChanges();
		}
	}

	public synchronized void deleteDocuments(final Set<DocumentId> rowIds)
	{
		if (rowIds == null || rowIds.isEmpty())
		{
			throw new IllegalArgumentException("At least one rowId shall be specified when deleting included documents");
		}

		assertWritable();

		for (final DocumentId rowId : rowIds)
		{
			final Document document = getDocumentById(rowId);
			assertDeleteDocumentAllowed(document);

			if (!document.isNew())
			{
				document.deleteFromRepository();
			}

			documents.remove(document.getDocumentId());
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
		if (!fullyLoaded)
		{
			loadAll();
		}

		int maxLineNo = 0;
		for (final Document document : documents.values())
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
