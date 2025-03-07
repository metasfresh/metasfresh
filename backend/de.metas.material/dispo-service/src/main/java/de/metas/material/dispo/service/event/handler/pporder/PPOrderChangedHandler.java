package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.Profiles;
import de.metas.document.engine.DocStatus;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderId;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	private static final Logger logger = LogManager.getLogger(PPOrderChangedHandler.class);

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
	public Collection<Class<? extends PPOrderChangedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderChangedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderChangedEvent event)
	{
		final PPOrderId ppOrderId = PPOrderId.ofRepoId(event.getPpOrderId());
		final List<Candidate> candidatesToUpdate = candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(ppOrderId);
		Check.errorIf(candidatesToUpdate.isEmpty(), "No Candidates found for PP_Order_ID={}", ppOrderId);

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		//
		// Header candidate (supply)
		final Candidate headerCandidate;
		{
			final Candidate headerCandidateToUpdate = extractHeaderCandidate(candidatesToUpdate);
			headerCandidate = processPPOrderChange(headerCandidateToUpdate, event);
			updatedCandidatesToPersist.add(headerCandidate);
		}

		updatedCandidatesToPersist.addAll(
				processPPOrderLinesChanges(
						candidatesToUpdate,
						event.getNewDocStatus(),
						event.getPpOrderLineChanges()));
		// TODO: handle delete and creation of new lines

		updatedCandidatesToPersist.forEach(candidateChangeService::onCandidateNewOrChange);
	}

	private static Candidate extractHeaderCandidate(final List<Candidate> candidates)
	{
		Candidate headerCandidate = null;
		for (final Candidate candidate : candidates)
		{
			final ProductionDetail productionDetailToUpdate = ProductionDetail.cast(candidate.getBusinessCaseDetail());
			if (productionDetailToUpdate.isFinishedGoods())
			{
				if (headerCandidate != null)
				{
					throw new AdempiereException("More than one header candidate found. " + headerCandidate + ", " + candidate)
							.appendParametersToMessage()
							.setParameter("1stHeaderCandidate", headerCandidate)
							.setParameter("2ndHeaderCandidate", candidate);
				}

				headerCandidate = candidate;
			}
		}

		if (headerCandidate == null)
		{
			throw new AdempiereException("No header candidate found in " + candidates);
		}

		return headerCandidate;
	}

	private static Candidate processPPOrderChange(
			@NonNull final Candidate candidateToUpdate,
			@NonNull final PPOrderChangedEvent ppOrderChangedEvent)
	{
		final ProductionDetail productionDetailToUpdate = ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
		if (!productionDetailToUpdate.isFinishedGoods())
		{
			throw new AdempiereException("Parameter candidateToUpdate needs to have finishedGoodsCandidate=true")
					.appendParametersToMessage()
					.setParameter("candidateToUpdate", candidateToUpdate);
		}

		final DocStatus newDocStatusFromEvent = ppOrderChangedEvent.getNewDocStatus();
		final BigDecimal newPlannedQty = ppOrderChangedEvent.getNewQtyRequired();

		final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
				.ppOrderDocStatus(newDocStatusFromEvent)
				.qty(newPlannedQty)
				.build();

		final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

		final Candidate updatedCandidate = candidateToUpdate.toBuilder()
				.businessCaseDetail(updatedProductionDetail)
				.materialDescriptor(candidateToUpdate.getMaterialDescriptor()
											.withQuantity(newCandidateQty)
											.withDate(ppOrderChangedEvent.getNewDatePromised()))
				.build();

		return updatedCandidate;
	}

	private static List<Candidate> processPPOrderLinesChanges(
			@NonNull final List<Candidate> candidatesToUpdate,
			@NonNull final DocStatus ppOrderDocStatus,
			@NonNull final List<ChangedPPOrderLineDescriptor> ppOrderLineChanges)
	{
		final ImmutableMap<PPOrderAndBOMLineId, ChangedPPOrderLineDescriptor> ppOrderLineChangesByPPOrderLineId = Maps.uniqueIndex(ppOrderLineChanges, ChangedPPOrderLineDescriptor::getOldPPOrderLineId);

		final List<Candidate> updatedCandidates = new ArrayList<>();
		for (final Candidate candidateToUpdate : candidatesToUpdate)
		{
			final ProductionDetail productionDetailToUpdate = ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
			if (!productionDetailToUpdate.isBOMLine())
			{
				continue; // this is the header's candidate; deal with it in the other method
			}

			final PPOrderAndBOMLineId ppOrderBOMLineId = productionDetailToUpdate.getPpOrderAndBOMLineId();
			final ChangedPPOrderLineDescriptor changeDescriptor = ppOrderLineChangesByPPOrderLineId.get(ppOrderBOMLineId);
			if (changeDescriptor != null) // might be null if the line got deleted
			{
				final Candidate updatedCandidate = processPPOrderLineChange(candidateToUpdate, ppOrderDocStatus, changeDescriptor);
				updatedCandidates.add(updatedCandidate);
			}
		}

		return updatedCandidates;
	}

	private static Candidate processPPOrderLineChange(
			@NonNull final Candidate candidateToUpdate,
			@NonNull final DocStatus ppOrderDocStatus,
			@NonNull final ChangedPPOrderLineDescriptor ppOrderLineChange)
	{
		final ProductionDetail productionDetailToUpdate = ProductionDetail.cast(candidateToUpdate.getBusinessCaseDetail());
		if (!productionDetailToUpdate.isBOMLine())
		{
			throw new AdempiereException("Parameter candidateToUpdate needs to have bomLine=true")
					.appendParametersToMessage()
					.setParameter("candidateToUpdate", candidateToUpdate);
		}

		final BigDecimal newPlannedQty = ppOrderLineChange.getNewQtyRequired();

		final ProductionDetail updatedProductionDetail = productionDetailToUpdate.toBuilder()
				.ppOrderDocStatus(ppOrderDocStatus)
				.ppOrderRef(PPOrderRef.withPPOrderAndBOMLineId(productionDetailToUpdate.getPpOrderRef(), ppOrderLineChange.getNewPPOrderLineId()))
				.qty(newPlannedQty)
				.build();

		final BigDecimal newCandidateQty = newPlannedQty.max(candidateToUpdate.computeActualQty());

		return candidateToUpdate.toBuilder()
				.businessCaseDetail(updatedProductionDetail)
				.materialDescriptor(candidateToUpdate.getMaterialDescriptor()
											.withQuantity(newCandidateQty)
											.withDate(ppOrderLineChange.getIssueOrReceiveDate()))
				.build();
	}

}
