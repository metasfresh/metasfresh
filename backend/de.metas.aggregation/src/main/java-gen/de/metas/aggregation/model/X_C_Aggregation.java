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

/** Generated Model for C_Aggregation
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Aggregation extends org.compiere.model.PO implements I_C_Aggregation, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1340639626L;

    /** Standard Constructor */
    public X_C_Aggregation (Properties ctx, int C_Aggregation_ID, String trxName)
    {
      super (ctx, C_Aggregation_ID, trxName);
      /** if (C_Aggregation_ID == 0)
        {
			setAD_Table_ID (0);
			setC_Aggregation_ID (0);
			setEntityType (null);
// U
			setIsDefault (false);
// N
			setIsDefaultPO (false);
// N
			setIsDefaultSO (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_Aggregation (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** 
	 * AggregationUsageLevel AD_Reference_ID=540546
	 * Reference name: C_Aggregation_AggregationUsageLevel
	 */
	public static final int AGGREGATIONUSAGELEVEL_AD_Reference_ID=540546;
	/** Header = H */
	public static final String AGGREGATIONUSAGELEVEL_Header = "H";
	/** Line = L */
	public static final String AGGREGATIONUSAGELEVEL_Line = "L";
	/** Set Level.
		@param AggregationUsageLevel 
		Aggregation usage level
	  */
	@Override
	public void setAggregationUsageLevel (java.lang.String AggregationUsageLevel)
	{

		set_Value (COLUMNNAME_AggregationUsageLevel, AggregationUsageLevel);
	}

	/** Get Level.
		@return Aggregation usage level
	  */
	@Override
	public java.lang.String getAggregationUsageLevel () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AggregationUsageLevel);
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

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard (Einkauf).
		@param IsDefaultPO 
		Default value for purchase transactions
	  */
	@Override
	public void setIsDefaultPO (boolean IsDefaultPO)
	{
		set_Value (COLUMNNAME_IsDefaultPO, Boolean.valueOf(IsDefaultPO));
	}

	/** Get Standard (Einkauf).
		@return Default value for purchase transactions
	  */
	@Override
	public boolean isDefaultPO () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultPO);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Standard (Verkauf).
		@param IsDefaultSO 
		Default value for sales transactions
	  */
	@Override
	public void setIsDefaultSO (boolean IsDefaultSO)
	{
		set_Value (COLUMNNAME_IsDefaultSO, Boolean.valueOf(IsDefaultSO));
	}

	/** Get Standard (Verkauf).
		@return Default value for sales transactions
	  */
	@Override
	public boolean isDefaultSO () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefaultSO);
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
}