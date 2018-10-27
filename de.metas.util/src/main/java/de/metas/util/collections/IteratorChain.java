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


import java.io.Closeable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * An {@link Iterator} implementation which chains a collection of iterators.
 * 
 * @author tsa
 * 
 * @param <E>
 */
public class IteratorChain<E> implements Iterator<E>, Closeable
{
	private final List<Iterator<E>> iterators = new ArrayList<Iterator<E>>();
	private boolean addingLocked = false;

	/**
	 * Add another iterator at the end of the chain.
	 * 
	 * @param it
	 * @return this
	 * @throws IllegalArgumentException if <code>it</code> is null
	 * @throws IllegalStateException if the chain is locked due to some read/write operations
	 */
	public IteratorChain<E> addIterator(Iterator<E> it)
	{
		if (it == null)
		{
			throw new IllegalArgumentException("it is null");
		}
		if (addingLocked)
		{
			throw new IllegalStateException("Cannot add more iterators after chain was locked due to a read operation");
		}

		iterators.add(it);

		return this;
	}

	@Override
	public boolean hasNext()
	{
		addingLocked = true;

		while (!iterators.isEmpty())
		{
			final Iterator<E> it = iterators.get(0);
			final boolean hasNext = it.hasNext();
			if (hasNext)
			{
				return true;
			}

			final Iterator<E> removedIterator = iterators.remove(0);
			IteratorUtils.close(removedIterator);
		}

		return false;
	}

	@Override
	public E next()
	{
		addingLocked = true;
		return iterators.get(0).next();
	}

	@Override
	public void remove()
	{
		iterators.get(0).remove();
		addingLocked = true;
	}

	@Override
	public void close()
	{
		for (final Iterator<?> iterator : iterators)
		{
			IteratorUtils.close(iterator);
		}
	}
}
