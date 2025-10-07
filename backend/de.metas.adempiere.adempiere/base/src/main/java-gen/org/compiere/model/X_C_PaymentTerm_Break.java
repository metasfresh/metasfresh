// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_PaymentTerm_Break
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_PaymentTerm_Break extends org.compiere.model.PO implements I_C_PaymentTerm_Break, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2040939261L;

    /** Standard Constructor */
    public X_C_PaymentTerm_Break (final Properties ctx, final int C_PaymentTerm_Break_ID, @Nullable final String trxName)
    {
      super (ctx, C_PaymentTerm_Break_ID, trxName);
    }

    /** Load Constructor */
    public X_C_PaymentTerm_Break (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_PaymentTerm_Break_ID (final int C_PaymentTerm_Break_ID)
	{
		if (C_PaymentTerm_Break_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_Break_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentTerm_Break_ID, C_PaymentTerm_Break_ID);
	}

	@Override
	public int getC_PaymentTerm_Break_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_Break_ID);
	}

	@Override
	public void setC_PaymentTerm_ID (final int C_PaymentTerm_ID)
	{
		if (C_PaymentTerm_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_ID, C_PaymentTerm_ID);
	}

	@Override
	public int getC_PaymentTerm_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setLine (final int Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	@Override
	public int getLine() 
	{
		return get_ValueAsInt(COLUMNNAME_Line);
	}

	@Override
	public void setOffsetDays (final int OffsetDays)
	{
		set_Value (COLUMNNAME_OffsetDays, OffsetDays);
	}

	@Override
	public int getOffsetDays() 
	{
		return get_ValueAsInt(COLUMNNAME_OffsetDays);
	}

	@Override
	public void setPercent (final @Nullable BigDecimal Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	@Override
	public BigDecimal getPercent() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Percent);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ReferenceDateType AD_Reference_ID=541989
	 * Reference name: ReferenceDateType
	 */
	public static final int REFERENCEDATETYPE_AD_Reference_ID=541989;
	/** InvoiceDate = IV */
	public static final String REFERENCEDATETYPE_InvoiceDate = "IV";
	/** BLDate = BL */
	public static final String REFERENCEDATETYPE_BLDate = "BL";
	/** OrderDate = OD */
	public static final String REFERENCEDATETYPE_OrderDate = "OD";
	/** LCDate = LC */
	public static final String REFERENCEDATETYPE_LCDate = "LC";
	/** ETADate = ET */
	public static final String REFERENCEDATETYPE_ETADate = "ET";
	@Override
	public void setReferenceDateType (final java.lang.String ReferenceDateType)
	{
		set_Value (COLUMNNAME_ReferenceDateType, ReferenceDateType);
	}

	@Override
	public java.lang.String getReferenceDateType() 
	{
		return get_ValueAsString(COLUMNNAME_ReferenceDateType);
	}
}