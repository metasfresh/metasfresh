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

/** Generated Model for CM_AccessProfile
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_AccessProfile extends PO implements I_CM_AccessProfile, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_AccessProfile (Properties ctx, int CM_AccessProfile_ID, String trxName)
    {
      super (ctx, CM_AccessProfile_ID, trxName);
      /** if (CM_AccessProfile_ID == 0)
        {
			setCM_AccessProfile_ID (0);
			setIsExclude (true);
// Y
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_CM_AccessProfile (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_AccessProfile[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Web Access Profile.
		@param CM_AccessProfile_ID 
		Web Access Profile
	  */
	public void setCM_AccessProfile_ID (int CM_AccessProfile_ID)
	{
		if (CM_AccessProfile_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_AccessProfile_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_AccessProfile_ID, Integer.valueOf(CM_AccessProfile_ID));
	}

	/** Get Web Access Profile.
		@return Web Access Profile
	  */
	public int getCM_AccessProfile_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_AccessProfile_ID);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Exclude.
		@param IsExclude 
		Exclude access to the data - if not selected Include access to the data
	  */
	public void setIsExclude (boolean IsExclude)
	{
		set_Value (COLUMNNAME_IsExclude, Boolean.valueOf(IsExclude));
	}

	/** Get Exclude.
		@return Exclude access to the data - if not selected Include access to the data
	  */
	public boolean isExclude () 
	{
		Object oo = get_Value(COLUMNNAME_IsExclude);
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