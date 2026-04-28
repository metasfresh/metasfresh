package de.metas.event.remote.rabbitmq.queues.material_dispo;

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
public class MaterialEventsQueueConfiguration implements IEventBusQueueConfiguration
{
	public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.material");
	static final String QUEUE_NAME_SPEL = "#{metasfreshMaterialEventsQueue.name}";
	private static final String QUEUE_BEAN_NAME = "metasfreshMaterialEventsQueue";
	private static final String EXCHANGE_NAME_PREFIX = "metasfresh-material-events";

	@Value(RabbitMQEventBusConfiguration.APPLICATION_NAME_SPEL)
	private String appName;

	@Bean(QUEUE_BEAN_NAME)
	public AnonymousQueue materialEventsQueue()
	{
		final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
		return new AnonymousQueue(eventQueueNamingStrategy);
	}

	@Bean
	public DirectExchange materialEventsExchange()
	{
		return new DirectExchange(EXCHANGE_NAME_PREFIX);
	}

	@Bean
	public Binding materialEventsBinding()
	{
		return BindingBuilder.bind(materialEventsQueue())
				.to(materialEventsExchange()).with(EXCHANGE_NAME_PREFIX);
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
	public String getExchangeName()
	{
		return materialEventsExchange().getName();
	}
}
