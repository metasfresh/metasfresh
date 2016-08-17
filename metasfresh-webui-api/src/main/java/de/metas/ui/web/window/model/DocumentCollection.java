package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.UncheckedExecutionException;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;

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

@Component
public class DocumentCollection
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

	@Autowired
	private DocumentRepository documentsRepository;

	private final LoadingCache<DocumentKey, Document> documents = CacheBuilder.newBuilder()
			.removalListener(new RemovalListener<DocumentKey, Document>()
			{
				@Override
				public void onRemoval(final RemovalNotification<DocumentKey, Document> notification)
				{
					final Document document = notification.getValue();
					document.destroy();
				}
			})
			.build(new CacheLoader<DocumentKey, Document>()
			{
				@Override
				public Document load(final DocumentKey documentKey)
				{
					return retrieveRootDocument(documentKey);
				}

			});

	private static final String DYNATTRIBUTE_IndexedDocumentKey = DocumentCollection.class.getName() + ".IndexedDocumentKey";

	/* package */ DocumentCollection()
	{
		super();
	}

	public DocumentDescriptorFactory getDocumentDescriptorFactory()
	{
		return documentDescriptorFactory;
	}

	private final DocumentEntityDescriptor getDocumentEntityDescriptor(final int adWindowId)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(adWindowId);
		return descriptor.getEntityDescriptor();
	}

	private Document getRootDocument(final int adWindowId, final DocumentId documentId)
	{
		if (documentId.isNew())
		{
			final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(adWindowId);
			final Document document = documentsRepository.createNewDocument(entityDescriptor, Document.NULL);

			final DocumentId temporaryDocumentId = DocumentId.of(document.getDocumentId());
			final DocumentKey temporaryDocumentKey = DocumentKey.of(adWindowId, temporaryDocumentId);
			document.setInternalAttribute(DYNATTRIBUTE_IndexedDocumentKey, temporaryDocumentKey);
			documents.put(temporaryDocumentKey, document);

			return document;
		}
		else
		{
			final DocumentKey documentKey = DocumentKey.of(adWindowId, documentId);
			try
			{
				return documents.get(documentKey);
			}
			catch (final UncheckedExecutionException | ExecutionException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
	}

	public Document getDocument(final DocumentPath documentPath)
	{
		final Document rootDocument = getRootDocument(documentPath.getAD_Window_ID(), documentPath.getDocumentId());

		if (!documentPath.isIncludedDocument())
		{
			return rootDocument;
		}

		if (documentPath.isNewIncludedDocument())
		{
			return rootDocument.createIncludedDocument(documentPath.getDetailId());
		}
		else
		{
			return rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId());
		}
	}

	public Document getDocumentForWriting(final DocumentPath documentPath)
	{
		final Document rootDocumentOrig = getRootDocument(documentPath.getAD_Window_ID(), documentPath.getDocumentId());
		final Document rootDocumentCopy = rootDocumentOrig.copyWritable();

		if (!documentPath.isIncludedDocument())
		{
			return rootDocumentCopy;
		}

		if (documentPath.isNewIncludedDocument())
		{
			return rootDocumentCopy.createIncludedDocument(documentPath.getDetailId());
		}
		else
		{
			return rootDocumentCopy.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId());
		}
	}

	public List<Document> getDocuments(final DocumentPath documentPath)
	{
		final Document rootDocument = getRootDocument(documentPath.getAD_Window_ID(), documentPath.getDocumentId());

		if (!documentPath.isIncludedDocument())
		{
			return ImmutableList.of(rootDocument);
		}

		if (documentPath.isAnyIncludedDocument())
		{
			return rootDocument.getIncludedDocuments(documentPath.getDetailId());
		}
		else
		{
			return ImmutableList.of(rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId()));
		}
	}

	/** Retrieves document from repository */
	private Document retrieveRootDocument(final DocumentKey documentKey)
	{
		final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(documentKey.getAD_Window_ID());

		if (documentKey.getDocumentId().isNew())
		{
			throw new IllegalArgumentException("documentId cannot be NEW");
		}

		final DocumentRepositoryQuery query = DocumentRepositoryQuery.ofRecordId(entityDescriptor, documentKey.getDocumentId().toInt());
		final Document document = documentsRepository.retriveDocument(query);
		if (document == null)
		{
			throw new DocumentNotFoundException(documentKey);
		}

		document.setInternalAttribute(DYNATTRIBUTE_IndexedDocumentKey, documentKey);
		return document;
	}

	public void cacheReset()
	{
		// TODO: invalidate only those which are: 1. NOW new; 2. NOT currently editing
		documents.invalidateAll();
	}

	public void commit(final Document document)
	{
		//
		// Try saving it if possible
		document.saveIfPossible();

		//
		// Get the root document and it's OLD and NEW ids
		final Document rootDocument = document.getRootDocument();

		final Integer rootDocumentIdNew = rootDocument.getDocumentId();
		final DocumentKey rootDocumentKeyOld = rootDocument.getInternalAttribute(DYNATTRIBUTE_IndexedDocumentKey);

		//
		// Add the newly saved and changed document back to index
		final int rootAD_Window_ID = document.getEntityDescriptor().getAD_Window_ID();
		final DocumentKey rootDocumentKeyNew = DocumentKey.of(rootAD_Window_ID, DocumentId.of(rootDocumentIdNew));
		final Document rootDocumentReadonly = rootDocument.copyReadonly();
		rootDocumentReadonly.setInternalAttribute(DYNATTRIBUTE_IndexedDocumentKey, rootDocumentKeyNew);
		documents.put(rootDocumentKeyNew, rootDocumentReadonly);

		//
		// Invalidate the old staled document
		if (!Objects.equals(rootDocumentKeyNew, rootDocumentKeyOld))
		{
			documents.invalidate(rootDocumentKeyOld);
		}

		// TODO: in case this is an included document then reindex the IncludedDocumentsCollection!
	}

	private static final class DocumentKey
	{
		public static final DocumentKey of(final int adWindowId, final DocumentId documentId)
		{
			return new DocumentKey(adWindowId, documentId);
		}

		private final int AD_Window_ID;
		private final DocumentId documentId;

		private Integer _hashcode = null;

		private DocumentKey(final int adWindowId, final DocumentId documentId)
		{
			super();
			AD_Window_ID = adWindowId;
			this.documentId = Preconditions.checkNotNull(documentId, "documentId");
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("AD_Window_ID", AD_Window_ID)
					.add("documentId", documentId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			if (_hashcode == null)
			{
				_hashcode = Objects.hash(AD_Window_ID, documentId);
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
			if (!(obj instanceof DocumentKey))
			{
				return false;
			}

			final DocumentKey other = (DocumentKey)obj;
			return AD_Window_ID == other.AD_Window_ID
					&& Objects.equals(documentId, other.documentId);
		}

		public int getAD_Window_ID()
		{
			return AD_Window_ID;
		}

		public DocumentId getDocumentId()
		{
			return documentId;
		}
	}
}
