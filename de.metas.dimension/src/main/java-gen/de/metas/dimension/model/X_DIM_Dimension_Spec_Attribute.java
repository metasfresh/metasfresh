/** Generated Model - DO NOT CHANGE */
package de.metas.dimension.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for DIM_Dimension_Spec_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_DIM_Dimension_Spec_Attribute extends org.compiere.model.PO implements I_DIM_Dimension_Spec_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1849412027L;

    /** Standard Constructor */
    public X_DIM_Dimension_Spec_Attribute (Properties ctx, int DIM_Dimension_Spec_Attribute_ID, String trxName)
    {
      super (ctx, DIM_Dimension_Spec_Attribute_ID, trxName);
      /** if (DIM_Dimension_Spec_Attribute_ID == 0)
        {
			setDIM_Dimension_Spec_Attribute_ID (0);
			setDIM_Dimension_Spec_ID (0);
			setIsIncludeAllAttributeValues (true);
// Y
			setIsValueAggregate (false);
// N
			setM_Attribute_ID (0);
        } */
    }

    /** Load Constructor */
    public X_DIM_Dimension_Spec_Attribute (Properties ctx, ResultSet rs, String trxName)
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

	/** 
	 * AttributeValueType AD_Reference_ID=326
	 * Reference name: M_Attribute Value Type
	 */
	public static final int ATTRIBUTEVALUETYPE_AD_Reference_ID=326;
	/** StringMax40 = S */
	public static final String ATTRIBUTEVALUETYPE_StringMax40 = "S";
	/** Number = N */
	public static final String ATTRIBUTEVALUETYPE_Number = "N";
	/** List = L */
	public static final String ATTRIBUTEVALUETYPE_List = "L";
	/** Date = D */
	public static final String ATTRIBUTEVALUETYPE_Date = "D";
	/** Set Attribute Value Type.
		@param AttributeValueType 
		Type of Attribute Value
	  */
	@Override
	public void setAttributeValueType (java.lang.String AttributeValueType)
	{

		set_Value (COLUMNNAME_AttributeValueType, AttributeValueType);
	}

	/** Get Attribute Value Type.
		@return Type of Attribute Value
	  */
	@Override
	public java.lang.String getAttributeValueType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AttributeValueType);
	}

	/** Set Dimensionsspezifikation (Merkmal).
		@param DIM_Dimension_Spec_Attribute_ID Dimensionsspezifikation (Merkmal)	  */
	@Override
	public void setDIM_Dimension_Spec_Attribute_ID (int DIM_Dimension_Spec_Attribute_ID)
	{
		if (DIM_Dimension_Spec_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_Attribute_ID, Integer.valueOf(DIM_Dimension_Spec_Attribute_ID));
	}

	/** Get Dimensionsspezifikation (Merkmal).
		@return Dimensionsspezifikation (Merkmal)	  */
	@Override
	public int getDIM_Dimension_Spec_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.dimension.model.I_DIM_Dimension_Spec getDIM_Dimension_Spec() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class);
	}

	@Override
	public void setDIM_Dimension_Spec(de.metas.dimension.model.I_DIM_Dimension_Spec DIM_Dimension_Spec)
	{
		set_ValueFromPO(COLUMNNAME_DIM_Dimension_Spec_ID, de.metas.dimension.model.I_DIM_Dimension_Spec.class, DIM_Dimension_Spec);
	}

	/** Set Dimensionsspezifikation.
		@param DIM_Dimension_Spec_ID Dimensionsspezifikation	  */
	@Override
	public void setDIM_Dimension_Spec_ID (int DIM_Dimension_Spec_ID)
	{
		if (DIM_Dimension_Spec_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DIM_Dimension_Spec_ID, Integer.valueOf(DIM_Dimension_Spec_ID));
	}

	/** Get Dimensionsspezifikation.
		@return Dimensionsspezifikation	  */
	@Override
	public int getDIM_Dimension_Spec_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DIM_Dimension_Spec_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Alle Attributwerte.
		@param IsIncludeAllAttributeValues Alle Attributwerte	  */
	@Override
	public void setIsIncludeAllAttributeValues (boolean IsIncludeAllAttributeValues)
	{
		set_Value (COLUMNNAME_IsIncludeAllAttributeValues, Boolean.valueOf(IsIncludeAllAttributeValues));
	}

	/** Get Alle Attributwerte.
		@return Alle Attributwerte	  */
	@Override
	public boolean isIncludeAllAttributeValues () 
	{
		Object oo = get_Value(COLUMNNAME_IsIncludeAllAttributeValues);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Merkmalswert-Zusammenfassung.
		@param IsValueAggregate Merkmalswert-Zusammenfassung	  */
	@Override
	public void setIsValueAggregate (boolean IsValueAggregate)
	{
		set_Value (COLUMNNAME_IsValueAggregate, Boolean.valueOf(IsValueAggregate));
	}

	/** Get Merkmalswert-Zusammenfassung.
		@return Merkmalswert-Zusammenfassung	  */
	@Override
	public boolean isValueAggregate () 
	{
		Object oo = get_Value(COLUMNNAME_IsValueAggregate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_M_Attribute getM_Attribute() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class);
	}

	@Override
	public void setM_Attribute(org.compiere.model.I_M_Attribute M_Attribute)
	{
		set_ValueFromPO(COLUMNNAME_M_Attribute_ID, org.compiere.model.I_M_Attribute.class, M_Attribute);
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Produkt-Merkmal
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Produkt-Merkmal
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Gruppenname.
		@param ValueAggregateName Gruppenname	  */
	@Override
	public void setValueAggregateName (java.lang.String ValueAggregateName)
	{
		set_Value (COLUMNNAME_ValueAggregateName, ValueAggregateName);
	}

	/** Get Gruppenname.
		@return Gruppenname	  */
	@Override
	public java.lang.String getValueAggregateName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ValueAggregateName);
	}
}