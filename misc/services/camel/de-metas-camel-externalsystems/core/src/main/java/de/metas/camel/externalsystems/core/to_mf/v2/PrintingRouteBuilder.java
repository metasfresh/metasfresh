/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2023 metas GmbH
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
import de.metas.common.rest_api.v2.printing.request.JsonPrintingResultRequest;
import de.metas.common.rest_api.v2.printing.response.JsonPrintingDataResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRINTING_QUEUE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_PRINTING_DATA_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_PRINT_V2_BASE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_SET_PRINTING_RESULT_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PrintingRouteBuilder extends RouteBuilder
{

	@Override
	public void configure() throws Exception
	{
		from(direct(MF_GET_PRINTING_DATA_ROUTE_ID))
				.routeId(MF_GET_PRINTING_DATA_ROUTE_ID)
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_PRINT_V2_BASE + "}}/getPrintingData/" + "${header." + HEADER_PRINTING_QUEUE_ID + "}")
				.streamCaching()
				.to(direct(UNPACK_V2_API_RESPONSE))
				.unmarshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonPrintingDataResponse.class));

		from(direct(MF_SET_PRINTING_RESULT_ROUTE_ID))
				.routeId(MF_SET_PRINTING_RESULT_ROUTE_ID)
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(),JsonPrintingResultRequest.class))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.POST))
				.toD("{{" + MF_PRINT_V2_BASE + "}}/setPrintingResult/" + "${header." + HEADER_PRINTING_QUEUE_ID + "}");
	}



}
