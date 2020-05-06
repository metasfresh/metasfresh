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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Comparator;

/**
 * If any of the given arguments to compare is {@code null}, then this compoarator returns 0, i.e. treads them as equal.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T>
 */
@SuppressWarnings("rawtypes")
public class ComparableComparatorNullsEqual<T extends Comparable> implements Comparator<T>
{
	private static final transient ComparableComparatorNullsEqual<Comparable> instance = new ComparableComparatorNullsEqual<Comparable>();

	@SuppressWarnings("unchecked")
	public static <T extends Comparable> ComparableComparatorNullsEqual<T> getInstance()
	{
		return (ComparableComparatorNullsEqual<T>)instance;
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
			return 0;
		}
		if (o2 == null)
		{
			return 0;
		}

		@SuppressWarnings("unchecked")
		final int cmp = o1.compareTo(o2);
		return cmp;
	}

}
