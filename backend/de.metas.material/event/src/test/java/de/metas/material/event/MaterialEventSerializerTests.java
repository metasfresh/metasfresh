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
import de.metas.material.event.ddorder.DDOrderAdvisedEvent;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderDocStatusChangedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.eventbus.MaterialEventConverter;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.event.picking.PickingRequestedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent;
import de.metas.material.event.pporder.PPOrderChangedEvent.ChangedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderChangedEvent.DeletedPPOrderLineDescriptor;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import de.metas.material.event.pporder.PPOrderLine;
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
import de.metas.material.event.shipmentschedule.ShipmentScheduleUpdatedEvent;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.material.event.stock.StockChangedEvent;
import de.metas.material.event.stock.StockChangedEvent.StockChangeDetails;
import de.metas.material.event.stockestimate.StockEstimateCreatedEvent;
import de.metas.material.event.stockestimate.StockEstimateDeletedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;
import de.metas.material.event.transactions.TransactionDeletedEvent;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.util.JSONObjectMapper;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.*;

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
		// Test via materialEventConverter
		{
			final Event eventbusEvent = materialEventConverter.fromMaterialEvent(originalEvent);
			final MaterialEvent deserializedEvent = materialEventConverter.toMaterialEvent(eventbusEvent);

			assertThat(deserializedEvent).isEqualTo(originalEvent);
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
				.eventDescriptor(createEventDescriptor())
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
	public void ddOrderRequestedEvent()
	{
		final DDOrderRequestedEvent event = DDOrderRequestedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.dateOrdered(NOW)
				.ddOrder(createDdOrder(0))
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderAdvisedEvent()
	{
		final DDOrderAdvisedEvent event = DDOrderAdvisedEvent.builder()
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.advisedToCreateDDrder(true)
				.pickIfFeasible(false)
				.ddOrder(createDdOrder(0))
				.fromWarehouseId(WarehouseId.ofRepoId(30))
				.toWarehouseId(WarehouseId.ofRepoId(40))
				.eventDescriptor(createEventDescriptor())
				.build();
		event.validate();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderCreatedEvent()
	{
		final DDOrderCreatedEvent event = DDOrderCreatedEvent.builder()
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ddOrder(createDdOrder(20))
				.fromWarehouseId(WarehouseId.ofRepoId(30))
				.toWarehouseId(WarehouseId.ofRepoId(40))
				.eventDescriptor(createEventDescriptor())
				.build();
		event.validate();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderChangedDocStatusEvent()
	{
		final DDOrderDocStatusChangedEvent event = DDOrderDocStatusChangedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.ddOrderId(10)
				.newDocStatus("newDocStatus")
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	private DDOrder createDdOrder(final int ddOrderId)
	{
		return DDOrder.builder()
				.datePromised(SystemTime.asInstant())
				.ddOrderId(ddOrderId)
				.docStatus("IP")
				.materialDispoGroupId(MaterialDispoGroupId.ofInt(35))
				.line(DDOrderLine.builder()
						.productDescriptor(createProductDescriptor())
						.ddOrderLineId(21)
						.durationDays(31)
						.networkDistributionLineId(41)
						.qty(TEN)
						.salesOrderLineId(61)
						.fromWarehouseMinMaxDescriptor(createSampleMinMaxDescriptor())
						.build())
				.orgId(OrgId.ofRepoId(40))
				.plantId(50)
				.productPlanningId(60)
				.shipperId(70)
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
				.eventDescriptor(createEventDescriptor())
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
				.eventDescriptor(createEventDescriptor())
				.dateOrdered(NOW)
				.ppOrder(PPOrder.builder()
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
						.line(createPPOrderLine())
						.line(PPOrderLine.builder()
								.productDescriptor(createProductDescriptorWithOffSet(20))
								.issueOrReceiveDate(NOW)
								.description("desc2")
								.productBomLineId(380)
								.qtyRequired(valueOf(320))
								.receipt(false)
								.build())
						.build())
				.build();

		event.validate();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderAdvisedEvent()
	{
		final PPOrderAdvisedEvent event = PPOrderAdvisedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ppOrder(createPPOrder())
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderCreatedEvent()
	{
		final PPOrderCreatedEvent event = PPOrderCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ppOrder(createPPOrder())
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderDeletedEvent()
	{
		final PPOrderDeletedEvent event = PPOrderDeletedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.ppOrderId(10)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderQtyEnteredChangedEvent()
	{
		final PPOrderChangedEvent event = PPOrderChangedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				// .productDescriptor(createProductDescriptor())
				.newDatePromised(NOW)
				.oldDatePromised(de.metas.common.util.time.SystemTime.asInstant())
				.oldDocStatus(DocStatus.Completed)
				.newDocStatus(DocStatus.Closed)
				.oldQtyRequired(TEN)
				.newQtyRequired(ELEVEN)
				.oldQtyDelivered(TWELVE)
				.newQtyDelivered(THIRTEEN)
				.newPPOrderLine(createPPOrderLine())
				.ppOrderLineChange(ChangedPPOrderLineDescriptor.builder()
						.productDescriptor(createProductDescriptorWithOffSet(3))
						.minMaxDescriptor(createSampleMinMaxDescriptor())
						.issueOrReceiveDate(NOW)
						.oldPPOrderLineId(40)
						.newPPOrderLineId(50)
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
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(100, 100))
				.ppOrderId(1234)
				.datePromised(NOW)
				.dateStartSchedule(NOW)
				.plantId(ResourceId.ofRepoId(110))
				.productDescriptor(createProductDescriptor())
				.productPlanningId(130)
				.qtyRequired(TEN)
				.qtyDelivered(ONE)
				.warehouseId(WarehouseId.ofRepoId(150))
				.line(createPPOrderLine())
				.line(PPOrderLine.builder()
						.productDescriptor(createProductDescriptorWithOffSet(20))
						.minMaxDescriptor(createSampleMinMaxDescriptor())
						.issueOrReceiveDate(NOW)
						.description("desc2")
						.productBomLineId(380)
						.qtyRequired(valueOf(320))
						.receipt(false)
						.build())
				.build();
	}

	private PPOrderLine createPPOrderLine()
	{
		return PPOrderLine.builder()
				.productDescriptor(createProductDescriptorWithOffSet(10))
				.issueOrReceiveDate(NOW)
				.description("desc1")
				.productBomLineId(280)
				.qtyRequired(valueOf(220))
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.receipt(true)
				.build();
	}

	@Test
	public void purchaseCandidateAdvisedEvent()
	{
		final PurchaseCandidateAdvisedEvent purchaseAdvisedEvent = PurchaseCandidateAdvisedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.productPlanningId(10)
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(purchaseAdvisedEvent);
	}

	@Test
	public void purchaseCandidateCreatedEvent()
	{
		final PurchaseCandidateCreatedEvent event = PurchaseCandidateCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.purchaseCandidateRepoId(20)
				.purchaseMaterialDescriptor(createMaterialDescriptor())
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.vendorId(30)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void purchaseCandidateUpdatedEvent()
	{
		final PurchaseCandidateUpdatedEvent event = PurchaseCandidateUpdatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.purchaseCandidateRepoId(20)
				.purchaseMaterialDescriptor(createMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.vendorId(30)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void purchaseCandidateRequestedEvent()
	{
		final PurchaseCandidateRequestedEvent event = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.purchaseMaterialDescriptor(createMaterialDescriptor())
				.supplyCandidateRepoId(10)
				.build();
		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void forecastCreatedEvent()
	{
		final MaterialDescriptor materialDescriptor = createMaterialDescriptor();

		final ForecastLine forecastLine = ForecastLine.builder()
				.forecastLineId(30)
				.materialDescriptor(materialDescriptor)
				.build();
		final Forecast forecast = Forecast.builder()
				.forecastId(20)
				.docStatus("docStatus")
				.forecastLine(forecastLine)
				.build();
		final ForecastCreatedEvent forecastCreatedEvent = ForecastCreatedEvent
				.builder()
				.forecast(forecast)
				.eventDescriptor(createEventDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(forecastCreatedEvent);
	}

	@Test
	public void supplyRequiredEvent()
	{
		final SupplyRequiredEvent materialDemandEvent = SupplyRequiredEvent.builder()
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.build();
		assertEventEqualAfterSerializeDeserialize(materialDemandEvent);
	}

	private SupplyRequiredDescriptor createSupplyRequiredDescriptor()
	{
		return SupplyRequiredDescriptor.builder()
				.demandCandidateId(30)
				.eventDescriptor(createEventDescriptor())
				.forecastLineId(40)
				.materialDescriptor(createMaterialDescriptor())
				.orderLineId(50)
				.shipmentScheduleId(60)
				.build();
	}

	@Test
	public void receiptScheduleCreatedEvent()
	{
		final ReceiptScheduleCreatedEvent event = ReceiptScheduleCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.orderLineDescriptor(createOrderLineDescriptor())
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
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
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
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
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
						.documentLineDescriptor(createOrderLineDescriptor())
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
						.eventDescriptor(createEventDescriptor())
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

	private static OrderLineDescriptor createOrderLineDescriptor()
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
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.reservedQuantity(new BigDecimal("3"))
				.shipmentScheduleId(4);
	}

	@Test
	public void shipmentScheduleUpdatedEvent()
	{
		final ShipmentScheduleUpdatedEvent shipmentScheduleUpdatedEvent = ShipmentScheduleUpdatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.orderedQuantityDelta(new BigDecimal("2"))
				.reservedQuantity(new BigDecimal("3"))
				.reservedQuantityDelta(new BigDecimal("4"))
				.shipmentScheduleId(5)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleUpdatedEvent);
	}

	@Test
	public void shipmentScheduleDeletedEvent()
	{
		final ShipmentScheduleDeletedEvent shipmentScheduleDeletedEvent = ShipmentScheduleDeletedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.reservedQuantity(new BigDecimal("3"))
				.shipmentScheduleId(5)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleDeletedEvent);
	}

	@Test
	public void stockCountCreatedEvent()
	{
		final StockEstimateCreatedEvent stockCountCreatedEvent = StockEstimateCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
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
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.date(NOW)
				.plantId(2)
				.eventDate(Instant.now())
				.build();

		assertEventEqualAfterSerializeDeserialize(stockCountDeletedEvent);
	}

	@Test
	public void transactionCreatedEvent()
	{
		final TransactionCreatedEvent evt = createSampleTransactionEvent();

		evt.assertValid();
		assertEventEqualAfterSerializeDeserialize(evt);
	}

	public static TransactionCreatedEvent createSampleTransactionEvent()
	{
		return TransactionCreatedEvent
				.builder()
				.transactionId(10)
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.shipmentScheduleIds2Qty(20, TEN)
				.shipmentScheduleIds2Qty(21, ONE.negate())
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
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.minMaxDescriptor(createSampleMinMaxDescriptor())
				.shipmentScheduleIds2Qty(20, TEN)
				.shipmentScheduleIds2Qty(21, ONE.negate())
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	@Test
	public void purchaseOfferCreatedEvent()
	{
		final PurchaseOfferCreatedEvent evt = PurchaseOfferCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
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
				.eventDescriptor(createEventDescriptor())
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
				.eventDescriptor(createEventDescriptor())
				.procurementCandidateId(10)
				.date(NOW)
				.productDescriptor(createProductDescriptor())
				.qty(new BigDecimal("20"))
				.build();

		assertEventEqualAfterSerializeDeserialize(evt);
	}
}
