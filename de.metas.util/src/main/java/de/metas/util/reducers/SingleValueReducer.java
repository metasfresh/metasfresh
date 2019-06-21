package de.metas.util.reducers;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import de.metas.util.Check;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2019 metas GmbH
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

@ToString
final class SingleValueReducer<T> implements BinaryOperator<T>
{
	static final transient SingleValueReducer<Object> DEFAULT = new SingleValueReducer<>();

	private final Function<List<T>, ? extends RuntimeException> exceptionFactory;

	private SingleValueReducer()
	{
		this.exceptionFactory = values -> Check.mkEx("Distinct values are not allowed: " + values);
	}

	SingleValueReducer(@NonNull final Function<List<T>, ? extends RuntimeException> exceptionFactory)
	{
		this.exceptionFactory = exceptionFactory;
	}

	@Override
	public T apply(@NonNull final T value1, @NonNull final T value2)
	{
		if (Objects.equals(value1, value2))
		{
			return value1;
		}
		else
		{
			throw exceptionFactory.apply(Arrays.asList(value1, value2));
		}
	}
}
