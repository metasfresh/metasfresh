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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 * 	Request Status Model
 *  @author Jorg Janke
 *  @version $Id: MStatus.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MStatus extends X_R_Status
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4549127671165860354L;


	/**
	 * 	Get Request Status (cached)
	 *	@param ctx context
	 *	@param R_Status_ID id
	 *	@return Request Status or null
	 */
	public static MStatus get (Properties ctx, int R_Status_ID)
	{
		if (R_Status_ID == 0)
			return null;
		Integer key = new Integer (R_Status_ID);
		MStatus retValue = (MStatus)s_cache.get(key);
		if (retValue == null)
		{
			retValue = new MStatus (ctx, R_Status_ID, null);
			s_cache.put(key, retValue);
		}
		return retValue;
	}	//	get

	/**
	 * 	Get Default Request Status
	 *	@param ctx context
	 *	@param R_RequestType_ID request type
	 *	@return Request Type
	 */
	public static MStatus getDefault (Properties ctx, int R_RequestType_ID)
	{
		Integer key = new Integer(R_RequestType_ID);
		MStatus retValue = (MStatus)s_cacheDefault.get(key);
		if (retValue != null)
			return retValue;
		//	Get New
		String sql = "SELECT * FROM R_Status s "
			+ "WHERE EXISTS (SELECT * FROM R_RequestType rt "
				+ "WHERE rt.R_StatusCategory_ID=s.R_StatusCategory_ID"
				+ " AND rt.R_RequestType_ID=?)"
			+ " AND IsDefault='Y' "
			+ "ORDER BY SeqNo";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, R_RequestType_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
				retValue = new MStatus (ctx, rs, null);
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			s_log.log(Level.SEVERE, sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		if (retValue != null)
			s_cacheDefault.put(key, retValue);
		return retValue;
	}	//	getDefault

	/**
	 * 	Get Closed Status
	 *	@param ctx context
	 *	@return Request Type
	 */
	public static MStatus[] getClosed (Properties ctx)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		String sql = "SELECT * FROM R_Status "
			+ "WHERE AD_Client_ID=? AND IsActive='Y' AND IsClosed='Y' "
			+ "ORDER BY Value";
		ArrayList<MStatus> list = new ArrayList<MStatus>();
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt(1, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add(new MStatus (ctx, rs, null));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (SQLException ex)
		{
			s_log.log(Level.SEVERE, sql, ex);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
		}
		catch (SQLException ex1)
		{
		}
		pstmt = null;
		MStatus[] retValue = new MStatus[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	get

	
	/** Static Logger					*/
	private static CLogger s_log = CLogger.getCLogger(MStatus.class);
	/**	Cache							*/
	static private CCache<Integer,MStatus> s_cache
		= new CCache<Integer,MStatus> ("R_Status", 10);
	/**	Default Cache (Key=Client)		*/
	static private CCache<Integer,MStatus> s_cacheDefault
		= new CCache<Integer,MStatus>("R_Status", 10);


	/**************************************************************************
	 * 	Default Constructor
	 *	@param ctx context
	 *	@param R_Status_ID is
	 *	@param trxName trx
	 */
	public MStatus (Properties ctx, int R_Status_ID, String trxName)
	{
		super (ctx, R_Status_ID, trxName);
		if (R_Status_ID == 0)
		{
		//	setValue (null);
		//	setName (null);
			setIsClosed (false);	// N
			setIsDefault (false);
			setIsFinalClose (false);	// N
			setIsOpen (false);
			setIsWebCanUpdate (true);
		}
	}	//	MStatus

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MStatus (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MStatus
	

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		if (isOpen() && isClosed())
			setIsClosed(false);
		if (isFinalClose() && !isClosed())
			setIsFinalClose(false);
		//
		if (!isWebCanUpdate() && getUpdate_Status_ID() != 0)
			setUpdate_Status_ID(0);
		if (getTimeoutDays() == 0 && getNext_Status_ID() != 0)
			setNext_Status_ID(0);
		//
		return true;
	}	//	beforeSave
	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MStatus[");
		sb.append(get_ID()).append("-").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
}	//	MStatus
