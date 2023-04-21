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

import de.metas.camel.externalsystems.common.CamelRoutesGroup;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CustomRouteController;
import de.metas.camel.externalsystems.core.authorization.provider.MetasfreshAuthProvider;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessagePayload;
import de.metas.common.util.StringUtils;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static de.metas.camel.externalsystems.core.CoreConstants.CUSTOM_FROM_MF_ROUTE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class CustomMessageFromMFRouteBuilder extends RouteBuilder
{
	public static final String CUSTOM_MESSAGE_FROM_MF_ROUTE_ID = "RabbitMQ_custom_message_from_MF_Route_ID";
	private final static String CUSTOM_FROM_MF_ROUTE_ID = "RabbitMQ_custom_from_MF_ID";

	@NonNull
	private final MetasfreshAuthProvider authProvider;
	@NonNull
	private final CustomRouteController customRouteController;

	public CustomMessageFromMFRouteBuilder(
			@NonNull final MetasfreshAuthProvider authProvider,
			@NonNull final CustomRouteController customRouteController
	)
	{
		this.authProvider = authProvider;
		this.customRouteController = customRouteController;
	}

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID));

		from(CUSTOM_FROM_MF_ROUTE)
				.routeId(CUSTOM_FROM_MF_ROUTE_ID)
				.group(CamelRoutesGroup.ALWAYS_ON.getCode())
				.to(direct(CUSTOM_MESSAGE_FROM_MF_ROUTE_ID));

		from(direct(CUSTOM_MESSAGE_FROM_MF_ROUTE_ID))
				.routeId(CUSTOM_MESSAGE_FROM_MF_ROUTE_ID)
				.group(CamelRoutesGroup.ALWAYS_ON.getCode())
				.streamCaching()
				.log("Invoked!")
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemMessage.class))
				.process(this::processCustomMessage);
	}

	private void processCustomMessage(@NonNull final Exchange exchange) throws IOException
	{
		final JsonExternalSystemMessage message = exchange.getIn().getBody(JsonExternalSystemMessage.class);

		if (message == null)
		{
			throw new RuntimeException("Missing JsonExternalSystemMessage body!");
		}

		switch (message.getType())
		{
			case AUTHORIZATION_REPLY -> handleAuthorizationMessage(message);
			default -> throw new RuntimeException("Unexpected message type! Type=" + message.getType());
		}
	}

	private void handleAuthorizationMessage(@NonNull final JsonExternalSystemMessage authorizationMessage) throws IOException
	{
		final JsonExternalSystemMessagePayload messagePayload = JsonObjectMapperHolder.sharedJsonObjectMapper()
				.readValue(authorizationMessage.getPayload(), JsonExternalSystemMessagePayload.class);

		authProvider.setAuthToken(messagePayload.getAuthToken());

		log.info("Received from MF: API AuthToken: {}", StringUtils.maskString(messagePayload.getAuthToken()));

		customRouteController.startAllRoutes();
	}
}
