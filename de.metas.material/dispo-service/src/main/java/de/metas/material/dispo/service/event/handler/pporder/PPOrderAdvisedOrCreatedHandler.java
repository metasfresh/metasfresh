package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.Optional;

import javax.annotation.Nullable;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.Flag;
import de.metas.material.dispo.commons.candidate.ProductionDetail.ProductionDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderEvent;
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

public abstract class PPOrderAdvisedOrCreatedHandler<T extends AbstractPPOrderEvent>
		implements MaterialEventHandler<T>
{
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PPOrderAdvisedOrCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	protected final int handleAbstractPPOrderEvent(@NonNull final AbstractPPOrderEvent ppOrderEvent)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		final CandidateStatus candidateStatus = getCandidateStatus(ppOrder);

		final SupplyRequiredDescriptor supplyRequiredDescriptor = ppOrderEvent.getSupplyRequiredDescriptor();

		final CandidatesQuery preExistingSupplyQuery = createPreExistingCandidatesQuery(ppOrderEvent);
		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		final CandidateBuilder builder = existingCandidateOrNull != null
				? existingCandidateOrNull.toBuilder()
				: Candidate.builderForEventDescr(ppOrderEvent.getEventDescriptor());

		final ProductionDetail headerCandidateProductionDetail = createProductionDetailForPPOrder(
				ppOrderEvent,
				existingCandidateOrNull);
		final MaterialDescriptor headerCandidateMaterialDescriptor = createMaterialDescriptorForPpOrder(
				ppOrder);
		final DemandDetail headerCandidateDemandDetail = computeDemandDetailOrNull(
				CandidateType.SUPPLY,
				supplyRequiredDescriptor,
				headerCandidateMaterialDescriptor);

		final Candidate headerCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.status(candidateStatus)
				.businessCaseDetail(headerCandidateProductionDetail)
				.additionalDemandDetail(headerCandidateDemandDetail)
				.materialDescriptor(headerCandidateMaterialDescriptor)
				.build();

		final Candidate candidateWithGroupId = candidateChangeHandler.onCandidateNewOrChange(headerCandidate);

		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final CandidatesQuery lineQuery = createPreExistingCandidatesQuery(ppOrderLine, ppOrderEvent);
			final Candidate existingLineCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(lineQuery);

			final CandidateBuilder lineCandidateBuilder = existingLineCandidate != null
					? existingLineCandidate.toBuilder()
					: Candidate.builderForEventDescr(ppOrderEvent.getEventDescriptor());

			final CandidateType lineCandidateType = extractCandidateType(
					ppOrderLine);
			final MaterialDescriptor lineCandidateMaterialDescriptor = createMaterialDescriptorForPpOrderAndLine(
					ppOrder,
					ppOrderLine);
			final DemandDetail lineCandidateDemandDetail = computeDemandDetailOrNull(
					lineCandidateType,
					supplyRequiredDescriptor,
					lineCandidateMaterialDescriptor);
			final ProductionDetail lineCandidateProductionDetail = createProductionDetailForPPOrderAndLine(
					ppOrderEvent,
					ppOrderLine);

			lineCandidateBuilder
					.type(lineCandidateType)
					.businessCase(CandidateBusinessCase.PRODUCTION)
					.status(candidateStatus)
					.groupId(candidateWithGroupId.getGroupId())
					.seqNo(candidateWithGroupId.getSeqNo() + 1)
					.businessCaseDetail(lineCandidateProductionDetail)
					.additionalDemandDetail(lineCandidateDemandDetail)
					.materialDescriptor(lineCandidateMaterialDescriptor);

			// in case of CandidateType.DEMAND this might trigger further demand events
			candidateChangeHandler.onCandidateNewOrChange(lineCandidateBuilder.build());
		}

		return candidateWithGroupId.getGroupId();
	}

	/**
	 * Creates and returns a {@link DemandDetail} for the given {@code supplyRequiredDescriptor},
	 * if the respective candidate should have one.
	 * Supply candidates that are about *another* product that the required one (i.e. co- and by-products) may not have that demand detail.
	 * (Otherwise, their stock candidate would be connected to the resp. demand record)
	 */
	private DemandDetail computeDemandDetailOrNull(
			final CandidateType lineCandidateType,
			final SupplyRequiredDescriptor supplyRequiredDescriptor,
			final MaterialDescriptor materialDescriptor)
	{
		final DemandDetail demandDetail = //
				DemandDetail.forSupplyRequiredDescriptorOrNull(supplyRequiredDescriptor);
		if (demandDetail == null)
		{
			return null;
		}

		if (lineCandidateType == CandidateType.DEMAND)
		{
			return demandDetail;
		}

		final MaterialDescriptor requiredMaterialDescriptor = supplyRequiredDescriptor.getMaterialDescriptor();
		if (lineCandidateType == CandidateType.SUPPLY
				&& requiredMaterialDescriptor.getProductId() == materialDescriptor.getProductId()
				&& requiredMaterialDescriptor.getStorageAttributesKey().equals(materialDescriptor.getStorageAttributesKey()))
		{
			return demandDetail;
		}

		return null;
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			AbstractPPOrderEvent ppOrderEvent);

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			PPOrderLine ppOrderLine,
			AbstractPPOrderEvent ppOrderEvent);

	protected final CandidateType extractCandidateType(final PPOrderLine ppOrderLine)
	{
		return ppOrderLine.isReceipt() ? CandidateType.SUPPLY : CandidateType.DEMAND;
	}

	private static MaterialDescriptor createMaterialDescriptorForPpOrder(final PPOrder ppOrder)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(ppOrder.getDatePromised())
				.productDescriptor(ppOrder.getProductDescriptor())
				.quantity(ppOrder.getQuantity())
				.warehouseId(ppOrder.getWarehouseId())
				.bPartnerId(ppOrder.getBPartnerId())
				.build();
		return materialDescriptor;
	}

	private static MaterialDescriptor createMaterialDescriptorForPpOrderAndLine(
			@NonNull final PPOrder ppOrder,
			@NonNull final PPOrderLine ppOrderLine)
	{
		final BigDecimal quantity = ppOrderLine.getQtyRequired().abs(); // supply-ppOrderLines have negative qtyRequired

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(ppOrderLine.getIssueOrReceiveDate())
				.productDescriptor(ppOrderLine.getProductDescriptor())
				.quantity(quantity)
				.warehouseId(ppOrder.getWarehouseId())
				.bPartnerId(ppOrder.getBPartnerId())
				.build();
		return materialDescriptor;
	}

	private static CandidateStatus getCandidateStatus(@NonNull final PPOrder ppOrder)
	{
		final CandidateStatus candidateStatus;
		final String docStatus = ppOrder.getDocStatus();

		if (ppOrder.getPpOrderId() <= 0)
		{
			candidateStatus = CandidateStatus.doc_planned;
		}
		else
		{
			candidateStatus = EventUtil.getCandidateStatus(docStatus);
		}
		return candidateStatus;
	}

	private ProductionDetail createProductionDetailForPPOrder(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			@Nullable final Candidate existingCandidateOrNull)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		// get our initial builder with existing values, or create a new one
		final ProductionDetailBuilder initialBuilder = Optional.ofNullable(existingCandidateOrNull)
				.map(Candidate::getBusinessCaseDetail)
				.map(ProductionDetail::cast)
				.map(ProductionDetail::toBuilder)
				.orElse(ProductionDetail.builder());

		// build and return the productionDetail
		final ProductionDetail newProductionDetailForPPOrder = initialBuilder
				.advised(extractIsAdviseEvent(ppOrderEvent))
				.pickDirectlyIfFeasible(extractIsDirectlyPickSupply(ppOrderEvent))
				.plannedQty(ppOrder.getQuantity())
				.plantId(ppOrder.getPlantId())
				.productPlanningId(ppOrder.getProductPlanningId())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.build();
		return newProductionDetailForPPOrder;
	}

	private ProductionDetail createProductionDetailForPPOrderAndLine(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			@NonNull final PPOrderLine ppOrderLine)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		return ProductionDetail.builder()
				.advised(extractIsAdviseEvent(ppOrderEvent))
				.pickDirectlyIfFeasible(extractIsDirectlyPickSupply(ppOrderEvent))
				.plantId(ppOrder.getPlantId())
				.plannedQty(ppOrderLine.getQtyRequired())
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
