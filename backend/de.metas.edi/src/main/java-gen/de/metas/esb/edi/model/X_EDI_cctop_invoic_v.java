<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

=======
// Generated Model - DO NOT CHANGE
package de.metas.esb.edi.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
<<<<<<< HEAD
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1115794842L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_v (Properties ctx, int EDI_cctop_invoic_v_ID, String trxName)
=======
@SuppressWarnings("unused")
public class X_EDI_cctop_invoic_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 2045527870L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_v (final Properties ctx, final int EDI_cctop_invoic_v_ID, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, EDI_cctop_invoic_v_ID, trxName);
    }

    /** Load Constructor */
<<<<<<< HEAD
    public X_EDI_cctop_invoic_v (Properties ctx, ResultSet rs, String trxName)
=======
    public X_EDI_cctop_invoic_v (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
<<<<<<< HEAD
	protected org.compiere.model.POInfo initPO(Properties ctx)
=======
	protected org.compiere.model.POInfo initPO(final Properties ctx)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
<<<<<<< HEAD
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
=======
	public void setC_BPartner_Location_ID (final int C_BPartner_Location_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
=======
			set_Value (COLUMNNAME_C_BPartner_Location_ID, C_BPartner_Location_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
=======
	public void setC_Invoice(final org.compiere.model.I_C_Invoice C_Invoice)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
<<<<<<< HEAD
	public void setC_Invoice_ID (int C_Invoice_ID)
=======
	public void setC_Invoice_ID (final int C_Invoice_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
=======
			set_Value (COLUMNNAME_C_Invoice_ID, C_Invoice_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
=======
	public void setC_Order(final org.compiere.model.I_C_Order C_Order)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
<<<<<<< HEAD
	public void setC_Order_ID (int C_Order_ID)
=======
	public void setC_Order_ID (final int C_Order_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
<<<<<<< HEAD
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
=======
			set_Value (COLUMNNAME_C_Order_ID, C_Order_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
<<<<<<< HEAD
	public void setCountryCode (java.lang.String CountryCode)
=======
	public void setCountryCode (final @Nullable java.lang.String CountryCode)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	@Override
	public void setCountryCode_3digit (java.lang.String CountryCode_3digit)
=======
		return get_ValueAsString(COLUMNNAME_CountryCode);
	}

	@Override
	public void setCountryCode_3digit (final @Nullable java.lang.String CountryCode_3digit)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_CountryCode_3digit, CountryCode_3digit);
	}

	@Override
	public java.lang.String getCountryCode_3digit() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode_3digit);
=======
		return get_ValueAsString(COLUMNNAME_CountryCode_3digit);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
	public void setCreditMemoReason (java.lang.String CreditMemoReason)
	{

=======
	public void setCreditMemoReason (final @Nullable java.lang.String CreditMemoReason)
	{
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	@Override
	public java.lang.String getCreditMemoReason() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReason);
	}

	@Override
	public void setCreditMemoReasonText (java.lang.String CreditMemoReasonText)
=======
		return get_ValueAsString(COLUMNNAME_CreditMemoReason);
	}

	@Override
	public void setCreditMemoReasonText (final @Nullable java.lang.String CreditMemoReasonText)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_CreditMemoReasonText, CreditMemoReasonText);
	}

	@Override
	public java.lang.String getCreditMemoReasonText() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReasonText);
	}

	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
<<<<<<< HEAD
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
=======
	public void setDateOrdered (final @Nullable java.sql.Timestamp DateOrdered)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
<<<<<<< HEAD
	public void seteancom_doctype (java.lang.String eancom_doctype)
=======
	public void seteancom_doctype (final @Nullable java.lang.String eancom_doctype)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_eancom_doctype, eancom_doctype);
	}

	@Override
	public java.lang.String geteancom_doctype() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_eancom_doctype);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
=======
		return get_ValueAsString(COLUMNNAME_eancom_doctype);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (final int EDI_cctop_invoic_v_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
=======
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, EDI_cctop_invoic_v_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
<<<<<<< HEAD
	public void setEDIDesadvDocumentNo (String EDIDesadvDocumentNo)
=======
	public void setEDIDesadvDocumentNo (final @Nullable java.lang.String EDIDesadvDocumentNo)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueNoCheck (COLUMNNAME_EDIDesadvDocumentNo, EDIDesadvDocumentNo);
	}

	@Override
<<<<<<< HEAD
	public String getEDIDesadvDocumentNo()
=======
	public java.lang.String getEDIDesadvDocumentNo() 
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_EDIDesadvDocumentNo);
	}

	@Override
<<<<<<< HEAD
	public void setGrandTotal (BigDecimal GrandTotal)
=======
	public void setGrandTotal (final @Nullable BigDecimal GrandTotal)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getGrandTotal() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
=======
	public BigDecimal getGrandTotal() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setinvoice_documentno (java.lang.String invoice_documentno)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_invoice_documentno, invoice_documentno);
	}

	@Override
	public java.lang.String getinvoice_documentno() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_invoice_documentno);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
=======
		return get_ValueAsString(COLUMNNAME_invoice_documentno);
	}

	@Override
	public void setISO_Code (final @Nullable java.lang.String ISO_Code)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
=======
		return get_ValueAsString(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setMovementDate (final @Nullable java.sql.Timestamp MovementDate)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
<<<<<<< HEAD
	public void setPOReference (java.lang.String POReference)
=======
	public void setPOReference (final @Nullable java.lang.String POReference)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	@Override
	public void setreceivergln (java.lang.String receivergln)
	{
		set_Value (COLUMNNAME_receivergln, receivergln);
	}

	@Override
	public java.lang.String getreceivergln() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_receivergln);
	}

	@Override
	public void setsendergln (java.lang.String sendergln)
	{
		set_Value (COLUMNNAME_sendergln, sendergln);
	}

	@Override
	public java.lang.String getsendergln() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_sendergln);
	}

	@Override
	public void setshipment_documentno (java.lang.String shipment_documentno)
	{
		set_Value (COLUMNNAME_shipment_documentno, shipment_documentno);
	}

	@Override
	public java.lang.String getshipment_documentno() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_shipment_documentno);
	}

	@Override
	public void setTotalLines (java.math.BigDecimal TotalLines)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	@Override
<<<<<<< HEAD
	public java.math.BigDecimal getTotalLines() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
=======
	public BigDecimal getTotalLines() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void settotaltaxbaseamt (java.math.BigDecimal totaltaxbaseamt)
	{
		set_Value (COLUMNNAME_totaltaxbaseamt, totaltaxbaseamt);
	}

	@Override
	public java.math.BigDecimal gettotaltaxbaseamt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_totaltaxbaseamt);
=======
	public void setTotalLinesWithSurchargeAmt (final @Nullable BigDecimal TotalLinesWithSurchargeAmt)
	{
		set_Value (COLUMNNAME_TotalLinesWithSurchargeAmt, TotalLinesWithSurchargeAmt);
	}

	@Override
	public BigDecimal getTotalLinesWithSurchargeAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLinesWithSurchargeAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void settotalvat (java.math.BigDecimal totalvat)
	{
		set_Value (COLUMNNAME_totalvat, totalvat);
	}

	@Override
	public java.math.BigDecimal gettotalvat() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_totalvat);
=======
	public void setTotalTaxBaseAmt (final @Nullable BigDecimal TotalTaxBaseAmt)
	{
		set_Value (COLUMNNAME_TotalTaxBaseAmt, TotalTaxBaseAmt);
	}

	@Override
	public BigDecimal getTotalTaxBaseAmt() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalTaxBaseAmt);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
<<<<<<< HEAD
	public void setVATaxID (java.lang.String VATaxID)
=======
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
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
<<<<<<< HEAD
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
=======
		return get_ValueAsString(COLUMNNAME_VATaxID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}