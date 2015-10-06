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
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 *  Interest Area.
 * 	Note: if model is changed, update
 * 	org.compiere.wstore.Info.getInterests()
 *  manually
 *
 *  @author Jorg Janke
 *  @version $Id: MInterestArea.java,v 1.3 2006/07/30 00:51:05 jjanke Exp $
 */
public class MInterestArea extends X_R_InterestArea
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6910076559329764930L;


	/**
	 * 	Get all active interest areas
	 *	@param ctx context
	 *	@return interest areas
	 */
	public static MInterestArea[] getAll (Properties ctx)
	{
		ArrayList<MInterestArea> list = new ArrayList<MInterestArea>();
		String sql = "SELECT * FROM R_InterestArea WHERE IsActive='Y'";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				MInterestArea ia = new MInterestArea (ctx, rs, null);
				list.add (ia);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log(Level.SEVERE, sql, e);
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
		MInterestArea[] retValue = new MInterestArea[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getAll

	
	/**
	 * 	Get MInterestArea from Cache
	 *	@param ctx context
	 *	@param R_InterestArea_ID id
	 *	@return MInterestArea
	 */
	public static MInterestArea get (Properties ctx, int R_InterestArea_ID)
	{
		Integer key = new Integer (R_InterestArea_ID);
		MInterestArea retValue = (MInterestArea) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MInterestArea (ctx, R_InterestArea_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	} //	get

	/**	Cache						*/
	private static CCache<Integer,MInterestArea> s_cache = 
		new CCache<Integer,MInterestArea>("R_InterestArea", 5);
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MInterestArea.class);
	
	
	/**
	 * 	Constructor
	 *	@param ctx context
	 *	@param R_InterestArea_ID interest area
	 *	@param trxName transaction
	 */
	public MInterestArea (Properties ctx, int R_InterestArea_ID, String trxName)
	{
		super (ctx, R_InterestArea_ID, trxName);
		if (R_InterestArea_ID == 0)
		{
		//	setName (null);
		//	setR_InterestArea_ID (0);
			setIsSelfService (false);
		}
	}	//	MInterestArea

	/**
	 * 	Loader Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MInterestArea (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MInterestArea

	
	/**
	 * 	Get Value
	 *	@return value
	 */
	public String getValue()
	{
		String s = super.getValue ();
		if (s != null && s.length () > 0)
			return s;
		return super.getName();
	}	//	getValue

	/**
	 * 	String representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInterestArea[")
			.append (get_ID()).append(" - ").append(getName())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/*************************************************************************/

	private int 				m_AD_User_ID = -1;
	private MContactInterest 	m_ci = null;

	/**
	 * 	Set Subscription info "constructor".
	 * 	Create inactive Subscription
	 *	@param AD_User_ID contact
	 */
	public void setSubscriptionInfo (int AD_User_ID)
	{
		m_AD_User_ID = AD_User_ID;
		m_ci = MContactInterest.get(getCtx(), getR_InterestArea_ID(), AD_User_ID, 
			false, get_TrxName());
	}	//	setSubscription

	/**
	 * 	Set AD_User_ID
	 *	@param AD_User_ID user
	 */
	public void setAD_User_ID (int AD_User_ID)
	{
		m_AD_User_ID = AD_User_ID;
	}

	/**
	 * 	Get AD_User_ID
	 *	@return user
	 */
	public int getAD_User_ID ()
	{
		return m_AD_User_ID;
	}

	/**
	 * 	Get Subscribe Date
	 *	@return subscribe date
	 */
	public Timestamp getSubscribeDate ()
	{
		if (m_ci != null)
			return m_ci.getSubscribeDate();
		return null;
	}

	/**
	 * 	Get Opt Out Date
	 *	@return opt-out date
	 */
	public Timestamp getOptOutDate ()
	{
		if (m_ci != null)
			return m_ci.getOptOutDate();
		return null;
	}

	/**
	 * 	Is Subscribed
	 *	@return true if sunscribed
	 */
	public boolean isSubscribed()
	{
		if (m_AD_User_ID <= 0 || m_ci == null)
			return false;
		//	We have a BPartner Contact
		return m_ci.isSubscribed();
	}	//	isSubscribed

}	//	MInterestArea
