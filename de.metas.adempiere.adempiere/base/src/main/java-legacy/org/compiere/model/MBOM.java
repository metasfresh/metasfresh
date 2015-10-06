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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.CCache;
import org.compiere.util.CLogger;
import org.compiere.util.DB;

/**
 * 	BOM Model
 *  @author Jorg Janke
 *  @version $Id: MBOM.java,v 1.3 2006/07/30 00:51:03 jjanke Exp $
 */
public class MBOM extends X_M_BOM
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8885316310068284701L;


	/**
	 * 	Get BOM from Cache
	 *	@param ctx context
	 *	@param M_BOM_ID id
	 *	@return MBOM
	 */
	public static MBOM get (Properties ctx, int M_BOM_ID)
	{
		Integer key = new Integer (M_BOM_ID);
		MBOM retValue = (MBOM) s_cache.get (key);
		if (retValue != null)
			return retValue;
		retValue = new MBOM (ctx, M_BOM_ID, null);
		if (retValue.get_ID () != 0)
			s_cache.put (key, retValue);
		return retValue;
	}	//	get

	/**
	 * 	Get BOMs Of Product
	 *	@param ctx context
	 *	@param M_Product_ID product
	 *	@param trxName trx
	 *	@param whereClause optional WHERE clause w/o AND
	 *	@return array of BOMs
	 */
	public static MBOM[] getOfProduct (Properties ctx, int M_Product_ID, 
		String trxName, String whereClause)
	{
		ArrayList<MBOM> list = new ArrayList<MBOM>();
		String sql = "SELECT * FROM M_BOM WHERE M_Product_ID=?";
		if (whereClause != null && whereClause.length() > 0)
			sql += " AND " + whereClause;
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_Product_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MBOM (ctx, rs, trxName));
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
		
		MBOM[] retValue = new MBOM[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	getOfProduct

	/**	Cache						*/
	private static CCache<Integer,MBOM>	s_cache	
		= new CCache<Integer,MBOM>("M_BOM", 20);
	/**	Logger						*/
	private static CLogger	s_log	= CLogger.getCLogger (MBOM.class);

	
	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_BOM_ID id
	 *	@param trxName trx
	 */
	public MBOM (Properties ctx, int M_BOM_ID, String trxName)
	{
		super (ctx, M_BOM_ID, trxName);
		if (M_BOM_ID == 0)
		{
		//	setM_Product_ID (0);
		//	setName (null);
			setBOMType (BOMTYPE_CurrentActive);	// A
			setBOMUse (BOMUSE_Master);	// A
		}
	}	//	MBOM

	/**
	 * 	Load Constructor
	 *	@param ctx ctx
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MBOM (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MBOM

	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true/false
	 */
	@Override
	protected boolean beforeSave (boolean newRecord)
	{
		//	BOM Type
		if (newRecord || is_ValueChanged("BOMType"))
		{
			//	Only one Current Active
			if (getBOMType().equals(BOMTYPE_CurrentActive))
			{
				MBOM[] boms = getOfProduct(getCtx(), getM_Product_ID(), get_TrxName(),
					"BOMType='A' AND BOMUse='" + getBOMUse() + "' AND IsActive='Y'");
				if (boms.length == 0	//	only one = this 
					|| (boms.length == 1 && boms[0].getM_BOM_ID() == getM_BOM_ID()))
					;
				else
				{
					throw new AdempiereException("Can only have one Current Active BOM for Product BOM Use (" + getBOMType() + ")");
				}
			}
			//	Only one MTO
			else if (getBOMType().equals(BOMTYPE_Make_To_Order))
			{
				MBOM[] boms = getOfProduct(getCtx(), getM_Product_ID(), get_TrxName(), 
					"IsActive='Y'");
				if (boms.length == 0	//	only one = this 
					|| (boms.length == 1 && boms[0].getM_BOM_ID() == getM_BOM_ID()))
					;
				else
				{
					throw new AdempiereException("Can only have single Make-to-Order BOM for Product");
				}
			}
		}	//	BOM Type
		
		return true;
	}	//	beforeSave
	
}	//	MBOM
