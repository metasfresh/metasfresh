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
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.MultiQueryRequest;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProducts;
import de.metas.camel.externalsystems.shopware6.product.ImportProductsRouteContext;
import lombok.NonNull;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT;
import static de.metas.camel.externalsystems.shopware6.api.model.QueryHelper.buildQueryParentProductRequest;

public class GetProductVariantParentProcessor implements Processor
{
	private final ProcessLogger processLogger;

	public GetProductVariantParentProcessor(@NonNull final ProcessLogger processLogger)
	{
		this.processLogger = processLogger;
	}

	@Override
	public void process(final Exchange exchange) throws Exception
	{
		final ImportProductsRouteContext context = ProcessorHelper.getPropertyOrThrowError(exchange, ROUTE_PROPERTY_IMPORT_PRODUCTS_CONTEXT, ImportProductsRouteContext.class);

		final JsonProduct product = exchange.getIn().getBody(JsonProduct.class);
		context.setJsonProduct(product);

		final ShopwareClient shopwareClient = context.getShopwareClient();

		if (product.getParentId() != null)
		{
			processLogger.logMessage("Getting variant parent", context.getPInstanceId());

			//TODO - maybe add getSingleProduct to shopware client.
			final MultiQueryRequest getProductsRequest = buildQueryParentProductRequest(product.getParentId());
			final Optional<JsonProducts> jsonProductsOptional = shopwareClient.getProducts(getProductsRequest);

			if (jsonProductsOptional.isEmpty())
			{
				exchange.getIn().setBody(null);
				return;
			}

			final JsonProduct parentProduct = jsonProductsOptional.get().getProductList().get(0);
			context.setParentJsonProduct(parentProduct);
		}
		else
		{
			exchange.getIn().setBody(null);
			return;
		}

	}

}
