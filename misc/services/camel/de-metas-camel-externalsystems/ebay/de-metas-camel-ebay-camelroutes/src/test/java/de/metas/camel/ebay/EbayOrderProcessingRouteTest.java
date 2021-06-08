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

package de.metas.camel.ebay;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
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
import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
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
public class EbayOrderProcessingRouteTest {
	
	@Autowired
	protected CamelContext camelContext;
	
	//@EndpointInject("metasfresh.upsert-bpartner.camel.uri") 
	@EndpointInject("mock:result")
	protected MockEndpoint resultEndpoint;
	
	@EndpointInject("mock:direct:metasfresh.upsert-bpartner-v2.camel.uri")
	protected MockEndpoint resultBParnters;
	
	@EndpointInject("mock:direct:To-MF_PushOLCandidates-Route")
	protected MockEndpoint resultOLCs;
	
	@Produce("direct:start")
    protected ProducerTemplate template;
	
	@Mock
	public OrderApi orderApi;
	
	@Mock
	public OAuth2Api authApi;
	
	public List<Order> orders;
	
	
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
	public void testRouteSetup() throws Exception {
		
		//mock result of bpartner upsert.
		final MockUpsertBPartnerProcessor createdBPartnerProcessor = new MockUpsertBPartnerProcessor();
		AdviceWith.adviceWith(camelContext, GetEbayOrdersRouteBuilder.PROCESS_ORDERS_ROUTE_ID,
				advice -> advice.interceptSendToEndpoint("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI+ "}}")
						.skipSendToOriginalEndpoint()
						.process(createdBPartnerProcessor)

		);
		 
		 camelContext.start();
		 
		
		//our assertions for the example json
		resultBParnters.expectedMessageCount(1);
		
		
		//prepare api call
        Map<String,String> parameters = new HashMap<>();
        parameters.put(ExternalSystemConstants.PARAM_API_KEY, "key");
        parameters.put(ExternalSystemConstants.PARAM_TENANT, "tenant");
        parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, "ua");
        parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, "bp");
        parameters.put(EbayConstants.PARAM_API_MODE, "sandbox");
        parameters.put(EbayConstants.PARAM_API_AUTH_CODE, "");
        parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, "%5B2016-03-21T08:25:43.511Z%5D");
        
        JsonExternalSystemRequest jesr = new JsonExternalSystemRequest(
        		"orgCode", 							// ??
        		JsonExternalSystemName.of("ebay"),
        		"command", 							//command to execute
        		null,  								//adPInstanceId
        		JsonMetasfreshId.of(1), 			//externalSystemConfigId
        		parameters);						//params
        
        
        //put mock clients into body
        Map<String,Object> body = new HashMap<>();
        body.put(EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT, orderApi);
        body.put(EbayConstants.ROUTE_PROPERTY_EBAY_AUTH_CLIENT, authApi);

        //prepare order api
    	ObjectMapper mapper = new ObjectMapper();
        InputStream is = EbayOrderProcessingRouteTest.class.getResourceAsStream("/examples/ebay-order-new-of-consumer.json");
        OrderSearchPagedCollection mockResult = mapper.readValue(is, OrderSearchPagedCollection.class);
        
        when(orderApi.getOrders(any(), any(), any(), any(), any())).thenReturn(mockResult);
        
        //prepare auth api
        Optional<AccessToken> token = Optional.of(new AccessToken());
        Optional<RefreshToken> rtoken = Optional.of(new RefreshToken());
        
        OAuthResponse res = new OAuthResponse(token, rtoken);
        when(authApi.exchangeCodeForAccessToken(any(),anyString())).thenReturn(res);
        
        
        //send message
        template.sendBodyAndHeaders("direct:" + GetEbayOrdersRouteBuilder.GET_ORDERS_ROUTE_ID, jesr, body);
        
        //check assertions
        resultBParnters.assertIsSatisfied();
	}
	
	
	@Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Override
        @Bean
        public RouteBuilder route() {
        	return new GetEbayOrdersRouteBuilder();
        }
    }
	
	
	private static class MockUpsertBPartnerProcessor implements Processor
	{
		private int called = 0;

		@Override
		public void process(final Exchange exchange)
		{
			called++;
			final InputStream upsertBPartnerResponse = EbayOrderProcessingRouteTest.class.getResourceAsStream("/metas-mock-results/50_CamelUpsertBPartnerCompositeResponse.json");
			exchange.getIn().setBody(upsertBPartnerResponse);
		}
	}

}
