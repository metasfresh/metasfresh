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

	private static final long serialVersionUID = -2027894248L;

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
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setDueDate (final java.sql.Timestamp DueDate)
	{
		set_Value (COLUMNNAME_DueDate, DueDate);
	}

	@Override
	public java.sql.Timestamp getDueDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DueDate);
	}

	@Override
	public void setPercent (final int Percent)
	{
		set_Value (COLUMNNAME_Percent, Percent);
	}

	@Override
	public int getPercent() 
	{
		return get_ValueAsInt(COLUMNNAME_Percent);
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

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	/** 
	 * Status AD_Reference_ID=541993
	 * Reference name: C_OrderPaySchedule_Status
	 */
	public static final int STATUS_AD_Reference_ID=541993;
	/** Pending_Ref = PR */
	public static final String STATUS_Pending_Ref = "PR";
	/** Awaiting_Pay = WP */
	public static final String STATUS_Awaiting_Pay = "WP";
	/** Paid = P */
	public static final String STATUS_Paid = "P";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}