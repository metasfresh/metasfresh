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

import de.metas.util.Check;

/**
 * An comparator wrapper which is converting an comparator of type <code>IT</code> to a comparator of type <code>OT</code> by performing an unchecked cast. <br/>
 * <br/>
 * Example:
 * 
 * <pre>
 * Comparator&lt;String&gt; myStringComparator = ....;
 * Comparator&lt;Object&gt; myObjectComparator = new UncheckedCastComparator&lt;Object, String&gt;(myStringComparator);
 * 
 * List&lt;Object&gt; list = ...; // a list of Object on which we know that we have String objects
 * Collections.sort(list, myObjectComparator);
 * </pre>
 * 
 * @author tsa
 * 
 * @param <OT> output type
 * @param <IT> input type
 */
public class UncheckedCastComparator<OT, IT> implements Comparator<OT>
{
	private final Comparator<IT> comparator;

	public UncheckedCastComparator(Comparator<IT> comparator)
	{
		Check.assumeNotNull(comparator, "comparator not null");
		this.comparator = comparator;
	}

	@Override
	public int compare(OT o1, OT o2)
	{
		@SuppressWarnings("unchecked")
		final IT casted_o1 = (IT)o1;

		@SuppressWarnings("unchecked")
		final IT casted_o2 = (IT)o2;

		return comparator.compare(casted_o1, casted_o2);
	}

}
