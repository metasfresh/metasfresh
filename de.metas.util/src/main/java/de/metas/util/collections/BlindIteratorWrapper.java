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
import java.util.Iterator;

/**
 * Converts a {@link BlindIterator} to a true {@link Iterator} (which has {@link #hasNext()}).
 * 
 * @author tsa
 * 
 * @param <E>
 */
public class BlindIteratorWrapper<E> implements Iterator<E>, Closeable
{
	private final BlindIterator<E> iterator;

	private E next;

	public BlindIteratorWrapper(final BlindIterator<E> iterator)
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
		if (next == null)
		{
			next = iterator.next();
		}

		return next != null;
	}

	@Override
	public E next()
	{
		if (next != null)
		{
			final E retValue = next;
			next = null;
			return retValue;
		}

		return iterator.next();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("remove operation not supported");
	}

	@Override
	public void close()
	{
		IteratorUtils.close(iterator);
	}

}
