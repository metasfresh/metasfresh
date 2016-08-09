package de.metas.ui.web.window.model;

import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.sql.SqlDocumentRepository;

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

public class DocumentCollection
{
	private final DocumentDescriptorFactory documentDescriptorFactory = new DocumentDescriptorFactory();

	private final DocumentRepository documentsRepository = new SqlDocumentRepository();

	private final LoadingCache<DocumentKey, Document> documents = CacheBuilder.newBuilder()
			.build(new CacheLoader<DocumentKey, Document>()
			{
				@Override
				public Document load(final DocumentKey documentKey)
				{
					return createDocument(documentKey);
				}

			});

	public DocumentDescriptorFactory getDocumentDescriptorFactory()
	{
		return documentDescriptorFactory;
	}
	
	private final DocumentEntityDescriptor getDocumentEntityDescriptor(final int adWindowId)
	{
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(adWindowId);
		return descriptor.getEntityDescriptor();
	}

	public Document getDocument(final int adWindowId, final DocumentId documentId)
	{
		if (documentId.isNew())
		{
			final DocumentEntityDescriptor entityDescriptor = getDocumentEntityDescriptor(adWindowId);
			final Document document = documentsRepository.createNewDocument(entityDescriptor);

			final DocumentId temporaryDocumentId = DocumentId.of(document.getDocumentId());
			final DocumentKey temporaryDocumentKey = DocumentKey.of(adWindowId, temporaryDocumentId);
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
			catch (final ExecutionException e)
			{
				throw AdempiereException.wrapIfNeeded(e);
			}
		}
	}

	public Document getDocument(final int adWindowId, final String idStr, final String detailId, final String rowId)
	{
		final DocumentId documentId = DocumentId.of(idStr);
		final Document document = getDocument(adWindowId, documentId);
		if (Check.isEmpty(rowId))
		{
			return document;
		}

		// TODO get the included document
		Check.assumeNotEmpty(detailId, "detailId is not empty");
		throw new UnsupportedOperationException("detailId and rowId are not supported");
	}

	private Document createDocument(final DocumentKey documentKey)
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
			throw new AdempiereException("@NotFound@ " + documentKey);
		}
		return document;
	}
}
