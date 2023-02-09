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

import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.v2.RetrieveProductCamelRequest;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRODUCT_IDENTIFIER;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class MaterialProductRouteBuilder extends RouteBuilder
{

	@Override
	public void configure() throws Exception
	{
		from(direct(MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID))
				.routeId(MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID)
				.streamCaching()
				.log("Route invoked!")
				.process(this::validateAndAttachHeaders)
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.GET))
				.toD("{{metasfresh.material-products-v2.api-uri}}/${header.orgCode}/${header.productIdentifier}")

				.to(direct(UNPACK_V2_API_RESPONSE));
	}

	private void validateAndAttachHeaders(@NonNull final Exchange exchange)
	{
		final Object request = exchange.getIn().getBody();
		if (!(request instanceof RetrieveProductCamelRequest))
		{
			throw new RuntimeCamelException("The route " + ExternalSystemCamelConstants.MF_RETRIEVE_MATERIAL_PRODUCT_INFO_V2_CAMEL_ROUTE_ID + " requires the body to be instanceof RetrieveProductCamelRequest. "
													+ "However, it is " + (request == null ? "null" : request.getClass().getName()));
		}

		final RetrieveProductCamelRequest retrieveProductCamelRequest = exchange.getIn().getBody(RetrieveProductCamelRequest.class);

		exchange.getIn().setHeader(HEADER_PRODUCT_IDENTIFIER, retrieveProductCamelRequest.getProductIdentifier());
		exchange.getIn().setHeader(HEADER_ORG_CODE, retrieveProductCamelRequest.getOrgCode());
	}
}
