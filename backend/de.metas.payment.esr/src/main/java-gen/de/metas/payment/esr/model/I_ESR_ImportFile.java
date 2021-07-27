package de.metas.payment.esr.model;

import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ESR_ImportFile
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ESR_ImportFile 
{

	String Table_Name = "ESR_ImportFile";

//	/** AD_Table_ID=541753 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_AttachmentEntry_ID (int AD_AttachmentEntry_ID);

	/**
	 * Get Attachment.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_AttachmentEntry_ID();

	@Nullable org.compiere.model.I_AD_AttachmentEntry getAD_AttachmentEntry();

	void setAD_AttachmentEntry(@Nullable org.compiere.model.I_AD_AttachmentEntry AD_AttachmentEntry);

	ModelColumn<I_ESR_ImportFile, org.compiere.model.I_AD_AttachmentEntry> COLUMN_AD_AttachmentEntry_ID = new ModelColumn<>(I_ESR_ImportFile.class, "AD_AttachmentEntry_ID", org.compiere.model.I_AD_AttachmentEntry.class);
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
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_Created = new ModelColumn<>(I_ESR_ImportFile.class, "Created", null);
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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_DataType = new ModelColumn<>(I_ESR_ImportFile.class, "DataType", null);
	String COLUMNNAME_DataType = "DataType";

	/**
	 * Set Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_Description = new ModelColumn<>(I_ESR_ImportFile.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Sum Control Amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_Control_Amount (BigDecimal ESR_Control_Amount);

	/**
	 * Get Sum Control Amount.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	BigDecimal getESR_Control_Amount();

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_ESR_Control_Amount = new ModelColumn<>(I_ESR_ImportFile.class, "ESR_Control_Amount", null);
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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_ESR_Control_Trx_Qty = new ModelColumn<>(I_ESR_ImportFile.class, "ESR_Control_Trx_Qty", null);
	String COLUMNNAME_ESR_Control_Trx_Qty = "ESR_Control_Trx_Qty";

	/**
	 * Set ESR Import File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setESR_ImportFile_ID (int ESR_ImportFile_ID);

	/**
	 * Get ESR Import File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getESR_ImportFile_ID();

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_ESR_ImportFile_ID = new ModelColumn<>(I_ESR_ImportFile.class, "ESR_ImportFile_ID", null);
	String COLUMNNAME_ESR_ImportFile_ID = "ESR_ImportFile_ID";

	/**
	 * Set ESR Payment Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setESR_Import_ID (int ESR_Import_ID);

	/**
	 * Get ESR Payment Import.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getESR_Import_ID();

	@Nullable de.metas.payment.esr.model.I_ESR_Import getESR_Import();

	void setESR_Import(@Nullable de.metas.payment.esr.model.I_ESR_Import ESR_Import);

	ModelColumn<I_ESR_ImportFile, de.metas.payment.esr.model.I_ESR_Import> COLUMN_ESR_Import_ID = new ModelColumn<>(I_ESR_ImportFile.class, "ESR_Import_ID", de.metas.payment.esr.model.I_ESR_Import.class);
	String COLUMNNAME_ESR_Import_ID = "ESR_Import_ID";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName (@Nullable java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFileName();

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_FileName = new ModelColumn<>(I_ESR_ImportFile.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_Hash = new ModelColumn<>(I_ESR_ImportFile.class, "Hash", null);
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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_IsActive = new ModelColumn<>(I_ESR_ImportFile.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_IsReceipt = new ModelColumn<>(I_ESR_ImportFile.class, "IsReceipt", null);
	String COLUMNNAME_IsReceipt = "IsReceipt";

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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_IsValid = new ModelColumn<>(I_ESR_ImportFile.class, "IsValid", null);
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

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_Processed = new ModelColumn<>(I_ESR_ImportFile.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ESR_ImportFile, Object> COLUMN_Updated = new ModelColumn<>(I_ESR_ImportFile.class, "Updated", null);
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
