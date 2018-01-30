package de.metas.util.stream;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.adempiere.util.collections.IteratorUtils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public final class StreamUtils
{
	/**
	 * Dices given stream in smaller chunks, each of them having maximum <code>chunkSize</code> elements.
	 */
	public static <T> Stream<List<T>> dice(@NonNull final Stream<T> stream, final int chunkSize)
	{
		if (chunkSize <= 0)
		{
			throw new IllegalArgumentException("chunkSize shall be > 0");
		}

		final Iterator<T> streamIterator = stream.iterator();
		return IteratorUtils.stream(() -> readChunkOrNull(streamIterator, chunkSize));
	}

	private static final <T> List<T> readChunkOrNull(final Iterator<T> iterator, final int chunkSize)
	{
		final List<T> list = new ArrayList<>(chunkSize);
		while (iterator.hasNext() && list.size() < chunkSize)
		{
			list.add(iterator.next());
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
}
