package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
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

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderQtyChangedHandler implements MaterialEventHandler<PPOrderChangedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public PPOrderQtyChangedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateChangeService candidateChangeService)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;

	}

	@Override
	public Collection<Class<? extends PPOrderChangedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PPOrderChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderChangedEvent ppOrderQtyChangedEvent)
	{
		final List<Candidate> candidatesToUpdate = PPOrderUtil.retrieveCandidatesForPPOrderId(
				candidateRepositoryRetrieval,
				ppOrderQtyChangedEvent.getPpOrderId());

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		updatedCandidatesToPersist.addAll(processPPOrderChange(candidatesToUpdate, ppOrderQtyChangedEvent));
		updatedCandidatesToPersist.addAll(processPPOrderLineChanges(candidatesToUpdate, ppOrderQtyChangedEvent.getPpOrderLineChanges()));

		// TODO: handle delete and creation of new lines

		updatedCandidatesToPersist.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}

	private List<Candidate> processPPOrderChange(
			@NonNull final List<Candidate> candidatesToUpdate,
			@NonNull final PPOrderChangedEvent ppOrderQtyChangedEvent)
	{
		final List<Candidate> updatedCandidates = new ArrayList<>();
		for (final Candidate candidateToUpdate : candidatesToUpdate)
		{
			final ProductionDetail productionDetailToUpdate = //
					ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
			if (productionDetailToUpdate.getPpOrderLineId() > 0)
			{
				continue;
			}

			final BigDecimal newPlannedQty = ppOrderQtyChangedEvent.getNewQuantity();
			final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
					.plannedQty(newPlannedQty)
					.build();

			final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

			final Candidate updatedCandidate = candidateToUpdate.toBuilder()
					.businessCaseDetail(updatedProductionDetail)
					.materialDescriptor(candidateToUpdate.getMaterialDescriptor().withQuantity(newCandidateQty))
					.build();
			updatedCandidates.add(updatedCandidate);
		}
		return updatedCandidates;
	}

	private List<Candidate> processPPOrderLineChanges(
			@NonNull final List<Candidate> candidatesToUpdate,
			@NonNull final List<ChangedPPOrderLineDescriptor> ppOrderLineChanges)
	{
		final List<Candidate> updatedCandidates = new ArrayList<>();

		final ImmutableMap<Integer, ChangedPPOrderLineDescriptor> oldPPOrderLineId2ChangeDescriptor = Maps.uniqueIndex(ppOrderLineChanges, ChangedPPOrderLineDescriptor::getOldPPOrderLineId);

		for (final Candidate candidateToUpdate : candidatesToUpdate)
		{
			final ProductionDetail productionDetailToUpdate = //
					ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());

			if (productionDetailToUpdate.getPpOrderLineId() <= 0)
			{
				continue;
			}

			final ChangedPPOrderLineDescriptor changeDescriptor = oldPPOrderLineId2ChangeDescriptor
					.get(productionDetailToUpdate.getPpOrderLineId());

			final BigDecimal newPlannedQty = changeDescriptor.getNewQuantity();

			final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
					.ppOrderLineId(changeDescriptor.getNewPPOrderLineId())
					.plannedQty(newPlannedQty)
					.build();

			final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

			final Candidate updatedCandidate = candidateToUpdate.toBuilder()
					.businessCaseDetail(updatedProductionDetail)
					.materialDescriptor(candidateToUpdate.getMaterialDescriptor().withQuantity(newCandidateQty))
					.build();

			updatedCandidates.add(updatedCandidate);
		}

		return updatedCandidates;
	}
}
