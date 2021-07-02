package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_BP_BankAccount_InvoiceAutoAllocateRule
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_BankAccount_InvoiceAutoAllocateRule 
{

	String Table_Name = "C_BP_BankAccount_InvoiceAutoAllocateRule";

//	/** AD_Table_ID=541673 */
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
	 * Set Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID);

	/**
	 * Get Partner Bank Account.
	 * Bank Account of the Business Partner
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_ID();

	String COLUMNNAME_C_BP_BankAccount_ID = "C_BP_BankAccount_ID";

	/**
	 * Set Invoice Auto allocation Restrictions.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_BankAccount_InvoiceAutoAllocateRule_ID (int C_BP_BankAccount_InvoiceAutoAllocateRule_ID);

	/**
	 * Get Invoice Auto allocation Restrictions.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_BankAccount_InvoiceAutoAllocateRule_ID();

	ModelColumn<I_C_BP_BankAccount_InvoiceAutoAllocateRule, Object> COLUMN_C_BP_BankAccount_InvoiceAutoAllocateRule_ID = new ModelColumn<>(I_C_BP_BankAccount_InvoiceAutoAllocateRule.class, "C_BP_BankAccount_InvoiceAutoAllocateRule_ID", null);
	String COLUMNNAME_C_BP_BankAccount_InvoiceAutoAllocateRule_ID = "C_BP_BankAccount_InvoiceAutoAllocateRule_ID";

	/**
	 * Set DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_DocTypeInvoice_ID (int C_DocTypeInvoice_ID);

	/**
	 * Get DocType Invoice.
	 * Document type used for invoices generated from this sales document
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_DocTypeInvoice_ID();

	String COLUMNNAME_C_DocTypeInvoice_ID = "C_DocTypeInvoice_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_BankAccount_InvoiceAutoAllocateRule, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_BankAccount_InvoiceAutoAllocateRule.class, "Created", null);
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

	ModelColumn<I_C_BP_BankAccount_InvoiceAutoAllocateRule, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_BankAccount_InvoiceAutoAllocateRule.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_BankAccount_InvoiceAutoAllocateRule, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_BankAccount_InvoiceAutoAllocateRule.class, "Updated", null);
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
