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
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.util.Check;
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.apache.camel.spi.PropertiesComponent;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_AUDIT_TRAIL;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_EXTERNAL_SYSTEM_VALUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TARGET_ROUTE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_TRACE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.core.CoreConstants.CONCURRENT_CONSUMERS_PROPERTY;
import static de.metas.camel.externalsystems.core.CoreConstants.FROM_MF_ROUTE;
import static de.metas.camel.externalsystems.core.CoreConstants.THREAD_POOL_SIZE_PROPERTY;
import static de.metas.common.externalsystem.ExternalSystemConstants.PARAM_CHILD_CONFIG_VALUE;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CallDispatcherRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String DISPATCH_ROUTE_ID = "MF-To-ExternalSystem-Dispatcher";

	@NonNull
	private final ProcessLogger processLogger;

	private final static String FROM_MF_ROUTE_ID = "RabbitMQ_from_MF_ID";

	private static final Logger logger = Logger.getLogger(CallDispatcherRouteBuilder.class.getName());

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

		final PropertiesComponent propertiesComponent = getContext().getPropertiesComponent();

		logger.log(Level.INFO, "Property " + THREAD_POOL_SIZE_PROPERTY + "=" + propertiesComponent.resolveProperty(THREAD_POOL_SIZE_PROPERTY));
		logger.log(Level.INFO, "Property " + CONCURRENT_CONSUMERS_PROPERTY + "=" + propertiesComponent.resolveProperty(CONCURRENT_CONSUMERS_PROPERTY));

		from(FROM_MF_ROUTE)
				.routeId(FROM_MF_ROUTE_ID)
				.to("direct:dispatch");

		from("direct:dispatch")
				.routeId(DISPATCH_ROUTE_ID)
				.streamCaching()
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemRequest.class))
				.process(this::processExternalSystemRequest)
				.log("routing request to route ${header." + HEADER_TARGET_ROUTE + "}; MessageId=${in.messageId}")
				.process(this::logRequestRouted)
				.toD("direct:${header." + HEADER_TARGET_ROUTE + "}", false)
				.process(this::logInvocationDone);
	}

	private void logRequestRouted(@NonNull final Exchange exchange)
	{
		final String targetRoute = exchange.getIn().getHeader(HEADER_TARGET_ROUTE, String.class);

		processLogger.logMessage("Routing request to: " + targetRoute,
								 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class));
	}

	private void logInvocationDone(@NonNull final Exchange exchange)
	{
		final String targetRoute = exchange.getIn().getHeader(HEADER_TARGET_ROUTE, String.class);

		processLogger.logMessage("Invocation done: " + targetRoute,
								 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class));
	}

	private void processExternalSystemRequest(@NonNull final Exchange exchange)
	{
		final var request = exchange.getIn().getBody(JsonExternalSystemRequest.class);
		exchange.getIn().setHeader(HEADER_TARGET_ROUTE, request.getExternalSystemName().getName() + "-" + request.getCommand());
		exchange.getIn().setHeader(HEADER_TRACE_ID, request.getTraceId());

		if (request.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
			exchange.getIn().setHeader(ExternalSystemConstants.HEADER_PINSTANCE_ID, request.getAdPInstanceId().getValue());
		}

		if (EmptyUtil.isNotBlank(request.getWriteAuditEndpoint()))
		{
			exchange.getIn().setHeader(HEADER_AUDIT_TRAIL, request.getWriteAuditEndpoint());
		}

		if (request.getParameters() != null && Check.isNotBlank(request.getParameters().get(PARAM_CHILD_CONFIG_VALUE)))
		{
			exchange.getIn().setHeader(HEADER_EXTERNAL_SYSTEM_VALUE, request.getParameters().get(PARAM_CHILD_CONFIG_VALUE));
		}
	}
}
