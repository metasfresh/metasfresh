// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_Attribute extends org.compiere.model.PO implements I_M_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 353596514L;

    /** Standard Constructor */
    public X_M_Attribute (final Properties ctx, final int M_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, M_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_JavaClass_ID (final int AD_JavaClass_ID)
	{
		if (AD_JavaClass_ID < 1) 
			set_Value (COLUMNNAME_AD_JavaClass_ID, null);
		else 
			set_Value (COLUMNNAME_AD_JavaClass_ID, AD_JavaClass_ID);
	}

	@Override
	public int getAD_JavaClass_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_JavaClass_ID);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
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
	@Override
	public void setAttributeValueType (final java.lang.String AttributeValueType)
	{
		set_Value (COLUMNNAME_AttributeValueType, AttributeValueType);
	}

	@Override
	public java.lang.String getAttributeValueType() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeValueType);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDescriptionPattern (final @Nullable java.lang.String DescriptionPattern)
	{
		set_Value (COLUMNNAME_DescriptionPattern, DescriptionPattern);
	}

	@Override
	public java.lang.String getDescriptionPattern() 
	{
		return get_ValueAsString(COLUMNNAME_DescriptionPattern);
	}

	@Override
	public void setIsAlwaysUpdateable (final boolean IsAlwaysUpdateable)
	{
		set_Value (COLUMNNAME_IsAlwaysUpdateable, IsAlwaysUpdateable);
	}

	@Override
	public boolean isAlwaysUpdateable() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAlwaysUpdateable);
	}

	@Override
	public void setIsAttrDocumentRelevant (final boolean IsAttrDocumentRelevant)
	{
		set_Value (COLUMNNAME_IsAttrDocumentRelevant, IsAttrDocumentRelevant);
	}

	@Override
	public boolean isAttrDocumentRelevant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAttrDocumentRelevant);
	}

	@Override
	public void setIsHighVolume (final boolean IsHighVolume)
	{
		set_Value (COLUMNNAME_IsHighVolume, IsHighVolume);
	}

	@Override
	public boolean isHighVolume() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHighVolume);
	}

	@Override
	public void setIsInstanceAttribute (final boolean IsInstanceAttribute)
	{
		set_Value (COLUMNNAME_IsInstanceAttribute, IsInstanceAttribute);
	}

	@Override
	public boolean isInstanceAttribute() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInstanceAttribute);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsPricingRelevant (final boolean IsPricingRelevant)
	{
		set_Value (COLUMNNAME_IsPricingRelevant, IsPricingRelevant);
	}

	@Override
	public boolean isPricingRelevant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPricingRelevant);
	}

	@Override
	public void setIsPrintedInDocument (final boolean IsPrintedInDocument)
	{
		set_Value (COLUMNNAME_IsPrintedInDocument, IsPrintedInDocument);
	}

	@Override
	public boolean isPrintedInDocument() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPrintedInDocument);
	}

	@Override
	public void setIsReadOnlyValues (final boolean IsReadOnlyValues)
	{
		set_Value (COLUMNNAME_IsReadOnlyValues, IsReadOnlyValues);
	}

	@Override
	public boolean isReadOnlyValues() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReadOnlyValues);
	}

	@Override
	public void setIsStorageRelevant (final boolean IsStorageRelevant)
	{
		set_Value (COLUMNNAME_IsStorageRelevant, IsStorageRelevant);
	}

	@Override
	public boolean isStorageRelevant() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStorageRelevant);
	}

	@Override
	public void setM_Attribute_ID (final int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Attribute_ID, M_Attribute_ID);
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public org.compiere.model.I_M_AttributeSearch getM_AttributeSearch()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSearch_ID, org.compiere.model.I_M_AttributeSearch.class);
	}

	@Override
	public void setM_AttributeSearch(final org.compiere.model.I_M_AttributeSearch M_AttributeSearch)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSearch_ID, org.compiere.model.I_M_AttributeSearch.class, M_AttributeSearch);
	}

	@Override
	public void setM_AttributeSearch_ID (final int M_AttributeSearch_ID)
	{
		if (M_AttributeSearch_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeSearch_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSearch_ID, M_AttributeSearch_ID);
	}

	@Override
	public int getM_AttributeSearch_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSearch_ID);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setPrintValue_Override (final @Nullable java.lang.String PrintValue_Override)
	{
		set_Value (COLUMNNAME_PrintValue_Override, PrintValue_Override);
	}

	@Override
	public java.lang.String getPrintValue_Override() 
	{
		return get_ValueAsString(COLUMNNAME_PrintValue_Override);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_ValueNoCheck (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setValueMax (final @Nullable BigDecimal ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	@Override
	public BigDecimal getValueMax() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueMax);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValueMin (final @Nullable BigDecimal ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	@Override
	public BigDecimal getValueMin() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueMin);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}