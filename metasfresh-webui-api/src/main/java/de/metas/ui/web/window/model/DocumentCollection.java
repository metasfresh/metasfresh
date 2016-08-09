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
	private DocumentDescriptorFactory documentDescriptorFactory = new DocumentDescriptorFactory();

	private DocumentRepository documentsRepository = new SqlDocumentRepository();

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

	public Document getDocument(final int adWindowId, final DocumentId documentId)
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
		final DocumentDescriptor descriptor = documentDescriptorFactory.getDocumentDescriptor(documentKey.getAD_Window_ID());
		final DocumentEntityDescriptor entityDescriptor = descriptor.getEntityDescriptor();

		if (documentKey.getDocumentId().isNew())
		{
			return documentsRepository.createNewDocument(entityDescriptor);
		}
		else
		{
			final DocumentRepositoryQuery query = DocumentRepositoryQuery.ofRecordId(entityDescriptor, documentKey.getDocumentId().toInt());
			final Document document = documentsRepository.retriveDocument(query);
			if (document == null)
			{
				throw new AdempiereException("@NotFound@ " + documentKey);
			}
			return document;
		}
	}
}
