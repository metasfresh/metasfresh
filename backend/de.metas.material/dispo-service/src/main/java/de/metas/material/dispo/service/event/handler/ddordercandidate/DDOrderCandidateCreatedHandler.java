package de.metas.material.dispo.service.event.handler.ddordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddordercandidate.AbstractDDOrderCandidateEvent;
import de.metas.material.event.ddordercandidate.DDOrderCandidateCreatedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderCandidateCreatedHandler
		extends DDOrderCandidateAdvisedOrCreatedHandler<DDOrderCandidateCreatedEvent>
{
	public DDOrderCandidateCreatedHandler(
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
	public Collection<Class<? extends DDOrderCandidateCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(DDOrderCandidateCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final DDOrderCandidateCreatedEvent event)
	{
		createAndProcessCandidates(event);
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(@NonNull final AbstractDDOrderCandidateEvent event, @NonNull final CandidateType candidateType)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		if (supplyRequiredDescriptor != null && supplyRequiredDescriptor.getSupplyCandidateId() > 0)
		{
			return CandidatesQuery.fromId(CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId()));
		}

		final MaterialDispoGroupId groupId = event.getMaterialDispoGroupId();
		if (groupId == null)
		{
			// returned false, but don't write another log message; we already logged in the other createQuery() method
			return CandidatesQuery.FALSE;
		}

		return CandidatesQuery.builder()
				.type(candidateType)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.groupId(groupId)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.productId(event.getProductId())
						.storageAttributesKey(event.getStorageAttributesKey())
						.build())
				.build();
	}
}
