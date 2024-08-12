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
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrderRef;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateCreatedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.product.ResourceId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.function.Consumer;

@RequiredArgsConstructor
abstract class DDOrderCandidateAdvisedOrCreatedHandler<T extends AbstractDDOrderCandidateEvent>
		implements MaterialEventHandler<T>
{
	@NonNull protected final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
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
	private static ResourceId extractPlantId(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
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
				return event.getSupplyDate();
			case DEMAND:
				return event.getDemandDate();
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
		final MaterialDispoGroupId groupId = supplyCandidate.getGroupIdNotNull();
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
		final Candidate existingCandidate = candidateRepositoryRetrieval.retrieveLatestMatch(createPreExistingCandidatesQuery(event, candidateType)).orElse(null);

		final CandidateBuilder builder = existingCandidate != null
				? existingCandidate.toBuilder()
				: Candidate.builderForEventDescriptor(event.getEventDescriptor());

		builder.type(candidateType)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.materialDescriptor(MaterialDescriptor.builder()
						.productDescriptor(event.getProductDescriptor())
						// .customerId(candidate.getBPartnerId()) // the candidate's bpartner is not the customer, but probably the shipper
						.quantity(event.getQty())
						.date(computeDate(event, candidateType))
						.warehouseId(extractWarehouseId(event, candidateType))
						.build())
				.businessCaseDetail(updateDistributionDetail(toDistributionDetailBuilder(existingCandidate), event, candidateType))
				.simulated(event.isSimulated());

		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		if (supplyRequiredDescriptor != null)
		{
			builder.additionalDemandDetail(DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor).withTraceId(event.getTraceId()));
		}

		if (updater != null)
		{
			updater.accept(builder);
		}

		return candidateChangeHandler.onCandidateNewOrChange(builder.build()).getCandidate();
	}

	private static DistributionDetail.DistributionDetailBuilder toDistributionDetailBuilder(@Nullable final Candidate existingCandidate)
	{
		final DistributionDetail existingDistributionDetail = existingCandidate != null
				? existingCandidate.getBusinessCaseDetail(DistributionDetail.class).orElse(null)
				: null;

		return existingDistributionDetail != null
				? existingDistributionDetail.toBuilder()
				: DistributionDetail.builder();
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(AbstractDDOrderCandidateEvent event, CandidateType candidateType);

	private static DistributionDetail updateDistributionDetail(
			@NonNull final DistributionDetail.DistributionDetailBuilder builder,
			@NonNull final AbstractDDOrderCandidateEvent event,
			@NonNull final CandidateType candidateType)
	{
		return builder
				.ddOrderRef(DDOrderRef.ofNullableDDOrderCandidateId(event.getExistingDDOrderCandidateId()))
				.distributionNetworkAndLineId(event.getDistributionNetworkAndLineId())
				.qty(event.getQty())
				.plantId(extractPlantId(event, candidateType))
				.productPlanningId(event.getProductPlanningId())
				.shipperId(event.getShipperId())
				.forwardPPOrderRef(event.getForwardPPOrderRef())
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
