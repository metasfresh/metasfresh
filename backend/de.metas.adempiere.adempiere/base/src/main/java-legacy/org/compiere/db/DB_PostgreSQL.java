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
 * Portions created by Victor Perez are Copyright (C) 1999-2005 e-Evolution,S.C
 * Contributor(s): Victor Perez *
 *****************************************************************************/
package org.compiere.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.Duration;
import java.util.List;

import javax.sql.DataSource;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBConnectionAcquireTimeoutException;
import org.adempiere.exceptions.DBException;
import org.adempiere.exceptions.DBNoConnectionException;
import org.compiere.dbPort.Convert;
import org.compiere.dbPort.Convert_PostgreSQL;
import org.compiere.dbPort.Convert_PostgreSQL_Native;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.resourcepool.BasicResourcePool_MetasfreshObserver;

import de.metas.connection.impl.DB_PostgreSQL_ConnectionCustomizer;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.SystemUtils;
import lombok.NonNull;

/**
 * PostgreSQL Database Port
 *
 * @author @author Jorg Janke, Victor P�rez
 * @version $Id: DB_PostgreSQL.java,v 1.23 2005/03/11 20:29:01 jjanke Exp $
 *          ---
 *          Modifications: removed static references to database connection and instead always
 *          get a new connection from database pool manager which manages all connections
 *          set rw/ro properties for the connection accordingly.
 * @author Ashley Ramdass (Posterita)
 */
public class DB_PostgreSQL implements AdempiereDatabase
{
	private static final String CONFIG_UseNativeConverter = "org.compiere.db.DB_PostgreSQL.UseNativeConverter";
	private static final String CONFIG_UseNativeConverter_DefaultValue = "true";

	private static final String CONFIG_CheckoutTimeout_SwingClient = "org.compiere.db.DB_PostgreSQL.CheckoutTimeout";

	private static final String CONFIG_UnreturnedConnectionTimeoutMillis = "db.postgresql.unreturnedConnectionTimeoutMillis";
	private static final Duration CONFIG_UnreturnedConnectionTimeoutMillis_DefaultValue = Duration.ofHours(2);

	/**
	 * Statement Converter for external use (i.e. returned by {@link #getConvert()}.
	 */
	private final Convert m_convert;

	/**
	 * Statement Converter internally (i.e. all methods from this class will use this one).
	 *
	 * @see #getInternalConverter()
	 */
	private final Convert m_convertInternal;

	/** Database driver */
	private static final Supplier<org.postgresql.Driver> driverSupplier = Suppliers.memoize(() -> {
		try
		{
			final org.postgresql.Driver driver = new org.postgresql.Driver();
			DriverManager.registerDriver(driver);
			DriverManager.setLoginTimeout(Database.CONNECTION_TIMEOUT);
			return driver;
		}
		catch (SQLException e)
		{
			throw new DBException("Failed registering postgresql database driver", e);
		}
	});

	/** Driver class */
	public static final String DRIVER = "org.postgresql.Driver";

	/** Default Port */
	public static final int DEFAULT_PORT = 5432;

	/** Data Source */
	private transient ComboPooledDataSource _dataSource = null;
	private transient volatile boolean _dataSourceInitialized = false;
	private final Object _dataSourceLock = new Object();

	/** Cached Database Name */
	private String m_dbName = null;

	// private String m_userName = null;

	/** Connection String (the last one about we were asked) */
	private String m_connectionURL;

	/** Logger */
	private static final Logger log = LogManager.getLogger(DB_PostgreSQL.class);
	private int m_maxBusyConnectionsThreshold = 0;

	/**
	 * PostgreSQL Database
	 */
	public DB_PostgreSQL()
	{
		final Convert_PostgreSQL converter = new Convert_PostgreSQL();
		final Convert_PostgreSQL_Native converterNative = new Convert_PostgreSQL_Native();

		//
		// Check and configure if we shall use or not native converter (i.e. pass-through) internally
		final String useNativeConverterStr = System.getProperty(CONFIG_UseNativeConverter, CONFIG_UseNativeConverter_DefaultValue);
		final boolean useNativeConverter = Boolean.valueOf(useNativeConverterStr);
		if (useNativeConverter)
		{
			// For external use we will return the standard PostgreSQL converter
			this.m_convert = converter;
			// For internal use we will return the native/pass-through converter
			this.m_convertInternal = converterNative;
		}
		else
		{
			// For external use we will return the native/pass-through converter
			// because statements will be converted by internal converter anyway
			this.m_convert = converterNative;
			// For internal use we will return the standard PostgreSQL converter
			this.m_convertInternal = converter;
		}

	}   // DB_PostgreSQL

	@Override
	public final Convert getConvert()
	{
		// For external methods we use the regular PostgreSQL converter
		// NOTE: method org.compiere.util.DB.convertSqlToNative(String) is rellying on this
		return m_convert;
	}

	/**
	 * Gets internal {@link Convert}er. This converter is used internally by methods from this class.
	 *
	 * @return internal converter
	 */
	private final Convert getInternalConverter()
	{
		// For all methods from this class we use the native converter, which actually does nothing
		return m_convertInternal;
	}

	/**
	 * Get Database Name
	 *
	 * @return database short name
	 */
	@Override
	public String getName()
	{
		return Database.DB_POSTGRESQL;
	}   // getName

	/**
	 * Get Database Description
	 *
	 * @return database long name and version
	 */
	@Override
	public String getDescription()
	{
		try
		{
			return getDriver().toString();
		}
		catch (Exception e)
		{
			// shall not happen
			e.printStackTrace();
		}
		return "No Driver";
	}   // getDescription

	/**
	 * Get Standard JDBC Port
	 *
	 * @return standard port
	 */
	@Override
	public int getStandardPort()
	{
		return DEFAULT_PORT;
	}   // getStandardPort

	/**
	 * Get and register Database Driver
	 *
	 * @return Driver
	 */
	@Override
	public java.sql.Driver getDriver()
	{
		return driverSupplier.get();
	}   // getDriver

	/**
	 * Get Database Connection String.
	 * Requirements:
	 * - createdb -E UNICODE compiere
	 *
	 * @param connection Connection Descriptor
	 * @return connection String
	 */
	@Override
	public String getConnectionURL(final CConnection connection)
	{
		final String dbHost = connection.getDbHost();
		final int dbPort = connection.getDbPort();
		final String dbName = connection.getDbName();
		final String dbUsername = connection.getDbUid();
		final String connectionURL = m_connectionURL = getConnectionURL(dbHost, dbPort, dbName, dbUsername);
		return connectionURL;
	}   // getConnectionString

	/**
	 * Get Connection URL
	 *
	 * @param dbHost db Host
	 * @param dbPort db Port
	 * @param dbName sb Name
	 * @param userName user name
	 * @return connection url
	 */
	@Override
	public String getConnectionURL(String dbHost, int dbPort, String dbName, String userName)
	{
		// jdbc:postgresql://hostname:portnumber/databasename?encoding=UNICODE
		final StringBuilder sb = new StringBuilder("jdbc:postgresql://")
				.append(dbHost).append(":").append(dbPort)
				.append("/").append(dbName)
				.append("?encoding=UNICODE");
		return sb.toString();
	}	// getConnectionURL

	/**
	 * Get JDBC Catalog
	 *
	 * @return catalog (database name)
	 */
	@Override
	public String getCatalog()
	{
		if (m_dbName != null)
		{
			return m_dbName;
		}
		// log.error("Database Name not set (yet) - call getConnectionURL first");
		return null;
	}	// getCatalog

	/**
	 * Get JDBC Schema
	 *
	 * @return null (->we will use the public/default schema)
	 */
	@Override
	public String getSchema()
	{
		return null;
	}	// getSchema

	/**
	 * Supports BLOB
	 *
	 * @return true if BLOB is supported
	 */
	@Override
	public boolean supportsBLOB()
	{
		return true;
	}   // supportsBLOB

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("DB_PostgreSQL[");
		sb.append(m_connectionURL);
		sb.append("\n").append(getStatus());
		sb.append("]");
		return sb.toString();
	}

	@Override
	public String getStatus()
	{
		final ComboPooledDataSource dataSource = getDataSourceOrNull();
		if (dataSource == null)
		{
			return "No datasource";
		}

		final StringBuilder sb = new StringBuilder();
		try
		{
			sb.append("# Connections: ").append(dataSource.getNumConnections());
			sb.append(" , # Busy Connections: ").append(dataSource.getNumBusyConnections()).append("/").append(m_maxBusyConnectionsThreshold);
			sb.append(" , # Idle Connections: ").append(dataSource.getNumIdleConnections());
			sb.append(" , # Orphaned Connections: ").append(dataSource.getNumUnclosedOrphanedConnections());

			if (dataSource.isDebugUnreturnedConnectionStackTraces())
			{
				try
				{
					int index = 1;
					final List<String> connectionInfos = getAquiredConnectionInfos(dataSource);
					for (final String info : connectionInfos)
					{
						sb.append("\n\t\t " + index + ": " + info);
						index++;
					}
				}
				catch (final Exception ex)
				{
					log.warn("Failed fetching connections debug info. Ignored.", ex);
				}

			}
		}
		catch (Exception e)
		{
		}
		return sb.toString();
	}	// getStatus

	/*************************************************************************
	 * Convert an individual Oracle Style statements to target database statement syntax
	 *
	 * @param oraStatement
	 * @return converted Statement
	 * @throws Exception
	 */
	@Override
	public String convertStatement(final String oraStatement)
	{
		final Convert converter = getInternalConverter();
		final List<String> retValue = converter.convert(oraStatement);

		if (retValue == null)
		// begin vpj-cd 24/06/2005 e-evolution
		{
			log.error(("DB_PostgreSQL.convertStatement - Not Converted (" + oraStatement + ") - "
					+ converter.getConversionError()));
			throw new IllegalArgumentException("DB_PostgreSQL.convertStatement - Not Converted (" + oraStatement + ") - "
					+ converter.getConversionError());
		}
		// end vpj-cd 24/06/2005 e-evolution

		// begin vpj-cd e-evolution 03/14/2005
		if (retValue.isEmpty())
		{
			return oraStatement;
		}
		// end vpj-cd e-evolution 03/14/2005

		if (retValue.size() != 1)
		// begin vpj-cd 24/06/2005 e-evolution
		{
			log.error(("DB_PostgreSQL.convertStatement - Convert Command Number=" + retValue.size()
					+ " (" + oraStatement + ") - " + converter.getConversionError()));
			throw new IllegalArgumentException("DB_PostgreSQL.convertStatement - Convert Command Number=" + retValue.size()
					+ " (" + oraStatement + ") - " + converter.getConversionError());
		}
		// end vpj-cd 24/06/2005 e-evolution

		final String pgStatement = retValue.get(0);

		// Diagnostics (show changed, but not if AD_Error)
		if (log.isDebugEnabled())
		{
			if (!oraStatement.equals(pgStatement) && pgStatement.indexOf("AD_Error") == -1)
			{
				// begin vpj-cd 24/06/2005 e-evolution
				log.debug("PostgreSQL =>" + pgStatement + "<= <" + oraStatement + ">");
			}
		}
		// end vpj-cd 24/06/2005 e-evolution

		//
		return pgStatement;
	}   // convertStatement

	/**
	 * Get Name of System User
	 *
	 * @return e.g. sa, system
	 */
	@Override
	public String getSystemUser()
	{
		return "postgres";
	}	// getSystemUser

	/**
	 * Get Name of System Database
	 *
	 * @param databaseName database Name
	 * @return e.g. master or database Name
	 */
	@Override
	public String getSystemDatabase(String databaseName)
	{
		return "template1";
	}	// getSystemDatabase

	/**
	 * Get Cached Connection
	 *
	 * @param connection connection
	 * @param autoCommit auto commit
	 * @param transactionIsolation trx isolation
	 * @return Connection
	 * @throws Exception
	 */
	@Override
	public Connection getCachedConnection(final CConnection connection, final boolean autoCommit, final int transactionIsolation) throws Exception
	{
		Connection conn = null;
		boolean connOk = false;
		try
		{
			final ComboPooledDataSource m_ds = getDataSource(connection);
			if (m_ds == null)
			{
				throw new DBNoConnectionException("Data source could not be retrieved for " + connection);
			}

			conn = m_ds.getConnection();
			conn.setAutoCommit(autoCommit);
			conn.setTransactionIsolation(transactionIsolation);

			final int numConnections = m_ds.getNumBusyConnections();
			final int maxBusyconnectionsThreshold = this.m_maxBusyConnectionsThreshold;
			if (numConnections >= maxBusyconnectionsThreshold && maxBusyconnectionsThreshold > 0)
			{
				// metas-ts: i think running the finalizer won't be a big help, but anyways, exhausting the connection pool is usually an issue
				// suggestions to consider:
				// * allow it to be configured for certain scenarios
				// * only log, but don't even try the finalizing
				final String statusBefore = getStatus();

				// hengsin: make a best effort to reclaim leak connection
				Runtime.getRuntime().runFinalization();

				final String statusAfter = getStatus();

				final Thread currentThread = Thread.currentThread();
				log.warn(numConnections + " busy connections found (>= " + maxBusyconnectionsThreshold + "). Running finalizations..."
						+ "\n # Thread: " + currentThread.getName() + " (ID=" + currentThread.getId() + ")"
						+ "\n # Status(initial): " + statusBefore
						+ "\n # Status(after finalizations started): " + statusAfter);
			}

			connOk = true;
			return conn;
		}
		catch (final SQLException sqlException)
		{
			final Throwable cause = sqlException.getCause();
			if (cause instanceof com.mchange.v2.resourcepool.TimeoutException)
			{
				throw new DBConnectionAcquireTimeoutException(sqlException);
			}

			throw sqlException;
		}
		finally
		{
			if (!connOk)
			{
				DB.close(conn);
			}
		}
	}	// getCachedConnection

	/**
	 * Gets current {@link DataSource}.
	 *
	 * NOTE: this method is not initializing the {@link DataSource}.
	 *
	 * @return current data source our null
	 */
	private final ComboPooledDataSource getDataSourceOrNull()
	{
		if (!_dataSourceInitialized)
		{
			synchronized (_dataSourceLock)
			{
				return _dataSource;
			}
		}
		return _dataSource;
	}

	/**
	 * Create DataSource (Client)
	 *
	 * @param connection connection
	 * @return data source or null if database could not be initialized
	 */
	@Override
	public ComboPooledDataSource getDataSource(final CConnection connection)
	{
		if (!_dataSourceInitialized)
		{
			synchronized (_dataSourceLock)
			{
				if (!_dataSourceInitialized)
				{
					final ComboPooledDataSource dataSource = this._dataSource = createDataSource(connection);
					log.info("Data source: {}", dataSource.toString(/* show_config */true));

					final int maxBusyConnectionsThreshold = this.m_maxBusyConnectionsThreshold = (int)(dataSource.getMaxPoolSize() * 0.80);
					log.info("MaxBusyConnectionsThreshold={}", maxBusyConnectionsThreshold);

					_dataSourceInitialized = true;
				}
			}
		}
		return _dataSource;
	}

	/**
	 * Creates {@link DataSource} based on {@link CConnection} properties.
	 *
	 * NOTE: this method never throws exception but just logs it.
	 *
	 * @param connection
	 * @return {@link DataSource}
	 */
	@NonNull
	private ComboPooledDataSource createDataSource(final CConnection connection)
	{
		try
		{
			System.setProperty("com.mchange.v2.log.MLog", com.mchange.v2.log.slf4j.Slf4jMLog.class.getName());
			// System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "ALL");
			final ComboPooledDataSource cpds = new ComboPooledDataSource();
			cpds.setDataSourceName("metasfreshDS");
			cpds.setDriverClass(DRIVER);
			// loads the jdbc driver
			cpds.setJdbcUrl(getConnectionURL(connection));
			cpds.setUser(connection.getDbUid());
			cpds.setPassword(connection.getDbPwd());
			cpds.setPreferredTestQuery(DEFAULT_CONN_TEST_SQL);
			cpds.setIdleConnectionTestPeriod(1200);
			// cpds.setTestConnectionOnCheckin(true);
			// cpds.setTestConnectionOnCheckout(true);
			cpds.setAcquireRetryAttempts(2);

			if (Ini.isSwingClient())
			{
				// Set checkout timeout to avoid forever locking when trying to connect to a not existing host.
				cpds.setCheckoutTimeout(SystemUtils.getSystemProperty(CONFIG_CheckoutTimeout_SwingClient, 20 * 1000));

				cpds.setInitialPoolSize(1);
				cpds.setMinPoolSize(1);
				cpds.setMaxPoolSize(20);
				cpds.setMaxIdleTimeExcessConnections(1200);
				cpds.setMaxIdleTime(900);
			}
			else
			{
				// these are set in c3p0.properties files
				// cpds.setInitialPoolSize(10);
				// cpds.setMinPoolSize(5);
				// cpds.setMaxPoolSize(150);
				cpds.setMaxIdleTimeExcessConnections(1200);
				cpds.setMaxIdleTime(1200);
			}

			//
			// Timeout unreturned connections
			// i.e. kill them and get them back to the pool.
			final Duration unreturnedConnectionTimeout = getUnreturnedConnectionTimeout();
			if (unreturnedConnectionTimeout.getSeconds() > 0)
			{
				// IMPORTANT: unreturnedConnectionTimeout is in seconds, see https://www.mchange.com/projects/c3p0/#unreturnedConnectionTimeout
				cpds.setUnreturnedConnectionTimeout((int)unreturnedConnectionTimeout.getSeconds());
				cpds.setDebugUnreturnedConnectionStackTraces(true);
			}

			// 04006: add a customizer to set the log level for message that are send to the client
			// background: if there are too many messages sent (e.g. from a verbose and long-running DB function)
			// then the whole JVM might suffer an OutOfMemoryError
			cpds.setConnectionCustomizerClassName(DB_PostgreSQL_ConnectionCustomizer.class.getName());

			return cpds;
		}
		catch (Exception ex)
		{
			throw new DBNoConnectionException("Could not initialise C3P0 Datasource", ex);
		}
	}

	private static Duration getUnreturnedConnectionTimeout()
	{
		return Duration.ofMillis(SystemUtils.getSystemProperty(
				CONFIG_UnreturnedConnectionTimeoutMillis,
				(int)CONFIG_UnreturnedConnectionTimeoutMillis_DefaultValue.toMillis()));
	}

	private final void closeDataSource()
	{
		synchronized (_dataSourceLock)
		{
			if (_dataSource != null)
			{
				try
				{
					_dataSource.close();
					log.info("Datasource closed: {}", _dataSource);
				}
				catch (Exception e)
				{
					// NOTE: don't use logger because it might involve database connection
					e.printStackTrace();
				}
				_dataSource = null;
				_dataSourceInitialized = false;
			}
		}
	}

	/**
	 * Get Driver Connection
	 *
	 * @param dbUrl URL
	 * @param dbUid user
	 * @param dbPwd password
	 * @return connection
	 * @throws SQLException
	 */
	@Override
	public Connection getDriverConnection(String dbUrl, String dbUid, String dbPwd)
			throws SQLException
	{
		getDriver();
		return DriverManager.getConnection(dbUrl, dbUid, dbPwd);
	}	// getDriverConnection

	/**
	 * Close
	 */
	@Override
	public void close()
	{
		log.info(toString());

		closeDataSource();
	}	// close

	/**
	 * Check and generate an alternative SQL
	 *
	 * @reExNo number of re-execution
	 * @msg previous execution error message
	 * @sql previous executed SQL
	 * @return String, the alternative SQL, null if no alternative
	 */
	@Override
	public String getAlternativeSQL(int reExNo, String msg, String sql)
	{
		return null; // do not do re-execution of alternative SQL
	}

	/**
	 * Get constraint type associated with the index
	 *
	 * @tableName table name
	 * @IXName Index name
	 * @return String[0] = 0: do not know, 1: Primary Key 2: Foreign Key
	 *         String[1] - String[n] = Constraint Name
	 */
	@Override
	public String getConstraintType(Connection conn, String tableName, String IXName)
	{
		if (IXName == null || IXName.length() == 0)
		{
			return "0";
		}
		if (IXName.toUpperCase().endsWith("_KEY"))
		{
			return "1" + IXName;
		}
		else
		{
			return "0";
			// jz temp, modify later from user.constraints
		}
	}

	/**
	 * Check if DBMS support the sql statement
	 *
	 * @sql SQL statement
	 * @return true: yes
	 */
	@Override
	public boolean isSupported(String sql)
	{
		return true;
		// jz temp, modify later
	}

	/**
	 * Dump table lock info to console for current transaction
	 *
	 * @param conn
	 */
	public static void dumpLocks(Connection conn)
	{
		Statement stmt = null;
		try
		{
			String sql = "select pg_class.relname,pg_locks.* from pg_class,pg_locks where pg_class.relfilenode=pg_locks.relation order by 1";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int cnt = rs.getMetaData().getColumnCount();
			System.out.println();
			while (rs.next())
			{
				for (int i = 0; i < cnt; i++)
				{
					Object value = rs.getObject(i + 1);
					if (i > 0)
					{
						System.out.print(", ");
					}
					System.out.print(value != null ? value.toString() : "");
				}
				System.out.println();
			}
			System.out.println();
		}
		catch (Exception e)
		{

		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close();
				}
			}
			catch (Exception e)
			{
			}
		}
	}

	@Override
	public int getNextID(@NonNull final String sequenceName)
	{
		Check.assumeNotEmpty(sequenceName, "sequenceName not empty");
		int m_sequence_id = DB.getSQLValue(null, "SELECT nextval('" + sequenceName.toLowerCase() + "')");
		return m_sequence_id;
	}

	@Override
	public String TO_SEQUENCE_NEXTVAL(final String sequenceName)
	{
		Check.assumeNotEmpty(sequenceName, "sequenceName not empty");
		final StringBuilder sb = new StringBuilder();
		sb.append("nextval(").append(DB.TO_STRING(sequenceName.toLowerCase())).append(")");

		return sb.toString();
	}

	private final boolean hasSequence(final String dbSequenceName, final String trxName)
	{
		final int cnt = DB.getSQLValueEx(trxName, "SELECT COUNT(*) FROM pg_class WHERE UPPER(relname)=? AND relkind='S'", dbSequenceName.toUpperCase());
		return cnt > 0;
	}

	@Override
	public boolean createSequence(String dbSequenceName, int increment, int minvalue, int maxvalue, int start, String trxName)
	{
		Check.assumeNotEmpty(dbSequenceName, "dbSequenceName not empty");

		final int no;

		//
		// New Sequence
		if (!hasSequence(dbSequenceName, trxName))
		{
			no = DB.executeUpdate("CREATE SEQUENCE " + dbSequenceName.toUpperCase()
					+ " INCREMENT " + increment
					+ " MINVALUE " + minvalue
					+ " MAXVALUE " + maxvalue
					+ " START " + start, trxName);
		}
		//
		// Already existing sequence => ALTER
		else
		{
			no = DB.executeUpdate("ALTER SEQUENCE " + dbSequenceName.toUpperCase()
					+ " INCREMENT " + increment
					+ " MINVALUE " + minvalue
					+ " MAXVALUE " + maxvalue
					+ " RESTART " + start, trxName);
		}
		if (no == -1)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public void renameSequence(final String dbSequenceNameOld, final String dbSequenceNameNew)
	{
		Check.assumeNotEmpty(dbSequenceNameOld, "dbSequenceNameOld not empty");
		Check.assumeNotEmpty(dbSequenceNameNew, "dbSequenceNameNew not empty");

		final String trxName = ITrx.TRXNAME_ThreadInherited;

		// If there is no such sequence then do nothing.
		// This is ok because the table name could be a view
		if (!hasSequence(dbSequenceNameOld, trxName))
		{
			log.info("Cannot rename sequence " + dbSequenceNameOld + " to " + dbSequenceNameNew + " because the sequence does not exist. Ignore.");
			return;
		}

		// NOTE: we are not using parameters because this command will be logged in migration scripts.
		DB.executeUpdateEx("ALTER SEQUENCE " + dbSequenceNameOld + " RENAME TO " + dbSequenceNameNew, trxName);
	}

	@Override
	public boolean isQueryTimeoutSupported()
	{
		return false;
	}

	/**
	 * Implemented using the limit and offset feature. use 1 base index for start and end parameter
	 *
	 * @param sql
	 * @param start
	 * @param end
	 */
	@Override
	public String addPagingSQL(String sql, int start, int end)
	{
		final Convert converter = getInternalConverter();
		final String newSql = sql
				+ "\n " + converter.markNative("LIMIT") + " " + (end - start + 1)
				+ "  " + converter.markNative("OFFSET") + " " + (start - 1);
		return newSql;
	}

	@Override
	public boolean isPagingSupported()
	{
		return true;
	}

	@Override
	public String getSQLDataType(int displayType, String columnName, int fieldLength)
	{
		if (columnName.equals("EntityType")
				|| columnName.equals("AD_Language"))
		{
			return "VARCHAR(" + fieldLength + ")";
		}
		// ID
		if (DisplayType.isID(displayType))
		{
			if (displayType == DisplayType.Image 	// FIXTHIS
					&& columnName.equals("BinaryData"))
			{
				return "BYTEA";
			}
			else if (columnName.endsWith("_ID")
					|| columnName.endsWith("tedBy")
					|| columnName.endsWith("_Acct")
					|| "AD_Key".equals(columnName) // HARDCODED for AD_Ref_Table.AD_Key
					|| "AD_Display".equals(columnName) // HARDCODED for AD_Ref_Table.AD_Display
			)
			{
				return "NUMERIC(10)";
			}
			else if (fieldLength < 4)
			{
				return "CHAR(" + fieldLength + ")";
			}
			else
			{
				// EntityType, AD_Language fallback
				return "VARCHAR(" + fieldLength + ")";
			}
		}
		//
		if (displayType == DisplayType.Integer)
		{
			return "NUMERIC(10)";
		}
		if (displayType == DisplayType.DateTime)
		{
			return "TIMESTAMP WITH TIME ZONE";
		}
		if (DisplayType.isDate(displayType))
		{
			return "TIMESTAMP WITHOUT TIME ZONE";
		}
		if (DisplayType.isNumeric(displayType))
		{
			return "NUMERIC";
		}
		if (displayType == DisplayType.Binary)
		{
			return "BYTEA";
		}
		if (displayType == DisplayType.TextLong
				|| (displayType == DisplayType.Text && fieldLength >= 4000))
		{
			return "TEXT";
		}
		if (displayType == DisplayType.YesNo)
		{
			return "CHAR(1)";
		}
		if (displayType == DisplayType.List)
		{
			if (fieldLength == 1)
			{
				return "CHAR(" + fieldLength + ")";
			}
			else
			{
				return "VARCHAR(" + fieldLength + ")";
			}
		}
		if (displayType == DisplayType.Color)   // this condition is never reached - filtered above in isID
		{
			if (columnName.endsWith("_ID"))
			{
				return "NUMERIC(10)";
			}
			else
			{
				return "CHAR(" + fieldLength + ")";
			}
		}
		if (displayType == DisplayType.Button)
		{
			if (columnName.endsWith("_ID"))
			{
				return "NUMERIC(10)";
			}
			else
			{
				return "CHAR(" + fieldLength + ")";
			}
		}
		if (!DisplayType.isText(displayType))
		{
			log.error("Unhandled Data Type = " + displayType);
		}

		return "VARCHAR(" + fieldLength + ")";
	}	// getSQLDataType

	@Override
	public String getConnectionBackendId(final Connection connection, final boolean throwDBException)
	{
		Check.assumeNotNull(connection, "connection not null");

		final int pgBackendPID;

		final String sql = "{ ? = call pg_backend_pid() }";
		CallableStatement stmt = null;
		try
		{
			if (connection.isClosed() && !throwDBException)
			{
				return "<connection is closed, not trying to get backend PID>";
			}

			stmt = connection.prepareCall(sql);
			stmt.registerOutParameter(1, Types.INTEGER);
			stmt.execute();
			pgBackendPID = stmt.getInt(1);
		}
		catch (SQLException e)
		{
			if (throwDBException)
			{
				throw new DBException(e, sql);
			}
			else
			{
				return "<caught " + e + " with message " + e.getMessage() + " when trying to get backend PID>";
			}
		}
		finally
		{
			DB.close(stmt);
			stmt = null;
		}

		return String.valueOf(pgBackendPID);
	}

	private static List<String> getAquiredConnectionInfos(final ComboPooledDataSource dataSource) throws Exception
	{
		final List<String> infos = BasicResourcePool_MetasfreshObserver.getAquiredConnectionInfos(dataSource);
		return infos != null ? infos : ImmutableList.of();
	}
}   // DB_PostgreSQL
