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
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.Shopware6QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroups;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderLines;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderTransactions;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonPaymentMethod;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder;
import de.metas.camel.externalsystems.shopware6.order.ImportOrdersRouteContext;
import de.metas.camel.externalsystems.shopware6.order.processor.BuildOrdersContextProcessor;
import de.metas.camel.externalsystems.shopware6.order.query.OrderQueryHelper;
import de.metas.camel.externalsystems.shopware6.order.query.PageAndLimit;
import de.metas.camel.externalsystems.shopware6.salutation.SalutationInfoProvider;
import de.metas.common.externalsystem.JsonESRuntimeParameterUpsertRequest;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import de.metas.common.externalsystem.JsonOrderProcessingConfig;
import de.metas.common.externalsystem.JsonProductLookup;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.rest_api.v2.order.JsonOrderPaymentCreateRequest;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.SHOPWARE6_SYSTEM_NAME;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BILLING_ADDRESS_HTTP_URL;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BILLING_SALUTATION_DISPLAY_NAME;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BILLING_SALUTATION_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_UPSERT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CREATE_PAYMENT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CURRENCY_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_EUR_CODE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_IS_TAX_INCLUDED;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_JSON_EMAIL_PATH;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_JSON_METASFRESH_ID_PATH;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_JSON_PATH_SALES_REP_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_JSON_SHOPWARE_ID_PATH;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_NORMAL_VAT_PRODUCT_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_NORMAL_VAT_RATES;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_CREATE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_OL_CAND_PROCESS;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_REDUCED_VAT_PRODUCT_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_REDUCED_VAT_RATES;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_SALUTATION_DISPLAY_NAME;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_SALUTATION_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_TARGET_PRICE_LIST_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_TRACE_ID;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_UPSERT_RUNTIME_PARAMETERS;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.BUILD_ORDERS_CONTEXT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_PAGE_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.OLCAND_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_OLCAND_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDERS_PAGE_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.order.GetOrdersRouteBuilder.UPSERT_RUNTIME_PARAMS_ROUTE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CONFIG_MAPPINGS;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_IS_TAX_INCLUDED;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_METASFRESH_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_EMAIL;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_JSON_PATH_SALES_REP_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_NORMAL_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_NO;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_ORDER_PROCESSING;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRICE_LIST_CURRENCY_CODE;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_PRODUCT_LOOKUP;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_REDUCED_VAT_RATES;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_TARGET_PRICE_LIST_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetOrdersRouteBuilder_HappyFlow_Tests extends CamelTestSupport
{

	private static final String SALES_REP_IDENTIFIER = "mockSalesRepIdentifier";

	private static final String ORDER_PAGE_INDEX_TOKEN = "$pageIndex$";
	private static final String JSON_SHOPWARE_MAPPINGS = "01_JsonExternalSystemShopware6ConfigMappings.json";
	private static final String HAPPY_FLOW = "happyFlow/";

	private static final String JSON_ORDERS_PAGE_RESOURCE_PATH = HAPPY_FLOW + "10_JsonOrders_Page_" + ORDER_PAGE_INDEX_TOKEN + ".json";
	protected static final String JSON_ORDERS_RESOURCE_PATH = HAPPY_FLOW + "10_JsonOrders.json";
	private static final String JSON_ORDER_TRANSACTIONS_PATH = HAPPY_FLOW + "12_JsonOrderTransactions.json";
	private static final String JSON_ORDER_PAYMENT_METHOD_PATH = HAPPY_FLOW + "14_JsonPaymentMethod.json";
	private static final String JSON_ORDER_GROUPS_PATH = HAPPY_FLOW + "16_JsonCustomerGroups.json";
	private static final String JSON_ORDER_DELIVERIES_PATH = HAPPY_FLOW + "20_JsonOrderDeliveries.json";
	private static final String JSON_ORDER_BILLING_ADDRESS_PATH = HAPPY_FLOW + "30_Billing_JsonOrderAddressCustomId.json";
	private static final String JSON_ORDER_LINES = HAPPY_FLOW + "40_JsonOrderLines.json";
	private static final String JSON_COUNTRY_INFO_PATH = "JsonCountry_DE.json";

	protected static final String JSON_UPSERT_BPARTNER_REQUEST = HAPPY_FLOW + "50_CamelUpsertBPartnerCompositeRequest.json";
	protected static final String JSON_UPSERT_BPARTNER_RESPONSE = HAPPY_FLOW + "50_CamelUpsertBPartnerCompositeResponse.json";

	protected static final String JSON_OL_CAND_CREATE_REQUEST = HAPPY_FLOW + "60_JsonOLCandCreateBulkRequest.json";
	protected static final String JSON_ORDER_PAYMENT_CREATE_REQUEST = HAPPY_FLOW + "63_JsonOrderPaymentCreateRequest.json";

	protected static final String JSON_UPSERT_RUNTIME_PARAMS_REQUEST = HAPPY_FLOW + "65_JsonESRuntimeParameterUpsertRequest.json";

	protected static final String JSON_OL_CAND_PROCESS_REQUEST = HAPPY_FLOW + "70_JsonOLCandProcessRequest.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetOrdersRouteBuilder_HappyFlow_Tests.class.getClassLoader().getResourceAsStream("application.properties"));
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
		final ProducerTemplate producerTemplate = Mockito.mock(ProducerTemplate.class);

		return new GetOrdersRouteBuilder(processLogger, producerTemplate);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockUpsertBPartnerProcessor createdBPartnerProcessor = new MockUpsertBPartnerProcessor(JSON_UPSERT_BPARTNER_RESPONSE);
		final MockSuccessfullyCreatedOLCandProcessor successfullyCreatedOLCandProcessor = new MockSuccessfullyCreatedOLCandProcessor();
		final MockSuccessfullyProcessOLCandProcessor successfullyProcessOLCandProcessor = new MockSuccessfullyProcessOLCandProcessor();
		final MockSuccessfullyCreatePaymentProcessor createPaymentProcessor = new MockSuccessfullyCreatePaymentProcessor();
		final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor = new MockSuccessfullyUpsertRuntimeParamsProcessor();
		final MockSuccessfullyCalledGetOrderPage mockSuccessfullyCalledGetOrderPage = new MockSuccessfullyCalledGetOrderPage();

		final JsonExternalSystemRequest request = createJsonExternalSystemRequestBuilder()
				.productLookup(JsonProductLookup.ProductId)
				.build();

		final MockBuildOrdersContextProcessor buildOrdersContextProcessor = new MockBuildOrdersContextProcessor(request, 2, JSON_ORDERS_PAGE_RESOURCE_PATH, 2);

		prepareRouteForTesting(createdBPartnerProcessor,
							   successfullyCreatedOLCandProcessor,
							   successfullyProcessOLCandProcessor,
							   runtimeParamsProcessor,
							   createPaymentProcessor,
							   buildOrdersContextProcessor,
							   mockSuccessfullyCalledGetOrderPage);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		// validate BPUpsertCamelRequest
		final InputStream expectedUpsertBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);

		final MockEndpoint bPartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bPartnerMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPartnerRequestIS, BPUpsertCamelRequest.class));

		//validate JsonOLCandCreateBulkRequest
		final InputStream olCandCreateRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_CREATE_REQUEST);

		final MockEndpoint olCandMockEndpoint = getMockEndpoint(MOCK_OL_CAND_CREATE);
		olCandMockEndpoint.expectedBodiesReceived(objectMapper.readValue(olCandCreateRequestIS, JsonOLCandCreateBulkRequest.class));

		//validate JsonOLCandProcessRequest
		final InputStream jsonOLCandProcessRequestIS = this.getClass().getResourceAsStream(JSON_OL_CAND_PROCESS_REQUEST);

		final MockEndpoint olCandProcessEndpoint = getMockEndpoint(MOCK_OL_CAND_PROCESS);
		olCandProcessEndpoint.expectedBodiesReceived(objectMapper.readValue(jsonOLCandProcessRequestIS, JsonOLCandProcessRequest.class));

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
		assertThat(successfullyProcessOLCandProcessor.called).isEqualTo(1);
		assertThat(runtimeParamsProcessor.called).isEqualTo(1);
		assertThat(createPaymentProcessor.called).isEqualTo(1);
		assertThat(buildOrdersContextProcessor.called).isEqualTo(1);
		assertThat(mockSuccessfullyCalledGetOrderPage.called).isEqualTo(2);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(
			final MockUpsertBPartnerProcessor upsertBPartnerProcessor,
			final MockSuccessfullyCreatedOLCandProcessor olCandProcessor,
			final MockSuccessfullyProcessOLCandProcessor processOLCandProcessor,
			final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor,
			final MockSuccessfullyCreatePaymentProcessor createPaymentProcessor,
			final MockBuildOrdersContextProcessor buildOrdersContextProcessor,
			final MockSuccessfullyCalledGetOrderPage mockSuccessfullyCalledGetOrderPage) throws Exception
	{
		AdviceWith.adviceWith(context, GET_ORDERS_ROUTE_ID,
							  advice -> advice.weaveById(BUILD_ORDERS_CONTEXT_PROCESSOR_ID)
									  .replace()
									  .process(buildOrdersContextProcessor));

		AdviceWith.adviceWith(context, PROCESS_ORDERS_PAGE_ROUTE_ID,
							  advice -> advice.weaveById(GET_ORDERS_PAGE_PROCESSOR_ID)
									  .after()
									  .process(mockSuccessfullyCalledGetOrderPage));

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
							  advice -> {
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

		AdviceWith.adviceWith(context, PROCESS_OLCAND_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_OL_CAND_PROCESS)
									  .process(processOLCandProcessor));

		AdviceWith.adviceWith(context, UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_UPSERT_RUNTIME_PARAMETERS)
									  .process(runtimeParamsProcessor));
	}

	protected static String loadAsString(@NonNull final String name)
	{
		final InputStream inputStream = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}

	protected static class MockSuccessfullyCreatedOLCandProcessor implements Processor
	{
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	public static class MockBuildOrdersContextProcessor implements Processor
	{
		private final JsonExternalSystemRequest externalSystemRequest;
		private final String jsonOrderLinesPath;
		private final String jsonOrderPath;
		private final String customJsonPaymentMethod;
		private final String customJsonOrderTransaction;

		private final int numberOfPages;
		private final int pageSize;

		protected int called = 0;

		public MockBuildOrdersContextProcessor(@NonNull final JsonExternalSystemRequest externalSystemRequest, final int numberOfPages, final String jsonOrderPath, final int pageSize)
		{
			this(externalSystemRequest, null, numberOfPages, jsonOrderPath, pageSize);
		}

		public MockBuildOrdersContextProcessor(
				@NonNull final JsonExternalSystemRequest externalSystemRequest,
				@Nullable final String jsonOrderLinesPath,
				final int numberOfPages,
				final String jsonOrderPath,
				final int pageSize)
		{
			this(externalSystemRequest, jsonOrderLinesPath, numberOfPages, jsonOrderPath, pageSize, null, null);
		}

		public MockBuildOrdersContextProcessor(
				@NonNull final JsonExternalSystemRequest externalSystemRequest,
				@Nullable final String jsonOrderLinesPath,
				final int numberOfPages,
				final String jsonOrderPath,
				final int pageSize,
				@Nullable final String customJsonPaymentMethod,
				@Nullable final String customJsonOrderTransaction)

		{
			this.externalSystemRequest = externalSystemRequest;
			this.numberOfPages = numberOfPages;

			this.jsonOrderLinesPath = (Check.isNotBlank(jsonOrderLinesPath))
					? jsonOrderLinesPath
					: JSON_ORDER_LINES;

			this.customJsonPaymentMethod = (Check.isNotBlank(customJsonPaymentMethod))
					? customJsonPaymentMethod
					: JSON_ORDER_PAYMENT_METHOD_PATH;

			this.customJsonOrderTransaction = (Check.isNotBlank(customJsonOrderTransaction))
					? customJsonOrderTransaction
					: JSON_ORDER_TRANSACTIONS_PATH;

			this.jsonOrderPath = jsonOrderPath;
			this.pageSize = pageSize;
		}

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			called++;

			// mock getOrders
			final ObjectMapper mapper = new ObjectMapper();
			mapper.registerModule(new JavaTimeModule());

			// mock shopware client
			final ShopwareClient shopwareClient = prepareShopwareClientMock(mapper);

			//set up the exchange
			final CurrencyInfoProvider currencyInfoProvider = CurrencyInfoProvider.builder()
					.currencyId2IsoCode(ImmutableMap.of(MOCK_CURRENCY_ID, MOCK_EUR_CODE))
					.build();

			final SalutationInfoProvider salutationInfoProvider = SalutationInfoProvider.builder()
					.salutationId2DisplayName(ImmutableMap.of(MOCK_SALUTATION_ID, MOCK_SALUTATION_DISPLAY_NAME,
															  MOCK_BILLING_SALUTATION_ID, MOCK_BILLING_SALUTATION_DISPLAY_NAME))
					.build();

			final ImportOrdersRouteContext ordersContext = BuildOrdersContextProcessor.buildContext(
					externalSystemRequest,
					shopwareClient,
					currencyInfoProvider,
					salutationInfoProvider,
					pageSize);

			exchange.setProperty(ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ordersContext);
		}

		@NonNull
		private ShopwareClient prepareShopwareClientMock(final ObjectMapper mapper) throws IOException
		{
			final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
			final PInstanceLogger pInstanceLogger = PInstanceLogger.of(processLogger);

			final ShopwareClient dumbShopwareClient = ShopwareClient.of("does", "not", "https://www.matter.com", pInstanceLogger);
			final ShopwareClient shopwareClientSpy = Mockito.spy(dumbShopwareClient);

			Mockito.doNothing().when(shopwareClientSpy).refreshTokenIfExpired();

			//1. mock getDeliveries
			final String deliveriesString = loadAsString(JSON_ORDER_DELIVERIES_PATH);

			Mockito.doReturn(ResponseEntity.ok(deliveriesString))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(String.class), any());

			//2. mock get billing order address
			final String billingAddressString = loadAsString(JSON_ORDER_BILLING_ADDRESS_PATH);
			final UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(MOCK_BILLING_ADDRESS_HTTP_URL);
			Mockito.doReturn(ResponseEntity.ok(billingAddressString))
					.when(shopwareClientSpy)
					.performWithRetry(eq(uriComponentsBuilder.build().toUri()), eq(HttpMethod.GET), eq(String.class), any());

			//3. mock getCountryDetails
			final InputStream countryIS = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(JSON_COUNTRY_INFO_PATH);
			final JsonCountry jsonCountry = mapper.readValue(countryIS, JsonCountry.class);

			Mockito.doReturn(Optional.of(jsonCountry))
					.when(shopwareClientSpy)
					.getCountryDetails(any(String.class));

			//4. mock orderLines
			final InputStream orderLinesIS = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(jsonOrderLinesPath);
			final JsonOrderLines orderLines = mapper.readValue(orderLinesIS, JsonOrderLines.class);

			Mockito.doReturn(ResponseEntity.ok(orderLines))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonOrderLines.class), any());

			//5. mock order transactions
			final InputStream orderTrxIS = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(customJsonOrderTransaction);
			final JsonOrderTransactions orderTransactions = mapper.readValue(orderTrxIS, JsonOrderTransactions.class);

			Mockito.doReturn(ResponseEntity.ok(orderTransactions))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonOrderTransactions.class), any());

			//6. mock payment method
			final InputStream paymentMethodIS =GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(customJsonPaymentMethod);
			final JsonPaymentMethod paymentMethod = mapper.readValue(paymentMethodIS, JsonPaymentMethod.class);

			Mockito.doReturn(Optional.of(paymentMethod))
					.when(shopwareClientSpy)
					.getPaymentMethod(any());

			//7. mock order customer group
			final InputStream orderCustomerGroup = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(JSON_ORDER_GROUPS_PATH);
			final JsonCustomerGroups customerGroups = mapper.readValue(orderCustomerGroup, JsonCustomerGroups.class);

			Mockito.doReturn(ResponseEntity.ok(customerGroups))
					.when(shopwareClientSpy)
					.performWithRetry(any(), eq(HttpMethod.GET), eq(JsonCustomerGroups.class), any());

			//8. mock get orders
			for (int pageIndex = 1; pageIndex <= numberOfPages; pageIndex++)
			{
				final String actualFileName = jsonOrderPath.contains(ORDER_PAGE_INDEX_TOKEN)
						? jsonOrderPath.replace(ORDER_PAGE_INDEX_TOKEN, String.valueOf(pageIndex))
						: jsonOrderPath;

				final String orders = loadAsString(actualFileName);

				final Shopware6QueryRequest shopware6QueryRequest = OrderQueryHelper.buildShopware6QueryRequest(externalSystemRequest, PageAndLimit.of(pageIndex, pageSize));

				Mockito.doReturn(ResponseEntity.ok(orders))
						.when(shopwareClientSpy)
						.performWithRetry(any(), eq(HttpMethod.POST), eq(String.class), eq(shopware6QueryRequest));
			}

			return shopwareClientSpy;
		}
	}

	protected static class MockUpsertBPartnerProcessor implements Processor
	{
		private final String upsertBPartnerMockResponse;

		protected int called = 0;

		public MockUpsertBPartnerProcessor(@NonNull final String upsertBPartnerMockResponse)
		{
			this.upsertBPartnerMockResponse = upsertBPartnerMockResponse;
		}

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream upsertBPartnerResponse = GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(upsertBPartnerMockResponse);
			exchange.getIn().setBody(upsertBPartnerResponse);
		}
	}

	static class MockSuccessfullyProcessOLCandProcessor implements Processor
	{
		@Getter
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	static class MockSuccessfullyUpsertRuntimeParamsProcessor implements Processor
	{
		@Getter
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	protected static class MockSuccessfullyCreatePaymentProcessor implements Processor
	{
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	protected static class MockSuccessfullyCalledGetOrderPage implements Processor
	{
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	@NonNull
	@Builder(builderMethodName = "createJsonExternalSystemRequestBuilder", builderClassName = "JsonExternalSystemRequestBuilder")
	protected static JsonExternalSystemRequest creatJsonExternalSystemRequest(
			@Nullable final String orderId,
			@Nullable final String orderNo,
			@Nullable final JsonProductLookup productLookup,
			@Nullable final String customJsonShopwareMappingPath,
			@Nullable final Map<String, String> overrideParams) throws IOException
	{
		final ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());

		final InputStream shopwareMappingsIS = Optional.ofNullable(customJsonShopwareMappingPath)
				.map(GetOrdersRouteBuilder_HappyFlow_Tests.class::getResourceAsStream)
				.orElse(GetOrdersRouteBuilder_HappyFlow_Tests.class.getResourceAsStream(JSON_SHOPWARE_MAPPINGS));

		final JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappings = mapper.readValue(shopwareMappingsIS, JsonExternalSystemShopware6ConfigMappings.class);

		final JsonProductLookup lookup = CoalesceUtil.coalesceNotNull(productLookup, JsonProductLookup.ProductNumber);

		final Map<String, String> parameters = new HashMap<>();
		parameters.put(PARAM_NORMAL_VAT_RATES, MOCK_NORMAL_VAT_RATES);
		parameters.put(PARAM_FREIGHT_COST_NORMAL_PRODUCT_ID, String.valueOf(MOCK_NORMAL_VAT_PRODUCT_ID));
		parameters.put(PARAM_REDUCED_VAT_RATES, MOCK_REDUCED_VAT_RATES);
		parameters.put(PARAM_FREIGHT_COST_REDUCED_PRODUCT_ID, String.valueOf(MOCK_REDUCED_VAT_PRODUCT_ID));
		parameters.put(PARAM_ORDER_ID, orderId);
		parameters.put(PARAM_ORDER_NO, orderNo);
		parameters.put(PARAM_PRODUCT_LOOKUP, lookup.name());
		parameters.put(PARAM_CONFIG_MAPPINGS, mapper.writeValueAsString(shopware6ConfigMappings));
		parameters.put(PARAM_JSON_PATH_EMAIL, MOCK_JSON_EMAIL_PATH);
		parameters.put(PARAM_TARGET_PRICE_LIST_ID, MOCK_TARGET_PRICE_LIST_ID);
		parameters.put(PARAM_IS_TAX_INCLUDED, MOCK_IS_TAX_INCLUDED);
		parameters.put(PARAM_PRICE_LIST_CURRENCY_CODE, MOCK_EUR_CODE);
		parameters.put(PARAM_JSON_PATH_CONSTANT_METASFRESH_ID, MOCK_JSON_METASFRESH_ID_PATH);
		parameters.put(PARAM_JSON_PATH_CONSTANT_SHOPWARE_ID, MOCK_JSON_SHOPWARE_ID_PATH);
		parameters.put(PARAM_JSON_PATH_SALES_REP_ID, MOCK_JSON_PATH_SALES_REP_ID);
		parameters.put(PARAM_ORDER_PROCESSING, JsonOrderProcessingConfig.INVOICE.getCode());

		if (overrideParams != null)
		{
			parameters.putAll(overrideParams);
		}

		return JsonExternalSystemRequest
				.builder()
				.traceId(MOCK_TRACE_ID)
				.externalSystemName(JsonExternalSystemName.of(SHOPWARE6_SYSTEM_NAME))
				.externalSystemChildConfigValue("ChildValue")
				.externalSystemConfigId(JsonMetasfreshId.of(1))
				.orgCode(MOCK_ORG_CODE)
				.command("command")
				.parameters(parameters)
				.build();
	}
}
