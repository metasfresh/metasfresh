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

/** Generated Model for I_InOutLineConfirm
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_I_InOutLineConfirm extends PO implements I_I_InOutLineConfirm, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_I_InOutLineConfirm (Properties ctx, int I_InOutLineConfirm_ID, String trxName)
    {
      super (ctx, I_InOutLineConfirm_ID, trxName);
      /** if (I_InOutLineConfirm_ID == 0)
        {
			setConfirmationNo (null);
			setConfirmedQty (Env.ZERO);
			setDifferenceQty (Env.ZERO);
			setI_InOutLineConfirm_ID (0);
			setI_IsImported (false);
			setM_InOutLineConfirm_ID (0);
			setScrappedQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_I_InOutLineConfirm (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_I_InOutLineConfirm[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Confirmation No.
		@param ConfirmationNo 
		Confirmation Number
	  */
	public void setConfirmationNo (String ConfirmationNo)
	{
		set_Value (COLUMNNAME_ConfirmationNo, ConfirmationNo);
	}

	/** Get Confirmation No.
		@return Confirmation Number
	  */
	public String getConfirmationNo () 
	{
		return (String)get_Value(COLUMNNAME_ConfirmationNo);
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

	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Ship/Receipt Confirmation Import Line.
		@param I_InOutLineConfirm_ID 
		Material Shipment or Receipt Confirmation Import Line
	  */
	public void setI_InOutLineConfirm_ID (int I_InOutLineConfirm_ID)
	{
		if (I_InOutLineConfirm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_InOutLineConfirm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_InOutLineConfirm_ID, Integer.valueOf(I_InOutLineConfirm_ID));
	}

	/** Get Ship/Receipt Confirmation Import Line.
		@return Material Shipment or Receipt Confirmation Import Line
	  */
	public int getI_InOutLineConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_InOutLineConfirm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getI_InOutLineConfirm_ID()));
    }

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_M_InOutLineConfirm getM_InOutLineConfirm() throws RuntimeException
    {
		return (I_M_InOutLineConfirm)MTable.get(getCtx(), I_M_InOutLineConfirm.Table_Name)
			.getPO(getM_InOutLineConfirm_ID(), get_TrxName());	}

	/** Set Ship/Receipt Confirmation Line.
		@param M_InOutLineConfirm_ID 
		Material Shipment or Receipt Confirmation Line
	  */
	public void setM_InOutLineConfirm_ID (int M_InOutLineConfirm_ID)
	{
		if (M_InOutLineConfirm_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLineConfirm_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLineConfirm_ID, Integer.valueOf(M_InOutLineConfirm_ID));
	}

	/** Get Ship/Receipt Confirmation Line.
		@return Material Shipment or Receipt Confirmation Line
	  */
	public int getM_InOutLineConfirm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLineConfirm_ID);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
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
}