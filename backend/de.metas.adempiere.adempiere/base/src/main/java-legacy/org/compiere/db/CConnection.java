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
package org.compiere.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.util.Check;
import org.springframework.core.env.Environment;

/**
 * Adempiere Connection Descriptor
 *
 * @author Jorg Janke
 * @author Marek Mosiewicz<marek.mosiewicz@jotel.com.pl> - support for RMI over HTTP
 * @version $Id: CConnection.java,v 1.5 2006/07/30 00:55:13 jjanke Exp $
 */
public final class CConnection implements Serializable, Cloneable
{
	private static final long serialVersionUID = -7893119456331485444L;
	/**
	 * Singleton Connection
	 */
	private static volatile CConnection s_cc = null;
	/**
	 * Logger
	 */
	private static final transient Logger log = LogManager.getLogger(CConnection.class);

	static
	{
		LogManager.skipIssueReportingForLoggerName(log);
	}

	/**
	 * Get/Set default client/server Connection
	 *
	 * @return Connection Descriptor
	 */
	public static CConnection get()
	{
		if (s_cc == null)
		{
			synchronized (CConnection.class)
			{
				if (s_cc == null)
				{
					final CConnection cc = createInstanceWithIni();
					s_cc = cc;
				}
			}
		}
		return s_cc;
	}

	public static void createInstance(@NonNull final CConnectionAttributes connectionAttributes)
	{
		synchronized (CConnection.class)
		{
			if (s_cc != null)
			{
				throw new AdempiereException("We already have a connection instance")
						.appendParametersToMessage()
						.setParameter("cconnection",s_cc)
						.setParameter("connectionAttributes",connectionAttributes);
			}
			final CConnection cc = new CConnection();
			cc.setAttributes(connectionAttributes);

			cc.setDataSource();
			if (!cc.isDatabaseOK(true))
			{
				throw new AdempiereException("Unnable to connect to database")
						.appendParametersToMessage()
						.setParameter("connectionAttributes",connectionAttributes);
			}
			s_cc = cc;
		}
	}

	/**
	 * Creates and setups a new CConnection instance.
	 *
	 * @return Connection Descriptor; never returns null.
	 */
	private static CConnection createInstanceWithIni()
	{
		CConnection cc = createFromIniIfOK();
		//
		// Ask user to provide the configuration if not already configured
		while (cc == null || !connectionIsOK(cc))
		{
			cc = createInstance_FromUI(cc);
		}
		Check.assumeNotNull(cc, "cc not null"); // shall never happen

		//
		// Save connection configuration to settings
		Ini.setProperty(Ini.P_CONNECTION, cc.toStringLong());
		Ini.saveProperties();

		//
		// Return the newly created connection
		log.debug("Created: {}", cc);
		return cc;
	}

	@Nullable
	private static CConnection createFromIniIfOK()
	{
		// First, try to create the connection from properties, if any
		final String attributes = Ini.getProperty(Ini.P_CONNECTION);
		final boolean iniFileHasAttributes = !Check.isEmpty(attributes);
		if (!iniFileHasAttributes)
		{
			return null;
		}

		final CConnection cc = new CConnection();
		try
		{
			cc.setAttributes(attributes);
			cc.testDatabaseIfNeeded();

			return cc;
		}
		catch (Exception e)
		{
			log.error("Failed loading the connection from attributes: {}", attributes, e);
			return null;
		}
	}

	private static boolean connectionIsOK(@Nullable final CConnection cc)
	{
		if (cc == null)
		{
			return false;
		}
		cc.testDatabaseIfNeeded();
		return cc.isDatabaseOK();
	}

	/**
	 * Creates a connection configuration by asking the user.
	 *
	 * @param ccTemplate connection template (optional)
	 * @return user created {@link CConnection}; never returns <code>null</code>
	 * @throws DBNoConnectionException if user canceled the settings panel
	 */
	private static CConnection createInstance_FromUI(final CConnection ccTemplate)
	{
		final CConnection ccTemplateToUse;
		if (ccTemplate == null)
		{
			ccTemplateToUse = new CConnection();
			ccTemplateToUse.setDbHost("localhost");
			ccTemplateToUse.setDbUid("metasfresh");
			ccTemplateToUse.setDbPwd("metasfresh");
		}
		else
		{
			ccTemplateToUse = ccTemplate;
		}

		// Ask the user (UI!) to provide the parameters
		final CConnectionDialog ccd = new CConnectionDialog(ccTemplateToUse);
		final CConnection cc = ccd.getConnection();
		Check.assumeNotNull(cc, "cc not null"); // shall not happen

		// If user canceled this dialog, there is NO point to go forward because it will propagate the wrong connection (task 09327)
		if (ccd.isCancel())
		{
			throw new DBNoConnectionException("User canceled the connection dialog");
		}

		return cc;
	}

	private CConnection()
	{
	}    // CConnection

	/**
	 * Connection attributes
	 */
	private transient final CConnectionAttributes attrs = CConnectionAttributes.empty();
	/**
	 * Database
	 */
	private volatile AdempiereDatabase _database = null;

	/**
	 * Is Database Connection OK flag. It can have following values
	 * <ul>
	 * <li>true - database connection is alive
	 * <li>false - database connection is not alive
	 * <li>null - database connection status is unknown
	 * </ul>
	 */
	private Boolean _okDB = null;

	/**
	 * DB Info
	 */
	private String m_dbInfo = null;

	/*************
	 * Get Database Host name
	 *
	 * @return db host name
	 */
	public String getDbHost()
	{
		return attrs.getDbHost();
	}    // getDbHost

	/**
	 * Set Database host name
	 *
	 * @param db_host db host
	 */
	void setDbHost(String db_host)
	{
		if (Objects.equals(attrs.getDbHost(), db_host))
		{
			return;
		}

		attrs.setDbHost(db_host);
		closeDataSource();
	}    // setDbHost

	/**
	 * Get Database Name (Service Name)
	 *
	 * @return db name
	 */
	public String getDbName()
	{
		return attrs.getDbName();
	}    // getDbName

	/**
	 * Set Database Name (Service Name)
	 *
	 * @param db_name db name
	 */
	void setDbName(String db_name)
	{
		if (Objects.equals(attrs.getDbName(), db_name))
		{
			return;
		}
		attrs.setDbName(db_name);
		closeDataSource();
	}    // setDbName

	/**
	 * Get DB Port
	 *
	 * @return port
	 */
	public int getDbPort()
	{
		return attrs.getDbPort();
	}    // getDbPort

	/**
	 * Set DB Port
	 *
	 * @param db_port db port
	 */
	void setDbPort(int db_port)
	{
		if (Objects.equals(attrs.getDbPort(), db_port))
		{
			return;
		}
		attrs.setDbPort(db_port);
		closeDataSource();
	}    // setDbPort

	/**
	 * Set DB Port from string
	 *
	 * @param db_portString db port as String
	 */
	void setDbPort(String db_portString)
	{
		if (Check.isEmpty(db_portString, true))
		{
			return;
		}

		try
		{
			setDbPort(Integer.parseInt(db_portString));
		}
		catch (Exception e)
		{
			log.error("Error parsing db port: " + db_portString, e);
		}
	}    // setDbPort

	/**
	 * Get Database Password
	 *
	 * @return db password
	 */
	public String getDbPwd()
	{
		return attrs.getDbPwd();
	}    // getDbPwd

	/**
	 * Set DB password
	 *
	 * @param db_pwd db user password
	 */
	void setDbPwd(String db_pwd)
	{
		if (Objects.equals(attrs.getDbPwd(), db_pwd))
		{
			return;
		}
		attrs.setDbPwd(db_pwd);
		closeDataSource();
	}    // setDbPwd

	/**
	 * Get Database User
	 *
	 * @return db user
	 */
	public String getDbUid()
	{
		return attrs.getDbUid();
	}    // getDbUid

	/**
	 * Set Database User
	 *
	 * @param db_uid db user id
	 */
	void setDbUid(String db_uid)
	{
		if (Objects.equals(attrs.getDbUid(), db_uid))
		{
			return;
		}
		attrs.setDbUid(db_uid);
		closeDataSource();
	}    // setDbUid

	/**
	 * Get Database Type
	 *
	 * @return database type
	 */
	public String getType()
	{
		return Database.DB_POSTGRESQL;
	}

	/**
	 * Supports BLOB
	 *
	 * @return true if BLOB is supported
	 */
	public boolean supportsBLOB()
	{
		return getDatabase().supportsBLOB();
	} // supportsBLOB

	/**
	 * Is Database Connection OK.
	 *
	 * @return true if database connection is OK
	 */
	// NOTE: this method is SUPPOSED to not contact the database server or do any expensive operations.
	// It shall just return a status flag.
	// Before changing this, check the caller, and mainly check in this class, because there are some methods which depends on this approach.
	public boolean isDatabaseOK()
	{
		final boolean checkifUnknown = false;
		return isDatabaseOK(checkifUnknown);
	}    // isDatabaseOK

	/**
	 * Is Database Connection OK.
	 *
	 * @param checkifUnknown if true and the current connection status is not known, the system will try to contact the database and figure out if the db connection is really alive
	 * @return true if the database connection is alive
	 */
	public boolean isDatabaseOK(final boolean checkifUnknown)
	{
		if (checkifUnknown && this._okDB == null)
		{
			updateDatabaseOKStatus();
		}

		final Boolean ok = this._okDB;
		return ok != null && ok;
	}

	/**
	 * Sets the {@link #_okDB} flag.
	 */
	private void updateDatabaseOKStatus()
	{
		// If we are currently running this method, don't run it again.
		if (_runningUpdateDatabaseOKStatus.getAndSet(true))
		{
			return;
		}

		//
		// Actually try to aquire a new database connection.
		// The getConnection(...) method will actually the the _okDB flag.
		try (final IAutoCloseable c = LogManager.temporaryDisableIssueReporting())
		{
			Connection conn = null;
			try
			{
				conn = getConnection(true, Connection.TRANSACTION_READ_COMMITTED);
			}
			finally
			{
				DB.close(conn);
			}
		}
		catch (Exception e)
		{
			// ignore the error
		}
		finally
		{
			_runningUpdateDatabaseOKStatus.set(false);
		}

	}

	private final AtomicBoolean _runningUpdateDatabaseOKStatus = new AtomicBoolean(false);

	/**************************************************************************
	 * Create DB Connection
	 *
	 * @return data source != null
	 */
	public boolean setDataSource()
	{
		final DataSource dataSource = getDatabase().getDataSource(this);
		_okDB = null; // unknown
		return dataSource != null;
	}    // setDataSource

	/**
	 * Close DB connection.
	 */
	public synchronized void closeDataSource()
	{
		if (_database != null)
		{
			_database.close();
			_database = null;
		}

		m_dbInfo = null;
		_okDB = false;
	}

	/**
	 * Get Server Connection
	 *
	 * @return {@link DataSource} or null
	 */
	public DataSource getDataSource()
	{
		final AdempiereDatabase database = getDatabaseOrNull();
		if (database == null)
		{
			return null;
		}
		return database.getDataSource(this);
	}    // getDataSource

	/**
	 * Test Database Connection.
	 */
	public synchronized void testDatabase() throws Exception
	{
		closeDataSource();
		setDataSource();

		//
		// the actual test
		Connection conn = null;
		try
		{
			conn = getConnection(true, Connection.TRANSACTION_READ_COMMITTED);
			getDBInfo(); // triggers DBInfo retrieval
		}
		finally
		{
			DB.close(conn);
		}
	}    // testDatabase

	/**
	 * Tests database connection, if not already tested.
	 * <p>
	 * This method never throws an exception.
	 *
	 * @return true if database connection is OK
	 */
	private boolean testDatabaseIfNeeded()
	{
		// Check if database connection is already OK
		if (isDatabaseOK())
		{
			return true;
		}

		// Test database connection now
		try
		{
			testDatabase();
			return true;
		}
		catch (Exception e)
		{
			log.error("Testing database connection failed for {}", this, e);
			return false;
		}
	}

	/*************************************************************************
	 * Short String representation
	 *
	 * @return {dbHost-dbName-userid}
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("{").append(attrs.getDbHost())
				.append("-").append(attrs.getDbName())
				.append("-").append(attrs.getDbUid())
				.append("}");
		return sb.toString();
	}    // toString

	/**
	 * Get DB Version Info (short text).
	 * <p>
	 * This method never throws exceptions.
	 *
	 * @return info
	 */
	public String getDBInfo()
	{
		if (m_dbInfo == null)
		{
			try
			{
				final String dbInfo = retrieveDBInfo();
				m_dbInfo = dbInfo;
				return dbInfo;
			}
			catch (SQLException e)
			{
				log.error("Failed retrieving database informations", e);
				return "?";
			}
		}
		return m_dbInfo;
	}

	private String retrieveDBInfo() throws SQLException
	{
		Connection conn = null;
		try
		{
			conn = getConnection(true, Connection.TRANSACTION_READ_COMMITTED);
			final DatabaseMetaData dbmd = conn.getMetaData();

			// NOTE: keep this text short because it will be stored as a short reference in places like AD_Issue.DatabaseInfo
			StringBuilder sb = new StringBuilder();
			sb.append(dbmd.getDatabaseProductVersion()).append(";").append(dbmd.getDriverVersion());
			return sb.toString();
		}
		finally
		{
			DB.close(conn);
		}
	}

	/**
	 * String representation.
	 * Used also for Instantiation
	 *
	 * @return string representation
	 * @see #setAttributes(String) setAttributes
	 */
	public String toStringLong()
	{
		return attrs.toString();
	}    // toStringLong

	/**
	 * Set Attributes from String (pares toStringLong())
	 *
	 * @param attributes attributes
	 * @throws AdempiereException if something went wrong
	 */
	private void setAttributes(@NonNull final String attributes)
	{
		try
		{
			final CConnectionAttributes connectionAttributes = CConnectionAttributes.of(attributes);
			setAttributes(connectionAttributes);
		}
		catch (Exception e)
		{
			// NOTE: don't append the attributes because the exception will be logged and "attributes" might contain sensitive informations (like db password).
			throw new AdempiereException("Cannot configure connection from attributes", e);
		}
	}

	private void setAttributes(@NonNull final CConnectionAttributes connectionAttributes)
	{
		setDbHost(connectionAttributes.getDbHost());
		setDbPort(connectionAttributes.getDbPort());
		setDbName(connectionAttributes.getDbName());

		setDbUid(connectionAttributes.getDbUid());
		setDbPwd(connectionAttributes.getDbPwd());
	}

	/**
	 * Equals
	 *
	 * @param o object
	 * @return true if o equals this
	 */
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null)
		{
			return false;
		}
		if (o instanceof CConnection)
		{
			final CConnection cc = (CConnection)o;
			if (true
					&& cc.getDbHost().equals(attrs.getDbHost())
					&& cc.getDbPort() == attrs.getDbPort()
					&& cc.getDbName().equals(attrs.getDbName())
					&& cc.getDbUid().equals(attrs.getDbUid())
					&& cc.getDbPwd().equals(attrs.getDbPwd()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Get Info.
	 * - Database, Driver, Status Info
	 *
	 * @return info
	 */
	public String getInfo()
	{
		final StringBuilder sb = new StringBuilder();

		final String dbInfo = m_dbInfo;
		if (dbInfo != null)
		{
			sb.append(dbInfo);
		}

		AdempiereDatabase database = getDatabaseOrNull();
		if (database != null)
		{
			sb.append("\n").append(database);
		}

		sb.append(", DatabaseOK=").append(isDatabaseOK());
		return sb.toString();
	}    // getInfo

	/**
	 * @return hashcode of attrs
	 */
	@Override
	public int hashCode()
	{
		return attrs.hashCode();
	}    // hashCode

	/**
	 * Get Database
	 *
	 * @return database
	 */
	public synchronized AdempiereDatabase getDatabase()
	{
		if (_database != null)
		{
			return _database;
		}

		try
		{
			_database = Database.newAdempiereDatabase(getType());
			_database.getDataSource(this);
		}
		catch (NoClassDefFoundError ee)
		{
			System.err.println("Environment Error - Check Adempiere.properties - " + ee);
			ee.printStackTrace();

			if (Ini.isSwingClient())
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(null, "There is a configuration error:\n" + ee
								+ "\nDo you want to reset the saved configuration?",
						"Adempiere Configuration Error",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE))
				{
					Ini.deletePropertyFile();
				}
			}
			System.exit(1);
		}
		catch (Exception e)
		{
			throw DBNoConnectionException.wrapIfNeeded(e);
		}

		return _database;
	}    // getDatabase

	private synchronized AdempiereDatabase getDatabaseOrNull()
	{
		return _database;
	}

	/**
	 * Get Connection String
	 *
	 * @return connection string or empty string if database was not initialized
	 */
	public String getConnectionURL()
	{
		final AdempiereDatabase db = getDatabaseOrNull();
		if (db != null)
		{
			return db.getConnectionURL(this);
		}
		else
		{
			return "";
		}
	}    // getConnectionURL

	/**
	 * Acquires a database Connection.
	 * <p>
	 * Sets the {@link #isDatabaseOK()} flag.
	 *
	 * @param autoCommit           true if auto-commit connection
	 * @param transactionIsolation Connection transaction level
	 * @return database connection
	 */
	public Connection getConnection(final boolean autoCommit, final int transactionIsolation)
	{
		final AdempiereDatabase db = getDatabase(); // creates connection if not already exist
		if (db == null)
		{
			throw new DBNoConnectionException("No AdempiereDatabase found");
		}

		Connection conn = null;
		boolean connOk = false;
		try
		{
			conn = db.getCachedConnection(this, autoCommit, transactionIsolation);
			if (conn == null)
			{
				// shall not happen
				throw new DBNoConnectionException();
			}

			// Mark the database connection status as OK
			_okDB = true;

			connOk = true;
			return conn;
		}
		catch (UnsatisfiedLinkError ule)
		{
			_okDB = false;
			final String msg = "" + ule.getLocalizedMessage() + " -> Did you set the LD_LIBRARY_PATH ? - " + getConnectionURL();
			throw new DBNoConnectionException(msg, ule);
		}
		catch (Exception ex)
		{
			_okDB = false;
			throw DBNoConnectionException.wrapIfNeeded(ex);
		}
		finally
		{
			if (!connOk)
			{
				DB.close(conn);
			}
		}
	}    // getConnection

	/**
	 * Get Transaction Isolation Info
	 *
	 * @param transactionIsolation
	 * @return transaction isolation name
	 */
	@SuppressWarnings("unused")
	private static String getTransactionIsolationInfo(int transactionIsolation)
	{
		if (transactionIsolation == Connection.TRANSACTION_NONE)
		{
			return "NONE";
		}
		if (transactionIsolation == Connection.TRANSACTION_READ_COMMITTED)
		{
			return "READ_COMMITTED";
		}
		if (transactionIsolation == Connection.TRANSACTION_READ_UNCOMMITTED)
		{
			return "READ_UNCOMMITTED";
		}
		if (transactionIsolation == Connection.TRANSACTION_REPEATABLE_READ)
		{
			return "REPEATABLE_READ";
		}
		if (transactionIsolation == Connection.TRANSACTION_SERIALIZABLE)
		{
			return "SERIALIZABLE";
		}
		return "<?" + transactionIsolation + "?>";
	}    // getTransactionIsolationInfo

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CConnection c = (CConnection)super.clone();
		return c;
	}
}    // CConnection
