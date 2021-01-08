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

package de.metas.cucumber.stepdefs.procurementweb;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.rabbitmq.client.AMQP.BasicProperties;
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
import de.metas.common.procurement.sync.protocol.dto.SyncContract;
import de.metas.common.procurement.sync.protocol.dto.SyncContractLine;
import de.metas.common.procurement.sync.protocol.dto.SyncUser;
import de.metas.common.procurement.sync.protocol.request_to_metasfresh.GetAllBPartnersRequest;
import de.metas.common.procurement.sync.protocol.request_to_procurementweb.PutBPartnersRequest;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.StepDefData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.springframework.amqp.core.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.assertThat;

public class ProcurementWebToMetasfresh_StepDef
{
	private final ConnectionFactory procurementWebuiFactory;

	private final StepDefData<SyncBPartner> syncBPartnerStepDefData = new StepDefData<>();
	private final StepDefData<SyncContract> syncContractStepDefData = new StepDefData<>();

	public ProcurementWebToMetasfresh_StepDef()
	{
		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull(); // guard

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
			final BasicProperties properties = new BasicProperties.Builder()
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

		final ImmutableMap<String, SyncBPartner> name2bpartner = Maps.uniqueIndex(putBPartnersRequest.getBpartners(), SyncBPartner::getName);

		for (final Map<String, String> tableRow : dataTable.<String, String>asMaps(String.class, String.class))
		{
			final String name = tableRow.get("Name");
			assertThat(name2bpartner).as("Missing syncBPartner with name=%s", name).containsKey(name);
			final SyncBPartner syncBPartner = name2bpartner.get(name);

			final boolean deleted = DataTableUtil.extractBooleanForColumnName(tableRow, "Deleted");
			assertThat(syncBPartner.isDeleted()).isEqualTo(deleted);

			final String identifier = DataTableUtil.extractStringForColumnName(tableRow, StepDefConstants.TABLECOLUMN_IDENTIFIER);
			syncBPartnerStepDefData.put(identifier, syncBPartner);
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
			public void handleDelivery(final String consumerTag, final Envelope envelope, final BasicProperties properties, final byte[] body)
			{
				message[0] = new String(body, StandardCharsets.UTF_8);
				System.out.println("\n[x] Received on queue '" + Constants.QUEUE_NAME_MF_TO_PW + "': message= '" + message[0] + "'");
				countDownLatch.countDown();
			}
		};
		channel.basicConsume(Constants.QUEUE_NAME_MF_TO_PW, true, consumer);

		System.out.print("Waiting for message receipt");
		final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS); // give us a bit of time while debugging
		assertThat(messageReceivedWithinTimeout).isTrue();

		final RequestToProcurementWeb requestToProcurementWeb = Constants.PROCUREMENT_WEBUI_OBJECT_MAPPER.readValue(message[0], RequestToProcurementWeb.class);
		assertThat(requestToProcurementWeb).isInstanceOf(clazz);
		return (T)requestToProcurementWeb;
	}

	@Then("the PutBPartnersRequest contains these Users:")
	public void the_put_b_partners_request_contains_these_users(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.<String, String>asMaps(String.class, String.class))
		{
			final String identifier = DataTableUtil.extractStringForColumnName(tableRow, "BPartner." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final SyncBPartner syncBPartner = syncBPartnerStepDefData.get(identifier);

			final ImmutableMap<String, SyncUser> email2User = Maps.uniqueIndex(syncBPartner.getUsers(), SyncUser::getEmail);

			final String email = DataTableUtil.extractStringForColumnName(tableRow, "Email");
			assertThat(email2User).as("Missing syncUser with email=%s", email).containsKey(email);

			final SyncUser syncUser = email2User.get(email);
			final String password = DataTableUtil.extractStringForColumnName(tableRow, "Password");
			final String language = DataTableUtil.extractStringForColumnName(tableRow, "Language");

			assertThat(syncUser.getPassword()).isEqualTo(password);
			assertThat(syncUser.getLanguage()).isEqualTo(language);
		}
	}

	@Then("the PutBPartnersRequest contains these Contracts:")
	public void the_put_b_partners_request_contains_these_contracts(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.<String, String>asMaps(String.class, String.class))
		{
			final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "BPartner." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final SyncBPartner syncBPartner = syncBPartnerStepDefData.get(bpartnerIdentifier);

			final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			final String dateFromStr = DataTableUtil.extractStringForColumnName(tableRow, "DateFrom");
			final String dateToStr = DataTableUtil.extractStringForColumnName(tableRow, "DateTo");

			final Optional<SyncContract> syncContract = syncBPartner.getContracts().stream()
					.filter(c -> dateFromStr.equals(c == null ? "" : df.format(c.getDateFrom())))
					.filter(c -> dateToStr.equals(c == null ? "" : df.format(c.getDateTo())))
					.findAny();
			assertThat(syncContract).as("Missing SyncContract with DateFrom=%s and DateTo=%s", dateFromStr, dateToStr).isPresent();

			final boolean deleted = DataTableUtil.extractBooleanForColumnName(tableRow, "Deleted");
			assertThat(syncContract.get().isDeleted()).isEqualTo(deleted);

			syncContractStepDefData.put(DataTableUtil.extractRecordIdentifier(tableRow, "Contract"), syncContract.get());
		}
	}

	@Then("the PutBPartnersRequest contains these ContractLines:")
	public void the_put_b_partners_request_contains_these_contract_lines(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> tableRow : dataTable.<String, String>asMaps(String.class, String.class))
		{
			final String contractIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "Contract." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final SyncContract syncContract = syncContractStepDefData.get(contractIdentifier);
			final List<SyncContractLine> contractLines = syncContract.getContractLines();

			final String productName = DataTableUtil.extractStringForColumnName(tableRow, "Product.Name");

			final Optional<SyncContractLine> product = contractLines.stream()
					.filter(l -> productName.equals(l.getProduct().getName()))
					.findAny();

			assertThat(product)
					.as("Missing SyncContractLine with contractIdentifier=%s and product.name=%s", contractIdentifier, productName)
					.isPresent();
		}
	}
}
