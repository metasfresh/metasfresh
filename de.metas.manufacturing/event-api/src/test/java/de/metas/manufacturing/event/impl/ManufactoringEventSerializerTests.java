package de.metas.manufacturing.event.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.time.Instant;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Table;
import org.junit.Before;
import org.junit.Test;

import de.metas.manufacturing.event.ManufacturingEvent;
import de.metas.manufacturing.event.ReceiptScheduleEvent;
import de.metas.manufacturing.event.TransactionEvent;

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
				.productId(24)
				.promisedDate(SystemTime.asDate())
				.qtyOrdered(BigDecimal.TEN)
				.receiptScheduleDeleted(false)
				.reference(TableRecordReference.of("someOtherTable", 100))
				.warehouseId(23)
				.when(Instant.now())
				.build();

		assertThat(evt.getQtyOrdered(), comparesEqualTo(BigDecimal.TEN)); // guard

		final String serializedEvt = ManufacturingEventSerializer.get().serialize(evt);
		final ManufacturingEvent deserializedEvt = ManufacturingEventSerializer.get().deserialize(serializedEvt);

		assertThat(deserializedEvt, is(evt));
	}

	@Test
	public void testTransactionEvent()
	{

		final TransactionEvent evt = createSampleTransactionEvent();

		final String serializedEvt = ManufacturingEventSerializer.get().serialize(evt);

		final ManufacturingEvent deserializedEvt = ManufacturingEventSerializer.get().deserialize(serializedEvt);
		assertThat(deserializedEvt instanceof TransactionEvent, is(true));
		assertThat(((TransactionEvent)deserializedEvt).getProductId(), is(14)); // "spot check": picking the productId
		assertThat(deserializedEvt, is(evt));
	}

	public static TransactionEvent createSampleTransactionEvent()
	{
		final I_AD_Table someTable = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		someTable.setTableName("someTable");
		InterfaceWrapperHelper.save(someTable);

		final TransactionEvent evt = TransactionEvent
				.builder()
				.locatorId(10)
				.movementDate(SystemTime.asDate())
				.productId(14)
				.qty(BigDecimal.TEN)
				.reference(TableRecordReference.of(1, 2))
				.warehouseId(12)
				.when(Instant.now())
				.build();
		return evt;
	}
}
