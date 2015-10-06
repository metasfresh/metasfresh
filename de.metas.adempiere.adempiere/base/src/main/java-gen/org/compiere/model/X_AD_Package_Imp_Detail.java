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

/** Generated Model for AD_Package_Imp_Detail
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Package_Imp_Detail extends PO implements I_AD_Package_Imp_Detail, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Package_Imp_Detail (Properties ctx, int AD_Package_Imp_Detail_ID, String trxName)
    {
      super (ctx, AD_Package_Imp_Detail_ID, trxName);
      /** if (AD_Package_Imp_Detail_ID == 0)
        {
			setAD_Original_ID (0);
			setAD_Package_Imp_Detail_ID (0);
			setAD_Package_Imp_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Package_Imp_Detail (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Package_Imp_Detail[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Action.
		@param Action 
		Indicates the Action to be performed
	  */
	public void setAction (String Action)
	{
		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Action.
		@return Indicates the Action to be performed
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
	}

	/** Set Ad_Backup_ID.
		@param Ad_Backup_ID Ad_Backup_ID	  */
	public void setAd_Backup_ID (int Ad_Backup_ID)
	{
		if (Ad_Backup_ID < 1) 
			set_Value (COLUMNNAME_Ad_Backup_ID, null);
		else 
			set_Value (COLUMNNAME_Ad_Backup_ID, Integer.valueOf(Ad_Backup_ID));
	}

	/** Get Ad_Backup_ID.
		@return Ad_Backup_ID	  */
	public int getAd_Backup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ad_Backup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Original_ID.
		@param AD_Original_ID AD_Original_ID	  */
	public void setAD_Original_ID (int AD_Original_ID)
	{
		if (AD_Original_ID < 1) 
			set_Value (COLUMNNAME_AD_Original_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Original_ID, Integer.valueOf(AD_Original_ID));
	}

	/** Get AD_Original_ID.
		@return AD_Original_ID	  */
	public int getAD_Original_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Original_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Package_Imp_Detail_ID.
		@param AD_Package_Imp_Detail_ID AD_Package_Imp_Detail_ID	  */
	public void setAD_Package_Imp_Detail_ID (int AD_Package_Imp_Detail_ID)
	{
		if (AD_Package_Imp_Detail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_Detail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_Detail_ID, Integer.valueOf(AD_Package_Imp_Detail_ID));
	}

	/** Get AD_Package_Imp_Detail_ID.
		@return AD_Package_Imp_Detail_ID	  */
	public int getAD_Package_Imp_Detail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Package_Imp_Detail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Package_Imp_ID.
		@param AD_Package_Imp_ID AD_Package_Imp_ID	  */
	public void setAD_Package_Imp_ID (int AD_Package_Imp_ID)
	{
		if (AD_Package_Imp_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_ID, Integer.valueOf(AD_Package_Imp_ID));
	}

	/** Get AD_Package_Imp_ID.
		@return AD_Package_Imp_ID	  */
	public int getAD_Package_Imp_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Package_Imp_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Success.
		@param Success Success	  */
	public void setSuccess (String Success)
	{
		set_Value (COLUMNNAME_Success, Success);
	}

	/** Get Success.
		@return Success	  */
	public String getSuccess () 
	{
		return (String)get_Value(COLUMNNAME_Success);
	}

	/** Set DB Table Name.
		@param TableName 
		Name of the table in the database
	  */
	public void setTableName (String TableName)
	{
		set_Value (COLUMNNAME_TableName, TableName);
	}

	/** Get DB Table Name.
		@return Name of the table in the database
	  */
	public String getTableName () 
	{
		return (String)get_Value(COLUMNNAME_TableName);
	}

	/** Set Type.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	public void setType (String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Type.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	/** Set Uninstall.
		@param Uninstall Uninstall	  */
	public void setUninstall (boolean Uninstall)
	{
		set_ValueNoCheck (COLUMNNAME_Uninstall, Boolean.valueOf(Uninstall));
	}

	/** Get Uninstall.
		@return Uninstall	  */
	public boolean isUninstall () 
	{
		Object oo = get_Value(COLUMNNAME_Uninstall);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}