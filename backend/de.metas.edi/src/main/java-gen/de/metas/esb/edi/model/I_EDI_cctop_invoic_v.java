package de.metas.esb.edi.model;


/** Generated Interface for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_EDI_cctop_invoic_v 
{

    /** TableName=EDI_cctop_invoic_v */
    public static final String Table_Name = "EDI_cctop_invoic_v";

    /** AD_Table_ID=540462 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_BPartner_Location_ID();

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Invoice_ID();

	public org.compiere.model.I_C_Invoice getC_Invoice();

	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice);

    /** Column definition for C_Invoice_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice>(I_EDI_cctop_invoic_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
    /** Column name C_Invoice_ID */
    public static final String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getC_Order_ID();

	public org.compiere.model.I_C_Order getC_Order();

	public void setC_Order(org.compiere.model.I_C_Order C_Order);

    /** Column definition for C_Order_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order>(I_EDI_cctop_invoic_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountryCode (java.lang.String CountryCode);

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryCode();

    /** Column definition for CountryCode */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CountryCode", null);
    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Set ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCountryCode_3digit (java.lang.String CountryCode_3digit);

	/**
	 * Get ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCountryCode_3digit();

    /** Column definition for CountryCode_3digit */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode_3digit = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CountryCode_3digit", null);
    /** Column name CountryCode_3digit */
    public static final String COLUMNNAME_CountryCode_3digit = "CountryCode_3digit";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditMemoReason (java.lang.String CreditMemoReason);

	/**
	 * Get Gutschrift Grund.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditMemoReason();

    /** Column definition for CreditMemoReason */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReason = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CreditMemoReason", null);
    /** Column name CreditMemoReason */
    public static final String COLUMNNAME_CreditMemoReason = "CreditMemoReason";

	/**
	 * Set CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCreditMemoReasonText (java.lang.String CreditMemoReasonText);

	/**
	 * Get CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCreditMemoReasonText();

    /** Column definition for CreditMemoReasonText */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReasonText = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "CreditMemoReasonText", null);
    /** Column name CreditMemoReasonText */
    public static final String COLUMNNAME_CreditMemoReasonText = "CreditMemoReasonText";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateInvoiced (java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateInvoiced();

    /** Column definition for DateInvoiced */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateInvoiced = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "DateInvoiced", null);
    /** Column name DateInvoiced */
    public static final String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDateOrdered (java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getDateOrdered();

    /** Column definition for DateOrdered */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateOrdered = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "DateOrdered", null);
    /** Column name DateOrdered */
    public static final String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void seteancom_doctype (java.lang.String eancom_doctype);

	/**
	 * Get eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String geteancom_doctype();

    /** Column definition for eancom_doctype */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_eancom_doctype = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "eancom_doctype", null);
    /** Column name eancom_doctype */
    public static final String COLUMNNAME_eancom_doctype = "eancom_doctype";

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getEDI_cctop_invoic_v_ID();

    /** Column definition for EDI_cctop_invoic_v_ID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDI_cctop_invoic_v_ID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "EDI_cctop_invoic_v_ID", null);
    /** Column name EDI_cctop_invoic_v_ID */
    public static final String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEDIDesadvDocumentNo (String EDIDesadvDocumentNo);

	/**
	 * Get EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public String getEDIDesadvDocumentNo();

	/** Column definition for EDIDesadvDocumentNo */
	public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDIDesadvDocumentNo = new org.adempiere.model.ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDIDesadvDocumentNo", null);
	/** Column name EDIDesadvDocumentNo */
	public static final String COLUMNNAME_EDIDesadvDocumentNo = "EDIDesadvDocumentNo";

	/**
	 * Set Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setGrandTotal (java.math.BigDecimal GrandTotal);

	/**
	 * Get Summe Gesamt.
	 * Summe über Alles zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getGrandTotal();

    /** Column definition for GrandTotal */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotal = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "GrandTotal", null);
    /** Column name GrandTotal */
    public static final String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setinvoice_documentno (java.lang.String invoice_documentno);

	/**
	 * Get invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getinvoice_documentno();

    /** Column definition for invoice_documentno */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_invoice_documentno = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "invoice_documentno", null);
    /** Column name invoice_documentno */
    public static final String COLUMNNAME_invoice_documentno = "invoice_documentno";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setISO_Code (java.lang.String ISO_Code);

	/**
	 * Get ISO Währungscode.
	 * Dreibuchstabiger ISO 4217 Code für die Währung
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getISO_Code();

    /** Column definition for ISO_Code */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ISO_Code = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "ISO_Code", null);
    /** Column name ISO_Code */
    public static final String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMovementDate (java.sql.Timestamp MovementDate);

	/**
	 * Get Bewegungs-Datum.
	 * Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getMovementDate();

    /** Column definition for MovementDate */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_MovementDate = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "MovementDate", null);
    /** Column name MovementDate */
    public static final String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPOReference (java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPOReference();

    /** Column definition for POReference */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_POReference = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "POReference", null);
    /** Column name POReference */
    public static final String COLUMNNAME_POReference = "POReference";

	/**
	 * Set receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setreceivergln (java.lang.String receivergln);

	/**
	 * Get receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getreceivergln();

    /** Column definition for receivergln */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_receivergln = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "receivergln", null);
    /** Column name receivergln */
    public static final String COLUMNNAME_receivergln = "receivergln";

	/**
	 * Set sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setsendergln (java.lang.String sendergln);

	/**
	 * Get sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getsendergln();

    /** Column definition for sendergln */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_sendergln = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "sendergln", null);
    /** Column name sendergln */
    public static final String COLUMNNAME_sendergln = "sendergln";

	/**
	 * Set shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setshipment_documentno (java.lang.String shipment_documentno);

	/**
	 * Get shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getshipment_documentno();

    /** Column definition for shipment_documentno */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_shipment_documentno = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "shipment_documentno", null);
    /** Column name shipment_documentno */
    public static final String COLUMNNAME_shipment_documentno = "shipment_documentno";

	/**
	 * Set Summe Zeilen.
	 * Summe aller Zeilen zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTotalLines (java.math.BigDecimal TotalLines);

	/**
	 * Get Summe Zeilen.
	 * Summe aller Zeilen zu diesem Beleg
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getTotalLines();

    /** Column definition for TotalLines */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLines = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "TotalLines", null);
    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Set totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void settotaltaxbaseamt (java.math.BigDecimal totaltaxbaseamt);

	/**
	 * Get totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal gettotaltaxbaseamt();

    /** Column definition for totaltaxbaseamt */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_totaltaxbaseamt = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "totaltaxbaseamt", null);
    /** Column name totaltaxbaseamt */
    public static final String COLUMNNAME_totaltaxbaseamt = "totaltaxbaseamt";

	/**
	 * Set totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void settotalvat (java.math.BigDecimal totalvat);

	/**
	 * Get totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal gettotalvat();

    /** Column definition for totalvat */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_totalvat = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "totalvat", null);
    /** Column name totalvat */
    public static final String COLUMNNAME_totalvat = "totalvat";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVATaxID (java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVATaxID();

    /** Column definition for VATaxID */
    public static final org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_VATaxID = new org.adempiere.model.ModelColumn<I_EDI_cctop_invoic_v, Object>(I_EDI_cctop_invoic_v.class, "VATaxID", null);
    /** Column name VATaxID */
    public static final String COLUMNNAME_VATaxID = "VATaxID";
}
