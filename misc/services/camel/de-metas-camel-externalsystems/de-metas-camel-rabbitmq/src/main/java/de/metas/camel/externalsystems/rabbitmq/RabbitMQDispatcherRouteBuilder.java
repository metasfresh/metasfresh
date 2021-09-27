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

package de.metas.camel.externalsystems.rabbitmq;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.rabbitmq.api.DispatchMessageRequest;
import de.metas.camel.externalsystems.rabbitmq.api.JsonRabbitMQHttpMessage;
import de.metas.camel.externalsystems.rabbitmq.api.JsonRabbitMQHttpResponse;
import de.metas.camel.externalsystems.rabbitmq.common.CamelConstants;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class RabbitMQDispatcherRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String RABBITMQ_DEADLETTER_ROUTE_ID = "direct:RabbitMQ-deadLetter";
	@VisibleForTesting
	static final String RABBIT_MQ_RESPONSE_PROCESSOR_ID = "RabbitMQ-responseProcessorId";
	@VisibleForTesting
	static final String RABBITMQ_ENDPOINT_ID = "rabbitMQEndpointId";
	@VisibleForTesting
	static final String RABBITMQ_PUBLISH_ENDPOINT = "/messages/publish";
	@VisibleForTesting
	static final String RABBITMQ_MESSAGE_SENDER = "RabbitMQMessageSender";

	public static final String RABBITMQ_DISPATCHER_ROUTE_ID = "RabbitMQDispatcher";

	@Override
	public void configure()
	{
		CamelRouteUtil.setupProperties(getContext());

		final String maximumRedeliveries = CamelRouteUtil.resolveProperty(getContext(), CamelConstants.EXPORT_BPARTNER_RETRY_COUNT, "0");
		final String redeliveryDelay = CamelRouteUtil.resolveProperty(getContext(), CamelConstants.EXPORT_BPARTNER_RETRY_DELAY, "0");

		errorHandler(deadLetterChannel(RABBITMQ_DEADLETTER_ROUTE_ID)
							 .logHandled(true)
							 .maximumRedeliveries(Integer.parseInt(maximumRedeliveries))
							 .redeliveryDelay(Integer.parseInt(redeliveryDelay)));

		from(direct(RABBITMQ_DISPATCHER_ROUTE_ID))
				.routeId(RABBITMQ_DISPATCHER_ROUTE_ID)
				.streamCaching()
				.process(this::extractAndAttachRabbitHttpRequest)
				.to(direct(RABBITMQ_MESSAGE_SENDER));

		from(direct(RABBITMQ_MESSAGE_SENDER))
				.routeId(RABBITMQ_MESSAGE_SENDER)
				//dev-note: we need the whole route to be replayed in case of redelivery
				.errorHandler(noErrorHandler())
				.log("invoked")
				.marshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonRabbitMQHttpMessage.class))
				//dev-note: the actual path is computed in this.extractAndAttachRabbitHttpRequest()
				.to("https://placeholder").id(RABBITMQ_ENDPOINT_ID)

				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonRabbitMQHttpResponse.class))
				.process(this::validateMessageRouted).id(RABBIT_MQ_RESPONSE_PROCESSOR_ID);

		from(RABBITMQ_DEADLETTER_ROUTE_ID)
				.routeId(RABBITMQ_DEADLETTER_ROUTE_ID)
				.to(direct(MF_ERROR_ROUTE_ID));
	}

	private void extractAndAttachRabbitHttpRequest(@NonNull final Exchange exchange)
	{
		final Object dispatchMessageRequestCandidate = exchange.getIn().getBody();

		if (!(dispatchMessageRequestCandidate instanceof DispatchMessageRequest))
		{
			throw new RuntimeCamelException("The route " + RABBITMQ_DISPATCHER_ROUTE_ID + " requires the body to be instanceof DispatchMessageRequest."
													+ " However, it is " + (dispatchMessageRequestCandidate == null ? "null" : dispatchMessageRequestCandidate.getClass().getName()));
		}

		final DispatchMessageRequest dispatchMessageRequest = (DispatchMessageRequest)dispatchMessageRequestCandidate;

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().setHeader(AUTHORIZATION, dispatchMessageRequest.getAuthToken());
		exchange.getIn().setHeader(Exchange.HTTP_URI, dispatchMessageRequest.getUrl() + RABBITMQ_PUBLISH_ENDPOINT);
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpEndpointBuilderFactory.HttpMethods.POST);
		exchange.getIn().setBody(dispatchMessageRequest.getMessage());
	}

	private void validateMessageRouted(@NonNull final Exchange exchange)
	{
		final JsonRabbitMQHttpResponse jsonRabbitMQHttpResponse = exchange.getIn().getBody(JsonRabbitMQHttpResponse.class);

		if (!jsonRabbitMQHttpResponse.isRouted())
		{
			throw new RuntimeCamelException("RabbitMQ export message was not sent to at least one queue");
		}
	}
}
