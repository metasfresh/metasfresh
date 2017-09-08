package de.metas.ui.web.picking.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.*;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.PickingCandidateCommand;
import de.metas.ui.web.picking.PickingCandidateCommand.RemoveQtyFromHURequest;
import de.metas.ui.web.picking.PickingSlotRow;

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

public class WEBUI_Picking_ReturnQtyToSourceHU
		extends WEBUI_Picking_PickFrom_M_Source_HU
		implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateCommand pickingCandidateCommand;

	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKING_SLOT));
		}

		final String rowType = pickingSlotRow.getType().getName();
		final boolean cuRow = Objects.equals(rowType, HUEditorRowType.VHU.getName()) || Objects.equals(rowType, HUEditorRowType.HUStorage.getName());
		if (cuRow)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_CU));
		}

		if (!checkSourceHuPrecondition())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		final RemoveQtyFromHURequest request = RemoveQtyFromHURequest.builder()
				.qtyCU(qtyCU)
				.huId(pickingSlotRow.getHuId())
				.pickingSlotId(pickingSlotRow.getPickingSlotId())
				.shipmentScheduleId(getView().getShipmentScheduleId())
				.build();

		pickingCandidateCommand.removeQtyFromHU(request);

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}
}
