package org.adempiere.sql.impl;

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

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

import javax.sql.RowSet;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.CCachedRowSet;
import org.compiere.util.CStatement;
import org.compiere.util.CStatementVO;
import org.compiere.util.DB;
import org.compiere.util.Trx;

/* package */abstract class AbstractCStatementProxy<ST extends Statement> implements CStatement
{
	private Connection m_conn = null;
	private boolean closed = false;

	/** Used if local */
	private transient ST p_stmt = null;
	/** Value Object, never null */
	private final CStatementVO p_vo;

	public AbstractCStatementProxy(final CStatementVO vo)
	{
		super();

		if (vo == null)
		{
			throw new DBException("CStatementVO shall not be null");
		}
		this.p_vo = vo;

		try
		{
			Connection conn = null;
			final Trx trx = getTrx(p_vo);
			if (trx != null)
			{
				conn = trx.getConnection();
			}
			else
			{
				if (vo.getResultSetConcurrency() == ResultSet.CONCUR_UPDATABLE)
					m_conn = DB.getConnectionRW();
				else
					m_conn = DB.getConnectionRO();
				conn = m_conn;
			}
			if (conn == null)
				throw new DBNoConnectionException();
			p_stmt = createStatement(conn, vo);
		}
		catch (SQLException e)
		{
			// log.error("CStatement", e);
			final String sql = vo == null ? null : vo.getSql();
			throw new DBException(e, sql);
		}
	}

	protected final CStatementVO getVO()
	{
		return p_vo;
	}

	protected abstract ST createStatement(final Connection conn, final CStatementVO vo) throws SQLException;

	protected final ST getStatementImpl()
	{
		return p_stmt;
	}

	@Override
	public final ResultSet executeQuery(final String sql) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().executeQuery(sqlConverted);
	}

	@Override
	public final <T> T unwrap(final Class<T> iface) throws SQLException
	{
		return getStatementImpl().unwrap(iface);
	}

	@Override
	public final int executeUpdate(final String sql) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().executeUpdate(sqlConverted);
	}

	@Override
	public final boolean isWrapperFor(final Class<?> iface) throws SQLException
	{
		return getStatementImpl().isWrapperFor(iface);
	}

	@Override
	public final void close() throws SQLException
	{
		if (closed)
		{
			return;
		}

		try
		{
			if (p_stmt != null)
			{
				p_stmt.close();
			}
		}
		finally
		{
			if (m_conn != null)
			{
				try
				{
					m_conn.close();
				}
				catch (Exception e)
				{
				}
			}
			m_conn = null;
			closed = true;
		}
	}

	@Override
	public final int getMaxFieldSize() throws SQLException
	{
		return getStatementImpl().getMaxFieldSize();
	}

	@Override
	public final void setMaxFieldSize(final int max) throws SQLException
	{
		getStatementImpl().setMaxFieldSize(max);
	}

	@Override
	public final int getMaxRows() throws SQLException
	{
		return getStatementImpl().getMaxRows();
	}

	@Override
	public final void setMaxRows(final int max) throws SQLException
	{
		getStatementImpl().setMaxRows(max);
	}

	@Override
	public final void setEscapeProcessing(final boolean enable) throws SQLException
	{
		getStatementImpl().setEscapeProcessing(enable);
	}

	@Override
	public final int getQueryTimeout() throws SQLException
	{
		return getStatementImpl().getQueryTimeout();
	}

	@Override
	public final void setQueryTimeout(final int seconds) throws SQLException
	{
		getStatementImpl().setQueryTimeout(seconds);
	}

	@Override
	public final void cancel() throws SQLException
	{
		getStatementImpl().cancel();
	}

	@Override
	public final SQLWarning getWarnings() throws SQLException
	{
		return getStatementImpl().getWarnings();
	}

	@Override
	public final void clearWarnings() throws SQLException
	{
		getStatementImpl().clearWarnings();
	}

	@Override
	public final void setCursorName(final String name) throws SQLException
	{
		getStatementImpl().setCursorName(name);
	}

	@Override
	public final boolean execute(final String sql) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().execute(sqlConverted);
	}

	@Override
	public final ResultSet getResultSet() throws SQLException
	{
		return getStatementImpl().getResultSet();
	}

	@Override
	public final int getUpdateCount() throws SQLException
	{
		return getStatementImpl().getUpdateCount();
	}

	@Override
	public final boolean getMoreResults() throws SQLException
	{
		return getStatementImpl().getMoreResults();
	}

	@Override
	public final void setFetchDirection(final int direction) throws SQLException
	{
		getStatementImpl().setFetchDirection(direction);
	}

	@Override
	public final int getFetchDirection() throws SQLException
	{
		return getStatementImpl().getFetchDirection();
	}

	@Override
	public final void setFetchSize(final int rows) throws SQLException
	{
		getStatementImpl().setFetchSize(rows);
	}

	@Override
	public final int getFetchSize() throws SQLException
	{
		return getStatementImpl().getFetchSize();
	}

	@Override
	public final int getResultSetConcurrency() throws SQLException
	{
		return getStatementImpl().getResultSetConcurrency();
	}

	@Override
	public final int getResultSetType() throws SQLException
	{
		return getStatementImpl().getResultSetType();
	}

	@Override
	public final void addBatch(final String sql) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		getStatementImpl().addBatch(sqlConverted);
	}

	@Override
	public final void clearBatch() throws SQLException
	{
		getStatementImpl().clearBatch();
	}

	@Override
	public final int[] executeBatch() throws SQLException
	{
		return getStatementImpl().executeBatch();
	}

	@Override
	public final Connection getConnection() throws SQLException
	{
		return getStatementImpl().getConnection();
	}

	@Override
	public final boolean getMoreResults(final int current) throws SQLException
	{
		return getStatementImpl().getMoreResults(current);
	}

	@Override
	public final ResultSet getGeneratedKeys() throws SQLException
	{
		return getStatementImpl().getGeneratedKeys();
	}

	@Override
	public final int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().executeUpdate(sqlConverted, autoGeneratedKeys);
	}

	@Override
	public final int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().executeUpdate(sqlConverted, columnIndexes);
	}

	@Override
	public final int executeUpdate(final String sql, final String[] columnNames) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().executeUpdate(sqlConverted, columnNames);
	}

	@Override
	public final boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().execute(sqlConverted, autoGeneratedKeys);
	}

	@Override
	public final boolean execute(final String sql, final int[] columnIndexes) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().execute(sqlConverted, columnIndexes);
	}

	@Override
	public final boolean execute(final String sql, final String[] columnNames) throws SQLException
	{
		final String sqlConverted = convertSqlAndSet(sql);
		return getStatementImpl().execute(sqlConverted, columnNames);
	}

	@Override
	public final int getResultSetHoldability() throws SQLException
	{
		return getStatementImpl().getResultSetHoldability();
	}

	@Override
	public final boolean isClosed() throws SQLException
	{
		return closed;
	}

	@Override
	public final void setPoolable(final boolean poolable) throws SQLException
	{
		getStatementImpl().setPoolable(poolable);
	}

	@Override
	public final boolean isPoolable() throws SQLException
	{
		return getStatementImpl().isPoolable();
	}

	@Override
	public final void closeOnCompletion() throws SQLException
	{
		getStatementImpl().closeOnCompletion();
	}

	@Override
	public final boolean isCloseOnCompletion() throws SQLException
	{
		return getStatementImpl().isCloseOnCompletion();
	}

	@Override
	public final void finalize() throws Throwable
	{
		if (p_stmt != null && !closed)
		{
			close();
		}

		super.finalize();
	}

	@Override
	public final String getSql()
	{
		final CStatementVO vo = this.p_vo;
		if (vo != null)
		{
			return vo.getSql();
		}
		return null;
	}

	protected final String convertSqlAndSet(final String sql)
	{
		final String sqlConverted = DB.getDatabase().convertStatement(sql);
		p_vo.setSql(sqlConverted);

		return sqlConverted;
	}

	@Override
	public RowSet getRowSet()
	{
		Check.assumeNotNull(p_vo, "VO is not null");

		final String sql = p_vo.getSql();

		RowSet rowSet = null;
		ResultSet rs = null;
		try
		{
			rs = p_stmt.executeQuery(sql);
			rowSet = CCachedRowSet.getRowSet(rs);
		}
		catch (Exception ex)
		{
			throw new DBException(ex, sql);
		}
		finally
		{
			DB.close(rs);
		}
		return rowSet;
	}

	@Override
	public final void commit() throws SQLException
	{
		if (m_conn != null && !m_conn.getAutoCommit())
		{
			m_conn.commit();
		}
	}

	private static final Trx getTrx(final CStatementVO vo)
	{
		if (vo == null)
		{
			return null;
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final String trxName = vo.getTrxName();
		if (trxManager.isNull(trxName))
		{
			return (Trx)ITrx.TRX_None;
		}
		else
		{
			final ITrx trx = trxManager.get(trxName, false); // createNew=false

			// NOTE: we assume trx if of type Trx because we need to invoke getConnection()
			return (Trx)trx;
		}
	}

	@Override
	public String toString()
	{
		return "AbstractCStatementProxy [m_conn=" + m_conn + ", closed=" + closed + ", p_vo=" + p_vo + "]";
	}
}
