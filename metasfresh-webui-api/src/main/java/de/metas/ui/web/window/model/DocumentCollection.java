package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
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
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
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

@Component
public class DocumentCollection
{
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;

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
	
	public final DocumentDescriptor getDocumentDescriptor(final int adWindowId)
	{
		return documentDescriptorFactory.getDocumentDescriptor(adWindowId);
	}

	public final DocumentEntityDescriptor getDocumentEntityDescriptor(final int adWindowId)
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
		final DocumentId documentId = documentPath.getDocumentId();
		final DocumentKey documentKey = DocumentKey.of(documentPath.getDocumentType(), documentPath.getDocumentTypeId(), documentId);
		try
		{
			return documents.get(documentKey)
					.refreshFromRepositoryIfStaled();
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
		final DocumentsRepository documentsRepository = entityDescriptor.getDataBinding().getDocumentsRepository();
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

		if (documentPath.isRootDocument())
		{
			return rootDocument;
		}

		return rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId());
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
			rootDocumentWritable = rootDocument.copy(CopyMode.CheckOutWritable);
		}

		//
		// Get/create the included document if any
		if (documentPath.isRootDocument())
		{
			return rootDocumentWritable;
		}
		else if (documentPath.isSingleNewIncludedDocument())
		{
			return rootDocumentWritable.createIncludedDocument(documentPath.getDetailId());
		}
		else if (documentPath.isSingleIncludedDocument())
		{
			return rootDocumentWritable.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId());
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
		else if (documentPath.isSingleIncludedDocument())
		{
			return ImmutableList.of(rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId()));
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

		final Document document = DocumentQuery.ofRecordId(entityDescriptor, documentKey.getDocumentId())
				.retriveDocumentOrNull();
		if (document == null)
		{
			throw new DocumentNotFoundException(documentKey.getDocumentPath());
		}

		return document;
	}

	public void cacheReset()
	{
		// TODO: invalidate only those which are: 1. NOW new; 2. NOT currently editing
		documents.invalidateAll();
		documents.cleanUp();
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
		final Document rootDocumentReadonly = rootDocument.copy(CopyMode.CheckInReadonly);
		documents.put(rootDocumentKey, rootDocumentReadonly);
	}

	public void delete(final DocumentPath documentPath)
	{
		if (documentPath.isRootDocument())
		{
			final Document rootDocument = getRootDocument(documentPath);
			if (!rootDocument.isNew())
			{
				rootDocument.deleteFromRepository();
			}

			// Remove it from index
			final DocumentKey rootDocumentKey = DocumentKey.of(rootDocument);
			documents.invalidate(rootDocumentKey);
		}
		else if (documentPath.hasIncludedDocuments())
		{
			final Document rootDocument = getRootDocument(documentPath).copy(CopyMode.CheckOutWritable);
			rootDocument.deleteIncludedDocuments(documentPath.getDetailId(), documentPath.getRowIds());
			commit(rootDocument);
		}
		else
		{
			throw new InvalidDocumentPathException(documentPath);
		}
	}
	
	public void deleteAll(final List<DocumentPath> documentPaths)
	{
		// FIXME: i think we shall refactor this method and make sure that "deleteAll" is atomic
		
		for (final DocumentPath documentPath : documentPaths)
		{
			delete(documentPath);
		}
	}

	public TableRecordReference getTableRecordReference(final DocumentPath documentPath)
	{
		final DocumentEntityDescriptor rootEntityDescriptor = getDocumentEntityDescriptor(documentPath.getAD_Window_ID());

		if (documentPath.isRootDocument())
		{
			final String tableName = rootEntityDescriptor.getTableName();
			final int recordId = documentPath.getDocumentId().toInt();
			return TableRecordReference.of(tableName, recordId);
		}
		
		final DocumentEntityDescriptor includedEntityDescriptor = rootEntityDescriptor.getIncludedEntityByDetailId(documentPath.getDetailId());
		final String tableName = includedEntityDescriptor.getTableName();
		final int recordId = documentPath.getSingleRowId().toInt();
		return TableRecordReference.of(tableName, recordId);
	}

	private static final class DocumentKey
	{
		public static final DocumentKey of(final Document document)
		{
			final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();
			return new DocumentKey(entityDescriptor.getDocumentType(), entityDescriptor.getDocumentTypeId(), document.getDocumentId());
		}

		public static final DocumentKey of(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
		{
			return new DocumentKey(documentType, documentTypeId, documentId);
		}

		private DocumentType documentType;
		private DocumentId documentTypeId;
		private final DocumentId documentId;

		private Integer _hashcode = null;

		private DocumentKey(final DocumentType documentType, final DocumentId documentTypeId, final DocumentId documentId)
		{
			super();
			this.documentType = documentType;
			this.documentTypeId = documentTypeId;
			this.documentId = Preconditions.checkNotNull(documentId, "documentId");
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("type", documentType)
					.add("typeId", documentTypeId)
					.add("documentId", documentId)
					.toString();
		}

		@Override
		public int hashCode()
		{
			if (_hashcode == null)
			{
				_hashcode = Objects.hash(documentType, documentTypeId, documentId);
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
			return Objects.equals(documentType, other.documentType)
					&& Objects.equals(documentTypeId, other.documentTypeId)
					&& Objects.equals(documentId, other.documentId);
		}

		public int getAD_Window_ID()
		{
			Check.assume(documentType == DocumentType.Window, "documentType shall be {} but it was {}", DocumentType.Window, documentType);
			return documentTypeId.toInt();
		}

		public DocumentId getDocumentId()
		{
			return documentId;
		}
		
		public DocumentPath getDocumentPath()
		{
			return DocumentPath.rootDocumentPath(documentType, documentTypeId, documentId);
		}
	} // DocumentKey
}
