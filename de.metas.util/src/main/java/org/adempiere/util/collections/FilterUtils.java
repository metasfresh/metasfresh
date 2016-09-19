package org.adempiere.util.collections;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author metas-dev <dev@metasfresh.com>
 * @deprecated now that we finally have java8, please use its stream/filter API instead. see e.g. <code>de.metas.handlingunits.client.terminal.select.api.impl.ReceiptScheduleFiltering.retrieveTableRows()</code> for an example
 */
@Deprecated
public final class FilterUtils
{
	private FilterUtils()
	{
		super();
	}

	/**
	 * @param list
	 * @param condition
	 * @return sub-list containing values where the given <code>condition</code> returned <code>true</code>
	 */
	public static <T> List<T> filter(final List<T> list, final Predicate<T> condition)
	{
		if (list == null)
		{
			return null;
		}

		final List<T> result = new ArrayList<T>();
		for (final T candidate : list)
		{
			if (!condition.evaluate(candidate))
			{
				continue;
			}

			result.add(candidate);
		}
		return result;
	}

	/**
	 * @param set
	 * @param condition
	 * @return sub-list containing values filtered by condition
	 */
	public static <T> Set<T> filter(final Set<T> set, final Predicate<T> condition)
	{
		if (set == null)
		{
			return null;
		}

		final Set<T> result = new HashSet<T>();
		for (final T candidate : set)
		{
			if (!condition.evaluate(candidate))
			{
				continue;
			}

			result.add(candidate);
		}
		return result;
	}

	/**
	 * @param map
	 * @param condition
	 * @return sub-map containing values filtered by condition
	 */
	public static <K, V> Map<K, V> filter(final Map<K, V> map, final Predicate<V> condition)
	{
		if (map == null)
		{
			return null;
		}

		final Map<K, V> result = new HashMap<K, V>();
		for (final Map.Entry<K, V> e : map.entrySet())
		{
			if (!condition.evaluate(e.getValue()))
			{
				continue;
			}

			result.put(e.getKey(), e.getValue());
		}
		return result;
	}

	/**
	 * @param list
	 * @param condition
	 * @return index of first occurrence in the list filtered by condition, -1 otherwise
	 */
	public static <T> int indexOf(final List<T> list, final Predicate<T> condition)
	{
		if (list == null)
		{
			return -1;
		}

		final int len = list.size();
		for (int i = 0; i < len; i++)
		{
			final T item = list.get(i);
			if (condition.evaluate(item))
			{
				return i;
			}
		}
		return -1;
	}
}
