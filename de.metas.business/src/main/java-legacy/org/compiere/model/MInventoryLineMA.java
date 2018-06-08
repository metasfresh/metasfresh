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
import java.util.ArrayList;
import java.util.Properties;

import org.compiere.util.DB;
import org.slf4j.Logger;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.logging.LogManager;


/**
 *	Inventory Material Allocation
 *
 *  @author Jorg Janke
 *  @version $Id: MInventoryLineMA.java,v 1.3 2006/07/30 00:51:04 jjanke Exp $
 */
public class MInventoryLineMA extends X_M_InventoryLineMA
{
	/**
	 *
	 */
	private static final long serialVersionUID = 811710371747854597L;


	/**
	 * 	Get Material Allocations for Line
	 *	@param ctx context
	 *	@param M_InventoryLine_ID line
	 *	@param trxName trx
	 *	@return allocations
	 */
	public static MInventoryLineMA[] get (Properties ctx, int M_InventoryLine_ID, String trxName)
	{
		ArrayList<MInventoryLineMA> list = new ArrayList<MInventoryLineMA>();
		String sql = "SELECT * FROM M_InventoryLineMA WHERE M_InventoryLine_ID=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement (sql, trxName);
			pstmt.setInt (1, M_InventoryLine_ID);
			rs = pstmt.executeQuery ();
			while (rs.next ())
				list.add (new MInventoryLineMA (ctx, rs, trxName));
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

		MInventoryLineMA[] retValue = new MInventoryLineMA[list.size ()];
		list.toArray (retValue);
		return retValue;
	}	//	get

	/**
	 * 	Delete all Material Allocation for Inventory
	 *	@param M_Inventory_ID inventory
	 *	@param trxName transaction
	 *	@return number of rows deleted or -1 for error
	 */
	public static int deleteInventoryMA (int M_Inventory_ID, String trxName)
	{
		String sql = "DELETE FROM M_InventoryLineMA ma WHERE EXISTS "
			+ "(SELECT * FROM M_InventoryLine l WHERE l.M_InventoryLine_ID=ma.M_InventoryLine_ID"
			+ " AND M_Inventory_ID=" + M_Inventory_ID + ")";
		return DB.executeUpdate(sql, trxName);
	}	//	deleteInventoryMA

	/**
	 * 	Delete all Material Allocation for Inventory
	 *	@param M_InventoryLine_ID inventory
	 *	@param trxName transaction
	 *	@return number of rows deleted or -1 for error
	 */
	public static int deleteInventoryLineMA (int M_InventoryLine_ID, String trxName)
	{
		String sql = "DELETE FROM M_InventoryLineMA ma WHERE EXISTS "
			+ "(SELECT * FROM M_InventoryLine l WHERE l.M_InventoryLine_ID=ma.M_InventoryLine_ID"
			+ " AND M_InventoryLine_ID=" + M_InventoryLine_ID + ")";
		return DB.executeUpdate(sql, trxName);
	}	//	deleteInventoryMA

	/**	Logger	*/
	private static Logger	s_log	= LogManager.getLogger(MInventoryLineMA.class);


	/**************************************************************************
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param M_InventoryLineMA_ID ignored
	 *	@param trxName trx
	 */
	public MInventoryLineMA (Properties ctx, int M_InventoryLineMA_ID, String trxName)
	{
		super (ctx, M_InventoryLineMA_ID, trxName);
	}	//	MInventoryLineMA

	/**
	 * 	Load Cosntructor
	 *	@param ctx context
	 *	@param rs result set
	 *	@param trxName trx
	 */
	public MInventoryLineMA (Properties ctx, ResultSet rs, String trxName)
	{
		super (ctx, rs, trxName);
	}	//	MInventoryLineMA

	/**
	 * 	Parent Constructor
	 *	@param parent parent
	 *	@param M_AttributeSetInstance_ID asi
	 *	@param MovementQty qty
	 */
	public MInventoryLineMA (MInventoryLine parent, int M_AttributeSetInstance_ID, BigDecimal MovementQty)
	{
		this (parent.getCtx(), 0, parent.get_TrxName());
		setClientOrg(parent);
		setM_InventoryLine_ID(parent.getM_InventoryLine_ID());
		//
		setM_AttributeSetInstance_ID(M_AttributeSetInstance_ID);
		setMovementQty(MovementQty);
	}	//	MInventoryLineMA

	/**
	 * 	String Representation
	 *	@return info
	 */
	@Override
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MInventoryLineMA[");
		sb.append("M_InventoryLine_ID=").append(getM_InventoryLine_ID())
			.append(",M_AttributeSetInstance_ID=").append(getM_AttributeSetInstance_ID())
			.append(", Qty=").append(getMovementQty())
			.append ("]");
		return sb.toString ();
	}	//	toString

}	//	MInventoryLineMA
