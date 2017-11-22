package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialQuery;
import de.metas.material.dispo.commons.repository.StockRepository;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.demandWasFound.SupplyRequiredEvent;
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

	private final MaterialEventService materialEventService;

	private final StockCandidateService stockCandidateServiceRetrieval;

	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	public DemandCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final MaterialEventService materialEventService,
			@NonNull final StockRepository stockRepository,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepository;
		this.candidateRepositoryWriteService = candidateRepositoryCommands;
		this.materialEventService = materialEventService;
		this.stockRepository = stockRepository;
		this.stockCandidateServiceRetrieval = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.DEMAND, CandidateType.UNRELATED_DECREASE);
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also it's <b>child</b> stock candidate.
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

		final Optional<Candidate> possibleChildStockCandidate = candidateRepository.retrieveSingleChild(demandCandidateWithId.getId());
		if (possibleChildStockCandidate.isPresent())
		{
			// this supply candidate is not new and already has a stock candidate as its parent. be sure to update exactly *that* candidate
			childStockWithDemand = stockCandidateServiceRetrieval
					.updateStock(
							demandCandidateWithId, () -> {
								// don't check if we might create a new stock candidate, because we know we don't.
								// Instead we might run into trouble with CandidateRepository.retrieveExact() and multiple matching records.
								// So get the one that we know already exists and just update its quantity
								final Candidate childStockCandidate = possibleChildStockCandidate.get();
								return candidateRepositoryWriteService
										.updateQty(
												childStockCandidate
														.withQuantity(
																childStockCandidate.getQuantity().subtract(demandCandidateWithId.getQuantity())));
							});
		}
		else
		{
			// check if there is a supply record with the same demand detail and material descriptor, check
			final CandidatesQuery queryForExistingSupply = CandidatesQuery.builder()
					.type(CandidateType.SUPPLY)
					.demandDetail(demandCandidateWithId.getDemandDetail())
					.materialDescriptor(demandCandidateWithId.getMaterialDescriptor().withoutQuantity())
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
			if (existingSupplyParentStockWithoutOwnParentId != null)
			{
				final Candidate existingSupplyParentWithUpdatedQty = existingSupplyParentStockWithoutOwnParentId
						.withQuantity(existingSupplyParentStockWithoutOwnParentId.getQuantity().subtract(demandCandidateWithId.getQuantity()))
						.withParentId(CandidatesQuery.UNSPECIFIED_PARENT_ID);

				candidateRepositoryWriteService.updateQty(existingSupplyParentWithUpdatedQty);
				childStockWithDemand = existingSupplyParentWithUpdatedQty;
			}
			else
			{
				final Candidate newDemandCandidateChild = stockCandidateServiceRetrieval.createStockCandidate(demandCandidateWithId.withNegatedQuantity());
				childStockWithDemand = candidateRepositoryWriteService
						.addOrUpdatePreserveExistingSeqNo(newDemandCandidateChild);
			}
		}

		candidateRepositoryWriteService.updateOverwriteStoredSeqNo(childStockWithDemand
				.withParentId(demandCandidateWithId.getId()));

		final Candidate demandCandidateToReturn;

		if (childStockWithDemand.getSeqNo() != expectedStockSeqNo)
		{
			// there was already a stock candidate which already had a seqNo.
			// keep it and in turn update the demandCandidate's seqNo accordingly
			demandCandidateToReturn = demandCandidate
					.withSeqNo(childStockWithDemand.getSeqNo() - 1);
			candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(demandCandidateToReturn);
		}
		else
		{
			demandCandidateToReturn = demandCandidateWithId;
		}

		if (demandCandidate.getType() == CandidateType.DEMAND)
		{
			final MaterialQuery query = MaterialQuery.forMaterialDescriptor(demandCandidate.getMaterialDescriptor());
			final BigDecimal availableQuantity = stockRepository.retrieveAvailableStockQtySum(query);
			final boolean demandExceedsAvailableQty = demandCandidate.getQuantity().compareTo(availableQuantity) > 0;

			if (demandExceedsAvailableQty)
			{
				// there would be no more stock left, so
				// notify whoever is in charge that we have a demand to balance
				final BigDecimal requiredAdditionalQty = demandCandidate.getQuantity().subtract(availableQuantity);

				final SupplyRequiredEvent materialDemandEvent = SupplyRequiredEventCreator.createMaterialDemandEvent(demandCandidateWithId, requiredAdditionalQty);
				materialEventService.fireEvent(materialDemandEvent);
			}
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
}
