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
import java.sql.Timestamp;
import java.util.Properties;

/** Generated Model for AD_Attribute_Value
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Attribute_Value extends PO implements I_AD_Attribute_Value, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Attribute_Value (Properties ctx, int AD_Attribute_Value_ID, String trxName)
    {
      super (ctx, AD_Attribute_Value_ID, trxName);
      /** if (AD_Attribute_Value_ID == 0)
        {
			setAD_Attribute_ID (0);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Attribute_Value (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Attribute_Value[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Attribute.
		@param AD_Attribute_ID System Attribute	  */
	public void setAD_Attribute_ID (int AD_Attribute_ID)
	{
		if (AD_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Attribute_ID, Integer.valueOf(AD_Attribute_ID));
	}

	/** Get System Attribute.
		@return System Attribute	  */
	public int getAD_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Record ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Record ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set V_Date.
		@param V_Date V_Date	  */
	public void setV_Date (Timestamp V_Date)
	{
		set_Value (COLUMNNAME_V_Date, V_Date);
	}

	/** Get V_Date.
		@return V_Date	  */
	public Timestamp getV_Date () 
	{
		return (Timestamp)get_Value(COLUMNNAME_V_Date);
	}

	/** Set V_Number.
		@param V_Number V_Number	  */
	public void setV_Number (String V_Number)
	{
		set_Value (COLUMNNAME_V_Number, V_Number);
	}

	/** Get V_Number.
		@return V_Number	  */
	public String getV_Number () 
	{
		return (String)get_Value(COLUMNNAME_V_Number);
	}

	/** Set V_String.
		@param V_String V_String	  */
	public void setV_String (String V_String)
	{
		set_Value (COLUMNNAME_V_String, V_String);
	}

	/** Get V_String.
		@return V_String	  */
	public String getV_String () 
	{
		return (String)get_Value(COLUMNNAME_V_String);
	}
}