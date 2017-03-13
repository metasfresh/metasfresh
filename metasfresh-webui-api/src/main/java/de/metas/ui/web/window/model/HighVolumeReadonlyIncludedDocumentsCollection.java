package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Set;

import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.exceptions.DocumentNotFoundException;
import de.metas.ui.web.window.exceptions.InvalidDocumentStateException;
import de.metas.ui.web.window.model.Document.CopyMode;

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

public final class HighVolumeReadonlyIncludedDocumentsCollection implements IIncludedDocumentsCollection
{
	private final Document parentDocument;
	private final DocumentEntityDescriptor entityDescriptor;

	public HighVolumeReadonlyIncludedDocumentsCollection(final Document parentDocument, final DocumentEntityDescriptor entityDescriptor)
	{
		this.parentDocument = parentDocument;
		this.entityDescriptor = entityDescriptor;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("detailId", entityDescriptor.getDetailId())
				.toString();
	}

	@Override
	public IIncludedDocumentsCollection copy(final Document parentDocumentCopy, final CopyMode copyMode)
	{
		return this;
	}

	@Override
	public List<Document> getDocuments()
	{
		return DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.retriveDocuments();
	}

	@Override
	public Document getDocumentById(final DocumentId documentId)
	{
		final Document document = DocumentQuery.builder(entityDescriptor)
				.setParentDocument(parentDocument)
				.setRecordId(documentId)
				.retriveDocumentOrNull();
		if (document == null)
		{
			final DocumentPath documentPath = parentDocument
					.getDocumentPath()
					.createChildPath(entityDescriptor.getDetailId(), documentId);
			throw new DocumentNotFoundException(documentPath);
		}

		return document;
	}

	@Override
	public void assertNewDocumentAllowed()
	{
		throw new InvalidDocumentStateException(parentDocument, "Tab is readonly");
	}

	@Override
	public Document createNewDocument()
	{
		throw new InvalidDocumentStateException(parentDocument, "Tab is readonly");
	}

	@Override
	public void deleteDocuments(final Set<DocumentId> documentIds)
	{
		throw new InvalidDocumentStateException(parentDocument, "Tab is readonly");
	}

	@Override
	public DocumentValidStatus checkAndGetValidStatus()
	{
		return DocumentValidStatus.documentValid();
	}

	@Override
	public boolean hasChangesRecursivelly()
	{
		return false;
	}

	@Override
	public void saveIfHasChanges()
	{
	}

	@Override
	public void markStaleAll()
	{
	}

	@Override
	public int getNextLineNo()
	{
		throw new UnsupportedOperationException();
	}
}
