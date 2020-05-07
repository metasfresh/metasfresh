package de.metas.util;

import java.util.function.Supplier;

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

/**
 * Wraps a possible exception (optional) which might be obtained from a supplier (deferred).
 * 
 * @param <T> exception type
 */
@ToString
public class OptionalDeferredException<T extends Throwable>
{
	public static <T extends Throwable> OptionalDeferredException<T> noError()
	{
		@SuppressWarnings("unchecked")
		final OptionalDeferredException<T> noError = (OptionalDeferredException<T>)NO_ERROR;
		return noError;
	}

	public static <T extends Throwable> OptionalDeferredException<T> of(@NonNull final Supplier<T> exceptionSupplier)
	{
		return new OptionalDeferredException<>(exceptionSupplier);
	}

	private static final OptionalDeferredException<Throwable> NO_ERROR = new OptionalDeferredException<>();
	private final Supplier<T> exceptionSupplier;

	private OptionalDeferredException()
	{
		exceptionSupplier = null;
	}

	private OptionalDeferredException(@NonNull final Supplier<T> exceptionSupplier)
	{
		this.exceptionSupplier = exceptionSupplier;
	}

	public boolean isNoError()
	{
		return exceptionSupplier == null;
	}

	public void throwIfError() throws T
	{
		if (exceptionSupplier == null)
		{
			return;
		}

		final T exception = exceptionSupplier.get();
		if (exception == null)
		{
			return;
		}

		throw exception;
	}
}
