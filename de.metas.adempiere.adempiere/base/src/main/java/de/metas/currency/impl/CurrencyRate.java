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
import java.util.Date;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.currency.ICurrencyRate;

public final class CurrencyRate implements ICurrencyRate
{
	private final BigDecimal conversionRate;
	private final int fromCurrencyId;
	private final int toCurrencyId;
	private final int conversionTypeId;
	private final Date conversionDate;
	private final int currencyPrecision;

	public CurrencyRate(final BigDecimal conversionRate,
			final int fromCurrencyId, final int toCurrencyId,
			final int currencyPrecision,
			final int conversionTypeId,
			final Date conversionDate)
	{
		super();

		Check.assumeNotNull(conversionRate, "conversionRate not null");
		this.conversionRate = conversionRate;

		Check.assume(fromCurrencyId > 0, "fromCurrencyId > 0");
		this.fromCurrencyId = fromCurrencyId;
		Check.assume(toCurrencyId > 0, "toCurrencyId > 0");
		this.toCurrencyId = toCurrencyId;

		Check.assume(conversionTypeId > 0, "conversionTypeId > 0");
		this.conversionTypeId = conversionTypeId;

		Check.assumeNotNull(conversionDate, "conversionDate not null");
		this.conversionDate = conversionDate;

		this.currencyPrecision = currencyPrecision;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public final BigDecimal convertAmount(final BigDecimal amount)
	{
		return convertAmount(amount, getCurrencyPrecision());
	}

	@Override
	public final BigDecimal convertAmount(final BigDecimal amount, final int precision)
	{
		Check.assumeNotNull(amount, "amount not null");

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
	public int getC_ConversionType_ID()
	{
		return conversionTypeId;
	}

	@Override
	public Date getConversionDate()
	{
		return conversionDate;
	}

	private int getCurrencyPrecision()
	{
		return currencyPrecision;
	}
}
