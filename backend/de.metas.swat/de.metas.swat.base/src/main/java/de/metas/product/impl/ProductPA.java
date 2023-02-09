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
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductPA;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.X_M_ProductScalePrice;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MProductPricing;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;
public class ProductPA implements IProductPA
{

	public static final String WHERE_PRODUCT_PRICE = I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "=?";

	public static final String WHERE_PRODUCT_SCALE_PRICE = I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID + "=?";

	private static final Logger logger = LogManager.getLogger(ProductPA.class);

	private final static String SQL_SCALEPRICE_FOR_QTY = //
			" SELECT * "//
					+ " FROM " + I_M_ProductScalePrice.Table_Name //
					+ " WHERE " //
					+ I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID + "=?" //
					+ "    AND " + I_M_ProductScalePrice.COLUMNNAME_Qty + "<=?" //
					+ "    AND " + I_M_ProductScalePrice.COLUMNNAME_IsActive + "='Y'" //
					+ " ORDER BY " + I_M_ProductScalePrice.COLUMNNAME_Qty + " DESC";

	private static final String PREFIX_ERR_MSG_NONEXISTING_PROD = "Param 'productId' must be the of product that exists in the database. Was: ";

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
		final I_M_Product product = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class, ctx, trxName)
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

	@Override
	public Collection<I_M_ProductScalePrice> retrieveScalePrices(final int productPriceId, final String trxName)
	{
		return new Query(Env.getCtx(), I_M_ProductScalePrice.Table_Name, WHERE_PRODUCT_SCALE_PRICE, trxName)
				.setParameters(productPriceId)
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list(I_M_ProductScalePrice.class);
	}

	/**
	 * Invokes {@link X_M_ProductScalePrice#X_M_ProductScalePrice(java.util.Properties, int, String)} .
	 *
	 * @param trxName
	 * @return
	 */
	private I_M_ProductScalePrice createScalePrice(final String trxName)
	{
		return InterfaceWrapperHelper.newInstance(I_M_ProductScalePrice.class, PlainContextAware.newWithTrxName(Env.getCtx(), trxName));
	}

	/**
	 * Returns an existing scale price or (if <code>createNew</code> is true) creates a new one.
	 */
	@Nullable
	@Override
	public I_M_ProductScalePrice retrieveOrCreateScalePrices(
			final int productPriceId, final BigDecimal qty,
			final boolean createNew, final String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(
				SQL_SCALEPRICE_FOR_QTY, trxName);

		ResultSet rs = null;
		try
		{

			pstmt.setInt(1, productPriceId);
			pstmt.setBigDecimal(2, qty);
			pstmt.setMaxRows(1);

			rs = pstmt.executeQuery();

			if (rs.next())
			{

				logger.debug("Returning existing instance for M_ProductPrice " + productPriceId + " and quantity " + qty);
				return new X_M_ProductScalePrice(Env.getCtx(), rs, trxName);
			}
			if (createNew)
			{

				logger.debug("Returning new instance for M_ProductPrice " + productPriceId + " and quantity " + qty);
				final I_M_ProductScalePrice newInstance = createScalePrice(trxName);
				newInstance.setM_ProductPrice_ID(productPriceId);
				newInstance.setQty(qty);
				return newInstance;
			}
			return null;

		}
		catch (final SQLException e)
		{
			throw new DBException(e, SQL_SCALEPRICE_FOR_QTY);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public I_M_ProductScalePrice retrieveScalePrices(final int productPriceId, final BigDecimal qty, final String trxName)
	{
		return retrieveOrCreateScalePrices(productPriceId, qty, false, trxName);
	}
}
