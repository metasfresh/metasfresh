package de.metas.ui.web.picking.process;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class WEBUI_Picking_AddQtyToHU extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	@Param(parameterName = "QtyCU", mandatory = true)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isHURow())
		{
			return ProcessPreconditionsResolution.reject("select HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final int huId = pickingSlotRow.getHuId();
		final int pickingSlotId = pickingSlotRow.getPickingSlotId();
		final int shipmentScheduleId = getView().getShipmentScheduleId();
		
		pickingSlotRepo.addQtyToHU(qtyCU, huId, pickingSlotId, shipmentScheduleId);

		invalidateView();
		invalidateParentView();

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
