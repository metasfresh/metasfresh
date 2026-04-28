package de.metas.event.remote.rabbitmq.queues.cache_invalidation;

import de.metas.event.Topic;
import de.metas.event.remote.IEventBusQueueConfiguration;
import de.metas.event.remote.rabbitmq.RabbitMQEventBusConfiguration;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Base64UrlNamingStrategy;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.NamingStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class CacheInvalidationQueueConfiguration implements IEventBusQueueConfiguration
{
	public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.cache.CacheInvalidationRemoteHandler");
	static final String QUEUE_NAME_SPEL = "#{metasfreshCacheInvalidationEventsQueue.name}";
	private static final String QUEUE_BEAN_NAME = "metasfreshCacheInvalidationEventsQueue";
	private static final String EXCHANGE_NAME_PREFIX = "metasfresh-cache-events";

	@Value(RabbitMQEventBusConfiguration.APPLICATION_NAME_SPEL)
	private String appName;

	@Bean(QUEUE_BEAN_NAME)
	public AnonymousQueue cacheInvalidationQueue()
	{
		final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
		return new AnonymousQueue(eventQueueNamingStrategy);
	}

	@Bean
	public DirectExchange cacheInvalidationExchange()
	{
		return new DirectExchange(EXCHANGE_NAME_PREFIX);
	}

	@Bean
	public Binding cacheInvalidationBinding()
	{
		return BindingBuilder.bind(cacheInvalidationQueue())
				.to(cacheInvalidationExchange()).with(EXCHANGE_NAME_PREFIX);
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
	public String getExchangeName()
	{
		return cacheInvalidationExchange().getName();
	}
}
