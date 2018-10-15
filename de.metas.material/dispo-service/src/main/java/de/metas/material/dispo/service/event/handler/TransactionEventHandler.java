package de.metas.material.dispo.service.event.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Util;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.google.common.collect.ImmutableSet;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.util.Check;
import de.metas.util.Loggables;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo
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
public class TransactionEventHandler implements MaterialEventHandler<AbstractTransactionEvent>
{
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepository;
	private final PostMaterialEventService postMaterialEventService;

	public TransactionEventHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
		this.postMaterialEventService = postMaterialEventService;
	}

	@Override
	public Collection<Class<? extends AbstractTransactionEvent>> getHandeledEventType()
	{
		return ImmutableList.of(TransactionCreatedEvent.class, TransactionDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates = createCandidatesForTransactionEvent(event);
		for (final Candidate candidate : candidates)
		{
			candidateChangeHandler.onCandidateNewOrChange(candidate);
		}
	}

	@VisibleForTesting
	List<Candidate> createCandidatesForTransactionEvent(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates = new ArrayList<>();

		if (event.getShipmentScheduleIds2Qtys() != null && !event.getShipmentScheduleIds2Qtys().isEmpty())
		{
			candidates.addAll(prepareCandidatesForShipmentScheduleIds(event));
		}
		else if (event.getReceiptScheduleIds2Qtys() != null && !event.getReceiptScheduleIds2Qtys().isEmpty())
		{
			candidates.addAll(prepareCandidateForReceiptScheduleIds(event));
		}
		else if (event.getPpOrderId() > 0)
		{
			final List<Candidate> candidateForPPorder = createCandidateForPPorder(event);
			firePickRequiredEventIfFeasible(candidateForPPorder.get(0), event);

			candidates.addAll(candidateForPPorder);
		}
		else if (event.getDdOrderLineId() > 0)
		{
			final List<Candidate> candidateForDDorder = createCandidateForDDorder(event);
			firePickRequiredEventIfFeasible(candidateForDDorder.get(0), event);

			candidates.addAll(candidateForDDorder);
		}
		else
		{
			candidates.addAll(prepareUnrelatedCandidate(event));
		}
		return candidates;
	}

	private void firePickRequiredEventIfFeasible(
			@NonNull final Candidate candidate,
			@NonNull final AbstractTransactionEvent transactionEvent)
	{
		if (transactionEvent instanceof TransactionDeletedEvent)
		{
			return;
		}

		final Flag pickDirectlyIfFeasible = extractPickDirectlyIfFeasible(candidate);
		if (!pickDirectlyIfFeasible.toBoolean())
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event's candidate has pickDirectlyIfFeasible={}; candidate={}",
					pickDirectlyIfFeasible, candidate);
			return;
		}

		final DemandDetail demandDetail = candidate.getDemandDetail();
		final boolean noShipmentScheduleForPicking = demandDetail == null || demandDetail.getShipmentScheduleId() <= 0;
		if (noShipmentScheduleForPicking)
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event's candidate has no shipmentScheduleId; candidate={}",
					candidate);
			return;
		}

		final Collection<HUDescriptor> huOnHandQtyChangeDescriptors = transactionEvent.getHuOnHandQtyChangeDescriptors();
		final boolean noHUsToPick = huOnHandQtyChangeDescriptors == null || huOnHandQtyChangeDescriptors.isEmpty();
		if (noHUsToPick)
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event has no HuOnHandQtyChangeDescriptors");
			return;
		}

		final ImmutableSet<Integer> huIdsToPick = huOnHandQtyChangeDescriptors.stream()
				.filter(huDescriptor -> huDescriptor.getQuantity().signum() > 0)
				.map(HUDescriptor::getHuId)
				.collect(ImmutableSet.toImmutableSet());

		final PickingRequestedEvent pickingRequestedEvent = PickingRequestedEvent.builder()
				.eventDescriptor(transactionEvent.getEventDescriptor())
				.shipmentScheduleId(demandDetail.getShipmentScheduleId())
				.topLevelHuIdsToPick(huIdsToPick)
				.build();

		postMaterialEventService.postEventAfterNextCommit(pickingRequestedEvent);
	}

	private Flag extractPickDirectlyIfFeasible(@NonNull final Candidate candidate)
	{
		final Flag pickDirectlyIfFeasible;
		switch (candidate.getBusinessCase())
		{
			case PRODUCTION:
				pickDirectlyIfFeasible = ProductionDetail
						.cast(candidate.getBusinessCaseDetail())
						.getPickDirectlyIfFeasible();
				break;
			case DISTRIBUTION:
				pickDirectlyIfFeasible = DistributionDetail
						.cast(candidate.getBusinessCaseDetail())
						.getPickDirectlyIfFeasible();
				break;
			default:
				pickDirectlyIfFeasible = null;
				Check.fail("Unsupported business case {}; candidate={}",
						candidate.getBusinessCase(), candidate);
		}
		return pickDirectlyIfFeasible;
	}

	private List<Candidate> prepareCandidatesForShipmentScheduleIds(@NonNull final AbstractTransactionEvent event)
	{
		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = event.getShipmentScheduleIds2Qtys();

		final Builder<Candidate> result = ImmutableList.builder();

		for (final Entry<Integer, BigDecimal> shipmentScheduleId2Qty : shipmentScheduleIds2Qtys.entrySet())
		{
			final List<Candidate> candidates = createCandidateForShipmentSchedule(event, shipmentScheduleId2Qty);
			result.addAll(candidates);
		}
		return result.build();
	}

	private List<Candidate> createCandidateForShipmentSchedule(
			@NonNull final AbstractTransactionEvent event,
			@NonNull final Entry<Integer, BigDecimal> shipmentScheduleId2Qty)
	{
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofShipmentScheduleId(shipmentScheduleId2Qty.getKey());

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(CandidateType.DEMAND)
				.demandDetailsQuery(demandDetailsQuery) // only search via demand detail ..the product and warehouse will also match, but e.g. the date probably won't!
				.build();
		final Candidate existingCandidate = retrieveBestMatchingCandidateOrNull(query, event);

		final List<Candidate> candidates;

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final DemandDetail demandDetail = DemandDetail.forShipmentScheduleIdAndOrderLineId(
					shipmentScheduleId2Qty.getKey(),
					-1,
					-1,
					shipmentScheduleId2Qty.getValue());

			final CandidateBuilder builder = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					shipmentScheduleId2Qty.getValue());

			final Candidate candidate = builder
					.businessCaseDetail(demandDetail)
					.transactionDetail(createTransactionDetail(event))
					.build();
			candidates = ImmutableList.of(candidate);
		}
		else if (existingCandidate != null)
		{
			candidates = createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
					existingCandidate,
					createTransactionDetail(event));
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidates;
	}

	private List<Candidate> prepareCandidateForReceiptScheduleIds(@NonNull final AbstractTransactionEvent event)
	{
		final Map<Integer, BigDecimal> receiptScheduleIds2Qtys = event.getReceiptScheduleIds2Qtys();

		final Builder<Candidate> result = ImmutableList.builder();

		for (final Entry<Integer, BigDecimal> receiptScheduleId2Qty : receiptScheduleIds2Qtys.entrySet())
		{
			final List<Candidate> candidates = createCandidateForReceiptSchedule(event, receiptScheduleId2Qty);
			result.addAll(candidates);
		}
		return result.build();
	}

	/** uses PurchaseDetails.receiptScheduleRepoId to find out if a candidate already exists */
	private List<Candidate> createCandidateForReceiptSchedule(
			@NonNull final AbstractTransactionEvent event,
			@NonNull final Entry<Integer, BigDecimal> receiptScheduleId2Qty)
	{
		final List<Candidate> candidates;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final PurchaseDetailsQuery purchaseDetailsQuery = PurchaseDetailsQuery.builder()
				.receiptScheduleRepoId(receiptScheduleId2Qty.getKey())
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(CandidateType.SUPPLY) // without it we might get stock candidates which we don't want
				.purchaseDetailsQuery(purchaseDetailsQuery)
				.build();

		final Candidate existingCandidate = retrieveBestMatchingCandidateOrNull(query, event);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			// prepare the purchase detail with our inoutLineId
			final PurchaseDetail purchaseDetail = PurchaseDetail.builder()
					.advised(Flag.FALSE_DONT_UPDATE)
					.plannedQty(receiptScheduleId2Qty.getValue())
					.receiptScheduleRepoId(receiptScheduleId2Qty.getKey())
					.build();

			final Candidate candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.businessCaseDetail(purchaseDetail)
							.transactionDetail(transactionDetailOfEvent)
							.build();
			candidates = ImmutableList.of(candidate);

		}
		else if (existingCandidate != null)
		{
			candidates = createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidates;
	}

	private List<Candidate> createCandidateForPPorder(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final int ppOrderLineIdForQuery = event.getPpOrderLineId() > 0
				? event.getPpOrderLineId()
				: ProductionDetailsQuery.NO_PP_ORDER_LINE_ID;

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderId(event.getPpOrderId())
				.ppOrderLineId(ppOrderLineIdForQuery).build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.productionDetailsQuery(productionDetailsQuery)
				.build();

		final Candidate existingCandidate = retrieveBestMatchingCandidateOrNull(query, event);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final ProductionDetail productionDetail = productionDetailsQuery
					.toProductionDetailBuilder()
					.advised(Flag.FALSE_DONT_UPDATE)
					.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE)
					.plannedQty(event.getQuantity())
					.build();

			final Candidate candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.businessCaseDetail(productionDetail)
							.transactionDetail(transactionDetailOfEvent)
							.build();
			candidates = ImmutableList.of(candidate);
		}
		else if (existingCandidate != null)
		{
			candidates = createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidates;
	}

	private List<Candidate> createCandidateForDDorder(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final DistributionDetailsQuery distributionDetailsQuery = DistributionDetailsQuery.builder()
				.ddOrderLineId(event.getDdOrderLineId())
				.ddOrderId(event.getDdOrderId())
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.distributionDetailsQuery(distributionDetailsQuery) // only search via distribution detail, ..the product and warehouse will also match, but e.g. the date probably won't!
				.build();

		final Candidate existingCandidate = retrieveBestMatchingCandidateOrNull(query, event);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final DistributionDetail distributionDetail = distributionDetailsQuery
					.toDistributionDetailBuilder()
					.plannedQty(event.getQuantity())
					.build();

			final Candidate candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.businessCaseDetail(distributionDetail)
							.transactionDetail(transactionDetailOfEvent)
							.build();
			candidates = ImmutableList.of(candidate);
		}
		else if (existingCandidate != null)
		{
			candidates = createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidates;
	}

	private Candidate retrieveBestMatchingCandidateOrNull(
			@NonNull final CandidatesQuery queryWithoutAttributesKey,
			@NonNull final AbstractTransactionEvent transactionEvent)
	{
		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery
				.builder()
				.storageAttributesKey(transactionEvent.getMaterialDescriptor().getStorageAttributesKey())
				.build();

		final CandidatesQuery queryWithAttributesKey = queryWithoutAttributesKey
				.withMaterialDescriptorQuery(materialDescriptorQuery)
				.withMatchExactStorageAttributesKey(true);

		final Candidate existingCandidate = Util.coalesceSuppliers(
				() -> candidateRepository.retrieveLatestMatchOrNull(queryWithAttributesKey),
				() -> candidateRepository.retrieveLatestMatchOrNull(queryWithoutAttributesKey));
		return existingCandidate;
	}

	private AdempiereException createExceptionForUnexpectedEvent(@NonNull final AbstractTransactionEvent event)
	{
		return new AdempiereException("AbstractTransactionEvent with unexpected type and not-yet-existing candidate")
				.appendParametersToMessage()
				.setParameter("abstractTransactionEvent", event);
	}

	private TransactionDetail createTransactionDetail(@NonNull final AbstractTransactionEvent event)
	{
		final TransactionDetail transactionDetailOfEvent = TransactionDetail
				.forCandidateOrQuery(
						event.getQuantityDelta(), // quantity and storageAttributesKey won't be used in the query, but in the following insert or update
						event.getMaterialDescriptor().getStorageAttributesKey(),
						event.getMaterialDescriptor().getAttributeSetInstanceId(),
						event.getTransactionId());
		return transactionDetailOfEvent;
	}

	private List<Candidate> prepareUnrelatedCandidate(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final CandidatesQuery query = CandidatesQuery.builder()
				.transactionDetail(TransactionDetail.forQuery(event.getTransactionId()))
				.build();
		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final Candidate candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.transactionDetail(transactionDetailOfEvent)
							.build();
			candidates = ImmutableList.of(candidate);
		}
		else if (existingCandidate != null)
		{
			candidates = createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidates;
	}

	/**
	 * @return a list with one or two candidates. The list's first item contains the given {@code changedTransactionDetail}.
	 */
	@VisibleForTesting
	List<Candidate> createOneOrTwoCandidatesWithChangedTransactionDetailAndQuantity(
			@NonNull final Candidate candidate,
			@NonNull final TransactionDetail changedTransactionDetail)
	{
		final boolean transactionMatchesCandidate = Objects
				.equals(
						candidate.getMaterialDescriptor().getStorageAttributesKey(),
						changedTransactionDetail.getStorageAttributesKey());

		if (transactionMatchesCandidate)
		{
			final ImmutableList<TransactionDetail> otherTransactionDetails = candidate.getTransactionDetails()
					.stream()
					.filter(transactionDetail -> transactionDetail.getTransactionId() != changedTransactionDetail.getTransactionId())
					.collect(ImmutableList.toImmutableList());

			// note: using TreeSet to make sure we don't end up with duplicated transactionDetails
			final TreeSet<TransactionDetail> newTransactionDetailsSet = new TreeSet<>(Comparator.comparing(TransactionDetail::getTransactionId));
			newTransactionDetailsSet.addAll(otherTransactionDetails);
			newTransactionDetailsSet.add(changedTransactionDetail);

			final Candidate withTransactionDetails = candidate.withTransactionDetails(ImmutableList.copyOf(newTransactionDetailsSet));
			final BigDecimal actualQty = withTransactionDetails.computeActualQty();
			final BigDecimal plannedQty = candidate.getPlannedQty();

			return ImmutableList.of(withTransactionDetails.withQuantity(actualQty.max(plannedQty)));
		}
		else
		{
			// create a copy of candidate, just with
			// * the transaction's ASI/storage-key
			// * plannedQty == actualQty == transactionQty
			// * the transactionDetail added to it
			final MaterialDescriptor newMaterialDescriptor = candidate
					.getMaterialDescriptor()
					.withStorageAttributes(
							changedTransactionDetail.getStorageAttributesKey(),
							changedTransactionDetail.getAttributeSetInstanceId())
					.withQuantity(changedTransactionDetail.getQuantity());

			final Candidate newCandidate = candidate
					.toBuilder()
					.id(null)
					.parentId(null) // important to make sure a supply new candidate gets a stock record
					.materialDescriptor(newMaterialDescriptor)
					.clearTransactionDetails()
					.transactionDetail(changedTransactionDetail)
					.build();

			// subtract the transaction's Qty from the candidate
			final BigDecimal actualQty = candidate.computeActualQty();
			final BigDecimal plannedQty = candidate.getPlannedQty().subtract(changedTransactionDetail.getQuantity());
			final BigDecimal updatedQty = actualQty.max(plannedQty);
			final Candidate updatedCandidate = candidate.withQuantity(updatedQty);

			// return the subtracted-qty-candidate and the copy
			return ImmutableList.of(newCandidate, updatedCandidate);
		}
	}

	/**
	 * @param transactionCreatedEvent note that creating a new candidate doesn't make sense for a {@link TransactionDeletedEvent}
	 */
	@VisibleForTesting
	static CandidateBuilder createBuilderForNewUnrelatedCandidate(
			@NonNull final TransactionCreatedEvent transactionCreatedEvent,
			@NonNull final BigDecimal quantity)
	{
		final CandidateBuilder builder = Candidate
				.builderForEventDescr(transactionCreatedEvent.getEventDescriptor());
		if (quantity.signum() <= 0)
		{
			return builder.type(CandidateType.UNRELATED_DECREASE)
					.materialDescriptor(transactionCreatedEvent.getMaterialDescriptor().withQuantity(quantity.negate()));
		}
		else
		{
			return builder.type(CandidateType.UNRELATED_INCREASE)
					.materialDescriptor(transactionCreatedEvent.getMaterialDescriptor());
		}
	}
}
