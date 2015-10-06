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

/** Generated Model for ASP_Window
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_ASP_Window extends PO implements I_ASP_Window, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_ASP_Window (Properties ctx, int ASP_Window_ID, String trxName)
    {
      super (ctx, ASP_Window_ID, trxName);
      /** if (ASP_Window_ID == 0)
        {
			setAD_Window_ID (0);
			setASP_Level_ID (0);
			setASP_Status (null);
// U
        } */
    }

    /** Load Constructor */
    public X_ASP_Window (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_ASP_Window[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Window getAD_Window() throws RuntimeException
    {
		return (I_AD_Window)MTable.get(getCtx(), I_AD_Window.Table_Name)
			.getPO(getAD_Window_ID(), get_TrxName());	}

	/** Set Window.
		@param AD_Window_ID 
		Data entry or display window
	  */
	public void setAD_Window_ID (int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Window_ID, Integer.valueOf(AD_Window_ID));
	}

	/** Get Window.
		@return Data entry or display window
	  */
	public int getAD_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_ASP_Level getASP_Level() throws RuntimeException
    {
		return (I_ASP_Level)MTable.get(getCtx(), I_ASP_Level.Table_Name)
			.getPO(getASP_Level_ID(), get_TrxName());	}

	/** Set ASP Level.
		@param ASP_Level_ID ASP Level	  */
	public void setASP_Level_ID (int ASP_Level_ID)
	{
		if (ASP_Level_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ASP_Level_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ASP_Level_ID, Integer.valueOf(ASP_Level_ID));
	}

	/** Get ASP Level.
		@return ASP Level	  */
	public int getASP_Level_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Level_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getASP_Level_ID()));
    }

	/** ASP_Status AD_Reference_ID=53234 */
	public static final int ASP_STATUS_AD_Reference_ID=53234;
	/** Hide = H */
	public static final String ASP_STATUS_Hide = "H";
	/** Show = S */
	public static final String ASP_STATUS_Show = "S";
	/** Undefined = U */
	public static final String ASP_STATUS_Undefined = "U";
	/** Set ASP Status.
		@param ASP_Status ASP Status	  */
	public void setASP_Status (String ASP_Status)
	{

		set_Value (COLUMNNAME_ASP_Status, ASP_Status);
	}

	/** Get ASP Status.
		@return ASP Status	  */
	public String getASP_Status () 
	{
		return (String)get_Value(COLUMNNAME_ASP_Status);
	}

	/** Set ASP Window.
		@param ASP_Window_ID ASP Window	  */
	public void setASP_Window_ID (int ASP_Window_ID)
	{
		if (ASP_Window_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ASP_Window_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ASP_Window_ID, Integer.valueOf(ASP_Window_ID));
	}

	/** Get ASP Window.
		@return ASP Window	  */
	public int getASP_Window_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Window_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}