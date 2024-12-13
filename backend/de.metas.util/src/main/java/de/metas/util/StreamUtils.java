/*
 * #%L
 * de.metas.util
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

package de.metas.util;

<<<<<<< HEAD
=======
import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.PeekIterator;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
>>>>>>> de339ff2c6 (PostFinance Export reduce memory usage (#19613))
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class StreamUtils
{
	/**
	 * Dices given stream in smaller chunks, each of them having maximum <code>chunkSize</code> elements.
	 */
	public static <T> Stream<List<T>> dice(@NonNull final Stream<T> stream, final int chunkSize)
	{
		return dice(stream, chunkSize, null);
	}

	/**
	 * Dices given stream in smaller chunks, each of them having maximum <code>chunkSize</code> elements. Items should be ordered by groupKey
	 */
	public static <T> Stream<List<T>> dice(@NonNull final Stream<T> stream, final int maxChunkSize, @Nullable final Function<T, Object> groupByKeyExtractor)
	{
		if (maxChunkSize <= 0)
		{
			throw new IllegalArgumentException("chunkSize shall be > 0");
		}

		final PeekIterator<T> streamIterator = IteratorUtils.asPeekIterator(stream.iterator());
		return IteratorUtils.stream(() -> readChunkOrNull(streamIterator, maxChunkSize, groupByKeyExtractor));
	}

<<<<<<< HEAD
	private static final <T> List<T> readChunkOrNull(final Iterator<T> iterator, final int chunkSize)
=======
	@Nullable
	private static <T> List<T> readChunkOrNull(
			@NonNull final PeekIterator<T> iterator,
			final int maxChunkSize,
			@Nullable final Function<T, Object> groupByKeyExtractor)
>>>>>>> de339ff2c6 (PostFinance Export reduce memory usage (#19613))
	{
		Object previousGroupKey = null;
		boolean isFirstItem = true;

		final List<T> list = new ArrayList<>(maxChunkSize);
		while (iterator.hasNext() && list.size() < maxChunkSize)
		{
			final T item = iterator.peek();

			if(groupByKeyExtractor != null)
			{
				final Object groupKey = groupByKeyExtractor.apply(item);
				if (isFirstItem)
				{
					previousGroupKey = groupKey;
				}
				else if (!Objects.equals(previousGroupKey, groupKey))
				{
					break;
				}
			}
			list.add(iterator.next());

			isFirstItem = false;
		}

		return !list.isEmpty() ? list : null;
	}

	/**
	 * Thanks to https://stackoverflow.com/a/27872852/1012103
	 */
	public static <T> Predicate<T> distinctByKey(@NonNull final Function<? super T, Object> keyExtractor)
	{
		final Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	@SafeVarargs
	public static <T> Stream<T> concat(final Stream<T>... streams)
	{
		if (streams.length == 0)
		{
			return Stream.empty();
		}
		else if (streams.length == 1)
		{
			return streams[0];
		}
		else if (streams.length == 2)
		{
			return Stream.concat(streams[0], streams[1]);
		}
		else
		{
			Stream<T> result = streams[0];
			for (int i = 1; i < streams.length; i++)
			{
				result = Stream.concat(result, streams[i]);
			}
			return result;
		}
	}
}
