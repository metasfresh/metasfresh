/*
 * #%L
 * de-metas-camel-externalsystems-core
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

package de.metas.camel.externalsystems.core.authorization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CustomRouteController;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.core.CoreConstants.CUSTOM_FROM_MF_ROUTE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class CustomMessageFromMFRouteBuilder extends RouteBuilder
{
	public final static String CUSTOM_FROM_MF_ROUTE_ID = "RabbitMQ_custom_from_MF_ID";

	final MetasfreshAuthProvider authProvider;
	final ObjectMapper objectMapper;
	final CustomRouteController customRouteController;

	public CustomMessageFromMFRouteBuilder(
			@NonNull final MetasfreshAuthProvider authProvider,
			@NonNull final ObjectMapper objectMapper,
			@NonNull final CustomRouteController customRouteController
	)
	{
		this.authProvider = authProvider;
		this.objectMapper = objectMapper;
		this.customRouteController = customRouteController;
	}

	@Override
	public void configure() throws Exception
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID));

		from(CUSTOM_FROM_MF_ROUTE)
				.routeId(CUSTOM_FROM_MF_ROUTE_ID)
				.streamCaching()
				.autoStartup(false)
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemMessage.class))
				.process(this::processCustomMessage);
	}

	private void processCustomMessage(@NonNull final Exchange exchange) throws JsonProcessingException
	{
		final JsonExternalSystemMessage message = exchange.getIn().getBody(JsonExternalSystemMessage.class);

		if (message != null)
		{
			switch (message.getType())
			{
				case AUTHORIZATION_REPLY:
				{
					if (message.getPayload() != null)
					{
						final JsonExternalSystemMessagePayload messagePayload = objectMapper.readValue(message.getPayload(), JsonExternalSystemMessagePayload.class);

						authProvider.setAuthToken(messagePayload.getAuthToken());

						customRouteController.startAllRoutes(exchange);
					}
					break;
				}
			}
		}
	}

}
