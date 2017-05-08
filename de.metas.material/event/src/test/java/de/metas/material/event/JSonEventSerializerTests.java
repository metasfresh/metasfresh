package de.metas.material.event;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Before;
import org.junit.Test;

import de.metas.event.Event;
import de.metas.event.jms.JsonEventSerializer;
import de.metas.material.event.impl.ManufactoringEventSerializerTests;
import de.metas.material.event.impl.MaterialEventSerializer;

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
/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JSonEventSerializerTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * Verifies that {@link JsonEventSerializer} works, because we put a JSON string into an {@link Event} which is also serialized into JSON,
	 * so there might be problems down that road if {@link JsonEventSerializer} get confused about which parts of the string are just payload and which aren't.
	 *
	 */
	@Test
	public void test()
	{
		final TransactionEvent inOutEvent = ManufactoringEventSerializerTests.createSampleTransactionEvent();
		final String inOutEventStr = MaterialEventSerializer.get().serialize(inOutEvent);

		final Event event = Event.builder()
				.putProperty(MaterialEventService.MANUFACTURING_DISPOSITION_EVENT, inOutEventStr)
				.build();

		final String eventStr = JsonEventSerializer.instance.toString(event);

		final Event deserialisedEvent = JsonEventSerializer.instance.fromString(eventStr);
		final String deserializedInOutEventStr = deserialisedEvent.getProperty(MaterialEventService.MANUFACTURING_DISPOSITION_EVENT);

		final MaterialEvent deserializedInOutEvent = MaterialEventSerializer.get().deserialize(deserializedInOutEventStr);

		assertThat(deserializedInOutEvent instanceof TransactionEvent, is(true));
		assertThat(((TransactionEvent)deserializedInOutEvent)
				.getMaterialDescr()
				.getProductId(), is(14)); // "spot check": picking the productId of the 2nd line
		assertThat(deserializedInOutEvent, is(inOutEvent));
	}
}
