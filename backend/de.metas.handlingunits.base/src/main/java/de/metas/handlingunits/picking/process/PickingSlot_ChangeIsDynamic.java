/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.process;

import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.picking.PickingSlotService;
import de.metas.handlingunits.picking.requests.ReleasePickingSlotRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.api.IPickingSlotDAO;
import de.metas.picking.api.PickingSlotId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class PickingSlot_ChangeIsDynamic extends JavaProcess implements IProcessPrecondition
{
	private final static AdMessageKey SLOT_CANNOT_BE_RELEASED = AdMessageKey.of("de.metas.handlingunits.picking.process.SLOT_CANNOT_BE_RELEASED");

	@Param(parameterName = "IsDynamic")
	private boolean isDynamic;

	private final IPickingSlotDAO slotDAO = Services.get(IPickingSlotDAO.class);
	private final SpringContextHolder.Lazy<PickingSlotService> pickingSlotServiceLazy = SpringContextHolder.lazyBean(PickingSlotService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_PickingSlot pickingSlot = slotDAO.getById(PickingSlotId.ofRepoId(getRecord_ID()), I_M_PickingSlot.class);
		if (isDynamic == pickingSlot.isDynamic())
		{
			//nothing to do
			return MSG_OK;
		}

		if (pickingSlot.isDynamic())
		{
			turnOffDynamicAllocation(pickingSlot);
		}
		else
		{
			turnOnDynamicAllocation(pickingSlot);
		}

		return MSG_OK;
	}

	private void turnOnDynamicAllocation(@NonNull final I_M_PickingSlot pickingSlot)
	{
		pickingSlot.setIsDynamic(true);
		slotDAO.save(pickingSlot);
	}

	private void turnOffDynamicAllocation(@NonNull final I_M_PickingSlot pickingSlot)
	{
		final PickingSlotId pickingSlotId = PickingSlotId.ofRepoId(pickingSlot.getM_PickingSlot_ID());

		final boolean released = pickingSlotServiceLazy.get().releasePickingSlot(ReleasePickingSlotRequest.ofSlotId(pickingSlotId));
		if (!released)
		{
			throw new AdempiereException(SLOT_CANNOT_BE_RELEASED)
					.markAsUserValidationError();
		}

		pickingSlot.setIsDynamic(false);
		slotDAO.save(pickingSlot);
	}
}
