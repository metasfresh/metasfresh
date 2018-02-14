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
import de.metas.ui.web.window.datatypes.DocumentId;
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

public class WEBUI_Picking_Launcher extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private static final int MAX_ROWS_ALLOWED = 50;

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

		final DocumentIdsSelection selectedRowIds = getSelectedRootDocumentIds();
		final List<TableRecordReference> records = getView().streamByIds(selectedRowIds)
				.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
				.map(IViewRow::getId)
				.map(DocumentId::removeDocumentPrefixAndConvertToInt)
				.map(recordId -> TableRecordReference.of(I_M_Packageable_V.Table_Name, recordId))
				.collect(ImmutableList.toImmutableList());
		if (records.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		getResult().setRecordsToOpen(records, PickingConstants.WINDOWID_PickingView.toInt());

		return MSG_OK;
	}

	private ProcessPreconditionsResolution verifySelectedDocuments()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRootDocumentIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final long selectionSize = getSelectionSize(selectedRowIds);
		if (selectionSize > MAX_ROWS_ALLOWED)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_TOO_MANY_PACKAGEABLES_1P, MAX_ROWS_ALLOWED));
		}

		// Make sure that they all have the same C_BPartner and location.
		if (selectionSize > 1)
		{
			final Set<Integer> bpartnerLocationIds = getView().streamByIds(selectedRowIds)
					.flatMap(selectedRow -> selectedRow.getIncludedRows().stream())
					.map(this::getBPartnerLocationId)
					.collect(Collectors.toSet());
			if (bpartnerLocationIds.size() > 1)
			{
				return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_DIVERGING_LOCATIONS));
			}
		}

		return ProcessPreconditionsResolution.accept();
	}

	private int getBPartnerLocationId(@NonNull final IViewRow row)
	{
		final JSONLookupValue jsonLookupValue = (JSONLookupValue)row
				.getFieldNameAndJsonValues()
				.get(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
		return jsonLookupValue.getKeyAsInt();
	}

	private long getSelectionSize(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getView().size();
		}
		else
		{
			return rowIds.size();
		}
	}

	private DocumentIdsSelection getSelectedRootDocumentIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isAll())
		{
			return selectedRowIds;
		}
		else if (selectedRowIds.isEmpty())
		{
			return selectedRowIds;
		}
		else
		{
			return selectedRowIds.stream().filter(DocumentId::isInt).collect(DocumentIdsSelection.toDocumentIdsSelection());
		}
	}

}
