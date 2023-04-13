package de.metas.event.remote;

import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.logging.LogManager;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Component
@EnableRabbit
public class RabbitMQEventBusRemoteEndpoint implements IEventBusRemoteEndpoint
{
	private static final Logger logger = LogManager.getLogger(RabbitMQEventBusRemoteEndpoint.class);

	public static final String HEADER_SenderId = "metasfresh-events.SenderId";
	public static final String HEADER_TopicName = "metasfresh-events.TopicName";
	public static final String HEADER_TopicType = "metasfresh-events.TopicType";

	private final String senderId;
	private final EventBusMonitoringService eventBusMonitoringService;

	private IEventBusFactory eventBusFactory;

	public RabbitMQEventBusRemoteEndpoint(@NonNull final EventBusMonitoringService eventBusMonitoringService)
	{
		senderId = EventBusConfig.getSenderId();
		this.eventBusMonitoringService = eventBusMonitoringService;
	}

	@Override
	public void setEventBusFactory(@NonNull final IEventBusFactory eventBusFactory)
	{
		this.eventBusFactory = eventBusFactory;
	}

	@RabbitListener(queues = {
			RabbitMQEventBusConfiguration.DefaultQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.AccountingQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.ManageSchedulerQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.AsyncBatchQueueConfiguration.QUEUE_NAME_SPEL,
			RabbitMQEventBusConfiguration.EffortControlQueueConfiguration.QUEUE_NAME_SPEL
	})
	public void onGenericRemoteEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName,
			@Header(HEADER_TopicType) final Type topicType)
	{
		onRemoteEvent(event, senderId, topicName, topicType);
	}

	@RabbitListener(queues = {RabbitMQEventBusConfiguration.CacheInvalidationQueueConfiguration.QUEUE_NAME_SPEL})
	public void onCacheInvalidationEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName,
			@Header(HEADER_TopicType) final Type topicType)
	{
		onRemoteEvent(event, senderId, topicName, topicType);
	}

	// dev-note: have a dedicated listener so that we have a dedicated channel
	@RabbitListener(queues = {
			RabbitMQEventBusConfiguration.MaterialEventsQueueConfiguration.QUEUE_NAME_SPEL
	})
	public void onMaterialEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName,
			@Header(HEADER_TopicType) final Type topicType)
	{
		onRemoteEvent(event, senderId, topicName, topicType);
	}

	private void onRemoteEvent(
			final Event event,
			final String senderId,
			final String topicName,
			final Type topicType)
	{
		final Topic topic = Topic.of(topicName, topicType);

		if (topic.getType() != topicType)
		{
			logger.debug("onEvent - different local topicType for topicName:[{}], remote type:[{}] local type=[{}]; -> ignoring event", topicName, topicType, topic.getType());
			return;
		}

		final IEventBus localEventBus = eventBusFactory.getEventBus(topic);

		if (Type.LOCAL == topic.getType() && !Objects.equals(getSenderId(), senderId))
		{
			logger.debug("onEvent - type LOCAL but event's senderId={} is not equal to the *local* senderId={}; -> ignoring event", senderId, getSenderId());
			return;
		}

		final boolean monitorIncomingEvents = EventBusConfig.isMonitorIncomingEvents();
		final boolean localEventBusAsync = localEventBus.isAsync();
		final Topic localEventBusTopic = localEventBus.getTopic();
		try
		{
			if (monitorIncomingEvents && !localEventBusAsync)
			{
				logger.debug("onEvent - localEventBus is not async and isMonitorIncomingEvents=true; -> monitoring event processing; localEventBus={}", localEventBus);
				eventBusMonitoringService.extractInfosAndMonitor(event,
																 localEventBusTopic,
																 () -> onRemoteEvent0(localEventBus, event, localEventBusTopic.getName()));
			}
			else
			{
				logger.debug("onEvent - localEventBus.isAsync={} and isMonitorIncomingEvents={}; -> cannot monitor event processing; localEventBus={}", localEventBusAsync, monitorIncomingEvents, localEventBus);
				onRemoteEvent0(localEventBus, event, localEventBusTopic.getName());
			}
		}
		catch (final Exception ex)
		{
			logger.warn("onEvent - Failed forwarding event to topic {}: {}", localEventBusTopic.getName(), event, ex);
		}
	}

	private void onRemoteEvent0(
			@NonNull final IEventBus localEventBus,
			@NonNull final Event event,
			@NonNull final String topicName)
	{
		localEventBus.processEvent(event);

		final long durationMillis = System.currentTimeMillis() - event.getWhen().toEpochMilli();
		logger.debug("Received and processed event in {}ms, topic={}: {}", durationMillis, topicName, event);
	}

	private String getSenderId()
	{
		return senderId;
	}

	@Override
	public boolean isConnected()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void checkConnection()
	{
		// TODO Auto-generated method stub
	}

}
