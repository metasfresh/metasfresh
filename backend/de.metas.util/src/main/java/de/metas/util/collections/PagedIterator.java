package de.metas.util.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import de.metas.util.Check;
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
	private final PageFetcher<E> pageFetcher;
	private final int pageSize;
	private final int lastRow;

	private int nextPageFirstRow;
	private Iterator<E> currentPageIterator;
	private boolean finished = false;

	@Builder
	private PagedIterator(
			@NonNull final PageFetcher<E> pageFetcher,
			final int pageSize,
			final int firstRow, // first row, zero based
			final int maxRows)
	{
		Check.assume(firstRow >= 0, "firstRow >= 0");
		Check.assume(pageSize > 0, "pageSize > 0");

		this.pageFetcher = pageFetcher;
		this.pageSize = pageSize;

		final int maxRowsEffective = maxRows > 0 ? maxRows : Integer.MAX_VALUE;
		lastRow = firstRow + maxRowsEffective;

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
			final Collection<E> currentPage = getAndIncrementPage();
			if (currentPage == null)
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

	private final Collection<E> getAndIncrementPage()
	{
		final int pageFirstRow = nextPageFirstRow;
		final int pageSize = computePageSize(pageFirstRow);
		if (pageSize <= 0)
		{
			return null;
		}

		final Page<E> currentPage = pageFetcher.getPage(pageFirstRow, pageSize);
		if (currentPage == null)
		{
			return null;
		}

		if (currentPage.getLastRowZeroBased() != null)
		{
			nextPageFirstRow = currentPage.getLastRowZeroBased() + 1;
		}
		else
		{
			nextPageFirstRow = computeNextPageFirstRow(pageFirstRow, pageSize);
		}

		return currentPage.getRows();
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

	@lombok.Value
	public static final class Page<E>
	{
		public static <E> Page<E> ofRows(final List<E> rows)
		{
			final Integer lastRow = null;
			return new Page<>(rows, lastRow);
		}

		public static <E> Page<E> ofRowsOrNull(final List<E> rows)
		{
			return rows != null && !rows.isEmpty()
					? ofRows(rows)
					: null;
		}

		public static <E> Page<E> ofRowsAndLastRowIndex(final List<E> rows, final int lastRowZeroBased)
		{
			return new Page<>(rows, lastRowZeroBased);
		}

		private final List<E> rows;
		private final Integer lastRowZeroBased;

		private Page(final List<E> rows, final Integer lastRowZeroBased)
		{
			Check.assumeNotEmpty(rows, "rows is not empty");
			Check.assume(lastRowZeroBased == null || lastRowZeroBased >= 0, "lastRow shall be null, positive or zero");

			this.rows = rows;
			this.lastRowZeroBased = lastRowZeroBased;
		}
	}

	/** Loads and provides the requested page */
	@FunctionalInterface
	public interface PageFetcher<E>
	{
		/**
		 * @param firstRow (first page is ZERO)
		 * @param pageSize max rows to return
		 * @return page or null in case there is no page
		 */
		Page<E> getPage(int firstRow, int pageSize);
	}
}
