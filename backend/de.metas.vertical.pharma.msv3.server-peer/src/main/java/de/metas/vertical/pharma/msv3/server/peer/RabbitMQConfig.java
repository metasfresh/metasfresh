package de.metas.vertical.pharma.msv3.server.peer;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.google.common.collect.ImmutableList;

@Profile("!test")
@Configuration
@EnableRabbit
public class RabbitMQConfig
{
	public static final String QUEUENAME_MSV3ServerRequests = "msv3-server-requests";
	public static final String QUEUENAME_UserChangedEvents = "msv3-server-UserChangedEvents";
	public static final String QUEUENAME_StockAvailabilityUpdatedEvent = "msv3-server-StockAvailabilityUpdatedEvents";
	public static final String QUEUENAME_ProductExcludeUpdatedEvents = "msv3-server-ProductExcludeUpdatedEvents";
	public static final String QUEUENAME_SyncOrderRequestEvents = "msv3-server-SyncOrderRequestEvents";
	public static final String QUEUENAME_SyncOrderResponseEvents = "msv3-server-SyncOrderResponseEvents";

	@Bean(name = QUEUENAME_MSV3ServerRequests)
	public Queue msv3ServerRequestsQueue()
	{
		return new Queue(QUEUENAME_MSV3ServerRequests);
	}

	@Bean(name = QUEUENAME_UserChangedEvents)
	public Queue userChangedEventsQueue()
	{
		return new Queue(QUEUENAME_UserChangedEvents);
	}

	@Bean(name = QUEUENAME_StockAvailabilityUpdatedEvent)
	public Queue stockAvailabilityUpdatedEventQueue()
	{
		return new Queue(QUEUENAME_StockAvailabilityUpdatedEvent);
	}
	
	@Bean(name = QUEUENAME_ProductExcludeUpdatedEvents)
	public Queue productExcludeUpdatedEventsQueue()
	{
		return new Queue(QUEUENAME_ProductExcludeUpdatedEvents);
	}

	@Bean(name = QUEUENAME_SyncOrderRequestEvents)
	public Queue syncOrderRequestEventsQueue()
	{
		return new Queue(QUEUENAME_SyncOrderRequestEvents);
	}

	@Bean(name = QUEUENAME_SyncOrderResponseEvents)
	public Queue syncOrderResponseEventsQueue()
	{
		return new Queue(QUEUENAME_SyncOrderResponseEvents);
	}

	// Note: with spring boot-2 this somehow didn't work anymore. It didn't create the queues in rabbitmq, so i added the code above, which works.
	// @Bean
	// List<Declarable> queuesAndBindings()
	// {
	// 	return ImmutableList.<Declarable> builder()
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_MSV3ServerRequests))
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_UserChangedEvents))
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_StockAvailabilityUpdatedEvent))
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_ProductExcludeUpdatedEvents))
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_SyncOrderRequestEvents))
	// 			.addAll(createQueueExchangeAndBinding(QUEUENAME_SyncOrderResponseEvents))
	// 			.build();
	// }

	// private static List<Declarable> createQueueExchangeAndBinding(final String queueName)
	// {
	// 	final Queue queue = QueueBuilder.nonDurable(queueName).build();
	// 	final TopicExchange exchange = new TopicExchange(queueName + "-exchange");
	// 	final Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName);
	// 	return ImmutableList.<Declarable> of(queue, exchange, binding);
	// }
}
