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
import de.metas.material.dispo.commons.repository.CandidateSaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseMultiQuery;
import de.metas.material.dispo.commons.repository.atp.AvailableToPromiseRepository;
import de.metas.material.dispo.service.candidatechange.StockCandidateService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.event.MaterialPlanningContextHelper;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

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
@RequiredArgsConstructor
public class DemandCandidateHandler implements CandidateHandler
{
	@NonNull private final CandidateRepositoryRetrieval candidateRepository;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;
	@NonNull private final PostMaterialEventService materialEventService;
	@NonNull private final AvailableToPromiseRepository availableToPromiseRepository;
	@NonNull private final StockCandidateService stockCandidateService;
	@NonNull private final SupplyCandidateHandler supplyCandidateHandler;
	@NonNull private final MaterialPlanningContextHelper helper;
	@NonNull private final PPOrderCandidateDemandMatcher ppOrderCandidateDemandMatcher;

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
	public CandidateSaveResult onCandidateNewOrChange(
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

		CandidateSaveResult candidateSaveResult = candidateRepositoryWriteService.addOrUpdateOverwriteStoredSeqNo(candidate);
		if (!candidateSaveResult.isDateChanged() && !candidateSaveResult.isQtyChanged())
		{
			// nothing to do
			return candidateSaveResult;
		}

		final Candidate savedCandidate = candidateSaveResult.getCandidate();

		final Optional<Candidate> preExistingChildStockCandidate = candidateRepository.retrieveSingleChild(savedCandidate.getId());
		final CandidateId preExistingChildStockId = preExistingChildStockCandidate.map(Candidate::getId).orElse(null);

		final CandidateSaveResult stockCandidate = stockCandidateService
				.createStockCandidate(savedCandidate.withNegatedQuantity())
				.withCandidateId(preExistingChildStockId);

		final CandidateSaveResult savedStockCandidate;
		if (preExistingChildStockCandidate.isPresent())
		{
			savedStockCandidate = candidateRepositoryWriteService
					.addOrUpdateOverwriteStoredSeqNo(stockCandidate.getCandidate().withParentId(savedCandidate.getId()));
		}
		else
		{
			savedStockCandidate = candidateRepositoryWriteService
					.add(stockCandidate.getCandidate().withParentId(savedCandidate.getId()));
		}

		candidateRepositoryWriteService.getCurrentAtpAndUpdateQtyDetails(savedCandidate, savedStockCandidate.getCandidate(), null);

		stockCandidateService.applyDeltaToMatchingLaterStockCandidates(savedStockCandidate);

		candidateSaveResult = candidateSaveResult.withParentId(savedStockCandidate.getId());
		// Evaluated on every DEMAND change now (not only on create). Accepted trade-off: it is one material-planning
		// context lookup that short-circuits for non-lot-for-lot products; the extra bound-supply DB query is done
		// only inside the lot-for-lot branch below.
		final boolean isLotForLotDemand = savedCandidate.getType() == CandidateType.DEMAND && isUseLotForLotQty(savedCandidate);

		if (savedCandidate.getType() == CandidateType.DEMAND && candidateSaveResult.getQtyDelta().signum() > 0)
		{
			fireSupplyRequiredEventIfNeeded(candidateSaveResult.getCandidate(), savedStockCandidate.getCandidate(), isLotForLotDemand, candidateSaveResult.isNew());
		}

		// Demand decreased: reduce the bound supply. SupplyRequiredDecreasedHandler reduces what it can (un-processed
		// candidates) and, for whatever it cannot reduce (an already-processed production that cannot be un-produced),
		// fires a user notification so a planner can rebalance another candidate. This holds for lot-for-lot too.
		if (candidateSaveResult.getQtyDelta().signum() < 0)
		{
			fireSupplyRequiredDecreasedEventIfNeeded(savedCandidate, candidateSaveResult.getQtyDelta().negate());
		}

		return candidateSaveResult;
	}

	private boolean isUseLotForLotQty(@NonNull final Candidate savedCandidate)
	{
		// Lot-for-lot applies to any demand change, not only to the demand's first creation: a later
		// reactivate / qty-change is still lot-for-lot and must size supply to THIS order's own qty — not
		// fall back to global-ATP netting (which absorbs other orders' still-open deficits).
		// This assumes that there is only one match on the material planning context. (de.metas.material.planning.event.SupplyRequiredHandler.handleSupplyRequiredEvent)
		// So other parts shouldn't be affected by this.
		final MaterialPlanningContext materialPlanningContext = helper.createContextOrNull(MaterialPlanningContextHelper.MaterialPlanningContextRequest.builder()
				.orgId(savedCandidate.getClientAndOrgId().getOrgId())
				.warehouseId(savedCandidate.getWarehouseId())
				.productId(savedCandidate.getProductId())
				.attributeSetInstanceId(savedCandidate.getAttributeSetInstanceId())
				.build());

		if (materialPlanningContext == null)
		{
			return false;
		}

		return materialPlanningContext.isManufacturedLot4Lot() && ppOrderCandidateDemandMatcher.matches(materialPlanningContext);
	}

	private void fireSupplyRequiredDecreasedEventIfNeeded(final Candidate savedCandidate, final BigDecimal decreasedQty)
	{
		materialEventService.enqueueEventAfterNextCommit(SupplyRequiredEventCreator.createSupplyRequiredDecreasedEvent(savedCandidate, decreasedQty));
	}

	@Override
	public void onCandidateDelete(@NonNull final Candidate candidate)
	{
		assertCorrectCandidateType(candidate);

		final Function<CandidateId, CandidateRepositoryWriteService.DeleteResult> deleteCandidateFunc = CandidateHandlerUtil.getDeleteFunction(candidate.getBusinessCase(), candidateRepositoryWriteService);

		final CandidateRepositoryWriteService.DeleteResult stockDeleteResult = deleteCandidateFunc.apply(candidate.getId());

		final DateAndSeqNo timeOfDeletedStock = stockDeleteResult.getPreviousTime();

		final BigDecimal previousQty = candidate.getQuantity().negate();

		final CandidateSaveResult applyDeltaRequest = CandidateSaveResult.builder()
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

	private void fireSupplyRequiredEventIfNeeded(
			@NonNull final Candidate demandCandidate,
			@NonNull final Candidate stockCandidate,
			final boolean isUseLotForLotQty,
			final boolean isNewDemand)
	{
		if (demandCandidate.isSimulated())
		{
			fireSimulatedSupplyRequiredEvent(demandCandidate, stockCandidate);
		}
		else if (isUseLotForLotQty)
		{
			if (isNewDemand)
			{
				// first time this lot-for-lot demand is seen: create its bound production supply for the full demand qty.
				postSupplyRequiredEvent(demandCandidate, demandCandidate.getQuantity());
			}
			else
			{
				// a lot-for-lot demand CHANGE (reactivate / qty change). Reconcile the demand's OWN already-bound
				// production supply (matched by DemandDetail = order / order line / shipment schedule) to the new
				// demand qty — NOT the global product ATP (which would absorb other orders' still-open deficits).
				// CRITICAL: fire ONLY when the bound supply does not already equal the demand qty. A spurious re-fire
				// of an already-covered lot-for-lot demand re-runs the advisor and (with IsCreatePlan) spawns a
				// duplicate PP_Order / supply candidate.
				final BigDecimal boundSupplyQty = candidateRepositoryWriteService
						.getSupplyCandidatesForDemand(demandCandidate, de.metas.material.dispo.commons.candidate.CandidateBusinessCase.PRODUCTION)
						.stream()
						.map(Candidate::getQuantity)
						.reduce(ZERO, BigDecimal::add);

				if (boundSupplyQty.compareTo(demandCandidate.getQuantity()) != 0)
				{
					postLotForLotSupplyRequiredEventReusingBoundSupply(demandCandidate, demandCandidate.getQuantity());
				}
				else
				{
					Loggables.addLog("Lot-for-lot demand change already covered by bound supply (qty={}); firing nothing", boundSupplyQty);
				}
			}
		}
		else
		{
			fireSupplyRequiredEventIfQtyBelowZero(demandCandidate);
		}
	}

	/**
	 * Lot-for-lot demand change: fire a {@link SupplyRequiredEvent} with {@code supplyCandidateId=null} so that
	 * {@code PPOrderCandidateAdvisedHandler} matches the demand's EXISTING bound production supply by its
	 * {@code DemandDetail} (order / order line / shipment schedule) — group-agnostically — and updates that single
	 * candidate to {@code requiredQty} (grow, shrink or no-op) rather than minting a duplicate. Keeps lot-for-lot to
	 * exactly one production candidate sized to this order's own qty, never absorbing an earlier order's open
	 * deficit.
	 */
	private void postLotForLotSupplyRequiredEventReusingBoundSupply(
			@NonNull final Candidate demandCandidateWithId,
			@NonNull final BigDecimal requiredQty)
	{
		final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator
				.createSupplyRequiredEvent(demandCandidateWithId, requiredQty, null);

		materialEventService.enqueueEventAfterNextCommit(supplyRequiredEvent);
		Loggables.addLog("Fire lot-for-lot supplyRequiredEvent (reuse bound supply) after next commit; event={}", supplyRequiredEvent);
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

		final CandidateId supplyCandidateId = supplyCandidateHandler.onCandidateNewOrChange(supplyCandidate, OnNewOrChangeAdvise.DONT_UPDATE).getId();

		final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator
				.createSupplyRequiredEvent(demandCandidateWithId, requiredQty, supplyCandidateId);

		materialEventService.enqueueEventAfterNextCommit(supplyRequiredEvent);
		Loggables.addLog("Fire supplyRequiredEvent after next commit; event={}", supplyRequiredEvent);
	}
}
