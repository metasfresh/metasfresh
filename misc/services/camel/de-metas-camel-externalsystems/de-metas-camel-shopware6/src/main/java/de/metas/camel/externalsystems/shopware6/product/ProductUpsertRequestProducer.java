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

package de.metas.camel.externalsystems.shopware6.product;

import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DEFAULT_PRODUCT_UOM;
import static de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat.formatExternalId;

@Value
public class ProductUpsertRequestProducer
{
	@NonNull
	List<JsonProduct> products;

	@NonNull
	ImportProductsRouteContext routeContext;

	@Builder
	public ProductUpsertRequestProducer(
			@NonNull final List<JsonProduct> products,
			@NonNull final ImportProductsRouteContext routeContext)
	{
		this.products = products;
		this.routeContext = routeContext;
	}

	@NonNull
	public Optional<JsonRequestProductUpsert> run()
	{
		if (products.isEmpty())
		{
			return Optional.empty();
		}

		final JsonRequestProductUpsert jsonRequestProductUpsert = JsonRequestProductUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(getProductItems())
				.build();

		return Optional.of(jsonRequestProductUpsert);
	}

	@NonNull
	private List<JsonRequestProductUpsertItem> getProductItems()
	{
		return products.stream()
				.map(this::mapProductToProductRequestItem)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	@NonNull
	private Optional<JsonRequestProductUpsertItem> mapProductToProductRequestItem(@NonNull final JsonProduct product)
	{
		if (product.getName() == null)
		{
			return Optional.empty();
		}

		routeContext.setNextImportStartingTimestamp(product.getUpdatedAtNonNull().toInstant());

		final String productIdentifier = formatExternalId(product.getId());

		final JsonRequestProduct jsonRequestProduct = new JsonRequestProduct();

		jsonRequestProduct.setCode(product.getProductNumber());
		jsonRequestProduct.setEan(product.getEan());
		jsonRequestProduct.setName(product.getName());
		jsonRequestProduct.setType(JsonRequestProduct.Type.ITEM);
		jsonRequestProduct.setUomCode(DEFAULT_PRODUCT_UOM);

		return Optional.of(JsonRequestProductUpsertItem.builder()
								   .productIdentifier(productIdentifier)
								   .requestProduct(jsonRequestProduct)
								   .build());
	}
}
