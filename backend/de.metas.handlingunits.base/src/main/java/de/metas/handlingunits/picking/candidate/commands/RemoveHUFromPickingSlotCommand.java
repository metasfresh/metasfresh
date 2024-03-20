package de.metas.handlingunits.picking.candidate.commands;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.IHUPickingSlotBL;
import de.metas.handlingunits.picking.PickingCandidate;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.picking.api.PickingSlotId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.util.lang.IMutable;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final PickingCandidateRepository pickingCandidateRepository;

	private final HuId huId;
	private final PickingSlotId pickingSlotId;

	@Builder
	private RemoveHUFromPickingSlotCommand(
			@NonNull final PickingCandidateRepository pickingCandidateRepository,
			@Nullable final HuId huId,
			@Nullable final PickingSlotId pickingSlotId)
	{
		Check.assume(huId != null || pickingSlotId != null, "At least one of HuId or PickingSlot must be set");

		this.pickingCandidateRepository = pickingCandidateRepository;
		this.huId = huId;
		this.pickingSlotId = pickingSlotId;
	}

	public void perform()
	{
		final Set<HuId> huIdAndDownstream = new HashSet<>();
		new HUIterator()
			.setEnableStorageIteration(false)
			.setListener(new HUIteratorListenerAdapter()
			{
				@Override
				public Result beforeHU(IMutable<I_M_HU> hu)
				{
					huIdAndDownstream.add(HuId.ofRepoId(hu.getValue().getM_HU_ID()));
					return Result.CONTINUE;
				}
			})
			.iterate(handlingUnitsDAO.getById(huId));

		final List<PickingCandidate> candidates = retrievePickingCandidates(ImmutableSet.copyOf(huIdAndDownstream));
		final Set<PickingSlotId> pickingSlotIds = PickingCandidate.extractPickingSlotIds(candidates);

		pickingCandidateRepository.deletePickingCandidates(candidates);
		huPickingSlotBL.releasePickingSlotsIfPossible(pickingSlotIds);

	}

	private List<PickingCandidate> retrievePickingCandidates(@NonNull final ImmutableSet<HuId> huIds)
	{
		return pickingCandidateRepository.getDraftedByHuIdAndPickingSlotId(huIds, pickingSlotId);
	}
}
