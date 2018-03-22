package de.metas.product.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.util.Env;

import de.metas.adempiere.util.CacheCtx;
import de.metas.product.IProductDAO;
import de.metas.product.IProductMappingAware;
import lombok.NonNull;

public class ProductDAO implements IProductDAO
{
	@Override
	@Cached(cacheName = I_M_Product.Table_Name + "#by#" + I_M_Product.COLUMNNAME_UPC)
	public I_M_Product retrieveProductByUPC(@CacheCtx final Properties ctx, final String upc)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product.COLUMNNAME_UPC, upc)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.firstOnly(I_M_Product.class);
	}

	@Override
	public I_M_Product retrieveProductByValue(final Properties ctx, @NonNull final String value)
	{
		final int productId = retrieveProductIdByValue(ctx, value);
		return productId > 0 ? loadOutOfTrx(productId, I_M_Product.class) : null;
	}

	@Override
	public int retrieveProductIdByValue(@NonNull final String value)
	{
		return retrieveProductIdByValue(Env.getCtx(), value);
	}

	@Cached(cacheName = I_M_Product.Table_Name + "#ID#by#" + I_M_Product.COLUMNNAME_Value)
	public int retrieveProductIdByValue(@CacheCtx final Properties ctx, @NonNull final String value)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_Product.COLUMNNAME_Value, value)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx)
				.create()
				.firstIdOnly();
	}

	@Override
	@Cached(cacheName = I_M_Product_Category.Table_Name + "#Default")
	public I_M_Product_Category retrieveDefaultProductCategory(@CacheCtx final Properties ctx)
	{
		final I_M_Product_Category pc = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_Category.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_M_Product_Category.COLUMNNAME_IsDefault, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Product_Category.COLUMNNAME_M_Product_Category_ID)
				.endOrderBy()
				.create()
				.first(I_M_Product_Category.class);
		Check.assumeNotNull(pc, "default product category shall exist");
		return pc;
	}

	@Override
	public I_M_Product retrieveMappedProductOrNull(final I_M_Product product,
			final I_AD_Org org)
	{
		final IProductMappingAware productMappingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(product, IProductMappingAware.class);
		if (productMappingAware.getM_Product_Mapping_ID() <= 0)
		{
			return null;
		}
		if (!productMappingAware.getM_Product_Mapping().isActive())
		{
			return null;
		}

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_Product.class, product)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(IProductMappingAware.COLUMNNAME_M_Product_Mapping_ID, productMappingAware.getM_Product_Mapping_ID())
				.addEqualsFilter(I_M_Product.COLUMN_AD_Org_ID, org.getAD_Org_ID())
				.create()
				.firstOnly(I_M_Product.class);
	}

	@Override
	public List<de.metas.product.model.I_M_Product> retrieveAllMappedProducts(final I_M_Product product)
	{
		final IProductMappingAware productMappingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(product, IProductMappingAware.class);
		if (productMappingAware.getM_Product_Mapping_ID() <= 0)
		{
			return Collections.emptyList();
		}
		if (!productMappingAware.getM_Product_Mapping().isActive())
		{
			return Collections.emptyList();
		}

		return Services.get(IQueryBL.class).createQueryBuilder(de.metas.product.model.I_M_Product.class, product)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(IProductMappingAware.COLUMNNAME_M_Product_Mapping_ID, productMappingAware.getM_Product_Mapping_ID())
				.addNotEqualsFilter(I_M_Product.COLUMNNAME_M_Product_ID, product.getM_Product_ID())
				.create()
				.list(de.metas.product.model.I_M_Product.class);
	}
}
