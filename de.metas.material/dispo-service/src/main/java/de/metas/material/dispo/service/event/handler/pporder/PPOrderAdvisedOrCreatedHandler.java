package de.metas.material.dispo.service.event.handler.pporder;

import javax.annotation.Nullable;

import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail.ProductionDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
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
	private final RequestMaterialOrderService requestMaterialOrderService;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PPOrderAdvisedOrCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final RequestMaterialOrderService candidateService)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
		this.requestMaterialOrderService = candidateService;
	}

	protected final void handlePPOrderAdvisedOrCreatedEvent(
			@NonNull final AbstractPPOrderEvent ppOrderEvent,
			final boolean isPPOrderAdvisedEvent)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		final CandidateStatus candidateStatus = getCandidateStatus(ppOrder);

		final DemandDetail demandDetailOrNull = DemandDetail.createOrNull(
				ppOrderEvent.getSupplyRequiredDescriptor());

		final CandidatesQuery preExistingSupplyQuery = createPreExistingCandidatesQuery(ppOrderEvent);
		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		final CandidateBuilder builder = existingCandidateOrNull != null
				? existingCandidateOrNull.toBuilder()
				: Candidate.builderForEventDescr(ppOrderEvent.getEventDescriptor());

		final ProductionDetail newProductionDetailForPPOrder = createProductionDetailForPPOrder(
				ppOrder,
				existingCandidateOrNull,
				isPPOrderAdvisedEvent);
		// note that newProductionDetailForPPOrder.isAdvised() might be != isPPOrderAdvisedEvent

		final Candidate supplyCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.status(candidateStatus)
				.productionDetail(newProductionDetailForPPOrder)
				.demandDetail(demandDetailOrNull)
				.materialDescriptor(createMaterialDescriptorFromPpOrder(ppOrder))
				.build();

		final Candidate candidateWithGroupId = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);

		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final CandidatesQuery lineQuery = createPreExistingCandidatesQuery(ppOrderLine, ppOrderEvent);
			final Candidate existingLineCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(lineQuery);

			final CandidateBuilder lineCandidateBuilder = existingLineCandidate != null
					? existingLineCandidate.toBuilder()
					: Candidate.builderForEventDescr(ppOrderEvent.getEventDescriptor());

			lineCandidateBuilder
					.type(extractCandidateType(ppOrderLine))
					.businessCase(CandidateBusinessCase.PRODUCTION)
					.status(candidateStatus)
					.groupId(candidateWithGroupId.getGroupId())
					.seqNo(candidateWithGroupId.getSeqNo() + 1)
					.materialDescriptor(createMaterialDescriptorForPpOrderAndLine(ppOrder, ppOrderLine))
					.demandDetail(demandDetailOrNull)
					.productionDetail(createProductionDetailForPPOrderAndLine(
							ppOrder,
							ppOrderLine,
							newProductionDetailForPPOrder.isAdvised()));

			// in case of CandidateType.DEMAND this might trigger further demand events
			candidateChangeHandler.onCandidateNewOrChange(lineCandidateBuilder.build());
		}

		if (ppOrder.isAdvisedToCreatePPOrder())
		{
			requestMaterialOrderService.requestMaterialOrder(candidateWithGroupId.getGroupId());
		}
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			AbstractPPOrderEvent ppOrderEvent);

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			PPOrderLine ppOrderLine,
			AbstractPPOrderEvent ppOrderEvent);

	protected CandidateType extractCandidateType(final PPOrderLine ppOrderLine)
	{
		return ppOrderLine.isReceipt() ? CandidateType.SUPPLY : CandidateType.DEMAND;
	}

	private static MaterialDescriptor createMaterialDescriptorFromPpOrder(final PPOrder ppOrder)
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
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(ppOrderLine.getIssueOrReceiveDate())
				.productDescriptor(ppOrderLine.getProductDescriptor())
				.quantity(ppOrderLine.getQtyRequired())
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
			@NonNull final PPOrder ppOrder,
			@Nullable final Candidate existingCandidateOrNull,
			final boolean isPPOrderAdvisedEvent)
	{
		//
		// get our effective builder and advised values, depending on whether a candidate already exists
		final ProductionDetail existingProductionDetailOrNull = existingCandidateOrNull != null
				? existingCandidateOrNull.getProductionDetail()
				: null;

		final ProductionDetailBuilder initialBuilder;
		final boolean effectiveAdvisedValue;
		if (existingProductionDetailOrNull == null)
		{
			initialBuilder = ProductionDetail.builder();
			effectiveAdvisedValue = isPPOrderAdvisedEvent;
		}
		else
		{
			initialBuilder = existingProductionDetailOrNull.toBuilder();
			effectiveAdvisedValue = existingProductionDetailOrNull.isAdvised() || isPPOrderAdvisedEvent; // don't go "back" to false if it was already true
		}

		//
		// build and return the productionDetail
		final ProductionDetail newProductionDetailForPPOrder = initialBuilder
				.advised(effectiveAdvisedValue)
				.plannedQty(ppOrder.getQuantity())
				.plantId(ppOrder.getPlantId())
				.productPlanningId(ppOrder.getProductPlanningId())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.build();
		return newProductionDetailForPPOrder;
	}

	private static ProductionDetail createProductionDetailForPPOrderAndLine(
			@NonNull final PPOrder ppOrder,
			@NonNull final PPOrderLine ppOrderLine,
			final boolean advised)
	{
		return ProductionDetail.builder()
				.advised(advised)
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
}
