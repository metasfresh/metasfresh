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

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.BPRetrieveCamelRequest;
import de.metas.camel.externalsystems.common.v2.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.bpartner.v2.request.JsonRequestBPartnerUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_BPARTNER_IDENTIFIER;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_EXTERNALSYSTEM_CONFIG_ID;
import static de.metas.common.externalsystem.ExternalSystemConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class BPartnerRouteBuilderV2 extends RouteBuilder
{
	@VisibleForTesting
	static final String ROUTE_ID = "To-MF_Upsert-BPartner_V2";
	@VisibleForTesting
	static final String RETRIEVE_BPARTNER_ROUTE_ID = "To-MF_retrieveBPartner_V2";
	@VisibleForTesting
	static final String RETRIEVE_BPARTNER_PROCESSOR_ID = "RetrieveBPartnerProcessorId";
	@VisibleForTesting
	static final String RETRIEVE_BPARTNER_ENDPOINT_ID = "RetrieveBPartnerEndpointId";

	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_V2_CAMEL_URI + "}}")
				.routeId(ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof BPUpsertCamelRequest))
					{
						throw new RuntimeCamelException("The route " + ROUTE_ID + " requires the body to be instanceof BPUpsertCamelRequest V2."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((BPUpsertCamelRequest)lookupRequest).getOrgCode());
					final var jsonRequestBPartnerUpsert = ((BPUpsertCamelRequest)lookupRequest).getJsonRequestBPartnerUpsert();

					log.info("BPartner upsert route invoked with " + jsonRequestBPartnerUpsert.getRequestItems().size() + " requestItems");
					exchange.getIn().setBody(jsonRequestBPartnerUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestBPartnerUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-bpartner-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}")

				.to(direct(UNPACK_V2_API_RESPONSE));


		from("{{" + ExternalSystemCamelConstants.MF_RETRIEVE_BPARTNER_V2_CAMEL_URI + "}}")
				.routeId(RETRIEVE_BPARTNER_ROUTE_ID)
				.streamCaching()

				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof BPRetrieveCamelRequest))
					{
						throw new RuntimeCamelException("The route " + RETRIEVE_BPARTNER_ROUTE_ID + " requires the body to be instanceof BPRetrieveCamelRequest."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					final BPRetrieveCamelRequest retrieveCamelRequest = ((BPRetrieveCamelRequest)lookupRequest);

					exchange.getIn().setHeader(HEADER_BPARTNER_IDENTIFIER, retrieveCamelRequest.getBPartnerIdentifier());
					exchange.getIn().setHeader(HEADER_EXTERNALSYSTEM_CONFIG_ID, retrieveCamelRequest.getExternalSystemConfigId().getValue());

					if (retrieveCamelRequest.getAdPInstanceId() != null)
					{
						exchange.getIn().setHeader(HEADER_PINSTANCE_ID, retrieveCamelRequest.getAdPInstanceId().getValue());
					}
				}).id(RETRIEVE_BPARTNER_PROCESSOR_ID)

				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{metasfresh.retrieve-bpartner-v2.api.uri}}/${header." + HEADER_BPARTNER_IDENTIFIER + "}").id(RETRIEVE_BPARTNER_ENDPOINT_ID)

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
