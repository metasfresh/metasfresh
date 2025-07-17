package de.metas.event.remote.rabbitmq.queues.accounting;

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
// IMPORTANT: sending accounting events shall be available for any profile, any instance
public class AccountingQueueConfiguration implements IEventBusQueueConfiguration
{
	public static final Topic EVENTBUS_TOPIC = Topic.distributed("de.metas.acct.handler.DocumentPostRequest");
	static final String QUEUE_NAME_SPEL = "#{metasfreshAccountingEventsQueue.name}";
	private static final String QUEUE_BEAN_NAME = "metasfreshAccountingEventsQueue";
	private static final String EXCHANGE_NAME_PREFIX = "metasfresh-accounting-events";

	@Bean(QUEUE_BEAN_NAME)
	public Queue accountingQueue()
	{
		// final NamingStrategy eventQueueNamingStrategy = new Base64UrlNamingStrategy(EVENTBUS_TOPIC.getName() + "." + appName + "-");
		// return new AnonymousQueue(eventQueueNamingStrategy);
		return new Queue(EVENTBUS_TOPIC.getName(), true);
	}

	@Bean
	public DirectExchange accountingExchange()
	{
		return new DirectExchange(EXCHANGE_NAME_PREFIX);
	}

	@Bean
	public Binding accountingBinding()
	{
		return BindingBuilder.bind(accountingQueue())
				.to(accountingExchange()).with(EXCHANGE_NAME_PREFIX);
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
	public String getExchangeName()
	{
		return accountingExchange().getName();
	}
}
