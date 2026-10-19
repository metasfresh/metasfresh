/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.event.impl;

import com.google.common.collect.ImmutableList;
import de.metas.event.Event;
import de.metas.event.Topic;
import de.metas.event.test.ChannelRecordingRabbitTemplateFactory;
import de.metas.event.test.ChannelRecordingRabbitTemplateFactory.Publish;
import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.event.remote.rabbitmq.queues.default_queue.DefaultQueueConfiguration;
import org.adempiere.test.AdempiereTestHelper;
import org.springframework.amqp.AmqpException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RabbitMQEnqueuerTest
{
	private static final Topic DISTRIBUTED_TOPIC = Topic.distributed("de.metas.test.distributed");
	private static final Topic LOCAL_TOPIC = Topic.local("de.metas.test.local");

	private ChannelRecordingRabbitTemplateFactory broker;
	private RabbitMQEnqueuer enqueuer;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		broker = new ChannelRecordingRabbitTemplateFactory();
		enqueuer = new RabbitMQEnqueuer(
				broker.newRabbitTemplate(sentObject -> ((Event)sentObject).getEventName()),
				new RabbitMQDestinationResolver(ImmutableList.of(new DefaultQueueConfiguration())));
	}

	private static Event event(final String name)
	{
		return Event.builder().setEventName(name).build();
	}

	@Test
	void withoutBatch_eachEventIsSentRightAway_onTheChannelItChecksOut()
	{
		enqueuer.enqueueDistributedEvent(event("e1"), DISTRIBUTED_TOPIC);
		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1");

		enqueuer.enqueueDistributedEvent(event("e2"), DISTRIBUTED_TOPIC);
		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e2");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber)
				.as("each plain send checks out its own channel (the fake always opens a new one, like a busy CachingConnectionFactory)")
				.containsExactly(1, 2);
	}

	@Test
	void withoutBatch_aFailingSendThrows_asBefore()
	{
		broker.failPublishOfBody("e1");

		assertThatThrownBy(() -> enqueuer.enqueueDistributedEvent(event("e1"), DISTRIBUTED_TOPIC))
				.isInstanceOf(AmqpException.class);
	}

	@Test
	void batch_sendsAllEventsAtTheEnd_inOrder_onOneChannel()
	{
		enqueuer.enqueueEventsInOneBatch(() -> {
			enqueuer.enqueueDistributedEvent(event("e1"), DISTRIBUTED_TOPIC);
			enqueuer.enqueueLocalEvent(event("e2"), LOCAL_TOPIC);
			enqueuer.enqueueDistributedEvent(event("e3"), DISTRIBUTED_TOPIC);

			assertThat(broker.getPublishes()).as("nothing is sent before the batch ends").isEmpty();
		});

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e2", "e3");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber).containsOnly(1);
		assertThat(broker.getPublishes()).extracting(Publish::getExchange)
				.as("distributed events still go to the topic's exchange, local events to the default exchange (routed to the topic's queue)")
				.containsExactly("metasfresh-events", "", "metasfresh-events");
	}

	@Test
	void nestedBatch_joinsTheOuterBatch()
	{
		enqueuer.enqueueEventsInOneBatch(() -> {
			enqueuer.enqueueDistributedEvent(event("e1"), DISTRIBUTED_TOPIC);
			enqueuer.enqueueEventsInOneBatch(() -> enqueuer.enqueueDistributedEvent(event("e2"), DISTRIBUTED_TOPIC));
			assertThat(broker.getPublishes()).as("the inner batch does not send").isEmpty();
			enqueuer.enqueueDistributedEvent(event("e3"), DISTRIBUTED_TOPIC);
		});

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e2", "e3");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber).containsOnly(1);
	}

	private void enqueueBatchOf(final String... names)
	{
		enqueuer.enqueueEventsInOneBatch(() -> {
			for (final String name : names)
			{
				enqueuer.enqueueDistributedEvent(event(name), DISTRIBUTED_TOPIC);
			}
		});
	}

	@Test
	void batch_aSendThatFailsRightAway_isRetriedOnANewChannel_thenSkipped_andTheOthersAreSent()
	{
		broker.failPublishOfBody("e2");

		enqueueBatchOf("e1", "e2", "e3", "e4");

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e3", "e4");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber)
				.as("e2 fails on channel 1 and again on channel 2; e3 and e4 go out on channel 3")
				.containsExactly(1, 3, 3);
	}

	/**
	 * E.g. a missing exchange: the broker closes the channel after the publish, so the publish of the next event fails.
	 * That next event is fine and must not be lost.
	 */
	@Test
	void batch_whenTheBrokerClosesTheChannelAfterAPublish_theNextEventIsSentOnANewChannel()
	{
		broker.closeChannelAfterPublishOfBody("e2");

		enqueueBatchOf("e1", "e2", "e3", "e4");

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e2", "e3", "e4");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber).containsExactly(1, 1, 2, 2);
	}

	@Test
	void batchOfOneEvent_aFailingSendIsLogged_notThrown()
	{
		broker.failPublishOfBody("e1");

		enqueueBatchOf("e1");

		assertThat(broker.getPublishes()).isEmpty();
	}

	@Test
	void batch_whenTheCodeThrows_theEventsEnqueuedSoFarAreStillSent_andTheBatchIsClosed()
	{
		assertThatThrownBy(() -> enqueuer.enqueueEventsInOneBatch(() -> {
			enqueuer.enqueueDistributedEvent(event("e1"), DISTRIBUTED_TOPIC);
			throw new IllegalStateException("test");
		})).isInstanceOf(IllegalStateException.class);

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1");

		enqueuer.enqueueDistributedEvent(event("e2"), DISTRIBUTED_TOPIC);
		assertThat(broker.getPublishes()).as("after the batch, events are sent right away again").extracting(Publish::getBody).containsExactly("e1", "e2");
	}
}
