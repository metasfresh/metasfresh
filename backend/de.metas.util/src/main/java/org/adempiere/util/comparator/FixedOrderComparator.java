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


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import de.metas.util.Check;

/**
 * Comparator which sorts the given set by using a partially specified order.
 * 
 * e.g. Following code will sort a list of strings by putting the value "before1" at the beginning of the list and value "after1" at the end of the string new. The "*" is the not matched marker, which
 * is a placeholder for the values which are not fixed.
 * 
 * <pre>
 * Comparator<String> cmp = FixedOrderComparator<String>("*", "before1", "*", "after1");
 * List<String> list = Arrays.asList("value1", "value2", "after1", "before1", "value3");
 *  // result will be: "before1", "value1", "value2", "value3", "after1"
 * </pre>
 * 
 * NOTE: the order of not matched elements is not guaranteed
 * 
 * @author tsa
 * 
 * @param <T>
 */
public class FixedOrderComparator<T> implements Comparator<T>
{
	private List<T> fixedOrderList;
	// private T notMatchedMarker;
	private int notMatchedMarkerIndex;

	public FixedOrderComparator(final T notMatchedMarker, final List<T> fixedOrderList)
	{
		Check.assumeNotNull(fixedOrderList, "fixedOrderList not null");
		Check.assume(!fixedOrderList.isEmpty(), "fixedOrderList not empty");
		Check.assumeNotNull(notMatchedMarker, "notMatchedMarker not null");

		this.fixedOrderList = fixedOrderList;
		// this.notMatchedMarker = notMatchedMarker;

		notMatchedMarkerIndex = fixedOrderList.indexOf(notMatchedMarker);
		Check.assume(notMatchedMarkerIndex >= 0, "notMatchedMarker '{}' shall be present in fixed order list: {}", notMatchedMarker, fixedOrderList);
	}

	@SafeVarargs
	public FixedOrderComparator(final T notMatchedMarker, final T... fixedOrderElements)
	{
		this(notMatchedMarker, Arrays.asList(fixedOrderElements));
	}

	@Override
	public int compare(T o1, T o2)
	{
		int idx1 = fixedOrderList.indexOf(o1);
		if (idx1 < 0)
		{
			idx1 = notMatchedMarkerIndex;
		}

		int idx2 = fixedOrderList.indexOf(o2);
		if (idx2 < 0)
		{
			idx2 = notMatchedMarkerIndex;
		}

		return idx1 - idx2;
	}

	@Override
	public String toString()
	{
		return "FixedOrderComparator [fixedOrderList=" + fixedOrderList + ", notMatchedMarkerIndex=" + notMatchedMarkerIndex + "]";
	}
}
