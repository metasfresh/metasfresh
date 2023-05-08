/*
 * #%L
 * de-metas-camel-grssignum
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

package de.metas.camel.externalsystems.grssignum.from_grs.bom;

import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.auth.TokenCredentials;
import de.metas.camel.externalsystems.common.v2.VerifyBOMCamelRequest;
import de.metas.camel.externalsystems.grssignum.GRSSignumConstants;
import de.metas.camel.externalsystems.grssignum.from_grs.RetrieveExternalSystemRequestBuilder;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.processor.ProductsAttachFileProcessor;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.processor.PushBOMProductsProcessor;
import de.metas.camel.externalsystems.grssignum.from_grs.bom.processor.PushProductProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.ExternalIdentifierFormat;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_VERIFY_BOM_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.common.externalsystem.parameters.GRSSignumParameters.PARAM_BasePathForExportDirectories;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PushBOMProductsRouteBuilder extends RouteBuilder
{
	public static final String PUSH_BOM_PRODUCTS_ROUTE_ID = "GRSSignum-pushBOMs";
	public static final String ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID = "GRSSignum-bomProductsAttachFile";

	public static final String PUSH_BOM_PRODUCTS_ROUTE_PROCESSOR_ID = "GRSSignum-pushBOMsProcessorID";
	public static final String PUSH_PRODUCT_ROUTE_PROCESSOR_ID = "GRSSignum-pushProductProcessorID";
	public static final String ATTACH_FILE_TO_BOM_PRODUCTS_PROCESSOR_ID = "GRSSignum-bomProductsAttachFileProcessorID";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(PUSH_BOM_PRODUCTS_ROUTE_ID))
				.routeId(PUSH_BOM_PRODUCTS_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonBOM.class))
				.process(new PushProductProcessor()).id(PUSH_PRODUCT_ROUTE_PROCESSOR_ID)
				.to(direct(MF_UPSERT_PRODUCT_V2_CAMEL_URI))
				.process(new PushBOMProductsProcessor()).id(PUSH_BOM_PRODUCTS_ROUTE_PROCESSOR_ID)
				.to(direct(MF_UPSERT_BOM_V2_CAMEL_URI))

				.process(this::prepareBOMVerifyRequest)
				.to(direct(MF_VERIFY_BOM_V2_CAMEL_URI))

				.to(direct(ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID));

		from(direct(ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID))
				.routeId(ATTACH_FILE_TO_BOM_PRODUCT_ROUTE_ID)

				.process(RetrieveExternalSystemRequestBuilder::buildAndAttachRetrieveExternalSystemRequest)
				.to("{{" + ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO + "}}")
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonExternalSystemInfo.class))
				.process(this::processExternalSystemInfo)

				.process(new ProductsAttachFileProcessor()).id(ATTACH_FILE_TO_BOM_PRODUCTS_PROCESSOR_ID)
				.choice()
					.when(body().isNull())
						.log("No attachments found!")
					.otherwise()
						.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
				.end();
		//@formatter:on
	}

	private void processExternalSystemInfo(@NonNull final Exchange exchange)
	{
		final PushBOMsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																					 GRSSignumConstants.ROUTE_PROPERTY_PUSH_BOMs_CONTEXT,
																					 PushBOMsRouteContext.class);

		final JsonExternalSystemInfo externalSystemInfo = exchange.getIn().getBody(JsonExternalSystemInfo.class);

		if (externalSystemInfo == null)
		{
			throw new RuntimeException("Missing JsonExternalSystemInfo!");
		}

		context.setExportDirectoriesBasePath(externalSystemInfo.getParameters().get(PARAM_BasePathForExportDirectories));
	}

	private void prepareBOMVerifyRequest(@NonNull final Exchange exchange)
	{
		final PushBOMsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																					 GRSSignumConstants.ROUTE_PROPERTY_PUSH_BOMs_CONTEXT,
																					 PushBOMsRouteContext.class);

		final TokenCredentials credentials = (TokenCredentials)SecurityContextHolder.getContext().getAuthentication().getCredentials();

		final VerifyBOMCamelRequest verifyBOMCamelRequest = VerifyBOMCamelRequest.builder()
				.orgCode(credentials.getOrgCode())
				.productIdentifier(ExternalIdentifierFormat.asExternalIdentifier(context.getJsonBOM().getProductId()))
				.build();

		exchange.getIn().setBody(verifyBOMCamelRequest);
	}
}
