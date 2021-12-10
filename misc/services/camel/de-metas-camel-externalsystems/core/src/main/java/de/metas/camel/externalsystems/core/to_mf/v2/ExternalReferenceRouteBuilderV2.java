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
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.externalreference.JsonExternalReferenceLookupRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExternalReferenceRouteBuilderV2 extends RouteBuilder
{
	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from(direct(MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_ROUTE_ID))
				.routeId(MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_ROUTE_ID)
				.streamCaching()
				.process(this::validateRequest)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{" + MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_URI + "}}/${header.orgCode}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}

	private void validateRequest(@NonNull final Exchange exchange)
	{
		final var lookupRequest = exchange.getIn().getBody();
		if (!(lookupRequest instanceof JsonExternalReferenceLookupRequest))
		{
			throw new RuntimeCamelException("The route " + MF_LOOKUP_EXTERNAL_REFERENCE_V2_CAMEL_ROUTE_ID + " requires the body to be instanceof JsonExternalReferenceLookupRequest. "
													+ "However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
		}
		final JsonExternalReferenceLookupRequest lookupRequest1 = (JsonExternalReferenceLookupRequest)lookupRequest;
		log.info("Route invoked with " + lookupRequest1.getItems().size() + " request items");
	}
}
