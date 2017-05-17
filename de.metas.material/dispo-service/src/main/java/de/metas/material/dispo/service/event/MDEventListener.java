package de.metas.material.dispo.service.event;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.service.CandidateChangeHandler;
import de.metas.material.dispo.DemandCandidateDetail;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.ProductionPlanEvent;
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
@Lazy
public class MDEventListener implements MaterialEventListener
{

	private final CandidateChangeHandler candidateChangeHandler;

	private final ProductionPlanEventHandler productionPlanEventHandler;

	private DistributionPlanEventHandler distributionPlanEventHandler;

	public MDEventListener(
			@NonNull final CandidateChangeHandler candidateChangeHandler,
			@NonNull final DistributionPlanEventHandler distributionPlanEventHandler,
			@NonNull final ProductionPlanEventHandler productionPlanEventHandler)
	{
		this.distributionPlanEventHandler = distributionPlanEventHandler;
		this.productionPlanEventHandler = productionPlanEventHandler;
		this.candidateChangeHandler = candidateChangeHandler;
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
			distributionPlanEventHandler.handleDistributionPlanEvent((DistributionPlanEvent)event);
		}
		else if (event instanceof ProductionPlanEvent)
		{
			productionPlanEventHandler.handleProductionPlanEvent((ProductionPlanEvent)event);
		}
	}

	private void handleTransactionEvent(@NonNull final TransactionEvent event)
	{
		if (event.isTransactionDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final EventDescr eventDescr = event.getEventDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId())
				.warehouseId(materialDescr.getWarehouseId())
				.date(materialDescr.getDate())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();

		candidateChangeHandler.addOrUpdateStock(candidate);
	}

	private void handleReceiptScheduleEvent(@NonNull final ReceiptScheduleEvent event)
	{
		if (event.isReceiptScheduleDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final EventDescr eventDescr = event.getEventDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.RECEIPT)
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId())
				.date(materialDescr.getDate())
				.warehouseId(materialDescr.getWarehouseId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();

		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}

	private void handleShipmentScheduleEvent(@NonNull final ShipmentScheduleEvent event)
	{
		if (event.isShipmentScheduleDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final EventDescr eventDescr = event.getEventDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.subType(SubType.SHIPMENT)
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId())
				.date(materialDescr.getDate())
				.warehouseId(materialDescr.getWarehouseId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.demandDetail(DemandCandidateDetail.builder()
						.orderLineId(event.getOrderLineId())
						.build())
				.build();
		candidateChangeHandler.onCandidateNewOrChange(candidate);
	}
}
