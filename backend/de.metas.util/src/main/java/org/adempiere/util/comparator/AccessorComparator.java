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

import de.metas.util.TypedAccessor;
import lombok.NonNull;

/**
 * Wraps a given comparator by transforming the values from outer type to inner type
 *
 * @author tsa
 *
 * @param <OT> outer type
 * @param <IT> inner type
 */
public class AccessorComparator<OT, IT> implements Comparator<OT>
{
	private final Comparator<IT> cmp;
	private final TypedAccessor<IT> accessor;

	/**
	 *
	 * @param cmp comparator to be used for comparing inner type values
	 * @param accessor accessor which will get the inner type from a given outer type object
	 */
	public AccessorComparator(
			@NonNull final Comparator<IT> cmp,
			@NonNull final TypedAccessor<IT> accessor)
	{
		this.cmp = cmp;
		this.accessor = accessor;
	}

	@Override
	public int compare(OT o1, OT o2)
	{
		if (o1 == o2)
		{
			return 0;
		}
		if (o1 == null)
		{
			return +1;
		}
		if (o2 == null)
		{
			return -1;
		}

		final IT value1 = accessor.getValue(o1);
		final IT value2 = accessor.getValue(o2);

		return cmp.compare(value1, value2);
	}

	@Override
	public String toString()
	{
		return "AccessorComparator [cmp=" + cmp + ", accessor=" + accessor + "]";
	}
}
