package org.adempiere.ad.dao.impl;

import java.io.Closeable;

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
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.persistence.TableModelClassLoader;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.IQuery;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

import de.metas.dao.selection.QuerySelectionHelper;
import de.metas.dao.selection.QuerySelectionHelper.UUISelection;
import de.metas.dao.selection.QuerySelectionToDeleteHelper;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Buffered {@link Iterator} over a {@link TypedSqlQuery} result.
 * <p>
 * This iterator works like {@link POBufferedIterator} but in this case the result is guaranteed.
 *
 * @author tsa
 *
 * @param <ET> model interface
 */
/* package */final class GuaranteedPOBufferedIterator<T, ET extends T> implements Iterator<ET>, Closeable
{
	/** Original query */
	private final TypedSqlQuery<T> query;
	/** Model class */
	private final Class<ET> clazz;
	/** Model's Key Column Name (NOT fully qualified) */
	private final String keyColumnName;

	/** Selection ID */
	// NOTE: we are keeping here as class field only because we want to have it in toString().
	private final String querySelectionUUID;
	private final String trxName;
	/** How many rows are in our selection */
	private final long rowsCount;
	/**
	 * How many rows were fetched from our selection until now.
	 * <p>
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
	 * <p>
	 * NOTE: never ever use this iterator for navigating but use the {@link #peekingBufferedIterator}. The only reason why we have it as a class field is because we want to set things like BufferSize
	 * and because we want to include it in toString().
	 */
	private final POBufferedIterator<ET, ET> bufferedIterator;

	/** Peeking iterator which wraps {@link #bufferedIterator} */
	private final PeekingIterator<ET> peekingBufferedIterator;

	private final AtomicBoolean closed = new AtomicBoolean(false);

	/* package */ GuaranteedPOBufferedIterator(@NonNull final TypedSqlQuery<T> query, final Class<ET> clazz)
	{
		this.query = query;

		// Check.assume(clazz != null, "clazz != null"); // class can be null
		this.clazz = clazz;

		this.trxName = query.getTrxName();

		final String tableName = query.getTableName();
		this.keyColumnName = query.getKeyColumnName();
		if (keyColumnName == null)
		{
			throw new DBException("Table " + tableName + " has 0 or more than 1 key columns");
		}

		//
		// Select the records using the original query and INSERT their IDs to our T_Query_Selection
		// !! ...using the query's ordering, such that for non-UNION'ed-queries, T_Query_Selection.Line reflects this ordering !!
		final UUISelection uuidSelection = QuerySelectionHelper.createUUIDSelection(query);
		this.rowsCount = uuidSelection.getSize();
		this.querySelectionUUID = uuidSelection.getUuid();

		//
		// If model class is null (which it currently is allowed to be!) or e.g. just "Object", then find our class to use from the table name
		final Class<ET> clazzToUse;
		if (!InterfaceWrapperHelper.isModelInterface(clazz))
		{
			Check.errorIf(Check.isEmpty(tableName, true), "If, class is null or not a model interface, then at least the tableName has to be != null in this={}", this);
			@SuppressWarnings("unchecked")
			final Class<ET> clazzForTableName = (Class<ET>)TableModelClassLoader.instance.getClass(tableName);
			clazzToUse = clazzForTableName;
		}
		else
		{
			clazzToUse = clazz;
		}

		final TypedSqlQuery<ET> querySelection = QuerySelectionHelper.createUUIDSelectionQuery(
				PlainContextAware.newWithTrxName(query.getCtx(), query.getTrxName()),
				clazzToUse,
				querySelectionUUID);

		//
		// Create the buffered iterator which will retrieve from selection, page by page
		// provide column ZZ_Line so the iterator can page without using OFFSET
		this.bufferedIterator = new POBufferedIterator<>(
				querySelection,
				clazzToUse,
				QuerySelectionHelper.SELECTION_LINE_ALIAS);
		this.peekingBufferedIterator = Iterators.peekingIterator(this.bufferedIterator);
	}

	@Override
	protected void finalize()
	{
		QuerySelectionToDeleteHelper.scheduleDeleteSelection(querySelectionUUID, trxName);
	}

	/**
	 * Checks if given <code>model</code> is a valid model which can be returned by {@link #next()}.
	 * <p>
	 * A model is considered invalid when
	 * <ul>
	 * <li>it's ID is null, which means it was deleted after the selection was build. Those records shall be skipped
	 * </ul>
	 */
	private boolean isValidModel(final ET model)
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

	private boolean isClosed()
	{
		return closed.get();
	}

	@Override
	public void close()
	{
		if (closed.getAndSet(true))
		{
			// already closed
			return;
		}

		QuerySelectionToDeleteHelper.scheduleDeleteSelectionNoFail(querySelectionUUID, trxName);
	}

	@Override
	public boolean hasNext()
	{
		if (isClosed())
		{
			return false;
		}

		// NOTE: we do this checking only to have last page optimizations
		// e.g. consider having 100 records, page size=10, so last page will be fully loaded.
		// If we are not doing this checking, the buffered iterator will try to load the next page (which is empty),
		// in order to find out that there is nothing to be loaded.
		if (rowsFetched >= rowsCount)
		{
			close();
			return false;
		}

		try
		{
			while (peekingBufferedIterator.hasNext())
			{
				// Check the rowsFetched again, because in this while we are also navigating forward and we skip invalid values
				if (rowsFetched >= rowsCount)
				{
					close();
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

			close();
			return false;
		}
		catch (final RuntimeException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@Override
	public ET next()
	{
		if (isClosed())
		{
			throw new AdempiereException("Iterator was already closed: " + this);
		}

		try
		{
			final ET value = peekingBufferedIterator.next();
			rowsFetched++;
			return value;
		}
		catch (final RuntimeException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	@Override
	public void remove()
	{
		peekingBufferedIterator.remove();
	}

	/**
	 * Sets buffer/page size, i.e. the number of rows to be loaded by this iterator at a time.
	 *
	 * @see IQuery#OPTION_IteratorBufferSize
	 */
	public void setBufferSize(final int bufferSize)
	{
		bufferedIterator.setBufferSize(bufferSize);
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
