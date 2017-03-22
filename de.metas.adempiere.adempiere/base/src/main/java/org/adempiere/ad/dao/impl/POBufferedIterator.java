package org.adempiere.ad.dao.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.model.POInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Buffered {@link Iterator} over a {@link TypedSqlQuery} result.
 *
 * @author tsa
 *
 * @param <ET> model interface
 */
/* package */final class POBufferedIterator<T, ET extends T> implements Iterator<ET>
{
	private static final transient Logger logger = LogManager.getLogger(POBufferedIterator.class);

	private final TypedSqlQuery<T> query;
	private final Class<ET> clazz;
	private final String rowNumberColumn;

	private int bufferSize = 50;
	private int offset = 0; // in the DB, line is set via the row_number() window function wich starts at 1

	private Iterator<ET> bufferIterator;

	/**
	 * Buffer was fully loaded? True when buffer contains as much data as it was required. If this flag is false then it's a good indicator that we are on last page.
	 *
	 */
	private boolean bufferFullyLoaded = false;

	/**
	 *
	 * @param query
	 * @param clazz
	 * @param rowNumberColumn optional, may be <code>null</code>.
	 *            If a column is given, then this iterator will not use offset, but assume that the column contain a row number that starts at 1,
	 *            as created by the <code>row_nbumber()</code> window function. This class will then use this column by not paging with offset, but with in the where-clause "rowNumberColumn > offset".
	 *            Thanks to http://use-the-index-luke.com/no-offset.
	 *
	 */
	/* package */ POBufferedIterator(
			final TypedSqlQuery<T> query,
			final Class<ET> clazz,
			final String rowNumberColumn)
	{
		// Make sure database paging is supported
		if (!DB.getDatabase().isPagingSupported())
		{
			throw new DBException("Database paging support is required in order to have " + POBufferedIterator.class + " working");
		}

		Check.assume(query != null, "query != null");
		this.query = query.copy();
		if (Check.isEmpty(this.query.getOrderBy(), true))
		{
			final String orderBy = buildOrderBy(this.query.getTableName());
			if (Check.isEmpty(orderBy))
			{
				throw new DBException("Query does not have ORDER BY and we could not build one for given table because there are no key columns: " + query);
			}

			logger.trace("Using default build-in ORDER BY: {}", orderBy);
			this.query.setOrderBy(orderBy);
		}

		// Check.assume(clazz != null, "clazz != null"); // class can be null
		this.clazz = clazz;
		this.rowNumberColumn = rowNumberColumn;
	}

	/**
	 * Build standard ORDER BY clause (by Key Columns).
	 *
	 * @param tableName
	 * @return ORDER BY clause or ""
	 */
	private static String buildOrderBy(final String tableName)
	{
		final POInfo poInfo = POInfo.getPOInfo(tableName);

		final StringBuilder orderBy = new StringBuilder();
		for (String keyColumnName : poInfo.getKeyColumnNames())
		{
			if (orderBy.length() > 0)
			{
				orderBy.append(", ");
			}
			orderBy.append(keyColumnName);
		}

		return orderBy.toString();
	}

	@Override
	public boolean hasNext()
	{
		final Iterator<ET> it = getBufferIterator();
		return it.hasNext();
	}

	@Override
	public ET next()
	{
		final Iterator<ET> it = getBufferIterator();
		final ET value = it.next();
		return value;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException("Remove operation not supported.");
	}

	private Iterator<ET> getBufferIterator()
	{
		// Buffer iterator was not initialized yet, loading first page
		if (bufferIterator == null)
		{
			loadNextPage();
			return bufferIterator;
		}

		// Buffer iterator has reached the end. We load the next page only if current page was fully load.
		// Else, makes no sense to load next page because last page was a short one so we are sure that there are no more pages
		if (!bufferIterator.hasNext() && bufferFullyLoaded)
		{
			loadNextPage();
		}

		return bufferIterator;
	}

	private void loadNextPage()
	{
		final TypedSqlQuery<T> queryToUse;

		query.setLimit(bufferSize);
		if (Check.isEmpty(rowNumberColumn, true))
		{
			query.setLimit(bufferSize, offset);
			queryToUse = query;
		}
		else
		{
			query.setLimit(bufferSize);
			queryToUse = query.addWhereClause(true, rowNumberColumn + " > " + offset);
		}
		final List<ET> buffer = queryToUse.list(clazz);
		bufferIterator = buffer.iterator();

		final int bufferSizeActual = buffer.size();
		bufferFullyLoaded = bufferSizeActual >= bufferSize;

		if (logger.isDebugEnabled())
		{
			logger.debug("Loaded next page: bufferSize=" + bufferSize + ", offset=" + offset + " => " + bufferSizeActual + " records (fullyLoaded=" + bufferFullyLoaded + ")");
		}

		offset += bufferSizeActual;
	}

	/**
	 * Sets buffer/page size, i.e. the number of rows to be loaded by this iterator at a time.
	 *
	 * @param bufferSize
	 * @see Query#OPTION_IteratorBufferSize
	 */
	public void setBufferSize(int bufferSize)
	{
		Check.assume(bufferSize > 0, "bufferSize > 0");
		this.bufferSize = bufferSize;
	}

	public int getBufferSize()
	{
		return bufferSize;
	}

	@Override
	public String toString()
	{
		return "POBufferedIterator [clazz=" + clazz
				+ ", bufferSize=" + bufferSize
				+ ", offset=" + offset
				+ ", query=" + query
				+ "]";
	}
}
