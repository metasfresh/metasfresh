package org.adempiere.util.lang;

import java.io.Serializable;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Mutable <code>int</code> wrapper.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public final class MutableInt implements Comparable<MutableInt>, Serializable
{
	private int value;

	public MutableInt(final int value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@Override
	public int hashCode()
	{
		return value;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		if (obj instanceof MutableInt)
		{
			final MutableInt other = (MutableInt)obj;
			return value == other.value;
		}
		else
		{
			return false;
		}
	}

	@Override
	public int compareTo(final MutableInt obj)
	{
		return value - obj.value;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(final int value)
	{
		this.value = value;
	}

	public int incrementAndGet()
	{
		value++;
		return value;
	}

	public int decrementAndGet()
	{
		value--;
		return value;
	}

}
