package org.adempiere.sql.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.sql.RowSet;

import org.adempiere.ad.dao.IQueryStatisticsCollector;
import org.compiere.util.CStatement;

import com.google.common.base.MoreObjects;
import com.google.common.base.Stopwatch;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General public final License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General public final License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * {@link CStatement} wrapper which traces SQL queries
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <StatementType>
 */
class TracingStatement<StatementType extends AbstractCStatementProxy<? extends Statement>> implements CStatement
{
	protected final StatementType delegate;

	/**
	 * SQL Queries collector
	 */
	/* package */static IQueryStatisticsCollector SQL_QUERIES_COLLECTOR = null;

	public TracingStatement(final StatementType delegate)
	{
		super();
		this.delegate = delegate;
	}

	@Override
	public final String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(delegate)
				.toString();
	}

	@FunctionalInterface
	protected static interface SqlCall<T>
	{
		T call() throws SQLException;
	}

	protected final <T> T trace(final String sql, final SqlCall<T> sqlCall) throws SQLException
	{
		final IQueryStatisticsCollector collector = SQL_QUERIES_COLLECTOR;
		if (collector == null)
		{
			return sqlCall.call();
		}

		final Stopwatch duration = Stopwatch.createStarted();
		final T retValue = sqlCall.call();
		collector.collect(sql, duration.stop());

		return retValue;
	}

	protected final <T> T trace(final SqlCall<T> sqlCall) throws SQLException
	{
		final IQueryStatisticsCollector collector = SQL_QUERIES_COLLECTOR;
		if (collector == null)
		{
			return sqlCall.call();
		}

		final Stopwatch duration = Stopwatch.createStarted();
		final T retValue = sqlCall.call();
		collector.collect(delegate.getVO(), duration.stop());

		return retValue;
	}

	@Override
	public final String getSql()
	{
		return delegate.getSql();
	}

	@Override
	public final RowSet getRowSet()
	{
		return delegate.getRowSet();
	}

	@Override
	public final void commit() throws SQLException
	{
		delegate.commit();
	}

	@Override
	public void finalize() throws Throwable
	{
		delegate.finalize();
	}

	@Override
	public final <T> T unwrap(final Class<T> iface) throws SQLException
	{
		return delegate.unwrap(iface);
	}

	@Override
	public final ResultSet executeQuery(final String sql) throws SQLException
	{
		return trace(sql, () -> delegate.executeQuery(sql));
	}

	@Override
	public final boolean isWrapperFor(final Class<?> iface) throws SQLException
	{
		return delegate.isWrapperFor(iface);
	}

	@Override
	public final int executeUpdate(final String sql) throws SQLException
	{
		return trace(sql, () -> delegate.executeUpdate(sql));
	}

	@Override
	public final void close() throws SQLException
	{
		delegate.close();
	}

	@Override
	public final int getMaxFieldSize() throws SQLException
	{
		return delegate.getMaxFieldSize();
	}

	@Override
	public final void setMaxFieldSize(final int max) throws SQLException
	{
		delegate.setMaxFieldSize(max);
	}

	@Override
	public final int getMaxRows() throws SQLException
	{
		return delegate.getMaxRows();
	}

	@Override
	public final void setMaxRows(final int max) throws SQLException
	{
		delegate.setMaxRows(max);
	}

	@Override
	public final void setEscapeProcessing(final boolean enable) throws SQLException
	{
		delegate.setEscapeProcessing(enable);
	}

	@Override
	public final int getQueryTimeout() throws SQLException
	{
		return delegate.getQueryTimeout();
	}

	@Override
	public final void setQueryTimeout(final int seconds) throws SQLException
	{
		delegate.setQueryTimeout(seconds);
	}

	@Override
	public final void cancel() throws SQLException
	{
		delegate.cancel();
	}

	@Override
	public final SQLWarning getWarnings() throws SQLException
	{
		return delegate.getWarnings();
	}

	@Override
	public final void clearWarnings() throws SQLException
	{
		delegate.clearWarnings();
	}

	@Override
	public final void setCursorName(final String name) throws SQLException
	{
		delegate.setCursorName(name);
	}

	@Override
	public final boolean execute(final String sql) throws SQLException
	{
		return trace(sql, () -> delegate.execute(sql));
	}

	@Override
	public final ResultSet getResultSet() throws SQLException
	{
		return delegate.getResultSet();
	}

	@Override
	public final int getUpdateCount() throws SQLException
	{
		return delegate.getUpdateCount();
	}

	@Override
	public final boolean getMoreResults() throws SQLException
	{
		return delegate.getMoreResults();
	}

	@Override
	public final void setFetchDirection(final int direction) throws SQLException
	{
		delegate.setFetchDirection(direction);
	}

	@Override
	public final int getFetchDirection() throws SQLException
	{
		return delegate.getFetchDirection();
	}

	@Override
	public final void setFetchSize(final int rows) throws SQLException
	{
		delegate.setFetchSize(rows);
	}

	@Override
	public final int getFetchSize() throws SQLException
	{
		return delegate.getFetchSize();
	}

	@Override
	public final int getResultSetConcurrency() throws SQLException
	{
		return delegate.getResultSetConcurrency();
	}

	@Override
	public final int getResultSetType() throws SQLException
	{
		return delegate.getResultSetType();
	}

	@Override
	public final void addBatch(final String sql) throws SQLException
	{
		delegate.addBatch(sql);
	}

	@Override
	public final void clearBatch() throws SQLException
	{
		delegate.clearBatch();
	}

	@Override
	public final int[] executeBatch() throws SQLException
	{
		return trace(() -> delegate.executeBatch());
	}

	@Override
	public final Connection getConnection() throws SQLException
	{
		return delegate.getConnection();
	}

	@Override
	public final boolean getMoreResults(final int current) throws SQLException
	{
		return delegate.getMoreResults(current);
	}

	@Override
	public final ResultSet getGeneratedKeys() throws SQLException
	{
		return delegate.getGeneratedKeys();
	}

	@Override
	public final int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException
	{
		return trace(sql, () -> delegate.executeUpdate(sql, autoGeneratedKeys));
	}

	@Override
	public final int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException
	{
		return trace(sql, () -> delegate.executeUpdate(sql, columnIndexes));
	}

	@Override
	public final int executeUpdate(final String sql, final String[] columnNames) throws SQLException
	{
		return trace(sql, () -> delegate.executeUpdate(sql, columnNames));
	}

	@Override
	public final boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException
	{
		return trace(sql, () -> delegate.execute(sql, autoGeneratedKeys));
	}

	@Override
	public final boolean execute(final String sql, final int[] columnIndexes) throws SQLException
	{
		return trace(sql, () -> delegate.execute(sql, columnIndexes));
	}

	@Override
	public final boolean execute(final String sql, final String[] columnNames) throws SQLException
	{
		return trace(sql, () -> delegate.execute(sql, columnNames));
	}

	@Override
	public final int getResultSetHoldability() throws SQLException
	{
		return delegate.getResultSetHoldability();
	}

	@Override
	public final boolean isClosed() throws SQLException
	{
		return delegate.isClosed();
	}

	@Override
	public final void setPoolable(final boolean poolable) throws SQLException
	{
		delegate.setPoolable(poolable);
	}

	@Override
	public final boolean isPoolable() throws SQLException
	{
		return delegate.isPoolable();
	}

	@Override
	public final void closeOnCompletion() throws SQLException
	{
		delegate.closeOnCompletion();
	}

	@Override
	public final boolean isCloseOnCompletion() throws SQLException
	{
		return delegate.isCloseOnCompletion();
	}
}
