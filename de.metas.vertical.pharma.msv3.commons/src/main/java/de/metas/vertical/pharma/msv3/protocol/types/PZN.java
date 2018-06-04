package de.metas.vertical.pharma.msv3.protocol.types;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

/**
 * PZN (Pharma-Zentral-Nummer) is a code for medicine identification in Germany and maybe other countries.
 * 
 * @see https://www.activebarcode.com/codes/pzn.html
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class PZN
{
	@JsonCreator
	public static PZN of(final long value)
	{
		return new PZN(value);
	}

	private final long valueAsLong;

	private PZN(final long value)
	{
		if (value <= 0)
		{
			throw new IllegalArgumentException("Invalid PZN value: " + value);
		}

		this.valueAsLong = value;
	}

	public String getValueAsString()
	{
		return String.valueOf(valueAsLong);
	}

	@JsonValue
	public long toJson()
	{
		return valueAsLong;
	}
}
