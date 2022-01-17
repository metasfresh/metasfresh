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

import de.metas.camel.externalsystems.common.v2.RetrieveHUCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.handlingunits.JsonHUAttributes;
import de.metas.common.handlingunits.JsonHUAttributesRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_HU_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_HU_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class HURouteBuilderV2 extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID))
				.routeId(MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID)
				.streamCaching()

				.process(this::validateAndAttachHeaders)

				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{" + MF_RETRIEVE_HU_V2_CAMEL_URI + "}}/${header.M_HU_ID}")

				.to(direct(UNPACK_V2_API_RESPONSE));

		from(direct(MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID))
				.routeId(MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID)
				.streamCaching()

				.process(this::validateHUUpdateRequest)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonHUAttributes.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{" + MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_URI + "}}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}

	private void validateAndAttachHeaders(@NonNull final Exchange exchange)
	{
		final var lookupRequest = exchange.getIn().getBody();
		if (!(lookupRequest instanceof RetrieveHUCamelRequest))
		{
			throw new RuntimeCamelException("The route " + MF_RETRIEVE_HU_V2_CAMEL_ROUTE_ID + " requires the body to be instanceof RetrieveHUCamelRequest."
													+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
		}

		final RetrieveHUCamelRequest retrieveHUCamelRequest = ((RetrieveHUCamelRequest)lookupRequest);

		exchange.getIn().setHeader(HEADER_HU_ID, retrieveHUCamelRequest.getHuIdentifier());
		exchange.getIn().setHeader(HEADER_EXTERNALSYSTEM_CONFIG_ID, retrieveHUCamelRequest.getExternalSystemConfigId().getValue());

		if (retrieveHUCamelRequest.getAdPInstanceId() != null)
		{
			exchange.getIn().setHeader(HEADER_PINSTANCE_ID, retrieveHUCamelRequest.getAdPInstanceId().getValue());
		}
	}

	private void validateHUUpdateRequest(@NonNull final Exchange exchange)
	{
		final var lookupRequest = exchange.getIn().getBody();
		if (!(lookupRequest instanceof JsonHUAttributesRequest))
		{
			throw new RuntimeCamelException("The route " + MF_UPDATE_HU_ATTRIBUTES_V2_CAMEL_ROUTE_ID + " requires the body to be instanceof JsonHUAttributesRequest."
													+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
		}

		final JsonHUAttributesRequest request = ((JsonHUAttributesRequest)lookupRequest);

		exchange.getIn().setBody(request);
	}
}
