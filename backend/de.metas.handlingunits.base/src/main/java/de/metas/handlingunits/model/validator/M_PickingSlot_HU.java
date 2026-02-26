package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_PickingSlot_HU;
import de.metas.handlingunits.model.I_M_PickingSlot_Trx;
import de.metas.handlingunits.picking.slot.IHUPickingSlotBL;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

@Validator(I_M_PickingSlot_HU.class)
public class M_PickingSlot_HU
{
	private final IHUPickingSlotBL pickingSlotBL = Services.get(IHUPickingSlotBL.class);

	/**
	 * If a picking-slot-hu is made by a user, then add it to the picking-slot-hu and update the respective {@link I_M_PickingSlot_Trx} to <code>IsUserAction='Y'</code>.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW }, ifUIAction = true)
	public void onNewAsUserAction(final I_M_PickingSlot_HU pickingSlotHU)
	{
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoId(pickingSlotHU.getM_PickingSlot_ID());
		final HuId huId = HuId.ofRepoId(pickingSlotHU.getM_HU_ID());

		final IHUPickingSlotBL.IQueueActionResult result = pickingSlotBL.addToPickingSlotQueue(pickingSlotId, huId);

		final I_M_PickingSlot_Trx pickingSlotTrx = Check.assumeNotNull(result.getM_PickingSlot_Trx(),
				"The result of addToPickingSlotQueue contains a M_PickingSlot_Trx for {} and {} ", pickingSlotId, huId);
		pickingSlotTrx.setIsUserAction(true);
		InterfaceWrapperHelper.save(pickingSlotTrx);
	}

	/**
	 * If a picking-slot-hu is deleted by a user, then remove it from the picking-slot-hu and update the respective {@link I_M_PickingSlot_Trx} to <code>IsUserAction='Y'</code>.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_DELETE }, ifUIAction = true)
	public void onDeleteAsUserAction(final I_M_PickingSlot_HU pickingSlotHU)
	{
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoId(pickingSlotHU.getM_PickingSlot_ID());
		final HuId huId = HuId.ofRepoId(pickingSlotHU.getM_HU_ID());

		final IHUPickingSlotBL.IQueueActionResult result = pickingSlotBL.removeFromPickingSlotQueue(pickingSlotId, huId);

		final I_M_PickingSlot_Trx pickingSlotTrx = Check.assumeNotNull(result.getM_PickingSlot_Trx(),
				"The result of addToPickingSlotQueue contains a M_PickingSlot_Trx for {} and {} ", pickingSlotId, huId);
		pickingSlotTrx.setIsUserAction(true);
		InterfaceWrapperHelper.save(pickingSlotTrx);
	}
}
