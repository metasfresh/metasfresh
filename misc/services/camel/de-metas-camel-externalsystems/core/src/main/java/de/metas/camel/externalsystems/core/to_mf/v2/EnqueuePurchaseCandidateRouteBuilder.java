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

import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.JsonPurchaseCandidatesRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class EnqueuePurchaseCandidateRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI))
				.routeId(MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI)
				.streamCaching()
				.process(exchange -> {
					final var request = exchange.getIn().getBody();
					if (!(request instanceof JsonPurchaseCandidatesRequest))//
					{
						throw new RuntimeCamelException("The route " + MF_ENQUEUE_PURCHASE_CANDIDATES_V2_CAMEL_URI + " requires the body to be instanceof JsonPurchaseCandidatesRequest."
																+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final JsonPurchaseCandidatesRequest jsonPurchaseCandidatesRequest = (JsonPurchaseCandidatesRequest)request;
					log.info("Enqueue purchases candidate route invoked with " + jsonPurchaseCandidatesRequest);
					exchange.getIn().setBody(jsonPurchaseCandidatesRequest);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonPurchaseCandidatesRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{metasfresh.enqueue-purchases-candidate-v2.api.uri}}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
