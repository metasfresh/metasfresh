package de.metas.material.cockpit.availableforsales.event;

import de.metas.event.Topic;
import de.metas.event.remote.IEventBusQueueConfiguration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class EnqueueAvailableForSalesConfiguration implements IEventBusQueueConfiguration
{
	public static final Topic TOPIC = Topic.distributed("de.metas.material.cockpit.availableforsales");
	private static final String BASE_BEAN_NAME = "metasfreshMaterialCockpitAvailableForSales";
	private static final String QUEUE_BEAN_NAME = BASE_BEAN_NAME + "Queue";
	static final String QUEUE_NAME_SPEL = "#{" + QUEUE_BEAN_NAME + ".name}";
	private static final String EXCHANGE_NAME = TOPIC.getName();

	@Bean(QUEUE_BEAN_NAME)
	public Queue queue()
	{
		// final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
		// return new AnonymousQueue(eventQueueNamingStrategy);
		return new Queue(TOPIC.getName(), true);
	}

	@Bean(BASE_BEAN_NAME + "Exchange")
	public DirectExchange exchange()
	{
		return new DirectExchange(EXCHANGE_NAME);
	}

	@Bean(BASE_BEAN_NAME + "Binding")
	public Binding binding()
	{
		return BindingBuilder.bind(queue())
				.to(exchange())
				.with(EXCHANGE_NAME);
	}

	@Override
	public String getQueueName()
	{
		return queue().getName();
	}

	@Override
	public Optional<String> getTopicName()
	{
		return Optional.of(TOPIC.getName());
	}

	@Override
	public String getExchangeName()
	{
		return exchange().getName();
	}
}
