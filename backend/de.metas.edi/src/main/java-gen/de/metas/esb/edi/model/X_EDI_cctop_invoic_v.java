// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EDI_cctop_invoic_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2045527870L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_v (final Properties ctx, final int EDI_cctop_invoic_v_ID, @Nullable final String trxName)
    {
      super (ctx, EDI_cctop_invoic_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
	}

	@Override
	public int getC_BPartner_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_Location_ID);
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
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
	}

	@Override
	public int getC_Invoice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Invoice_ID);
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
	public void setCountryCode (final @Nullable java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
		return get_ValueAsString(COLUMNNAME_CountryCode);
	}

	@Override
	public void setCountryCode_3digit (final @Nullable java.lang.String CountryCode_3digit)
	{
		set_Value (COLUMNNAME_CountryCode_3digit, CountryCode_3digit);
	}

	@Override
	public java.lang.String getCountryCode_3digit() 
	{
		return get_ValueAsString(COLUMNNAME_CountryCode_3digit);
	}

	/** 
	 * CreditMemoReason AD_Reference_ID=540014
	 * Reference name: C_CreditMemo_Reason
	 */
	public static final int CREDITMEMOREASON_AD_Reference_ID=540014;
	/** Falschlieferung = CMF */
	public static final String CREDITMEMOREASON_Falschlieferung = "CMF";
	/** Doppellieferung = CMD */
	public static final String CREDITMEMOREASON_Doppellieferung = "CMD";
	@Override
	public void setCreditMemoReason (final @Nullable java.lang.String CreditMemoReason)
	{
		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	@Override
	public java.lang.String getCreditMemoReason() 
	{
		return get_ValueAsString(COLUMNNAME_CreditMemoReason);
	}

	@Override
	public void setCreditMemoReasonText (final @Nullable java.lang.String CreditMemoReasonText)
	{
		set_Value (COLUMNNAME_CreditMemoReasonText, CreditMemoReasonText);
	}

	@Override
	public java.lang.String getCreditMemoReasonText() 
	{
		return get_ValueAsString(COLUMNNAME_CreditMemoReasonText);
	}

	@Override
	public void setDateAcct (final @Nullable java.sql.Timestamp DateAcct)
	{
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	@Override
	public java.sql.Timestamp getDateAcct() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateAcct);
	}

	@Override
	public void setDateInvoiced (final @Nullable java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void seteancom_doctype (final @Nullable java.lang.String eancom_doctype)
	{
		set_Value (COLUMNNAME_eancom_doctype, eancom_doctype);
	}

	@Override
	public java.lang.String geteancom_doctype() 
	{
		return get_ValueAsString(COLUMNNAME_eancom_doctype);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setEDIDesadvDocumentNo (final @Nullable java.lang.String EDIDesadvDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_EDIDesadvDocumentNo, EDIDesadvDocumentNo);
	}

	@Override
	public java.lang.String getEDIDesadvDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_EDIDesadvDocumentNo);
	}

	@Override
	public void setGrandTotal (final @Nullable BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setGrandTotalWithSurchargeAmt (final BigDecimal GrandTotalWithSurchargeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_GrandTotalWithSurchargeAmt, GrandTotalWithSurchargeAmt);
	}

	@Override
	public BigDecimal getGrandTotalWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotalWithSurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * InvoicableQtyBasedOn AD_Reference_ID=541023
	 * Reference name: InvoicableQtyBasedOn
	 */
	public static final int INVOICABLEQTYBASEDON_AD_Reference_ID=541023;
	/** Nominal = Nominal */
	public static final String INVOICABLEQTYBASEDON_Nominal = "Nominal";
	/** CatchWeight = CatchWeight */
	public static final String INVOICABLEQTYBASEDON_CatchWeight = "CatchWeight";
	@Override
	public void setInvoicableQtyBasedOn (final @Nullable java.lang.String InvoicableQtyBasedOn)
	{
		set_ValueNoCheck (COLUMNNAME_InvoicableQtyBasedOn, InvoicableQtyBasedOn);
	}

	@Override
	public java.lang.String getInvoicableQtyBasedOn() 
	{
		return get_ValueAsString(COLUMNNAME_InvoicableQtyBasedOn);
	}

	@Override
	public void setinvoice_documentno (final @Nullable java.lang.String invoice_documentno)
	{
		set_Value (COLUMNNAME_invoice_documentno, invoice_documentno);
	}

	@Override
	public java.lang.String getinvoice_documentno() 
	{
		return get_ValueAsString(COLUMNNAME_invoice_documentno);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setPOReference (final @Nullable java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
		return get_ValueAsString(COLUMNNAME_POReference);
	}

	@Override
	public void setReceiverGLN (final @Nullable java.lang.String ReceiverGLN)
	{
		set_Value (COLUMNNAME_ReceiverGLN, ReceiverGLN);
	}

	@Override
	public java.lang.String getReceiverGLN() 
	{
		return get_ValueAsString(COLUMNNAME_ReceiverGLN);
	}

	@Override
	public void setSenderGLN (final @Nullable java.lang.String SenderGLN)
	{
		set_Value (COLUMNNAME_SenderGLN, SenderGLN);
	}

	@Override
	public java.lang.String getSenderGLN() 
	{
		return get_ValueAsString(COLUMNNAME_SenderGLN);
	}

	@Override
	public void setShipment_DocumentNo (final @Nullable java.lang.String Shipment_DocumentNo)
	{
		set_Value (COLUMNNAME_Shipment_DocumentNo, Shipment_DocumentNo);
	}

	@Override
	public java.lang.String getShipment_DocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_Shipment_DocumentNo);
	}

	@Override
	public void setSurchargeAmt (final @Nullable BigDecimal SurchargeAmt)
	{
		set_Value (COLUMNNAME_SurchargeAmt, SurchargeAmt);
	}

	@Override
	public BigDecimal getSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_SurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalLines (final @Nullable BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	@Override
	public BigDecimal getTotalLines() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalLinesWithSurchargeAmt (final @Nullable BigDecimal TotalLinesWithSurchargeAmt)
	{
		set_Value (COLUMNNAME_TotalLinesWithSurchargeAmt, TotalLinesWithSurchargeAmt);
	}

	@Override
	public BigDecimal getTotalLinesWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLinesWithSurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalTaxBaseAmt (final @Nullable BigDecimal TotalTaxBaseAmt)
	{
		set_Value (COLUMNNAME_TotalTaxBaseAmt, TotalTaxBaseAmt);
	}

	@Override
	public BigDecimal getTotalTaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalTaxBaseAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalVat (final @Nullable BigDecimal TotalVat)
	{
		set_Value (COLUMNNAME_TotalVat, TotalVat);
	}

	@Override
	public BigDecimal getTotalVat() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalVat);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setTotalVatWithSurchargeAmt (final BigDecimal TotalVatWithSurchargeAmt)
	{
		set_ValueNoCheck (COLUMNNAME_TotalVatWithSurchargeAmt, TotalVatWithSurchargeAmt);
	}

	@Override
	public BigDecimal getTotalVatWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalVatWithSurchargeAmt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setVATaxID (final @Nullable java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return get_ValueAsString(COLUMNNAME_VATaxID);
	}
}