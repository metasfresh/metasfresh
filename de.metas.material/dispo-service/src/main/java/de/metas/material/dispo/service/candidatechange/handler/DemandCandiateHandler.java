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
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
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

	private final AvailableToPromiseRepository availableToPromiseRepository;

	private final PostMaterialEventService materialEventService;

	private final StockCandidateService stockCandidateService;

	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	public DemandCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository,
			@NonNull final StockCandidateService stockCandidateService)
	{
		this.candidateRepository = candidateRepository;
		this.candidateRepositoryWriteService = candidateRepositoryCommands;
		this.materialEventService = materialEventService;
		this.availableToPromiseRepository = availableToPromiseRepository;
		this.stockCandidateService = stockCandidateService;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(
				CandidateType.DEMAND,
				CandidateType.UNRELATED_DECREASE,
				CandidateType.INVENTORY_DOWN);
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also its <b>child</b> stock candidate.
	 */
	@Override
	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		assertCorrectCandidateType(candidate);

		final SaveResult candidateSaveResult = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate);

		if (!candidateSaveResult.isDateChanged() && !candidateSaveResult.isQtyChanged())
		{
			return candidateSaveResult.toCandidateWithQtyDelta(); // nothing to do
		}

		final Candidate stockCandidate;

		final Candidate savedCandidate = candidateSaveResult.getCandidate();
		final Optional<Candidate> childStockCandidate = candidateRepository.retrieveSingleChild(savedCandidate.getId());
		if (childStockCandidate.isPresent())
		{
			stockCandidate = stockCandidateService
					.createStockCandidate(savedCandidate.withNegatedQuantity())
					.withId(childStockCandidate.get().getId());
		}
		else
		{
			stockCandidate = stockCandidateService
					.createStockCandidate(savedCandidate.withNegatedQuantity());
		}

		final Candidate savedStockCandidate = candidateRepositoryWriteService
				.addOrUpdateOverwriteStoredSeqNo(stockCandidate.withParentId(savedCandidate.getId()))
				.getCandidate();

		final SaveResult deltaToApplyToLaterStockCandiates = SaveResult.builder()
				.previousQty(candidateSaveResult.getPreviousQty())
				.previousTime(candidateSaveResult.getPreviousTime())
				.candidate(savedCandidate)
				.build();

		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(deltaToApplyToLaterStockCandiates);

		// set the stock candidate as child for the demand candidate
		candidateRepositoryWriteService.updateCandidateById(
				savedCandidate
						.withParentId(savedStockCandidate.getId()));

		final Candidate candidateToReturn = candidateSaveResult
				.toCandidateWithQtyDelta()
				.withParentId(savedStockCandidate.getId());

		if (savedCandidate.getType() == CandidateType.DEMAND)
		{
			fireSupplyRequiredEventIfQtyBelowZero(candidateToReturn);
		}
		return candidateToReturn;
	}
	//
	// public Candidate onCandidateNewOrChange2(@NonNull final Candidate candidate)
	// {
	// assertCorrectCandidateType(candidate);
	//
	// final SaveResult candidateSaveResult = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate);
	//
	// final Candidate savedCandidate = candidateSaveResult.getCandidate();
	// if (!candidateSaveResult.isDateChanged() && !candidateSaveResult.isQtyChanged())
	// {
	// return candidateSaveResult.toCandidateWithQtyDelta(); // nothing to do
	// }
	//
	// // this is the seqno which the new stock candidate shall get according to the demand candidate
	// final int expectedStockSeqNo = savedCandidate.getSeqNo() + 1;
	//
	// final Candidate childStockWithDemand;
	// final SaveResult childStockWithDemandDelta;
	//
	// final Optional<Candidate> possibleChildStockCandidate = candidateRepository.retrieveSingleChild(savedCandidate.getId());
	// if (possibleChildStockCandidate.isPresent())
	// {
	// // TODO must know&handle if date changed
	// childStockWithDemand = possibleChildStockCandidate.get()
	// .withQuantity(savedCandidate.getQuantity().negate())
	// .withDate(savedCandidate.getDate());
	// childStockWithDemandDelta = stockCandidateService.updateQtyAndDate(childStockWithDemand); // TODO shall be updateQtyAndDate
	// }
	// else
	// {
	// final Candidate templateForNewDemandCandidateChild = savedCandidate
	// .withQuantity(candidateSaveResult.getQtyDelta().negate())
	// .withSeqNo(expectedStockSeqNo);
	// final Candidate newDemandCandidateChild = stockCandidateService.createStockCandidate(templateForNewDemandCandidateChild);
	//
	// childStockWithDemandDelta = candidateRepositoryWriteService
	// .addOrUpdatePreserveExistingSeqNo(newDemandCandidateChild);
	// childStockWithDemand = childStockWithDemandDelta.getCandidate();
	// }
	//
	// candidateRepositoryWriteService
	// .updateCandidateById(childStockWithDemand.withParentId(savedCandidate.getId()));
	//
	// stockCandidateService
	// .applyDeltaToMatchingLaterStockCandidates(childStockWithDemandDelta);
	//
	// final Candidate demandCandidateToReturn;
	//
	// if (childStockWithDemand.getSeqNo() != expectedStockSeqNo)
	// {
	// // there was already a stock candidate which already had a seqNo.
	// // keep it and in turn update the demandCandidate's seqNo accordingly
	// demandCandidateToReturn = candidate
	// .withSeqNo(childStockWithDemand.getSeqNo() - 1);
	// candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(demandCandidateToReturn);
	// }
	// else
	// {
	// demandCandidateToReturn = candidateSaveResult.toCandidateWithQtyDelta();
	// }
	//
	// if (savedCandidate.getType() == CandidateType.DEMAND)
	// {
	// fireSupplyRequiredEventIfQtyBelowZero(demandCandidateToReturn);
	// }
	// return demandCandidateToReturn;
	// }

	private void assertCorrectCandidateType(@NonNull final Candidate demandCandidate)
	{
		final CandidateType type = demandCandidate.getType();

		Preconditions.checkArgument(
				getHandeledTypes().contains(demandCandidate.getType()),
				"Given parameter 'demandCandidate' has type=%s; demandCandidate=%s",
				type, demandCandidate);
	}

	private void fireSupplyRequiredEventIfQtyBelowZero(@NonNull final Candidate demandCandidateWithId)
	{
		final AvailableToPromiseMultiQuery query = AvailableToPromiseMultiQuery
				.forDescriptorAndAllPossibleBPartnerIds(demandCandidateWithId.getMaterialDescriptor());

		final BigDecimal availableQuantityAfterDemandWasApplied = availableToPromiseRepository.retrieveAvailableStockQtySum(query);

		if (availableQuantityAfterDemandWasApplied.signum() < 0)
		{
			final BigDecimal requiredQty = availableQuantityAfterDemandWasApplied.negate();

			final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator //
					.createSupplyRequiredEvent(demandCandidateWithId, requiredQty);
			materialEventService.postEventAfterNextCommit(supplyRequiredEvent);
		}
	}
}
