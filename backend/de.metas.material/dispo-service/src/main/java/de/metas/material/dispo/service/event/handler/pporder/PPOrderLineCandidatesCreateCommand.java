package de.metas.material.dispo.service.event.handler.pporder;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.Builder;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2019 metas GmbH
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

/**
 * Creates (or updates) {@link Candidate}s for each {@link PPOrderLine}.
 */
final class PPOrderLineCandidatesCreateCommand
{
	// services
	private final CandidateChangeService candidateChangeService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private final PPOrder ppOrder;
	private final DemandDetail headerDemandDetail;
	private final MaterialDispoGroupId groupId;
	private final int headerCandidateSeqNo;
	private final Flag advised;
	private final Flag pickDirectlyIfFeasible;

	@Builder(buildMethodName = "_build")
	private PPOrderLineCandidatesCreateCommand(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			//
			@NonNull final PPOrder ppOrder,
			@Nullable final DemandDetail headerDemandDetail,
			@NonNull final MaterialDispoGroupId groupId,
			@NonNull final Integer headerCandidateSeqNo,
			@NonNull final Flag advised,
			@NonNull final Flag pickDirectlyIfFeasible)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;

		this.ppOrder = ppOrder;
		this.headerDemandDetail = headerDemandDetail;

		this.groupId = groupId;
		this.headerCandidateSeqNo = headerCandidateSeqNo;
		this.advised = advised;
		this.pickDirectlyIfFeasible = pickDirectlyIfFeasible;
	}

	public static class PPOrderLineCandidatesCreateCommandBuilder
	{
		public void create()
		{
			_build().create();
		}
	}

	public void create()
	{
		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final Candidate lineCandidate = createLineCandidate(ppOrderLine);
			candidateChangeService.onCandidateNewOrChange(lineCandidate);
		}
	}

	private Candidate createLineCandidate(@NonNull final PPOrderLine ppOrderLine)
	{
		final CandidatesQuery candidatesQuery = createPreExistingCandidatesQuery(ppOrder, ppOrderLine);
		final Candidate existingLineCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(candidatesQuery);

		final CandidateBuilder candidateBuilder = existingLineCandidate != null
				? existingLineCandidate.toBuilder()
				: Candidate.builderForClientAndOrgId(ppOrder.getPpOrderData().getClientAndOrgId());

		final CandidateType candidateType = PPOrderHandlerUtils.extractCandidateType(ppOrderLine);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(ppOrderLine);
		final DemandDetail lineCandidateDemandDetail = headerDemandDetail;

		final ProductionDetail productionDetail = createProductionDetail(ppOrderLine);

		candidateBuilder
				.type(candidateType)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.groupId(groupId)
				.seqNo(headerCandidateSeqNo + 1)
				.businessCaseDetail(productionDetail)
				.additionalDemandDetail(lineCandidateDemandDetail)
				.materialDescriptor(materialDescriptor);

		// in case of CandidateType.DEMAND this might trigger further demand events

		return candidateBuilder.build();
	}

	private static CandidatesQuery createPreExistingCandidatesQuery(
			@NonNull final PPOrder ppOrder,
			@NonNull final PPOrderLine ppOrderLine)
	{
		final MaterialDispoGroupId groupId = ppOrder.getPpOrderData().getMaterialDispoGroupId();
		if (groupId == null)
		{
			// return false, but don't write another log message; we already logged in the other createQuery() method
			return CandidatesQuery.FALSE;
		}

		return CandidatesQuery.builder()
				.type(PPOrderHandlerUtils.extractCandidateType(ppOrderLine))
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.groupId(groupId)
				.materialDescriptorQuery(PPOrderHandlerUtils.createMaterialDescriptorQuery(ppOrderLine.getPpOrderLineData().getProductDescriptor()))
				.build();
	}

	private MaterialDescriptor createMaterialDescriptor(@NonNull final PPOrderLine ppOrderLine)
	{
		return MaterialDescriptor.builder()
				.date(ppOrderLine.getPpOrderLineData().getIssueOrReceiveDate())
				.productDescriptor(ppOrderLine.getPpOrderLineData().getProductDescriptor())
				.quantity(ppOrderLine.getPpOrderLineData().getQtyOpenNegateIfReceipt())
				.warehouseId(ppOrder.getPpOrderData().getWarehouseId())
				// .customerId(ppOrder.getBPartnerId()) not 100% sure if the ppOrder's bPartner is the customer this is made for
				.build();
	}

	private ProductionDetail createProductionDetail(@NonNull final PPOrderLine ppOrderLine)
	{
		return ProductionDetail.builder()
				.advised(advised)
				.pickDirectlyIfFeasible(pickDirectlyIfFeasible)
				.plantId(ppOrder.getPpOrderData().getPlantId())
				.workstationId(ppOrder.getPpOrderData().getWorkstationId())
				.qty(ppOrderLine.getPpOrderLineData().getQtyRequired())
				.productPlanningId(ppOrder.getPpOrderData().getProductPlanningId())
				.productBomLineId(ppOrderLine.getPpOrderLineData().getProductBomLineId())
				.description(ppOrderLine.getPpOrderLineData().getDescription())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.ppOrderLineId(ppOrderLine.getPpOrderLineId())
				.build();
	}

}
