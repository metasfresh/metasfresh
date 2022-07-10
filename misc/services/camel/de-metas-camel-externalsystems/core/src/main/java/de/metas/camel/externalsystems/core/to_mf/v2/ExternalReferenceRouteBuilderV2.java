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
import de.metas.common.externalreference.v2.JsonExternalReferenceLookupRequest;
import de.metas.common.externalsystem.ExternalSystemConstants;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class ExternalReferenceRouteBuilderV2 extends RouteBuilder
{
	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from(direct(MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI))
				.routeId(MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI)
				.streamCaching()
				.log("Route invoked")
				.process(exchange -> {
					final Object camelRequest = exchange.getIn().getBody();
					if (!(camelRequest instanceof ExternalReferenceLookupCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_LOOKUP_EXTERNALREFERENCE_V2_CAMEL_URI + " requires the body to be instanceof ExternalReferenceLookupCamelRequest. "
																+ "However, it is " + (camelRequest == null ? "null" : camelRequest.getClass().getName()));
					}

					final ExternalReferenceLookupCamelRequest externalReferenceLookupCamelRequest = (ExternalReferenceLookupCamelRequest)camelRequest;

					exchange.getIn().setHeader(HEADER_EXTERNALSYSTEM_CONFIG_ID, externalReferenceLookupCamelRequest.getExternalSystemConfigId().getValue());
					exchange.getIn().setHeader(HEADER_ORG_CODE, externalReferenceLookupCamelRequest.getOrgCode());

					if (externalReferenceLookupCamelRequest.getAdPInstanceId() != null)
					{
						exchange.getIn().setHeader(ExternalSystemConstants.HEADER_PINSTANCE_ID, externalReferenceLookupCamelRequest.getAdPInstanceId().getValue());
					}

					final JsonExternalReferenceLookupRequest request = externalReferenceLookupCamelRequest.getJsonExternalReferenceLookupRequest();

					exchange.getIn().setBody(request);

					log.info("Route invoked with " + request.getItems().size() + " request items");
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.lookup-externalreference-v2.api.uri}}/${header.orgCode}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
