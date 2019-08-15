package de.metas.material.dispo.service.event.handler.pporder;

import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail.ProductionDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
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

abstract class PPOrderAdvisedOrCreatedHandler<T extends AbstractPPOrderEvent> implements MaterialEventHandler<T>
{
	private final CandidateChangeService candidateChangeService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	/**
	 *
	 * @param candidateChangeService
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	PPOrderAdvisedOrCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	/**
	 * @return candidateGroupId
	 */
	protected final MaterialDispoGroupId handleAbstractPPOrderEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final Candidate headerCandidate = createHeaderCandidate(ppOrderEvent);

		final PPOrder ppOrder = ppOrderEvent.getPpOrder();
		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final Candidate lineCandidate = createLineCandidate(
					ppOrderEvent,
					ppOrderLine,
					headerCandidate.getGroupId(),
					headerCandidate.getSeqNo());
			candidateChangeService.onCandidateNewOrChange(lineCandidate);
		}

		return headerCandidate.getGroupId();
	}

	private Candidate createHeaderCandidate(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();
		final SupplyRequiredDescriptor supplyRequiredDescriptor = ppOrderEvent.getSupplyRequiredDescriptor();

		// final CandidateStatus candidateStatus = getCandidateStatus(ppOrder);

		final CandidatesQuery preExistingSupplyQuery = createPreExistingCandidatesQuery(ppOrder, supplyRequiredDescriptor);
		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		final CandidateBuilder builder = existingCandidateOrNull != null
				? existingCandidateOrNull.toBuilder()
				: Candidate.builderForClientAndOrgId(ppOrder.getClientAndOrgId());

		final ProductionDetail headerCandidateProductionDetail = createProductionDetailForPPOrder(
				ppOrderEvent,
				existingCandidateOrNull);
		final MaterialDescriptor headerCandidateMaterialDescriptor = createMaterialDescriptorForPPOrder(ppOrder);
		final DemandDetail headerCandidateDemandDetail = computeDemandDetailOrNull(
				CandidateType.SUPPLY,
				supplyRequiredDescriptor,
				headerCandidateMaterialDescriptor);

		final Candidate headerCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				// .status(candidateStatus)
				.businessCaseDetail(headerCandidateProductionDetail)
				.additionalDemandDetail(headerCandidateDemandDetail)
				.materialDescriptor(headerCandidateMaterialDescriptor)
				.build();

		final Candidate headerCandidateWithGroupId = candidateChangeService.onCandidateNewOrChange(headerCandidate);
		return headerCandidateWithGroupId;
	}

	private Candidate createLineCandidate(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			@NonNull final PPOrderLine ppOrderLine,
			final MaterialDispoGroupId candidateGroupId,
			final int headerCandidateSeqNo)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();
		final SupplyRequiredDescriptor supplyRequiredDescriptor = ppOrderEvent.getSupplyRequiredDescriptor();

		final CandidatesQuery candidatesQuery = createPreExistingCandidatesQuery(ppOrder, ppOrderLine, supplyRequiredDescriptor);
		final Candidate existingLineCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(candidatesQuery);

		final CandidateBuilder candidateBuilder = existingLineCandidate != null
				? existingLineCandidate.toBuilder()
				: Candidate.builderForClientAndOrgId(ppOrder.getClientAndOrgId());

		final CandidateType candidateType = PPOrderHandlerUtils.extractCandidateType(ppOrderLine);
		final MaterialDescriptor materialDescriptor = createMaterialDescriptorForPPOrderLine(ppOrderLine, ppOrder);
		final DemandDetail lineCandidateDemandDetail = computeDemandDetailOrNull(
				candidateType,
				supplyRequiredDescriptor,
				materialDescriptor);
		final ProductionDetail productionDetail = createProductionDetailForPPOrderLine(ppOrderEvent, ppOrderLine);

		candidateBuilder
				.type(candidateType)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				// .status(candidateStatus)
				.groupId(candidateGroupId)
				.seqNo(headerCandidateSeqNo + 1)
				.businessCaseDetail(productionDetail)
				.additionalDemandDetail(lineCandidateDemandDetail)
				.materialDescriptor(materialDescriptor);

		// in case of CandidateType.DEMAND this might trigger further demand events

		//
		return candidateBuilder.build();
	}

	/**
	 * Creates and returns a {@link DemandDetail} for the given {@code supplyRequiredDescriptor},
	 * if the respective candidate should have one.
	 * Supply candidates that are about *another* product that the required one (i.e. co- and by-products) may not have that demand detail.
	 * (Otherwise, their stock candidate would be connected to the resp. demand record)
	 */
	private static DemandDetail computeDemandDetailOrNull(
			@NonNull final CandidateType lineCandidateType,
			@Nullable final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		if (supplyRequiredDescriptor == null)
		{
			return null;
		}

		if (lineCandidateType == CandidateType.DEMAND)
		{
			return DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		}

		final MaterialDescriptor requiredMaterialDescriptor = supplyRequiredDescriptor.getMaterialDescriptor();
		if (lineCandidateType == CandidateType.SUPPLY
				&& requiredMaterialDescriptor.getProductId() == materialDescriptor.getProductId()
				&& requiredMaterialDescriptor.getStorageAttributesKey().equals(materialDescriptor.getStorageAttributesKey()))
		{
			return DemandDetail.forSupplyRequiredDescriptor(supplyRequiredDescriptor);
		}

		return null;
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			PPOrder ppOrder,
			@Nullable SupplyRequiredDescriptor supplyRequiredDescriptor);

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			PPOrder ppOrder,
			PPOrderLine ppOrderLine,
			@Nullable SupplyRequiredDescriptor supplyRequiredDescriptor);

	private static MaterialDescriptor createMaterialDescriptorForPPOrder(final PPOrder ppOrder)
	{
		return MaterialDescriptor.builder()
				.date(ppOrder.getDatePromised())
				.productDescriptor(ppOrder.getProductDescriptor())
				.quantity(ppOrder.getQtyOpen())
				.warehouseId(ppOrder.getWarehouseId())
				// .customerId(ppOrder.getBPartnerId()) not 100% sure if the ppOrder's bPartner is the customer this is made for
				.build();
	}

	private static MaterialDescriptor createMaterialDescriptorForPPOrderLine(
			@NonNull final PPOrderLine ppOrderLine,
			@NonNull final PPOrder ppOrder)
	{
		return MaterialDescriptor.builder()
				.date(ppOrderLine.getIssueOrReceiveDate())
				.productDescriptor(ppOrderLine.getProductDescriptor())
				.quantity(ppOrderLine.getQtyOpenNegateIfReceipt())
				.warehouseId(ppOrder.getWarehouseId())
				// .customerId(ppOrder.getBPartnerId()) not 100% sure if the ppOrder's bPartner is the customer this is made for
				.build();
	}

	// private static CandidateStatus getCandidateStatus(@NonNull final PPOrder ppOrder)
	// {
	// final CandidateStatus candidateStatus;
	// final String docStatus = ppOrder.getDocStatus();
	//
	// if (ppOrder.getPpOrderId() <= 0)
	// {
	// candidateStatus = CandidateStatus.doc_planned;
	// }
	// else
	// {
	// candidateStatus = EventUtil.getCandidateStatus(docStatus);
	// }
	// return candidateStatus;
	// }

	private ProductionDetail createProductionDetailForPPOrder(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			@Nullable final Candidate existingCandidateOrNull)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		return prepareProductionDetail(existingCandidateOrNull)
				.advised(extractIsAdviseEvent(ppOrderEvent))
				.pickDirectlyIfFeasible(extractIsDirectlyPickSupply(ppOrderEvent))
				.qty(ppOrder.getQtyRequired())
				.plantId(ppOrder.getPlantId())
				.productPlanningId(ppOrder.getProductPlanningId())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.build();
	}

	/**
	 * @return initial builder with existing values, or create a new one
	 */
	private static ProductionDetailBuilder prepareProductionDetail(@Nullable final Candidate existingCandidateOrNull)
	{
		return Optional.ofNullable(existingCandidateOrNull)
				.map(Candidate::getBusinessCaseDetail)
				.map(ProductionDetail::cast)
				.map(ProductionDetail::toBuilder)
				.orElse(ProductionDetail.builder());
	}

	private ProductionDetail createProductionDetailForPPOrderLine(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			@NonNull final PPOrderLine ppOrderLine)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		return ProductionDetail.builder()
				.advised(extractIsAdviseEvent(ppOrderEvent))
				.pickDirectlyIfFeasible(extractIsDirectlyPickSupply(ppOrderEvent))
				.plantId(ppOrder.getPlantId())
				.qty(ppOrderLine.getQtyRequired())
				.productPlanningId(ppOrder.getProductPlanningId())
				.productBomLineId(ppOrderLine.getProductBomLineId())
				.description(ppOrderLine.getDescription())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.ppOrderLineId(ppOrderLine.getPpOrderLineId())
				.build();
	}

	protected abstract Flag extractIsAdviseEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent);

	protected abstract Flag extractIsDirectlyPickSupply(@NonNull final AbstractPPOrderEvent ppOrderEvent);
}
