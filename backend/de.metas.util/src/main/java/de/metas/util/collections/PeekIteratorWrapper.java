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


import java.util.Iterator;

// based on com.google.common.collect.Iterators.PeekingImpl
public class PeekIteratorWrapper<E> implements PeekIterator<E>, IteratorWrapper<E>
{
	private final Iterator<E> iterator;
	private boolean hasPeeked;
	private E peekedElement;

	public PeekIteratorWrapper(final Iterator<E> iterator)
	{
		super();
		if (iterator == null)
		{
			throw new IllegalArgumentException("iterator is null");
		}
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext()
	{
		return hasPeeked || iterator.hasNext();
	}

	@Override
	public E next()
	{
		if (!hasPeeked)
		{
			final E result = iterator.next();
			return result;
		}

		final E result = peekedElement;
		hasPeeked = false;
		peekedElement = null;
		return result;
	}

	@Override
	public void remove()
	{
		if (hasPeeked)
		{
			throw new IllegalStateException("Can't remove after you've peeked at next");
		}
		iterator.remove();
	}

	@Override
	public E peek()
	{
		if (!hasPeeked)
		{
			peekedElement = iterator.next();
			hasPeeked = true;
		}
		return peekedElement;
	}

	@Override
	public Iterator<E> getParentIterator()
	{
		return iterator;
	}

}
