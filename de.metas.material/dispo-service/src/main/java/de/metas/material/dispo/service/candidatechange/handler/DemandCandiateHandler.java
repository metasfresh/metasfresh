package de.metas.material.dispo.service.candidatechange.handler;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
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

	private final MaterialEventService materialEventService;

	private final StockCandidateService stockCandidateService;

	private final CandidateRepositoryCommands candidateRepositoryCommands;

	public DemandCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryCommands candidateRepositoryCommands,
			@NonNull final MaterialEventService materialEventService,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepository;
		this.candidateRepositoryCommands = candidateRepositoryCommands;
		this.materialEventService = materialEventService;
		this.stockCandidateService = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(CandidateType.DEMAND, CandidateType.UNRELATED_DECREASE);
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also it's <b>child</b> stock candidate.
	 *
	 * @param demandCandidate
	 * @return
	 */
	@Override
	public Candidate onCandidateNewOrChange(@NonNull final Candidate demandCandidate)
	{
		assertCorrectCandidateType(demandCandidate);

		final Candidate demandCandidateWithId = candidateRepositoryCommands
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
			childStockWithDemand = stockCandidateService
					.updateStock(
							demandCandidateWithId, () -> {
								// don't check if we might create a new stock candidate, because we know we don't.
								// Instead we might run into trouble with CandidateRepository.retrieveExact() and multiple matching records.
								// So get the one that we know already exists and just update its quantity
								final Candidate childStockCandidate = possibleChildStockCandidate.get();
								return candidateRepositoryCommands
										.updateQty(
												childStockCandidate
														.withQuantity(
																childStockCandidate.getQuantity().subtract(demandCandidateWithId.getQuantity())));
							});
		}

		else
		{
			childStockWithDemand = stockCandidateService.addOrUpdateStock(
					demandCandidate
							.withSeqNo(expectedStockSeqNo)
							.withQuantity(demandCandidateWithId.getQuantity().negate())
							.withParentId(demandCandidateWithId.getId()));
		}

		final Candidate demandCandidateToReturn;

		if (childStockWithDemand.getSeqNo() != expectedStockSeqNo)
		{
			// there was already a stock candidate which already had a seqNo.
			// keep it and in turn update the demandCandidate's seqNo accordingly
			demandCandidateToReturn = demandCandidate
					.withSeqNo(childStockWithDemand.getSeqNo() - 1);
			candidateRepositoryCommands.addOrUpdateOverwriteStoredSeqNo(demandCandidateToReturn);
		}
		else
		{
			demandCandidateToReturn = demandCandidateWithId;
		}

		if (demandCandidate.getType() == CandidateType.DEMAND)
		{
			final BigDecimal availableQuantity = candidateRepository.retrieveAvailableStock(demandCandidate.getMaterialDescriptor());
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
