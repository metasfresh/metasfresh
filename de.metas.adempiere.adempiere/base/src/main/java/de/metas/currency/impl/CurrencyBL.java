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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;

import org.adempiere.service.ClientId;
import org.adempiere.service.OrgId;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.currency.ConversionType;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionContext.CurrencyConversionContextBuilder;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.currency.ICurrencyRate;
import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
import lombok.NonNull;

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

		final AcctSchema as = Services.get(IAcctSchemaDAO.class).getByCliendAndOrg(ClientId.ofRepoId(adClientId), OrgId.ofRepoIdOrAny(adOrgId));
		Check.assumeNotNull(as, "Missing C_AcctSchema for AD_Client_ID={} and AD_Org_ID={}", adClientId, adOrgId);

		final CurrencyId currencyId = as.getCurrencyId();
		return Services.get(ICurrencyDAO.class).getById(currencyId);
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
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				ConvDate,
				CurrencyConversionTypeId.ofRepoIdOrNull(C_ConversionType_ID),
				AD_Client_ID,
				AD_Org_ID);
		final CurrencyConversionResult conversionResult = convert(conversionCtx, Amt, CurFrom_ID, CurTo_ID);
		if (conversionResult == null)
		{
			return null;
		}
		return conversionResult.getAmount();
	}

	@Override
	public final CurrencyConversionResult convert(final CurrencyConversionContext conversionCtx,
			final BigDecimal amt,
			final int CurFrom_ID,
			final int CurTo_ID)
	{
		Check.assumeNotNull(amt, "Amt not null");

		final CurrencyConversionResult result = createCurrencyConversionResult(conversionCtx);
		result.setSourceAmount(amt);
		result.setSourceCurrencyId(CurrencyId.ofRepoId(CurFrom_ID));
		result.setCurrencyId(CurrencyId.ofRepoId(CurTo_ID));

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
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				ConvDate,
				(CurrencyConversionTypeId)null, // C_ConversionType_ID,
				AD_Client_ID,
				AD_Org_ID);
		final CurrencyConversionResult conversionResult = convert(conversionCtx, Amt, CurFrom_ID, CurTo_ID);
		if (conversionResult == null)
		{
			return null;
		}
		return conversionResult.getAmount();
	}

	@Override
	public final BigDecimal getRate(final int CurFrom_ID, final int CurTo_ID, final Timestamp ConvDate, final int ConversionType_ID, final int AD_Client_ID, final int AD_Org_ID)
	{
		final CurrencyConversionContext conversionCtx = createCurrencyConversionContext(
				ConvDate,
				CurrencyConversionTypeId.ofRepoIdOrNull(ConversionType_ID),
				AD_Client_ID,
				AD_Org_ID);
		return getRate(conversionCtx, CurFrom_ID, CurTo_ID);
	}

	@Override
	public final CurrencyConversionContext createCurrencyConversionContext(
			final Date convDate,
			final CurrencyConversionTypeId currencyConversionTypeId,
			final int AD_Client_ID,
			final int AD_Org_ID)
	{
		CurrencyConversionContextBuilder conversionCtx = CurrencyConversionContext.builder();
		conversionCtx.clientId(ClientId.ofRepoId(AD_Client_ID));
		conversionCtx.orgId(OrgId.ofRepoId(AD_Org_ID));

		final LocalDate convDateToUse = convDate != null ? TimeUtil.asLocalDate(convDate) : SystemTime.asLocalDate();

		// Conversion Type
		if (currencyConversionTypeId != null)
		{
			conversionCtx.conversionTypeId(currencyConversionTypeId);
		}
		else
		{
			final CurrencyConversionTypeId defaultConversionTypeId = retrieveDefaultConversionTypeId(convDateToUse, AD_Client_ID, AD_Org_ID);
			conversionCtx.conversionTypeId(defaultConversionTypeId);
		}

		// Conversion Date
		conversionCtx.conversionDate(convDateToUse);

		return conversionCtx.build();
	}

	@Override
	public final CurrencyConversionContext createCurrencyConversionContext(final Date ConvDate, final ConversionType conversionType, final int AD_Client_ID, final int AD_Org_ID)
	{
		// Find C_ConversionType_ID
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final CurrencyConversionTypeId conversionTypeId = currencyDAO.getConversionTypeId(ConversionType.Spot);

		return createCurrencyConversionContext(ConvDate, conversionTypeId, AD_Client_ID, AD_Org_ID);
	}

	protected CurrencyConversionTypeId retrieveDefaultConversionTypeId(final LocalDate date, final int adClientId, final int adOrgId)
	{
		final Properties ctx = Env.getCtx();
		final ICurrencyDAO currencyDAO = Services.get(ICurrencyDAO.class);
		final I_C_ConversionType conversionType = currencyDAO.retrieveDefaultConversionType(ctx, adClientId, adOrgId, TimeUtil.asDate(date));
		return CurrencyConversionTypeId.ofRepoId(conversionType.getC_ConversionType_ID());
	}

	@Override
	public BigDecimal getRate(final CurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		final ICurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, CurFrom_ID, CurTo_ID);
		return currencyRate == null ? null : currencyRate.getConversionRate();
	}

	@Override
	public ICurrencyRate getCurrencyRateOrNull(final CurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		Check.assumeNotNull(conversionCtx, "conversionCtx not null");
		final CurrencyConversionTypeId conversionTypeId = conversionCtx.getConversionTypeId();
		final LocalDate conversionDate = conversionCtx.getConversionDate();

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
	public ICurrencyRate getCurrencyRate(final CurrencyConversionContext conversionCtx, final int currencyFromId, final int currencyToId)
	{
		final ICurrencyRate currencyRate = getCurrencyRateOrNull(conversionCtx, currencyFromId, currencyToId);
		if (currencyRate == null)
		{
			throw new NoCurrencyRateFoundException(conversionCtx, currencyFromId, currencyToId);
		}
		return currencyRate;
	}

	private final CurrencyConversionResult createCurrencyConversionResult(@NonNull final CurrencyConversionContext conversionCtx)
	{
		final CurrencyConversionResult result = new CurrencyConversionResult();
		result.setClientId(conversionCtx.getClientId());
		result.setOrgId(conversionCtx.getOrgId());
		result.setConversionDate(conversionCtx.getConversionDate());
		result.setConversionTypeId(conversionCtx.getConversionTypeId());
		return result;
	}

}
