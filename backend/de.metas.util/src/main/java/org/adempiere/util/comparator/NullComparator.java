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


import java.io.Serializable;
import java.util.Comparator;

/**
 * A {@link Comparator} which does nothing.
 *
 * @author tsa
 *
 */
public final class NullComparator implements Comparator<Object>, Serializable
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8769676939269341332L;

	@SuppressWarnings("rawtypes")
	private static final Comparator instance = new NullComparator();

	public static <T> Comparator<T> getInstance()
	{
		@SuppressWarnings("unchecked")
		final Comparator<T> cmp = instance;
		return cmp;
	}

	public static <T> boolean isNull(final Comparator<T> comparator)
	{
		return comparator == null || comparator == instance;
	}

	private NullComparator()
	{
		super();
	}

	@Override
	public int compare(final Object o1, final Object o2)
	{
		return 0;
	}
}
