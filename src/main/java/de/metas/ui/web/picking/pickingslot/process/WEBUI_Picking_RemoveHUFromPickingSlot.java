package de.metas.ui.web.picking.pickingslot.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotViewFactory;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

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

/**
 * Unassigns the currently selected HU from its picking slot so that it could be picked again.
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_RemoveHUFromPickingSlot
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}
		
		if (pickingSlotRow.isProcessed())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NO_UNPROCESSED_RECORDS));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PickingSlotRow huRow = getSingleSelectedRow();
		pickingCandidateService.removeHUFromPickingSlot(huRow.getHuId());

		return MSG_OK;
	}
	
	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView(); // picking slots view (right side)
		invalidateParentView(); // packageables view (left side)
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}
}
