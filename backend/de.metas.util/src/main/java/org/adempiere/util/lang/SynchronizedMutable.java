package org.adempiere.util.lang;

import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

	@Override
	public synchronized T computeIfNull(@NonNull final Supplier<T> supplier)
	{
		if (value == null)
		{
			this.value = supplier.get();
		}

		return this.value;
	}

	public synchronized T computeIfNotNull(@NonNull final UnaryOperator<T> remappingFunction)
	{
		if (value != null)
		{
			this.value = remappingFunction.apply(this.value);
		}

		return this.value;
	}

	@Value
	@Builder
	public static class OldAndNewValues<T>
	{
		T oldValue;
		T newValue;

		public boolean isValueChanged()
		{
			return !Objects.equals(oldValue, newValue);
		}
	}

	public synchronized OldAndNewValues<T> computeIfNotNullReturningOldAndNew(@NonNull final UnaryOperator<T> remappingFunction)
	{
		if (value != null)
		{
			final T oldValue = this.value;
			this.value = remappingFunction.apply(oldValue);
			return OldAndNewValues.<T> builder()
					.oldValue(oldValue)
					.newValue(this.value)
					.build();
		}
		else
		{
			return OldAndNewValues.<T> builder()
					.oldValue(this.value)
					.newValue(this.value)
					.build();
		}
	}

}
