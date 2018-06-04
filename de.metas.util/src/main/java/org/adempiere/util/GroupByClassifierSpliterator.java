package org.adempiere.util;

import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;

import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class GroupByClassifierSpliterator<E> implements Spliterator<List<E>>
{
	private final Spliterator<E> source;
	private final Function<E, ?> classifier;

	private E nextValue;
	private boolean hasNextValue;

	public GroupByClassifierSpliterator(
			@NonNull final Spliterator<E> source,
			@NonNull final Function<E, ?> classifier)
	{
		this.source = source;
		this.classifier = classifier;
	}

	@Override
	public boolean tryAdvance(Consumer<? super List<E>> action)
	{
		GroupCollector<E> groupCollector = null;

		E item;
		while ((item = nextOrNull()) != null)
		{
			final Object itemClassifierValue = classifier.apply(item);
			if (groupCollector != null && !groupCollector.isMatchingClassifier(itemClassifierValue))
			{
				putBack(item);
				action.accept(groupCollector.finish());
				return true;
			}

			if (groupCollector == null)
			{
				groupCollector = new GroupCollector<>(itemClassifierValue);
			}
			groupCollector.collect(item);
		}

		if (groupCollector != null)
		{
			action.accept(groupCollector.finish());
			return true;
		}
		else
		{
			return false;
		}
	}

	private E nextOrNull()
	{
		if (hasNextValue)
		{
			final E value = this.nextValue;
			hasNextValue = false;
			nextValue = null;
			return value;
		}

		final IMutable<E> valueRef = new Mutable<>();
		if (!source.tryAdvance(valueRef::setValue))
		{
			return null;
		}

		final E value = valueRef.getValue();
		if (value == null)
		{
			throw new NullPointerException("null values are not allowed in a " + GroupByClassifierSpliterator.class);
		}
		return value;
	}

	private void putBack(E value)
	{
		// shall not happen
		if (hasNextValue)
		{
			throw new IllegalStateException("Only one value is allowed to be put back");
		}

		hasNextValue = true;
		nextValue = value;
	}

	@Override
	public Spliterator<List<E>> trySplit()
	{
		return null;
	}

	@Override
	public long estimateSize()
	{
		return Long.MAX_VALUE;
	}

	@Override
	public int characteristics()
	{
		return source.characteristics();
	}

	private static final class GroupCollector<E>
	{
		private final Object classifierValue;
		private final ImmutableList.Builder<E> items = ImmutableList.builder();

		public GroupCollector(final Object classifierValue)
		{
			this.classifierValue = classifierValue;
		}

		public boolean isMatchingClassifier(final Object classifierValueToMatch)
		{
			return Objects.equals(this.classifierValue, classifierValueToMatch);
		}

		public void collect(final E item)
		{
			items.add(item);
		}

		public List<E> finish()
		{
			return items.build();
		}
	}
}
