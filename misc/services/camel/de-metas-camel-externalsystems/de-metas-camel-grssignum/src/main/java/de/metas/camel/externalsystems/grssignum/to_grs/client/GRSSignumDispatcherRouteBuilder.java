/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.to_grs.client;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.to_grs.client.model.DispatchRequest;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static org.apache.camel.Exchange.HTTP_URI;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class GRSSignumDispatcherRouteBuilder extends RouteBuilder
{
	public static final String GRS_DEADLETTER_ROUTE_ID = "direct:GRS-deadLetter";
	public static final String GRS_DISPATCHER_ROUTE_ID = "GRSDispatcher";
	public static final String GRS_MESSAGE_SENDER = "GRSMessageSender";
	public static final String GRS_ENDPOINT_ID = "GRSEndpointId";

	@Override
	public void configure() throws Exception
	{
		CamelRouteUtil.setupProperties(getContext());

		final String maximumRedeliveries = CamelRouteUtil.resolveProperty(getContext(), GRSSignumConstants.EXPORT_BPARTNER_RETRY_COUNT, "0");
		final String redeliveryDelay = CamelRouteUtil.resolveProperty(getContext(), GRSSignumConstants.EXPORT_BPARTNER_RETRY_DELAY, "0");

		errorHandler(deadLetterChannel(GRS_DEADLETTER_ROUTE_ID)
							 .logHandled(true)
							 .maximumRedeliveries(Integer.parseInt(maximumRedeliveries))
							 .redeliveryDelay(Integer.parseInt(redeliveryDelay)));

		from(GRS_DEADLETTER_ROUTE_ID)
				.routeId(GRS_DEADLETTER_ROUTE_ID)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(GRS_DISPATCHER_ROUTE_ID))
				.routeId(GRS_DISPATCHER_ROUTE_ID)
				.streamCaching()
				.process(this::extractAndAttachGRSSignumHttpRequest)
				.to(direct(GRS_MESSAGE_SENDER));

		from(direct(GRS_MESSAGE_SENDER))
				.routeId(GRS_MESSAGE_SENDER)
				//dev-note: we need the whole route to be replayed in case of redelivery
				.errorHandler(noErrorHandler())
				.log("invoked")
				
				//uncomment this to get an "escaped" JSON string with e.g. "\{ blabla \}"
				//.marshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), String.class))
				
				//dev-note: the actual path is computed in this.extractAndAttachGRSSignumHttpRequest()
				.to("https://placeholder").id(GRS_ENDPOINT_ID);
	}

	private void extractAndAttachGRSSignumHttpRequest(@NonNull final Exchange exchange)
	{
		final Object dispatchMessageRequestCandidate = exchange.getIn().getBody();

		if (!(dispatchMessageRequestCandidate instanceof DispatchRequest))
		{
			throw new RuntimeCamelException("The route " + GRS_DISPATCHER_ROUTE_ID
													+ " requires the body to be instanceof DispatchMessageRequest."
													+ " However, it is " + (dispatchMessageRequestCandidate == null ? "null" : dispatchMessageRequestCandidate.getClass().getName()));
		}

		final DispatchRequest dispatchMessageRequest = (DispatchRequest)dispatchMessageRequestCandidate;

		exchange.getIn().removeHeaders("CamelHttp*");
		exchange.getIn().removeHeader(AUTHORIZATION); // remove the token from metasfresh's API
		exchange.getIn().setHeader(HTTP_URI, dispatchMessageRequest.getUrl());
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, HttpEndpointBuilderFactory.HttpMethods.POST);

		if (Check.isNotBlank(dispatchMessageRequest.getAuthToken()))
		{
			exchange.getIn().setHeader(AUTHORIZATION, dispatchMessageRequest.getAuthToken());
		}

		exchange.getIn().setBody(dispatchMessageRequest.getRequest());
	}
}
