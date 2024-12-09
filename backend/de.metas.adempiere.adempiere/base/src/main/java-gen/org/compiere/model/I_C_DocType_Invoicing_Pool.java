package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_DocType_Invoicing_Pool
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_DocType_Invoicing_Pool 
{

	String Table_Name = "C_DocType_Invoicing_Pool";

//	/** AD_Table_ID=542277 */
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
	 * Set Invoicing Pool.
	 * An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocType_Invoicing_Pool_ID (int C_DocType_Invoicing_Pool_ID);

	/**
	 * Get Invoicing Pool.
	 * An invoicing pool is used to aggregate invoices and credit memos into a single document. It contains specific document types for aggregating positive invoice amounts (e.g., purchase invoice) and negative amounts (e.g., credit memo).
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocType_Invoicing_Pool_ID();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_C_DocType_Invoicing_Pool_ID = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "C_DocType_Invoicing_Pool_ID", null);
	String COLUMNNAME_C_DocType_Invoicing_Pool_ID = "C_DocType_Invoicing_Pool_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_Created = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "Created", null);
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

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_IsActive = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set (Apply to) different invoice document types only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsOnDistinctICTypes (boolean IsOnDistinctICTypes);

	/**
	 * Get (Apply to) different invoice document types only.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isOnDistinctICTypes();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_IsOnDistinctICTypes = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "IsOnDistinctICTypes", null);
	String COLUMNNAME_IsOnDistinctICTypes = "IsOnDistinctICTypes";

	/**
	 * Set Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Sales Transaction.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSOTrx();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_IsSOTrx = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "IsSOTrx", null);
	String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_Name = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Negative Amount Document Type.
	 * DocType to use for resulting document when grand total of invoices in the pool is negative
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setNegative_Amt_C_DocType_ID (int Negative_Amt_C_DocType_ID);

	/**
	 * Get Negative Amount Document Type.
	 * DocType to use for resulting document when grand total of invoices in the pool is negative
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getNegative_Amt_C_DocType_ID();

	String COLUMNNAME_Negative_Amt_C_DocType_ID = "Negative_Amt_C_DocType_ID";

	/**
	 * Set Positive Amount Document Type.
	 * DocType to use for resulting document when grand total of invoices in the pool is positive
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPositive_Amt_C_DocType_ID (int Positive_Amt_C_DocType_ID);

	/**
	 * Get Positive Amount Document Type.
	 * DocType to use for resulting document when grand total of invoices in the pool is positive
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPositive_Amt_C_DocType_ID();

	String COLUMNNAME_Positive_Amt_C_DocType_ID = "Positive_Amt_C_DocType_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_DocType_Invoicing_Pool, Object> COLUMN_Updated = new ModelColumn<>(I_C_DocType_Invoicing_Pool.class, "Updated", null);
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
