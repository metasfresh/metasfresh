package de.metas.event.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.event.Topic;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Base64UrlNamingStrategy;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.NamingStrategy;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionNameStrategy;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import java.util.Optional;
import java.util.function.Function;

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
	 * <p>
	 * (although right now it doesn't need to work..)
	 */
	@Bean
	public ConnectionNameStrategy connectionNameStrategy()
	{
		return connectionFactory -> appName;
	}

	@Bean
	public EventBusMonitoringService eventBusMonitoringService(@NonNull final Optional<PerformanceMonitoringService> performanceMonitoringService)
	{
		return new EventBusMonitoringService(performanceMonitoringService.orElse(NoopPerformanceMonitoringService.INSTANCE));
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
		else if (ManageSchedulerQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return ManageSchedulerQueueConfiguration.EXCHANGE_NAME;
		}
		else if (AsyncBatchQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return AsyncBatchQueueConfiguration.EXCHANGE_NAME;
		}
		else
		{
			return DefaultQueueConfiguration.EXCHANGE_NAME;
		}
	}

	@NonNull
	public static String getAMQPQueueNameByTopicName(final String topicName)
	{
		final Function<String, String> getLocalQueueName = (beanName) ->
				SpringContextHolder.instance.getBean(AnonymousQueue.class, beanName).getName();

		if (AccountingQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return getLocalQueueName.apply(AccountingQueueConfiguration.QUEUE_BEAN_NAME);
		}
		else if (CacheInvalidationQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return getLocalQueueName.apply(CacheInvalidationQueueConfiguration.QUEUE_BEAN_NAME);
		}
		else if (ManageSchedulerQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return getLocalQueueName.apply(ManageSchedulerQueueConfiguration.QUEUE_BEAN_NAME);
		}
		else if (AsyncBatchQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return getLocalQueueName.apply(AsyncBatchQueueConfiguration.QUEUE_BEAN_NAME);
		}
		else if (MaterialEventsQueueConfiguration.EVENTBUS_TOPIC.getName().equals(topicName))
		{
			return getLocalQueueName.apply(MaterialEventsQueueConfiguration.QUEUE_BEAN_NAME);
		}
		else
		{
			return getLocalQueueName.apply(DefaultQueueConfiguration.QUEUE_BEAN_NAME);
		}
	}

	@Configuration
	public static class DefaultQueueConfiguration
	{
		public static final String QUEUE_BEAN_NAME = "metasfreshEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue eventsQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy("metasfresh.events." + appName + "-");
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
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.cache.CacheInvalidationRemoteHandler");
		private static final String QUEUE_BEAN_NAME = "metasfreshCacheInvalidationEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshCacheInvalidationEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-cache-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue cacheInvalidationQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
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
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.acct.handler.DocumentPostRequest");
		private static final String QUEUE_BEAN_NAME = "metasfreshAccountingEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshAccountingEventsQueue.name}";
		private static final String EXCHANGE_NAME = "metasfresh-accounting-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue accountingQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
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

	@Configuration
	public static class ManageSchedulerQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.externalsystem.rabbitmq.request.ManageSchedulerRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshManageSchedulerQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshManageSchedulerQueue";
		private static final String EXCHANGE_NAME = "metasfresh-scheduler-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue schedulerQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange schedulerExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding schedulerBinding()
		{
			return BindingBuilder.bind(schedulerQueue()).to(schedulerExchange());
		}
	}

	@Configuration
	public static class AsyncBatchQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.async.eventbus.AsyncBatchNotifyRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshAsyncBatchQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshAsyncBatchQueue";
		private static final String EXCHANGE_NAME = "metasfresh-async-batch-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue asyncBatchQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange asyncBatchExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding asyncBatchBinding()
		{
			return BindingBuilder.bind(asyncBatchQueue()).to(asyncBatchExchange());
		}
	}

	@Configuration
	public static class MaterialEventsQueueConfiguration
	{
		public static final String QUEUE_NAME_SPEL = "#{metasfreshMaterialEventsQueue.name}";
		private static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.material");
		private static final String QUEUE_BEAN_NAME = "metasfreshMaterialEventsQueue";
		private static final String EXCHANGE_NAME = "metasfresh-material-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue materialEventsQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange materialEventsExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding materialEventsBinding()
		{
			return BindingBuilder.bind(materialEventsQueue()).to(materialEventsExchange());
		}
	}
}
