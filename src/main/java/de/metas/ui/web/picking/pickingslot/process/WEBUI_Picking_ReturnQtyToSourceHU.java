package de.metas.ui.web.picking.pickingslot.process;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_CU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;

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
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition
{
	@Autowired
	private PickingCandidateService pickingCandidateService;

	@Autowired
	private HuId2SourceHUsService sourceHUsRepository;

	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

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

		final String rowType = pickingSlotRow.getType().getName();
		final boolean cuRow = Objects.equals(rowType, HUEditorRowType.VHU.getName()) || Objects.equals(rowType, HUEditorRowType.HUStorage.getName());
		if (cuRow)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_CU));
		}

		if (!checkSourceHuPreconditionIncludingEmptyHUs())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		pickingCandidateService.removeQtyFromHU()
				.qtyCU(qtyCU)
				.huId(pickingSlotRow.getHuId())
				.productId(pickingSlotRow.getHuProductId())
				.build()
				.perform();

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private boolean checkSourceHuPreconditionIncludingEmptyHUs()
	{
		final Collection<Integer> sourceHUs = sourceHUsRepository.retrieveMatchingSourceHUIds(getSingleSelectedRow().getHuId());
		return !sourceHUs.isEmpty();
	}
}
