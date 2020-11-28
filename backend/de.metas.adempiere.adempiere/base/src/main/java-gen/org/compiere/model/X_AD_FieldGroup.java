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

/** Generated Model for AD_FieldGroup
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_FieldGroup extends PO implements I_AD_FieldGroup, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_FieldGroup (Properties ctx, int AD_FieldGroup_ID, String trxName)
    {
      super (ctx, AD_FieldGroup_ID, trxName);
      /** if (AD_FieldGroup_ID == 0)
        {
			setAD_FieldGroup_ID (0);
			setEntityType (null);
// U
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_AD_FieldGroup (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_FieldGroup[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Field Group.
		@param AD_FieldGroup_ID 
		Logical grouping of fields
	  */
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID)
	{
		if (AD_FieldGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_FieldGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_FieldGroup_ID, Integer.valueOf(AD_FieldGroup_ID));
	}

	/** Get Field Group.
		@return Logical grouping of fields
	  */
	public int getAD_FieldGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_FieldGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** FieldGroupType AD_Reference_ID=53000 */
	public static final int FIELDGROUPTYPE_AD_Reference_ID=53000;
	/** Tab = T */
	public static final String FIELDGROUPTYPE_Tab = "T";
	/** Label = L */
	public static final String FIELDGROUPTYPE_Label = "L";
	/** Collapse = C */
	public static final String FIELDGROUPTYPE_Collapse = "C";
	/** Set Field Group Type.
		@param FieldGroupType Field Group Type	  */
	public void setFieldGroupType (String FieldGroupType)
	{

		set_Value (COLUMNNAME_FieldGroupType, FieldGroupType);
	}

	/** Get Field Group Type.
		@return Field Group Type	  */
	public String getFieldGroupType () 
	{
		return (String)get_Value(COLUMNNAME_FieldGroupType);
	}

	/** Set Collapsed By Default.
		@param IsCollapsedByDefault 
		Flag to set the initial state of collapsible field group.
	  */
	public void setIsCollapsedByDefault (boolean IsCollapsedByDefault)
	{
		set_Value (COLUMNNAME_IsCollapsedByDefault, Boolean.valueOf(IsCollapsedByDefault));
	}

	/** Get Collapsed By Default.
		@return Flag to set the initial state of collapsible field group.
	  */
	public boolean isCollapsedByDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsCollapsedByDefault);
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