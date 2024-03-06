package de.metas.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

/*
 * #%L
 * de.metas.util
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

@UtilityClass
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Optionals
{
	@SafeVarargs
	public static <T> Optional<T> firstPresentOfSuppliers(final Supplier<Optional<T>>... suppliers)
	{
		if (suppliers == null || suppliers.length == 0)
		{
			return Optional.empty();
		}

		for (final Supplier<Optional<T>> supplier : suppliers)
		{
			final Optional<T> optionalValue = supplier.get();
			if (optionalValue != null && optionalValue.isPresent())
			{
				return optionalValue;
			}
		}

		return Optional.empty();
	}

	/**
	 * To be used until we upgrade to Java9 where we have Optional.stream
	 */
	public static <T> Stream<T> stream(@NonNull final Optional<T> optional)
	{
		//noinspection OptionalIsPresent
		return optional.isPresent() ? Stream.of(optional.get()) : Stream.empty();
	}
}
