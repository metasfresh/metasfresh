package de.metas.material.dispo.service.event.handler.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateUpdatedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderCandidateUpdatedHandler
		extends DDOrderCandidateAdvisedOrCreatedHandler<DDOrderCandidateUpdatedEvent>
{
	public DDOrderCandidateUpdatedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final DDOrderDetailRequestHandler ddOrderDetailRequestHandler,
			@NonNull final MainDataRequestHandler mainDataRequestHandler)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler,
				ddOrderDetailRequestHandler,
				mainDataRequestHandler);
	}

	@Override
	public Collection<Class<? extends DDOrderCandidateUpdatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateUpdatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateUpdatedEvent event)
	{
		createAndProcessCandidates(event);
	}

	@Override
	protected Flag extractIsAdviseEvent(final @NonNull AbstractDDOrderCandidateEvent event) {return Flag.FALSE_DONT_UPDATE;}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		final int ddOrderCandidateId = event.getExistingDDOrderCandidateId();
		if (ddOrderCandidateId <= 0)
		{
			throw new AdempiereException("existing DD_Order_Candidate_ID should be provided")
					.setParameter("event", event)
					.appendParametersToMessage();
		}

		return CandidatesQuery.builder()
				.type(candidateType)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.productId(event.getProductId())
						.storageAttributesKey(event.getStorageAttributesKey())
						.build())
				.groupId(event.getMaterialDispoGroupId())
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.distributionDetailsQuery(DistributionDetailsQuery.builder().ddOrderCandidateId(ddOrderCandidateId).build())
				.build();
	}
}
