/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
package org.compiere.db;

import org.adempiere.exceptions.DBException;
import org.compiere.dbPort.Convert;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

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
	String getName();

	/**
	 * Get Database Description
	 *
	 * @return database long name and version
	 */
	String getDescription();

	/**
	 * Get and register Database Driver
	 *
	 * @return Driver
	 */
	Driver getDriver();

	/**
	 * Get Standard JDBC Port
	 *
	 * @return standard port
	 */
	int getStandardPort();

	/**
	 * Get Database Connection String
	 *
	 * @param connection Connection Descriptor
	 * @return connection String
	 */
	String getConnectionURL(CConnection connection);

	/**
	 * Get Connection URL
	 *
	 * @param dbHost db Host
	 * @param dbPort db Port
	 * @param dbName db Name
	 * @param userName user name
	 * @return url
	 */
	String getConnectionURL(String dbHost, int dbPort, String dbName, String userName);

	/**
	 * Get JDBC Catalog
	 *
	 * @return catalog
	 */
	String getCatalog();

	/**
	 * Get JDBC Schema
	 *
	 * @return schema
	 */
	String getSchema();

	/**
	 * Supports BLOB
	 *
	 * @return true if BLOB is supported
	 */
	boolean supportsBLOB();

	/**
	 * String Representation
	 *
	 * @return info
	 */
	@Override
	String toString();

	/**************************************************************************
	 * Convert an individual Oracle Style statements to target database statement syntax
	 *
	 * @param oraStatement oracle statement
	 * @return converted Statement
	 */
	String convertStatement(String oraStatement);

	/**
	 * Check if DBMS support the sql statement
	 *
	 * @param sql SQL statement
	 * @return true: yes
	 */
	boolean isSupported(String sql);

	/**
	 * Get constraint type associated with the index
	 *
	 * @param conn connection
	 * @param tableName table name
	 * @param IXName Index name
	 * @return String[0] = 0: do not know, 1: Primary Key 2: Foreign Key
	 *         String[1] - String[n] = Constraint Name
	 */
	String getConstraintType(Connection conn, String tableName, String IXName);

	/**
	 * Check and generate an alternative SQL
	 *
	 * @param reExNo number of re-execution
	 * @param msg previous execution error message
	 * @param sql previous executed SQL
	 * @return String, the alternative SQL, null if no alternative
	 */
	String getAlternativeSQL(int reExNo, String msg, String sql);

	/**
	 * Get Name of System User
	 *
	 * @return e.g. sa, system
	 */
	String getSystemUser();

	/**
	 * Get Name of System Database
	 *
	 * @param databaseName database Name
	 * @return e.g. master or database Name
	 */
	String getSystemDatabase(String databaseName);


	/**
	 * Return next sequence this Sequence
	 */
	int getNextID(String Name);

	/**
	 * Creates SQL for retrieving next sequence value.
	 */
	String TO_SEQUENCE_NEXTVAL(final String sequenceName);

	/**
	 * Create Native Sequence
	 *
	 * @return true if sequence was created or updated
	 */
	boolean createSequence(String name, int increment, int minvalue, int maxvalue, int start, String trxName);

	/**
	 * Rename the native sequence
	 *
	 * @param dbSequenceNameOld sequence name
	 * @param dbSequenceNameNew new sequence name
	 */
	void renameSequence(String dbSequenceNameOld, String dbSequenceNameNew);

	/**
	 * Get Cached Connection on Server
	 *
	 * @param connection info
	 * @param autoCommit true if autocommit connection
	 * @param transactionIsolation Connection transaction level
	 * @return connection or null
	 */
	Connection getCachedConnection(CConnection connection, boolean autoCommit, int transactionIsolation) throws Exception;

	/**
	 * Get Driver Connection
	 *
	 * @param dbUrl URL
	 * @param dbUid user
	 * @param dbPwd password
	 * @return connection
	 */
	Connection getDriverConnection(String dbUrl, String dbUid, String dbPwd) throws SQLException;

	/**
	 * Create DataSource
	 *
	 * @param connection connection
	 * @return data dource
	 */
	DataSource getDataSource(CConnection connection);

	/**
	 * Get Status
	 *
	 * @return status info or null if no local datasource available
	 */
	String getStatus();

	/**
	 * Close database.
	 * Shall never fail.
	 */
	void close();

	Convert getConvert();

	/**
	 * @return true if jdbc driver support statement timeout
	 */
	boolean isQueryTimeoutSupported();

	/**
	 * Default sql use to test whether a connection is still valid
	 */
	String DEFAULT_CONN_TEST_SQL = "SELECT Version FROM AD_System";

	/**
	 * Is the database have sql extension that return a subset of the query result
	 *
	 * @return boolean
	 */
	boolean isPagingSupported();

	/**
	 * modify sql to return a subset of the query result
	 */
	String addPagingSQL(String sql, int start, int end);

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
	 * @param throwDBException if the connection is closed or an {@link SQLException} is caught, then this parameter decides if an {@link DBException} shall be thrown. Set to <code>false</code> if you
	 *            are already exception handling and just want to use this method to get whatever info you can (and not to throw yet another exception).
	 */
	String getConnectionBackendId(Connection connection, boolean throwDBException);
}   // AdempiereDatabase
