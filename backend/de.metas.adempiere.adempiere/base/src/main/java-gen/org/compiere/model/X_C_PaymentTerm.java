// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PaymentTerm
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PaymentTerm extends org.compiere.model.PO implements I_C_PaymentTerm, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1464354586L;

    /** Standard Constructor */
    public X_C_PaymentTerm (final Properties ctx, final int C_PaymentTerm_ID, @Nullable final String trxName)
    {
      super (ctx, C_PaymentTerm_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PaymentTerm (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAfterDelivery (final boolean AfterDelivery)
	{
		set_Value (COLUMNNAME_AfterDelivery, AfterDelivery);
	}

	@Override
	public boolean isAfterDelivery() 
	{
		return get_ValueAsBoolean(COLUMNNAME_AfterDelivery);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
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
	public void setDiscount (final BigDecimal Discount)
	{
		set_Value (COLUMNNAME_Discount, Discount);
	}

	@Override
	public BigDecimal getDiscount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscount2 (final BigDecimal Discount2)
	{
		set_Value (COLUMNNAME_Discount2, Discount2);
	}

	@Override
	public BigDecimal getDiscount2() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Discount2);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountDays (final int DiscountDays)
	{
		set_Value (COLUMNNAME_DiscountDays, DiscountDays);
	}

	@Override
	public int getDiscountDays() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays);
	}

	@Override
	public void setDiscountDays2 (final int DiscountDays2)
	{
		set_Value (COLUMNNAME_DiscountDays2, DiscountDays2);
	}

	@Override
	public int getDiscountDays2() 
	{
		return get_ValueAsInt(COLUMNNAME_DiscountDays2);
	}

	@Override
	public void setDocumentNote (final @Nullable java.lang.String DocumentNote)
	{
		set_Value (COLUMNNAME_DocumentNote, DocumentNote);
	}

	@Override
	public java.lang.String getDocumentNote() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNote);
	}

	@Override
	public void setExternalId (final @Nullable java.lang.String ExternalId)
	{
		set_Value (COLUMNNAME_ExternalId, ExternalId);
	}

	@Override
	public java.lang.String getExternalId() 
	{
		return get_ValueAsString(COLUMNNAME_ExternalId);
	}

	@Override
	public void setFixMonthCutoff (final int FixMonthCutoff)
	{
		set_Value (COLUMNNAME_FixMonthCutoff, FixMonthCutoff);
	}

	@Override
	public int getFixMonthCutoff() 
	{
		return get_ValueAsInt(COLUMNNAME_FixMonthCutoff);
	}

	@Override
	public void setFixMonthDay (final int FixMonthDay)
	{
		set_Value (COLUMNNAME_FixMonthDay, FixMonthDay);
	}

	@Override
	public int getFixMonthDay() 
	{
		return get_ValueAsInt(COLUMNNAME_FixMonthDay);
	}

	@Override
	public void setFixMonthOffset (final int FixMonthOffset)
	{
		set_Value (COLUMNNAME_FixMonthOffset, FixMonthOffset);
	}

	@Override
	public int getFixMonthOffset() 
	{
		return get_ValueAsInt(COLUMNNAME_FixMonthOffset);
	}

	@Override
	public void setGraceDays (final int GraceDays)
	{
		set_Value (COLUMNNAME_GraceDays, GraceDays);
	}

	@Override
	public int getGraceDays() 
	{
		return get_ValueAsInt(COLUMNNAME_GraceDays);
	}

	@Override
	public void setGroupCode (final @Nullable java.lang.String GroupCode)
	{
		set_Value (COLUMNNAME_GroupCode, GroupCode);
	}

	@Override
	public java.lang.String getGroupCode() 
	{
		return get_ValueAsString(COLUMNNAME_GroupCode);
	}

	@Override
	public void setIsComplex (final boolean IsComplex)
	{
		set_Value (COLUMNNAME_IsComplex, IsComplex);
	}

	@Override
	public boolean isComplex() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsComplex);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsDueFixed (final boolean IsDueFixed)
	{
		set_Value (COLUMNNAME_IsDueFixed, IsDueFixed);
	}

	@Override
	public boolean isDueFixed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDueFixed);
	}

	@Override
	public void setIsNextBusinessDay (final boolean IsNextBusinessDay)
	{
		set_Value (COLUMNNAME_IsNextBusinessDay, IsNextBusinessDay);
	}

	@Override
	public boolean isNextBusinessDay() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsNextBusinessDay);
	}

	@Override
	public void setIsValid (final boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, IsValid);
	}

	@Override
	public boolean isValid() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsValid);
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
	public void setName_Invoice (final @Nullable java.lang.String Name_Invoice)
	{
		set_Value (COLUMNNAME_Name_Invoice, Name_Invoice);
	}

	@Override
	public java.lang.String getName_Invoice() 
	{
		return get_ValueAsString(COLUMNNAME_Name_Invoice);
	}

	/** 
	 * NetDay AD_Reference_ID=167
	 * Reference name: Weekdays
	 */
	public static final int NETDAY_AD_Reference_ID=167;
	/** Sunday = 7 */
	public static final String NETDAY_Sunday = "7";
	/** Monday = 1 */
	public static final String NETDAY_Monday = "1";
	/** Tuesday = 2 */
	public static final String NETDAY_Tuesday = "2";
	/** Wednesday = 3 */
	public static final String NETDAY_Wednesday = "3";
	/** Thursday = 4 */
	public static final String NETDAY_Thursday = "4";
	/** Friday = 5 */
	public static final String NETDAY_Friday = "5";
	/** Saturday = 6 */
	public static final String NETDAY_Saturday = "6";
	@Override
	public void setNetDay (final @Nullable java.lang.String NetDay)
	{
		set_Value (COLUMNNAME_NetDay, NetDay);
	}

	@Override
	public java.lang.String getNetDay() 
	{
		return get_ValueAsString(COLUMNNAME_NetDay);
	}

	@Override
	public void setNetDays (final int NetDays)
	{
		set_Value (COLUMNNAME_NetDays, NetDays);
	}

	@Override
	public int getNetDays() 
	{
		return get_ValueAsInt(COLUMNNAME_NetDays);
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
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}