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

import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.compiere.util.CStatement;
import org.compiere.util.CStatementVO;
import org.compiere.util.DB;
import org.compiere.util.Trx;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;

/* package */abstract class AbstractCStatementProxy<ST extends Statement> implements CStatement
{
	@NonNull
	private final CStatementVO vo;

	private boolean closed = false;
	@Nullable
	private Connection ownedConnection;

	private final transient ST stmt;

	public AbstractCStatementProxy(@NonNull final CStatementVO vo)
	{
		this.vo = vo;

		try
		{
			final Connection connectionToUse;
			final Trx trx = getTrx(vo);
			if (trx != null)
			{
				connectionToUse = trx.getConnection();
			}
			else
			{
				connectionToUse = this.ownedConnection = vo.getResultSetConcurrency() == ResultSet.CONCUR_UPDATABLE
						? DB.getConnectionRW()
						: DB.getConnectionRO();
			}

			// shall not happen
			if (connectionToUse == null)
			{
				throw new DBNoConnectionException();
			}

			stmt = createStatement(connectionToUse, vo);
		}
		catch (final SQLException e)
		{
			throw new DBException(e, vo.getSql());
		}
	}

	protected final CStatementVO getVO()
	{
		return vo;
	}

	protected abstract ST createStatement(final Connection conn, final CStatementVO vo) throws SQLException;

	protected final ST getStatementImpl()
	{
		return stmt;
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
			if (stmt != null)
			{
				stmt.close();
			}
		}
		finally
		{
			// Close owned connection if any
			if (this.ownedConnection != null)
			{
				try
				{
					this.ownedConnection.close();
				}
				catch (final Exception ignored)
				{
				}
			}
			this.ownedConnection = null;

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
	public void clearBatch() throws SQLException
	{
		getStatementImpl().clearBatch();
	}

	@Override
	public int[] executeBatch() throws SQLException
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
		if (stmt != null && !closed)
		{
			close();
		}

		super.finalize();
	}

	@Override
	public final String getSql()
	{
		return this.vo.getSql();
	}

	protected final String convertSqlAndSet(final String sql)
	{
		final String sqlConverted = DB.getDatabase().convertStatement(sql);
		vo.setSql(sqlConverted);

		MigrationScriptFileLoggerHolder.logMigrationScript(sql);

		return sqlConverted;
	}

	@Override
	public final void commit() throws SQLException
	{
		if (this.ownedConnection != null && !this.ownedConnection.getAutoCommit())
		{
			this.ownedConnection.commit();
		}
	}

	@Nullable
	private static Trx getTrx(@NonNull final CStatementVO vo)
	{
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
		return "AbstractCStatementProxy [ownedConnection=" + this.ownedConnection + ", closed=" + closed + ", p_vo=" + vo + "]";
	}
}
