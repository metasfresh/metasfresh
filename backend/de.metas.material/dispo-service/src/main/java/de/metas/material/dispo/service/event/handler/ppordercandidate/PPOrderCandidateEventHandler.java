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

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler;
import de.metas.material.dispo.service.event.handler.pporder.PPOrderHandlerUtils;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderCandidateEvent;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderCandidateUpdatedEvent;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class PPOrderCandidateEventHandler
{
	private final CandidateChangeService candidateChangeService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public PPOrderCandidateEventHandler(final CandidateChangeService candidateChangeService, final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@NonNull
	protected Candidate createHeaderCandidate(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@NonNull final CandidatesQuery preExistingSupplyQuery)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final PPOrderCandidate ppOrderCandidate = event.getPpOrderCandidate();

		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		final Candidate.CandidateBuilder builder = existingCandidateOrNull != null
				? existingCandidateOrNull.toBuilder()
				: Candidate.builderForClientAndOrgId(ppOrderCandidate.getPpOrderData().getClientAndOrgId());

		final ProductionDetail headerCandidateProductionDetail = createProductionDetailForPPOrderCandidate(event, existingCandidateOrNull);

		final MaterialDescriptor headerCandidateMaterialDescriptor = createMaterialDescriptorForPPOrderCandidate(ppOrderCandidate);

		final DemandDetail headerCandidateDemandDetail = PPOrderHandlerUtils.computeDemandDetailOrNull(
				CandidateType.SUPPLY,
				supplyRequiredDescriptor,
				headerCandidateMaterialDescriptor);

		final Candidate headerCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.businessCaseDetail(headerCandidateProductionDetail)
				.additionalDemandDetail(headerCandidateDemandDetail)
				.materialDescriptor(headerCandidateMaterialDescriptor)
				// .groupId(null) // will be set after save
				.build();

		final boolean attemptUpdate = !CandidatesQuery.FALSE.equals(preExistingSupplyQuery);

		return candidateChangeService.onCandidateNewOrChange(
				headerCandidate,
				CandidateHandler.OnNewOrChangeAdvise.attemptUpdate(attemptUpdate));
	}

	@NonNull
	private ProductionDetail createProductionDetailForPPOrderCandidate(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@Nullable final Candidate existingCandidateOrNull)
	{
		final ProductionDetail.ProductionDetailBuilder productionDetailBuilder = prepareProductionDetail(existingCandidateOrNull);

		if (event instanceof PPOrderCandidateAdvisedEvent)
		{
			productionDetailBuilder.pickDirectlyIfFeasible(Flag.of(PPOrderCandidateAdvisedEvent.cast(event).isDirectlyPickIfFeasible()));
			productionDetailBuilder.advised(Flag.of(true));
		}
		else if (event instanceof PPOrderCandidateUpdatedEvent)
		{
			productionDetailBuilder.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE);
			productionDetailBuilder.advised(Flag.of(false));
		}
		else
		{
			productionDetailBuilder.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE);
			productionDetailBuilder.advised(Flag.of(false));
		}

		final PPOrderCandidate ppOrderCandidate = event.getPpOrderCandidate();

		return productionDetailBuilder
				.qty(ppOrderCandidate.getPpOrderData().getQtyOpen())
				.plantId(ppOrderCandidate.getPpOrderData().getPlantId())
				.productPlanningId(ppOrderCandidate.getPpOrderData().getProductPlanningId())
				.ppOrderCandidateId(ppOrderCandidate.getPpOrderCandidateId())
				.build();
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptorForPPOrderCandidate(final PPOrderCandidate ppOrderCandidate)
	{
		return MaterialDescriptor.builder()
				.date(ppOrderCandidate.getPpOrderData().getDatePromised())
				.productDescriptor(ppOrderCandidate.getPpOrderData().getProductDescriptor())
				.quantity(ppOrderCandidate.getPpOrderData().getQtyOpen())
				.warehouseId(ppOrderCandidate.getPpOrderData().getWarehouseId())
				.build();
	}

	@NonNull
	private ProductionDetail.ProductionDetailBuilder prepareProductionDetail(@Nullable final Candidate existingCandidateOrNull)
	{
		return Optional.ofNullable(existingCandidateOrNull)
				.map(Candidate::getBusinessCaseDetail)
				.map(ProductionDetail::cast)
				.map(ProductionDetail::toBuilder)
				.orElse(ProductionDetail.builder());
	}
}
