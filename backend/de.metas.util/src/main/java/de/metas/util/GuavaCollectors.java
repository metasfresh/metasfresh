package de.metas.util;

import com.google.common.base.Joiner;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimaps;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * @author based on <a href="https://gist.github.com/JakeWharton/9734167">GIST</a>
 * @author metas-dev <dev@metasfresh.com>
 */
@UtilityClass
public final class GuavaCollectors
{
	public static <T> Collector<T, ?, ArrayList<T>> toArrayList()
	{
		return Collector.of(
				ArrayList::new,
				ArrayList::add,
				(acc1, acc2) -> {
					acc1.addAll(acc2);
					return acc1;
				},
				Function.identity());
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 */
	public static <T> Collector<T, ?, ImmutableList<T>> toImmutableList()
	{
		return ImmutableList.toImmutableList();
	}

	/**
	 * Collect a stream of elements into an {@link ImmutableList}.
	 * <p>
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
	 * <p>
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
	 * @param keyFunction        key function for identifying duplicates
	 * @param duplicatesConsumer consumer that takes the duplicate key and item as parameters
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
		return ImmutableSet.toImmutableSet();
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

	public static <K, V> ImmutableMapEntry<K, V> entry(final K key, final V value)
	{
		return ImmutableMapEntry.of(key, value);
	}

	public static <K, V> Collector<Entry<K, V>, ?, ImmutableMap<K, V>> toImmutableMap()
	{
		return ImmutableMap.toImmutableMap(Entry::getKey, Entry::getValue);
	}

	/**
	 * Collects to {@link ImmutableMap}.
	 * <p>
	 * If duplicate key was found, the last provided item will be used.
	 *
	 * @return immutable map collector
	 * @see #toImmutableMapByKeyKeepFirstDuplicate(Function)
	 */
	public static <K, V> Collector<V, ?, ImmutableMap<K, V>> toImmutableMapByKey(final Function<? super V, ? extends K> keyMapper)
	{
		// NOTE: before changing the "duplicates" behavior please check the callers first!
		return ImmutableMap.toImmutableMap(keyMapper, value -> value, (valuePrev, valueNow) -> valueNow);
	}

	/**
	 * Collects to {@link HashMap}.
	 * <p>
	 * If duplicate key was found, the last provided item will be used.
	 *
	 * @return {@link HashMap} collector
	 */
	public static <K, V> Collector<V, ?, HashMap<K, V>> toHashMapByKey(final Function<? super V, ? extends K> keyMapper)
	{
		// NOTE: before changing the "duplicates" behavior please check the callers first!
		final Function<V, V> valueMapper = value -> value;
		final BinaryOperator<V> mergeFunction = (valuePrev, valueNow) -> valueNow;
		return Collectors.toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
	}

	public static <K, V> Collector<V, ?, HashMap<K, V>> toHashMapByKeyFailOnDuplicates(final Function<? super V, ? extends K> keyMapper)
	{
		final Function<V, V> valueMapper = value -> value;
		final BinaryOperator<V> mergeFunction = (valuePrev, valueNow) -> {
			throw new IllegalStateException("Duplicates not allowed: " + valuePrev + ", " + valueNow);
		};
		return Collectors.toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
	}

	public static <K, V> Collector<V, ?, HashMap<K, V>> toHashMapByKey(final Function<? super V, ? extends K> keyMapper, final BinaryOperator<V> mergeFunction)
	{
		final Function<V, V> valueMapper = value -> value;
		return Collectors.toMap(keyMapper, valueMapper, mergeFunction, HashMap::new);
	}

	/**
	 * Collects to {@link LinkedHashMap}.
	 * <p>
	 * If duplicate key was found, the last provided item will be used.
	 *
	 * @return {@link LinkedHashMap} collector
	 */
	public static <K, V> Collector<V, ?, LinkedHashMap<K, V>> toLinkedHashMapByKey(final Function<? super V, ? extends K> keyMapper)
	{
		// NOTE: before changing the "duplicates" behavior please check the callers first!
		return Collectors.toMap(
				keyMapper,
				value -> value,
				(valuePrev, valueNow) -> valueNow, // keep last 
				LinkedHashMap::new
		);
	}

	/**
	 * Collects to {@link ImmutableMap}.
	 * <p>
	 * If duplicate key was found, the first provided item will be used.
	 *
	 * @return immutable map collector
	 * @see #toImmutableMapByKey(Function)
	 */
	public static <K, V> Collector<V, ?, ImmutableMap<K, V>> toImmutableMapByKeyKeepFirstDuplicate(final Function<? super V, ? extends K> keyMapper)
	{
		return ImmutableMap.toImmutableMap(keyMapper, value -> value, (valuePrev, valueNow) -> valuePrev);
	}

	public static <K, V, M extends Map<K, V>> Collector<Entry<K, V>, ?, M> toMap(final Supplier<M> mapSupplier)
	{
		final BiConsumer<M, Map.Entry<K, V>> accumulator = (map, entry) -> map.put(entry.getKey(), entry.getValue());
		final BinaryOperator<M> combiner = (map1, map2) -> {
			map1.putAll(map2);
			return map1;
		};
		final Function<M, M> finisher = Function.identity();
		return Collector.of(mapSupplier, accumulator, combiner, finisher);
	}

	public static <K, V, M extends Map<K, V>> Collector<V, ?, M> toMapByKey(
			final Supplier<M> mapSupplier,
			final Function<V, K> keyMapper)
	{
		final BinaryOperator<V> mergeFunction = (u, v) -> {
			// throw new IllegalStateException("Duplicate keys: " + u + ", " + v);
			return v; // keep last
		};
		return Collectors.toMap(
				keyMapper,
				Function.identity(),// valueMapper
				mergeFunction, // mergeFunction
				mapSupplier);
	}

	public static <K, V> Collector<Entry<K, V>, ?, HashMap<K, V>> toHashMap()
	{
		return toMap(HashMap::new);
	}

	public static <K, V> Collector<Entry<K, V>, ?, LinkedHashMap<K, V>> toLinkedHashMap()
	{
		return toMap(LinkedHashMap::new);
	}

	public static <K, V> Collector<V, ?, ImmutableListMultimap<K, V>> toImmutableListMultimap(final Function<? super V, ? extends K> keyMapper)
	{
		return ImmutableListMultimap.toImmutableListMultimap(keyMapper, value -> value);
	}

	public static <K, V> Collector<Map.Entry<K, V>, ?, ImmutableListMultimap<K, V>> toImmutableListMultimap()
	{
		return ImmutableListMultimap.toImmutableListMultimap(Entry::getKey, Entry::getValue);
	}

	public static <K, V> Collector<Map.Entry<K, V>, ?, ImmutableSetMultimap<K, V>> toImmutableSetMultimap()
	{
		return ImmutableSetMultimap.toImmutableSetMultimap(Entry::getKey, Entry::getValue);
	}

	public static <K, V> Collector<V, ?, ArrayListMultimap<K, V>> toArrayListMultimapByKey(@NonNull final Function<V, K> keyFunction)
	{
		return Multimaps.toMultimap(keyFunction, Function.identity(), ArrayListMultimap::create);
	}

	/**
	 * Collects all items, in case of duplicates it keeps the first ones and returns a stream.
	 */
	public static <K, V> Collector<V, ?, Stream<V>> distinctBy(final Function<V, K> keyMapper)
	{
		final Supplier<LinkedHashMap<K, V>> supplier = LinkedHashMap::new;
		final BiConsumer<LinkedHashMap<K, V>, V> accumulator = (map, value) -> {
			final K key = keyMapper.apply(value);
			map.putIfAbsent(key, value);
		};
		final BinaryOperator<LinkedHashMap<K, V>> combiner = (l, r) -> {
			l.putAll(r);
			return l;
		};
		final Function<LinkedHashMap<K, V>, Stream<V>> finisher = map -> map.values().stream();
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <T> Collector<T, ?, T> singleElementOrThrow(@NonNull final Supplier<? extends RuntimeException> exceptionSupplier)
	{
		final Supplier<List<T>> supplier = ArrayList::new;
		final BiConsumer<List<T>, T> accumulator = List::add;
		final BinaryOperator<List<T>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<List<T>, T> finisher = list -> {
			if (list.size() != 1)
			{
				throw exceptionSupplier.get();
			}
			return list.get(0);
		};

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <T> Collector<T, ?, T> uniqueElementOrThrow(@NonNull final Function<Set<T>, ? extends RuntimeException> exceptionSupplier)
	{
		return Collector.<T, Set<T>, T>of(
				LinkedHashSet::new,
				Set::add,
				(l, r) -> {
					l.addAll(r);
					return l;
				},
				set -> {
					if (set.size() != 1)
					{
						throw exceptionSupplier.apply(set);
					}
					return set.iterator().next();
				});
	}

	public static <T> Stream<List<T>> groupByAndStream(final Stream<T> stream, final Function<T, ?> classifier)
	{
		final boolean parallel = false;
		return StreamSupport.stream(new GroupByClassifierSpliterator<>(stream.spliterator(), classifier), parallel);
	}

	public static <T> Stream<List<T>> batchAndStream(final Stream<T> stream, final int batchSize)
	{
		return StreamSupport.stream(new BatchSpliterator<>(stream.spliterator(), batchSize), stream.isParallel());
	}

	public static <K, V, K2> Function<Map.Entry<K, V>, ImmutableMapEntry<K2, V>> mapKey(@NonNull final Function<K, K2> keyMapper)
	{
		return entry -> entry(keyMapper.apply(entry.getKey()), entry.getValue());
	}

	public static <K, V, V2> Function<Map.Entry<K, V>, ImmutableMapEntry<K, V2>> mapValue(@NonNull final Function<V, V2> valueMapper)
	{
		return entry -> entry(entry.getKey(), valueMapper.apply(entry.getValue()));
	}

	public static <T, R> Collector<T, ?, R> collectUsingListAccumulator(@NonNull final Function<List<T>, R> finisher)
	{
		final Supplier<List<T>> supplier = ArrayList::new;
		final BiConsumer<List<T>, T> accumulator = List::add;
		final BinaryOperator<List<T>> combiner = (acc1, acc2) -> {
			acc1.addAll(acc2);
			return acc1;
		};

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <T, R> Collector<T, ?, R> collectUsingHashSetAccumulator(@NonNull final Function<HashSet<T>, R> finisher)
	{
		final Supplier<HashSet<T>> supplier = HashSet::new;
		final BiConsumer<HashSet<T>, T> accumulator = HashSet::add;
		final BinaryOperator<HashSet<T>> combiner = (acc1, acc2) -> {
			acc1.addAll(acc2);
			return acc1;
		};

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <T, R, K> Collector<T, ?, R> collectUsingMapAccumulator(@NonNull final Function<T, K> keyMapper, @NonNull final Function<Map<K, T>, R> finisher)
	{
		final Supplier<Map<K, T>> supplier = LinkedHashMap::new;
		final BiConsumer<Map<K, T>, T> accumulator = (map, item) -> map.put(keyMapper.apply(item), item);
		final BinaryOperator<Map<K, T>> combiner = (acc1, acc2) -> {
			acc1.putAll(acc2);
			return acc1;
		};

		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	public static <T> Collector<T, ?, Optional<ImmutableSet<T>>> toOptionalImmutableSet()
	{
		return Collectors.collectingAndThen(
				ImmutableSet.toImmutableSet(),
				set -> !set.isEmpty() ? Optional.of(set) : Optional.empty());
	}

}
