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
package org.compiere.process;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import javax.sql.RowSet;

import org.compiere.db.CConnection;
import org.compiere.interfaces.Server;
import org.compiere.model.MReplication;
import org.compiere.model.MReplicationLog;
import org.compiere.model.MReplicationRun;
import org.compiere.model.MSystem;
import org.compiere.util.CCachedRowSet;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Local (Central) Data Replication.
 * 	Note: requires migration technology
 *
 *  @author Jorg Janke
 *  @version $Id: ReplicationLocal.java,v 1.2 2006/07/30 00:51:01 jjanke Exp $
 */
public class ReplicationLocal extends SvrProcess
{
	/**	System Record				*/
	private	MSystem				m_system = null;
	/**	Replication Info			*/
	private MReplication 		m_replication = null;
	/**	Replication Run				*/
	private MReplicationRun 	m_replicationRun = null;
	/**	Test Flag					*/
	private Boolean				m_test = Boolean.FALSE;
	/**	Replication Flag			*/
	private boolean				m_replicated = true;
	/**	Remote Server				*/
	private Server 				m_serverRemote = null;
	private long				m_start = System.currentTimeMillis();
	/**	Date Run on Remote			*/
	private Timestamp			m_replicationStart = new Timestamp (m_start);

	/**	Logger						*/
	private static CLogger		s_log = CLogger.getCLogger(ReplicationLocal.class);

	/**	Remote class				*/
	private static String	REMOTE = "org.compiere.process.ReplicationRemote";
	protected static String	START = "com.adempiere.client.StartReplication";

	/**
	 *  Prepare - e.g., get Parameters.
	 */
	public void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("IsTest"))
				m_test = Boolean.valueOf("Y".equals (para[i].getParameter()));
			else
				log.log(Level.SEVERE, "prepare - Unknown Parameter: " + name);
		}
		m_system = MSystem.get (getCtx());
	}	//	prepare

	/**
	 *  Perform process.
	 *  @return Message
	 *  @throws Exception if not successful
	 */
	public String doIt() throws Exception
	{
		if (m_system == null || !m_system.isValid())
			return ("SystemNotSetupForReplication");
		//
		log.info("doIt - Record_ID=" + getRecord_ID() + ", test=" + m_test);
		connectRemote();
		//
		setupRemote();
		mergeData();
		sendUpdates();

		//	Save Info
		log.info("doIt - Replicated=" + m_replicated + " - " + m_replicationStart);
		m_replicationRun.setIsReplicated(m_replicated);
		double sec = (System.currentTimeMillis() - m_start);
		sec /= 1000;
		m_replicationRun.setDescription(sec + " s");
		m_replicationRun.save();
		if (m_replicated)
		{
			m_replication.setDateLastRun (m_replicationStart);
			m_replication.save();
		}
		//
		exit();
		return m_replicated ? "Replicated" : "Replication Error";
	}	//	doIt


	/**
	 * 	Connect to Remote Server
	 *	@throws java.lang.Exception
	 */
	private void connectRemote() throws Exception
	{
		//	Replication Info
		m_replication = new MReplication (getCtx(), getRecord_ID(), get_TrxName());
		//
		String AppsHost = m_replication.getHostAddress();
		int AppsPort = m_replication.getHostPort();
		
		CConnection connection = new CConnection(AppsHost);
		connection.setAppsPort(AppsPort);
		log.info (AppsHost + ":" + AppsPort);
		try
		{
			Server server = connection.getServer();
		//	log.fine("- ServerHome: " + serverHome);
			if (server == null)
				throw new Exception ("NoServer");
			m_serverRemote = server;
		//	log.fine("- Server: " + m_serverRemote);
		//	log.fine("- Remote Status = " + m_serverRemote.getStatus());
		}
		catch (Exception ex)
		{
			log.log(Level.SEVERE, "connectRemote", ex);
			throw new Exception (ex);
		}
	}	//	connectRemote


	/**
	 *	Setup Remote AD_System/AD_Table/AD_Sequence for Remote Management.
	 * 	@throws Exception
	 */
	private void setupRemote() throws Exception
	{
		log.info("setupRemote");
		//
		String sql = "SELECT rt.AD_Table_ID, rt.ReplicationType, t.TableName "
			+ "FROM AD_ReplicationTable rt"
			+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
			+ "WHERE rt.IsActive='Y' AND t.IsActive='Y'"
			+ " AND AD_ReplicationStrategy_ID=? "	//	#1
			+ "ORDER BY t.LoadSeq";
		RowSet rowset = getRowSet(sql, new Object[]{new Integer(m_replication.getAD_ReplicationStrategy_ID())});
		if (rowset == null)
			throw new Exception("setupRemote - No RowSet Data");

		//	Data Info
		RemoteSetupVO data = new RemoteSetupVO();
		data.Test = m_test;
		data.ReplicationTable = rowset;	//	RowSet
		data.IDRangeStart = m_replication.getIDRangeStart();
		data.IDRangeEnd   = m_replication.getIDRangeEnd();
		data.AD_Client_ID = m_replication.getRemote_Client_ID();
		data.AD_Org_ID = m_replication.getRemote_Org_ID();
		data.Prefix = m_replication.getPrefix();
		data.Suffix = m_replication.getSuffix();
		//	Process Info
		ProcessInfo pi = new ProcessInfo(data.toString(), 0);
		pi.setClassName (REMOTE);
		pi.setSerializableObject(data);
		Object result = doIt(START, "init", new Object[]{m_system});
		if (result == null || !Boolean.TRUE.equals(result))
			throw new Exception("setupRemote - Init Error - " + result);
		//	send it
		pi = m_serverRemote.process (Env.newTemporaryCtx(), pi);
		ProcessInfoLog[] logs = pi.getLogs();
		Timestamp dateRun = null;
		if (logs != null && logs.length > 0)
			dateRun = logs[0].getP_Date();	//	User Remote Timestamp!
		//
		log.info ("setupRemote - " + pi + " - Remote Timestamp = " + dateRun);
		if (dateRun != null)
			m_replicationStart = dateRun;
		m_replicationRun = new MReplicationRun (getCtx(), m_replication.getAD_Replication_ID(), m_replicationStart, get_TrxName());
		m_replicationRun.save();
	}	//	setupRemote

	/*************************************************************************/

	/**
	 * 	Receive new Data from Remote.
	 * 	@throws Exception
	 */
	private void mergeData() throws Exception
	{
		log.info("mergeData");
		//
		String sql = "SELECT rt.AD_Table_ID, rt.ReplicationType, t.TableName, rt.AD_ReplicationTable_ID "
			+ "FROM AD_ReplicationTable rt"
			+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
			+ "WHERE rt.IsActive='Y' AND t.IsActive='Y'"
			+ " AND AD_ReplicationStrategy_ID=?" 	//	#1
			+ " AND rt.ReplicationType='M' "		//	Merge
			+ "ORDER BY t.LoadSeq";
		RowSet rowset = getRowSet(sql, new Object[]{new Integer(m_replication.getAD_ReplicationStrategy_ID())});
		try
		{
			while (rowset.next())
				mergeDataTable (rowset.getInt(1), rowset.getString(3), rowset.getInt(4));
			rowset.close();
			rowset = null;
		}
		catch (SQLException ex)
		{
			log.log(Level.SEVERE, "mergeData", ex);
			m_replicated = false;
		}
	}	//	mergeData

	/**
	 * 	Receive New Data from Remote (and send local updates)
	 * 	@param AD_Table_ID table id
	 * 	@param TableName table name
	 * 	@param AD_ReplicationTable_ID id
	 * 	@return true if success
	 * 	@throws java.lang.Exception
	 */
	private boolean mergeDataTable (int AD_Table_ID, String TableName, int AD_ReplicationTable_ID) throws Exception
	{
		RemoteMergeDataVO data = new RemoteMergeDataVO();
		data.Test = m_test;
		data.TableName = TableName;
		//	Create SQL
		StringBuffer sql = new StringBuffer("SELECT * FROM ")
			.append(TableName)
			.append(" WHERE AD_Client_ID=").append(m_replication.getRemote_Client_ID());
		if (m_replication.getRemote_Org_ID() != 0)
			sql.append(" AND AD_Org_ID IN (0,").append(m_replication.getRemote_Org_ID()).append(")");
		if (m_replication.getDateLastRun() != null)
			sql.append(" AND Updated >= ").append(DB.TO_DATE(m_replication.getDateLastRun(), false));
		sql.append(" ORDER BY ");
		data.KeyColumns = getKeyColumns(AD_Table_ID);
		if (data.KeyColumns == null || data.KeyColumns.length == 0)
		{
			log.log(Level.SEVERE, "mergeDataTable - No KeyColumns for " + TableName);
			m_replicated = false;
			return false;
		}
		for (int i = 0; i < data.KeyColumns.length; i++)
		{
			if (i > 0)
				sql.append(",");
			sql.append(data.KeyColumns[i]);
		}
		data.Sql = sql.toString();
		//	New Central Data
		data.CentralData = getRowSet(data.Sql, null);
		if (data.CentralData == null)
		{
			log.fine("mergeDataTable - CentralData is Null - " + TableName);
			m_replicated = false;
			return false;
		}

		//	Process Info
		ProcessInfo pi = new ProcessInfo("MergeData", 0);
		pi.setClassName (REMOTE);
		pi.setSerializableObject(data);
		//	send it
		pi = m_serverRemote.process (Env.newTemporaryCtx(), pi);
		ProcessInfoLog[] logs = pi.getLogs();
		String msg = "< ";
		if (logs != null && logs.length > 0)
			msg += logs[0].getP_Msg();	//	Remote Message
		log.info("mergeDataTable - " + pi);
		//
		MReplicationLog rLog = new MReplicationLog (getCtx(), m_replicationRun.getAD_Replication_Run_ID(), AD_ReplicationTable_ID, msg, get_TrxName());
		if (pi.isError())
		{
			log.severe ("mergeDataTable Error - " + pi);
			m_replicated = false;
			rLog.setIsReplicated(false);
		}
		else	//	import data fom remote
		{
			RowSet sourceRS = (RowSet)pi.getSerializableObject();
			RowSet targetRS = getRowSet(data.Sql, null);
			Object result = doIt (START, "sync", new Object[]	//	Merge
				{data.TableName, data.KeyColumns, sourceRS, targetRS, m_test, Boolean.TRUE});
			boolean replicated = isReplicated(result);
			if (replicated)
				log.fine("mergeDataTable -> " + TableName + " - " + result);
			else
			{
				m_replicated = false;
				log.severe ("mergeDataTable -> " + TableName + " - " + result);
			}
			rLog.setIsReplicated(replicated);
			if (result != null)
				rLog.setP_Msg("< " + result.toString());
			sourceRS.close();
			sourceRS = null;
			targetRS.close();
			targetRS = null;
		}
		rLog.save();
		return !pi.isError();
	}	//	mergeDataTable

	/**
	 * 	Get Key Columns (PK or FK) of Table
	 *	@param AD_Table_ID id
	 *	@return Key Columns
	 */
	public String[] getKeyColumns (int AD_Table_ID)
	{
		ArrayList<String> list = new ArrayList<String>();
		PreparedStatement pstmt = null;
		try
		{
			//	Get Keys
			String sql = "SELECT ColumnName FROM AD_Column "
				+ "WHERE AD_Table_ID=?"
				+ " AND IsKey='Y'";
			pstmt = DB.prepareStatement(sql, get_TrxName());
			pstmt.setInt(1, AD_Table_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
				list.add(rs.getString(1));
			rs.close();

			//	no keys - search for parents
			if (list.size() == 0)
			{
				sql = "SELECT ColumnName FROM AD_Column "
					+ "WHERE AD_Table_ID=?"
					+ " AND IsParent='Y'";
				pstmt = DB.prepareStatement(sql, get_TrxName());
				pstmt.setInt(1, AD_Table_ID);
				rs = pstmt.executeQuery();
				while (rs.next())
					list.add(rs.getString(1));
				rs.close();
			}
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, "getKeyColumns", e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
		}
		catch (Exception e)
		{
		}

		//	Convert to Array
		String[] retValue = new String[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getKeyColumns

	/*************************************************************************/

	/**
	 *	Send Updates to Remote (i.e. r/o on remote)
	 *	@throws Exception
	 */
	private void sendUpdates() throws Exception
	{
		log.info("sendUpdates");
		//
		String sql = "SELECT rt.AD_Table_ID, rt.ReplicationType, t.TableName, rt.AD_ReplicationTable_ID "
			+ "FROM AD_ReplicationTable rt"
			+ " INNER JOIN AD_Table t ON (rt.AD_Table_ID=t.AD_Table_ID) "
			+ "WHERE rt.IsActive='Y' AND t.IsActive='Y'"
			+ " AND AD_ReplicationStrategy_ID=?" 	//	#1
			+ " AND rt.ReplicationType='R' "		//	Reference
			+ "ORDER BY t.LoadSeq";
		RowSet rowset = getRowSet(sql, new Object[]{new Integer(m_replication.getAD_ReplicationStrategy_ID())});
		try
		{
			while (rowset.next())
				sendUpdatesTable (rowset.getInt(1), rowset.getString(3), rowset.getInt(4));
			rowset.close();
		}
		catch (SQLException ex)
		{
			log.log(Level.SEVERE, "sendUpdates", ex);
			m_replicated = false;
		}
	}	//	sendUpdates

	/**
	 * 	Send UPdates to Remote
	 * 	@param AD_Table_ID table id
	 * 	@param TableName table
	 * 	@param AD_ReplicationTable_ID id
	 * 	@return true if success
	 * 	@throws Exception
	 */
	private boolean sendUpdatesTable (int AD_Table_ID, String TableName, int AD_ReplicationTable_ID) throws Exception
	{
		RemoteUpdateVO data = new RemoteUpdateVO();
		data.Test = m_test;
		data.TableName = TableName;
		//	Create SQL
		StringBuffer sql = new StringBuffer ("SELECT * FROM ")
			.append(TableName)
			.append(" WHERE AD_Client_ID=").append(m_replication.getRemote_Client_ID());
		if (m_replication.getRemote_Org_ID() != 0)
			sql.append(" AND AD_Org_ID IN (0,").append(m_replication.getRemote_Org_ID()).append(")");
		if (m_replication.getDateLastRun() != null)
			sql.append(" AND Updated >= ").append(DB.TO_DATE(m_replication.getDateLastRun(), false));
		sql.append(" ORDER BY ");
		data.KeyColumns = getKeyColumns(AD_Table_ID);
		if (data.KeyColumns == null || data.KeyColumns.length == 0)
		{
			log.log(Level.SEVERE, "sendUpdatesTable - No KeyColumns for " + TableName);
			m_replicated = false;
			return false;
		}
		for (int i = 0; i < data.KeyColumns.length; i++)
		{
			if (i > 0)
				sql.append(",");
			sql.append(data.KeyColumns[i]);
		}
		data.Sql = sql.toString();
		//	New Data
		data.CentralData = getRowSet(data.Sql, null);
		if (data.CentralData == null)
		{
			log.fine("sendUpdatesTable - Null - " + TableName);
			m_replicated = false;
			return false;
		}
		int rows = 0;
		try
		{
			if (data.CentralData.last())
				rows = data.CentralData.getRow();
			data.CentralData.beforeFirst();		//	rewind
		}
		catch (SQLException ex)
		{
			log.fine("RowCheck  " + ex);
			m_replicated = false;
			return false;
		}
		if (rows == 0)
		{
			log.fine("No Rows - " + TableName);
			return true;
		}
		else
			log.fine(TableName + " #" + rows);

		//	Process Info
		ProcessInfo pi = new ProcessInfo("SendUpdates", 0);
		pi.setClassName (REMOTE);
		pi.setSerializableObject(data);
		//	send it
		pi = m_serverRemote.process (Env.newTemporaryCtx(), pi);
		log.info("sendUpdatesTable - " + pi);
		ProcessInfoLog[] logs = pi.getLogs();
		String msg = "> ";
		if (logs != null && logs.length > 0)
			msg += logs[0].getP_Msg();	//	Remote Message
		//
		MReplicationLog rLog = new MReplicationLog (getCtx(), m_replicationRun.getAD_Replication_Run_ID(), AD_ReplicationTable_ID, msg, get_TrxName());
		if (pi.isError())
			m_replicated = false;
		rLog.setIsReplicated(!pi.isError());
		rLog.save();
		return !pi.isError();
	}	//	sendUpdatesTable

	/**
	 * 	Clean up resources (connections)
	 */
	private void exit()
	{
		log.info ("exit");
		Object result = doIt(START, "exit", null);
		ProcessInfo pi = new ProcessInfo("Exit", 0);
		pi.setClassName (REMOTE);
		pi.setSerializableObject(m_replicationStart);
		//	send it
		try
		{
			m_serverRemote.process (Env.newTemporaryCtx(), pi);
		}
		catch (Exception ex)
		{
		}
	}	//	exit

	
	/**************************************************************************
	 * 	Get RowSet of Local Connection
	 *	@param sql sql
	 * 	@param args optional argument array - supported: Integer, Timestamp, BigDecimal - rest is concerted to String
	 *	@return row set
	 */
	public static RowSet getRowSet (String sql, Object[] args)
	{
		//	shared connection
		Connection conn = DB.getConnectionRO();
		PreparedStatement pstmt = null;
		RowSet rowSet = null;
		//
		try
		{
			pstmt = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			//	Set Parameters
			if (args != null)
			{
				for (int i = 0; i < args.length; i++)
				{
					if (args[i] == null)
						s_log.log(Level.SEVERE, "NULL Argument " + i);
					else if (args[i] instanceof Integer)
						pstmt.setInt(i+1, ((Integer)args[i]).intValue());
					else if (args[i] instanceof Timestamp)
						pstmt.setTimestamp(i+1, (Timestamp)args[i]);
					else if (args[i] instanceof BigDecimal)
						pstmt.setBigDecimal(i+1, (BigDecimal)args[i]);
					else
						pstmt.setString(i+1, args[i].toString());
				}
			}
			//
			ResultSet rs = pstmt.executeQuery();
			rowSet = CCachedRowSet.getRowSet(rs);
			pstmt.close();
			pstmt = null;
		}
		catch (Exception ex)
		{
			s_log.log(Level.SEVERE, sql, ex);
			throw new RuntimeException (ex);
		}
		//	Close Cursor
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, "close pstmt", e);
		}
			
		return rowSet;
	}	//	getRowSet

	/**
	 * 	Is data successful replicated
	 *	@param result sync return value
	 *	@return true if replicated
	 */
	public static boolean isReplicated (Object result)
	{
		boolean replicated = result != null && !Boolean.FALSE.equals(result);
		if (replicated)
			replicated = result.toString().endsWith("Errors=0");
		return replicated;
	}	//	isReplicated

}	//	ReplicationLocal
