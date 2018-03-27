package de.metas.event.remote;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

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

@Configuration
@Profile("!eventbus_activemq")
@EnableRabbit // needed for @RabbitListener to be considered
public class AMQPEventBusConfiguration
{
	public static final String EVENTS_QUEUE_BEAN_NAME = "metasfreshEventsQueue";
	public static final String EVENTS_QUEUE_NAME_SPEL = "#{metasfreshEventsQueue.name}";
	public static final String EVENTS_EXCHANGE_NAME = "metasfresh-events";

	private static final AnonymousQueue.NamingStrategy eventQueueNamingStrategy = new AnonymousQueue.Base64UrlNamingStrategy("metasfresh.events-");

	@Bean(EVENTS_QUEUE_BEAN_NAME)
	public AnonymousQueue eventsQueue()
	{
		return new AnonymousQueue(eventQueueNamingStrategy);
	}

	@Bean
	public FanoutExchange eventsExchange()
	{
		return new FanoutExchange(EVENTS_EXCHANGE_NAME);
	}

	@Bean
	public Binding eventsBinding()
	{
		return BindingBuilder.bind(eventsQueue()).to(eventsExchange());
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

	@Bean
	public RabbitMQEventBusRemoteEndpoint eventBusRemoteEndpoint(final AmqpTemplate amqpTemplate)
	{
		return new RabbitMQEventBusRemoteEndpoint(amqpTemplate);
	}
}
