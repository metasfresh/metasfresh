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
import java.util.Iterator;
import java.util.Properties;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.logging.LogManager;

import org.compiere.util.DB;

/**
 *	Cash Book Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MCashBook.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 */
public class MCashBook extends X_C_CashBook
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4602423783184037174L;

	/**
	 * 	Get MCashBook from Cache
	 *	@param ctx context
	 *	@param C_CashBook_ID id
	 *	@return MCashBook
	 */
	public static MCashBook get (Properties ctx, int C_CashBook_ID)
	{
		return get(ctx, C_CashBook_ID, null);
	}	//	get
	
	/**
	 * Gets MCashBook from Cache under transaction scope
	 * @param ctx 				context	
	 * @param C_CashBook_ID		id of cashbook to load
	 * @param trxName			transaction name
	 * @return   Cashbook
	 */
	public static MCashBook get(Properties ctx, int C_CashBook_ID, String trxName)
	{
		Integer key = new Integer (C_CashBook_ID);
		MCashBook retValue = (MCashBook) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MCashBook (ctx, C_CashBook_ID, trxName);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get CashBook for Org and Currency
	 *	@param ctx context
	 *	@param AD_Org_ID org
	 *	@param C_Currency_ID currency
	 *	@return cash book or null
	 */
	public static MCashBook get (Properties ctx, int AD_Org_ID, int C_Currency_ID)
	{
		//	Try from cache
		Iterator it = s_cache.values().iterator();
		while (it.hasNext())
		{
			MCashBook cb = (MCashBook)it.next();
			if (cb.getAD_Org_ID() == AD_Org_ID && cb.getC_Currency_ID() == C_Currency_ID)
				return cb;
		}
		
		//	Get from DB
		MCashBook retValue = null;
		String sql = "SELECT * FROM C_CashBook "
			+ "WHERE AD_Org_ID=? AND C_Currency_ID=? "
			+ "ORDER BY IsDefault DESC";
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, null);
			pstmt.setInt (1, AD_Org_ID);
			pstmt.setInt (2, C_Currency_ID);
			ResultSet rs = pstmt.executeQuery ();
			if (rs.next ())
			{
				retValue = new MCashBook (ctx, rs, null);
				Integer key = new Integer (retValue.getC_CashBook_ID());
				s_cache.put (key, retValue);
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			s_log.error("get", e);
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
	}	//	get
	

	/**	Cache						*/
	private static CCache<Integer,MCashBook> s_cache
		= new CCache<Integer,MCashBook>("", 20);
	/**	Static Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MCashBook.class);
	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param C_CashBook_ID id
	 *	@param trxName transaction
	 */
	public MCashBook (Properties ctx, int C_CashBook_ID, String trxName)
	{
		super (ctx, C_CashBook_ID, trxName);
	}	//	MCashBook

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MCashBook (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MCashBook
	
	/**
	 * 	After Save
	 *	@param newRecord new
	 *	@param success success
	 *	@return success
	 */
	protected boolean afterSave (boolean newRecord, boolean success)
	{
		if (newRecord && success)
			insert_Accounting("C_CashBook_Acct", "C_AcctSchema_Default", null);

		return success;
	}	//	afterSave

	/**
	 * 	Before Delete
	 *	@return true
	 */
	protected boolean beforeDelete ()
	{
		return delete_Accounting("C_Cashbook_Acct"); 
	}	//	beforeDelete

}	//	MCashBook
