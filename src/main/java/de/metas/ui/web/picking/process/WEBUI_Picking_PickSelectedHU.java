package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_NOT_TOP_LEVEL_HU;

import org.adempiere.exceptions.AdempiereException;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.picking.PickingCandidateCommand;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;

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
 * This process is called from the HU selection dialog that is opened by {@link WEBUI_Picking_OpenHUsToPick}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_PickSelectedHU extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateCommand pickingCandidateCommand;
	@Autowired
	private IViewsRepository viewsRepo;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final HUEditorRow huRow = getSingleSelectedRow();
		if (!huRow.isTopLevel())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_NOT_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final HUEditorRow huRow = getSingleSelectedRow();
		final int huId = huRow.getM_HU_ID();
		if (!huRow.isTopLevel())
		{
			// TODO: extract as top level
			throw new AdempiereException("Not a top level HU");
		}

		final PickingSlotView pickingSlotsView = getPickingSlotView();
		final PickingSlotRow pickingSlotRow = getPickingSlotRow();
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final int shipmentScheduleId = pickingSlotsView.getShipmentScheduleId();

		pickingCandidateCommand.addHUToPickingSlot(huId, pickingSlotId, shipmentScheduleId);

		invalidateView(pickingSlotsView.getViewId()); // picking slots view
		invalidateView(pickingSlotsView.getParentViewId()); // picking view

		// After this process finished successfully go back to picking slots view
		getResult().setWebuiIncludedViewIdToOpen(pickingSlotsView.getViewId().getViewId());

		return MSG_OK;
	}

	@Override
	protected HUEditorView getView()
	{
		return HUEditorView.cast(super.getView());
	}

	@Override
	protected HUEditorRow getSingleSelectedRow()
	{
		return HUEditorRow.cast(super.getSingleSelectedRow());
	}

	protected PickingSlotView getPickingSlotView()
	{
		final ViewId parentViewId = getView().getParentViewId();
		if (parentViewId == null)
		{
			return null;
		}
		final IView parentView = viewsRepo.getView(parentViewId);
		return PickingSlotView.cast(parentView);
	}

	protected PickingSlotRow getPickingSlotRow()
	{
		final HUEditorView huView = getView();
		final DocumentId pickingSlotRowId = huView.getParentRowId();

		final PickingSlotView pickingSlotView = getPickingSlotView();
		return pickingSlotView.getById(pickingSlotRowId);
	}

}
