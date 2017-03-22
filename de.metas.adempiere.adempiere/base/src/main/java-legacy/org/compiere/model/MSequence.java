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
package org.compiere.model;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *	Sequence Model.
 *	@see org.compiere.process.SequenceCheck
 *  @author Jorg Janke
 *  @version $Id: MSequence.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 */
public class MSequence extends X_AD_Sequence
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6827013120475678483L;

	public static final String SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID = "DICTIONARY_ID_USE_CENTRALIZED_ID";
	public static final String SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID = "PROJECT_ID_USE_CENTRALIZED_ID";
	
	/** Use SQL procedure to get next id			*/
	  //begin vpj-cd e-evolution 02/11/2005  PostgreSQL
	//private static final boolean USE_PROCEDURE = true;
	private static boolean USE_PROCEDURE = false;
	//end vpj-cd e-evolution 02/11/2005

	public static final int QUERY_TIME_OUT = 10;

	public static int getNextID (int AD_Client_ID, String TableName)
	{
		return getNextID(AD_Client_ID, TableName, ITrx.TRXNAME_None);
	}

	/**
	 *
	 *	Get next number for Key column = 0 is Error.
	 *  @param AD_Client_ID client
	 *  @param TableName table name
	 * 	@param trxName_NOT_USED deprecated, not used anymore. The value is obtained out of transaction (task 08240).
	 *  @return next no or (-1=not found, -2=error)
	 */
	// metas: 01558 - refactored in order to use newly introduced methods
	public static int getNextID (int AD_Client_ID, String TableName, String trxName_NOT_USED)
	{
		// FIXME: 08240 because we had big issues with AD_Sequence getting locked, we decided to acquire next sequence out of transaction (as a workaround) 
		final String trxName = ITrx.TRXNAME_None;
		
		if (TableName == null || TableName.length() == 0)
			throw new IllegalArgumentException("TableName missing");

		int retValue = -1;

		//	Check AdempiereSys
		final boolean adempiereSys = isAdempiereSys(AD_Client_ID);
		//
		if (s_log.isTraceEnabled())
			s_log.trace(TableName + " - AdempiereSys=" + adempiereSys  + " [" + trxName + "]");
		  //begin vpj-cd e-evolution 09/02/2005 PostgreSQL
		final String selectSQL = "SELECT CurrentNext, CurrentNextSys, IncrementNo, AD_Sequence_ID "
				+ "FROM AD_Sequence "
				+ "WHERE Name=?"
				+ " AND IsActive='Y' AND IsTableID='Y' AND IsAutoSequence='Y' "
				+ " FOR UPDATE OF AD_Sequence ";
		USE_PROCEDURE=false;
		

		//hengsin: executing getNextID in transaction create huge performance and locking issue
		//Trx trx = trxName == null ? null : Trx.get(trxName, true);
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		for (int i = 0; i < 3; i++)
		{
			try
			{
				conn = DB.getConnectionID();
				//	Error
				if (conn == null)
					return -1;

				pstmt = conn.prepareStatement(selectSQL,
					ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
				pstmt.setString(1, TableName);
				//
				if (!USE_PROCEDURE && DB.getDatabase().isQueryTimeoutSupported())
					pstmt.setQueryTimeout(QUERY_TIME_OUT);
				rs = pstmt.executeQuery();
				if (LogManager.isLevelFinest())
					s_log.trace("AC=" + conn.getAutoCommit() + ", RO=" + conn.isReadOnly()
						+ " - Isolation=" + conn.getTransactionIsolation() + "(" + Connection.TRANSACTION_READ_COMMITTED
						+ ") - RSType=" + pstmt.getResultSetType() + "(" + ResultSet.TYPE_SCROLL_SENSITIVE
						+ "), RSConcur=" + pstmt.getResultSetConcurrency() + "(" + ResultSet.CONCUR_UPDATABLE
						+ ")");
				if (rs.next())
				{

					int AD_Sequence_ID = rs.getInt(4);
					boolean gotFromHTTP = false;

					// If maintaining official dictionary try to get the ID from http official server
					if (isQueryCentralizedIDServer(TableName, AD_Client_ID))
					{
						// get ID from http site
						retValue = getNextOfficialID_HTTP(TableName);
						if (retValue > 0) {
							PreparedStatement updateSQL;
							updateSQL = conn.prepareStatement("UPDATE AD_Sequence SET CurrentNextSys = ? + 1 WHERE AD_Sequence_ID = ?");
							try {
								updateSQL.setInt(1, retValue);
								updateSQL.setInt(2, AD_Sequence_ID);
								updateSQL.executeUpdate();
							} finally {
								updateSQL.close();
							}
						}
						gotFromHTTP = true;
					}

					// If not official dictionary try to get the ID from http custom server - if configured
					if (isQueryProjectIDServer(TableName, AD_Client_ID))
					{
							// get ID from http site
							retValue = getNextProjectID_HTTP(TableName);
							if (retValue > 0) {
								PreparedStatement updateSQL;
								updateSQL = conn.prepareStatement("UPDATE AD_Sequence SET CurrentNext = GREATEST(CurrentNext, ? + 1) WHERE AD_Sequence_ID = ?");
								try {
									updateSQL.setInt(1, retValue);
									updateSQL.setInt(2, AD_Sequence_ID);
									updateSQL.executeUpdate();
								} finally {
									updateSQL.close();
								}
							}
							gotFromHTTP = true;
					}

					if (! gotFromHTTP) {
						//
						if (USE_PROCEDURE)
						{
							retValue = nextID(conn, AD_Sequence_ID, adempiereSys);
						}
						else
						{
							PreparedStatement updateSQL;
							int incrementNo = rs.getInt(3);
							if (adempiereSys) {
								updateSQL = conn
										.prepareStatement("UPDATE AD_Sequence SET CurrentNextSys = CurrentNextSys + ? WHERE AD_Sequence_ID = ?");
								retValue = rs.getInt(2);
							} else {
								updateSQL = conn
										.prepareStatement("UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ?");
								retValue = rs.getInt(1);
							}
							try {
								updateSQL.setInt(1, incrementNo);
								updateSQL.setInt(2, AD_Sequence_ID);
								updateSQL.executeUpdate();
							} finally {
								updateSQL.close();
							}
						}
					}

					//if (trx == null)
					conn.commit();
				}
				else
					s_log.error("No record found - " + TableName);

				//
				break;		//	EXIT
			}
			catch (Exception e)
			{
				s_log.error(TableName + " - " + e.getMessage(), e);
				try
				{
					if (conn != null)
						conn.rollback();
				} catch (SQLException e1) { }
			}
			finally
			{
				DB.close(rs, pstmt);
				pstmt = null;
				rs = null;
				if (conn != null)
				{
					try {
						conn.close();
					} catch (SQLException e) {}
					conn = null;
				}
			}
			Thread.yield();		//	give it time
		}


		//s_log.trace(retValue + " - Table=" + TableName + " [" + trx + "]");
		return retValue;
	}	//	getNextID

	/**
	 * 	Get Next ID
	 *	@param conn connection
	 *	@param AD_Sequence_ID sequence
	 *	@param adempiereSys sys
	 *	@return next id or -1 (error) or -3 (parameter)
	 */
	static int nextID (Connection conn, int AD_Sequence_ID, boolean adempiereSys)
	{
		if (conn == null || AD_Sequence_ID == 0)
			return -3;
		//
		int retValue = -1;
		String sqlUpdate = "{call nextID(?,?,?)}";
		CallableStatement cstmt = null;
		try
		{
			cstmt = conn.prepareCall (sqlUpdate,
				ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			cstmt.setInt(1, AD_Sequence_ID);
			cstmt.setString(2, adempiereSys ? "Y" : "N");
			cstmt.registerOutParameter(3, Types.INTEGER);
			if (DB.getDatabase().isQueryTimeoutSupported())
			{
				cstmt.setQueryTimeout(QUERY_TIME_OUT);
			}
			cstmt.execute();
			retValue = cstmt.getInt(3);
		}
		catch (Exception e)
		{
			s_log.error(e.toString());
		}
		finally
		{
			DB.close(cstmt);
		}
		return retValue;
	}	//	nextID

	/**
	 * Get next id by year
	 * @param conn
	 * @param AD_Sequence_ID
	 * @param incrementNo
	 * @param calendarYear
	 * @return next id
	 */
	static int nextIDByYear(Connection conn, int AD_Sequence_ID,
			int incrementNo, String calendarYear) {
		if (conn == null || AD_Sequence_ID == 0)
			return -3;
		//
		int retValue = -1;
		String sqlUpdate = "{call nextIDByYear(?,?,?,?)}";
		CallableStatement cstmt = null;
		try {
			cstmt = conn.prepareCall(sqlUpdate, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			cstmt.setInt(1, AD_Sequence_ID);
			cstmt.setInt(2, incrementNo);
			cstmt.setString(3, calendarYear);
			cstmt.registerOutParameter(4, Types.INTEGER);
			if (DB.getDatabase().isQueryTimeoutSupported())
			{
				cstmt.setQueryTimeout(QUERY_TIME_OUT);
			}
			cstmt.execute();
			retValue = cstmt.getInt(4);
		} catch (Exception e) {
			s_log.error(e.toString());
		} finally {
			DB.close(cstmt);
		}
		return retValue;
	} // nextID

	/**************************************************************************
	 *	Check/Initialize Client DocumentNo/Value Sequences
	 *	@param ctx context
	 *	@param AD_Client_ID client
	 *	@param trxName transaction
	 *	@return true if no error
	 */
	public static boolean checkClientSequences (Properties ctx, int AD_Client_ID, String trxName)
	{
		String sql = "SELECT TableName "
			+ "FROM AD_Table t "
			+ "WHERE IsActive='Y' AND IsView='N'"
			//	Get all Tables with DocumentNo or Value
			+ " AND AD_Table_ID IN "
				+ "(SELECT AD_Table_ID FROM AD_Column " + "WHERE ColumnName = 'DocumentNo' OR ColumnName = 'Value')"
			//	Ability to run multiple times
			+ " AND 'DocumentNo_' || TableName NOT IN (SELECT Name FROM AD_Sequence s " + "WHERE s.AD_Client_ID=?)";
		int counter = 0;
		boolean success = true;
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				String tableName = rs.getString(1);
				s_log.debug("Add: " + tableName);
				MSequence seq = new MSequence (ctx, AD_Client_ID, tableName, trxName);
				if (seq.save())
					counter++;
				else
				{
					s_log.error("Not created - AD_Client_ID=" + AD_Client_ID
						+ " - "  + tableName);
					success = false;
				}
			}
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
		s_log.info("AD_Client_ID=" + AD_Client_ID
			+ " - created #" + counter
			+ " - success=" + success);
		return success;
	}	//	checkClientSequences

	/**
	 * 	Get Sequence
	 *	@param ctx context
	 *	@param tableName table name
	 *	@return Sequence
	 * @deprecated Please use {@link ISequenceDAO#retrieveTableSequenceOrNull(Properties, String)}
	 */
	@Deprecated
	public static MSequence get (Properties ctx, String tableName)
	{
		return get(ctx, tableName, null);
	}

	/**
	 * 	Get Sequence
	 *	@param ctx context
	 *	@param tableName table name
	 *  @param trxName optional transaction name
	 *	@return Sequence
	 * @deprecated Please use {@link ISequenceDAO#retrieveTableSequenceOrNull(Properties, String, String)}
	 */
	@Deprecated
	public static MSequence get (Properties ctx, String tableName, String trxName)
	{
		final I_AD_Sequence seq = Services.get(ISequenceDAO.class).retrieveTableSequenceOrNull(ctx, tableName, trxName);
		final MSequence seqPO = LegacyAdapters.convertToPO(seq);
		return seqPO;
	}	//	get


	/**	Sequence for Table Document No's	*/
	public static final String PREFIX_DOCSEQ = "DocumentNo_";
	/**	Start Number			*/
	public static final int		INIT_NO = 1000000;	//	1 Mio
	/**	Start System Number		*/
	// public static final int		INIT_SYS_NO = 100; // start number for Compiere
	public static final int		INIT_SYS_NO = 50000;   // start number for Adempiere
	/** Static Logger			*/
	private static Logger 		s_log = LogManager.getLogger(MSequence.class);


	/**************************************************************************
	 *	Standard Constructor
	 *	@param ctx context
	 *	@param AD_Sequence_ID id
	 *	@param trxName transaction
	 */
	public MSequence (Properties ctx, int AD_Sequence_ID, String trxName)
	{
		super(ctx, AD_Sequence_ID, trxName);
		if (AD_Sequence_ID == 0)
		{
		//	setName (null);
			//
			setIsTableID(false);
			setStartNo (INIT_NO);
			setCurrentNext (INIT_NO);
			setCurrentNextSys (INIT_SYS_NO);
			setIncrementNo (1);
			setIsAutoSequence (true);
			setIsAudited(false);
			setStartNewYear(false);
		}
	}	//	MSequence

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MSequence (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MSequence

	/**
	 * 	New Document Sequence Constructor
	 *	@param ctx context
	 *	@param AD_Client_ID owner
	 *	@param tableName name
	 *	@param trxName transaction
	 */
	public MSequence (Properties ctx, int AD_Client_ID, String tableName, String trxName)
	{
		this (ctx, 0, trxName);
		setClientOrg(AD_Client_ID, 0);			//	Client Ownership
		setName(PREFIX_DOCSEQ + tableName);
		setDescription("DocumentNo/Value for Table " + tableName);
	}	//	MSequence;

	/**
	 * 	New Document Sequence Constructor
	 *	@param ctx context
	 *	@param AD_Client_ID owner
	 *	@param sequenceName name
	 *	@param StartNo start
	 *	@param trxName trx
	 */
	public MSequence (Properties ctx, int AD_Client_ID, String sequenceName, int StartNo, String trxName)
	{
		this (ctx, 0, trxName);
		setClientOrg(AD_Client_ID, 0);			//	Client Ownership
		setName(sequenceName);
		setDescription(sequenceName);
		setStartNo(StartNo);
		setCurrentNext(StartNo);
		setCurrentNextSys(StartNo/10);
	}	//	MSequence;


	/**************************************************************************
	 * 	Get Next No and increase current next
	 *	@return next no to use
	 */
	public int getNextID()
	{
		int retValue = getCurrentNext();
		setCurrentNext(retValue + getIncrementNo());
		return retValue;
	}	//	getNextNo

	/**
	 *	Get next number for Key column
	 *  @param AD_Client_ID client
	 *  @param TableName table name
	 * 	@param trxName optional Transaction Name
	 *  @return next no or (-1=error)
	 */
	public static synchronized int getNextOfficialID_HTTP (String TableName)
	{
		String website = MSysConfig.getValue("DICTIONARY_ID_WEBSITE"); // "http://developer.adempiere.com/cgi-bin/get_ID";
		String prm_USER = MSysConfig.getValue("DICTIONARY_ID_USER");  // "globalqss";
		String prm_PASSWORD = MSysConfig.getValue("DICTIONARY_ID_PASSWORD");  // "password_inseguro";
		String prm_TABLE = TableName;
		String prm_ALTKEY = "";  // TODO: generate alt-key based on key of table
		String prm_COMMENT = MSysConfig.getValue("DICTIONARY_ID_COMMENTS");
		String prm_PROJECT = new String("Adempiere");

		return getNextID_HTTP(TableName, website, prm_USER,
				prm_PASSWORD, prm_TABLE, prm_ALTKEY, prm_COMMENT, prm_PROJECT);
	}

	/**
	 *	Get next number for Key column
	 *  @param AD_Client_ID client
	 *  @param TableName table name
	 * 	@param trxName optional Transaction Name
	 *  @return next no or (-1=error)
	 */
	public static synchronized int getNextProjectID_HTTP (String TableName)
	{
		String website = MSysConfig.getValue("PROJECT_ID_WEBSITE"); // "http://developer.adempiere.com/cgi-bin/get_ID";
		String prm_USER = MSysConfig.getValue("PROJECT_ID_USER");  // "globalqss";
		String prm_PASSWORD = MSysConfig.getValue("PROJECT_ID_PASSWORD");  // "password_inseguro";
		String prm_TABLE = TableName;
		String prm_ALTKEY = "";  // TODO: generate alt-key based on key of table
		String prm_COMMENT = MSysConfig.getValue("PROJECT_ID_COMMENTS");
		String prm_PROJECT = MSysConfig.getValue("PROJECT_ID_PROJECT");

		return getNextID_HTTP(TableName, website, prm_USER,
				prm_PASSWORD, prm_TABLE, prm_ALTKEY, prm_COMMENT, prm_PROJECT);
	}

	private static int getNextID_HTTP(String TableName,
			String website, String prm_USER, String prm_PASSWORD,
			String prm_TABLE, String prm_ALTKEY, String prm_COMMENT,
			String prm_PROJECT) {
		final StringBuilder response = new StringBuilder();
		int retValue = -1;
		try {
			String completeUrl = website + "?" + "USER="
					+ URLEncoder.encode(prm_USER, "UTF-8") + "&PASSWORD="
					+ URLEncoder.encode(prm_PASSWORD, "UTF-8") + "&PROJECT="
					+ URLEncoder.encode(prm_PROJECT, "UTF-8") + "&TABLE="
					+ URLEncoder.encode(prm_TABLE, "UTF-8") + "&ALTKEY="
					+ URLEncoder.encode(prm_ALTKEY, "UTF-8") + "&COMMENT="
					+ URLEncoder.encode(prm_COMMENT, "UTF-8");

			// Now use the URL class to parse the user-specified URL into
			// its various parts: protocol, host, port, filename.
			URL url = new URL(completeUrl);
			
			InputStream from_server = url.openStream();

			// Now read the server's response, and write it to the file
			byte[] buffer = new byte[4096];
			int bytes_read;
			while((bytes_read = from_server.read(buffer)) != -1) {
				for (int i = 0; i < bytes_read; i++) {
					if (buffer[i] != 10)
						response.append((char) buffer[i]);
				}
			}

			retValue = Integer.parseInt(response.toString());
			if (retValue <= 0)
				retValue = -1;
		}
		catch (Exception e)
		{    // Report any errors that arise
			//System.err.println(e);
			//retValue = -1;
			final String errmsg = "Failed fetching next ID from HTTP service"
					+ "\n URL: " + website
					+ "\n Project: " + prm_PROJECT
					+ "\n User: " + prm_USER
					+ "\n Table: " + prm_TABLE
					+ "\b Response: " + response;
			throw new AdempiereException(errmsg, e);
		}
		s_log.info("getNextID_HTTP - {}={} ({})", TableName, response, retValue);

		return retValue;
	}

	private static boolean isExceptionCentralized(String tableName) {
		String [] exceptionTables = new String[] {
				"AD_ACCESSLOG", 
				"AD_Attachment",
				"AD_ALERTPROCESSORLOG",
				"AD_CHANGELOG",
				"AD_ISSUE",
				"AD_LDAPPROCESSORLOG",
				"AD_MIGRATION",
				"AD_MIGRATIONSTEP",
				"AD_MIGRATIONDATA",
				"AD_PACKAGE_IMP",
				"AD_PACKAGE_IMP_BACKUP",
				"AD_PACKAGE_IMP_DETAIL",
				"AD_PACKAGE_IMP_INST",
				"AD_PACKAGE_IMP_PROC",
				"AD_PINSTANCE",
				"AD_PINSTANCE_LOG",
				"AD_PINSTANCE_PARA",
				"AD_REPLICATION_LOG",
				"AD_SCHEDULERLOG",
				"AD_SESSION",
				"AD_WORKFLOWPROCESSORLOG",
				"CM_WEBACCESSLOG",
				"C_ACCTPROCESSORLOG", 
				"IMP_ProcessorLog",
				"K_INDEXLOG",
				"R_REQUESTPROCESSORLOG",
				"T_AGING",
				"T_ALTER_COLUMN",
				"T_DISTRIBUTIONRUNDETAIL",
				"T_INVENTORYVALUE",
				"T_INVOICEGL",
				"T_REPLENISH",
				"T_REPORT",
				"T_REPORTSTATEMENT",
				"T_SELECTION",
				"T_SELECTION2",
				"T_SPOOL",
				"T_TRANSACTION",
				"T_TRIALBALANCE"
			};
		for (int i = 0; i < exceptionTables.length; i++) {
			if (tableName.equalsIgnoreCase(exceptionTables[i]))
				return true;
		}
		
		// If MigrationLogger is ignoring it, for sure we don't need a Centralized ID
		if (Services.get(IMigrationLogger.class).getTablesToIgnoreUC().contains(tableName.toUpperCase()))
		{
			return true;
		}

		// don't log selects or insert/update for exception tables (i.e. AD_Issue, AD_ChangeLog)
		return false;
	}

	/**
	 * 
	 * @param AD_Client_ID
	 * @return true if we need to use system IDs
	 */
	// metas: 01558
	public static boolean isAdempiereSys(int AD_Client_ID)
	{
		boolean adempiereSys = Ini.isPropertyBool(Ini.P_ADEMPIERESYS);
		if (adempiereSys && AD_Client_ID > 11)
			adempiereSys = false;
		return adempiereSys;
	}
	
	/**
	 * 
	 * @param TableName table name or null
	 * @param AD_Client_ID
	 * @return true if we need to query Centralized Dictionary ID Server
	 */
	// metas: 01558
	public static boolean isQueryCentralizedIDServer(final String TableName, final int AD_Client_ID)
	{
		if (!isAdempiereSys(AD_Client_ID))
		{
			s_log.debug("Returning 'false' because isAdempiereSys()==false for AD_Client_ID {}", AD_Client_ID);
			return false;
		}
		if (Ini.getRunMode() == RunMode.BACKEND)
		{
			s_log.debug("Returning 'false' because RunMode == BACKEND");
			return false; // task 08011: we are running on the server; we don't need central ID because we won't record SQL-scripts
		}
		if (!MSysConfig.getBooleanValue(SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID, true))
		{
			s_log.debug("Returning 'false' because AD_Sysconfig {} (default=true) returned false", SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID);
			return false;
		}
		// Check if is an exception
		if (TableName != null && isExceptionCentralized(TableName))
		{
			s_log.debug("Returning 'false' because TableName {} is excluded from getting centralized IDs", TableName);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @param TableName table name or null
	 * @param AD_Client_ID
	 * @return true if we need to query Project ID Server
	 */
	// metas: 01558
	public static boolean isQueryProjectIDServer(final String TableName, final int AD_Client_ID)
	{
		if (isAdempiereSys(AD_Client_ID))
		{
			s_log.debug("Returning 'false' because isAdempiereSys()==true for AD_Client_ID {}", AD_Client_ID);
			return false;
		}
		if (Ini.getRunMode() == RunMode.BACKEND)
		{
			s_log.debug("Returning 'false' because RunMode == BACKEND");
			return false; // task 08011: we are running on the server; we don't need central ID because we won't record SQL-scripts
		}
		if (!MSysConfig.getBooleanValue(SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID, false))
		{
			s_log.debug("Returning 'false' because AD_Sysconfig {} (default=false) returned false", SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID);
			return false;
		}
		// Check if is an exception
		if (TableName != null && isExceptionCentralized(TableName))
		{
			s_log.debug("Returning 'false' because TableName {} is excluded from getting centralized IDs", TableName);
			return false;
		}

		// If LogMigrationScript flag is activated, always ask Project ID Server
		if (Ini.isPropertyBool(Ini.P_LOGMIGRATIONSCRIPT))
		{
			s_log.debug("Returning 'true' because Ini {} is true", Ini.P_LOGMIGRATIONSCRIPT);
			return true;
		}
		else
		{
			// If LogMigrationScript flag is not activated, then ask the Project ID server only if we logged in as System
			final boolean queryProjectIdServer = (AD_Client_ID == 0);
			s_log.debug("Returning '{}' because AD_Client_ID is {}", new Object[] { queryProjectIdServer, AD_Client_ID });
			return queryProjectIdServer;
		}
	}
	
	/**
	 * 
	 * @param TableName table name or null 
	 * @param AD_Client_ID 
	 * @return true if we use an external ID system (e.g. centralized ID server, project ID server)
	 */
	// metas: 01558
	public static boolean isUseExternalIDSystem(final String TableName, final int AD_Client_ID)
	{
		return isQueryCentralizedIDServer(TableName, AD_Client_ID)
		|| isQueryProjectIDServer(TableName, AD_Client_ID);
	}

	/**
	 * @return true if the external ID system is enabled (e.g. centralized ID server, project ID server)
	 */
	public static boolean isExternalIDSystemEnabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		
		if (sysConfigBL.getBooleanValue(SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID, false))
		{
			return true;
		}
		if (sysConfigBL.getBooleanValue(SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID, false))
		{
			return true;
		}
		
		return false;
	}

}	//	MSequence
