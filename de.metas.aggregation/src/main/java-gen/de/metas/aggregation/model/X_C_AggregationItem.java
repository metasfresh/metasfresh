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

/** Generated Model for C_AggregationItem
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AggregationItem extends org.compiere.model.PO implements I_C_AggregationItem, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -353108449L;

    /** Standard Constructor */
    public X_C_AggregationItem (Properties ctx, int C_AggregationItem_ID, String trxName)
    {
      super (ctx, C_AggregationItem_ID, trxName);
      /** if (C_AggregationItem_ID == 0)
        {
			setC_Aggregation_ID (0);
			setC_AggregationItem_ID (0);
			setEntityType (null);
// U
			setType (null);
// COL
        } */
    }

    /** Load Constructor */
    public X_C_AggregationItem (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class);
	}

	@Override
	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column)
	{
		set_ValueFromPO(COLUMNNAME_AD_Column_ID, org.compiere.model.I_AD_Column.class, AD_Column);
	}

	/** Set Spalte.
		@param AD_Column_ID 
		Spalte in der Tabelle
	  */
	@Override
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Spalte.
		@return Spalte in der Tabelle
	  */
	@Override
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.aggregation.model.I_C_Aggregation_Attribute getC_Aggregation_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Aggregation_Attribute_ID, de.metas.aggregation.model.I_C_Aggregation_Attribute.class);
	}

	@Override
	public void setC_Aggregation_Attribute(de.metas.aggregation.model.I_C_Aggregation_Attribute C_Aggregation_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_C_Aggregation_Attribute_ID, de.metas.aggregation.model.I_C_Aggregation_Attribute.class, C_Aggregation_Attribute);
	}

	/** Set Aggregation Attribute.
		@param C_Aggregation_Attribute_ID Aggregation Attribute	  */
	@Override
	public void setC_Aggregation_Attribute_ID (int C_Aggregation_Attribute_ID)
	{
		if (C_Aggregation_Attribute_ID < 1) 
			set_Value (COLUMNNAME_C_Aggregation_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_C_Aggregation_Attribute_ID, Integer.valueOf(C_Aggregation_Attribute_ID));
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

	@Override
	public de.metas.aggregation.model.I_C_Aggregation getC_Aggregation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Aggregation_ID, de.metas.aggregation.model.I_C_Aggregation.class);
	}

	@Override
	public void setC_Aggregation(de.metas.aggregation.model.I_C_Aggregation C_Aggregation)
	{
		set_ValueFromPO(COLUMNNAME_C_Aggregation_ID, de.metas.aggregation.model.I_C_Aggregation.class, C_Aggregation);
	}

	/** Set Aggregation Definition.
		@param C_Aggregation_ID Aggregation Definition	  */
	@Override
	public void setC_Aggregation_ID (int C_Aggregation_ID)
	{
		if (C_Aggregation_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Aggregation_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Aggregation_ID, Integer.valueOf(C_Aggregation_ID));
	}

	/** Get Aggregation Definition.
		@return Aggregation Definition	  */
	@Override
	public int getC_Aggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Aggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Aggregation Item Definition.
		@param C_AggregationItem_ID Aggregation Item Definition	  */
	@Override
	public void setC_AggregationItem_ID (int C_AggregationItem_ID)
	{
		if (C_AggregationItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AggregationItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AggregationItem_ID, Integer.valueOf(C_AggregationItem_ID));
	}

	/** Get Aggregation Item Definition.
		@return Aggregation Item Definition	  */
	@Override
	public int getC_AggregationItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AggregationItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public de.metas.aggregation.model.I_C_Aggregation getIncluded_Aggregation() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Included_Aggregation_ID, de.metas.aggregation.model.I_C_Aggregation.class);
	}

	@Override
	public void setIncluded_Aggregation(de.metas.aggregation.model.I_C_Aggregation Included_Aggregation)
	{
		set_ValueFromPO(COLUMNNAME_Included_Aggregation_ID, de.metas.aggregation.model.I_C_Aggregation.class, Included_Aggregation);
	}

	/** Set Included Aggregation.
		@param Included_Aggregation_ID Included Aggregation	  */
	@Override
	public void setIncluded_Aggregation_ID (int Included_Aggregation_ID)
	{
		if (Included_Aggregation_ID < 1) 
			set_Value (COLUMNNAME_Included_Aggregation_ID, null);
		else 
			set_Value (COLUMNNAME_Included_Aggregation_ID, Integer.valueOf(Included_Aggregation_ID));
	}

	/** Get Included Aggregation.
		@return Included Aggregation	  */
	@Override
	public int getIncluded_Aggregation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Included_Aggregation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Include Logic.
		@param IncludeLogic 
		If expression is evaluated as true, the item will be included. If evaluated as false, the item will be excluded.
	  */
	@Override
	public void setIncludeLogic (java.lang.String IncludeLogic)
	{
		set_Value (COLUMNNAME_IncludeLogic, IncludeLogic);
	}

	/** Get Include Logic.
		@return If expression is evaluated as true, the item will be included. If evaluated as false, the item will be excluded.
	  */
	@Override
	public java.lang.String getIncludeLogic () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IncludeLogic);
	}

	/** 
	 * Type AD_Reference_ID=540532
	 * Reference name: C_AggregationItem_Type
	 */
	public static final int TYPE_AD_Reference_ID=540532;
	/** Column = COL */
	public static final String TYPE_Column = "COL";
	/** IncludedAggregation = INC */
	public static final String TYPE_IncludedAggregation = "INC";
	/** Attribute = ATR */
	public static final String TYPE_Attribute = "ATR";
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