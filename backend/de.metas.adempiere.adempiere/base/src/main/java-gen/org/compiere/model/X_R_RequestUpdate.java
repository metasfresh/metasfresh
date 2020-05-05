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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for R_RequestUpdate
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_R_RequestUpdate extends PO implements I_R_RequestUpdate, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_R_RequestUpdate (Properties ctx, int R_RequestUpdate_ID, String trxName)
    {
      super (ctx, R_RequestUpdate_ID, trxName);
      /** if (R_RequestUpdate_ID == 0)
        {
			setConfidentialTypeEntry (null);
			setR_Request_ID (0);
			setR_RequestUpdate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_RequestUpdate (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_R_RequestUpdate[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** ConfidentialTypeEntry AD_Reference_ID=340 */
	public static final int CONFIDENTIALTYPEENTRY_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPEENTRY_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPEENTRY_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPEENTRY_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPEENTRY_PrivateInformation = "P";
	/** Set Entry Confidentiality.
		@param ConfidentialTypeEntry 
		Confidentiality of the individual entry
	  */
	public void setConfidentialTypeEntry (String ConfidentialTypeEntry)
	{

		set_Value (COLUMNNAME_ConfidentialTypeEntry, ConfidentialTypeEntry);
	}

	/** Get Entry Confidentiality.
		@return Confidentiality of the individual entry
	  */
	public String getConfidentialTypeEntry () 
	{
		return (String)get_Value(COLUMNNAME_ConfidentialTypeEntry);
	}

	/** Set End Time.
		@param EndTime 
		End of the time span
	  */
	public void setEndTime (Timestamp EndTime)
	{
		set_Value (COLUMNNAME_EndTime, EndTime);
	}

	/** Get End Time.
		@return End of the time span
	  */
	public Timestamp getEndTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndTime);
	}

	public I_M_Product getM_ProductSpent() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_ProductSpent_ID(), get_TrxName());	}

	/** Set Product Used.
		@param M_ProductSpent_ID 
		Product/Resource/Service used in Request
	  */
	public void setM_ProductSpent_ID (int M_ProductSpent_ID)
	{
		if (M_ProductSpent_ID < 1) 
			set_Value (COLUMNNAME_M_ProductSpent_ID, null);
		else 
			set_Value (COLUMNNAME_M_ProductSpent_ID, Integer.valueOf(M_ProductSpent_ID));
	}

	/** Get Product Used.
		@return Product/Resource/Service used in Request
	  */
	public int getM_ProductSpent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductSpent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity Invoiced.
		@param QtyInvoiced 
		Invoiced Quantity
	  */
	public void setQtyInvoiced (BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	/** Get Quantity Invoiced.
		@return Invoiced Quantity
	  */
	public BigDecimal getQtyInvoiced () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyInvoiced);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Quantity Used.
		@param QtySpent 
		Quantity used for this event
	  */
	public void setQtySpent (BigDecimal QtySpent)
	{
		set_Value (COLUMNNAME_QtySpent, QtySpent);
	}

	/** Get Quantity Used.
		@return Quantity used for this event
	  */
	public BigDecimal getQtySpent () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtySpent);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Result.
		@param Result 
		Result of the action taken
	  */
	public void setResult (String Result)
	{
		set_ValueNoCheck (COLUMNNAME_Result, Result);
	}

	/** Get Result.
		@return Result of the action taken
	  */
	public String getResult () 
	{
		return (String)get_Value(COLUMNNAME_Result);
	}

	public I_R_Request getR_Request() throws RuntimeException
    {
		return (I_R_Request)MTable.get(getCtx(), I_R_Request.Table_Name)
			.getPO(getR_Request_ID(), get_TrxName());	}

	/** Set Request.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Request.
		@return Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request Update.
		@param R_RequestUpdate_ID 
		Request Updates
	  */
	public void setR_RequestUpdate_ID (int R_RequestUpdate_ID)
	{
		if (R_RequestUpdate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_RequestUpdate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_RequestUpdate_ID, Integer.valueOf(R_RequestUpdate_ID));
	}

	/** Get Request Update.
		@return Request Updates
	  */
	public int getR_RequestUpdate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_RequestUpdate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getR_RequestUpdate_ID()));
    }

	/** Set Start Time.
		@param StartTime 
		Time started
	  */
	public void setStartTime (Timestamp StartTime)
	{
		set_Value (COLUMNNAME_StartTime, StartTime);
	}

	/** Get Start Time.
		@return Time started
	  */
	public Timestamp getStartTime () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartTime);
	}
}