package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.cache.CCache;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.rest_api.v2.product.ExternalIdentifierProductLookupService;
import de.metas.rest_api.v2.product.ProductAndHUPIItemProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.web.exception.MissingResourceException;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.With;
import org.compiere.model.I_M_Product;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

@RequiredArgsConstructor
public final class ProductMasterDataProvider
{
	private final IProductBL productsBL = Services.get(IProductBL.class);

	@NonNull private final ExternalIdentifierProductLookupService productLookupService;

	@Value
	private static class ProductCacheKey
	{
		@NonNull
		OrgId orgId;

		@NonNull
		ExternalIdentifier productExternalIdentifier;
	}

	@Value
	@Builder
	public static class ProductInfo
	{
		@NonNull
		ProductId productId;

		@NonNull
		HUPIItemProductId hupiItemProductId;

		@NonNull
		UomId uomId;

		@With
		boolean justCreated;
	}

	private final CCache<ProductCacheKey, ProductInfo> productInfoCache = CCache
			.<ProductCacheKey, ProductInfo>builder()
			.cacheName(this.getClass().getSimpleName() + "-productInfoCache")
			.tableName(I_M_Product.Table_Name)
			.build();

	public ProductInfo getProductInfo(
			@NonNull final ExternalIdentifier productExternalIdentifier,
			@NonNull final OrgId orgId)
	{
		return productInfoCache.getOrLoad(
				new ProductCacheKey(orgId, productExternalIdentifier),
				this::getProductInfo0);

	}

	private ProductInfo getProductInfo0(@NonNull final ProductCacheKey key)
	{
		final ExternalIdentifier productIdentifier = key.getProductExternalIdentifier();
		
		final ProductAndHUPIItemProductId productAndHUPIItemProductId = productLookupService
				.resolveProductExternalIdentifier(productIdentifier, key.getOrgId())
				.orElseThrow(() -> MissingResourceException.builder()
						.resourceName("productIdentifier")
						.resourceIdentifier(productIdentifier.getRawValue())
						.build());

		final UomId uomId = productsBL.getStockUOMId(productAndHUPIItemProductId.getProductId());

		return ProductInfo.builder()
				.productId(productAndHUPIItemProductId.getProductId())
				.hupiItemProductId(productAndHUPIItemProductId.getHupiItemProductId())
				.uomId(uomId)
				.build();
	}
}
