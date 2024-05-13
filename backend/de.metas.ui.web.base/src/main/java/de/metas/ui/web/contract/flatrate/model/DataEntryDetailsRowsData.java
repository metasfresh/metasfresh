/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.contract.flatrate.model;

import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.view.IEditableView;
import de.metas.ui.web.view.template.IEditableRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import java.util.List;
import java.util.Map;

public class DataEntryDetailsRowsData implements IEditableRowsData<DataEntryDetailsRow>
{
	private final ImmutableMap<DocumentId, DataEntryDetailsRow> rows;

	public DataEntryDetailsRowsData(@NonNull final ImmutableMap<DocumentId, DataEntryDetailsRow> rows)
	{
		this.rows = rows;
	}

	@Override
	public void patchRow(final IEditableView.RowEditingContext ctx, final List<JSONDocumentChangedEvent> fieldChangeRequests)
	{
		
	}

	@Override
	public Map<DocumentId, DataEntryDetailsRow> getDocumentId2TopLevelRows()
	{
		return rows;
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return null;
	}

	@Override
	public void invalidateAll()
	{

	}
}
