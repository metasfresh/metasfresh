/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.cucumber.stepdefs.externalsystem.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.CommandLineParser;
import de.metas.JsonObjectMapperHolder;
import de.metas.ServerBoot;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.externalsystem.JsonExternalSystemMessageTypeEnum;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_ES_TO_MF_CUSTOM;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES_CUSTOM;
import static org.assertj.core.api.Assertions.*;

public class ExternalSystem_Authorization_StepDef
{
	private final ConnectionFactory externalSystemsAuthorizationFactory;

	final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

	public ExternalSystem_Authorization_StepDef()
	{
		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull(); // guard

		externalSystemsAuthorizationFactory = new ConnectionFactory();
		externalSystemsAuthorizationFactory.setHost(commandLineOptions.getRabbitHost());
		externalSystemsAuthorizationFactory.setPort(commandLineOptions.getRabbitPort());
		externalSystemsAuthorizationFactory.setUsername(commandLineOptions.getRabbitUser());
		externalSystemsAuthorizationFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	@And("send authorization request towards external system to metasfresh custom queue")
	public void request_external_system_authorization() throws IOException, TimeoutException
	{
		final JsonExternalSystemMessage message = JsonExternalSystemMessage.builder()
				.type(JsonExternalSystemMessageTypeEnum.REQUEST_AUTHORIZATION)
				.build();

		final String string = objectMapper.writeValueAsString(message);

		try (final Connection connection = externalSystemsAuthorizationFactory.newConnection())
		{
			final Channel channel = connection.createChannel();
			final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.contentEncoding(StandardCharsets.UTF_8.displayName())
					.contentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();

			channel.basicPublish("", QUEUE_NAME_ES_TO_MF_CUSTOM, properties, string.getBytes());
			System.out.println("[x] Sent on queue '" + QUEUE_NAME_ES_TO_MF_CUSTOM + "': message= '" + string + "'");
		}
	}

	@Then("receive authorization token reply from metasfresh to external system custom queue")
	public void validate_authorization_token_obtained() throws InterruptedException, IOException, TimeoutException
	{
		final JsonExternalSystemMessage message = receiveRequest(JsonExternalSystemMessage.class);

		assertThat(message).isNotNull();
		assertThat(message.getPayload()).isNotBlank();

		final JsonExternalSystemMessagePayload messagePayload = objectMapper.readValue(message.getPayload(), JsonExternalSystemMessagePayload.class);

		assertThat(messagePayload).isNotNull();
		assertThat(messagePayload.getAuthToken()).isNotBlank();
	}

	private <T extends JsonExternalSystemMessage> T receiveRequest(@NonNull final Class<T> clazz) throws IOException, TimeoutException, InterruptedException
	{
		final Connection connection = externalSystemsAuthorizationFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		final String[] message = { null };
		final DefaultConsumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body)
			{
				message[0] = new String(body, StandardCharsets.UTF_8);
				System.out.println("\n[x] Received on queue '" + QUEUE_NAME_MF_TO_ES_CUSTOM + "': message= '" + message[0] + "'");
				countDownLatch.countDown();
			}
		};
		channel.basicConsume(QUEUE_NAME_MF_TO_ES_CUSTOM, true, consumer);

		System.out.print("Waiting for message receipt");
		final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS); // wait for the token reply to get added in the queue
		assertThat(messageReceivedWithinTimeout).isTrue();

		final JsonExternalSystemMessage requestToExternalSystemMessage = objectMapper.readValue(message[0], JsonExternalSystemMessage.class);
		assertThat(requestToExternalSystemMessage).isInstanceOf(clazz);
		return (T)requestToExternalSystemMessage;
	}
}
