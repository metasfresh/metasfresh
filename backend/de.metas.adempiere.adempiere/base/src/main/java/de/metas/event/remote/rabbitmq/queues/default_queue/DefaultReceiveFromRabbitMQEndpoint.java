package de.metas.event.remote.rabbitmq.queues.default_queue;

import de.metas.event.Event;
import de.metas.event.IEventBusFactory;
import de.metas.event.Type;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.event.remote.rabbitmq.RabbitMQReceiveFromEndpointTemplate;
import lombok.NonNull;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

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
public class DefaultReceiveFromRabbitMQEndpoint extends RabbitMQReceiveFromEndpointTemplate
{
	public DefaultReceiveFromRabbitMQEndpoint(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final EventBusMonitoringService eventBusMonitoringService)
	{
		super(eventBusFactory, eventBusMonitoringService);
	}

	@RabbitListener(queues = { DefaultQueueConfiguration.QUEUE_NAME_SPEL })
	public void onEvent(
			@Payload final Event event,
			@Header(HEADER_SenderId) final String senderId,
			@Header(HEADER_TopicName) final String topicName,
			@Header(HEADER_TopicType) final Type topicType)
	{
		onRemoteEvent(event, senderId, topicName, topicType);
	}
}
