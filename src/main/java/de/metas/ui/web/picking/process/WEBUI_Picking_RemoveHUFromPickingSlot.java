package de.metas.ui.web.picking.process;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.picking.PickingSlotViewRepository;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WEBUI_Picking_RemoveHUFromPickingSlot extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		
		final PickingSlotRow huRow = getSingleSelectedRow();
		if(!huRow.isHURow())
		{
			return ProcessPreconditionsResolution.reject("select an HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PickingSlotRow huRow = getSingleSelectedRow();
		final int huId = huRow.getHuId();
		final int pickingSlotId = huRow.getPickingSlotId();
		pickingSlotRepo.removeHUFromPickingSlot(huId, pickingSlotId);

		invalidateView();

		return MSG_OK;
	}

	@Override
	protected PickingSlotView getView()
	{
		return PickingSlotView.cast(super.getView());
	}

	@Override
	protected PickingSlotRow getSingleSelectedRow()
	{
		return PickingSlotRow.cast(super.getSingleSelectedRow());
	}
}
