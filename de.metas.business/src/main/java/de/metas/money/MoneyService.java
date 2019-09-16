package de.metas.money;

import java.math.BigDecimal;
import java.util.Objects;

import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import de.metas.currency.Amount;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
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

		final CurrencyConversionContext currencyConversionContext = currencyBL
				.createCurrencyConversionContext(
						SystemTime.asLocalDate(),
						ConversionTypeMethod.Spot,
						Env.getClientId(),
						Env.getOrgId());

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				currencyConversionContext,
				money.toBigDecimal(),
				money.getCurrencyId(),
				targetCurrencyId);

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
				.computePercentageOf(input.toBigDecimal(), currency.getPrecision().toInt());

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

		final BigDecimal newValue = percent.subtractFromBase(input.toBigDecimal(), currency.getPrecision().toInt());
		return Money.of(newValue, input.getCurrencyId());
	}

	public ITranslatableString toTranslatableString(@NonNull final Money money)
	{
		final Currency currency = currencyRepository.getById(money.getCurrencyId());

		return TranslatableStrings.builder()
				.append(money.toBigDecimal(), DisplayType.Amount)
				.append(" ")
				.append(currency.getCurrencyCode().toThreeLetterCode())
				.build();
	}

	public Amount toAmount(@NonNull final Money money)
	{
		return money.toAmount(currencyId -> currencyRepository.getById(currencyId).getCurrencyCode());
	}

	public Money multiply(
			@NonNull final Quantity qty,
			@NonNull final ProductPrice price)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Quantity qtyInPriceUnit = uomConversionBL.convertQuantityTo(
				qty,
				UOMConversionContext.of(price.getProductId()), price.getUomId());
		return multiply(qtyInPriceUnit, price.toMoney());
	}

	public Money multiply(@NonNull final Quantity qty, @NonNull final Money money)
	{

		final CurrencyPrecision currencyPrecision = currencyRepository
				.getById(money.getCurrencyId())
				.getPrecision();

		final BigDecimal moneyAmount = money.toBigDecimal();
		final BigDecimal netAmt = qty.toBigDecimal().multiply(moneyAmount);

		return Money.of(
				currencyPrecision.round(netAmt),
				money.getCurrencyId());
	}
}
