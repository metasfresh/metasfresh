/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.impl.AbstractTrx;
import org.adempiere.ad.trx.api.impl.JdbcTrxSavepoint;
import org.adempiere.exceptions.DBException;
import org.slf4j.Logger;
import org.slf4j.MDC;

import de.metas.logging.LogManager;
import de.metas.util.Services;

import javax.annotation.Nullable;

/**
 * Transaction Management. - Create new Transaction by Trx.get(name); - ..transactions.. - commit(); ---- start(); ---- commit(); - close();
 *
 * @author Jorg Janke
 * @author Low Heng Sin - added rollback(boolean) and commit(boolean) [20070105] - remove unnecessary use of savepoint - use UUID for safer transaction name generation
 * @author Teo Sarca, http://www.arhipac.ro
 *         <li>FR [ 2080217 ] Implement TrxRunnable
 *         <li>BF [ 2876927 ] Oracle JDBC driver problem
 *         https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2876927&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com
 *         <li>BF [ 2849122 ] PO.AfterSave is not rollback on error - add releaseSavepoint method
 *         https://sourceforge.net/tracker/index.php?func=detail&aid=2849122&group_id=176962&atid=879332#
 */
public class Trx extends AbstractTrx
{
	@Deprecated
	public static Trx get(final String trxName, final boolean createNew)
	{
		final ITrx trx = Services.get(ITrxManager.class).get(trxName, createNew);
		return (Trx)trx;
	}

	@Deprecated
	public static String createTrxName(final String prefix)
	{
		return Services.get(ITrxManager.class).createTrxName(prefix);
	}

	private static final String MDC_TRX_NAME = "TrxName";
	private static final Logger logger = LogManager.getLogger(Trx.class);
	@Nullable private Connection m_connection = null;

	public Trx(final ITrxManager trxManager, final String trxName, final boolean autocommit)
	{
		this(trxManager, trxName, (Connection)null, autocommit);

		// String threadName = Thread.currentThread().getName(); // for debugging
	}

	private Trx(final ITrxManager trxManager, final String trxName, final Connection con, final boolean autocommit)
	{
		super(trxManager, trxName, autocommit);

		MDC.put(MDC_TRX_NAME, trxName == null ? ITrx.TRXNAME_NoneNotNull : trxName); // TODO: maybe log if there already was a trxName??
		setConnection(con);
	}

	public Connection getConnection()
	{
		logger.trace("Active={}, Connection={}", isActive(), m_connection);

		// Handle the case when the connection was already closed
		// Example: one case when we can get this is when we start a process with a transaction
		// and that process is commiting the transaction somewhere
		if (m_connection != null)
		{
			boolean isClosed = false;
			try
			{
				isClosed = m_connection.isClosed();
			}
			catch (final SQLException e)
			{
				logger.warn("Error checking if the connection is closed. Assume closed.", e);
				isClosed = true;
			}
			if (isClosed)
			{
				logger.info("Connection is closed. Trying to create another connection.");
				m_connection = null;
			}
		}

		if (m_connection == null) 	// get new Connection
		{
			setConnection(DB.createConnection(isAutoCommit(), Connection.TRANSACTION_READ_COMMITTED));
		}
		if (!isActive())
		{
			start();
		}

		try
		{
			m_connection.setAutoCommit(isAutoCommit());
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
		}

		return m_connection;
	}	// getConnection

	/**
	 * Set Connection
	 *
	 * @param conn connection
	 */
	private void setConnection(final Connection conn)
	{
		if (conn == null)
		{
			return;
		}

		m_connection = conn;
		logger.trace("Connection={}", conn);

		//
		// Configure the connection
		try
		{
			m_connection.setAutoCommit(false);

			//
			// Get Connection's backend PID (if configured)
			if (getTrxManager().isDebugConnectionBackendId())
			{
				final boolean throwDBException = true; // we expect everything to be in order to get a PID
				final String debugConnectionBackendId = DB.getDatabase().getConnectionBackendId(m_connection, throwDBException);
				setDebugConnectionBackendId(debugConnectionBackendId);
			}
			// NOTE: works with c3p0 from version 0.9.5 (released on 02.01.2015)
			m_connection.setClientInfo("ApplicationName", "adempiere/" + getTrxName()); // task 08353
		}
		catch (final Exception e)
		{
			logger.warn("Failed setting the connection. Ignored.", e);
		}
	}	// setConnection

	@Override
	protected boolean isJustStarted()
	{
		if (!isActive())
		{
			return false;
		}

		final boolean justStarted = m_connection == null;
		return justStarted;
	}

	@Override
	protected boolean rollbackNative(final boolean throwException) throws SQLException
	{
		final String trxName = getTrxName();

		//
		// Get current connection
		// NOTE: we are not calling getConnection() because we don't want to acquire a new connection in case it was not used already.
		final Connection connection = m_connection;

		if (connection == null || connection.getAutoCommit())
		{
			logger.debug("rollbackNative: doing nothing because we have a null or autocommit connection; this={}, connection={}", this, m_connection);
			// => consider this a success because if there was no open transaction then there is nothing to rollback
			return true;
		}

		// Case: we really have something to rollback (because connection was acquired and used)
		try
		{
			connection.rollback();
			logger.debug("rollbackNative: OK - {}", trxName);
			return true;
		}
		catch (final SQLException e)
		{
			if (throwException)
			{
				throw e;
			}
			else
			{
				logger.warn("rollbackNative: FAILED but don't throw exception for trxName={}", trxName, e);
				return false;
			}
		}
	}

	@Override
	protected boolean rollbackNative(final ITrxSavepoint savepoint) throws SQLException
	{
		if (m_connection == null || m_connection.getAutoCommit())
		{
			logger.debug("rollbackNative: doing nothing because we have a null or autocomit connection; this={}, connection={}", this, m_connection);
			return false;
		}

		final String trxName = getTrxName();
		final Savepoint jdbcSavepoint = (Savepoint)savepoint.getNativeSavepoint();

		// local
		try
		{
			if (m_connection != null)
			{
				m_connection.rollback(jdbcSavepoint);
				logger.debug("rollbackNative: done for trxName={}", trxName);
				return true;
			}
		}
		catch (final SQLException e)
		{
			// Do nothing. The Savepoint might have been discarded because of an intermediate commit or rollback
			// FIXME: track in AbstractTrx which savepoints where implicitly discarded in this way and don't call rollbackNative in such a case.
			// log.error(trxName, e);
			// throw e;
		}
		return false;
	}	// rollback

	@Override
	protected boolean commitNative(final boolean throwException) throws SQLException
	{
		if (m_connection == null || m_connection.getAutoCommit())
		{
			logger.debug("commitNative: doing nothing because we have an autocomit connection; this={}, connection={}", this, m_connection);
			return true;
		}

		final String trxName = getTrxName();

		//
		// Get current connection
		// NOTE: we are not calling getConnection() because we don't want to acquire a new connection in case it was not used already.
		final Connection connection = this.m_connection;

		//
		// Case: we really have something to commit (because connection was acquired and used)
		if (connection != null)
		{
			try
			{
				connection.commit();
				logger.debug("commitNative: OK - {}", trxName);
				// m_active = false;
				return true;
			}
			catch (final SQLException e)
			{
				if (throwException)
				{
					// m_active = false;
					throw e;
				}
				else
				{
					logger.warn("commitNative: FAILED but don't throw exception for trxName={}", trxName, e);
					return false;
				}
			}
		}
		//
		// Case: nothing was done on this transaction (because connection is null, so it was not acquired)
		else
		{
			// => consider this a success because even if nothing was done on this transaction, nothing failed neither
			return true;
		}
	}	// commit

	@Override
	protected synchronized boolean closeNative()
	{
		if (m_connection == null)
		{
			logger.debug("closeNative - m_connection is already null; just return true");
			MDC.remove(MDC_TRX_NAME); // TODO: log if there was no TrxName
			return true; // nothing to do
		}

		// Close Connection
		try
		{
			// NOTE: let the org.compiere.db.DB_PostgreSQL_ConnectionCustomizer to update the ApplicationName because
			// it will be performed in a separate thread so here we don't have to wait.
			// m_connection.setClientInfo("ApplicationName", "adempiere/CLOSED"); // task 08353

			// Note: c3p0 makes sure that uncommitted changes are rolled by (=>default) or committed
			// See https://www.mchange.com/projects/c3p0/index.html#autoCommitOnClose
			m_connection.close();
			logger.debug("closeNative - closed m_connection={}", m_connection);
		}
		catch (final SQLException e)
		{
			logger.warn("closeNative - failed closing connection={}, trxName={} but IGNORED.", m_connection, getTrxName());
		}
		m_connection = null;
		MDC.remove(MDC_TRX_NAME); // TODO: log if there was no TrxName
		return true;
	}	// close

	@Override
	protected ITrxSavepoint createTrxSavepointNative(final String name) throws Exception
	{
		if (m_connection == null)
		{
			getConnection();
		}

		if (m_connection.getAutoCommit())
		{
			logger.debug("createTrxSavepointNative: returning null because we have an autocomit connection; this={}, connection={}", this, m_connection);
			return null;
		}

		if (m_connection != null)
		{
			final Savepoint jdbcSavepoint;
			if (name != null)
			{
				jdbcSavepoint = m_connection.setSavepoint(name);
			}
			else
			{
				jdbcSavepoint = m_connection.setSavepoint();
			}

			final JdbcTrxSavepoint savepoint = new JdbcTrxSavepoint(this, jdbcSavepoint);
			return savepoint;
		}
		else
		{
			return null;
		}
	}

	@Override
	protected boolean releaseSavepointNative(final ITrxSavepoint savepoint) throws Exception
	{
		final Savepoint jdbcSavepoint = (Savepoint)savepoint.getNativeSavepoint();

		if (m_connection == null)
		{
			logger.warn("Cannot release savepoint {} because there is no active connection. Ignoring it.", savepoint);
			return false;
		}
		if (m_connection.isClosed())
		{
			logger.warn("Cannot release savepoint {} because the connection is closed. Ignoring it.", savepoint);
			return false;
		}

		m_connection.releaseSavepoint(jdbcSavepoint);
		return true;
	}
}	// Trx
