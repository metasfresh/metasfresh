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
import java.util.Comparator;
import java.util.List;

import de.metas.util.Check;

public class ComparatorChain<T> implements Comparator<T>
{
	private final List<Comparator<T>> comparators = new ArrayList<Comparator<T>>();

	public ComparatorChain()
	{
	}

	public ComparatorChain<T> addComparator(Comparator<T> cmp)
	{
		final boolean reverse = false;
		return addComparator(cmp, reverse);
	}

	public ComparatorChain<T> addComparator(Comparator<T> cmp, boolean reverse)
	{
		Check.assumeNotNull(cmp, "cmp not null");
		if (reverse)
		{
			comparators.add(new ReverseComparator<T>(cmp));
		}
		else
		{
			comparators.add(cmp);
		}

		return this;
	}

	@Override
	public int compare(T o1, T o2)
	{
		Check.assume(!comparators.isEmpty(), "ComparatorChain contains comparators");

		for (final Comparator<T> cmp : comparators)
		{
			final int result = cmp.compare(o1, o2);
			if (result != 0)
			{
				return result;
			}
		}

		// assuming that objects are equal if none of child comparators said otherwise
		return 0;
	}

	@Override
	public String toString()
	{
		return "ComparatorChain [comparators=" + comparators + "]";
	}
}
