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

/** Generated Model for C_Channel
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Channel extends PO implements I_C_Channel, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_Channel (Properties ctx, int C_Channel_ID, String trxName)
    {
      super (ctx, C_Channel_ID, trxName);
      /** if (C_Channel_ID == 0)
        {
			setC_Channel_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Channel (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Channel[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_PrintColor getAD_PrintColor() throws RuntimeException
    {
		return (I_AD_PrintColor)MTable.get(getCtx(), I_AD_PrintColor.Table_Name)
			.getPO(getAD_PrintColor_ID(), get_TrxName());	}

	/** Set Print Color.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Print Color.
		@return Color used for printing and display
	  */
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Channel.
		@param C_Channel_ID 
		Sales Channel
	  */
	public void setC_Channel_ID (int C_Channel_ID)
	{
		if (C_Channel_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Channel_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Channel_ID, Integer.valueOf(C_Channel_ID));
	}

	/** Get Channel.
		@return Sales Channel
	  */
	public int getC_Channel_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Channel_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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