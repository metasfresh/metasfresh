package de.metas.material.event;

import de.metas.material.event.attributes.AttributesChangedEvent;
import de.metas.material.event.attributes.AttributesKeyWithASI;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.EventDescriptor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JacksonMaterialEventSerializerTest
{
	private final JacksonMaterialEventSerializer jsonSerializer = JacksonMaterialEventSerializer.instance;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init(); // needed for ITableRecordReference
	}

	@Test
	public void test1()
	{
		final MaterialEvent event = AttributesChangedEvent.builder()
				.date(Instant.now())
				.eventDescriptor(EventDescriptor.ofClientAndOrg(1, 1))
				.huId(11)
				.productId(12)
				.qty(BigDecimal.TEN)
				.warehouseId(WarehouseId.ofRepoId(13))
				.oldStorageAttributes(AttributesKeyWithASI.of(AttributesKey.ofAttributeValueIds(14, 15), AttributeSetInstanceId.ofRepoId(16)))
				.newStorageAttributes(AttributesKeyWithASI.of(null, AttributeSetInstanceId.NONE))
				.build();
		testSerializeUnserialize(event);
	}

	private void testSerializeUnserialize(final MaterialEvent event)
	{
		final String jsonEvent = jsonSerializer.toString(event);
		final MaterialEvent eventRestored = jsonSerializer.fromString(jsonEvent);
		Assert.assertEquals(event, eventRestored);
	}

}
