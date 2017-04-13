package de.metas.manufacturing.dispo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.manufacturing.dispo.Candidate;
import de.metas.manufacturing.dispo.Candidate.SubType;
import de.metas.manufacturing.dispo.Candidate.Type;
import de.metas.manufacturing.dispo.CandidateChangeHandler;
import de.metas.manufacturing.event.ManufacturingEvent;
import de.metas.manufacturing.event.ManufacturingEventListener;
import de.metas.manufacturing.event.ReceiptScheduleEvent;
import de.metas.manufacturing.event.ShipmentScheduleEvent;
import de.metas.manufacturing.event.TransactionEvent;
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
public class MDEventListener implements ManufacturingEventListener
{

	@Autowired
	private CandidateChangeHandler candidateChangeHandler;

	@Override
	public void onEvent(@NonNull final ManufacturingEvent event)
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
	}

	private void handleTransactionEvent(@NonNull final TransactionEvent event)
	{
		if (event.isTransactionDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}
		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.warehouseId(event.getWarehouseId())
				.date(event.getMovementDate())
				.productId(event.getProductId())
				.quantity(event.getQty())
				.referencedRecord(event.getReference())
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

		final Candidate candidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.RECEIPT)
				.date(event.getPromisedDate())
				.warehouseId(event.getWarehouseId())
				.productId(event.getProductId())
				.quantity(event.getQtyOrdered())
				.referencedRecord(event.getReference())
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
		final Candidate candidate = Candidate.builder()
				.type(Type.DEMAND)
				.date(event.getPreparationDate())
				.warehouseId(event.getWarehouseId())
				.productId(event.getProductId())
				.quantity(event.getQtyOrdered())
				.referencedRecord(event.getReference())
				.build();
		candidateChangeHandler.onDemandCandidateNewOrChange(candidate);
	}
}
