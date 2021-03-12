package org.adempiere.util.lang;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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
 * Defines a mutable value reference
 * 
 * @author tsa
 * 
 * @param <T>
 */
public interface IMutable<T> extends IReference<T>
{
	/**
	 * @return reference value
	 */
	@Override
	@Nullable
	T getValue();

	/**
	 * Sets reference value
	 */
	void setValue(@Nullable T value);

	/**
	 * @param remappingFunction function which takes the current value as input and which shall return the new value
	 */
	default T compute(@NonNull final UnaryOperator<T> remappingFunction)
	{
		final T valueOld = getValue();
		final T valueNew = remappingFunction.apply(valueOld);
		setValue(valueNew);
		return valueNew;
	}

	default T computeIfNull(@NonNull final Supplier<T> supplier)
	{
		final T valueOld = getValue();
		if (valueOld == null)
		{
			final T valueNew = supplier.get();
			setValue(valueNew);
			return valueNew;
		}
		else
		{
			return valueOld;
		}
	}
}
