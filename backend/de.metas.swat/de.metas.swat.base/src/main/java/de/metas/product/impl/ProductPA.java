package de.metas.product.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheIgnore;
import de.metas.cache.annotation.CacheTrx;
import de.metas.location.CountryId;
import de.metas.organization.OrgId;
import de.metas.product.IProductPA;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.MProductPricing;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Properties;

public class ProductPA implements IProductPA
{
	final private IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_M_Product retrieveProduct(
			final Properties ctx,
			final String value,
			final boolean throwEx,
			final String trxName)
	{
		if (value == null)
		{
			throw new IllegalArgumentException("Param 'value' may not be null");
		}

		return retrieveProduct(ctx, I_M_Product.COLUMNNAME_Value, value, throwEx, trxName);
	}

	@Override
	public I_M_Product retrieveProduct(
			final Properties ctx,
			int productId,
			boolean throwEx,
			String trxName)
	{
		return retrieveProduct(ctx, I_M_Product.COLUMNNAME_M_Product_ID, productId, throwEx, trxName);
	}

	@Cached(cacheName = I_M_Product.Table_Name + "#By#ColumnName")
		/* package */I_M_Product retrieveProduct(
			final @CacheCtx Properties ctx,
			final String colName,
			final @CacheAllowMutable Object param,
			final @CacheIgnore boolean throwEx,
			final @CacheTrx String trxName)
	{
		final I_M_Product product = queryBL.createQueryBuilder(I_M_Product.class, ctx, trxName)
				.addEqualsFilter(colName, param)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_Product.class);

		if (product == null && throwEx)
		{
			throw new IllegalArgumentException("There is no product with '" + colName + "=" + param + "'");
		}
		return product;
	}

	@Override
	public BigDecimal retrievePriceStd(
			final @NonNull OrgId orgId,
			final int productId,
			final int partnerId,
			final int priceListId,
			@Nullable final CountryId countryId,
			final BigDecimal qty,
			final boolean soTrx)
	{
		final MProductPricing pricing = new MProductPricing(
				orgId,
				productId,
				partnerId,
				countryId,
				qty,
				soTrx);
		pricing.setM_PriceList_ID(priceListId);

		final BigDecimal priceStd = pricing.getPriceStd();
		if (priceStd.signum() == 0)
		{
			pricing.throwProductNotOnPriceListException();
		}
		return priceStd;
	}
}
