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

import de.metas.camel.externalsystems.common.GetProductsCamelRequest;
import de.metas.camel.externalsystems.common.v2.ProductPriceUpsertCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.camel.externalsystems.core.CoreConstants;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRICE_LIST_VERSION_IDENTIFIER;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * This route invokes the metasfresh REST-API to retrieve product master data.
 * It expects to have a {@link GetProductsCamelRequest} as exchange body.
 */
@Component
public class ProductPriceRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI))
				.routeId(MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI)
				.streamCaching()
				.process(exchange -> {
					final var lookupRequest = exchange.getIn().getBody();
					if (!(lookupRequest instanceof ProductPriceUpsertCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_UPSERT_PRODUCT_PRICE_V2_CAMEL_URI + " requires the body to be instanceof ProductPriceUpsertCamelRequest V2."
																+ " However, it is " + (lookupRequest == null ? "null" : lookupRequest.getClass().getName()));
					}

					exchange.getIn().setHeader(HEADER_PRICE_LIST_VERSION_IDENTIFIER, ((ProductPriceUpsertCamelRequest)lookupRequest).getPriceListVersionIdentifier());
					final var jsonRequestProductPriceUpsert = ((ProductPriceUpsertCamelRequest)lookupRequest).getJsonRequestProductPriceUpsert();

					log.info("Product price upsert route invoked with " + jsonRequestProductPriceUpsert.getRequestItems().size() + " requestItems");
					exchange.getIn().setBody(jsonRequestProductPriceUpsert);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonRequestProductUpsert.class))
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-product-price-v2.api.uri}}/${header." + HEADER_PRICE_LIST_VERSION_IDENTIFIER + "}/productPrices")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}
}
