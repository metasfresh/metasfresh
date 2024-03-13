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

package de.metas.handlingunits.picking;

import de.metas.handlingunits.picking.job.service.PickingJobService;
import de.metas.handlingunits.picking.requests.ReleasePickingSlotRequest;
import de.metas.i18n.AdMessageKey;
import de.metas.picking.api.IPickingSlotBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickingSlotService
{
	private final static AdMessageKey ONGOING_PICKING_JOBS_ERR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.ONGOING_PICKING_JOBS_ERR_MSG");
	private final static AdMessageKey DRAFTED_PICKING_CANDIDATES_ERR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.DRAFTED_PICKING_CANDIDATES_ERR_MSG");
	private final static AdMessageKey QUEUED_HUS_ON_SLOT_ERR_MSG = AdMessageKey.of("de.metas.handlingunits.picking.QUEUED_HUS_ON_SLOT_ERR_MSG");

	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final IPickingSlotBL pickingSlotBL = Services.get(IPickingSlotBL.class);
	private final PickingJobService pickingJobService;
	private final PickingCandidateService pickingCandidateService;

	public boolean releasePickingSlot(@NonNull final ReleasePickingSlotRequest request)
	{
		final boolean clearedAllPickingJobs = pickingJobService.clearAssignmentsForSlot(request.getPickingSlotId(), request.isAbortOngoingPickingJobs());
		if (!clearedAllPickingJobs)
		{
			throw new AdempiereException(ONGOING_PICKING_JOBS_ERR_MSG).markAsUserValidationError();
		}

		final boolean clearedAllUnprocessedHUs = pickingCandidateService.clearPickingSlot(request.getPickingSlotId(), request.isRemoveUnprocessedHUsFromSlot());
		if (!clearedAllUnprocessedHUs)
		{
			throw new AdempiereException(DRAFTED_PICKING_CANDIDATES_ERR_MSG).markAsUserValidationError();
		}

		final boolean clearedAllQueuedHUs = huPickingSlotBL.clearPickingSlotQueue(request.getPickingSlotId(), request.isRemoveQueuedHUsFromSlot());
		if (!clearedAllQueuedHUs)
		{
			throw new AdempiereException(QUEUED_HUS_ON_SLOT_ERR_MSG).markAsUserValidationError();
		}

		huPickingSlotBL.releasePickingSlotIfPossible(request.getPickingSlotId());

		return pickingSlotBL.isAvailableForAnyBPartner(request.getPickingSlotId());
	}
}
