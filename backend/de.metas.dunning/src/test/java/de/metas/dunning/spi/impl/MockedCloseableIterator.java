package de.metas.dunning.spi.impl;

/*
 * #%L
 * de.metas.dunning
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;

import de.metas.util.collections.IteratorUtils;

public final class MockedCloseableIterator<E> implements Iterator<E>, Closeable
{
	private static List<MockedCloseableIterator<?>> mockedIterators = new ArrayList<MockedCloseableIterator<?>>();

	public static <E> Iterator<E> mock(final Iterator<E> iterator)
	{
		final MockedCloseableIterator<E> mockedIterator = new MockedCloseableIterator<E>(iterator);
		mockedIterators.add(mockedIterator);
		return mockedIterator;
	}

	public static void assertAllClosed()
	{
		final List<MockedCloseableIterator<?>> iteratorsNotClosed = new ArrayList<MockedCloseableIterator<?>>();
		for (MockedCloseableIterator<?> it : mockedIterators)
		{
			if (!it.isClosed())
			{
				iteratorsNotClosed.add(it);
			}
		}

		Assert.assertTrue("Not closed iterators found: " + iteratorsNotClosed, iteratorsNotClosed.isEmpty());
	}

	public static void clear()
	{
		mockedIterators.clear();
	}

	private final Iterator<E> iterator;
	private boolean closed = false;

	private MockedCloseableIterator(final Iterator<E> iterator)
	{
		if (iterator == null)
		{
			throw new IllegalArgumentException("Cannot mock an null iterator");
		}
		this.iterator = iterator;
	}

	@Override
	public boolean hasNext()
	{
		return iterator.hasNext();
	}

	@Override
	public E next()
	{
		return iterator.next();
	}

	@Override
	public void remove()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws IOException
	{
		IteratorUtils.close(iterator);
		closed = true;
	}

	public boolean isClosed()
	{
		return closed;
	}
}
