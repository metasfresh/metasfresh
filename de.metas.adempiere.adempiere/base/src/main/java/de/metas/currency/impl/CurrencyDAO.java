/**
 *
 */
package de.metas.currency.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.util.CacheCtx;
import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyDAO;

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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CurrencyDAO implements ICurrencyDAO
{
	@Override
	@Cached(cacheName = I_C_Currency.Table_Name)
	public I_C_Currency retrieveCurrency(@CacheCtx final Properties ctx, final int currencyId)
	{
		return InterfaceWrapperHelper.create(ctx, currencyId, I_C_Currency.class, ITrx.TRXNAME_None);
	}

	@Override
	@Cached(cacheName = I_C_Currency.Table_Name + "#by#" + I_C_Currency.COLUMNNAME_ISO_Code)
	public I_C_Currency retrieveCurrencyByISOCode(@CacheCtx final Properties ctx, final String ISOCode)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Currency.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Currency.COLUMNNAME_ISO_Code, ISOCode)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				//
				.orderBy()
				.addColumn(I_C_Currency.COLUMNNAME_ISO_Code) // ordering by value to make it easier for the user to browse the logging.
				.endOrderBy()
				//
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 500)
				.firstOnly(I_C_Currency.class);
	}

	@Override
	public String getISO_Code(Properties ctx, int C_Currency_ID)
	{
		final I_C_Currency currency = retrieveCurrency(ctx, C_Currency_ID);
		if (currency == null)
		{
			return null;
		}
		return currency.getISO_Code();
	}

	@Override
	public int getStdPrecision(Properties ctx, int C_Currency_ID)
	{
		final I_C_Currency c = retrieveCurrency(ctx, C_Currency_ID);
		if (c == null || c.getC_Currency_ID() <= 0)
		{
			return 2; // default
		}
		return c.getStdPrecision();
	}

	@Override
	@Cached(cacheName = I_C_ConversionType_Default.Table_Name + "#by#Dimension")
	public I_C_ConversionType retrieveDefaultConversionType(@CacheCtx final Properties ctx, final int adClientId, final int adOrgId, final Date date)
	{
		// NOTE to developer: keep in sync with: getDefaultConversionType_ID database function

		Check.assumeNotNull(date, "date not null");
		final Date dateDay = TimeUtil.trunc(date, TimeUtil.TRUNC_DAY);

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType_Default.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_C_ConversionType_Default.COLUMN_AD_Client_ID, adClientId, Env.CTXVALUE_AD_Client_ID_System)
				.addInArrayOrAllFilter(I_C_ConversionType_Default.COLUMN_AD_Org_ID, adClientId, Env.CTXVALUE_AD_Org_ID_System)
				.addCompareFilter(I_C_ConversionType_Default.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, dateDay)
				//
				.orderBy()
				.addColumn(I_C_ConversionType_Default.COLUMN_ValidFrom, Direction.Descending, Nulls.Last)
				.addColumn(I_C_ConversionType_Default.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_ConversionType_Default.COLUMN_AD_Org_ID, Direction.Descending, Nulls.Last)
				.endOrderBy()
				//
				.setLimit(1) // only the first one
				//
				.andCollect(I_C_ConversionType_Default.COLUMN_C_ConversionType_ID)
				.create()
				.firstOnlyNotNull(I_C_ConversionType.class);
	}

	@Override
	@Cached(cacheName = I_C_ConversionType.Table_Name + "#by#" + I_C_ConversionType.COLUMNNAME_Value)
	public I_C_ConversionType retrieveConversionType(@CacheCtx final Properties ctx, final ConversionType type)
	{
		Check.assumeNotNull(type, "type not null");
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_ConversionType.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_C_ConversionType.COLUMN_Value, type.getCode())
				//
				.create()
				.firstOnlyNotNull(I_C_ConversionType.class);
	}

	/**
	 * @param conversionCtx
	 * @param CurFrom_ID
	 * @param CurTo_ID
	 * @return query which is finding the best matching {@link I_C_Conversion_Rate} for given parameters.
	 */
	protected final IQueryBuilder<I_C_Conversion_Rate> retrieveRateQuery(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		final Properties ctx = Env.getCtx();
		final int conversionTypeId = conversionCtx.getC_ConversionType_ID();
		final Date conversionDate = conversionCtx.getConversionDate();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Conversion_Rate.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_Currency_ID, CurFrom_ID)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_Currency_ID_To, CurTo_ID)
				.addEqualsFilter(I_C_Conversion_Rate.COLUMN_C_ConversionType_ID, conversionTypeId)
				.addCompareFilter(I_C_Conversion_Rate.COLUMN_ValidFrom, Operator.LESS_OR_EQUAL, conversionDate)
				.addCompareFilter(I_C_Conversion_Rate.COLUMN_ValidTo, Operator.GREATER_OR_EQUAL, conversionDate)
				.addInArrayOrAllFilter(I_C_Conversion_Rate.COLUMN_AD_Client_ID, 0, conversionCtx.getAD_Client_ID())
				.addInArrayOrAllFilter(I_C_Conversion_Rate.COLUMN_AD_Org_ID, 0, conversionCtx.getAD_Org_ID())
				//
				.orderBy()
				.addColumn(I_C_Conversion_Rate.COLUMN_AD_Client_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Conversion_Rate.COLUMN_AD_Org_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_C_Conversion_Rate.COLUMN_ValidFrom, Direction.Descending, Nulls.Last)
				.endOrderBy()
				//
				.setLimit(1) // first only
		;
	}

	@Override
	public BigDecimal retrieveRateOrNull(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		final List<Map<String, Object>> result = retrieveRateQuery(conversionCtx, CurFrom_ID, CurTo_ID)
				.setLimit(1)
				.create()
				.listColumns(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);

		if (result.isEmpty())
		{
			return null;
		}

		final BigDecimal rate = (BigDecimal)result.get(0).get(I_C_Conversion_Rate.COLUMNNAME_MultiplyRate);
		return rate;
	}
}
