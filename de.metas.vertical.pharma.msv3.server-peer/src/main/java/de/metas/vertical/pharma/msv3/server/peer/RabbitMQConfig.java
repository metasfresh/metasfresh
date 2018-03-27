package de.metas.vertical.pharma.msv3.server.peer;

import java.util.List;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarable;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableList;

@Configuration
public class RabbitMQConfig
{
	public static final String QUEUENAME_MSV3ServerRequests = "msv3-server-requests";
	public static final String QUEUENAME_UserChangedEvents = "msv3-server-UserChangedEvents";
	public static final String QUEUENAME_StockAvailabilityUpdatedEvent = "msv3-server-StockAvailabilityUpdatedEvents";
	public static final String QUEUENAME_CreateOrderRequestEvents = "msv3-server-CreateOrderRequestEvents";
	public static final String QUEUENAME_CreateOrderResponseEvents = "msv3-server-CreateOrderResponseEvents";

	@Bean
	List<Declarable> queuesAndBindings()
	{
		return ImmutableList.<Declarable> builder()
				.addAll(createQueueExchangeAndBinding(QUEUENAME_MSV3ServerRequests))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_UserChangedEvents))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_StockAvailabilityUpdatedEvent))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_CreateOrderRequestEvents))
				.addAll(createQueueExchangeAndBinding(QUEUENAME_CreateOrderResponseEvents))
				.build();
	}

	private static final List<Declarable> createQueueExchangeAndBinding(final String queueName)
	{
		final Queue queue = QueueBuilder.nonDurable(queueName).build();
		final TopicExchange exchange = new TopicExchange(queueName + "-exchange");
		final Binding binding = BindingBuilder.bind(queue).to(exchange).with(queueName);
		return ImmutableList.<Declarable> of(queue, exchange, binding);
	}

//	@Bean
//	public org.springframework.amqp.support.converter.MessageConverter amqpMessageConverter(final ObjectMapper jsonObjectMapper)
//	{
//		return new Jackson2JsonMessageConverter(jsonObjectMapper);
//	}
//
//	@Bean
//	public org.springframework.messaging.converter.MessageConverter messageConverter()
//	{
//		return new MappingJackson2MessageConverter();
//	}
//
//	@Bean
//	public MessageHandlerMethodFactory messageHandlerMethodFactory()
//	{
//		final DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
//		factory.setMessageConverter(messageConverter());
//		return factory;
//	}
//
//	@Configuration
//	@EnableRabbit // needed for @RabbitListener to be considered
//	@ConditionalOnBean(RabbitTemplate.class) // skip it if the RabbitAutoConfiguration was excluded
//	public static class EnableRabbitListeners
//	{
//	}
}
