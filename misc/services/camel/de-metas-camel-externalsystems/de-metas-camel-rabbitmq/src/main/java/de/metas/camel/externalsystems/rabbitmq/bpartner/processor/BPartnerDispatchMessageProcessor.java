/*
 * #%L
 * de-metas-camel-rabbitmq
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

package de.metas.camel.externalsystems.rabbitmq.bpartner.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.camel.externalsystems.common.JsonObjectMapperHolder;
import de.metas.camel.externalsystems.rabbitmq.api.DispatchMessageRequest;
import de.metas.camel.externalsystems.rabbitmq.api.JsonRabbitMQHttpMessage;
import de.metas.camel.externalsystems.rabbitmq.api.JsonRabbitMQProperties;
import de.metas.camel.externalsystems.rabbitmq.bpartner.ExportBPartnerRouteContext;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.common.bpartner.v2.response.JsonResponseComposite;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import static de.metas.camel.externalsystems.rabbitmq.api.ApiConstants.PERSISTENT_DELIVERY_MODE;
import static de.metas.camel.externalsystems.rabbitmq.api.ApiConstants.RABBIT_MQ_PAYLOAD_ENCODING;
import static de.metas.camel.externalsystems.rabbitmq.common.CamelConstants.ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT;

public class BPartnerDispatchMessageProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final JsonResponseComposite jsonResponseComposite = exchange.getIn().getBody(JsonResponseComposite.class);

		final ExportBPartnerRouteContext routeContext = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_EXPORT_BPARTNER_CONTEXT, ExportBPartnerRouteContext.class);

		final ObjectMapper objectMapper = JsonObjectMapperHolder.sharedJsonObjectMapper();

		final String payload = objectMapper.writeValueAsString(jsonResponseComposite);

		final JsonRabbitMQHttpMessage jsonRabbitMQHttpMessage = JsonRabbitMQHttpMessage.builder()
				.routingKey(routeContext.getRoutingKey())
				.jsonRabbitMQProperties(getDefaultRabbitMQProperties())
				.payload(payload)
				.payloadEncoding(RABBIT_MQ_PAYLOAD_ENCODING)
				.build();

		final DispatchMessageRequest dispatchMessageRequest = DispatchMessageRequest.builder()
				.url(routeContext.getRemoteUrl())
				.authToken(routeContext.getAuthToken())
				.message(jsonRabbitMQHttpMessage)
				.build();

		exchange.getIn().setBody(dispatchMessageRequest);
	}

	@NonNull
	private JsonRabbitMQProperties getDefaultRabbitMQProperties()
	{
		return JsonRabbitMQProperties.builder()
				.delivery_mode(PERSISTENT_DELIVERY_MODE)
				.build();
	}
}
