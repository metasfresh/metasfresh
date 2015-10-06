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
import org.compiere.util.KeyNamePair;

/** Generated Model for M_MovementLineConfirm
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_MovementLineConfirm extends PO implements I_M_MovementLineConfirm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_MovementLineConfirm (Properties ctx, int M_MovementLineConfirm_ID, String trxName)
    {
      super (ctx, M_MovementLineConfirm_ID, trxName);
      /** if (M_MovementLineConfirm_ID == 0)
        {
			setConfirmedQty (Env.ZERO);
			setDifferenceQty (Env.ZERO);
			setM_MovementConfirm_ID (0);
			setM_MovementLineConfirm_ID (0);
			setM_MovementLine_ID (0);
			setProcessed (false);
			setScrappedQty (Env.ZERO);
			setTargetQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_MovementLineConfirm (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_M_MovementLineConfirm[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Confirmed Quantity.
		@param ConfirmedQty 
		Confirmation of a received quantity
	  */
	public void setConfirmedQty (BigDecimal ConfirmedQty)
	{
		set_Value (COLUMNNAME_ConfirmedQty, ConfirmedQty);
	}

	/** Get Confirmed Quantity.
		@return Confirmation of a received quantity
	  */
	public BigDecimal getConfirmedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ConfirmedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Difference.
		@param DifferenceQty 
		Difference Quantity
	  */
	public void setDifferenceQty (BigDecimal DifferenceQty)
	{
		set_Value (COLUMNNAME_DifferenceQty, DifferenceQty);
	}

	/** Get Difference.
		@return Difference Quantity
	  */
	public BigDecimal getDifferenceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public I_M_InventoryLine getM_InventoryLine() throws RuntimeException
    {
		return (I_M_InventoryLine)MTable.get(getCtx(), I_M_InventoryLine.Table_Name)
			.getPO(getM_InventoryLine_ID(), get_TrxName());	}

	/** Set Phys.Inventory Line.
		@param M_InventoryLine_ID 
		Unique line in an Inventory document
	  */
	public void setM_InventoryLine_ID (int M_InventoryLine_ID)
	{
		if (M_InventoryLine_ID < 1) 
			set_Value (COLUMNNAME_M_InventoryLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InventoryLine_ID, Integer.valueOf(M_InventoryLine_ID));
	}

	/** Get Phys.Inventory Line.
		@return Unique line in an Inventory document
	  */
	public int getM_InventoryLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InventoryLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_MovementConfirm getM_MovementConfirm() throws RuntimeException
    {
		return (I_M_MovementConfirm)MTable.get(getCtx(), I_M_MovementConfirm.Table_Name)
			.getPO(getM_MovementConfirm_ID(), get_TrxName());	}

	/** Set Move Confirm.
		@param M_MovementConfirm_ID 
		Inventory Move Confirmation
	  */
	public void setM_MovementConfirm_ID (int M_MovementConfirm_ID)
	{
		if (M_MovementConfirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MovementConfirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MovementConfirm_ID, Integer.valueOf(M_MovementConfirm_ID));
	}

	/** Get Move Confirm.
		@return Inventory Move Confirmation
	  */
	public int getM_MovementConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementConfirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_MovementConfirm_ID()));
    }

	/** Set Move Line Confirm.
		@param M_MovementLineConfirm_ID 
		Inventory Move Line Confirmation
	  */
	public void setM_MovementLineConfirm_ID (int M_MovementLineConfirm_ID)
	{
		if (M_MovementLineConfirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_MovementLineConfirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_MovementLineConfirm_ID, Integer.valueOf(M_MovementLineConfirm_ID));
	}

	/** Get Move Line Confirm.
		@return Inventory Move Line Confirmation
	  */
	public int getM_MovementLineConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLineConfirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_MovementLine getM_MovementLine() throws RuntimeException
    {
		return (I_M_MovementLine)MTable.get(getCtx(), I_M_MovementLine.Table_Name)
			.getPO(getM_MovementLine_ID(), get_TrxName());	}

	/** Set Move Line.
		@param M_MovementLine_ID 
		Inventory Move document Line
	  */
	public void setM_MovementLine_ID (int M_MovementLine_ID)
	{
		if (M_MovementLine_ID < 1) 
			set_Value (COLUMNNAME_M_MovementLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_MovementLine_ID, Integer.valueOf(M_MovementLine_ID));
	}

	/** Get Move Line.
		@return Inventory Move document Line
	  */
	public int getM_MovementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_MovementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Scrapped Quantity.
		@param ScrappedQty 
		The Quantity scrapped due to QA issues
	  */
	public void setScrappedQty (BigDecimal ScrappedQty)
	{
		set_Value (COLUMNNAME_ScrappedQty, ScrappedQty);
	}

	/** Get Scrapped Quantity.
		@return The Quantity scrapped due to QA issues
	  */
	public BigDecimal getScrappedQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ScrappedQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Target Quantity.
		@param TargetQty 
		Target Movement Quantity
	  */
	public void setTargetQty (BigDecimal TargetQty)
	{
		set_Value (COLUMNNAME_TargetQty, TargetQty);
	}

	/** Get Target Quantity.
		@return Target Movement Quantity
	  */
	public BigDecimal getTargetQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TargetQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}