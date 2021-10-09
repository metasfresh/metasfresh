package de.metas.impexp;

import java.util.ArrayList;
import java.util.function.Supplier;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

@ToString
public class RecordingSupplier<T> implements Supplier<T>
{
	public static <T> RecordingSupplier<T> wrap(@NonNull final Supplier<T> delegate)
	{
		if (delegate instanceof RecordingSupplier)
		{
			return (RecordingSupplier<T>)delegate;
		}
		else
		{
			return new RecordingSupplier<>(delegate);
		}
	}

	private Supplier<T> delegate;

	private final ArrayList<T> previousValues = new ArrayList<>();

	private RecordingSupplier(@NonNull final Supplier<T> delegate)
	{
		this.delegate = delegate;
	}

	@Override
	public T get()
	{
		T value = delegate.get();
		previousValues.add(value);
		return value;
	}

	public ArrayList<T> getPreviousValues()
	{
		return new ArrayList<>(previousValues);
	}
}
