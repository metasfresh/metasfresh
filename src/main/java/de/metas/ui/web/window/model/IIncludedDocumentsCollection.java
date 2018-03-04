package de.metas.ui.web.window.model;

import java.util.List;

import org.adempiere.ad.expression.api.LogicExpressionResult;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.Document.OnValidStatusChanged;

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

public interface IIncludedDocumentsCollection
{
	DetailId getDetailId();

	IIncludedDocumentsCollection copy(Document parentDocumentCopy, CopyMode copyMode);

	OrderedDocumentsList getDocuments(List<DocumentQueryOrderBy> orderBys);

	Document getDocumentById(DocumentId documentId);

	void updateStatusFromParent();

	void assertNewDocumentAllowed();

	LogicExpressionResult getAllowCreateNewDocument();

	LogicExpressionResult getAllowDeleteDocument();

	Document createNewDocument();

	void deleteDocuments(DocumentIdsSelection documentIds);

	DocumentValidStatus checkAndGetValidStatus(OnValidStatusChanged onValidStatusChanged);

	/**
	 * Check if there are any changes in any of the included documents
	 * 
	 * @return true if there are some changes
	 */
	boolean hasChangesRecursivelly();

	void saveIfHasChanges();

	void markStaleAll();
	
	void markStale(final DocumentId rowId);

	/** @return true if contains at least one stale document */
	boolean isStale();

	int getNextLineNo();

	default void onChildSaved(final Document document)
	{
		// nothing
	}

	default void onChildChanged(Document document)
	{
		// nothing
	}
}
