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

import de.metas.camel.ebay.processor.GetEbayOrdersProcessor;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemName;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;

@CamelSpringTest
@ContextConfiguration(classes = EbayOrderImportRouteTest.ContextConfig.class)
public class EbayOrdersProcessorTest {
	
	@Autowired
	protected CamelContext camelContext;
	
	@EndpointInject("mock:result")
	protected MockEndpoint resultEndpoint;
	
	@Produce("direct:Ebay-GetOrders")
    protected ProducerTemplate template;

	@Test
	@DirtiesContext
	public void testMock() throws Exception {
		String expectedBody = "<matched/>";

        resultEndpoint.expectedBodiesReceived(expectedBody);

        
        Map<String,String> parameters = new HashMap<>();
        parameters.put(ExternalSystemConstants.PARAM_UPDATED_AFTER, "%5B2016-03-21T08:25:43.511Z%5D");

        
        
        JsonExternalSystemRequest jesr = new JsonExternalSystemRequest("orgCode", JsonExternalSystemName.of("ebay"), "command", JsonMetasfreshId.of(1), parameters);

        template.sendBody(jesr);
        
        resultEndpoint.assertIsSatisfied();
	}
	
	
	@Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Override
        @Bean
        public RouteBuilder route() {
        	return new RouteBuilder() {
                public void configure() {
                    from("direct:start").process(new GetEbayOrdersProcessor()).to("mock:result");
                }
            };
        }
    }

}