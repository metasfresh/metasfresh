/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.cucumber.stepdefs.externalsystem;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.CommandLineParser;
import de.metas.JsonObjectMapperHolder;
import de.metas.ServerBoot;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefData;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BPARTNER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static org.assertj.core.api.Assertions.*;

public class MetasfreshToExternalSystemRabbitMQ_StepDef
{
	private final ConnectionFactory metasfreshToRabbitMQFactory;
	private final StepDefData<I_C_BPartner> bpartnerTable;
	private final StepDefData<I_ExternalSystem_Config> externalSystemConfigTable;

	public MetasfreshToExternalSystemRabbitMQ_StepDef(
			@NonNull final StepDefData<I_C_BPartner> bpartnerTable,
			@NonNull final StepDefData<I_ExternalSystem_Config> externalSystemConfigTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.externalSystemConfigTable = externalSystemConfigTable;

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull();

		metasfreshToRabbitMQFactory = new ConnectionFactory();
		metasfreshToRabbitMQFactory.setHost(commandLineOptions.getRabbitHost());
		metasfreshToRabbitMQFactory.setPort(commandLineOptions.getRabbitPort());
		metasfreshToRabbitMQFactory.setUsername(commandLineOptions.getRabbitUser());
		metasfreshToRabbitMQFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	@And("RabbitMQ MF_TO_ExternalSystem queue is purged")
	public void rabbitMQs_MF_TO_ExternalSystem_queue_is_empty() throws IOException, TimeoutException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		final Channel channel = connection.createChannel();
		channel.queuePurge(QUEUE_NAME_MF_TO_ES);
	}

	@Then("RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:")
	public void rabbitMQ_receives_an_external_system_request(@NonNull final DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		final JsonExternalSystemRequest requestToRabbitMQ = pollRequestFromQueue();

		final Map<String, String> tableRow = dataTable.asMaps().get(0);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String externalSystemConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config.COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");

		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(bpartner).isNotNull();

		final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(externalSystemConfigIdentifier);
		assertThat(externalSystemConfig).isNotNull();

		final String requestBPartnerId = requestToRabbitMQ.getParameters().get(PARAM_BPARTNER_ID);

		assertThat(Integer.valueOf(requestBPartnerId)).isEqualTo(bpartner.getC_BPartner_ID());
		assertThat(requestToRabbitMQ.getExternalSystemConfigId().getValue()).isEqualTo(externalSystemConfig.getExternalSystem_Config_ID());
	}

	private JsonExternalSystemRequest pollRequestFromQueue() throws IOException, TimeoutException, InterruptedException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		final String[] message = new String[1];

		final DefaultConsumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body)
			{
				message[0] = new String(body, StandardCharsets.UTF_8);
				countDownLatch.countDown();
			}
		};
		channel.basicConsume(QUEUE_NAME_MF_TO_ES, true, consumer);

		final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS);
		assertThat(messageReceivedWithinTimeout).isTrue();

		return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(message[0], JsonExternalSystemRequest.class);
	}
}