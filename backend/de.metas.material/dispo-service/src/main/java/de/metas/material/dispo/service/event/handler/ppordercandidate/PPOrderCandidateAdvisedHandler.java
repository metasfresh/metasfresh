/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.dispo.service.event.handler.ppordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.candidatechange.handler.DemandCandiateHandler;
import de.metas.material.dispo.service.candidatechange.handler.SupplyCandidateHandler;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ProductPlanningService;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public final class PPOrderCandidateAdvisedHandler extends PPOrderCandidateEventHandler
		implements MaterialEventHandler<PPOrderCandidateAdvisedEvent>
{
	private final PostMaterialEventService materialEventService;
	private final ProductPlanningService productPlanningService;

	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final SupplyCandidateHandler supplyCandidateHandler;

	public PPOrderCandidateAdvisedHandler(
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final ProductPlanningService productPlanningService,
			@NonNull final SupplyCandidateHandler supplyCandidateHandler)
	{
		super(candidateChangeService, candidateRepositoryRetrieval);

		this.materialEventService = materialEventService;
		this.productPlanningService = productPlanningService;
		this.supplyCandidateHandler = supplyCandidateHandler;
	}

	@Override
	public Collection<Class<? extends PPOrderCandidateAdvisedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderCandidateAdvisedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PPOrderCandidateAdvisedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCandidateAdvisedEvent event)
	{
		validateEvent(event);

		// check if SupplyCandidate didn't get created in DemandCandidateHandler#postSupplyRequiredEvent() if required Qty was 0
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final PPOrderCandidateAdvisedEvent updatedEvent;
		if(supplyRequiredDescriptor != null && supplyRequiredDescriptor.getSupplyCandidateId() <= 0)
		{
			final Candidate supplyCandidate = Candidate.builderForClientAndOrgId(event.getEventDescriptor().getClientAndOrgId())
					.type(CandidateType.SUPPLY)
					.businessCase(null)
					.businessCaseDetail(null)
					.materialDescriptor(supplyRequiredDescriptor.getMaterialDescriptor())
					//.groupId() // don't assign the new supply candidate to the demand candidate's groupId! it needs to "found" its own group
					.minMaxDescriptor(supplyRequiredDescriptor.getMinMaxDescriptor())
					.quantity(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity())
					.simulated(supplyRequiredDescriptor.isSimulated())
					.lotForLot(supplyRequiredDescriptor.getIsLotForLot())
					.build();

			final Candidate supplyCandidateWithId = supplyCandidateHandler.onCandidateNewOrChange(supplyCandidate, CandidateHandler.OnNewOrChangeAdvise.DONT_UPDATE);
			final int supplyCandidateId = supplyCandidateWithId.getId().getRepoId();
			final SupplyRequiredDescriptor updatedSupplyRequiredDescriptor = supplyRequiredDescriptor.toBuilder().supplyCandidateId(supplyCandidateId).build();
			updatedEvent = event.toBuilder().supplyRequiredDescriptor(updatedSupplyRequiredDescriptor).build();
		}
		else
		{
			updatedEvent = event;
		}

		final PPOrderCandidateAdvisedEvent eventWithRecomputedQty = getEventWithRecomputedQtyAndDate(updatedEvent);

		final MaterialDispoGroupId groupId = handlePPOrderCandidateAdvisedEvent(eventWithRecomputedQty);

		if (eventWithRecomputedQty.getPpOrderCandidate().getPpOrderData().getQtyRequired().signum() == 0)
		{
			return;
		}

		final PPOrderCandidate ppOrderCandidateWithGroupId = eventWithRecomputedQty
				.getPpOrderCandidate()
				.toBuilder()
				.ppOrderData(eventWithRecomputedQty.getPpOrderCandidate()
									 .getPpOrderData()
									 .toBuilder()
									 .materialDispoGroupId(groupId)
									 .build())
				.build();

		final PPOrderCandidateRequestedEvent ppOrderRequestEvent = PPOrderCandidateRequestedEvent
				.builder()
				.eventDescriptor(EventDescriptor.ofEventDescriptor(eventWithRecomputedQty.getEventDescriptor()))
				.ppOrderCandidate(ppOrderCandidateWithGroupId)
				.supplyRequiredDescriptor(eventWithRecomputedQty.getSupplyRequiredDescriptor())
				.directlyCreatePPOrder(eventWithRecomputedQty.isDirectlyCreatePPOrder())
				.build();

		materialEventService.enqueueEventNow(ppOrderRequestEvent);
	}

	private MaterialDispoGroupId handlePPOrderCandidateAdvisedEvent(@NonNull final PPOrderCandidateAdvisedEvent ppOrderCandidateAdvisedEvent)
	{
		final Candidate headerCandidate = createHeaderCandidate(ppOrderCandidateAdvisedEvent);

		return headerCandidate.getGroupId();
	}

	@NonNull
	private Candidate createHeaderCandidate(@NonNull final PPOrderCandidateAdvisedEvent event)
	{
		final CandidatesQuery preExistingSupplyQuery = createPreExistingSupplyCandidateQuery(event);

		return createHeaderCandidate(event, preExistingSupplyQuery);
	}

	@NonNull
	private CandidatesQuery createPreExistingSupplyCandidateQuery(@NonNull final PPOrderCandidateAdvisedEvent ppOrderCandidateAdvisedEvent)
	{
		if (!ppOrderCandidateAdvisedEvent.isTryUpdateExistingCandidate())
		{
			return CandidatesQuery.FALSE;
		}

		final SupplyRequiredDescriptor supplyRequiredDescriptor = ppOrderCandidateAdvisedEvent.getSupplyRequiredDescriptor();
		final CandidateId supplyCandidateId = CandidateId.ofRepoIdOrNull(supplyRequiredDescriptor.getSupplyCandidateId());

		if (supplyCandidateId != null)
		{ // the original request already contained an existing supply-candidate's ID that we need to update now.
			return CandidatesQuery.fromId(supplyCandidateId);
		}

		final PPOrderCandidate ppOrderCandidate = ppOrderCandidateAdvisedEvent.getPpOrderCandidate();

		final DemandDetail demandDetail = DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofDemandDetail(demandDetail);

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.productPlanningId(ppOrderCandidate.getPpOrderData().getProductPlanningId())
				.build();

		return CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.demandDetailsQuery(demandDetailsQuery)
				.productionDetailsQuery(productionDetailsQuery)
				.build();
	}

	@NonNull
	private PPOrderCandidateAdvisedEvent getEventWithRecomputedQtyAndDate(@NonNull final PPOrderCandidateAdvisedEvent event)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();

		if (supplyRequiredDescriptor == null)
		{
			return event;
		}

		final PPOrderData ppOrderData = event.getPpOrderCandidate().getPpOrderData();

		if (willProductionBeReadyInTimeForDemand(supplyRequiredDescriptor, ppOrderData))
		{
			return event;
		}

		final List<Candidate> stockCandidatesBetweenDemandAndPossibleSupply = getStockCandidatesBetweenDemandAndSupply(supplyRequiredDescriptor, ppOrderData);
		if (stockCandidatesBetweenDemandAndPossibleSupply.isEmpty())
		{
			return event;
		}

		final Function<BigDecimal,Instant> recalculateProductionDatePromisedFormula = (qtyRequired) -> getNewDatePromisedForQty(qtyRequired, event);

		ProductionTimingResult bestProductionTiming = ProductionTimingResult.builder()
				.datePromised(ppOrderData.getDatePromised())
				.qtyRequired(ppOrderData.getQtyRequired())
				.missingQtySolvedTime(ppOrderData.getDatePromised())
				.build();

		for (final Candidate futureStockCandidate : stockCandidatesBetweenDemandAndPossibleSupply)
		{
			final Optional<ProductionTimingResult> ppOrderDataWithBetterTiming = getBetterTimingIfAvailable(recalculateProductionDatePromisedFormula,
																											futureStockCandidate,
																											supplyRequiredDescriptor,
																											bestProductionTiming.getMissingQtySolvedTime());

			if (ppOrderDataWithBetterTiming.isPresent())
			{
				bestProductionTiming = ppOrderDataWithBetterTiming.get();
			}
		}

		final PPOrderData ppOrderDataWithBestTiming = ppOrderData.toBuilder()
				.qtyRequired(bestProductionTiming.getQtyRequired())
				.datePromised(bestProductionTiming.getDatePromised())
				.build();

		return buildWithPPOrderData(event, ppOrderDataWithBestTiming);
	}

	@NonNull
	private Optional<ProductionTimingResult> getBetterTimingIfAvailable(
			@NonNull final Function<BigDecimal,Instant> recalculateProductionDateFormula,
			@NonNull final Candidate stockCandidate,
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final Instant currentMissingQtySolvingTime)
	{
		final BigDecimal demandQty = supplyRequiredDescriptor.isSimulated()
				? supplyRequiredDescriptor.getFullDemandQty()
				: supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();

		final BigDecimal qtyRequiredWithFutureStock = getQtyRequiredConsideringFutureStock(stockCandidate, demandQty);

		final boolean thereIsEnoughStockWithoutAnyProductionSupply = qtyRequiredWithFutureStock.signum() <= 0;

		if (thereIsEnoughStockWithoutAnyProductionSupply)
		{
			final ProductionTimingResult result = ProductionTimingResult.builder()
					.datePromised(SystemTime.asInstant())
					.qtyRequired(BigDecimal.ZERO)
					.missingQtySolvedTime(stockCandidate.getDate())
					.build();

			return Optional.of(result)
					.filter(productionTimingResult -> productionTimingResult.getMissingQtySolvedTime().isBefore(currentMissingQtySolvingTime));
		}

		final boolean thereIsEvenLessStockAvailable = qtyRequiredWithFutureStock.compareTo(demandQty) >= 0;
		if (thereIsEvenLessStockAvailable)
		{
			return Optional.empty();
		}

		final Instant newProductionDatePromised = recalculateProductionDateFormula.apply(qtyRequiredWithFutureStock);

		final Instant missingQtySolvedDate = newProductionDatePromised.isAfter(stockCandidate.getDate())
				? newProductionDatePromised
				: stockCandidate.getDate();
		
		if (missingQtySolvedDate.isAfter(currentMissingQtySolvingTime))
		{
			return Optional.empty();
		}
		
		final BigDecimal qtyRequiredToBeInStock = demandQty.subtract(qtyRequiredWithFutureStock);
		
		if (!validateThereWillBeEnoughStockAtGivenDate(supplyRequiredDescriptor, qtyRequiredToBeInStock, missingQtySolvedDate))
		{
			return Optional.empty();
		}

		final ProductionTimingResult result = ProductionTimingResult.builder()
				.datePromised(newProductionDatePromised)
				.qtyRequired(qtyRequiredWithFutureStock)
				.missingQtySolvedTime(missingQtySolvedDate)
				.build();

		return Optional.of(result);
	}

	@NonNull
	private List<Candidate> getStockCandidatesBetweenDemandAndSupply(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final PPOrderData ppOrderData)
	{
		final Instant demandDate = supplyRequiredDescriptor.getMaterialDescriptor().getDate();
		final Instant productionEarliestSupplyDate = ppOrderData.getDatePromised();

		final boolean isDemandDateInThePast = Instant.now().isAfter(demandDate);
		final Instant rangeStartTime = isDemandDateInThePast ? Instant.now() : demandDate;

		final ImmutableList.Builder<Candidate> stockCandidatesCollector = ImmutableList.builder();

		if (isDemandDateInThePast)
		{
			getLatestStockAtGivenTime(supplyRequiredDescriptor, rangeStartTime, DateAndSeqNo.Operator.EXCLUSIVE)
					.ifPresent(stockCandidatesCollector::add);
		}

		retrieveStockCandidatesBetweenDates(supplyRequiredDescriptor, rangeStartTime, productionEarliestSupplyDate)
				.forEach(stockCandidatesCollector::add);

		final Candidate unspecifiedSupplyCandidate = getUnspecifiedSupplyCandidate(supplyRequiredDescriptor);

		return stockCandidatesCollector.build()
				.stream()
				.filter(stockCandidate -> !isStockForSupplyCandidate(stockCandidate, unspecifiedSupplyCandidate))
				.collect(ImmutableList.toImmutableList());
	}

	private boolean willProductionBeReadyInTimeForDemand(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final PPOrderData ppOrderData)
	{
		return ppOrderData.getDatePromised()
				.compareTo(supplyRequiredDescriptor.getMaterialDescriptor().getDate()) <= 0;
	}

	@NonNull
	private Optional<Candidate> getLatestStockAtGivenTime(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final Instant givenTime,
			@NonNull final DateAndSeqNo.Operator operator)
	{
		final DateAndSeqNo rangeEndDate = DateAndSeqNo.atTimeNoSeqNo(givenTime).withOperator(operator);

		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.forDescriptor(
				supplyRequiredDescriptor.getMaterialDescriptor(),
				MaterialDescriptorQuery.CustomerIdOperator.GIVEN_ID_OR_NULL,
				null,
				rangeEndDate);

		final CandidatesQuery stockCandidatesQuery = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.build();

		final Candidate latestStock = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(stockCandidatesQuery);

		return Optional.ofNullable(latestStock);
	}

	@NonNull
	private List<Candidate> retrieveStockCandidatesBetweenDates(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final Instant rangeStartTime,
			@NonNull final Instant rangeEndTime)
	{
		final DateAndSeqNo rangeStartDate = DateAndSeqNo.atTimeNoSeqNo(rangeStartTime).withOperator(DateAndSeqNo.Operator.INCLUSIVE);
		final DateAndSeqNo rangeEndDate = DateAndSeqNo.atTimeNoSeqNo(rangeEndTime).withOperator(DateAndSeqNo.Operator.EXCLUSIVE);

		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.forDescriptor(supplyRequiredDescriptor.getMaterialDescriptor(),
																									  MaterialDescriptorQuery.CustomerIdOperator.GIVEN_ID_OR_NULL,
																									  rangeStartDate,
																									  rangeEndDate);

		final CandidatesQuery stockCandidatesQuery = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidateId.UNSPECIFIED)
				.build();

		return candidateRepositoryRetrieval
				.retrieveOrderedByDateAndSeqNo(stockCandidatesQuery);
	}

	@NonNull
	private BigDecimal getQtyRequiredConsideringFutureStock(
			@NonNull final Candidate stockCandidate,
			@NonNull final BigDecimal initialQtyRequired)
	{
		return initialQtyRequired.subtract(stockCandidate.getQuantity());
	}

	@NonNull
	private Instant getNewDatePromisedForQty(
			@NonNull final BigDecimal recomputedQtyBasedOnDemand,
			@NonNull final PPOrderCandidateAdvisedEvent ppOrderCandidateAdvisedEvent)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoIdOrNull(ppOrderCandidateAdvisedEvent.getPpOrderCandidate().getPpOrderData().getProductPlanningId());

		Check.assumeNotNull(productPlanningId, "There should be a ProductPlanningId on event at this stage!");

		final ProductPlanning productPlanningRecord = productPlanningDAO.getById(productPlanningId);

		final int durationDays = productPlanningService.calculateDurationDays(productPlanningRecord, recomputedQtyBasedOnDemand);

		return ppOrderCandidateAdvisedEvent.getPpOrderCandidate().getPpOrderData().getDateStartSchedule().plus(durationDays, ChronoUnit.DAYS);
	}
	
	private boolean validateThereWillBeEnoughStockAtGivenDate(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final BigDecimal requiredQtyInStock,
			@NonNull final Instant givenTime)
	{
		final Optional<Candidate> latestStockAtGivenTime = getLatestStockAtGivenTime(supplyRequiredDescriptor, givenTime, DateAndSeqNo.Operator.INCLUSIVE);
		
		return latestStockAtGivenTime
				.map(Candidate::getQuantity)
				.map(availableStock -> availableStock.compareTo(requiredQtyInStock) >= 0)
				.orElse(false);
	}

	private boolean isStockForSupplyCandidate(@NonNull final Candidate stock, @NonNull final Candidate supplyCandidate)
	{
		Check.assume(CandidateId.isRegularNonNull(supplyCandidate.getParentId()), "Supply Candidates have the stock candidate as parent!");

		return stock.getId().equals(supplyCandidate.getParentId());
	}

	@NonNull
	private Candidate getUnspecifiedSupplyCandidate(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final CandidateId supplyCandidateId = CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId());

		return candidateRepositoryRetrieval.retrieveLatestMatch(CandidatesQuery.fromId(supplyCandidateId))
				.orElseThrow(() -> new AdempiereException("Missing Candidate for Id:" + supplyRequiredDescriptor.getSupplyCandidateId()));
	}

	@NonNull
	private static PPOrderCandidateAdvisedEvent buildWithPPOrderData(@NonNull final PPOrderCandidateAdvisedEvent event, @NonNull final PPOrderData ppOrderData)
	{
		return event.toBuilder()
				.ppOrderCandidate(event.getPpOrderCandidate().withPpOrderData(ppOrderData))
				.build();
	}

	@Value
	@Builder
	private static class ProductionTimingResult
	{
		@NonNull
		Instant datePromised;

		@NonNull
		BigDecimal qtyRequired;

		@NonNull
		Instant missingQtySolvedTime;
	}
}