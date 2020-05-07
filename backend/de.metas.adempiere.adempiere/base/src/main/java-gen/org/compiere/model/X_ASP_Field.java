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

/** Generated Model for ASP_Field
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_ASP_Field extends PO implements I_ASP_Field, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_ASP_Field (Properties ctx, int ASP_Field_ID, String trxName)
    {
      super (ctx, ASP_Field_ID, trxName);
      /** if (ASP_Field_ID == 0)
        {
			setASP_Status (null);
// U
        } */
    }

    /** Load Constructor */
    public X_ASP_Field (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_ASP_Field[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Field getAD_Field() throws RuntimeException
    {
		return (I_AD_Field)MTable.get(getCtx(), I_AD_Field.Table_Name)
			.getPO(getAD_Field_ID(), get_TrxName());	}

	/** Set Field.
		@param AD_Field_ID 
		Field on a database table
	  */
	public void setAD_Field_ID (int AD_Field_ID)
	{
		if (AD_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Field_ID, Integer.valueOf(AD_Field_ID));
	}

	/** Get Field.
		@return Field on a database table
	  */
	public int getAD_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ASP Field.
		@param ASP_Field_ID ASP Field	  */
	public void setASP_Field_ID (int ASP_Field_ID)
	{
		if (ASP_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ASP_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ASP_Field_ID, Integer.valueOf(ASP_Field_ID));
	}

	/** Get ASP Field.
		@return ASP Field	  */
	public int getASP_Field_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Field_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public I_ASP_Tab getASP_Tab() throws RuntimeException
    {
		return (I_ASP_Tab)MTable.get(getCtx(), I_ASP_Tab.Table_Name)
			.getPO(getASP_Tab_ID(), get_TrxName());	}

	/** Set ASP Tab.
		@param ASP_Tab_ID ASP Tab	  */
	public void setASP_Tab_ID (int ASP_Tab_ID)
	{
		if (ASP_Tab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_ASP_Tab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_ASP_Tab_ID, Integer.valueOf(ASP_Tab_ID));
	}

	/** Get ASP Tab.
		@return ASP Tab	  */
	public int getASP_Tab_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ASP_Tab_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}