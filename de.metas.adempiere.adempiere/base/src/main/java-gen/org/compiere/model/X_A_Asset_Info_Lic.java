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

/** Generated Model for A_Asset_Info_Lic
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Info_Lic extends PO implements I_A_Asset_Info_Lic, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Info_Lic (Properties ctx, int A_Asset_Info_Lic_ID, String trxName)
    {
      super (ctx, A_Asset_Info_Lic_ID, trxName);
      /** if (A_Asset_Info_Lic_ID == 0)
        {
			setA_Asset_ID (0);
			setA_Asset_Info_Lic_ID (0);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Info_Lic (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Info_Lic[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set A_Asset_Info_Lic_ID.
		@param A_Asset_Info_Lic_ID A_Asset_Info_Lic_ID	  */
	public void setA_Asset_Info_Lic_ID (int A_Asset_Info_Lic_ID)
	{
		if (A_Asset_Info_Lic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Info_Lic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Info_Lic_ID, Integer.valueOf(A_Asset_Info_Lic_ID));
	}

	/** Get A_Asset_Info_Lic_ID.
		@return A_Asset_Info_Lic_ID	  */
	public int getA_Asset_Info_Lic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Info_Lic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Info_Lic_ID()));
    }

	/** Set Issuing Agency.
		@param A_Issuing_Agency Issuing Agency	  */
	public void setA_Issuing_Agency (String A_Issuing_Agency)
	{
		set_Value (COLUMNNAME_A_Issuing_Agency, A_Issuing_Agency);
	}

	/** Get Issuing Agency.
		@return Issuing Agency	  */
	public String getA_Issuing_Agency () 
	{
		return (String)get_Value(COLUMNNAME_A_Issuing_Agency);
	}

	/** Set License Fee.
		@param A_License_Fee License Fee	  */
	public void setA_License_Fee (BigDecimal A_License_Fee)
	{
		set_Value (COLUMNNAME_A_License_Fee, A_License_Fee);
	}

	/** Get License Fee.
		@return License Fee	  */
	public BigDecimal getA_License_Fee () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_License_Fee);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set License No.
		@param A_License_No License No	  */
	public void setA_License_No (String A_License_No)
	{
		set_Value (COLUMNNAME_A_License_No, A_License_No);
	}

	/** Get License No.
		@return License No	  */
	public String getA_License_No () 
	{
		return (String)get_Value(COLUMNNAME_A_License_No);
	}

	/** Set Policy Renewal Date.
		@param A_Renewal_Date Policy Renewal Date	  */
	public void setA_Renewal_Date (Timestamp A_Renewal_Date)
	{
		set_Value (COLUMNNAME_A_Renewal_Date, A_Renewal_Date);
	}

	/** Get Policy Renewal Date.
		@return Policy Renewal Date	  */
	public Timestamp getA_Renewal_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_A_Renewal_Date);
	}

	/** Set Account State.
		@param A_State 
		State of the Credit Card or Account holder
	  */
	public void setA_State (String A_State)
	{
		set_Value (COLUMNNAME_A_State, A_State);
	}

	/** Get Account State.
		@return State of the Credit Card or Account holder
	  */
	public String getA_State () 
	{
		return (String)get_Value(COLUMNNAME_A_State);
	}

	/** Set Text.
		@param Text Text	  */
	public void setText (String Text)
	{
		set_Value (COLUMNNAME_Text, Text);
	}

	/** Get Text.
		@return Text	  */
	public String getText () 
	{
		return (String)get_Value(COLUMNNAME_Text);
	}
}