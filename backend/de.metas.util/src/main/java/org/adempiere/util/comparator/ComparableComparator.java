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


import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class ComparableComparator<T extends Comparable> implements Comparator<T>
{
	private static final transient ComparableComparator<Comparable> instanceNullsFirst = new ComparableComparator<Comparable>(true);
	private static final transient ComparableComparator<Comparable> instanceNullsLast = new ComparableComparator<Comparable>(false);

	/**
	 * Default Nulls First option.
	 *
	 * NOTE: for backward compatibility we set it to "true".
	 */
	private static final boolean NULLSFIRST_DEFAULT = true;

	public static <T extends Comparable> ComparableComparator<T> getInstance()
	{
		return getInstance(NULLSFIRST_DEFAULT);
	}

	public static <T extends Comparable> ComparableComparator<T> getInstance(final boolean nullsFirst)
	{
		@SuppressWarnings("unchecked")
		final ComparableComparator<T> cmp = (ComparableComparator<T>)(nullsFirst ? instanceNullsFirst : instanceNullsLast);
		return cmp;
	}


	public static <T extends Comparable> ComparableComparator<T> getInstance(Class<T> clazz)
	{
		return getInstance(NULLSFIRST_DEFAULT);
	}

	public static <T extends Comparable> ComparableComparator<T> getInstance(final Class<T> clazz, final boolean nullsFirst)
	{
		return getInstance(nullsFirst);
	}

	private final boolean nullsFirst;

	public ComparableComparator()
	{
		this(NULLSFIRST_DEFAULT);
	}

	public ComparableComparator(final boolean nullsFirst)
	{
		this.nullsFirst = nullsFirst;
	}

	@Override
	public int compare(T o1, T o2)
	{
		if (o1 == o2)
		{
			return 0;
		}

		if (o1 == null)
		{
			return nullsFirst ? -1 : +1;
		}
		if (o2 == null)
		{
			return nullsFirst ? +1 : -1;
		}

		@SuppressWarnings("unchecked")
		final int cmp = o1.compareTo(o2);
		return cmp;
	}

}
