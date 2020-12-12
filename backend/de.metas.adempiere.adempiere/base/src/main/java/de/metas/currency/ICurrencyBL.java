package de.metas.currency;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

import de.metas.currency.exceptions.NoCurrencyRateFoundException;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

/**
 * Currency conversion services.
 *
 * @author tsa
 */
public interface ICurrencyBL extends ISingletonService
{
	@NonNull CurrencyConversionContext createCurrencyConversionContext(
			@Nullable LocalDate convDate,
			@Nullable CurrencyConversionTypeId conversionTypeId,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	@NonNull CurrencyConversionContext createCurrencyConversionContext(
			@NonNull LocalDate convDate,
			@Nullable ConversionTypeMethod conversionType,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	/**
	 * @return base currency of AD_Client and AD_Org which are set in context.
	 */
	Currency getBaseCurrency(Properties ctx);

	/**
	 * @return base currency of given client and org
	 */
	Currency getBaseCurrency(
			ClientId adClientId,
			OrgId adOrgId);

	/**
	 * @return base currency ID of given client and org
	 */
	CurrencyId getBaseCurrencyId(
			ClientId adClientId,
			OrgId adOrgId);

	@Deprecated
	@NonNull
	BigDecimal convertBase(
			BigDecimal amt,
			CurrencyId currencyFromId,
			LocalDate convDate,
			CurrencyConversionTypeId conversionTypeId,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	@Deprecated
	@NonNull
	BigDecimal convert(
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			LocalDate convDate,
			CurrencyConversionTypeId conversionTypeId,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	/**
	 * Convert an amount with today's default rate
	 *
	 * @param currencyFromId The C_Currency_ID FROM
	 * @param currencyToId   The C_Currency_ID TO
	 * @param amt            amount to be converted
	 * @param clientId       client
	 * @param orgId          organization
	 * @return converted amount
	 */
	@Deprecated
	@NonNull
	BigDecimal convert(
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	@NonNull
	CurrencyConversionResult convert(
			CurrencyConversionContext conversionCtx,
			BigDecimal amt,
			CurrencyId currencyFromId,
			CurrencyId currencyToId);

	@NonNull
	default CurrencyConversionResult convert(
			@NonNull final CurrencyConversionContext conversionCtx,
			@NonNull final Money amt,
			@NonNull final CurrencyId currencyToId)
	{
		return convert(conversionCtx, amt.toBigDecimal(), amt.getCurrencyId(), currencyToId);
	}

	Optional<CurrencyRate> getCurrencyRateIfExists(
			CurrencyId currencyFromId,
			CurrencyId currencyToId,
			LocalDate convDate,
			CurrencyConversionTypeId conversionTypeId,
			ClientId clientId,
			OrgId orgId);

	CurrencyRate getCurrencyRate(
			@NonNull CurrencyId currencyFromId,
			@NonNull CurrencyId currencyToId,
			@Nullable LocalDate convDate,
			@Nullable CurrencyConversionTypeId conversionTypeId,
			@NonNull ClientId clientId,
			@NonNull OrgId orgId);

	/**
	 * @throws NoCurrencyRateFoundException
	 */
	@NonNull
	CurrencyRate getCurrencyRate(
			CurrencyConversionContext conversionCtx,
			CurrencyId currencyFromId,
			CurrencyId currencyToId);

	@NonNull
	CurrencyCode getCurrencyCodeById(@NonNull CurrencyId currencyId);

}
