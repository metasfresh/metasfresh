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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Check;

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
		super();
	}

	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 * 
	 * @param list list to be converted to string
	 * @param separator between elements separator
	 * @return string representation
	 * @see #toString(Collection, String, Converter)
	 */
	public static <ET, LT extends Collection<ET>> String toString(final LT list, final String separator)
	{
		// Use default element string converter
		final Converter<String, ET> elementStringConverter = null;

		return toString(list, separator, elementStringConverter);
	}

	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 * 
	 * @param list list to be converted to string
	 * @param separator between elements separator
	 * @param elementStringConverter converter to be used when converting one list element to string
	 * @return string representation
	 */
	public static <ET, LT extends Collection<ET>> String toString(final LT list,
			final String separator,
			final Converter<String, ET> elementStringConverter)
	{
		if (list == null)
		{
			return "";
		}
		if (list.isEmpty())
		{
			return "";
		}

		final String separatorToUse = separator == null ? "" : separator;

		final StringBuilder sb = new StringBuilder();
		for (final ET element : list)
		{
			if (sb.length() > 0)
			{
				sb.append(separatorToUse);
			}

			final String elementStr;
			if (elementStringConverter == null)
			{
				elementStr = String.valueOf(element);
			}
			else
			{
				elementStr = elementStringConverter.convert(element);
			}

			sb.append(elementStr);
		}

		return sb.toString();
	}

	public static List<Integer> asList(final int... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptyList();
		}

		final List<Integer> list = new ArrayList<Integer>(arr.length);
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

	public static Set<Integer> asIntSet(final int... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final Set<Integer> set = new HashSet<>(arr.length);
		for (int i : arr)
		{
			set.add(i);
		}

		return set;
	}

	public static final <T> Set<T> asSet(@SuppressWarnings("unchecked") final T... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final Set<T> set = new HashSet<>(arr.length);
		for (final T e : arr)
		{
			set.add(e);
		}

		return set;
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
		for (final Iterator<? extends E> it = list.iterator(); it.hasNext();)
		{
			final E element = it.next();
			if (predicate.apply(element))
			{
				copy.add(element);
			}
		}
		return copy;
	}


	/**
	 * Assumes that only one element will be matched by filter and returns it.
	 * 
	 * If there were more elements matching or no element was matching an exception will be thrown.
	 * 
	 * @param collection
	 * @param filter filter used to match the element
	 * @return matching element; returns null ONLY if the element is null
	 */
	public static <T> T singleElement(final Collection<T> collection, final java.util.function.Predicate<T> filter)
	{
		Check.assumeNotEmpty(collection, "collection not empty");
		Check.assumeNotNull(filter, "filter not null");

		final List<T> result = new ArrayList<>();

		final Iterator<T> it = collection.iterator();
		while (it.hasNext())
		{
			final T e = it.next();
			if (filter.test(e))
			{
				result.add(e);
			}
		}

		Check.assume(result.size() == 1, "One and only one matching element was expected but we got more or none: {}", result);
		return result.get(0);
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * 
	 * If the collection has more elements or no element then an exception will be thrown.
	 * 
	 * @param collection
	 * @return element; returns null ONLY if the element is null
	 */
	public static <T> T singleElement(final Collection<T> collection)
	{
		Check.assumeNotNull(collection, "collection not null");
		Check.assume(collection.size() == 1, "One and only one was expected for: {}", collection);
		return collection.iterator().next();
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * 
	 * If the collection has more elements or no element then <code>null</code> will be returned.
	 * 
	 * @param collection
	 * @return element
	 */
	public final static <T> T singleElementOrNull(final Collection<T> collection)
	{
		final T defaultValue = null;
		return singleElementOrDefault(collection, defaultValue);
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * 
	 * If the collection has more elements or no element then <code>defaultValue</code> will be returned.
	 * 
	 * @param collection
	 * @param defaultValue value to be returned in case there are more then one elements or no element
	 * @return element
	 */
	public final static <T> T singleElementOrDefault(final Collection<T> collection, final T defaultValue)
	{
		if (collection == null)
		{
			return defaultValue;
		}
		if (collection.size() != 1)
		{
			return defaultValue;
		}

		final T element = collection.iterator().next();
		if (element == null)
		{
			return defaultValue;
		}
		return element;
	}

	/**
	 * Converts the element of given <code>list</code> of type <code>InputType</code> to a list of <code>OutputType</code> by using given <code>converter</code>.
	 * 
	 * @param list input list (i.e. list to convert)
	 * @param converter converter to be used to convert elements
	 * @return list of OutputTypes.
	 */
	public static <OutputType, InputType> List<OutputType> convert(final List<InputType> list, final Converter<OutputType, InputType> converter)
	{
		if (list == null)
		{
			return null;
		}
		if (list.isEmpty())
		{
			return new ArrayList<>();
		}

		final List<OutputType> listConv = new ArrayList<>(list.size());
		for (final InputType element : list)
		{
			final OutputType elementConv = converter.convert(element);
			listConv.add(elementConv);
		}

		return listConv;
	}

	/**
	 * Removes first element from {@link Set} and returns it.
	 * 
	 * NOTE: this method is NOT checking if the set is null or empty.
	 * 
	 * @param set
	 * @return firt element
	 */
	public static final <T> T removeFirst(final Set<T> set)
	{
		final Iterator<T> it = set.iterator();
		final T element = it.next();
		it.remove();
		return element;
	}
}
