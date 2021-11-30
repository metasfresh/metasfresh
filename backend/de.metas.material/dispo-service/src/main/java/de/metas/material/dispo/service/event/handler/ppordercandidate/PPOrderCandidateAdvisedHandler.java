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
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateRequestedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public final class PPOrderCandidateAdvisedHandler extends PPOrderCandidateEventHandler
		implements MaterialEventHandler<PPOrderCandidateAdvisedEvent>
{
	private final PostMaterialEventService materialEventService;

	public PPOrderCandidateAdvisedHandler(
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		super(candidateChangeService, candidateRepositoryRetrieval);

		this.materialEventService = materialEventService;
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

		final MaterialDispoGroupId groupId = handlePPOrderCandidateAdvisedEvent(event);

		final PPOrderCandidate ppOrderCandidateWithGroupId = event
				.getPpOrderCandidate()
				.toBuilder()
				.ppOrderData(event.getPpOrderCandidate()
									 .getPpOrderData()
									 .toBuilder()
									 .materialDispoGroupId(groupId)
									 .build())
				.build();

		final PPOrderCandidateRequestedEvent ppOrderRequestEvent = PPOrderCandidateRequestedEvent
				.builder()
				.eventDescriptor(event.getEventDescriptor())
				.ppOrderCandidate(ppOrderCandidateWithGroupId)
				.supplyRequiredDescriptor(event.getSupplyRequiredDescriptor())
				.directlyCreatePPOrder(event.isDirectlyCreatePPOrder())
				.build();

		materialEventService.postEventNow(ppOrderRequestEvent);
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
}