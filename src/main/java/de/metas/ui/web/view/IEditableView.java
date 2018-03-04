package de.metas.ui.web.view;

import java.util.List;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.DocumentCollection;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

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

public interface IEditableView extends IView
{
	void patchViewRow(RowEditingContext ctx, List<JSONDocumentChangedEvent> fieldChangeRequests);

	LookupValuesList getFieldTypeahead(RowEditingContext ctx, String fieldName, String query);

	LookupValuesList getFieldDropdown(RowEditingContext ctx, String fieldName);

	@Builder
	@Getter
	@ToString(exclude = "documentsCollection")
	public class RowEditingContext
	{
		@NonNull
		private final DocumentId rowId;
		@NonNull
		private final DocumentCollection documentsCollection;
	}
}
