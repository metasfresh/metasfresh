/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Attribute
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Attribute extends org.compiere.model.PO implements I_M_Attribute, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 224866491L;

    /** Standard Constructor */
    public X_M_Attribute (Properties ctx, int M_Attribute_ID, String trxName)
    {
      super (ctx, M_Attribute_ID, trxName);
      /** if (M_Attribute_ID == 0)
        {
			setAttributeValueType (null);
// S
			setIsAttrDocumentRelevant (false);
// N
			setIsInstanceAttribute (false);
			setIsMandatory (false);
			setIsPricingRelevant (false);
// N
			setIsReadOnlyValues (false);
// N
			setM_Attribute_ID (0);
			setName (null);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_M_Attribute (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.javaclasses.model.I_AD_JavaClass getAD_JavaClass() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class);
	}

	@Override
	public void setAD_JavaClass(de.metas.javaclasses.model.I_AD_JavaClass AD_JavaClass)
	{
		set_ValueFromPO(COLUMNNAME_AD_JavaClass_ID, de.metas.javaclasses.model.I_AD_JavaClass.class, AD_JavaClass);
	}

	/** Set Java Klasse.
		@param AD_JavaClass_ID Java Klasse	  */
	@Override
	public void setAD_JavaClass_ID (int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, Integer.valueOf(AD_JavaClass_ID));
	}

	/** Get Java Klasse.
		@return Java Klasse	  */
	@Override
	public int getAD_JavaClass_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_JavaClass_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	/** Set Dynamische Validierung.
		@param AD_Val_Rule_ID 
		Regel für die  dynamische Validierung
	  */
	@Override
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, Integer.valueOf(AD_Val_Rule_ID));
	}

	/** Get Dynamische Validierung.
		@return Regel für die  dynamische Validierung
	  */
	@Override
	public int getAD_Val_Rule_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Val_Rule_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
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

	/** Set Auf Belegen auszuweisen.
		@param IsAttrDocumentRelevant 
		Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	  */
	@Override
	public void setIsAttrDocumentRelevant (boolean IsAttrDocumentRelevant)
	{
		set_Value (COLUMNNAME_IsAttrDocumentRelevant, Boolean.valueOf(IsAttrDocumentRelevant));
	}

	/** Get Auf Belegen auszuweisen.
		@return Wenn ein Attribute auf Belegen auszuweisen ist, bedeutet das, das Lieferposionen mit unterschiedlichen Attributwerten nicht zu einer Rechnungszeile zusammengefasst werden können.
	  */
	@Override
	public boolean isAttrDocumentRelevant () 
	{
		Object oo = get_Value(COLUMNNAME_IsAttrDocumentRelevant);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Instanz-Attribut.
		@param IsInstanceAttribute 
		The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	  */
	@Override
	public void setIsInstanceAttribute (boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, Boolean.valueOf(IsInstanceAttribute));
	}

	/** Get Instanz-Attribut.
		@return The product attribute is specific to the instance (like Serial No, Lot or Guarantee Date)
	  */
	@Override
	public boolean isInstanceAttribute () 
	{
		Object oo = get_Value(COLUMNNAME_IsInstanceAttribute);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Pflichtangabe.
		@param IsMandatory 
		Data entry is required in this column
	  */
	@Override
	public void setIsMandatory (boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, Boolean.valueOf(IsMandatory));
	}

	/** Get Pflichtangabe.
		@return Data entry is required in this column
	  */
	@Override
	public boolean isMandatory () 
	{
		Object oo = get_Value(COLUMNNAME_IsMandatory);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set isPricingRelevant.
		@param IsPricingRelevant isPricingRelevant	  */
	@Override
	public void setIsPricingRelevant (boolean IsPricingRelevant)
	{
		set_Value (COLUMNNAME_IsPricingRelevant, Boolean.valueOf(IsPricingRelevant));
	}

	/** Get isPricingRelevant.
		@return isPricingRelevant	  */
	@Override
	public boolean isPricingRelevant () 
	{
		Object oo = get_Value(COLUMNNAME_IsPricingRelevant);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsReadOnlyValues.
		@param IsReadOnlyValues IsReadOnlyValues	  */
	@Override
	public void setIsReadOnlyValues (boolean IsReadOnlyValues)
	{
		set_Value (COLUMNNAME_IsReadOnlyValues, Boolean.valueOf(IsReadOnlyValues));
	}

	/** Get IsReadOnlyValues.
		@return IsReadOnlyValues	  */
	@Override
	public boolean isReadOnlyValues () 
	{
		Object oo = get_Value(COLUMNNAME_IsReadOnlyValues);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Merkmal.
		@param M_Attribute_ID 
		Product Attribute
	  */
	@Override
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	/** Get Merkmal.
		@return Product Attribute
	  */
	@Override
	public int getM_Attribute_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Attribute_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_AttributeSearch getM_AttributeSearch() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSearch_ID, org.compiere.model.I_M_AttributeSearch.class);
	}

	@Override
	public void setM_AttributeSearch(org.compiere.model.I_M_AttributeSearch M_AttributeSearch)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSearch_ID, org.compiere.model.I_M_AttributeSearch.class, M_AttributeSearch);
	}

	/** Set Merkmal-Suche.
		@param M_AttributeSearch_ID 
		Common Search Attribute 
	  */
	@Override
	public void setM_AttributeSearch_ID (int M_AttributeSearch_ID)
	{
		if (M_AttributeSearch_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeSearch_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSearch_ID, Integer.valueOf(M_AttributeSearch_ID));
	}

	/** Get Merkmal-Suche.
		@return Common Search Attribute 
	  */
	@Override
	public int getM_AttributeSearch_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSearch_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Suchschlüssel.
		@param Value 
		Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Max. Wert.
		@param ValueMax 
		Maximum Value for a field
	  */
	@Override
	public void setValueMax (java.math.BigDecimal ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	/** Get Max. Wert.
		@return Maximum Value for a field
	  */
	@Override
	public java.math.BigDecimal getValueMax () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueMax);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Min. Wert.
		@param ValueMin 
		Minimum Value for a field
	  */
	@Override
	public void setValueMin (java.math.BigDecimal ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	/** Get Min. Wert.
		@return Minimum Value for a field
	  */
	@Override
	public java.math.BigDecimal getValueMin () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueMin);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}