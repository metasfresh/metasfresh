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

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT;

public class ProductUpsertProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public ProductUpsertProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportProductsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT, ImportProductsRouteContext.class);

		final JsonProduct product = exchange.getIn().getBody(JsonProduct.class);
		context.setJsonProduct(product);

		final ProductUpsertRequestProducer productUpsertRequestProducer = ProductUpsertRequestProducer.builder()
				.product(product)
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
