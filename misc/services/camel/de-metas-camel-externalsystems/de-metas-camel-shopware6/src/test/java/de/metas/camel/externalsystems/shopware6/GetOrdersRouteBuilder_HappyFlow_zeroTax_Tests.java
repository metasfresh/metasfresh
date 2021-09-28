/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroups;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddressAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderLines;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransactions;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrders;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonPaymentMethod;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.processor.GetOrdersProcessor;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.ordercandidates.v2.request.JsonOLCandClearRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_UPSERT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CREATE_PAYMENT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CURRENCY_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_EUR_CODE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_NORMAL_VAT_PRODUCT_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_NORMAL_VAT_RATES;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_CLEAR;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_CREATE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_REDUCED_VAT_PRODUCT_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_REDUCED_VAT_RATES;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_STORE_RAW_DATA;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_UPSERT_RUNTIME_PARAMETERS;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.CLEAR_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.OLCAND_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.UPSERT_RUNTIME_PARAMS_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CONFIG_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_VAT_RATES;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.nullable;

public class GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests extends CamelTestSupport
{
	private static final String SALES_REP_IDENTIFIER = "mockSalesRepIdentifier";
	private static final String JSON_SHOPWARE_MAPPINGS = "01_JsonExternalSystemShopware6ConfigMappings.json";

	private static final String HAPPY_FLOW_ZERO_TAX = "happyFlow_zeroTax/";

	private static final String JSON_ORDERS_RESOURCE_PATH = HAPPY_FLOW_ZERO_TAX + "10_JsonOrders.json";
	private static final String JSON_ORDER_TRANSACTIONS_PATH = HAPPY_FLOW_ZERO_TAX + "12_JsonOrderTransactions.json";
	private static final String JSON_ORDER_PAYMENT_METHOD_PATH = HAPPY_FLOW_ZERO_TAX + "14_JsonPaymentMethod.json";
	private static final String JSON_ORDER_GROUPS_PATH = HAPPY_FLOW_ZERO_TAX + "16_JsonCustomerGroups.json";
	private static final String JSON_ORDER_DELIVERIES_PATH = HAPPY_FLOW_ZERO_TAX + "20_JsonOrderDeliveries_zeroTax.json";
	private static final String JSON_ORDER_BILLING_ADDRESS_PATH = HAPPY_FLOW_ZERO_TAX + "30_Billing_JsonOrderAddressCustomId.json";
	private static final String JSON_ORDER_LINES = HAPPY_FLOW_ZERO_TAX + "40_JsonOrderLines.json";
	private static final String JSON_COUNTRY_INFO_PATH = "JsonCountry_DE.json";

	private static final String JSON_UPSERT_BPARTNER_REQUEST = HAPPY_FLOW_ZERO_TAX + "50_CamelUpsertBPartnerCompositeRequest.json";
	private static final String JSON_UPSERT_BPARTNER_RESPONSE = HAPPY_FLOW_ZERO_TAX + "50_CamelUpsertBPartnerCompositeResponse.json";

	private static final String JSON_OL_CAND_CREATE_REQUEST = HAPPY_FLOW_ZERO_TAX + "60_JsonOLCandCreateBulkRequest.json";
	private static final String JSON_ORDER_PAYMENT_CREATE_REQUEST = HAPPY_FLOW_ZERO_TAX + "63_JsonOrderPaymentCreateRequest.json";

	private static final String JSON_UPSERT_RUNTIME_PARAMS_REQUEST = HAPPY_FLOW_ZERO_TAX + "65_JsonESRuntimeParameterUpsertRequest.json";

	private static final String JSON_OL_CAND_CLEAR_REQUEST = HAPPY_FLOW_ZERO_TAX + "70_JsonOLCandClearRequest.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Override
	protected RouteBuilder createRouteBuilder()
	{
		final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
		return new GetOrdersRouteBuilder(processLogger);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow_zeroTax() throws Exception
	{
		final MockUpsertBPartnerProcessor createdBPartnerProcessor = new MockUpsertBPartnerProcessor();
		final MockSuccessfullyCreatedOLCandProcessor successfullyCreatedOLCandProcessor = new MockSuccessfullyCreatedOLCandProcessor();
		final MockSuccessfullyClearOrdersProcessor successfullyClearOrdersProcessor = new MockSuccessfullyClearOrdersProcessor();
		final MockSuccessfullyCreatePaymentProcessor createPaymentProcessor = new MockSuccessfullyCreatePaymentProcessor();
		final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor = new MockSuccessfullyUpsertRuntimeParamsProcessor();

		prepareRouteForTesting(createdBPartnerProcessor,
							   successfullyCreatedOLCandProcessor,
							   successfullyClearOrdersProcessor,
							   runtimeParamsProcessor,
							   createPaymentProcessor);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		final MockEndpoint storeRawDataEndpoint = getMockEndpoint(MOCK_STORE_RAW_DATA);
		storeRawDataEndpoint.expectedMessageCount(1);
		
		// validate BPUpsertCamelRequest
		final InputStream expectedUpsertBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);

		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPartnerRequestIS, BPUpsertCamelRequest.class));

		//validate JsonOLCandCreateBulkRequest
		final InputStream olCandCreateRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_CREATE_REQUEST);

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(MOCK_OL_CAND_CREATE);
		olCandMockEndpoint.expectedBodiesReceived(objectMapper.readValue(olCandCreateRequestIS, JsonOLCandCreateBulkRequest.class));

		//validate JsonOLCandClearRequest
		final InputStream jsonOLCandClearRequest = this.getClass().getResourceAsStream(JSON_OL_CAND_CLEAR_REQUEST);

		final MockEndpoint olCandClearEndpoint = getMockEndpoint(MOCK_OL_CAND_CLEAR);
		olCandClearEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonOLCandClearRequest, JsonOLCandClearRequest.class));

		//validate create payment request
		final InputStream jsonCreatePaymentRequest = this.getClass().getResourceAsStream(JSON_ORDER_PAYMENT_CREATE_REQUEST);

		final MockEndpoint createPaymentEndpoint = getMockEndpoint(MOCK_CREATE_PAYMENT);
		createPaymentEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonCreatePaymentRequest, JsonOrderPaymentCreateRequest.class));

		//validate upsert runtime parameters request
		final InputStream jsonUpsertRuntimeParamsRequest = this.getClass().getResourceAsStream(JSON_UPSERT_RUNTIME_PARAMS_REQUEST);

		final MockEndpoint upsertRuntimeParametersEndpoint = getMockEndpoint(MOCK_UPSERT_RUNTIME_PARAMETERS);
		upsertRuntimeParametersEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonUpsertRuntimeParamsRequest, JsonESRuntimeParameterUpsertRequest.class));

		template.sendBody("direct:" + GET_ORDERS_ROUTE_ID, "Body not relevant!");

		assertThat(createdBPartnerProcessor.called).isEqualTo(1);
		assertThat(successfullyCreatedOLCandProcessor.called).isEqualTo(1);
		assertThat(successfullyClearOrdersProcessor.called).isEqualTo(1);
		assertThat(runtimeParamsProcessor.called).isEqualTo(1);
		assertThat(createPaymentProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final MockUpsertBPartnerProcessor upsertBPartnerProcessor,
			final MockSuccessfullyCreatedOLCandProcessor olCandProcessor,
			final MockSuccessfullyClearOrdersProcessor olCandClearProcessor,
			final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor,
			final MockSuccessfullyCreatePaymentProcessor createPaymentProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, GET_ORDERS_ROUTE_ID,
							  advice -> advice.weaveById(GET_ORDERS_PROCESSOR_ID)
									  .replace()
									  .process(new MockGetOrdersProcessor()));

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
							  advice -> {
								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.STORE_RAW_DATA_ROUTE)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_STORE_RAW_DATA);

								  advice.weaveById(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_BPARTNER_UPSERT);

								  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
										  .skipSendToOriginalEndpoint()
										  .process(upsertBPartnerProcessor);

								  advice.weaveById(OLCAND_REQ_PROCESSOR_ID)
										  .after()
										  .to(MOCK_OL_CAND_CREATE);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .process(olCandProcessor);

								  advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID)
										  .skipSendToOriginalEndpoint()
										  .to(MOCK_CREATE_PAYMENT)
										  .process(createPaymentProcessor);
							  });

		AdviceWith.adviceWith(context, CLEAR_ORDERS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_CLEAR_OL_CANDIDATES_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_OL_CAND_CLEAR)
									  .process(olCandClearProcessor));

		AdviceWith.adviceWith(context, UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_UPSERT_RUNTIME_PARAMETERS)
									  .process(runtimeParamsProcessor));
	}

	private static String loadAsString(@NonNull final String name)
	{
		final InputStream inputStream = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}

	private static class MockSuccessfullyCreatedOLCandProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	public static class MockGetOrdersProcessor implements Processor
	{
		@Override
		public void process(final Exchange exchange) throws IOException
		{
			// mock getOrders
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());

			final InputStream ordersIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDERS_RESOURCE_PATH);
			final JsonOrders jsonOrders = mapper.readValue(ordersIS, JsonOrders.class);

			final List<OrderCandidate> orderCandidates = jsonOrders
					.getData()
					.stream()
					.map(order -> OrderCandidate.builder().jsonOrder(order).salesRepId(SALES_REP_IDENTIFIER).build())
					.collect(Collectors.toList());

			// mock shopware client
			final ShopwareClient shopwareClient = prepareShopwareClientMock(mapper);

			//set up the exchange
			final CurrencyInfoProvider currencyInfoProvider = CurrencyInfoProvider.builder()
					.currencyId2IsoCode(ImmutableMap.of(MOCK_CURRENCY_ID, MOCK_EUR_CODE))
					.build();

			final InputStream shopwareMappingsIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_SHOPWARE_MAPPINGS);
			final JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappings = mapper.readValue(shopwareMappingsIS, JsonExternalSystemShopware6ConfigMappings.class);

			final Map<String, String> parameters = new HashMap<>();
			parameters.put(PARAM_FREIGHT_COST_NORMAL_VAT_RATES, MOCK_NORMAL_VAT_RATES);
			parameters.put(PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID, String.valueOf(MOCK_NORMAL_VAT_PRODUCT_ID));
			parameters.put(PARAM_FREIGHT_COST_REDUCED_VAT_RATES, MOCK_REDUCED_VAT_RATES);
			parameters.put(PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID, String.valueOf(MOCK_REDUCED_VAT_PRODUCT_ID));
			parameters.put(PARAM_CONFIG_MAPPINGS, mapper.writeValueAsString(shopware6ConfigMappings));

			final JsonExternalSystemRequest externalSystemRequest = JsonExternalSystemRequest
					.builder()
					.externalSystemName(JsonExternalSystemName.of(SHOPWARE6_SYSTEM_NAME))
					.externalSystemConfigId(JsonMetasfreshId.of(1))
					.orgCode(MOCK_ORG_CODE)
					.command("command")
					.parameters(parameters)
					.build();

			final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
			final ImportOrdersRouteContext ordersContext = new GetOrdersProcessor(processLogger)
					.buildContext(externalSystemRequest, shopwareClient, currencyInfoProvider);

			exchange.getIn().setBody(orderCandidates);
			exchange.setProperty(ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ordersContext);
		}

		@NonNull
		private ShopwareClient prepareShopwareClientMock(final ObjectMapper mapper) throws IOException
		{
			final ShopwareClient dumbShopwareClient = ShopwareClient.of("does", "not", "https://www.matter.com");
			final ShopwareClient shopwareClientSpy = Mockito.spy(dumbShopwareClient);

			Mockito.doNothing().when(shopwareClientSpy).refreshTokenIfExpired();

			//1. mock getDeliveries
			final String deliveriesString = loadAsString(JSON_ORDER_DELIVERIES_PATH);

			Mockito.doReturn(ResponseEntity.ok(deliveriesString))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(String.class), any());

			//2. mock getOrderAddressDetails
			final InputStream billingAddressIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDER_BILLING_ADDRESS_PATH);
			final JsonOrderAddress billingAddress = mapper.readValue(billingAddressIS, JsonOrderAddress.class);

			Mockito.doReturn(Optional.of(JsonOrderAddressAndCustomId.builder()
												 .jsonOrderAddress(billingAddress)
												 .build()))
					.when(shopwareClientSpy)
					.getOrderAddressDetails(nullable(String.class), nullable(String.class));

			//3. mock getCountryDetails
			final InputStream countryIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_COUNTRY_INFO_PATH);
			final JsonCountry jsonCountry = mapper.readValue(countryIS, JsonCountry.class);

			Mockito.doReturn(Optional.of(jsonCountry))
					.when(shopwareClientSpy)
					.getCountryDetails(any(String.class));

			//4. mock orderLines
			final InputStream orderLinesIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDER_LINES);
			final JsonOrderLines orderLines = mapper.readValue(orderLinesIS, JsonOrderLines.class);

			Mockito.doReturn(ResponseEntity.ok(orderLines))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonOrderLines.class), any());

			//5. mock order transactions
			final InputStream orderTrxIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDER_TRANSACTIONS_PATH);
			final JsonOrderTransactions orderTransactions = mapper.readValue(orderTrxIS, JsonOrderTransactions.class);

			Mockito.doReturn(ResponseEntity.ok(orderTransactions))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonOrderTransactions.class), any());

			//6. mock payment method
			final InputStream paymentMethodIS = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDER_PAYMENT_METHOD_PATH);
			final JsonPaymentMethod paymentMethod = mapper.readValue(paymentMethodIS, JsonPaymentMethod.class);

			Mockito.doReturn(Optional.of(paymentMethod))
					.when(shopwareClientSpy)
					.getPaymentMethod(any());

			//7. mock order customer group
			final InputStream orderCustomerGroup = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_ORDER_GROUPS_PATH);
			final JsonCustomerGroups customerGroups = mapper.readValue(orderCustomerGroup, JsonCustomerGroups.class);

			Mockito.doReturn(ResponseEntity.ok(customerGroups))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonCustomerGroups.class), any());

			return shopwareClientSpy;
		}
	}

	private static class MockUpsertBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream upsertBPartnerResponse = GetOrdersRouteBuilder_HappyFlow_zeroTax_Tests.class.getResourceAsStream(JSON_UPSERT_BPARTNER_RESPONSE);
			exchange.getIn().setBody(upsertBPartnerResponse);
		}
	}

	static class MockSuccessfullyClearOrdersProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	static class MockSuccessfullyUpsertRuntimeParamsProcessor implements Processor
	{
		@Getter
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockSuccessfullyCreatePaymentProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}
}
