package de.metas.ui.web.material.cockpit.event;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.procurement.AbstractPurchaseOfferEvent;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.material.event.shipmentschedule.AbstractShipmentScheduleEvent;
import de.metas.material.event.stockestimate.AbstractStockCountEvent;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.material.event.transactions.AbstractTransactionEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.ui.web.material.cockpit.event.DataUpdateRequest.DataUpdateRequestBuilder;
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
public class MaterialCockpitEventListener implements MaterialEventListener
{
	private final DataUpdateRequestHandler dataUpdateRequestHandler;

	public MaterialCockpitEventListener(
			@NonNull final MaterialEventService materialEventService,
			@NonNull final DataUpdateRequestHandler dataUpdateRequestHandler)
	{
		this.dataUpdateRequestHandler = dataUpdateRequestHandler;

		materialEventService.registerListener(this);
		materialEventService.subscribeToEventBus();
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

		// ReceiptSchedule
		else if (event instanceof ReceiptScheduleCreatedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((ReceiptScheduleCreatedEvent)event));
		}
		else if (event instanceof ReceiptScheduleUpdatedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((ReceiptScheduleUpdatedEvent)event));
		}
		else if (event instanceof ReceiptScheduleDeletedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((ReceiptScheduleDeletedEvent)event));
		}

		// ShipmentSchedule
		else if (event instanceof AbstractShipmentScheduleEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractShipmentScheduleEvent)event));
		}

		// stock count
		else if (event instanceof StockEstimateCreatedEvent) /* i.e. fresh-qtyOnhand-line */
		{
			requests.add(createDataUpdateRequestForEvent((StockEstimateCreatedEvent)event));
		}
		else if (event instanceof StockEstimateDeletedEvent) /* i.e. fresh-qtyOnhand-line */
		{
			requests.add(createDataUpdateRequestForEvent((StockEstimateDeletedEvent)event));
		}

		else if (event instanceof TransactionCreatedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((TransactionCreatedEvent)event));
		}
		else if (event instanceof TransactionDeletedEvent)
		{
			requests.add(createDataUpdateRequestForEvent((TransactionDeletedEvent)event));
		}

		else if (event instanceof AbstractPurchaseOfferEvent)
		{
			requests.add(createDataUpdateRequestForEvent((AbstractPurchaseOfferEvent)event));
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
		final MaterialDescriptor materialDescriptor = transactionEvent.getMaterialDescriptor();

		final DataUpdateRequestBuilder dataRequestBuilder = DataUpdateRequest.builder()
				.identifier(identifier)
				.onHandQtyChange(transactionEvent.getQuantityDelta());

		if (isDirectMovementWarehouse(materialDescriptor.getWarehouseId()))
		{
			dataRequestBuilder.directMovementQty(transactionEvent.getQuantityDelta());
		}
		return dataRequestBuilder.build();
	}

	private boolean isDirectMovementWarehouse(final int warehouseId)
	{
		final int intValue = Services.get(ISysConfigBL.class).getIntValue(IHUMovementBL.SYSCONFIG_DirectMove_Warehouse_ID, -1);
		return intValue == warehouseId;
	}

	private DataUpdateRequest createDataUpdateRequestForEvent(
			@NonNull final AbstractStockCountEvent stockEstimateEvent)
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

	private DataRecordIdentifier createIdentifier(final MaterialDescriptor material)
	{
		final DataRecordIdentifier identifier = DataRecordIdentifier.builder()
				.productDescriptor(material)
				.date(TimeUtil.getDay(material.getDate()))
				.plantId(0)
				.build();
		return identifier;
	}
}
