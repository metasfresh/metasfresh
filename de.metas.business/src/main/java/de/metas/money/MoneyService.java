package de.metas.money;

import java.math.BigDecimal;
import java.util.Objects;

import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.currency.ConversionType;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyConversionContext;
import de.metas.currency.ICurrencyConversionResult;
import de.metas.lang.Percent;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.time.SystemTime;
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
	private final CurrencyRepository currencyRepository;

	public MoneyService(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	public Money convertMoneyToCurrency(
			@NonNull final Money money,
			@NonNull final CurrencyId targetCurrencyId)
	{
		if (Objects.equals(
				money.getCurrencyId(),
				targetCurrencyId))
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
				money.getCurrencyId().getRepoId(),
				targetCurrencyId.getRepoId());

		final BigDecimal convertedAmount = Check.assumeNotNull(
				conversionResult.getAmount(),
				"CurrencyConversion from currencyId={} to currencyId={} needs to work; currencyConversionContext={}, currencyConversionResult={}",
				money.getCurrencyId(), targetCurrencyId, currencyConversionContext, conversionResult);

		return Money.of(convertedAmount, targetCurrencyId);
	}

	public Money percentage(@NonNull final Percent percent, @NonNull final Money input)
	{
		if (percent.isOneHundred())
		{
			return input;
		}

		final Currency currency = currencyRepository.getById(input.getCurrencyId());

		final BigDecimal newValue = percent
				.multiply(input.getValue(), currency.getPrecision());

		return Money.of(newValue, input.getCurrencyId());
	}

	public Money subtractPercent(@NonNull final Percent percent, @NonNull final Money input)
	{
		if (percent.isZero())
		{
			return input;
		}

		if (input.isZero())
		{
			return input;
		}

		final Currency currency = currencyRepository.getById(input.getCurrencyId());

		final BigDecimal newValue = percent.subtractFromBase(input.getValue(), currency.getPrecision());
		return Money.of(newValue, input.getCurrencyId());
	}
}
