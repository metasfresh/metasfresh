/*
 * #%L
 * de-metas-federated-rabbitmq
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rabbitmq.federated;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import de.metas.common.procurement.sync.Constants;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the federated RabbitMQ setup.
 * Starts the two rabbits via Testcontainers and then runs for tests in a predefined order.
 * Each test sends to or receives from one of the two federated queues.
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FederatedRabbitMQTest
{
	private static final String procurement_webui_rabbitmq_host = "localhost";
	private static final Integer procurement_webui_rabbitmq_port = 5673;

	private static final String metasfresh_rabbitmq_host = "localhost";
	private static final Integer metasfresh_rabbitmq_port = 5674;

	public static DockerComposeContainer environment;

	private static Instant instant;

	@BeforeAll
	static void beforeAll()
	{
		environment =
				new DockerComposeContainer(new File("src/main/docker/docker-compose.yml"))
						.withExposedService("procurement_webui_rabbitmq", 5674
								, Wait.forLogMessage(".*Federation link.*authenticated and granted access to vhost.*", 1).withStartupTimeout(Duration.ofSeconds(60))
						)
						.withExposedService("metasfresh_rabbitmq", 5673
								, Wait.forLogMessage(".*Federation link.*authenticated and granted access to vhost.*", 1).withStartupTimeout(Duration.ofSeconds(60))
						)
						.withLocalCompose(true);
		environment.start();

		instant = Instant.now();
	}

	@Test
	@Order(10)
	void sendToQueueMetasfresh2ProcurementWebui() throws IOException, TimeoutException
	{
		final ConnectionFactory metasfreshFactory = new ConnectionFactory();
		metasfreshFactory.setHost(metasfresh_rabbitmq_host);
		metasfreshFactory.setPort(procurement_webui_rabbitmq_port);
		metasfreshFactory.setUsername("metasfresh");
		metasfreshFactory.setPassword("vNz4R9bGzlcGG5o6Dnnt");

		final String message = createFromMetasfreshMessage();

		try (final Connection connection = metasfreshFactory.newConnection();
				final Channel channel = connection.createChannel())
		{
			channel.basicPublish("", de.metas.common.procurement.sync.Constants.QUEUE_NAME_MF_TO_PW, null, message.getBytes());
			System.out.println("[x] Sent on queue '" + Constants.QUEUE_NAME_MF_TO_PW + "': message= '" + message + "'");
		}
	}

	@Test
	@Order(20)
	void receiveFromQueueMetasfresh2ProcurementWebui() throws IOException, InterruptedException, TimeoutException
	{
		final ConnectionFactory procurementWebuiFactory = new ConnectionFactory();
		procurementWebuiFactory.setHost(procurement_webui_rabbitmq_host);
		procurementWebuiFactory.setPort(metasfresh_rabbitmq_port);
		procurementWebuiFactory.setUsername("procurement_webui");
		procurementWebuiFactory.setPassword("9aORIRWlS7TV9VjFVNBj");

		final Connection connection = procurementWebuiFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		final String[] message = { null };
		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			message[0] = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("\n[x] Received on queue '" + Constants.QUEUE_NAME_MF_TO_PW + "': message= '" + message[0] + "'");
			countDownLatch.countDown();
		};
		channel.basicConsume(Constants.QUEUE_NAME_MF_TO_PW, true, deliverCallback, consumerTag -> {
		});

		System.out.print("Waiting for message receipt");
		assertThat(countDownLatch.await(5, TimeUnit.SECONDS)).isTrue();

		assertThat(message[0]).isEqualTo(createFromMetasfreshMessage());
	}

	@Test
	@Order(30)
	void sendToQueueProcurementWebui2Metasfresh() throws IOException, TimeoutException
	{
		final ConnectionFactory procurementWebuiFactory = new ConnectionFactory();
		procurementWebuiFactory.setHost(procurement_webui_rabbitmq_host);
		procurementWebuiFactory.setPort(metasfresh_rabbitmq_port);
		procurementWebuiFactory.setUsername("procurement_webui");
		procurementWebuiFactory.setPassword("9aORIRWlS7TV9VjFVNBj");

		final String message = createFromProcurementWebuiMessage();
		try (final Connection connection = procurementWebuiFactory.newConnection();
				final Channel channel = connection.createChannel())
		{
			channel.basicPublish("", Constants.QUEUE_NAME_PW_TO_MF, null, message.getBytes());
			System.out.println("[x] Sent on queue '" + Constants.QUEUE_NAME_PW_TO_MF + "': message= '" + message + "'");
		}
	}

	@Test
	@Order(40)
	void receiveFromQueueProcurementWebui2Metasfresh() throws IOException, InterruptedException, TimeoutException
	{
		final ConnectionFactory metasfreshFactory = new ConnectionFactory();
		metasfreshFactory.setHost(metasfresh_rabbitmq_host);
		metasfreshFactory.setPort(metasfresh_rabbitmq_port);
		metasfreshFactory.setUsername("metasfresh");
		metasfreshFactory.setPassword("vNz4R9bGzlcGG5o6Dnnt");

		final Connection connection = metasfreshFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final String[] message = { null };
		final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			message[0] = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("\n[x] Received on queue '" + Constants.QUEUE_NAME_PW_TO_MF + "': message= '" + message[0] + "'");
			countDownLatch.countDown();
		};
		channel.basicConsume(Constants.QUEUE_NAME_PW_TO_MF, true, deliverCallback, consumerTag -> {
		});
		System.out.print("Waiting for message receipt");

		assertThat(countDownLatch.await(5, TimeUnit.SECONDS)).isTrue();
		assertThat(message[0]).isEqualTo(createFromProcurementWebuiMessage());
	}

	@NonNull
	private String createFromMetasfreshMessage()
	{
		return "Hello from metasfresh! Timestamp=" + instant;
	}

	@NonNull
	private String createFromProcurementWebuiMessage()
	{
		return "Hello from the procurement-webui! Timestamp=" + instant;
	}
}
