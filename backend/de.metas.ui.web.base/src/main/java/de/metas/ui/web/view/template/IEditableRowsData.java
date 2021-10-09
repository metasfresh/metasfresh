package de.metas.ui.web.view.template;

import java.util.List;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IEditableView.RowEditingContext;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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


public interface IEditableRowsData<T extends IViewRow> extends IRowsData<T>
{
	void patchRow(RowEditingContext ctx, List<JSONDocumentChangedEvent> fieldChangeRequests);
}