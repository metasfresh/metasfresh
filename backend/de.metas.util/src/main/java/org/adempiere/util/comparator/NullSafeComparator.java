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

/**
 * A comparator that can deal with <code>null</code>
 * 
 * Thanks to http://stackoverflow.com/questions/481813/how-to-simplify-a-null-safe-compareto-implementation.
 * @param <T>
 * 
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class NullSafeComparator<T extends Comparable> implements Comparator<T>
{
	@SuppressWarnings("unchecked")
	@Override
	public int compare(T one, T two)
	{
		
		if (one == null ^ two == null)
		{
			return (one == null) ? -1 : 1;
		}

		if (one == null && two == null)
		{
			return 0;
		}

		return one.compareTo(two);
	}

	

}
