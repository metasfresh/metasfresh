package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.Check;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
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
public class PPOrderChangedHandler implements MaterialEventHandler<PPOrderChangedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public PPOrderChangedHandler(
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
	public void handleEvent(@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		final List<Candidate> candidatesToUpdate = PPOrderUtil.retrieveCandidatesForPPOrderId(
				candidateRepositoryRetrieval,
				ppOrderChangedEvent.getPpOrderId());

		Check.errorIf(candidatesToUpdate.isEmpty(),
				"No Candidates found for PP_Order_ID={} ",
				ppOrderChangedEvent.getPpOrderId());

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		updatedCandidatesToPersist.addAll(
				processPPOrderChange(
						candidatesToUpdate,
						ppOrderChangedEvent));

		updatedCandidatesToPersist.addAll(
				processPPOrderLineChanges(
						candidatesToUpdate,
						ppOrderChangedEvent.getDocStatus(),
						ppOrderChangedEvent.getPpOrderLineChanges()));

		// TODO: handle delete and creation of new lines

		updatedCandidatesToPersist.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}

	private List<Candidate> processPPOrderChange(
			@NonNull final List<Candidate> candidatesToUpdate,
			@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		final String newDocStatusFromEvent = ppOrderChangedEvent.getDocStatus();
		final CandidateStatus newCandidateStatus = EventUtil
				.getCandidateStatus(newDocStatusFromEvent);

		final List<Candidate> updatedCandidates = new ArrayList<>();
		for (final Candidate candidateToUpdate : candidatesToUpdate)
		{
			final ProductionDetail productionDetailToUpdate = //
					ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
			if (productionDetailToUpdate.getPpOrderLineId() > 0)
			{
				continue; // this is a line's candidate; deal with it in the other method
			}

			final BigDecimal newPlannedQty = ppOrderChangedEvent.getNewQtyRequired();
			final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
					.ppOrderDocStatus(newDocStatusFromEvent)
					.plannedQty(newPlannedQty)
					.build();

			final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

			final Candidate updatedCandidate = candidateToUpdate.toBuilder()
					.status(newCandidateStatus)
					.businessCaseDetail(updatedProductionDetail)
					.materialDescriptor(candidateToUpdate.getMaterialDescriptor().withQuantity(newCandidateQty))
					.build();
			updatedCandidates.add(updatedCandidate);
		}
		return updatedCandidates;
	}

	private List<Candidate> processPPOrderLineChanges(
			@NonNull final List<Candidate> candidatesToUpdate,
			@NonNull final String newDocStatusFromEvent,
			@NonNull final List<ChangedPPOrderLineDescriptor> ppOrderLineChanges)
	{
		final List<Candidate> updatedCandidates = new ArrayList<>();

		final CandidateStatus newCandidateStatus = EventUtil
				.getCandidateStatus(newDocStatusFromEvent);

		final ImmutableMap<Integer, ChangedPPOrderLineDescriptor> oldPPOrderLineId2ChangeDescriptor =//
				Maps.uniqueIndex(ppOrderLineChanges, ChangedPPOrderLineDescriptor::getOldPPOrderLineId);

		for (final Candidate candidateToUpdate : candidatesToUpdate)
		{
			final ProductionDetail productionDetailToUpdate = //
					ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
			if (productionDetailToUpdate.getPpOrderLineId() <= 0)
			{
				continue; // this is the header's candidate; deal with it in the other method
			}

			final ChangedPPOrderLineDescriptor changeDescriptor = oldPPOrderLineId2ChangeDescriptor
					.get(productionDetailToUpdate.getPpOrderLineId());

			final BigDecimal newPlannedQty = changeDescriptor.getNewQtyRequired();

			final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
					.ppOrderDocStatus(newDocStatusFromEvent)
					.ppOrderLineId(changeDescriptor.getNewPPOrderLineId())
					.plannedQty(newPlannedQty)
					.build();

			final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

			final Candidate updatedCandidate = candidateToUpdate.toBuilder()
					.status(newCandidateStatus)
					.businessCaseDetail(updatedProductionDetail)
					.materialDescriptor(candidateToUpdate.getMaterialDescriptor().withQuantity(newCandidateQty))
					.build();

			updatedCandidates.add(updatedCandidate);
		}

		return updatedCandidates;
	}
}
