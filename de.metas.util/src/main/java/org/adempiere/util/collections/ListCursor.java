package org.adempiere.util.collections;

import java.util.ArrayList;

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


import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

/**
 * Iterator implementation for {@link List} which also supports {@link #current()} value.
 * 
 * The biggest difference between this implementation and other list iterators is that here we are allowing adding new elements to the list while iterating.
 * 
 * WARNING: behaviour is unexpected in case you are adding items before the cursor's position.
 * 
 * @author tsa
 *
 * @param <T>
 */
public final class ListCursor<T> implements Iterator<T>
{
	private final List<T> list= new ArrayList<>();
	
	private int currentIndex = -1;
	private T currentItem = null;
	private boolean currentItemSet = false;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public List<T> getList()
	{
		return list;
	}

	/**
	 * Append item at the end of the underlying list.
	 * 
	 * @param item
	 */
	public void append(final T item)
	{
		list.add(item);
	}

	public T current()
	{
		Check.assume(currentItemSet, "has current item");
		return currentItem;
	}

	public void setCurrentValue(final T value)
	{
		Check.assume(currentItemSet, "has current item");
		list.set(currentIndex, value);
		this.currentItem = value;
	}

	public void closeCurrent()
	{
		currentItemSet = false;
		currentItem = null;
	}

	public boolean hasCurrent()
	{
		return currentItemSet;
	}

	@Override
	public boolean hasNext()
	{
		final int size = list.size();
		if (currentIndex < 0)
		{
			return size > 0;
		}
		else
		{
			return currentIndex + 1 < size;
		}

	}

	@Override
	public T next()
	{
		// Calculate the next index
		final int nextIndex;
		if (currentIndex < 0)
		{
			nextIndex = 0;
		}
		else
		{
			nextIndex = currentIndex + 1;
		}

		// Make sure the new index is valid
		final int size = list.size();
		if (nextIndex >= size)
		{
			throw new NoSuchElementException("index=" + nextIndex + ", size=" + size);
		}

		// Update status
		this.currentIndex = nextIndex;
		this.currentItem = list.get(nextIndex);
		this.currentItemSet = true;

		return currentItem;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
