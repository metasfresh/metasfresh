package de.metas.currency;

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

import org.compiere.model.I_C_Currency;

import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.util.ISingletonService;

/**
 * Currency conversion services.
 * 
 * @author tsa
 * 
 */
public interface ICurrencyBL extends ISingletonService
{
	int DEFAULT_ConversionType_ID = 0;

	ICurrencyConversionContext createCurrencyConversionContext(Date ConvDate, int ConversionType_ID, int AD_Client_ID, int AD_Org_ID);

	ICurrencyConversionContext createCurrencyConversionContext(Date ConvDate, ConversionType conversionType, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Gets base currency of AD_Client and AD_Org which are set in context.
	 * 
	 * @param ctx
	 * @return currency
	 */
	I_C_Currency getBaseCurrency(Properties ctx);

	/**
	 * Gets base currency of given AD_Client and AD_Org
	 * 
	 * @param ctx
	 * @return currency
	 */
	I_C_Currency getBaseCurrency(Properties ctx, int adClientId, int adOrgId);

	/**
	 * Convert an amount to base Currency
	 * 
	 * @param ctx context
	 * @param CurFrom_ID The C_Currency_ID FROM
	 * @param ConvDate conversion date - if null - use current date
	 * @param C_ConversionType_ID conversion rate type - if 0 - use Default
	 * @param Amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount
	 */
	BigDecimal convertBase(Properties ctx, BigDecimal Amt, int CurFrom_ID, Timestamp ConvDate, int C_ConversionType_ID, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Convert an amount
	 * 
	 * @param ctx context
	 * @param CurFrom_ID The C_Currency_ID FROM
	 * @param CurTo_ID The C_Currency_ID TO
	 * @param ConvDate conversion date - if null - use current date
	 * @param C_ConversionType_ID conversion rate type - if 0 - use Default
	 * @param Amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount or null if no rate
	 */
	BigDecimal convert(Properties ctx, BigDecimal Amt, int CurFrom_ID, int CurTo_ID, Timestamp ConvDate, int C_ConversionType_ID, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Convert an amount with today's default rate
	 * 
	 * @param ctx context
	 * @param CurFrom_ID The C_Currency_ID FROM
	 * @param CurTo_ID The C_Currency_ID TO
	 * @param Amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount
	 */
	BigDecimal convert(Properties ctx, BigDecimal Amt, int CurFrom_ID, int CurTo_ID, int AD_Client_ID, int AD_Org_ID);

	ICurrencyConversionResult convert(ICurrencyConversionContext conversionCtx, BigDecimal Amt, int CurFrom_ID, int CurTo_ID);

	/**
	 * Get Currency Conversion Rate
	 * 
	 * @param CurFrom_ID The C_Currency_ID FROM
	 * @param CurTo_ID The C_Currency_ID TO
	 * @param ConvDate The Conversion date - if null - use current date
	 * @param ConversionType_ID Conversion rate type - if 0 - use Default
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return currency Rate or null
	 */
	BigDecimal getRate(int CurFrom_ID, int CurTo_ID, Timestamp ConvDate, int ConversionType_ID, int AD_Client_ID, int AD_Org_ID);

	BigDecimal getRate(ICurrencyConversionContext conversionCtx, int CurFrom_ID, int CurTo_ID);

	ICurrencyRate getCurrencyRateOrNull(ICurrencyConversionContext conversionCtx, int CurFrom_ID, int CurTo_ID);

	/**
	 * 
	 * @param conversionCtx
	 * @param currencyFromId
	 * @param currencyToId
	 * @return currency rate; never returns null
	 * @throws NoCurrencyRateFoundException
	 */
	ICurrencyRate getCurrencyRate(ICurrencyConversionContext conversionCtx, int currencyFromId, int currencyToId);
}
