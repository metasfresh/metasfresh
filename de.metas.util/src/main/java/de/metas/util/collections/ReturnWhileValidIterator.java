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

import de.metas.util.Check;

/**
 * An interator wrapper which it's {@link #hasNext()} and {@link #next()} methods will always return the same element until configured <code>isValidPredicate</code> predicate will say the element is
 * no longer valid.
 *
 * To be used when you want to iterate over mutable elements which you want to keep them in the iterator's current position while a condition is matched.
 * 
 * 
 * @author tsa
 *
 * @param <T> element type
 */
public class ReturnWhileValidIterator<T> implements Iterator<T>, IteratorWrapper<T>
{
	private final Predicate<T> isValidPredicate;
	private final Iterator<T> iterator;
	private T current;

	public ReturnWhileValidIterator(final Iterator<T> iterator, final Predicate<T> isValidPredicate)
	{
		super();

		Check.assumeNotNull(iterator, "iterator not null");
		this.iterator = iterator;

		Check.assumeNotNull(isValidPredicate, "isValidPredicate not null");
		this.isValidPredicate = isValidPredicate;
	}

	@Override
	public final Iterator<T> getParentIterator()
	{
		return iterator;
	}

	@Override
	public final boolean hasNext()
	{
		clearCurrentIfNotValid();
		if (current != null)
		{
			return true;
		}

		return iterator.hasNext();
	}

	private final void clearCurrentIfNotValid()
	{
		if (current == null)
		{
			return;
		}

		final boolean valid = isValidPredicate.test(current);
		if (!valid)
		{
			current = null;
		}
	}

	@Override
	public final T next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		// NOTE: we assume "current" was also checked if it's still valid (in hasNext() method)

		if (current != null)
		{
			return current;
		}

		return iterator.next();
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
