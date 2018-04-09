package de.metas.ordercandidate.api;

import java.util.Comparator;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.swat.base
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

@Value
public class OLCandAggregation
{
	public static OLCandAggregation of(final List<OLCandAggregationColumn> columns)
	{
		return new OLCandAggregation(columns);
	}

	private static final NopOrderingComparator DO_NOTHING_COMPARATOR = new NopOrderingComparator();

	private final ImmutableList<OLCandAggregationColumn> columns;
	private final ImmutableList<OLCandAggregationColumn> orderByColumns;
	private final ImmutableList<OLCandAggregationColumn> splitOrderDiscriminatorColumns;
	private final ImmutableList<OLCandAggregationColumn> groupByColumns;

	@Getter(lazy = true)
	private final Comparator<OLCand> orderingComparator = createOrderingComparator();

	private OLCandAggregation(final List<OLCandAggregationColumn> columns)
	{
		this.columns = ImmutableList.copyOf(columns);

		orderByColumns = this.columns.stream()
				.filter(OLCandAggregationColumn::isOrderByColumn)
				.sorted(Comparator.comparing(OLCandAggregationColumn::getOrderBySeqNo))
				.collect(ImmutableList.toImmutableList());

		splitOrderDiscriminatorColumns = this.columns.stream()
				.filter(OLCandAggregationColumn::isSplitOrderDiscriminator)
				.collect(ImmutableList.toImmutableList());

		groupByColumns = this.columns.stream()
				.filter(OLCandAggregationColumn::isGroupByColumn)
				.collect(ImmutableList.toImmutableList());
	}

	private Comparator<OLCand> createOrderingComparator()
	{
		return getOrderByColumns()
				.stream()
				.map(this::createColumnOrderingComparator)
				.reduce(DO_NOTHING_COMPARATOR, Comparator::thenComparing);
	}

	private final Comparator<OLCand> createColumnOrderingComparator(final OLCandAggregationColumn column)
	{
		return new OLCandColumnOrderingComparator(column);
	}

	private static final class NopOrderingComparator implements Comparator<OLCand>
	{
		@Override
		public int compare(final OLCand o1, final OLCand o2)
		{
			return 0;
		}
	}

	private static final class OLCandColumnOrderingComparator implements Comparator<OLCand>
	{
		private final OLCandAggregationColumn column;

		private OLCandColumnOrderingComparator(@NonNull final OLCandAggregationColumn column)
		{
			this.column = column;
		}

		@Override
		public int compare(final OLCand o1, final OLCand o2)
		{
			final Object val1 = o1.getValueByColumn(column);
			final Object val2 = o2.getValueByColumn(column);

			// allow null values
			if (val1 == val2)
			{
				return 0;
			}
			if (val1 == null)
			{
				return -1;
			}
			if (val2 == null)
			{
				return 1;
			}

			Check.assume(val1.getClass() == val2.getClass(), "{} and {} have the same class", val1, val2);

			final Comparable<Object> comparableVal1 = toComparable(val1);
			final Comparable<Object> comparableVal2 = toComparable(val2);
			return comparableVal1.compareTo(comparableVal2);
		}

		@SuppressWarnings("unchecked")
		private final Comparable<Object> toComparable(final Object value)
		{
			return (Comparable<Object>)value;
		}
	}

}
