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
import de.metas.event.log.EventLogService;
import de.metas.event.log.EventLogsRepository;
import de.metas.event.remote.rabbitmq.RabbitMQDestinationResolver;
import de.metas.event.remote.rabbitmq.queues.default_queue.DefaultQueueConfiguration;
import de.metas.event.log.EventLogId;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventBusEnqueueEventsTest
{
	private static final Topic TOPIC = Topic.distributed("de.metas.test.distributed");

	private ChannelRecordingRabbitTemplateFactory broker;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		broker = new ChannelRecordingRabbitTemplateFactory();
	}

	private EventBus newEventBus(@NonNull final EventLogService eventLogService)
	{
		final RabbitMQEnqueuer enqueuer = new RabbitMQEnqueuer(
				broker.newRabbitTemplate(sentObject -> ((Event)sentObject).getEventName()),
				new RabbitMQDestinationResolver(ImmutableList.of(new DefaultQueueConfiguration())));

		return new EventBus(
				TOPIC,
				null,
				EventBusFactory.createMicrometerEventBusStatsCollector(TOPIC, new SimpleMeterRegistry()),
				enqueuer,
				new EventBusMonitoringService(NoopPerformanceMonitoringService.INSTANCE),
				eventLogService);
	}

	private static Event loggedEvent(final String name)
	{
		return Event.builder().setEventName(name).shallBeLogged().build();
	}

	@Test
	void eventsAreSentInOrder_onOneChannel()
	{
		final EventBus eventBus = newEventBus(new EventLogService(new EventLogsRepository()));

		eventBus.enqueueEvents(ImmutableList.of(loggedEvent("e1"), loggedEvent("e2"), loggedEvent("e3")));

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e2", "e3");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber).containsOnly(1);
	}

	@Test
	void anEventWhoseLogCannotBeSaved_isSkipped_andTheOthersAreStillSent()
	{
		final EventLogService eventLogServiceFailingForE2 = new EventLogService(new EventLogsRepository())
		{
			@Override
			public EventLogId saveEvent(@NonNull final Event event, @NonNull final Topic eventBusTopic)
			{
				if ("e2".equals(event.getEventName()))
				{
					throw new IllegalStateException("Simulated failure to save the event log");
				}
				return super.saveEvent(event, eventBusTopic);
			}
		};
		final EventBus eventBus = newEventBus(eventLogServiceFailingForE2);

		eventBus.enqueueEvents(ImmutableList.of(loggedEvent("e1"), loggedEvent("e2"), loggedEvent("e3")));

		assertThat(broker.getPublishes()).extracting(Publish::getBody).containsExactly("e1", "e3");
		assertThat(broker.getPublishes()).extracting(Publish::getChannelNumber).containsOnly(1);
	}
}
