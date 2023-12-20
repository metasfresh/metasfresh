/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

import de.metas.common.util.time.SystemTime;
import de.metas.event.Event;
import de.metas.event.EventBusConfig;
import de.metas.event.EventEnqueuer;
import de.metas.event.Topic;
import de.metas.event.remote.RabbitMQDestinationResolver;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.metas.event.EventBusConfig.getSenderId;
import static de.metas.event.remote.RabbitMQEventBusRemoteEndpoint.HEADER_SenderId;
import static de.metas.event.remote.RabbitMQEventBusRemoteEndpoint.HEADER_TopicName;
import static de.metas.event.remote.RabbitMQEventBusRemoteEndpoint.HEADER_TopicType;

@Component
public class RabbitMQEnqueuer implements EventEnqueuer
{
	private static final transient Logger logger = EventBusConfig.getLogger(RabbitMQEnqueuer.class);

	private final AmqpTemplate amqpTemplate;
	private final RabbitMQDestinationResolver rabbitMQDestinationResolver;

	public RabbitMQEnqueuer(
			@NonNull final AmqpTemplate amqpTemplate,
			@NonNull final RabbitMQDestinationResolver rabbitMQDestinationResolver)
	{
		this.amqpTemplate = amqpTemplate;
		this.rabbitMQDestinationResolver = rabbitMQDestinationResolver;
	}

	@Override
	public void enqueueDistributedEvent(final Event event, final Topic topic)
	{
		final String amqpExchangeName = rabbitMQDestinationResolver.getAMQPExchangeNameByTopicName(topic.getName());
		final String routingKey = ""; // ignored for fan-out exchanges
		amqpTemplate.convertAndSend(
				amqpExchangeName,
				routingKey,
				event,
				getMessagePostProcessor(topic));

		logger.debug("Send event; topicName={}; event={}; type={}; timestamp={}, ThreadId={}", topic.getName(), event, topic.getType(), SystemTime.asTimestamp(), Thread.currentThread().getId());
	}

	@Override
	public void enqueueLocalEvent(final Event event, final Topic topic)
	{
		final String queueName = rabbitMQDestinationResolver.getAMQPQueueNameByTopicName(topic.getName());

		amqpTemplate.convertAndSend(queueName,
									event,
									getMessagePostProcessor(topic));

		logger.debug("Send event; topicName={}; event={}; type={}", topic.getName(), event, topic.getType());
	}

	@NonNull
	private MessagePostProcessor getMessagePostProcessor(@NonNull final Topic topic)
	{
		return message -> {
			final Map<String, Object> headers = message.getMessageProperties().getHeaders();
			headers.put(HEADER_SenderId, getSenderId());
			headers.put(HEADER_TopicName, topic.getName());
			headers.put(HEADER_TopicType, topic.getType());
			return message;
		};
	}
}
