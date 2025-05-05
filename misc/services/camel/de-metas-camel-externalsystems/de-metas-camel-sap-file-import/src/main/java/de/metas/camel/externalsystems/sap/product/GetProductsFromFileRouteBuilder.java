/*
 * #%L
 * de-metas-camel-sap
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

package de.metas.camel.externalsystems.sap.product;

import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.mapping.ExternalMappingsHolder;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.sap.config.ProductFileEndpointConfig;
import de.metas.camel.externalsystems.sap.model.product.ProductRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.common.util.Check;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static de.metas.camel.externalsystems.sap.SAPConstants.DEFAULT_PRODUCT_CATEGORY_ID;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetProductsFromFileRouteBuilder extends IdAwareRouteBuilder
{
	@VisibleForTesting
	public static final String UPSERT_PRODUCT_ENDPOINT_ID = "SAP-Products-upsertProductEndpointId";
	private static final String UPSERT_PRODUCT_PROCESSOR_ID = "SAP-Products-upsertProductProcessorId";

	@NonNull
	private final ProductFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final ProcessLogger processLogger;

	@NonNull
	private final ExternalMappingsHolder externalMappingsHolder;

	@Builder
	private GetProductsFromFileRouteBuilder(
			@NonNull final ProductFileEndpointConfig fileEndpointConfig,
			@NonNull final CamelContext camelContext,
			@NonNull final String routeId,
			@NonNull final JsonExternalSystemRequest enabledByExternalSystemRequest,
			@NonNull final ProcessLogger processLogger)
	{
		super(camelContext);
		this.fileEndpointConfig = fileEndpointConfig;
		this.routeId = routeId;
		this.enabledByExternalSystemRequest = enabledByExternalSystemRequest;
		this.processLogger = processLogger;
		this.externalMappingsHolder = ExternalMappingsHolder.of(enabledByExternalSystemRequest.getParameters());
	}

	@Override
	public void configure() throws Exception
	{
		//@formatter:off
		from(fileEndpointConfig.getProductFileEndpoint())
				.id(routeId)
				.log("Product Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.process(this::validateEnableExternalSystemRequest)
				.split(body().tokenize("\n"))
					.streaming()
				    .doTry()
						.unmarshal(new BindyCsvDataFormat(ProductRow.class))
						.process(getProductUpsertProcessor()).id(UPSERT_PRODUCT_PROCESSOR_ID)
						.choice()
							.when(bodyAs(ProductUpsertCamelRequest.class).isNull())
								.log(LoggingLevel.INFO, "Nothing to do! No product to upsert!")
							.otherwise()
								.log(LoggingLevel.DEBUG, "Calling metasfresh-api to upsert Product: ${body}")
								.to(direct(MF_UPSERT_PRODUCT_V2_CAMEL_URI)).id(UPSERT_PRODUCT_ENDPOINT_ID)
							.endChoice()
						.end()
					.endDoTry()
					.doCatch(Throwable.class)
						.to(direct(MF_ERROR_ROUTE_ID))
					.end()
				.end();
		//@formatter:on
	}

	private void validateEnableExternalSystemRequest(@NonNull final Exchange exchange)
	{
		Check.assume(externalMappingsHolder.hasProductTypeMappings(),
					 "Product type mappings cannot be missing! ExternalSystem_Config_Id: " + enabledByExternalSystemRequest.getExternalSystemConfigId() + "!");
	}

	@NonNull
	private ProductUpsertProcessor getProductUpsertProcessor()
	{
		final PInstanceLogger pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.build();

		final String defaultProductCategoryId = getContext().getPropertiesComponent().resolveProperty(DEFAULT_PRODUCT_CATEGORY_ID)
				.orElseThrow(() -> new RuntimeCamelException("Missing mandatory property: " + DEFAULT_PRODUCT_CATEGORY_ID));

		return new ProductUpsertProcessor(enabledByExternalSystemRequest,
										  pInstanceLogger,
										  externalMappingsHolder,
										  JsonMetasfreshId.of(defaultProductCategoryId));
	}
}
