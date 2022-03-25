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

package de.metas.camel.externalsystems.grssignum.from_grs.product;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.CamelRouteUtil;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.grssignum.from_grs.RetrieveExternalSystemRequestBuilder;
import de.metas.camel.externalsystems.grssignum.from_grs.product.processor.PushRawMaterialsProcessor;
import de.metas.camel.externalsystems.grssignum.from_grs.product.processor.RawMaterialAttachFileProcessor;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBPartnerProduct;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.externalsystem.JsonExternalSystemInfo;
import de.metas.common.util.Check;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.common.RouteBuilderHelper.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT;
import static de.metas.common.externalsystem.parameters.GRSSignumParameters.PARAM_BasePathForExportDirectories;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

@Component
public class PushRawMaterialsRouteBuilder extends RouteBuilder
{
	public static final String PUSH_RAW_MATERIALS_ROUTE_ID = "GRSSignum-pushRawMaterials";
	public static final String ATTACH_FILE_TO_RAW_MATERIALS_ROUTE_ID = "GRSSignum-rawMaterialsAttachFile";

	public static final String PUSH_RAW_MATERIALS_PROCESSOR_ID = "GRSSignum-pushRawMaterialsProcessorID";
	public static final String ATTACH_FILE_TO_RAW_MATERIALS_PROCESSOR_ID = "GRSSignum-rawMaterialsAttachFileProcessorID";

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(direct(MF_ERROR_ROUTE_ID));

		from(direct(PUSH_RAW_MATERIALS_ROUTE_ID))
				.routeId(PUSH_RAW_MATERIALS_ROUTE_ID)
				.log("Route invoked!")
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonProduct.class))
				.process(this::buildAndAttachContext)

				.process(new PushRawMaterialsProcessor()).id(PUSH_RAW_MATERIALS_PROCESSOR_ID)
				.to(direct(MF_UPSERT_PRODUCT_V2_CAMEL_URI))

				.to(direct(ATTACH_FILE_TO_RAW_MATERIALS_ROUTE_ID));

		from(direct(ATTACH_FILE_TO_RAW_MATERIALS_ROUTE_ID))
				.routeId(ATTACH_FILE_TO_RAW_MATERIALS_ROUTE_ID)

				.process(RetrieveExternalSystemRequestBuilder::buildAndAttachRetrieveExternalSystemRequest)
				.to("{{" + ExternalSystemCamelConstants.MF_GET_EXTERNAL_SYSTEM_INFO + "}}")
				.unmarshal(CamelRouteUtil.setupJacksonDataFormatFor(getContext(), JsonExternalSystemInfo.class))
				.process(this::processExternalSystemInfo)

				.choice()
					.when(isAttachFilesEnabled())
						.process(this::prepareBPartnerProducts)
						.split(body())
							.process(new RawMaterialAttachFileProcessor()).id(ATTACH_FILE_TO_RAW_MATERIALS_PROCESSOR_ID)
							.to(direct(ExternalSystemCamelConstants.MF_ATTACHMENT_ROUTE_ID))
						.end()
				.end();
		//@formatter:on
	}

	private void buildAndAttachContext(@NonNull final Exchange exchange)
	{
		final JsonProduct jsonProduct = exchange.getIn().getBody(JsonProduct.class);

		final PushRawMaterialsRouteContext routeContext = PushRawMaterialsRouteContext.builder()
				.jsonProduct(jsonProduct)
				.build();

		exchange.setProperty(ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT, routeContext);
	}

	private void processExternalSystemInfo(@NonNull final Exchange exchange)
	{
		final JsonExternalSystemInfo externalSystemInfo = exchange.getIn().getBody(JsonExternalSystemInfo.class);

		if (externalSystemInfo == null)
		{
			throw new RuntimeException("Missing JsonExternalSystemInfo!");
		}

		final String basePathForExportDirectories = externalSystemInfo.getParameters().get(PARAM_BasePathForExportDirectories);

		final PushRawMaterialsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																							 ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT,
																							 PushRawMaterialsRouteContext.class);
		context.setBasePathForExportDirectories(basePathForExportDirectories);
	}

	@NonNull
	private Predicate isAttachFilesEnabled()
	{
		return exchange -> {
			final PushRawMaterialsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																								 ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT,
																								 PushRawMaterialsRouteContext.class);

			return context.getBasePathForExportDirectories() != null;
		};
	}

	private void prepareBPartnerProducts(@NonNull final Exchange exchange)
	{
		final PushRawMaterialsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange,
																							 ROUTE_PROPERTY_PUSH_RAW_MATERIALS_CONTEXT,
																							 PushRawMaterialsRouteContext.class);

		if (Check.isEmpty(context.getJsonProduct().getBPartnerProducts()))
		{
			exchange.getIn().setBody(ImmutableList.of());
			return;
		}

		final ImmutableList<JsonBPartnerProduct> bPartnerProducts = context.getJsonProduct().getBPartnerProducts()
				.stream()
				.filter(jsonBPartnerProduct -> Check.isNotBlank(jsonBPartnerProduct.getAttachmentFilePath()))
				.collect(ImmutableList.toImmutableList());

		exchange.getIn().setBody(bPartnerProducts);
	}
}
