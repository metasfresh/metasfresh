package de.metas.vertical.pharma.msv3.server.peer;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
/*
 * #%L
 * metasfresh-pharma.msv3.server-peer-metasfresh
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
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

@Profile("!test")
@Configuration
public class RabbitMQConfig
{
	public static final String QUEUENAME_MSV3ServerRequests = "msv3-server-requests";
	public static final String QUEUENAME_UserChangedEvents = "msv3-server-UserChangedEvents";
	public static final String QUEUENAME_StockAvailabilityUpdatedEvent = "msv3-server-StockAvailabilityUpdatedEvents";
	public static final String QUEUENAME_SyncOrderRequestEvents = "msv3-server-SyncOrderRequestEvents";
	public static final String QUEUENAME_SyncOrderResponseEvents = "msv3-server-SyncOrderResponseEvents";

	@Bean
	List<Declarable> queuesAndBindings()
	{
		return ImmutableList.<Declarable> builder()
				.addAll(createQueueExchangeAndBinding(QUEUENAME_MSV3ServerRequests))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_UserChangedEvents))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_StockAvailabilityUpdatedEvent))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_SyncOrderRequestEvents))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_SyncOrderResponseEvents))
				.build();
	}

	private static final List<Declarable> createQueueExchangeAndBinding(final String queueName)
	{
		final Queue queue = QueueBuilder.nonDurable(queueName).build();
		final TopicExchange exchange = new TopicExchange(queueName + "-exchange");
		final Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName);
		return ImmutableList.<Declarable> of(queue, exchange, binding);
	}

	@Bean
	public org.springframework.amqp.support.converter.MessageConverter amqpMessageConverter(final ObjectMapper jsonObjectMapper)
	{
		return new Jackson2JsonMessageConverter(jsonObjectMapper);
	}

	@Bean
	public org.springframework.messaging.converter.MessageConverter messageConverter()
	{
		return new MappingJackson2MessageConverter();
	}

	@Bean
	public MessageHandlerMethodFactory messageHandlerMethodFactory()
	{
		final DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(messageConverter());
		return factory;
	}

	@Configuration
	@EnableRabbit // needed for @RabbitListener to be considered
	@ConditionalOnBean(RabbitTemplate.class) // skip it if the RabbitAutoConfiguration was excluded
	public static class EnableRabbitListeners
	{
	}
}
