package de.metas.material.dispo.service.event.handler.ddordercandidate;

import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateCreatedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.product.ResourceId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

@RequiredArgsConstructor
abstract class DDOrderCandidateAdvisedOrCreatedHandler<T extends AbstractDDOrderCandidateEvent>
		implements MaterialEventHandler<T>
{
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWrite;
	@NonNull private final CandidateChangeService candidateChangeHandler;
	@NonNull private final DDOrderDetailRequestHandler ddOrderDetailRequestHandler;
	@NonNull private final MainDataRequestHandler mainDataRequestHandler;

	@NonNull
	protected static WarehouseId extractWarehouseId(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		switch (candidateType)
		{
			case SUPPLY:
				return event.getTargetWarehouseId();
			case DEMAND:
				return event.getSourceWarehouseId();
			default:
				throw new AdempiereException("Unexpected candidateType").appendParametersToMessage()
						.setParameter("candidateType", candidateType)
						.setParameter("event", event);
		}
	}

	@Nullable
	protected static ResourceId extractPlantId(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		switch (candidateType)
		{
			case SUPPLY:
				return event.getTargetPlantId();
			case DEMAND:
				return null;
			default:
				throw new AdempiereException("Unexpected candidateType").appendParametersToMessage()
						.setParameter("candidateType", candidateType)
						.setParameter("event", event);
		}
	}

	@NonNull
	protected static Instant computeDate(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		switch (candidateType)
		{
			case SUPPLY:
				return event.getDatePromised();
			case DEMAND:
				final Instant datePromised = event.getDatePromised();
				final Duration duration = Duration.ofDays(event.getDurationDays());
				return datePromised.minus(duration);
			default:
				throw new AdempiereException("Unexpected candidateType").appendParametersToMessage()
						.setParameter("candidateType", candidateType)
						.setParameter("event", event);
		}
	}

	@NonNull
	protected final CandidatesGroup createAndProcessCandidates(final AbstractDDOrderCandidateEvent event)
	{
		//
		// create or update the supply candidate
		final Candidate supplyCandidate = createOrUpdateCandidate(event, CandidateType.SUPPLY, null);

		//
		// create  or update the demand candidate
		final MaterialDispoGroupId groupId = Check.assumeNotNull(supplyCandidate.getGroupId(), "supply candidate has groupId set: {}", supplyCandidate);
		final int expectedSeqNoForDemandCandidate = supplyCandidate.getSeqNo() + 1; // we expect the demand candidate to go with the supplyCandidate's SeqNo + 1
		// NOTE this might cause 'candidateChangeHandler' to trigger another event
		final Candidate demandCandidate = createOrUpdateCandidate(
				event,
				CandidateType.DEMAND,
				builder -> builder.parentId(supplyCandidate.getId())
						.groupId(groupId)
						.seqNo(expectedSeqNoForDemandCandidate)
						.minMaxDescriptor(event.getFromWarehouseMinMaxDescriptor())
		);

		//
		// Adjust seqNo(s) in case is not what we expected
		if (expectedSeqNoForDemandCandidate != demandCandidate.getSeqNo())
		{
			// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
			candidateRepositoryWrite.updateCandidateById(supplyCandidate.withSeqNo(demandCandidate.getSeqNo() - 1));

			final Candidate parentOfSupplyCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidate.getParentId()));
			candidateRepositoryWrite.updateCandidateById(parentOfSupplyCandidate.withSeqNo(demandCandidate.getSeqNo() - 2));
		}

		//
		// Fire main data updates
		DDOrderCandidateCreatedEvent.castIfApplies(event).ifPresent(this::handleMainDataUpdates);

		return CandidatesGroup.of(supplyCandidate, demandCandidate);
	}

	private @NonNull Candidate createOrUpdateCandidate(
			@NonNull final AbstractDDOrderCandidateEvent event,
			@NonNull final CandidateType candidateType,
			@Nullable final Consumer<CandidateBuilder> updater)
	{
		final CandidateBuilder builder = candidateRepositoryRetrieval.retrieveLatestMatch(createPreExistingCandidatesQuery(event, candidateType))
				.map(Candidate::toBuilder)
				.orElseGet(() -> Candidate.builderForEventDescr(event.getEventDescriptor()));

		builder.type(candidateType)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(event.getProductDescriptor())
						// .customerId(candidate.getBPartnerId()) // the candidate's bpartner is not the customer, but probably the shipper
						.quantity(event.getQty())
						.date(computeDate(event, candidateType))
						.warehouseId(extractWarehouseId(event, candidateType))
						.build())
				.businessCaseDetail(toDistributionDetail(event, candidateType))
				.additionalDemandDetail(DemandDetail.forSupplyRequiredDescriptor(event.getSupplyRequiredDescriptorNotNull()).withTraceId(event.getTraceId()))
				.simulated(event.isSimulated())
				.build();

		if (updater != null)
		{
			updater.accept(builder);
		}

		return candidateChangeHandler.onCandidateNewOrChange(builder.build())
				.getCandidate();
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(AbstractDDOrderCandidateEvent event, CandidateType candidateType);

	protected abstract Flag extractIsAdviseEvent(@NonNull final AbstractDDOrderCandidateEvent event);

	private static DistributionDetail toDistributionDetail(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		return DistributionDetail.builder()
				//.ddOrderDocStatus(ddOrder.getDocStatus())
				//.ddOrderId(ddOrder.getDdOrderId())
				//.ddOrderLineId(ddOrderLine.getDdOrderLineId())
				.distributionNetworkAndLineId(event.getDistributionNetworkAndLineId())
				.qty(event.getQty())
				.plantId(extractPlantId(event, candidateType))
				.productPlanningId(event.getProductPlanningId())
				.shipperId(event.getShipperId())
				.build();
	}

	private void handleMainDataUpdates(@NonNull final DDOrderCandidateCreatedEvent event)
	{
		if (event.isSimulated())
		{
			return;
		}

		// final OrgId orgId = event.getEventDescriptor().getOrgId();
		// final ZoneId timeZone = orgDAO.getTimeZone(orgId);
		//
		// final DDOrderMainDataHandler mainDataUpdater = DDOrderMainDataHandler.builder()
		// 		.ddOrderDetailRequestHandler(ddOrderDetailRequestHandler)
		// 		.mainDataRequestHandler(mainDataRequestHandler)
		// 		.abstractDDOrderEvent(event)
		// 		.ddOrderLine(ddOrderLine)
		// 		.orgZone(timeZone)
		// 		.build();
		//
		// mainDataUpdater.handleUpdate();
	}
}
