/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core;

import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ROUTE_PROPERTY_RAW_DATA;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.STORE_RAW_DATA_ROUTE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.STORE_RAW_DATA_URI;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * Stores the string from {@link de.metas.camel.externalsystems.common.ExternalSystemCamelConstants#ROUTE_PROPERTY_RAW_DATA} to the component specified by the property 
 */
@Component
public class StoreRawDataRouteBuilder extends RouteBuilder
{

	private static final String PROPERTY_ORIGINAL_BODY = "OriginalBody";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());

		//@formatter:off
		from(direct(STORE_RAW_DATA_ROUTE))
				.routeId(STORE_RAW_DATA_ROUTE)
				.log("Route invoked")
				.streamCaching()
				.process(this::prepareExchange)
				.to("{{" + STORE_RAW_DATA_URI + "}}")
				.process(this::restoreExchange);
		//@formatter:on
	}

	private void prepareExchange(@NonNull final Exchange exchange)
	{
		final Object body = exchange.getIn().getBody();
		exchange.setProperty(PROPERTY_ORIGINAL_BODY, body);
		exchange.getIn().setBody(exchange.getProperty(ROUTE_PROPERTY_RAW_DATA));
	}

	private void restoreExchange(@NonNull final Exchange exchange)
	{
		final Object rawData = exchange.getIn().getBody(String.class);
		exchange.setProperty(ROUTE_PROPERTY_RAW_DATA, rawData);

		final Object originalBody = exchange.removeProperty(PROPERTY_ORIGINAL_BODY);
		exchange.getIn().setBody(originalBody);
	}
}
