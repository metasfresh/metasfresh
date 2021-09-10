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
import de.metas.camel.externalsystems.common.v2.BPLocationCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.bpartner.v2.request.JsonRequestLocationUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_BPARTNER_IDENTIFIER;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class BPartnerLocationRouteBuilder extends RouteBuilder
{
	private static final String ROUTE_ID = "To-MF_Upsert-BPartnerLocation_V2";

	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_LOCATION_V2_CAMEL_URI + "}}")
				.routeId(ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof BPLocationCamelRequest))
					{
						throw new RuntimeCamelException("The route " + ROUTE_ID + " requires the body to be instanceof BPLocationCamelRequest V2."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((BPLocationCamelRequest)lookupRequest).getOrgCode());
					exchange.getIn().setHeader(HEADER_BPARTNER_IDENTIFIER, ((BPLocationCamelRequest)lookupRequest).getBPartnerIdentifier());
					final JsonRequestLocationUpsert jsonRequestLocationUpsert = ((BPLocationCamelRequest)lookupRequest).getJsonRequestLocationUpsert();

					exchange.getIn().setBody(jsonRequestLocationUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestLocationUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-bpartner-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}/${header." + HEADER_BPARTNER_IDENTIFIER + "}/location")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
