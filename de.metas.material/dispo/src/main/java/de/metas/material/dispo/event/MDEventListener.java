package de.metas.material.dispo.event;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateChangeHandler;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.ProductionCandidateDetail;
import de.metas.material.dispo.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ReceiptScheduleEvent;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.TransactionEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
public class MDEventListener implements MaterialEventListener
{

	private final CandidateChangeHandler candidateChangeHandler;

	private final CandidateRepository candidateRepository;

	private final SupplyProposalEvaluator supplyProposalEvaluator;

	public MDEventListener(@NonNull final CandidateChangeHandler candidateChangeHandler,
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		if (event instanceof TransactionEvent)
		{
			handleTransactionEvent((TransactionEvent)event);
		}
		else if (event instanceof ReceiptScheduleEvent)
		{
			handleReceiptScheduleEvent((ReceiptScheduleEvent)event);
		}
		else if (event instanceof ShipmentScheduleEvent)
		{
			handleShipmentScheduleEvent((ShipmentScheduleEvent)event);
		}
		else if (event instanceof DistributionPlanEvent)
		{
			handleDistributionPlanEvent((DistributionPlanEvent)event);
		}
		else if (event instanceof ProductionPlanEvent)
		{
			handleProductionPlanEvent((ProductionPlanEvent)event);
		}
	}

	private void handleProductionPlanEvent(final ProductionPlanEvent event)
	{
		final PPOrder ppOrder = event.getPpOrder();

		// ppOrder.getProductBomUomId(); // TODO

		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.PRODUCTION)
				.date(ppOrder.getDatePromised())
				.orgId(ppOrder.getOrgId())
				.productId(ppOrder.getProductId())

				.quantity(ppOrder.getQuantity())
				.warehouseId(ppOrder.getWarehouseId())
				.reference(event.getReference())
				.productionDetail(ProductionCandidateDetail.builder()
						.plantId(ppOrder.getPlantId())
						.productPlanningId(ppOrder.getProductPlanningId())
						.build())
				.build();

		// this might cause 'candidateChangeHandler' to trigger another event
		final Candidate candidateWithGroupId = candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);

		for (final PPOrderLine ppOrderLine : ppOrder.getLines())
		{
			final Candidate lineCandidate = Candidate.builder()
					.type(ppOrderLine.isReceipt() ? Type.SUPPLY : Type.DEMAND)
					.subType(SubType.PRODUCTION)

					.groupId(candidateWithGroupId.getGroupId())
					.seqNo(candidateWithGroupId.getSeqNo() + 1)

					.date(ppOrderLine.isReceipt() ? ppOrder.getDatePromised() : ppOrder.getDateStartSchedule())
					.orgId(ppOrder.getOrgId())
					.productId(ppOrderLine.getProductId())
					.attributeSetInstanceId(ppOrderLine.getAttributeSetInstanceId())
					.quantity(ppOrderLine.getQtyRequired())
					.warehouseId(ppOrder.getWarehouseId())
					.reference(event.getReference())
					.productionDetail(ProductionCandidateDetail.builder()
							.productBomLineId(ppOrderLine.getProductBomLineId())
							.description(ppOrderLine.getDescription())
							.build())
					.build();

			candidateChangeHandler.onCandidateNewOrChange(lineCandidate);
		}
	}

	private void handleDistributionPlanEvent(final DistributionPlanEvent event)
	{
		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final SupplyProposal proposal = SupplyProposal.builder()
				.date(event.getDistributionStart())
				.productId(materialDescr.getProductId())
				.destWarehouseId(materialDescr.getWarehouseId())
				.sourceWarehouseId(event.getFromWarehouseId())
				.build();
		if (!supplyProposalEvaluator.evaluateSupply(proposal))
		{
			// 'supplyProposalEvaluator' told us to ignore the given supply candidate.
			// the reason for this could be that it found an already existing distribution plan pointing in the other direction.
			// so instead of playing an infinite game of ping-ping with the material-planning component, it ignored the given 'event'
			// and leave it to the user to come up with a great idea.
			return;
		}

		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.DISTRIBUTION)
				.date(materialDescr.getDate())
				.orgId(materialDescr.getOrgId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.warehouseId(materialDescr.getWarehouseId())
				.reference(event.getReference())
				.build();

		final Candidate supplyCandidateWithId = candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);
		if (supplyCandidateWithId.getQuantity().signum() == 0)
		{
			// nothing was added as supply in the destination warehouse, so there is no demand to register either
			return;
		}

		// we expect the demand candidate to go with the supplyCandidates SeqNo + 1,
		// *but* it might also be the case that the demandCandidate attaches to an existing stock and in that case would need to get another SeqNo
		final int expectedSeqNoForDemandCandidate = supplyCandidateWithId.getSeqNo() + 1;

		final Candidate demandCandidate = supplyCandidate
				.withType(Type.DEMAND)
				.withSubType(SubType.DISTRIBUTION)
				.withGroupId(supplyCandidateWithId.getGroupId())
				.withParentId(supplyCandidateWithId.getId())
				.withQuantity(supplyCandidateWithId.getQuantity()) // what was added as supply in the destination warehouse needs to be registered as demand in the source warehouse
				.withDate(event.getDistributionStart())
				.withSeqNo(expectedSeqNoForDemandCandidate)
				.withWarehouseId(event.getFromWarehouseId());

		// this might cause 'candidateChangeHandler' to trigger another event
		final Candidate demandCandidateWithId = candidateChangeHandler.onDemandCandidateNewOrChange(demandCandidate);
		if (expectedSeqNoForDemandCandidate == demandCandidateWithId.getSeqNo())
		{
			return; // we are done
		}

		// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
		candidateRepository.addOrReplace(supplyCandidateWithId
				.withSeqNo(demandCandidateWithId.getSeqNo() - 1),
				false);

		candidateRepository.addOrReplace(candidateRepository
				.retrieve(supplyCandidateWithId.getParentId())
				.withSeqNo(demandCandidateWithId.getSeqNo() - 2),
				false);
	}

	private void handleTransactionEvent(@NonNull final TransactionEvent event)
	{
		if (event.isTransactionDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.orgId(materialDescr.getOrgId())
				.warehouseId(materialDescr.getWarehouseId())
				.date(materialDescr.getDate())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();

		candidateChangeHandler.updateStock(candidate);
	}

	private void handleReceiptScheduleEvent(@NonNull final ReceiptScheduleEvent event)
	{
		if (event.isReceiptScheduleDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.RECEIPT)
				.orgId(materialDescr.getOrgId())
				.date(materialDescr.getDate())
				.warehouseId(materialDescr.getWarehouseId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();

		candidateChangeHandler.onSupplyCandidateNewOrChange(candidate);
	}

	private void handleShipmentScheduleEvent(@NonNull final ShipmentScheduleEvent event)
	{
		if (event.isShipmentScheduleDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.SHIPMENT)
				.orgId(materialDescr.getOrgId())
				.date(materialDescr.getDate())
				.warehouseId(materialDescr.getWarehouseId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(candidate);
	}
}
