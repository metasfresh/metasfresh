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

/**
 * Wraps a given iterator and makes it readonly (i.e. {@link #remove()} will throw exception)
 * 
 * @author tsa
 *
 * @param <T>
 */
/* package */final class UnmodifiableIterator<T> implements Iterator<T>
// , IteratorWrapper<E> // don't implement it because we want to hide the implementation
{
	private final Iterator<T> iterator;

	public UnmodifiableIterator(final Iterator<T> iterator)
	{
		super();
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	@Override
	public T next()
	{
		return iterator.next();
	}

	/**
	 * @throws UnsupportedOperationException
	 */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
