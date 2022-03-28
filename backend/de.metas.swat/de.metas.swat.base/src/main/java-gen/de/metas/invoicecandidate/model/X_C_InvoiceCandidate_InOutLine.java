// Generated Model - DO NOT CHANGE
package de.metas.invoicecandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for C_InvoiceCandidate_InOutLine
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_InvoiceCandidate_InOutLine extends org.compiere.model.PO implements I_C_InvoiceCandidate_InOutLine, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1646123523L;

    /** Standard Constructor */
    public X_C_InvoiceCandidate_InOutLine (final Properties ctx, final int C_InvoiceCandidate_InOutLine_ID, @Nullable final String trxName)
    {
      super (ctx, C_InvoiceCandidate_InOutLine_ID, trxName);
    }

    /** Load Constructor */
    public X_C_InvoiceCandidate_InOutLine (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_Candidate_ID, C_Invoice_Candidate_ID);
	}

	@Override
	public int getC_Invoice_Candidate_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_Candidate_ID);
	}

	@Override
	public void setC_InvoiceCandidate_InOutLine_ID (final int C_InvoiceCandidate_InOutLine_ID)
	{
		if (C_InvoiceCandidate_InOutLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceCandidate_InOutLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceCandidate_InOutLine_ID, C_InvoiceCandidate_InOutLine_ID);
	}

	@Override
	public int getC_InvoiceCandidate_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_InvoiceCandidate_InOutLine_ID);
	}

	@Override
	public void setC_UOM_ID (final int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_UOM_ID, C_UOM_ID);
	}

	@Override
	public int getC_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_UOM_ID);
	}

	@Override
	public void setIsInOutApprovedForInvoicing (final boolean IsInOutApprovedForInvoicing)
	{
		throw new IllegalArgumentException ("IsInOutApprovedForInvoicing is virtual column");	}

	@Override
	public boolean isInOutApprovedForInvoicing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsInOutApprovedForInvoicing);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(final org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (final int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, M_InOutLine_ID);
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public void setQtyDelivered (final @Nullable BigDecimal QtyDelivered)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDelivered, QtyDelivered);
	}

	@Override
	public BigDecimal getQtyDelivered() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDelivered);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM_Catch (final @Nullable BigDecimal QtyDeliveredInUOM_Catch)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDeliveredInUOM_Catch, QtyDeliveredInUOM_Catch);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM_Catch() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM_Catch);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM_Nominal (final @Nullable BigDecimal QtyDeliveredInUOM_Nominal)
	{
		set_Value (COLUMNNAME_QtyDeliveredInUOM_Nominal, QtyDeliveredInUOM_Nominal);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM_Nominal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM_Nominal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyDeliveredInUOM_Override (final @Nullable BigDecimal QtyDeliveredInUOM_Override)
	{
		set_ValueNoCheck (COLUMNNAME_QtyDeliveredInUOM_Override, QtyDeliveredInUOM_Override);
	}

	@Override
	public BigDecimal getQtyDeliveredInUOM_Override() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredInUOM_Override);
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
}