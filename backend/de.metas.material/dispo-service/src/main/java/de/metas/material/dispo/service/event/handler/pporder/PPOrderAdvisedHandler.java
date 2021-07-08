package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.common.util.time.SystemTime;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
public final class PPOrderAdvisedHandler
		extends PPOrderAdvisedOrCreatedHandler<PPOrderAdvisedEvent>
{

	private final PostMaterialEventService materialEventService;

	/**
	 * @param materialEventService needed in case we directly request a {@link PPOrderAdvisedEvent}'s proposed PP_Order to be created.
	 */
	public PPOrderAdvisedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final PostMaterialEventService materialEventService)
	{
		super(candidateChangeHandler, candidateRepositoryRetrieval);

		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<Class<? extends PPOrderAdvisedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PPOrderAdvisedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PPOrderAdvisedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final PPOrderAdvisedEvent event)
	{
		final MaterialDispoGroupId groupId = handleAbstractPPOrderEvent(event); // creates on the supply-candidate

		if (event.isDirectlyCreatePPOrder())
		{
			final PPOrder ppOrderWithGroupId = event.getPpOrder()
					.toBuilder()
					.materialDispoGroupId(groupId) // without it we won't be able to assign the new PPOrder to the candidate that we just made
					.build();

			final PPOrderRequestedEvent ppOrderRequestEvent = PPOrderRequestedEvent
					.builder()
					.eventDescriptor(event.getEventDescriptor())
					.dateOrdered(SystemTime.asInstant())
					.ppOrder(ppOrderWithGroupId)
					.build();
			materialEventService.postEventNow(ppOrderRequestEvent);
		}
	}

	@Override
	protected CandidatesQuery createPreExistingSupplyCandidateQuery(@NonNull final AbstractPPOrderEvent abstractPPOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = PPOrderAdvisedEvent.cast(abstractPPOrderEvent);
		if(!ppOrderAdvisedEvent.isTryUpdateExistingCandidate())
		{
			return CandidatesQuery.FALSE;
		}

		final SupplyRequiredDescriptor supplyRequiredDescriptor = ppOrderAdvisedEvent.getSupplyRequiredDescriptor();

		final CandidateId supplyCandidateId = CandidateId.ofRepoIdOrNull(supplyRequiredDescriptor.getSupplyCandidateId());
		if (supplyCandidateId != null)
		{ // the original request already contained an existing supply-candidate's ID that we need to update now.
			return CandidatesQuery.fromId(supplyCandidateId);
		}

		final PPOrder ppOrder = ppOrderAdvisedEvent.getPpOrder();

		final DemandDetail demandDetail = DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofDemandDetail(demandDetail);

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.productPlanningId(ppOrder.getProductPlanningId())
				.build();

		return CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.demandDetailsQuery(demandDetailsQuery)
				.productionDetailsQuery(productionDetailsQuery)
				.build();
	}

	@Override
	protected Flag extractIsAdviseEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		return Flag.TRUE;
	}

	@Override
	protected Flag extractIsDirectlyPickSupply(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = PPOrderAdvisedEvent.cast(ppOrderEvent);
		return Flag.of(ppOrderAdvisedEvent.isDirectlyPickSupply());
	}
}
