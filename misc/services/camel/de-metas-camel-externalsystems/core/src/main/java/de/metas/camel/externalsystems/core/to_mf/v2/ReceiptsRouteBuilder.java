/*
 * #%L
 * de-metas-camel-externalsystems-core
 * %%
 * Copyright (C) 2025 metas GmbH
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

import de.metas.camel.externalsystems.common.v2.ReceiptsCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.shipping.v2.receipt.JsonCreateReceiptsRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.http.common.HttpMethods;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_CREATE_RECEIPTS_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * This route invokes the metasfresh REST-API to create receipts.
 * It expects to have a {@link ReceiptsCamelRequest} as exchange body.
 */
@Component
public class ReceiptsRouteBuilder extends RouteBuilder
{
	private static final String CREATE_RECEIPTS_ROUTE_ID = "To-MF_createReceipts_V2";

	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_CREATE_RECEIPTS_V2_CAMEL_URI))
				.routeId(CREATE_RECEIPTS_ROUTE_ID)
				.streamCache("true")
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof ReceiptsCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_CREATE_RECEIPTS_V2_CAMEL_URI + " requires the body to be instanceof ReceiptsCamelRequest V2."
								+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					final JsonCreateReceiptsRequest jsonCreateReceiptsRequest = ((ReceiptsCamelRequest)lookupRequest).getJsonCreateReceiptsRequest();

					exchange.getIn().setBody(jsonCreateReceiptsRequest);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonCreateReceiptsRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
				.toD("{{metasfresh.create-receipts-v2.api.uri}}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
