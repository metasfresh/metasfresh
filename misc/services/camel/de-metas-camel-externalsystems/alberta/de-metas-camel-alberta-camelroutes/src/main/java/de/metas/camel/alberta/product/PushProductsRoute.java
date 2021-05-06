/*
 * #%L
 * de-metas-camel-alberta-camelroutes
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

package de.metas.camel.alberta.product;

import de.metas.camel.alberta.ProcessorHelper;
import de.metas.camel.alberta.product.processor.PrepareAlbertaArticlesProcessor;
import de.metas.camel.alberta.product.processor.PushArticlesProcessor;
import de.metas.camel.alberta.product.processor.RetrieveProductsProcessor;
import de.metas.camel.externalsystems.common.ExternalSystemCamelConstants;
import de.metas.common.externalreference.JsonRequestExternalReferenceUpsert;
import de.metas.common.product.v2.response.JsonGetProductsResponse;
import de.metas.common.product.v2.response.JsonProduct;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.endpoint.StaticEndpointBuilders;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.List;

import static de.metas.camel.alberta.CamelRouteUtil.setupJacksonDataFormatFor;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.HEADER_PINSTANCE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_ERROR_ROUTE_ID;
import static de.metas.camel.externalsystems.common.ExternalSystemCamelConstants.MF_GET_PRODUCTS_ROUTE_ID;

@Component
public class PushProductsRoute extends RouteBuilder
{
	public static final String PUSH_PRODUCTS = "Alberta-pushProducts";
	public static final String PROCESS_PRODUCT_ROUTE_ID = "Alberta-pushProduct";

	public static final String RETRIEVE_PRODUCTS_PROCESSOR_ID = "GetProductsFromMetasfreshProcessor";
	public static final String PREPARE_ARTICLE_PROCESSOR_ID = "PrepareArticleProcessor";
	public static final String PUSH_ARTICLE_PROCESSOR_ID = "PushArticleProcessor";

	@Override
	public void configure()
	{
		errorHandler(defaultErrorHandler());
		onException(Exception.class)
				.to(StaticEndpointBuilders.direct(MF_ERROR_ROUTE_ID));

		//@formatter:off
		// this EP's name is matching the JsonExternalSystemRequest's ExternalSystem and Command
		from(StaticEndpointBuilders.direct(PUSH_PRODUCTS))
				.routeId(PUSH_PRODUCTS)
				.log("Route invoked")
				.streamCaching()
				.process(new RetrieveProductsProcessor()).id(RETRIEVE_PRODUCTS_PROCESSOR_ID)

				.to(StaticEndpointBuilders.direct(MF_GET_PRODUCTS_ROUTE_ID))
				.unmarshal(setupJacksonDataFormatFor(getContext(), JsonGetProductsResponse.class))

				.process(this::processProductsResponseFromMF)
				.choice()
					.when(body().isNull())
						.log(LoggingLevel.INFO, "Nothing to do! No products pulled from MF!")
					.otherwise()
						.split(body())
							.to(StaticEndpointBuilders.direct(PROCESS_PRODUCT_ROUTE_ID))
						.end()
				.endChoice()

				.process((exchange) -> ProcessorHelper.logProcessMessage(exchange, PUSH_PRODUCTS + " route finalized work!" + Instant.now(),
																		 exchange.getIn().getHeader(HEADER_PINSTANCE_ID, Integer.class)));

		from(StaticEndpointBuilders.direct(PROCESS_PRODUCT_ROUTE_ID))
				.routeId(PROCESS_PRODUCT_ROUTE_ID)
				.log("Route invoked")
				.process(new PrepareAlbertaArticlesProcessor()).id(PREPARE_ARTICLE_PROCESSOR_ID)
				.process(new PushArticlesProcessor()).id(PUSH_ARTICLE_PROCESSOR_ID)
				.choice()
					.when(bodyAs(JsonRequestExternalReferenceUpsert.class).isNull())
						.log(LoggingLevel.INFO, "Nothing to do! No JsonRequestExternalReferenceUpsert found!")
					.otherwise()
						.to("{{" + ExternalSystemCamelConstants.MF_UPSERT_EXTERNALREFERENCE_CAMEL_URI + "}}")
				.endChoice();
		//@formatter:on
	}

	private void processProductsResponseFromMF(@NonNull final Exchange exchange)
	{
		final JsonGetProductsResponse response = exchange.getIn().getBody(JsonGetProductsResponse.class);

		final List<JsonProduct> products = (response != null && !CollectionUtils.isEmpty(response.getProducts()))
				? response.getProducts()
				: null;

		exchange.getIn().setBody(products);
	}
}
