package de.metas.currency.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_C_ConversionType;
import org.compiere.model.I_C_ConversionType_Default;
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyDAO;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Plain {@link ICurrencyDAO} implementation, exclusively to be used for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class PlainCurrencyDAO extends CurrencyDAO
{
	public PlainCurrencyDAO()
	{
		super();

		if (Adempiere.isUnitTestMode())
		{
			createDefaultConversionTypes();
		}
		else
		{
			throw new IllegalStateException("Service " + PlainCurrencyDAO.class + " shall be used only for testing");
		}
	}

	/**
	 * Creates all {@link I_C_ConversionType}s (from {@link ConversionType}) and sets the {@link ConversionType#Spot} as default.
	 */
	public void createDefaultConversionTypes()
	{
		final Properties ctx = Env.getCtx();
		for (final ConversionType type : ConversionType.values())
		{
			final I_C_ConversionType conversionType = InterfaceWrapperHelper.create(ctx, I_C_ConversionType.class, ITrx.TRXNAME_None);
			InterfaceWrapperHelper.setValue(conversionType, I_C_ConversionType.COLUMNNAME_AD_Client_ID, Env.CTXVALUE_AD_Client_ID_System);
			conversionType.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_System);
			conversionType.setValue(type.getCode());
			conversionType.setName(type.toString());
			InterfaceWrapperHelper.save(conversionType);

			if (type == ConversionType.Spot)
			{
				final I_C_ConversionType_Default conversionTypeDefault = InterfaceWrapperHelper.newInstance(I_C_ConversionType_Default.class, conversionType, true);
				conversionTypeDefault.setC_ConversionType(conversionType);
				conversionTypeDefault.setValidFrom(TimeUtil.getDay(1970, 1, 1));
				InterfaceWrapperHelper.save(conversionTypeDefault);
			}
		}
	}

	/**
	 * Creates/Updates an {@link I_C_Conversion_Rate} using given parameters and following defaults:
	 * <ul>
	 * <li>ValidFrom: 1970-01-01
	 * <li>ConversionType: default
	 * <li>Client/Org: from {@link Env#getCtx()}
	 * </ul>
	 *
	 * @param currencyFrom
	 * @param currencyTo
	 * @param rate
	 */
	public void setRate(final I_C_Currency currencyFrom, final I_C_Currency currencyTo, final BigDecimal rate)
	{
		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);
		final Date date = TimeUtil.getDay(1970, 1, 1);
		final I_C_ConversionType conversionType = retrieveDefaultConversionType(ctx, adClientId, adOrgId, date);
		final ICurrencyConversionContext conversionCtx = CurrencyConversionContext.builder()
				.setAD_Client_ID(adClientId)
				.setAD_Org_ID(adOrgId)
				.setC_ConversionType_ID(conversionType.getC_ConversionType_ID())
				.setConversionDate(date)
				.build();

		I_C_Conversion_Rate conversionRate = retrieveRateQuery(conversionCtx, currencyFrom.getC_Currency_ID(), currencyTo.getC_Currency_ID())
				.create()
				.first();
		if (conversionRate == null)
		{
			conversionRate = InterfaceWrapperHelper.create(ctx, I_C_Conversion_Rate.class, ITrx.TRXNAME_None);
			conversionRate.setAD_Org_ID(adOrgId);
			conversionRate.setC_ConversionType(conversionType);
			conversionRate.setC_Currency(currencyFrom);
			conversionRate.setC_Currency_ID_To(currencyTo.getC_Currency_ID());
			// FIXME: this one is not working due a bug in POJOWrapper or because it's not respecting the standard naming conventions (i.e. C_Currency_To_ID)
			// conversionRate.setC_Currency_To(currencyTo);
			conversionRate.setValidFrom(TimeUtil.asTimestamp(date));
		}

		conversionRate.setMultiplyRate(rate);
		conversionRate.setDivideRate(null); // not used

		InterfaceWrapperHelper.save(conversionRate);
	}

	/**
	 * {@inheritDoc}
	 *
	 * <p>
	 * If the currency was not found, this method is automatically creating it.
	 */
	@Override
	public I_C_Currency retrieveCurrencyByISOCode(final Properties ctx, final String ISOCode)
	{
		I_C_Currency currency = super.retrieveCurrencyByISOCode(ctx, ISOCode);

		// Create the currency if it does not exist.
		// We do this to speed up the test writing.
		if (currency == null)
		{
			currency = InterfaceWrapperHelper.create(ctx, I_C_Currency.class, ITrx.TRXNAME_None);
			currency.setISO_Code(ISOCode);
			currency.setCurSymbol(ISOCode);
			currency.setStdPrecision(2);
			currency.setCostingPrecision(4);
			InterfaceWrapperHelper.save(currency);
		}

		return currency;
	}
}
