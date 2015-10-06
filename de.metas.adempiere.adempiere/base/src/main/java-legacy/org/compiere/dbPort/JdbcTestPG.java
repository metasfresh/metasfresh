/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.
 * This program is free software; you can redistribute it and/or modify it
 * under the terms version 2 of the GNU General Public License as published
 * by the Free Software Foundation. This program is distributed in the hope
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 * You may reach us at: ComPiere, Inc. - http://www.compiere.org/license.html
 * 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA or info@compiere.org 
 *****************************************************************************/
package org.compiere.dbPort;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *  JDBC Performance Test.
 *
 *  @author     Jorg Janke
 *  @version    $Id: JdbcTestPG.java,v 1.4 2005/03/11 20:29:03 jjanke Exp $
 */
public class JdbcTestPG extends Thread
{
/*****************************************************************************

Multiple Connections Fetch=10 	Conn=407 	Stmt=15 	Query=1516 	Retrieve=203 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=2141 	Stmt=1734 	Query=1719
Multiple Connections Fetch=10 	Conn=47 	Stmt=0 	Query=1234 	Retrieve=31 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1312 	Stmt=1265 	Query=1265
Multiple Connections Fetch=10 	Conn=31 	Stmt=0 	Query=1266 	Retrieve=15 	ClRs=0 	ClStmt=0 	ClConn=0 	- Total=1312 	Stmt=1281 	Query=1281
Shared Connection    Threads=10 	Yield=false 	ms= 13047 	each= 1304
Shared Connection    Threads=10 	Yield=false 	ms= 12891 	each= 1289
Shared Connection    Threads=10 	Yield=true 	ms= 13422 	each= 1342
Shared Connection    Threads=10 	Yield=true 	ms= 12969 	each= 1296
Multiple Connections Threads=10 	Yield=false 	ms= 13046 	each= 1304
Multiple Connections Threads=10 	Yield=false 	ms= 12891 	each= 1289
Multiple Connections Threads=10 	Yield=true 	ms= 13062 	each= 1306
Multiple Connections Threads=10 	Yield=true 	ms= 13062 	each= 1306
Multiple PreCreated  Threads=10 	Yield=false 	ms= 9968 	each= 996
Multiple PreCreated  Threads=10 	Yield=false 	ms= 10250 	each= 1025
Multiple PreCreated  Threads=10 	Yield=true 	ms= 10109 	each= 1010
Multiple PreCreated  Threads=10 	Yield=true 	ms= 9906 	each= 990

******************************************************************************/

	// Default no of threads to 10
	private static final int        NUM_OF_THREADS = 10;

	private static final String     CONNECTION =
		"jdbc:postgresql://linux:5432/compiere";

	private static final String     UID = "compiere";
	private static final String     PWD = "compiere";
	private static final String     STATEMENT = "SELECT * FROM AD_Column";
	private static final boolean    WITH_OUTPUT = false;

	private static boolean          s_do_yield = true;

	private static Connection       s_sconn = null;
	private static Connection[]     s_conn = null;

	private static int              s_fetchSize = 10;

	//  Connection
	private static int              s_cType = 0;
	private static final String[]   C_INFO = {
		"Shared Connection    ",
		"Multiple Connections ",
		"Multiple PreCreated  ",
	//	"Data Source          ",
	//	"Connection Cache     "
		};
	private static final int        C_SHARED = 0;
	private static final int        C_MULTIPLE = 1;
	private static final int        C_PRECREATED = 2;
//	private static final int        C_DATASOURCE = 3;
//	private static final int        C_CACHE = 4;

	//  Data
	private static int              s_rType = 0;
	private static final String[]   R_INFO = {
		"ResultSet            ",
//		"Cached RowSet        ",
//		"JDBC RowSet          "
		};
	private static final int        R_RESULTSET = 0;
//	private static final int        R_CACHED_ROWSET = 1;
//	private static final int        R_JDBC_ROWSET = 2;


	/**
	 *  Main Test
	 *  @param args
	 */
	public static void main (String args [])
	{
		try
		{
			/* Load the JDBC driver */
			DriverManager.registerDriver(new org.postgresql.Driver());

			s_cType = C_MULTIPLE;
			statementTiming();
			statementTiming();
			statementTiming();
			//
			s_fetchSize = 10;       //  standard value

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
			if (WITH_OUTPUT)
				System.out.println("Starting #" + i);
			if (s_cType == C_PRECREATED)
				s_conn[i] = DriverManager.getConnection (CONNECTION, UID, PWD);
			//
			threadList[i] = new JdbcTestPG(i);
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

			long startStatement = System.currentTimeMillis();
			Statement stmt = conn.createStatement ();
		//	stmt.setFetchSize(s_fetchSize);

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
		//	System.out.println(i);

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


	/*************************************************************************/

	/**
	 *  JdbcTest Thread
	 *  @param id Thread ID
	 */
	public JdbcTestPG (int id)
	{
		super();
		m_myId = id;
	}

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
			if (WITH_OUTPUT)
				System.out.println("Thread " + m_myId + " waiting");
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
		//	stmt.setFetchSize(s_fetchSize);

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

}   //  JdbcTestPG
