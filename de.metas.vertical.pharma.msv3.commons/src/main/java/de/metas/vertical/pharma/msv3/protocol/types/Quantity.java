package de.metas.vertical.pharma.msv3.protocol.types;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class Quantity
{
	@JsonCreator
	public static Quantity of(final int value)
	{
		if (value == 0)
		{
			return ZERO;
		}
		return new Quantity(value);
	}

	public static Quantity of(@NonNull final BigDecimal qty)
	{
		return of(qty.intValueExact());
	}

	public static final Quantity ZERO = new Quantity(0);

	private static final int MAX_VALUE = 99999;

	private final int valueAsInt;

	private Quantity(final int value)
	{
		if (value < 0)
		{
			throw new IllegalArgumentException("Quantity shall be greater than " + value);
		}
		if (value > MAX_VALUE)
		{
			throw new IllegalArgumentException("The MSV3 standard allows a maximum quantity of " + value);
		}

		valueAsInt = value;
	}

	public Quantity min(final Quantity otherQty)
	{
		return valueAsInt <= otherQty.valueAsInt ? this : otherQty;
	}

	public Quantity min(final int otherQty)
	{
		return valueAsInt <= otherQty ? this : Quantity.of(otherQty);
	}

	public BigDecimal getValueAsBigDecimal()
	{
		return BigDecimal.valueOf(valueAsInt);
	}

	@JsonValue
	public int toJson()
	{
		return valueAsInt;
	}

	public boolean isZero()
	{
		return valueAsInt == 0;
	}
}
