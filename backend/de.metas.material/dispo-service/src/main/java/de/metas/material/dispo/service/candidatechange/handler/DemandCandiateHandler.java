package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.DeleteResult;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;

import static java.math.BigDecimal.ZERO;

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
public class DemandCandiateHandler implements CandidateHandler
{
	private final CandidateRepositoryRetrieval candidateRepository;
	private final AvailableToPromiseRepository availableToPromiseRepository;
	private final PostMaterialEventService materialEventService;
	private final StockCandidateService stockCandidateService;
	private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	private final SupplyCandidateHandler supplyCandidateHandler;

	public DemandCandiateHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryWriteService,
			@NonNull final PostMaterialEventService postMaterialEventService,
			@NonNull final AvailableToPromiseRepository availableToPromiseRepository,
			@NonNull final StockCandidateService stockCandidateService,
			@NonNull final SupplyCandidateHandler supplyCandidateHandler)
	{
		this.candidateRepository = candidateRepositoryRetrieval;
		this.candidateRepositoryWriteService = candidateRepositoryWriteService;
		this.materialEventService = postMaterialEventService;
		this.availableToPromiseRepository = availableToPromiseRepository;
		this.stockCandidateService = stockCandidateService;
		this.supplyCandidateHandler = supplyCandidateHandler;
	}

	@Override
	public Collection<CandidateType> getHandeledTypes()
	{
		return ImmutableList.of(
				CandidateType.DEMAND,
				CandidateType.UNEXPECTED_DECREASE,
				CandidateType.INVENTORY_DOWN,
				CandidateType.ATTRIBUTES_CHANGED_FROM);
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also its <b>child</b> stock candidate.
	 */
	@Override
	public Candidate onCandidateNewOrChange(
			@NonNull final Candidate candidate,
			@NonNull final OnNewOrChangeAdvise advise)
	{
		if (!advise.isAttemptUpdate())
		{
			throw new AdempiereException("This handler does not how to deal with isAttemptUpdate=false").appendParametersToMessage()
					.setParameter("handler", candidate)
					.setParameter("candidate", candidate);
		}

		assertCorrectCandidateType(candidate);

		final SaveResult candidateSaveResult = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate);

		if (!candidateSaveResult.isDateChanged() && !candidateSaveResult.isQtyChanged())
		{
			return candidateSaveResult.toCandidateWithQtyDelta(); // nothing to do
		}

		final Candidate savedCandidate = candidateSaveResult.getCandidate();

		final Optional<Candidate> preExistingChildStockCandidate = candidateRepository.retrieveSingleChild(savedCandidate.getId());
		final CandidateId preExistingChildStockId = preExistingChildStockCandidate.map(Candidate::getId).orElse(null);

		final SaveResult stockCandidate = stockCandidateService
				.createStockCandidate(savedCandidate.withNegatedQuantity())
				.withCandidateId(preExistingChildStockId);

		final Candidate savedStockCandidate;
		if (preExistingChildStockCandidate.isPresent())
		{
			savedStockCandidate = candidateRepositoryWriteService
					.addOrUpdateOverwriteStoredSeqNo(stockCandidate.getCandidate().withParentId(savedCandidate.getId()))
					.getCandidate();
		}
		else
		{
			savedStockCandidate = candidateRepositoryWriteService
					.add(stockCandidate.getCandidate().withParentId(savedCandidate.getId()))
					.getCandidate();
		}

		final SaveResult deltaToApplyToLaterStockCandiates = candidateSaveResult.withNegatedQuantity();

		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(deltaToApplyToLaterStockCandiates);

		final Candidate candidateToReturn = candidateSaveResult
				.toCandidateWithQtyDelta()
				.withParentId(savedStockCandidate.getId());

		if (savedCandidate.getType() == CandidateType.DEMAND)
		{
			fireSupplyRequiredEventIfNeeded(candidateSaveResult.getCandidate(), savedStockCandidate);
		}
		return candidateToReturn;
	}

	@Override
	public void onCandidateDelete(@NonNull final Candidate candidate)
	{
		assertCorrectCandidateType(candidate);

		candidateRepositoryWriteService.deleteCandidateById(candidate.getId());

		final Optional<Candidate> childStockCandidate = candidateRepository.retrieveSingleChild(candidate.getId());
		if (!childStockCandidate.isPresent())
		{
			return; // nothing to do
		}
		final DeleteResult stockDeleteResult = candidateRepositoryWriteService.deleteCandidateById(childStockCandidate.get().getId());

		final DateAndSeqNo timeOfDeletedStock = stockDeleteResult.getPreviousTime();

		final BigDecimal previousQty = candidate.getQuantity().negate();

		final SaveResult applyDeltaRequest = SaveResult.builder()
				.candidate(candidate
								   .withQuantity(ZERO)
								   .withDate(timeOfDeletedStock.getDate())
								   .withSeqNo(timeOfDeletedStock.getSeqNo()))
				.previousQty(previousQty)
				.build();
		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(applyDeltaRequest);
	}

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
		Loggables.addLog("Quantity after demand applied: {}", availableQuantityAfterDemandWasApplied);

		final BigDecimal requiredQty = computeRequiredQty(availableQuantityAfterDemandWasApplied, demandCandidateWithId.getMinMaxDescriptor());
		if (requiredQty.signum() > 0)
		{
			postSupplyRequiredEvent(demandCandidateWithId, requiredQty);
		}
	}

	@VisibleForTesting
	static BigDecimal computeRequiredQty(
			@NonNull final BigDecimal availableQuantityAfterDemandWasApplied,
			@NonNull final MinMaxDescriptor minMaxDescriptor)
	{
		if (availableQuantityAfterDemandWasApplied.compareTo(minMaxDescriptor.getMin()) >= 0)
		{
			return ZERO;
		}
		return minMaxDescriptor.getMax().subtract(availableQuantityAfterDemandWasApplied);
	}

	private void fireSupplyRequiredEventIfNeeded(@NonNull final Candidate demandCandidate, @NonNull final Candidate stockCandidate)
	{
		if (demandCandidate.isSimulated())
		{
			fireSimulatedSupplyRequiredEvent(demandCandidate, stockCandidate);
		}
		else
		{
			fireSupplyRequiredEventIfQtyBelowZero(demandCandidate);
		}
	}

	private void fireSimulatedSupplyRequiredEvent(@NonNull final Candidate simulatedCandidate, @NonNull final Candidate stockCandidate)
	{
		Check.assume(simulatedCandidate.isSimulated(), "fireSimulatedSupplyRequiredEvent should only be called for simulated candidates!");

		if (stockCandidate.getQuantity().signum() < 0)
		{
			postSupplyRequiredEvent(simulatedCandidate, stockCandidate.getQuantity().negate());
		}
	}

	private void postSupplyRequiredEvent(@NonNull final Candidate demandCandidateWithId, @NonNull final BigDecimal requiredQty)
	{
		// create supply record now! otherwise
		final Candidate supplyCandidate = Candidate.builderForClientAndOrgId(demandCandidateWithId.getClientAndOrgId())
				.type(CandidateType.SUPPLY)
				.businessCase(null)
				.businessCaseDetail(null)
				.materialDescriptor(demandCandidateWithId.getMaterialDescriptor().withQuantity(requiredQty))
				//.groupId() // don't assign the new supply candidate to the demand candidate's groupId! it needs to "found" its own group
				.minMaxDescriptor(demandCandidateWithId.getMinMaxDescriptor())
				.quantity(requiredQty)
				.simulated(demandCandidateWithId.isSimulated())
				.build();

		final Candidate supplyCandidateWithId = supplyCandidateHandler.onCandidateNewOrChange(supplyCandidate, OnNewOrChangeAdvise.DONT_UPDATE);

		final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator
				.createSupplyRequiredEvent(demandCandidateWithId, requiredQty, supplyCandidateWithId.getId());

		materialEventService.postEventAfterNextCommit(supplyRequiredEvent);

		Loggables.addLog("Fire supplyRequiredEvent after next commit; event={}", supplyRequiredEvent);
	}
}
