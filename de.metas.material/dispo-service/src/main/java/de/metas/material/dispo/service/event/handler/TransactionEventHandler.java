package de.metas.material.dispo.service.event.handler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Loggables;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.Flag;
import de.metas.material.dispo.commons.candidate.TransactionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.HUOnHandQtyChangeDescriptor;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
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

		candidates.forEach(candidate -> candidateChangeHandler.onCandidateNewOrChange(candidate));
	}

	@VisibleForTesting
	List<Candidate> createCandidatesForTransactionEvent(@NonNull final AbstractTransactionEvent event)
	{
		final List<Candidate> candidates = new ArrayList<>();

		if (event.getShipmentScheduleIds2Qtys() != null && !event.getShipmentScheduleIds2Qtys().isEmpty())
		{
			candidates.addAll(prepareCandidateForShipmentScheduleId(event));
		}
		else if (event.getPpOrderId() > 0)
		{
			final Candidate candidateForPPorder = prepareCandidateForPPorder(event);
			firePickRequiredEvent(candidateForPPorder, event);

			candidates.add(candidateForPPorder);
		}
		else if (event.getDdOrderLineId() > 0)
		{
			final Candidate candidateForDDorder = prepareCandidateForDDorder(event);
			firePickRequiredEvent(candidateForDDorder, event);

			candidates.add(candidateForDDorder);
		}
		else
		{
			candidates.add(prepareUnrelatedCandidate(event));
		}
		return candidates;
	}

	private void firePickRequiredEvent(
			@NonNull final Candidate candidateForPPorder,
			@NonNull final AbstractTransactionEvent transactionEvent)
	{
		if (transactionEvent instanceof TransactionDeletedEvent)
		{
			return;
		}

		final Flag pickDirectlyIfFeasible = candidateForPPorder
				.getProductionDetail()
				.getPickDirectlyIfFeasible();
		final boolean dontPickDirectly = !pickDirectlyIfFeasible
				.toBoolean();
		if (dontPickDirectly)
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event's candidate has pickDirectlyIfFeasible={}; candidate={}",
					pickDirectlyIfFeasible, candidateForPPorder);
			return;
		}

		final DemandDetail demandDetail = candidateForPPorder.getDemandDetail();
		final boolean noShipmentScheduleForPicking = demandDetail == null || demandDetail.getShipmentScheduleId() <= 0;
		if (noShipmentScheduleForPicking)
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event's candidate has no shipmentScheduleId; candidate={}",
					candidateForPPorder);
			return;
		}

		final List<HUOnHandQtyChangeDescriptor> huOnHandQtyChangeDescriptors = transactionEvent.getHuOnHandQtyChangeDescriptors();
		final boolean noHUsToPick = huOnHandQtyChangeDescriptors == null || huOnHandQtyChangeDescriptors.isEmpty();
		if (noHUsToPick)
		{
			Loggables.get().addLog("Not posting PickingRequestedEvent: this event has no HuOnHandQtyChangeDescriptors");
			return;
		}

		final ImmutableList<Integer> huIdsToPick = huOnHandQtyChangeDescriptors.stream()
				.filter(huDescriptor -> huDescriptor.getQuantity().signum() > 0)
				.map(HUOnHandQtyChangeDescriptor::getHuId)
				.collect(ImmutableList.toImmutableList());

		final PickingRequestedEvent pickingRequestedEvent = PickingRequestedEvent.builder()
				.eventDescriptor(transactionEvent.getEventDescriptor())
				.shipmentScheduleId(demandDetail.getShipmentScheduleId())
				.topLevelHuIdsToPick(huIdsToPick)
				.build();

		postMaterialEventService.postEventAfterNextCommit(pickingRequestedEvent);
	}

	private List<Candidate> prepareCandidateForShipmentScheduleId(@NonNull final AbstractTransactionEvent event)
	{
		final Map<Integer, BigDecimal> shipmentScheduleIds2Qtys = event.getShipmentScheduleIds2Qtys();

		final Builder<Candidate> result = ImmutableList.builder();

		for (final Entry<Integer, BigDecimal> shipmentScheduleId2Qty : shipmentScheduleIds2Qtys.entrySet())
		{
			final Candidate candidate = createCandidateForShipmentSchedule(event, shipmentScheduleId2Qty);
			result.add(candidate);
		}
		return result.build();
	}

	private Candidate createCandidateForShipmentSchedule(final AbstractTransactionEvent event, final Entry<Integer, BigDecimal> shipmentScheduleId2Qty)
	{
		final DemandDetail demandDetail = DemandDetail.forShipmentScheduleIdAndOrderLineId(
				shipmentScheduleId2Qty.getKey(),
				-1,
				-1);

		final CandidatesQuery query = CandidatesQuery.builder().type(CandidateType.DEMAND)
				.demandDetail(demandDetail) // only search via demand detail ..the product and warehouse will also match, but e.g. the date probably won't!
				.build();

		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);
		final Candidate candidate;

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					shipmentScheduleId2Qty.getValue())
							.demandDetail(demandDetail)
							.transactionDetail(createTransactionDetail(event))
							.build();
		}
		else if (existingCandidate != null)
		{
			candidate = newCandidateWithAddedTransactionDetail(
					existingCandidate,
					createTransactionDetail(event));
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}

		return candidate;
	}

	private Candidate prepareCandidateForPPorder(@NonNull final AbstractTransactionEvent event)
	{
		final Candidate candidate;
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

		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final ProductionDetail productionDetail = productionDetailsQuery
					.toProductionDetailBuilder()
					.advised(Flag.FALSE_DONT_UPDATE)
					.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE)
					.actualQty(event.getQuantity())
					.build();

			candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.productionDetail(productionDetail)
							.transactionDetail(transactionDetailOfEvent)
							.build();
		}
		else if (existingCandidate != null)
		{
			candidate = newCandidateWithAddedTransactionDetail(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidate;
	}

	private Candidate prepareCandidateForDDorder(@NonNull final AbstractTransactionEvent event)
	{
		final Candidate candidate;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final DistributionDetailsQuery distributionDetailsQuery = DistributionDetailsQuery.builder()
				.ddOrderLineId(event.getDdOrderLineId())
				.ddOrderId(event.getDdOrderId())
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.distributionDetailsQuery(distributionDetailsQuery) // only search via distribution detail, ..the product and warehouse will also match, but e.g. the date probably won't!
				.build();

		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			final DistributionDetail distributionDetail = distributionDetailsQuery
					.toDistributionDetailBuilder()
					.actualQty(event.getQuantity()).build();

			candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.distributionDetail(distributionDetail)
							.transactionDetail(transactionDetailOfEvent)
							.build();
		}
		else if (existingCandidate != null)
		{
			candidate = newCandidateWithAddedTransactionDetail(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidate;
	}

	private AdempiereException createExceptionForUnexpectedEvent(final AbstractTransactionEvent event)
	{
		return new AdempiereException("AbstractTransactionEvent with unexpected type and not-yet-existing candidate")
				.appendParametersToMessage()
				.setParameter("abstractTransactionEvent", event);
	}

	private TransactionDetail createTransactionDetail(@NonNull final AbstractTransactionEvent event)
	{
		final TransactionDetail transactionDetailOfEvent = TransactionDetail
				.forCandidateOrQuery(
						event.getQuantityDelta(), // quantity won't be used in the query, but in the following insert or update
						event.getTransactionId());
		return transactionDetailOfEvent;
	}

	private Candidate prepareUnrelatedCandidate(@NonNull final AbstractTransactionEvent event)
	{
		final Candidate candidate;
		final TransactionDetail transactionDetailOfEvent = createTransactionDetail(event);

		final CandidatesQuery query = CandidatesQuery.builder()
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(event.getMaterialDescriptor()))
				.transactionDetail(TransactionDetail.forQuery(event.getTransactionId()))
				.build();
		final Candidate existingCandidate = candidateRepository.retrieveLatestMatchOrNull(query);

		final boolean unrelatedNewTransaction = existingCandidate == null && event instanceof TransactionCreatedEvent;
		if (unrelatedNewTransaction)
		{
			candidate = createBuilderForNewUnrelatedCandidate(
					(TransactionCreatedEvent)event,
					event.getQuantity())
							.transactionDetail(transactionDetailOfEvent)
							.build();
		}
		else if (existingCandidate != null)
		{
			candidate = newCandidateWithAddedTransactionDetailAndQuantity(
					existingCandidate,
					transactionDetailOfEvent);
		}
		else
		{
			throw createExceptionForUnexpectedEvent(event);
		}
		return candidate;
	}

	private Candidate newCandidateWithAddedTransactionDetailAndQuantity(
			@NonNull final Candidate candidate,
			@NonNull final TransactionDetail transactionDetail)
	{
		final BigDecimal newQuantity = candidate
				.getQuantity()
				.add(transactionDetail.getQuantity());

		Candidate newCandidate = candidate.withQuantity(newQuantity);
		newCandidate = newCandidateWithAddedTransactionDetail(newCandidate, transactionDetail);
		return newCandidate;
	}

	private Candidate newCandidateWithAddedTransactionDetail(
			@NonNull final Candidate candidate,
			@NonNull final TransactionDetail transactionDetail)
	{
		final Builder<TransactionDetail> newTransactionDetailsList = //
				ImmutableList.<TransactionDetail> builder()
						.addAll(candidate.getTransactionDetails())
						.add(transactionDetail);

		return candidate.withTransactionDetails(newTransactionDetailsList.build());
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
