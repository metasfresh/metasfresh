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

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.common.ProcessLogger;
import de.metas.camel.externalsystems.shopware6.api.model.product.JsonProduct;
import de.metas.common.externalsystem.JsonUOM;
import de.metas.common.product.v2.request.JsonRequestProduct;
import de.metas.common.product.v2.request.JsonRequestProductUpsert;
import de.metas.common.product.v2.request.JsonRequestProductUpsertItem;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.DEFAULT_PRODUCT_UOM;
import static de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat.formatExternalId;

@Value
public class ProductUpsertRequestProducer
{
	@NonNull
	JsonProduct product;

	@NonNull
	ImportProductsRouteContext routeContext;

	@NonNull
	ProcessLogger processLogger;

	@Builder
	public ProductUpsertRequestProducer(
			@NonNull final JsonProduct product,
			@NonNull final ImportProductsRouteContext routeContext,
			@NonNull final ProcessLogger processLogger)
	{
		this.product = product;
		this.routeContext = routeContext;
		this.processLogger = processLogger;
	}

	@NonNull
	public JsonRequestProductUpsert run()
	{
		return JsonRequestProductUpsert.builder()
				.syncAdvise(SyncAdvise.CREATE_OR_MERGE)
				.requestItems(getProductItems())
				.build();
	}

	@NonNull
	private List<JsonRequestProductUpsertItem> getProductItems()
	{
		return mapProductToProductRequestItem()
				.map(ImmutableList::of)
				.orElseGet(ImmutableList::of);
	}

	@NonNull
	private Optional<JsonRequestProductUpsertItem> mapProductToProductRequestItem()
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
		jsonRequestProduct.setUomCode(getUOMCode(product.getUnitId()));

		return Optional.of(JsonRequestProductUpsertItem.builder()
								   .productIdentifier(productIdentifier)
								   .requestProduct(jsonRequestProduct)
								   .build());
	}

	@NonNull
	private String getUOMCode(@Nullable final String unitId)
	{
		if (unitId == null)
		{
			processLogger.logMessage("Shopware unit id is null, fallback to default UOM: PCE ! ProductId: " + product.getId(),
									 routeContext.getPInstanceId());
			return DEFAULT_PRODUCT_UOM;
		}

		if (routeContext.getUomMappings().isEmpty())
		{
			processLogger.logMessage("No UOM mappings found in the route context, fallback to default UOM: PCE ! ProductId: " + product.getId(),
									 routeContext.getPInstanceId());
			return DEFAULT_PRODUCT_UOM;
		}

		final String externalCode = routeContext.getShopwareUomInfoProvider().getCode(unitId);
		if (externalCode == null)
		{
			processLogger.logMessage("No externalCode found for unit id: " + unitId + ", fallback to default UOM: PCE ! ProductId: " + product.getId(),
									 routeContext.getPInstanceId());
			return DEFAULT_PRODUCT_UOM;
		}

		final JsonUOM jsonUOM = routeContext.getUomMappings().get(externalCode);
		if (jsonUOM == null)
		{
			processLogger.logMessage("No UOM mapping found for externalCode: " + externalCode + " ! fallback to default UOM: PCE ! ProductId: " + product.getId(),
									 routeContext.getPInstanceId());
			return DEFAULT_PRODUCT_UOM;
		}
		return jsonUOM.getCode();
	}
}
