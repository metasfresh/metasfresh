package org.adempiere.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
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

	private GuavaCollectors()
	{
	}
}
