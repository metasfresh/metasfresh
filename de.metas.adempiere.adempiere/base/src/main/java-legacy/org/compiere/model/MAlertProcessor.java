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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.util.CCache;
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.logging.LogManager;


/**
 *	Alert Processor
 *
 *  @author Jorg Janke
 *  @version $Id: MAlertProcessor.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MAlertProcessor extends X_AD_AlertProcessor
	implements AdempiereProcessor
{
	/**
	 *
	 */
	private static final long serialVersionUID = 9060358751064718910L;


	/**
	 * 	Get Active
	 *	@param ctx context
	 *	@return active processors
	 */
	public static MAlertProcessor[] getActive (Properties ctx)
	{
		ArrayList<MAlertProcessor> list = new ArrayList<>();
		String sql = "SELECT * FROM AD_AlertProcessor WHERE IsActive='Y'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MAlertProcessor (ctx, rs, null));
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MAlertProcessor[] retValue = new MAlertProcessor[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getActive

	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MAlertProcessor.class);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param AD_AlertProcessorLog_ID id
	 *	@param trxName transaction
	 */
	public MAlertProcessor (Properties ctx, int AD_AlertProcessorLog_ID, String trxName)
	{
		super (ctx, AD_AlertProcessorLog_ID, trxName);
	}	//	MAlertProcessor

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MAlertProcessor (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MAlertProcessor

	/** Cache: AD_AlertProcessor -> Alerts array */
	private static CCache<Integer, MAlert[]> s_cacheAlerts = new CCache<>("AD_Alert_ForProcessor", 10);

	/**
	 * 	Get Server ID
	 *	@return id
	 */
	@Override
	public String getServerID ()
	{
		return "AlertProcessor" + get_ID();
	}	//	getServerID

	/**
	 * 	Get Date Next Run
	 *	@param requery requery
	 *	@return date next run
	 */
	@Override
	public Timestamp getDateNextRun (boolean requery)
	{
		if (requery)
			load(get_TrxName());
		return getDateNextRun();
	}	//	getDateNextRun

	/**
	 * 	Get Logs
	 *	@return logs
	 */
	@Override
	public AdempiereProcessorLog[] getLogs ()
	{
		ArrayList<MAlertProcessorLog> list = new ArrayList<>();
		String sql = "SELECT * "
			+ "FROM AD_AlertProcessorLog "
			+ "WHERE AD_AlertProcessor_ID=? "
			+ "ORDER BY Created DESC";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getAD_AlertProcessor_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MAlertProcessorLog (getCtx(), rs, null));
 		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		MAlertProcessorLog[] retValue = new MAlertProcessorLog[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getLogs

	/**
	 * 	Delete old Request Log
	 *	@return number of records
	 */
	public int deleteLog()
	{
		if (getKeepLogDays() < 1)
			return 0;
		String sql = "DELETE FROM AD_AlertProcessorLog "
			+ "WHERE AD_AlertProcessor_ID=" + getAD_AlertProcessor_ID()
			+ " AND (Created+" + getKeepLogDays() + ") < now()";
		int no = DB.executeUpdate(sql, get_TrxName());
		return no;
	}	//	deleteLog


	/**
	 * 	Get Alerts
	 *	@param reload reload data
	 *	@return array of alerts
	 */
	public MAlert[] getAlerts (boolean reload)
	{
		MAlert[] alerts = s_cacheAlerts.get(get_ID());
		if (alerts != null && !reload)
			return alerts;
		String sql = "SELECT * FROM AD_Alert "
			+ "WHERE AD_AlertProcessor_ID=? AND IsActive='Y' ";
		ArrayList<MAlert> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, getAD_AlertProcessor_ID());
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MAlert (getCtx(), rs, null));
 		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		alerts = new MAlert[list.size ()];
		list.toArray (alerts);
		s_cacheAlerts.put(get_ID(), alerts);
		return alerts;
	}	//	getAlerts

	@Override
	public boolean saveOutOfTrx()
	{
		return save(ITrx.TRXNAME_None);
	}

}	//	MAlertProcessor
