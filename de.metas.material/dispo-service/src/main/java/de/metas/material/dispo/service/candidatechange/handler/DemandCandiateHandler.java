package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.StockMultiQuery;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
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
public class DemandCandiateHandler implements CandidateHandler
{
	private final CandidateRepositoryRetrieval candidateRepository;

	private final StockRepository stockRepository;

	private final PostMaterialEventService materialEventService;

	private final StockCandidateService stockCandidateService;

	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	public DemandCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final StockRepository stockRepository,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepository;
		this.candidateRepositoryWriteService = candidateRepositoryCommands;
		this.materialEventService = materialEventService;
		this.stockRepository = stockRepository;
		this.stockCandidateService = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.DEMAND, CandidateType.UNRELATED_DECREASE);
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also its <b>child</b> stock candidate.
	 */
	@Override
	public Candidate onCandidateNewOrChange(@NonNull final Candidate demandCandidate)
	{
		assertCorrectCandidateType(demandCandidate);

		final Candidate demandCandidateWithId = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(demandCandidate);

		if (demandCandidateWithId.getQuantity().signum() == 0)
		{
			// this candidate didn't change anything
			return demandCandidateWithId;
		}

		// this is the seqno which the new stock candidate shall get according to the demand candidate
		final int expectedStockSeqNo = demandCandidateWithId.getSeqNo() + 1;

		final Candidate childStockWithDemand;
		final Candidate childStockWithDemandDelta;

		final Optional<Candidate> possibleChildStockCandidate = candidateRepository.retrieveSingleChild(demandCandidateWithId.getId());
		if (possibleChildStockCandidate.isPresent())
		{
			childStockWithDemand = possibleChildStockCandidate.get().withQuantity(demandCandidate.getQuantity().negate());
			childStockWithDemandDelta = stockCandidateService.updateQty(childStockWithDemand);
		}
		else
		{
			// check if there is a supply record with the same demand detail and material descriptor
			final Candidate existingSupplyParentStockWithoutParentId = retrieveSupplyParentStockWithoutParentIdOrNull(demandCandidateWithId);
			if (existingSupplyParentStockWithoutParentId != null)
			{
				//
				final Candidate existingSupplyParentStockWithUpdatedQty = existingSupplyParentStockWithoutParentId
						.withQuantity(existingSupplyParentStockWithoutParentId.getQuantity().subtract(demandCandidateWithId.getQuantity()))
						.withParentId(CandidatesQuery.UNSPECIFIED_PARENT_ID);

				childStockWithDemandDelta = stockCandidateService.updateQty(existingSupplyParentStockWithUpdatedQty);
				childStockWithDemand = existingSupplyParentStockWithUpdatedQty;
			}
			else
			{
				final Candidate newDemandCandidateChild = stockCandidateService.createStockCandidate(demandCandidateWithId.withNegatedQuantity());
				childStockWithDemandDelta = candidateRepositoryWriteService
						.addOrUpdatePreserveExistingSeqNo(newDemandCandidateChild);
				childStockWithDemand = childStockWithDemandDelta.withQuantity(newDemandCandidateChild.getQuantity());
			}
		}

		candidateRepositoryWriteService.updateCandidate(childStockWithDemand
				.withParentId(demandCandidateWithId.getId()));

		final BigDecimal delta = childStockWithDemandDelta.getQuantity();
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(
				childStockWithDemandDelta.getMaterialDescriptor(),
				childStockWithDemandDelta.getGroupId(),
				delta);

		final Candidate demandCandidateToReturn;

		if (childStockWithDemandDelta.getSeqNo() != expectedStockSeqNo)
		{
			// there was already a stock candidate which already had a seqNo.
			// keep it and in turn update the demandCandidate's seqNo accordingly
			demandCandidateToReturn = demandCandidate
					.withSeqNo(childStockWithDemandDelta.getSeqNo() - 1);
			candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(demandCandidateToReturn);
		}
		else
		{
			demandCandidateToReturn = demandCandidateWithId;
		}

		if (demandCandidateWithId.getType() == CandidateType.DEMAND)
		{
			fireSupplyRequiredEventIfQtyBelowZero(demandCandidateWithId);
		}
		return demandCandidateToReturn;
	}

	private void assertCorrectCandidateType(@NonNull final Candidate demandCandidate)
	{
		final CandidateType type = demandCandidate.getType();

		Preconditions.checkArgument(
				type == CandidateType.DEMAND || type == CandidateType.UNRELATED_DECREASE,
				"Given parameter 'demandCandidate' has type=%s; demandCandidate=%s",
				type, demandCandidate);
	}

	private Candidate retrieveSupplyParentStockWithoutParentIdOrNull(@NonNull final Candidate demandCandidateWithId)
	{
		final CandidatesQuery queryForExistingSupply = CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.demandDetail(demandCandidateWithId.getDemandDetail())
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(demandCandidateWithId.getMaterialDescriptor()))
				.build();

		final Candidate existingSupplyParentStockWithoutOwnParentId;
		final Candidate existingSupply = candidateRepository.retrieveLatestMatchOrNull(queryForExistingSupply);
		if (existingSupply != null && existingSupply.getParentId() > 0)
		{
			final Candidate existingSupplyParentStock = candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(existingSupply.getParentId()));
			if (existingSupplyParentStock.getParentId() > 0)  // we only want to dock with currently "dangling" stock records
			{
				existingSupplyParentStockWithoutOwnParentId = null;
			}
			else
			{
				existingSupplyParentStockWithoutOwnParentId = existingSupplyParentStock;
			}
		}
		else
		{
			existingSupplyParentStockWithoutOwnParentId = null;
		}
		return existingSupplyParentStockWithoutOwnParentId;
	}

	private void fireSupplyRequiredEventIfQtyBelowZero(@NonNull final Candidate demandCandidateWithId)
	{
		final StockMultiQuery query = StockMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(demandCandidateWithId.getMaterialDescriptor());

		final BigDecimal availableQuantityAfterDemandWasApplied = stockRepository.retrieveAvailableStockQtySum(query);

		if (availableQuantityAfterDemandWasApplied.signum() < 0)
		{
			final BigDecimal requiredQty = availableQuantityAfterDemandWasApplied.negate();

			final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator //
					.createSupplyRequiredEvent(demandCandidateWithId, requiredQty);
			materialEventService.postEventAfterNextCommit(supplyRequiredEvent);
		}
	}
}
