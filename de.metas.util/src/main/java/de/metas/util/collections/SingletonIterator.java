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

/**
 * Immutable iterator of a single element
 */
public class SingletonIterator<E> implements Iterator<E>
{
	private final E element;
	private boolean consumed = false;

	public SingletonIterator(E element)
	{
		this.element = element;
	}

	@Override
	public boolean hasNext()
	{
		return !consumed;
	}

	@Override
	public E next()
	{
		if (consumed)
		{
			throw new NoSuchElementException();
		}

		consumed = true;
		return element;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

}
