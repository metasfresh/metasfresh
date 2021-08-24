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

package de.metas.camel.externalsystems.core.to_mf;

import de.metas.camel.externalsystems.common.LogMessageRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.rest_api.v1.CreatePInstanceLogRequest;
import de.metas.common.rest_api.v1.JsonPInstanceLog;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_EXTERNAL_SYSTEM_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOG_MESSAGE_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class LogMessageRouteBuilder extends RouteBuilder
{
	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());

		//@formatter:off
		from(direct(MF_LOG_MESSAGE_ROUTE_ID))
				.routeId(MF_LOG_MESSAGE_ROUTE_ID)
				.log("Route invoked")
				.streamCaching()
				.process(this::prepareRequest)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), CreatePInstanceLogRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_EXTERNAL_SYSTEM_URI + "}}/${header." + HEADER_PINSTANCE_ID + "}/externalstatus/message");
		//@formatter:on
	}

	private void prepareRequest(@NonNull final Exchange exchange)
	{
		final Object exchangeBody = exchange.getIn().getBody();

		if (!(exchangeBody instanceof LogMessageRequest))
		{
			throw new RuntimeException("Wrong exchange body class! Expected: " + LogMessageRequest.class.getName() + "!");
		}

		final LogMessageRequest logMessageRequest = (LogMessageRequest)exchangeBody;

		final CreatePInstanceLogRequest createPInstanceLogRequest = CreatePInstanceLogRequest.builder()
				.log(JsonPInstanceLog.builder()
							 .message(logMessageRequest.getLogMessage())
							 .build())
				.build();

		exchange.getIn().setBody(createPInstanceLogRequest);
		exchange.getIn().setHeader(HEADER_PINSTANCE_ID, logMessageRequest.getPInstanceId().getValue());
	}
}
