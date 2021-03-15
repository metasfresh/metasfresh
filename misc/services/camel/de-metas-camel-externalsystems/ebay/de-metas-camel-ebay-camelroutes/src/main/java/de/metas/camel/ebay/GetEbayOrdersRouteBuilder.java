package de.metas.camel.ebay;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import de.metas.camel.ebay.processor.GetEbayOrdersProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.ebay.api.model.Order;

/**
 * Route to fetch ebay orders and put them as order line candidates into metasfresh.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@Component
public class GetEbayOrdersRouteBuilder extends RouteBuilder{
	
	private static final String EXT_ID_PREFIX = "ebay";
	
	public static final String GET_ORDERS_ROUTE_ID = "Ebay-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Ebay-processOrder";

	public static final String GET_ORDERS_PROCESSOR_ID = "GetEbayOrdersProcessorId";
	public static final String CREATE_ESR_QUERY_REQ_PROCESSOR_ID = "CreateBPartnerESRQueryProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "CreateBPartnerUpsertReqProcessorId";

	

	@Override
	public void configure() throws Exception {
		
		log.trace("Configure ebay order route");
		
		from("direct:Ebay-GetOrders")
			.routeId(GET_ORDERS_ROUTE_ID)
			.log("Route invoked")
			.process(new GetEbayOrdersProcessor()).id(GET_ORDERS_PROCESSOR_ID)
			.split(body())
			// create bpartners in metasfresh
			.process(exchange -> {
				
				log.trace("Processing single order and create bpartner");
				
				final var order = exchange.getIn().getBody(Order.class);
				final var orgCode = exchange.getIn().getHeader(ExternalSystemCamelConstants.HEADER_ORG_CODE, String.class);
				
				//TBD
				
			})
			.process(exchange -> {

				// TBD.
			});
		
	}
	
}
