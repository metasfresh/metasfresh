/*
 * #%L
 * exchange-routes
 * %%
 * Copyright (C) 2022 metas GmbH
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

import com.adekia.exchange.camel.logger.SimpleLog;
import com.adekia.exchange.camel.processor.CtxProcessor;
import com.adekia.exchange.camel.processor.GetShipmentProcessor;
import com.adekia.exchange.camel.processor.ShipmentProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ShipmentRouteBuilder extends RouteBuilder
{

	public static final String CTX_PROCESSOR_ID = "Adekia-Ctx-shipmentProcessorId";
	public static final String SHIPMENT_ROUTE_ID = "Adekia-Shipment";
	public static final String GET_SHIPMENT_PROCESSOR_ID = "Adekia-Shipment-GetShipmentProcessorId";
	public static final String SHIPMENT_TRANSFORMER_ID = "Adekia-Shipment-TransformerId";

	private final CtxProcessor ctxBuilderProcessor;
	private final ShipmentProcessor shipmentProcessor;
	private final GetShipmentProcessor getShipmentProcessor;

	@Autowired
	public ShipmentRouteBuilder(
			CtxProcessor ctxBuilderProcessor,
			ShipmentProcessor shipmentProcessor,
			GetShipmentProcessor getShipmentProcessor)

	{
		this.ctxBuilderProcessor = ctxBuilderProcessor;
		this.shipmentProcessor = shipmentProcessor;
		this.getShipmentProcessor = getShipmentProcessor;
	}

	public void configure() throws Exception {
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//Simple direct route to retrieve collection of Orders and route individually
		from(direct(SHIPMENT_ROUTE_ID)) // todo
//		from("timer://foo?repeatCount=1")
				.routeId(SHIPMENT_ROUTE_ID)
				.process(ctxBuilderProcessor).id(CTX_PROCESSOR_ID)
				.log(LoggingLevel.INFO, "RUNNING Shipment Processor...")
				.process(getShipmentProcessor).id(GET_SHIPMENT_PROCESSOR_ID)
				.log(LoggingLevel.INFO, "  RESPONSE : ${body}")
				.choice()
				.when(body().isNull())
				.log(LoggingLevel.INFO, "  Nothing to do ! No order retrieved !").endChoice()
				.otherwise()
				.log(LoggingLevel.INFO, "    Processing Shipment")
				.process(shipmentProcessor).id(SHIPMENT_TRANSFORMER_ID)
				.split(exchangeProperty(SimpleLog.CAMEL_PROPERTY_NAME))
					.log("      ${body}")
				.end()
				.endChoice()
				.end();

		restConfiguration()
				.component("servlet");
		//http://localhost:8080/camel/api/shipment
		rest("/api")
				.post("/shipments/{shipmentId}/")
				.to(direct(SHIPMENT_ROUTE_ID).getUri());

	}

}
