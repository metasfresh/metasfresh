package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

import java.math.BigDecimal;

/** Generated Interface for C_RemittanceAdvice_Line
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_RemittanceAdvice_Line 
{

	String Table_Name = "C_RemittanceAdvice_Line";

//	/** AD_Table_ID=541574 */
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
	 * Set Additional notes.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditionalNotes (@Nullable java.lang.String AdditionalNotes);

	/**
	 * Get Additional notes.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAdditionalNotes();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_AdditionalNotes = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "AdditionalNotes", null);
	String COLUMNNAME_AdditionalNotes = "AdditionalNotes";

	/**
	 * Set Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Bill Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getBill_BPartner_ID();

	String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Invoice currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Invoice_Currency_ID (int C_Invoice_Currency_ID);

	/**
	 * Get Invoice currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Invoice_Currency_ID();

	String COLUMNNAME_C_Invoice_Currency_ID = "C_Invoice_Currency_ID";

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

	ModelColumn<I_C_RemittanceAdvice_Line, org.compiere.model.I_C_Invoice> COLUMN_C_Invoice_ID = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "C_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_C_Invoice_ID = "C_Invoice_ID";

	/**
	 * Set Remittance Advice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_RemittanceAdvice_ID (int C_RemittanceAdvice_ID);

	/**
	 * Get Remittance Advice.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_RemittanceAdvice_ID();

	org.compiere.model.I_C_RemittanceAdvice getC_RemittanceAdvice();

	void setC_RemittanceAdvice(org.compiere.model.I_C_RemittanceAdvice C_RemittanceAdvice);

	ModelColumn<I_C_RemittanceAdvice_Line, org.compiere.model.I_C_RemittanceAdvice> COLUMN_C_RemittanceAdvice_ID = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "C_RemittanceAdvice_ID", org.compiere.model.I_C_RemittanceAdvice.class);
	String COLUMNNAME_C_RemittanceAdvice_ID = "C_RemittanceAdvice_ID";

	/**
	 * Set Remittance Advice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_RemittanceAdvice_Line_ID (int C_RemittanceAdvice_Line_ID);

	/**
	 * Get Remittance Advice Line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_RemittanceAdvice_Line_ID();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_C_RemittanceAdvice_Line_ID = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "C_RemittanceAdvice_Line_ID", null);
	String COLUMNNAME_C_RemittanceAdvice_Line_ID = "C_RemittanceAdvice_Line_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_Created = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "Created", null);
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
	 * Set External invoice doc base type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalInvoiceDocBaseType (@Nullable java.lang.String ExternalInvoiceDocBaseType);

	/**
	 * Get External invoice doc base type.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalInvoiceDocBaseType();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_ExternalInvoiceDocBaseType = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "ExternalInvoiceDocBaseType", null);
	String COLUMNNAME_ExternalInvoiceDocBaseType = "ExternalInvoiceDocBaseType";

	/**
	 * Set Invoice Amt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceAmt (@Nullable BigDecimal InvoiceAmt);

	/**
	 * Get Invoice Amt.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceAmt();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_InvoiceAmt = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "InvoiceAmt", null);
	String COLUMNNAME_InvoiceAmt = "InvoiceAmt";

	/**
	 * Set Invoice amount (REMADV Currency).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceAmtInREMADVCurrency (@Nullable BigDecimal InvoiceAmtInREMADVCurrency);

	/**
	 * Get Invoice amount (REMADV Currency).
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceAmtInREMADVCurrency();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_InvoiceAmtInREMADVCurrency = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "InvoiceAmtInREMADVCurrency", null);
	String COLUMNNAME_InvoiceAmtInREMADVCurrency = "InvoiceAmtInREMADVCurrency";

	/**
	 * Set Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceDate (@Nullable java.sql.Timestamp InvoiceDate);

	/**
	 * Get Invoice date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getInvoiceDate();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_InvoiceDate = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "InvoiceDate", null);
	String COLUMNNAME_InvoiceDate = "InvoiceDate";

	/**
	 * Set Invoice gross amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceGrossAmount (@Nullable BigDecimal InvoiceGrossAmount);

	/**
	 * Get Invoice gross amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getInvoiceGrossAmount();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_InvoiceGrossAmount = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "InvoiceGrossAmount", null);
	String COLUMNNAME_InvoiceGrossAmount = "InvoiceGrossAmount";

	/**
	 * Set Invoice identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setInvoiceIdentifier (@Nullable java.lang.String InvoiceIdentifier);

	/**
	 * Get Invoice identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getInvoiceIdentifier();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_InvoiceIdentifier = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "InvoiceIdentifier", null);
	String COLUMNNAME_InvoiceIdentifier = "InvoiceIdentifier";

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

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsActive = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is amount valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAmountValid (boolean IsAmountValid);

	/**
	 * Get Is amount valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAmountValid();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsAmountValid = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsAmountValid", null);
	String COLUMNNAME_IsAmountValid = "IsAmountValid";

	/**
	 * Set Is business partner valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBPartnerValid (boolean IsBPartnerValid);

	/**
	 * Get Is business partner valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBPartnerValid();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsBPartnerValid = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsBPartnerValid", null);
	String COLUMNNAME_IsBPartnerValid = "IsBPartnerValid";

	/**
	 * Set Is invoice date valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceDateValid (boolean IsInvoiceDateValid);

	/**
	 * Get Is invoice date valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiceDateValid();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsInvoiceDateValid = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsInvoiceDateValid", null);
	String COLUMNNAME_IsInvoiceDateValid = "IsInvoiceDateValid";

	/**
	 * Set Is invoice doc type valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceDocTypeValid (boolean IsInvoiceDocTypeValid);

	/**
	 * Get Is invoice doc type valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiceDocTypeValid();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsInvoiceDocTypeValid = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsInvoiceDocTypeValid", null);
	String COLUMNNAME_IsInvoiceDocTypeValid = "IsInvoiceDocTypeValid";

	/**
	 * Set Is invoice resolved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvoiceResolved (boolean IsInvoiceResolved);

	/**
	 * Get Is invoice resolved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvoiceResolved();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsInvoiceResolved = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsInvoiceResolved", null);
	String COLUMNNAME_IsInvoiceResolved = "IsInvoiceResolved";

	/**
	 * Set Is line acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsLineAcknowledged (boolean IsLineAcknowledged);

	/**
	 * Get Is line acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isLineAcknowledged();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsLineAcknowledged = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsLineAcknowledged", null);
	String COLUMNNAME_IsLineAcknowledged = "IsLineAcknowledged";

	/**
	 * Set Is service columns resolved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsServiceColumnsResolved (boolean IsServiceColumnsResolved);

	/**
	 * Get Is service columns resolved.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isServiceColumnsResolved();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsServiceColumnsResolved = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsServiceColumnsResolved", null);
	String COLUMNNAME_IsServiceColumnsResolved = "IsServiceColumnsResolved";

	/**
	 * Set Is service fee VAT valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsServiceFeeVatRateValid (boolean IsServiceFeeVatRateValid);

	/**
	 * Get Is service fee VAT valid.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isServiceFeeVatRateValid();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_IsServiceFeeVatRateValid = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "IsServiceFeeVatRateValid", null);
	String COLUMNNAME_IsServiceFeeVatRateValid = "IsServiceFeeVatRateValid";

	/**
	 * Set Over/Under Payment Amount.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setOverUnderAmt (@Nullable BigDecimal OverUnderAmt);

	/**
	 * Get Over/Under Payment Amount.
	 * Over-Payment (unallocated) or Under-Payment (partial payment) Amount
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getOverUnderAmt();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_OverUnderAmt = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "OverUnderAmt", null);
	String COLUMNNAME_OverUnderAmt = "OverUnderAmt";

	/**
	 * Set Payment discount amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDiscountAmt (@Nullable BigDecimal PaymentDiscountAmt);

	/**
	 * Get Payment discount amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPaymentDiscountAmt();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_PaymentDiscountAmt = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "PaymentDiscountAmt", null);
	String COLUMNNAME_PaymentDiscountAmt = "PaymentDiscountAmt";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_Processed = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Remittance amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRemittanceAmt (BigDecimal RemittanceAmt);

	/**
	 * Get Remittance amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getRemittanceAmt();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_RemittanceAmt = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "RemittanceAmt", null);
	String COLUMNNAME_RemittanceAmt = "RemittanceAmt";

	/**
	 * Set Service business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setService_BPartner_ID (int Service_BPartner_ID);

	/**
	 * Get Service business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getService_BPartner_ID();

	String COLUMNNAME_Service_BPartner_ID = "Service_BPartner_ID";

	/**
	 * Set Service fee invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setService_Fee_Invoice_ID (int Service_Fee_Invoice_ID);

	/**
	 * Get Service fee invoice.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getService_Fee_Invoice_ID();

	@Nullable org.compiere.model.I_C_Invoice getService_Fee_Invoice();

	void setService_Fee_Invoice(@Nullable org.compiere.model.I_C_Invoice Service_Fee_Invoice);

	ModelColumn<I_C_RemittanceAdvice_Line, org.compiere.model.I_C_Invoice> COLUMN_Service_Fee_Invoice_ID = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "Service_Fee_Invoice_ID", org.compiere.model.I_C_Invoice.class);
	String COLUMNNAME_Service_Fee_Invoice_ID = "Service_Fee_Invoice_ID";

	/**
	 * Set Service product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setService_Product_ID (int Service_Product_ID);

	/**
	 * Get Service product.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getService_Product_ID();

	String COLUMNNAME_Service_Product_ID = "Service_Product_ID";

	/**
	 * Set Service tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setService_Tax_ID (int Service_Tax_ID);

	/**
	 * Get Service tax.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getService_Tax_ID();

	String COLUMNNAME_Service_Tax_ID = "Service_Tax_ID";

	/**
	 * Set Service fee amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setServiceFeeAmount (@Nullable BigDecimal ServiceFeeAmount);

	/**
	 * Get Service fee amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getServiceFeeAmount();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_ServiceFeeAmount = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "ServiceFeeAmount", null);
	String COLUMNNAME_ServiceFeeAmount = "ServiceFeeAmount";

	/**
	 * Set Service fee vat rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setServiceFeeVatRate (@Nullable BigDecimal ServiceFeeVatRate);

	/**
	 * Get Service fee vat rate.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getServiceFeeVatRate();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_ServiceFeeVatRate = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "ServiceFeeVatRate", null);
	String COLUMNNAME_ServiceFeeVatRate = "ServiceFeeVatRate";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_RemittanceAdvice_Line, Object> COLUMN_Updated = new ModelColumn<>(I_C_RemittanceAdvice_Line.class, "Updated", null);
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
}
