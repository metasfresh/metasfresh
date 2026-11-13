// Generated Model - DO NOT CHANGE
package de.metas.pos.repository.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_POS_InvoiceCardSettlement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_POS_InvoiceCardSettlement extends org.compiere.model.PO implements I_C_POS_InvoiceCardSettlement, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 298314404L;

    /** Standard Constructor */
    public X_C_POS_InvoiceCardSettlement (final Properties ctx, final int C_POS_InvoiceCardSettlement_ID, @Nullable final String trxName)
    {
      super (ctx, C_POS_InvoiceCardSettlement_ID, trxName);
    }

    /** Load Constructor */
    public X_C_POS_InvoiceCardSettlement (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAmount (final BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	@Override
	public BigDecimal getAmount() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Amount);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setBookingAttempts (final int BookingAttempts)
	{
		set_Value (COLUMNNAME_BookingAttempts, BookingAttempts);
	}

	@Override
	public int getBookingAttempts() 
	{
		return get_ValueAsInt(COLUMNNAME_BookingAttempts);
	}

	@Override
	public void setCashier_ID (final int Cashier_ID)
	{
		if (Cashier_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Cashier_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Cashier_ID, Cashier_ID);
	}

	@Override
	public int getCashier_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Cashier_ID);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Currency_ID (final int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, C_Currency_ID);
	}

	@Override
	public int getC_Currency_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Currency_ID);
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
	public void setC_Payment_ID (final int C_Payment_ID)
	{
		if (C_Payment_ID < 1) 
			set_Value (COLUMNNAME_C_Payment_ID, null);
		else 
			set_Value (COLUMNNAME_C_Payment_ID, C_Payment_ID);
	}

	@Override
	public int getC_Payment_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Payment_ID);
	}

	@Override
	public void setC_POS_ID (final int C_POS_ID)
	{
		if (C_POS_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_ID, C_POS_ID);
	}

	@Override
	public int getC_POS_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_ID);
	}

	@Override
	public void setC_POS_InvoiceCardSettlement_ID (final int C_POS_InvoiceCardSettlement_ID)
	{
		if (C_POS_InvoiceCardSettlement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_POS_InvoiceCardSettlement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_POS_InvoiceCardSettlement_ID, C_POS_InvoiceCardSettlement_ID);
	}

	@Override
	public int getC_POS_InvoiceCardSettlement_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_POS_InvoiceCardSettlement_ID);
	}

	@Override
	public void setErrorMsg (final @Nullable java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setIsAbandoned (final boolean IsAbandoned)
	{
		set_Value (COLUMNNAME_IsAbandoned, IsAbandoned);
	}

	@Override
	public boolean isAbandoned() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAbandoned);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	/** 
	 * Status AD_Reference_ID=542154
	 * Reference name: C_POS_InvoiceCardSettlement_Status
	 */
	public static final int STATUS_AD_Reference_ID=542154;
	/** Pending = PE */
	public static final String STATUS_Pending = "PE";
	/** Unknown = UN */
	public static final String STATUS_Unknown = "UN";
	/** Approved = AP */
	public static final String STATUS_Approved = "AP";
	/** BookingError = BE */
	public static final String STATUS_BookingError = "BE";
	/** Successful = SU */
	public static final String STATUS_Successful = "SU";
	/** CreditRefundPending = CR */
	public static final String STATUS_CreditRefundPending = "CR";
	/** Refunded = RF */
	public static final String STATUS_Refunded = "RF";
	/** Failed = FA */
	public static final String STATUS_Failed = "FA";
	/** Cancelled = CA */
	public static final String STATUS_Cancelled = "CA";
	/** Abandoned = AB */
	public static final String STATUS_Abandoned = "AB";
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

	@Override
	public void setSUMUP_Transaction_ID (final int SUMUP_Transaction_ID)
	{
		if (SUMUP_Transaction_ID < 1) 
			set_Value (COLUMNNAME_SUMUP_Transaction_ID, null);
		else 
			set_Value (COLUMNNAME_SUMUP_Transaction_ID, SUMUP_Transaction_ID);
	}

	@Override
	public int getSUMUP_Transaction_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_SUMUP_Transaction_ID);
	}
}