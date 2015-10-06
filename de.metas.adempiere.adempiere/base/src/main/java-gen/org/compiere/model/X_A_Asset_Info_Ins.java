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

/** Generated Model for A_Asset_Info_Ins
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_A_Asset_Info_Ins extends PO implements I_A_Asset_Info_Ins, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_A_Asset_Info_Ins (Properties ctx, int A_Asset_Info_Ins_ID, String trxName)
    {
      super (ctx, A_Asset_Info_Ins_ID, trxName);
      /** if (A_Asset_Info_Ins_ID == 0)
        {
			setA_Asset_ID (0);
			setA_Asset_Info_Ins_ID (0);
        } */
    }

    /** Load Constructor */
    public X_A_Asset_Info_Ins (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_A_Asset_Info_Ins[")
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

	/** Set A_Asset_Info_Ins_ID.
		@param A_Asset_Info_Ins_ID A_Asset_Info_Ins_ID	  */
	public void setA_Asset_Info_Ins_ID (int A_Asset_Info_Ins_ID)
	{
		if (A_Asset_Info_Ins_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Info_Ins_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_A_Asset_Info_Ins_ID, Integer.valueOf(A_Asset_Info_Ins_ID));
	}

	/** Get A_Asset_Info_Ins_ID.
		@return A_Asset_Info_Ins_ID	  */
	public int getA_Asset_Info_Ins_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Info_Ins_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getA_Asset_Info_Ins_ID()));
    }

	/** Set Insurance Premium.
		@param A_Ins_Premium Insurance Premium	  */
	public void setA_Ins_Premium (BigDecimal A_Ins_Premium)
	{
		set_Value (COLUMNNAME_A_Ins_Premium, A_Ins_Premium);
	}

	/** Get Insurance Premium.
		@return Insurance Premium	  */
	public BigDecimal getA_Ins_Premium () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Ins_Premium);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Insurance Company.
		@param A_Insurance_Co Insurance Company	  */
	public void setA_Insurance_Co (String A_Insurance_Co)
	{
		set_Value (COLUMNNAME_A_Insurance_Co, A_Insurance_Co);
	}

	/** Get Insurance Company.
		@return Insurance Company	  */
	public String getA_Insurance_Co () 
	{
		return (String)get_Value(COLUMNNAME_A_Insurance_Co);
	}

	/** Set Insured Value.
		@param A_Ins_Value Insured Value	  */
	public void setA_Ins_Value (BigDecimal A_Ins_Value)
	{
		set_Value (COLUMNNAME_A_Ins_Value, A_Ins_Value);
	}

	/** Get Insured Value.
		@return Insured Value	  */
	public BigDecimal getA_Ins_Value () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Ins_Value);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Policy Number.
		@param A_Policy_No Policy Number	  */
	public void setA_Policy_No (String A_Policy_No)
	{
		set_Value (COLUMNNAME_A_Policy_No, A_Policy_No);
	}

	/** Get Policy Number.
		@return Policy Number	  */
	public String getA_Policy_No () 
	{
		return (String)get_Value(COLUMNNAME_A_Policy_No);
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

	/** Set Replacement Costs.
		@param A_Replace_Cost Replacement Costs	  */
	public void setA_Replace_Cost (BigDecimal A_Replace_Cost)
	{
		set_Value (COLUMNNAME_A_Replace_Cost, A_Replace_Cost);
	}

	/** Get Replacement Costs.
		@return Replacement Costs	  */
	public BigDecimal getA_Replace_Cost () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_A_Replace_Cost);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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