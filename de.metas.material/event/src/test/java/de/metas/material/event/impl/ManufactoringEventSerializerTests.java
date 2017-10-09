package de.metas.material.event.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Table;
import org.junit.Before;
import org.junit.Test;

import de.metas.event.SimpleObjectSerializer;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.ReceiptScheduleEvent;
import de.metas.material.event.TransactionEvent;
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

public class ManufactoringEventSerializerTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testReceiptScheduleEvent()
	{
		final I_AD_Table someOtherTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		someOtherTable.setTableName("someOtherTable");
		InterfaceWrapperHelper.save(someOtherTable);

		final ReceiptScheduleEvent evt = ReceiptScheduleEvent.builder()
				.eventDescr(new EventDescr(1, 2))
				.materialDescr(MaterialDescriptor.builder()
						.date(SystemTime.asDate())
						.productId(13)
						.warehouseId(15)
						.quantity(BigDecimal.TEN)
						.build())
				.receiptScheduleDeleted(false)
				.reference(TableRecordReference.of("someOtherTable", 100))
				.build();
		assertThat(evt.getMaterialDescr().getQuantity(), comparesEqualTo(BigDecimal.TEN)); // guard

		final String serializedEvt = SimpleObjectSerializer.get().serialize(evt);
		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);

		assertThat(deserializedEvt, is(evt));
	}

	@Test
	public void testTransactionEvent()
	{

		final TransactionEvent evt = createSampleTransactionEvent();

		final String serializedEvt = SimpleObjectSerializer.get().serialize(evt);

		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);
		assertThat(deserializedEvt instanceof TransactionEvent, is(true));
		assertThat(((TransactionEvent)deserializedEvt)
				.getMaterialDescr()
				.getProductId(), is(14)); // "spot check": picking the productId
		assertThat(deserializedEvt, is(evt));
	}

	public static TransactionEvent createSampleTransactionEvent()
	{
		final I_AD_Table someTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		someTable.setTableName("someTable");
		InterfaceWrapperHelper.save(someTable);

		final TransactionEvent evt = TransactionEvent
				.builder()
				.eventDescr(new EventDescr(1, 2))
				.materialDescr(MaterialDescriptor.builder()
						.productId(14)
						.quantity(BigDecimal.TEN)
						.date(SystemTime.asDate())
						.warehouseId(12)
						.build())
				.reference(TableRecordReference.of(1, 2))
				.build();
		return evt;
	}

	@Test
	public void testProductionOrderEvent()
	{
		final PPOrderRequestedEvent event = PPOrderRequestedEvent.builder()
				.eventDescr(new EventDescr(1, 2))
				.reference(TableRecordReference.of("table", 24))
				.ppOrder(PPOrder.builder()
						.datePromised(SystemTime.asDate())
						.dateStartSchedule(SystemTime.asDate())
						.orgId(100)
						.plantId(110)
						.productId(120)
						.productPlanningId(130)
						.quantity(BigDecimal.TEN)
						.uomId(140)
						.warehouseId(150)
						.warehouseId(160)
						.line(PPOrderLine.builder()
								.attributeSetInstanceId(270)
								.description("desc1")
								.productBomLineId(280)
								.productId(290)
								.qtyRequired(BigDecimal.valueOf(220))
								.receipt(true)
								.build())
						.line(PPOrderLine.builder()
								.attributeSetInstanceId(370)
								.description("desc2")
								.productBomLineId(380)
								.productId(390)
								.qtyRequired(BigDecimal.valueOf(320))
								.receipt(false)
								.build())
						.build())
				.build();

		final String serializedEvt = SimpleObjectSerializer.get().serialize(event);

		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);

		assertThat(deserializedEvt, is(event));
	}

	@Test
	public void testProductionPlanEvent()
	{
		final ProductionPlanEvent event = ProductionPlanEvent.builder()
				.eventDescr(new EventDescr(1, 2))
				.reference(TableRecordReference.of("table", 24))
				.ppOrder(PPOrder.builder()
						.datePromised(SystemTime.asDate())
						.dateStartSchedule(SystemTime.asDate())
						.orgId(100)
						.plantId(110)
						.productId(120)
						.productPlanningId(130)
						.quantity(BigDecimal.TEN)
						.uomId(140)
						.warehouseId(150)
						.warehouseId(160)
						.line(PPOrderLine.builder()
								.attributeSetInstanceId(270)
								.description("desc1")
								.productBomLineId(280)
								.productId(290)
								.qtyRequired(BigDecimal.valueOf(220))
								.receipt(true)
								.build())
						.line(PPOrderLine.builder()
								.attributeSetInstanceId(370)
								.description("desc2")
								.productBomLineId(380)
								.productId(390)
								.qtyRequired(BigDecimal.valueOf(320))
								.receipt(false)
								.build())
						.build())
				.build();

		final String serializedEvt = SimpleObjectSerializer.get().serialize(event);

		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);

		assertThat(deserializedEvt, is(event));
	}

	@Test
	public void testForecastEvent()
	{
		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(SystemTime.asDate())
				.productId(20)
				.quantity(new BigDecimal("20"))
				.warehouseId(30)
				.build();

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
				.eventDescr(new EventDescr(1, 2))
				.build();

		final String serializedEvt = SimpleObjectSerializer.get().serialize(forecastEvent);

		final MaterialEvent deserializedEvt = SimpleObjectSerializer.get().deserialize(serializedEvt, MaterialEvent.class);

		assertThat(deserializedEvt, is(deserializedEvt));
	}
}
