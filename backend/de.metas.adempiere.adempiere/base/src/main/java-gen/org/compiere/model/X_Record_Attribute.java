// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Record_Attribute
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_Record_Attribute extends org.compiere.model.PO implements I_Record_Attribute, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 33224834L;

    /** Standard Constructor */
    public X_Record_Attribute (final Properties ctx, final int Record_Attribute_ID, @Nullable final String trxName)
    {
      super (ctx, Record_Attribute_ID, trxName);
    }

    /** Load Constructor */
    public X_Record_Attribute (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
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
		set_ValueNoCheck (COLUMNNAME_AttributeValueType, AttributeValueType);
	}

	@Override
	public java.lang.String getAttributeValueType() 
	{
		return get_ValueAsString(COLUMNNAME_AttributeValueType);
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
	public org.compiere.model.I_M_AttributeSet_IncludedTab getM_AttributeSet_IncludedTab()
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSet_IncludedTab_ID, org.compiere.model.I_M_AttributeSet_IncludedTab.class);
	}

	@Override
	public void setM_AttributeSet_IncludedTab(final org.compiere.model.I_M_AttributeSet_IncludedTab M_AttributeSet_IncludedTab)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSet_IncludedTab_ID, org.compiere.model.I_M_AttributeSet_IncludedTab.class, M_AttributeSet_IncludedTab);
	}

	@Override
	public void setM_AttributeSet_IncludedTab_ID (final int M_AttributeSet_IncludedTab_ID)
	{
		if (M_AttributeSet_IncludedTab_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_IncludedTab_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_AttributeSet_IncludedTab_ID, M_AttributeSet_IncludedTab_ID);
	}

	@Override
	public int getM_AttributeSet_IncludedTab_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeSet_IncludedTab_ID);
	}

	@Override
	public void setM_AttributeValue_ID (final int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1) 
			set_Value (COLUMNNAME_M_AttributeValue_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeValue_ID, M_AttributeValue_ID);
	}

	@Override
	public int getM_AttributeValue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_AttributeValue_ID);
	}

	@Override
	public void setRecord_Attribute_ID (final int Record_Attribute_ID)
	{
		if (Record_Attribute_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Record_Attribute_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_Attribute_ID, Record_Attribute_ID);
	}

	@Override
	public int getRecord_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_Attribute_ID);
	}

	@Override
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}

	@Override
	public void setValueDate (final @Nullable java.sql.Timestamp ValueDate)
	{
		set_Value (COLUMNNAME_ValueDate, ValueDate);
	}

	@Override
	public java.sql.Timestamp getValueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValueDate);
	}

	@Override
	public void setValueNumber (final @Nullable BigDecimal ValueNumber)
	{
		set_Value (COLUMNNAME_ValueNumber, ValueNumber);
	}

	@Override
	public BigDecimal getValueNumber() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_ValueNumber);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setValueString (final @Nullable java.lang.String ValueString)
	{
		set_Value (COLUMNNAME_ValueString, ValueString);
	}

	@Override
	public java.lang.String getValueString() 
	{
		return get_ValueAsString(COLUMNNAME_ValueString);
	}
}