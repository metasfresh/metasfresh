package de.metas.util;

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
 * Provides an implementation of an empty iterator.
 * 
 * @author tsa
 * 
 */
public final class EmptyIterator implements Iterator<Object>
{
	private static final EmptyIterator instance = new EmptyIterator();

	@SuppressWarnings("unchecked")
	public static <E> Iterator<E> getInstance()
	{
		return (Iterator<E>)instance;
	}

	private EmptyIterator()
	{
		super();
	}

	@Override
	public boolean hasNext()
	{
		return false;
	}

	@Override
	public Object next()
	{
		throw new NoSuchElementException();
	}

	@Override
	public void remove()
	{
		throw new IllegalStateException();
	}
}
