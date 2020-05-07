package de.metas.material.dispo.service.event.handler.ddorder;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
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
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.ddorder.AbstractDDOrderEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class DDOrderAdvisedHandler
		extends DDOrderAdvisedOrCreatedHandler<DDOrderAdvisedEvent>
{
	private final SupplyProposalEvaluator supplyProposalEvaluator;
	private final RequestMaterialOrderService requestMaterialOrderService;

	public DDOrderAdvisedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final RequestMaterialOrderService requestMaterialOrderService)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler);

		this.requestMaterialOrderService = requestMaterialOrderService;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	@Override
	public Collection<Class<? extends DDOrderAdvisedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(DDOrderAdvisedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final DDOrderAdvisedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final DDOrderAdvisedEvent event)
	{
		final DDOrder ddOrder = event.getDdOrder();
		for (final DDOrderLine ddOrderLine : ddOrder.getLines())
		{
			final Date orderLineStartDate = computeDate(event, ddOrderLine, CandidateType.DEMAND);

			final SupplyProposal proposal = SupplyProposal.builder()
					.date(orderLineStartDate)
					.productDescriptor(ddOrderLine.getProductDescriptor())
					// ignoring bpartner for now..not sure if that's good or not..
					.destWarehouseId(event.getToWarehouseId())
					.sourceWarehouseId(event.getFromWarehouseId())
					.build();
			if (!supplyProposalEvaluator.isProposalAccepted(proposal))
			{
				Loggables.get().addLog("proposal was rejected; returning");
				return;
			}
		}

		final Set<Integer> groupIds = handleAbstractDDOrderEvent(event);

		if (!event.isAdvisedToCreateDDrder())
		{
			return;
		}

		for (final int groupId : groupIds)
		{
			requestMaterialOrderService.requestMaterialOrder(groupId);
		}
	}

	@Override
	protected Flag extractIsAdviseEvent(@NonNull final AbstractDDOrderEvent ddOrderEvent)
	{
		return Flag.TRUE;
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final DDOrderLine ddOrderLine,
			@NonNull final CandidateType candidateType)
	{
		final DDOrderAdvisedEvent ddOrderAdvisedEvent = cast(ddOrderEvent);

		final DemandDetail demandDetail = //
				DemandDetail.forSupplyRequiredDescriptorOrNull(ddOrderEvent.getSupplyRequiredDescriptor());
		Check.errorIf(demandDetail == null, "Missing demandDetail for ppOrderAdvisedEvent={}", ddOrderAdvisedEvent);

		final DDOrder ddOrder = ddOrderAdvisedEvent.getDdOrder();
		final DistributionDetailsQuery distributionDetailsQuery = DistributionDetailsQuery.builder()
				.productPlanningId(ddOrder.getProductPlanningId())
				.networkDistributionLineId(ddOrderLine.getNetworkDistributionLineId())
				.build();

		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(ddOrderLine.getProductDescriptor().getProductId())
				.storageAttributesKey(ddOrderLine.getProductDescriptor().getStorageAttributesKey())
				.warehouseId(computeWarehouseId(ddOrderEvent, candidateType))
				.date(computeDate(ddOrderEvent, ddOrderLine, candidateType))
				.build();

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(candidateType)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.demandDetail(demandDetail)
				.materialDescriptorQuery(materialDescriptorQuery)
				.distributionDetailsQuery(distributionDetailsQuery)
				.build();

		return query;
	}

	private DDOrderAdvisedEvent cast(@NonNull final AbstractDDOrderEvent ddOrderEvent)
	{
		final DDOrderAdvisedEvent ddOrderAdvisedEvent = (DDOrderAdvisedEvent)ddOrderEvent;
		return ddOrderAdvisedEvent;
	}
}
