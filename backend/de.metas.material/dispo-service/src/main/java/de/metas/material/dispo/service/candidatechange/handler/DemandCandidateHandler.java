package de.metas.material.dispo.service.candidatechange.handler;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.inout.ShipmentScheduleId;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
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
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.event.MaterialPlanningContextHelper;
import de.metas.material.planning.pporder.PPOrderCandidateDemandMatcher;
import de.metas.material.planning.pporder.PPOrderCandidateRepository;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
	@NonNull private final PPOrderCandidateRepository ppOrderCandidateRepository;

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

		if (savedCandidate.getType() == CandidateType.DEMAND && candidateSaveResult.getQtyDelta().signum() > 0)
		{
			// Lot-for-lot is (re)evaluated on every demand increase, not only on create: a later reactivate /
			// qty-change is still lot-for-lot and must size supply to THIS order's own qty. One material-planning
			// context lookup that short-circuits (returns null) for non-lot-for-lot products.
			final MaterialPlanningContext lotForLotContext = getLotForLotContextOrNull(savedCandidate);
			fireSupplyRequiredEventIfNeeded(candidateSaveResult.getCandidate(), savedStockCandidate.getCandidate(), lotForLotContext);
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

	/**
	 * The lot-for-lot material-planning context for this demand, or {@code null} if the product is not lot-for-lot.
	 * Returns the context (not a boolean) so the caller can reuse its product-planning id for the committed-qty
	 * netting without a second lookup; assumes a single material-planning match.
	 */
	@Nullable
	private MaterialPlanningContext getLotForLotContextOrNull(@NonNull final Candidate savedCandidate)
	{
		final MaterialPlanningContext materialPlanningContext = helper.createContextOrNull(MaterialPlanningContextHelper.MaterialPlanningContextRequest.builder()
				.orgId(savedCandidate.getClientAndOrgId().getOrgId())
				.warehouseId(savedCandidate.getWarehouseId())
				.productId(savedCandidate.getProductId())
				.attributeSetInstanceId(savedCandidate.getAttributeSetInstanceId())
				.build());

		if (materialPlanningContext == null)
		{
			return null;
		}

		final boolean isLotForLot = materialPlanningContext.isManufacturedLot4Lot()
				&& ppOrderCandidateDemandMatcher.matches(materialPlanningContext);
		return isLotForLot ? materialPlanningContext : null;
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
			@Nullable final MaterialPlanningContext lotForLotContext)
	{
		if (demandCandidate.isSimulated())
		{
			fireSimulatedSupplyRequiredEvent(demandCandidate, stockCandidate);
		}
		else if (lotForLotContext != null)
		{
			// Lot-for-lot sizes supply to THIS order's OWN demand qty (order / shipment schedule), not the global
			// product ATP (which would absorb other orders' still-open deficits). Fire the NET shortfall = own demand
			// minus what is already committed to real production for this schedule — mirroring how ATP fires a gap
			// already net of existing supply. This net qty (computed here, at the fire point) is the ONLY
			// lot-for-lot-specific thing; the advisor then treats ATP and lot-for-lot identically (grow the open
			// candidate / create new / capacity split).
			final BigDecimal committedProductionQty = getCommittedProductionQtyForSchedule(demandCandidate, lotForLotContext);
			final BigDecimal shortfall = demandCandidate.getQuantity().subtract(committedProductionQty);
			if (shortfall.signum() > 0)
			{
				postLotForLotSupplyRequiredEvent(demandCandidate, shortfall);
			}
		}
		else
		{
			fireSupplyRequiredEventIfQtyBelowZero(demandCandidate);
		}
	}

	/**
	 * Lot-for-lot supply event: fired with the demand's OWN qty and {@code supplyCandidateId=null}, exactly like the
	 * ATP path fires the global-ATP gap. The advisor ({@code PPOrderCandidateAdvisedEventCreator}) then handles it
	 * identically for both — updating the pre-existing supply candidate (if any) or creating one. The sole
	 * lot-for-lot difference is the qty: this order's own demand rather than the global ATP gap.
	 */
	private void postLotForLotSupplyRequiredEvent(
			@NonNull final Candidate demandCandidateWithId,
			@NonNull final BigDecimal requiredQty)
	{
		final SupplyRequiredEvent supplyRequiredEvent = SupplyRequiredEventCreator
				.createSupplyRequiredEvent(demandCandidateWithId, requiredQty, null);

		materialEventService.enqueueEventAfterNextCommit(supplyRequiredEvent);
		Loggables.addLog("Fire lot-for-lot supplyRequiredEvent after next commit; event={}", supplyRequiredEvent);
	}

	/**
	 * Qty already committed to real production for this demand's shipment schedule — {@code QtyEntered} of the
	 * immovable ({@code Processed}/{@code IsClosed}) candidates, which persists through realization (unlike the MD
	 * supply qty, which drains to 0 on receipt), so a produced/closed order is never re-produced. Open candidates are
	 * excluded — those are what the advisor grows to the shortfall, so counting them would double-net.
	 */
	@NonNull
	private BigDecimal getCommittedProductionQtyForSchedule(
			@NonNull final Candidate demandCandidate,
			@NonNull final MaterialPlanningContext lotForLotContext)
	{
		final DemandDetail demandDetail = demandCandidate.getDemandDetail();
		if (demandDetail == null || demandDetail.getShipmentScheduleId() <= 0)
		{
			return ZERO;
		}

		final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(demandDetail.getShipmentScheduleId());
		final ProductPlanningId productPlanningId = lotForLotContext.getProductPlanning().getIdNotNull();

		// The repo keeps the sum UOM-aware (Quantity); convert to BigDecimal here — the latest point, since the
		// demand qty and the SupplyRequiredEvent are UOM-less BigDecimal.
		return ppOrderCandidateRepository
				.retrieveProcessedQtyByShipmentScheduleAndPlanning(shipmentScheduleId, productPlanningId)
				.map(quantity -> quantity.toBigDecimal())
				.orElse(ZERO);
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
