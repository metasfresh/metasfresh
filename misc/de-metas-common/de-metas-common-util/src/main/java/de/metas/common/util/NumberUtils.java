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

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

public class NumberUtils
{
	@NonNull
	public static ImmutableList<BigDecimal> asBigDecimalList(@Nullable final String value, @NonNull final String separator)
	{
		if (Check.isBlank(value))
		{
			return ImmutableList.of();
		}

		return Arrays.stream(value.split(separator))
				.map(NumberUtils::asBigDecimal)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public static BigDecimal asBigDecimal(@Nullable final Object value)
	{
		final boolean failIfNotParseable = true;
		final BigDecimal defaultValue = null;
		return asBigDecimal(value, defaultValue, failIfNotParseable);
	}

	@Nullable
	public static BigDecimal asBigDecimal(@Nullable final Object value, @Nullable final BigDecimal defaultValue)
	{
		final boolean failIfNotParseable = false;
		return asBigDecimal(value, defaultValue, failIfNotParseable);
	}

	public static int asInt(@NonNull final Object value)
	{
		if (value instanceof Integer)
		{
			return (int)value;
		}
		else if (value instanceof BigDecimal)
		{
			return ((BigDecimal)value).intValueExact();
		}
		else if (value instanceof Long)
		{
			return BigDecimal.valueOf((long)value).intValueExact();
		}
		else
		{
			final String valueStr = StringUtils.trimBlankToNull(value.toString());
			if (valueStr == null)
			{
				throw Check.mkEx("Cannot convert empty `" + value + "` (" + value.getClass() + ") to int");
			}

			try
			{
				return Integer.parseInt(valueStr);
			}
			catch (final NumberFormatException numberFormatException)
			{
				throw Check.mkEx("Cannot convert `" + value + "` (" + value.getClass() + ") to int", numberFormatException);
			}
		}
	}

	public static BigDecimal roundToBigDecimal(@NonNull final BigDecimal argToRound, @NonNull final BigDecimal roundingArg) {
		BigDecimal actualRoundingArg = roundingArg;
		BigDecimal actualArgToRound = argToRound;
		BigDecimal decimalAdjustments = BigDecimal.ONE;
		if (roundingArg.scale() > 0 || argToRound.scale() > 0)
		{
			final int scaleToConsider = Integer.max(roundingArg.scale(), argToRound.scale());
			decimalAdjustments = new BigDecimal(String.valueOf(Math.pow(10, scaleToConsider)));
			actualRoundingArg = roundingArg.multiply(decimalAdjustments);
			actualArgToRound = argToRound.multiply(decimalAdjustments);
		}

		return actualArgToRound
				.divide(actualRoundingArg, 0, RoundingMode.HALF_UP)
				.multiply(actualRoundingArg)
				.divide(decimalAdjustments, argToRound.scale(), RoundingMode.HALF_UP);
	}

	@Nullable
	private static BigDecimal asBigDecimal(
			@Nullable final Object value,
			@Nullable final BigDecimal defaultValue,
			final boolean failIfUnparsable)
	{
		if (value == null) //note that a zero-BigDecimal is also "empty" according to Check.IsEmpty()!
		{
			return defaultValue;
		}
		if (value instanceof BigDecimal)
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
			if (Check.isBlank(valueStr))
			{
				return defaultValue;
			}
			try
			{
				return new BigDecimal(valueStr.trim());
			}
			catch (final NumberFormatException numberFormatException)
			{
				final String errorMsg = "Cannot convert `" + value + "` (" + value.getClass() + ") to BigDecimal";

				if (failIfUnparsable)
				{
					final RuntimeException ex = Check.mkEx(errorMsg);
					ex.initCause(numberFormatException);
					throw ex;
				}
				else
				{
					System.err.println(errorMsg + ". Returning defaultValue=" + defaultValue);
					numberFormatException.printStackTrace();
					return defaultValue;
				}
			}
		}
	}

	@Nullable
	public static BigDecimal sumNullSafe(@Nullable final BigDecimal ag1, @Nullable final BigDecimal ag2)
	{
		if (ag1 == null && ag2 == null)
		{
			return null;
		}
		else if (ag1 == null)
		{
			return ag2;
		}
		else if (ag2 == null)
		{
			return ag1;
		}
		else
		{
			return ag1.add(ag2);
		}
	}

}
