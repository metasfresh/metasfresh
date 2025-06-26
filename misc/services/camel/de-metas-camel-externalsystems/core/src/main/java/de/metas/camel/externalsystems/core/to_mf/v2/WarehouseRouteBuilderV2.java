/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2024 metas GmbH
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
import de.metas.camel.externalsystems.common.v2.WarehouseUpsertCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.warehouse.JsonRequestWarehouseUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class WarehouseRouteBuilderV2 extends RouteBuilder
{
	@VisibleForTesting
	static final String ROUTE_ID = "To-MF_Upsert-Warehouse_V2";
	
	@Override
	public void configure()
	{
		errorHandler(noErrorHandler());

		from("{{" + ExternalSystemCamelConstants.MF_UPSERT_WAREHOUSE_V2_CAMEL_URI + "}}")
				.routeId(ROUTE_ID)
				.streamCaching()
				.process(exchange -> {
					final var upsertRequest = exchange.getIn().getBody();
					if (!(upsertRequest instanceof WarehouseUpsertCamelRequest))
					{
						throw new RuntimeCamelException("The route " + ROUTE_ID + " requires the body to be instanceof WarehouseUpsertCamelRequest V2."
																+ " However, it is " + (upsertRequest == null ? "null" : upsertRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_ORG_CODE, ((WarehouseUpsertCamelRequest)upsertRequest).getOrgCode());
					final var jsonRequestWarehouseUpsert = ((WarehouseUpsertCamelRequest)upsertRequest).getJsonRequestWarehouseUpsert();

					log.info("Warehouse upsert route invoked with " + jsonRequestWarehouseUpsert.getRequestItems().size() + " requestItems");
					exchange.getIn().setBody(jsonRequestWarehouseUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestWarehouseUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-warehouse-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
