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
import org.compiere.util.KeyNamePair;

/** Generated Model for AD_HouseKeeping
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_HouseKeeping extends PO implements I_AD_HouseKeeping, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_HouseKeeping (Properties ctx, int AD_HouseKeeping_ID, String trxName)
    {
      super (ctx, AD_HouseKeeping_ID, trxName);
      /** if (AD_HouseKeeping_ID == 0)
        {
			setAD_HouseKeeping_ID (0);
			setAD_Table_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_HouseKeeping (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_HouseKeeping[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set House Keeping Configuration.
		@param AD_HouseKeeping_ID House Keeping Configuration	  */
	public void setAD_HouseKeeping_ID (int AD_HouseKeeping_ID)
	{
		if (AD_HouseKeeping_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_HouseKeeping_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_HouseKeeping_ID, Integer.valueOf(AD_HouseKeeping_ID));
	}

	/** Get House Keeping Configuration.
		@return House Keeping Configuration	  */
	public int getAD_HouseKeeping_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_HouseKeeping_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Table getAD_Table() throws RuntimeException
    {
		return (I_AD_Table)MTable.get(getCtx(), I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

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

	/** Set Backup Folder.
		@param BackupFolder 
		Backup Folder
	  */
	public void setBackupFolder (String BackupFolder)
	{
		set_Value (COLUMNNAME_BackupFolder, BackupFolder);
	}

	/** Get Backup Folder.
		@return Backup Folder
	  */
	public String getBackupFolder () 
	{
		return (String)get_Value(COLUMNNAME_BackupFolder);
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

	/** Set Export XML Backup.
		@param IsExportXMLBackup Export XML Backup	  */
	public void setIsExportXMLBackup (boolean IsExportXMLBackup)
	{
		set_Value (COLUMNNAME_IsExportXMLBackup, Boolean.valueOf(IsExportXMLBackup));
	}

	/** Get Export XML Backup.
		@return Export XML Backup	  */
	public boolean isExportXMLBackup () 
	{
		Object oo = get_Value(COLUMNNAME_IsExportXMLBackup);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Save In Historic.
		@param IsSaveInHistoric Save In Historic	  */
	public void setIsSaveInHistoric (boolean IsSaveInHistoric)
	{
		set_Value (COLUMNNAME_IsSaveInHistoric, Boolean.valueOf(IsSaveInHistoric));
	}

	/** Get Save In Historic.
		@return Save In Historic	  */
	public boolean isSaveInHistoric () 
	{
		Object oo = get_Value(COLUMNNAME_IsSaveInHistoric);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Last Deleted.
		@param LastDeleted Last Deleted	  */
	public void setLastDeleted (int LastDeleted)
	{
		set_Value (COLUMNNAME_LastDeleted, Integer.valueOf(LastDeleted));
	}

	/** Get Last Deleted.
		@return Last Deleted	  */
	public int getLastDeleted () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LastDeleted);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Last Run.
		@param LastRun Last Run	  */
	public void setLastRun (Timestamp LastRun)
	{
		set_Value (COLUMNNAME_LastRun, LastRun);
	}

	/** Get Last Run.
		@return Last Run	  */
	public Timestamp getLastRun () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LastRun);
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

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Search Key.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	public void setValue (String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Search Key.
		@return Search key for the record in the format required - must be unique
	  */
	public String getValue () 
	{
		return (String)get_Value(COLUMNNAME_Value);
	}

	/** Set Sql WHERE.
		@param WhereClause 
		Fully qualified SQL WHERE clause
	  */
	public void setWhereClause (String WhereClause)
	{
		set_Value (COLUMNNAME_WhereClause, WhereClause);
	}

	/** Get Sql WHERE.
		@return Fully qualified SQL WHERE clause
	  */
	public String getWhereClause () 
	{
		return (String)get_Value(COLUMNNAME_WhereClause);
	}
}