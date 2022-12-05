package org.adempiere.util.lang;

import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

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
	public static <T> SynchronizedMutable<T> of(@Nullable final T value)
	{
		return new SynchronizedMutable<>(value);
	}

	public static <T> SynchronizedMutable<T> empty()
	{
		return new SynchronizedMutable<>(null);
	}

	@Nullable
	private T value;

	private SynchronizedMutable(@Nullable final T value)
	{
		this.value = value;
	}

	@Nullable
	@Override
	public synchronized T getValue()
	{
		return value;
	}

	@Override
	public synchronized void setValue(@Nullable final T value)
	{
		this.value = value;
	}

	@Nullable
	public synchronized T setValueAndReturnPrevious(final T value)
	{
		final T previousValue = this.value;
		this.value = value;
		return previousValue;
	}

	@Override
	public synchronized T compute(@NonNull final UnaryOperator<T> remappingFunction)
	{
		this.value = remappingFunction.apply(this.value);
		return this.value;
	}

	@SuppressWarnings("unused")
	public synchronized OldAndNewValues<T> computeReturningOldAndNew(@NonNull final UnaryOperator<T> remappingFunction)
	{
		final T oldValue = this.value;
		final T newValue = this.value = remappingFunction.apply(oldValue);
		return OldAndNewValues.<T>builder()
				.oldValue(oldValue)
				.newValue(newValue)
				.build();

	}

	@Override
	public synchronized T computeIfNull(@NonNull final Supplier<T> supplier)
	{
		if (value == null)
		{
			this.value = supplier.get();
		}

		return this.value;
	}

	@Nullable
	public synchronized T computeIfNotNull(@NonNull final UnaryOperator<T> remappingFunction)
	{
		if (value != null)
		{
			this.value = remappingFunction.apply(this.value);
		}

		return this.value;
	}

	public synchronized OldAndNewValues<T> computeIfNotNullReturningOldAndNew(@NonNull final UnaryOperator<T> remappingFunction)
	{
		if (value != null)
		{
			final T oldValue = this.value;
			this.value = remappingFunction.apply(oldValue);
			return OldAndNewValues.<T>builder()
					.oldValue(oldValue)
					.newValue(this.value)
					.build();
		}
		else
		{
			return OldAndNewValues.nullValues();
		}
	}

}
