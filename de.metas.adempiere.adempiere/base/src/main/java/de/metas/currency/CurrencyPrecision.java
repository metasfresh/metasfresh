package de.metas.currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@EqualsAndHashCode
@ToString
public final class CurrencyPrecision
{
	public static CurrencyPrecision ofInt(final int precision)
	{
		if (precision >= 0 && precision < cachedValues.length)
		{
			return cachedValues[precision];
		}
		else
		{
			return new CurrencyPrecision(precision);
		}
	}

	private static final CurrencyPrecision[] cachedValues = new CurrencyPrecision[] {
			new CurrencyPrecision(0),
			new CurrencyPrecision(1),
			new CurrencyPrecision(2),
			new CurrencyPrecision(3),
			new CurrencyPrecision(4),
			new CurrencyPrecision(5),
			new CurrencyPrecision(6),
			new CurrencyPrecision(7),
			new CurrencyPrecision(8),
			new CurrencyPrecision(9),
			new CurrencyPrecision(10),
			new CurrencyPrecision(11),
			new CurrencyPrecision(12),
	};

	private final int precision;

	private CurrencyPrecision(final int precision)
	{
		Check.assumeGreaterOrEqualToZero(precision, "precision");
		this.precision = precision;
	}

	public BigDecimal roundIfNeeded(@NonNull final BigDecimal amt)
	{
		if (amt.scale() > precision)
		{
			return amt.setScale(precision, RoundingMode.HALF_UP);
		}
		else
		{
			return amt;
		}
	}

	public int toInt()
	{
		return precision;
	}
}
