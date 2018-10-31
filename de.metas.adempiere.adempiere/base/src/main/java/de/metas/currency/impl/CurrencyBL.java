package de.metas.currency.impl;

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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;

import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyConversionResult;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.ICurrencyRate;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;

public class CurrencyBL implements ICurrencyBL
{
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
		Check.assumeNotNull(ac, "Missing C_AcctSchema for AD_Client_ID={} and AD_Org_ID={}", adClientId, adOrgId);

		final I_C_Currency currency = ac.getC_Currency();
		Check.assumeNotNull(currency, "C_AcctSchema {} has no currency", ac);

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
		// Calculate the converted amount
		final BigDecimal amountConv = currencyRate.convertAmount(amt);
		result.setAmount(amountConv);

		return result;
	}	// convert

	protected int getCurrencyPrecision(final int currencyId)
	{
		return Services.get(ICurrencyDAO.class).getStdPrecision(Env.getCtx(), currencyId);
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
	public final ICurrencyConversionContext createCurrencyConversionContext(
			final Date ConvDate,
			final int ConversionType_ID,
			final int AD_Client_ID,
			final int AD_Org_ID)
	{
		final CurrencyConversionContext.Builder conversionCtx = CurrencyConversionContext.builder();
		conversionCtx.setAD_Client_ID(AD_Client_ID);
		conversionCtx.setAD_Org_ID(AD_Org_ID);

		final Date convDateToUse = ConvDate != null ? ConvDate : SystemTime.asDate();

		// Conversion Type
		if (ConversionType_ID > 0)
		{
			conversionCtx.setC_ConversionType_ID(ConversionType_ID);
		}
		else
		{
			final int defaultConversionTypeId = retrieveDefaultConversionTypeId(convDateToUse, AD_Client_ID, AD_Org_ID);
			conversionCtx.setC_ConversionType_ID(defaultConversionTypeId);
		}

		// Conversion Date
		conversionCtx.setConversionDate(convDateToUse);

		return conversionCtx.build();
	}

	@Override
	public final ICurrencyConversionContext createCurrencyConversionContext(final Date ConvDate, final ConversionType conversionType, final int AD_Client_ID, final int AD_Org_ID)
	{
		// Find C_ConversionType_ID
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_ConversionType conversionTypeDef = currencyDAO.retrieveConversionType(Env.getCtx(), ConversionType.Spot);
		final int conversionTypeId = conversionTypeDef.getC_ConversionType_ID();

		return createCurrencyConversionContext(ConvDate, conversionTypeId, AD_Client_ID, AD_Org_ID);
	}

	protected int retrieveDefaultConversionTypeId(final Date date, final int adClientId, final int adOrgId)
	{
		final Properties ctx = Env.getCtx();
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_ConversionType conversionType = currencyDAO.retrieveDefaultConversionType(ctx, adClientId, adOrgId, date);
		return conversionType.getC_ConversionType_ID();
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
		Check.assume(conversionTypeId > 0, "C_ConversionType_ID is set in {}", conversionCtx);
		final Date conversionDate = conversionCtx.getConversionDate();
		Check.assumeNotNull(conversionDate, "C_ConversionType_ID is set in {}", conversionCtx);

		final int currencyPrecision = getCurrencyPrecision(CurTo_ID);

		if (CurFrom_ID == CurTo_ID)
		{
			final BigDecimal conversionRate = BigDecimal.ONE;
			return new CurrencyRate(conversionRate, CurFrom_ID, CurTo_ID, currencyPrecision, conversionTypeId, conversionDate);
		}

		final BigDecimal rate = Services.get(ICurrencyDAO.class).retrieveRateOrNull(conversionCtx, CurFrom_ID, CurTo_ID);
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
