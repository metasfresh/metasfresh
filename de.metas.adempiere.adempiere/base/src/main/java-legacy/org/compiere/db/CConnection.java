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
import java.util.Hashtable;
import java.util.logging.Level;

import javax.naming.CommunicationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.adempiere.as.ASFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.adempiere.util.Check;
import org.compiere.Adempiere;
import org.compiere.interfaces.Server;
import org.compiere.interfaces.Status;
import org.compiere.session.ServerBase;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.ValueNamePair;

/**
 * Adempiere Connection Descriptor
 *
 * @author Jorg Janke
 * @author Marek Mosiewicz<marek.mosiewicz@jotel.com.pl> - support for RMI over HTTP
 * @version $Id: CConnection.java,v 1.5 2006/07/30 00:55:13 jjanke Exp $
 */
public final class CConnection implements Serializable, Cloneable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7893119456331485444L;
	/** Singleton Connection */
	private static volatile CConnection s_cc = null;
	/** Logger */
	private static final transient CLogger log = CLogger.getCLogger(CConnection.class)
			.setSkipIssueReporting();

	/** Connection profiles */
	@Deprecated
	public static ValueNamePair[] CONNECTIONProfiles = new ValueNamePair[] {
			new ValueNamePair("L", "LAN") };

	/** Connection Profile LAN */
	@Deprecated
	public static final String PROFILE_LAN = "L";
	/**
	 * Connection Profile Terminal Server
	 * 
	 * @deprecated
	 **/
	@Deprecated
	public static final String PROFILE_TERMINAL = "T";
	/** Connection Profile VPM */
	@Deprecated
	public static final String PROFILE_VPN = "V";
	/** Connection Profile WAN */
	@Deprecated
	public static final String PROFILE_WAN = "W";

	private final static String COMPONENT_NS = "java:comp/env";

	/** Prefer component namespace when running at server **/
	private boolean useComponentNamespace = !Ini.isClient();

	/** System property flag to embed server bean in process **/
	public final static String SERVER_EMBEDDED = "org.adempiere.server.embedded";

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
		final String apps_host = Adempiere.getCodeBaseHost();
		CConnection cc;

		//
		// Case: no connection settings found in Ini/config file
		final String attributes = Ini.getProperty(Ini.P_CONNECTION);
		if (attributes == null || attributes.length() == 0)
		{
			// hengsin, zero setup for webstart client
			if (apps_host != null && Adempiere.isWebStartClient() && !CConnection.isServerEmbedded())
			{
				cc = new CConnection(apps_host);
				cc.setConnectionProfile(CConnection.PROFILE_LAN);
				cc.setAppsPort(ASFactory.getApplicationServer().getDefaultNamingServicePort());
				if (cc.testAppsServer() == null)
				{
					Ini.setProperty(Ini.P_CONNECTION, cc.toStringLong());
					Ini.saveProperties();
				}
			}
			// Case: create a new connection from scratch
			else
			{
				cc = new CConnection(apps_host);

				// Ask the user (UI!) to provide the parameters
				final CConnectionDialog ccd = new CConnectionDialog(cc);
				cc = ccd.getConnection();
				
				// If user canceled this dialog, there is NO point to go forward because it will propagate the wrong connection (task 09327)
				if (ccd.isCancel())
				{
					throw new DBNoConnectionException("User canceled the connection dialog");
				}

				// Test database connection
				if (!ccd.isCancel() && !cc.isDatabaseOK())
				{
					try
					{
						cc.testDatabase();
					}
					catch (Exception e)
					{
						log.log(Level.SEVERE, e.getMessage(), e);
					}
				}

				// set also in ALogin and Ctrl
				Ini.setProperty(Ini.P_CONNECTION, cc.toStringLong());
				Ini.saveProperties(Ini.isClient());
			}
		}
		//
		// Case: connection settings are available
		else
		{
			cc = new CConnection(null);
			cc.setAttributes(attributes);
		}

		log.log(Level.FINE, "Created: {0}", cc);
		return cc;
	}

	/**************************************************************************
	 * Adempiere Connection
	 * 
	 * @param host optional application/db host
	 */
	public CConnection(final String host)
	{
		super();
		if (host != null)
		{
			attrs.setAppsHost(host);
			attrs.setDbHost(host);
		}
	} 	// CConnection

	/** Connection attributes */
	private final CConnectionAttributes attrs = new CConnectionAttributes();
	/** Database */
	private volatile AdempiereDatabase _database = null;
	private Exception m_appsException = null;

	/** Database Connection */
	private boolean m_okDB = false;
	/** Apps Server Connection */
	private boolean m_okApps = false;

	/** Server Version */
	private String m_version = null;

	/** Server Session (cached) */
	private Server m_server = null;
	/** DB Info */
	private String m_dbInfo = null;

	/** Had application server been query **/
	private boolean m_queryAppsServer = false;

	private final static String SECURITY_PRINCIPAL = "org.adempiere.security.principal";

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
	 * Set Name
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
		m_okApps = false;
		m_queryAppsServer = false;
	}

	/**
	 * Get Apps Port
	 * 
	 * @return port
	 */
	public int getAppsPort()
	{
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
		m_queryAppsServer = false;
	}

	/**
	 * Set Apps Port
	 * 
	 * @param apps_portString appd port as String
	 */
	void setAppsPort(String apps_portString)
	{
		try
		{
			if (apps_portString == null || apps_portString.length() == 0)
				;
			else
				setAppsPort(Integer.parseInt(apps_portString));
		}
		catch (Exception e)
		{
			log.severe(e.toString());
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
		if (isServerEmbedded())
		{
			return true;
		}

		if (Ini.isClient() && !tryContactAgain && m_queryAppsServer)
		{
			return m_okApps;
		}

		// Carlos Ruiz - globalqss - speed up when jnp://MyAppsServer:1099 is set
		if (attrs.isNoAppsHost())
		{
			if (tryContactAgain)
			{
				log.warning(getAppsHost() + " ignored");
			}
			return false;
		}

		m_queryAppsServer = true;

		// Contact it
		try
		{
			Status status = (Status)lookup(Status.JNDI_NAME);
			m_version = status.getDateVersion();
			m_okApps = true;
		}
		catch (Exception ce)
		{
			m_okApps = false;
			String connect = m_env.get(Context.PROVIDER_URL);
			if (connect == null || connect.trim().length() == 0)
				connect = getAppsHost() + ":" + getAppsPort();
			log.warning(connect
					+ "\n - " + ce.toString()
					+ "\n - " + m_env);
			ce.printStackTrace();
		}
		catch (Throwable t)
		{
			m_okApps = false;
			String connect = m_env.get(Context.PROVIDER_URL);
			if (connect == null || connect.trim().length() == 0)
				connect = getAppsHost() + ":" + getAppsPort();
			log.warning(connect
					+ "\n - " + t.toString()
					+ "\n - " + m_env);
			t.printStackTrace();
		}
		return m_okApps;
	} 	// isAppsOK

	/**
	 * Test ApplicationServer
	 * 
	 * @return Exception or null
	 */
	public synchronized Exception testAppsServer()
	{
		queryAppsServerInfo();
		return getAppsServerException();
	} 	// testAppsServer

	/**
	 * Get Server
	 * 
	 * @return {@link Server}; never returns <code>null</code>
	 */
	public Server getServer()
	{
		// NOTE: cache Server only for client. On server side, ask it each time
		final boolean useCache = Ini.isClient();

		//
		// If server is already cached and we are allowed to use cache, return it from cache
		if (useCache && m_server != null)
		{
			return m_server;
		}

		//
		// Create a new Server instance
		final Server server = createServer();
		// Update cache
		m_server = useCache ? server : null;

		return m_server;
	}	// getServer

	/**
	 * @return newly created {@link Server}; never returns <code>null</code>
	 */
	private final Server createServer()
	{
		//
		// Create Embedded server if applies
		if (isServerEmbedded())
		{
			return new ServerBase();
		}

		//
		// Lookup for Server EJB
		try
		{
			final Server server = (Server)lookup(Server.JNDI_NAME);
			return server;
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "", ex);
			m_iContext = null; // reset cached context
			throw new AdempiereException(ex);
		}
	}

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
		else if (isServerEmbedded())
		{
			return "embedded";
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
	 * Set DB Port
	 * 
	 * @param db_portString db port as String
	 */
	void setDbPort(String db_portString)
	{
		try
		{
			if (db_portString == null || db_portString.length() == 0)
				;
			else
				setDbPort(Integer.parseInt(db_portString));
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "Error parsing db port: " + db_portString, e);
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
	 * Set Connection Profile
	 *
	 * @param connectionProfile connection profile
	 * @deprecated
	 */
	@Deprecated
	void setConnectionProfile(ValueNamePair connectionProfile)
	{
		if (connectionProfile != null)
			setConnectionProfile(PROFILE_LAN);
	}	// setConnectionProfile

	/**
	 * Set Connection Profile
	 *
	 * @param connectionProfile connection profile
	 * @deprecated
	 */
	@Deprecated
	public void setConnectionProfile(String connectionProfile)
	{
	}	// setConnectionProfile

	/**
	 * Get Connection Profile
	 *
	 * @return connection profile
	 * @deprecated
	 */
	@Deprecated
	public String getConnectionProfile()
	{
		return PROFILE_LAN;
	}	// getConnectionProfile

	/**
	 * Get Connection Profile Text
	 * 
	 * @param connectionProfile
	 * @return connection profile text
	 * @deprecated
	 */
	@Deprecated
	public String getConnectionProfileText(String connectionProfile)
	{
		for (int i = 0; i < CONNECTIONProfiles.length; i++)
		{
			if (CONNECTIONProfiles[i].getValue().equals(connectionProfile))
				return CONNECTIONProfiles[i].getName();
		}
		return CONNECTIONProfiles[0].getName();
	}	// getConnectionProfileText

	/**
	 * Get Connection Profile Text
	 *
	 * @return connection profile text
	 * @deprecated
	 */
	@Deprecated
	public String getConnectionProfileText()
	{
		return getConnectionProfileText(getConnectionProfile());
	}	// getConnectionProfileText

	/**
	 * Get Connection Profile
	 *
	 * @return connection profile
	 * @deprecated
	 */
	@Deprecated
	public ValueNamePair getConnectionProfilePair()
	{
		for (int i = 0; i < CONNECTIONProfiles.length; i++)
		{
			if (CONNECTIONProfiles[i].getValue().equals(getConnectionProfile()))
				return CONNECTIONProfiles[i];
		}
		return CONNECTIONProfiles[0];
	}	// getConnectionProfilePair

	/**
	 * Is DB via Firewall
	 * 
	 * @return true if via firewall
	 */
	public boolean isViaFirewall()
	{
		return attrs.isViaFirewall();
	}

	/**
	 * Method setViaFirewall
	 * 
	 * @param viaFirewall boolean
	 */
	void setViaFirewall(boolean viaFirewall)
	{
		if (attrs.isViaFirewall() == viaFirewall)
		{
			return;
		}
		attrs.setViaFirewall(viaFirewall);
		closeDataSource();
	}

	/**
	 * Method setViaFirewall
	 * 
	 * @param viaFirewallString String
	 */
	void setViaFirewall(String viaFirewallString)
	{
		try
		{
			setViaFirewall(Boolean.valueOf(viaFirewallString).booleanValue());
		}
		catch (Exception e)
		{
			log.severe(e.toString());
		}
	}

	/**
	 * Method getFwHost
	 * 
	 * @return String
	 */
	public String getFwHost()
	{
		return attrs.getFwHost();
	}

	/**
	 * Method setFwHost
	 * 
	 * @param fw_host String
	 */
	void setFwHost(String fw_host)
	{
		if (Check.equals(attrs.getFwHost(), fw_host))
		{
			return;
		}
		attrs.setFwHost(fw_host);
		closeDataSource();
	}

	/**
	 * Get Firewall port
	 * 
	 * @return firewall port
	 */
	public int getFwPort()
	{
		return attrs.getFwPort();
	}

	/**
	 * Set Firewall port
	 * 
	 * @param fw_port firewall port
	 */
	void setFwPort(int fw_port)
	{
		if (attrs.getFwPort() == fw_port)
		{
			return;
		}
		attrs.setFwPort(fw_port);
		closeDataSource();
	}

	public void setFwPort(final String fwPortStr)
	{
		try
		{
			if (fwPortStr == null || fwPortStr.length() == 0)
			{
				;
			}
			else
			{
				setFwPort(Integer.parseInt(fwPortStr));
			}
		}
		catch (final Exception e)
		{
			log.log(Level.WARNING, "Failed parsing FW port: " + fwPortStr, e);
		}
	}

	/**
	 * Is it a bequeath connection
	 * 
	 * @return true if bequeath connection
	 */
	public boolean isBequeath()
	{
		return attrs.isBequeath();
	}

	/**
	 * Set Bequeath
	 * 
	 * @param bequeath bequeath connection
	 */
	void setBequeath(boolean bequeath)
	{
		if (attrs.isBequeath() == bequeath)
		{
			return;
		}
		attrs.setBequeath(bequeath);
		closeDataSource();
	}

	/**
	 * Set Bequeath
	 * 
	 * @param bequeathString bequeath connection as String (true/false)
	 */
	void setBequeath(String bequeathString)
	{
		try
		{
			setBequeath(Boolean.valueOf(bequeathString).booleanValue());
		}
		catch (Exception e)
		{
			log.severe(e.toString());
		}
	}	// setBequeath

	/**
	 * Get Database Type
	 * 
	 * @return database type
	 */
	public String getType()
	{
		return attrs.getDbType();
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
		// Oracle
		if (isOracle())
		{
			if (getDbPort() != DB_Oracle.DEFAULT_PORT)
				setDbPort(DB_Oracle.DEFAULT_PORT);
			setFwPort(DB_Oracle.DEFAULT_CM_PORT);
		}
		else
		{
			setBequeath(false);
			setViaFirewall(false);
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
	 * Is Oracle DB
	 * 
	 * @return true if Oracle
	 */
	public boolean isOracle()
	{
		return Database.DB_ORACLE.equals(attrs.getDbType());
	} 	// isOracle

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
	// NOTE: this method is SUPPOSED to not contact the database server or do any expensive opperations.
	// It shall just return a status flag.
	// Before changing this, check the caller, and mainly check in this class, because there are some methods which depends on this approach.
	public final boolean isDatabaseOK()
	{
		return m_okDB;
	} 	// isDatabaseOK

	/**************************************************************************
	 * Create DB Connection
	 * 
	 * @return data source != null
	 */
	public boolean setDataSource()
	{
		final DataSource dataSource = getDatabase().getDataSource(this);
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
		m_okDB = false;
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
				log.log(Level.SEVERE, "Failed retrieving database informations", e);
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
	 * Used also for Instanciation
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
	 */
	private void setAttributes(final String attributes)
	{
		try
		{
			final CConnectionAttributes connectionString = CConnectionAttributes.of(attributes);
			setName(connectionString.getName());
			setAppsHost(connectionString.getAppsHost());
			setAppsPort(connectionString.getAppsPort());
			//
			setType(connectionString.getDbType());
			setDbHost(connectionString.getDbHost());
			setDbPort(connectionString.getDbPort());
			setDbName(connectionString.getDbName());
			//
			setBequeath(connectionString.isBequeath());
			setViaFirewall(connectionString.isViaFirewall());
			setFwHost(connectionString.getFwHost());
			setFwPort(connectionString.getFwPort());
			//
			setDbUid(connectionString.getDbUid());
			setDbPwd(connectionString.getDbPwd());
			//
		}
		catch (Exception e)
		{
			log.severe(attributes + " - " + e.toString());
		}
	}	// setAttributes

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
	 * Create Connection - no not close.
	 * Sets m_dbException
	 * 
	 * @param autoCommit true if autocommit connection
	 * @param transactionIsolation Connection transaction level
	 * @return Connection
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
			m_okDB = true;

			connOk = true;
			return conn;
		}
		catch (UnsatisfiedLinkError ule)
		{
			m_okDB = false;
			final String msg = "" + ule.getLocalizedMessage() + " -> Did you set the LD_LIBRARY_PATH ? - " + getConnectionURL();
			throw new DBNoConnectionException(msg, ule);
		}
		catch (Exception ex)
		{
			m_okDB = false;
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

	private InitialContext m_iContext = null;
	private Hashtable<String, String> m_env = null;

	/**
	 * Get Application Server Initial Context
	 * 
	 * @param useCache if true, use existing cache
	 * @return Initial Context or null
	 */
	public InitialContext getInitialContext(boolean useCache)
	{
		if (useCache && m_iContext != null)
			return m_iContext;

		// Set Environment
		if (m_env == null || !useCache)
		{
			SecurityPrincipal sp = (SecurityPrincipal)Env.getCtx().get(SECURITY_PRINCIPAL);
			String principal = sp != null ? sp.principal : null;
			String credential = sp != null ? sp.credential : null;
			m_env = getInitialEnvironment(getAppsHost(), getAppsPort(), false,
					principal, credential);
		}
		String connect = m_env.get(Context.PROVIDER_URL);
		Env.setContext(Env.getCtx(), Context.PROVIDER_URL, connect);

		// Get Context
		m_iContext = null;
		try
		{
			m_iContext = new InitialContext(m_env);
		}
		catch (Exception ex)
		{
			m_okApps = false;
			m_appsException = ex;
			if (connect == null)
				connect = m_env.get(Context.PROVIDER_URL);
			log.severe(connect
					+ "\n - " + ex.toString()
					+ "\n - " + m_env);
			if (CLogMgt.isLevelFinest())
				ex.printStackTrace();
		}
		return m_iContext;
	}	// getInitialContext

	/**
	 * Get Initial Environment
	 * 
	 * @param AppsHost host
	 * @param AppsPort port
	 * @param RMIoverHTTP ignore, retained for backward compatibility
	 * @param principal
	 * @param credential
	 * @return environment
	 */
	private Hashtable<String, String> getInitialEnvironment(String AppsHost, int AppsPort,
			boolean RMIoverHTTP, String principal, String credential)
	{
		return ASFactory.getApplicationServer()
				.getInitialContextEnvironment(AppsHost, AppsPort, principal, credential);
	}	// getInitialEnvironment

	/**
	 * Query Application Server Status.
	 * update okApps
	 * 
	 * @return true ik OK
	 */
	private boolean queryAppsServerInfo()
	{
		log.finer(getAppsHost());
		long start = System.currentTimeMillis();
		m_okApps = false;
		m_queryAppsServer = true;
		m_appsException = null;

		// Carlos Ruiz - globalqss - speed up when jnp://MyAppsServer:1099 is set
		if (attrs.isNoAppsHost())
		{
			log.warning("Application server " + getAppsHost() + " ignored");
			return m_okApps; // false
		}

		try
		{
			Status status = (Status)lookup(Status.JNDI_NAME);
			//
			updateInfoFromServer(status);
			//
			m_okApps = true;
		}
		catch (CommunicationException ce)	// not a "real" error
		{
			m_appsException = ce;
			String connect = m_env.get(Context.PROVIDER_URL);
			if (connect == null || connect.trim().length() == 0)
				connect = getAppsHost() + ":" + getAppsPort();
			log.warning(connect
					+ "\n - " + ce.toString()
					+ "\n - " + m_env);
			ce.printStackTrace();
		}
		catch (Exception e)
		{
			m_appsException = e;
			String connect = m_env.get(Context.PROVIDER_URL);
			if (connect == null || connect.trim().length() == 0)
				connect = getAppsHost() + ":" + getAppsPort();
			log.warning(connect
					+ "\n - " + e.toString()
					+ "\n - " + m_env);
			e.printStackTrace();
		}
		log.fine("Success=" + m_okApps + " - " + (System.currentTimeMillis() - start) + "ms");
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
	private void updateInfoFromServer(Status svr) throws Exception
	{
		if (svr == null)
			throw new IllegalArgumentException("AppsServer was NULL");

		setType(svr.getDbType());
		setDbHost(svr.getDbHost());
		setDbPort(svr.getDbPort());
		setDbName(svr.getDbName());
		setDbUid(svr.getDbUid());
		setDbPwd(svr.getDbPwd());
		setBequeath(false);
		//
		setFwHost(svr.getFwHost());
		setFwPort(svr.getFwPort());
		if (getFwHost().length() == 0)
			setViaFirewall(false);
		m_version = svr.getDateVersion();
		log.config("Server=" + getDbHost() + ", DB=" + getDbName());
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
		return Boolean.getBoolean(SERVER_EMBEDDED);
	}

	public void setAppServerCredential(String principal, String credential)
	{
		SecurityPrincipal sp = new SecurityPrincipal();
		sp.principal = principal;
		sp.credential = credential;
		Env.getCtx().put(SECURITY_PRINCIPAL, sp);
		m_iContext = null;
		m_env = null;
		m_server = null; // reset cached Server EJB
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		CConnection c = (CConnection)super.clone();
		return c;
	}

	private Object lookup(final String jndiName) throws NamingException
	{
		final InitialContext ctx = getInitialContext(Ini.isClient());

		//
		// First check components namespace
		if (useComponentNamespace)
		{
			try
			{
				return ctx.lookup(COMPONENT_NS + "/" + jndiName);
			}
			catch (NamingException e)
			{
				// Reset the flag, so next time it won't be checked.
				useComponentNamespace = false;
				log.log(Level.WARNING, "Component name space not available", e.getLocalizedMessage());
			}
		}

		//
		// Check global JNDI lookup
		return ctx.lookup(jndiName);
	}
}	// CConnection
