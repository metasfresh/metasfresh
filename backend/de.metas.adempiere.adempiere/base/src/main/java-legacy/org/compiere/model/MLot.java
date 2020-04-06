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

import org.compiere.util.DB;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 *  Product Lot
 *
 *	@author Jorg Janke
 *	@version $Id: MLot.java,v 1.3 2006/07/30 00:51:02 jjanke Exp $
 *	FR: [ 2214883 ] Remove SQL code and Replace for Query - red1
 */
public class MLot extends X_M_Lot
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2238962371935615958L;
	/**	Logger					*/
	private static Logger		s_log = LogManager.getLogger(MLot.class);

	/**
	 * 	Get Lot for Product
	 *	@param ctx context
	 *	@param M_Product_ID product
	 *	@param lot
	 *	@param trxName transaction
	 *	@return Array of Lots for Product
	 */
	public static MLot getProductLot (Properties ctx, int M_Product_ID, String lot, String trxName)
	{
		String sql = "SELECT * FROM M_Lot WHERE M_Product_ID=? AND Name=?";
		MLot retValue = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_Product_ID);
			pstmt.setString(2, lot);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				retValue = new MLot (ctx, rs, trxName);
 		}
		catch (SQLException ex)
		{
			s_log.error(sql, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		return retValue;
	}	//	getProductLot

	/**
	 * 	Get Lot Key Name Pairs for Product
	 *	@param M_Product_ID product
	 *	@param trxName transaction
	 *	@return Array of Lot Key Name Pairs for Product
	 */
	public static KeyNamePair[] getProductLotPairs (int M_Product_ID, String trxName)
	{
		String sql = "SELECT M_Lot_ID, Name FROM M_Lot WHERE M_Product_ID=?";
		ArrayList<KeyNamePair> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_Product_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new KeyNamePair (rs.getInt(1), rs.getString(2)));
		}
		catch (SQLException ex)
		{
			s_log.error(sql, ex);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		KeyNamePair[] retValue = new KeyNamePair[list.size()];
		list.toArray(retValue);
		return retValue;
	}	//	getProductLotPairs

	
	/**************************************************************************
	 * 	Standard Constructor
	 * 	@param ctx context
	 * 	@param M_Lot_ID ID
	 *	@param trxName transaction
	 */
	public MLot (Properties ctx, int M_Lot_ID, String trxName)
	{
		super (ctx, M_Lot_ID, trxName);
		/** if (M_Lot_ID == 0)
		{
			setM_Lot_ID (0);
			setM_Product_ID (0);
			setName (null);
		}
		**/
	}	//	MLot

	/**
	 * 	Load Constructor
	 * 	@param ctx context
	 * 	@param rs result set
	 *	@param trxName transaction
	 */
	public MLot (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MLot

	/**
	 * 	Parent Constructor
	 * 	@param ctl lot control
	 * 	@param M_Product_ID product
	 * 	@param Name name
	 */
	public MLot (MLotCtl ctl, int M_Product_ID, String Name)
	{
		this (ctl.getCtx(), 0, ctl.get_TrxName());
		setClientOrg(ctl);
		setM_LotCtl_ID(ctl.getM_LotCtl_ID());
		setM_Product_ID (M_Product_ID);
		setName (Name);
	}	//	MLot

	/**
	 *	String Representation
	 * 	@return info
	 */
	@Override
	public String toString()
	{
		return getName();
	}	//	toString

}	//	MLot
