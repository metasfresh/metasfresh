/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.product.GetProductsRouteHelper;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_UPSERT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CUSTOMER_ADDRESS_HTTP_URL;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_CUSTOMER_GROUP_HTTP_URL;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_GET_CUSTOMERS_HTTP_URL;
import static de.metas.camel.externalsystems.shopware6.customer.GetCustomersRouteBuilder.GET_CUSTOMERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.customer.GetCustomersRouteBuilder.PREPARE_CONTEXT_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.customer.GetCustomersRouteBuilder.PROCESS_CUSTOMER_ROUTE_ID;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class GetCustomersRouteBuilderTest extends CamelTestSupport
{
	protected static final String JSON_EXTERNAL_SYSTEM_REQUEST = "10_JsonExternalSystemRequest.json";
	protected static final String JSON_CUSTOMER_RESOURCE_PATH = "20_JsonCustomer.json";
	protected static final String JSON_CUSTOMER_ADDRESS_DETAIL_PATH = "30_JsonAddress.json";
	protected static final String JSON_CUSTOMER_GROUP_PATH = "40_JsonCustomerGroup.json";
	protected static final String JSON_UPSERT_CUSTOMER_REQUEST = "50_BPUpsertCamelRequest.json";
	protected static final String JSON_COUNTRY_INFO_PATH = "JsonCountry_DE.json";

	protected final static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetCustomersRouteBuilder.class.getClassLoader().getResourceAsStream("application.properties"));
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
		final ProcessLogger processLoggerMock = Mockito.mock(ProcessLogger.class);
		return new GetCustomersRouteBuilder(processLoggerMock);
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockPrepareContextProcessor mockPrepareContextProcessor = new MockPrepareContextProcessor(loadAsString(JSON_CUSTOMER_RESOURCE_PATH));
		final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor = new MockUpsertBPartnerProcessor();
		final MockSuccessfullyUpsertRuntimeParamsProcessor mockRuntimeParamsProcessor = new MockSuccessfullyUpsertRuntimeParamsProcessor();

		//given
		prepareRouteForTesting(mockPrepareContextProcessor, mockUpsertBPartnerProcessor, mockRuntimeParamsProcessor);

		context.start();

		final InputStream expectedUpsertCustomerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_CUSTOMER_REQUEST);
		final MockEndpoint bpartnerMockEndpoint = getMockEndpoint(MOCK_BPARTNER_UPSERT);
		bpartnerMockEndpoint.expectedBodiesReceived(mapper.readValue(expectedUpsertCustomerRequestIS, BPUpsertCamelRequest.class));

		final InputStream invokeExternalSystemRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_SYSTEM_REQUEST);
		final JsonExternalSystemRequest invokeExternalSystemRequest = mapper
				.readValue(invokeExternalSystemRequestIS, JsonExternalSystemRequest.class);

		//when
		template.sendBody("direct:" + GET_CUSTOMERS_ROUTE_ID, invokeExternalSystemRequest);

		//then
		assertMockEndpointsSatisfied();
		assertThat(mockPrepareContextProcessor.called).isEqualTo(1);
		assertThat(mockUpsertBPartnerProcessor.called).isEqualTo(1);
		// dev-note: when `JsonExternalSystemRequest` contains `UpdatedAfterOverride` param, `UPSERT_RUNTIME_PARAMS_ROUTE_ID` route is not called
		assertThat(mockRuntimeParamsProcessor.called).isEqualTo(0);
	}

	protected void prepareRouteForTesting(
			@NonNull final MockPrepareContextProcessor mockPrepareContextProcessor,
			@NonNull final MockUpsertBPartnerProcessor mockUpsertBPartnerProcessor,
			@NonNull final MockSuccessfullyUpsertRuntimeParamsProcessor runtimeParamsProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, GET_CUSTOMERS_ROUTE_ID,
							  advice -> advice.weaveById(PREPARE_CONTEXT_PROCESSOR_ID)
									  .replace()
									  .process(mockPrepareContextProcessor));

		AdviceWith.adviceWith(context, PROCESS_CUSTOMER_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
									  .skipSendToOriginalEndpoint()
									  .to(MOCK_BPARTNER_UPSERT)
									  .process(mockUpsertBPartnerProcessor));

		AdviceWith.adviceWith(context, GetCustomersRouteBuilder.UPSERT_RUNTIME_PARAMS_ROUTE_ID,
							  advice -> advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID)
									  .skipSendToOriginalEndpoint()
									  .process(runtimeParamsProcessor));
	}

	protected static class MockPrepareContextProcessor implements Processor
	{
		private final String customerMockResponse;

		protected int called = 0;

		public MockPrepareContextProcessor(@NonNull final String customMockResponse)
		{
			this.customerMockResponse = customMockResponse;
		}

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			called++;

			final JsonExternalSystemRequest request = exchange.getIn().getBody(JsonExternalSystemRequest.class);

			final String updatedAfterOverride = request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER_OVERRIDE);

			final String updatedAfter = CoalesceUtil.coalesceNotNull(
					updatedAfterOverride,
					request.getParameters().get(ExternalSystemConstants.PARAM_UPDATED_AFTER),
					Instant.ofEpochSecond(0).toString());

			final boolean skipNextImportTimestamp = Optional.ofNullable(updatedAfterOverride).isPresent();

			final ImportCustomersRouteContext customersRouteContext = ImportCustomersRouteContext.builder()
					.orgCode(request.getOrgCode())
					.shopwareClient(prepareShopwareClientMock())
					.request(request)
					.updatedAfter(updatedAfter)
					.priceListBasicInfo(GetProductsRouteHelper.getTargetPriceListInfo(request))
					.skipNextImportTimestamp(skipNextImportTimestamp)
					.pageLimit(2) // this is a bit of a hack; we go with a page-size of two, knowing that our mocked paginated endpoint with return just one partner. so we'll invoke that EP just once
					.build();

			exchange.setProperty(ROUTE_PROPERTY_IMPORT_CUSTOMERS_CONTEXT, customersRouteContext);
		}

		@NonNull
		private ShopwareClient prepareShopwareClientMock() throws IOException
		{
			final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
			final PInstanceLogger pInstanceLogger = PInstanceLogger.of(processLogger);

			final ShopwareClient dumbShopwareClient = ShopwareClient.of("does", "not", "https://www.matter.com", pInstanceLogger);
			final ShopwareClient shopwareClientSpy = Mockito.spy(dumbShopwareClient);

			Mockito.doNothing().when(shopwareClientSpy).refreshTokenIfExpired();

			//1. mock get customer
			final UriComponentsBuilder uriComponentsBuilderCustomers = UriComponentsBuilder.fromHttpUrl(MOCK_GET_CUSTOMERS_HTTP_URL);
			Mockito.doReturn(ResponseEntity.ok(customerMockResponse))
					.when(shopwareClientSpy)
					.performWithRetry(eq(uriComponentsBuilderCustomers.build().toUri()), eq(HttpMethod.POST), eq(String.class), ArgumentMatchers.any());

			//2. mock get customer address
			final String customerAddressString = loadAsString(JSON_CUSTOMER_ADDRESS_DETAIL_PATH);
			final UriComponentsBuilder uriComponentsBuilderAddress = UriComponentsBuilder.fromHttpUrl(MOCK_CUSTOMER_ADDRESS_HTTP_URL);
			Mockito.doReturn(ResponseEntity.ok(customerAddressString))
					.when(shopwareClientSpy)
					.performWithRetry(eq(uriComponentsBuilderAddress.build().toUri()), eq(HttpMethod.GET), eq(String.class), ArgumentMatchers.any());

			//3. mock get customer group
			final String customerGroupString = loadAsString(JSON_CUSTOMER_GROUP_PATH);
			final UriComponentsBuilder uriComponentsBuilderGroup = UriComponentsBuilder.fromHttpUrl(MOCK_CUSTOMER_GROUP_HTTP_URL);
			Mockito.doReturn(ResponseEntity.ok(customerGroupString))
					.when(shopwareClientSpy)
					.performWithRetry(eq(uriComponentsBuilderGroup.build().toUri()), eq(HttpMethod.GET), eq(String.class), ArgumentMatchers.any());

			//4. mock getCountryDetails
			final InputStream countryIS = GetCustomersRouteBuilderTest.class.getResourceAsStream(JSON_COUNTRY_INFO_PATH);
			final JsonCountry jsonCountry = mapper.readValue(countryIS, JsonCountry.class);

			Mockito.doReturn(Optional.of(jsonCountry))
					.when(shopwareClientSpy)
					.getCountryDetails(any(String.class));

			return shopwareClientSpy;
		}
	}

	protected static class MockUpsertBPartnerProcessor implements Processor
	{
		protected int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	protected static class MockSuccessfullyUpsertRuntimeParamsProcessor implements Processor
	{
		protected int called = 0;

		@Override
		public void process(final Exchange exchange) throws IOException
		{
			called++;
		}
	}

	protected static String loadAsString(@NonNull final String name)
	{
		final InputStream inputStream = GetCustomersRouteBuilderTest.class.getResourceAsStream(name);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}
}
