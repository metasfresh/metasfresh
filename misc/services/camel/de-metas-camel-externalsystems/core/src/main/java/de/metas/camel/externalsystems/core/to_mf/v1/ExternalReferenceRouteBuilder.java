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

package de.metas.camel.externalsystems.core.to_mf.v1;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.externalreference.v1.JsonExternalReferenceCreateRequest;
import de.metas.common.externalreference.v1.JsonExternalReferenceLookupRequest;
import de.metas.common.externalreference.v1.JsonRequestExternalReferenceUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

@Component
public class ExternalReferenceRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String CREATE_ROUTE_ID = "To-MF_Create-ExternalReference";

	@VisibleForTesting
	static final String LOOKUP_ROUTE_ID = "To-MF_Lookup-ExternalReference";

	@VisibleForTesting
	static final String UPSERT_ROUTE_ID = "To-MF_Upsert-ExternalReference";

	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from("{{" + ExternalSystemCamelConstants.MF_CREATE_EXTERNALREFERENCE_CAMEL_URI + "}}")
				.routeId(CREATE_ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof JsonExternalReferenceCreateRequest))
					{
						throw new RuntimeCamelException("The route " + CREATE_ROUTE_ID + " requires the body to be instanceof JsonExternalReferenceCreateRequest. "
								+ "However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}
					final JsonExternalReferenceCreateRequest lookupRequest1 = (JsonExternalReferenceCreateRequest)lookupRequest;
					log.info("Route invoked with " + lookupRequest1.getItems().size() + " request items");
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceCreateRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.to("{{metasfresh.create-externalreference.api.uri}}");

		from("{{" + ExternalSystemCamelConstants.MF_LOOKUP_EXTERNALREFERENCE_CAMEL_URI + "}}")
				.routeId(LOOKUP_ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final Object lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof JsonExternalReferenceLookupRequest))
					{
						throw new RuntimeCamelException("The route " + LOOKUP_ROUTE_ID + " requires the body to be instanceof JsonExternalReferenceLookupRequest. "
								+ "However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}
					final JsonExternalReferenceLookupRequest lookupRequest1 = (JsonExternalReferenceLookupRequest)lookupRequest;
					log.info("Route invoked with " + lookupRequest1.getItems().size() + " request items");
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonExternalReferenceLookupRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.lookup-externalreference.api.uri}}/${header.orgCode}");

		from("{{" + ExternalSystemCamelConstants.MF_UPSERT_EXTERNALREFERENCE_CAMEL_URI + "}}")
				.routeId(UPSERT_ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof JsonRequestExternalReferenceUpsert))
					{
						throw new RuntimeCamelException("The route " + UPSERT_ROUTE_ID + " requires the body to be instanceof JsonRequestExternalReferenceUpsert. "
																+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final JsonRequestExternalReferenceUpsert upsertRequest = (JsonRequestExternalReferenceUpsert)request;
					log.info("Route invoked with system name: " + upsertRequest.getSystemName().getName());
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestExternalReferenceUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-externalreference.api.uri}}/${header.orgCode}");
	}
}
