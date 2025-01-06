package de.metas.material.dispo.service.event.handler.ddordercandidate;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.order.OrderAndLineId;
import de.metas.product.IProductBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderCandidateAdvisedHandler
		extends DDOrderCandidateAdvisedOrCreatedHandler<DDOrderCandidateAdvisedEvent>
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final SupplyProposalEvaluator supplyProposalEvaluator;
	@NonNull private final PostMaterialEventService materialEventService;

	public DDOrderCandidateAdvisedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final DDOrderDetailRequestHandler ddOrderDetailRequestHandler,
			@NonNull final MainDataRequestHandler mainDataRequestHandler,
			@NonNull final PostMaterialEventService materialEventService)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler,
				ddOrderDetailRequestHandler,
				mainDataRequestHandler);

		this.supplyProposalEvaluator = supplyProposalEvaluator;
		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<Class<? extends DDOrderCandidateAdvisedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateAdvisedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateAdvisedEvent event)
	{
		if (!isAccepted(event))
		{
			deleteUnspecificSupplyCandidate(event);
			return;
		}

		final DDOrderCandidateAdvisedEvent eventEffective = updateEvent(event);
		final CandidatesGroup group = createAndProcessCandidates(eventEffective);

		materialEventService.enqueueEventAfterNextCommit(toDDOrderCandidateRequestedEvent(group, eventEffective));
	}

	private boolean isAccepted(final DDOrderCandidateAdvisedEvent event)
	{
		return supplyProposalEvaluator.isProposalAccepted(
				SupplyProposalEvaluator.SupplyProposal.builder()
						.supplyWarehouseId(event.getTargetWarehouseId())
						.demandWarehouseId(event.getSourceWarehouseId())
						.demandDetail(DemandDetail.forSupplyRequiredDescriptor(event.getSupplyRequiredDescriptorNotNull()))
						.productId(event.getProductId())
						.date(event.getSupplyRequiredDescriptorNotNull().getDemandDate())
						.build()
		);
	}

	private void deleteUnspecificSupplyCandidate(@NonNull final DDOrderCandidateAdvisedEvent event)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		if (supplyRequiredDescriptor == null)
		{
			return;
		}
		final CandidateId supplyCandidateId = CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId());
		if (supplyCandidateId == null)
		{
			return;
		}

		final Candidate supplyCandidate = candidateRepositoryRetrieval.retrieveById(supplyCandidateId);

		candidateChangeHandler.onCandidateDelete(supplyCandidate);
		Loggables.addLog("Deleted {}", supplyCandidate);
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();

		if (CandidateType.SUPPLY.equals(candidateType))
		{ // see if there is already an unspecific (without business-case etc) supply that now needs to be updated
			if (supplyRequiredDescriptor != null && supplyRequiredDescriptor.getSupplyCandidateId() > 0)
			{
				return CandidatesQuery.fromId(CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId()));
			}
		}

		if (supplyRequiredDescriptor == null)
		{
			throw new AdempiereException("supplyRequiredDescriptor shall be set for " + event);
		}

		return CandidatesQuery.builder()
				.type(candidateType)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.demandDetailsQuery(DemandDetailsQuery.ofSupplyRequiredDescriptor(supplyRequiredDescriptor))
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.productId(event.getProductId())
						.storageAttributesKey(event.getStorageAttributesKey())
						.warehouseId(extractWarehouseId(event, candidateType))
						.atTime(DateAndSeqNo.atTimeNoSeqNo(computeDate(event, candidateType)))
						.build())
				.distributionDetailsQuery(DistributionDetailsQuery.builder()
						.productPlanningId(event.getProductPlanningId())
						.distributionNetworkAndLineId(event.getDistributionNetworkAndLineId())
						.build())
				.build();
	}

	private DDOrderCandidateAdvisedEvent updateEvent(@NonNull final DDOrderCandidateAdvisedEvent event)
	{
		final PPOrderId ppOrderId = findPPOrderId(event).orElse(null);
		return event.withPPOrderId(ppOrderId);
	}

	private Optional<PPOrderId> findPPOrderId(@NonNull final DDOrderCandidateAdvisedEvent event)
	{
		final PPOrderRef ppOrderRef = event.getForwardPPOrderRef();
		if (ppOrderRef == null)
		{
			return Optional.empty();
		}

		if (ppOrderRef.getPpOrderId() != null)
		{
			return Optional.of(ppOrderRef.getPpOrderId());
		}

		if (ppOrderRef.getPpOrderCandidateId() <= 0)
		{
			return Optional.empty();
		}

		final Candidate candidate = candidateRepositoryRetrieval.retrieveLatestMatch(CandidatesQuery.builder()
						.businessCase(CandidateBusinessCase.PRODUCTION)
						.productionDetailsQuery(ProductionDetailsQuery.builder()
								.ppOrderCandidateId(ppOrderRef.getPpOrderCandidateId())
								.build())
						.build())
				.orElse(null);
		if (candidate == null)
		{
			return Optional.empty();
		}

		final ProductionDetail productionDetail = candidate.getBusinessCaseDetail(ProductionDetail.class).orElse(null);
		if (productionDetail == null)
		{
			return Optional.empty();
		}

		if (productionDetail.getPpOrderRef() == null)
		{
			return Optional.empty();
		}

		return Optional.ofNullable(productionDetail.getPpOrderRef().getPpOrderId());
	}

	@VisibleForTesting
	DDOrderCandidateRequestedEvent toDDOrderCandidateRequestedEvent(
			@NonNull final CandidatesGroup group,
			@NonNull final DDOrderCandidateAdvisedEvent event)
	{
		final String traceId = event.getTraceId();

		return DDOrderCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(group.getClientAndOrgId(), traceId))
				.dateOrdered(SystemTime.asInstant())
				.ddOrderCandidateData(toDDOrderCandidateData(group))
				.createDDOrder(event.isAdvisedToCreateDDOrder())
				.build();
	}

	private DDOrderCandidateData toDDOrderCandidateData(@NonNull final CandidatesGroup group)
	{
		final Candidate supplyCandidate = group.getSingleSupplyCandidate();
		final Candidate demandCandidate = group.getSingleDemandCandidate();

		final DistributionDetail supplyDistributionDetail = DistributionDetail.cast(supplyCandidate.getBusinessCaseDetail());
		final OrderAndLineId salesOrderLineId = demandCandidate.getSalesOrderLineId();

		return DDOrderCandidateData.builder()
				.clientAndOrgId(supplyCandidate.getClientAndOrgId())
				.productPlanningId(supplyDistributionDetail.getProductPlanningId())
				.distributionNetworkAndLineId(supplyDistributionDetail.getDistributionNetworkAndLineId())
				//
				.sourceWarehouseId(demandCandidate.getWarehouseId())
				.targetWarehouseId(supplyCandidate.getWarehouseId())
				.targetPlantId(supplyDistributionDetail.getPlantId())
				.shipperId(supplyDistributionDetail.getShipperId())
				//
				.customerId(BPartnerId.toRepoId(supplyCandidate.getCustomerId()))
				.salesOrderId(OrderAndLineId.toOrderRepoId(salesOrderLineId))
				.salesOrderLineId(OrderAndLineId.toOrderLineRepoId(salesOrderLineId))
				.forwardPPOrderRef(getPpOrderRef(supplyCandidate))
				//
				.productDescriptor(supplyCandidate.getMaterialDescriptor())
				.fromWarehouseMinMaxDescriptor(demandCandidate.getMinMaxDescriptor().toNullIfZero())
				.supplyDate(supplyCandidate.getDate())
				.demandDate(demandCandidate.getDate())
				//
				.qty(supplyCandidate.getQuantity())
				.uomId(productBL.getStockUOMId(supplyCandidate.getProductId()).getRepoId())
				//
				.simulated(supplyCandidate.isSimulated())
				.materialDispoGroupId(group.getEffectiveGroupId())
				.build();
	}

	private static PPOrderRef getPpOrderRef(final Candidate candidate)
	{
		final ProductionDetail productionDetail = candidate.getBusinessCaseDetail(ProductionDetail.class).orElse(null);
		if (productionDetail != null)
		{
			return productionDetail.getPpOrderRef();
		}

		final DistributionDetail distributionDetail = candidate.getBusinessCaseDetail(DistributionDetail.class).orElse(null);
		if (distributionDetail != null)
		{
			return distributionDetail.getForwardPPOrderRef();
		}

		return null;
	}

}
