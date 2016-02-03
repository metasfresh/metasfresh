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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;

import javax.sql.DataSource;

import oracle.jdbc.OracleDriver;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Check;
import org.compiere.dbPort.Convert;
import org.compiere.dbPort.Convert_Oracle;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Ini;
import org.compiere.util.Language;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *  Oracle Database Port
 *
 *  @author     Jorg Janke
 *  @version    $Id: DB_Oracle.java,v 1.7 2006/09/22 23:35:19 jjanke Exp $
 *  ---
 *  Modifications: Refactoring. Replaced Oracle Cache Manager with C3P0
 *  connection pooling framework for better and more efficient connnection handling
 *  
 *  @author Ashley Ramdass (Posterita)
 */
public class DB_Oracle implements AdempiereDatabase
{
    /**
     *  Oracle Database
     */
    public DB_Oracle()
    {
        /** Causes VPN problems ???
        try
        {
            getDriver();
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, e.getMessage());
        }
        **/
        // teo [ bug 1638208 ]: oracle 10g DATETIME issue
        // http://www.oracle.com/technology/tech/java/sqlj_jdbc/htdocs/jdbc_faq.htm#08_01
        try
        {
            System.setProperty("oracle.jdbc.V8Compatible", "true");
        }
        catch (Exception e)
        {
            log.log(Level.SEVERE, e.getMessage());
        }
    }   //  DB_Oracle

	/** Static Driver */
	private static final Supplier<OracleDriver> driverSupplier = Suppliers.memoize(new Supplier<OracleDriver>()
	{
		@Override
		public OracleDriver get()
		{
			// Speed up transfer rate
			System.setProperty("oracle.jdbc.TcpNoDelay", "true");
			// Oracle Multi - Language
			System.setProperty("oracle.jdbc.defaultNChar", "true");
			//
			try
			{
				final OracleDriver driver = new OracleDriver();
				DriverManager.registerDriver(driver);
				DriverManager.setLoginTimeout(Database.CONNECTION_TIMEOUT);
				return driver;
			}
			catch (SQLException e)
			{
				throw new DBException("Failed registering oracle database driver", e);
			}
		}
	});

    /** Driver Class Name           */
    public static final String      DRIVER = "oracle.jdbc.OracleDriver";

    /** Default Port                */
    public static final int         DEFAULT_PORT = 1521;
    /** Default Connection Manager Port */
    public static final int         DEFAULT_CM_PORT = 1630;

    /** Connection String           */
    private String                  m_connectionURL;

	// /** Statement Cache (50) */
	// private static final String MAX_STATEMENTS = "200";
    /** Data Source                 */
    private ComboPooledDataSource   m_ds = null;

    /** Cached User Name            */
    private String                  m_userName = null;

    private Convert m_convert = new Convert_Oracle();

    /** Logger          */
    private static CLogger          log = CLogger.getCLogger (DB_Oracle.class);


    private static int              m_maxbusyconnections = 0;


    /**
     *  Get Database Name
     *  @return database short name
     */
    @Override
	public String getName()
    {
        return Database.DB_ORACLE;
    }   //  getName

    /**
     *  Get Database Description
     *  @return database long name and version
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
			e.printStackTrace();
        }
        return "No Driver";
    }   //  getDescription

    /**
     *  Get Standard JDBC Port
     *  @return standard port
     */
    @Override
	public int getStandardPort()
    {
        return DEFAULT_PORT;
    }   //  getStandardPort

    /**
     *  Get and register Database Driver
     *  @return Driver
     *  @throws SQLException
     */
    @Override
	public Driver getDriver()
    {
		return driverSupplier.get();
    }   //  getDriver

    /**
     *  Get Database Connection String.
     *  <pre>
     *  Timing:
     *  - CM with source_route not in address_list  = 28.5 sec
     *  - CM with source_route in address_list      = 58.0 sec
     *  - direct    = 4.3-8 sec  (no real difference if on other box)
     *  - bequeath  = 3.4-8 sec
     *  </pre>
     *  @param connection Connection Descriptor
     *  @return connection String
     */
    @Override
	public String getConnectionURL (CConnection connection)
    {
        StringBuffer sb = null;
        //  Server Connections (bequeath)
        if (connection.isBequeath())
        {
            sb = new StringBuffer ("jdbc:oracle:oci8:@");
            //  bug: does not work if there is more than one db instance - use Net8
        //  sb.append(connection.getDbName());
        }
        else        //  thin driver
        {
            sb = new StringBuffer ("jdbc:oracle:thin:@");
            //  direct connection
            if (connection.isViaFirewall())
            {
                //  (description=(address_list=
                //  ( (source_route=yes)
                //    (address=(protocol=TCP)(host=cmhost)(port=1630))
                //    (address=(protocol=TCP)(host=dev)(port=1521))
                //  (connect_data=(service_name=dev1.adempiere.org)))
                sb.append("(DESCRIPTION=(ADDRESS_LIST=")
                    .append("(SOURCE_ROUTE=YES)")
                    .append("(ADDRESS=(PROTOCOL=TCP)(HOST=").append(connection.getFwHost())
                        .append(")(PORT=").append(connection.getFwPort()).append("))")
                    .append("(ADDRESS=(PROTOCOL=TCP)(HOST=").append(connection.getDbHost())
                        .append(")(PORT=").append(connection.getDbPort()).append(")))")
                    .append("(CONNECT_DATA=(SERVICE_NAME=").append(connection.getDbName()).append(")))");
            }
            else
            {
                //  old: jdbc:oracle:thin:@dev2:1521:sid
                //  new: jdbc:oracle:thin:@//dev2:1521/serviceName
                sb.append("//")
                    .append(connection.getDbHost())
                    .append(":").append(connection.getDbPort())
                    .append("/").append(connection.getDbName());
            }
        }
        m_connectionURL = sb.toString();
    //  log.config(m_connectionURL);
        //
        m_userName = connection.getDbUid();
        return m_connectionURL;
    }   //  getConnectionURL

    /**
     *  Get Connection URL.
     *  http://download-east.oracle.com/docs/cd/B14117_01/java.101/b10979/urls.htm#BEIDBFDF
     *  @param dbHost db Host
     *  @param dbPort db Port
     *  @param dbName db Name
     *  @param userName user name
     *  @return connection
     */
    @Override
	public String getConnectionURL (String dbHost, int dbPort, String dbName,
        String userName)
    {
        m_userName = userName;
        m_connectionURL = "jdbc:oracle:thin:@//"
            + dbHost + ":" + dbPort + "/" + dbName;
        return m_connectionURL;
    }   //  getConnectionURL

    /**
     *  Get JDBC Catalog
     *  @return null - not used
     */
    @Override
	public String getCatalog()
    {
        return null;
    }   //  getCatalog

    /**
     *  Get JDBC Schema
     *  @return user name
     */
    @Override
	public String getSchema()
    {
        if (m_userName != null)
            return m_userName.toUpperCase();
        log.severe("User Name not set (yet) - call getConnectionURL first");
        return null;
    }   //  getSchema

    /**
     *  Supports BLOB
     *  @return true if BLOB is supported
     */
    @Override
	public boolean supportsBLOB()
    {
        return true;
    }   //  supportsBLOB

    /**
     *  String Representation
     *  @return info
     */
    @Override
	public String toString()
    {
        StringBuffer sb = new StringBuffer("DB_Oracle[");
        sb.append(m_connectionURL);
        try
        {
            StringBuffer logBuffer = new StringBuffer(50);
            logBuffer.append("# Connections: ").append(m_ds.getNumConnections());
            logBuffer.append(" , # Busy Connections: ").append(m_ds.getNumBusyConnections());
            logBuffer.append(" , # Idle Connections: ").append(m_ds.getNumIdleConnections());
            logBuffer.append(" , # Orphaned Connections: ").append(m_ds.getNumUnclosedOrphanedConnections());
        }
        catch (Exception e)
        {
            sb.append("=").append(e.getLocalizedMessage());
        }
        sb.append("]");
        return sb.toString();
    }   //  toString

    /**
     *  Get Status
     *  @return status info
     */
    @Override
	public String getStatus()
    {
        if (m_ds == null)
        {
            return null;
        }

        StringBuffer sb = new StringBuffer();
        try
        {
            sb.append("# Connections: ").append(m_ds.getNumConnections());
            sb.append(" , # Busy Connections: ").append(m_ds.getNumBusyConnections());
            sb.append(" , # Idle Connections: ").append(m_ds.getNumIdleConnections());
            sb.append(" , # Orphaned Connections: ").append(m_ds.getNumUnclosedOrphanedConnections());
        }
        catch (Exception e)
        {}
        return sb.toString();
    }   //  getStatus


    /**************************************************************************
     *  Convert an individual Oracle Style statements to target database statement syntax.
     *  @param oraStatement oracle statement
     *  @return converted Statement oracle statement
     */
    @Override
    public String convertStatement (String oraStatement)
    {
    	Convert.logMigrationScript(oraStatement, null);
        return oraStatement;
    }   //  convertStatement



    /**
     *  Check if DBMS support the sql statement
     *  @sql SQL statement
     *  @return true: yes
     */
    @Override
    public boolean isSupported(String sql)
    {
        return true;
        //jz temp, modify later
    }



    /**
     *  Get constraint type associated with the index
     *  @tableName table name
     *  @IXName Index name
     *  @return String[0] = 0: do not know, 1: Primary Key  2: Foreign Key
     *          String[1] - String[n] = Constraint Name
     */
    @Override
    public String getConstraintType(Connection conn, String tableName, String IXName)
    {
        if (IXName == null || IXName.length()==0)
            return "0";
        if (IXName.endsWith("_KEY"))
            return "1"+IXName;
        else
            return "0";
        //jz temp, modify later from user.constraints
    }

    /**
     *  Get Name of System User
     *  @return system
     */
    @Override
    public String getSystemUser()
    {
        return "system";
    }   //  getSystemUser

    /**
     *  Get Name of System Database
     *  @param databaseName database Name
     *  @return e.g. master or database Name
     */
    @Override
    public String getSystemDatabase(String databaseName)
    {
        return databaseName;
    }   //  getSystemDatabase


    /**
     *  Create SQL TO Date String from Timestamp
     *
     *  @param  time Date to be converted
     *  @param  dayOnly true if time set to 00:00:00
     *
     *  @return TO_DATE('2001-01-30 18:10:20',''YYYY-MM-DD HH24:MI:SS')
     *      or  TO_DATE('2001-01-30',''YYYY-MM-DD')
     */
    @Override
    public String TO_DATE (Timestamp time, boolean dayOnly)
    {
        if (time == null)
        {
            if (dayOnly)
                return "TRUNC(now())";
            return "now()";
        }

        StringBuffer dateString = new StringBuffer("TO_DATE('");
        //  YYYY-MM-DD HH24:MI:SS.mmmm  JDBC Timestamp format
        String myDate = time.toString();
        if (dayOnly)
        {
            dateString.append(myDate.substring(0,10));
            dateString.append("','YYYY-MM-DD')");
        }
        else
        {
            dateString.append(myDate.substring(0, myDate.indexOf('.')));    //  cut off miliseconds
            dateString.append("','YYYY-MM-DD HH24:MI:SS')");
        }
        return dateString.toString();
    }   //  TO_DATE

    /**
     *  Create SQL for formatted Date, Number
     *
     *  @param  columnName  the column name in the SQL
     *  @param  displayType Display Type
     *  @param  AD_Language 6 character language setting (from Env.LANG_*)
     *
     *  @return TRIM(TO_CHAR(columnName,'999G999G999G990D00','NLS_NUMERIC_CHARACTERS='',.'''))
     *      or TRIM(TO_CHAR(columnName,'TM9')) depending on DisplayType and Language
     *  @see org.compiere.util.DisplayType
     *  @see org.compiere.util.Env
     *
     *   */
    @Override
    public String TO_CHAR (String columnName, int displayType, String AD_Language)
    {
        StringBuffer retValue = new StringBuffer("TRIM(TO_CHAR(");
        retValue.append(columnName);

        //  Numbers
        if (DisplayType.isNumeric(displayType))
        {
            if (displayType == DisplayType.Amount)
                retValue.append(",'999G999G999G990D00'");
            else
                retValue.append(",'TM9'");
            //  TO_CHAR(GrandTotal,'999G999G999G990D00','NLS_NUMERIC_CHARACTERS='',.''')
            if (!Language.isDecimalPoint(AD_Language))      //  reversed
                retValue.append(",'NLS_NUMERIC_CHARACTERS='',.'''");
        }
        else if (DisplayType.isDate(displayType))
        {
            retValue.append(",'")
                .append(Language.getLanguage(AD_Language).getDBdatePattern())
                .append("'");
        }
        retValue.append("))");
        //
        return retValue.toString();
    }   //  TO_CHAR

    @Override
    public String TO_CHAR (String columnName, int displayType, String AD_Language, String formatPattern)
    {
    	// TODO: implement Oracle support for format pattern
    	return TO_CHAR(columnName, displayType, AD_Language);
    }

    /**
     *  Return number as string for INSERT statements with correct precision
     *  @param number number
     *  @param displayType display Type
     *  @return number as string
     */
    @Override
    public String TO_NUMBER (BigDecimal number, int displayType)
    {
        if (number == null)
            return "NULL";
        BigDecimal result = number;
        int scale = DisplayType.getDefaultPrecision(displayType);
        if (scale > number.scale())
        {
            try
            {
                result = number.setScale(scale, BigDecimal.ROUND_HALF_UP);
            }
            catch (Exception e)
            {
            //  log.severe("Number=" + number + ", Scale=" + " - " + e.getMessage());
            }
        }
        return result.toString();
    }   //  TO_NUMBER

    /**
     *  Create DataSource
     *  @param connection connection
     *  @return data dource
     */
    @Override
    public DataSource getDataSource(CConnection connection)
    {
        if (m_ds != null)
            return m_ds;

        try
        {
            System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
            //System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "ALL");
            ComboPooledDataSource cpds = new ComboPooledDataSource();
            cpds.setDataSourceName("AdempiereDS");
            cpds.setDriverClass(DRIVER);
            //loads the jdbc driver
            cpds.setJdbcUrl(getConnectionURL(connection));
            cpds.setUser(connection.getDbUid());
            cpds.setPassword(connection.getDbPwd());
            cpds.setPreferredTestQuery(DEFAULT_CONN_TEST_SQL);
            cpds.setIdleConnectionTestPeriod(1200);
            cpds.setAcquireRetryAttempts(2);
            //cpds.setTestConnectionOnCheckin(true);
            //cpds.setTestConnectionOnCheckout(true);
            //cpds.setCheckoutTimeout(60);

            if (Ini.isClient())
            {
                cpds.setInitialPoolSize(1);
                cpds.setMinPoolSize(1);
                cpds.setMaxPoolSize(15);
                cpds.setMaxIdleTimeExcessConnections(1200);
                cpds.setMaxIdleTime(900);
                m_maxbusyconnections = 10;
            }
            else
            {
                cpds.setInitialPoolSize(10);
                cpds.setMinPoolSize(5);
                cpds.setMaxPoolSize(150);
                cpds.setMaxIdleTimeExcessConnections(1200);
                cpds.setMaxIdleTime(1200);
                m_maxbusyconnections = 120;
            }

            //the following sometimes kill active connection!
            //cpds.setUnreturnedConnectionTimeout(1200);
            //cpds.setDebugUnreturnedConnectionStackTraces(true);

            m_ds = cpds;
        }
        catch (Exception ex)
        {
            m_ds = null;
            //log might cause infinite loop since it will try to acquire database connection again
            //log.log(Level.SEVERE, "Could not initialise C3P0 Datasource", ex);
            System.err.println("Could not initialise C3P0 Datasource: " + ex.getLocalizedMessage());
        }

        return m_ds;
    }   //  getDataSource


    /**
     *  Get Cached Connection
     *  @param connection info
     *  @param autoCommit true if autocommit connection
     *  @param transactionIsolation Connection transaction level
     *  @return connection or null
     *  @throws Exception
     */
    @Override
    public Connection getCachedConnection (CConnection connection,
        boolean autoCommit, int transactionIsolation)
        throws Exception
    {
        Connection conn = null;
        Exception exception = null;
        try
        {
            if (m_ds == null)
                getDataSource(connection);

            //
            try
            {
                conn = m_ds.getConnection();
                if (conn != null)
                {
                    if (conn.getTransactionIsolation() != transactionIsolation)
                        conn.setTransactionIsolation(transactionIsolation);
                    if (conn.getAutoCommit() != autoCommit)
                        conn.setAutoCommit(autoCommit);
//                      conn.setDefaultRowPrefetch(20);     //  10 default - reduces round trips
                }
            }
            catch (Exception e)
            {
                exception = e;
                conn = null;
                if (DBException.isInvalidUserPassError(e))
                {
                	//log might cause infinite loop since it will try to acquire database connection again
                	/*
                    log.severe("Cannot connect to database: "
                        + getConnectionURL(connection)
                        + " - UserID=" + connection.getDbUid());
                    */
                	System.err.println("Cannot connect to database: "
                            + getConnectionURL(connection)
                            + " - UserID=" + connection.getDbUid());
                }
            }

            if (conn == null && exception != null)
            {
            	//log might cause infinite loop since it will try to acquire database connection again
            	/*
                log.log(Level.SEVERE, exception.toString());
                log.fine(toString()); */
            	System.err.println(exception.toString());
            }
        }
        catch (Exception e)
        {
            exception = e;
        }

        try
        {
        	if (conn != null) {
        		int numConnections = m_ds.getNumBusyConnections();
	            if(numConnections >= m_maxbusyconnections && m_maxbusyconnections > 0)
	            {
	                log.warning(getStatus());
	                //hengsin: make a best effort to reclaim leak connection
	                Runtime.getRuntime().runFinalization();
	            }
        	}
        }
        catch (Exception ex)
        {

        }
        if (exception != null)
            throw exception;
        return conn;
    }   //  getCachedConnection

    /**
     *  Get Driver Connection
     *  @param dbUrl URL
     *  @param dbUid user
     *  @param dbPwd password
     *  @return connection
     *  @throws SQLException
     */
    @Override
    public Connection getDriverConnection (String dbUrl, String dbUid, String dbPwd)
        throws SQLException
    {
        getDriver();
        return DriverManager.getConnection (dbUrl, dbUid, dbPwd);
    }   //  getDriverConnection

    /**
     *  Close
     */
    @Override
    public void close()
    {
        log.config(toString());
        if (m_ds != null)
        {
            try
            {
                m_ds.close();
            }
            catch (Exception e)
            {
                log.log(Level.SEVERE, "Could not close Data Source");
            }
        }
        m_ds = null;
    }   //  close

    /**
     *  Clean up
     */
    public void cleanup()
    {

    }   //  cleanup

    /**
     *  Check and generate an alternative SQL
     *  @reExNo number of re-execution
     *  @msg previous execution error message
     *  @sql previous executed SQL
     *  @return String, the alternative SQL, null if no alternative
     */
    @Override
    public String getAlternativeSQL(int reExNo, String msg, String sql)
    {
        //check reExNo or based on reExNo to do a decision. Currently none

        return null; //do not do re-execution of alternative SQL
    }

    @Override
    public Convert getConvert()
    {
        return m_convert;
    }

    @Override
	public int getNextID(String Name)
    {
		int m_sequence_id = DB.getSQLValue(null, "SELECT "+Name.toUpperCase()+".nextval FROM DUAL");
		return m_sequence_id;
	}
	
	@Override
	public String TO_SEQUENCE_NEXTVAL(final String sequenceName)
	{
		Check.assumeNotEmpty(sequenceName, "sequenceName not empty");
		final StringBuilder sb = new StringBuilder();
		sb.append(sequenceName).append(".nextval");

		return sb.toString();
	}

    @Override
    public boolean createSequence(String name , int increment , int minvalue , int maxvalue ,int  start , String trxName) 
	{
		int no = DB.executeUpdate("DROP SEQUENCE "+name.toUpperCase(), trxName);
		no = DB.executeUpdateEx("CREATE SEQUENCE "+name.toUpperCase()													
							+ " MINVALUE " + minvalue 
							+ " MAXVALUE " + maxvalue
							+ " START WITH " + start 
							+ " INCREMENT BY " + increment +" CACHE 20", trxName)
							;
		if(no == -1 )
			return false;
		else 
			return true;
	}
    
    @Override
    public void renameSequence(final String dbSequenceNameOld, final String dbSequenceNameNew)
    {
    	final String trxName = ITrx.TRXNAME_ThreadInherited;
		DB.executeUpdateEx("RENAME " + dbSequenceNameOld.toUpperCase() + " TO " + dbSequenceNameNew.toUpperCase(), trxName);
    }

	@Override
	public boolean isQueryTimeoutSupported() {
		return true;
	}
	
	@Override
	public String addPagingSQL(String sql, int start, int end) {
		//not supported, too many corner case that doesn't work using rownum. to investigate later
		return sql;
	}

	@Override
	public boolean isPagingSupported() {
		return false;
	}

	@Override
	public String getSQLDataType (int displayType, String columnName, int fieldLength)
	{
		if (columnName.equals("EntityType")
			|| columnName.equals ("AD_Language"))
			return "VARCHAR2(" + fieldLength + ")";
		//	ID
		if (DisplayType.isID(displayType))
		{
			if (displayType == DisplayType.Image 	//	FIXTHIS
				&& columnName.equals("BinaryData"))
				return "BLOB";
			//	ID, CreatedBy/UpdatedBy, Acct
			else if (columnName.endsWith("_ID") 
				|| columnName.endsWith("tedBy") 
				|| columnName.endsWith("_Acct")
				|| "AD_Key".equals(columnName) // HARDCODED for AD_Ref_Table.AD_Key
				|| "AD_Display".equals(columnName) // HARDCODED for AD_Ref_Table.AD_Display
				)
				return "NUMBER(10)";
			else if (fieldLength < 4)
				return "CHAR(" + fieldLength + ")";
			else	//	EntityType, AD_Language	fallback
				return "VARCHAR2(" + fieldLength + ")";
		}
		//
		if (displayType == DisplayType.Integer)
			return "NUMBER(10)";
		if (displayType == DisplayType.DateTime)
			return "TIMESTAMP WITH TIME ZONE";
		if (DisplayType.isDate(displayType))
			return "DATE";
		if (DisplayType.isNumeric(displayType))
			return "NUMBER";
		if (displayType == DisplayType.Binary)
			return "BLOB";
		if (displayType == DisplayType.TextLong 
			|| (displayType == DisplayType.Text && fieldLength >= 4000))
			return "CLOB";
		if (displayType == DisplayType.YesNo)
			return "CHAR(1)";
		if (displayType == DisplayType.List) {
			if (fieldLength == 1)
				return "CHAR(" + fieldLength + ")";
			else
				return "NVARCHAR2(" + fieldLength + ")";			
		}
		if (displayType == DisplayType.Color) // this condition is never reached - filtered above in isID
		{
			if (columnName.endsWith("_ID"))
				return "NUMBER(10)";
			else
				return "CHAR(" + fieldLength + ")";
		}
		if (displayType == DisplayType.Button)
		{
			if (columnName.endsWith("_ID"))
				return "NUMBER(10)";
			else
				return "CHAR(" + fieldLength + ")";
		}
		if (!DisplayType.isText(displayType))
			log.severe("Unhandled Data Type = " + displayType);
				
		return "NVARCHAR2(" + fieldLength + ")";
	}	//	getSQLDataType

	/**
	 * @thorws UnsupportedOperationException always
	 */
	@Override
	public String getConnectionBackendId(Connection connection, boolean throwDBException)
	{
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String getRowIdSql(final String tableName)
	{
		throw new UnsupportedOperationException();
	}

}   //  DB_Oracle
