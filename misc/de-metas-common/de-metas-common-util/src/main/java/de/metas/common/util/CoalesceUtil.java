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
import java.util.Objects;
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

	@Nullable
	public <T> T coalesce(@Nullable final T value1, @NonNull final Supplier<T> value2)
	{
		return value1 != null ? value1 : value2.get();
	}

	/**
	 * @return first not null value from list
	 * @see #coalesce(Object...)
	 */
	// NOTE: this method is optimized for common usage
	public <T> T coalesce(final T value1, final T value2, final T value3)
	{
		return value1 != null ? value1 : (value2 != null ? value2 : value3);
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

	/**
	 * Similar to {@link #coalesce(Object...)}, but invokes the given suppliers' get methods one by one.
	 * Note that it also works if some of the given supplier themselves are {@code null}.
	 */
	@SafeVarargs
	public static final <T> T coalesceSuppliers(final Supplier<T>... values)
	{
		return firstValidValue(Objects::nonNull, values);
	}

	@SafeVarargs
	public <T> T firstValidValue(@NonNull final Predicate<T> isValidPredicate, final Supplier<T>... values)
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
	 * @param values
	 * @return first greater than zero value or zero
	 */
	public int firstGreaterThanZero(int... values)
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
	public String firstNotEmptyTrimmed(@NonNull final String... values)
	{
		for (int i = 0; i < values.length; i++)
		{
			if (EmptyUtil.isNotBlank(values[i]))
			{
				return values[i].trim();
			}
		}
		return null;
	}
}
