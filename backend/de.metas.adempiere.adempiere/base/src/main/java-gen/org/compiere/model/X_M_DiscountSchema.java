// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_DiscountSchema
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_DiscountSchema extends org.compiere.model.PO implements I_M_DiscountSchema, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 174899977L;

    /** Standard Constructor */
    public X_M_DiscountSchema (final Properties ctx, final int M_DiscountSchema_ID, @Nullable final String trxName)
    {
      super (ctx, M_DiscountSchema_ID, trxName);
    }

    /** Load Constructor */
    public X_M_DiscountSchema (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setBreakValue_Attribute_ID (final int BreakValue_Attribute_ID)
	{
		if (BreakValue_Attribute_ID < 1) 
			set_Value (COLUMNNAME_BreakValue_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_BreakValue_Attribute_ID, BreakValue_Attribute_ID);
	}

	@Override
	public int getBreakValue_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_BreakValue_Attribute_ID);
	}

	/** 
	 * BreakValueType AD_Reference_ID=540886
	 * Reference name: M_DiscountSchema_BreakValueType
	 */
	public static final int BREAKVALUETYPE_AD_Reference_ID=540886;
	/** Quantity = Q */
	public static final String BREAKVALUETYPE_Quantity = "Q";
	/** Amount = A */
	public static final String BREAKVALUETYPE_Amount = "A";
	/** Attribute = T */
	public static final String BREAKVALUETYPE_Attribute = "T";
	@Override
	public void setBreakValueType (final java.lang.String BreakValueType)
	{
		set_Value (COLUMNNAME_BreakValueType, BreakValueType);
	}

	@Override
	public java.lang.String getBreakValueType() 
	{
		return get_ValueAsString(COLUMNNAME_BreakValueType);
	}

	/** 
	 * CumulativeLevel AD_Reference_ID=246
	 * Reference name: M_Discount CumulativeLevel
	 */
	public static final int CUMULATIVELEVEL_AD_Reference_ID=246;
	/** Position = L */
	public static final String CUMULATIVELEVEL_Position = "L";
	@Override
	public void setCumulativeLevel (final @Nullable java.lang.String CumulativeLevel)
	{
		set_Value (COLUMNNAME_CumulativeLevel, CumulativeLevel);
	}

	@Override
	public java.lang.String getCumulativeLevel() 
	{
		return get_ValueAsString(COLUMNNAME_CumulativeLevel);
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

	/** 
	 * DiscountType AD_Reference_ID=247
	 * Reference name: M_Discount Type
	 */
	public static final int DISCOUNTTYPE_AD_Reference_ID=247;
	/** Flat Percent = F */
	public static final String DISCOUNTTYPE_FlatPercent = "F";
	/** Formula = S */
	public static final String DISCOUNTTYPE_Formula = "S";
	/** Breaks = B */
	public static final String DISCOUNTTYPE_Breaks = "B";
	/** Pricelist = P */
	public static final String DISCOUNTTYPE_Pricelist = "P";
	@Override
	public void setDiscountType (final java.lang.String DiscountType)
	{
		set_Value (COLUMNNAME_DiscountType, DiscountType);
	}

	@Override
	public java.lang.String getDiscountType() 
	{
		return get_ValueAsString(COLUMNNAME_DiscountType);
	}

	@Override
	public void setDoNotChangeZeroPrices (final boolean DoNotChangeZeroPrices)
	{
		set_Value (COLUMNNAME_DoNotChangeZeroPrices, DoNotChangeZeroPrices);
	}

	@Override
	public boolean isDoNotChangeZeroPrices() 
	{
		return get_ValueAsBoolean(COLUMNNAME_DoNotChangeZeroPrices);
	}

	@Override
	public void setFlatDiscount (final @Nullable BigDecimal FlatDiscount)
	{
		set_Value (COLUMNNAME_FlatDiscount, FlatDiscount);
	}

	@Override
	public BigDecimal getFlatDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_FlatDiscount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setIsBPartnerFlatDiscount (final boolean IsBPartnerFlatDiscount)
	{
		set_Value (COLUMNNAME_IsBPartnerFlatDiscount, IsBPartnerFlatDiscount);
	}

	@Override
	public boolean isBPartnerFlatDiscount() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsBPartnerFlatDiscount);
	}

	@Override
	public org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge getM_DiscountSchema_Calculated_Surcharge()
	{
		return get_ValueAsPO(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class);
	}

	@Override
	public void setM_DiscountSchema_Calculated_Surcharge(final org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge M_DiscountSchema_Calculated_Surcharge)
	{
		set_ValueFromPO(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, org.compiere.model.I_M_DiscountSchema_Calculated_Surcharge.class, M_DiscountSchema_Calculated_Surcharge);
	}

	@Override
	public void setM_DiscountSchema_Calculated_Surcharge_ID (final int M_DiscountSchema_Calculated_Surcharge_ID)
	{
		if (M_DiscountSchema_Calculated_Surcharge_ID < 1) 
			set_Value (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, null);
		else 
			set_Value (COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID, M_DiscountSchema_Calculated_Surcharge_ID);
	}

	@Override
	public int getM_DiscountSchema_Calculated_Surcharge_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_Calculated_Surcharge_ID);
	}

	@Override
	public void setM_DiscountSchema_ID (final int M_DiscountSchema_ID)
	{
		if (M_DiscountSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DiscountSchema_ID, M_DiscountSchema_ID);
	}

	@Override
	public int getM_DiscountSchema_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_DiscountSchema_ID);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setScript (final @Nullable java.lang.String Script)
	{
		set_Value (COLUMNNAME_Script, Script);
	}

	@Override
	public java.lang.String getScript() 
	{
		return get_ValueAsString(COLUMNNAME_Script);
	}

	@Override
	public void setValidFrom (final java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}
}