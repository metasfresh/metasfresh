package de.metas.handlingunits.picking.candidate.commands;

import de.metas.handlingunits.picking.slot.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateStatus;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

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
 * <p>
 * The status will be changed from Closed to Processed.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
class UnClosePickingCandidateCommand
{
	private final transient IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);

	private final PickingCandidate pickingCandidate;

	@Builder
	private UnClosePickingCandidateCommand(@NonNull final PickingCandidate pickingCandidate)
	{
		this.pickingCandidate = pickingCandidate;
	}

	/**
	 * Note to dev: keep in sync with {@link ClosePickingCandidateCommand#perform()}
	 */
	public void perform()
	{
		if (!PickingCandidateStatus.Closed.equals(pickingCandidate.getProcessingStatus()))
		{
			throw new AdempiereException("Invalid picking candidate status. Expected CLosed but it was " + pickingCandidate.getProcessingStatus())
					.setParameter("pickingCandidate", pickingCandidate);
		}

		final PickingSlotId pickingSlotId = pickingCandidate.getPickingSlotId();
		if (pickingSlotId == null)
		{
			throw new AdempiereException("Not in a picking slot");
		}

		if (huPickingSlotBL.isPickingRackSystem(pickingSlotId))
		{
			throw new AdempiereException("Unclosing a picking candidate when picking slot is a rack system is not allowed")
					.setParameter("M_PickingSlot_ID", pickingSlotId)
					.setParameter("pickingCandidate", pickingCandidate);
		}

		huPickingSlotBL.removeFromPickingSlotQueue(pickingSlotId, pickingCandidate.getPackedToHuId());
	}
}
