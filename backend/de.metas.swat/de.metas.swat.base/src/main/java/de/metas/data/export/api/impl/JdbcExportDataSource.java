package de.metas.data.export.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import de.metas.common.util.pair.ImmutablePair;
import org.compiere.util.DB;
import org.compiere.util.Trx;
import org.slf4j.Logger;

import de.metas.data.export.api.IExportDataSource;
import de.metas.logging.LogManager;
import de.metas.util.Check;

/**
 * JDBC implementation of {@link IExportDataSource}
 *
 * @author tsa
 *
 */
public class JdbcExportDataSource implements IExportDataSource
{
	private static final Logger logger = LogManager.getLogger(JdbcExportDataSource.class);

	private final List<String> fields;
	private final List<String> sqlFields;
	private final String sqlSelect;
	private final String sqlCount;
	/**
	 * SQL Where Clause to be used, not actually needed, it's just for reference
	 */
	private final String sqlWhereClause;
	private final List<Object> sqlParams;

	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private boolean closed = false;

	private List<Object> currentRow = null;

	public JdbcExportDataSource(final List<String> fields, final List<String> sqlFields,
			final String sqlSelect,
			final String sqlCount,
			final String sqlWhereClause,
			final List<Object> sqlParams/* not ImmutableList because list elements might be null */)
	{
		this.fields = Collections.unmodifiableList(new ArrayList<String>(fields));
		this.sqlFields = Collections.unmodifiableList(new ArrayList<String>(sqlFields));
		this.sqlSelect = sqlSelect;
		this.sqlCount = sqlCount;
		this.sqlWhereClause = sqlWhereClause;
		this.sqlParams = sqlParams == null ? null : Collections.unmodifiableList(new ArrayList<>(sqlParams));
	}

	/**
	 * Gets the ResultSet to be used.
	 *
	 * NOTE: the result set is cached in class properties, so you can invoke this method as many times as you want, only the first time the ResultSet will actually fetched from database.
	 *
	 * @return result set to be used. Never returns NULL.
	 * @throws DBException on any error
	 * @throws AdempiereException if the result set was opened and then closed (i.e. we already iterated this data source).
	 */
	private ResultSet getResultSet()
	{
		Check.assume(!closed, "stream not closed");

		if (rs != null)
		{
			return rs;
		}

		logger.info("SQL: \r\n" + sqlSelect);
		logger.info("SQL params: " + sqlParams);

		DB.saveConstraints();

		boolean ok = false;
		try
		{
			// disabling trx timeout, as this might be a long-running process
			DB.getConstraints().setTrxTimeoutSecs(-1, false);

			final ImmutablePair<Connection, PreparedStatement> connAndStmt = DB.prepareConnectionAndStatementForDataExport(sqlSelect, sqlParams);
			conn = connAndStmt.getLeft();
			pstmt = connAndStmt.getRight();

			rs = pstmt.executeQuery();
			ok = true;
		}
		catch (SQLException e)
		{
			throw new DBException(e, sqlSelect, sqlParams);
		}
		finally
		{
			DB.restoreConstraints();
			if (!ok)
			{
				close();
			}
		}

		return rs;
	}

	/**
	 * Retrieve next row from ResultSet.
	 *
	 * If there is no row available (ResultSet reached the end) then null is returned
	 *
	 * @return row as list of Objects or null
	 */
	private List<Object> retrieveNextOrNull()
	{
		final ResultSet rs = getResultSet(); // NOPMD by tsa on 3/17/13 1:11 PM
		List<Object> row = null;
		boolean ok = false;
		try
		{
			if (!rs.next())
			{
				// close(); // no need to close it if there is no more data to read because it will be closed in finally block
				return null;
			}

			row = readLine(rs, sqlFields);
			ok = true;
		}
		catch (SQLException e)
		{
			close();
			throw new DBException(e);
		}
		finally
		{
			if (!ok)
			{
				close();
			}
		}

		return row;
	}

	@Override
	public void prepare()
	{
		// Just opening the ResultSet if is not already opened.
		// Following method will make sure that the ResultSet was not already opened and closed.
		getResultSet();
	}

	@Override
	public List<String> getFieldNames()
	{
		return fields;
	}

	@Override
	public void close()
	{
		DB.close(rs, pstmt);
		rs = null;
		pstmt = null;

		DB.close(conn);
		conn = null;

		currentRow = null;
		closed = true;
	}

	@Override
	public boolean hasNext()
	{
		if (currentRow == null)
		{
			currentRow = retrieveNextOrNull();
		}

		return currentRow != null;
	}

	@Override
	public List<Object> next()
	{
		if (currentRow != null)
		{
			final List<Object> rowToReturn = currentRow;
			currentRow = null;
			return rowToReturn;
		}

		currentRow = retrieveNextOrNull();
		if (currentRow == null)
		{
			throw new NoSuchElementException();
		}

		return currentRow;
	}

	@Override
	public void remove()
	{
		throw new UnsupportedOperationException();
	}

	private List<Object> readLine(ResultSet rs, List<String> sqlFields) throws SQLException
	{
		final List<Object> values = new ArrayList<Object>();
		for (final String columnName : sqlFields)
		{
			final Object value = rs.getObject(columnName);
			values.add(value);
		}

		return values;
	}

	private Integer rowsCount = null;

	@Override
	public int size()
	{
		if (Check.isEmpty(sqlCount, true))
		{
			throw new IllegalStateException("Counting is not supported");
		}

		if (rowsCount == null)
		{
			logger.info("SQL: {}", sqlCount);
			logger.info("SQL Params: {}", sqlParams);
			rowsCount = DB.getSQLValueEx(Trx.TRXNAME_None, sqlCount, sqlParams);

			logger.info("Rows Count: {}" + rowsCount);
		}

		return rowsCount;
	}

	public String getSqlSelect()
	{
		return sqlSelect;
	}

	public List<Object> getSqlParams()
	{
		if (sqlParams == null)
		{
			return Collections.emptyList();
		}
		return sqlParams;
	}

	public String getSqlWhereClause()
	{
		return sqlWhereClause;
	}
}
