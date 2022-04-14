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
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.SEDA_BLOCK_WHEN_FULL_PROPERTY;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.SEDA_CONCURRENT_CONSUMERS_PROPERTY;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.SEDA_QUEUE_SIZE_PROPERTY;
import static de.metas.camel.externalsystems.core.CoreConstants.FROM_MF_ROUTE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.seda;

@Component
public class CallDispatcherRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String DISPATCH_ROUTE_ID = "MF-To-ExternalSystem-Dispatcher";
	static final String DISPATCH_ASYNC_ROUTE_ID = "MF-To-ExternalSystem-AsyncDispatcher";
	static final String DISPATCH_ROUTE = "dispatch";
	static final String DISPATCH_ASYNC_ROUTE = "asyncDispatch";

	@NonNull
	private final ProcessLogger processLogger;

	final static String FROM_MF_ROUTE_ID = "RabbitMQ_from_MF_ID";

	public CallDispatcherRouteBuilder(final @NonNull ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		from(FROM_MF_ROUTE)
				.routeId(FROM_MF_ROUTE_ID)
				.to(direct(DISPATCH_ASYNC_ROUTE));

		//dev-note: intermediate route for easy testing
		from(direct(DISPATCH_ASYNC_ROUTE))
				.routeId(DISPATCH_ASYNC_ROUTE_ID)
				.to(seda(DISPATCH_ROUTE)
							.size("{{" + SEDA_QUEUE_SIZE_PROPERTY + "}}")
							.blockWhenFull("{{" + SEDA_BLOCK_WHEN_FULL_PROPERTY + "}}"));

		from(seda(DISPATCH_ROUTE)
					 .concurrentConsumers("{{" + SEDA_CONCURRENT_CONSUMERS_PROPERTY + "}}")
					 .size("{{" + SEDA_QUEUE_SIZE_PROPERTY + "}}"))
				.routeId(DISPATCH_ROUTE_ID)
				.streamCaching()
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemRequest.class))
				.process(exchange -> {
					final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
					exchange.getIn().setHeader("targetRoute", request.getExternalSystemName().getName() + "-" + request.getCommand());

					if (request.getAdPInstanceId() != null)
					{
						exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
					}
				})
				.log("routing request to route ${header.targetRoute}")
				.process(this::logRequestRouted)
				.toD("direct:${header.targetRoute}", false)
				.process(this::logInvocationDone);
	}

	private void logRequestRouted(@NonNull final Exchange exchange)
	{
		final String targetRoute = exchange.getIn().getHeader("targetRoute", String.class);

		processLogger.logMessage("Routing request to: " + targetRoute,
								 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class));
	}

	private void logInvocationDone(@NonNull final Exchange exchange)
	{
		final String targetRoute = exchange.getIn().getHeader("targetRoute", String.class);

		processLogger.logMessage("Invocation done: " + targetRoute,
								 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class));
	}
}
