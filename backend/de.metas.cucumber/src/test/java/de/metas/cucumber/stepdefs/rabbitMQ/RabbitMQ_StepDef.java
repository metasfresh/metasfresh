/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.cucumber.stepdefs.rabbitMQ;

import com.google.common.collect.ImmutableList;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.event.Event;
import de.metas.event.IEventBus;
import de.metas.event.Topic;
import de.metas.event.impl.EventBusFactory;
import de.metas.event.remote.RabbitMQDestinationResolver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Base64UrlNamingStrategy;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.NamingStrategy;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

public class RabbitMQ_StepDef
{
	private final RabbitMQDestinationResolver rabbitMQDestinationSolver = SpringContextHolder.instance.getBean(RabbitMQDestinationResolver.class);
	private final EventBusFactory eventBusFactory = SpringContextHolder.instance.getBean(EventBusFactory.class);
	private final AmqpTemplate amqpTemplate = SpringContextHolder.instance.getBean(AmqpTemplate.class);

	private final Queue_StepDefData queueTable;
	private final Channel_StepDefData channelTable;
	private final Consumer_StepDefData consumerTable;
	private final Topic_StepDefData topicTable;
	private final CachingConnectionFactory connectionFactory;

	public RabbitMQ_StepDef(
			@NonNull final Queue_StepDefData queueTable,
			@NonNull final Channel_StepDefData channelTable,
			@NonNull final Topic_StepDefData topicTable,
			@NonNull final Consumer_StepDefData consumerTable)
	{
		this.queueTable = queueTable;
		this.channelTable = channelTable;
		this.topicTable = topicTable;
		this.consumerTable = consumerTable;

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull();

		connectionFactory = new CachingConnectionFactory(commandLineOptions.getRabbitHost(), commandLineOptions.getRabbitPort());
		connectionFactory.setUsername(commandLineOptions.getRabbitUser());
		connectionFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	@And("wait until de.metas.material rabbitMQ queue is empty or throw exception after 5 minutes")
	public void wait_empty_material_queue() throws InterruptedException
	{
		waitEmptyMaterialQueue();
	}

	@Given("rabbitMQ queue is created")
	public void create_queue(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createQueue(tableRow);
		}
	}

	@Given("rabbitMQ 'Event' consumer is created")
	public void create_consumer(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createConsumer(tableRow);
		}
	}

	@Given("rabbitMQ channel is created")
	public void create_channel(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createChannel(tableRow);
		}
	}

	@Given("register consumer for queue")
	public void register_consumer_for_queue(@NonNull final DataTable dataTable) throws IOException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			registerConsumerForQueue(tableRow);
		}
	}

	@When("enqueue 'Event'")
	public void enqueue_Event(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			enqueueEvent(tableRow);
		}
	}

	@Then("consumer {string} receives messages")
	public void consumer_receives_number_of_messages(
			@NonNull final String consumerIdentifier,
			@NonNull final DataTable dataTable) throws InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);

		final Stream<String> expectedBodiesStream = tableRows.stream()
				.map(row -> DataTableUtil.extractStringForColumnName(row, "Event.body"));

		consumerReceivesEvents(expectedBodiesStream, consumerIdentifier);
	}

	@Then("^consumer (.*) receives no message within the next (.)s$")
	public void consumer_receives_no_message(@NonNull final String consumerIdentifier, final int timeoutSec) throws InterruptedException
	{
		final RabbitMQConsumer consumer = consumerTable.get(consumerIdentifier);

		final String receivedMessage = consumer.waitForMessage(timeoutSec);

		assertThat(receivedMessage).isNull();
	}

	@Then("channel is closed")
	public void close_channel(@NonNull final DataTable dataTable) throws IOException, TimeoutException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			closeChannel(tableRow);
		}
	}

	@And("serverBoot local queue is created")
	public void create_serverBoot_queue(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			createServerBootQueue(tableRow);
		}
	}

	@Then("deregister queue for topic")
	public void deregister_queue_for_topic(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{
			deregisterQueueForTopic(tableRow);
		}
	}

	private void deregisterQueueForTopic(@NonNull final Map<String, String> tableRow)
	{
		final String topicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Identifier");

		final Topic topic = topicTable.get(topicIdentifier);

		rabbitMQDestinationSolver.deregisterQueue(topic.getName());
	}

	private void createServerBootQueue(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "Identifier");
		final String topicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Identifier");
		final String exchangeNamePrefix = DataTableUtil.extractStringForColumnName(tableRow, "ExchangeNamePrefix");

		final String fanoutExchangeName = exchangeNamePrefix + "-fanout";
		final String directExchangeName = exchangeNamePrefix + "-direct";

		final Topic topic = topicTable.get(topicIdentifier);
		final RabbitMQTestConfiguration testRabbitMQConfiguration = new RabbitMQTestConfiguration(topic, fanoutExchangeName);
		final AnonymousQueue anonymousQueue = testRabbitMQConfiguration.getQueue();

		final RabbitAdmin admin = new RabbitAdmin(connectionFactory);

		final FanoutExchange fanoutExchange = new FanoutExchange(fanoutExchangeName, false, true);
		final DirectExchange directExchange = new DirectExchange(directExchangeName, false, true);

		admin.declareExchange(fanoutExchange);
		admin.declareExchange(directExchange);
		admin.declareQueue(anonymousQueue);
		admin.declareBinding(BindingBuilder.bind(anonymousQueue).to(fanoutExchange));
		admin.declareBinding(BindingBuilder.bind(anonymousQueue).to(directExchange).with(anonymousQueue.getName()));

		rabbitMQDestinationSolver.registerQueue(testRabbitMQConfiguration);

		queueTable.put(identifier, anonymousQueue);
	}

	private void closeChannel(@NonNull final Map<String, String> tableRow) throws IOException, TimeoutException
	{
		final String channelIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Channel.Identifier");

		final Channel channel = channelTable.get(channelIdentifier);
		channel.close();
	}

	private void consumerReceivesEvents(
			@NonNull final Stream<String> expectedEventBodiesStream,
			@NonNull final String consumerIdentifier) throws InterruptedException
	{
		final RabbitMQConsumer consumer = consumerTable.get(consumerIdentifier);

		final List<String> expectedEventBodiesSorted = expectedEventBodiesStream.sorted().collect(ImmutableList.toImmutableList());

		final List<String> actualEventBodies = consumer.collectMessages(expectedEventBodiesSorted.size())
				.stream()
				.map(Event::getBody)
				.sorted()
				.collect(ImmutableList.toImmutableList());

		assertThat(actualEventBodies).isEqualTo(expectedEventBodiesSorted);
	}

	private void registerConsumerForQueue(@NonNull final Map<String, String> tableRow) throws IOException
	{
		final String queueIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Queue.Identifier");
		final String channelIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Channel.Identifier");
		final String consumerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Consumer.Identifier");

		final Consumer consumer = consumerTable.get(consumerIdentifier);
		final Channel channel = channelTable.get(channelIdentifier);
		final Queue queue = queueTable.get(queueIdentifier);
		final boolean autoAck = true;

		channel.basicConsume(queue.getName(), autoAck, consumer);
	}

	private void createChannel(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "Identifier");

		final Connection connection = connectionFactory.createConnection();
		final Channel channel = connection.createChannel(true);

		channelTable.put(identifier, channel);
	}

	private void createConsumer(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "Identifier");

		final String channelIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Channel.Identifier");
		final Channel channel = channelTable.get(channelIdentifier);

		final RabbitMQConsumer consumer = new RabbitMQConsumer(channel);

		consumerTable.put(identifier, consumer);
	}

	private void createQueue(@NonNull final Map<String, String> tableRow)
	{
		final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "Identifier");
		final String exchangeNamePrefix = DataTableUtil.extractStringForColumnName(tableRow, "ExchangeNamePrefix");
		final String topicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Identifier");
		final Topic topic = topicTable.get(topicIdentifier);

		final NamingStrategy queueNamingStrategy = new Base64UrlNamingStrategy(topic.getName() + "." + "cucumber" + "-");
		final AnonymousQueue queue = new AnonymousQueue(queueNamingStrategy);

		final RabbitAdmin admin = new RabbitAdmin(connectionFactory);

		final FanoutExchange exchange = new FanoutExchange(exchangeNamePrefix + "-fanout", false, true);

		admin.declareExchange(exchange);
		admin.declareQueue(queue);
		admin.declareBinding(BindingBuilder.bind(queue).to(exchange));

		queueTable.put(identifier, queue);
	}

	private void enqueueEvent(@NonNull final Map<String, String> tableRow)
	{
		final String topicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Topic.Identifier");
		final String eventBody = DataTableUtil.extractStringForColumnName(tableRow, "Event.Body");

		final Topic topic = topicTable.get(topicIdentifier);

		final IEventBus eventBus = eventBusFactory.getEventBus(topic);
		eventBus.enqueueEvent(Event.builder()
									  .withBody(eventBody)
									  .build());
	}

	private void waitEmptyMaterialQueue() throws InterruptedException
	{
		final long nowMillis = System.currentTimeMillis();
		final long deadLineMillis = nowMillis + (300 * 1000L);    // dev-note: await maximum 5 minutes

		final String queueName = rabbitMQDestinationSolver.getAMQPQueueNameByTopicName("de.metas.material");
		final RabbitAdmin rabbitAdmin = new RabbitAdmin(((RabbitTemplate)amqpTemplate));

		long messageCount;
		do
		{
			Thread.sleep(1000);

			final QueueInformation queueInformation = Optional.ofNullable(rabbitAdmin.getQueueInfo(queueName))
					.orElseThrow(() -> new AdempiereException("Queue does not exist!")
							.appendParametersToMessage()
							.setParameter("QueueName", queueName));

			messageCount = queueInformation.getMessageCount();
		}
		while (messageCount > 0 && deadLineMillis > System.currentTimeMillis());

		if (messageCount > 0)
		{
			throw new AdempiereException("Queue has not been entirely processed in 5 minutes !")
					.appendParametersToMessage()
					.setParameter("QueueName", queueName);
		}
	}
}