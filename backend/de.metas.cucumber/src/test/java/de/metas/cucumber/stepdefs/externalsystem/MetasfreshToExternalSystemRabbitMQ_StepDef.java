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
import com.fasterxml.jackson.databind.ObjectMapper;
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
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
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
import de.metas.cucumber.stepdefs.pporder.PP_Order_StepDefData;
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
import java.util.function.Function;

import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_BPARTNER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_HU_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST;
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
	
	private final ObjectMapper objectMapper = JsonObjectMapperHolder.newJsonObjectMapper();

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
		final AMQP.Queue.PurgeOk purgeOk = channel.queuePurge(QUEUE_NAME_MF_TO_ES);
		logger.info("Purged {} messages from queue {}", purgeOk.getMessageCount(), QUEUE_NAME_MF_TO_ES);
	}

	@Then("RabbitMQ receives a JsonExternalSystemRequest with the following external system config and bpartnerId as parameters:")
	public void rabbitMQ_receives_an_external_system_request(@NonNull final DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);
		final String bpartnerIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_C_BPartner.COLUMNNAME_C_BPartner_ID + ".Identifier");
		final String externalSystemConfigIdentifier = DataTableUtil.extractStringForColumnName(tableRow, COLUMNNAME_ExternalSystem_Config_ID + ".Identifier");

		final I_C_BPartner bpartner = bpartnerTable.get(bpartnerIdentifier);
		assertThat(bpartner).isNotNull();

		final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(externalSystemConfigIdentifier);
		assertThat(externalSystemConfig).isNotNull();

		final int numberOfMessages = 1;

		final List<JsonExternalSystemRequest> requests = pollRequestFromQueue(numberOfMessages,
																			  (jsonESRequest) -> isJsonExternalSystemRequestMatchingRows(dataTable, jsonESRequest));
		final JsonExternalSystemRequest requestToRabbitMQ = requests.get(0);

		assertThat(Integer.parseInt(requestToRabbitMQ.getParameters().get(PARAM_BPARTNER_ID)))
				.as("Wrong C_BPartner_ID in RabbitMQ request; identifier=%s; requests=%s", bpartnerIdentifier, requestToRabbitMQ)
				.isEqualTo(bpartner.getC_BPartner_ID());
		assertThat(requestToRabbitMQ.getExternalSystemConfigId().getValue())
				.as("Wrong ExternalSystem_Config_ID in RabbitMQ request; identifier=%s; requests=%s", externalSystemConfigIdentifier, requestToRabbitMQ)
				.isEqualTo(externalSystemConfig.getExternalSystem_Config_ID());
	}

	@Then("RabbitMQ receives a JsonExternalSystemRequest with the following external system config and parameter:")
	public void rabbitMQ_receives_an_external_system_request_param(@NonNull final DataTable dataTable) throws IOException, TimeoutException, InterruptedException
	{
		final List<Map<String, String>> tableRows = dataTable.asMaps();

		final int numberOfMessages = tableRows.size();

		final List<JsonExternalSystemRequest> requests = pollRequestFromQueue(numberOfMessages,
																			  (jsonESRequest) -> isJsonExternalSystemRequestMatchingRows(dataTable, jsonESRequest));

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

				checkExistingJsonExternalSystemRequestForHu(requests, externalSystemConfig, hu);
			}

			final String expectedJsonExternalReferenceLookupRequest = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.parameters.JsonExternalReferenceLookupRequest");
			if (EmptyUtil.isNotBlank(expectedJsonExternalReferenceLookupRequest))
			{
				final JsonExternalReferenceLookupRequest expectedRequest = objectMapper.readValue(expectedJsonExternalReferenceLookupRequest, JsonExternalReferenceLookupRequest.class);

				checkExistingJsonExternalSystemRequestForExternalLookupRequest(requests, externalSystemConfig, expectedRequest);
			}

			final String bpIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT.parameters" + I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(bpIdentifier))
			{
				final I_C_BPartner bPartner = bpartnerTable.get(bpIdentifier);
				assertThat(bPartner).isNotNull();

				checkExistingJsonExternalSystemRequestForBPartner(requests, externalSystemConfig, bPartner);
			}

			final String ppOrderIdentifier = DataTableUtil.extractStringOrNullForColumnName(tableRow, "OPT." + I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
			if (Check.isNotBlank(ppOrderIdentifier))
			{
				validateExportPPOrderWithPLUFile(ppOrderIdentifier, requests, externalSystemConfig, tableRow);
			}
		}
	}

	@NonNull
	private List<JsonExternalSystemRequest> pollRequestFromQueue(
			final int numberOfMessages,
			final Function<JsonExternalSystemRequest, Boolean> messageQualifier) throws IOException, TimeoutException, InterruptedException
	{
		Channel channel = null;

		try
		{
			final ImmutableList.Builder<JsonExternalSystemRequest> collector = ImmutableList.builder();

			final Connection connection = metasfreshToRabbitMQFactory.newConnection();
			channel = connection.createChannel();

			final CountDownLatch countDownLatch = new CountDownLatch(numberOfMessages);

			final DefaultConsumer consumer = new DefaultConsumer(channel)
			{
				@Override
				public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws JsonProcessingException
				{
					final String externalSystemRequest = new String(body, StandardCharsets.UTF_8);

					logger.info("*** {}: received message: {}", QUEUE_NAME_MF_TO_ES, externalSystemRequest);

					final JsonExternalSystemRequest jsonExternalSystemRequest = objectMapper.readValue(externalSystemRequest, JsonExternalSystemRequest.class);

					if (messageQualifier.apply(jsonExternalSystemRequest))
					{
						collector.add(jsonExternalSystemRequest);
						countDownLatch.countDown();
					}
				}
			};

			channel.basicConsume(QUEUE_NAME_MF_TO_ES, true, consumer);

			final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS);

			assertThat(messageReceivedWithinTimeout).isTrue();

			return collector.build();
		}
		finally
		{
			if (channel != null)
			{
				channel.close();
			}
		}
	}

	@NonNull
	private JsonExternalReferenceLookupRequest readJsonExternalReferenceLookupRequest(@NonNull final String jsonExternalReferenceLookupRequest)
	{
		try
		{
			return objectMapper.readValue(jsonExternalReferenceLookupRequest, JsonExternalReferenceLookupRequest.class);
		}
		catch (final JsonProcessingException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("jsonExternalReferenceLookupRequest", jsonExternalReferenceLookupRequest);
		}
	}

	private boolean isJsonExternalSystemRequestMatchingRows(@NonNull final DataTable dataTable, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			if (isRequestMatchingRow(row, externalSystemRequest))
			{
				return true;
			}
		}
		return false;
	}

	private boolean isRequestMatchingRow(final Map<String, String> row, final JsonExternalSystemRequest externalSystemRequest)
	{
		final String externalSystemConfigIdentifier = DataTableUtil.extractStringForColumnName(row, COLUMNNAME_ExternalSystem_Config_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (!isMatchingESRequestBasedOnESConfig(externalSystemConfigIdentifier, externalSystemRequest))
		{
			return false;
		}

		final String huIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + COLUMNNAME_M_HU_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (isMatchingESRequestBasedOnHU(huIdentifier, externalSystemRequest))
		{
			return true;
		}

		final String expectedJsonExternalReferenceLookupRequest = DataTableUtil.extractStringOrNullForColumnName(row, "OPT.parameters.JsonExternalReferenceLookupRequest");
		if (isMatchingESRequestBasedOnExtRefLookupReq(expectedJsonExternalReferenceLookupRequest, externalSystemRequest))
		{
			return true;
		}

		final String bpIdentifier = Optional.ofNullable(DataTableUtil.extractStringOrNullForColumnName(row, "OPT.parameters." + I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER))
				.orElseGet(() -> DataTableUtil.extractStringOrNullForColumnName(row, I_C_BPartner.COLUMNNAME_C_BPartner_ID + "." + TABLECOLUMN_IDENTIFIER));

		if (isMatchingESRequestBasedOnBPartner(bpIdentifier, externalSystemRequest))
		{
			return true;
		}

		final String ppOrderIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_PP_Order.COLUMNNAME_PP_Order_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (isMatchingESRequestBasedOnPPOrderIdentifier(ppOrderIdentifier, externalSystemRequest))
		{
			return true;
		}

		return false;
	}

	private void checkExistingJsonExternalSystemRequestForHu(
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final I_M_HU hu) throws JsonProcessingException
	{
		final JsonExternalSystemRequest jsonExternalSystemRequest = requests.stream()
				.filter(request -> request.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID())
				.filter(request -> request.getParameters().get(PARAM_BPARTNER_ID) != null)
				.filter(request -> hu.getM_HU_ID() == Integer.parseInt(request.getParameters().get(PARAM_BPARTNER_ID)))
				.findFirst()
				.orElse(null);

		if (jsonExternalSystemRequest == null)
		{
			logger.info("*** Target JsonExternalSystemRequest not found, see list: " + JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requests));
		}
	}

	private void checkExistingJsonExternalSystemRequestForExternalLookupRequest(
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final JsonExternalReferenceLookupRequest expectedRequest) throws JsonProcessingException
	{
		final JsonExternalSystemRequest jsonExternalSystemRequest = requests.stream()
				.filter(request -> request.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID())
				.filter(request -> request.getParameters().get(PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST) != null)
				.filter(request -> readJsonExternalReferenceLookupRequest(request.getParameters().get(PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST)).equals(expectedRequest))
				.findFirst()
				.orElse(null);

		if (jsonExternalSystemRequest == null)
		{
			logger.info("*** Target JsonExternalSystemRequest not found, see list: " + JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requests));
		}
	}

	private void checkExistingJsonExternalSystemRequestForBPartner(
			@NonNull final List<JsonExternalSystemRequest> requests,
			@NonNull final I_ExternalSystem_Config externalSystemConfig,
			@NonNull final I_C_BPartner bPartner) throws JsonProcessingException
	{
		final JsonExternalSystemRequest jsonExternalSystemRequest = requests.stream()
				.filter(request -> request.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID())
				.filter(request -> request.getParameters().get(PARAM_BPARTNER_ID) != null)
				.filter(request -> String.valueOf(bPartner.getC_BPartner_ID()).equals(request.getParameters().get(PARAM_BPARTNER_ID)))
				.findFirst()
				.orElse(null);

		if (jsonExternalSystemRequest == null)
		{
			logger.info("*** Target JsonExternalSystemRequest not found, see list: " + JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(requests));
		}
	}

	private boolean isMatchingESRequestBasedOnESConfig(@NonNull final String externalSystemConfigIdentifier, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		final I_ExternalSystem_Config externalSystemConfig = externalSystemConfigTable.get(externalSystemConfigIdentifier);
		assertThat(externalSystemConfig).isNotNull();

		return externalSystemRequest.getExternalSystemConfigId().getValue() == externalSystemConfig.getExternalSystem_Config_ID();
	}

	private boolean isMatchingESRequestBasedOnHU(@Nullable final String huIdentifier, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		if (Check.isBlank(huIdentifier))
		{
			return false;
		}

		final I_M_HU hu = huTable.get(huIdentifier);
		final String huIdAsString = externalSystemRequest.getParameters().get(PARAM_HU_ID);

		return Check.isNotBlank(huIdAsString)
				&& Integer.parseInt(huIdAsString) == hu.getM_HU_ID();
	}

	private boolean isMatchingESRequestBasedOnExtRefLookupReq(@Nullable final String expectedJsonExternalReferenceLookupRequest, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		if (Check.isBlank(expectedJsonExternalReferenceLookupRequest))
		{
			return false;
		}

		final String jsonExternalRefLookupReq = externalSystemRequest.getParameters().get(PARAM_JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST);
		if (Check.isBlank(jsonExternalRefLookupReq))
		{
			return false;
		}

		try
		{
			final JsonExternalReferenceLookupRequest expectedRequest = objectMapper.readValue(expectedJsonExternalReferenceLookupRequest, JsonExternalReferenceLookupRequest.class);

			final JsonExternalReferenceLookupRequest actualRequest = readJsonExternalReferenceLookupRequest(jsonExternalRefLookupReq);
			if (actualRequest.equals(expectedRequest))
			{
				return true;
			}
		}
		catch (final JsonProcessingException e)
		{
			throw new RuntimeException(e.getMessage());
		}

		return false;
	}

	private boolean isMatchingESRequestBasedOnBPartner(@Nullable final String bpartnerIdentifier, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		if (Check.isNotBlank(bpartnerIdentifier))
		{
			final I_C_BPartner bPartner = bpartnerTable.get(bpartnerIdentifier);
			final String bpartnerIdAsString = externalSystemRequest.getParameters().get(PARAM_BPARTNER_ID);

			return Check.isNotBlank(bpartnerIdAsString) && Integer.parseInt(bpartnerIdAsString) == bPartner.getC_BPartner_ID();
		}
		return false;
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

	private boolean isMatchingESRequestBasedOnPPOrderIdentifier(@Nullable final String ppOrderIdentifier, @NonNull final JsonExternalSystemRequest externalSystemRequest)
	{
		if (Check.isNotBlank(ppOrderIdentifier))
		{
			final I_PP_Order ppOrder = ppOrderTable.get(ppOrderIdentifier);
			final String ppOrderIdAsString = externalSystemRequest.getParameters().get(PARAM_PP_ORDER_ID);

			return Check.isNotBlank(ppOrderIdAsString) && Integer.parseInt(ppOrderIdAsString) == ppOrder.getPP_Order_ID();
		}

		return false;
	}
}