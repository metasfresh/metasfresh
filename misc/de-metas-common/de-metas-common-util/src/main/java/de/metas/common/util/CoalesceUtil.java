/*
 * #%L
 * de-metas-common-util
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

package de.metas.common.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

@UtilityClass
public class CoalesceUtil
{
	/**
	 * @return first not null value from list
	 * @see #coalesce(Object...)
	 */
	@Nullable
	public <T> T coalesce(@Nullable final T value1, @Nullable final T value2)
	{
		return value1 == null ? value2 : value1;
	}

	@NonNull
	public <T> T coalesceNotNull(@Nullable final T value1, @Nullable final T value2)
	{
		final T result = value1 == null ? value2 : value1;
		if (result == null)
		{
			throw new NullPointerException("At least one of value1 or value2 has to be not-null");
		}
		return result;
	}

	@Nullable
	public <T> T coalesce(@Nullable final T value1, @Nullable final Supplier<T> value2)
	{
		return value1 != null ? value1 : (value2 != null ? value2.get() : null);
	}

	@NonNull
	public <T> T coalesceNotNull(@Nullable final T value1, @NonNull final Supplier<T> value2Supplier)
	{
		if(value1 != null)
		{
			return value1;
		}
		else
		{
			final T value2 = value2Supplier.get();
			if (value2 == null)
			{
				throw new NullPointerException("At least one of value1 or value2 has to be not-null");
			}

			return value2;
		}
	}

	/**
	 * @return first not null value from list
	 * @see #coalesce(Object...)
	 */
	// NOTE: this method is optimized for common usage
	@Nullable
	public <T> T coalesce(@Nullable final T value1, @Nullable final T value2, @Nullable final T value3)
	{
		return value1 != null ? value1 : (value2 != null ? value2 : value3);
	}

	@NonNull
	public <T> T coalesceNotNull(@Nullable final T value1, @Nullable final T value2, @Nullable final T value3)
	{
		final T result = value1 != null ? value1 : (value2 != null ? value2 : value3);
		if (result == null)
		{
			throw new NullPointerException("At least one of value1, value2 or value3 has to be not-null");
		}
		return result;
	}

	/**
	 * @return first not null value from list
	 */
	@SafeVarargs
	@Nullable
	public <T> T coalesce(@Nullable final T... values)
	{
		if (values == null || values.length == 0)
		{
			return null;
		}
		for (final T value : values)
		{
			if (value != null)
			{
				return value;
			}
		}
		return null;
	}

	@SafeVarargs
	@NonNull
	public <T> T coalesceNotNull(@Nullable final T... values)
	{
		final T result = coalesce(values);
		if (result == null)
		{
			throw new NullPointerException("At least one parameter has to be not-null");
		}
		return result;
	}

	/**
	 * Similar to {@link #coalesce(Object...)}, but invokes the given suppliers' get methods one by one.
	 * Note that it also works if some of the given supplier themselves are {@code null}.
	 */
	@SafeVarargs
	@Nullable
	public static <T> T coalesceSuppliers(@Nullable final Supplier<T>... values)
	{
		return firstValidValue(Objects::nonNull, values);
	}

	@SafeVarargs
	@NonNull
	public static <T> T coalesceSuppliersNotNull(@NonNull final Supplier<T>... values)
	{
		return Check.assumeNotNull(
				firstValidValue(Objects::nonNull, values),
				"At least one of the given suppliers={} has to return not-null", (Object[])values);
	}

	@SafeVarargs
	@NonNull
	public static <T> Optional<T> optionalOfFirstNonNullSupplied(@Nullable final Supplier<T>... values)
	{
		return Optional.ofNullable(coalesceSuppliers(values));
	}

	
	@SafeVarargs
	@Nullable
	public <T> T firstValidValue(@NonNull final Predicate<T> isValidPredicate, @Nullable final Supplier<T>... values)
	{
		if (values == null || values.length == 0)
		{
			return null;
		}
		for (final Supplier<T> supplier : values)
		{
			if (supplier != null)
			{
				final T value = supplier.get();
				if (isValidPredicate.test(value))
				{
					return value;
				}
			}
		}
		return null;
	}

	/**
	 * Analog to {@link #coalesce(Object...)}, returns the first <code>int</code> value that is greater than 0.
	 *
	 * @return first greater than zero value or zero
	 */
	public int firstGreaterThanZero(final int... values)
	{
		if (values == null || values.length == 0)
		{
			return 0;
		}
		for (final int value : values)
		{
			if (value > 0)
			{
				return value;
			}
		}
		return 0;
	}

	@SafeVarargs
	public int firstGreaterThanZeroSupplier(@NonNull final Supplier<Integer>... suppliers)
	{
		if (suppliers == null || suppliers.length == 0)
		{
			return 0;
		}
		for (final Supplier<Integer> supplier : suppliers)
		{
			final Integer value = supplier.get();
			if (value > 0)
			{
				return value;
			}
		}
		return 0;
	}

	/**
	 * @return the first non-empty string or {@code null}.
	 */
	@Nullable
	public String firstNotEmptyTrimmed(@Nullable final String... values)
	{
		return firstNotBlank(values);
	}

	@Nullable
	public String firstNotBlank(@Nullable final String... values)
	{
		if(values == null || values.length == 0)
		{
			return null;
		}

		for (final String value : values)
		{
			if (value != null && EmptyUtil.isNotBlank(value))
			{
				return value.trim();
			}
		}

		return null;
	}

	@Nullable
	@SafeVarargs
	public String firstNotBlank(@Nullable final Supplier<String>... valueSuppliers)
	{
		if(valueSuppliers == null || valueSuppliers.length == 0)
		{
			return null;
		}

		for (final Supplier<String> valueSupplier : valueSuppliers)
		{
			if(valueSupplier == null)
			{
				continue;
			}

			final String value = valueSupplier.get();
			if (value != null && EmptyUtil.isNotBlank(value))
			{
				return value.trim();
			}
		}

		return null;
	}



	public int countNotNulls(@Nullable final Object... values)
	{
		if (values == null || values.length <= 0)
		{
			return 0;
		}

		int count = 0;
		for (final Object value : values)
		{
			if (value != null)
			{
				count++;
			}
		}

		return count;
	}

	@NonNull
	public BigDecimal firstPositiveOrZero(final BigDecimal... values)
	{
		if (values == null || values.length == 0)
		{
			return BigDecimal.ZERO;
		}
		for (final BigDecimal value : values)
		{
			if (value != null && value.signum() > 0)
			{
				return value;
			}
		}
		return BigDecimal.ZERO;
	}
}
