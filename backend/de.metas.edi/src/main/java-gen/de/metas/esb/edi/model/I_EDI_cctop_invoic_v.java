package de.metas.esb.edi.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for EDI_cctop_invoic_v
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_EDI_cctop_invoic_v 
{

	String Table_Name = "EDI_cctop_invoic_v";

//	/** AD_Table_ID=540462 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/**
	 * Get Location.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_Location_ID();

	String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/**
	 * Set Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_ID (int C_Invoice_ID);

	/**
	 * Get Invoice.
	 * Invoice Identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getC_Invoice();

	void setC_Invoice(@Nullable org.compiere.model.I_C_Invoice C_Invoice);

	ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Order_ID (int C_Order_ID);

	/**
	 * Get Sales order.
	 * Order
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Order_ID();

	@Nullable org.compiere.model.I_C_Order getC_Order();

	void setC_Order(@Nullable org.compiere.model.I_C_Order C_Order);

	ModelColumn<I_EDI_cctop_invoic_v, org.compiere.model.I_C_Order> COLUMN_C_Order_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "C_Order_ID", org.compiere.model.I_C_Order.class);
	String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/**
	 * Set ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountryCode (@Nullable java.lang.String CountryCode);

	/**
	 * Get ISO Country Code.
	 * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCountryCode();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CountryCode", null);
	String COLUMNNAME_CountryCode = "CountryCode";

	/**
	 * Set ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCountryCode_3digit (@Nullable java.lang.String CountryCode_3digit);

	/**
	 * Get ISO Ländercode 3 Stelliger.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCountryCode_3digit();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CountryCode_3digit = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CountryCode_3digit", null);
	String COLUMNNAME_CountryCode_3digit = "CountryCode_3digit";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Created = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Credit Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditMemoReason (@Nullable java.lang.String CreditMemoReason);

	/**
	 * Get Credit Reason.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditMemoReason();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReason = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CreditMemoReason", null);
	String COLUMNNAME_CreditMemoReason = "CreditMemoReason";

	/**
	 * Set CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCreditMemoReasonText (@Nullable java.lang.String CreditMemoReasonText);

	/**
	 * Get CreditMemoReasonText.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCreditMemoReasonText();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_CreditMemoReasonText = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "CreditMemoReasonText", null);
	String COLUMNNAME_CreditMemoReasonText = "CreditMemoReasonText";

	/**
	 * Set Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateAcct (@Nullable java.sql.Timestamp DateAcct);

	/**
	 * Get Accounting Date.
	 * Accounting Date
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateAcct();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateAcct = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateAcct", null);
	String COLUMNNAME_DateAcct = "DateAcct";

	/**
	 * Set Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateInvoiced (@Nullable java.sql.Timestamp DateInvoiced);

	/**
	 * Get Date.
	 * Date printed on Invoice
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateInvoiced();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateInvoiced = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateInvoiced", null);
	String COLUMNNAME_DateInvoiced = "DateInvoiced";

	/**
	 * Set Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDateOrdered (@Nullable java.sql.Timestamp DateOrdered);

	/**
	 * Get Date.
	 * Date of Order
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getDateOrdered();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_DateOrdered = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "DateOrdered", null);
	String COLUMNNAME_DateOrdered = "DateOrdered";

	/**
	 * Set eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void seteancom_doctype (@Nullable java.lang.String eancom_doctype);

	/**
	 * Get eancom_doctype.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String geteancom_doctype();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_eancom_doctype = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "eancom_doctype", null);
	String COLUMNNAME_eancom_doctype = "eancom_doctype";

	/**
	 * Set EDI_cctop_invoic_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDI_cctop_invoic_v_ID (int EDI_cctop_invoic_v_ID);

	/**
	 * Get EDI_cctop_invoic_v.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getEDI_cctop_invoic_v_ID();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDI_cctop_invoic_v_ID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDI_cctop_invoic_v_ID", null);
	String COLUMNNAME_EDI_cctop_invoic_v_ID = "EDI_cctop_invoic_v_ID";

	/**
	 * Set EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEDIDesadvDocumentNo (@Nullable java.lang.String EDIDesadvDocumentNo);

	/**
	 * Get EDI Desadv-Nr..
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEDIDesadvDocumentNo();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_EDIDesadvDocumentNo = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "EDIDesadvDocumentNo", null);
	String COLUMNNAME_EDIDesadvDocumentNo = "EDIDesadvDocumentNo";

	/**
	 * Set Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setGrandTotal (@Nullable BigDecimal GrandTotal);

	/**
	 * Get Grand Total.
	 * Total amount of document
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotal();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotal = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "GrandTotal", null);
	String COLUMNNAME_GrandTotal = "GrandTotal";

	/**
	 * Set GrandTotalWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setGrandTotalWithSurchargeAmt (BigDecimal GrandTotalWithSurchargeAmt);

	/**
	 * Get GrandTotalWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getGrandTotalWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_GrandTotalWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "GrandTotalWithSurchargeAmt", null);
	String COLUMNNAME_GrandTotalWithSurchargeAmt = "GrandTotalWithSurchargeAmt";

	/**
	 * Set Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoicableQtyBasedOn (@Nullable java.lang.String InvoicableQtyBasedOn);

	/**
	 * Get Invoicable Quantity per.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoicableQtyBasedOn();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_InvoicableQtyBasedOn = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "InvoicableQtyBasedOn", null);
	String COLUMNNAME_InvoicableQtyBasedOn = "InvoicableQtyBasedOn";

	/**
	 * Set invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setinvoice_documentno (@Nullable java.lang.String invoice_documentno);

	/**
	 * Get invoice_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getinvoice_documentno();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_invoice_documentno = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "invoice_documentno", null);
	String COLUMNNAME_invoice_documentno = "invoice_documentno";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_IsActive = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setISO_Code (@Nullable java.lang.String ISO_Code);

	/**
	 * Get ISO Currency Code.
	 * Three letter ISO 4217 Code of the Currency
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getISO_Code();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ISO_Code = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "ISO_Code", null);
	String COLUMNNAME_ISO_Code = "ISO_Code";

	/**
	 * Set Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMovementDate (@Nullable java.sql.Timestamp MovementDate);

	/**
	 * Get Date.
	 * Date a product was moved in or out of inventory
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getMovementDate();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_MovementDate = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "MovementDate", null);
	String COLUMNNAME_MovementDate = "MovementDate";

	/**
	 * Set Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPOReference (@Nullable java.lang.String POReference);

	/**
	 * Get Order Reference.
	 * Transaction Reference Number (Sales Order, Purchase Order) of your Business Partner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPOReference();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_POReference = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "POReference", null);
	String COLUMNNAME_POReference = "POReference";

	/**
	 * Set receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReceiverGLN (@Nullable java.lang.String ReceiverGLN);

	/**
	 * Get receivergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReceiverGLN();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_ReceiverGLN = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "ReceiverGLN", null);
	String COLUMNNAME_ReceiverGLN = "ReceiverGLN";

	/**
	 * Set sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSenderGLN (@Nullable java.lang.String SenderGLN);

	/**
	 * Get sendergln.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSenderGLN();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_SenderGLN = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "SenderGLN", null);
	String COLUMNNAME_SenderGLN = "SenderGLN";

	/**
	 * Set shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setShipment_DocumentNo (@Nullable java.lang.String Shipment_DocumentNo);

	/**
	 * Get shipment_documentno.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getShipment_DocumentNo();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Shipment_DocumentNo = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Shipment_DocumentNo", null);
	String COLUMNNAME_Shipment_DocumentNo = "Shipment_DocumentNo";

	/**
	 * Set SurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSurchargeAmt (@Nullable BigDecimal SurchargeAmt);

	/**
	 * Get SurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_SurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "SurchargeAmt", null);
	String COLUMNNAME_SurchargeAmt = "SurchargeAmt";

	/**
	 * Set Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalLines (@Nullable BigDecimal TotalLines);

	/**
	 * Get Total Lines.
	 * Total of all document lines
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLines();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLines = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalLines", null);
	String COLUMNNAME_TotalLines = "TotalLines";

	/**
	 * Set TotalLinesWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalLinesWithSurchargeAmt (@Nullable BigDecimal TotalLinesWithSurchargeAmt);

	/**
	 * Get TotalLinesWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalLinesWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalLinesWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalLinesWithSurchargeAmt", null);
	String COLUMNNAME_TotalLinesWithSurchargeAmt = "TotalLinesWithSurchargeAmt";

	/**
	 * Set totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalTaxBaseAmt (@Nullable BigDecimal TotalTaxBaseAmt);

	/**
	 * Get totaltaxbaseamt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalTaxBaseAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalTaxBaseAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalTaxBaseAmt", null);
	String COLUMNNAME_TotalTaxBaseAmt = "TotalTaxBaseAmt";

	/**
	 * Set totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTotalVat (@Nullable BigDecimal TotalVat);

	/**
	 * Get totalvat.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalVat();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalVat = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalVat", null);
	String COLUMNNAME_TotalVat = "TotalVat";

	/**
	 * Set TotalVatWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTotalVatWithSurchargeAmt (BigDecimal TotalVatWithSurchargeAmt);

	/**
	 * Get TotalVatWithSurchargeAmt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getTotalVatWithSurchargeAmt();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_TotalVatWithSurchargeAmt = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "TotalVatWithSurchargeAmt", null);
	String COLUMNNAME_TotalVatWithSurchargeAmt = "TotalVatWithSurchargeAmt";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_Updated = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setVATaxID (@Nullable java.lang.String VATaxID);

	/**
	 * Get VAT ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getVATaxID();

	ModelColumn<I_EDI_cctop_invoic_v, Object> COLUMN_VATaxID = new ModelColumn<>(I_EDI_cctop_invoic_v.class, "VATaxID", null);
	String COLUMNNAME_VATaxID = "VATaxID";
}
