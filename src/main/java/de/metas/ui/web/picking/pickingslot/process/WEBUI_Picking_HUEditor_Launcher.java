package de.metas.ui.web.picking.pickingslot.process;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.List;

import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotBL.PickingHUsQuery;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;
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

/**
 * This process opens the HUsToPick view.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
public class WEBUI_Picking_HUEditor_Launcher extends PickingSlotViewBasedProcess
{
	@Autowired
	private IViewsRepository viewsRepo;

	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final List<Integer> availableHUIdsToPick = retrieveAvailableHuIdsForCurrentShipmentScheduleId();

		final IView husToPickView = createHUEditorView(availableHUIdsToPick);

		getResult().setWebuiIncludedViewIdToOpen(husToPickView.getViewId().getViewId());
		getResult().setWebuiViewProfileId("husToPick");

		return MSG_OK;
	}

	private List<Integer> retrieveAvailableHuIdsForCurrentShipmentScheduleId()
	{
		final int shipmentScheduleId = getView().getCurrentShipmentScheduleId();

		final PickingHUsQuery query = PickingHUsQuery.builder()
				.shipmentSchedule(loadOutOfTrx(shipmentScheduleId, I_M_ShipmentSchedule.class))
				.onlyTopLevelHUs(false)
				.onlyIfAttributesMatchWithShipmentSchedules(true)
				.build();
		final List<Integer> availableHUIdsToPick = huPickingSlotBL.retrieveAvailableHUIdsToPick(query);
		return availableHUIdsToPick;
	}

	private IView createHUEditorView(@NonNull final List<Integer> availableHUIdsToPick)
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final ViewId pickingSlotViewId = getView().getViewId();

		final IView husToPickView = viewsRepo.createView(
				CreateViewRequest.builder(HUsToPickViewFactory.WINDOW_ID, JSONViewDataType.includedView)
						.setParentViewId(pickingSlotViewId)
						.setParentRowId(pickingSlotRow.getId())
						.addStickyFilters(HUIdsFilterHelper.createFilter(availableHUIdsToPick))
						.build());
		return husToPickView;
	}
}
