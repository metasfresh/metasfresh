package org.adempiere.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;

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
	public static final <T, R> MemoizingFunction<T, R> memoizing(final Function<T, R> delegate)
	{
		if (delegate instanceof SimpleMemoizingFunction)
		{
			return (SimpleMemoizingFunction<T, R>)delegate;
		}
		return new SimpleMemoizingFunction<>(delegate);
	}

	/**
	 * Same as {@link #memoizing(Function)} but the <code>keyFunction</code> will be internally used to index the cached results.
	 */
	public static final <T, R, K> MemoizingFunction<T, R> memoizing(final Function<T, R> delegate, final Function<T, K> keyFunction)
	{
		return new MemoizingFunctionWithKeyExtractor<>(delegate, keyFunction);
	}

	/**
	 * Same as {@link #memoizing(Function)} but the value of the first call will be memorized.
	 *
	 * This might look similar to {@link ExtendedMemorizingSupplier} with the difference that in this case,
	 * on first call the function will be called with <code>input</code> parameter which can be used for some initializations.
	 */
	public static final <T, R> MemoizingFunction<T, R> memoizingFirstCall(final Function<T, R> delegate)
	{
		final Function<T, Boolean> keyFunction = input -> Boolean.TRUE;
		return new MemoizingFunctionWithKeyExtractor<>(delegate, keyFunction);
	}

	/**
	 * Function which memorize it's calls, so repeated calls with same inputs will be served from it's internal cache.
	 *
	 * IMPORTANT: some implementations might use some "key function" internally, which derives the given input and produced a cache. The value will be stored for that key.
	 * So in this case, the function will serve from it's internal cache if there is already a precalculated result for the key which is derived from given <code>input</code>.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 * @param <T> input type
	 * @param <R> result type
	 */
	public static interface MemoizingFunction<T, R> extends Function<T, R>
	{
		@Override
		R apply(final T input);

		/**
		 * @param input
		 * @return memorized value or <code>null</code>
		 */
		R peek(final T input);
	}

	private static class MemoizingFunctionWithKeyExtractor<T, R, K> implements MemoizingFunction<T, R>
	{
		private final Function<T, R> delegate;
		private final Function<T, K> keyFunction;
		private final transient ConcurrentHashMap<K, R> values = new ConcurrentHashMap<>();

		private MemoizingFunctionWithKeyExtractor(final Function<T, R> delegate, final Function<T, K> keyFunction)
		{
			super();
			Check.assumeNotNull(delegate, "Parameter function is not null");
			Check.assumeNotNull(keyFunction, "Parameter keyFunction is not null");
			this.delegate = delegate;
			this.keyFunction = keyFunction;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper("memoizing")
					.addValue(delegate)
					.add("keyFunction", keyFunction)
					.toString();
		}

		@Override
		public R apply(final T input)
		{
			return values.computeIfAbsent(keyFunction.apply(input), k -> delegate.apply(input));
		}

		@Override
		public R peek(final T input)
		{
			return values.get(keyFunction.apply(input));
		}
	}

	private static final class SimpleMemoizingFunction<T, R> extends MemoizingFunctionWithKeyExtractor<T, R, T>
	{
		private SimpleMemoizingFunction(final Function<T, R> delegate)
		{
			super(delegate, input -> input);
		}

	}

}
