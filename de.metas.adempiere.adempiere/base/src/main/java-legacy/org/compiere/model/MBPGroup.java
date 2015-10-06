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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Business Partner Group Model 
 *	
 *  @author Jorg Janke
 *  @version $Id: MBPGroup.java,v 1.4 2006/09/23 15:54:22 jjanke Exp $
 */
public class MBPGroup extends X_C_BP_Group
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3037428352124938328L;

	/**
	 * 	Get MBPGroup from Cache
	 *	@param ctx context
	 *	@param C_BP_Group_ID id
	 *	@return MBPGroup
	 */
	public static MBPGroup get (Properties ctx, int C_BP_Group_ID)
	{
		Integer key = new Integer (C_BP_Group_ID);
		MBPGroup retValue = (MBPGroup) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MBPGroup (ctx, C_BP_Group_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get Default MBPGroup
	 *	@param ctx context
	 *	@return MBPGroup
	 */
	public static MBPGroup getDefault (Properties ctx)
	{
		int AD_Client_ID = Env.getAD_Client_ID(ctx);
		Integer key = new Integer (AD_Client_ID);
		MBPGroup retValue = (MBPGroup) s_cacheDefault.get (key);
		if (retValue != null)
			return retValue;
		
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM C_BP_Group g "
			+ "WHERE IsDefault='Y' AND AD_Client_ID=? "
			+ "ORDER BY IsActive DESC";
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Client_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = new MBPGroup (ctx, rs, null);
				if (retValue.get_ID () != 0)
					s_cacheDefault.put (key, retValue);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
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
		if (retValue == null)
			s_log.warning("No Default BP Group for AD_Client_ID=" + AD_Client_ID);
		return retValue;
	}	//	get

	/**
	 * 	Get MBPGroup from Business Partner
	 *	@param ctx context
	 *	@param C_BPartner_ID business partner id
	 *	@return MBPGroup
	 */
	public static MBPGroup getOfBPartner (Properties ctx, int C_BPartner_ID)
	{
		MBPGroup retValue = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT * FROM C_BP_Group g "
			+ "WHERE EXISTS (SELECT * FROM C_BPartner p "
				+ "WHERE p.C_BPartner_ID=? AND p.C_BP_Group_ID=g.C_BP_Group_ID)";
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, C_BPartner_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = new MBPGroup (ctx, rs, null);
				Integer key = new Integer (retValue.getC_BP_Group_ID());
				if (retValue.get_ID () != 0)
					s_cache.put (key, retValue);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.log (Level.SEVERE, sql, e);
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
		
		return retValue;
	}	//	getOfBPartner
	
	/**	Cache						*/
	private static CCache<Integer,MBPGroup>	s_cache
		= new CCache<Integer,MBPGroup>("BP_Group", 10);
	/**	Default Cache					*/
	private static CCache<Integer,MBPGroup>	s_cacheDefault
		= new CCache<Integer,MBPGroup>("BP_Group", 5);
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (MBPGroup.class);
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_BP_Group_ID id
	 *	@param trxName transaction
	 */
	public MBPGroup (Properties ctx, int C_BP_Group_ID, String trxName)
	{
		super (ctx, C_BP_Group_ID, trxName);
		if (C_BP_Group_ID == 0)
		{
		//	setValue (null);
		//	setName (null);
			setIsConfidentialInfo (false);	// N
			setIsDefault (false);
			setPriorityBase(PRIORITYBASE_Same);
		}	
	}	//	MBPGroup

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MBPGroup (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MBPGroup
	
	
	/**
	 * 	Get Credit Watch Percent
	 *	@return 90 or defined percent
	 */
	public BigDecimal getCreditWatchPercent ()
	{
		BigDecimal bd = super.getCreditWatchPercent();
		if (bd != null)
			return bd;
		return new BigDecimal(90);
	}	//	getCreditWatchPercent

	/**
	 * 	Get Credit Watch Ratio
	 *	@return 0.90 or defined percent
	 */
	public BigDecimal getCreditWatchRatio()
	{
		BigDecimal bd = super.getCreditWatchPercent();
		if (bd != Env.ZERO)
			return bd.divide(Env.ONEHUNDRED, 2, BigDecimal.ROUND_HALF_UP);
		return new BigDecimal(0.90);
	}	//	getCreditWatchRatio

	
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 	After Save
	 *	@param newRecord new record
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			return insert_Accounting("C_BP_Group_Acct", "C_AcctSchema_Default", null);
		return success;
	}	//	afterSave
	
	
	/**
	 * 	Before Delete
	 *	@return true
	 */
	protected boolean beforeDelete ()
	{
		return delete_Accounting("C_BP_Group_Acct");
	}	//	beforeDelete
	
}	//	MBPGroup
