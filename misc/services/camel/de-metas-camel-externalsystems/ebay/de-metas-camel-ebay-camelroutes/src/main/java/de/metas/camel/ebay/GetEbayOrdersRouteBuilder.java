package de.metas.camel.ebay;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import de.metas.camel.ebay.processor.CreateBPartnerUpsertReqForEbayOrderProcessor;
import de.metas.camel.ebay.processor.CreateOrderLineCandidateUpsertReqForEbayOrderProcessor;
import de.metas.camel.ebay.processor.GetEbayOrdersProcessor;
import de.metas.camel.ebay.processor.OrderFilterProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;

/**
 * Route to fetch ebay orders and put them as order line candidates into metasfresh.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@Component
public class GetEbayOrdersRouteBuilder extends RouteBuilder{
	
	public static final String GET_ORDERS_ROUTE_ID = "Ebay-getOrders";
	public static final String PROCESS_ORDERS_ROUTE_ID = "Ebay-processOrders";
	public static final String PROCESS_ORDER_BPARTNER_ROUTE_ID = "Ebay-processOrderBPartner";
	public static final String PROCESS_ORDER_OLC_ROUTE_ID = "Ebay-processOrderOLC";
	public static final String FILTER_ORDER_ROUTE_ID = "Ebay-filterOrder";
	
	@Override
	public void configure() {
		
		log.debug("Configure ebay order route");
		
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));
		
		
		//@formatter:off
		//first, get orders from ebay and split them.
		from(StaticEndpointBuilders.direct(GET_ORDERS_ROUTE_ID))
			.routeId(GET_ORDERS_ROUTE_ID)
			.log(LoggingLevel.DEBUG, "Ebay get order route invoked")
			.process(new GetEbayOrdersProcessor())
			.split(body())
			.to( direct(PROCESS_ORDERS_ROUTE_ID));
		
		//second, hand over individual orders for further processing.
		from( direct(PROCESS_ORDERS_ROUTE_ID))
			.routeId(PROCESS_ORDERS_ROUTE_ID)
			.log("Ebay process orders route invoked")
			.doTry()
				.process(new OrderFilterProcessor()).id(FILTER_ORDER_ROUTE_ID)
			
				//create bparners and put them in bparner import pipeline.
				.process(new CreateBPartnerUpsertReqForEbayOrderProcessor()).id(PROCESS_ORDER_BPARTNER_ROUTE_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to store business partners!")
				.to( direct(ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI) )
				
				//unmarshal bparner upsert requests and then
//				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
				
				//create order line candidates.
				.process(new CreateOrderLineCandidateUpsertReqForEbayOrderProcessor()).id(PROCESS_ORDER_OLC_ROUTE_ID)
				.log(LoggingLevel.DEBUG, "Calling metasfresh-api to store order candidates!")
				.to( direct(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID) )
				
			.doCatch(Exception.class)
			.to(direct(MF_ERROR_ROUTE_ID))
			.end();
		
		//@formatter:on
	}
	
}
