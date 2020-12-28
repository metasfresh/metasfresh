/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.CommandLineParser.CommandLineOptions;
import de.metas.ServerBoot;
import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.dto.SyncBPartner;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcurementWebToMetasfresh_StepDef
{
	private final ConnectionFactory procurementWebuiFactory;

	public ProcurementWebToMetasfresh_StepDef()
	{
		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();

		procurementWebuiFactory = new ConnectionFactory();
		procurementWebuiFactory.setHost(commandLineOptions.getRabbitHost());
		procurementWebuiFactory.setPort(commandLineOptions.getRabbitPort());
		procurementWebuiFactory.setUsername(commandLineOptions.getRabbitUser());
		procurementWebuiFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	@When("metasfresh receives a GetAllBPartnersRequest via RabbitMQ")
	public void metasfresh_receives_a_get_all_b_partners_request() throws IOException, TimeoutException
	{
		final String string = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.writeValueAsString(GetAllBPartnersRequest.INSTANCE);

		try (final Connection connection = procurementWebuiFactory.newConnection())
		{
			final Channel channel = connection.createChannel();
			final AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					.contentEncoding(StandardCharsets.UTF_8.displayName())
					.contentType(MessageProperties.CONTENT_TYPE_JSON)
					.build();

			channel.basicPublish("", Constants.QUEUE_NAME_PW_TO_MF, properties, string.getBytes());
			System.out.println("[x] Sent on queue '" + Constants.QUEUE_NAME_PW_TO_MF + "': message= '" + string + "'");
		}
	}

	@Then("metasfresh responds with a PutBPartnersRequest that contains these BPartners:")
	public void metasfresh_responds_with_a_put_b_partners_request_that_contains_these_b_partners(@NonNull final DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		final PutBPartnersRequest putBPartnersRequest = receiveRequest(PutBPartnersRequest.class);
		final List<SyncBPartner> bpartners = putBPartnersRequest.getBpartners();
		final List<Map<String, String>> tableRows = dataTable.asMaps(String.class, String.class);
		for (final Map<String, String> tableRow : tableRows)
		{

		}
	}

	private <T extends RequestToProcurementWeb> T receiveRequest(@NonNull final Class<T> clazz) throws IOException, TimeoutException, InterruptedException
	{
		final Connection connection = procurementWebuiFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		final String[] message = { null };
		final DefaultConsumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body)
			{
				message[0] = new String(body, StandardCharsets.UTF_8);
				System.out.println("\n[x] Received on queue '" + Constants.QUEUE_NAME_MF_TO_PW + "': message= '" + message[0] + "'");
				countDownLatch.countDown();
			}
		};
		channel.basicConsume(Constants.QUEUE_NAME_MF_TO_PW, true, consumer);

		System.out.print("Waiting for message receipt");
		final boolean messageReceivedWithinTimeout = countDownLatch.await(5, TimeUnit.SECONDS);
		assertThat(messageReceivedWithinTimeout).isTrue();

		final RequestToProcurementWeb requestToProcurementWeb = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(message[0], RequestToProcurementWeb.class);
		assertThat(requestToProcurementWeb).isInstanceOf(clazz);
		return (T)requestToProcurementWeb;
	}

	@Then("the PutBPartnersRequest contains these Users:")
	public void the_put_b_partners_request_contains_these_users(DataTable dataTable)
	{

		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		// throw new io.cucumber.java.PendingException();
	}

	@Then("the PutBPartnersRequest contains these Contracts:")
	public void the_put_b_partners_request_contains_these_contracts(DataTable dataTable)
	{
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		// throw new io.cucumber.java.PendingException();
	}

	@Then("the PutBPartnersRequest contains these ContractLines:")
	public void the_put_b_partners_request_contains_these_contract_lines(DataTable dataTable)
	{
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		// throw new io.cucumber.java.PendingException();
	}
}
