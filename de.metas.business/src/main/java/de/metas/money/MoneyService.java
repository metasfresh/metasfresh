package de.metas.money;

import java.math.BigDecimal;
import java.util.Objects;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyConversionResult;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Service
public class MoneyService
{
	public Money convertMoneyToCurrency(
			@NonNull final Money money,
			@NonNull final Currency targetCurrency)
	{
		if (Objects.equals(
				money.getCurrency().getId(),
				targetCurrency.getId()))
		{
			return money;
		}

		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final ICurrencyConversionContext currencyConversionContext = currencyBL
				.createCurrencyConversionContext(
						SystemTime.asDate(),
						ConversionType.Spot,
						Env.getAD_Client_ID(),
						Env.getAD_Org_ID(Env.getCtx()));

		final ICurrencyConversionResult conversionResult = currencyBL.convert(
				currencyConversionContext,
				money.getValue(),
				money.getCurrency().getId().getRepoId(),
				targetCurrency.getId().getRepoId());

		final BigDecimal convertedAmount = Check.assumeNotNull(
				conversionResult.getAmount(),
				"CurrencyConversion from currency={} to currency={} needs to work; currencyConversionContext={}, currencyConversionResult={}",
				money.getCurrency(), targetCurrency, currencyConversionContext, conversionResult);

		return Money.of(convertedAmount, targetCurrency);
	}
}
