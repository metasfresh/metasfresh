package de.metas.event.remote.rabbitmq.queues.default_queue;

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
public class DefaultQueueConfiguration implements IEventBusQueueConfiguration
{
	public static final String QUEUE_BEAN_NAME = "metasfreshEventsQueue";
	static final String QUEUE_NAME_SPEL = "#{metasfreshEventsQueue.name}";
	private static final String EXCHANGE_NAME_PREFIX = "metasfresh-events";

	@Value(RabbitMQEventBusConfiguration.APPLICATION_NAME_SPEL)
	private String appName;

	@Bean(QUEUE_BEAN_NAME)
	public AnonymousQueue eventsQueue()
	{
		final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy("metasfresh.events." + appName + "-");
		return new AnonymousQueue(eventQueueNamingStrategy);
	}

	@Bean
	public DirectExchange eventsExchange()
	{
		return new DirectExchange(EXCHANGE_NAME_PREFIX);
	}

	@Bean
	public Binding eventsBinding()
	{
		return BindingBuilder.bind(eventsQueue())
				.to(eventsExchange()).with(EXCHANGE_NAME_PREFIX);
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
	public String getExchangeName()
	{
		return eventsExchange().getName();
	}
}
