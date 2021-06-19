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

package de.metas.camel.externalsystems.core.from_mf;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.core.CoreConstants.FROM_MF_ROUTE;

@Component
public class CallDispatcherRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String DISPATCH_ROUTE_ID = "MF-To-ExternalSystem-Dispatcher";

	private final static String FROM_MF_ROUTE_ID = "RabbitMQ_from_MF_ID";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		from(FROM_MF_ROUTE)
				.routeId(FROM_MF_ROUTE_ID)
				.to("direct:dispatch");

		from("direct:dispatch")
				.routeId(DISPATCH_ROUTE_ID)
				.streamCaching()
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemRequest.class))
				.process(exchange -> {
					final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
					exchange.getIn().setHeader("targetRoute", request.getExternalSystemName().getName() + "-" + request.getCommand());
				})
				.log("routing request to route ${header.targetRoute}")
				.toD("direct:${header.targetRoute}", false);
	}
}
