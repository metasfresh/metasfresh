package org.adempiere.util.comparator;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.metas.util.Check;

/**
 * Comparator which sorts a given set by using the item's cardinality in a given list.
 * 
 * i.e. the items will be sorted by their "indexOf" in a given reference list.
 * 
 * @author tsa
 *
 */
public final class CardinalityOrderComparator<T> implements Comparator<T>
{
	public static final <T> Builder<T> builder()
	{
		return new Builder<>();
	}

	private List<T> cardinalityList;

	private CardinalityOrderComparator(final Builder<T> builder)
	{
		super();

		// NOTE: we shall not copy the cardinality list because we don't know what type is it,
		// and maybe the developer is rellying on a specific indexOf implementation of that list.
		Check.assumeNotNull(builder.cardinalityList, "cardinalityList not null");
		this.cardinalityList = builder.cardinalityList;
	}

	@Override
	public int compare(final T o1, final T o2)
	{
		final int cardinality1 = cardinality(o1);
		final int cardinality2 = cardinality(o2);
		return cardinality1 - cardinality2;
	}

	private final int cardinality(final T item)
	{
		final int cardinality = cardinalityList.indexOf(item);

		// If item was not found in our cardinality list, return MAX_VALUE
		// => not found items will be comsidered last ones
		if (cardinality < 0)
		{
			return Integer.MAX_VALUE;
		}

		return cardinality;
	}

	public static final class Builder<T>
	{
		private List<T> cardinalityList;

		Builder()
		{
			super();
		}

		public CardinalityOrderComparator<T> build()
		{
			return new CardinalityOrderComparator<>(this);
		}

		/**
		 * Convenient method to copy and sort a given list.
		 * 
		 * @param list
		 * @return sorted list (as a new copy)
		 */
		public List<T> copyAndSort(final List<T> list)
		{
			final CardinalityOrderComparator<T> comparator = build();
			final List<T> listSorted = new ArrayList<>(list);
			Collections.sort(listSorted, comparator);
			return listSorted;
		}

		/**
		 * Sets the list of all items.
		 * When sorting, this is the list which will give the cardinality of a given item (i.e. the indexOf).
		 * 
		 * WARNING: the given cardinality list will be used on line, as it is and it won't be copied, because we don't know what indexOf implementation to use.
		 * So, please make sure you are not chaning the list in meantime.
		 * 
		 * @param cardinalityList
		 * @return
		 */
		public Builder<T> setCardinalityList(final List<T> cardinalityList)
		{
			this.cardinalityList = cardinalityList;
			return this;
		}
	}
}
