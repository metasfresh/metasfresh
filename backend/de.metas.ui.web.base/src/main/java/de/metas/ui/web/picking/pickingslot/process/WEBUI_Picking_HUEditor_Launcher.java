package de.metas.ui.web.picking.pickingslot.process;

import de.metas.process.ProcessExecutionResult.ViewOpenTarget;
import de.metas.process.ProcessExecutionResult.WebuiViewToOpen;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.husToPick.HUsToPickViewFactory;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterData;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowId;
import de.metas.ui.web.picking.pickingslot.PickingSlotView;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import org.compiere.SpringContextHolder;

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
	private final IViewsRepository viewsRepo = SpringContextHolder.instance.getBean(IViewsRepository.class);
	private final HUsToPickViewFactory husToPickViewFactory = SpringContextHolder.instance.getBean(HUsToPickViewFactory.class);

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
		final IView husToPickView = createHUsToPickView();

		getResult().setWebuiViewToOpen(WebuiViewToOpen.builder()
											   .viewId(husToPickView.getViewId().getViewId())
											   .profileId("husToPick")
											   .target(ViewOpenTarget.IncludedView)
											   .build());

		return MSG_OK;
	}

	private IView createHUsToPickView()
	{
		final PickingSlotView pickingSlotsView = getPickingSlotView();
		final PickingSlotRowId pickingSlotRowId = getSingleSelectedPickingSlotRow().getPickingSlotRowId();

		return viewsRepo.createView(husToPickViewFactory.createViewRequest(
				pickingSlotsView.getViewId(),
				pickingSlotRowId,
				pickingSlotsView.getCurrentShipmentScheduleId(),
				getBarcodeFilterData().orElse(null)));
	}
}
