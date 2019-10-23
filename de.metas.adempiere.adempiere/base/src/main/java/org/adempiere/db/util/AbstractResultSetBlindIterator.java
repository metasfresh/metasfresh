package org.adempiere.db.util;

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


import java.io.Closeable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.adempiere.exceptions.DBException;
import org.compiere.util.DB;

import de.metas.util.collections.BlindIterator;
import de.metas.util.collections.BlindIteratorWrapper;

/**
 * Abstract class useful for implementing iterators over {@link ResultSet}s.
 * 
 * This class behaves like an {@link BlindIterator}. If you want full {@link Iterator} functionality then you can wrap this by using {@link BlindIteratorWrapper}.
 * 
 * @author tsa
 * 
 * @param <E>
 */
public abstract class AbstractResultSetBlindIterator<E> implements BlindIterator<E>, Closeable
{
	private ResultSet rs = null;
	private boolean closed = false;

	public AbstractResultSetBlindIterator()
	{
		super();
	}

	/**
	 * Create and returns the {@link ResultSet}.
	 * 
	 * This method will be called internally, one time, right before trying to iterate first element.
	 * 
	 * @return
	 * @throws SQLException
	 */
	protected abstract ResultSet createResultSet() throws SQLException;

	/**
	 * Method responsible for fetching current row from {@link ResultSet} and convert it to target object.
	 * 
	 * NOTE: implementors of this method shall NEVER call rs.next().
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected abstract E fetch(ResultSet rs) throws SQLException;

	@Override
	public final E next()
	{
		if (closed)
		{
			throw new NoSuchElementException("ResultSet already closed");
		}

		boolean keepAliveResultSet = false; // if false, underlying ResultSet will be closed in finally block
		try
		{
			if (rs == null)
			{
				rs = createResultSet();
				if (rs == null)
				{
					throw new IllegalStateException("No ResultSet was created");
				}
			}

			if (!rs.next())
			{
				// we reached the end of result set
				keepAliveResultSet = false; // flag that we need to close the ResultSet
				return null;
			}

			final E item = fetch(rs);
			keepAliveResultSet = true;
			return item;
		}
		catch (SQLException e)
		{
			keepAliveResultSet = false; // make sure we will close the ResultSet
			onSQLException(e);

			// NOTE: we shall not reach this point because onSQLException is assumed to throw the exception
			throw new DBException(e);
		}
		finally
		{
			if (!keepAliveResultSet)
			{
				close();
			}
		}
	}

	/**
	 * Gets the {@link SQLException} and throws the proper exception (maybe with more informations)
	 * 
	 * @param e
	 */
	protected void onSQLException(final SQLException e)
	{
		throw new DBException(e);
	}

	/**
	 * Closes the underlying {@link ResultSet}.
	 */
	@Override
	public void close()
	{
		DB.close(rs);
		rs = null;
		closed = true;
	}
}
