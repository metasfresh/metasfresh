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
import java.util.Properties;

import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Business Partner Contact Interest.
 *  Adempiere complies with spamming laws.
 *  If the opt out date is set (by the user),
 *  you should not subscribe the user again.
 *  Internally, the isActive flag is used.
 *
 *  @author Jorg Janke
 *  @version $Id: MContactInterest.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MContactInterest extends X_R_ContactInterest
{
	/**
	 *
	 */
	private static final long serialVersionUID = -4720845687902863428L;

	/**
	 * 	Get Contact Interest
	 *	@param ctx context
	 *	@param R_InterestArea_ID interest ares
	 *	@param AD_User_ID user
	 * 	@param isActive create as active
	 *	@param trxName transaction
	 *	@return Contact Interest
	 */
	public static MContactInterest get (Properties ctx,
		int R_InterestArea_ID, int AD_User_ID, boolean isActive, String trxName)
	{
		MContactInterest retValue = null;
		String sql = "SELECT * FROM R_ContactInterest "
			+ "WHERE R_InterestArea_ID=? AND AD_User_ID=?";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql, trxName);
			pstmt.setInt(1, R_InterestArea_ID);
			pstmt.setInt(2, AD_User_ID);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				retValue = new MContactInterest (ctx, rs, trxName);
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error(sql, e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		if (retValue == null)
		{
			retValue = new MContactInterest (ctx, R_InterestArea_ID, AD_User_ID,
				isActive, trxName);
			s_log.debug("NOT found - " + retValue);
		}
		else
			s_log.debug("Found - " + retValue);
		return retValue;
	}	//	get


	/**************************************************************************
	 * 	Persistency Constructor
	 *	@param ctx context
	 *	@param id ignored
	 *	@param trxName transaction
	 */
	public MContactInterest (Properties ctx, int id, String trxName)
	{
		super(ctx, id, trxName);
	}	//	MContactInterest

	/**
	 * 	Constructor
	 * 	@param ctx context
	 * 	@param R_InterestArea_ID interest area
	 * 	@param AD_User_ID partner contact
	 * 	@param isActive create as active
	 *	@param trxName transaction
	 */
	public MContactInterest (Properties ctx, int R_InterestArea_ID, int AD_User_ID, boolean isActive, String trxName)
	{
		super(ctx, 0, trxName);
		setR_InterestArea_ID (R_InterestArea_ID);
		setAD_User_ID (AD_User_ID);
		setIsActive(isActive);
	}	//	MContactInterest

	/**
	 *  Create & Load existing Persistent Object.
	 *  @param ctx context
	 *  @param rs load from current result set position (no navigation, not closed)
	 *	@param trxName transaction
	 */
	public MContactInterest (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MContactInterest

	/**	Static Logger				*/
	private static Logger		s_log = LogManager.getLogger(MContactInterest.class);

	/**
	 * 	Set OptOut Date
	 * 	User action only.
	 *	@param OptOutDate date
	 */
	@Override
	public void setOptOutDate (Timestamp OptOutDate)
	{
		if (OptOutDate == null)
			OptOutDate = new Timestamp(System.currentTimeMillis());
		log.debug("" + OptOutDate);
		super.setOptOutDate(OptOutDate);
		setIsActive(false);
	}	//	setOptOutDate

	/**
	 * 	Unsubscribe.
	 * 	User action only.
	 */
	public void unsubscribe()
	{
		setOptOutDate(null);
	}	//	unsubscribe

	/**
	 * 	Is Opted Out
	 *	@return true if opted out
	 */
	public boolean isOptOut()
	{
		return getOptOutDate() != null;
	}	//	isOptOut

	/**
	 * 	Set Subscribe Date
	 * 	User action only.
	 *	@param SubscribeDate date
	 */
	@Override
	public void setSubscribeDate (Timestamp SubscribeDate)
	{
		if (SubscribeDate == null)
			SubscribeDate = new Timestamp(System.currentTimeMillis());
		log.debug("" + SubscribeDate);
		super.setSubscribeDate(SubscribeDate);
		super.setOptOutDate(null);
		setIsActive(true);
	}	//	setSubscribeDate

	/**
	 * 	Subscribe
	 * 	User action only.
	 */
	public void subscribe()
	{
		setSubscribeDate(null);
		if (!isActive())
			setIsActive(true);
	}	//	subscribe

	/**
	 * 	Subscribe.
	 * 	User action only.
	 * 	@param subscribe subscribe
	 */
	public void subscribe (boolean subscribe)
	{
		if (subscribe)
			setSubscribeDate(null);
		else
			setOptOutDate(null);
	}	//	subscribe


	/**
	 * 	Is Subscribed.
	 * 	Active is set internally,
	 * 	the opt out date is set by the user via the web UI.
	 *	@return true if subscribed
	 */
	public boolean isSubscribed()
	{
		return isActive() && getOptOutDate() == null;
	}	//	isSubscribed


	/**
	 * 	String representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MContactInterest[")
			.append("R_InterestArea_ID=").append(getR_InterestArea_ID())
			.append(",AD_User_ID=").append(getAD_User_ID())
			.append(",Subscribed=").append(isSubscribed())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**************************************************************************
	 * 	@param args ignored
	 */
	public static void main (String[] args)
	{
		org.compiere.Adempiere.startup(true);
		int R_InterestArea_ID = 1000002;
		int AD_User_ID = 1000002;
		MContactInterest ci = MContactInterest.get(Env.getCtx(), R_InterestArea_ID, AD_User_ID, false, null);
		ci.subscribe();
		ci.save();
		//
		ci = MContactInterest.get(Env.getCtx(), R_InterestArea_ID, AD_User_ID, false, null);
	}	//	main


}	//	MContactInterest
