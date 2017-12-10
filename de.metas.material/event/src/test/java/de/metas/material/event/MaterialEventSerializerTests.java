package de.metas.material.event;

import static de.metas.material.event.EventTestHelper.NOW;
import static de.metas.material.event.EventTestHelper.WAREHOUSE_ID;
import static de.metas.material.event.EventTestHelper.createMaterialDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptor;
import static de.metas.material.event.EventTestHelper.createProductDescriptorWithOffSet;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.junit.Before;
import org.junit.Test;

import de.metas.event.SimpleObjectSerializer;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedOrCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderAdvisedOrCreatedEvent;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.shipmentschedule.ShipmentScheduleCreatedEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.event.transactions.TransactionCreatedEvent;

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
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void ddOrderRequestedEvent()
	{
		final DDOrderRequestedEvent event = DDOrderRequestedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.groupId(20)
				.ddOrder(createDdOrder())
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ddOrderAdvisedOrCreatedEvent()
	{
		final DDOrderAdvisedOrCreatedEvent event = DDOrderAdvisedOrCreatedEvent.builder()
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ddOrder(createDdOrder())
				.fromWarehouseId(30)
				.eventDescriptor(createEventDescriptor())
				.toWarehouseId(40)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	private DDOrder createDdOrder()
	{
		return DDOrder.builder()
				.advisedToCreateDDrder(true)
				.datePromised(SystemTime.asDayTimestamp())
				.ddOrderId(30)
				.docStatus("IP")
				.line(DDOrderLine.builder()
						.productDescriptor(createProductDescriptor())
						.ddOrderLineId(21)
						.durationDays(31)
						.networkDistributionLineId(41)
						.qty(BigDecimal.TEN)
						.salesOrderLineId(61)
						.build())
				.orgId(40)
				.plantId(50)
				.productPlanningId(60)
				.shipperId(70)
				.build();
	}

	@Test
	public void ppOrderRequestedEvent()
	{
		final PPOrderRequestedEvent event = PPOrderRequestedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.groupId(30)
				.ppOrder(PPOrder.builder()
						.datePromised(NOW)
						.dateStartSchedule(NOW)
						.orgId(100)
						.plantId(110)
						.productDescriptor(createProductDescriptor())
						.productPlanningId(130)
						.quantity(BigDecimal.TEN)
						.uomId(140)
						.warehouseId(WAREHOUSE_ID)
						.line(PPOrderLine.builder()
								.productDescriptor(createProductDescriptorWithOffSet(10))
								.description("desc1")
								.productBomLineId(280)
								.qtyRequired(BigDecimal.valueOf(220))
								.receipt(true)
								.build())
						.line(PPOrderLine.builder()
								.productDescriptor(createProductDescriptorWithOffSet(20))
								.description("desc2")
								.productBomLineId(380)
								.qtyRequired(BigDecimal.valueOf(320))
								.receipt(false)
								.build())
						.build())
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void ppOrderAdvisedOrCreatedEvent()
	{
		final PPOrderAdvisedOrCreatedEvent event = PPOrderAdvisedOrCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.supplyRequiredDescriptor(createSupplyRequiredDescriptor())
				.ppOrder(PPOrder.builder()
						.datePromised(NOW)
						.dateStartSchedule(NOW)
						.orgId(100)
						.plantId(110)
						.productDescriptor(createProductDescriptor())
						.productPlanningId(130)
						.quantity(BigDecimal.TEN)
						.uomId(140)
						.warehouseId(150)
						.line(PPOrderLine.builder()
								.productDescriptor(createProductDescriptorWithOffSet(10))
								.description("desc1")
								.productBomLineId(280)
								.qtyRequired(BigDecimal.valueOf(220))
								.receipt(true)
								.build())
						.line(PPOrderLine.builder()
								.productDescriptor(createProductDescriptorWithOffSet(20))
								.description("desc2")
								.productBomLineId(380)
								.qtyRequired(BigDecimal.valueOf(320))
								.receipt(false)
								.build())
						.build())
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
				.reference(TableRecordReference.of("table", 24))
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
				.eventDescr(createEventDescriptor())
				.forecastLineId(40)
				.materialDescriptor(createMaterialDescriptor())
				.orderLineId(50)
				.shipmentScheduleId(60)
				.build();
	}

	@Test
	public void shipmentScheduleCreatedEvent()
	{
		final ShipmentScheduleCreatedEvent shipmentScheduleEvent = ShipmentScheduleCreatedEvent.builder()
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.shipmentScheduleId(3)
				.orderLineId(4)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleEvent);
	}

	@Test
	public void transactionCreatedEvent()
	{
		final TransactionCreatedEvent evt = createSampleTransactionEvent();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	public static TransactionCreatedEvent createSampleTransactionEvent()
	{
		final TransactionCreatedEvent evt = TransactionCreatedEvent
				.builder()
				.transactionId(10)
				.eventDescriptor(createEventDescriptor())
				.materialDescriptor(createMaterialDescriptor())
				.build();
		return evt;
	}

	public static MaterialEvent assertEventEqualAfterSerializeDeserialize(final MaterialEvent originalEvent)
	{
		final String serializedEvt = SimpleObjectSerializer.get().serialize(originalEvent);
		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);

		assertThat(deserializedEvt).isEqualTo(originalEvent);
		return deserializedEvt;
	}

	private static EventDescriptor createEventDescriptor()
	{
		return new EventDescriptor(1, 2);
	}
}
