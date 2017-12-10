package de.metas.material.dispo.service.event.handler;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
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
public class PPOrderAdvisedOrCreatedHandler
{
	private final CandidateChangeService candidateChangeHandler;
	private final RequestMaterialOrderService requestMaterialOrderService;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PPOrderAdvisedOrCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final RequestMaterialOrderService candidateService)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.requestMaterialOrderService = candidateService;
	}

	public void handlePPOrderAdvisedOrCreatedEvent(final PPOrderAdvisedOrCreatedEvent productionAdvisedEvent)
	{
		final PPOrder ppOrder = productionAdvisedEvent.getPpOrder();

		final CandidateStatus candidateStatus = getCandidateStatus(ppOrder);

		final DemandDetail demandDetailOrNull = DemandDetail.createOrNull(
				productionAdvisedEvent.getSupplyRequiredDescriptor());

		final Candidate supplyCandidate = Candidate.builderForEventDescr(productionAdvisedEvent.getEventDescriptor())
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.status(candidateStatus)
				.productionDetail(createProductionDetailForPPOrder(ppOrder))
				.demandDetail(demandDetailOrNull)
				.materialDescriptor(createMaterialDescriptorFromPpOrder(ppOrder))
				.build();

		final Candidate candidateWithGroupId = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);

		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final CandidateBuilder builder = Candidate.builderForEventDescr(productionAdvisedEvent.getEventDescriptor())
					.type(ppOrderLine.isReceipt() ? CandidateType.SUPPLY : CandidateType.DEMAND)
					.businessCase(CandidateBusinessCase.PRODUCTION)
					.status(candidateStatus)
					.groupId(candidateWithGroupId.getGroupId())
					.seqNo(candidateWithGroupId.getSeqNo() + 1)
					.materialDescriptor(createMaterialDescriptorForPpOrderAndLine(ppOrder, ppOrderLine))
					.demandDetail(demandDetailOrNull)
					.productionDetail(createProductionDetailForPPOrderAndLine(ppOrder, ppOrderLine));

			// in case of CandidateType.DEMAND this might trigger further demand events
			candidateChangeHandler.onCandidateNewOrChange(builder.build());
		}

		if (ppOrder.isAdvisedToCreatePPOrder())
		{
			requestMaterialOrderService.requestMaterialOrder(candidateWithGroupId.getGroupId());
		}
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

	private static MaterialDescriptor createMaterialDescriptorForPpOrderAndLine(final PPOrder ppOrder, final PPOrderLine ppOrderLine)
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(ppOrderLine.isReceipt() ? ppOrder.getDatePromised() : ppOrder.getDateStartSchedule())
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

	private static ProductionDetail createProductionDetailForPPOrder(@NonNull final PPOrder ppOrder)
	{
		final ProductionDetail productionCandidateDetail = ProductionDetail.builder()
				.plantId(ppOrder.getPlantId())
				.productPlanningId(ppOrder.getProductPlanningId())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.uomId(ppOrder.getUomId())
				.build();
		return productionCandidateDetail;
	}

	private static ProductionDetail createProductionDetailForPPOrderAndLine(
			@NonNull final PPOrder ppOrder,
			@NonNull final PPOrderLine ppOrderLine)
	{
		return ProductionDetail.builder()
				.plantId(ppOrder.getPlantId())
				.productPlanningId(ppOrder.getProductPlanningId())
				.productBomLineId(ppOrderLine.getProductBomLineId())
				.description(ppOrderLine.getDescription())
				.ppOrderId(ppOrder.getPpOrderId())
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.ppOrderLineId(ppOrderLine.getPpOrderLineId())
				.build();
	}
}
