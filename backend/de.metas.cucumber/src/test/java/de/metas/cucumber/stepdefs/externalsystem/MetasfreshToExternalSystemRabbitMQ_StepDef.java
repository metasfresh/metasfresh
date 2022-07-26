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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.CommandLineParser;
import de.metas.JsonObjectMapperHolder;
import de.metas.ServerBoot;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlConfigProductMapping;
import de.metas.common.externalsystem.leichundmehl.JsonExternalSystemLeichMehlPluFileConfigs;
import de.metas.common.util.Check;
import de.metas.common.util.EmptyUtil;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.hu.M_HU_StepDefData;
import de.metas.cucumber.stepdefs.productionorder.PP_Order_StepDefData;
import de.metas.externalsystem.model.I_ExternalSystem_Config;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.logging.LogManager;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Stream;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BPARTNER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_HU_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PP_ORDER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.QUEUE_NAME_MF_TO_ES;
import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static de.metas.externalsystem.model.I_ExternalSystem_Config.COLUMNNAME_ExternalSystem_Config_ID;
import static de.metas.handlingunits.model.I_M_HU.COLUMNNAME_M_HU_ID;
import static org.assertj.core.api.Assertions.*;

public class MetasfreshToExternalSystemRabbitMQ_StepDef
{
	private final static Logger logger = LogManager.getLogger(MetasfreshToExternalSystemRabbitMQ_StepDef.class);

	private final ConnectionFactory metasfreshToRabbitMQFactory;
	private final C_BPartner_StepDefData bpartnerTable;
	private final M_HU_StepDefData huTable;
	private final ExternalSystem_Config_StepDefData externalSystemConfigTable;
	private final PP_Order_StepDefData ppOrderTable;
	private final M_Product_StepDefData productTable;

	public MetasfreshToExternalSystemRabbitMQ_StepDef(
			@NonNull final C_BPartner_StepDefData bpartnerTable,
			@NonNull final M_HU_StepDefData huTable,
			@NonNull final ExternalSystem_Config_StepDefData externalSystemConfigTable,
			@NonNull final PP_Order_StepDefData ppOrderTable,
			@NonNull final M_Product_StepDefData productTable)
	{
		this.bpartnerTable = bpartnerTable;
		this.huTable = huTable;
		this.externalSystemConfigTable = externalSystemConfigTable;
		this.ppOrderTable = ppOrderTable;
		this.productTable = productTable;

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
		final int numberOfMessages = 1;
		final List<JsonExternalSystemRequest> requests = pollRequestFromQueue(numberOfMessages);
		final JsonExternalSystemRequest requestToRabbitMQ = requests.get(0);

		final Map<String, String> tableRow = dataTable.asMaps().get(0);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String externalSystemConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");

		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(bpartner).isNotNull();

		final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(externalSystemConfigIdentifier);
		assertThat(externalSystemConfig).isNotNull();

		final String requestBPartnerId = requestToRabbitMQ.getParameters().get(PARAM_BPARTNER_ID);

		assertThat(Integer.valueOf(requestBPartnerId)).isEqualTo(bpartner.getC_BPartner_ID());
		assertThat(requestToRabbitMQ.getExternalSystemConfigId().getValue()).isEqualTo(externalSystemConfig.getExternalSystem_Config_ID());
	}

	@Then("RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:")
	public void rabbitMQ_receives_an_external_system_request_param(@NonNull final DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();

		final int numberOfMessages = tableRows.size();
		final List<JsonExternalSystemRequest> requests = pollRequestFromQueue(numberOfMessages);

		for (final Map<String, String> tableRow : tableRows)
		{
			final String externalSystemConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_ExternalSystem_Config_ID + "." + TABLECOLUMN_IDENTIFIER);

			final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(externalSystemConfigIdentifier);
			assertThat(externalSystemConfig).isNotNull();

			final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (EmptyUtil.isNotBlank(huIdentifier))
			{
				final I_M_HU hu = huTable.get(huIdentifier);
				assertThat(hu).isNotNull();

				final JsonExternalSystemRequest jsonExternalSystemRequest = findJsonExternalSystemRequestForHu(requests, externalSystemConfig, hu);

				if (jsonExternalSystemRequest == null)
				{
					logger.info("*** Target JsonExternalSystemRequest not found, see list: " + JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requests));
				}
				assertThat(jsonExternalSystemRequest).isNotNull();
			}

			final String ppOrderIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(ppOrderIdentifier))
			{
				validateExportPPOrderWithPLUFile(ppOrderIdentifier, requests, externalSystemConfig, tableRow);
			}
		}
	}

	private List<JsonExternalSystemRequest> pollRequestFromQueue(final int numberOfMessages) throws IOException, TimeoutException, InterruptedException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(numberOfMessages);

		final String[] messages = new String[numberOfMessages];

		final DefaultConsumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body)
			{
				messages[(int)(numberOfMessages - countDownLatch.getCount())] = new String(body, StandardCharsets.UTF_8);
				countDownLatch.countDown();
			}
		};

		channel.basicConsume(QUEUE_NAME_MF_TO_ES, true, consumer);

		final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS);

		assertThat(messageReceivedWithinTimeout).isTrue();

		channel.close();

		return Stream.of(messages)
				.map(message -> {
					try
					{
						return JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(message, JsonExternalSystemRequest.class);
					}
					catch (final JsonProcessingException e)
					{
						throw AdempiereException.wrapIfNeeded(e);
					}
				})
				.collect(ImmutableList.toImmutableList());
	}

	private void validateExportPPOrderWithPLUFile(
			@NonNull final String ppOrderIdentifier,
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final Map<String, String> tableRow) throws JsonProcessingException
	{
		final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);

		final JsonExternalSystemRequest jsonExternalSystemRequest = findJsonExternalSystemRequestForPPOrder(requests, externalSystemConfig, ppOrder);

		if (jsonExternalSystemRequest == null)
		{
			logger.info("*** Target JsonExternalSystemRequest not found, see list: " + JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requests));
		}

		assertThat(jsonExternalSystemRequest).isNotNull();

		final Map<String, String> params = jsonExternalSystemRequest.getParameters();

		final String portNumber = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_TCP_PortNumber);
		assertThat(params.get(ExternalSystemConstants.PARAM_TCP_PORT_NUMBER)).isEqualTo(portNumber);

		final String host = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_TCP_Host);
		assertThat(params.get(ExternalSystemConstants.PARAM_TCP_HOST)).isEqualTo(host);

		final String product_BaseFolderName = DataTableUtil.extractStringForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_Product_BaseFolderName);
		assertThat(params.get(ExternalSystemConstants.PARAM_PRODUCT_BASE_FOLDER_NAME)).isEqualTo(product_BaseFolderName);

		final boolean pluFileEnabled = DataTableUtil.extractBooleanForColumnName(tableRow, I_ExternalSystem_Config_LeichMehl.COLUMNNAME_IsPluFileExportAuditEnabled);
		final String pluFileEnabledConfig = params.get(ExternalSystemConstants.PARAM_PLU_FILE_EXPORT_AUDIT_ENABLED);
		assertThat(Boolean.parseBoolean(pluFileEnabledConfig)).isEqualTo(pluFileEnabled);

		final String productMappingString = params.get(ExternalSystemConstants.PARAM_CONFIG_MAPPINGS);
		assertThat(productMappingString).isNotNull();

		final JsonExternalSystemLeichMehlConfigProductMapping productMapping = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(productMappingString, JsonExternalSystemLeichMehlConfigProductMapping.class);

		final String pluFile = DataTableUtil.extractStringForColumnName(tableRow, "ConfigMappings.pluFile");
		assertThat(productMapping.getPluFile()).isEqualTo(pluFile);

		final String productIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "ConfigMappings.M_Product_ID.Identifier");
		final I_M_Product product = productTable.get(productIdentifier);
		assertThat(productMapping.getProductId().getValue()).isEqualTo(product.getM_Product_ID());

		final String expectedPluFileConfigsString = DataTableUtil.extractStringForColumnName(tableRow, ExternalSystemConstants.PARAM_PLU_FILE_CONFIG);
		final JsonExternalSystemLeichMehlPluFileConfigs expectedPluFileConfigs = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(expectedPluFileConfigsString, JsonExternalSystemLeichMehlPluFileConfigs.class);

		final String actualPluFileConfigsString = params.get(ExternalSystemConstants.PARAM_PLU_FILE_CONFIG);
		final JsonExternalSystemLeichMehlPluFileConfigs actualPluFileConfigs = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(actualPluFileConfigsString, JsonExternalSystemLeichMehlPluFileConfigs.class);

		assertThat(actualPluFileConfigs).isEqualTo(expectedPluFileConfigs);
	}

	@Nullable
	private JsonExternalSystemRequest findJsonExternalSystemRequestForHu(
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final I_M_HU hu)
	{
		JsonExternalSystemRequest foundRequest = null;

		for (final JsonExternalSystemRequest request : requests)
		{
			boolean found;

			if (request.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID())
			{
				found = true;
			}
			else
			{
				logger.info("*** JsonExternalSystemRequest:" + request + " skipped;\nExternalSystemConfigId:" + externalSystemConfig.getExternalSystem_Config_ID() + " does not match");
				continue;
			}

			if (Integer.parseInt(request.getParameters().get(PARAM_HU_ID)) == hu.getM_HU_ID())
			{
				found = true;
			}
			else
			{
				logger.info("*** JsonExternalSystemRequest:" + request + " skipped;\nHUId:" + hu.getM_HU_ID() + " does not match");
				continue;
			}

			if (found)
			{
				foundRequest = request;
				break;
			}
		}

		return Optional.ofNullable(foundRequest)
				.orElse(null);
	}

	@Nullable
	private JsonExternalSystemRequest findJsonExternalSystemRequestForPPOrder(
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final I_PP_Order ppOrder)
	{
		JsonExternalSystemRequest foundRequest = null;

		for (final JsonExternalSystemRequest request : requests)
		{
			boolean found;

			if (request.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID())
			{
				found = true;
			}
			else
			{
				logger.info("*** JsonExternalSystemRequest:" + request + " skipped;\nExternalSystemConfigId:" + externalSystemConfig.getExternalSystem_Config_ID() + " does not match");
				continue;
			}

			if (request.getParameters().get(PARAM_PP_ORDER_ID) != null)
			{
				found = true;
			}
			else
			{
				logger.info("*** JsonExternalSystemRequest:" + request + " skipped;\nParam PP_Order_ID:" + ppOrder.getPP_Order_ID() + " not present");
				continue;
			}

			if (Integer.parseInt(request.getParameters().get(PARAM_PP_ORDER_ID)) == ppOrder.getPP_Order_ID())
			{
				found = true;
			}
			else
			{
				logger.info("*** JsonExternalSystemRequest:" + request + " skipped;\nParam PP_Order_ID:" + ppOrder.getPP_Order_ID() + " does not match");
				continue;
			}

			if (found)
			{
				foundRequest = request;
				break;
			}
		}

		return Optional.ofNullable(foundRequest)
				.orElse(null);
	}
}