package de.metas.ui.web.pickingslotsClearing.process;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.pickingslotsClearing.PickingSlotsClearingView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

public class WEBUI_PickingSlotsClearingView_TakeOutHU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition
{
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);

	private final List<HUExtractedFromPickingSlotEvent> husExtractedEvents = new ArrayList<>();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (!selectedRowIds.isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow row = getSingleSelectedPickingSlotRow();
		if (!row.isTopLevelHU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a top level HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		//
		// Get the HU
		final PickingSlotRow huRow = getSingleSelectedPickingSlotRow();
		Check.assume(huRow.isTopLevelHU(), "row {} shall be a top level HU", huRow);
		final I_M_HU hu = InterfaceWrapperHelper.load(huRow.getHuId(), I_M_HU.class);

		//
		// Remove the HU from it's picking slot
		huPickingSlotBL.removeFromPickingSlotQueueRecursivelly(hu);

		//
		// Make sure the HU has the BPartner/Location of the picking slot
		final PickingSlotRow pickingSlotRow = getRootRowForSelectedPickingSlotRows();
		if (pickingSlotRow.getBPartnerId() > 0)
		{
			hu.setC_BPartner_ID(pickingSlotRow.getBPartnerId());
			hu.setC_BPartner_Location_ID(pickingSlotRow.getBPartnerLocationId());
			InterfaceWrapperHelper.save(hu);
		}

		//
		// Move the HU to an after picking locator
		moveToAfterPickingLocator(hu);

		//
		// Inactive all those picking candidates
		pickingCandidateService.inactivateForHUId(HuId.ofRepoId(hu.getM_HU_ID()));

		husExtractedEvents.add(HUExtractedFromPickingSlotEvent.builder()
				.huId(hu.getM_HU_ID())
				.bpartnerId(hu.getC_BPartner_ID())
				.bpartnerLocationId(hu.getC_BPartner_Location_ID())
				.build());

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		//
		// Invalidate the views
		// Expectation: the HU shall disappear from picking slots view (left side) and shall appear on after picking HUs view (right side).
		final PickingSlotsClearingView pickingSlotsClearingView = getPickingSlotsClearingView();
		invalidateView(pickingSlotsClearingView.getViewId());
		//
		husExtractedEvents.forEach(pickingSlotsClearingView::handleEvent);
	}
}
