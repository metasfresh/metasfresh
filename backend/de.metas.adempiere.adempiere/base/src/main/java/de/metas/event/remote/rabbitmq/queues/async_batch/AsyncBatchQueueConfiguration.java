package de.metas.event.remote.rabbitmq.queues.async_batch;

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
public class AsyncBatchQueueConfiguration implements IEventBusQueueConfiguration
{
	public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.async.eventbus.AsyncBatchNotifyRequest");
	static final String QUEUE_NAME_SPEL = "#{metasfreshAsyncBatchQueue.name}";
	private static final String QUEUE_BEAN_NAME = "metasfreshAsyncBatchQueue";
	private static final String EXCHANGE_NAME_PREFIX = "metasfresh-async-batch-events";

	@Value(RabbitMQEventBusConfiguration.APPLICATION_NAME_SPEL)
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
	}
}
