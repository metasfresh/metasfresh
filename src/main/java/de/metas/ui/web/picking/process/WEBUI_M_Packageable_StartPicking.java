package de.metas.ui.web.picking.process;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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

public class WEBUI_M_Packageable_StartPicking extends ViewBasedProcessTemplate
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
		final List<Integer> rowIds = getView().getByIds(selectedRowIds)
				.stream()
				.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
				.map(IViewRow::getId)
				.map(rowId -> rowId.removePrefixAndConvertToInt("D")) // FIXME: hardcoded
				.collect(ImmutableList.toImmutableList());
		if (rowIds.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		getResult().setRecordsToOpen(TableRecordReference.ofRecordIds(I_M_Packageable_V.Table_Name, rowIds), PickingConstants.WINDOWID_PickingView.toInt());
		return MSG_OK;
	}

}
