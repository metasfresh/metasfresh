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
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.ddorder.DistributionPlanEvent;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.pporder.ProductionPlanEvent;

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
				.eventDescr(createEventDescriptor())
				.groupId(20)
				.ddOrder(createDdOrder())
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	@Test
	public void distributionPlanEvent()
	{
		final DistributionPlanEvent event = DistributionPlanEvent.builder()
				.ddOrder(createDdOrder())
				.fromWarehouseId(30)
				.eventDescr(createEventDescriptor())
				.toWarehouseId(40)
				.build();

		assertEventEqualAfterSerializeDeserialize(event);
	}

	private DDOrder createDdOrder()
	{
		return DDOrder.builder()
				.createDDrder(true)
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
				.eventDescr(createEventDescriptor())
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
	public void productionPlanEvent()
	{
		final ProductionPlanEvent event = ProductionPlanEvent.builder()
				.eventDescr(createEventDescriptor())
				.materialDemandDescr(createMaterialDemandDescriptor())
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
	public void forecastEvent()
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
		final ForecastEvent forecastEvent = ForecastEvent
				.builder()
				.forecast(forecast)
				.eventDescr(createEventDescriptor())
				.build();

		assertEventEqualAfterSerializeDeserialize(forecastEvent);
	}

	@Test
	public void materialDemandEvent()
	{
		final MaterialDemandEvent materialDemandEvent = MaterialDemandEvent.builder()
				.materialDemandDescr(createMaterialDemandDescriptor())
				.build();
		assertEventEqualAfterSerializeDeserialize(materialDemandEvent);
	}

	private MaterialDemandDescriptor createMaterialDemandDescriptor()
	{
		return MaterialDemandDescriptor.builder()
				.demandCandidateId(30)
				.eventDescr(createEventDescriptor())
				.forecastLineId(40)
				.materialDescriptor(createMaterialDescriptor())
				.orderLineId(50)
				.shipmentScheduleId(60)
				.build();
	}

	@Test
	public void shipmentScheduleEvent()
	{
		final ShipmentScheduleEvent shipmentScheduleEvent = ShipmentScheduleEvent.builder()
				.eventDescr(createEventDescriptor())
				.materialDescr(createMaterialDescriptor())
				.shipmentScheduleId(3)
				.orderLineId(4)
				.build();

		assertEventEqualAfterSerializeDeserialize(shipmentScheduleEvent);
	}

	@Test
	public void transactionEvent()
	{
		final TransactionEvent evt = createSampleTransactionEvent();

		assertEventEqualAfterSerializeDeserialize(evt);
	}

	public static TransactionEvent createSampleTransactionEvent()
	{
		final TransactionEvent evt = TransactionEvent
				.builder()
				.transactionId(10)
				.eventDescr(createEventDescriptor())
				.materialDescr(createMaterialDescriptor())
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

	private static EventDescr createEventDescriptor()
	{
		return new EventDescr(1, 2);
	}
}
