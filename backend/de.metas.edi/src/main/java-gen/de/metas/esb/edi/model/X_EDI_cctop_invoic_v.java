/** Generated Model - DO NOT CHANGE */
package de.metas.esb.edi.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_cctop_invoic_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_cctop_invoic_v extends org.compiere.model.PO implements I_EDI_cctop_invoic_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 247505135L;

    /** Standard Constructor */
    public X_EDI_cctop_invoic_v (Properties ctx, int EDI_cctop_invoic_v_ID, String trxName)
    {
      super (ctx, EDI_cctop_invoic_v_ID, trxName);
      /** if (EDI_cctop_invoic_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_cctop_invoic_v (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Standort.
		@param C_BPartner_Location_ID 
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Auftrag.
		@param C_Order_ID 
		Order
	  */
	@Override
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Order
	  */
	@Override
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ISO Ländercode.
		@param CountryCode 
		Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public void setCountryCode (java.lang.String CountryCode)
	{
		set_Value (COLUMNNAME_CountryCode, CountryCode);
	}

	/** Get ISO Ländercode.
		@return Zweibuchstabiger ISO Ländercode gemäß ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	@Override
	public java.lang.String getCountryCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CountryCode);
	}

	/** Set ISO Ländercode 3 Stelliger.
		@param CountryCode_3digit ISO Ländercode 3 Stelliger	  */
	@Override
	public void setCountryCode_3digit (java.lang.String CountryCode_3digit)
	{
		set_Value (COLUMNNAME_CountryCode_3digit, CountryCode_3digit);
	}

	/** Get ISO Ländercode 3 Stelliger.
		@return ISO Ländercode 3 Stelliger	  */
	@Override
	public java.lang.String getCountryCode_3digit () 
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
	/** Set Gutschrift Grund.
		@param CreditMemoReason Gutschrift Grund	  */
	@Override
	public void setCreditMemoReason (java.lang.String CreditMemoReason)
	{

		set_Value (COLUMNNAME_CreditMemoReason, CreditMemoReason);
	}

	/** Get Gutschrift Grund.
		@return Gutschrift Grund	  */
	@Override
	public java.lang.String getCreditMemoReason () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReason);
	}

	/** Set CreditMemoReasonText.
		@param CreditMemoReasonText CreditMemoReasonText	  */
	@Override
	public void setCreditMemoReasonText (java.lang.String CreditMemoReasonText)
	{
		set_Value (COLUMNNAME_CreditMemoReasonText, CreditMemoReasonText);
	}

	/** Get CreditMemoReasonText.
		@return CreditMemoReasonText	  */
	@Override
	public java.lang.String getCreditMemoReasonText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_CreditMemoReasonText);
	}

	/** Set Rechnungsdatum.
		@param DateInvoiced 
		Datum auf der Rechnung
	  */
	@Override
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced)
	{
		set_Value (COLUMNNAME_DateInvoiced, DateInvoiced);
	}

	/** Get Rechnungsdatum.
		@return Datum auf der Rechnung
	  */
	@Override
	public java.sql.Timestamp getDateInvoiced () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateInvoiced);
	}

	/** Set Auftragsdatum.
		@param DateOrdered 
		Datum des Auftrags
	  */
	@Override
	public void setDateOrdered (java.sql.Timestamp DateOrdered)
	{
		set_Value (COLUMNNAME_DateOrdered, DateOrdered);
	}

	/** Get Auftragsdatum.
		@return Datum des Auftrags
	  */
	@Override
	public java.sql.Timestamp getDateOrdered () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateOrdered);
	}

	/** Set eancom_doctype.
		@param eancom_doctype eancom_doctype	  */
	@Override
	public void seteancom_doctype (java.lang.String eancom_doctype)
	{
		set_Value (COLUMNNAME_eancom_doctype, eancom_doctype);
	}

	/** Get eancom_doctype.
		@return eancom_doctype	  */
	@Override
	public java.lang.String geteancom_doctype () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_eancom_doctype);
	}

	/** Set EDI_cctop_invoic_v.
		@param EDI_cctop_invoic_v_ID EDI_cctop_invoic_v	  */
	@Override
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID)
	{
		if (EDI_cctop_invoic_v_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_EDI_cctop_invoic_v_ID, Integer.valueOf(EDI_cctop_invoic_v_ID));
	}

	/** Get EDI_cctop_invoic_v.
		@return EDI_cctop_invoic_v	  */
	@Override
	public int getEDI_cctop_invoic_v_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EDI_cctop_invoic_v_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summe Gesamt.
		@param GrandTotal 
		Summe über Alles zu diesem Beleg
	  */
	@Override
	public void setGrandTotal (java.math.BigDecimal GrandTotal)
	{
		set_Value (COLUMNNAME_GrandTotal, GrandTotal);
	}

	/** Get Summe Gesamt.
		@return Summe über Alles zu diesem Beleg
	  */
	@Override
	public java.math.BigDecimal getGrandTotal () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_GrandTotal);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set invoice_documentno.
		@param invoice_documentno invoice_documentno	  */
	@Override
	public void setinvoice_documentno (java.lang.String invoice_documentno)
	{
		set_Value (COLUMNNAME_invoice_documentno, invoice_documentno);
	}

	/** Get invoice_documentno.
		@return invoice_documentno	  */
	@Override
	public java.lang.String getinvoice_documentno () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_invoice_documentno);
	}

	/** Set ISO Währungscode.
		@param ISO_Code 
		Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public void setISO_Code (java.lang.String ISO_Code)
	{
		set_Value (COLUMNNAME_ISO_Code, ISO_Code);
	}

	/** Get ISO Währungscode.
		@return Dreibuchstabiger ISO 4217 Code für die Währung
	  */
	@Override
	public java.lang.String getISO_Code () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ISO_Code);
	}

	/** Set Bewegungsdatum.
		@param MovementDate 
		Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public void setMovementDate (java.sql.Timestamp MovementDate)
	{
		set_Value (COLUMNNAME_MovementDate, MovementDate);
	}

	/** Get Bewegungsdatum.
		@return Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	  */
	@Override
	public java.sql.Timestamp getMovementDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_MovementDate);
	}

	/** Set Referenz.
		@param POReference 
		Referenz-Nummer des Kunden
	  */
	@Override
	public void setPOReference (java.lang.String POReference)
	{
		set_Value (COLUMNNAME_POReference, POReference);
	}

	/** Get Referenz.
		@return Referenz-Nummer des Kunden
	  */
	@Override
	public java.lang.String getPOReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_POReference);
	}

	/** Set receivergln.
		@param receivergln receivergln	  */
	@Override
	public void setreceivergln (java.lang.String receivergln)
	{
		set_Value (COLUMNNAME_receivergln, receivergln);
	}

	/** Get receivergln.
		@return receivergln	  */
	@Override
	public java.lang.String getreceivergln () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_receivergln);
	}

	/** Set sendergln.
		@param sendergln sendergln	  */
	@Override
	public void setsendergln (java.lang.String sendergln)
	{
		set_Value (COLUMNNAME_sendergln, sendergln);
	}

	/** Get sendergln.
		@return sendergln	  */
	@Override
	public java.lang.String getsendergln () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_sendergln);
	}

	/** Set shipment_documentno.
		@param shipment_documentno shipment_documentno	  */
	@Override
	public void setshipment_documentno (java.lang.String shipment_documentno)
	{
		set_Value (COLUMNNAME_shipment_documentno, shipment_documentno);
	}

	/** Get shipment_documentno.
		@return shipment_documentno	  */
	@Override
	public java.lang.String getshipment_documentno () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_shipment_documentno);
	}

	/** Set Summe Zeilen.
		@param TotalLines 
		Summe aller Zeilen zu diesem Beleg
	  */
	@Override
	public void setTotalLines (java.math.BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Summe Zeilen.
		@return Summe aller Zeilen zu diesem Beleg
	  */
	@Override
	public java.math.BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set totaltaxbaseamt.
		@param totaltaxbaseamt totaltaxbaseamt	  */
	@Override
	public void settotaltaxbaseamt (java.math.BigDecimal totaltaxbaseamt)
	{
		set_Value (COLUMNNAME_totaltaxbaseamt, totaltaxbaseamt);
	}

	/** Get totaltaxbaseamt.
		@return totaltaxbaseamt	  */
	@Override
	public java.math.BigDecimal gettotaltaxbaseamt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totaltaxbaseamt);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set totalvat.
		@param totalvat totalvat	  */
	@Override
	public void settotalvat (java.math.BigDecimal totalvat)
	{
		set_Value (COLUMNNAME_totalvat, totalvat);
	}

	/** Get totalvat.
		@return totalvat	  */
	@Override
	public java.math.BigDecimal gettotalvat () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_totalvat);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Umsatzsteuer ID.
		@param VATaxID Umsatzsteuer ID	  */
	@Override
	public void setVATaxID (java.lang.String VATaxID)
	{
		set_Value (COLUMNNAME_VATaxID, VATaxID);
	}

	/** Get Umsatzsteuer ID.
		@return Umsatzsteuer ID	  */
	@Override
	public java.lang.String getVATaxID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VATaxID);
	}
}