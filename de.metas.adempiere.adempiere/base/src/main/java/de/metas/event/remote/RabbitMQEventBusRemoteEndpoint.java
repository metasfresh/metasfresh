package de.metas.event.remote;

import java.util.Map;
import java.util.Objects;

import org.adempiere.util.Services;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import de.metas.event.Event;
import de.metas.event.EventBusConstants;
import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.event.IEventListener;
import de.metas.event.Topic;
import de.metas.event.Type;
import de.metas.logging.LogManager;

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

// @Component
public class RabbitMQEventBusRemoteEndpoint implements IEventBusRemoteEndpoint
{
	private static final Logger logger = LogManager.getLogger(RabbitMQEventBusRemoteEndpoint.class);

	private static final String HEADER_SenderId = "metasfresh-events.SenderId";
	private static final String HEADER_TopicName = "metasfresh-events.TopicName";

	private final String senderId;
	private final AmqpTemplate amqpTemplate;

	private final IEventListener eventBus2amqpListener = EventBus2RemoteEndpointHandler.newInstance(this);

	public RabbitMQEventBusRemoteEndpoint(final AmqpTemplate amqpTemplate)
	{
		senderId = EventBusConstants.getSenderId();
		this.amqpTemplate = amqpTemplate;
	}

	@RabbitListener(queues = AMQPEventBusConfiguration.EVENTS_QUEUE_NAME_SPEL)
	public void onRemoteEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName)
	{
		try
		{
			if (Objects.equals(getSenderId(), senderId))
			{
				return;
			}

			final Topic topic = Topic.of(topicName, Type.REMOTE);
			final IEventBus eventBus = Services.get(IEventBusFactory.class).getEventBusIfExists(topic);
			if (eventBus == null)
			{
				return;
			}

			event.markReceivedByEventBusId(createEventBusId(topicName));

			eventBus.postEvent(event);

			final long durationMillis = System.currentTimeMillis() - event.getWhen().toEpochMilli();
			System.out.println("Received event in " + durationMillis + "ms, topic=" + topicName + ": " + event);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed forwarding event to topic {}: {}", topicName, event, ex);
		}
	}

	@Override
	public void sendEvent(final String topicName, final Event event)
	{
		try
		{
			// If the event comes from this JMS, don't forward it back
			final String jmsEventBusId = createEventBusId(topicName);
			if (event.wasReceivedByEventBusId(jmsEventBusId))
			{
				return;
			}

			amqpTemplate.convertAndSend(AMQPEventBusConfiguration.EVENTS_EXCHANGE_NAME, "", event, message -> {
				final Map<String, Object> headers = message.getMessageProperties().getHeaders();
				headers.put(HEADER_SenderId, getSenderId());
				headers.put(HEADER_TopicName, topicName);
				return message;
			});

			logger.debug("AMQP: send event: {}", event);
			System.out.println("AMQP: send event to topicName=" + topicName + ": " + event);
		}
		catch (final Exception e)
		{
			logger.warn("Failed to send {} to {}. Ignored.", event, topicName, e);
		}
	}

	private final String createEventBusId(final String topicName)
	{
		return getSenderId() + "_" + topicName;
	}

	private String getSenderId()
	{
		return senderId;
	}

	@Override
	public boolean bindIfNeeded(final IEventBus eventBus)
	{
		eventBus.subscribe(eventBus2amqpListener);

		// TODO Auto-generated method stub
		return false;
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
