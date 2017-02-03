package org.adempiere.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

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
 *
 * @author based on https://gist.github.com/JakeWharton/9734167
 * @author metas-dev <dev@metasfresh.com>
 */
public final class GuavaCollectors
{
	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 */
	public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList()
	{
		return Collector.of(
				ImmutableList.Builder::new // supplier
				, ImmutableList.Builder::add // accumulator
				, (l, r) -> l.addAll(r.build()) // combiner
				, ImmutableList.Builder<T>::build // finisher
		);
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 *
	 * This method is excluding duplicates.
	 */
	public static <T> Collector<T, ?, ImmutableList<T>> toImmutableListExcludingDuplicates()
	{
		// NOTE: internally we use a LinkedHashSet accumulator to preserve the order.

		final Supplier<LinkedHashSet<T>> supplier = LinkedHashSet::new;
		final BiConsumer<LinkedHashSet<T>, T> accumulator = LinkedHashSet::add;
		final BinaryOperator<LinkedHashSet<T>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<LinkedHashSet<T>, ImmutableList<T>> finisher = ImmutableList::copyOf;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 * 
	 * Duplicates will be automatically discarded.
	 *
	 * @param keyFunction key function for identifying duplicates
	 */
	public static <T, K> Collector<T, ?, ImmutableList<T>> toImmutableListExcludingDuplicates(final Function<T, K> keyFunction)
	{
		final BiConsumer<K, T> duplicatesConsumer = (key, duplicate) -> {
		};
		return toImmutableListExcludingDuplicates(keyFunction, duplicatesConsumer);
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 *
	 * @param keyFunction key function for identifying duplicates
	 * @param duplicates consumer; it takes the duplicate key and item as parameters
	 */
	public static <T, K> Collector<T, ?, ImmutableList<T>> toImmutableListExcludingDuplicates(final Function<T, K> keyFunction, final BiConsumer<K, T> duplicatesConsumer)
	{
		// NOTE: internally we use a LinkedHashMap accumulator to preserve the order and also to identify and report duplicates.

		final Supplier<LinkedHashMap<K, T>> supplier = LinkedHashMap::new;
		final BiConsumer<LinkedHashMap<K, T>, T> accumulator = (map, item) -> {
			final K key = keyFunction.apply(item);
			if (map.containsKey(key))
			{
				duplicatesConsumer.accept(key, item);
				return;
			}

			map.put(key, item);
		};
		final BinaryOperator<LinkedHashMap<K, T>> combiner = (map1, map2) -> {
			if (map2.isEmpty())
			{
				return map1;
			}
			for (final Map.Entry<K, T> e2 : map2.entrySet())
			{
				final K key2 = e2.getKey();
				final T value = e2.getValue();
				if (map1.containsKey(key2))
				{
					duplicatesConsumer.accept(key2, value);
					continue;
				}

				map1.put(key2, value);
			}
			map1.putAll(map2);
			return map1;
		};
		final Function<LinkedHashMap<K, T>, ImmutableList<T>> finisher = (map) -> ImmutableList.copyOf(map.values());
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableSet}.
	 */
	public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSet()
	{
		return Collector.of(
				ImmutableSet.Builder::new // supplier
				, ImmutableSet.Builder::add // accumulator
				, (l, r) -> l.addAll(r.build()) // combiner
				, ImmutableSet.Builder<T>::build // finisher
				, Collector.Characteristics.UNORDERED // characteristics
		);
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableSet}.
	 *
	 * @param duplicateConsumer consumer to be called in case a duplicate was found
	 */
	public static <T> Collector<T, ?, ImmutableSet<T>> toImmutableSetHandlingDuplicates(final Consumer<T> duplicateConsumer)
	{
		// NOTE: internally we use a LinkedHashSet accumulator to preserve the order.

		final Supplier<LinkedHashSet<T>> supplier = LinkedHashSet::new;
		final BiConsumer<LinkedHashSet<T>, T> accumulator = (accum, item) -> {
			final boolean added = accum.add(item);
			if (!added)
			{
				duplicateConsumer.accept(item);
			}
		};
		final BinaryOperator<LinkedHashSet<T>> combiner = (leftAccum, rightAccum) -> {
			for (final T item : rightAccum)
			{
				final boolean added = leftAccum.add(item);
				if (!added)
				{
					duplicateConsumer.accept(item);
				}
			}
			return leftAccum;
		};
		final Function<LinkedHashSet<T>, ImmutableSet<T>> finisher = ImmutableSet::copyOf;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	/**
	 * Collect items and join them to String using given <code>joiner</code>.
	 *
	 * @param joiner
	 * @return collector
	 */
	public static <T> Collector<T, ?, String> toString(final Joiner joiner)
	{
		final Supplier<List<T>> supplier = ArrayList::new;
		final BiConsumer<List<T>, T> accumulator = List::add;
		final BinaryOperator<List<T>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<List<T>, String> finisher = joiner::join;
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <K, V> Map.Entry<K, V> entry(final K key, final V value)
	{
		return new Map.Entry<K, V>()
		{
			@Override
			public K getKey()
			{
				return key;
			}

			@Override
			public V getValue()
			{
				return value;
			}

			@Override
			public V setValue(final V value)
			{
				throw new UnsupportedOperationException();
			}
		};
	}

	public static <K, V> Collector<Entry<K, V>, ?, ImmutableMap<K, V>> toImmutableMap()
	{
		final Supplier<ImmutableMap.Builder<K, V>> supplier = ImmutableMap.Builder::new;
		final BiConsumer<ImmutableMap.Builder<K, V>, Entry<K, V>> accumulator = (builder, entry) -> builder.put(entry);
		final BinaryOperator<ImmutableMap.Builder<K, V>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> finisher = (builder) -> builder.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	/**
	 * Collects to {@link ImmutableMap}.
	 *
	 * If duplicate key was found, the last provided item will be used.
	 *
	 * @param keyMapper
	 * @return immutable map collector
	 */
	public static <K, V> Collector<V, ?, ImmutableMap<K, V>> toImmutableMapByKey(final Function<? super V, ? extends K> keyMapper)
	{
		final Supplier<ImmutableMap.Builder<K, V>> supplier = ImmutableMap.Builder::new;
		final BiConsumer<ImmutableMap.Builder<K, V>, V> accumulator = (builder, item) -> {
			final K key = keyMapper.apply(item);
			builder.put(key, item);
		};
		final BinaryOperator<ImmutableMap.Builder<K, V>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableMap.Builder<K, V>, ImmutableMap<K, V>> finisher = (builder) -> builder.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toMap(final Supplier<Map<K, V>> mapSupplier)
	{
		final BiConsumer<Map<K, V>, Entry<K, V>> accumulator = (map, entry) -> map.put(entry.getKey(), entry.getValue());
		final BinaryOperator<Map<K, V>> combiner = (map1, map2) -> {
			map1.putAll(map2);
			return map1;
		};
		final Function<Map<K, V>, Map<K, V>> finisher = (map) -> map;
		return Collector.of(mapSupplier, accumulator, combiner, finisher);
	}

	public static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toHashMap()
	{
		return toMap(HashMap::new);
	}
	
	public static <K, V> Collector<Entry<K, V>, ?, Map<K, V>> toLinkedHashMap()
	{
		return toMap(LinkedHashMap::new);
	}

	public static <K, V> Collector<V, ?, ImmutableListMultimap<K, V>> toImmutableListMultimap(final Function<? super V, ? extends K> keyMapper)
	{
		final Supplier<ImmutableListMultimap.Builder<K, V>> supplier = ImmutableListMultimap.Builder::new;
		final BiConsumer<ImmutableListMultimap.Builder<K, V>, V> accumulator = (builder, item) -> builder.put(keyMapper.apply(item), item);
		final BinaryOperator<ImmutableListMultimap.Builder<K, V>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableListMultimap.Builder<K, V>, ImmutableListMultimap<K, V>> finisher = (builder) -> builder.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <K, V> Collector<Map.Entry<K, V>, ?, ImmutableListMultimap<K, V>> toImmutableListMultimap()
	{
		final Supplier<ImmutableListMultimap.Builder<K, V>> supplier = ImmutableListMultimap.Builder::new;
		final BiConsumer<ImmutableListMultimap.Builder<K, V>, Map.Entry<K, V>> accumulator = (builder, entry) -> builder.put(entry);
		final BinaryOperator<ImmutableListMultimap.Builder<K, V>> combiner = (builder1, builder2) -> builder1.putAll(builder2.build());
		final Function<ImmutableListMultimap.Builder<K, V>, ImmutableListMultimap<K, V>> finisher = (builder) -> builder.build();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	private GuavaCollectors()
	{
	}
}
