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
import java.math.RoundingMode;
import java.time.LocalDate;

import de.metas.currency.ICurrencyRate;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

@ToString
public final class CurrencyRate implements ICurrencyRate
{
	private final BigDecimal conversionRate;
	private final int fromCurrencyId;
	private final int toCurrencyId;
	private final CurrencyConversionTypeId conversionTypeId;
	private final LocalDate conversionDate;
	private final int currencyPrecision;

	public CurrencyRate(
			@NonNull final BigDecimal conversionRate,
			final int fromCurrencyId,
			final int toCurrencyId,
			final int currencyPrecision,
			@NonNull final CurrencyConversionTypeId conversionTypeId,
			@NonNull final LocalDate conversionDate)
	{
		Check.assume(fromCurrencyId > 0, "fromCurrencyId > 0");
		Check.assume(toCurrencyId > 0, "toCurrencyId > 0");

		this.conversionRate = conversionRate;
		this.fromCurrencyId = fromCurrencyId;
		this.toCurrencyId = toCurrencyId;
		this.conversionTypeId = conversionTypeId;
		this.conversionDate = conversionDate;
		this.currencyPrecision = currencyPrecision;
	}

	@Override
	public final BigDecimal convertAmount(final BigDecimal amount)
	{
		return convertAmount(amount, getCurrencyPrecision());
	}

	@Override
	public final BigDecimal convertAmount(@NonNull final BigDecimal amount, final int precision)
	{
		final BigDecimal rate = getConversionRate();
		BigDecimal amountConv = rate.multiply(amount);

		if (precision >= 0 && amountConv.scale() > precision)
		{
			amountConv = amountConv.setScale(precision, RoundingMode.HALF_UP);
		}

		return amountConv;
	}

	@Override
	public BigDecimal getConversionRate()
	{
		return conversionRate;
	}

	@Override
	public int getFrom_Currency_ID()
	{
		return fromCurrencyId;
	}

	@Override
	public int getTo_Currency_ID()
	{
		return toCurrencyId;
	}

	@Override
	public CurrencyConversionTypeId getConversionTypeId()
	{
		return conversionTypeId;
	}

	@Override
	public LocalDate getConversionDate()
	{
		return conversionDate;
	}

	private int getCurrencyPrecision()
	{
		return currencyPrecision;
	}
}
