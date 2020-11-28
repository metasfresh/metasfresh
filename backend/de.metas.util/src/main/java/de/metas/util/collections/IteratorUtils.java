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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.base.Throwables;

import de.metas.util.EmptyIterator;
import de.metas.util.collections.PagedIterator.PagedIteratorBuilder;
import lombok.NonNull;

public final class IteratorUtils
{
	/**
	 * @deprecated better follow e.g. http://www.lambdafaq.org/how-can-i-turn-an-iterator-into-an-iterable/
	 */
	@Deprecated
	public static <E> Iterable<E> asIterable(final Iterator<E> it)
	{
		return () -> it;
	}

	/**
	 * Converts an {@link Iterator} to a {@link List} by fetching all of its elements.
	 *
	 * NOTE: if Iterator is closeable, it will be closed.
	 *
	 * @param it
	 * @return unmodifiable list
	 */
	public static <E> List<E> asList(final Iterator<E> it)
	{
		try
		{
			if (!it.hasNext())
			{
				return Collections.emptyList();
			}

			final List<E> list = new ArrayList<>();
			while (it.hasNext())
			{
				final E e = it.next();
				list.add(e);
			}

			return Collections.unmodifiableList(list);
		}
		finally
		{
			close(it);
		}
	}

	public static <E> Iterator<E> asIterator(final BlindIterator<E> blindIterator)
	{
		if (blindIterator instanceof Iterator)
		{
			@SuppressWarnings("unchecked")
			final Iterator<E> it = (Iterator<E>)blindIterator;
			return it;
		}

		final Iterator<E> it = new BlindIteratorWrapper<>(blindIterator);
		return it;
	}

	public static void close(final Iterator<?> iterator)
	{
		if (iterator == null)
		{
			return;
		}

		if (iterator instanceof Closeable)
		{
			final Closeable closeable = (Closeable)iterator;
			try
			{
				closeable.close();
			}
			catch (IOException e)
			{
				throw Throwables.propagate(e);
			}
		}
		else if (iterator instanceof AutoCloseable)
		{
			final AutoCloseable closeable = (AutoCloseable)iterator;
			try
			{
				closeable.close();
			}
			catch (Exception e)
			{
				throw Throwables.propagate(e);
			}
		}
		else if (iterator instanceof IteratorWrapper)
		{
			final IteratorWrapper<?> wrapper = (IteratorWrapper<?>)iterator;
			final Iterator<?> parentIterator = wrapper.getParentIterator();
			close(parentIterator);
		}

		// iterator is not closeable, nothing to do
	}

	public static void closeQuietly(final Iterator<?> iterator)
	{
		try
		{
			close(iterator);
		}
		catch (Throwable e)
		{
			// ignore the exception
			e.printStackTrace();
		}
	}

	public static void close(final BlindIterator<?> iterator)
	{
		if (iterator == null)
		{
			return;
		}

		if (iterator instanceof Closeable)
		{
			final Closeable closeable = (Closeable)iterator;
			try
			{
				closeable.close();
			}
			catch (IOException e)
			{
				throw Throwables.propagate(e);
			}
		}
		else if (iterator instanceof AutoCloseable)
		{
			final AutoCloseable closeable = (AutoCloseable)iterator;
			try
			{
				closeable.close();
			}
			catch (Exception e)
			{
				throw Throwables.propagate(e);
			}
		}
		else if (iterator instanceof IteratorWrapper)
		{
			final IteratorWrapper<?> wrapper = (IteratorWrapper<?>)iterator;
			final Iterator<?> parentIterator = wrapper.getParentIterator();
			close(parentIterator);
		}

		// iterator is not closeable, nothing to do
	}

	public static <E> PeekIterator<E> asPeekIterator(final Iterator<E> iterator)
	{
		if (iterator instanceof PeekIterator)
		{
			final PeekIterator<E> peekIterator = (PeekIterator<E>)iterator;
			return peekIterator;
		}
		else
		{
			return new PeekIteratorWrapper<>(iterator);
		}
	}

	public static <IT, OT> Iterator<OT> map(@NonNull final Iterator<IT> iterator, @NonNull final Function<IT, OT> mapper)
	{
		return new MappingIteratorWrapper<>(iterator, mapper);
	}

	public static <T> Iterator<T> unmodifiableIterator(final Iterator<T> iterator)
	{
		return new UnmodifiableIterator<>(iterator);
	}

	public static <T> Iterator<T> emptyIterator()
	{
		return EmptyIterator.getInstance();
	}

	/**
	 * @param iterator
	 * @return the iterator wrapped to stream
	 */
	public static <T> Stream<T> stream(final Iterator<T> iterator)
	{
		Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED);
		final boolean parallel = false;
		return StreamSupport.stream(spliterator, parallel);
	}

	public static <T> Stream<T> stream(final BlindIterator<T> blindIterator)
	{
		return stream(asIterator(blindIterator));
	}

	public static <T> PagedIteratorBuilder<T> newPagedIterator()
	{
		return PagedIterator.builder();
	}
}
