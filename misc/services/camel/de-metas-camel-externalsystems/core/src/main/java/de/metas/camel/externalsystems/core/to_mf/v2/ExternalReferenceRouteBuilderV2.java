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

package de.metas.camel.externalsystems.core.to_mf.v2;

import de.metas.camel.externalsystems.common.v2.ExternalReferenceLookupCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * This route invokes the metasfresh REST-API to retrieve external reference data.
 * It expects to have a {@link ExternalReferenceLookupCamelRequest} as exchange body.
 */
@Component
public class ExternalReferenceRouteBuilderV2 extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID))
				.routeId(MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID)
				.streamCaching()
				.log("Route invoked")
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();

					if (!(lookupRequest instanceof ExternalReferenceLookupCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_LOOKUP_EXTERNAL_REFERENCE_v2_ROUTE_ID + " requires the body to be instanceof ExternalReferenceLookupCamelRequest."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((ExternalReferenceLookupCamelRequest)lookupRequest).getOrgCode());

					final JsonExternalReferenceLookupRequest jsonExternalReferenceLookupRequest = ((ExternalReferenceLookupCamelRequest)lookupRequest).getExternalReferenceLookupRequest();
					log.info("Lookup external reference route invoked with " + jsonExternalReferenceLookupRequest);
					exchange.getIn().setBody(jsonExternalReferenceLookupRequest);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.search-externalreference.v2.api.uri}}/${header." + HEADER_ORG_CODE + "}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
