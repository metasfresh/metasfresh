package org.adempiere.util.lang;

/*
 * #%L
 * de.metas.util
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;

import de.metas.util.Check;

/**
 * Wraps a {@link BigDecimal} and makes it mutable.
 * 
 * @author tsa
 *
 */
public final class MutableBigDecimal extends Number implements IMutable<BigDecimal>
{
	/**
	 *
	 */
	private static final long serialVersionUID = -171154938877066205L;

	private BigDecimal value;

	public MutableBigDecimal(final BigDecimal value)
	{
		super();
		Check.assumeNotNull(value, "value not null");
		this.value = value;
	}

	public MutableBigDecimal(final Number value)
	{
		this(value == null ? (BigDecimal)null : new BigDecimal(value.toString()));
	}

	public MutableBigDecimal()
	{
		this(BigDecimal.ZERO);
	}

	@Override
	public String toString()
	{
		return value.toString();
	};

	@Override
	public BigDecimal getValue()
	{
		return value;
	}

	@Override
	public void setValue(final BigDecimal value)
	{
		if (value instanceof BigDecimal)
		{
			this.value = value;
		}
		else
		{
			this.value = new BigDecimal(value.toString());
		}
	}

	@Override
	public byte byteValue()
	{
		return value.byteValue();
	}

	@Override
	public double doubleValue()
	{
		return value.doubleValue();
	}

	@Override
	public float floatValue()
	{
		return value.floatValue();
	}

	@Override
	public int intValue()
	{
		return value.intValue();
	}

	@Override
	public long longValue()
	{
		return value.longValue();
	}

	@Override
	public short shortValue()
	{
		return value.shortValue();
	}

	public void add(final BigDecimal augend)
	{
		value = value.add(augend);
	}

	public void subtract(final BigDecimal subtrahend)
	{
		value = value.subtract(subtrahend);
	}

	public void multiply(final BigDecimal multiplicand)
	{
		value = value.multiply(multiplicand);
	}

	public void divide(final BigDecimal divisor, final int scale, final RoundingMode roundingMode)
	{
		value = value.divide(divisor, scale, roundingMode);
	}

	public boolean comparesEqualTo(final BigDecimal val)
	{
		return value.compareTo(val) == 0;
	}

	public int signum()
	{
		return value.signum();
	}

	public MutableBigDecimal min(final MutableBigDecimal value)
	{
		if (this.value.compareTo(value.getValue()) <= 0)
		{
			return this;
		}
		else
		{
			return value;
		}
	}

	public MutableBigDecimal max(final MutableBigDecimal value)
	{
		if (this.value.compareTo(value.getValue()) >= 0)
		{
			return this;
		}
		else
		{
			return value;
		}
	}
}
