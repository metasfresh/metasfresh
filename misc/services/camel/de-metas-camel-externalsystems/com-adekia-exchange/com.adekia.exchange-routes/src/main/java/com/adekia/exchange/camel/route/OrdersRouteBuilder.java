/*
 * #%L
 * camel-routes
 * %%
 * Copyright (C) 2022 Adekia
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

package com.adekia.exchange.camel.route;

import com.adekia.exchange.camel.processor.*;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class OrdersRouteBuilder extends RouteBuilder {
    public static final String CTX_PROCESSOR_ID = "Adekia-Ctx-processorId";
    public static final String GET_ORDERS_ROUTE_ID = "Adekia-Order-getOrders";
    public static final String GET_ORDERS_PROCESSOR_ID = "Adekia-Order-getOrdersProcessorId";
    public static final String GET_ORDER_ROUTE_ID = "Adekia-Order-getOrder";
    public static final String GET_ORDER_PROCESSOR_ID = "Adekia-Order-getOrderProcessorId";

    public static final String ORDER_ROUTE_ID = "Adekia-Order-processOrder";
    public static final String ORDER_PROCESSOR_ID = "Adekia-Order-OrderProcessorId";
    public static final String ORDER_PAYMENT_PROCESSOR_ID = "Adekia-OrderPayment-PaymentProcessorId";
    public static final String ORDER_BP_PROCESSOR_ID = "Adekia-OrderBP-BPProcessorId";

    private final OrderCtxProcessor ctxBuilderProcessor;
    private final GetOrderProcessor getOrderProcessor;
    private final GetOrdersProcessor getOrdersProcessor;
    private final OrderProcessor orderProcessor;
    private final OrderPaymentProcessor orderPaymentProcessor;
    private final OrderBPProcessor orderBPProcessor;

    @Autowired
    public OrdersRouteBuilder(
            OrderCtxProcessor ctxBuilderProcessor,
            GetOrdersProcessor getOrdersProcessor,
            GetOrderProcessor getOrderProcessor,
            OrderProcessor orderProcessor,
            OrderPaymentProcessor orderPaymentProcessor,
            OrderBPProcessor orderBPProcessor
    ) {
        this.ctxBuilderProcessor = ctxBuilderProcessor;
        this.getOrderProcessor = getOrderProcessor;
        this.getOrdersProcessor = getOrdersProcessor;
        this.orderProcessor = orderProcessor;
        this.orderPaymentProcessor = orderPaymentProcessor;
        this.orderBPProcessor = orderBPProcessor;
    }

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler());
        onException(Exception.class)
                .to(direct(MF_ERROR_ROUTE_ID));

        //Simple direct route to retrieve collection of Orders and route individually
        // todo		from(direct(GET_ORDERS_ROUTE_ID))
        from("timer://foo?repeatCount=1")
                .routeId(GET_ORDERS_ROUTE_ID)
                .process(ctxBuilderProcessor).id(CTX_PROCESSOR_ID)
                .log(LoggingLevel.INFO, "RUNNING GetOrdersProcessor...")
                .process(getOrdersProcessor).id(GET_ORDERS_PROCESSOR_ID)
                .log(LoggingLevel.INFO, "  RESPONSE : ${body}")
                .choice()
                .when(body().isNull())
                .log(LoggingLevel.INFO, "Nothing to do ! No orders retrieved !").endChoice()
                .otherwise()
                .split(body())
                .to(direct(ORDER_ROUTE_ID))
                .end()
                .endChoice()
                .end();

        // Simple direct route to Retrieve an Order and route
        from(direct(GET_ORDER_ROUTE_ID))
                .routeId(GET_ORDER_ROUTE_ID)
                .log(LoggingLevel.INFO, "RUNNING GetOrderProcessor...")
                .process(getOrderProcessor).id(GET_ORDER_PROCESSOR_ID)
                .to(direct(ORDER_ROUTE_ID));

        // Simple direct route to process an OrderType
        from(direct(ORDER_ROUTE_ID))
                .routeId(ORDER_ROUTE_ID)
                .log(LoggingLevel.INFO, "    Processing Order :")
                .choice()
                .when(body().isNull())
                .log(LoggingLevel.INFO, "    Nothing to do ! Order was skipped !")
                .endChoice()
                .otherwise()
                .log(LoggingLevel.INFO, "      ORDERTYPE  : ${body}")
                .process(orderBPProcessor).id(ORDER_BP_PROCESSOR_ID)
                .log(LoggingLevel.INFO, "      ORDERBP    : ${body}")
                // todo					.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")

                .process(orderProcessor).id(ORDER_PROCESSOR_ID)
                .log(LoggingLevel.INFO, "      JSONOLCAND : ${body}")
                // todo						.to(direct(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID))
                .process(orderPaymentProcessor).id(ORDER_PAYMENT_PROCESSOR_ID)
                .split(body())
                .log(LoggingLevel.INFO, "      JSONOLPAY  : ${body}")
                // todo						.to(direct(ExternalSystemCamelConstants.MF_CREATE_ORDER_PAYMENT_ROUTE_ID))
                .end()
                .endChoice()
                .end();

        restConfiguration()
                .component("servlet");
        //http://localhost:8080/camel/api/orders
        rest("/api")
                .get("/orders")
                .to(direct(GET_ORDERS_ROUTE_ID).getUri())
                .get("/orders/{orderId}")
                .to(direct(GET_ORDER_ROUTE_ID).getUri());

    }
}
