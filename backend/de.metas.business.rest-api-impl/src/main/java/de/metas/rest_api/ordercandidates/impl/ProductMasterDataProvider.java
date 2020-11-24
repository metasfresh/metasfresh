package de.metas.rest_api.ordercandidates.impl;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import de.metas.rest_api.common.SyncAdvise;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;

import de.metas.cache.CCache;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.rest_api.ordercandidates.request.JsonProductInfo;
import de.metas.security.PermissionService;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Wither;

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
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000); // FIXME HARDCODED
	private PermissionService permissionService;

	public ProductMasterDataProvider(@NonNull final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	@Value
	private static class ProductCacheKey
	{
		@NonNull
		OrgId orgId;

		@NonNull
		JsonProductInfo jsonProductInfo;
	}

	@Value
	@Builder
	public static class ProductInfo
	{
		@NonNull
		ProductId productId;

		@NonNull
		UomId uomId;

		@Wither
		boolean justCreated;
	}

	private final CCache<ProductCacheKey, ProductInfo> productInfoCache = CCache
			.<ProductCacheKey, ProductInfo> builder()
			.cacheName(this.getClass().getSimpleName() + "-productInfoCache")
			.tableName(I_M_Product.Table_Name)
			.build();

	public ProductInfo getCreateProductInfo(
			@NonNull final JsonProductInfo jsonProductInfo,
			@NonNull final OrgId orgId)
	{
		final AtomicBoolean justCreated = new AtomicBoolean(false);
		final ProductInfo productInfo = productInfoCache.getOrLoad(
				new ProductCacheKey(orgId, jsonProductInfo),
				key -> getCreateProductInfo0(key, justCreated));

		return productInfo.withJustCreated(justCreated.get());
	}

	private ProductInfo getCreateProductInfo0(
			@NonNull final ProductCacheKey key,
			@NonNull final AtomicBoolean justCreated)
	{
		final JsonProductInfo jsonProductInfo = key.getJsonProductInfo();
		final OrgId orgId = key.getOrgId();

		final ProductId existingProductId = lookupProductIdOrNull(jsonProductInfo, orgId);

		final SyncAdvise.IfExists ifExists = jsonProductInfo.getSyncAdvise().getIfExists();
		if (existingProductId != null && !ifExists.isUpdate())
		{
			final UomId uomId = getProductUOMId(existingProductId, jsonProductInfo.getUomCode());
			return ProductInfo.builder()
					.productId(existingProductId)
					.uomId(uomId)
					.build();
		}

		final I_M_Product productRecord;
		final boolean newProduct;
		if (existingProductId != null)
		{
			newProduct = false;
			productRecord = load(existingProductId, I_M_Product.class);
		}
		else
		{
			// if the product doesn't exist and we got here, then ifNotExsits equals "create"
			newProduct = true;
			productRecord = newInstance(I_M_Product.class);
			productRecord.setAD_Org_ID(orgId.getRepoId());
			productRecord.setValue(jsonProductInfo.getCode());
		}

		productRecord.setName(jsonProductInfo.getName());
		final String productType;
		switch (jsonProductInfo.getType())
		{
			case SERVICE:
				productType = X_M_Product.PRODUCTTYPE_Service;
				break;
			case ITEM:
				productType = X_M_Product.PRODUCTTYPE_Item;
				break;
			default:
				Check.fail("Unexpected type={}; jsonProductInfo={}", jsonProductInfo.getType(), jsonProductInfo);
				productType = null;
				break;
		}
		productRecord.setProductType(productType);

		productRecord.setM_Product_Category_ID(defaultProductCategoryId.getRepoId());

		final UomId uomId = uomDAO.getUomIdByX12DE355(jsonProductInfo.getUomCode());
		productRecord.setC_UOM_ID(UomId.toRepoId(uomId));

		permissionService.assertCanCreateOrUpdate(productRecord);
		saveRecord(productRecord);
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());

		if (newProduct)
		{
			justCreated.set(true);
		}

		return ProductInfo.builder()
				.productId(productId)
				.uomId(uomId)
				.build();
	}

	private ProductId lookupProductIdOrNull(
			@NonNull final JsonProductInfo json,
			@NonNull final OrgId orgId)
	{
		final String productValue = json.getCode();
		final ExternalId productExternalId = json.getExternalId();
		final SyncAdvise syncAdvise = json.getSyncAdvise();

		final ProductId existingProductId;
		if (Check.isEmpty(productValue, true) && productExternalId == null)
		{
			existingProductId = null;
		}
		else
		{
			final ProductQuery query = ProductQuery.builder()
					.value(productValue)
					.externalId(productExternalId)
					.orgId(orgId)
					.includeAnyOrg(true)
					.outOfTrx(syncAdvise.isLoadReadOnly())
					.build();
			existingProductId = productDAO.retrieveProductIdBy(query);
		}

		if (existingProductId == null && syncAdvise.getIfNotExists().isFail())
		{
			final String msg = StringUtils.formatMessage("Found no existing product with orgId in ({}, 0); Search parameters - used if not null or empty: value={}; externalId={}",
					OrgId.toRepoIdOrAny(orgId), productValue, productExternalId);
			throw new ProductNotFoundException(msg);
		}

		return existingProductId;
	}

	private UomId getProductUOMId(
			@Nullable final ProductId productId,
			@Nullable final String uomCode)
	{
		if (!Check.isEmpty(uomCode, true))
		{
			return uomDAO.getUomIdByX12DE355(uomCode);
		}
		else
		{
			return productsBL.getStockUOMId(productId);
		}
	}
}
