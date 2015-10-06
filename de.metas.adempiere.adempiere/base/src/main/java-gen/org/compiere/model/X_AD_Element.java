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

/** Generated Model for AD_Element
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_Element extends PO implements I_AD_Element, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_Element (Properties ctx, int AD_Element_ID, String trxName)
    {
      super (ctx, AD_Element_ID, trxName);
      /** if (AD_Element_ID == 0)
        {
			setAD_Element_ID (0);
			setColumnName (null);
			setEntityType (null);
// U
			setName (null);
			setPrintName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Element (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Element[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set System Element.
		@param AD_Element_ID 
		System Element enables the central maintenance of column description and help.
	  */
	public void setAD_Element_ID (int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Element_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Element_ID, Integer.valueOf(AD_Element_ID));
	}

	/** Get System Element.
		@return System Element enables the central maintenance of column description and help.
	  */
	public int getAD_Element_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Element_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set DB Column Name.
		@param ColumnName 
		Name of the column in the database
	  */
	public void setColumnName (String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	/** Get DB Column Name.
		@return Name of the column in the database
	  */
	public String getColumnName () 
	{
		return (String)get_Value(COLUMNNAME_ColumnName);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getColumnName());
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

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
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

	/** Set PO Description.
		@param PO_Description 
		Description in PO Screens
	  */
	public void setPO_Description (String PO_Description)
	{
		set_Value (COLUMNNAME_PO_Description, PO_Description);
	}

	/** Get PO Description.
		@return Description in PO Screens
	  */
	public String getPO_Description () 
	{
		return (String)get_Value(COLUMNNAME_PO_Description);
	}

	/** Set PO Help.
		@param PO_Help 
		Help for PO Screens
	  */
	public void setPO_Help (String PO_Help)
	{
		set_Value (COLUMNNAME_PO_Help, PO_Help);
	}

	/** Get PO Help.
		@return Help for PO Screens
	  */
	public String getPO_Help () 
	{
		return (String)get_Value(COLUMNNAME_PO_Help);
	}

	/** Set PO Name.
		@param PO_Name 
		Name on PO Screens
	  */
	public void setPO_Name (String PO_Name)
	{
		set_Value (COLUMNNAME_PO_Name, PO_Name);
	}

	/** Get PO Name.
		@return Name on PO Screens
	  */
	public String getPO_Name () 
	{
		return (String)get_Value(COLUMNNAME_PO_Name);
	}

	/** Set PO Print name.
		@param PO_PrintName 
		Print name on PO Screens/Reports
	  */
	public void setPO_PrintName (String PO_PrintName)
	{
		set_Value (COLUMNNAME_PO_PrintName, PO_PrintName);
	}

	/** Get PO Print name.
		@return Print name on PO Screens/Reports
	  */
	public String getPO_PrintName () 
	{
		return (String)get_Value(COLUMNNAME_PO_PrintName);
	}

	/** Set Print Text.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Print Text.
		@return The label text to be printed on a document or correspondence.
	  */
	public String getPrintName () 
	{
		return (String)get_Value(COLUMNNAME_PrintName);
	}
}