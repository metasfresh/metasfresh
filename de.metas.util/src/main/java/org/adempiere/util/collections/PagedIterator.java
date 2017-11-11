package org.adempiere.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PagedIterator<E> implements Iterator<E>
{
	private static final int DEFAULT_PAGESIZE = 100;

	private final PageFetcher<E> pageFetcher;
	private final int pageSize;

	private final int lastRow;

	private int nextPageFirstRow;
	private Iterator<E> currentPageIterator;
	private boolean finished = false;

	@Builder
	private PagedIterator(
			final int firstRow, // first row, zero based
			final int maxRows,
			@NonNull final PageFetcher<E> pageFetcher,
			final int pageSize)
	{
		Check.assume(firstRow >= 0, "firstRow >= 0");

		final int maxRowsEffective = maxRows > 0 ? maxRows : Integer.MAX_VALUE;
		this.lastRow = firstRow + maxRowsEffective;
		this.pageFetcher = pageFetcher;
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGESIZE;

		nextPageFirstRow = firstRow;
	}

	@Override
	public boolean hasNext()
	{
		if (finished)
		{
			return false;
		}

		if (currentPageIterator == null || !currentPageIterator.hasNext())
		{
			final int pageFirstRow = nextPageFirstRow;
			final int pageSize = computePageSize(pageFirstRow);
			if (pageSize <= 0)
			{
				currentPageIterator = null;
				finished = true;
				return false;
			}

			nextPageFirstRow = computeNextPageFirstRow(pageFirstRow, pageSize);

			final Collection<E> currentPage = pageFetcher.getPage(pageFirstRow, pageSize);
			if (currentPage.isEmpty())
			{
				currentPageIterator = null;
				finished = true;
				return false;
			}
			else
			{
				currentPageIterator = currentPage.iterator();
				return true;
			}
		}
		else
		{
			return currentPageIterator.hasNext();
		}
	}

	private int computePageSize(final int firstRow)
	{
		final int remainingRows = lastRow - firstRow;
		if (remainingRows <= 0)
		{
			return 0;
		}
		return Math.min(pageSize, remainingRows);
	}

	private int computeNextPageFirstRow(final int firstRow, final int pageSize)
	{
		return firstRow + pageSize;
	}

	@Override
	public E next()
	{
		if (!hasNext())
		{
			throw new NoSuchElementException();
		}

		return currentPageIterator.next();
	}

	public Stream<E> stream()
	{
		return IteratorUtils.stream(this);
	}

	/** Loads and provides the requested page */
	@FunctionalInterface
	public interface PageFetcher<E>
	{
		/**
		 * @param firstRow (first page is ZERO)
		 * @param pageSize max rows to return
		 * @return page
		 */
		Collection<E> getPage(int firstRow, int pageSize);
	}
}
