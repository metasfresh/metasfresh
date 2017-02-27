package de.metas.ui.web.window.model;

import java.util.List;
import java.util.Set;

import de.metas.ui.web.window.datatypes.DocumentId;
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

/* package */interface IIncludedDocumentsCollection
{
	IIncludedDocumentsCollection copy(Document parentDocumentCopy, CopyMode copyMode);

	List<Document> getDocuments();

	Document getDocumentById(DocumentId documentId);

	void assertNewDocumentAllowed();

	Document createNewDocument();

	void deleteDocuments(Set<DocumentId> documentIds);

	DocumentValidStatus checkAndGetValidStatus();

	boolean hasChangesRecursivelly();

	void saveIfHasChanges();

	void markStaleAll();

	int getNextLineNo();
}
