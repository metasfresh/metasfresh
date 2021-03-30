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
import de.metas.camel.externalsystems.common.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDeliveries;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrders;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.CREATE_ESR_QUERY_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.GET_ORDERS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_ORG_CODE;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class GetOrdersRouteBuilderTests extends CamelTestSupport
{
	private static final String MOCK_ESR_QUERY_REQUEST = "mock:esrQueryRequest";
	private static final String MOCK_SHOPWARE_TO_MF_RESULT = "mock:ShopwareToMFResult";

	private static final String JSON_ORDERS_RESOURCE_PATH = "/de/metas/camel/externalsystems/shopware6/JsonOrders.json";
	private static final String JSON_ORDER_DELIVERIES_PATH = "/de/metas/camel/externalsystems/shopware6/JsonOrderDeliveries.json";
	private static final String JSON_COUNTRY_INFO_PATH = "/de/metas/camel/externalsystems/shopware6/JsonCountry.json";

	private static final String JSON_EXTERNAL_REFERENCE_LOOKUP_RESPONSE = "/de/metas/camel/externalsystems/shopware6/JsonExternalREfLookupResponse.json";
	private static final String JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST = "/de/metas/camel/externalsystems/shopware6/JsonExternalREfLookupRequest.json";
	private static final String JSON_UPSERT_BPARTNER_REQUEST = "/de/metas/camel/externalsystems/shopware6/CamelUpsertBPartnerCompositeRequest.json";

	@Override
	protected Properties useOverridePropertiesWithPropertiesComponent()
	{
		final var properties = new Properties();
		try
		{
			properties.load(GetOrdersRouteBuilderTests.class.getClassLoader().getResourceAsStream("application.properties"));
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
		return new GetOrdersRouteBuilder();
	}

	@Override
	public boolean isUseAdviceWith()
	{
		return true;
	}

	@Test
	void happyFlow() throws Exception
	{
		final MockSuccessfullyCreatedBPartnerProcessor createdBPartnerProcessor = new MockSuccessfullyCreatedBPartnerProcessor();

		prepareRouteForTesting(createdBPartnerProcessor);

		context.start();

		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());

		//validate esr query request
		final InputStream esrQueryRequestIS = this.getClass().getResourceAsStream(JSON_EXTERNAL_REFERENCE_LOOKUP_REQUEST);
		
		final MockEndpoint esrQueryValidationMockEndpoint = getMockEndpoint(MOCK_ESR_QUERY_REQUEST);
		esrQueryValidationMockEndpoint.expectedBodiesReceived(objectMapper.readValue(esrQueryRequestIS, JsonExternalReferenceLookupRequest.class));

		//validate the final outcome
		final InputStream expectedUpsertBPartnerRequestIS = this.getClass().getResourceAsStream(JSON_UPSERT_BPARTNER_REQUEST);

		final MockEndpoint upsertBPValidationMockEndpoint = getMockEndpoint(MOCK_SHOPWARE_TO_MF_RESULT);
		upsertBPValidationMockEndpoint.expectedBodiesReceived(objectMapper.readValue(expectedUpsertBPartnerRequestIS, BPUpsertCamelRequest.class));

		//fire the route
		template.sendBody("direct:" + GET_ORDERS_ROUTE_ID, "Empty body");

		assertThat(createdBPartnerProcessor.called).isEqualTo(1);
		assertMockEndpointsSatisfied();
	}

	private void prepareRouteForTesting(final MockSuccessfullyCreatedBPartnerProcessor successfullyCreatedBPartnerProcessor) throws Exception
	{
		AdviceWith.adviceWith(context, GET_ORDERS_ROUTE_ID,
										  advice -> advice.weaveById(GET_ORDERS_PROCESSOR_ID)
												  .replace()
												  .process(new MockGetOrdersProcessor()));

		AdviceWith.adviceWith(context, PROCESS_ORDER_ROUTE_ID,
										  advice -> {
											  // validate the external reference query and send a response
											  advice.weaveById(CREATE_ESR_QUERY_REQ_PROCESSOR_ID)
													  .after()
													  .to(MOCK_ESR_QUERY_REQUEST);
											  advice.interceptSendToEndpoint("{{" + MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")
													  .skipSendToOriginalEndpoint()
													  .process(new MockExternalReferenceResponse());

											  // validate the upsert request and send a response
											  advice.weaveById(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
													  .after()
													  .to(MOCK_SHOPWARE_TO_MF_RESULT);
											  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}")
													  .skipSendToOriginalEndpoint()
													  .process(successfullyCreatedBPartnerProcessor);
										  });
	}

	private static class MockSuccessfullyCreatedBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockGetOrdersProcessor implements Processor
	{
		@Override
		public void process(final Exchange exchange) throws IOException
		{
			// mock getOrders
			final ObjectMapper mapper = new ObjectMapper();
			final InputStream ordersIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_ORDERS_RESOURCE_PATH);
			final JsonOrders jsonOrders = mapper.readValue(ordersIS, JsonOrders.class);

			// mock shopware client
			final ShopwareClient shopwareClient = prepareShopwareClientMock(mapper);

			//set up the exchange
			exchange.getIn().setBody(jsonOrders.getData());
			exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, MOCK_ORG_CODE);
			exchange.setProperty(ROUTE_PROPERTY_SHOPWARE_CLIENT, shopwareClient);
		}

		@NonNull
		private ShopwareClient prepareShopwareClientMock(final ObjectMapper mapper) throws IOException
		{
			final ShopwareClient shopwareClient = Mockito.mock(ShopwareClient.class);

			//1. mock getDeliveries
			final InputStream deliveriesIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_ORDER_DELIVERIES_PATH);
			final JsonOrderDeliveries jsonOrderDeliveries = mapper.readValue(deliveriesIS, JsonOrderDeliveries.class);

			Mockito.when(shopwareClient.getDeliveries(any(String.class)))
					.thenReturn(Optional.of(jsonOrderDeliveries));

			//2. mock getCountryDetails
			final InputStream countryIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_COUNTRY_INFO_PATH);
			final JsonCountry jsonCountry = mapper.readValue(countryIS, JsonCountry.class);

			Mockito.when(shopwareClient.getCountryDetails(any(String.class)))
					.thenReturn(Optional.of(jsonCountry));

			return shopwareClient;
		}
	}

	private static class MockExternalReferenceResponse implements Processor
	{
		@Override
		public void process(final Exchange exchange)
		{
			final InputStream esrLookupResponse = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_EXTERNAL_REFERENCE_LOOKUP_RESPONSE);

			exchange.getIn().setBody(esrLookupResponse);
		}
	}
}
