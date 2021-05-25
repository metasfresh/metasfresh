package de.metas.camel.ebay;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.camel.externalsystems.ebay.api.model.Order;
import de.metas.camel.externalsystems.ebay.api.model.OrderSearchPagedCollection;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;

@CamelSpringTest
@ContextConfiguration(classes = EbayOrderProcessingRouteTest.ContextConfig.class)
public class EbayOrderProcessingRouteTest {
	
	@Autowired
	protected CamelContext camelContext;
	
	//@EndpointInject("metasfresh.upsert-bpartner.camel.uri") 
	@EndpointInject("mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce("direct:start")
    protected ProducerTemplate template;
	
	@Mock
	public OrderApi orderApi;
	
	@Mock
	public OAuth2Api authApi;
	
	public List<Order> orders;
	

	@Test
	@DirtiesContext
	public void testRouteSetup() throws Exception {
		
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
        
        
        
        Map<String,Object> body = new HashMap<>();
        body.put(EbayConstants.ROUTE_PROPERTY_EBAY_CLIENT, orderApi);
        body.put(EbayConstants.ROUTE_PROPERTY_EBAY_AUTH_CLIENT, authApi);

        
        //prepare order api
    	ObjectMapper mapper = new ObjectMapper();
        InputStream is = EbayOrderProcessingRouteTest.class.getResourceAsStream("/examples/ebay-order-single.json");
        
        //CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, Order.class);
        
        OrderSearchPagedCollection mockResult = mapper.readValue(is, OrderSearchPagedCollection.class);
       
        
        OrderSearchPagedCollection ebayResponse = new OrderSearchPagedCollection();
        ebayResponse.setOrders(null);
        when(orderApi.getOrders(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(ebayResponse);
        
         
        //prepare auth api
        Optional<AccessToken> token = Optional.of(new AccessToken());
        Optional<RefreshToken> rtoken = Optional.of(new RefreshToken());
        
        OAuthResponse res = new OAuthResponse(token, rtoken);
        when(authApi.exchangeCodeForAccessToken(any(),anyString())).thenReturn(res);
        
        template.sendBodyAndHeaders("direct:" + GetEbayOrdersRouteBuilder.GET_ORDERS_ROUTE_ID, jesr, body);
        
        
        Thread.sleep(2000);
        resultEndpoint.assertIsSatisfied();
	}
	
	
	@Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Override
        @Bean
        public RouteBuilder route() {
        	return new GetEbayOrdersRouteBuilder();
        }
    }

}
