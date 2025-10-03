// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_OrderPaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_OrderPaySchedule extends org.compiere.model.PO implements I_C_OrderPaySchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1019055865L;

    /** Standard Constructor */
    public X_C_OrderPaySchedule (final Properties ctx, final int C_OrderPaySchedule_ID, @Nullable final String trxName)
    {
      super (ctx, C_OrderPaySchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_OrderPaySchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_Order_ID (final int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setC_OrderPaySchedule_ID (final int C_OrderPaySchedule_ID)
	{
		if (C_OrderPaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_OrderPaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_OrderPaySchedule_ID, C_OrderPaySchedule_ID);
	}

	@Override
	public int getC_OrderPaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderPaySchedule_ID);
	}

	@Override
	public void setC_PaymentTerm_Break_ID (final int C_PaymentTerm_Break_ID)
	{
		if (C_PaymentTerm_Break_ID < 1) 
			set_Value (COLUMNNAME_C_PaymentTerm_Break_ID, null);
		else 
			set_Value (COLUMNNAME_C_PaymentTerm_Break_ID, C_PaymentTerm_Break_ID);
	}

	@Override
	public int getC_PaymentTerm_Break_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaymentTerm_Break_ID);
	}

	@Override
	public void setDueAmt (final BigDecimal DueAmt)
	{
		set_Value (COLUMNNAME_DueAmt, DueAmt);
	}

	@Override
	public BigDecimal getDueAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DueAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDueDate (final @Nullable java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
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