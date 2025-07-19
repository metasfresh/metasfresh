/*
 * #%L
 * de-metas-camel-pcm-file-import
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

package de.metas.camel.externalsystems.pcm.product;

import de.metas.camel.externalsystems.common.IdAwareRouteBuilder;
import de.metas.camel.externalsystems.common.PInstanceLogger;
import de.metas.camel.externalsystems.common.PInstanceUtil;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.pcm.SkipFirstLinePredicate;
import de.metas.camel.externalsystems.pcm.config.ProductFileEndpointConfig;
import de.metas.camel.externalsystems.pcm.product.model.ProductRow;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;

import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.ERROR_WRITE_TO_ADISSUE;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_UPSERT_PRODUCT_V2_CAMEL_URI;
import static org.apache.camel.builder.endpoint.StaticEndpointBuilders.direct;

public class GetProductFromFileRouteBuilder extends IdAwareRouteBuilder
{
	public static final String UPSERT_PRODUCT_PROCESSOR_ID = "GetProductFromFileRouteBuilder.UPSERT_PRODUCT_PROCESSOR_ID";
	public static final String UPSERT_PRODUCT_ENDPOINT_ID = "GetProductFromFileRouteBuilder.UPSERT_PRODUCT_ENDPOINT_ID";

	@NonNull
	private final ProductFileEndpointConfig fileEndpointConfig;

	@Getter
	@NonNull
	private final String routeId;

	@NonNull
	private final JsonExternalSystemRequest enabledByExternalSystemRequest;

	@NonNull
	private final PInstanceLogger pInstanceLogger;

	@Builder
	private GetProductFromFileRouteBuilder(
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
		this.pInstanceLogger = PInstanceLogger.builder()
				.processLogger(processLogger)
				.pInstanceId(enabledByExternalSystemRequest.getAdPInstanceId())
				.build();
	}

	@Override
	public void configure()
	{
		//@formatter:off
		from(fileEndpointConfig.getProductFileEndpoint())
				.id(routeId)
				.streamCache("true")
				.log("Product Sync Route Started with Id=" + routeId)
				.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				.split(body().tokenize("\n"))
					.streaming()
					.process(exchange -> PInstanceUtil.setPInstanceHeader(exchange, enabledByExternalSystemRequest))
				    .filter(new SkipFirstLinePredicate())
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
						.to(direct(ERROR_WRITE_TO_ADISSUE))
					.end()
				.end();
		//@formatter:on
	}

	@NonNull
	private ProductUpsertProcessor getProductUpsertProcessor()
	{
		return ProductUpsertProcessor.builder()
				.externalSystemRequest(enabledByExternalSystemRequest)
				.pInstanceLogger(pInstanceLogger)
				.build();
	}
}
