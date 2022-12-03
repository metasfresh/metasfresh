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

import de.metas.camel.externalsystems.common.v2.BPProductCamelRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_BPARTNER_IDENTIFIER;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_BPARTNER_PRODUCTS_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * This route invokes the metasfresh REST-API to retrieve bPartnerProduct master data.
 * It expects to have a {@link BPProductCamelRequest} as exchange body.
 */
@Component
public class BPartnerProductRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_GET_BPARTNER_PRODUCTS_ROUTE_ID))
				.routeId(MF_GET_BPARTNER_PRODUCTS_ROUTE_ID)
				.streamCaching()
				.log("Route invoked")
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();

					if (!(lookupRequest instanceof BPProductCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_GET_BPARTNER_PRODUCTS_ROUTE_ID + " requires the body to be instanceof BPProductCamelRequest."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((BPProductCamelRequest)lookupRequest).getOrgCode());
					exchange.getIn().setHeader(HEADER_BPARTNER_IDENTIFIER, ((BPProductCamelRequest)lookupRequest).getBPartnerIdentifier());
				})
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{metasfresh.upsert-bpartner-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}/${header." + HEADER_BPARTNER_IDENTIFIER + "}/products")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
