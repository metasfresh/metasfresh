package org.adempiere.pricing.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_DiscountSchemaLine;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_M_PriceList;
import de.metas.adempiere.model.I_M_ProductPrice;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

public class PriceListDAO implements IPriceListDAO
{
	private static final transient Logger logger = LogManager.getLogger(PriceListDAO.class);

	@Override
	public void updateTaxCategory(final I_M_PriceList_Version plv,
			final I_M_DiscountSchemaLine dsl,
			final int adPinstanceId,
			final String trxName)
	{
		// update tax categories using productPrices from base plv
		final StringBuilder updTaxCategory = new StringBuilder();
		updTaxCategory.append(" UPDATE M_ProductPrice  "
				+ " SET ");
		if (dsl.getC_TaxCategory_Target_ID() > 0)
		{
			// our dsl has a C_TaxCategory_Target_ID set, so update our new product prices accordingly
			updTaxCategory.append("   " + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "=" + dsl.getC_TaxCategory_Target_ID());
		}
		else
		{
			// our dsl did not specify a C_TaxCategory_Target_ID, so just use the one from the source product prices
			updTaxCategory.append("   " + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "=bpp." + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID);
		}

		updTaxCategory.append(
				"   , " + I_M_ProductPrice.COLUMNNAME_UseScalePrice + "=bpp." + I_M_ProductPrice.COLUMNNAME_UseScalePrice
						+ "   , " + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "=bpp." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice
						+ " FROM ( "
						+ "   select M_Product_ID"
						+ "      , " + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID
						+ "      , " + I_M_ProductPrice.COLUMNNAME_UseScalePrice
						+ "      , " + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice
						+ "   from M_ProductPrice pp "
						+ "   where true "
						+ "      AND M_PriceList_Version_ID=? " /* plv.getM_Pricelist_Version_Base_ID() */
						// + " 	 AND pp.IsActive='Y' " // update even if not active (07102)
						+ "      AND EXISTS ( "
						+ "        SELECT 1 FROM T_Selection s "
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=? " /* adPinstanceId */
						+ "      ) "
						+ "  ) bpp "
						+ " WHERE true "
						+ " 	AND M_ProductPrice.M_Product_ID = bpp.M_Product_ID "

						// task 08136: just f*****g update the pp of our *current* price-list-version, not all existing pps that have the same product
						+ "     AND M_ProductPrice.M_PriceList_Version_ID = ? "); // plv.getM_PriceList_Version_ID()

		final PreparedStatement pstmtTaxCat = DB.prepareStatement(updTaxCategory.toString(), trxName);
		try
		{
			pstmtTaxCat.setInt(1, plv.getM_Pricelist_Version_Base_ID());
			pstmtTaxCat.setInt(2, adPinstanceId);
			pstmtTaxCat.setInt(3, plv.getM_PriceList_Version_ID());
			final int result = pstmtTaxCat.executeUpdate();

			logger.info("Updated " + result + " product prices");
		}
		catch (final SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(pstmtTaxCat);
		}
	}

	@Override
	public void updateScalePrices(
			final I_M_PriceList_Version plv,
			final I_M_DiscountSchemaLine dsl,
			final int adPinstanceId,
			final String trxName)
	{
		//
		// deleting existing scale prices
		final String delSql =
				"DELETE FROM M_ProductScalePrice psp \n"
						+ " WHERE true\n"
						+ "   AND EXISTS (\n"
						+ "        SELECT 1 \n"
						+ "        FROM T_Selection s, M_ProductPrice pp\n"
						+ "        WHERE true\n"
						+ "          AND pp.M_Product_ID=s.T_Selection_ID AND pp.M_ProductPrice_ID=psp.M_ProductPrice_ID\n"
						+ "          AND pp.M_PriceList_Version_ID=" + plv.getM_PriceList_Version_ID() + "\n"
						+ "          AND s.AD_PInstance_ID=" + adPinstanceId + "\n"
						+ "          AND pp." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N'\n"
						+ "   ) ";

		final int delResult = DB.executeUpdateEx(delSql, trxName);
		logger.info("Deleted " + delResult + " product scale prices");

		final I_C_Currency targetCurrency = plv.getM_PriceList().getC_Currency();
		final int targetCurrencyId = targetCurrency.getC_Currency_ID();

		//
		// inserting new scale prices
		final String sqlins = "INSERT INTO M_ProductScalePrice \n"
				+ " ( \n"
				+ "   M_ProductPrice_ID, \n"
				+ "   AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,\n"
				+ "   Qty, PriceList, PriceStd, PriceLimit\n"
				+ " )\n"
				+ " SELECT \n"
				+ "  pp.M_ProductPrice_ID, \n"
				+ dsl.getAD_Client_ID() + ", "
				+ plv.getAD_Org_ID() + ","
				+ " bpsp.IsActive," // IsActive
				+ " now(), " // Created
				+ plv.getUpdatedBy() + "," // CreatedBy
				+ " now()," // Updated
				+ plv.getUpdatedBy() + ", \n" // UpdatedBy
				+ "   bpsp.Qty, \n"
				+ "   COALESCE(currencyConvert(\n"
				+ "     bpsp.PriceList, bpl.C_Currency_ID, " + targetCurrencyId
				+ "     , ?, " + dsl.getC_ConversionType_ID() /* dsl.getConversionDate() */
				+ "     , " + dsl.getAD_Client_ID() + ", " + plv.getAD_Org_ID()
				+ "   ),0),\n"
				+ "   COALESCE(currencyConvert("
				+ "      bpsp.PriceStd, bpl.C_Currency_ID, " + targetCurrencyId
				+ "      , ?, " + dsl.getC_ConversionType_ID() /* dsl.getConversionDate() */
				+ "      , " + dsl.getAD_Client_ID() + ", " + plv.getAD_Org_ID()
				+ "    ),0),\n"
				+ "    COALESCE(currencyConvert("
				+ "      bpsp.PriceLimit, bpl.C_Currency_ID, " + targetCurrencyId
				+ "      , ?, " + dsl.getC_ConversionType_ID() /* dsl.getConversionDate() */
				+ "      , " + dsl.getAD_Client_ID() + ", " + plv.getAD_Org_ID()
				+ "    ),0)\n"
				+ " FROM M_ProductScalePrice bpsp\n"
				+ "   INNER JOIN M_ProductPrice bpp ON bpp.M_ProductPrice_ID=bpsp.M_ProductPrice_ID\n"
				+ "   INNER JOIN M_ProductPrice pp ON bpp.M_Product_ID=pp.M_Product_ID \n"
				+ "   INNER JOIN M_PriceList_Version bplv ON pp.M_PriceList_Version_ID=bplv.M_PriceList_Version_ID\n"
				+ "   INNER JOIN M_PriceList bpl ON bplv.M_PriceList_ID=bpl.M_PriceList_ID\n" /* pl needed for src currency */
				+ " WHERE true \n"
				+ "   AND bpp.M_PriceList_Version_ID=" + plv.getM_Pricelist_Version_Base_ID() + "\n"
				+ "   AND pp.M_PriceList_Version_ID=" + plv.getM_PriceList_Version_ID() + "\n"
				+ "   AND EXISTS ( \n"
				+ "        SELECT 1 FROM T_Selection s \n"
				+ "        WHERE bpp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + adPinstanceId + "\n"
				+ "   ) \n"
				+ "   AND bpp.UseScalePrice='Y'\n"
		// + "   AND bpp.Isactive='Y'\n" // copy inactive ones too (07102)
		;

		// org.compiere.util.Trx.get(trxName, false).commit()
		final PreparedStatement pstmt = DB.prepareStatement(sqlins, trxName);
		try
		{
			pstmt.setTimestamp(1, dsl.getConversionDate());
			pstmt.setTimestamp(2, dsl.getConversionDate());
			pstmt.setTimestamp(3, dsl.getConversionDate());
			final int result = pstmt.executeUpdate();
			logger.info("Inserted " + result + " product scale prices");
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sqlins);
		}
		finally
		{
			DB.close(pstmt);
		}

		//
		// calculating scale prices
		final String sqlUpdPrices =
				" UPDATE M_ProductScalePrice psp "
						+ " SET	" // task 06853: translated DECODE to CASE
						// + "   PriceList = (DECODE('"+ dsl.getList_Base() + "', 'S', psp.PriceStd, 'X', psp.PriceLimit, psp.PriceList"+ " ) + ?) * (1 - ?/100),"
						+ "   PriceList = (CASE '" + dsl.getList_Base() + "' WHEN 'S' THEN psp.PriceStd WHEN 'X' THEN psp.PriceLimit ELSE psp.PriceList END + ?) * (1 - ?/100),"

						// + "   PriceStd = (DECODE('" + dsl.getStd_Base() + "', 'L', psp.PriceList, 'X', psp.PriceLimit, psp.PriceStd" + " ) + ?) * (1 - ?/100), "
						+ "   PriceStd = (CASE '" + dsl.getStd_Base() + "' WHEN 'L' THEN psp.PriceList WHEN 'X' THEN psp.PriceLimit ELSE psp.PriceStd END + ?) * (1 - ?/100), "

						// + "   PriceLimit = (DECODE('" + dsl.getLimit_Base() + "', 'L', psp.PriceList, 'S', psp.PriceStd, psp.PriceLimit" + " ) + ?) * (1 - ?/100) "
						+ "   PriceLimit = (CASE '" + dsl.getLimit_Base() + "' WHEN 'L' THEN psp.PriceList WHEN 'S' THEN psp.PriceStd ELSE psp.PriceLimit END + ?) * (1 - ?/100) "
						+ " FROM"
						+ "   M_ProductPrice pp "
						+ " WHERE "
						+ "   psp.M_ProductPrice_ID=pp.M_ProductPrice_ID"
						+ "   AND pp.M_PriceList_Version_ID = " + plv.getM_PriceList_Version_ID()
						+ "   AND EXISTS ( "
						+ "        SELECT 1 FROM T_Selection s "
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + adPinstanceId
						+ "   ) "
						+ "   AND pp.UseScalePrice='Y'"
						// + "   AND psp.IsActive='Y'" // update even if active or not (07102)
						// + "   AND pp.IsActive='Y'" // update even if active or not (07102)
						+ "   AND pp." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N'" // update only if Season Fixed Price is not set (07082)
		;

		final PreparedStatement pstmUpdPrices = DB.prepareStatement(sqlUpdPrices, trxName);
		try
		{
			pstmUpdPrices.setBigDecimal(1, dsl.getLimit_AddAmt());
			pstmUpdPrices.setBigDecimal(2, dsl.getLimit_Discount());
			pstmUpdPrices.setBigDecimal(3, dsl.getStd_AddAmt());
			pstmUpdPrices.setBigDecimal(4, dsl.getStd_Discount());
			pstmUpdPrices.setBigDecimal(5, dsl.getLimit_AddAmt());
			pstmUpdPrices.setBigDecimal(6, dsl.getLimit_Discount());

			final int result = pstmUpdPrices.executeUpdate();
			logger.info("Updated " + result + " product scale prices (price values)");
		}
		catch (final SQLException e)
		{
			throw new AdempiereException(e);
		}
		finally
		{
			DB.close(pstmUpdPrices);
		}
		//
		// Rounding (AD_Reference_ID=155)
		final String sqlUpdRound =
				"UPDATE	M_ProductScalePrice psp "
						+ " SET	" // task 06853: translated DECODE to CASE
						+ "  PriceList = CASE '" + dsl.getList_Rounding() + "'"
						+ "    WHEN 'N' THEN psp.PriceList "
						+ "    WHEN '0' THEN ROUND(psp.PriceList, 0)" // Even .00
						+ "    WHEN 'D' THEN ROUND(psp.PriceList, 1)" // Dime .10
						+ "    WHEN 'T' THEN ROUND(psp.PriceList, -1) " // Ten 10.00
						+ "    WHEN '5' THEN ROUND(psp.PriceList*20,0)/20" // Nickle .05
						+ "    WHEN 'Q' THEN ROUND(psp.PriceList*4,0)/4" // Quarter .25
						+ "    WHEN '9' THEN CASE" // Whole 9 or 5
						+ "      WHEN MOD(ROUND(psp.PriceList),10)<=5 THEN ROUND(psp.PriceList)+(5-MOD(ROUND(psp.PriceList),10))"
						+ "      WHEN MOD(ROUND(psp.PriceList),10)>5 THEN ROUND(psp.PriceList)+(9-MOD(ROUND(psp.PriceList),10)) "
						+ "    END"
						+ "    ELSE ROUND(psp.PriceList, " + targetCurrency.getStdPrecision() + ")"
						+ "    END,"

						+ "  PriceStd = CASE '" + dsl.getStd_Rounding() + "' "
						+ "    WHEN 'N' THEN psp.PriceStd "
						+ "    WHEN '0' THEN ROUND(psp.PriceStd, 0) " // Even .00
						+ "    WHEN 'D' THEN ROUND(psp.PriceStd, 1) " // Dime .10
						+ "    WHEN 'T' THEN ROUND(psp.PriceStd, -1)" // Ten 10.00
						+ "    WHEN '5' THEN ROUND(psp.PriceStd*20,0)/20" // Nickle .05
						+ "    WHEN 'Q' THEN ROUND(psp.PriceStd*4,0)/4" // Quarter .25
						+ "    WHEN '9' THEN CASE" // Whole 9 or 5
						+ "      WHEN MOD(ROUND(psp.PriceStd),10)<=5 THEN ROUND(psp.PriceStd)+(5-MOD(ROUND(psp.PriceStd),10))"
						+ "      WHEN MOD(ROUND(psp.PriceStd),10)>5 THEN ROUND(psp.PriceStd)+(9-MOD(ROUND(psp.PriceStd),10)) "
						+ "      END "
						+ "    ELSE ROUND(psp.PriceStd, " + targetCurrency.getStdPrecision() + ")"
						+ "    END,"

						+ "  PriceLimit = CASE '" + dsl.getLimit_Rounding() + "'"
						+ "		WHEN 'N' THEN psp.PriceLimit "
						+ "		WHEN '0' THEN ROUND(psp.PriceLimit, 0)	" // Even .00
						+ "		WHEN 'D' THEN ROUND(psp.PriceLimit, 1)	" // Dime .10
						+ "		WHEN 'T' THEN ROUND(psp.PriceLimit, -1)	" // Ten 10.00
						+ "		WHEN '5' THEN ROUND(psp.PriceLimit*20,0)/20	" // Nickle .05
						+ "		WHEN 'Q' THEN ROUND(psp.PriceLimit*4,0)/4" // Quarter .25
						+ "     WHEN '9' THEN CASE" // Whole 9 or 5
						+ "       WHEN MOD(ROUND(psp.PriceLimit),10)<=5 THEN ROUND(psp.PriceLimit)+(5-MOD(ROUND(psp.PriceLimit),10))"
						+ "       WHEN MOD(ROUND(psp.PriceLimit),10)>5 THEN ROUND(psp.PriceLimit)+(9-MOD(ROUND(psp.PriceLimit),10)) "
						+ "     END"
						+ "		ELSE ROUND(psp.PriceLimit, " + targetCurrency.getStdPrecision() + ")"
						+ "     END"

						+ " FROM"
						+ "   M_ProductPrice pp "
						+ " WHERE "
						+ "   psp.M_ProductPrice_ID=pp.M_ProductPrice_ID"
						+ "   AND pp.M_PriceList_Version_ID = " + plv.getM_PriceList_Version_ID()
						+ "   AND EXISTS ( "
						+ "        SELECT * FROM T_Selection s "
						+ "        WHERE pp.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=" + adPinstanceId
						+ "   ) "
						+ "   AND pp.UseScalePrice='Y'"
						+ "   AND pp.Isactive='Y'"
						// + "   AND psp.IsActive='Y'" // update even if active or not
						+ "   AND pp." + I_M_ProductPrice.COLUMNNAME_IsSeasonFixedPrice + "='N'" // update only if Season Fixed Price is not set (07082)
		;

		final int roundResult = DB.executeUpdateEx(sqlUpdRound, trxName);
		logger.info("Updated " + roundResult + " product scale prices (rounding)");
	}

	@Override
	@Cached(cacheName = I_M_PriceList.Table_Name + "#By#M_PriceList_ID")
	public I_M_PriceList retrievePriceList(@CacheCtx final Properties ctx, final int priceListId)
	{
		if (priceListId <= 0)
		{
			return null;
		}

		final I_M_PriceList priceList = InterfaceWrapperHelper.create(ctx, priceListId, I_M_PriceList.class, ITrx.TRXNAME_None);
		return priceList;
	}

	@Override
	public I_M_ProductPrice retrieveProductPrice(final org.compiere.model.I_M_PriceList_Version plv, final int productId)
	{
		Check.assumeNotNull(plv, "plv not null");

		final I_M_ProductPrice pp = retrieveProductPriceOrNull(plv, productId);
		if (pp == null)
		{
			throw new ProductNotOnPriceListException(plv, productId);
		}

		return pp;
	}

	@Override
	public I_M_ProductPrice retrieveProductPriceOrNull(final org.compiere.model.I_M_PriceList_Version plv, final int productId)
	{
		if (plv == null)
		{
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(plv);
		final String trxName = InterfaceWrapperHelper.getTrxName(plv);

		final int priceListVersionId = plv.getM_PriceList_Version_ID();

		return retrieveProductPriceOrNull(ctx, priceListVersionId, productId, trxName);
	}

	@Cached(cacheName = I_M_ProductPrice.Table_Name + "#By#" + I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "#" + I_M_ProductPrice.COLUMNNAME_M_Product_ID)
	@Override
	public I_M_ProductPrice retrieveProductPriceOrNull(
			@CacheCtx final Properties ctx,
			final int priceListVersionId,
			final int productId,
			@CacheTrx final String trxName)
	{
		final I_M_ProductPrice result = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class, ctx, trxName)
				.addEqualsFilter(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.addEqualsFilter(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, priceListVersionId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(I_M_ProductPrice.class);

		return result;
	}

	@Override
	public Iterator<I_M_ProductPrice> retrieveAllProductPrices(final org.compiere.model.I_M_PriceList_Version plv)
	{
		return retrieveProductPricesQuery(plv)
				.create()
				.iterate(I_M_ProductPrice.class);
	}

	@Override
	public IQueryBuilder<I_M_ProductPrice> retrieveProductPricesQuery(final org.compiere.model.I_M_PriceList_Version plv)
	{
		Check.assumeNotNull(plv, "Param 'plv' not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class, plv)
				.addEqualsFilter(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID())
				.addOnlyActiveRecordsFilter();
	}

	@Override
	public Iterator<I_M_ProductPrice> retrieveAllProductPricesOrderedBySeqNOandProductName(final I_M_PriceList_Version plv)
	{
		final IQueryBuilder<I_M_ProductPrice> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_ProductPrice.class, plv)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID());

		queryBuilder.orderBy()
				.addColumn(org.compiere.model.I_M_ProductPrice.COLUMNNAME_SeqNo)
				.addColumn(org.compiere.model.I_M_ProductPrice.COLUMNNAME_M_Product_ID);

		return queryBuilder.create()
				.iterate(I_M_ProductPrice.class);
	}

	@Override
	public Iterator<I_M_PriceList> retrievePriceLists(final I_M_PricingSystem pricingSystem, final I_C_Country country, final boolean isSOTrx)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_M_PriceList> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList.class, pricingSystem)
				.addOnlyActiveRecordsFilter();

		final ICompositeQueryFilter<I_M_PriceList> filters = queryBL.<I_M_PriceList> createCompositeQueryFilter(I_M_PriceList.class)
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_M_PricingSystem_ID, pricingSystem.getM_PricingSystem_ID())
				.addEqualsFilter(org.compiere.model.I_M_PriceList.COLUMNNAME_IsSOPriceList, isSOTrx);

		final ICompositeQueryFilter<I_M_PriceList> countryFilter = queryBL.<I_M_PriceList> createCompositeQueryFilter(I_M_PriceList.class)
				.setJoinOr()
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, country.getC_Country_ID())
				.addEqualsFilter(I_M_PriceList.COLUMNNAME_C_Country_ID, null);
		filters
				.addFilter(countryFilter);

		return queryBuilder
				.filter(filters)
				.orderBy()
				.addColumn(I_M_PriceList.COLUMNNAME_C_Country_ID, Direction.Ascending, Nulls.Last).endOrderBy()
				.create()
				.iterate(I_M_PriceList.class);
	}

	@Override
	public I_M_PriceList_Version retrievePriceListVersionOrNull(
			final org.compiere.model.I_M_PriceList priceList,
			final Date date,
			final Boolean processed)
	{
		Check.assumeNotNull(priceList, "param 'priceList' not null", priceList);
		Check.assumeNotNull(priceList, "param 'date' not null", date);

		final Properties ctx = InterfaceWrapperHelper.getCtx(priceList);

		final int priceListId = priceList.getM_PriceList_ID();
		final I_M_PriceList_Version plv = retrieveLastVersion(ctx, priceListId, date, processed);

		return plv;
	}

	@Cached(cacheName = I_M_PriceList_Version.Table_Name + "#By#M_PriceList_ID#Date#Processed")
	/* package */ I_M_PriceList_Version retrieveLastVersion(@CacheCtx final Properties ctx,
			final int priceListId,
			final Date date,
			final Boolean processed)
	{
		Check.assumeNotNull(date, "Param 'date' is not null; other params: priceListId={}, processed={}, ctx={}", priceListId, processed, ctx);

		final IQueryBuilder<I_M_PriceList_Version> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_PriceList_Version.class, ctx, ITrx.TRXNAME_None)
				// Same pricelist
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				// valid from must be before the date we need it
				.addCompareFilter(
						I_M_PriceList_Version.COLUMNNAME_ValidFrom,
						CompareQueryFilter.Operator.LESS_OR_EQUAL,
						new Timestamp(date.getTime()),
						DateTruncQueryFilterModifier.DAY)

				// active
				.addOnlyActiveRecordsFilter();

		if (processed != null)
		{
			queryBuilder
					.addEqualsFilter(org.adempiere.pricing.model.I_M_PriceList_Version.COLUMNNAME_Processed, processed);
		}

		// Order the version by validFrom, descending.
		queryBuilder.orderBy()
				.addColumn(I_M_PriceList_Version.COLUMNNAME_ValidFrom, false);

		final IQuery<I_M_PriceList_Version> query = queryBuilder.create();
		final I_M_PriceList_Version result = query.first();
		if (result == null)
		{
			logger.warn("None found M_PriceList_ID=" + priceListId + ", date=" + date + ", query=" + query);
		}
		return result;
	}

	@Override
	public I_M_PriceList_Version retrieveNextVersionOrNull(final I_M_PriceList_Version plv)
	{
		// we want the PLV with the lowest ValidFrom that is just greater than plv's
		final Operator validFromOperator = Operator.GREATER;
		final boolean orderAscending = true;

		return retrievePreviousOrNext(plv, validFromOperator, orderAscending);
	}

	@Override
	public I_M_PriceList_Version retrievePreviousVersionOrNull(final I_M_PriceList_Version plv)
	{
		// we want the PLV with the highest ValidFrom that is just less than plv's
		final Operator validFromOperator = Operator.LESS;
		final boolean orderAscending = false; // i.e. descending
		return retrievePreviousOrNext(plv, validFromOperator, orderAscending);
	}

	private I_M_PriceList_Version retrievePreviousOrNext(final I_M_PriceList_Version plv,
			final Operator validFromOperator,
			final boolean orderAscending)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_PriceList_Version.class, plv)
				// active
				.addOnlyActiveRecordsFilter()
				// same price list
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, plv.getM_PriceList_ID())
				// same processed value
				.addEqualsFilter(org.adempiere.pricing.model.I_M_PriceList_Version.COLUMNNAME_Processed, true)
				// valid from must be after the given PLV's validFrom date
				.addCompareFilter(I_M_PriceList_Version.COLUMNNAME_ValidFrom, validFromOperator, plv.getValidFrom())
				// by validFrom, ascending.
				.orderBy()
				.addColumn(I_M_PriceList_Version.COLUMNNAME_ValidFrom, orderAscending)
				.endOrderBy()
				.create()
				.first();
	}

	@Override
	public IQueryFilter<I_M_ProductPrice> createQueryFilter(final I_M_DiscountSchemaLine dsl)
	{
		Check.assumeNotNull(dsl, "dsl not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_ProductPrice> filters = queryBL.createCompositeQueryFilter(I_M_ProductPrice.class);

		if (dsl.getC_TaxCategory_ID() > 0)
		{
			filters.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID, dsl.getC_TaxCategory_ID());
		}
		if (dsl.getM_Product_ID() > 0)
		{
			filters.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, dsl.getM_Product_ID());
		}
		if (dsl.getM_Product_Category_ID() > 0)
		{
			final IQuery<I_M_Product> productQuery = queryBL.createQueryBuilder(I_M_Product.class, dsl)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_Product.COLUMNNAME_M_Product_Category_ID, dsl.getM_Product_Category_ID())
					.create();

			filters.addInSubQueryFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID,
					I_M_Product.COLUMNNAME_M_Product_ID,  // subQueryColumn,
					productQuery// subQuery
			);
		}

		return filters;
	}

	@Override
	public IQueryFilter<I_M_ProductPrice> createProductPriceQueryFilterForProductInSelection(final int adPInstanceId)
	{
		final IQueryFilter<I_M_ProductPrice> productsInSelectionFilter = new TypedSqlQueryFilter<I_M_ProductPrice>(
				"EXISTS (SELECT 1 FROM T_Selection s WHERE M_ProductPrice.M_Product_ID=s.T_Selection_ID AND s.AD_PInstance_ID=?)",
				Arrays.<Object> asList(adPInstanceId)
				);
		return productsInSelectionFilter;
	}
}
