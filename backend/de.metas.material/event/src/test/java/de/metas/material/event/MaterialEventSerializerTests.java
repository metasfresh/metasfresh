package de.metas.material.event;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.event.Event;
import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.HUDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MinMaxDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.SubscriptionLineDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastDeletedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.DeletedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.procurement.PurchaseOfferCreatedEvent;
import de.metas.material.event.procurement.PurchaseOfferDeletedEvent;
import de.metas.material.event.procurement.PurchaseOfferUpdatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.event.purchase.PurchaseCandidateCreatedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.material.event.purchase.PurchaseCandidateUpdatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleDeletedEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleUpdatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent.ShipmentScheduleCreatedEventBuilder;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDeletedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleDetail;
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.material.planning.ProductPlanningId;
import de.metas.material.planning.ddorder.DistributionNetworkAndLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import de.metas.shipping.ShipperId;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static de.metas.material.event.EventTestHelper.newMaterialDescriptor;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * metasfresh-manufacturing-event-api
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
public class MaterialEventSerializerTests
{

	private static final BigDecimal ELEVEN = TEN.add(ONE);

	private static final BigDecimal TWELVE = ELEVEN.add(ONE);

	private static final BigDecimal THIRTEEN = TWELVE.add(ONE);

	private MaterialEventConverter materialEventConverter;

	private static EventDescriptor newEventDescriptor() {return EventDescriptor.ofClientOrgAndTraceId(ClientAndOrgId.ofClientAndOrg(1, 2), "traceId");}

	@BeforeEach
	public void init()
	{
		this.materialEventConverter = new MaterialEventConverter();
	}

	private void assertEventEqualAfterSerializeDeserialize(final MaterialEvent originalEvent)
	{
		//
		// Test direct serialization/deserialization
		{
			final JSONObjectMapper<MaterialEvent> jsonObjectMapper = JSONObjectMapper.forClass(MaterialEvent.class);

			final String serializedEvent = jsonObjectMapper.writeValueAsString(originalEvent);
			final MaterialEvent deserializedEvent = jsonObjectMapper.readValue(serializedEvent);

			assertThat(deserializedEvent).isEqualTo(originalEvent);
		}

		//
		// Test via materialEventConverter, without serializing the Event wrapper
		{
			final Event eventbusEvent = materialEventConverter.fromMaterialEvent(originalEvent);
			final MaterialEvent deserializedMaterialEvent = materialEventConverter.toMaterialEvent(eventbusEvent);

			assertThat(deserializedMaterialEvent).isEqualTo(originalEvent);
		}

		//
		// Test via materialEventConverter, serializing/deserializing the Event wrapper
		{
			final JSONObjectMapper<Event> jsonObjectMapper = JSONObjectMapper.forClass(Event.class);

			final Event eventbusEvent = materialEventConverter.fromMaterialEvent(originalEvent);

			final String serializedEvent = jsonObjectMapper.writeValueAsString(eventbusEvent);
			final Event deserializedEvent = jsonObjectMapper.readValue(serializedEvent);
			final MaterialEvent deserializedMaterialEvent = materialEventConverter.toMaterialEvent(deserializedEvent);

			assertThat(deserializedMaterialEvent).isEqualTo(originalEvent);
		}
	}

	private static EventDescriptor createEventDescriptor()
	{
		return EventDescriptor.ofClientAndOrg(1, 2);
	}

	@Test
	public void attributesChangedEvent()
	{
		final String delim = AttributesKey.ATTRIBUTES_KEY_DELIMITER;
		final String newKey = "3" + delim + "1" + delim + "2";
		final String oldKey = "4" + delim + "2" + delim + "3";

		final AttributesChangedEvent event = AttributesChangedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.date(NOW)
				.oldStorageAttributes(AttributesKeyWithASI.of(AttributesKey.ofString(oldKey), AttributeSetInstanceId.ofRepoId(10)))
				.newStorageAttributes(AttributesKeyWithASI.of(AttributesKey.ofString(newKey), AttributeSetInstanceId.ofRepoId(20)))
				.huId(30)
				.productId(40)
				.warehouseId(WarehouseId.ofRepoId(50))
				.qty(BigDecimal.TEN)
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderCreatedEvent()
	{
		final DDOrderCreatedEvent event = DDOrderCreatedEvent.builder()
				.supplyRequiredDescriptor(newSupplyRequiredDescriptor())
				.ddOrder(newDDOrder())
				.eventDescriptor(newEventDescriptor())
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderChangedDocStatusEvent()
	{
		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.ddOrderId(10)
				.newDocStatus(DocStatus.Completed)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	private static DDOrder newDDOrder()
	{
		final Instant supplyDate = SystemTime.asInstant();
		return DDOrder.builder()
				.supplyDate(supplyDate)
				.ddOrderId(20)
				.docStatus(DocStatus.InProgress)
				.materialDispoGroupId(MaterialDispoGroupId.ofInt(35))
				.line(newDDOrderLine(supplyDate))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(39, 40))
				.plantId(ResourceId.ofRepoId(50))
				.productPlanningId(ProductPlanningId.ofRepoId(60))
				.sourceWarehouseId(WarehouseId.ofRepoId(30))
				.targetWarehouseId(WarehouseId.ofRepoId(40))
				.shipperId(ShipperId.ofRepoId(70))
				.forwardPPOrderRef(newPPOrderRef())
				.build();
	}

	private static DDOrderLine newDDOrderLine(final Instant supplyDate)
	{
		return DDOrderLine.builder()
				.productDescriptor(createProductDescriptor())
				.ddOrderLineId(21)
				.demandDate(supplyDate.minus(10, ChronoUnit.DAYS))
				.distributionNetworkAndLineId(DistributionNetworkAndLineId.ofRepoIds(40, 41))
				.qtyMoved(new BigDecimal("10"))
				.qtyToMove(new BigDecimal("3"))
				.salesOrderLineId(61)
				.fromWarehouseMinMaxDescriptor(createSampleMinMaxDescriptor())
				.build();
	}

	private static PPOrderRef newPPOrderRef()
	{
		return PPOrderRef.builder()
				.ppOrderCandidateId(1)
				.ppOrderLineCandidateId(2)
				.ppOrderId(PPOrderId.ofRepoId(3))
				.ppOrderBOMLineId(PPOrderBOMLineId.ofRepoId(4))
				.build();
	}

	private static MinMaxDescriptor createSampleMinMaxDescriptor()
	{
		return MinMaxDescriptor.builder()
				.min(new BigDecimal("2"))
				.max(new BigDecimal("3")).build();
	}

	@Test
	public void pickingRequestedEvent()
	{
		final PickingRequestedEvent event = PickingRequestedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.pickingSlotId(10)
				.shipmentScheduleId(20)
				.topLevelHuIdsToPick(ImmutableSet.of(30, 40))
				.build();

		event.assertValid();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderRequestedEvent()
	{
		final PPOrderRequestedEvent event = PPOrderRequestedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.dateOrdered(NOW)
				.ppOrder(PPOrder.builder()
						.ppOrderData(PPOrderData.builder()
								.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(100, 100))
								.datePromised(NOW)
								.dateStartSchedule(NOW)
								.materialDispoGroupId(MaterialDispoGroupId.ofInt(30))
								.plantId(ResourceId.ofRepoId(110))
								.productDescriptor(createProductDescriptor())
								.productPlanningId(130)
								.qtyRequired(TEN)
								.qtyDelivered(ONE)
								.warehouseId(WAREHOUSE_ID)
								.build())
						.line(createPPOrderLine())
						.line(PPOrderLine.builder()
								.ppOrderLineData(PPOrderLineData.builder()
										.productDescriptor(createProductDescriptorWithOffSet(20))
										.issueOrReceiveDate(NOW)
										.description("desc2")
										.productBomLineId(380)
										.qtyRequired(valueOf(320))
										.receipt(false)
										.build())
								.build())
						.build())
				.build();

		event.validate();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderCreatedEvent()
	{
		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.ppOrder(createPPOrder())
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderDeletedEvent()
	{
		final PPOrderDeletedEvent event = PPOrderDeletedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.ppOrderId(10)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderQtyEnteredChangedEvent()
	{
		final PPOrderChangedEvent event = PPOrderChangedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				// .productDescriptor(createProductDescriptor())
				.newDatePromised(NOW)
				.oldDatePromised(de.metas.common.util.time.SystemTime.asInstant())
				.oldDocStatus(DocStatus.Completed)
				.newDocStatus(DocStatus.Closed)
				.oldQtyRequired(new BigDecimal("10"))
				.newQtyRequired(new BigDecimal("11"))
				.oldQtyDelivered(new BigDecimal("12"))
				.newQtyDelivered(new BigDecimal("13"))
				.newPPOrderLine(createPPOrderLine())
				.ppOrderLineChange(ChangedPPOrderLineDescriptor.builder()
						.productDescriptor(createProductDescriptorWithOffSet(3))
						.minMaxDescriptor(createSampleMinMaxDescriptor())
						.issueOrReceiveDate(NOW)
						.oldPPOrderLineId(PPOrderAndBOMLineId.ofRepoIds(1, 40))
						.newPPOrderLineId(PPOrderAndBOMLineId.ofRepoIds(1, 50))
						.oldQtyRequired(new BigDecimal("60"))
						.newQtyRequired(new BigDecimal("70"))
						.oldQtyDelivered(new BigDecimal("61"))
						.newQtyDelivered(new BigDecimal("71"))
						.build())
				.deletedPPOrderLine(DeletedPPOrderLineDescriptor.builder()
						.productDescriptor(createProductDescriptorWithOffSet(1))
						.issueOrReceiveDate(NOW)
						.ppOrderLineId(20)
						.qtyRequired(new BigDecimal("40"))
						.qtyDelivered(new BigDecimal("41"))
						.build())
				.deletedPPOrderLine(DeletedPPOrderLineDescriptor.builder()
						.productDescriptor(createProductDescriptorWithOffSet(2))
						.issueOrReceiveDate(NOW)
						.ppOrderLineId(30)
						.qtyRequired(new BigDecimal("50"))
						.qtyDelivered(new BigDecimal("51"))
						.build())
				.ppOrderAfterChanges(createPPOrder())
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	private PPOrder createPPOrder()
	{
		return PPOrder.builder()
				.ppOrderId(1234)
				.ppOrderData(PPOrderData.builder()
						.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(100, 100))
						.datePromised(NOW)
						.dateStartSchedule(NOW)
						.plantId(ResourceId.ofRepoId(110))
						.productDescriptor(createProductDescriptor())
						.productPlanningId(130)
						.qtyRequired(TEN)
						.qtyDelivered(ONE)
						.warehouseId(WarehouseId.ofRepoId(150))
						.build())
				.line(createPPOrderLine())
				.line(PPOrderLine.builder()
						.ppOrderLineData(PPOrderLineData.builder()
								.productDescriptor(createProductDescriptorWithOffSet(20))
								.minMaxDescriptor(createSampleMinMaxDescriptor())
								.issueOrReceiveDate(NOW)
								.description("desc2")
								.productBomLineId(380)
								.qtyRequired(valueOf(320))
								.receipt(false)
								.build())
						.build())
				.build();
	}

	private PPOrderLine createPPOrderLine()
	{
		return PPOrderLine.builder()
				.ppOrderLineData(PPOrderLineData.builder()
						.productDescriptor(createProductDescriptorWithOffSet(10))
						.issueOrReceiveDate(NOW)
						.description("desc1")
						.productBomLineId(280)
						.qtyRequired(valueOf(220))
						.minMaxDescriptor(createSampleMinMaxDescriptor())
						.receipt(true)
						.build())
				.build();
	}

	@Test
	public void purchaseCandidateAdvisedEvent()
	{
		final PurchaseCandidateAdvisedEvent purchaseAdvisedEvent = PurchaseCandidateAdvisedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.productPlanningId(10)
				.supplyRequiredDescriptor(newSupplyRequiredDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(purchaseAdvisedEvent);
	}

	@Test
	public void purchaseCandidateCreatedEvent()
	{
		final PurchaseCandidateCreatedEvent event = PurchaseCandidateCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.purchaseCandidateRepoId(20)
				.purchaseMaterialDescriptor(newMaterialDescriptor())
				.supplyRequiredDescriptor(newSupplyRequiredDescriptor())
				.vendorId(30)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void purchaseCandidateUpdatedEvent()
	{
		final PurchaseCandidateUpdatedEvent event = PurchaseCandidateUpdatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.purchaseCandidateRepoId(20)
				.purchaseMaterialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.vendorId(30)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void purchaseCandidateRequestedEvent()
	{
		final PurchaseCandidateRequestedEvent event = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.purchaseMaterialDescriptor(newMaterialDescriptor())
				.supplyCandidateRepoId(10)
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void forecastCreatedEvent()
	{
		final Forecast forecast = createForecast(newMaterialDescriptor());
		final ForecastCreatedEvent forecastCreatedEvent = ForecastCreatedEvent
				.builder()
				.forecast(forecast)
				.eventDescriptor(newEventDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(forecastCreatedEvent);
	}

	@Test
	public void forecastDeletedEvent()
	{
		final Forecast forecast = createForecast(newMaterialDescriptor());
		final ForecastDeletedEvent forecastDeletedEvent = ForecastDeletedEvent
				.builder()
				.forecast(forecast)
				.eventDescriptor(newEventDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(forecastDeletedEvent);
	}

	@Test
	public void supplyRequiredEvent()
	{
		final SupplyRequiredEvent materialDemandEvent = SupplyRequiredEvent.builder()
				.supplyRequiredDescriptor(newSupplyRequiredDescriptor())
				.build();
		assertEventEqualAfterSerializeDeserialize(materialDemandEvent);
	}

	public static SupplyRequiredDescriptor newSupplyRequiredDescriptor()
	{
		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(30)
				.eventDescriptor(newEventDescriptor())
				.forecastLineId(40)
				.materialDescriptor(newMaterialDescriptor())
				.orderLineId(50)
				.shipmentScheduleId(60)
				.build();
	}

	@Test
	public void receiptScheduleCreatedEvent()
	{
		final ReceiptScheduleCreatedEvent event = ReceiptScheduleCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.orderLineDescriptor(newOrderLineDescriptor())
				.reservedQuantity(new BigDecimal("2"))
				.receiptScheduleId(3)
				.build();
		event.validate();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void receiptScheduleUpdatedEvent()
	{
		final ReceiptScheduleUpdatedEvent event = ReceiptScheduleUpdatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.orderedQuantityDelta(new BigDecimal("2"))
				.reservedQuantity(new BigDecimal("3"))
				.reservedQuantityDelta(new BigDecimal("4"))
				.receiptScheduleId(5)
				.build();
		event.validate();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void receiptScheduleDeletedEvent()
	{
		final ReceiptScheduleDeletedEvent receiptScheduleCreatedEvent = ReceiptScheduleDeletedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.reservedQuantity(new BigDecimal("2"))
				.receiptScheduleId(3)
				.build();
		receiptScheduleCreatedEvent.validate();
		assertEventEqualAfterSerializeDeserialize(receiptScheduleCreatedEvent);
	}

	@Test
	public void shipmentScheduleCreatedEvent_with_OrderLineDescriptor()
	{
		final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent = //
				createShipmentScheduleEventBuilder()
						.documentLineDescriptor(newOrderLineDescriptor())
						.build();
		shipmentScheduleCreatedEvent.validate();
		assertEventEqualAfterSerializeDeserialize(shipmentScheduleCreatedEvent);
	}

	@Test
	public void shipmentScheduleCreatedEvent_with_SubscriptionLineDescriptor()
	{

		final ShipmentScheduleCreatedEvent shipmentScheduleCreatedEvent = //
				createShipmentScheduleEventBuilder()
						.documentLineDescriptor(createSubscriptionLineDescriptor())
						.build();
		shipmentScheduleCreatedEvent.validate();
		assertEventEqualAfterSerializeDeserialize(shipmentScheduleCreatedEvent);
	}

	@Test
	public void stockChangedEvent()
	{
		final StockChangeDetails stockChangeDetails = StockChangeDetails
				.builder()
				.resetStockPInstanceId(ResetStockPInstanceId.ofRepoId(10))
				.transactionId(20)
				.stockId(30)
				.build();

		final StockChangedEvent stockChangedEvent = //
				StockChangedEvent.builder()
						.eventDescriptor(newEventDescriptor())
						.productDescriptor(createProductDescriptor())
						.qtyOnHand(ONE)
						.qtyOnHandOld(TEN)
						.warehouseId(WAREHOUSE_ID)
						.stockChangeDetails(stockChangeDetails)
						.changeDate(NOW)
						.build();
		stockChangedEvent.validate();
		assertEventEqualAfterSerializeDeserialize(stockChangedEvent);
	}

	private static OrderLineDescriptor newOrderLineDescriptor()
	{
		return OrderLineDescriptor.builder()
				.orderLineId(4)
				.orderId(5)
				.orderBPartnerId(6)
				.docTypeId(7)
				.build();
	}

	private static SubscriptionLineDescriptor createSubscriptionLineDescriptor()
	{
		return SubscriptionLineDescriptor.builder()
				.subscriptionProgressId(4)
				.flatrateTermId(5)
				.subscriptionBillBPartnerId(6)
				.build();
	}

	private ShipmentScheduleCreatedEventBuilder createShipmentScheduleEventBuilder()
	{
		return ShipmentScheduleCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(TEN)
						.orderedQuantityDelta(TEN)
						.reservedQuantityDelta(new BigDecimal("3"))
						.reservedQuantity(new BigDecimal("3"))
						.build())
				.shipmentScheduleId(4);
	}

	@Test
	public void shipmentScheduleUpdatedEvent()
	{
		final ShipmentScheduleUpdatedEvent shipmentScheduleUpdatedEvent = ShipmentScheduleUpdatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(new BigDecimal("2"))
						.orderedQuantityDelta(new BigDecimal("2"))
						.reservedQuantity(new BigDecimal("3"))
						.reservedQuantityDelta(new BigDecimal("4"))
						.build())
				.shipmentScheduleId(5)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleUpdatedEvent);
	}

	@Test
	public void shipmentScheduleDeletedEvent()
	{
		final ShipmentScheduleDeletedEvent shipmentScheduleDeletedEvent = ShipmentScheduleDeletedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.shipmentScheduleDetail(ShipmentScheduleDetail.builder()
						.orderedQuantity(new BigDecimal("2"))
						.orderedQuantityDelta(new BigDecimal("2"))
						.reservedQuantity(new BigDecimal("3"))
						.reservedQuantityDelta(new BigDecimal("4"))
						.build())
				.shipmentScheduleId(5)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleDeletedEvent);
	}

	@Test
	public void stockCountCreatedEvent()
	{
		final StockEstimateCreatedEvent stockCountCreatedEvent = StockEstimateCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.date(NOW)
				.plantId(2)
				.eventDate(Instant.now())
				.build();

		assertEventEqualAfterSerializeDeserialize(stockCountCreatedEvent);
	}

	@Test
	public void stockCountDeletedEvent()
	{
		final StockEstimateDeletedEvent stockCountDeletedEvent = StockEstimateDeletedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.date(NOW)
				.plantId(2)
				.eventDate(Instant.now())
				.build();

		assertEventEqualAfterSerializeDeserialize(stockCountDeletedEvent);
	}

	@Test
	public void transactionCreatedEvent()
	{
		final TransactionCreatedEvent evt = newTransactionCreatedEvent();

		evt.assertValid();
		assertEventEqualAfterSerializeDeserialize(evt);
	}

	private static TransactionCreatedEvent newTransactionCreatedEvent()
	{
		return TransactionCreatedEvent
				.builder()
				.transactionId(10)
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.huOnHandQtyChangeDescriptor(HUDescriptor.builder()
						.huId(30)
						.productDescriptor(createProductDescriptor())
						.quantity(TEN)
						.build())
				.build();
	}

	@Test
	public void transactionDeletedEvent()
	{
		final TransactionDeletedEvent evt = TransactionDeletedEvent
				.builder()
				.transactionId(10)
				.eventDescriptor(newEventDescriptor())
				.materialDescriptor(newMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	@Test
	public void purchaseOfferCreatedEvent()
	{
		final PurchaseOfferCreatedEvent evt = PurchaseOfferCreatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.procurementCandidateId(10)
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.qty(new BigDecimal("20"))
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	@Test
	public void purchaseOfferUpdatedEvent()
	{
		final PurchaseOfferUpdatedEvent evt = PurchaseOfferUpdatedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.procurementCandidateId(10)
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.qty(new BigDecimal("20"))
				.qtyDelta(new BigDecimal("30"))
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	@Test
	public void purchaseOfferDeletedEvent()
	{
		final PurchaseOfferDeletedEvent evt = PurchaseOfferDeletedEvent.builder()
				.eventDescriptor(newEventDescriptor())
				.procurementCandidateId(10)
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.qty(new BigDecimal("20"))
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	private static Forecast createForecast(@NonNull final MaterialDescriptor materialDescriptor)
	{
		final ForecastLine forecastLine = ForecastLine.builder()
				.forecastLineId(30)
				.materialDescriptor(materialDescriptor)
				.build();
		return Forecast.builder()
				.forecastId(20)
				.docStatus("docStatus")
				.forecastLine(forecastLine)
				.build();
	}
}
