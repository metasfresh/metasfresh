package de.metas.material.cockpit;

import java.util.ArrayList;
import java.util.List;

import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.material.cockpit.DataUpdateRequest.DataUpdateRequestBuilder;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.procurement.AbstractPurchaseOfferEvent;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.stock.OnHandQuantityChangedEvent;
import de.metas.material.event.stockestimate.AbstractStockEstimateEvent;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
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
// needs to be lazy because somewhere down the road, MaterialEventService wants to get something from AD_SystemConfig
// ..and that means the DB has to be there..
// without lazy, it might just get the PlainSysConfigDAO
@Lazy
public class MaterialCockpitEventListener implements MaterialEventListener
{
	private final DataUpdateRequestHandler dataUpdateRequestHandler;

	public MaterialCockpitEventListener(
			@NonNull final MaterialEventService materialEventService,
			@NonNull final DataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;
	}

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		final List<DataUpdateRequest> requests = new ArrayList<>();

		// else if (event instanceof DDOrderAdvisedOrCreatedEvent)
		// {
		// we currently don't care for "warehouse/locator" changes, so: nothing to do
		// }

		// ddOrder
		if (event instanceof PPOrderAdvisedOrCreatedEvent)
		{
			requests.addAll(createDataUpdateRequestForEvent((PPOrderAdvisedOrCreatedEvent)event));
			// if not "planned", but "done", then *DO NOT, because the old impl doesn't either*:
			// * "undo" the former change, i.e. subtract on the "supply" side, add on the "demand" side
			// * update things similar to transactionEvent
		}

		else if (event instanceof AbstractReceiptScheduleEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractReceiptScheduleEvent)event));
		}

		else if (event instanceof AbstractShipmentScheduleEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractShipmentScheduleEvent)event));
		}

		else if (event instanceof AbstractStockEstimateEvent) /* i.e. fresh-qtyOnhand-line */
		{
			requests.add(createDataUpdateRequestForEvent((AbstractStockEstimateEvent)event));
		}

		else if (event instanceof AbstractTransactionEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractTransactionEvent)event));
		}

		else if (event instanceof AbstractPurchaseOfferEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractPurchaseOfferEvent)event));
		}

		else if (event instanceof OnHandQuantityChangedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((OnHandQuantityChangedEvent)event));
		}

		requests.forEach(request -> dataUpdateRequestHandler.handleDataUpdateRequest(request));
	}

	private ImmutableList<DataUpdateRequest> createDataUpdateRequestForEvent(
			@NonNull final PPOrderAdvisedOrCreatedEvent ppOrderAdvisedOrCreatedEvent)
	{
		final PPOrder ppOrder = ppOrderAdvisedOrCreatedEvent.getPpOrder();
		final List<PPOrderLine> lines = ppOrder.getLines();

		final ImmutableList.Builder<DataUpdateRequest> requests = ImmutableList.builder();
		for (final PPOrderLine line : lines)
		{
			final DataRecordIdentifier identifier = DataRecordIdentifier.builder()
					.productDescriptor(line.getProductDescriptor())
					.date(TimeUtil.getDay(ppOrder.getDatePromised()))
					.build();

			final DataUpdateRequest request = DataUpdateRequest.builder()
					.identifier(identifier)
					.requiredForProductionQty(line.getQtyRequired())
					.build();
			requests.add(request);
		}
		return requests.build();
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractPurchaseOfferEvent purchaseOfferedEvent)
	{
		final DataRecordIdentifier identifier = DataRecordIdentifier.builder()
				.productDescriptor(purchaseOfferedEvent.getProductDescriptor())
				.date(TimeUtil.getDay(purchaseOfferedEvent.getDate()))
				.build();

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.offeredQty(purchaseOfferedEvent.getQtyDelta())
				.build();
		return request;
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractTransactionEvent transactionEvent)
	{
		final DataRecordIdentifier identifier = createIdentifier(transactionEvent.getMaterialDescriptor());

		final DataUpdateRequestBuilder dataRequestBuilder = DataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(transactionEvent.getQuantityDelta());

		if (transactionEvent.isDirectMovementWarehouse())
		{
			dataRequestBuilder.directMovementQty(transactionEvent.getQuantityDelta());
		}
		return dataRequestBuilder.build();
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractStockEstimateEvent stockEstimateEvent)
	{
		final DataRecordIdentifier identifier = DataRecordIdentifier.builder()
				.productDescriptor(stockEstimateEvent.getProductDescriptor())
				.date(TimeUtil.getDay(stockEstimateEvent.getDate()))
				.plantId(stockEstimateEvent.getPlantId())
				.build();

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.countedQty(stockEstimateEvent.getQuantityDelta())
				.build();
		return request;
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractReceiptScheduleEvent receiptScheduleEvent)
	{
		final MaterialDescriptor orderedMaterial = receiptScheduleEvent.getOrderedMaterial();
		final DataRecordIdentifier identifier = createIdentifier(orderedMaterial);

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.orderedPurchaseQty(receiptScheduleEvent.getOrderedQuantityDelta())
				.reservedPurchaseQty(receiptScheduleEvent.getReservedQuantityDelta())
				.build();
		return request;
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractShipmentScheduleEvent shipmentScheduleEvent)
	{
		final MaterialDescriptor orderedMaterial = shipmentScheduleEvent.getOrderedMaterial();

		final DataRecordIdentifier identifier = createIdentifier(orderedMaterial);

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.orderedSalesQty(shipmentScheduleEvent.getOrderedQuantityDelta())
				.reservedSalesQty(shipmentScheduleEvent.getReservedQuantityDelta())
				.build();
		return request;
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(@NonNull final OnHandQuantityChangedEvent event)
	{
		final DataRecordIdentifier identifier = createIdentifier(event.getMaterialdescriptor());

		final DataUpdateRequest request = DataUpdateRequest.builder()
				.identifier(identifier)
				.stockQuantity(event.getQuantityDelta())
				.build();
		return request;
	}

	private DataRecordIdentifier createIdentifier(@NonNull final MaterialDescriptor material)
	{
		final DataRecordIdentifier identifier = DataRecordIdentifier.builder()
				.productDescriptor(material)
				.date(TimeUtil.getDay(material.getDate()))
				.plantId(0)
				.build();
		return identifier;
	}

}
