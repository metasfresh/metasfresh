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
package org.compiere.dbPort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.RowSet;

import oracle.jdbc.pool.OracleDataSource;

//import oracle.jdbc.rowset.*;

/**
 *  JDBC Performance Test - Oracle
 *
 *  @author     Jorg Janke
 *  @version    $Id: JdbcTest.java,v 1.2 2006/07/30 00:55:04 jjanke Exp $
 */
public class JdbcTest extends Thread
{
/*****************************************************************************

Multiple Connections Fetch=10 	Conn=2032 	Stmt=0 	Query=47 	Retrieve=2109 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=4188 	Stmt=2156 	Query=2156
Multiple Connections Fetch=10 	Conn=141 	Stmt=0 	Query=31 	Retrieve=1875 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2047 	Stmt=1906 	Query=1906
Multiple Connections Fetch=10 	Conn=141 	Stmt=0 	Query=31 	Retrieve=1844 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2016 	Stmt=1875 	Query=1875
Data Source          Fetch=10 	Conn=172 	Stmt=0 	Query=16 	Retrieve=1875 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2063 	Stmt=1891 	Query=1891
Data Source          Fetch=10 	Conn=672 	Stmt=15 	Query=16 	Retrieve=1797 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2500 	Stmt=1828 	Query=1813
Data Source          Fetch=10 	Conn=156 	Stmt=0 	Query=16 	Retrieve=1766 	ClRs=0 	ClStmt=0 	ClConn=15 	- Total=1953 	Stmt=1782 	Query=1782
Connection Cache     Fetch=10 	Conn=141 	Stmt=0 	Query=125 	Retrieve=1766 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2032 	Stmt=1891 	Query=1891
Connection Cache     Fetch=10 	Conn=0 	Stmt=0 	Query=15 	Retrieve=1766 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1781 	Stmt=1781 	Query=1781
Connection Cache     Fetch=10 	Conn=0 	Stmt=0 	Query=16 	Retrieve=1765 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1781 	Stmt=1781 	Query=1781
Multiple Connections Fetch=20 	Conn=4501 	Stmt=0 	Query=15 	Retrieve=1313 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=5829 	Stmt=1328 	Query=1328
Multiple Connections Fetch=20 	Conn=125 	Stmt=0 	Query=16 	Retrieve=1312 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1453 	Stmt=1328 	Query=1328
Multiple Connections Fetch=20 	Conn=141 	Stmt=0 	Query=31 	Retrieve=1406 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1578 	Stmt=1437 	Query=1437
Data Source          Fetch=20 	Conn=126 	Stmt=0 	Query=31 	Retrieve=1297 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1454 	Stmt=1328 	Query=1328
Data Source          Fetch=20 	Conn=125 	Stmt=0 	Query=16 	Retrieve=1328 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1469 	Stmt=1344 	Query=1344
Data Source          Fetch=20 	Conn=140 	Stmt=0 	Query=16 	Retrieve=1469 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1625 	Stmt=1485 	Query=1485
Connection Cache     Fetch=20 	Conn=0 	Stmt=0 	Query=31 	Retrieve=1344 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1375 	Stmt=1375 	Query=1375
Connection Cache     Fetch=20 	Conn=0 	Stmt=0 	Query=16 	Retrieve=1375 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1391 	Stmt=1391 	Query=1391
Connection Cache     Fetch=20 	Conn=0 	Stmt=0 	Query=15 	Retrieve=1375 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1390 	Stmt=1390 	Query=1390
JDBC RowSet          Fetch=10 	Conn=16 	Stmt=0 	Query=3969 	Retrieve=3047 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=7032 	Stmt=7016 	Query=7016
JDBC RowSet          Fetch=10 	Conn=0 	Stmt=0 	Query=172 	Retrieve=2781 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2953 	Stmt=2953 	Query=2953
JDBC RowSet          Fetch=10 	Conn=0 	Stmt=0 	Query=313 	Retrieve=2609 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2922 	Stmt=2922 	Query=2922
Cached RowSet        Fetch=10 	Conn=63 	Stmt=0 	Query=5406 	Retrieve=16 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=5485 	Stmt=5422 	Query=5422
Cached RowSet        Fetch=10 	Conn=0 	Stmt=0 	Query=3907 	Retrieve=0 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=3907 	Stmt=3907 	Query=3907
Cached RowSet        Fetch=10 	Conn=0 	Stmt=0 	Query=3890 	Retrieve=0 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=3890 	Stmt=3890 	Query=3890
Shared Connection    Threads=10 	Yield=false 	ms= 18267 	each= 1826
Shared Connection    Threads=10 	Yield=false 	ms= 18220 	each= 1822
Shared Connection    Threads=10 	Yield=true 	ms= 18329 	each= 1832
Shared Connection    Threads=10 	Yield=true 	ms= 18314 	each= 1831
Multiple Connections Threads=10 	Yield=false 	ms= 14610 	each= 1461
Multiple Connections Threads=10 	Yield=false 	ms= 14360 	each= 1436
Multiple Connections Threads=10 	Yield=true 	ms= 13986 	each= 1398
Multiple Connections Threads=10 	Yield=true 	ms= 14017 	each= 1401
Multiple PreCreated  Threads=10 	Yield=false 	ms= 5376 	each= 537
Multiple PreCreated  Threads=10 	Yield=false 	ms= 1828 	each= 182
Multiple PreCreated  Threads=10 	Yield=true 	ms= 12017 	each= 1201
Multiple PreCreated  Threads=10 	Yield=true 	ms= 12032 	each= 1203
Data Source          Threads=10 	Yield=false 	ms= 13391 	each= 1339
Data Source          Threads=10 	Yield=false 	ms= 13532 	each= 1353
Data Source          Threads=10 	Yield=true 	ms= 13923 	each= 1392
Data Source          Threads=10 	Yield=true 	ms= 13829 	each= 1382
Connection Cache     Threads=10 	Yield=false 	ms= 12907 	each= 1290 	CacheSize=2, Active=0
Connection Cache     Threads=10 	Yield=false 	ms= 12907 	each= 1290 	CacheSize=2, Active=0
Connection Cache     Threads=10 	Yield=true 	ms= 12813 	each= 1281 	CacheSize=2, Active=0
Connection Cache     Threads=10 	Yield=true 	ms= 12813 	each= 1281 	CacheSize=2, Active=0

******************************************************************************/

	// Default no of threads to 10
	private static final int        NUM_OF_THREADS = 10;

	private static final String     DRIVER =
	//	"oci8";
		"thin";

	private static final String     CONNECTION =
	//	"jdbc:oracle:oci8:@";
	//	"jdbc:oracle:oci8:@dev1";
		"jdbc:oracle:thin:@//dev:1521/dev1";


	private static final String     UID = "adempiere";
	private static final String     PWD = "adempiere";
	private static final String     STATEMENT = "SELECT * FROM AD_Column";
	private static final boolean    WITH_OUTPUT = false;

	private static boolean          s_do_yield = true;

	private static Connection       s_sconn = null;
	private static Connection[]     s_conn = null;
	private static OracleDataSource s_ds = null;
//	private static OracleConnectionCacheImpl   s_cc = null;

	private static int              s_fetchSize = 10;

	//  Connection
	private static int              s_cType = 0;
	private static final String[]   C_INFO = {
		"Shared Connection    ",
		"Multiple Connections ",
		"Multiple PreCreated  ",
		"Data Source          ",
		"Connection Cache     "};
	private static final int        C_SHARED = 0;
	private static final int        C_MULTIPLE = 1;
	private static final int        C_PRECREATED = 2;
	private static final int        C_DATASOURCE = 3;
	private static final int        C_CACHE = 4;

	//  Data
	private static int              s_rType = 0;
	private static final String[]   R_INFO = {
		"ResultSet            ",
		"Cached RowSet        ",
		"JDBC RowSet          "};
	private static final int        R_RESULTSET = 0;
	private static final int        R_CACHED_ROWSET = 1;
	private static final int        R_JDBC_ROWSET = 2;



	/**
	 *  Main Test Start
	 *  @param args
	 */
	public static void main (String args [])
	{
		try
		{
			/* Load the JDBC driver */
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

			s_ds = new OracleDataSource();
			s_ds.setDriverType(DRIVER);
			s_ds.setServerName("dev");
			s_ds.setNetworkProtocol("tcp");
			s_ds.setDatabaseName("dev1");
			s_ds.setPortNumber(1521);
			s_ds.setUser("adempiere");
			s_ds.setPassword("adempiere");
			/*
			s_cc = new OracleConnectionCacheImpl();
			s_cc.setDriverType(DRIVER);
			s_cc.setServerName("dev");
			s_cc.setNetworkProtocol("tcp");
			s_cc.setDatabaseName("dev1");
			s_cc.setPortNumber(1521);
			s_cc.setUser("adempiere");
			s_cc.setPassword("adempiere");
			s_cc.setMaxLimit(NUM_OF_THREADS/4);
			s_cc.setCacheScheme(OracleConnectionCacheImpl.FIXED_WAIT_SCHEME);
		//	s_cc.setCacheScheme(OracleConnectionCacheImpl.DYNAMIC_SCHEME);
		 	 */

			s_fetchSize = 10;
			s_cType = C_MULTIPLE;
			statementTiming();
			statementTiming();
			statementTiming();
			s_cType = C_DATASOURCE;
			statementTiming();
			statementTiming();
			statementTiming();
			s_cType = C_CACHE;
			statementTiming();
			statementTiming();
			statementTiming();
			s_fetchSize = 20;
			s_cType = C_MULTIPLE;
			statementTiming();
			statementTiming();
			statementTiming();
			s_cType = C_DATASOURCE;
			statementTiming();
			statementTiming();
			statementTiming();
			s_cType = C_CACHE;
			statementTiming();
			statementTiming();
			statementTiming();
			//
			s_fetchSize = 10;       //  standard value
/**
			s_rType = R_JDBC_ROWSET;
			rowSetTiming();
			rowSetTiming();
			rowSetTiming();
			s_rType = R_CACHED_ROWSET;
			rowSetTiming();
			rowSetTiming();
			rowSetTiming();
**/
			//
			s_cType = C_SHARED;
			s_do_yield = false;
			runTest();
			runTest();
			s_do_yield = true;
			runTest();
			runTest();
			//
			s_cType = C_MULTIPLE;
			s_do_yield = false;
			runTest();
			runTest();
			s_do_yield = true;
			runTest();
			runTest();
			//
			s_cType = C_PRECREATED;
			s_do_yield = false;
			runTest();
			runTest();
			s_do_yield = true;
			runTest();
			runTest();
			//
			s_cType = C_DATASOURCE;
			s_do_yield = false;
			runTest();
			runTest();
			s_do_yield = true;
			runTest();
			runTest();
			//
			s_cType = C_CACHE;
			s_do_yield = false;
			runTest();
			runTest();
			s_do_yield = true;
			runTest();
			runTest();
			//

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}   //  main

	/*************************************************************************/

	/**
	 *  Run the test
	 *  @throws Exception
	 */
	static void runTest() throws Exception
	{
		// Create the threads
		Thread[] threadList = new Thread[NUM_OF_THREADS];
		s_conn = new Connection[NUM_OF_THREADS];

		if (s_cType == C_SHARED)
			s_sconn = DriverManager.getConnection (CONNECTION, UID, PWD);
		//
		// spawn threads
		for (int i = 0; i < NUM_OF_THREADS; i++)
		{
			if (s_cType == C_PRECREATED)
				s_conn[i] = DriverManager.getConnection (CONNECTION, UID, PWD);
			//
			threadList[i] = new JdbcTest(i);
			threadList[i].start();
		}
		// Start everyone at the same time
		long start = System.currentTimeMillis();
		setGreenLight ();
		//  wait for all threads to end
		for (int i = 0; i < NUM_OF_THREADS; i++)
			threadList[i].join();
		//
		if (s_sconn != null)
			s_sconn.close();
		s_sconn = null;
		for (int i = 0; i < NUM_OF_THREADS; i++)
		{
			if (s_conn[i] != null)
				s_conn[i].close();
			s_conn[i] = null;
		}
		long result = System.currentTimeMillis() - start;
		System.out.print (C_INFO[s_cType]
			+ "Threads=" + NUM_OF_THREADS
			+ " \tYield=" + s_do_yield
			+ " \tms= " + result
			+ " \teach= " + (result/NUM_OF_THREADS));
	//	if (s_cType == C_CACHE)
	//		System.out.print (" \tCacheSize=" + s_cc.getCacheSize() + ", Active=" + s_cc.getActiveSize());
		System.out.println();
	}   // runTest


	/**
	 *  Statement Timing
	 */
	private static void statementTiming()
	{
		try
		{
			long startConnection = System.currentTimeMillis();
			Connection conn = null;
			if (s_cType == C_MULTIPLE)
				conn = DriverManager.getConnection (CONNECTION, UID, PWD);
			if (s_cType == C_DATASOURCE)
				conn = s_ds.getConnection();
		//	if (s_cType == C_CACHE)
		//		conn = s_cc.getConnection();

			long startStatement = System.currentTimeMillis();
			Statement stmt = conn.createStatement ();
			stmt.setFetchSize(s_fetchSize);

			long startQuery = System.currentTimeMillis();
			ResultSet rs = stmt.executeQuery (STATEMENT);

			int i = 0;
			long startRetrieve = System.currentTimeMillis();
			while (rs.next())
			{
				rs.getString(1);
				i++;
			}
			long endRetrieve = System.currentTimeMillis();
		//  System.out.println(i);

			rs.close();
			rs = null;
			long endQuery = System.currentTimeMillis();

			stmt.close();
			stmt = null;
			long endStatement = System.currentTimeMillis();

			conn.close();
			conn = null;
			long endConnection = System.currentTimeMillis();

			//
			System.out.println(C_INFO[s_cType]
				+ "Fetch=" + s_fetchSize
				+ " \tConn=" + (startStatement - startConnection)
				+ " \tStmt=" + (startQuery - startStatement)
				+ " \tQuery=" + (startRetrieve - startQuery)
				+ " \tRetrieve=" + (endRetrieve - startRetrieve)
				+ " \tClRs=" + (endQuery - endRetrieve)
				+ " \tClStmt=" + (endStatement - endQuery)
				+ " \tClConn=" + (endConnection - endStatement)
				+ " \t- Total=" + (endConnection - startConnection)
				+ " \tStmt=" + (endStatement - startStatement)
				+ " \tQuery=" + (endQuery - startQuery));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}   //  statementTiming

	/**
	 *  Row Set Timing
	 */
	private static void rowSetTiming()
	{
		try
		{
			long startConnection = System.currentTimeMillis();
			RowSet rowset = null;
			/**
			if (s_rType == R_JDBC_ROWSET)
				rowset = new OracleJDBCRowSet ();
			else if (s_rType == R_CACHED_ROWSET)
				rowset = new OracleCachedRowSet();
			**/
			rowset.setUrl (CONNECTION);
			rowset.setUsername (UID);
			rowset.setPassword (PWD);
			rowset.setFetchSize(s_fetchSize);

			long startStatement = System.currentTimeMillis();
			rowset.setCommand (STATEMENT);

			long startQuery = System.currentTimeMillis();
			rowset.execute ();

			long startRetrieve = System.currentTimeMillis();
			while (rowset.next ())
			{
			}
			long endRetrieve = System.currentTimeMillis();
			long endQuery = System.currentTimeMillis();

			rowset.close();
			long endStatement = System.currentTimeMillis();
			long endConnection = System.currentTimeMillis();
			//
			System.out.println(R_INFO[s_rType]
				+ "Fetch=" + s_fetchSize
				+ " \tConn=" + (startStatement - startConnection)
				+ " \tStmt=" + (startQuery - startStatement)
				+ " \tQuery=" + (startRetrieve - startQuery)
				+ " \tRetrieve=" + (endRetrieve - startRetrieve)
				+ " \tClRs=" + (endQuery - endRetrieve)
				+ " \tClStmt=" + (endStatement - endQuery)
				+ " \tClConn=" + (endConnection - endStatement)
				+ " \t- Total=" + (endConnection - startConnection)
				+ " \tStmt=" + (endStatement - startStatement)
				+ " \tQuery=" + (endQuery - startQuery));
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}   //  rowSetTiming

	/*************************************************************************/

	/**
	 *  JDBC Test
	 *  @param id Thread ID
	 */
	public JdbcTest(int id)
	{
		super();
		m_myId = id;
	}   //  JdbcTest

	private int m_myId = 0;

	/**
	 *  Async Worker
	 */
	public void run()
	{
		ResultSet  rs   = null;
		Statement  stmt = null;

		try
		{
			while (!getGreenLight())
				yield();
			if (WITH_OUTPUT)
				System.out.println("Thread " + m_myId + " started");

			// Get the connection & statement
			if (s_cType == C_SHARED)
				stmt = s_sconn.createStatement ();
			else if (s_cType == C_MULTIPLE)
			{
				s_conn[m_myId] = DriverManager.getConnection (CONNECTION, UID, PWD);
				stmt = s_conn[m_myId].createStatement ();
			}
			else if (s_cType == C_PRECREATED)
			{
				stmt = s_conn[m_myId].createStatement ();
			}
			else if (s_cType == C_DATASOURCE)
			{
				s_conn[m_myId] = s_ds.getConnection();
				stmt = s_conn[m_myId].createStatement ();
			}
		//	else if (s_cType == C_CACHE)
		//	{
		//		s_conn[m_myId] = s_cc.getConnection();
		//		stmt = s_conn[m_myId].createStatement ();
		//	}
			stmt.setFetchSize(s_fetchSize);

			// Execute the Query
			rs = stmt.executeQuery (STATEMENT);

			// Loop through the results
			while (rs.next())
			{
				if (s_do_yield)
					yield();  // Yield To other threads
			}

			// Close all the resources
			rs.close();
			rs = null;

			// Close the statement
			stmt.close();
			stmt = null;

			// Close the local connection
			if (s_cType == C_SHARED || s_cType == C_PRECREATED)
				;
			else
			{
				s_conn[m_myId].close();
				s_conn[m_myId] = null;
			}
		}
		catch (Exception e)
		{
			System.out.println("Thread " + m_myId + " got Exception: " + e);
			e.printStackTrace();
			return;
		}
		if (WITH_OUTPUT)
			System.out.println("Thread " + m_myId + " finished");
	}

	/*************************************************************************/

	static boolean greenLight = false;
	static synchronized void setGreenLight () { greenLight = true; }
	synchronized boolean getGreenLight () { return greenLight; }

}   //  JdbcTest
