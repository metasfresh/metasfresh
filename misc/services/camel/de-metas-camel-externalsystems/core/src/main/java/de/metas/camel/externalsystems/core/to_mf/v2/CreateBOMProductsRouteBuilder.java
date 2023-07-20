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

import de.metas.camel.externalsystems.common.v2.BOMUpsertCamelRequest;
import de.metas.camel.externalsystems.common.v2.VerifyBOMCamelRequest;
import de.metas.camel.externalsystems.core.CamelRouteHelper;
import de.metas.common.rest_api.v2.bom.JsonBOMCreateRequest;
import org.apache.camel.Exchange;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.dsl.HttpEndpointBuilderFactory;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_ORG_CODE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PRODUCT_IDENTIFIER;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_VERIFY_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.core.to_mf.v2.UnpackV2ResponseRouteBuilder.UNPACK_V2_API_RESPONSE;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class CreateBOMProductsRouteBuilder extends RouteBuilder
{
	@Override
	public void configure()
	{
		from(direct(MF_UPSERT_BOM_V2_CAMEL_URI))
				.routeId(MF_UPSERT_BOM_V2_CAMEL_URI)
				.streamCaching()
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof BOMUpsertCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_UPSERT_BOM_V2_CAMEL_URI + " requires the body to be instanceof BOMUpsertCamelRequest V2."
																+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final BOMUpsertCamelRequest bomUpsertCamelRequest = (BOMUpsertCamelRequest)request;

					exchange.getIn().setHeader(HEADER_ORG_CODE, bomUpsertCamelRequest.getOrgCode());

					final JsonBOMCreateRequest jsonBOMCreateRequest = bomUpsertCamelRequest.getJsonBOMCreateRequest();

					exchange.getIn().setBody(jsonBOMCreateRequest);
				})
				.marshal(CamelRouteHelper.setupJacksonDataFormatFor(getContext(), JsonBOMCreateRequest.class))
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-bom-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}")

				.to(direct(UNPACK_V2_API_RESPONSE));

		from(direct(MF_VERIFY_BOM_V2_CAMEL_URI))
				.routeId(MF_VERIFY_BOM_V2_CAMEL_URI)
				.streamCaching()
				.process(exchange -> {
					final Object request = exchange.getIn().getBody();
					if (!(request instanceof VerifyBOMCamelRequest))
					{
						throw new RuntimeCamelException("The route " + MF_VERIFY_BOM_V2_CAMEL_URI + " requires the body to be instanceof VerifyBOMCamelRequest V2."
																+ " However, it is " + (request == null ? "null" : request.getClass().getName()));
					}

					final VerifyBOMCamelRequest verifyBOMCamelRequest = (VerifyBOMCamelRequest)request;

					exchange.getIn().setHeader(HEADER_ORG_CODE, verifyBOMCamelRequest.getOrgCode());
					exchange.getIn().setHeader(HEADER_PRODUCT_IDENTIFIER, verifyBOMCamelRequest.getProductIdentifier());

					exchange.getIn().setBody(null);
				})
				.removeHeaders("CamelHttp*")
				.setHeader(Exchange.HTTP_METHOD, constant(HttpEndpointBuilderFactory.HttpMethods.PUT))
				.toD("{{metasfresh.upsert-bom-v2.api.uri}}/${header." + HEADER_ORG_CODE + "}/verify/${header." + HEADER_PRODUCT_IDENTIFIER + "}");
	}
}
