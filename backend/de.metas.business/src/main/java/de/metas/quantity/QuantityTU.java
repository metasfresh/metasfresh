package de.metas.quantity;

import java.math.BigDecimal;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

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

/**
 * Quantity of TUs (Transport Units).
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@EqualsAndHashCode
public final class QuantityTU implements Comparable<QuantityTU>
{
	@JsonCreator
	public static QuantityTU ofInt(final int value)
	{
		if (value >= 0 && value < cache.length)
		{
			return cache[value];
		}

		return new QuantityTU(value);
	}

	public static QuantityTU ofBigDecimal(@NonNull final BigDecimal value)
	{
		final int valueInt;
		try
		{
			valueInt = value.intValueExact();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Quantity TUs shall be integer: " + value, ex);
		}

		return ofInt(valueInt);
	}

	public static final QuantityTU ZERO = new QuantityTU(0);
	public static final QuantityTU ONE = new QuantityTU(1);

	private static final QuantityTU[] cache = new QuantityTU[] {
			ZERO,
			ONE,
			new QuantityTU(2),
			new QuantityTU(3),
			new QuantityTU(4),
			new QuantityTU(5),
			new QuantityTU(6),
			new QuantityTU(7),
			new QuantityTU(8),
			new QuantityTU(9),
			new QuantityTU(10),
	};

	private final int value;

	private QuantityTU(final int value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@JsonValue
	public int toInt()
	{
		return value;
	}

	public BigDecimal toBigDecimal()
	{
		return BigDecimal.valueOf(value);
	}

	@Override
	public int compareTo(@NonNull final QuantityTU other)
	{
		return this.value - other.value;
	}

	public QuantityTU add(@NonNull final QuantityTU toAdd)
	{
		if (this.value == 0)
		{
			return toAdd;
		}
		else if (toAdd.value == 0)
		{
			return this;
		}
		else
		{
			return ofInt(this.value + toAdd.value);
		}
	}

	public QuantityTU subtract(@NonNull final QuantityTU toSubtract)
	{
		if (toSubtract.value == 0)
		{
			return this;
		}
		else
		{
			return ofInt(this.value - toSubtract.value);
		}
	}
}
