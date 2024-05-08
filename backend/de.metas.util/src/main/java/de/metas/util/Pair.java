package de.metas.util;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;

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


/**
 * Generic class to hold a pair of objects. The given first and second may be null.
 * 
 * @author ts
 * @author tsa
 * 
 * @param <F> the type of the first item
 * @param <S> the type of the second item
 * 
 * @deprecated Please consider using {@link ImmutablePair}
 */
@Deprecated
public final class Pair<F, S> implements IPair<F, S>
{
	private final F first;

	private final S second;

	public Pair(final F first, final S second)
	{
		super();
		this.first = first;
		this.second = second;
	}

	public F getFirst()
	{
		return first;
	}

	public S getSecond()
	{
		return second;
	}

	@Override
	public int hashCode()
	{
		// Note: i wanted to move ArrayKey from adempiere base to util, but i'm already kinda out of the tasks's original scope.
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (!(obj instanceof Pair))
		{
			return false;
		}
		final Pair<?, ?> other = (Pair<?, ?>)obj;

		if (!Check.equals(this.first, other.first))
		{
			return false;
		}

		if (!Check.equals(this.second, other.second))
		{
			return false;
		}

		return true;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("Pair [first=");
		builder.append(first);
		builder.append(", second=");
		builder.append(second);
		builder.append("]");
		return builder.toString();
	}
	
	@Override
	public F getLeft()
	{
		return getFirst();
	}
	
	@Override
	public S getRight()
	{
		return getSecond();
	}
}
