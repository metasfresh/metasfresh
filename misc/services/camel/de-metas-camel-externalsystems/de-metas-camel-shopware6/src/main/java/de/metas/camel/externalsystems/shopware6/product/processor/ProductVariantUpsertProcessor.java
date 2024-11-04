/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.product.processor;

import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.common.ProcessorHelper;
import de.metas.camel.externalsystems.common.v2.ProductUpsertCamelRequest;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.camel.externalsystems.shopware6.product.ImportProductsRouteContext;
import de.metas.camel.externalsystems.shopware6.product.ProductUpsertRequestProducer;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT;

public class ProductVariantUpsertProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public ProductVariantUpsertProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportProductsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT, ImportProductsRouteContext.class);

		final JsonProduct productParent = context.getParentJsonProduct();
		final JsonProduct product = context.getJsonProduct();

		if (productParent == null)
		{
			exchange.getIn().setBody(null);
			return;
		}

		// merge missing details to variant.
		final JsonProduct mergedProduct = new JsonProduct(
				product.getId(),
				product.getParentId(),
				Optional.ofNullable(product.getName()).orElse(productParent.getName()),
				product.getProductNumber(),
				Optional.ofNullable(product.getEan()).orElse(productParent.getEan()),
				Optional.ofNullable(product.getUnitId()).orElse(productParent.getUnitId()),
				Optional.ofNullable(product.getJsonTax()).orElse(productParent.getJsonTax()),
				product.getCreatedAt(),
				product.getUpdatedAt(),
				Optional.ofNullable(product.getPrices()).orElse(productParent.getPrices())
		);

		context.setJsonProduct(mergedProduct);

		//currently, this code is duplicated -> refactor, if solution is permanent or replace with variant specific upsert.
		final ProductUpsertRequestProducer productUpsertRequestProducer = ProductUpsertRequestProducer.builder()
				.product(mergedProduct)
				.routeContext(context)
				.processLogger(processLogger)
				.build();

		final JsonRequestProductUpsert productRequestProducerResult = productUpsertRequestProducer.run();

		if (productRequestProducerResult.getRequestItems().isEmpty())
		{
			exchange.getIn().setBody(null);
			return;
		}

		final ProductUpsertCamelRequest productUpsertCamelRequest = ProductUpsertCamelRequest.builder()
				.jsonRequestProductUpsert(productRequestProducerResult)
				.orgCode(context.getOrgCode())
				.build();

		exchange.getIn().setBody(productUpsertCamelRequest);
	}

}
