package de.metas.handlingunits.picking.pickingCandidateCommands;

import java.util.List;

import org.adempiere.util.Services;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_Picking_Candidate;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.picking.api.PickingSlotId;
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

public class RemoveHUFromPickingSlotCommand
{
	private final IHUPickingSlotBL huPickingSlotBL = Services.get(IHUPickingSlotBL.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final HuId huId;

	@Builder
	private RemoveHUFromPickingSlotCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@NonNull final HuId huId)
	{
		this.pickingCandidateRepository = pickingCandidateRepository;
		this.huId = huId;
	}

	public void perform()
	{
		final List<I_M_Picking_Candidate> candidates = retrievePickingCandidates();
		final ImmutableSet<PickingSlotId> pickingSlotIds = candidates.stream()
				.map(pc -> PickingSlotId.ofRepoId(pc.getM_PickingSlot_ID()))
				.distinct()
				.collect(ImmutableSet.toImmutableSet());

		pickingCandidateRepository.deletePickingCandidates(candidates);
		pickingSlotIds.forEach(huPickingSlotBL::releasePickingSlotIfPossible);

	}

	private List<I_M_Picking_Candidate> retrievePickingCandidates()
	{
		return pickingCandidateRepository.retrievePickingCandidatesByHUIds(ImmutableSet.of(huId));
	}

}
