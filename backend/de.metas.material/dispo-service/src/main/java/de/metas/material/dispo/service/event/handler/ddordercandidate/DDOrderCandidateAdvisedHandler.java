package de.metas.material.dispo.service.event.handler.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
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
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateAdvisedEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.util.Loggables;
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
	private final SupplyProposalEvaluator supplyProposalEvaluator;
	private final RequestMaterialOrderService requestMaterialOrderService;

	public DDOrderCandidateAdvisedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final RequestMaterialOrderService requestMaterialOrderService,
			@NonNull final DDOrderDetailRequestHandler ddOrderDetailRequestHandler,
			@NonNull final MainDataRequestHandler mainDataRequestHandler)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler,
				ddOrderDetailRequestHandler,
				mainDataRequestHandler);

		this.requestMaterialOrderService = requestMaterialOrderService;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
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
			return;
		}

		final DDOrderCandidateAdvisedEvent eventEffective = updateEvent(event);
		final CandidatesGroup group = createAndProcessCandidates(eventEffective);

		if (eventEffective.isAdvisedToCreateDDOrderCandidate())
		{
			requestMaterialOrderService.requestMaterialOrderForCandidates(group, eventEffective.getTraceId());
		}
		else
		{
			Loggables.addLog("Skip creating DD_Order_Candidate because isAdvisedToCreateDDOrderCandidate=false (maybe because PP_Product_Planning.IsCreatePlan=false?)");
		}
	}

	private boolean isAccepted(final DDOrderCandidateAdvisedEvent event)
	{
		return supplyProposalEvaluator.isProposalAccepted(
				SupplyProposalEvaluator.SupplyProposal.builder()
						.supplyWarehouseId(event.getTargetWarehouseId())
						.demandWarehouseId(event.getSourceWarehouseId())
						.demandDetail(DemandDetail.forSupplyRequiredDescriptor(event.getSupplyRequiredDescriptorNotNull()))
						.build()
		);
	}

	@Override
	protected Flag extractIsAdviseEvent(@NonNull final AbstractDDOrderCandidateEvent ddOrderEvent)
	{
		return Flag.TRUE;
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
		final DDOrderCandidateData ddOrderCandidate = event.getDdOrderCandidate();
		final PPOrderRef ppOrderRef = ddOrderCandidate.getPpOrderRef();
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
}
