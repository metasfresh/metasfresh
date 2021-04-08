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
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.country.JsonCountry;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddress;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAddressAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderAndCustomId;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrderDeliveries;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonOrders;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.GET_ORDERS_PROCESSOR_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.GET_ORDERS_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.GetOrdersRouteBuilder.PROCESS_ORDER_ROUTE_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_ORG_CODE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_SHOPWARE_CLIENT;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_ID_JSON_PATH;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_BPARTNER_LOCATION_ID_JSON_PATH;
import static de.metas.camel.externalsystems.shopware6.ShopwareTestConstants.MOCK_ORG_CODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

public class GetOrdersRouteBuilderTests extends CamelTestSupport
{
	private static final String MOCK_SHOPWARE_TO_MF_RESULT = "mock:ShopwareToMFResult";

	private static final String JSON_ORDERS_RESOURCE_PATH = "/de/metas/camel/externalsystems/shopware6/JsonOrders.json";
	private static final String JSON_ORDER_DELIVERIES_PATH = "/de/metas/camel/externalsystems/shopware6/JsonOrderDeliveries.json";
	private static final String JSON_ORDER_BILLING_ADDRESS_PATH = "/de/metas/camel/externalsystems/shopware6/JsonOrderAddressCustomId.json";
	private static final String JSON_COUNTRY_INFO_PATH = "/de/metas/camel/externalsystems/shopware6/JsonCountry.json";

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
											  // validate the upsert request and send a response
											  advice.weaveById(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
													  .after()
													  .to(MOCK_SHOPWARE_TO_MF_RESULT);
											  advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
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

			final List<JsonOrderAndCustomId> jsonOrderAndCustomIds = jsonOrders
					.getData()
					.stream()
					.map(order -> JsonOrderAndCustomId.builder().jsonOrder(order).build())
					.collect(Collectors.toList());

			// mock shopware client
			final ShopwareClient shopwareClient = prepareShopwareClientMock(mapper);

			//set up the exchange
			exchange.getIn().setBody(jsonOrderAndCustomIds);
			exchange.setProperty(ROUTE_PROPERTY_ORG_CODE, MOCK_ORG_CODE);
			exchange.setProperty(ROUTE_PROPERTY_SHOPWARE_CLIENT, shopwareClient);
			exchange.setProperty(ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_ID, MOCK_BPARTNER_ID_JSON_PATH);
			exchange.setProperty(ROUTE_PROPERTY_PATH_CONSTANT_BPARTNER_LOCATION_ID, MOCK_BPARTNER_LOCATION_ID_JSON_PATH);
		}

		@NonNull
		private ShopwareClient prepareShopwareClientMock(final ObjectMapper mapper) throws IOException
		{
			final ShopwareClient shopwareClient = Mockito.mock(ShopwareClient.class);

			//1. mock getDeliveries
			final InputStream deliveriesIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_ORDER_DELIVERIES_PATH);
			final List<JsonOrderAddressAndCustomId> deliveryAddresses = mapper.readValue(deliveriesIS, JsonOrderDeliveries.class)
					.getData()
					.stream()
					.map(jsonOrderDelivery -> JsonOrderAddressAndCustomId.builder().jsonOrderAddress(jsonOrderDelivery.getShippingOrderAddress()).build())
					.collect(Collectors.toList());

			Mockito.when(shopwareClient.getDeliveryAddresses(any(String.class), any(String.class)))
					.thenReturn(deliveryAddresses);

			//2. mock getOrderAddressDetails
			final InputStream billingAddressIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_ORDER_BILLING_ADDRESS_PATH);
			final JsonOrderAddress billingAddress = mapper.readValue(billingAddressIS, JsonOrderAddress.class);

			Mockito.when(shopwareClient.getOrderAddressDetails(any(String.class), any(String.class)))
					.thenReturn(Optional.of(JsonOrderAddressAndCustomId.builder()
													.jsonOrderAddress(billingAddress)
													.customId(billingAddress.getId() + "_custom")
													.build()));

			//3. mock getCountryDetails
			final InputStream countryIS = GetOrdersRouteBuilderTests.class.getResourceAsStream(JSON_COUNTRY_INFO_PATH);
			final JsonCountry jsonCountry = mapper.readValue(countryIS, JsonCountry.class);

			Mockito.when(shopwareClient.getCountryDetails(any(String.class)))
					.thenReturn(Optional.of(jsonCountry));

			return shopwareClient;
		}
	}
}
