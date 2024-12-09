/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

<<<<<<< HEAD
import de.metas.handlingunits.HuId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
=======
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.Optional;

public class WEBUI_Picking_ForcePickToExistingHU extends WEBUI_Picking_PickQtyToExistingHU
		implements IProcessPrecondition
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
			return ProcessPreconditionsResolution.rejectWithInternalReason("Use WEBUI_Picking_PickQtyToExistingHU for non force shipping cases!");
		}

		return ProcessPreconditionsResolution.accept();
	}

	protected String doIt() throws Exception
	{
<<<<<<< HEAD
		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();

		final HuId packToHuId = pickingSlotRow.getHuId();

		validatePickingToHU();

		forcePick(getQtyToPack(), packToHuId);
=======
		validatePickingToHU();

		forcePick(getQtyToPack(), getPackToHuId());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}
}
