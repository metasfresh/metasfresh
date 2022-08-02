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
import de.metas.common.rest_api.v2.project.JsonRequestProjectUpsert;
import de.metas.common.rest_api.v2.project.workorder.JsonWorkOrderProjectUpsertRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_WORK_ORDER_PROJECT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_WORK_ORDER_PROJECT_V2_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class WorkOrderProjectRouteBuilder extends RouteBuilder
{
	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from(direct(MF_UPSERT_WORK_ORDER_PROJECT_V2_ROUTE_ID))
				.routeId(MF_UPSERT_WORK_ORDER_PROJECT_V2_ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof JsonWorkOrderProjectUpsertRequest))
					{
						throw new RuntimeCamelException("The route " + MF_UPSERT_WORK_ORDER_PROJECT_V2_ROUTE_ID + " requires the body to be instanceof JsonWorkOrderProjectRequest."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					final JsonWorkOrderProjectUpsertRequest jsonRequestUpsert = ((JsonWorkOrderProjectUpsertRequest)lookupRequest);

					log.info("WorkOrder-Project upsert route invoked");
					exchange.getIn().setBody(jsonRequestUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestProjectUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_UPSERT_WORK_ORDER_PROJECT_V2_CAMEL_URI + "}}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
