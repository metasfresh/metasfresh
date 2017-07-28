package de.metas.ui.web.picking.process;

import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUQueryBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.ui.web.handlingunits.HUIdsFilterHelper;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.json.JSONViewDataType;

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
 * This process opens a HU editor window within the picking window
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_OpenHUsToPick extends ViewBasedProcessTemplate
{
	@Autowired
	private IViewsRepository viewsRepo;

	private final transient IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final ViewId pickingSlotViewId = getView().getViewId();
		// final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		// final int shipmentScheduleId = getView().getShipmentScheduleId();

		// TODO: refactor together with the Swing UI version of retrieving the huIds
		// see de.metas.fresh.picking.terminal.FreshProductKey.findAvailableHUs(boolean)
		final IHUQueryBuilder huIdsToAvailableToPickQuery = Services.get(IHandlingUnitsDAO.class)
				.createHUQueryBuilder()
				.setHUStatus(X_M_HU.HUSTATUS_Active)
				.onlyNotLocked();

		final IView husToPickView = viewsRepo.createView(CreateViewRequest.builder(WEBUI_HU_Constants.WEBUI_HU_Window_ID, JSONViewDataType.includedView)
				.setParentViewId(pickingSlotViewId)
				.setParentRowId(pickingSlotRow.getId())
				.addStickyFilters(HUIdsFilterHelper.createFilter(huIdsToAvailableToPickQuery))

				.addAdditionalRelatedProcessDescriptor(RelatedProcessDescriptor.builder()
						.processId(adProcessDAO.retriveProcessIdByClassIfUnique(Env.getCtx(), WEBUI_Picking_PickSelectedHU.class))
						.webuiQuickAction(true)
						.build())
				.build());

		getResult().setWebuiIncludedViewIdToOpen(husToPickView.getViewId().getViewId());

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
