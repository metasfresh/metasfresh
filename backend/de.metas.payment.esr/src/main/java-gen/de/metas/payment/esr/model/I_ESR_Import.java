package de.metas.payment.esr.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ESR_Import
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ESR_Import 
{

	String Table_Name = "ESR_Import";

//	/** AD_Table_ID=540409 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Attachment.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_AttachmentEntry_ID();

	ModelColumn<I_ESR_Import, Object> COLUMN_AD_AttachmentEntry_ID = new ModelColumn<>(I_ESR_Import.class, "AD_AttachmentEntry_ID", null);
	String COLUMNNAME_AD_AttachmentEntry_ID = "AD_AttachmentEntry_ID";

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
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ESR_Import, Object> COLUMN_Created = new ModelColumn<>(I_ESR_Import.class, "Created", null);
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
	 * Set Data Type.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDataType (@Nullable java.lang.String DataType);

	/**
	 * Get Data Type.
	 * Type of data
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDataType();

	ModelColumn<I_ESR_Import, Object> COLUMN_DataType = new ModelColumn<>(I_ESR_Import.class, "DataType", null);
	String COLUMNNAME_DataType = "DataType";

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

	ModelColumn<I_ESR_Import, Object> COLUMN_DateDoc = new ModelColumn<>(I_ESR_Import.class, "DateDoc", null);
	String COLUMNNAME_DateDoc = "DateDoc";

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

	ModelColumn<I_ESR_Import, Object> COLUMN_Description = new ModelColumn<>(I_ESR_Import.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Sum Control Amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Control_Amount (@Nullable BigDecimal ESR_Control_Amount);

	/**
	 * Get Sum Control Amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getESR_Control_Amount();

	ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Control_Amount = new ModelColumn<>(I_ESR_Import.class, "ESR_Control_Amount", null);
	String COLUMNNAME_ESR_Control_Amount = "ESR_Control_Amount";

	/**
	 * Set Control Qty Transactions.
	 * Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Control_Trx_Qty (@Nullable BigDecimal ESR_Control_Trx_Qty);

	/**
	 * Get Control Qty Transactions.
	 * Der Wert wurde aus der Eingabedatei eingelesen (falls dort bereit gestellt)
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getESR_Control_Trx_Qty();

	ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Control_Trx_Qty = new ModelColumn<>(I_ESR_Import.class, "ESR_Control_Trx_Qty", null);
	String COLUMNNAME_ESR_Control_Trx_Qty = "ESR_Control_Trx_Qty";

	/**
	 * Set ESR Payment Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Payment Import.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getESR_Import_ID();

	ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Import_ID = new ModelColumn<>(I_ESR_Import.class, "ESR_Import_ID", null);
	String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";

	/**
	 * Set Anzahl Transaktionen.
	 * Anzahl der importierten Zeilen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setESR_Trx_Qty (@Nullable BigDecimal ESR_Trx_Qty);

	/**
	 * Get Anzahl Transaktionen.
	 * Anzahl der importierten Zeilen
	 *
	 * <br>Type: Quantity
	 * <br>Mandatory: false
	 * <br>Virtual Column: true (lazy loading)
	 * @deprecated Please don't use it because this is a lazy loading column and it might affect the performances
	 */
	@Deprecated
	BigDecimal getESR_Trx_Qty();

	ModelColumn<I_ESR_Import, Object> COLUMN_ESR_Trx_Qty = new ModelColumn<>(I_ESR_Import.class, "ESR_Trx_Qty", null);
	String COLUMNNAME_ESR_Trx_Qty = "ESR_Trx_Qty";

	/**
	 * Set Hash.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHash (@Nullable java.lang.String Hash);

	/**
	 * Get Hash.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHash();

	ModelColumn<I_ESR_Import, Object> COLUMN_Hash = new ModelColumn<>(I_ESR_Import.class, "Hash", null);
	String COLUMNNAME_Hash = "Hash";

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

	ModelColumn<I_ESR_Import, Object> COLUMN_IsActive = new ModelColumn<>(I_ESR_Import.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Archive File.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsArchiveFile (boolean IsArchiveFile);

	/**
	 * Get Archive File.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isArchiveFile();

	ModelColumn<I_ESR_Import, Object> COLUMN_IsArchiveFile = new ModelColumn<>(I_ESR_Import.class, "IsArchiveFile", null);
	String COLUMNNAME_IsArchiveFile = "IsArchiveFile";

	/**
	 * Set Receipt.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReceipt (boolean IsReceipt);

	/**
	 * Get Receipt.
	 * This is a sales transaction (receipt)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReceipt();

	ModelColumn<I_ESR_Import, Object> COLUMN_IsReceipt = new ModelColumn<>(I_ESR_Import.class, "IsReceipt", null);
	String COLUMNNAME_IsReceipt = "IsReceipt";

	/**
	 * Set Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReconciled (boolean IsReconciled);

	/**
	 * Get Reconciled.
	 * Payment is reconciled with bank statement
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReconciled();

	ModelColumn<I_ESR_Import, Object> COLUMN_IsReconciled = new ModelColumn<>(I_ESR_Import.class, "IsReconciled", null);
	String COLUMNNAME_IsReconciled = "IsReconciled";

	/**
	 * Set Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsValid (boolean IsValid);

	/**
	 * Get Is Valid.
	 * The element is valid
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isValid();

	ModelColumn<I_ESR_Import, Object> COLUMN_IsValid = new ModelColumn<>(I_ESR_Import.class, "IsValid", null);
	String COLUMNNAME_IsValid = "IsValid";

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

	ModelColumn<I_ESR_Import, Object> COLUMN_Processed = new ModelColumn<>(I_ESR_Import.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 * @deprecated Please don't use it because this is a virtual column
	 */
	@Deprecated
	void setProcessing (boolean Processing);

	/**
	 * Get Process Now.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: true
	 */
	boolean isProcessing();

	ModelColumn<I_ESR_Import, Object> COLUMN_Processing = new ModelColumn<>(I_ESR_Import.class, "Processing", null);
	String COLUMNNAME_Processing = "Processing";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ESR_Import, Object> COLUMN_Updated = new ModelColumn<>(I_ESR_Import.class, "Updated", null);
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
