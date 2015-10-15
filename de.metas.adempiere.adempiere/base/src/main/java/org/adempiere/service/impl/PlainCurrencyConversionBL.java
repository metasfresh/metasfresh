package org.adempiere.service.impl;

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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.currency.ICurrencyConversionContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Util.ArrayKey;

public class PlainCurrencyConversionBL extends CurrencyConversionBL
{
	private String defaultCurrencyIsoCode = "CHF";
	private int defaultConversionTypeId = 12345; // just a dummy value is enough

	@Override
	public I_C_Currency getBaseCurrency(Properties ctx, int adClientId, int adOrgId)
	{
		return getCreateCurrency(ctx, defaultCurrencyIsoCode);
	}

	private final Map<ArrayKey, BigDecimal> rates = new HashMap<ArrayKey, BigDecimal>();

	@Override
	protected BigDecimal retrieveRateOrNull(final ICurrencyConversionContext conversionCtx, final int CurFrom_ID, final int CurTo_ID)
	{
		if (CurFrom_ID == CurTo_ID)
		{
			return BigDecimal.ONE;
		}

		final ArrayKey key = new ArrayKey(CurFrom_ID, CurTo_ID);
		final BigDecimal rate = rates.get(key);
		if (rate == null)
		{
			throw new RuntimeException("Rate not found for currency=" + CurFrom_ID + "->" + CurTo_ID);
		}

		return rate;
	}
	
	@Override
	protected int retrieveDefaultConversionTypeId(final int adClientId)
	{
		return defaultConversionTypeId;
	}

	
	@Override
	protected int getCurrencyPrecision(final int currencyId)
	{
		return getCurrencyById(currencyId).getStdPrecision();
	}


	public void setRate(final I_C_Currency currencyFrom, final I_C_Currency currencyTo, final BigDecimal rate)
	{
		final ArrayKey key = new ArrayKey(currencyFrom.getC_Currency_ID(), currencyTo.getC_Currency_ID());
		rates.put(key, rate);
	}

	public I_C_Currency getCreateCurrency(final Properties ctx, final String isoCode)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		I_C_Currency currency = db.getFirstOnly(I_C_Currency.class, new IQueryFilter<I_C_Currency>()
		{

			@Override
			public boolean accept(I_C_Currency pojo)
			{
				return Check.equals(pojo.getISO_Code(), isoCode);
			}
		});
		if (currency == null)
		{
			currency = db.newInstance(ctx, I_C_Currency.class);
			currency.setISO_Code(isoCode);
			currency.setCurSymbol(isoCode);
			currency.setStdPrecision(2);
			currency.setCostingPrecision(4);
			InterfaceWrapperHelper.save(currency);
		}
		return currency;
	}

	public I_C_Currency getCurrencyById(final int currencyId)
	{
		final POJOLookupMap db = POJOLookupMap.get();
		return db.lookup(I_C_Currency.class, currencyId);
	}
}
