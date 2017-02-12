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
package org.compiere.db;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.naming.CommunicationException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.jms.IJMSService;
import de.metas.logging.LogManager;
import de.metas.session.jaxrs.IStatusService;
import de.metas.session.jaxrs.StatusServiceResult;

/**
 * Adempiere Connection Descriptor
 *
 * @author Jorg Janke
 * @author Marek Mosiewicz<marek.mosiewicz@jotel.com.pl> - support for RMI over HTTP
 * @version $Id: CConnection.java,v 1.5 2006/07/30 00:55:13 jjanke Exp $
 */
public final class CConnection implements Serializable, Cloneable
{
	private static final String MSG_APPSERVER_CONNECTION_PROBLEM = "CConnection.AppserverConnectionProblem";

	/**
	 *
	 */
	private static final long serialVersionUID = -7893119456331485444L;
	/** Singleton Connection */
	private static volatile CConnection s_cc = null;
	/** Logger */
	private static final transient Logger log = LogManager.getLogger(CConnection.class);
	static
	{
		LogManager.skipIssueReportingForLoggerName(log);
	}

	/** System property flag to embed server bean in process **/
	public final static String SERVER_EMBEDDED_PROPERTY = "de.metas.server.embedded";

	public final static String SERVER_EMBEDDED_APPSERVER_HOSTNAME = "localhost";

	public final static int SERVER_DEFAULT_APPSERVER_PORT = 61616;

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
					final CConnection cc = createInstance();
					s_cc = cc;
				}
			}
		}
		return s_cc;
	} 	// get

	/**
	 * Creates and setups a new CConnection instance.
	 *
	 * @return Connection Descriptor; never returns null.
	 */
	private static final CConnection createInstance()
	{
		CConnection cc = null;
		//
		// First, try to create the connection from properties, if any
		final String attributes = Ini.getProperty(Ini.P_CONNECTION);
		if (!Check.isEmpty(attributes))
		{
			cc = new CConnection();
			try
			{
				cc.setAttributes(attributes);
				cc.testDatabaseIfNeeded();
			}
			catch (Exception e)
			{
				cc = null;
				log.error("Failed loading the connection from attributes: {}", attributes, e);
			}
		}

		//
		// Ask user to provide the configuration if not already configured
		while(cc == null || !cc.isDatabaseOK())
		{
			cc = createInstance_FromUI(cc);
			cc.testDatabaseIfNeeded();
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
			ccTemplateToUse.setAppsPort(SERVER_DEFAULT_APPSERVER_PORT);
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
		super();
	} 	// CConnection

	/** Connection attributes */
	private final CConnectionAttributes attrs = new CConnectionAttributes();
	/** Database */
	private volatile AdempiereDatabase _database = null;
	private Exception m_appsException = null;

	/**
	 * Is Database Connection OK flag. It can have following values
	 * <ul>
	 * <li>true - database connection is alive
	 * <li>false - database connection is not alive
	 * <li>null - database connection status is unknown
	 * </ul>
	 */
	private Boolean _okDB = null;
	/** Apps Server Connection */
	private boolean m_okApps = false;

	/** Server Version */
	private String m_version = null;

	/** DB Info */
	private String m_dbInfo = null;

	/** Had application server been query **/
	private boolean m_appServerWasQueried = false;

	private static IStatusServiceEndPointProvider m_statusServiceEndpointProvider = null;

	/**
	 * don't access directly, but use {@link #getStatusServiceOrNull()} instead.
	 */
	private IStatusService m_statusServiceEndpoint = null;

	/*************************************************************************
	 * Get Name
	 *
	 * @return connection name
	 */
	public String getName()
	{
		return attrs.getName();
	}

	/**
	 * Set Name
	 *
	 * @param name connection name
	 */
	void setName(String name)
	{
		attrs.setName(name);
	}	// setName

	/**
	 * Sets the name from this instance's {@link #toString()} result.
	 */
	protected void setName()
	{
		attrs.setName(toString());
	} 	// setName

	/*************
	 * Get Application Host
	 *
	 * @return apps host
	 */
	public String getAppsHost()
	{
		if (isServerEmbedded())
		{
			return SERVER_EMBEDDED_APPSERVER_HOSTNAME;
		}

		return attrs.getAppsHost();
	}

	/**
	 * Set Application Host
	 *
	 * @param apps_host apps host
	 */
	void setAppsHost(String apps_host)
	{
		if (Check.equals(attrs.getAppsHost(), apps_host))
		{
			return;
		}

		attrs.setAppsHost(apps_host);
		setName();

		resetAppsServer(); // reset our cached infos about the apps server.
	}

	private final void resetAppsServer()
	{
		m_okApps = false;
		m_appServerWasQueried = false;
		m_appsException = null;
		m_version = null;

		m_statusServiceEndpoint = null;

		// Reset EJB context
		// m_env = null;
		// m_iContext = null;
	}

	/**
	 * Get Apps Port
	 *
	 * @return port
	 */
	public int getAppsPort()
	{
		if (isServerEmbedded())
		{
			return SERVER_DEFAULT_APPSERVER_PORT;
		}
		final int appsPort = attrs.getAppsPort();
		if (appsPort > 0)
		{
			return appsPort;
		}
		// appsPort<=0 never makes sense, so update it to our default.
		setAppsPort(SERVER_DEFAULT_APPSERVER_PORT);
		return attrs.getAppsPort();
	}

	/**
	 * Set Apps Port
	 *
	 * @param apps_port apps port
	 */
	public void setAppsPort(int apps_port)
	{
		if (Check.equals(attrs.getAppsPort(), apps_port))
		{
			return;
		}
		attrs.setAppsPort(apps_port);
		m_okApps = false;
		m_appServerWasQueried = false;

		resetAppsServer(); // reset our cached infos about the apps server.
	}

	/**
	 * Set Apps Port from string
	 *
	 * @param apps_portString appd port as String
	 */
	void setAppsPort(String apps_portString)
	{
		if (Check.isEmpty(apps_portString, true))
		{
			return;
		}

		try
		{
			setAppsPort(Integer.parseInt(apps_portString));
		}
		catch (Exception e)
		{
			log.error(e.toString());
		}
	} 	// setAppsPort

	/**
	 * Is Application Server OK
	 *
	 * @param tryContactAgain try to contact again
	 * @return true if Apps Server exists
	 */
	public boolean isAppsServerOK(boolean tryContactAgain)
	{
		if (Ini.isClient() && !tryContactAgain && m_appServerWasQueried)
		{
			return m_okApps; // return the info that we already have
		}

		m_okApps = false;
		m_appServerWasQueried = true; // set this member to true, also if the invocation was not successful.

		if (Check.isEmpty(getAppsHost(), true) || attrs.isNoAppsHost())
		{
			// we don't even have an application server name set, so clearly the appServer is *not* OK.
			// note that we need to to also avoid a StackOverFlowError, because otherwise out status-server would try to get the getAppsHost() using
			// CConnection.get().getAppsHost(), resulting in another call to isAppsServerOK(), and so on.
			return m_okApps;
		}

		final IStatusService statusService = getStatusServiceOrNull();
		if (statusService == null)
		{
			return false; // can't get an endpoint to connect with.
		}

		// Contact it
		try
		{
			Services.get(IJMSService.class).updateConfiguration();
			final StatusServiceResult status = statusService.getStatus();

			m_version = status.getDateVersion();
			m_okApps = true;
		}
		catch (Throwable t)
		{
			m_okApps = false;
			String connect = toString();
			if (Check.isEmpty(connect, true))
			{
				connect = getAppsHost() + ":" + getAppsPort();
			}
			log.error("Caught this while trying to connect to application server {}: {}", connect, t.toString());
			Services.get(IClientUI.class).error(0, MSG_APPSERVER_CONNECTION_PROBLEM, t.getLocalizedMessage());
			t.printStackTrace();
		}
		return m_okApps;
	} 	// isAppsOK

	private IStatusService getStatusServiceOrNull()
	{
		if (m_statusServiceEndpoint == null)
		{
			if (m_statusServiceEndpointProvider == null)
			{
				return null;
			}
			m_statusServiceEndpoint = m_statusServiceEndpointProvider.provide(this);
		}
		return m_statusServiceEndpoint;
	}

	/**
	 * Test ApplicationServer
	 *
	 * @return Exception or null
	 */
	public synchronized Exception testAppsServer()
	{
		if (CConnection.isServerEmbedded())
		{
			return null; // there is nothing to do
		}
		queryAppsServerInfo();
		return getAppsServerException();
	} 	// testAppsServer

	/**
	 * Get Apps Server Version Info (user friendly)
	 *
	 * @return application server version. It could return following values:
	 *         <ul>
	 *         <li>actual application server version
	 *         <li>"embedded" if we are running with an embedded server
	 *         <li>"unknown" if for some reason application server version is not set / not available
	 *         </ul>
	 */
	public String getServerVersionInfo()
	{
		final String version = getServerVersion();
		if (!Check.isEmpty(version))
		{
			return version;
		}
		else
		{
			return "unknown";
		}
	}	// getServerVersion

	/**
	 * Get Apps Server Version.
	 *
	 * @return version or null;
	 */
	public String getServerVersion()
	{
		return m_version;
	}

	/*************
	 * Get Database Host name
	 *
	 * @return db host name
	 */
	public String getDbHost()
	{
		return attrs.getDbHost();
	}	// getDbHost

	/**
	 * Set Database host name
	 *
	 * @param db_host db host
	 */
	void setDbHost(String db_host)
	{
		if (Check.equals(attrs.getDbHost(), db_host))
		{
			return;
		}

		attrs.setDbHost(db_host);
		setName();
		closeDataSource();
	}	// setDbHost

	/**
	 * Get Database Name (Service Name)
	 *
	 * @return db name
	 */
	public String getDbName()
	{
		return attrs.getDbName();
	}	// getDbName

	/**
	 * Set Database Name (Service Name)
	 *
	 * @param db_name db name
	 */
	void setDbName(String db_name)
	{
		if (Check.equals(attrs.getDbName(), db_name))
		{
			return;
		}
		attrs.setDbName(db_name);
		setName();
		closeDataSource();
	}	// setDbName

	/**
	 * Get DB Port
	 *
	 * @return port
	 */
	public int getDbPort()
	{
		return attrs.getDbPort();
	}	// getDbPort

	/**
	 * Set DB Port
	 *
	 * @param db_port db port
	 */
	void setDbPort(int db_port)
	{
		if (Check.equals(attrs.getDbPort(), db_port))
		{
			return;
		}
		attrs.setDbPort(db_port);
		closeDataSource();
	}	// setDbPort

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
	} 	// setDbPort

	/**
	 * Get Database Password
	 *
	 * @return db password
	 */
	public String getDbPwd()
	{
		return attrs.getDbPwd();
	}	// getDbPwd

	/**
	 * Set DB password
	 *
	 * @param db_pwd db user password
	 */
	void setDbPwd(String db_pwd)
	{
		if (Check.equals(attrs.getDbPwd(), db_pwd))
		{
			return;
		}
		attrs.setDbPwd(db_pwd);
		closeDataSource();
	}	// setDbPwd

	/**
	 * Get Database User
	 *
	 * @return db user
	 */
	public String getDbUid()
	{
		return attrs.getDbUid();
	}	// getDbUid

	/**
	 * Set Database User
	 *
	 * @param db_uid db user id
	 */
	void setDbUid(String db_uid)
	{
		if (Check.equals(attrs.getDbUid(), db_uid))
		{
			return;
		}
		attrs.setDbUid(db_uid);
		setName();
		closeDataSource();
	}	// setDbUid


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
	 * Set Database Type and default settings.
	 * Checked against installed databases
	 *
	 * @param type database Type, e.g. Database.DB_ORACLE
	 */
	synchronized void setType(final String type)
	{
		for (int i = 0; i < Database.DB_NAMES.length; i++)
		{
			if (Database.DB_NAMES[i].equals(type))
			{
				if (!Check.equals(attrs.getDbType(), type))
				{
					attrs.setDbType(type);
					closeDataSource();
				}
				break;
			}
		}

		// begin vpj-cd e-evolution 09 ene 2006
		// PostgreSQL
		if (isPostgreSQL())
		{
			if (getDbPort() != DB_PostgreSQL.DEFAULT_PORT)
				setDbPort(DB_PostgreSQL.DEFAULT_PORT);
		}
		// end vpj-cd e-evolution 09 ene 2006
	} 	// setType

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

	 * Is PostgreSQL DB
	 *
	 * @return true if PostgreSQL
	 */
	public boolean isPostgreSQL()
	{
		return Database.DB_POSTGRESQL.equals(attrs.getDbType());
	} 	// isPostgreSQL

	/**
	 * Is Database Connection OK.
	 *
	 * @return true if database connection is OK
	 */
	// NOTE: this method is SUPPOSED to not contact the database server or do any expensive operations.
	// It shall just return a status flag.
	// Before changing this, check the caller, and mainly check in this class, because there are some methods which depends on this approach.
	public final boolean isDatabaseOK()
	{
		final boolean checkifUnknown = false;
		return isDatabaseOK(checkifUnknown);
	} 	// isDatabaseOK

	/**
	 * Is Database Connection OK.
	 *
	 * @param checkifUnknown if true and the current connection status is not known, the system will try to contact the database and figure out if the db connection is really alive
	 * @return true if the database connection is alive
	 */
	public final boolean isDatabaseOK(final boolean checkifUnknown)
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
	private final void updateDatabaseOKStatus()
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
	} 	// setDataSource

	/**
	 * Close DB connection.
	 */
	public synchronized final void closeDataSource()
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
	} 	// getDataSource

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
	} 	// testDatabase

	/**
	 * Tests database connection, if not already tested.
	 *
	 * This method never throws an exception.
	 *
	 * @return true if database connection is OK
	 */
	private final boolean testDatabaseIfNeeded()
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
	 * @return appsHost{dbHost-dbName-uid}
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder(attrs.getAppsHost());
		sb.append("{").append(attrs.getDbHost())
				.append("-").append(attrs.getDbName())
				.append("-").append(attrs.getDbUid())
				.append("}");
		return sb.toString();
	} 	// toString

	/**
	 * Get DB Version Info (short text).
	 *
	 * This method never throws exceptions.
	 *
	 * @return info
	 */
	public final String getDBInfo()
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

	private final String retrieveDBInfo() throws SQLException
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
	}	// toStringLong

	/**
	 * Set Attributes from String (pares toStringLong())
	 *
	 * @param attributes attributes
	 * @throws AdempiereException if something went wrong
	 */
	private void setAttributes(final String attributes)
	{
		try
		{
			final CConnectionAttributes connectionString = CConnectionAttributes.of(attributes);
			setName(connectionString.getName());
			setAppsHost(connectionString.getAppsHost());

			int appsPort = connectionString.getAppsPort();
			if (appsPort == 1099)
			{
				appsPort = SERVER_DEFAULT_APPSERVER_PORT; // this is a workaround, since older config files might still have 1099, but our new standard port is 61616
			}
			setAppsPort(appsPort);

			//
			setType(connectionString.getDbType());
			setDbHost(connectionString.getDbHost());
			setDbPort(connectionString.getDbPort());
			setDbName(connectionString.getDbName());
			//
			setDbUid(connectionString.getDbUid());
			setDbPwd(connectionString.getDbPwd());
		}
		catch (Exception e)
		{
			// NOTE: don't append the attributes because the exception will be logged and "attributes" might contain sensitive informations (like db password).
			throw new AdempiereException("Cannot configure connection from attributes", e);
		}
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
			if (cc.getAppsHost().equals(attrs.getAppsHost())
					&& cc.getAppsPort() == attrs.getAppsPort()
					&& cc.getDbHost().equals(attrs.getDbHost())
					&& cc.getDbPort() == attrs.getDbPort()
					&& cc.getDbName().equals(attrs.getDbName())
					&& cc.getType().equals(attrs.getDbType())
					&& cc.getDbUid().equals(attrs.getDbUid())
					&& cc.getDbPwd().equals(attrs.getDbPwd()))
				return true;
		}
		return false;
	}	// equals

	/*************************************************************************
	 * Hashcode
	 *
	 * @return hashcode of name
	 */
	@Override
	public int hashCode()
	{
		return attrs.getName().hashCode();
	} 	// hashCode

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

		sb.append("\nAppsServerOK=").append(isAppsServerOK(false));
		sb.append(", DatabaseOK=").append(isDatabaseOK());
		return sb.toString();
	}	// getInfo

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

			if (Ini.isClient())
			{
				if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog
						(null, "There is a configuration error:\n" + ee
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
	} 	// getDatabase

	private final synchronized AdempiereDatabase getDatabaseOrNull()
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
			return db.getConnectionURL(this);
		else
			return "";
	} 	// getConnectionURL

	/**
	 * Acquires a database Connection.
	 *
	 * Sets the {@link #isDatabaseOK()} flag.
	 *
	 * @param autoCommit true if auto-commit connection
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
	}	// getConnection

	/*************************************************************************/
// @formatter:off
//	private InitialContext m_iContext = null;
//	//private Hashtable<String, String> m_env = null;
//
//	/**
//	 * Get Application Server Initial Context
//	 *
//	 * @param useCache if true, use existing cache
//	 * @return Initial Context or null
//	 */
//	public InitialContext getInitialContext(boolean useCache)
//	{
//		if (useCache && m_iContext != null)
//			return m_iContext;
//
//		// Set Environment
//		if (m_env == null || !useCache)
//		{
//			SecurityPrincipal sp = (SecurityPrincipal)Env.getCtx().get(SECURITY_PRINCIPAL);
//			String principal = sp != null ? sp.principal : null;
//			String credential = sp != null ? sp.credential : null;
//			m_env = getInitialEnvironment(getAppsHost(), getAppsPort(), false,
//					principal, credential);
//		}
//		String connect = m_env.get(Context.PROVIDER_URL);
//		Env.setContext(Env.getCtx(), Context.PROVIDER_URL, connect);
//
//		// Get Context
//		m_iContext = null;
//		try
//		{
//			m_iContext = new InitialContext(m_env);
//		}
//		catch (Exception ex)
//		{
//			m_okApps = false;
//			m_appsException = ex;
//			if (connect == null)
//				connect = m_env.get(Context.PROVIDER_URL);
//			log.error(connect
//					+ "\n - " + ex.toString()
//					+ "\n - " + m_env);
//			if (CLogMgt.isLevelFinest())
//				ex.printStackTrace();
//		}
//		return m_iContext;
//	}	// getInitialContext
//
//	/**
//	 * Get Initial Environment
//	 *
//	 * @param AppsHost host
//	 * @param AppsPort port
//	 * @param RMIoverHTTP ignore, retained for backward compatibility
//	 * @param principal
//	 * @param credential
//	 * @return environment
//	 */
//	private Hashtable<String, String> getInitialEnvironment(String AppsHost, int AppsPort,
//			boolean RMIoverHTTP, String principal, String credential)
//	{
//		return ASFactory.getApplicationServer()
//				.getInitialContextEnvironment(AppsHost, AppsPort, principal, credential);
//	}	// getInitialEnvironment
	// @formatter:on

	/**
	 * Query Application Server Status.
	 * update okApps
	 *
	 * @return true if OK
	 */
	private boolean queryAppsServerInfo()
	{
		log.trace(getAppsHost());
		long start = System.currentTimeMillis();

		m_appsException = null;

		try
		{
			final IStatusService statusService = getStatusServiceOrNull();
			if (statusService == null)
			{
				return false;
			}
			if (!isAppsServerOK(false))
			{
				return false;
			}

			updateInfoFromServer(statusService);
		}
		catch (CommunicationException ce)	// not a "real" error
		{
			m_appsException = ce;
			String connect = toString();
			if (Check.isEmpty(connect, true))
			{
				connect = getAppsHost() + ":" + getAppsPort();
			}
			log.warn(connect + "\n - " + ce.toString());
			ce.printStackTrace();
		}
		catch (Exception e)
		{
			m_appsException = e;
			String connect = toString();
			if (Check.isEmpty(connect, true))
			{
				connect = getAppsHost() + ":" + getAppsPort();
			}
			log.warn(connect + "\n - " + e.toString());
			e.printStackTrace();
		}
		log.debug("Success=" + m_okApps + " - " + (System.currentTimeMillis() - start) + "ms");
		return m_okApps;
	}	// setAppsServerInfo

	/**
	 * Get Last Exception of Apps Server Connection attempt
	 *
	 * @return Exception or null
	 */
	private Exception getAppsServerException()
	{
		return m_appsException;
	} 	// getAppsServerException

	/**
	 * Update Connection Info from Apps Server
	 *
	 * @param svr Apps Server Status
	 * @throws Exception
	 */
	private void updateInfoFromServer(final IStatusService svr) throws Exception
	{
		if (svr == null)
		{
			throw new IllegalArgumentException("AppsServer was NULL");
		}

		final StatusServiceResult status = svr.getStatus();

		setType(status.getDbType());
		setDbHost(status.getDbHost());
		setDbPort(status.getDbPort());
		setDbName(status.getDbName());
		setDbUid(status.getDbUid());
		setDbPwd(status.getDbPwd());

		m_version = status.getDateVersion();
		log.debug("Server=" + getDbHost() + ", DB=" + getDbName());
	} 	// update Info

	/**
	 * Get Status Info
	 *
	 * @return info
	 */
	public String getStatus()
	{
		StringBuilder sb = new StringBuilder();
		sb.append(attrs.getName());

		final AdempiereDatabase db = getDatabaseOrNull();
		if (db != null)
			sb.append(db.getStatus());
		return sb.toString();
	}	// getStatus

	/**
	 * Get Transaction Isolation Info
	 *
	 * @param transactionIsolation
	 * @return transaction isolation name
	 */
	@SuppressWarnings("unused")
	private static final String getTransactionIsolationInfo(int transactionIsolation)
	{
		if (transactionIsolation == Connection.TRANSACTION_NONE)
			return "NONE";
		if (transactionIsolation == Connection.TRANSACTION_READ_COMMITTED)
			return "READ_COMMITTED";
		if (transactionIsolation == Connection.TRANSACTION_READ_UNCOMMITTED)
			return "READ_UNCOMMITTED";
		if (transactionIsolation == Connection.TRANSACTION_REPEATABLE_READ)
			return "REPEATABLE_READ";
		if (transactionIsolation == Connection.TRANSACTION_SERIALIZABLE)
			return "SERIALIZABLE";
		return "<?" + transactionIsolation + "?>";
	}	// getTransactionIsolationInfo

	/**
	 * @return true if server is embedded in process
	 */
	public static boolean isServerEmbedded()
	{
		return Boolean.getBoolean(SERVER_EMBEDDED_PROPERTY); // return the system property
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CConnection c = (CConnection)super.clone();
		return c;
	}

	/**
	 * Implementors provide a IStatusService proxy, implementation or whatever that can be used by the CConnection to talk to the application server.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	public interface IStatusServiceEndPointProvider
	{
		IStatusService provide(CConnection cConnection);
	}

	public static void setStatusServiceEndPointProvider(final IStatusServiceEndPointProvider provider)
	{
		Check.errorIf(m_statusServiceEndpointProvider != null,
				"CConnection already has m_statusServiceEndpointProvider={}",
				m_statusServiceEndpointProvider);

		m_statusServiceEndpointProvider = provider;
	}

}	// CConnection
