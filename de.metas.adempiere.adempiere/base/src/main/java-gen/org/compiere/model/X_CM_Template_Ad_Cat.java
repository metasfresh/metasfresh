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

/** Generated Model for CM_Template_Ad_Cat
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_Template_Ad_Cat extends PO implements I_CM_Template_Ad_Cat, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_Template_Ad_Cat (Properties ctx, int CM_Template_Ad_Cat_ID, String trxName)
    {
      super (ctx, CM_Template_Ad_Cat_ID, trxName);
      /** if (CM_Template_Ad_Cat_ID == 0)
        {
			setCM_Ad_Cat_ID (0);
			setCM_Template_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_Template_Ad_Cat (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_CM_Template_Ad_Cat[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_CM_Ad_Cat getCM_Ad_Cat() throws RuntimeException
    {
		return (I_CM_Ad_Cat)MTable.get(getCtx(), I_CM_Ad_Cat.Table_Name)
			.getPO(getCM_Ad_Cat_ID(), get_TrxName());	}

	/** Set Advertisement Category.
		@param CM_Ad_Cat_ID 
		Advertisement Category like Banner Homepage 
	  */
	public void setCM_Ad_Cat_ID (int CM_Ad_Cat_ID)
	{
		if (CM_Ad_Cat_ID < 1) 
			set_Value (COLUMNNAME_CM_Ad_Cat_ID, null);
		else 
			set_Value (COLUMNNAME_CM_Ad_Cat_ID, Integer.valueOf(CM_Ad_Cat_ID));
	}

	/** Get Advertisement Category.
		@return Advertisement Category like Banner Homepage 
	  */
	public int getCM_Ad_Cat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Ad_Cat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Template getCM_Template() throws RuntimeException
    {
		return (I_CM_Template)MTable.get(getCtx(), I_CM_Template.Table_Name)
			.getPO(getCM_Template_ID(), get_TrxName());	}

	/** Set Template.
		@param CM_Template_ID 
		Template defines how content is displayed
	  */
	public void setCM_Template_ID (int CM_Template_ID)
	{
		if (CM_Template_ID < 1) 
			set_Value (COLUMNNAME_CM_Template_ID, null);
		else 
			set_Value (COLUMNNAME_CM_Template_ID, Integer.valueOf(CM_Template_ID));
	}

	/** Get Template.
		@return Template defines how content is displayed
	  */
	public int getCM_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Template_ID);
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