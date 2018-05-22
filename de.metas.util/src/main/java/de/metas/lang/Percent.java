package de.metas.lang;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.util.Check;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.util
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

@Value
public class Percent
{
	/**
	 * @param value 100 based percent
	 */
	public static Percent of(final BigDecimal value)
	{
		if (value.signum() == 0)
		{
			return ZERO;
		}
		else if (ONE_HUNDRED.getValueAsBigDecimal().compareTo(value) == 0)
		{
			return ONE_HUNDRED;
		}
		else
		{
			return new Percent(value);
		}
	}

	public static Percent ofNullable(final BigDecimal value)
	{
		return value != null ? of(value) : null;
	}

	/**
	 * @param value 100 based percent
	 */
	public static Percent of(final int value)
	{
		return of(BigDecimal.valueOf(value));
	}

	public static final Percent ZERO = new Percent(BigDecimal.ZERO);
	private static final BigDecimal ONE_HUNDRED_VALUE = BigDecimal.valueOf(100);
	public static final Percent ONE_HUNDRED = new Percent(ONE_HUNDRED_VALUE);

	private final BigDecimal valueAsBigDecimal;

	private Percent(@NonNull final BigDecimal valueAsBigDecimal)
	{
		this.valueAsBigDecimal = valueAsBigDecimal;
	}

	public boolean isZero()
	{
		return valueAsBigDecimal.signum() == 0;
	}

	public boolean isOneHundred()
	{
		return ONE_HUNDRED_VALUE.compareTo(valueAsBigDecimal) == 0;
	}

	public Percent add(@NonNull final Percent percent)
	{
		if (isZero())
		{
			return percent;
		}
		else if (percent.isZero())
		{
			return this;
		}
		else
		{
			return of(valueAsBigDecimal.add(percent.valueAsBigDecimal));
		}
	}

	public Percent subtract(@NonNull final Percent percent)
	{
		if (percent.isZero())
		{
			return this;
		}
		return of(this.valueAsBigDecimal.subtract(percent.valueAsBigDecimal));
	}

	public BigDecimal multiply(@NonNull final BigDecimal base, final int precision)
	{
		Check.assumeGreaterOrEqualToZero(precision, "precision");

		if (base.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		else if (isZero())
		{
			return BigDecimal.ZERO;
		}
		else if (isOneHundred())
		{
			return base.setScale(precision, RoundingMode.HALF_UP);
		}
		else
		{
			return base
					.setScale(precision + 2)
					.divide(ONE_HUNDRED_VALUE, RoundingMode.UNNECESSARY)
					.multiply(valueAsBigDecimal)
					.setScale(precision, RoundingMode.HALF_UP);
		}
	}

	public BigDecimal subtractFromBase(@NonNull final BigDecimal base, final int precision)
	{
		Check.assumeGreaterOrEqualToZero(precision, "precision");

		if (base.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		else if (isZero())
		{
			return base.setScale(precision, RoundingMode.HALF_UP);
		}
		else if (isOneHundred())
		{
			return BigDecimal.ZERO;
		}
		else
		{
			return base
					.setScale(precision + 2)
					.divide(ONE_HUNDRED_VALUE, RoundingMode.UNNECESSARY)
					.multiply(ONE_HUNDRED_VALUE.subtract(valueAsBigDecimal))
					.setScale(precision, RoundingMode.HALF_UP);
		}
	}
}
