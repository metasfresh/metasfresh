package org.adempiere.service.impl;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.currency.ICurrencyConversionContext;
import org.adempiere.currency.ICurrencyConversionResult;
import org.adempiere.currency.ICurrencyRate;
import org.adempiere.currency.exceptions.NoCurrencyRateFoundException;
import org.adempiere.currency.impl.CurrencyConversionContext;
import org.adempiere.currency.impl.CurrencyConversionResult;
import org.adempiere.currency.impl.CurrencyRate;
import org.adempiere.service.ICurrencyConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Currency;
import org.compiere.model.MConversionType;
import org.compiere.model.MCurrency;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

public class CurrencyConversionBL implements ICurrencyConversionBL
{
	private static final transient CLogger logger = CLogger.getCLogger(CurrencyConversionBL.class);

	@Override
	public final I_C_Currency getBaseCurrency(final Properties ctx)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		return getBaseCurrency(ctx, adClientId, adOrgId);
	}

	@Override
	public I_C_Currency getBaseCurrency(final Properties ctx, final int adClientId, final int adOrgId)
	{
		Check.assume(adClientId >= 0, "adClientId >= 0");

		final I_C_AcctSchema ac = Services.get(IAcctSchemaDAO.class).retrieveAcctSchema(ctx, adClientId, adOrgId);
		Check.assumeNotNull(ac, "Missing C_AcctSchema for AD_Client_ID={0} and AD_Org_ID={1}", adClientId, adOrgId);

		final I_C_Currency currency = ac.getC_Currency();
		Check.assumeNotNull(currency, "C_AcctSchema {0} has no currency", ac);

		return currency;
	}

	@Override
	public final BigDecimal convertBase(
			final Properties ctx,
			final BigDecimal Amt,
			final int CurFrom_ID,
			final Timestamp ConvDate,
			final int C_ConversionType_ID,
			final int AD_Client_ID,
			final int AD_Org_ID)
	{
		final int CurTo_ID = getBaseCurrency(ctx, AD_Client_ID, AD_Org_ID).getC_Currency_ID();
		return convert(ctx, Amt, CurFrom_ID, CurTo_ID, ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
	}

	@Override
	public final BigDecimal convert(final Properties ctx,
			final BigDecimal Amt, final int CurFrom_ID, final int CurTo_ID,
			final Timestamp ConvDate, final int C_ConversionType_ID,
			final int AD_Client_ID, final int AD_Org_ID)
	{
		final ICurrencyConversionContext conversionCtx = createCurrencyConversionContext(ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
		final ICurrencyConversionResult conversionResult = convert(conversionCtx, Amt, CurFrom_ID, CurTo_ID);
		if (conversionResult == null)
		{
			return null;
		}
		return conversionResult.getAmount();
	}

	@Override
	public final ICurrencyConversionResult convert(final ICurrencyConversionContext conversionCtx,
			final BigDecimal amt,
			final int CurFrom_ID,
			final int CurTo_ID)
	{
		Check.assumeNotNull(amt, "Amt not null");

		final CurrencyConversionResult result = createCurrencyConversionResult(conversionCtx);
		result.setSourceAmount(amt);
		result.setSource_Currency_ID(CurFrom_ID);
		result.setC_Currency_ID(CurTo_ID);

		if (amt.signum() == 0)
		{
			result.setAmount(amt);
			result.setConversionRate(null); // N/A
			return result;
		}

		// Same Currency
		if (CurFrom_ID == CurTo_ID)
		{
			result.setAmount(amt);
			result.setConversionRate(BigDecimal.ONE);
			return result;
		}

		// Get Rate
		final ICurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, CurFrom_ID, CurTo_ID);
		if (currencyRate == null)
		{
			// TODO: evaluate if we can throw an exception here
			return null;
		}
		result.setConversionRate(currencyRate.getConversionRate());

		//
		// Calcualte the converted amount
		final BigDecimal amountConv = currencyRate.convertAmount(amt);
		result.setAmount(amountConv);

		return result;
	}	// convert

	protected int getCurrencyPrecision(final int currencyId)
	{
		return MCurrency.getStdPrecision(Env.getCtx(), currencyId);
	}

	@Override
	public final BigDecimal convert(final Properties ctx,
			final BigDecimal Amt, final int CurFrom_ID, final int CurTo_ID,
			final int AD_Client_ID, final int AD_Org_ID)
	{
		final Timestamp ConvDate = null;
		final int C_ConversionType_ID = DEFAULT_ConversionType_ID;
		final ICurrencyConversionContext conversionCtx = createCurrencyConversionContext(ConvDate, C_ConversionType_ID, AD_Client_ID, AD_Org_ID);
		final ICurrencyConversionResult conversionResult = convert(conversionCtx, Amt, CurFrom_ID, CurTo_ID);
		if (conversionResult == null)
		{
			return null;
		}
		return conversionResult.getAmount();
	}

	@Override
	public final BigDecimal getRate(final int CurFrom_ID, final int CurTo_ID, final Timestamp ConvDate, final int ConversionType_ID, final int AD_Client_ID, final int AD_Org_ID)
	{
		final ICurrencyConversionContext conversionCtx = createCurrencyConversionContext(ConvDate, ConversionType_ID, AD_Client_ID, AD_Org_ID);
		return getRate(conversionCtx, CurFrom_ID, CurTo_ID);
	}

	@Override
	public final ICurrencyConversionContext createCurrencyConversionContext(final Timestamp ConvDate, final int ConversionType_ID, final int AD_Client_ID, final int AD_Org_ID)
	{
		final CurrencyConversionContext conversionCtx = new CurrencyConversionContext();
		conversionCtx.setAD_Client_ID(AD_Client_ID);
		conversionCtx.setAD_Org_ID(AD_Org_ID);

		// Conversion Type
		if (ConversionType_ID > 0)
		{
			conversionCtx.setC_ConversionType_ID(ConversionType_ID);
		}
		else
		{
			final int defaultConversionTypeId = retrieveDefaultConversionTypeId(AD_Client_ID);
			conversionCtx.setC_ConversionType_ID(defaultConversionTypeId);
		}

		// Conversion Date
		if (ConvDate != null)
		{
			conversionCtx.setConversionDate(ConvDate);
		}
		else
		{
			conversionCtx.setConversionDate(SystemTime.asDate());
		}

		return conversionCtx;
	}

	protected int retrieveDefaultConversionTypeId(final int adClientId)
	{
		return MConversionType.getDefault(adClientId);
	}

	@Override
	public BigDecimal getRate(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		final ICurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, CurFrom_ID, CurTo_ID);
		return currencyRate == null ? null : currencyRate.getConversionRate();
	}

	@Override
	public ICurrencyRate getCurrencyRateOrNull(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		Check.assumeNotNull(conversionCtx, "conversionCtx not null");
		final int conversionTypeId = conversionCtx.getC_ConversionType_ID();
		Check.assume(conversionTypeId > 0, "C_ConversionType_ID is set in {0}", conversionCtx);
		final Date conversionDate = conversionCtx.getConversionDate();
		Check.assumeNotNull(conversionDate, "C_ConversionType_ID is set in {0}", conversionCtx);

		final int currencyPrecision = getCurrencyPrecision(CurTo_ID);

		if (CurFrom_ID == CurTo_ID)
		{
			final BigDecimal conversionRate = BigDecimal.ONE;
			return new CurrencyRate(conversionRate, CurFrom_ID, CurTo_ID, currencyPrecision, conversionTypeId, conversionDate);
		}

		final BigDecimal rate = retrieveRateOrNull(conversionCtx, CurFrom_ID, CurTo_ID);
		if (rate == null)
		{
			return null;
		}
		return new CurrencyRate(rate, CurFrom_ID, CurTo_ID, currencyPrecision, conversionTypeId, conversionDate);
	}

	@Override
	public ICurrencyRate getCurrencyRate(final ICurrencyConversionContext conversionCtx, final int currencyFromId, final int currencyToId)
	{
		final ICurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, currencyFromId, currencyToId);
		if (currencyRate == null)
		{
			throw new NoCurrencyRateFoundException(conversionCtx, currencyFromId, currencyToId);
		}
		return currencyRate;
	}

	protected BigDecimal retrieveRateOrNull(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		final int conversionTypeId = conversionCtx.getC_ConversionType_ID();
		final Date conversionDate = conversionCtx.getConversionDate();

		// Get Rate
		final String sql = "SELECT MultiplyRate "
				+ "FROM C_Conversion_Rate "
				+ "WHERE C_Currency_ID=?"					// #1
				+ " AND C_Currency_ID_To=?"					// #2
				+ " AND	C_ConversionType_ID=?"				// #3
				+ " AND	? BETWEEN ValidFrom AND ValidTo"	// #4 TRUNC (?) ORA-00932: inconsistent datatypes: expected NUMBER got TIMESTAMP
				+ " AND AD_Client_ID IN (0,?)"				// #5
				+ " AND AD_Org_ID IN (0,?) "				// #6
				+ "ORDER BY AD_Client_ID DESC, AD_Org_ID DESC, ValidFrom DESC";
		final Object[] sqlParams = new Object[] {
				CurFrom_ID,
				CurTo_ID,
				conversionTypeId,
				conversionDate,
				conversionCtx.getAD_Client_ID(),
				conversionCtx.getAD_Org_ID()
		};
		final BigDecimal rate = DB.getSQLValueBDEx(ITrx.TRXNAME_None, sql, sqlParams);
		if (rate == null)
		{
			logger.log(Level.INFO, "currency rate not found - CurFrom={0}, CurTo={1}, context={2}", new Object[] { CurFrom_ID, CurTo_ID, conversionCtx });
			return null;
		}

		return rate;
	}

	private final CurrencyConversionResult createCurrencyConversionResult(final ICurrencyConversionContext conversionCtx)
	{
		Check.assumeNotNull(conversionCtx, "conversionCtx not null");

		final CurrencyConversionResult result = new CurrencyConversionResult();
		result.setAD_Client_ID(conversionCtx.getAD_Client_ID());
		result.setAD_Org_ID(conversionCtx.getAD_Org_ID());
		result.setConversionDate(conversionCtx.getConversionDate());
		result.setC_ConversionType_ID(conversionCtx.getC_ConversionType_ID());
		return result;
	}
}
