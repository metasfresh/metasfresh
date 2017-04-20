package de.metas.material.dispo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateChangeHandler;
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
		else if(event instanceof DistributionPlanEvent)
		{
			handleDistributionPlanEvent((DistributionPlanEvent)event);
		}
	}

	private void handleDistributionPlanEvent(DistributionPlanEvent event)
	{
		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.DISTRIBUTION)
				.date(event.getMaterialDescr().getDate())
				.orgId(event.getMaterialDescr().getOrgId())
				.productId(event.getMaterialDescr().getProductId())
				.quantity(event.getMaterialDescr().getQty())
				.reference(event.getReference())
				.build();
		candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);
		
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
