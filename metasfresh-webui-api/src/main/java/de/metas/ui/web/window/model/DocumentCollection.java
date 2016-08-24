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

import de.metas.ui.web.window.datatypes.DataTypes;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;

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

	/**
	 * Gets an existing root document
	 * 
	 * @param documentPath
	 * @return root document (readonly)
	 */
	private Document getRootDocument(final DocumentPath documentPath)
	{
		final int adWindowId = documentPath.getAD_Window_ID();
		final DocumentId documentId = documentPath.getDocumentId();
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

	/**
	 * Creates a new root document.
	 * 
	 * @param documentPath
	 * @return new root document (writable)
	 */
	private Document createRootDocument(final DocumentPath documentPath)
	{
		if (!documentPath.isNewDocument())
		{
			throw new InvalidDocumentPathException(documentPath, "new document ID was expected");
		}

		final int adWindowId = documentPath.getAD_Window_ID();
		final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(adWindowId);
		final Document document = documentsRepository.createNewDocument(entityDescriptor, Document.NULL);
		// NOTE: we assume document is writable
		// NOTE: we are not adding it to index. That shall be done on "commit".
		return document;
	}

	/**
	 * Gets (readonly) document identified by given <code>documentPath</code>.
	 *
	 * @param documentPath
	 * @return readonly document
	 */
	public Document getDocument(final DocumentPath documentPath)
	{
		final Document rootDocument = getRootDocument(documentPath);

		if (!documentPath.isIncludedDocument())
		{
			return rootDocument;
		}

		return rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId());
	}

	/**
	 * Gets or creates a new document (writable mode)
	 *
	 * @param documentPath
	 * @return document (writable copy)
	 */
	public Document getOrCreateDocumentForWriting(final DocumentPath documentPath)
	{
		//
		// Get/Create the root document (writable)
		final Document rootDocumentWritable;
		if (documentPath.isNewDocument())
		{
			rootDocumentWritable = createRootDocument(documentPath);
		}
		else
		{
			final Document rootDocument = getRootDocument(documentPath);
			rootDocumentWritable = rootDocument.copyWritable();
		}

		//
		// Get/create the included document if any
		if (documentPath.isRootDocument())
		{
			return rootDocumentWritable;
		}
		else if (documentPath.isNewIncludedDocument())
		{
			return rootDocumentWritable.createIncludedDocument(documentPath.getDetailId());
		}
		else if (documentPath.isIncludedDocument())
		{
			return rootDocumentWritable.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId());
		}
		else
		{
			throw new InvalidDocumentPathException(documentPath);
		}
	}

	/**
	 * Gets (readonly) documents identified by given <code>documentPath</code>.
	 *
	 * @param documentPath
	 * @return readonly documents
	 */
	public List<Document> getDocuments(final DocumentPath documentPath)
	{
		final Document rootDocument = getRootDocument(documentPath);

		if (documentPath.isRootDocument())
		{
			return ImmutableList.of(rootDocument);
		}
		else if (documentPath.isAnyIncludedDocument())
		{
			return rootDocument.getIncludedDocuments(documentPath.getDetailId());
		}
		else if (documentPath.isIncludedDocument())
		{
			return ImmutableList.of(rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getRowId()));
		}
		else
		{
			throw new InvalidDocumentPathException(documentPath);
		}
	}

	/** Retrieves document from repository */
	private Document retrieveRootDocument(final DocumentKey documentKey)
	{
		final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(documentKey.getAD_Window_ID());

		if (documentKey.getDocumentId().isNew())
		{
			throw new InvalidDocumentPathException("documentId cannot be NEW");
		}

		final DocumentRepositoryQuery query = DocumentRepositoryQuery.ofRecordId(entityDescriptor, documentKey.getDocumentId().toInt());
		final Document document = documentsRepository.retriveDocument(query);
		if (document == null)
		{
			throw new DocumentNotFoundException(documentKey);
		}

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
		document.saveIfValidAndHasChanges();

		//
		// Get the root document
		final Document rootDocument = document.getRootDocument();

		//
		// Add the saved and changed document back to index
		final DocumentKey rootDocumentKey = DocumentKey.of(rootDocument);
		final Document rootDocumentReadonly = rootDocument.copyReadonly();
		documents.put(rootDocumentKey, rootDocumentReadonly);
	}

	public void delete(final DocumentPath documentPath)
	{
		if (documentPath.isRootDocument())
		{
			final Document rootDocument = getRootDocument(documentPath);
			if (!rootDocument.isNew())
			{
				documentsRepository.delete(rootDocument);
			}

			// Remove it from index
			final DocumentKey rootDocumentKey = DocumentKey.of(rootDocument);
			documents.invalidate(rootDocumentKey);
		}
		else if (documentPath.isIncludedDocument())
		{
			final Document rootDocument = getRootDocument(documentPath).copyWritable();
			rootDocument.deleteIncludedDocument(documentPath.getDetailId(), documentPath.getRowId());
			commit(rootDocument);
		}
		else
		{
			throw new InvalidDocumentPathException(documentPath);
		}
	}

	private static final class DocumentKey
	{
		public static final DocumentKey of(final Document document)
		{
			final int adWindowId = document.getEntityDescriptor().getAD_Window_ID();
			final DocumentId documentId = DocumentId.of(document.getDocumentId());
			return new DocumentKey(adWindowId, documentId);
		}

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
					&& DataTypes.equals(documentId, other.documentId);
		}

		public int getAD_Window_ID()
		{
			return AD_Window_ID;
		}

		public DocumentId getDocumentId()
		{
			return documentId;
		}
	} // DocumentKey
}
