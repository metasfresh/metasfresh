package de.metas.event.remote;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.event.Topic;
import de.metas.event.impl.EventBusMonitoringService;
import de.metas.monitoring.adapter.NoopPerformanceMonitoringService;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import lombok.NonNull;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Base64UrlNamingStrategy;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
	private static final String FANOUT_SUFFIX = "-fanout";
	private static final String DIRECT_SUFFIX = "-direct";
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

	@Configuration
	public static class DefaultQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final String QUEUE_BEAN_NAME = "metasfreshEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshEventsQueue.name}";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue eventsQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy("metasfresh.events." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutEventsExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directEventsExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutEventsBinding()
		{
			return BindingBuilder.bind(eventsQueue()).to(fanoutEventsExchange());
		}

		@Bean
		public Binding directEventsBinding()
		{
			return BindingBuilder.bind(eventsQueue()).to(directEventsExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return eventsQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.empty();
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutEventsExchange().getName();
		}
	}

	@Configuration
	public static class CacheInvalidationQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.cache.CacheInvalidationRemoteHandler");
		private static final String QUEUE_BEAN_NAME = "metasfreshCacheInvalidationEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshCacheInvalidationEventsQueue.name}";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-cache-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue cacheInvalidationQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutCacheInvalidationExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directCacheInvalidationExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutCacheInvalidationBinding()
		{
			return BindingBuilder.bind(cacheInvalidationQueue()).to(fanoutCacheInvalidationExchange());
		}

		@Bean
		public Binding directCacheInvalidationBinding()
		{
			return BindingBuilder.bind(cacheInvalidationQueue()).to(directCacheInvalidationExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return cacheInvalidationQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutCacheInvalidationExchange().getName();
		}
	}

	@Configuration
	public static class AccountingQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.acct.handler.DocumentPostRequest");
		private static final String QUEUE_BEAN_NAME = "metasfreshAccountingEventsQueue";
		public static final String QUEUE_NAME_SPEL = "#{metasfreshAccountingEventsQueue.name}";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-accounting-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue accountingQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutAccountingExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directAccountingExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutAccountingBinding()
		{
			return BindingBuilder.bind(accountingQueue()).to(fanoutAccountingExchange());
		}

		@Bean
		public Binding directAccountingBinding()
		{
			return BindingBuilder.bind(accountingQueue()).to(directAccountingExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return accountingQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutAccountingExchange().getName();
		}
	}

	@Configuration
	public static class ManageSchedulerQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.externalsystem.rabbitmq.request.ManageSchedulerRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshManageSchedulerQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshManageSchedulerQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-scheduler-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue schedulerQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutSchedulerExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directSchedulerExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutSchedulerBinding()
		{
			return BindingBuilder.bind(schedulerQueue()).to(fanoutSchedulerExchange());
		}

		@Bean
		public Binding directSchedulerBinding()
		{
			return BindingBuilder.bind(schedulerQueue()).to(directSchedulerExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return schedulerQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutSchedulerExchange().getName();
		}
	}

	@Configuration
	public static class AsyncBatchQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.async.eventbus.AsyncBatchNotifyRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshAsyncBatchQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshAsyncBatchQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-async-batch-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue asyncBatchQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutAsyncBatchExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directAsyncBatchExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutAsyncBatchBinding()
		{
			return BindingBuilder.bind(asyncBatchQueue()).to(fanoutAsyncBatchExchange());
		}

		@Bean
		public Binding directAsyncBatchBinding()
		{
			return BindingBuilder.bind(asyncBatchQueue()).to(directAsyncBatchExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return asyncBatchQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutAsyncBatchExchange().getName();
		}
	}

	@Configuration
	public static class MaterialEventsQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final String QUEUE_NAME_SPEL = "#{metasfreshMaterialEventsQueue.name}";
		private static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.material");
		private static final String QUEUE_BEAN_NAME = "metasfreshMaterialEventsQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-material-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue materialEventsQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			final Map<String, Object> arguments = new HashMap<>();
			arguments.put("x-single-active-consumer", true);	// dev-note: enforce messages to be consumed and processed in the same order they arrive in the queue
			return new AnonymousQueue(eventQueueNamingStrategy, arguments);
		}

		@Bean
		public FanoutExchange fanoutMaterialEventsExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directMaterialEventsExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutMaterialEventsBinding()
		{
			return BindingBuilder.bind(materialEventsQueue()).to(fanoutMaterialEventsExchange());
		}

		@Bean
		public Binding directMaterialEventsBinding()
		{
			return BindingBuilder.bind(materialEventsQueue()).to(directMaterialEventsExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return materialEventsQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutMaterialEventsExchange().getName();
		}
	}

	@Configuration
	public static class EffortControlQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.serviceprovider.eventbus.EffortControlEventRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshEffortControlQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshEffortControlQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-effort-control-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue effortControlQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
		public FanoutExchange fanoutEffortControlExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME_PREFIX + FANOUT_SUFFIX);
		}

		@Bean
		public DirectExchange directEffortControlExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX + DIRECT_SUFFIX);
		}

		@Bean
		public Binding fanoutEffortControlBinding()
		{
			return BindingBuilder.bind(effortControlQueue()).to(fanoutEffortControlExchange());
		}

		@Bean
		public Binding directEffortControlBinding()
		{
			return BindingBuilder.bind(effortControlQueue()).to(directEffortControlExchange()).with(getQueueName());
		}

		@Override
		public String getQueueName()
		{
			return effortControlQueue().getName();
		}

		@Override
		public Optional<String> getTopicName()
		{
			return Optional.of(EVENTBUS_TOPIC.getName());
		}

		@Override
		public String getFanoutExchangeName()
		{
			return fanoutEffortControlExchange().getName();
		}
	}
}
