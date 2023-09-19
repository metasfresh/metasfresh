/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_v, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1115794842L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_v (Properties ctx, int EDI_cctop_invoic_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_invoic_v_ID, trxName);
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
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
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
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
	public void setC_Order(org.compiere.model.I_C_Order C_Order)
	{
		set_ValueFromPO(COLUMNNAME_C_Order_ID, org.compiere.model.I_C_Order.class, C_Order);
	}

	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	@Override
	public int getC_Order_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Order_ID);
	}

	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	@Override
	public java.lang.String getCountryCode() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	@Override
	public void setCountryCode_3digit (java.lang.String CountryCode_3digit)
	{
		set_Value (COLUMNNAME_CountryCode_3digit, CountryCode_3digit);
	}

	@Override
	public java.lang.String getCountryCode_3digit() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode_3digit);
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
	public void setCreditMemoReason (java.lang.String CreditMemoReason)
	{

		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	@Override
	public java.lang.String getCreditMemoReason() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReason);
	}

	@Override
	public void setCreditMemoReasonText (java.lang.String CreditMemoReasonText)
	{
		set_Value (COLUMNNAME_CreditMemoReasonText, CreditMemoReasonText);
	}

	@Override
	public java.lang.String getCreditMemoReasonText() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReasonText);
	}

	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	@Override
	public java.sql.Timestamp getDateInvoiced() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateInvoiced);
	}

	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	@Override
	public java.sql.Timestamp getDateOrdered() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_DateOrdered);
	}

	@Override
	public void seteancom_doctype (java.lang.String eancom_doctype)
	{
		set_Value (COLUMNNAME_eancom_doctype, eancom_doctype);
	}

	@Override
	public java.lang.String geteancom_doctype() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_eancom_doctype);
	}

	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	@Override
	public int getEDI_cctop_invoic_v_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EDI_cctop_invoic_v_ID);
	}

	@Override
	public void setEDIDesadvDocumentNo (String EDIDesadvDocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_EDIDesadvDocumentNo, EDIDesadvDocumentNo);
	}

	@Override
	public String getEDIDesadvDocumentNo()
	{
		return get_ValueAsString(COLUMNNAME_EDIDesadvDocumentNo);
	}

	@Override
	public void setGrandTotal (BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	@Override
	public java.math.BigDecimal getGrandTotal() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_GrandTotal);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setinvoice_documentno (java.lang.String invoice_documentno)
	{
		set_Value (COLUMNNAME_invoice_documentno, invoice_documentno);
	}

	@Override
	public java.lang.String getinvoice_documentno() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_invoice_documentno);
	}

	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	@Override
	public java.lang.String getISO_Code() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	@Override
	public java.sql.Timestamp getMovementDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_MovementDate);
	}

	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	@Override
	public java.lang.String getPOReference() 
	{
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
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	@Override
	public java.math.BigDecimal getTotalLines() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_TotalLines);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void settotaltaxbaseamt (java.math.BigDecimal totaltaxbaseamt)
	{
		set_Value (COLUMNNAME_totaltaxbaseamt, totaltaxbaseamt);
	}

	@Override
	public java.math.BigDecimal gettotaltaxbaseamt() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_totaltaxbaseamt);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void settotalvat (java.math.BigDecimal totalvat)
	{
		set_Value (COLUMNNAME_totalvat, totalvat);
	}

	@Override
	public java.math.BigDecimal gettotalvat() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_totalvat);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	@Override
	public java.lang.String getVATaxID() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}