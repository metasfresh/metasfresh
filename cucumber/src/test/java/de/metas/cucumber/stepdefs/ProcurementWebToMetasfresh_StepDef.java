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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import de.metas.CommandLineParser.CommandLineOptions;
import de.metas.ServerBoot;
import de.metas.common.procurement.sync.Constants;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.compiere.SpringContextHolder;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

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

		Channel channel = null;
		try (final Connection connection = procurementWebuiFactory.newConnection())
		{
			channel = connection.createChannel();

			channel.basicPublish("", Constants.QUEUE_NAME_PW_TO_MF, null, string.getBytes());
			System.out.println("[x] Sent on queue '" + Constants.QUEUE_NAME_PW_TO_MF + "': message= '" + string + "'");
		}
	}

	@Then("metasfresh responds with a PutBPartnersRequest that contains these BPartners:")
	public void metasfresh_responds_with_a_put_b_partners_request_that_contains_these_b_partners(io.cucumber.datatable.DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		// final Connection connection = procurementWebuiFactory.newConnection();
		// final Channel channel = connection.createChannel();
		//
		// final CountDownLatch countDownLatch = new CountDownLatch(1);
		//
		// final String[] message = { null };
		// final DefaultConsumer consumer = new DefaultConsumer(channel)
		// {
		// 	@Override
		// 	public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws IOException
		// 	{
		// 		message[0] = new String(body, StandardCharsets.UTF_8);
		// 		System.out.println("\n[x] Received on queue '" + Constants.QUEUE_NAME_MF_TO_PW + "': message= '" + message[0] + "'");
		// 		countDownLatch.countDown();
		// 	}
		// };
		// channel.basicConsume(Constants.QUEUE_NAME_MF_TO_PW, true, consumer);
		//
		// System.out.print("Waiting for message receipt");
		// countDownLatch.await(5, TimeUnit.SECONDS);
		//
		// assertThat(message[0]).isEqualTo("blah");
	}

	@Then("the PutBPartnersRequest contains these Users:")
	public void the_put_b_partners_request_contains_these_users(io.cucumber.datatable.DataTable dataTable)
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
	public void the_put_b_partners_request_contains_these_contracts(io.cucumber.datatable.DataTable dataTable)
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
	public void the_put_b_partners_request_contains_these_contract_lines(io.cucumber.datatable.DataTable dataTable)
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
