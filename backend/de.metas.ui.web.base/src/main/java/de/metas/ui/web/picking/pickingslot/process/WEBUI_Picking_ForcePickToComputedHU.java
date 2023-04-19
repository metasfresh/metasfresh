/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.picking.pickingslot.process;

import de.metas.handlingunits.HuId;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import lombok.NonNull;

import java.util.Optional;

public class WEBUI_Picking_ForcePickToComputedHU extends WEBUI_Picking_PickQtyToComputedHU
		implements IProcessPrecondition, IProcessParametersCallout
{

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final Optional<ProcessPreconditionsResolution> preconditionsResolution = checkValidSelection();

		if (preconditionsResolution.isPresent())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(preconditionsResolution.get().getRejectReason());
		}

		if (!isForceDelivery())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason(" Use 'WEBUI_Picking_PickQtyToComputedHU' for non force shipping records!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected String doIt()
	{
		pickQtyToNewHUs(this::forcePickToNewHU);

		invalidatePackablesView();
		invalidatePickingSlotsView();

		return MSG_OK;
	}

	private void forcePickToNewHU(@NonNull final Quantity qtyCUToProcess)
	{
		final HuId packToHuId = createNewHuId();

		forcePick(qtyCUToProcess, packToHuId);

		printPickingLabelIfAutoPrint(packToHuId);
	}
}
