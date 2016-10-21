package de.metas.ui.web.window.controller;

import java.util.List;

import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;

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

public interface IWindowRestController
{

	JSONDocumentLayout layout(int adWindowId, String detailId, boolean advanced);

	List<JSONDocument> data(
			int adWindowId //
			, String idStr //
			, String detailId //
			, String rowIdStr //
			, String fieldsListStr //
			, boolean advanced //
	);

	List<JSONDocument> commit(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
			, boolean advanced //
			, List<JSONDocumentChangedEvent> events //
	);

	List<JSONDocument> delete(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
	);

	JSONLookupValuesList typeahead(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
			, String fieldName//
			, String query//
	);

	JSONLookupValuesList dropdown(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
			, String fieldName//
	);

	@Deprecated
	JSONDocumentLayoutTab viewLayout(int adWindowId,final JSONViewDataType viewDataType);

	@Deprecated
	JSONDocumentViewResult createView(
			int adWindowId //
			, final JSONViewDataType viewDataType //
			, int firstRow //
			, int pageLength //
			, List<JSONDocumentFilter> jsonFilters //
	);

	@Deprecated
	JSONDocumentViewResult browseView(String viewId, int firstRow, int pageLength, final String orderBysListStr);

	@Deprecated
	void deleteView(String viewId);

	JSONDocumentActionsList getDocumentActions(int adWindowId, String idStr, String detailId, String rowIdStr);

	JSONDocumentReferencesList getDocumentReferences(int adWindowId, String idStr, String detailId, String rowIdStr);
}