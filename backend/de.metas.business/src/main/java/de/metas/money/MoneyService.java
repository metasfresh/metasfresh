/*
 * #%L
 * de.metas.business
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

package de.metas.money;

import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.currency.ConversionTypeMethod;
import de.metas.currency.Currency;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyConversionResult;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRate;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.ICurrencyBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UOMConversionContext;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.adempiere.service.ClientId;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;

@Service
public class MoneyService implements CurrencyCodeToCurrencyIdBiConverter
{
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final CurrencyRepository currencyRepository;

	public MoneyService(@NonNull final CurrencyRepository currencyRepository)
	{
		this.currencyRepository = currencyRepository;
	}

	@Override
	public CurrencyId getCurrencyIdByCurrencyCode(@NonNull final CurrencyCode currencyCode)
	{
		return currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);
	}

	@Override
	public CurrencyCode getCurrencyCodeByCurrencyId(@NonNull final CurrencyId currencyId)
	{
		return currencyRepository.getCurrencyCodeById(currencyId);
	}

	public CurrencyId getBaseCurrencyId(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return currencyBL.getBaseCurrencyId(clientAndOrgId.getClientId(), clientAndOrgId.getOrgId());
	}

	public CurrencyCode getBaseCurrencyCode(@NonNull final ClientAndOrgId clientAndOrgId)
	{
		return currencyRepository.getCurrencyCodeById(getBaseCurrencyId(clientAndOrgId));
	}

	public CurrencyPrecision getStdPrecision(@NonNull final CurrencyCode currencyCode)
	{
		return currencyRepository.getStdPrecision(currencyCode);
	}

	public CurrencyPrecision getStdPrecision(@NonNull final CurrencyId currencyId)
	{
		return currencyRepository.getStdPrecision(currencyId);
	}

	@NonNull
	public CurrencyConversionContext createConversionContext(
			@NonNull final LocalDateAndOrgId convDate,
			@Nullable final CurrencyConversionTypeId conversionTypeId,
			@NonNull final ClientId clientId)
	{
		return currencyBL.createCurrencyConversionContext(convDate, conversionTypeId, clientId);
	}

	/**
	 * @deprecated Please use {@link #convertMoneyToCurrency(Money, CurrencyId, CurrencyConversionContext)} instead.
	 */
	@Deprecated
	@NonNull
	public Money convertMoneyToCurrency(
			@NonNull final Money money,
			@NonNull final CurrencyId targetCurrencyId)
	{
		if (Objects.equals(money.getCurrencyId(), targetCurrencyId))
		{
			return money;
		}

		final CurrencyConversionContext currencyConversionContext = currencyBL.createCurrencyConversionContext(
				SystemTime.asInstant(),
				Env.getClientId(),
				Env.getOrgId());

		return convertMoneyToCurrency(money, targetCurrencyId, currencyConversionContext);
	}

	@NonNull
	public Money convertMoneyToCurrency(
			@NonNull final Money money,
			@NonNull final CurrencyCode currencyCode,
			@NonNull final CurrencyConversionContext context)
	{
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(currencyCode);

		return convertMoneyToCurrency(money, currencyId, context);
	}

	@NonNull
	public Money convertMoneyToCurrency(
			@NonNull final Money money,
			@NonNull final CurrencyId targetCurrencyId,
			@NonNull final CurrencyConversionContext context)
	{
		if (Objects.equals(money.getCurrencyId(), targetCurrencyId))
		{
			return money;
		}

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				context,
				money.toBigDecimal(),
				money.getCurrencyId(),
				targetCurrencyId);

		final BigDecimal convertedAmount = Check.assumeNotNull(
				conversionResult.getAmount(),
				"CurrencyConversion from currencyId={} to currencyId={} needs to work; currencyConversionContext={}, currencyConversionResult={}",
				money.getCurrencyId(), targetCurrencyId, context, conversionResult);

		return Money.of(convertedAmount, targetCurrencyId);
	}

	@NonNull
	public Amount convertToCurrency(
			@NonNull final Amount amount,
			@NonNull final CurrencyCode targetCurrencyCode,
			@NonNull final CurrencyConversionContext context)
	{
		final CurrencyCode fromCurrencyCode = amount.getCurrencyCode();
		if (Objects.equals(fromCurrencyCode, targetCurrencyCode))
		{
			return amount;
		}

		final CurrencyId fromCurrencyId = currencyRepository.getCurrencyIdByCurrencyCode(fromCurrencyCode);
		final CurrencyId targetCurrencyId = currencyRepository.getCurrencyIdByCurrencyCode(targetCurrencyCode);

		final CurrencyConversionResult conversionResult = currencyBL.convert(
				context,
				amount.toBigDecimal(),
				fromCurrencyId,
				targetCurrencyId);

		final BigDecimal convertedAmount = Check.assumeNotNull(
				conversionResult.getAmount(),
				"CurrencyConversion from currency={}({}) to currencyId={}({}) needs to work; currencyConversionContext={}, currencyConversionResult={}",
				fromCurrencyCode, fromCurrencyId, targetCurrencyCode, targetCurrencyId, context, conversionResult);

		return Amount.of(convertedAmount, targetCurrencyCode);
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
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(money.getCurrencyId());

		return TranslatableStrings.builder()
				.append(money.toBigDecimal(), DisplayType.Amount)
				.append(" ")
				.append(currencyCode.toThreeLetterCode())
				.build();
	}

	public Amount toAmount(@NonNull final Money money)
	{
		return money.toAmount(currencyRepository::getCurrencyCodeById);
	}

	public Amount toAmount(@NonNull final BigDecimal value, @NonNull final CurrencyId currencyId)
	{
		final CurrencyCode currencyCode = currencyRepository.getCurrencyCodeById(currencyId);
		return Amount.of(value, currencyCode);
	}

	public Money toMoney(@NonNull final Amount amount)
	{
		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(amount.getCurrencyCode());
		return Money.of(amount.getAsBigDecimal(), currencyId);
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

	public CurrencyRate getCurrencyRate(
			@NonNull final CurrencyCode fromCurrency,
			@NonNull final CurrencyCode targetCurrency,
			@NonNull final CurrencyConversionContext context)
	{
		final CurrencyId fromCurrencyId = currencyRepository.getCurrencyIdByCurrencyCode(fromCurrency);
		final CurrencyId targetCurrencyId = currencyRepository.getCurrencyIdByCurrencyCode(targetCurrency);
		return currencyBL.getCurrencyRate(context, fromCurrencyId, targetCurrencyId);
	}

	public CurrencyRate getCurrencyRate(
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId targetCurrencyId,
			@NonNull final CurrencyConversionContext context)
	{
		return currencyBL.getCurrencyRate(context, fromCurrencyId, targetCurrencyId);
	}

	public CurrencyConversionTypeId getCurrencyConversionTypeId(@NonNull final ConversionTypeMethod type)
	{
		return currencyBL.getCurrencyConversionTypeId(type);
	}
}
