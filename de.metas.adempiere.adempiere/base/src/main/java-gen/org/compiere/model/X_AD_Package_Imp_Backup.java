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

/** Generated Model for AD_Package_Imp_Backup
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Package_Imp_Backup extends PO implements I_AD_Package_Imp_Backup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Package_Imp_Backup (Properties ctx, int AD_Package_Imp_Backup_ID, String trxName)
    {
      super (ctx, AD_Package_Imp_Backup_ID, trxName);
      /** if (AD_Package_Imp_Backup_ID == 0)
        {
			setAD_Package_Imp_Backup_ID (0);
			setAD_Package_Imp_Detail_ID (0);
			setAD_Package_Imp_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Package_Imp_Backup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Package_Imp_Backup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Column getAD_Column() throws RuntimeException
    {
		return (I_AD_Column)MTable.get(getCtx(), I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_Package_Imp_Backup_ID.
		@param AD_Package_Imp_Backup_ID AD_Package_Imp_Backup_ID	  */
	public void setAD_Package_Imp_Backup_ID (int AD_Package_Imp_Backup_ID)
	{
		if (AD_Package_Imp_Backup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_Backup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Package_Imp_Backup_ID, Integer.valueOf(AD_Package_Imp_Backup_ID));
	}

	/** Get AD_Package_Imp_Backup_ID.
		@return AD_Package_Imp_Backup_ID	  */
	public int getAD_Package_Imp_Backup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Package_Imp_Backup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Package_Imp_Backup_ID()));
    }

	/** Set AD_Package_Imp_Bck_Dir.
		@param AD_Package_Imp_Bck_Dir AD_Package_Imp_Bck_Dir	  */
	public void setAD_Package_Imp_Bck_Dir (String AD_Package_Imp_Bck_Dir)
	{
		set_Value (COLUMNNAME_AD_Package_Imp_Bck_Dir, AD_Package_Imp_Bck_Dir);
	}

	/** Get AD_Package_Imp_Bck_Dir.
		@return AD_Package_Imp_Bck_Dir	  */
	public String getAD_Package_Imp_Bck_Dir () 
	{
		return (String)get_Value(COLUMNNAME_AD_Package_Imp_Bck_Dir);
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

	/** Set AD_Package_Imp_Org_Dir.
		@param AD_Package_Imp_Org_Dir AD_Package_Imp_Org_Dir	  */
	public void setAD_Package_Imp_Org_Dir (String AD_Package_Imp_Org_Dir)
	{
		set_Value (COLUMNNAME_AD_Package_Imp_Org_Dir, AD_Package_Imp_Org_Dir);
	}

	/** Get AD_Package_Imp_Org_Dir.
		@return AD_Package_Imp_Org_Dir	  */
	public String getAD_Package_Imp_Org_Dir () 
	{
		return (String)get_Value(COLUMNNAME_AD_Package_Imp_Org_Dir);
	}

	public I_AD_Reference getAD_Reference() throws RuntimeException
    {
		return (I_AD_Reference)MTable.get(getCtx(), I_AD_Reference.Table_Name)
			.getPO(getAD_Reference_ID(), get_TrxName());	}

	/** Set Reference.
		@param AD_Reference_ID 
		System Reference and Validation
	  */
	public void setAD_Reference_ID (int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, Integer.valueOf(AD_Reference_ID));
	}

	/** Get Reference.
		@return System Reference and Validation
	  */
	public int getAD_Reference_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Reference_ID);
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

	/** Set ColValue.
		@param ColValue ColValue	  */
	public void setColValue (String ColValue)
	{
		set_Value (COLUMNNAME_ColValue, ColValue);
	}

	/** Get ColValue.
		@return ColValue	  */
	public String getColValue () 
	{
		return (String)get_Value(COLUMNNAME_ColValue);
	}

	/** Set Uninstall.
		@param Uninstall Uninstall	  */
	public void setUninstall (boolean Uninstall)
	{
		set_Value (COLUMNNAME_Uninstall, Boolean.valueOf(Uninstall));
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