package de.metas.payment.sepa.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for SEPA_Export
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_SEPA_Export 
{

	String Table_Name = "SEPA_Export";

//	/** AD_Table_ID=540603 */
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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_SEPA_Export, Object> COLUMN_Created = new ModelColumn<>(I_SEPA_Export.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_SEPA_Export, Object> COLUMN_Description = new ModelColumn<>(I_SEPA_Export.class, "Description", null);
	String COLUMNNAME_Description = "Description";

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

	ModelColumn<I_SEPA_Export, Object> COLUMN_DocumentNo = new ModelColumn<>(I_SEPA_Export.class, "DocumentNo", null);
	String COLUMNNAME_DocumentNo = "DocumentNo";

	/**
	 * Set IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIBAN (java.lang.String IBAN);

	/**
	 * Get IBAN.
	 * International Bank Account Number
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getIBAN();

	ModelColumn<I_SEPA_Export, Object> COLUMN_IBAN = new ModelColumn<>(I_SEPA_Export.class, "IBAN", null);
	String COLUMNNAME_IBAN = "IBAN";

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

	ModelColumn<I_SEPA_Export, Object> COLUMN_IsActive = new ModelColumn<>(I_SEPA_Export.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Sammelbuchungen exportieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsExportBatchBookings (boolean IsExportBatchBookings);

	/**
	 * Get Sammelbuchungen exportieren.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isExportBatchBookings();

	ModelColumn<I_SEPA_Export, Object> COLUMN_IsExportBatchBookings = new ModelColumn<>(I_SEPA_Export.class, "IsExportBatchBookings", null);
	String COLUMNNAME_IsExportBatchBookings = "IsExportBatchBookings";

	/**
	 * Set Date Payment.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPaymentDate (java.sql.Timestamp PaymentDate);

	/**
	 * Get Date Payment.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getPaymentDate();

	ModelColumn<I_SEPA_Export, Object> COLUMN_PaymentDate = new ModelColumn<>(I_SEPA_Export.class, "PaymentDate", null);
	String COLUMNNAME_PaymentDate = "PaymentDate";

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

	ModelColumn<I_SEPA_Export, Object> COLUMN_Processed = new ModelColumn<>(I_SEPA_Export.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_SEPA_Export, Object> COLUMN_Record_ID = new ModelColumn<>(I_SEPA_Export.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSEPA_CreditorIdentifier (@Nullable java.lang.String SEPA_CreditorIdentifier);

	/**
	 * Get SEPA Creditor Identifier.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSEPA_CreditorIdentifier();

	ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_CreditorIdentifier = new ModelColumn<>(I_SEPA_Export.class, "SEPA_CreditorIdentifier", null);
	String COLUMNNAME_SEPA_CreditorIdentifier = "SEPA_CreditorIdentifier";

	/**
	 * Set Creditor name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_CreditorName (java.lang.String SEPA_CreditorName);

	/**
	 * Get Creditor name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSEPA_CreditorName();

	ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_CreditorName = new ModelColumn<>(I_SEPA_Export.class, "SEPA_CreditorName", null);
	String COLUMNNAME_SEPA_CreditorName = "SEPA_CreditorName";

	/**
	 * Set SEPA Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_Export_ID (int SEPA_Export_ID);

	/**
	 * Get SEPA Export.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSEPA_Export_ID();

	ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_Export_ID = new ModelColumn<>(I_SEPA_Export.class, "SEPA_Export_ID", null);
	String COLUMNNAME_SEPA_Export_ID = "SEPA_Export_ID";

	/**
	 * Set SEPA Protocol.
	 * Version of the pain.001 credit-transfer file. Direct debit always uses pain.008.003.02.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSEPA_Protocol (java.lang.String SEPA_Protocol);

	/**
	 * Get SEPA Protocol.
	 * Version of the pain.001 credit-transfer file. Direct debit always uses pain.008.003.02.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSEPA_Protocol();

	ModelColumn<I_SEPA_Export, Object> COLUMN_SEPA_Protocol = new ModelColumn<>(I_SEPA_Export.class, "SEPA_Protocol", null);
	String COLUMNNAME_SEPA_Protocol = "SEPA_Protocol";

	/**
	 * Set Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSwiftCode (java.lang.String SwiftCode);

	/**
	 * Get Swift code.
	 * Swift Code or BIC
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSwiftCode();

	ModelColumn<I_SEPA_Export, Object> COLUMN_SwiftCode = new ModelColumn<>(I_SEPA_Export.class, "SwiftCode", null);
	String COLUMNNAME_SwiftCode = "SwiftCode";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_SEPA_Export, Object> COLUMN_Updated = new ModelColumn<>(I_SEPA_Export.class, "Updated", null);
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
