/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.common.util;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumberUtils
{
	@Nullable
	public static List<BigDecimal> asBigDecimalListOrNull(@Nullable final String value, @NonNull final String separator)
	{
		if (Check.isBlank(value))
		{
			return null;
		}

		return Arrays.stream(value.split(separator))
				.map(NumberUtils::asBigDecimal)
				.collect(Collectors.toList());
	}

	@Nullable
	private static BigDecimal asBigDecimal(@Nullable final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((int)value);
		}
		else if (value instanceof Long)
		{
			return BigDecimal.valueOf((long)value);
		}
		else
		{
			final String valueStr = value.toString();
			if(EmptyUtil.isBlank(valueStr))
			{
				return null;
			}
			try
			{
				return new BigDecimal(valueStr.trim());
			}
			catch (final NumberFormatException numberFormatException)
			{
				final String errorMsg = "Cannot convert `" + value + "` (" + value.getClass() + ") to BigDecimal";

				final RuntimeException ex = Check.mkEx(errorMsg);
				ex.initCause(numberFormatException);
				throw ex;
			}
		}
	}
}
