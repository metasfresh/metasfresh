package de.metas.ui.web.window.model;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;

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

public class IncludedDocumentsCollection
{
	private final DocumentEntityDescriptor entityDescriptor;
	private final Document parentDocument;

	private final Map<DocumentId, Document> documents = new LinkedHashMap<>();
	private boolean stale;
	private boolean fullyLoaded;

	/* package */ IncludedDocumentsCollection(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		super();
		this.parentDocument = parentDocument;
		this.entityDescriptor = entityDescriptor;

		stale = true;
		fullyLoaded = false;
	}

	private DocumentRepository getDocumentsRepository()
	{
		return parentDocument.getDocumentRepository();
	}

	public synchronized Document getDocumentById(final DocumentId id)
	{
		if (id == null || id.isNew())
		{
			throw new IllegalArgumentException("Invalid id: " + id);
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

		//
		// Load from underlying repository
		document = loadById(id);

		if (document == null)
		{
			throw new IllegalArgumentException("No document found for id=" + id + " in " + this);
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
		final Document document = getDocumentsRepository().createNewDocument(entityDescriptor, parentDocument);
		
		final DocumentId documentId = DocumentId.of(document.getDocumentId());
		documents.put(documentId, document);
		
		return document;
	}
	
	private final void clearDocumentsExceptNewOnes()
	{
		for (final Iterator<Document> it = documents.values().iterator(); it.hasNext();)
		{
			final Document document = it.next();
			if (document.isNew())
			{
				it.remove();
			}
		}
		
		fullyLoaded = false;
		stale = true;
	}

	private final Document loadById(final DocumentId id)
	{
		final DocumentRepositoryQuery query = DocumentRepositoryQuery.builder(entityDescriptor)
				.setRecordId(id)
				.setParentDocument(parentDocument)
				.build();

		final Document document = getDocumentsRepository().retriveDocument(query);
		if (document == null)
		{
			return null;
		}
		
		final Document documentOld = documents.put(DocumentId.of(document.getDocumentId()), document);
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
		final DocumentRepositoryQuery query = DocumentRepositoryQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.build();

		final List<Document> documentsNew = getDocumentsRepository().retriveDocuments(query);

		clearDocumentsExceptNewOnes();
		
		for (final Document document : documentsNew)
		{
			documents.put(DocumentId.of(document.getDocumentId()), document);
		}
		
		stale = false;
		fullyLoaded = true;
	}
}
