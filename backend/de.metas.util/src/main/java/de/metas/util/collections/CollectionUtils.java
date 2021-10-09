package de.metas.util.collections;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

@UtilityClass
public final class CollectionUtils
{
	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 *
	 * @param collection list to be converted to string
	 * @param separator  between elements separator
	 * @return string representation
	 * @see #toString(Collection, String, Converter)
	 */
	public static <ET, CT extends Collection<ET>> String toString(final CT collection, final String separator)
	{
		// Use default element string converter
		final Converter<String, ET> elementStringConverter = null;

		return toString(collection, separator, elementStringConverter);
	}

	/**
	 * Convert given <code>list</code> to string using given <code>separator</code>.
	 *
	 * @param collection             collection to be converted to string
	 * @param separator              between elements separator
	 * @param elementStringConverter converter to be used when converting one list element to string
	 * @return string representation
	 */
	public static <ET, CT extends Collection<ET>> String toString(final CT collection,
																  final String separator,
																  @Nullable final Converter<String, ET> elementStringConverter)
	{
		if (collection == null)
		{
			return "";
		}
		if (collection.isEmpty())
		{
			return "";
		}

		final String separatorToUse = separator == null ? "" : separator;

		final StringBuilder sb = new StringBuilder();
		for (final ET element : collection)
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

	public static Set<Integer> asIntSet(final int... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final Set<Integer> set = new HashSet<>(arr.length);
		for (final int i : arr)
		{
			set.add(i);
		}

		return set;
	}

	public static <T> Set<T> asSet(@SuppressWarnings("unchecked") final T... arr)
	{
		if (arr == null || arr.length == 0)
		{
			return Collections.emptySet();
		}

		final Set<T> set = new HashSet<>(arr.length);
		Collections.addAll(set, arr);

		return set;
	}

	/**
	 * Assumes that only one element will be matched by filter and returns it.
	 * <p>
	 * If there were more elements matching or no element was matching an exception will be thrown.
	 *
	 * @param filter filter used to match the element
	 * @return matching element; returns null ONLY if the element is null
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T> T singleElement(@NonNull final Collection<T> collection, @NonNull final java.util.function.Predicate<T> filter)
	{
		final List<T> result = new ArrayList<>();

		for (final T e : collection)
		{
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
	 * <p>
	 * If the collection has more elements or no element then an exception will be thrown.
	 *
	 * @return element; returns null ONLY if the element is null
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T> T singleElement(@NonNull final Collection<T> collection)
	{
		Check.errorUnless(collection.size() == 1, "The given collection needs to have exactly one 1 item, but has {} items; collection={}", collection.size(), collection);
		return collection.iterator().next();
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * <p>
	 * If the collection has more elements or no element then <code>null</code> will be returned.
	 *
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Nullable
	public static <T> T singleElementOrNull(final Collection<T> collection)
	{
		final T defaultValue = null;
		return singleElementOrDefault(collection, defaultValue);
	}

	/**
	 * Assumes that given collection has one element only and returns it.
	 * <p>
	 * If the collection has more elements or no element then <code>defaultValue</code> will be returned.
	 *
	 * @param defaultValue value to be returned in case there are more then one elements or no element
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Nullable
	public static <T> T singleElementOrDefault(final Collection<T> collection, @Nullable final T defaultValue)
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
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	public static <T, R> R extractSingleElement(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return singleElement(extractedElements);
	}

	/**
	 * @see de.metas.util.reducers.Reducers#singleValue()
	 */
	@Nullable
	public static <T, R> R extractSingleElementOrDefault(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction,
			@Nullable final R defaultValue)
	{
		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return singleElementOrDefault(extractedElements, defaultValue);
	}

	public static <T, R> boolean hasDifferentValues(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		if (collection.isEmpty())
		{
			return false;
		}

		final ImmutableList<R> extractedElements = extractDistinctElements(collection, extractFunction);
		return extractedElements.size() > 1;
	}

	public static <R, T> ImmutableList<R> extractDistinctElements(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		return collection
				.stream()
				.map(extractFunction)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	public static <R, T> ImmutableSet<R> extractDistinctElementsIntoSet(
			@NonNull final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		return collection
				.stream()
				.map(extractFunction)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}

	/**
	 * Converts the element of given <code>list</code> of type <code>InputType</code> to a list of <code>OutputType</code> by using given <code>converter</code>.
	 *
	 * @param collection      input list (i.e. list to convert)
	 * @param extractFunction converter to be used to convert elements
	 * @return list of OutputTypes.
	 */
	@Nullable
	public static <R, T> ImmutableList<R> convert(
			@Nullable final Collection<T> collection,
			@NonNull final Function<T, R> extractFunction)
	{
		if (collection == null)
		{
			return null;
		}

		return collection
				.stream()
				.map(extractFunction)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Removes first element from {@link Set} and returns it.
	 * <p>
	 * NOTE: this method is NOT checking if the set is null or empty.
	 *
	 * @return the removed first element
	 */
	public static <T> T removeFirst(@NonNull final Set<T> set)
	{
		final Iterator<T> it = set.iterator();
		final T element = it.next();
		it.remove();
		return element;
	}

	public static <K, V> Collection<V> getAllOrLoad(
			@NonNull final Map<K, V> map,
			@NonNull final Collection<K> keys,
			@NonNull final Function<Set<K>, Map<K, V>> valuesLoader)
	{
		if (keys.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Fetch from cache what's available
		final List<V> values = new ArrayList<>(keys.size());
		final Set<K> keysToLoad = new HashSet<>();
		for (final K key : ImmutableSet.copyOf(keys))
		{
			final V value = map.get(key);
			if (value == null)
			{
				keysToLoad.add(key);
			}
			else
			{
				values.add(value);
			}
		}

		//
		// Load the missing keys if any
		if (!keysToLoad.isEmpty())
		{
			final Map<K, V> valuesLoaded = valuesLoader.apply(keysToLoad);
			map.putAll(valuesLoaded); // add loaded values to cache
			values.addAll(valuesLoaded.values()); // add loaded values to the list we will return
		}

		//
		return values;
	}

	public static <K, V> LinkedHashMap<K, V> uniqueLinkedHashMap(
			@NonNull final Stream<V> stream,
			@NonNull final Function<? super V, ? extends K> keyFunction)
	{
		// thx to https://reversecoding.net/java-8-list-to-map/
		final LinkedHashMap<K, V> inventoryLineRecords = stream
				.collect(Collectors.toMap(
						keyFunction,
						Function.identity(),
						(u, v) -> {
							throw new IllegalStateException(String.format("Duplicate key %s", u));
						},
						LinkedHashMap::new));
		return inventoryLineRecords;
	}

	public static <T> ImmutableList<T> ofCommaSeparatedList(
			@Nullable final String commaSeparatedStr,
			@NonNull final Function<String, T> mapper)
	{
		if (Check.isBlank(commaSeparatedStr))
		{
			return ImmutableList.of();
		}

		return Splitter.on(",")
				.trimResults()
				.omitEmptyStrings()
				.splitToList(commaSeparatedStr)
				.stream()
				.map(mapper)
				.collect(ImmutableList.toImmutableList());
	}

	@Nullable
	public static <T> T emptyOrSingleElement(@Nullable final Collection<T> collection)
	{
		if (collection == null)
		{
			return null;
		}

		final int size = collection.size();
		if (size == 0)
		{
			return null;
		}
		else if (size == 1)
		{
			return collection.iterator().next();
		}
		else
		{
			throw Check.mkEx("The given collection needs to have ZERO or ONE item, but has " + size + " items; collection=" + collection);
		}
	}

	@NonNull
	public static <T> ArrayList<T> mergeLists(@NonNull final ArrayList<T> list1,@NonNull final ArrayList<T> list2)
	{
		list1.addAll(list2);
		return list1;
	}
}
