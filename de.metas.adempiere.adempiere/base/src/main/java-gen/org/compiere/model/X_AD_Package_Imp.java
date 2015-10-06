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

/** Generated Model for AD_Package_Imp
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Package_Imp extends PO implements I_AD_Package_Imp, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Package_Imp (Properties ctx, int AD_Package_Imp_ID, String trxName)
    {
      super (ctx, AD_Package_Imp_ID, trxName);
      /** if (AD_Package_Imp_ID == 0)
        {
			setAD_Package_Imp_ID (0);
			setDescription (null);
			setName (null);
			setProcessing (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Package_Imp (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Package_Imp[")
        .append(get_ID()).append("]");
      return sb.toString();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_Package_Imp_ID()));
    }

	/** Set CreatedDate.
		@param CreatedDate CreatedDate	  */
	public void setCreatedDate (String CreatedDate)
	{
		set_Value (COLUMNNAME_CreatedDate, CreatedDate);
	}

	/** Get CreatedDate.
		@return CreatedDate	  */
	public String getCreatedDate () 
	{
		return (String)get_Value(COLUMNNAME_CreatedDate);
	}

	/** Set Creator.
		@param Creator Creator	  */
	public void setCreator (String Creator)
	{
		set_Value (COLUMNNAME_Creator, Creator);
	}

	/** Get Creator.
		@return Creator	  */
	public String getCreator () 
	{
		return (String)get_Value(COLUMNNAME_Creator);
	}

	/** Set CreatorContact.
		@param CreatorContact CreatorContact	  */
	public void setCreatorContact (String CreatorContact)
	{
		set_Value (COLUMNNAME_CreatorContact, CreatorContact);
	}

	/** Get CreatorContact.
		@return CreatorContact	  */
	public String getCreatorContact () 
	{
		return (String)get_Value(COLUMNNAME_CreatorContact);
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

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
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

	/** Set PK_Status.
		@param PK_Status PK_Status	  */
	public void setPK_Status (String PK_Status)
	{
		set_Value (COLUMNNAME_PK_Status, PK_Status);
	}

	/** Get PK_Status.
		@return PK_Status	  */
	public String getPK_Status () 
	{
		return (String)get_Value(COLUMNNAME_PK_Status);
	}

	/** Set Package Version.
		@param PK_Version Package Version	  */
	public void setPK_Version (String PK_Version)
	{
		set_Value (COLUMNNAME_PK_Version, PK_Version);
	}

	/** Get Package Version.
		@return Package Version	  */
	public String getPK_Version () 
	{
		return (String)get_Value(COLUMNNAME_PK_Version);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Release No.
		@param ReleaseNo 
		Internal Release Number
	  */
	public void setReleaseNo (String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	/** Get Release No.
		@return Internal Release Number
	  */
	public String getReleaseNo () 
	{
		return (String)get_Value(COLUMNNAME_ReleaseNo);
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

	/** Set UpdatedDate.
		@param UpdatedDate UpdatedDate	  */
	public void setUpdatedDate (String UpdatedDate)
	{
		set_Value (COLUMNNAME_UpdatedDate, UpdatedDate);
	}

	/** Get UpdatedDate.
		@return UpdatedDate	  */
	public String getUpdatedDate () 
	{
		return (String)get_Value(COLUMNNAME_UpdatedDate);
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	public void setVersion (String Version)
	{
		set_Value (COLUMNNAME_Version, Version);
	}

	/** Get Version.
		@return Version of the table definition
	  */
	public String getVersion () 
	{
		return (String)get_Value(COLUMNNAME_Version);
	}
}