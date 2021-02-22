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

package de.metas.camel.externalsystems.core.to_mf;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.BPUpsertCamelRequest;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.bpartner.request.JsonRequestBPartnerUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;

@Component
public class BPartnerRouteBuilder extends RouteBuilder
{
	@VisibleForTesting
	static final String ROUTE_ID = "To-MF_Upsert-BPartner";

	@Override
	public void configure()
	{
		from("{{" + ExternalSystemCamelConstants.MF_UPSERT_BPARTNER_CAMEL_URI + "}}")
				.routeId(ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof BPUpsertCamelRequest))
					{
						throw new RuntimeCamelException("The route " + ROUTE_ID + " requires the body to be instanceof BPUpsertCamelRequest."
								+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((BPUpsertCamelRequest)lookupRequest).getOrgCode());
					final var jsonRequestBPartnerUpsert = ((BPUpsertCamelRequest)lookupRequest).getJsonRequestBPartnerUpsert();

					log.info("Route invoked with " + jsonRequestBPartnerUpsert.getRequestItems().size() + " requestItems");
					exchange.getIn().setBody(jsonRequestBPartnerUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestBPartnerUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("http://{{metasfresh.upsert-bpartner.api.uri}}/${header." + HEADER_ORG_CODE + "}");
	}
}
