package de.metas.material.dispo.service.event.handler.pporder;

import java.util.Collection;

import org.adempiere.util.Check;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderLine;
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
public final class PPOrderAdvisedHandler
		extends PPOrderAdvisedOrCreatedHandler<PPOrderAdvisedEvent>
{

	private final RequestMaterialOrderService requestMaterialOrderService;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PPOrderAdvisedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final RequestMaterialOrderService requestMaterialOrderService)
	{
		super(candidateChangeHandler, candidateRepositoryRetrieval);

		this.requestMaterialOrderService = requestMaterialOrderService;
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
		final int groupId = handleAbstractPPOrderEvent(event);

		if (event.isDirectlyCreatePPOrder())
		{
			requestMaterialOrderService.requestMaterialOrder(groupId);
		}
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(
			@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = cast(ppOrderEvent);

		final DemandDetail demandDetail = //
				DemandDetail.forSupplyRequiredDescriptorOrNull(ppOrderEvent.getSupplyRequiredDescriptor());
		Check.errorIf(demandDetail == null, "Missing demandDetail for ppOrderAdvisedEvent={}", ppOrderAdvisedEvent);

		final PPOrder ppOrder = ppOrderAdvisedEvent.getPpOrder();
		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.productPlanningId(ppOrder.getProductPlanningId())
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.demandDetail(demandDetail)
				.productionDetailsQuery(productionDetailsQuery)
				.build();

		return query;
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(
			@NonNull final PPOrderLine ppOrderLine,
			@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = cast(ppOrderEvent);

		final DemandDetail demandDetail = //
				DemandDetail.forSupplyRequiredDescriptorOrNull(ppOrderEvent.getSupplyRequiredDescriptor());
		Check.errorIf(demandDetail == null, "Missing demandDetail for ppOrderAdvisedEvent={}", ppOrderAdvisedEvent);

		final PPOrder ppOrder = ppOrderAdvisedEvent.getPpOrder();
		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.productPlanningId(ppOrder.getProductPlanningId())
				.productBomLineId(ppOrderLine.getProductBomLineId())
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(extractCandidateType(ppOrderLine))
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.demandDetail(demandDetail)
				.productionDetailsQuery(productionDetailsQuery)
				.build();

		return query;
	}

	@Override
	protected Flag extractIsAdviseEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		return Flag.TRUE;
	}

	@Override
	protected Flag extractIsDirectlyPickSupply(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = cast(ppOrderEvent);
		return Flag.of(ppOrderAdvisedEvent.isDirectlyPickSupply());
	}

	private PPOrderAdvisedEvent cast(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrderAdvisedEvent ppOrderAdvisedEvent = (PPOrderAdvisedEvent)ppOrderEvent;
		return ppOrderAdvisedEvent;
	}
}
