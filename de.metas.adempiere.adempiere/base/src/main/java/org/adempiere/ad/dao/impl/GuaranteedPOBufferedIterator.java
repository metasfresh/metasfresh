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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.IQuery;
import org.compiere.util.DB;
import org.slf4j.Logger;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import de.metas.logging.LogManager;

/**
 * Buffered {@link Iterator} over a {@link TypedSqlQuery} result.
 *
 * This iterator works like {@link POBufferedIterator} but in this case the result is guaranteed.
 *
 * @author tsa
 *
 * @param <ET> model interface
 */
/* package */final class GuaranteedPOBufferedIterator<T, ET extends T> implements Iterator<ET>
{
	private static final transient Logger logger = LogManager.getLogger(GuaranteedPOBufferedIterator.class);

	/** Original query */
	private final TypedSqlQuery<T> query;
	/** Model class */
	private final Class<ET> clazz;
	/** Model's Key Column Name (NOT fully qualified) */
	private final String keyColumnName;

	/** Selection ID */
	// NOTE: we are keeping here as class field only because we want to have it in toString().
	private final String querySelectionUUID;
	/** How many rows are in our selection */
	private final int rowsCount;
	/**
	 * How many rows were fetched from our selection until now.
	 *
	 * It's value means:
	 * <ul>
	 * <li>ZERO - no rows were fetched from the buffer (yet)
	 * <li>greather than zero - how many rows were fetched from the buffer until now
	 * <li>equals with {@link #rowsCount} - we have reached the end of our selection.
	 * </ul>
	 *
	 * NOTE: we are also counting invalid models because the main purpose of this counter is to be compared with {@link #rowsCount} which will tell us if we reached the end of our selection.
	 */
	private int rowsFetched = 0;
	/**
	 * Underlying buffered iterator used to actually load the records page by page.
	 *
	 * NOTE: never ever use this iterator for navigating but use the {@link #peekingBufferedIterator}. The only reason why we have it as a class field is because we want to set things like BufferSize
	 * and because we want to include it in toString().
	 */
	private final POBufferedIterator<ET, ET> bufferedIterator;
	/** Peeking iterator which wraps {@link #bufferedIterator} */
	private final PeekingIterator<ET> peekingBufferedIterator;

	/* package */GuaranteedPOBufferedIterator(final TypedSqlQuery<T> query, final Class<ET> clazz)
	{
		super();

		Check.assumeNotNull(query, "query not null");
		this.query = query;

		// Check.assume(clazz != null, "clazz != null"); // class can be null
		this.clazz = clazz;
		this.querySelectionUUID = UUID.randomUUID().toString();

		final String trxName = query.getTrxName();

		final String tableName = query.getTableName();
		this.keyColumnName = query.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new DBException("Table " + tableName + " has 0 or more than 1 key columns");
		}
		final String keyColumnNameFQ = tableName + "." + keyColumnName;

		//
		// Select the records using the original query and INSERT their IDs to our T_Query_Selection
		{
			final String orderBy = query.getOrderBy();

			final StringBuilder sqlRowNumber = new StringBuilder("row_number() OVER (");
			if (!Check.isEmpty(orderBy, true))
			{
				sqlRowNumber.append("ORDER BY ").append(orderBy);
			}
			sqlRowNumber.append(")");

			final StringBuilder sqlInsertIntoSelect = new StringBuilder();
			sqlInsertIntoSelect.append("INSERT INTO T_Query_Selection (uuid, line, record_id)")
					.append(" SELECT ")
					.append(DB.TO_STRING(querySelectionUUID))
					.append(", ").append(sqlRowNumber)
					.append(", ").append(keyColumnNameFQ)
					.append(" FROM ").append(tableName);

			final String sql = query.buildSQL(sqlInsertIntoSelect, true); // useOrderByClause = true
			final List<Object> params = query.getParametersEffective();

			this.rowsCount = DB.executeUpdateEx(sql,
					params == null ? null : params.toArray(),
					trxName);

			if (logger.isTraceEnabled())
			{
				logger.info("sql=" + sql + ", params=" + params + ", trxName=" + trxName + ", rowsCount=" + rowsCount);
			}
		}

		//
		// If model class is null (which it currently is allowed to be!), then find our class to use from the table name
		final Class<ET> clazzToUse;
		if (clazz == null)
		{
			Check.errorIf(Check.isEmpty(tableName, true), "If, class is null, then at least the tableName has to be != null in this={}", this);
			@SuppressWarnings("unchecked")
			final Class<ET> clazzForTableName = (Class<ET>)TableModelClassLoader.instance.getClass(tableName);
			clazzToUse = clazzForTableName;
		}
		else
		{
			clazzToUse = clazz;
		}

		//
		// Build the query used to retrieve models by querying the selection.
		// NOTE: we are using LEFT OUTER JOIN instead of INNER JOIN because
		// * methods like hasNext() are comparing the rowsFetched counter with rowsCount to detect if we reached the end of the selection (optimization).
		// * POBufferedIterator is using LIMIT/OFFSET clause for fetching the next page and eliminating rows from here would fuck the paging if one record was deleted in meantime.
		// So we decided to load everything here, and let the hasNext() method to deal with the case when the record is really missing.
		final String selectionSqlFrom = "(SELECT UUID as ZZ_UUID, Record_ID as ZZ_Record_ID, Line as ZZ_Line FROM T_Query_Selection) s "
				+ "\n LEFT OUTER JOIN " + tableName + " ON (" + keyColumnNameFQ + "=s.ZZ_Record_ID)";
		final String selectionWhereClause = "s.ZZ_UUID=?";
		final String selectionOrderBy = "s.ZZ_Line";
		final TypedSqlQuery<ET> querySelection = new TypedSqlQuery<ET>(query.getCtx(), clazzToUse, tableName, selectionWhereClause, trxName)
				.setParameters(querySelectionUUID)
				.setSqlFrom(selectionSqlFrom)
				.setOrderBy(selectionOrderBy);

		//
		// Create the buffered iterator which will retrieve from selection, page by page
		this.bufferedIterator = new POBufferedIterator<ET, ET>(querySelection, clazzToUse);
		this.peekingBufferedIterator = Iterators.peekingIterator(this.bufferedIterator);
	}

	/**
	 * Checks if given <code>model</code> is a valid model which can be returned by {@link #next()}.
	 *
	 * A model is considered invalid when
	 * <ul>
	 * <li>it's ID is null, which means it was deleted after the selection was build. Those records shall be skipped
	 * </ul>
	 *
	 * @param model
	 * @return
	 */
	private final boolean isValidModel(final ET model)
	{
		//
		// Make sure the ID column has a not null value.
		// If there is a null value it means the record was not found in database (e.g. it was deleted in meantime)
		// NOTE: we assume org.compiere.model.PO.loadColumn(int, ResultSet) is setting null in the values array if the column's value was null.
		final Object idObj = InterfaceWrapperHelper.getValueOrNull(model, keyColumnName);
		if (idObj == null)
		{
			return false;
		}

		return true;
	}

	@Override
	public boolean hasNext()
	{
		// NOTE: we do this checking only to have last page optimizations
		// e.g. consider having 100 records, page size=10, so last page will be fully loaded.
		// If we are not doing this checking, the buffered iterator will try to load the next page (which is empty),
		// in order to find out that there is nothing to be loaded.
		if (rowsFetched >= rowsCount)
		{
			return false;
		}

		while (peekingBufferedIterator.hasNext())
		{
			// Check the rowsFetched again, because in this while we are also navigating forward and we skip invalid values
			if (rowsFetched >= rowsCount)
			{
				return false;
			}

			final ET value = peekingBufferedIterator.peek();
			if (isValidModel(value))
			{
				return true;
			}
			// not a valid model:
			else
			{
				peekingBufferedIterator.next(); // skip this element because it's not valid
				rowsFetched++; // increase the rowsFetched because we use it to compare with "rowsCount" in order to figure out when we reached the end of the selection.
			}
		}

		return false;
	}

	@Override
	public ET next()
	{
		final ET value = peekingBufferedIterator.next();
		rowsFetched++;
		return value;
	}

	@Override
	public void remove()
	{
		peekingBufferedIterator.remove();
	}

	/**
	 * Sets buffer/page size, i.e. the number of rows to be loaded by this iterator at a time.
	 *
	 * @param bufferSize
	 * @see IQuery#OPTION_IteratorBufferSize
	 */
	public void setBufferSize(final int bufferSize)
	{
		bufferedIterator.setBufferSize(bufferSize);
	}

	public int getBufferSize()
	{
		return bufferedIterator.getBufferSize();
	}

	@Override
	public String toString()
	{
		return "GuaranteedPOBufferedIterator [clazz=" + clazz
				+ ", querySelectionUUID=" + querySelectionUUID
				+ ", rowsFetched=" + rowsFetched
				+ ", rowsCount=" + rowsCount
				+ ", query=" + query
				+ ", bufferedIterator=" + bufferedIterator
				+ "]";
	}

}
