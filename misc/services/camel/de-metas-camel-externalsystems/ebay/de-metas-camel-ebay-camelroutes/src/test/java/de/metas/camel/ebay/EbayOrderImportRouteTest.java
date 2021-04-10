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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;

@CamelSpringTest
@ContextConfiguration(classes = EbayOrderImportRouteTest.ContextConfig.class)
public class EbayOrderImportRouteTest {
	
	@Autowired
	protected CamelContext camelContext;
	
	//@EndpointInject("metasfresh.upsert-bpartner.camel.uri") 
	@EndpointInject("mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce("direct:Ebay-GetOrders")
    protected ProducerTemplate template;

	@Test
	@DirtiesContext
	public void testMock() throws Exception {
		String expectedBody = "<matched/>";

        resultEndpoint.expectedBodiesReceived(expectedBody);

        //template.sendBodyAndHeader(expectedBody, "foo", "bar");
        
        Map<String,String> parameters = new HashMap<>();
        parameters.put(ExternalSystemConstants.PARAM_API_KEY, "key");
        parameters.put(ExternalSystemConstants.PARAM_TENANT, "tenant");
        parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, "ua");
        parameters.put(ExternalSystemConstants.PARAM_BASE_PATH, "bp");
        
        JsonExternalSystemRequest jesr = new JsonExternalSystemRequest("orgCode", JsonExternalSystemName.of("ebay"),
        		"command", JsonMetasfreshId.of(1), parameters);
        

        template.sendBody(jesr);
        
        resultEndpoint.assertIsSatisfied();
	}
	
	
//	@EndpointInject("mock:result")
//    protected MockEndpoint resultEndpoint;
//
//    @Produce("direct:start")
//    protected ProducerTemplate template;
//
//    @DirtiesContext
//    @org.junit.jupiter.api.Test
//    public void testSendMatchingMessage() throws Exception {
//        String expectedBody = "<matched/>";
//
//        resultEndpoint.expectedBodiesReceived(expectedBody);
//
//        template.sendBodyAndHeader(expectedBody, "foo", "bar");
//
//        resultEndpoint.assertIsSatisfied();
//    }
//
//    @DirtiesContext
//    @Test
//    public void testSendNotMatchingMessage() throws Exception {
//        resultEndpoint.expectedMessageCount(0);
//
//        template.sendBodyAndHeader("<notMatched/>", "foo", "notMatchedHeaderValue");
//
//        resultEndpoint.assertIsSatisfied();
//    }
//	
	
	
	@Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Override
        @Bean
        public RouteBuilder route() {
        	return new GetEbayOrdersRouteBuilder();
//        	return new RouteBuilder() {
//                public void configure() {
//                    from("direct:start").filter(header("foo").isEqualTo("bar")).to("mock:result");
//                }
//            };
        }
    }

}
