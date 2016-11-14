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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

/**
 * Process Info with Utilities
 *
 * @author Jorg Janke
 * @version $Id: ProcessInfoUtil.java,v 1.2 2006/07/30 00:54:44 jjanke Exp $
 */
public class ProcessInfoUtil
{
	/** Logger */
	private static Logger s_log = LogManager.getLogger(ProcessInfoUtil.class);

	/**************************************************************************
	 * Query PInstance for result.
	 * Fill Summary and success in ProcessInfo
	 * 
	 * @param pi process info
	 */
	public static void loadSummaryFromDB(final ProcessExecutionResult result)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		//
		int sleepTime = 2000;	// 2 secomds
		int noRetry = 5;        // 10 seconds total
		//
		final String sql = "SELECT Result, ErrorMsg FROM AD_PInstance "
				+ "WHERE AD_PInstance_ID=?"
				+ " AND Result IS NOT NULL";
		final Object[] sqlParams = new Object[] { result.getAD_PInstance_ID() };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, ITrx.TRXNAME_None);
			for (int noTry = 0; noTry < noRetry; noTry++)
			{
				DB.setParameters(pstmt, sqlParams);

				rs = pstmt.executeQuery();
				if (rs.next())
				{
					// we have a result
					int i = rs.getInt(1);
					if (i == 1)
					{
						result.setSummary(msgBL.getMsg(Env.getCtx(), "Success"));
					}
					else
					{
						result.setSummary(msgBL.getMsg(Env.getCtx(), "Failure"), true);
					}
					String Message = rs.getString(2);
					rs.close();
					pstmt.close();
					//
					if (Message != null)
						result.addSummary("  (" + msgBL.parseTranslation(Env.getCtx(), Message) + ")");
					// s_log.debug("setSummaryFromDB - " + Message);
					return;
				}

				rs.close();
				// sleep
				try
				{
					if (noTry >= 3)
					{
						s_log.warn("Waiting for AD_PInstance_ID={} to return a result", result.getAD_PInstance_ID());
					}
					else
					{
						s_log.debug("Waiting for AD_PInstance_ID={} to return a result", result.getAD_PInstance_ID());
					}
					Thread.sleep(sleepTime);
				}
				catch (InterruptedException ie)
				{
					s_log.error("Sleep Thread", ie);
				}
			}
			pstmt.close();
		}
		catch (SQLException e)
		{
			s_log.error(sql, e);
			result.setThrowable(e);  // 03152
			result.setSummary(e.getLocalizedMessage(), true);
			return;
		}
		finally
		{
			DB.close(rs, pstmt);
		}

		result.setSummary(msgBL.getMsg(Env.getCtx(), "Timeout"), true);
	}	// setSummaryFromDB

	static List<ProcessInfoLog> retrieveLogsFromDB(final int adPInstanceId)
	{
		if(adPInstanceId <= 0)
		{
			return ImmutableList.of();
		}
		
		final String sql = "SELECT Log_ID, P_ID, P_Date, P_Number, P_Msg "
				+ "FROM AD_PInstance_Log "
				+ "WHERE AD_PInstance_ID=? "
				+ "ORDER BY Log_ID";
		final Object[] sqlParams = new Object[] { adPInstanceId };

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();
			
			final List<ProcessInfoLog> logs = new ArrayList<>();
			while (rs.next())
			{
				// int Log_ID, int P_ID, Timestamp P_Date, BigDecimal P_Number, String P_Msg
				final ProcessInfoLog log = new ProcessInfoLog(rs.getInt(1), rs.getInt(2), rs.getTimestamp(3), rs.getBigDecimal(4), rs.getString(5));
				log.markAsSavedInDB();
				logs.add(log);
			}
			
			return logs; 
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	/**
	 * Create Process Log
	 * 
	 * @param pi process info
	 */
	static void saveLogToDB(final ProcessExecutionResult result)
	{
		if (result.getAD_PInstance_ID() <= 0)
		{
			return;
		}

		final List<ProcessInfoLog> logsToSave = result.getCurrentLogs()
				.stream()
				.filter(log->!log.isSavedInDB())
				.collect(Collectors.toList());
		if (logsToSave.isEmpty())
		{
			return;
		}

		if (Adempiere.isUnitTestMode())
		{
			// don't try this is we aren't actually connected
			logsToSave.stream().forEach(log -> log.markAsSavedInDB());
			return;
		}

		final String sql = "INSERT INTO " + I_AD_PInstance_Log.Table_Name
				+ " (AD_PInstance_ID, Log_ID, P_Date, P_ID, P_Number, P_Msg)"
				+ " VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			for (final ProcessInfoLog log : logsToSave)
			{
				final Object[] sqlParams = new Object[] {
						result.getAD_PInstance_ID(),
						log.getLog_ID(),
						log.getP_Date(),
						log.getP_ID() == 0 ? null : log.getP_ID(),
						log.getP_Number(),
						log.getP_Msg()
				};

				DB.setParameters(pstmt, sqlParams);
				pstmt.addBatch();
			}

			pstmt.executeBatch();

			logsToSave.stream().forEach(log -> log.markAsSavedInDB());
		}
		catch (SQLException e)
		{
			// log only, don't fail
			s_log.error("Error while saving the process log lines", e);
		}
		finally
		{
			DB.close(pstmt);
			pstmt = null;
		}
	}
}	// ProcessInfoUtil
