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

package de.metas.camel.externalsystems.shopware6.order;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessLogger;
<<<<<<< HEAD
import de.metas.camel.externalsystems.shopware6.order.processor.ClearOrdersProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.CreateBPartnerUpsertReqProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.GetOrdersProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.OLCandRequestProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.OrderFilter;
import de.metas.camel.externalsystems.shopware6.order.processor.PaymentRequestProcessor;
=======
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.order.processor.BuildOrdersContextProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.CreateBPartnerUpsertReqProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.GetOrdersPageProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.OLCandRequestProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.OrderFilter;
import de.metas.camel.externalsystems.shopware6.order.processor.PaymentRequestProcessor;
import de.metas.camel.externalsystems.shopware6.order.processor.ProcessOLCandProcessor;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.camel.externalsystems.shopware6.order.processor.RuntimeParametersProcessor;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import lombok.NonNull;
import org.apache.camel.LoggingLevel;
<<<<<<< HEAD
=======
import org.apache.camel.Predicate;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
<<<<<<< HEAD
=======
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GetOrdersRouteBuilder extends RouteBuilder
{
<<<<<<< HEAD
	public static final String GET_ORDERS_ROUTE_ID = "Shopware6-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Shopware6-processOrder";
	public static final String CLEAR_ORDERS_ROUTE_ID = "Shopware6-clearOrders";
	public static final String UPSERT_RUNTIME_PARAMS_ROUTE_ID = "Shopware6-upsertRuntimeParams";

	public static final String GET_ORDERS_PROCESSOR_ID = "SW6Orders-GetOrdersProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "SW6Orders-CreateBPartnerUpsertReqProcessorId";
	public static final String OLCAND_REQ_PROCESSOR_ID = "SW6Orders-OLCandRequestProcessorId";
	public static final String CLEAR_OLCAND_PROCESSOR_ID = "SW6Orders-ClearOLCandProcessorId";
=======
	public static final String PROCESS_ORDERS_PAGE_ROUTE_ID = "Shopware6-processOrdersPage";
	public static final String GET_ORDERS_ROUTE_ID = "Shopware6-getOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Shopware6-processOrder";
	public static final String PROCESS_OLCAND_ROUTE_ID = "Shopware6-processOLCand";
	public static final String UPSERT_RUNTIME_PARAMS_ROUTE_ID = "Shopware6-upsertRuntimeParams";

	public static final String BUILD_ORDERS_CONTEXT_PROCESSOR_ID = "SW6Orders-BuildContextProcessorId";
	public static final String GET_ORDERS_PAGE_PROCESSOR_ID = "SW6Orders-GetOrdersPageProcessorId";
	public static final String CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID = "SW6Orders-CreateBPartnerUpsertReqProcessorId";
	public static final String OLCAND_REQ_PROCESSOR_ID = "SW6Orders-OLCandRequestProcessorId";
	public static final String PROCESS_OLCAND_PROCESSOR_ID = "SW6Orders-ProcessOLCandProcessorId";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public static final String FILTER_ORDER_PROCESSOR_ID = "SW6Orders-FilterOrderProcessorId";
	public static final String PAYMENT_REQUEST_PROCESSOR_ID = "SW6Orders-PaymentRequestProcessorId";
	public static final String RUNTIME_PARAMS_PROCESSOR_ID = "SW6Orders-RuntimeParamsProcessorId";

	private final ProcessLogger processLogger;
	private final ProducerTemplate producerTemplate;

	public GetOrdersRouteBuilder(@NonNull final ProcessLogger processLogger, @NonNull final ProducerTemplate producerTemplate)
	{
		this.processLogger = processLogger;
		this.producerTemplate = producerTemplate;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		from(direct(GET_ORDERS_ROUTE_ID))
				.routeId(GET_ORDERS_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
<<<<<<< HEAD
				.process(new GetOrdersProcessor(processLogger, producerTemplate)).id(GET_ORDERS_PROCESSOR_ID)
				.split(body())
					.to(direct(PROCESS_ORDER_ROUTE_ID))
				.end()
				.to(direct(UPSERT_RUNTIME_PARAMS_ROUTE_ID))
				.to(direct(CLEAR_ORDERS_ROUTE_ID))
				.process((exchange) -> processLogger.logMessage("Shopware6:GetOrders process ended!" + Instant.now(),
						exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class)));
=======
				.process(new BuildOrdersContextProcessor(processLogger, producerTemplate)).id(BUILD_ORDERS_CONTEXT_PROCESSOR_ID)
				.to(direct(PROCESS_ORDERS_PAGE_ROUTE_ID))
				.to(direct(UPSERT_RUNTIME_PARAMS_ROUTE_ID))
				.to(direct(PROCESS_OLCAND_ROUTE_ID))
				.process((exchange) -> processLogger.logMessage("Shopware6:GetOrders process ended!" + Instant.now(),
						exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class)));

		from(direct(PROCESS_ORDERS_PAGE_ROUTE_ID))
				.routeId(PROCESS_ORDERS_PAGE_ROUTE_ID)
				.log("Route invoked")
				.end()
				.process(new GetOrdersPageProcessor()).id(GET_ORDERS_PAGE_PROCESSOR_ID)
				.split(body())
					.to(direct(PROCESS_ORDER_ROUTE_ID))
				.end()
				.choice()
					.when(areMoreOrdersLeftToBeRetrieved())
						.to(direct(PROCESS_ORDERS_PAGE_ROUTE_ID))
					.otherwise()
						.log(LoggingLevel.INFO, "Nothing to do! No additional orders to retrieve!")
				.end();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		from(direct(PROCESS_ORDER_ROUTE_ID))
				.routeId(PROCESS_ORDER_ROUTE_ID)
				.log("Route invoked")
				.doTry()
					.process(new OrderFilter(processLogger)).id(FILTER_ORDER_PROCESSOR_ID)
					.choice()
						.when(body().isNull())
							.log(LoggingLevel.INFO, "Nothing to do! The order was filtered out!")
						.otherwise()
							.process(new CreateBPartnerUpsertReqProcessor()).id(CREATE_BPARTNER_UPSERT_REQ_PROCESSOR_ID)
<<<<<<< HEAD
=======
				// TODO: don't upsert the bpartner if the sync-advise sais "read-only"
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
							.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert BPartners: ${body}")
							.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")

							.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonResponseBPartnerCompositeUpsert.class))
							.process(new OLCandRequestProcessor()).id(OLCAND_REQ_PROCESSOR_ID)
							.to(direct(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID))

							.process(new PaymentRequestProcessor(processLogger)).id(PAYMENT_REQUEST_PROCESSOR_ID)
							.choice()
								.when(body().isNull())
									.log(LoggingLevel.INFO, "Nothing to do! No payment was found!")
								.otherwise()
									.to(direct(ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID))
								.end()
							.endChoice()
						.end()
				.endDoTry()
				.doCatch(Exception.class)
					.to(direct(MF_ERROR_ROUTE_ID))
				.end();

<<<<<<< HEAD
		from(direct(CLEAR_ORDERS_ROUTE_ID))
				.routeId(CLEAR_ORDERS_ROUTE_ID)
				.log("Route invoked")
				.process(new ClearOrdersProcessor()).id(CLEAR_OLCAND_PROCESSOR_ID)
				.split(body())
					.log(LoggingLevel.DEBUG, "Calling metasfresh-api to clear orders: ${body}")
					.to(direct(ExternalSystemCamelConstants.MF_CLEAR_OL_CANDIDATES_ROUTE_ID))
				.end();
=======
		from(direct(PROCESS_OLCAND_ROUTE_ID))
				.routeId(PROCESS_OLCAND_ROUTE_ID)
				.log("Route invoked")
				.process(new ProcessOLCandProcessor()).id(PROCESS_OLCAND_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to do! No OLCand was imported!")
					.otherwise()
						.split(body())
							.log(LoggingLevel.DEBUG, "Calling metasfresh-api to process OLCand: ${body}")
							.to(direct(ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID))
						.end()
					.end()
				.endChoice();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

		from(direct(UPSERT_RUNTIME_PARAMS_ROUTE_ID))
				.routeId(UPSERT_RUNTIME_PARAMS_ROUTE_ID)
				.log("Route invoked")
				.process(new RuntimeParametersProcessor()).id(RUNTIME_PARAMS_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert runtime parameters: ${body}")
					.otherwise()
						.to(direct(ExternalSystemCamelConstants.MF_UPSERT_RUNTIME_PARAMETERS_ROUTE_ID))
				.endChoice()
				.end();
		//@formatter:on
	}

<<<<<<< HEAD
=======
	private Predicate areMoreOrdersLeftToBeRetrieved()
	{
		return (exchange) -> {
			final ImportOrdersRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_ORDERS_CONTEXT, ImportOrdersRouteContext.class);
			return routeContext.isMoreOrdersAvailable();
		};
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
