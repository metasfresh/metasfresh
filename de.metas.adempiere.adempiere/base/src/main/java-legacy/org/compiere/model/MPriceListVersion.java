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
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.TimeUtil;

/**
 *	Price List Version Model
 *	
 *  @author Jorg Janke
 *  @version $Id: MPriceListVersion.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MPriceListVersion extends X_M_PriceList_Version
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607494586575155059L;

	/**
	 * 	Standard Cinstructor
	 *	@param ctx context
	 *	@param M_PriceList_Version_ID id
	 *	@param trxName transaction
	 */
	public MPriceListVersion(Properties ctx, int M_PriceList_Version_ID, String trxName)
	{
		super(ctx, M_PriceList_Version_ID, trxName);
		if (M_PriceList_Version_ID == 0)
		{
		//	setName (null);	// @#Date@
		//	setM_PriceList_ID (0);
		//	setValidFrom (TimeUtil.getDay(null));	// @#Date@
		//	setM_DiscountSchema_ID (0);
		}
	}	//	MPriceListVersion

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName transaction
	 */
	public MPriceListVersion(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MPriceListVersion

	/**
	 * 	Parent Constructor
	 *	@param pl parent
	 */
	public MPriceListVersion (MPriceList pl)
	{
		this (pl.getCtx(), 0, pl.get_TrxName());
		setClientOrg(pl);
		setM_PriceList_ID(pl.getM_PriceList_ID());
	}	//	MPriceListVersion
	
	/** Product Prices			*/
	private MProductPrice[] m_pp = null;
	/** Price List				*/
	private MPriceList		m_pl = null;

	/**
	 * 	Get Parent PriceList
	 *	@return price List
	 */
	public MPriceList getPriceList()
	{
		if (m_pl == null && getM_PriceList_ID() != 0)
			m_pl = MPriceList.get (getCtx(), getM_PriceList_ID(), null);
		return m_pl;
	}	//	PriceList
	
	
	/**
	 * 	Get Product Price
	 * 	@param refresh true if refresh
	 *	@return product price
	 */
	public MProductPrice[] getProductPrice (boolean refresh)
	{
		if (m_pp != null && !refresh)
			return m_pp;
		m_pp = getProductPrice(null);
		return m_pp;
	}	//	getProductPrice
	
	/**
	 * 	Get Product Price
	 * 	@param whereClause optional where clause
	 *	@return product price
	 */
	public MProductPrice[] getProductPrice (String whereClause)
	{
		ArrayList<MProductPrice> list = new ArrayList<MProductPrice>();
		String sql = "SELECT * FROM M_ProductPrice WHERE M_PriceList_Version_ID=?";
		if (whereClause != null)
			sql += " " + whereClause;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName ());
			pstmt.setInt (1, getM_PriceList_Version_ID());
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MProductPrice(getCtx(), rs, get_TrxName()));
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql, e);
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
		MProductPrice[] pp = new MProductPrice[list.size()];
		list.toArray(pp);
		return pp;
	}	//	getProductPrice
	
	/**
	 * 	Set Name to Valid From Date.
	 * 	If valid from not set, use today
	 */
	public void setName()
	{
		if (getValidFrom() == null)
			setValidFrom (TimeUtil.getDay(null));
		if (getName() == null)
		{
			String name = DisplayType.getDateFormat(DisplayType.Date)
				.format(getValidFrom());
			setName(name);
		}
	}	//	setName
	
	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{
		setName();
		
		return true;
	}	//	beforeSave
	
}	//	MPriceListVersion
