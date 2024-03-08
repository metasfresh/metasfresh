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

package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.ordercandidates.v2.request.JsonOLCandCreateBulkRequest;
import de.metas.common.ordercandidates.v2.request.JsonOLCandProcessRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class OrderLineCandRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		from(direct(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID))
				.routeId(ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof JsonOLCandCreateBulkRequest))
					{
						throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_PUSH_OL_CANDIDATES_ROUTE_ID + " requires the body to be instanceof JsonOLCandCreateBulkRequest. "
																+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final JsonOLCandCreateBulkRequest createOLCandidatesRequest = (JsonOLCandCreateBulkRequest)request;
					log.info("Route invoked with : " + createOLCandidatesRequest.getRequests().size() + " items.");
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonOLCandCreateBulkRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{metasfresh.olcands.v2.api.uri}}/bulk")

				.to(direct(UNPACK_V2_API_RESPONSE));

		from(direct(ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID))
				.routeId(ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID)
				.streamCaching()
				.log("Route invoked! request: ${body}")
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof JsonOLCandProcessRequest))
					{
						throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_PROCESS_OL_CANDIDATES_ROUTE_ID + " requires the body to be instanceof JsonOLCandProcessRequest. "
																+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
					}
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonOLCandProcessRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.olcands.v2.api.uri}}/process")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
