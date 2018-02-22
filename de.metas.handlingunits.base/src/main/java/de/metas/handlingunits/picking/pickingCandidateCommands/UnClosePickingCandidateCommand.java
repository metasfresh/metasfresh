package de.metas.handlingunits.picking.pickingCandidateCommands;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.handlingunits.model.I_M_PickingSlot;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.model.X_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.IHUPickingSlotDAO;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * Unclose picking candidate.
 * 
 * The status will be changed from Closed to Processed.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class UnClosePickingCandidateCommand
{
	private final transient IHUPickingSlotDAO huPickingSlotDAO = Services.get(IHUPickingSlotDAO.class);
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	private final I_M_Picking_Candidate pickingCandidate;

	@Builder
	private UnClosePickingCandidateCommand(@NonNull final I_M_Picking_Candidate pickingCandidate)
	{
		this.pickingCandidate = pickingCandidate;
	}

	/**
	 * Note to dev: keep in sync with {@link ClosePickingCandidateCommand#perform()}
	 */
	public void perform()
	{
		if (!X_M_Picking_Candidate.STATUS_CL.equals(pickingCandidate.getStatus()))
		{
			throw new AdempiereException("Invalid picking candidate status. Expected CLosed but it was " + pickingCandidate.getStatus())
					.setParameter("pickingCandidate", pickingCandidate);
		}

		final int pickingSlotId = pickingCandidate.getM_PickingSlot_ID();
		if (huPickingSlotDAO.isPickingRackSystem(pickingSlotId))
		{
			throw new AdempiereException("Unclosing a picking candidate when picking slot is a rack system is not allowed")
					.setParameter("M_PickingSlot_ID", pickingSlotId)
					.setParameter("pickingCandidate", pickingCandidate);
		}

		final I_M_PickingSlot pickingSlot = load(pickingSlotId, I_M_PickingSlot.class);
		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlot, pickingCandidate.getM_HU());
	}
}
