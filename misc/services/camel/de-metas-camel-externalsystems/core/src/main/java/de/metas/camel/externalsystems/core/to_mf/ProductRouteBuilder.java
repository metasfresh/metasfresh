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

import de.metas.camel.externalsystems.common.GetProductsCamelRequest;
import de.metas.camel.externalsystems.core.CoreConstants;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_PRODUCTS_ROUTE_ID;
import static de.metas.common.product.v2.response.ProductsQueryParams.AD_PINSTANCE_ID;
import static de.metas.common.product.v2.response.ProductsQueryParams.EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE;
import static de.metas.common.product.v2.response.ProductsQueryParams.EXTERNAL_SYSTEM_CONFIG_TYPE;
import static de.metas.common.product.v2.response.ProductsQueryParams.SINCE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

/**
 * This route invokes the metasfresh REST-API to retrieve product master data.
 * It expects to have a {@link GetProductsCamelRequest} as exchange body.
 */
@Component
public class ProductRouteBuilder extends RouteBuilder
{
	@Override
	public void configure() throws Exception
	{
		errorHandler(noErrorHandler());

		from(direct(MF_GET_PRODUCTS_ROUTE_ID))
				.routeId(MF_GET_PRODUCTS_ROUTE_ID)
				.streamCaching()
				.log("Route invoked")
				.process(exchange -> {
					final Object getProductsRequest = exchange.getIn().getBody();
					if (!(getProductsRequest instanceof GetProductsCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_GET_PRODUCTS_ROUTE_ID + " requires the body to be instanceof GetProductsCamelRequest. "
																+ "However, it is " + (getProductsRequest == null ? "null" : getProductsRequest.getClass().getName()));
					}
					final GetProductsCamelRequest getProductsCamelRequest = (GetProductsCamelRequest)getProductsRequest;

					exchange.getIn().setHeader("queryParams", getQueryParams(getProductsCamelRequest));
				})
				.removeHeaders("CamelHttp*")
				.setHeader(CoreConstants.AUTHORIZATION, simple(CoreConstants.AUTHORIZATION_TOKEN))
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("http://{{metasfresh.products.v2.api.uri}}?${header.queryParams}");
	}

	@NonNull
	private String getQueryParams(@NonNull final GetProductsCamelRequest getProductsCamelRequest)
	{
		String queryParamsAsPath = "";

		if (getProductsCamelRequest.getPInstanceId() != null)
		{
			queryParamsAsPath += AD_PINSTANCE_ID + "=" + getProductsCamelRequest.getPInstanceId().getValue() + "&";
		}

		if (!StringUtils.isEmpty(getProductsCamelRequest.getExternalSystemChildConfigValue()))
		{
			queryParamsAsPath += EXTERNAL_SYSTEM_CHILD_CONFIG_VALUE + "=" + getProductsCamelRequest.getExternalSystemChildConfigValue() + "&";
		}

		if (!StringUtils.isEmpty(getProductsCamelRequest.getExternalSystemType()))
		{
			queryParamsAsPath += EXTERNAL_SYSTEM_CONFIG_TYPE + "=" + getProductsCamelRequest.getExternalSystemType() + "&";
		}

		if (!StringUtils.isEmpty(getProductsCamelRequest.getSince()))
		{
			queryParamsAsPath += SINCE + "=" + getProductsCamelRequest.getSince();
		}

		return queryParamsAsPath.replaceFirst("&$", "");
	}
}
