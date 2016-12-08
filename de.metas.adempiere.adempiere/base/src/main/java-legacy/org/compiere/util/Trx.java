/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.util;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxSavepoint;
import org.adempiere.ad.trx.api.impl.AbstractTrx;
import org.adempiere.ad.trx.api.impl.JdbcTrxSavepoint;
import org.adempiere.util.Services;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

//import org.adempiere.util.trxConstraints.api.IOpenTrxBL;

/**
 * Transaction Management. - Create new Transaction by Trx.get(name); - ..transactions.. - commit(); ---- start(); ---- commit(); - close();
 * 
 * @author Jorg Janke
 * @author Low Heng Sin - added rollback(boolean) and commit(boolean) [20070105] - remove unnecessary use of savepoint - use UUID for safer transaction name generation
 * @author Teo Sarca, http://www.arhipac.ro <li>FR [ 2080217 ] Implement TrxRunnable <li>BF [ 2876927 ] Oracle JDBC driver problem
 *         https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2876927&group_id=176962
 * @author Teo Sarca, teo.sarca@gmail.com <li>BF [ 2849122 ] PO.AfterSave is not rollback on error - add releaseSavepoint method
 *         https://sourceforge.net/tracker/index.php?func=detail&aid=2849122&group_id=176962&atid=879332#
 */
public class Trx extends AbstractTrx implements VetoableChangeListener
{
	/**
	 * trxName=null marker
	 * 
	 * @deprecated Please use {@link ITrx#TRXNAME_None} instead
	 */
	// metas
	@Deprecated
	public static final String TRXNAME_None = ITrx.TRXNAME_None;

	/**
	 * Get Transaction
	 * 
	 * @param trxName trx name
	 * @param createNew if false, null is returned if not found
	 * @return Transaction or null
	 */
	public static synchronized Trx get(String trxName, boolean createNew)
	{
		final ITrx trx = Services.get(ITrxManager.class).get(trxName, createNew);
		return (Trx)trx;
	}	// get

	/**
	 * Create unique Transaction Name <b>and instantly create the new trx</b>.
	 * 
	 * @param prefix optional prefix
	 * @return unique name
	 */
	public static String createTrxName(final String prefix)
	{
		return Services.get(ITrxManager.class).createTrxName(prefix);
	}

	/**
	 * Create unique Transaction Name
	 * 
	 * @param prefix optional prefix
	 * 
	 * @return unique name
	 */
	public static String createTrxName(String prefix, final boolean createNew)
	{
		return Services.get(ITrxManager.class).createTrxName(prefix, createNew);
	}	// createTrxName

	/**
	 * Create unique Transaction Name
	 * 
	 * @return unique name
	 */
	public static String createTrxName()
	{
		final String prefix = null;
		return Services.get(ITrxManager.class).createTrxName(prefix);
	}	// createTrxName

	/**************************************************************************
	 * Transaction Constructor
	 * 
	 * @param trxName unique name
	 */
	public Trx(final ITrxManager trxManager, final String trxName)
	{
		this(trxManager, trxName, null);

		// String threadName = Thread.currentThread().getName(); // for debugging
	}	// Trx

	/**
	 * Transaction Constructor
	 * 
	 * @param trxName unique name
	 * @param con optional connection ( ignore for remote transaction )
	 * */
	private Trx(final ITrxManager trxManager, final String trxName, final Connection con)
	{
		super(trxManager, trxName);

		setConnection(con);
	}	// Trx

	/** Static Log */
	// private static final Logger s_log = CLogMgt.getLogger(Trx.class);
	/** Logger */
	private Logger log = LogManager.getLogger(getClass());

	private Connection m_connection = null;

	/**
	 * Get Connection
	 * 
	 * @return connection
	 */
	public Connection getConnection()
	{
		log.trace("Active={}, Connection={}", new Object[] { isActive(), m_connection });

		// metas: tsa: begin: Handle the case when the connection was already closed
		// Example: one case when we can get this is when we start a process with a transaction
		// and that process is commiting the transaction somewhere
		if (m_connection != null)
		{
			boolean isClosed = false;
			try
			{
				isClosed = m_connection.isClosed();
			}
			catch (SQLException e)
			{
				log.error("Error checking if the connection is closed. Assume closed - " + e.getLocalizedMessage(), e);
				isClosed = true;
			}
			if (isClosed)
			{
				log.info("Connection is closed. Trying to create another connection.");
				m_connection = null;
			}
		}
		// metas: tsa: end:
		if (m_connection == null)	// get new Connection
		{
			setConnection(DB.createConnection(false, Connection.TRANSACTION_READ_COMMITTED));
		}
		if (!isActive())
		{
			start();
		}
		return m_connection;
	}	// getConnection

	/**
	 * Set Connection
	 * 
	 * @param conn connection
	 */
	private void setConnection(Connection conn)
	{
		if (conn == null)
		{
			return;
		}

		m_connection = conn;
		log.trace("Connection={}", conn);

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
		catch (Exception e)
		{
			log.error("connection", e);
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
	protected boolean rollbackNative(boolean throwException) throws SQLException
	{
		final String trxName = getTrxName();
		
		//
		// Get current connection
		// NOTE: we are not calling getConnection() because we don't want to acquire a new connection in case it was not used already.
		final Connection connection = m_connection;
		
		//
		// Case: we really have something to rollback (because connection was acquired and used)
		if (connection != null)
		{
			try
			{
				connection.rollback();
				log.debug("rollbackNative: OK - {}", trxName);
				// m_active = false;
				return true;
			}
			catch (SQLException e)
			{
				log.error("rollbackNative: FAILED - {} (throwException={})", trxName, throwException, e);
				if (throwException)
				{
					// m_active = false;
					throw e;
				}
				return false;
			}
		}
		//
		// Case: nothing was done on this transaction (because connection is null, so it was not acquired)
		else
		{
			// => consider this a success because if nothing was done then there is nothing to rollback 
			return true;
		}
	}	// rollback

	@Override
	protected boolean rollbackNative(ITrxSavepoint savepoint) throws SQLException
	// metas: end: 02367
	{
		final String trxName = getTrxName();
		final Savepoint jdbcSavepoint = (Savepoint)savepoint.getNativeSavepoint();

		// local
		try
		{
			if (m_connection != null)
			{
				m_connection.rollback(jdbcSavepoint);
				log.debug("**** {}", trxName);
				return true;
			}
		}
		catch (SQLException e)
		{
			// Do nothing. The Savepoint might have been discarded because of an intermediate commit or rollback
			// FIXME: track in AbstractTrx which savepoints where implicitly discarded in this way and don't call rollbackNative in such a case. 
			// log.error(trxName, e);
			// throw e;
		}
		return false;
	}	// rollback

	@Override
	protected boolean commitNative(boolean throwException) throws SQLException
	{
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
				log.debug("commitNative: OK - {}", trxName);
				// m_active = false;
				return true;
			}
			catch (SQLException e)
			{
				log.error("commitNative: FAILED - {} (throwException={})", trxName, throwException, e);
				if (throwException)
				{
					// m_active = false;
					throw e;
				}
				return false;
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
	// metas: end: 02367
	{
		if (m_connection == null)
		{
			return true; // nothing to do
		}

		// Close Connection
		try
		{
			// NOTE: let the org.compiere.db.DB_PostgreSQL_ConnectionCustomizer to update the ApplicationName because
			// it will be performed in a separate thread so here we don't have to wait.
			// m_connection.setClientInfo("ApplicationName", "adempiere/CLOSED"); // task 08353
			
			m_connection.close();
		}
		catch (SQLException e)
		{
			log.error(getTrxName(), e);
		}
		m_connection = null;
		// m_active = false;
		return true;
	}	// close

	/**
	 * 
	 * @param name
	 * @return Savepoint
	 * @throws SQLException
	 * @Deprecated Please use {@link #createTrxSavepoint(String)}
	 */
	@Deprecated
	public Savepoint setSavepoint(String name) throws SQLException
	{
		final ITrxSavepoint savepoint = createTrxSavepoint(name);
		final Savepoint jdbcSavepoint = (Savepoint)savepoint.getNativeSavepoint();
		return jdbcSavepoint;
	}

	@Override
	protected ITrxSavepoint createTrxSavepointNative(final String name) throws Exception
	{
		if (m_connection == null)
		{
			getConnection();
		}

		if (m_connection != null)
		{
			final Savepoint jdbcSavepoint;
			if (name != null)
				jdbcSavepoint = m_connection.setSavepoint(name);
			else
				jdbcSavepoint = m_connection.setSavepoint();

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
			log.warn("Cannot release savepoint " + savepoint + " because there is no active connection. Ignoring it.");
			return false;
		}
		if (m_connection.isClosed())
		{
			log.warn("Cannot release savepoint " + savepoint + " because the connection is closed. Ignoring it.");
			return false;
		}

		m_connection.releaseSavepoint(jdbcSavepoint);
		return true;
	}

	/**
	 * Vetoable Change. Called from CCache to close connections
	 * 
	 * @param evt event
	 * @throws PropertyVetoException
	 */
	@Override
	public void vetoableChange(PropertyChangeEvent evt)
			throws PropertyVetoException
	{
		log.debug(evt.toString());
	}	// vetoableChange

	/**
	 * @return Trx[]
	 * @deprecated Please use {@link ITrxManager#getActiveTransactions()}
	 */
	@Deprecated
	public static ITrx[] getActiveTransactions()
	{
		return Services.get(ITrxManager.class).getActiveTransactions();
	}

	/**
	 * Delegates to {@link ITrxManager#run(TrxRunnable)}.
	 * 
	 * @deprecated Please use {@link ITrxManager#run(TrxRunnable)}
	 */
	// metas: backward compatibility
	@Deprecated
	public static void run(TrxRunnable r)
	{
		Services.get(ITrxManager.class).run(r);
	}

	/**
	 * Delegates to {@link ITrxManager#run(String, TrxRunnable)}.
	 * 
	 * @deprecated Please use {@link ITrxManager#run(String, TrxRunnable)}
	 */
	// metas: backward compatibility
	@Deprecated
	public static void run(String trxName, TrxRunnable r)
	{
		Services.get(ITrxManager.class).run(trxName, r);
	}

	/**
	 * Delegates to {@link ITrxManager#run(String, boolean, TrxRunnable)}.
	 * 
	 * @deprecated Please use {@link ITrxManager#run(String, boolean, TrxRunnable)}
	 */
	// metas: added manageTrx parameter
	// metas: backward compatibility
	@Deprecated
	public static void run(String trxName, boolean manageTrx, TrxRunnable r)
	{
		Services.get(ITrxManager.class).run(trxName, manageTrx, r);
	}
}	// Trx
