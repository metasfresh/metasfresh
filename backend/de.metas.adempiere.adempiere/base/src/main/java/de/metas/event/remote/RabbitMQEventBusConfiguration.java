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
<<<<<<< HEAD
import org.springframework.amqp.core.FanoutExchange;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	private static final String FANOUT_SUFFIX = "-fanout";
	private static final String DIRECT_SUFFIX = "-direct";
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	 * Thx to https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html#boot-features-rabbitmq
=======
	 * Thx to <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-messaging.html#boot-features-rabbitmq">spring-boot-features-rabbitmq</a>
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
		public DirectExchange eventsExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding eventsBinding()
		{
			return BindingBuilder.bind(eventsQueue())
					.to(eventsExchange()).with(EXCHANGE_NAME_PREFIX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		public String getFanoutExchangeName()
		{
			return fanoutEventsExchange().getName();
=======
		public String getExchangeName()
		{
			return eventsExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
		public DirectExchange cacheInvalidationExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding cacheInvalidationBinding()
		{
			return BindingBuilder.bind(cacheInvalidationQueue())
					.to(cacheInvalidationExchange()).with(EXCHANGE_NAME_PREFIX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		public String getFanoutExchangeName()
		{
			return fanoutCacheInvalidationExchange().getName();
=======
		public String getExchangeName()
		{
			return cacheInvalidationExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
		public DirectExchange accountingExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding accountingBinding()
		{
			return BindingBuilder.bind(accountingQueue())
					.to(accountingExchange()).with(EXCHANGE_NAME_PREFIX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		public String getFanoutExchangeName()
		{
			return fanoutAccountingExchange().getName();
=======
		public String getExchangeName()
		{
			return accountingExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
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
=======
		public DirectExchange schedulerExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding schedulerBinding()
		{
			return BindingBuilder.bind(schedulerQueue())
					.to(schedulerExchange()).with(EXCHANGE_NAME_PREFIX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		public String getFanoutExchangeName()
		{
			return fanoutSchedulerExchange().getName();
=======
		public String getExchangeName()
		{
			return schedulerExchange().getName();
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
		public DirectExchange asyncBatchExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding asyncBatchBinding()
		{
			return BindingBuilder.bind(asyncBatchQueue())
					.to(asyncBatchExchange()).with(EXCHANGE_NAME_PREFIX);
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
		public String getExchangeName()
		{
			return asyncBatchExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	@Configuration
	public static class MaterialEventsQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final String QUEUE_NAME_SPEL = "#{metasfreshMaterialEventsQueue.name}";
<<<<<<< HEAD
		private static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.material");
=======
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.material");
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		private static final String QUEUE_BEAN_NAME = "metasfreshMaterialEventsQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-material-events";

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
		public AnonymousQueue materialEventsQueue()
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
<<<<<<< HEAD
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
=======
		public DirectExchange materialEventsExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding materialEventsBinding()
		{
			return BindingBuilder.bind(materialEventsQueue())
					.to(materialEventsExchange()).with(EXCHANGE_NAME_PREFIX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
		public String getFanoutExchangeName()
		{
			return fanoutMaterialEventsExchange().getName();
=======
		public String getExchangeName()
		{
			return materialEventsExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}

	@Configuration
<<<<<<< HEAD
	public static class AsyncBatchQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.async.eventbus.AsyncBatchNotifyRequest");
		private static final String QUEUE_BEAN_NAME = "metasfreshAsyncBatchQueue";
		private static final String EXCHANGE_NAME = "metasfresh-async-batch-events";
=======
	public static class EffortControlQueueConfiguration implements IEventBusQueueConfiguration
	{
		public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.serviceprovider.eventbus.EffortControlEventRequest");
		public static final String QUEUE_NAME_SPEL = "#{metasfreshEffortControlQueue.name}";
		private static final String QUEUE_BEAN_NAME = "metasfreshEffortControlQueue";
		private static final String EXCHANGE_NAME_PREFIX = "metasfresh-effort-control-events";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		@Value(APPLICATION_NAME_SPEL)
		private String appName;

		@Bean(QUEUE_BEAN_NAME)
<<<<<<< HEAD
		public AnonymousQueue asyncBatchQueue()
=======
		public AnonymousQueue effortControlQueue()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		{
			final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
			return new AnonymousQueue(eventQueueNamingStrategy);
		}

		@Bean
<<<<<<< HEAD
		public FanoutExchange asyncBatchExchange()
		{
			return new FanoutExchange(EXCHANGE_NAME);
		}

		@Bean
		public Binding asyncBatchBinding()
		{
			return BindingBuilder.bind(asyncBatchQueue()).to(asyncBatchExchange());
=======
		public DirectExchange effortControlExchange()
		{
			return new DirectExchange(EXCHANGE_NAME_PREFIX);
		}

		@Bean
		public Binding effortControlBinding()
		{
			return BindingBuilder.bind(effortControlQueue())
					.to(effortControlExchange()).with(EXCHANGE_NAME_PREFIX);
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
		public String getExchangeName()
		{
			return effortControlExchange().getName();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		}
	}
}
