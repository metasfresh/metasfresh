package de.metas.ui.web.window.controller;

import java.util.List;

import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutSideList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;

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

	JSONDocumentLayout layout(int adWindowId, boolean advanced);

	JSONDocumentLayoutTab tabLayout(int adWindowId, String detailId, boolean advanced);

	JSONDocumentLayoutSideList sideListLayout(int adWindowId);

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

	List<JSONLookupValue> typeahead(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
			, String fieldName//
			, String query//
	);

	List<JSONLookupValue> dropdown(
			int adWindowId//
			, String idStr//
			, String detailId//
			, String rowIdStr//
			, String fieldName//
	);

	void cacheReset();
}