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
import java.time.LocalDate;

import de.metas.money.CurrencyConversionTypeId;
import de.metas.money.CurrencyId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public final class CurrencyRate
{
	BigDecimal conversionRate;
	CurrencyId fromCurrencyId;
	CurrencyId toCurrencyId;
	CurrencyConversionTypeId conversionTypeId;
	LocalDate conversionDate;
	CurrencyPrecision currencyPrecision;

	@Builder
	private CurrencyRate(
			@NonNull final BigDecimal conversionRate,
			@NonNull final CurrencyId fromCurrencyId,
			@NonNull final CurrencyId toCurrencyId,
			@NonNull final CurrencyPrecision currencyPrecision,
			@NonNull final CurrencyConversionTypeId conversionTypeId,
			@NonNull final LocalDate conversionDate)
	{
		this.conversionRate = conversionRate;
		this.fromCurrencyId = fromCurrencyId;
		this.toCurrencyId = toCurrencyId;
		this.conversionTypeId = conversionTypeId;
		this.conversionDate = conversionDate;
		this.currencyPrecision = currencyPrecision;
	}

	public final BigDecimal convertAmount(final BigDecimal amount)
	{
		return convertAmount(amount, getCurrencyPrecision());
	}

	public final BigDecimal convertAmount(@NonNull final BigDecimal amount, @NonNull final CurrencyPrecision precision)
	{
		final BigDecimal rate = getConversionRate();
		final BigDecimal amountConv = rate.multiply(amount);
		return precision.roundIfNeeded(amountConv);
	}
}
