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
import org.compiere.util.KeyNamePair;

/** Generated Model for C_Greeting
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Greeting extends PO implements I_C_Greeting, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_Greeting (Properties ctx, int C_Greeting_ID, String trxName)
    {
      super (ctx, C_Greeting_ID, trxName);
      /** if (C_Greeting_ID == 0)
        {
			setC_Greeting_ID (0);
			setIsDefault (false);
			setIsFirstNameOnly (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Greeting (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Greeting[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Greeting.
		@param C_Greeting_ID 
		Greeting to print on correspondence
	  */
	public void setC_Greeting_ID (int C_Greeting_ID)
	{
		if (C_Greeting_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Greeting_ID, Integer.valueOf(C_Greeting_ID));
	}

	/** Get Greeting.
		@return Greeting to print on correspondence
	  */
	public int getC_Greeting_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Greeting_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Greeting.
		@param Greeting 
		For letters, e.g. "Dear {0}" or "Dear Mr. {0}" - At runtime, "{0}" is replaced by the name
	  */
	public void setGreeting (String Greeting)
	{
		set_Value (COLUMNNAME_Greeting, Greeting);
	}

	/** Get Greeting.
		@return For letters, e.g. "Dear {0}" or "Dear Mr. {0}" - At runtime, "{0}" is replaced by the name
	  */
	public String getGreeting () 
	{
		return (String)get_Value(COLUMNNAME_Greeting);
	}

	/** Set Default.
		@param IsDefault 
		Default value
	  */
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Default.
		@return Default value
	  */
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set First name only.
		@param IsFirstNameOnly 
		Print only the first name in greetings
	  */
	public void setIsFirstNameOnly (boolean IsFirstNameOnly)
	{
		set_Value (COLUMNNAME_IsFirstNameOnly, Boolean.valueOf(IsFirstNameOnly));
	}

	/** Get First name only.
		@return Print only the first name in greetings
	  */
	public boolean isFirstNameOnly () 
	{
		Object oo = get_Value(COLUMNNAME_IsFirstNameOnly);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }
}