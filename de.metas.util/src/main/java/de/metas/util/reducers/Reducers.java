package de.metas.util.reducers;

import java.util.List;
import java.util.function.Function;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class Reducers
{
	public static <T> SingleValueReducer<T> singleValue()
	{
		@SuppressWarnings("unchecked")
		final SingleValueReducer<T> instanceCasted = (SingleValueReducer<T>)SingleValueReducer.DEFAULT;
		return instanceCasted;
	}

	public static <T> SingleValueReducer<T> singleValue(@NonNull final Function<List<T>, ? extends RuntimeException> exceptionFactory)
	{
		return new SingleValueReducer<>(exceptionFactory);
	}

}
