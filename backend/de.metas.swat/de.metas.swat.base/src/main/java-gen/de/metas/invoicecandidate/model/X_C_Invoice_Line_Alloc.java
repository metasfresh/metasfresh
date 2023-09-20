// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Invoice_Line_Alloc
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_Invoice_Line_Alloc extends org.compiere.model.PO implements I_C_Invoice_Line_Alloc, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1468716236L;

    /** Standard Constructor */
    public X_C_Invoice_Line_Alloc (final Properties ctx, final int C_Invoice_Line_Alloc_ID, @Nullable final String trxName)
    {
      super (ctx, C_Invoice_Line_Alloc_ID, trxName);
    }

    /** Load Constructor */
    public X_C_Invoice_Line_Alloc (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg getC_Invoice_Candidate_Agg()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class);
	}

	@Override
	public void setC_Invoice_Candidate_Agg(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg C_Invoice_Candidate_Agg)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_Agg_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Agg.class, C_Invoice_Candidate_Agg);
	}

	@Override
	public void setC_Invoice_Candidate_Agg_ID (final int C_Invoice_Candidate_Agg_ID)
	{
		if (C_Invoice_Candidate_Agg_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_Agg_ID, C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public int getC_Invoice_Candidate_Agg_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_Agg_ID);
	}

	@Override
	public de.metas.invoicecandidate.model.I_C_Invoice_Candidate getC_Invoice_Candidate()
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class);
	}

	@Override
	public void setC_Invoice_Candidate(final de.metas.invoicecandidate.model.I_C_Invoice_Candidate C_Invoice_Candidate)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_Candidate_ID, de.metas.invoicecandidate.model.I_C_Invoice_Candidate.class, C_Invoice_Candidate);
	}

	@Override
	public void setC_Invoice_Candidate_ID (final int C_Invoice_Candidate_ID)
	{
		if (C_Invoice_Candidate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public void setC_Invoice_Line_Alloc_ID (final int C_Invoice_Line_Alloc_ID)
	{
		if (C_Invoice_Line_Alloc_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Line_Alloc_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Invoice_Line_Alloc_ID, C_Invoice_Line_Alloc_ID);
	}

	@Override
	public int getC_Invoice_Line_Alloc_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Line_Alloc_ID);
	}

	/** 
	 * C_Invoice_Line_Alloc_Type AD_Reference_ID=541501
	 * Reference name: C_Invoice_Line_Alloc_Type
	 */
	public static final int C_INVOICE_LINE_ALLOC_TYPE_AD_Reference_ID=541501;
	/** CreatedFromIC = CreatedFromIC */
	public static final String C_INVOICE_LINE_ALLOC_TYPE_CreatedFromIC = "CreatedFromIC";
	/** CreditMemoReinvoicable = CreditMemoReinvoiceable */
	public static final String C_INVOICE_LINE_ALLOC_TYPE_CreditMemoReinvoiceable = "CreditMemoReinvoiceable";
	/** CreditMemoNotReinvoicable = CreditMemoNotReinvoiceable */
	public static final String C_INVOICE_LINE_ALLOC_TYPE_CreditMemoNotReinvoiceable = "CreditMemoNotReinvoiceable";
	@Override
	public void setC_Invoice_Line_Alloc_Type (final @Nullable java.lang.String C_Invoice_Line_Alloc_Type)
	{
		set_Value (COLUMNNAME_C_Invoice_Line_Alloc_Type, C_Invoice_Line_Alloc_Type);
	}

	@Override
	public java.lang.String getC_Invoice_Line_Alloc_Type() 
	{
		return get_ValueAsString(COLUMNNAME_C_Invoice_Line_Alloc_Type);
	}

	@Override
	public org.compiere.model.I_C_InvoiceLine getC_InvoiceLine()
	{
		return get_ValueAsPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class);
	}

	@Override
	public void setC_InvoiceLine(final org.compiere.model.I_C_InvoiceLine C_InvoiceLine)
	{
		set_ValueFromPO(COLUMNNAME_C_InvoiceLine_ID, org.compiere.model.I_C_InvoiceLine.class, C_InvoiceLine);
	}

	@Override
	public void setC_InvoiceLine_ID (final int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, C_InvoiceLine_ID);
	}

	@Override
	public int getC_InvoiceLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceLine_ID);
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

	/** 
	 * DocStatus AD_Reference_ID=131
	 * Reference name: _Document Status
	 */
	public static final int DOCSTATUS_AD_Reference_ID=131;
	/** Drafted = DR */
	public static final String DOCSTATUS_Drafted = "DR";
	/** Completed = CO */
	public static final String DOCSTATUS_Completed = "CO";
	/** Approved = AP */
	public static final String DOCSTATUS_Approved = "AP";
	/** NotApproved = NA */
	public static final String DOCSTATUS_NotApproved = "NA";
	/** Voided = VO */
	public static final String DOCSTATUS_Voided = "VO";
	/** Invalid = IN */
	public static final String DOCSTATUS_Invalid = "IN";
	/** Reversed = RE */
	public static final String DOCSTATUS_Reversed = "RE";
	/** Closed = CL */
	public static final String DOCSTATUS_Closed = "CL";
	/** Unknown = ?? */
	public static final String DOCSTATUS_Unknown = "??";
	/** InProgress = IP */
	public static final String DOCSTATUS_InProgress = "IP";
	/** WaitingPayment = WP */
	public static final String DOCSTATUS_WaitingPayment = "WP";
	/** WaitingConfirmation = WC */
	public static final String DOCSTATUS_WaitingConfirmation = "WC";
	@Override
	public void setDocStatus (final @Nullable java.lang.String DocStatus)
	{
		throw new IllegalArgumentException ("DocStatus is virtual column");	}

	@Override
	public java.lang.String getDocStatus() 
	{
		return get_ValueAsString(COLUMNNAME_DocStatus);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_ValueNoCheck (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
	}

	@Override
	public void setPriceEntered_Override (final @Nullable BigDecimal PriceEntered_Override)
	{
		set_Value (COLUMNNAME_PriceEntered_Override, PriceEntered_Override);
	}

	@Override
	public BigDecimal getPriceEntered_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_PriceEntered_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoiced (final BigDecimal QtyInvoiced)
	{
		set_Value (COLUMNNAME_QtyInvoiced, QtyInvoiced);
	}

	@Override
	public BigDecimal getQtyInvoiced() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoiced);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyInvoicedInUOM (final @Nullable BigDecimal QtyInvoicedInUOM)
	{
		set_Value (COLUMNNAME_QtyInvoicedInUOM, QtyInvoicedInUOM);
	}

	@Override
	public BigDecimal getQtyInvoicedInUOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyInvoicedInUOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyToInvoice_Override (final @Nullable BigDecimal QtyToInvoice_Override)
	{
		set_Value (COLUMNNAME_QtyToInvoice_Override, QtyToInvoice_Override);
	}

	@Override
	public BigDecimal getQtyToInvoice_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyToInvoice_Override);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}