package org.adempiere.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import com.google.common.base.MoreObjects;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Miscellaneous {@link Function} helpers.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class Functions
{
	private Functions()
	{
		throw new IllegalStateException();
	}

	/**
	 * Returns a function which caches the instance retrieved during the first
	 * call to {@code apply(input)} and returns that value on subsequent calls to
	 * {@code apply(input)}.
	 * See: <a href="http://en.wikipedia.org/wiki/Memoization">memoization</a>
	 *
	 * <p>
	 * The returned function is thread-safe.
	 *
	 * <p>
	 * If {@code delegate} is an instance created by an earlier call to {@code memoizing}, it is returned directly.
	 */
	public static final <T, R> Function<T, R> memoizing(final Function<T, R> delegate)
	{
		if (delegate instanceof MemoizingFunction)
		{
			return delegate;
		}
		return new MemoizingFunction<>(delegate);
	}

	private static final class MemoizingFunction<T, R> implements Function<T, R>
	{
		private final Function<T, R> delegate;
		private final transient ConcurrentHashMap<T, R> values = new ConcurrentHashMap<>();

		private MemoizingFunction(final Function<T, R> delegate)
		{
			super();
			Check.assumeNotNull(delegate, "Parameter function is not null");
			this.delegate = delegate;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("memoizing").addValue(delegate).toString();
		}

		@Override
		public R apply(final T input)
		{
			return values.computeIfAbsent(input, delegate);
		}
	}
}
