package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_DIVERGING_LOCATIONS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingConstants;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import lombok.NonNull;

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

public class WEBUI_Picking_Start extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return verifySelectedDocuments();
	}

	@Override
	protected String doIt() throws Exception
	{
		// repeat the verification from checkPreconditionsApplicable() just to be sure.
		// We had cases where the selected rows of checkPreconditionsApplicable() were not the selected rows of doIt() 
		final ProcessPreconditionsResolution verifySelectedDocuments = verifySelectedDocuments();
		if (verifySelectedDocuments.isRejected())
		{
			throw new AdempiereException(verifySelectedDocuments().getRejectReason().translate(Env.getAD_Language(getCtx())));
		}

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

	private ProcessPreconditionsResolution verifySelectedDocuments()
	{
		if (getSelectedDocumentIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (getSelectedDocumentIds().size() > 50)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P, 50));
		}

		if (getSelectedDocumentIds().size() > 1)
		{
			// make sure that they all have the same C_BPartner and location.
			final DocumentIdsSelection selectedRowIds = getSelectedDocumentIds();
			final Set<Integer> flatMap = getView().getByIds(selectedRowIds)
					.stream()
					.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
					.map(row -> getBPartnerLocationId(row))
					.collect(Collectors.toSet());
			if (flatMap.size() > 1)
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_DIVERGING_LOCATIONS));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private int getBPartnerLocationId(@NonNull final IViewRow row)
	{
		final JSONLookupValue jsonLookupValue = (JSONLookupValue)row.getFieldNameAndJsonValues().get(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
		return jsonLookupValue.getKeyAsInt();
	}
	
}
