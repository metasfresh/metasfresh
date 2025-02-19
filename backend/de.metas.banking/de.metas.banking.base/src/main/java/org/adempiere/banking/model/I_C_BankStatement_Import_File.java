package org.adempiere.banking.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BankStatement_Import_File
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BankStatement_Import_File 
{

	String Table_Name = "C_BankStatement_Import_File";

//	/** AD_Table_ID=542246 */
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
	 * Set Bank Statement Import-File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BankStatement_Import_File_ID (int C_BankStatement_Import_File_ID);

	/**
	 * Get Bank Statement Import-File.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BankStatement_Import_File_ID();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_C_BankStatement_Import_File_ID = new ModelColumn<>(I_C_BankStatement_Import_File.class, "C_BankStatement_Import_File_ID", null);
	String COLUMNNAME_C_BankStatement_Import_File_ID = "C_BankStatement_Import_File_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_Created = new ModelColumn<>(I_C_BankStatement_Import_File.class, "Created", null);
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
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFileName (String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	String getFileName();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_FileName = new ModelColumn<>(I_C_BankStatement_Import_File.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

	/**
	 * Set Imported timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImported (@Nullable java.sql.Timestamp Imported);

	/**
	 * Get Imported timestamp.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getImported();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_Imported = new ModelColumn<>(I_C_BankStatement_Import_File.class, "Imported", null);
	String COLUMNNAME_Imported = "Imported";

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

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BankStatement_Import_File.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Match Amounts.
	 * If set, the import process will also take into consideration the invoice open amount when looking for corresponding invoices.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMatchAmounts (boolean IsMatchAmounts);

	/**
	 * Get Match Amounts.
	 * If set, the import process will also take into consideration the invoice open amount when looking for corresponding invoices.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMatchAmounts();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_IsMatchAmounts = new ModelColumn<>(I_C_BankStatement_Import_File.class, "IsMatchAmounts", null);
	String COLUMNNAME_IsMatchAmounts = "IsMatchAmounts";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_Processed = new ModelColumn<>(I_C_BankStatement_Import_File.class, "Processed", null);
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

	ModelColumn<I_C_BankStatement_Import_File, Object> COLUMN_Updated = new ModelColumn<>(I_C_BankStatement_Import_File.class, "Updated", null);
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
