package de.metas.rest_api.v2.ordercandidates.impl;

import de.metas.cache.CCache;
import de.metas.externalreference.ExternalIdentifier;
import de.metas.externalreference.product.ProductExternalReferenceType;
import de.metas.externalreference.rest.ExternalReferenceRestControllerService;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.security.permissions2.PermissionService;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.exceptions.AdempiereException;
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

final class ProductMasterDataProvider
{
	private final IProductBL productsBL = Services.get(IProductBL.class);

	private final PermissionService permissionService;
	private final ExternalReferenceRestControllerService externalReferenceRestControllerService;

	public ProductMasterDataProvider(@NonNull final PermissionService permissionService, final ExternalReferenceRestControllerService externalReferenceRestControllerService)
	{
		this.permissionService = permissionService;
		this.externalReferenceRestControllerService = externalReferenceRestControllerService;
	}

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

		final ProductId productId;
		switch (productIdentifier.getType())
		{
			case METASFRESH_ID:
				productId = ProductId.ofRepoId(productIdentifier.asMetasfreshId().getValue());
				break;
			case EXTERNAL_REFERENCE:
				productId = externalReferenceRestControllerService
						.getJsonMetasfreshIdFromExternalReference(key.getOrgId(), productIdentifier, ProductExternalReferenceType.PRODUCT)
						.map(jsonProductId -> ProductId.ofRepoId(jsonProductId.getValue()))
						.orElseThrow(() -> new AdempiereException("Missing product for the given product external reference!")
								.appendParametersToMessage()
								.setParameter("external reference", key.getProductExternalIdentifier()));
				break;
			default:
				throw new AdempiereException("Unsupported external reference type!")
						.appendParametersToMessage()
						.setParameter("productIdentifier", productIdentifier);
		}

		final UomId uomId = productsBL.getStockUOMId(productId);

		return ProductInfo.builder()
				.productId(productId)
				.uomId(uomId)
				.build();
	}
}
