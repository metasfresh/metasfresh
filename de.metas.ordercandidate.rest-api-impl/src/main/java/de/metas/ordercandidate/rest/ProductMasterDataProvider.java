package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.service.OrgId;
import org.adempiere.uom.UomId;
import org.adempiere.uom.api.IUOMDAO;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.compiere.util.Util;

import de.metas.ordercandidate.rest.SyncAdvise.IfExists;
import de.metas.ordercandidate.rest.exceptions.ProductNotFoundException;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

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

public class ProductMasterDataProvider
{
	public static ProductMasterDataProvider of(
			@Nullable final Properties ctx,
			@Nullable final PermissionService permissionService)
	{
		return new ProductMasterDataProvider(
				Util.coalesceSuppliers(
						() -> permissionService,
						() -> PermissionService.of(ctx)));
	}

	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IProductBL productsBL = Services.get(IProductBL.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);

	private final ProductCategoryId defaultProductCategoryId = ProductCategoryId.ofRepoId(1000000); // TODO
	private PermissionService permissionService;

	public ProductMasterDataProvider(@NonNull final PermissionService permissionService)
	{
		this.permissionService = permissionService;
	}

	public ProductId getCreateProductId(
			@NonNull final JsonProductInfo json,
			@NonNull final OrgId orgId)
	{
		final Context context = Context.ofOrg(orgId);
		final ProductId existingProductId = lookupProductIdOrNull(json, context);

		final IfExists ifExists = json.getSyncAdvise().getIfExists();
		if (existingProductId != null && !ifExists.isUpdate())
		{
			return existingProductId;
		}

		final I_M_Product productRecord;
		if (existingProductId != null)
		{
			productRecord = load(existingProductId, I_M_Product.class);
		}
		else
		{
			// if the product doesn't exist and we got here, then ifNotExsits equals "create"
			productRecord = newInstance(I_M_Product.class);
			productRecord.setAD_Org_ID(context.getOrgId().getRepoId());
			productRecord.setValue(json.getCode());
		}

		productRecord.setName(json.getName());
		final String productType;
		switch (json.getType())
		{
			case SERVICE:
				productType = X_M_Product.PRODUCTTYPE_Service;
				break;
			case ITEM:
				productType = X_M_Product.PRODUCTTYPE_Item;
				break;
			default:
				Check.fail("Unexpected type={}; jsonProductInfo={}", json.getType(), json);
				productType = null;
				break;
		}

		productRecord.setM_Product_Category_ID(defaultProductCategoryId.getRepoId());

		productRecord.setProductType(productType);

		final UomId uomId = uomsRepo.getUomIdByX12DE355(json.getUomCode());
		productRecord.setC_UOM_ID(UomId.toRepoId(uomId));

		permissionService.assertCanCreateOrUpdate(productRecord);
		save(productRecord);

		return ProductId.ofRepoId(productRecord.getM_Product_ID());
	}

	private ProductId lookupProductIdOrNull(
			@NonNull final JsonProductInfo json,
			@NonNull final Context context)
	{
		final SyncAdvise syncAdvise = json.getSyncAdvise();

		final ProductId existingProductId;
		if (Check.isEmpty(json.getCode(), true))
		{
			if (syncAdvise.getIfNotExists().isFail())
			{
				final String msg = StringUtils.formatMessage("Found no existing product; Searched via value={} and orgId in ({}, 0)", json.getClass(), context.getOrgId());
				throw new ProductNotFoundException(msg);
			}
			existingProductId = null;
		}
		else
		{
			final boolean outOfTrx = IfExists.UPDATE.equals(syncAdvise.getIfExists()) || syncAdvise.getIfNotExists().isCreate();
			final ProductQuery query = ProductQuery.builder()
					.value(json.getCode())
					.orgId(context.getOrgId())
					.includeAnyOrg(true)
					.outOfTrx(outOfTrx)
					.build();
			existingProductId = productsRepo.retrieveProductIdBy(query);
		}
		return existingProductId;
	}

	public UomId getProductUOMId(
			@NonNull final ProductId productId,
			@Nullable final String uomCode)
	{
		if (!Check.isEmpty(uomCode, true))
		{
			return uomsRepo.getUomIdByX12DE355(uomCode);
		}
		else
		{
			return productsBL.getStockingUOMId(productId);
		}
	}
}
