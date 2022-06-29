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

package de.metas.camel.externalsystems.core.authorizationmf;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.authorizationmf.provider.MetasfreshAuthProvider;
import de.metas.common.externalsystem.ExternalSystemConstants;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.util.EmptyUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.RouteController;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.core.CoreConstants.CUSTOM_FROM_MF_ROUTE;
import static de.metas.camel.externalsystems.core.restapi.ExternalSystemRestAPIHandler.HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class FromMFAuthorizationRouteBuilder extends RouteBuilder
{
	public final static String CUSTOM_FROM_MF_ROUTE_ID = "RabbitMQ_custom_from_MF_ID";

	final MetasfreshAuthProvider authProvider;

	public FromMFAuthorizationRouteBuilder(final @NonNull MetasfreshAuthProvider authProvider)
	{
		this.authProvider = authProvider;
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
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemMessage.class))
				.process(this::storeAuthTokenAndStartRoutes)
				.to(direct(HANDLE_EXTERNAL_SYSTEM_SERVICES_ROUTE_ID));
	}

	private void storeAuthTokenAndStartRoutes(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemMessage message = exchange.getIn().getBody(JsonExternalSystemMessage.class);

		if (message != null && message.getType().equals(ExternalSystemConstants.FROM_MF_AUTHORIZATION_REPLY_MESSAGE_TYPE) && EmptyUtil.isNotBlank(message.getPayload().getAuthToken()))
		{
			authProvider.setAuthToken(message.getPayload().getAuthToken());

			final RouteController routeController = exchange.getContext().getRouteController();

			exchange.getContext().getRoutes().stream()
					.filter(route -> route.getGroup() == null)
					.forEach(route -> {
						try
						{
							routeController.startRoute(route.getRouteId());
						}
						catch (final Exception e)
						{
							throw new RuntimeException("Failed to start routes after authorization!", e);
						}
					});
		}
	}

}
