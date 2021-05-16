package de.metas.camel.ebay;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.junit5.CamelSpringTest;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import de.metas.camel.externalsystems.ebay.api.OrderApi;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;

import static org.mockito.Mockito.when;

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
        
        //TODO: prepare mocked result.
        when(orderApi.getOrders(null, null, null, null, null)).thenReturn(null);
        
        template.sendBodyAndHeaders("direct:" + GetEbayOrdersRouteBuilder.GET_ORDERS_ROUTE_ID, jesr, body);
        
        
        Thread.sleep(2000);
        
        
//        String expectedBody = "<matched/>";
//        resultEndpoint.expectedBodiesReceived(expectedBody);
        //resultEndpoint.expectedMessageCount(2);

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
