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
package de.metas.aggregation.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Aggregation_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Aggregation_Attribute extends org.compiere.model.PO implements I_C_Aggregation_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1727639712L;

    /** Standard Constructor */
    public X_C_Aggregation_Attribute (Properties ctx, int C_Aggregation_Attribute_ID, String trxName)
    {
      super (ctx, C_Aggregation_Attribute_ID, trxName);
      /** if (C_Aggregation_Attribute_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Aggregation_Attribute_ID (0);
			setEntityType (null);
// U
			setName (null);
			setType (null);
// A
        } */
    }

    /** Load Constructor */
    public X_C_Aggregation_Attribute (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aggregation Attribute.
		@param C_Aggregation_Attribute_ID Aggregation Attribute	  */
	@Override
	public void setC_Aggregation_Attribute_ID (int C_Aggregation_Attribute_ID)
	{
		if (C_Aggregation_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Aggregation_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Aggregation_Attribute_ID, Integer.valueOf(C_Aggregation_Attribute_ID));
	}

	/** Get Aggregation Attribute.
		@return Aggregation Attribute	  */
	@Override
	public int getC_Aggregation_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Aggregation_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Validierungscode.
		@param Code 
		Validierungscode
	  */
	@Override
	public void setCode (java.lang.String Code)
	{
		set_Value (COLUMNNAME_Code, Code);
	}

	/** Get Validierungscode.
		@return Validierungscode
	  */
	@Override
	public java.lang.String getCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Code);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entitäts-Art.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entitäts-Art.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	/** 
	 * Type AD_Reference_ID=540533
	 * Reference name: C_Aggregation_Attribute_Type
	 */
	public static final int TYPE_AD_Reference_ID=540533;
	/** Attribute = A */
	public static final String TYPE_Attribute = "A";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}