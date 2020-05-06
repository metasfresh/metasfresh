package de.metas.util.collections;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Predicate;

/**
 * Miscellaneous List utility methods
 *
 * @author tsa
 *
 */
public final class ListUtils
{
	private ListUtils()
	{
	}


	public static List<Integer> asList(final int... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptyList();
		}

		final List<Integer> list = new ArrayList<>(arr.length);
		for (int i : arr)
		{
			list.add(i);
		}

		return list;
	}

	/**
	 * Creates a copy as list of given iterable.
	 *
	 * @param iterable
	 * @return list or null if the parameter was null
	 */
	public static final <T> List<T> copyAsList(final Iterable<? extends T> iterable)
	{
		if (iterable == null)
		{
			return null;
		}

		if (iterable instanceof Collection)
		{
			final Collection<? extends T> col = (Collection<? extends T>)iterable;
			return new ArrayList<>(col);
		}
		else
		{
			final List<T> list = new ArrayList<>();
			for (final T item : iterable)
			{
				list.add(item);
			}
			return list;
		}
	}

	/**
	 * Creates a new list having all elements from given <code>list</code> in reversed order.
	 *
	 * NOTE: returning list is not guaranteed to me modifiable.
	 *
	 * @param list
	 * @return
	 */
	public static <T> List<T> copyAndReverse(final List<T> list)
	{
		if (list == null)
		{
			return null;
		}
		if (list.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<T> listCopy = new ArrayList<>(list);
		Collections.reverse(listCopy);

		return listCopy;
	}

	/**
	 * @param list
	 * @param predicate
	 * @return a filtered copy of given list
	 */
	public static final <E> List<E> copyAndFilter(final List<? extends E> list, final Predicate<? super E> predicate)
	{
		final List<E> copy = new ArrayList<>();
		for (final E element : list)
		{
			if (predicate.apply(element))
			{
				copy.add(element);
			}
		}
		return copy;
	}
}
