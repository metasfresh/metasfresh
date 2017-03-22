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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.sql.DataSource;

import org.adempiere.exceptions.DBException;
import org.compiere.dbPort.Convert;

/**
 * Interface for Adempiere Databases
 *
 * @author Jorg Janke
 * @version $Id: AdempiereDatabase.java,v 1.5 2006/09/22 23:35:19 jjanke Exp $
 */
public interface AdempiereDatabase
{
	/**
	 * Get Database Name
	 *
	 * @return database short name
	 */
	public String getName();

	/**
	 * Get Database Description
	 *
	 * @return database long name and version
	 */
	public String getDescription();

	/**
	 * Get and register Database Driver
	 *
	 * @return Driver
	 * @throws DBException
	 */
	public Driver getDriver();

	/**
	 * Get Standard JDBC Port
	 *
	 * @return standard port
	 */
	public int getStandardPort();

	/**
	 * Get Database Connection String
	 *
	 * @param connection Connection Descriptor
	 * @return connection String
	 */
	public String getConnectionURL(CConnection connection);

	/**
	 * Get Connection URL
	 *
	 * @param dbHost db Host
	 * @param dbPort db Port
	 * @param dbName db Name
	 * @param userName user name
	 * @return url
	 */
	public String getConnectionURL(String dbHost, int dbPort, String dbName, String userName);

	/**
	 * Get JDBC Catalog
	 *
	 * @return catalog
	 */
	public String getCatalog();

	/**
	 * Get JDBC Schema
	 *
	 * @return schema
	 */
	public String getSchema();

	/**
	 * Supports BLOB
	 *
	 * @return true if BLOB is supported
	 */
	public boolean supportsBLOB();

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	public String toString();

	/**************************************************************************
	 * Convert an individual Oracle Style statements to target database statement syntax
	 *
	 * @param oraStatement oracle statement
	 * @return converted Statement
	 */
	public String convertStatement(String oraStatement);

	/**
	 * Check if DBMS support the sql statement
	 *
	 * @sql SQL statement
	 * @return true: yes
	 */
	public boolean isSupported(String sql);

	/**
	 * Get constraint type associated with the index
	 *
	 * @conn connection
	 * @tableName table name
	 * @IXName Index name
	 * @return String[0] = 0: do not know, 1: Primary Key 2: Foreign Key
	 *         String[1] - String[n] = Constraint Name
	 */
	public String getConstraintType(Connection conn, String tableName, String IXName);

	/**
	 * Check and generate an alternative SQL
	 *
	 * @reExNo number of re-execution
	 * @msg previous execution error message
	 * @sql previous executed SQL
	 * @return String, the alternative SQL, null if no alternative
	 */
	public String getAlternativeSQL(int reExNo, String msg, String sql);

	/**
	 * Get Name of System User
	 *
	 * @return e.g. sa, system
	 */
	public String getSystemUser();

	/**
	 * Get Name of System Database
	 *
	 * @param databaseName database Name
	 * @return e.g. master or database Name
	 */
	public String getSystemDatabase(String databaseName);

	/**
	 * Create SQL TO Date String from Timestamp
	 *
	 * @param time Date to be converted
	 * @param dayOnly true if time set to 00:00:00
	 * @return date function
	 */
	public String TO_DATE(Timestamp time, boolean dayOnly);

	/**
	 * Create SQL for formatted Date, Number
	 *
	 * @param columnName the column name in the SQL
	 * @param displayType Display Type
	 *
	 * @return TRIM(TO_CHAR(columnName,'999G999G999G990D00','NLS_NUMERIC_CHARACTERS='',.'''))
	 *         or TRIM(TO_CHAR(columnName,'TM9')) depending on DisplayType and Language
	 * @see org.compiere.util.DisplayType
	 * @see org.compiere.util.Env
	 *
	 **/
	/**
	 * @see #TO_CHAR(String, int, String)
	 */
	public String TO_CHAR(String columnName, int displayType);

	/**
	 * Create SQL for formatted Date, Number.
	 *
	 * @param columnName the column name in the SQL
	 * @param displayType Display Type
	 * @param AD_Language 6 character language setting (from Env.LANG_*)
	 * @param formatPattern formatting pattern to be used ( {@link DecimalFormat} pattern, {@link SimpleDateFormat} pattern etc). In case the formatting pattern is not supported or is not valid, the
	 *            implementation method can ignore it silently.
	 *
	 * @return SQL code
	 */
	String TO_CHAR(String columnName, int displayType, String formatPattern);

	/**
	 * Return number as string for INSERT statements with correct precision
	 *
	 * @param number number
	 * @param displayType display Type
	 * @return number as string
	 */
	public String TO_NUMBER(BigDecimal number, int displayType);

	/**
	 * Return next sequence this Sequence
	 *
	 * @param Sequence Name
	 */
	public int getNextID(String Name);

	/**
	 * Creates SQL for retrieving next sequence value.
	 *
	 * @param sequenceName
	 * @return
	 */
	public String TO_SEQUENCE_NEXTVAL(final String sequenceName);

	/**
	 * Create Native Sequence
	 *
	 * @param Sequence Name
	 * @return true if sequence was created or updated
	 */
	public boolean createSequence(String name, int increment, int minvalue, int maxvalue, int start, String trxName);

	/**
	 * Rename the native sequence
	 *
	 * @param dbSequenceNameOld sequence name
	 * @param dbSequenceNameNew new sequence name
	 */
	public void renameSequence(String dbSequenceNameOld, String dbSequenceNameNew);

	/**
	 * Get Cached Connection on Server
	 *
	 * @param connection info
	 * @param autoCommit true if autocommit connection
	 * @param transactionIsolation Connection transaction level
	 * @return connection or null
	 * @throws Exception
	 */
	public Connection getCachedConnection(CConnection connection, boolean autoCommit, int transactionIsolation) throws Exception;

	/**
	 * Get Driver Connection
	 *
	 * @param dbUrl URL
	 * @param dbUid user
	 * @param dbPwd password
	 * @return connection
	 * @throws SQLException
	 */
	public Connection getDriverConnection(String dbUrl, String dbUid, String dbPwd) throws SQLException;

	/**
	 * Create DataSource
	 *
	 * @param connection connection
	 * @return data dource
	 */
	public DataSource getDataSource(CConnection connection);

	/**
	 * Get Status
	 *
	 * @return status info or null if no local datasource available
	 */
	public String getStatus();

	/**
	 * Close database.
	 * Shall never fail.
	 */
	public void close();

	public Convert getConvert();

	/**
	 * @return true if jdbc driver support statement timeout
	 */
	public boolean isQueryTimeoutSupported();

	/**
	 * Get Data Type
	 *
	 * @param DisplayType display type
	 * @return data type
	 */
	// public String getDataType (int displayType, int precision,
	// boolean defaultValue)

	/**
	 * Default sql use to test whether a connection is still valid
	 */
	public final static String DEFAULT_CONN_TEST_SQL = "SELECT Version FROM AD_System";

	/**
	 * Is the database have sql extension that return a subset of the query result
	 *
	 * @return boolean
	 */
	public boolean isPagingSupported();

	/**
	 * modify sql to return a subset of the query result
	 *
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public String addPagingSQL(String sql, int start, int end);

	/**
	 * Get SQL DataType
	 *
	 * @param displayType AD_Reference_ID
	 * @param columnName name
	 * @param fieldLength length
	 * @return SQL Data Type in Oracle Notation
	 */
	String getSQLDataType(int displayType, String columnName, int fieldLength);

	/**
	 *
	 * @param connection
	 * @param throwDBException if the connection is closed or an {@link SQLException} is caught, then this parameter decides if an {@link DBException} shall be thrown. Set to <code>false</code> if you
	 *            are already exception handling and just want to use this method to get whatever info you can (and not to throw yet another exception).
	 * @return
	 */
	String getConnectionBackendId(Connection connection, boolean throwDBException);

	/**
	 * @param tableName
	 * @return SQL code for getting the ROW ID of a given table.
	 */
	String getRowIdSql(final String tableName);
}   // AdempiereDatabase
