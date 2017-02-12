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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.ProductNotOnPriceListException;
import org.adempiere.model.I_M_ProductScalePrice;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.model.X_M_ProductScalePrice;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Product_PO;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.I_M_Requisition;
import org.compiere.model.I_M_RequisitionLine;
import org.compiere.model.MAttributeSet;
import org.compiere.model.MAttributeSetInstance;
import org.compiere.model.MPriceList;
import org.compiere.model.MPriceListVersion;
import org.compiere.model.MPricingSystem;
import org.compiere.model.MProduct;
import org.compiere.model.MProductPO;
import org.compiere.model.MProductPrice;
import org.compiere.model.MProductPricing;
import org.compiere.model.MRequisition;
import org.compiere.model.MRequisitionLine;
import org.compiere.model.MUOM;
import org.compiere.model.Query;
import org.compiere.model.X_M_Replenish;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheIgnore;
import de.metas.adempiere.util.CacheTrx;
import de.metas.adempiere.util.cache.annotations.CacheAllowMutable;
import de.metas.logging.LogManager;
import de.metas.product.IProductBL;
import de.metas.product.IProductPA;

public class ProductPA implements IProductPA
{

	public static final String WHERE_PRODUCT_PRICE =
			I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "=?";

	public static final String WHERE_PRODUCT_SCALE_PRICE =
			I_M_ProductScalePrice.COLUMNNAME_M_ProductPrice_ID + "=?";

	private static final Logger logger = LogManager.getLogger(ProductPA.class);

	public static final String SQL_PRODUCT =
			"SELECT * FROM "
					+ I_M_Product.Table_Name + " WHERE " + I_M_Product.COLUMNNAME_Value + "=?";

	private static final String SQL_SELECT_ASI =
			"SELECT asi.* "
					+ " FROM " + I_M_AttributeSetInstance.Table_Name + " asi"
					+ " WHERE "//
					+ "    asi." + I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSet_ID + "=?"
					+ "    AND asi." + I_M_AttributeSetInstance.COLUMNNAME_SerNo + "=? ";

	public static final String SQL_PRODUCT_PO = //
	"SELECT * FROM "
			+ I_M_Product_PO.Table_Name //
			+ " WHERE " //
			+ I_M_Product_PO.COLUMNNAME_M_Product_ID + "=? AND "
			+ I_M_Product_PO.COLUMNNAME_C_BPartner_ID + "=?";

	public static final String SQL_REQUISITION_LINE = //
			"SELECT rl.* " //
					+ " FROM M_RequisitionLine rl LEFT JOIN M_Requisition r ON rl.M_Requisition_ID=r.M_Requisition_ID "
					+ " WHERE " //
					+ "    r.DocStatus='CO' " //
					+ "    AND rl.M_Product_ID=? and r.M_WareHouse_ID=?";

	public static final String SQL_REQUISITION_LINES = //
			"SELECT rl.* " //
					+ " FROM M_RequisitionLine rl LEFT JOIN M_Requisition r ON rl.M_Requisition_ID=r.M_Requisition_ID "
					+ " WHERE " //
					+ "    r.DocumentNo=? ";

	public static final String SQL_REQUISITIONS = //
			"SELECT * " //
					+ " FROM M_Requisition " //
					+ " WHERE DocStatus=? ";

	public static final String SQL_REPLENISH = //
			"SELECT * " //
					+ " FROM M_Replenish " //
					+ " WHERE M_Product_ID=? AND M_Warehouse_ID=?";

	/**
	 * Selects price lists that have the same M_PricingSystem_ID as a given PL and have a certain C_Country_ID.
	 */
	public static final String SQL_PRICELIST_BY_SIBLING = //
			"SELECT p.* " //
					+ " FROM M_PriceList p " //
					+ " LEFT JOIN C_Location l " //
					+ "    ON l.C_Country_ID=p.C_Country_ID" //
					+ " WHERE (l.C_Location_ID=? OR l.C_Location_ID=0 OR l.C_Location_ID IS NULL)" //
					+ "    AND EXISTS (" //
					+ "          SELECT * FROM M_PriceList p2 " //
					+ "          WHERE p2.M_PricingSystem_ID=p.M_PricingSystem_ID AND p2.M_PriceList_ID=? AND p2.IsSOPriceList=p.IsSOPriceList" //
					+ "    ) " //
					+ "    AND p.IsActive='Y' AND l.IsActive='Y'"
					+ " ORDER BY coalesce(l.C_Location_ID, 0) DESC";

	public static final String SQL_PRICELIST_BY_RPICINGSYSTEM = //
			"SELECT p.* " //
					+ " FROM M_PriceList p " //
					+ " LEFT JOIN C_Location l " //
					+ "    ON l.C_Country_ID=p.C_Country_ID " //
					+ " WHERE " //
					+ "    p.M_PricingSystem_ID=? " //
					+ "    AND p.IsSOPriceList=? " //
					+ "    AND p.IsActive='Y' " //
					+ "    AND ( " //
					+ "          l.C_Location_ID=? " //
					+ "          OR l.C_Location_ID=0 OR l.C_Location_ID IS NULL " //
					+ "    )" //
					+ " ORDER BY coalesce(l.C_Location_ID, 0) DESC";;

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

	/**
	 * returns an attribute instance that has a certain text and belongs to a certain product.
	 * 
	 * @return the retrieved attribute set instance(s) or an empty collection if no asi for the given product with the given text yet.
	 * 
	 * @throws IllegalArgumentException if <li>Parameter <code>text</code> is null</li> <li>there is no product with the given <code>productId</code></li>
	 */
	@Override
	public Collection<MAttributeSetInstance> retrieveSerno(final int productId, final String text, final String trxName)
	{
		if (text == null)
		{
			throw new IllegalArgumentException("Param 'text' may not be null");
		}
		if (productId <= 0)
		{
			throw new IllegalArgumentException(PREFIX_ERR_MSG_NONEXISTING_PROD + productId);
		}

		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, trxName);
		if (product == null || product.getM_Product_ID() <= 0)
		{
			throw new IllegalArgumentException(PREFIX_ERR_MSG_NONEXISTING_PROD + productId);
		}

		final int attributeSetId = Services.get(IProductBL.class).getM_AttributeSet_ID(product);

		final String sql = SQL_SELECT_ASI;
		final Object[] sqlParams = new Object[] { attributeSetId, text };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			DB.setParameters(pstmt, sqlParams);

			rs = pstmt.executeQuery();

			final List<MAttributeSetInstance> result = new ArrayList<MAttributeSetInstance>();

			while (rs.next())
			{
				result.add(new MAttributeSetInstance(Env.getCtx(), rs, trxName));
			}

			return result;

		}
		catch (SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public MProductPO retrieveProductPO(final int productId,
			final int bPartnerId, String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_PRODUCT_PO,
				trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, productId);
			pstmt.setInt(2, bPartnerId);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				return new MProductPO(Env.getCtx(), rs, trxName);
			}

			if (logger.isDebugEnabled())
				logger.debug("There is no M_ProductPO entry for productId "
						+ productId + " and bPartnerId " + bPartnerId
						+ ". Returning null");
			return null;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public Collection<I_M_RequisitionLine> retrieveRequisitionlines(
			int productId, int warehouseId, String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(
				SQL_REQUISITION_LINE, trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, productId);
			pstmt.setInt(2, warehouseId);

			final ArrayList<I_M_RequisitionLine> result = new ArrayList<I_M_RequisitionLine>();

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new MRequisitionLine(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public I_M_Requisition retrieveRequisition(int requisitionId, String trxName)
	{
		return new MRequisition(Env.getCtx(), requisitionId, trxName);
	}

	@Override
	public Collection<I_M_RequisitionLine> retrieveRequisitionlines(
			String docNo, String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(
				SQL_REQUISITION_LINES, trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setString(1, docNo);

			final ArrayList<I_M_RequisitionLine> result = new ArrayList<I_M_RequisitionLine>();

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new MRequisitionLine(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public Collection<I_M_Requisition> retrieveRequisitions(
			final String docStatus, final String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_REQUISITIONS,
				trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setString(1, docStatus);

			final ArrayList<I_M_Requisition> result = new ArrayList<I_M_Requisition>();

			rs = pstmt.executeQuery();

			while (rs.next())
			{
				result.add(new MRequisition(Env.getCtx(), rs, trxName));
			}
			return result;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public I_M_Replenish retrieveReplenish(int productId, int warehouseId,
			String trxName)
	{

		final PreparedStatement pstmt = DB.prepareStatement(SQL_REPLENISH,
				trxName);

		ResultSet rs = null;

		try
		{
			pstmt.setInt(1, productId);
			pstmt.setInt(2, warehouseId);

			rs = pstmt.executeQuery();

			if (rs.next())
			{
				return new X_M_Replenish(Env.getCtx(), rs, trxName);
			}
			return null;

		}
		catch (SQLException e)
		{
			throw new RuntimeException(e);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Invokes {@link MAttributeSet#MAttributeSet(java.util.Properties, int, String)}.
	 */
	@Override
	public MAttributeSet retrieveAttributeSet(final int attributeSetId,
			final String trxName)
	{
		return new MAttributeSet(Env.getCtx(), attributeSetId, trxName);
	}

	@Override
	public I_M_PriceList retrievePriceList(int priceListId, String trxName)
	{
		return InterfaceWrapperHelper.create(new MPriceList(Env.getCtx(), priceListId, trxName), I_M_PriceList.class);
	}

	@Override
	public BigDecimal retrievePriceStd(final int productId,
			final int partnerId, final int priceListId, final BigDecimal qty,
			final boolean soTrx)
	{

		final MProductPricing pricing = new MProductPricing(productId, partnerId, qty, soTrx);

		pricing.setM_PriceList_ID(priceListId);

		final BigDecimal priceStd = pricing.getPriceStd();

		if (priceStd.signum() == 0)
		{
			throw new ProductNotOnPriceListException(pricing, -1);
		}
		return priceStd;
	}

	@Override
	@Cached
	public MPriceList retrievePriceListBySibling(
			@CacheCtx final Properties ctx,
			final int priceListId,
			final int bPartnerLocationId,
			@CacheTrx final String trxName)
	{
		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.create(ctx, bPartnerLocationId, I_C_BPartner_Location.class, trxName);

		final IDatabaseBL databaseBL = Services.get(IDatabaseBL.class);

		final List<MPriceList> result = databaseBL.retrieveList(
				SQL_PRICELIST_BY_SIBLING, new Object[] { priceListId,
						bPartnerLocation.getC_Location_ID() },
				MPriceList.class, trxName);

		final MPriceList pl;

		if (result.isEmpty())
		{
			pl = null;
		}
		else
		{
			pl = result.get(0);
		}
		return pl;
	}

	@Override
	@Cached(cacheName = I_M_PriceList.Table_Name + "-" + I_C_Location.Table_Name)
	public I_M_PriceList retrievePriceListByPricingSyst(
			final @CacheCtx Properties ctx,
			final int pricingSystemId,
			final int bPartnerLocationId,
			final boolean isSOPriceList,
			final @CacheTrx String trxName)
	{
		// In case we are dealing with Pricing System None, return the PriceList none
		if (pricingSystemId == MPricingSystem.M_PricingSystem_ID_None)
		{
			final I_M_PriceList pl = InterfaceWrapperHelper.create(ctx, MPriceList.M_PriceList_ID_None, I_M_PriceList.class, trxName);
			Check.assumeNotNull(pl, "pl not null");
			return pl;
		}

		final I_C_BPartner_Location bPartnerLocation = InterfaceWrapperHelper.create(ctx, bPartnerLocationId, I_C_BPartner_Location.class, trxName);

		final Object[] params = { //
		pricingSystemId, //
				isSOPriceList, //
				bPartnerLocation.getC_Location_ID() //
		};

		final IDatabaseBL databaseBL = Services.get(IDatabaseBL.class);

		final List<MPriceList> result = databaseBL.retrieveList(
				SQL_PRICELIST_BY_RPICINGSYSTEM, //
				params, MPriceList.class, trxName);

		final MPriceList pl;

		if (result.isEmpty())
		{
			pl = null;
		}
		else
		{
			pl = result.get(0);
		}
		return InterfaceWrapperHelper.create(pl, I_M_PriceList.class);
	}

	@Override
	public Collection<MProductPrice> retrieveProductPrices(
			final int priceListVersionId, final String trxName)
	{
		return new Query(Env.getCtx(), I_M_ProductPrice.Table_Name, WHERE_PRODUCT_PRICE, trxName)
				.setParameters(new Object[] { priceListVersionId })
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list();

	}

	@Override
	public Collection<I_M_ProductScalePrice> retrieveScalePrices(final int productPriceId, final String trxName)
	{
		return new Query(Env.getCtx(), I_M_ProductScalePrice.Table_Name, WHERE_PRODUCT_SCALE_PRICE, trxName)
				.setParameters(new Object[] { productPriceId })
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.list(I_M_ProductScalePrice.class);
	}

	/**
	 * Invokes {@link MProductScalePrice#MProductScalePrice(java.util.Properties, int, String)} .
	 * 
	 * @param trxName
	 * @return
	 */
	@Override
	public I_M_ProductScalePrice createScalePrice(final String trxName)
	{
		return InterfaceWrapperHelper.newInstance(I_M_ProductScalePrice.class, PlainContextAware.newWithTrxName(Env.getCtx(),trxName));
	}

	/**
	 * Invokes {@link MPriceListVersion#MPriceListVersion(java.util.Properties, int, String)} .
	 */
	@Cached
	@Override
	public I_M_PriceList_Version retrievePriceListVersion(
			final @CacheCtx Properties ctx,
			final int plvId,
			final String trxName)
	{
		return new MPriceListVersion(ctx, plvId, trxName);
	}

	/**
	 * Returns an existing scale price or (if <code>createNew</code> is true) creates a new one.
	 * 
	 * @param productPriceId
	 * @param qty
	 * @param createNew
	 * @param trxName
	 * @return
	 */
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
		catch (SQLException e)
		{
			throw new DBException(e, SQL_SCALEPRICE_FOR_QTY);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public MProduct retrieveCategoryProduct(final Properties ctx, final I_M_Product_Category category, final String trxName)
	{
		final String wc = I_M_Product.COLUMNNAME_IsCategoryProduct + "='Y' AND " + I_M_Product.COLUMNNAME_M_Product_Category_ID + "=?";

		return new Query(ctx, I_M_Product.Table_Name, wc, trxName)
				.setParameters(category.getM_Product_Category_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.first();
	}

	@Override
	public I_C_UOM retrieveProductUOM(final Properties ctx, final int productId)
	{
		final MProduct product = MProduct.get(ctx, productId);
		if (product == null)
		{
			throw new AdempiereException("@NotFound@ @M_Product_ID@ (ID=" + productId + ")");
		}

		return MUOM.get(ctx, product.getC_UOM_ID());
	}
}
