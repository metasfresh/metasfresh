// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InvoicePaySchedule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InvoicePaySchedule extends org.compiere.model.PO implements I_C_InvoicePaySchedule, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -151445274L;

    /** Standard Constructor */
    public X_C_InvoicePaySchedule (final Properties ctx, final int C_InvoicePaySchedule_ID, @Nullable final String trxName)
    {
      super (ctx, C_InvoicePaySchedule_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InvoicePaySchedule (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_C_Invoice getC_Invoice()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (final int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
	}

	@Override
	public void setC_InvoicePaySchedule_ID (final int C_InvoicePaySchedule_ID)
	{
		if (C_InvoicePaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoicePaySchedule_ID, C_InvoicePaySchedule_ID);
	}

	@Override
	public int getC_InvoicePaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoicePaySchedule_ID);
	}

	@Override
	public org.compiere.model.I_C_Order getC_Order()
	{
		return get_ValueAsPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class);
	}

	@Override
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
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
	public org.compiere.model.I_C_OrderPaySchedule getC_OrderPaySchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_OrderPaySchedule_ID, org.compiere.model.I_C_OrderPaySchedule.class);
	}

	@Override
	public void setC_OrderPaySchedule(final org.compiere.model.I_C_OrderPaySchedule C_OrderPaySchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_OrderPaySchedule_ID, org.compiere.model.I_C_OrderPaySchedule.class, C_OrderPaySchedule);
	}

	@Override
	public void setC_OrderPaySchedule_ID (final int C_OrderPaySchedule_ID)
	{
		if (C_OrderPaySchedule_ID < 1) 
			set_Value (COLUMNNAME_C_OrderPaySchedule_ID, null);
		else 
			set_Value (COLUMNNAME_C_OrderPaySchedule_ID, C_OrderPaySchedule_ID);
	}

	@Override
	public int getC_OrderPaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_OrderPaySchedule_ID);
	}

	@Override
	public org.compiere.model.I_C_PaySchedule getC_PaySchedule()
	{
		return get_ValueAsPO(COLUMNNAME_C_PaySchedule_ID, org.compiere.model.I_C_PaySchedule.class);
	}

	@Override
	public void setC_PaySchedule(final org.compiere.model.I_C_PaySchedule C_PaySchedule)
	{
		set_ValueFromPO(COLUMNNAME_C_PaySchedule_ID, org.compiere.model.I_C_PaySchedule.class, C_PaySchedule);
	}

	@Override
	public void setC_PaySchedule_ID (final int C_PaySchedule_ID)
	{
		if (C_PaySchedule_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaySchedule_ID, C_PaySchedule_ID);
	}

	@Override
	public int getC_PaySchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_PaySchedule_ID);
	}

	@Override
	public void setDiscountAmt (final @Nullable BigDecimal DiscountAmt)
	{
		set_Value (COLUMNNAME_DiscountAmt, DiscountAmt);
	}

	@Override
	public BigDecimal getDiscountAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_DiscountAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setDiscountDate (final @Nullable java.sql.Timestamp DiscountDate)
	{
		set_Value (COLUMNNAME_DiscountDate, DiscountDate);
	}

	@Override
	public java.sql.Timestamp getDiscountDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DiscountDate);
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
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
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
}