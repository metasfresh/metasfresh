package de.metas.event.remote;

import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.event.Topic;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import lombok.NonNull;

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
@EnableRabbit // needed for @RabbitListener to be considered
public class RabbitMQEventBusConfiguration
{
	private static final String APPLICATION_NAME_SPEL = "${spring.application.name:spring.application.name-not-set}";
	@Value(APPLICATION_NAME_SPEL)
	private String appName;

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

	/**
	 * Attempt to set the application name (e.g. metasfresh-webui-api) as the rabbitmq connection name.
	 * Thx to https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html#boot-features-rabbitmq
	 *
	 * (although right now it doesn't need to work..)
	 */
	@Bean
	public ConnectionNameStrategy connectionNameStrategy()
	{
		return connectionFactory -> appName;
	}

	@Bean
	public RabbitMQEventBusRemoteEndpoint eventBusRemoteEndpoint(
			@NonNull final AmqpTemplate amqpTemplate,
			@NonNull final Optional<PerformanceMonitoringService> performanceMonitoringService)
	{
		return new RabbitMQEventBusRemoteEndpoint(
				amqpTemplate,
				performanceMonitoringService.orElse(NoopPerformanceMonitoringService.INSTANCE));
	}

	public static String getAMQPExchangeNameByTopicName(final String topicName)
	{
		if (AccountingQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return AccountingQueueConfiguration.EXCHANGE_NAME;
		}
		else if (CacheInvalidationQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return CacheInvalidationQueueConfiguration.EXCHANGE_NAME;
		}
		else
		{
			return DefaultQueueConfiguration.EXCHANGE_NAME;
		}
	}

	@Configuration
	public static class DefaultQueueConfiguration
	{
		private static final String QUEUE_BEAN_NAME = "metasfreshEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue eventsQueue()
		{
			final AnonymousQueue.NamingStrategy eventQueueNamingStrategy = new AnonymousQueue.Base64UrlNamingStrategy("metasfresh.events." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange eventsExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding eventsBinding()
		{
			return BindingBuilder.bind(eventsQueue()).to(eventsExchange());
		}
	}

	@Configuration
	public static class CacheInvalidationQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.remote("de.metas.cache.CacheInvalidationRemoteHandler");
		private static final String QUEUE_BEAN_NAME = "metasfreshCacheInvalidationEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshCacheInvalidationEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-cache-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue cacheInvalidationQueue()
		{
			final AnonymousQueue.NamingStrategy eventQueueNamingStrategy = new AnonymousQueue.Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange cacheInvalidationExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding cacheInvalidationBinding()
		{
			return BindingBuilder.bind(cacheInvalidationQueue()).to(cacheInvalidationExchange());
		}

	}

	@Configuration
	public static class AccountingQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.remote("de.metas.acct.handler.DocumentPostRequest");
		private static final String QUEUE_BEAN_NAME = "metasfreshAccountingEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshAccountingEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-accounting-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue accountingQueue()
		{
			final AnonymousQueue.NamingStrategy eventQueueNamingStrategy = new AnonymousQueue.Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange accountingExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding accountingBinding()
		{
			return BindingBuilder.bind(accountingQueue()).to(accountingExchange());
		}
	}
}
