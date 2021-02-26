/*
 * #%L
 * de-metas-common-filemaker
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

package de.metas.common.filemaker;

import java.math.BigDecimal;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.Value;

@Value
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, isGetterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class COL
{
	public static COL of(final String data)
	{
		return new COL(new DATA(data));
	}

	@JsonProperty("DATA")
	DATA data;

	@JsonCreator
	public COL(@JsonProperty("DATA") final DATA data)
	{
		this.data = data;
	}

	public String getValueAsString()
	{
		return data != null ? data.getValue() : null;
	}

	public <T> T getValue(@NonNull final Function<String, T> mapper)
	{
		final String valueStr = getValueAsString();
		if (valueStr == null)
		{
			return null;
		}

		return mapper.apply(valueStr);
	}

	public BigDecimal getValueAsBigDecimal()
	{
		return getValue(COL::toBigDecimal);
	}

	private static BigDecimal toBigDecimal(final String valueStr)
	{
		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}

		try
		{
			return new BigDecimal(valueStr);
		}
		catch (final Exception e)
		{
			return null;
		}
	}

	public Integer getValueAsInteger()
	{
		return getValue(COL::toInteger);
	}

	private static Integer toInteger(final String valueStr)
	{
		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}

		try
		{
			return Integer.parseInt(valueStr);
		}
		catch (final Exception e)
		{
			return null;
		}
	}
}
