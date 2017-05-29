package de.metas.ui.web.window.model;

import de.metas.ui.web.window.datatypes.DocumentId;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface DocumentsRepository
{
	OrderedDocumentsList retrieveDocuments(DocumentQuery query, IDocumentChangesCollector changesCollector);

	/** @return document or null */
	Document retrieveDocument(DocumentQuery query, IDocumentChangesCollector changesCollector);

	/** @return document or null */
	default Document retrieveDocumentById(final DocumentEntityDescriptor entityDescriptor, final DocumentId recordId, final IDocumentChangesCollector changesCollector)
	{
		return retrieveDocument(DocumentQuery.ofRecordId(entityDescriptor, recordId).setChangesCollector(changesCollector).build(), changesCollector);
	}

	/**
	 * Retrieves parent's {@link DocumentId} for a child document identified by given query.
	 *
	 * @param parentEntityDescriptor
	 * @param childDocumentQuery
	 * @return parent's {@link DocumentId}; never returns null
	 */
	DocumentId retrieveParentDocumentId(DocumentEntityDescriptor parentEntityDescriptor, DocumentQuery childDocumentQuery);

	/**
	 *
	 * @param entityDescriptor
	 * @param parentDocument
	 * @return newly created document (not saved); never returns null
	 */
	Document createNewDocument(DocumentEntityDescriptor entityDescriptor, final Document parentDocument, final IDocumentChangesCollector changesCollector);

	void refresh(Document document);

	void save(Document document);

	void delete(Document document);

	String retrieveVersion(DocumentEntityDescriptor entityDescriptor, int documentIdAsInt);

	int retrieveLastLineNo(DocumentQuery query);
}
