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
package org.compiere.model;

import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.migration.logger.MigrationScriptFileLoggerHolder;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.LegacyAdapters;
import org.compiere.Adempiere.RunMode;
import org.compiere.util.DB;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;

/**
 * Sequence Model.
 *
 * @author Jorg Janke
 * @version $Id: MSequence.java,v 1.3 2006/07/30 00:58:04 jjanke Exp $
 * @see org.compiere.process.SequenceCheck
 */
public class MSequence extends X_AD_Sequence
{
	/**
	 *
	 */
	private static final long serialVersionUID = -6827013120475678483L;

	public static final String SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID = "DICTIONARY_ID_USE_CENTRALIZED_ID";
	public static final String SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID = "PROJECT_ID_USE_CENTRALIZED_ID";

	// end vpj-cd e-evolution 02/11/2005

	public static final int QUERY_TIME_OUT = 10;

	public static int getNextID(final int AD_Client_ID, @NonNull final String TableName)
	{
		Check.assumeNotEmpty(TableName, "The given parameter tableName is not empty");

		if (isQueryCentralizedIDServer(TableName, AD_Client_ID))
		{
			return getNextOfficialID_HTTP(TableName);
		}
		if (isQueryProjectIDServer(TableName, AD_Client_ID))
		{
			return getNextProjectID_HTTP(TableName);
		}

		final boolean adempiereSys = isAdempiereSys(AD_Client_ID);

		final String selectSQL = "SELECT CurrentNext, CurrentNextSys, IncrementNo, AD_Sequence_ID "
				+ "FROM AD_Sequence "
				+ "WHERE Name=?"
				+ " AND IsActive='Y' AND IsTableID='Y' AND IsAutoSequence='Y' "
				+ " FOR UPDATE OF AD_Sequence ";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			conn = DB.getConnectionID();

			pstmt = conn.prepareStatement(selectSQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			pstmt.setString(1, TableName);
			if (DB.getDatabase().isQueryTimeoutSupported())
			{
				pstmt.setQueryTimeout(QUERY_TIME_OUT);
			}

			rs = pstmt.executeQuery();
			if (rs.next())
			{

				final int AD_Sequence_ID = rs.getInt(4);

				PreparedStatement updateSQL;
				final int incrementNo = rs.getInt(3);
				final int nextId;
				if (adempiereSys)
				{
					updateSQL = conn.prepareStatement("UPDATE AD_Sequence SET CurrentNextSys = CurrentNextSys + ? WHERE AD_Sequence_ID = ?");
					nextId = rs.getInt(2);
				}
				else
				{
					updateSQL = conn.prepareStatement("UPDATE AD_Sequence SET CurrentNext = CurrentNext + ? WHERE AD_Sequence_ID = ?");
					nextId = rs.getInt(1);
				}

				try
				{
					updateSQL.setInt(1, incrementNo);
					updateSQL.setInt(2, AD_Sequence_ID);
					updateSQL.executeUpdate();
				}
				finally
				{
					updateSQL.close();
				}

				return nextId;
			}
			else
			{
				throw new AdempiereException("No AD_Sequence found for " + TableName)
						.setParameter("SQL", selectSQL)
						.appendParametersToMessage();
			}
		}
		catch (final Exception ex)
		{
			try
			{
				if (conn != null)
				{
					conn.rollback();
				}
			}
			catch (SQLException ignored)
			{
			}
			finally
			{
				conn = null;
			}

			throw AdempiereException.wrapIfNeeded(ex);
		}
		finally
		{
			try
			{
				if (conn != null)
				{
					conn.commit();
				}
			}
			catch (SQLException e)
			{
				throw DBException.wrapIfNeeded(e);
			}
			finally
			{
				DB.close(rs, pstmt);
				DB.close(conn);
			}
		}
	}    // getNextID

	/**************************************************************************
	 * Check/Initialize Client DocumentNo/Value Sequences
	 */
	public static void checkClientSequences(Properties ctx, int AD_Client_ID, String trxName)
	{
		final String sql = "SELECT TableName "
				+ "FROM AD_Table t "
				+ "WHERE IsActive='Y' AND IsView='N'"
				// Get all Tables with DocumentNo or Value
				+ " AND AD_Table_ID IN "
				+ "(SELECT AD_Table_ID FROM AD_Column " + "WHERE ColumnName = 'DocumentNo' OR ColumnName = 'Value')"
				// Ability to run multiple times
				+ " AND '" + IDocumentNoBuilder.PREFIX_DOCSEQ + "' || TableName NOT IN (SELECT Name FROM AD_Sequence s " + "WHERE s.AD_Client_ID=?)";
		//
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, AD_Client_ID);
			rs = pstmt.executeQuery();

			int counter = 0;
			boolean success = true;
			while (rs.next())
			{
				final String tableName = rs.getString(1);
				s_log.debug("Add: {}", tableName);
				MSequence seq = new MSequence(ctx, AD_Client_ID, tableName, trxName);
				if (seq.save())
				{
					counter++;
				}
				else
				{
					s_log.error("Not created - AD_Client_ID={} - {}", AD_Client_ID, tableName);
					success = false;
				}
			}

			s_log.info("AD_Client_ID={} - created #{} - success={}", AD_Client_ID, counter, success);
		}
		catch (Exception e)
		{
			throw new DBException(e, sql, Collections.singletonList(AD_Client_ID));
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	/**
	 * Get Sequence
	 *
	 * @param ctx       context
	 * @param tableName table name
	 * @return Sequence
	 * @deprecated Please use {@link ISequenceDAO#retrieveTableSequenceOrNull(Properties, String)}
	 */
	@Deprecated
	public static MSequence get(Properties ctx, String tableName)
	{
		return get(ctx, tableName, null);
	}

	/**
	 * Get Sequence
	 *
	 * @param ctx       context
	 * @param tableName table name
	 * @param trxName   optional transaction name
	 * @return Sequence
	 * @deprecated Please use {@link ISequenceDAO#retrieveTableSequenceOrNull(Properties, String, String)}
	 */
	@Deprecated
	public static MSequence get(Properties ctx, String tableName, String trxName)
	{
		final I_AD_Sequence seq = Services.get(ISequenceDAO.class).retrieveTableSequenceOrNull(ctx, tableName, trxName);
		return LegacyAdapters.convertToPO(seq);
	}    // get

	/**
	 * Start Number
	 */
	public static final int INIT_NO = 1000000;    // 1 Mio
	/**
	 * Start System Number
	 */
	// public static final int INIT_SYS_NO = 100; // start number for Compiere
	public static final int INIT_SYS_NO = 50000;   // start number for Adempiere
	/**
	 * Static Logger
	 */
	private static final Logger s_log = LogManager.getLogger(MSequence.class);

	/**************************************************************************
	 * Standard Constructor
	 *
	 * @param ctx context
	 * @param AD_Sequence_ID id
	 * @param trxName transaction
	 */
	public MSequence(Properties ctx, int AD_Sequence_ID, String trxName)
	{
		super(ctx, AD_Sequence_ID, trxName);
		if (AD_Sequence_ID == 0)
		{
			// setName (null);
			//
			setIsTableID(false);
			setStartNo(INIT_NO);
			setCurrentNext(INIT_NO);
			setCurrentNextSys(INIT_SYS_NO);
			setIncrementNo(1);
			setIsAutoSequence(true);
			setIsAudited(false);
			setRestartFrequency(null);
		}
	}    // MSequence

	/**
	 * Load Constructor
	 *
	 * @param ctx     context
	 * @param rs      result set
	 * @param trxName transaction
	 */
	public MSequence(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    // MSequence

	/**
	 * New Document Sequence Constructor
	 *
	 * @param ctx          context
	 * @param AD_Client_ID owner
	 * @param tableName    name
	 * @param trxName      transaction
	 */
	public MSequence(Properties ctx, int AD_Client_ID, String tableName, String trxName)
	{
		this(ctx, 0, trxName);
		setClientOrg(AD_Client_ID, 0);            // Client Ownership
		setName(IDocumentNoBuilder.PREFIX_DOCSEQ + tableName);
		setDescription("DocumentNo/Value for Table " + tableName);
	}    // MSequence;

	/**
	 * New Document Sequence Constructor
	 *
	 * @param ctx          context
	 * @param AD_Client_ID owner
	 * @param sequenceName name
	 * @param StartNo      start
	 * @param trxName      trx
	 */
	public MSequence(Properties ctx, int AD_Client_ID, String sequenceName, int StartNo, String trxName)
	{
		this(ctx, 0, trxName);
		setClientOrg(AD_Client_ID, 0);            // Client Ownership
		setName(sequenceName);
		setDescription(sequenceName);
		setStartNo(StartNo);
		setCurrentNext(StartNo);
		setCurrentNextSys(StartNo / 10);
	}    // MSequence;

	/**
	 * Get next number for Key column
	 *
	 * @param TableName table name
	 * @return next no or (-1=error)
	 */
	public static synchronized int getNextOfficialID_HTTP(String TableName)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		String website = sysConfigBL.getValue("DICTIONARY_ID_WEBSITE"); // "http://developer.adempiere.com/cgi-bin/get_ID";
		String prm_USER = sysConfigBL.getValue("DICTIONARY_ID_USER");  // "globalqss";
		String prm_PASSWORD = sysConfigBL.getValue("DICTIONARY_ID_PASSWORD");  // "password_inseguro";
		//noinspection UnnecessaryLocalVariable
		String prm_TABLE = TableName;
		String prm_ALTKEY = "";  // TODO: generate alt-key based on key of table
		String prm_COMMENT = sysConfigBL.getValue("DICTIONARY_ID_COMMENTS");
		String prm_PROJECT = "Adempiere";

		return getNextID_HTTP(TableName, website, prm_USER,
				prm_PASSWORD, prm_TABLE, prm_ALTKEY, prm_COMMENT, prm_PROJECT);
	}

	/**
	 * Get next number for Key column
	 *
	 * @param TableName table name
	 * @return next no or (-1=error)
	 */
	public static synchronized int getNextProjectID_HTTP(String TableName)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		String website = sysConfigBL.getValue("PROJECT_ID_WEBSITE"); // "http://developer.adempiere.com/cgi-bin/get_ID";
		String prm_USER = sysConfigBL.getValue("PROJECT_ID_USER");  // "globalqss";
		String prm_PASSWORD = sysConfigBL.getValue("PROJECT_ID_PASSWORD");  // "password_inseguro";
		//noinspection UnnecessaryLocalVariable
		String prm_TABLE = TableName;
		String prm_ALTKEY = "";  // TODO: generate alt-key based on key of table
		String prm_COMMENT = sysConfigBL.getValue("PROJECT_ID_COMMENTS");
		String prm_PROJECT = sysConfigBL.getValue("PROJECT_ID_PROJECT");

		return getNextID_HTTP(TableName, website, prm_USER,
				prm_PASSWORD, prm_TABLE, prm_ALTKEY, prm_COMMENT, prm_PROJECT);
	}

	private static int getNextID_HTTP(String TableName,
									  String website, String prm_USER, String prm_PASSWORD,
									  String prm_TABLE, String prm_ALTKEY, String prm_COMMENT,
									  String prm_PROJECT)
	{
		final StringBuilder response = new StringBuilder();
		try
		{
			final URL url = new URL(website + "?"
					+ "USER=" + URLEncoder.encode(prm_USER, "UTF-8")
					+ "&PASSWORD=" + URLEncoder.encode(prm_PASSWORD, "UTF-8")
					+ "&PROJECT=" + URLEncoder.encode(prm_PROJECT, "UTF-8")
					+ "&TABLE=" + URLEncoder.encode(prm_TABLE, "UTF-8")
					+ "&ALTKEY=" + URLEncoder.encode(prm_ALTKEY, "UTF-8")
					+ "&COMMENT=" + URLEncoder.encode(prm_COMMENT, "UTF-8"));

			final InputStream from_server = url.openStream();

			// Now read the server's response, and write it to the file
			byte[] buffer = new byte[4096];
			int bytes_read;
			while ((bytes_read = from_server.read(buffer)) != -1)
			{
				for (int i = 0; i < bytes_read; i++)
				{
					if (buffer[i] != 10)
					{
						response.append((char)buffer[i]);
					}
				}
			}

			final int nextId = Integer.parseInt(response.toString());
			if (nextId <= 0)
			{
				throw new AdempiereException("Got invalid ID from " + website);
			}

			s_log.info("Got next ID for {}: {} ({})", TableName, nextId, website);

			return nextId;
		}
		catch (Exception e)
		{    // Report any errors that arise
			// System.err.println(e);
			// retValue = -1;
			final String errmsg = "Failed fetching next ID from HTTP service"
					+ "\n URL: " + website
					+ "\n Project: " + prm_PROJECT
					+ "\n User: " + prm_USER
					+ "\n Table: " + prm_TABLE
					+ "\b Response: " + response;
			throw new AdempiereException(errmsg, e);
		}
	}

	private static boolean isExceptionCentralized(String tableName, final ClientId clientId)
	{
		String[] exceptionTables = new String[] {
				"AD_ACCESSLOG",
				"AD_Attachment",
				"AD_ALERTPROCESSORLOG",
				"AD_CHANGELOG",
				"AD_ISSUE",
				"AD_MIGRATION",
				"AD_MIGRATIONSTEP",
				"AD_MIGRATIONDATA",
				I_AD_Note.Table_Name.toUpperCase(),
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
				"IMP_ProcessorLog",
				"K_INDEXLOG",
				"R_REQUESTPROCESSORLOG",
				"T_AGING",
				"T_ALTER_COLUMN",
				"T_DISTRIBUTIONRUNDETAIL",
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
		for (String exceptionTable : exceptionTables)
		{
			if (tableName.equalsIgnoreCase(exceptionTable))
			{
				return true;
			}
		}

		// If MigrationLogger is ignoring it, for sure we don't need a Centralized ID
		if (!Services.get(IMigrationLogger.class).isLogTableName(tableName, clientId))
		{
			return true;
		}

		// don't log selects or insert/update for exception tables (i.e. AD_Issue, AD_ChangeLog)
		return false;
	}

	/**
	 * @return true if we need to use system IDs
	 */
	// metas: 01558
	public static boolean isAdempiereSys(int AD_Client_ID)
	{
		boolean adempiereSys = Ini.isPropertyBool(Ini.P_ADEMPIERESYS);
		if (adempiereSys && AD_Client_ID > 11)
		{
			adempiereSys = false;
		}
		return adempiereSys;
	}

	/**
	 * @param TableName table name or null
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

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID, true))
		{
			s_log.debug("Returning 'false' because AD_Sysconfig {} (default=true) returned false", SYSCONFIG_DICTIONARY_ID_USE_CENTRALIZED_ID);
			return false;
		}

		// Check if is an exception
		if (TableName != null && isExceptionCentralized(TableName, ClientId.ofRepoIdOrSystem(AD_Client_ID)))
		{
			s_log.debug("Returning 'false' because TableName {} is excluded from getting centralized IDs", TableName);
			return false;
		}

		return true;
	}

	/**
	 * @param TableName table name or null
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
		if (Ini.getRunMode() == RunMode.BACKEND && !MigrationScriptFileLoggerHolder.isEnabled())
		{
			s_log.debug("Returning 'false' because RunMode == BACKEND");
			return false; // task 08011: we are running on the server; we don't need central ID because we won't record SQL-scripts
		}

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		if (!sysConfigBL.getBooleanValue(SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID, false))
		{
			s_log.debug("Returning 'false' because AD_Sysconfig {} (default=false) returned false", SYSCONFIG_PROJECT_ID_USE_CENTRALIZED_ID);
			return false;
		}
		// Check if is an exception
		if (TableName != null && isExceptionCentralized(TableName, ClientId.ofRepoIdOrSystem(AD_Client_ID)))
		{
			s_log.debug("Returning 'false' because TableName {} is excluded from getting centralized IDs", TableName);
			return false;
		}

		// If LogMigrationScript flag is activated, always ask Project ID Server
		if (MigrationScriptFileLoggerHolder.isEnabled())
		{
			s_log.debug("Returning 'true' because migration scripts logging is enabled");
			return true;
		}
		else
		{
			// If LogMigrationScript flag is not activated, then ask the Project ID server only if we logged in as System
			final boolean queryProjectIdServer = (AD_Client_ID == 0);
			s_log.debug("Returning '{}' because AD_Client_ID is {}", queryProjectIdServer, AD_Client_ID);
			return queryProjectIdServer;
		}
	}

	/**
	 * @param TableName table name or null
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

}    // MSequence
