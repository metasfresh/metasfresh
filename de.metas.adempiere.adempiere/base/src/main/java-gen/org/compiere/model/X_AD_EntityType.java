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

/** Generated Model for AD_EntityType
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_EntityType extends PO implements I_AD_EntityType, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_EntityType (Properties ctx, int AD_EntityType_ID, String trxName)
    {
      super (ctx, AD_EntityType_ID, trxName);
      /** if (AD_EntityType_ID == 0)
        {
			setAD_EntityType_ID (0);
			setEntityType (null);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_EntityType (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_EntityType[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Entity Type.
		@param AD_EntityType_ID 
		System Entity Type
	  */
	public void setAD_EntityType_ID (int AD_EntityType_ID)
	{
		if (AD_EntityType_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_EntityType_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_EntityType_ID, Integer.valueOf(AD_EntityType_ID));
	}

	/** Get Entity Type.
		@return System Entity Type
	  */
	public int getAD_EntityType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_EntityType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Classpath.
		@param Classpath 
		Extension Classpath
	  */
	public void setClasspath (String Classpath)
	{
		set_Value (COLUMNNAME_Classpath, Classpath);
	}

	/** Get Classpath.
		@return Extension Classpath
	  */
	public String getClasspath () 
	{
		return (String)get_Value(COLUMNNAME_Classpath);
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

	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{
		set_ValueNoCheck (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
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

	/** Set ModelPackage.
		@param ModelPackage 
		Java Package of the model classes
	  */
	public void setModelPackage (String ModelPackage)
	{
		set_Value (COLUMNNAME_ModelPackage, ModelPackage);
	}

	/** Get ModelPackage.
		@return Java Package of the model classes
	  */
	public String getModelPackage () 
	{
		return (String)get_Value(COLUMNNAME_ModelPackage);
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

	/** Set WebUIServletListenerClass.
		@param WebUIServletListenerClass 
	  */
	public void setWebUIServletListenerClass (String WebUIServletListenerClass)
	{
		set_Value (COLUMNNAME_WebUIServletListenerClass, WebUIServletListenerClass);
	}

	/** Get WebUIServletListenerClass.
		@return WebUIServletListenerClass
	  */
	public String getWebUIServletListenerClass () 
	{
		return (String)get_Value(COLUMNNAME_WebUIServletListenerClass);
	}
}