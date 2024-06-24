package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/** Generated Interface for C_RemittanceAdvice
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_RemittanceAdvice 
{

	String Table_Name = "C_RemittanceAdvice";

//	/** AD_Table_ID=541573 */
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

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_AdditionalNotes = new ModelColumn<>(I_C_RemittanceAdvice.class, "AdditionalNotes", null);
	String COLUMNNAME_AdditionalNotes = "AdditionalNotes";

	/**
	 * Set Announced date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAnnouncedDateTrx (@Nullable java.sql.Timestamp AnnouncedDateTrx);

	/**
	 * Get Announced date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getAnnouncedDateTrx();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_AnnouncedDateTrx = new ModelColumn<>(I_C_RemittanceAdvice.class, "AnnouncedDateTrx", null);
	String COLUMNNAME_AnnouncedDateTrx = "AnnouncedDateTrx";

	/**
	 * Set Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_ID (int C_DocType_ID);

	/**
	 * Get Document Type.
	 * Document type or rules
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_ID();

	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 * Set Target payment document type.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Payment_Doctype_Target_ID (int C_Payment_Doctype_Target_ID);

	/**
	 * Get Target payment document type.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Payment_Doctype_Target_ID();

	String COLUMNNAME_C_Payment_Doctype_Target_ID = "C_Payment_Doctype_Target_ID";

	/**
	 * Set Payment.
	 * Payment identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Payment_ID (int C_Payment_ID);

	/**
	 * Get Payment.
	 * Payment identifier
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Payment_ID();

	String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/**
	 * Set Remittance Advice (REMADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_RemittanceAdvice_ID (int C_RemittanceAdvice_ID);

	/**
	 * Get Remittance Advice (REMADV).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_RemittanceAdvice_ID();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_C_RemittanceAdvice_ID = new ModelColumn<>(I_C_RemittanceAdvice.class, "C_RemittanceAdvice_ID", null);
	String COLUMNNAME_C_RemittanceAdvice_ID = "C_RemittanceAdvice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_Created = new ModelColumn<>(I_C_RemittanceAdvice.class, "Created", null);
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
	 * Set CurrencyReadOnlyFlag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCurrenciesReadOnlyFlag (boolean CurrenciesReadOnlyFlag);

	/**
	 * Get CurrencyReadOnlyFlag.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCurrenciesReadOnlyFlag();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_CurrenciesReadOnlyFlag = new ModelColumn<>(I_C_RemittanceAdvice.class, "CurrenciesReadOnlyFlag", null);
	String COLUMNNAME_CurrenciesReadOnlyFlag = "CurrenciesReadOnlyFlag";

	/**
	 * Set Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDateDoc (java.sql.Timestamp DateDoc);

	/**
	 * Get Document Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getDateDoc();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_DateDoc = new ModelColumn<>(I_C_RemittanceAdvice.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

	/**
	 * Set Destination bank account.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDestination_BP_BankAccount_ID (int Destination_BP_BankAccount_ID);

	/**
	 * Get Destination bank account.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDestination_BP_BankAccount_ID();

	String COLUMNNAME_Destination_BP_BankAccount_ID = "Destination_BP_BankAccount_ID";

	/**
	 * Set Destination business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDestintion_BPartner_ID (int Destintion_BPartner_ID);

	/**
	 * Get Destination business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getDestintion_BPartner_ID();

	String COLUMNNAME_Destintion_BPartner_ID = "Destintion_BPartner_ID";

	/**
	 * Set Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocAction (java.lang.String DocAction);

	/**
	 * Get Process Batch.
	 * Der zukünftige Status des Belegs
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocAction();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_DocAction = new ModelColumn<>(I_C_RemittanceAdvice.class, "DocAction", null);
	String COLUMNNAME_DocAction = "DocAction";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocStatus (java.lang.String DocStatus);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocStatus();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_DocStatus = new ModelColumn<>(I_C_RemittanceAdvice.class, "DocStatus", null);
	String COLUMNNAME_DocStatus = "DocStatus";

	/**
	 * Set Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDocumentNo (java.lang.String DocumentNo);

	/**
	 * Get Document No.
	 * Document sequence number of the document
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDocumentNo();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_DocumentNo = new ModelColumn<>(I_C_RemittanceAdvice.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set External document no.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalDocumentNo (@Nullable java.lang.String ExternalDocumentNo);

	/**
	 * Get External document no.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getExternalDocumentNo();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_ExternalDocumentNo = new ModelColumn<>(I_C_RemittanceAdvice.class, "ExternalDocumentNo", null);
	String COLUMNNAME_ExternalDocumentNo = "ExternalDocumentNo";

	/**
	 * Set Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setI_IsImported (boolean I_IsImported);

	/**
	 * Get Imported.
	 * Has this import been processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isI_IsImported();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_I_IsImported = new ModelColumn<>(I_C_RemittanceAdvice.class, "I_IsImported", null);
	String COLUMNNAME_I_IsImported = "I_IsImported";

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

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_IsActive = new ModelColumn<>(I_C_RemittanceAdvice.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Is document acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDocumentAcknowledged (boolean IsDocumentAcknowledged);

	/**
	 * Get Is document acknowledged.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDocumentAcknowledged();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_IsDocumentAcknowledged = new ModelColumn<>(I_C_RemittanceAdvice.class, "IsDocumentAcknowledged", null);
	String COLUMNNAME_IsDocumentAcknowledged = "IsDocumentAcknowledged";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_RemittanceAdvice.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Date Payment.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDate (@Nullable java.sql.Timestamp PaymentDate);

	/**
	 * Get Date Payment.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getPaymentDate();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_PaymentDate = new ModelColumn<>(I_C_RemittanceAdvice.class, "PaymentDate", null);
	String COLUMNNAME_PaymentDate = "PaymentDate";

	/**
	 * Set Payment discount amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPaymentDiscountAmountSum (@Nullable BigDecimal PaymentDiscountAmountSum);

	/**
	 * Get Payment discount amount.
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getPaymentDiscountAmountSum();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_PaymentDiscountAmountSum = new ModelColumn<>(I_C_RemittanceAdvice.class, "PaymentDiscountAmountSum", null);
	String COLUMNNAME_PaymentDiscountAmountSum = "PaymentDiscountAmountSum";

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

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_Processed = new ModelColumn<>(I_C_RemittanceAdvice.class, "Processed", null);
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

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_RemittanceAmt = new ModelColumn<>(I_C_RemittanceAdvice.class, "RemittanceAmt", null);
	String COLUMNNAME_RemittanceAmt = "RemittanceAmt";

	/**
	 * Set Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRemittanceAmt_Currency_ID (int RemittanceAmt_Currency_ID);

	/**
	 * Get Currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRemittanceAmt_Currency_ID();

	String COLUMNNAME_RemittanceAmt_Currency_ID = "RemittanceAmt_Currency_ID";

	/**
	 * Set Send at.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSendAt (@Nullable java.sql.Timestamp SendAt);

	/**
	 * Get Send at.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getSendAt();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_SendAt = new ModelColumn<>(I_C_RemittanceAdvice.class, "SendAt", null);
	String COLUMNNAME_SendAt = "SendAt";

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

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_ServiceFeeAmount = new ModelColumn<>(I_C_RemittanceAdvice.class, "ServiceFeeAmount", null);
	String COLUMNNAME_ServiceFeeAmount = "ServiceFeeAmount";

	/**
	 * Set Service fee currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setServiceFeeAmount_Currency_ID (int ServiceFeeAmount_Currency_ID);

	/**
	 * Get Service fee currency.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getServiceFeeAmount_Currency_ID();

	String COLUMNNAME_ServiceFeeAmount_Currency_ID = "ServiceFeeAmount_Currency_ID";

	/**
	 * Set Service fee extenal invoice no.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setServiceFeeExternalInvoiceDocumentNo (@Nullable java.lang.String ServiceFeeExternalInvoiceDocumentNo);

	/**
	 * Get Service fee extenal invoice no.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getServiceFeeExternalInvoiceDocumentNo();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_ServiceFeeExternalInvoiceDocumentNo = new ModelColumn<>(I_C_RemittanceAdvice.class, "ServiceFeeExternalInvoiceDocumentNo", null);
	String COLUMNNAME_ServiceFeeExternalInvoiceDocumentNo = "ServiceFeeExternalInvoiceDocumentNo";

	/**
	 * Set Service fee invoiced date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setServiceFeeInvoicedDate (@Nullable java.sql.Timestamp ServiceFeeInvoicedDate);

	/**
	 * Get Service fee invoiced date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getServiceFeeInvoicedDate();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_ServiceFeeInvoicedDate = new ModelColumn<>(I_C_RemittanceAdvice.class, "ServiceFeeInvoicedDate", null);
	String COLUMNNAME_ServiceFeeInvoicedDate = "ServiceFeeInvoicedDate";

	/**
	 * Set Source bank account.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_BP_BankAccount_ID (int Source_BP_BankAccount_ID);

	/**
	 * Get Source bank account.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_BP_BankAccount_ID();

	String COLUMNNAME_Source_BP_BankAccount_ID = "Source_BP_BankAccount_ID";

	/**
	 * Set Source business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSource_BPartner_ID (int Source_BPartner_ID);

	/**
	 * Get Source business partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSource_BPartner_ID();

	String COLUMNNAME_Source_BPartner_ID = "Source_BPartner_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_RemittanceAdvice, Object> COLUMN_Updated = new ModelColumn<>(I_C_RemittanceAdvice.class, "Updated", null);
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
