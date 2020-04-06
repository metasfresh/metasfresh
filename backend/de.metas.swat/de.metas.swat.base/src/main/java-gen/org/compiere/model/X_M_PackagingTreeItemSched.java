/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

/**
 * Generated Model for M_PackagingTreeItemSched
 * 
 * @author Adempiere (generated)
 * @version Release 3.5.4a - $Id$
 */
public class X_M_PackagingTreeItemSched extends PO implements I_M_PackagingTreeItemSched, I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110301L;

	/** Standard Constructor */
	public X_M_PackagingTreeItemSched(Properties ctx, int M_PackagingTreeItemSched_ID, String trxName)
	{
		super(ctx, M_PackagingTreeItemSched_ID, trxName);
		/**
		 * if (M_PackagingTreeItemSched_ID == 0) { setM_PackagingTreeItemSched_ID (0); setM_PackagingTreeItem_ID (0); }
		 */
	}

	/** Load Constructor */
	public X_M_PackagingTreeItemSched(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/**
	 * AccessLevel
	 * 
	 * @return 3 - Client - Org
	 */
	protected int get_AccessLevel()
	{
		return accessLevel.intValue();
	}

	/** Load Meta Data */
	protected POInfo initPO(Properties ctx)
	{
		POInfo poi = POInfo.getPOInfo(ctx, Table_ID, get_TrxName());
		return poi;
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer("X_M_PackagingTreeItemSched[")
				.append(get_ID()).append("]");
		return sb.toString();
	}

	/**
	 * Set Packaging Tree Item Schedule.
	 * 
	 * @param M_PackagingTreeItemSched_ID
	 *            Packaging Tree Item Schedule
	 */
	public void setM_PackagingTreeItemSched_ID(int M_PackagingTreeItemSched_ID)
	{
		if (M_PackagingTreeItemSched_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_PackagingTreeItemSched_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_PackagingTreeItemSched_ID, Integer.valueOf(M_PackagingTreeItemSched_ID));
	}

	/**
	 * Get Packaging Tree Item Schedule.
	 * 
	 * @return Packaging Tree Item Schedule
	 */
	public int getM_PackagingTreeItemSched_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingTreeItemSched_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	public I_M_PackagingTreeItem getM_PackagingTreeItem() throws RuntimeException
	{
		return (I_M_PackagingTreeItem)MTable.get(getCtx(), I_M_PackagingTreeItem.Table_Name)
				.getPO(getM_PackagingTreeItem_ID(), get_TrxName());
	}

	/**
	 * Set Packaging Tree Item.
	 * 
	 * @param M_PackagingTreeItem_ID
	 *            Packaging Tree Item
	 */
	public void setM_PackagingTreeItem_ID(int M_PackagingTreeItem_ID)
	{
		if (M_PackagingTreeItem_ID < 1)
			set_Value(COLUMNNAME_M_PackagingTreeItem_ID, null);
		else
			set_Value(COLUMNNAME_M_PackagingTreeItem_ID, Integer.valueOf(M_PackagingTreeItem_ID));
	}

	/**
	 * Get Packaging Tree Item.
	 * 
	 * @return Packaging Tree Item
	 */
	public int getM_PackagingTreeItem_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingTreeItem_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public I_M_ShipmentSchedule getM_ShipmentSchedule() throws RuntimeException
	{
		return (I_M_ShipmentSchedule)MTable.get(getCtx(), I_M_ShipmentSchedule.Table_Name)
				.getPO(getM_ShipmentSchedule_ID(), get_TrxName());
	}

	@Override
	public int getM_ShipmentSchedule_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ShipmentSchedule_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public void setM_ShipmentSchedule_ID(int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1)
			set_Value(COLUMNNAME_M_ShipmentSchedule_ID, null);
		else
			set_Value(COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	/**
	 * Set Menge.
	 * 
	 * @param Qty
	 *            Menge
	 */
	public void setQty(BigDecimal Qty)
	{
		set_Value(COLUMNNAME_Qty, Qty);
	}

	/**
	 * Get Menge.
	 * 
	 * @return Menge
	 */
	public BigDecimal getQty()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}
}
