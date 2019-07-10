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
import org.compiere.util.TimeUtil;

import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

/**
 * Currency conversion services.
 *
 * @author tsa
 *
 */
public interface ICurrencyBL extends ISingletonService
{
	CurrencyConversionContext createCurrencyConversionContext(
			Date ConvDate,
			CurrencyConversionTypeId ConversionType_ID,
			int AD_Client_ID,
			int AD_Org_ID);

	default CurrencyConversionContext createCurrencyConversionContext(
			final LocalDate ConvDate,
			final CurrencyConversionTypeId ConversionType_ID,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId)
	{
		return createCurrencyConversionContext(TimeUtil.asDate(ConvDate), ConversionType_ID, clientId.getRepoId(), orgId.getRepoId());
	}

	CurrencyConversionContext createCurrencyConversionContext(
			Date ConvDate, 
			ConversionType conversionType, 
			int AD_Client_ID, 
			int AD_Org_ID);

	/**
	 * @return base currency of AD_Client and AD_Org which are set in context.
	 */
	Currency getBaseCurrency(Properties ctx);

	/**
	 * @return base currency of given client and org
	 */
	Currency getBaseCurrency(ClientId adClientId, OrgId adOrgId);

	/**
	 * @return base currency ID of given client and org
	 */
	CurrencyId getBaseCurrencyId(ClientId adClientId, OrgId adOrgId);

	/**
	 * Convert an amount to base Currency
	 *
	 * @param ctx context
	 * @param currencyFromId The C_Currency_ID FROM
	 * @param ConvDate conversion date - if null - use current date
	 * @param C_ConversionType_ID conversion rate type - if 0 - use Default
	 * @param amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount
	 */
	@Deprecated
	BigDecimal convertBase(
			BigDecimal amt,
			CurrencyId currencyFromId,
			Timestamp ConvDate,
			int C_ConversionType_ID,
			int AD_Client_ID,
			int AD_Org_ID);

	/**
	 * Convert an amount
	 *
	 * @param currencyFromId The C_Currency_ID FROM
	 * @param currencyToId The C_Currency_ID TO
	 * @param ConvDate conversion date - if null - use current date
	 * @param C_ConversionType_ID conversion rate type - if 0 - use Default
	 * @param amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount or null if no rate
	 */
	@Deprecated
	BigDecimal convert(
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			Timestamp ConvDate,
			int C_ConversionType_ID,
			int AD_Client_ID,
			int AD_Org_ID);

	/**
	 * Convert an amount with today's default rate
	 *
	 * @param currencyFromId The C_Currency_ID FROM
	 * @param currencyToId The C_Currency_ID TO
	 * @param amt amount to be converted
	 * @param AD_Client_ID client
	 * @param AD_Org_ID organization
	 * @return converted amount
	 */
	@Deprecated
	BigDecimal convert(
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			int AD_Client_ID,
			int AD_Org_ID);

	CurrencyConversionResult convert(
			CurrencyConversionContext conversionCtx,
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId);

	/**
	 * @return currency Rate or null
	 */
	BigDecimal getRate(
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			Timestamp ConvDate,
			int ConversionType_ID,
			int AD_Client_ID,
			int AD_Org_ID);

	BigDecimal getRate(CurrencyConversionContext conversionCtx, CurrencyId currencyFromId, CurrencyId currencyToId);

	/**
	 *
	 * @param conversionCtx
	 * @param currencyFromId
	 * @param currencyToId
	 * @return currency rate; never returns null
	 * @throws NoCurrencyRateFoundException
	 */
	CurrencyRate getCurrencyRate(CurrencyConversionContext conversionCtx, CurrencyId currencyFromId, CurrencyId currencyToId);
}
