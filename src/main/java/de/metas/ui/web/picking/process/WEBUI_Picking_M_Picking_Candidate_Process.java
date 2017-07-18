package de.metas.ui.web.picking.process;

import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.IHUPickingSlotBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.picking.model.I_M_PickingSlot;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.PickingSlotRow;
import de.metas.ui.web.picking.PickingSlotView;
import de.metas.ui.web.picking.PickingSlotViewFactory;
import de.metas.ui.web.picking.PickingSlotViewRepository;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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
 * 
 * Note: this process is declared in the {@code AD_Process} table, but <b>not</b> added to it's respective window or table via application dictionary.<br>
 * Instead it is assigned to it's place by {@link PickingSlotViewFactory}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class WEBUI_Picking_M_Picking_Candidate_Process
		extends ViewBasedProcessTemplate
		implements IProcessPrecondition
{
	@Autowired
	private PickingSlotViewRepository pickingSlotRepo;

	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedDocumentIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		processForPickingSlot();
		return MSG_OK;
	}

	private void processForPickingSlot()
	{
		getSelectedDocumentIds();
		final List<PickingSlotRow> pickingSlotRows = getView().streamByIds(DocumentIdsSelection.ALL)
				.map(row -> PickingSlotRow.cast(row))
				.collect(Collectors.toList());
		
		final List<PickingSlotRow> rowsToSetProcessed= new ArrayList<>();
		
		//final List<PickingSlotRow> allRows = pickingSlotRepo.retrieveRowsByShipmentScheduleId(getView().getShipmentScheduleId());
		// TODO check if i have all the rows, or "just" the top level rows
		for (final PickingSlotRow row : pickingSlotRows)
		{
			if (!row.isHURow())
			{
				continue;
			}
			// check if the row was already processed
			
			final I_M_PickingSlot pickingSlot = load(row.getPickingSlotId(), I_M_PickingSlot.class);
			final I_M_HU hu = load(row.getHuId(), I_M_HU.class);
			
			huPickingSlotBL.addToPickingSlotQueue(pickingSlot, hu);
			rowsToSetProcessed.add(row);
		}
		pickingSlotRepo.setRowsProcessed(rowsToSetProcessed);

		getView().invalidateAll();
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
