package de.metas.ui.web.pickingslotsClearing.process;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;

import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_PickingSlotsClearingView_TakeOutTUAndAddToLU extends PickingSlotsClearingViewBasedProcess implements IProcessPrecondition
{
	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		//
		// Validate the picking slots clearing selected row (left side)
		if (!isSingleSelectedPickingSlotRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one picking slots HU");
		}
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		if (!pickingSlotRow.isTU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select an TU");
		}

		//
		// Validate the packing HUs selected row (right side)
		if (!isSingleSelectedPackingHUsRow())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select one and only one HU to pack to");
		}
		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		if (!packingHURow.isLU())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("select a LU to pack too");
		}

		//
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PickingSlotRow pickingSlotRow = getSingleSelectedPickingSlotRow();
		Check.assume(pickingSlotRow.isTU(), "Picking slot HU shall be a TU: {}", pickingSlotRow);
		final I_M_HU tuHU = InterfaceWrapperHelper.load(pickingSlotRow.getHuId(), I_M_HU.class);

		final HUEditorRow packingHURow = getSingleSelectedPackingHUsRow();
		Check.assume(packingHURow.isLU(), "Pack to HU shall be a LU: {}", packingHURow);
		final I_M_HU luHU = packingHURow.getM_HU();

		final BigDecimal qtyTU = BigDecimal.ONE;

		HUTransformService.newInstance()
				.tuToExistingLU(tuHU, qtyTU, luHU);

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		// Invalidate views
		getPickingSlotsClearingView().invalidateAll();
		getPackingHUsView().invalidateAll();
	}
}
