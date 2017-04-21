package de.metas.material.dispo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateChangeHandler;
import de.metas.material.dispo.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ReceiptScheduleEvent;
import de.metas.material.event.ShipmentScheduleEvent;
import de.metas.material.event.TransactionEvent;
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

	@Autowired
	private CandidateChangeHandler candidateChangeHandler;

	@Autowired
	private SupplyProposalEvaluator supplyProposalEvaluator;

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
	}

	private void handleDistributionPlanEvent(DistributionPlanEvent event)
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

		final Candidate demandCandidate = supplyCandidate
				.withType(Type.DEMAND)
				.withSubType(null)
				.withParentId(supplyCandidateWithId.getId())
				.withQuantity(supplyCandidateWithId.getQuantity()) // what was added as supply in the destination warehouse needs to be registered as demand in the source warehouse
				.withDate(event.getDistributionStart())
				.withWarehouseId(event.getFromWarehouseId());

		// this might cause 'candidateChangeHandler' to trigger another event
		candidateChangeHandler.onDemandCandidateNewOrChange(demandCandidate);
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
