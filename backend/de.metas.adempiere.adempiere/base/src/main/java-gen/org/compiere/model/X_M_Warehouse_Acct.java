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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Warehouse_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_Warehouse_Acct extends PO implements I_M_Warehouse_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_Warehouse_Acct (Properties ctx, int M_Warehouse_Acct_ID, String trxName)
    {
      super (ctx, M_Warehouse_Acct_ID, trxName);
      /** if (M_Warehouse_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setM_Warehouse_ID (0);
			setW_Differences_Acct (0);
			setW_InvActualAdjust_Acct (0);
			setW_Inventory_Acct (0);
			setW_Revaluation_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_M_Warehouse_Acct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_Warehouse_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Warehouse getM_Warehouse() throws RuntimeException
    {
		return (I_M_Warehouse)MTable.get(getCtx(), I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_ID(), get_TrxName());	}

	/** Set Warehouse.
		@param M_Warehouse_ID 
		Storage Warehouse and Service Point
	  */
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	/** Get Warehouse.
		@return Storage Warehouse and Service Point
	  */
	public int getM_Warehouse_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getW_Differences_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getW_Differences_Acct(), get_TrxName());	}

	/** Set Warehouse Differences.
		@param W_Differences_Acct 
		Warehouse Differences Account
	  */
	public void setW_Differences_Acct (int W_Differences_Acct)
	{
		set_Value (COLUMNNAME_W_Differences_Acct, Integer.valueOf(W_Differences_Acct));
	}

	/** Get Warehouse Differences.
		@return Warehouse Differences Account
	  */
	public int getW_Differences_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Differences_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getW_InvActualAdjust_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getW_InvActualAdjust_Acct(), get_TrxName());	}

	/** Set Inventory Adjustment.
		@param W_InvActualAdjust_Acct 
		Account for Inventory value adjustments for Actual Costing
	  */
	public void setW_InvActualAdjust_Acct (int W_InvActualAdjust_Acct)
	{
		set_Value (COLUMNNAME_W_InvActualAdjust_Acct, Integer.valueOf(W_InvActualAdjust_Acct));
	}

	/** Get Inventory Adjustment.
		@return Account for Inventory value adjustments for Actual Costing
	  */
	public int getW_InvActualAdjust_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_InvActualAdjust_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getW_Inventory_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getW_Inventory_Acct(), get_TrxName());	}

	/** Set (Not Used).
		@param W_Inventory_Acct 
		Warehouse Inventory Asset Account - Currently not used
	  */
	public void setW_Inventory_Acct (int W_Inventory_Acct)
	{
		set_Value (COLUMNNAME_W_Inventory_Acct, Integer.valueOf(W_Inventory_Acct));
	}

	/** Get (Not Used).
		@return Warehouse Inventory Asset Account - Currently not used
	  */
	public int getW_Inventory_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Inventory_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getW_Revaluation_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getW_Revaluation_Acct(), get_TrxName());	}

	/** Set Inventory Revaluation.
		@param W_Revaluation_Acct 
		Account for Inventory Revaluation
	  */
	public void setW_Revaluation_Acct (int W_Revaluation_Acct)
	{
		set_Value (COLUMNNAME_W_Revaluation_Acct, Integer.valueOf(W_Revaluation_Acct));
	}

	/** Get Inventory Revaluation.
		@return Account for Inventory Revaluation
	  */
	public int getW_Revaluation_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Revaluation_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}