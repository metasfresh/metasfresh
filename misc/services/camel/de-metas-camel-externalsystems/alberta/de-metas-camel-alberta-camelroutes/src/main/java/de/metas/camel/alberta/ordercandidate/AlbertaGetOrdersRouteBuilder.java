/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta.ordercandidate;

import de.metas.camel.alberta.ordercandidate.processor.CreateJsonOLCandCreateRequestProcessor;
import de.metas.camel.alberta.ordercandidate.processor.RetrieveOrdersProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class AlbertaGetOrdersRouteBuilder extends RouteBuilder
{
	public static final String GET_ORDERS_ROUTE_ID = "Alberta-getSalesOrders";
	public static final String PROCESS_ORDER_ROUTE_ID = "Alberta-processOrder";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
			// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
			from(direct(GET_ORDERS_ROUTE_ID))
					.routeId(GET_ORDERS_ROUTE_ID)
					.streamCaching()
					.process(new RetrieveOrdersProcessor())
					.split(body())
						.to(direct(PROCESS_ORDER_ROUTE_ID))
					.end();

			from(direct(PROCESS_ORDER_ROUTE_ID))
					.routeId(PROCESS_ORDER_ROUTE_ID)
					.process(new CreateJsonOLCandCreateRequestProcessor())

					.log(LoggingLevel.DEBUG, "Calling metasfresh-api to store order candidates!")
					.to( direct(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID) );
			//@formatter:on

	}
}

