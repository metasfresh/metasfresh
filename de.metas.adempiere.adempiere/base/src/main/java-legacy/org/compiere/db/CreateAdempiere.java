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

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.DBException;
import org.compiere.Adempiere;
import org.compiere.model.MColumn;
import org.compiere.model.MTable;
import org.compiere.model.M_Element;
import org.compiere.util.CLogMgt;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;

/**
 *  Class to Create a new Adempiere Database from a reference DB.
 *  <pre>
 *  - Create User
 *  - Create DDL (table, procedures, functions, etc.)
 *  </pre>
 *
 *  @author     Jorg Janke
 *  @version    $Id: CreateAdempiere.java,v 1.5 2006/09/22 23:35:19 jjanke Exp $
 */
public class CreateAdempiere
{
	/**
	 * 	Constructor
	 *	@param databaseType AdempiereDatabase.TYPE_
	 *	@param databaseHost database host
	 *	@param databasePort database port 0 for default
	 *	@param systemPassword system password
	 */
	public CreateAdempiere(String databaseType, String databaseHost, int databasePort,
		String systemPassword)
	{
		initDatabase(databaseType);
		m_databaseHost = databaseHost;
		if (databasePort == 0)
			m_databasePort = m_dbTarget.getStandardPort();
		else
			m_databasePort = databasePort;
		m_systemPassword = systemPassword;
		log.info(m_dbTarget.getName() + " on " + databaseHost);
	}   //  create

	/** Adempiere Target Database */
	private AdempiereDatabase 	m_dbTarget = null;
	/** Adempiere Source Database */
	private AdempiereDatabase 	m_dbSource = null;
	//
	private String				m_databaseHost = null;
	private int					m_databasePort = 0;
	private String 				m_systemPassword = null;
	private String 				m_adempiereUser = null;
	private String 				m_adempierePassword = null;
	private String 				m_databaseName = null;
	private String 				m_databaseDevice = null;
	//
	private Properties			m_ctx = Env.newTemporaryCtx();
	/** Cached connection		*/
	private Connection			m_conn = null;
	
	/**	Logger	*/
	private static CLogger	log	= CLogger.getCLogger (CreateAdempiere.class);
	
	/**
	 * 	Create Adempiere Database
	 *	@param databaseType Database.DB_
	 */
	private void initDatabase(String databaseType)
	{
		try
		{
			m_dbTarget = Database.newAdempiereDatabase(databaseType);
		}
		catch (Exception e)
		{
			log.severe(e.toString ());
			e.printStackTrace();
		}
		if (m_dbTarget == null)
			throw new IllegalStateException("No database: " + databaseType);
		
		//	Source Database
		m_dbSource = DB.getDatabase();
	}	//	createDatabase
	
	/**
	 * 	Clean Start - drop & re-create DB
	 */
	public void cleanStart()
	{
		Connection conn = getConnection(true, true);
		if (conn == null)
			throw new IllegalStateException("No Database");
		//
		dropDatabase(conn);
		createUser(conn);
		createDatabase(conn);
		//
		try
		{
			if (conn != null)
				conn.close();
		}
		catch (SQLException e2)
		{
			log.log(Level.SEVERE, "close connection", e2);
		}
		conn = null;
	}	//	cleanStart
	
	
	/**
	 *  Set Adempiere User
	 *  @param adempiereUser adempiere id
	 *  @param adempierePassword adempiere password
	 */
	public void setAdempiereUser (String adempiereUser, String adempierePassword)
	{
		m_adempiereUser = adempiereUser;
		m_adempierePassword = adempierePassword;
	}   //  setAdempiereUser

	/**
	 *  Set Database Name
	 *  @param databaseName db name
	 *  @param databaseDevice device or table space
	 */
	public void setDatabaseName (String databaseName, String databaseDevice)
	{
		m_databaseName = databaseName;
		m_databaseDevice = databaseDevice;
	}   //  createDatabase

	
	/**
	 * 	Test Connection
	 *	@return connection
	 */
	public boolean testConnection()
	{
		String dbUrl = m_dbTarget.getConnectionURL (m_databaseHost, m_databasePort, 
			m_databaseName, m_dbTarget.getSystemUser());	//	adempiere may not be defined yet
		log.info(dbUrl + " - " + m_dbTarget.getSystemUser() + "/" + m_systemPassword);
		try
		{
			Connection conn = m_dbTarget.getDriverConnection(dbUrl, m_dbTarget.getSystemUser(), m_systemPassword);
			//
			JDBCInfo info = new JDBCInfo(conn);
			if (CLogMgt.isLevelFinest())
			{
				info.listCatalogs();
				info.listSchemas();
			}
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "test", e);
			return false;
		}

		return true;
	}	//	testConnection
	
	
	/**************************************************************************
	 *  Create User
	 *  @param sysConn system connection
	 *  @return true if success
	 */
	public boolean createUser (Connection sysConn)
	{
		log.info(m_adempiereUser + "/" + m_adempierePassword);
		return executeCommands(m_dbTarget.getCommands(AdempiereDatabase.CMD_CREATE_USER), 
			sysConn, true, false);
	}   //  createUser

	/**
	 *  Create Database (User)
	 *  @param sysConn system connection
	 *  @return true if success
	 */
	public boolean createDatabase (Connection sysConn)
	{
		log.info(m_databaseName + "(" + m_databaseDevice + ")");
		return executeCommands(m_dbTarget.getCommands(AdempiereDatabase.CMD_CREATE_DATABASE), 
			sysConn, true, false);
	}   //  createDatabase	
	
	/**
	 *  Drop Database (User)
	 *  @param sysConn system connection
	 *  @return true if success
	 */
	public boolean dropDatabase (Connection sysConn)
	{
		log.info(m_databaseName);
		return executeCommands(m_dbTarget.getCommands(AdempiereDatabase.CMD_DROP_DATABASE), 
			sysConn, true, false);
	}   //  dropDatabase	
	
	
	/**
	 * 	Create Tables and copy data
	 * 	@param whereClause optional where clause
	 * 	@param dropFirst drop first
	 *	@return true if executed
	 */
	public boolean copy (String whereClause, boolean dropFirst)
	{
		log.info(whereClause);
		if (getConnection(false, true) == null)
			return false;
		//
		boolean success = true;
		int count = 0;
		ArrayList<String> list = new ArrayList<String>();
		String sql = "SELECT * FROM AD_Table";
		if (whereClause != null && whereClause.length() > 0)
			sql += " WHERE " + whereClause;
		sql += " ORDER BY TableName";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			//jz: pstmt.getConnection() could be null
			Connection conn = pstmt.getConnection();
			DatabaseMetaData md = null;
			if (conn != null)
				md = conn.getMetaData();
			else
			{
				//jz: globalization issue??
				throw new DBException("No Connection");
			}
			
			rs = pstmt.executeQuery ();
			while (rs.next() && success)
			{
				MTable table = new MTable (m_ctx, rs, null);
				if (table.isView())
					continue;
				if (dropFirst)
				{
					executeCommands(new String[]
					    {"DROP TABLE " + table.getTableName()}, 
						m_conn, false, false);
				}
				//
				if (createTable (table, md))
				{
					list.add(table.getTableName());
					count++;
				}
				else
					success = false;
			}
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
			success = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		if (!success)
			return false;
		
		/**	Enable Contraints */
		enableConstraints(list);

		databaseBuild();
		
		log.info("#" + count);
		
		try
		{
			if (m_conn != null)
				m_conn.close();
		}
		catch (SQLException e2)
		{
			log.log(Level.SEVERE, "close connection", e2);
		}
		m_conn = null;
		return success;
	}	//	copy

	/**
	 * 	Execute Script
	 * 	@param script file with script
	 *	@return true if executed
	 */
	public boolean execute (File script)
	{
		return false;
	}	//	createTables
	

	
	/**
	 * 	Create Table
	 *	@param mTable table model
	 *	@param md meta data
	 *	@return true if created
	 */
	private boolean createTable (MTable mTable, DatabaseMetaData md)
	{
		String tableName = mTable.getTableName();
		log.info(tableName);
		String catalog = m_dbSource.getCatalog();
		String schema = m_dbSource.getSchema();
		String table = tableName.toUpperCase();
		//	
		MColumn[] columns = mTable.getColumns(false);
		
		StringBuffer sb = new StringBuffer("CREATE TABLE ");
		sb.append(tableName).append(" (");
		try
		{
			//	Columns
			boolean first = true;
			ResultSet sourceColumns = md.getColumns(catalog, schema, table, null);
			while (sourceColumns.next())
			{
				sb.append(first ? "" : ", ");
				first = false;
				//	Case sensitive Column Name
				MColumn column = null;
				String columnName = sourceColumns.getString("COLUMN_NAME");
				for (int i = 0; i < columns.length; i++)
				{
					String cn = columns[i].getColumnName();
					if (cn.equalsIgnoreCase(columnName))
					{
						columnName = cn;
						column = columns[i];
						break;
					}
				}
				sb.append(columnName).append(" ");
				//	Data Type & Precision
				int sqlType = sourceColumns.getInt ("DATA_TYPE");		//	sql.Types
				String typeName = sourceColumns.getString ("TYPE_NAME");	//	DB Dependent
				int size = sourceColumns.getInt ("COLUMN_SIZE");
				int decDigits = sourceColumns.getInt("DECIMAL_DIGITS");
				if (sourceColumns.wasNull())
					decDigits = -1;
				if (typeName.equals("NUMBER"))
				{
					/** Oracle Style	*
					if (decDigits == -1)
						sb.append(typeName);
					else
						sb.append(typeName).append("(")
							.append(size).append(",").append(decDigits).append(")");
					/** Other DBs		*/
					int dt = column.getAD_Reference_ID();
					if (DisplayType.isID(dt))
						sb.append("INTEGER");
					else 
					{
						int scale = DisplayType.getDefaultPrecision(dt);
						sb.append("DECIMAL(")
							.append(18+scale).append(",").append(scale).append(")");
					}
				}					
				else if (typeName.equals("DATE") || typeName.equals("BLOB") || typeName.equals("CLOB"))
					sb.append(typeName);
				else if (typeName.equals("CHAR") || typeName.startsWith("VARCHAR"))
					sb.append(typeName).append("(").append(size).append(")");
				else if (typeName.startsWith("NCHAR") || typeName.startsWith("NVAR"))
					sb.append(typeName).append("(").append(size/2).append(")");
				else if (typeName.startsWith("TIMESTAMP"))
					sb.append("DATE");
				else 
					log.severe("Do not support data type " + typeName);
				//	Default
				String def = sourceColumns.getString("COLUMN_DEF");
				if (def != null)
				{
					//jz: replace '' to \', otherwise exception
					def.replaceAll("''", "\\'");
					sb.append(" DEFAULT ").append(def);
				}
				//	Null
				if (sourceColumns.getInt("NULLABLE") == DatabaseMetaData.columnNoNulls)
					sb.append(" NOT NULL");
				else
					sb.append(" NULL");
				
				//	Check Contraints


			}	//	for all columns
			sourceColumns.close();

			//	Primary Key
			ResultSet sourcePK = md.getPrimaryKeys(catalog, schema, table);
			//	TABLE_CAT=null, TABLE_SCHEM=REFERENCE, TABLE_NAME=A_ASSET, COLUMN_NAME=A_ASSET_ID, KEY_SEQ=1, PK_NAME=A_ASSET_KEY
			first = true;
			boolean hasPK = false;
			while (sourcePK.next())
			{
				hasPK = true;
				if (first)
					sb.append(", CONSTRAINT ").append(sourcePK.getString("PK_NAME")).append(" PRIMARY KEY (");
				else
					sb.append(",");
				first = false;
				String columnName = sourcePK.getString("COLUMN_NAME");
				sb.append(checkColumnName(columnName));
			}
			if (hasPK)	//	close constraint
				sb.append(")");	// USING INDEX TABLESPACE INDX
			sourcePK.close();
			//
			sb.append(")");	//	close create table
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "createTable", ex);
			return false;
		}

		//	Execute Create Table
		if (!executeCommands(new String[]{sb.toString()}, m_conn, false, true))
			return true;	// continue
		
		//	Create Inexes
		createTableIndexes(mTable, md);
		
		return createTableData(mTable);
	}	//	createTable
	
	/**
	 * 	Check Column Name
	 *	@param columnName column name
	 *	@return column name with correct case
	 */
	private String checkColumnName (String columnName)
	{
		return M_Element.getColumnName (columnName);
	}	//	checkColumnName
	
	/**
	 * 	Create Table Indexes
	 *	@param mTable table
	 *	@param md meta data
	 */
	private void createTableIndexes(MTable mTable, DatabaseMetaData md)
	{
		String tableName = mTable.getTableName();
		log.info(tableName);
		String catalog = m_dbSource.getCatalog();
		String schema = m_dbSource.getSchema();
		String table = tableName.toUpperCase();
		try
		{
			ResultSet sourceIndex = md.getIndexInfo(catalog, schema, table, false, false);

		}
		catch (Exception e)
		{
			
		}
	}	//	createTableIndexes
	
	
	/**
	 * 	Create/Copy Table Data
	 *	@param mTable model table
	 *	@return true if data created/copied
	 */
	private boolean createTableData (MTable mTable)
	{
		boolean success = true;
		int count = 0;
		int errors = 0;
		long start = System.currentTimeMillis();
		
		//	Get Table Data
		String sql = "SELECT * FROM " + mTable.getTableName();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, mTable.get_TrxName());
			rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				if (createTableDataRow(rs, mTable))
					count++;
				else
					errors++;
			}
 		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
			success = false;
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		long elapsed = System.currentTimeMillis() - start;
		log.config("Inserted=" + count + " - Errors=" + errors 
			+ " - " + elapsed + " ms");
		return success;
	}	//	createTableData
	
	/**
	 * 	Create Table Data Row
	 *	@param rs result set
	 *	@param mTable table
	 *	@return true if created
	 */
	private boolean createTableDataRow (ResultSet rs, MTable mTable)
	{
		StringBuffer insert = new StringBuffer ("INSERT INTO ")
			.append(mTable.getTableName()).append(" (");
		StringBuffer values = new StringBuffer ();
		//
		MColumn[] columns = mTable.getColumns(false);
		for (int i = 0; i < columns.length; i++)
		{
			if (i != 0)
			{
				insert.append(",");
				values.append(",");
			}
			MColumn column = columns[i];
			String columnName = column.getColumnName();
			insert.append(columnName);
			//
			int dt = column.getAD_Reference_ID();
			try
			{
				Object value = rs.getObject(columnName);
				if (rs.wasNull())
				{
					values.append("NULL");
				}
				else if (columnName.endsWith("_ID")	// Record_ID, C_ProjectType defined as Button
					|| DisplayType.isNumeric(dt) 
					|| (DisplayType.isID(dt) && !columnName.equals("AD_Language"))) 
				{
					BigDecimal bd = rs.getBigDecimal(columnName);
					String s = m_dbTarget.TO_NUMBER(bd, dt);
					values.append(s);
				}
				else if (DisplayType.isDate(dt))
				{
					Timestamp ts = rs.getTimestamp(columnName);
					String tsString = m_dbTarget.TO_DATE(ts, dt == DisplayType.Date);
					values.append(tsString);
				}
				else if (DisplayType.isLOB(dt))
				{
					// ignored
					values.append("NULL");
				}
				else if (DisplayType.isText(dt) || dt == DisplayType.YesNo 
					|| dt == DisplayType.List || dt == DisplayType.Button
					|| columnName.equals("AD_Language"))
				{
					String s = rs.getString(columnName);
					values.append(DB.TO_STRING(s));
				}
				else
				{
					log.warning("Unknown DisplayType=" + dt 
						+ " - " + value + " [" + value.getClass().getName() + "]");
					values.append("NuLl");
				}
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, columnName, e);
			}
		}	//	for all columns
		
		//
		insert.append(") VALUES (").append(values).append(")");
		return executeCommands(new String[]{insert.toString()}, 
			m_conn, false, false);	//	do not convert as text is converted
	}	//	createTableDataRow
	
	
	/**
	 * 	Enable Constraints
	 *	@param list list
	 *	@return true if constraints enabled/created
	 */
	private boolean enableConstraints (ArrayList<String> list)
	{
		log.info("");
		return false;
	}	//	enableConstraints
	

	private void databaseBuild()
	{
		//	Build Script
		//jz remove hard coded path later
		String fileName = "C:\\Adempiere\\adempiere-all2\\db\\database\\DatabaseBuild.sql";
		File file = new File (fileName);
		if (!file.exists())
			log.severe("No file: " + fileName);
		
	//	FileReader reader = new FileReader (file);
		
		
		
	}	//	databaseBuild
	
	/**
	 * 	Get Connection
	 * 	@param asSystem if true execute as db system administrator 
	 * 	@param createNew create new connection
	 *	@return connection or null
	 */
	private Connection getConnection (boolean asSystem, boolean createNew)
	{
		if (!createNew && m_conn != null)
			return m_conn;
		//
		String dbUrl = m_dbTarget.getConnectionURL(m_databaseHost, m_databasePort, 
			(asSystem ? m_dbTarget.getSystemDatabase(m_databaseName) : m_databaseName), 
			(asSystem ? m_dbTarget.getSystemUser() : m_adempiereUser));
		try
		{
			if (asSystem)
				m_conn = m_dbTarget.getDriverConnection(dbUrl, m_dbTarget.getSystemUser(), m_systemPassword);
			else
				m_conn = m_dbTarget.getDriverConnection(dbUrl, m_adempiereUser, m_adempierePassword);
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, dbUrl, e);
		}
		return m_conn;
	}	//	getConnection
	
	
	/**************************************************************************
	 * 	Execute Commands
	 * 	@param cmds array of SQL commands
	 * 	@param conn connection
	 * 	@param batch tf true commit as batch
	 * 	@param doConvert convert to DB specific notation
	 *	@return true if success
	 */
	private boolean executeCommands (String[] cmds, Connection conn, 
		boolean batch, boolean doConvert)
	{
		if (cmds == null || cmds.length == 0)
		{
			log.warning("No Commands");
			return false;
		}
		
		Statement stmt = null;
		String cmd = null;
		String cmdOriginal = null;
		try
		{
			if (conn == null)
			{
				conn = getConnection(false, false);
				if (conn == null)
					return false;
			}
			if (conn.getAutoCommit() == batch)
				conn.setAutoCommit(!batch);
			stmt = conn.createStatement();
			
			//	Commands
			for (int i = 0; i < cmds.length; i++)
			{
				cmd = cmds[i];
				cmdOriginal = cmds[i];
				if (cmd == null || cmd.length() == 0)
					continue;
				//
				if (cmd.indexOf('@') != -1)
				{
					cmd = Util.replace(cmd, "@SystemPassword@", m_systemPassword);
					cmd = Util.replace(cmd, "@AdempiereUser@", m_adempiereUser);
					cmd = Util.replace(cmd, "@AdempierePassword@", m_adempierePassword);
					cmd = Util.replace(cmd, "@SystemPassword@", m_systemPassword);
					cmd = Util.replace(cmd, "@DatabaseName@", m_databaseName);
					if (m_databaseDevice != null)
						cmd = Util.replace(cmd, "@DatabaseDevice@", m_databaseDevice);
				}
				if (doConvert)
					cmd = m_dbTarget.convertStatement(cmd);
				writeLog(cmd);
				log.finer(cmd);
				int no = stmt.executeUpdate(cmd);
				log.finest("# " + no);
			}
			//
			stmt.close();
			stmt = null;
			//
			if (batch)
				conn.commit();
			//
			return true;
		}
		catch (Exception e)
		{
			String msg = e.getMessage();
			if (msg == null || msg.length() == 0)
				msg = e.toString();
			msg += " (";
			if (e instanceof SQLException)
			{
				msg += "State=" + ((SQLException)e).getSQLState() 
					+ ",ErrorCode=" + ((SQLException)e).getErrorCode();
			}
			msg += ")";
			if (cmdOriginal != null && !cmdOriginal.equals(cmd))
				msg += " - " + cmdOriginal;
			msg += "\n=>" + cmd;
			log.log(Level.SEVERE, msg);
		}
		//	Error clean up
		try
		{
			if (stmt != null)
				stmt.close();
		}
		catch (SQLException e1)
		{
			log.log(Level.SEVERE, "close statement", e1);
		}
		stmt = null;
		return false;
	}	//	execureCommands

	
	/**
	 * 	Write to File Log
	 *	@param cmd cmd
	 */
	private void writeLog (String cmd)
	{
		try
		{
			if (m_writer == null)
			{
				File file = File.createTempFile("create", ".log");
				m_writer = new PrintWriter(new FileWriter(file));
				log.info(file.toString());
			}
			m_writer.println(cmd);
			m_writer.flush();
		}
		catch (Exception e)
		{
			log.severe(e.toString());
		}
	}	//	writeLog
	
	private PrintWriter 	m_writer = null;
	
	
	/**************************************************************************
	 * 	Create DB
	 *	@param args
	 */
	public static void main (String[] args)
	{
		Adempiere.startup(true);
		CLogMgt.setLevel(Level.FINE);
		CLogMgt.setLoggerLevel(Level.FINE,null);

		//	C_UOM_Conversion
		//	I_BankStatement
		//	
		//	Derby
		//jz: changed the password from "" to null
		 //begin vpj-cd e-Evolution 03/03/2005 PostgreSQL
		//PostgreSQL
        CreateAdempiere cc = new CreateAdempiere (Database.DB_POSTGRESQL, "127.0.0.2", 5432 , "adempiere");
		cc.setAdempiereUser("adempiere", "adempiere");
		cc.setDatabaseName("adempiere", "adempiere");
                // end begin vpj-cd e-Evolution 03/03/2005 PostgreSQL
		if (!cc.testConnection())
			return;
		cc.cleanStart();
		//
	//	cc.copy(null, false);
		cc.copy("TableName > 'C_RfQResponseLineQty'", false);
	}	//	main
	
}   //  CreateAdempiere
