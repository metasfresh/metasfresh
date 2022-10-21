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
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalsystem.JsonExternalSystemMessage;
import de.metas.common.externalsystem.JsonExternalSystemMessageType;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.core.CoreConstants.CUSTOM_TO_MF_ROUTE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class CustomMessageToMFRouteBuilder extends RouteBuilder
{
	public final static String CUSTOM_TO_MF_ROUTE_ID = "RabbitMQ_custom_to_MF_ID";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(CUSTOM_TO_MF_ROUTE_ID))
				.routeId(CUSTOM_TO_MF_ROUTE_ID)
				.group(CamelRoutesGroup.ALWAYS_ON.getCode())
				.streamCaching()
				.log("Invoked")
				.process(this::postAuthorizationMessage)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalSystemMessage.class))
				.to(CUSTOM_TO_MF_ROUTE);
	}

	private void postAuthorizationMessage(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemMessage message = JsonExternalSystemMessage.builder()
				.type(JsonExternalSystemMessageType.REQUEST_AUTHORIZATION)
				.build();

		exchange.getIn().setBody(message);
	}
}
