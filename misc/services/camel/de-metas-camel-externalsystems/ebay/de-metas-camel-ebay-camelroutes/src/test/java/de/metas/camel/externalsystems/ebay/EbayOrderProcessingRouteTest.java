/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.externalsystems.ebay;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.apache.camel.test.spring.junit5.UseOverridePropertiesWithPropertiesComponent;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import com.ebay.api.client.auth.oauth2.OAuth2Api;
import com.ebay.api.client.auth.oauth2.model.AccessToken;
import com.ebay.api.client.auth.oauth2.model.OAuthResponse;
import com.ebay.api.client.auth.oauth2.model.RefreshToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.model.LineItem;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;

/**
 * Unit test which instantiates the complete ebay order processing route and feeds a
 * mocked json result to process and check, whether all route components are called.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@CamelSpringTest
@ContextConfiguration(classes = EbayOrderProcessingRouteTest.ContextConfig.class)
@MockEndpointsAndSkip("direct:metasfresh.upsert-bpartner-v2.camel.uri")
@UseAdviceWith
public class EbayOrderProcessingRouteTest
{

	@Autowired
	protected CamelContext camelContext;

	@EndpointInject("mock:result")
	protected MockEndpoint resultEndpoint;

	@Produce("direct:start")
	protected ProducerTemplate template;

	@Mock
	public OrderApi orderApi;

	@Mock
	public OAuth2Api authApi;
	

	@UseOverridePropertiesWithPropertiesComponent
	public static Properties overrideProperties()
	{
		final var properties = new Properties();
		try
		{
			properties.load(EbayOrderProcessingRouteTest.class.getClassLoader().getResourceAsStream("application.properties"));
			return properties;
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Test
	@DirtiesContext
	public void flowWithSimpleOrder() throws Exception
	{

		// mock result of bpartner upsert.
		final MockUpsertBPartnerProcessor createdBPartnerProcessor = new MockUpsertBPartnerProcessor();
		final MockUpsertOLCProcessor createdOLCProcessor = new MockUpsertOLCProcessor();
		AdviceWith.adviceWith(camelContext, GetEbayOrdersRouteBuilder.PROCESS_ORDERS_ROUTE_ID,
				advice -> {
					advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
							.skipSendToOriginalEndpoint()
							.process(createdBPartnerProcessor);

					advice.interceptSendToEndpoint("direct:" + MF_PUSH_OL_CANDIDATES_ROUTE_ID)
						.skipSendToOriginalEndpoint()
						.process(createdOLCProcessor);
				});
		
		// mock result of product upsert.
		final MockUpsertProductProcessor createProductProcessor = new MockUpsertProductProcessor();
		final MockUpsertProductPriceProcessor createProductPriceProcessor = new MockUpsertProductPriceProcessor();
		AdviceWith.adviceWith(camelContext, GetEbayOrdersRouteBuilder.PROCESS_PRODUCTS_ROUTE_ID, 
				advice -> {
					
					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI)
							.skipSendToOriginalEndpoint()
							.process(createProductProcessor);

					advice.interceptSendToEndpoint("direct:" + ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI)
						.skipSendToOriginalEndpoint()
						.process(createProductPriceProcessor);
				});

		camelContext.start();


		// prepare api call
		Map<String, String> parameters = new HashMap<>();
		parameters.put(ExternalSystemConstants.PARAM_API_KEY, "key");
		parameters.put(ExternalSystemConstants.PARAM_TENANT, "tenant");
		parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, "bp");
		parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, "%5B2016-03-21T08:25:43.511Z%5D");
		parameters.put(ExternalSystemConstants.PARAM_API_MODE, ApiMode.SANDBOX.name());

		JsonExternalSystemRequest jesr = new JsonExternalSystemRequest(
				"orgCode",
				JsonExternalSystemName.of("ebay"),
				"getOrders",
				null,
				JsonMetasfreshId.of(1),
				parameters,
				"traceId",
				"auditendpoint",
				"externalSystemChildConfigValue");

		// put mock clients into body
		Map<String, Object> body = new HashMap<>();
		body.put(EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT, orderApi);
		body.put(EbayConstants.ROUTE_PROPERTY_EBAY_AUTH_CLIENT, authApi);

		// prepare order api
		ObjectMapper mapper = new ObjectMapper();
		InputStream is = EbayOrderProcessingRouteTest.class.getResourceAsStream("/examples/01_ebay-new-order-single-item-of-consumer.json");
		OrderSearchPagedCollection mockResult = mapper.readValue(is, OrderSearchPagedCollection.class);
		
		when(orderApi.getOrders(any(), any(), any(), any(), any())).thenReturn(mockResult);

		// prepare auth api
		Optional<AccessToken> token = Optional.of(new AccessToken());
		Optional<RefreshToken> rtoken = Optional.of(new RefreshToken());

		OAuthResponse res = new OAuthResponse(token, rtoken);
		when(authApi.getAccessToken(any(), any(), anyList())).thenReturn(res);

		// send message
		template.sendBodyAndHeaders("direct:" + GetEbayOrdersRouteBuilder.GET_ORDERS_ROUTE_ID, jesr, body);

		// check assertions
		assertThat(createdBPartnerProcessor.called).isEqualTo(1);
		assertThat(createdOLCProcessor.called).isEqualTo(1);
		assertThat(createProductProcessor.called).isEqualTo(1);
		//assertThat(createProductPriceProcessor.called).isEqualTo(1);
		
		
		//validate OLC 
		JsonOLCandCreateBulkRequest metasOLCr = createdOLCProcessor.jolccbr;
		
		Order ebayOrder = mockResult.getOrders().get(0);
		assertEquals(ebayOrder.getLineItems().size(), metasOLCr.getRequests().size(), "Matching number of line items generated");
		
		
		//validate line item (values from 01_ebay-new-order-single-item-of-consumer.json).
		JsonOLCandCreateRequest joccr = metasOLCr.getRequests().get(0);
		
		assertEquals( BigDecimal.valueOf(1), joccr.getQty());
		assertEquals("USD", joccr.getCurrencyCode());
		assertEquals("val-34324", joccr.getProductIdentifier());
		assertEquals( BigDecimal.valueOf(10.8), joccr.getPrice());
		
		
	}

	@Configuration
	public static class ContextConfig extends SingleRouteCamelConfiguration
	{
		@Override
		@Bean
		public RouteBuilder route()
		{
			final ProcessLogger processLogger = Mockito.mock(ProcessLogger.class);
			return new GetEbayOrdersRouteBuilder(processLogger);
		}
	}

	private static class MockUpsertBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream upsertBPartnerResponse = EbayOrderProcessingRouteTest.class.getResourceAsStream("/metas-mock-results/01_CamelUpsertBPartnerCompositeResponse.json");
			exchange.getIn().setBody(upsertBPartnerResponse);
		}
	}
	
	private static class MockUpsertProductProcessor implements Processor
	{
		private int called = 0;
		
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream upsertBPartnerResponse = EbayOrderProcessingRouteTest.class.getResourceAsStream("/metas-mock-results/02_CamelUpsertProductRequest.json");
			exchange.getIn().setBody(upsertBPartnerResponse);
		}
	}
	
	private static class MockUpsertProductPriceProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
		}
	}

	private static class MockUpsertOLCProcessor implements Processor
	{
		private int called = 0;
		
		private JsonOLCandCreateBulkRequest jolccbr;
		
		@Override
		public void process(final Exchange exchange)
		{
			called++;
			
			Object body = exchange.getIn().getBody();
			assertNotNull(body, "OCL must be created");
			jolccbr = (JsonOLCandCreateBulkRequest) body;
		}
	}

}
