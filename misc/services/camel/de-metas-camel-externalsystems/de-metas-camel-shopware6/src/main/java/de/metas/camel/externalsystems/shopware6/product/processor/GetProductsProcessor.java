/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.product.processor;

import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.ProcessorHelper;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.JsonQuery;
import de.metas.camel.externalsystems.shopware6.api.model.MultiJsonFilter;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.QueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProducts;
import de.metas.camel.externalsystems.shopware6.product.ImportProductsRouteContext;
import de.metas.camel.externalsystems.shopware6.product.ProductUpsertRequestProducer;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_CREATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.FIELD_UPDATED_AT;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.PARAMETERS_DATE_GTE;
import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT;

public class GetProductsProcessor implements Processor
{
	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportProductsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT, ImportProductsRouteContext.class);

		final JsonExternalSystemRequest externalSystemRequest = context.getExternalSystemRequest();

		final ShopwareClient shopwareClient = context.getShopwareClient();

		final MultiQueryRequest getProductsRequest = buildQueryProductsRequest(context.getNextImportStartingTimestamp());

		final Optional<JsonProducts> jsonProductsOptional = shopwareClient.getProducts(getProductsRequest);

		if (jsonProductsOptional.isEmpty())
		{
			exchange.getIn().setBody(null);
			return;
		}

		final List<JsonProduct> products = jsonProductsOptional.get().getProductList();

		final ProductUpsertRequestProducer productUpsertRequestProducer = ProductUpsertRequestProducer.builder()
				.products(products)
				.routeContext(context)
				.build();

		final Optional<JsonRequestProductUpsert> productRequestProducerResult = productUpsertRequestProducer.run();

		final ProductUpsertCamelRequest productUpsertCamelRequest = productRequestProducerResult.map(result -> ProductUpsertCamelRequest.builder()
				.jsonRequestProductUpsert(productRequestProducerResult.get())
				.orgCode(externalSystemRequest.getOrgCode())
				.build())
				.orElse(null);

		exchange.getIn().setBody(productUpsertCamelRequest);
	}

	@NonNull
	private MultiQueryRequest buildQueryProductsRequest(@NonNull final Instant updatedAfter)
	{
		final HashMap<String, String> parameters = new HashMap<>();
		parameters.put(PARAMETERS_DATE_GTE, updatedAfter.toString());

		return MultiQueryRequest.builder()
				.filter(MultiJsonFilter.builder()
								.operatorType(MultiJsonFilter.OperatorType.OR)
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_UPDATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.jsonQuery(JsonQuery.builder()
												   .field(FIELD_CREATED_AT)
												   .queryType(JsonQuery.QueryType.RANGE)
												   .parameters(parameters)
												   .build())
								.build())
				.build();
	}
}