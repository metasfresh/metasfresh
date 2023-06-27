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

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.ebay.processor.bpartner.CreateBPartnerUpsertReqForEbayOrderProcessor;
import de.metas.camel.externalsystems.ebay.processor.order.CreateOrderLineCandidateUpsertReqForEbayOrderProcessor;
import de.metas.camel.externalsystems.ebay.processor.order.GetEbayOrdersProcessor;
import de.metas.camel.externalsystems.ebay.processor.order.NextOrderImportRuntimeParameterUpsert;
import de.metas.camel.externalsystems.ebay.processor.order.OrderFilterProcessor;
import de.metas.camel.externalsystems.ebay.processor.product.CreateProductUpsertReqProcessor;
import de.metas.camel.externalsystems.ebay.processor.product.price.CreateProductPriceUpsertReqProcessor;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Route to fetch ebay orders and put them as order line candidates into metasfresh.
 * 
 * 
 * @author Werner Gaulke
 *
 */
@Component
public class GetEbayOrdersRouteBuilder extends RouteBuilder
{

	public static final String GET_ORDERS_ROUTE_ID = "Ebay-getOrders";
	public static final String PROCESS_ORDERS_ROUTE_ID = "Ebay-processOrders";
	public static final String PROCESS_ORDER_BPARTNER_ROUTE_ID = "Ebay-processOrderBPartner";
	public static final String PROCESS_PRODUCTS_ROUTE_ID = "Ebay-processProducts";
	public static final String PROCESS_ORDER_PRODUCTS_ROUTE_ID = "Ebay-processOrderProducts";
	public static final String PROCESS_ORDER_PRODUCTS_PRICES_ROUTE_ID = "Ebay-processOrderProductsPrices";
	public static final String PROCESS_ORDER_OLC_ROUTE_ID = "Ebay-processOrderOLC";
	public static final String PROCESS_NEXT_ORDER_IMPORT_RUNTIME_PARAMS_ROUTE_ID = "Ebay-nextOrderImportRuntimeParams";
	public static final String PROCESS_NEXT_ORDER_IMPORT_RUNTIME_PARAMS_PROCESSOR_ID = "Ebay-nextOrderImportRuntimeParamsProcessor";
	public static final String FILTER_ORDER_ROUTE_ID = "Ebay-filterOrder";

	private final ProcessLogger processLogger;

	public GetEbayOrdersRouteBuilder(final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{

		log.debug("Configure ebay order route");

		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		//1) get orders from ebay and split them.
		from(StaticEndpointBuilders.direct(GET_ORDERS_ROUTE_ID))
			.routeId(GET_ORDERS_ROUTE_ID)
			.log(LoggingLevel.DEBUG, "Ebay get order route invoked")
			.process(new GetEbayOrdersProcessor(processLogger))
			.split(body())
			.to( direct(PROCESS_ORDERS_ROUTE_ID));
		
		//2) process orders individually
		from( direct(PROCESS_ORDERS_ROUTE_ID))
			.routeId(PROCESS_ORDERS_ROUTE_ID)
			.log("Ebay process orders route invoked")
			.doTry()
				// filter fulfilled orders  
				.process(new OrderFilterProcessor()).id(FILTER_ORDER_ROUTE_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to do! The order was filtered out!")
					.otherwise()
					
						//i) create products
						.to(direct(PROCESS_PRODUCTS_ROUTE_ID))
						
						//ii) create bparners and put them in bparner import pipeline.
						.process(new CreateBPartnerUpsertReqForEbayOrderProcessor()).id(PROCESS_ORDER_BPARTNER_ROUTE_ID)
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to store business partners!")
						.to( "{{" + MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}" )
					
						.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
						
						//iii) create order line candidates.
						.process(new CreateOrderLineCandidateUpsertReqForEbayOrderProcessor()).id(PROCESS_ORDER_OLC_ROUTE_ID)
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to store order candidates!")
						.to( direct(MF_PUSH_OL_CANDIDATES_ROUTE_ID) )
						
					.end()
			.endDoTry()
			
			.doCatch(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID))
		
		.end();
		
		//3) process order products ->  i)
		from(direct(PROCESS_PRODUCTS_ROUTE_ID))
				.routeId(PROCESS_PRODUCTS_ROUTE_ID)
		
				.process(new CreateProductUpsertReqProcessor()).id(PROCESS_ORDER_PRODUCTS_ROUTE_ID)
		
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to do! No new or updated Products!")
					.otherwise()
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Products: ${body}")
						.to(direct(MF_UPSERT_PRODUCT_V2_CAMEL_URI))
			
						// and their prices.
						//.process(new CreateProductPriceUpsertReqProcessor()).id(PROCESS_ORDER_PRODUCTS_PRICES_ROUTE_ID)
						//.split(body())
						//	.to(direct(MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI))
				.endChoice()
		.end();
		
		//4) upsert params for future calls.
		from(direct(PROCESS_NEXT_ORDER_IMPORT_RUNTIME_PARAMS_ROUTE_ID))
				.routeId(PROCESS_NEXT_ORDER_IMPORT_RUNTIME_PARAMS_ROUTE_ID)
				.log("Route invoked")
		
				.process(new NextOrderImportRuntimeParameterUpsert()).id(PROCESS_NEXT_ORDER_IMPORT_RUNTIME_PARAMS_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert runtime parameters: ${body}")
					.otherwise()
						.to(direct(MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID))
				.endChoice()
		.end();
		//@formatter:on
	}

}
