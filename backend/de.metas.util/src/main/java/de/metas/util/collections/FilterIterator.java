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
import java.util.NoSuchElementException;
import java.util.function.Predicate;

/**
 * An {@link Iterator} which uses a {@link Predicate} to filter the items from underlying {@link Iterator}.
 * 
 * @author tsa
 * 
 * @param <E>
 */
public class FilterIterator<E> implements Iterator<E>, IteratorWrapper<E>
{
	private final Iterator<E> iterator;
	private final Predicate<E> predicate;

	private E next;
	private boolean nextSet = false;

	public FilterIterator(final Iterator<E> iterator, final Predicate<E> predicate)
	{
		super();
		if (iterator == null)
		{
			throw new IllegalArgumentException("iterator is null");
		}
		this.iterator = iterator;
		this.predicate = predicate;
	}

	public FilterIterator(final BlindIterator<E> iterator, final Predicate<E> predicate)
	{
		this(new BlindIteratorWrapper<E>(iterator), predicate);
	}

	@Override
	public boolean hasNext()
	{
		if (nextSet)
		{
			return true;
		}
		else
		{
			return setNextValid();
		}
	}

	@Override
	public E next()
	{
		if (!nextSet)
		{
			if (!setNextValid())
			{
				throw new NoSuchElementException();
			}
		}
		nextSet = false;
		return next;
	}

	/**
	 * Set next valid element
	 * 
	 * @return true if next valid element was found and set
	 */
	private final boolean setNextValid()
	{
		while (iterator.hasNext())
		{
			final E element = iterator.next();
			if (predicate.test(element))
			{
				next = element;
				nextSet = true;
				return true;
			}
		}
		return false;
	}

	/**
	 * @throws UnsupportedOperationException always
	 */
	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator<E> getParentIterator()
	{
		return iterator;
	}

}
