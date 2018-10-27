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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableSet;

import de.metas.util.Check;

/**
 * Implementation of Include/Exclude List pattern which acts like a {@link Predicate}.
 * 
 * Basically, the rules to accept an item are simple:
 * <ul>
 * <li>if the item is on exclude list, it will be always excluded
 * <li>if the item is on include list, it will be always included
 * <li>if the item is not on include nor on exclude list, it will be accepted ONLY if the includes list is empty (i.e. no particular includes were defined)
 * <li><code>null</code> items are NEVER accepted
 * </ul>
 * 
 * This implementation is immutable.
 * To create a new instance use the builder methods: {@link #builder()}, {@link #empty()}, {@link #ofExcludes(Object...)} etc.
 * 
 * @author tsa
 *
 * @param <T> includes/excludes item type.
 */
public class IncludeExcludeListPredicate<T> implements Predicate<T>
{
	private static final IncludeExcludeListPredicate<Object> EMPTY = new IncludeExcludeListPredicate<>();

	/**
	 * Convenient way to quickly create a {@link IncludeExcludeListPredicate} which contains given excludes.
	 * 
	 * To be really convenient when using, this method will ignore any nulls. i.e.
	 * <ul>
	 * <li>if given list is null or empty, no excludes will be added to the list that will be returned
	 * <li>if any of list elements are null, they will be skipped.
	 * </ul>
	 * 
	 * @param excludes
	 * @return excludes list; never returns null.
	 */
	@SafeVarargs
	public static final <T> IncludeExcludeListPredicate<T> ofExcludes(final T... excludes)
	{
		final Builder<T> includesExcludesList = builder();

		if (excludes != null && excludes.length > 0)
		{
			for (final T exclude : excludes)
			{
				if (exclude != null)
				{
					includesExcludesList.exclude(exclude);
				}
			}
		}
		return includesExcludesList.build();
	}

	public static final <T> Builder<T> builder()
	{
		return new Builder<>();
	}

	public static <T> IncludeExcludeListPredicate<T> empty()
	{
		@SuppressWarnings("unchecked")
		final IncludeExcludeListPredicate<T> emptyCasted = (IncludeExcludeListPredicate<T>)EMPTY;
		return emptyCasted;
	}

	private final Set<T> includes;
	private final Set<T> excludes;

	private IncludeExcludeListPredicate(final Builder<T> builder)
	{
		super();
		this.includes = ImmutableSet.copyOf(builder.includes);
		this.excludes = ImmutableSet.copyOf(builder.excludes);
	}

	/**
	 * Empty constructor
	 */
	private IncludeExcludeListPredicate()
	{
		super();
		includes = ImmutableSet.of();
		excludes = ImmutableSet.of();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public boolean test(final T item)
	{
		if (item == null)
		{
			return false;
		}

		// Don't accept it if it's explicitelly excluded
		if (excludes.contains(item))
		{
			return false;
		}

		// Accept it right away if it explicitelly included
		if (includes.contains(item))
		{
			return true;
		}

		//
		// Accept it ONLY if there are NO particular items to be included.
		// Else, we assume the user want's to include ONLY those.
		final boolean acceptAllByDefault = includes.isEmpty();
		return acceptAllByDefault;
	}

	public static class Builder<T>
	{
		private final Set<T> includes = new LinkedHashSet<>();
		private final Set<T> excludes = new LinkedHashSet<>();
		private IncludeExcludeListPredicate<T> lastBuilt = null;

		private Builder()
		{
			super();
		}

		public IncludeExcludeListPredicate<T> build()
		{
			if (lastBuilt != null)
			{
				return lastBuilt;
			}

			if (isEmpty())
			{
				lastBuilt = empty();
			}
			else
			{
				lastBuilt = new IncludeExcludeListPredicate<>(this);
			}
			return lastBuilt;
		}

		public final boolean isEmpty()
		{
			return includes.isEmpty() && excludes.isEmpty();
		}

		public Builder<T> include(final T itemToInclude)
		{
			Check.assumeNotNull(itemToInclude, "itemToInclude not null");
			final boolean added = includes.add(itemToInclude);

			// Reset last built, in case of change
			if (added)
			{
				lastBuilt = null;
			}

			return this;
		}

		public Builder<T> exclude(final T itemToExclude)
		{
			// guard against null: tollerate it but do nothing
			if (itemToExclude == null)
			{
				return this;
			}

			final boolean added = excludes.add(itemToExclude);

			// Reset last built, in case of change
			if (added)
			{
				lastBuilt = null;
			}

			return this;
		}
	}
}
