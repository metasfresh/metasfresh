package de.metas.fresh.picking.form;

import java.util.function.Function;

import java.util.Objects;
import com.google.common.collect.ImmutableSet;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
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

@ToString
@EqualsAndHashCode
public final class RowIndexes
{
	public static RowIndexes ofArray(final int[] rows)
	{
		if (rows == null || rows.length == 0)
		{
			return EMPTY;
		}

		return new RowIndexes(toImmutableSet(rows));
	}

	private static ImmutableSet<Integer> toImmutableSet(final int[] rows)
	{
		final ImmutableSet.Builder<Integer> set = ImmutableSet.builder();
		for (final int row : rows)
		{
			set.add(row);
		}
		return set.build();
	}

	public static final RowIndexes EMPTY = new RowIndexes(ImmutableSet.of());

	private final ImmutableSet<Integer> indexes;

	private RowIndexes(@NonNull final ImmutableSet<Integer> indexes)
	{
		this.indexes = indexes;
	}

	public boolean isEmpty()
	{
		return indexes.isEmpty();
	}

	public ImmutableSet<Integer> toIntSet()
	{
		return indexes;
	}

	public <T> ImmutableSet<T> toSet(@NonNull final Function<Integer, T> mapper)
	{
		if (indexes.isEmpty())
		{
			return ImmutableSet.of();
		}

		return indexes.stream()
				.map(mapper)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
