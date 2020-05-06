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
import org.compiere.util.DB;
import org.slf4j.Logger;

import de.metas.i18n.Msg;
import de.metas.logging.LogManager;

/**
 *	Request Processor Model
 *
 *  @author Jorg Janke
 *  @version $Id: MRequestProcessor.java,v 1.2 2006/07/30 00:51:02 jjanke Exp $
 */
public class MRequestProcessor extends X_R_RequestProcessor
	implements AdempiereProcessor
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3149710397208186523L;


	/**
	 * 	Get Active Request Processors
	 *	@param ctx context
	 *	@return array of Request
	 */
	public static MRequestProcessor[] getActive (Properties ctx)
	{
		ArrayList<MRequestProcessor> list = new ArrayList<>();
		String sql = "SELECT * FROM R_RequestProcessor WHERE IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRequestProcessor (ctx, rs, null));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MRequestProcessor[] retValue = new MRequestProcessor[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getActive

	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MRequestProcessor.class);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param R_RequestProcessor_ID id
	 */
	public MRequestProcessor (Properties ctx, int R_RequestProcessor_ID, String trxName)
	{
		super (ctx, R_RequestProcessor_ID, trxName);
		if (R_RequestProcessor_ID == 0)
		{
		//	setName (null);
			setFrequencyType (FREQUENCYTYPE_Day);
			setFrequency (0);
			setKeepLogDays (7);
			setOverdueAlertDays (0);
			setOverdueAssignDays (0);
			setRemindDays (0);
		//	setSupervisor_ID (0);
		}
	}	//	MRequestProcessor

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public MRequestProcessor (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequestProcessor

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param Supervisor_ID Supervisor
	 */
	public MRequestProcessor (MClient parent, int Supervisor_ID)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setName (parent.getName() + " - "
			+ Msg.translate(getCtx(), "R_RequestProcessor_ID"));
		setSupervisor_ID (Supervisor_ID);
	}	//	MRequestProcessor


	/**	The Lines						*/
	private MRequestProcessorRoute[]	m_routes = null;

	/**
	 * 	Get Routes
	 *	@param reload reload data
	 *	@return array of routes
	 */
	public MRequestProcessorRoute[] getRoutes (boolean reload)
	{
		if (m_routes != null && !reload)
			return m_routes;

		String sql = "SELECT * FROM R_RequestProcessor_Route WHERE R_RequestProcessor_ID=? ORDER BY SeqNo";
		ArrayList<MRequestProcessorRoute> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getR_RequestProcessor_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRequestProcessorRoute (getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		//
		m_routes = new MRequestProcessorRoute[list.size ()];
		list.toArray (m_routes);
		return m_routes;
	}	//	getRoutes

	/**
	 * 	Get Logs
	 *	@return Array of Logs
	 */
	@Override
	public AdempiereProcessorLog[] getLogs()
	{
		ArrayList<MRequestProcessorLog> list = new ArrayList<>();
		String sql = "SELECT * "
			+ "FROM R_RequestProcessorLog "
			+ "WHERE R_RequestProcessor_ID=? "
			+ "ORDER BY Created DESC";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
			pstmt.setInt (1, getR_RequestProcessor_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MRequestProcessorLog (getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		MRequestProcessorLog[] retValue = new MRequestProcessorLog[list.size ()];
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
		String sql = "DELETE FROM R_RequestProcessorLog "
			+ "WHERE R_RequestProcessor_ID=" + getR_RequestProcessor_ID()
			+ " AND (Created+" + getKeepLogDays() + ") < now()";
		int no = DB.executeUpdate(sql, get_TrxName());
		return no;
	}	//	deleteLog

	/**
	 * 	Get the date Next run
	 * 	@param requery requery database
	 * 	@return date next run
	 */
	@Override
	public Timestamp getDateNextRun (boolean requery)
	{
		if (requery)
			load(get_TrxName());
		return getDateNextRun();
	}	//	getDateNextRun

	/**
	 * 	Get Unique ID
	 *	@return Unique ID
	 */
	@Override
	public String getServerID()
	{
		return "RequestProcessor" + get_ID();
	}	//	getServerID

	@Override
	public boolean saveOutOfTrx()
	{
		return save(ITrx.TRXNAME_None);
	}

}	//	MRequestProcessor
