package de.metas.util.collections;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.IteratorWrapper;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class IteratorUtilsTest
{
	@Test
	public void test_close_AutoCloseable()
	{
		final MockedAutoCloseableIterator iterator = new MockedAutoCloseableIterator();
		IteratorUtils.close(iterator);
		Assert.assertTrue(iterator.closed);
	}

	@Test
	public void test_close_AutoCloseable_Wrapped()
	{
		final MockedAutoCloseableIterator iterator = new MockedAutoCloseableIterator();
		IteratorUtils.close(new MockedIteratorWrapper<>(iterator));
		Assert.assertTrue(iterator.closed);
	}

	@Test
	public void test_close_Closeable()
	{
		final MockedCloseableIterator iterator = new MockedCloseableIterator();
		IteratorUtils.close(iterator);
		Assert.assertTrue(iterator.closed);
	}

	@Test
	public void test_close_Closeable_Wrapped()
	{
		final MockedCloseableIterator iterator = new MockedCloseableIterator();
		IteratorUtils.close(new MockedIteratorWrapper<>(iterator));
		Assert.assertTrue(iterator.closed);
	}

	private static class MockedIterator implements Iterator<Object>
	{

		@Override
		public boolean hasNext()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public Object next()
		{
			throw new UnsupportedOperationException();
		}

		@Override
		public void remove()
		{
			throw new UnsupportedOperationException();
		}
	}

	private static class MockedAutoCloseableIterator extends MockedIterator implements AutoCloseable
	{
		public boolean closed = false;

		@Override
		public void close() throws Exception
		{
			closed = true;
		}
	}

	private static class MockedCloseableIterator extends MockedIterator implements Closeable
	{
		public boolean closed = false;

		@Override
		public void close() throws IOException
		{
			closed = true;
		}
	}

	private static class MockedIteratorWrapper<T> implements IteratorWrapper<T>, Iterator<T>
	{
		private final Iterator<T> delegate;

		public MockedIteratorWrapper(final Iterator<T> delegate)
		{
			super();
			this.delegate = delegate;
		}

		@Override
		public boolean hasNext()
		{
			return delegate.hasNext();
		}

		@Override
		public T next()
		{
			return delegate.next();
		}

		@Override
		public void remove()
		{
			delegate.remove();
		}

		@Override
		public Iterator<T> getParentIterator()
		{
			return delegate;
		}
	}

}
