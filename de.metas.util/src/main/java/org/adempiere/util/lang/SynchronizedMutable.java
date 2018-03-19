package org.adempiere.util.lang;

import java.util.function.Function;
import java.util.function.Supplier;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

public final class SynchronizedMutable<T> implements IMutable<T>
{
	public static <T> SynchronizedMutable<T> of(final T value)
	{
		return new SynchronizedMutable<>(value);
	}

	private T value;

	private SynchronizedMutable(final T value)
	{
		this.value = value;
	}

	@Override
	public synchronized T getValue()
	{
		return value;
	}

	@Override
	public synchronized void setValue(final T value)
	{
		this.value = value;
	}

	@Override
	public synchronized T compute(@NonNull final Function<T, T> remappingFunction)
	{
		this.value = remappingFunction.apply(this.value);
		return this.value;
	}

	public synchronized T computeIfNull(@NonNull final Supplier<T> supplier)
	{
		if (value == null)
		{
			this.value = supplier.get();
		}

		return this.value;
	}

	public synchronized T computeIfNotNull(@NonNull final Function<T, T> remappingFunction)
	{
		if (value != null)
		{
			this.value = remappingFunction.apply(this.value);
		}

		return this.value;
	}

}
