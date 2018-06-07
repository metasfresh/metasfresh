package de.metas.material.event.eventbus;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.event.Event;
import de.metas.event.log.EventLogUserService;
import de.metas.event.remote.ActiveMQJMSEndpoint;
import de.metas.event.remote.IEventSerializer;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventSerializerTests;
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
/**
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class MaterialEventConverterTests
{
	/**
	 * Verifies that {@link ActiveMQJMSEndpoint#DEFAULT_EVENT_SERIALIZER} works, because we put a JSON string into an {@link Event} which is also serialized into JSON,
	 * so there might be problems down that road if {@link ActiveMQJMSEndpoint#DEFAULT_EVENT_SERIALIZER} get confused about which parts of the string are just payload and which aren't.
	 */
	@Test
	public void serialize_and_deserialize_event_with_json_payload()
	{
		final TransactionCreatedEvent transactionEvent = MaterialEventSerializerTests.createSampleTransactionEvent();
		final MaterialEventConverter materialEventConverter = new MaterialEventConverter(new EventLogUserService());

		final Event metasfreshEvent = materialEventConverter
				.fromMaterialEvent(transactionEvent);

		final IEventSerializer eventSerializer = ActiveMQJMSEndpoint.DEFAULT_EVENT_SERIALIZER;
		final String eventStr = eventSerializer.toString(metasfreshEvent);

		final Event deserialisedMetasfreshEvent = eventSerializer.fromString(eventStr);
		final MaterialEvent deserializedTransactionEvent = materialEventConverter
				.toMaterialEvent(deserialisedMetasfreshEvent);

		assertThat(deserializedTransactionEvent).isInstanceOf(TransactionCreatedEvent.class);
		assertThat(((TransactionCreatedEvent)deserializedTransactionEvent)
				.getMaterialDescriptor().getProductId())
						.isEqualTo(transactionEvent.getMaterialDescriptor().getProductId()); // "spot check": picking the productId
		assertThat(deserializedTransactionEvent).isEqualTo(transactionEvent);
	}
}
