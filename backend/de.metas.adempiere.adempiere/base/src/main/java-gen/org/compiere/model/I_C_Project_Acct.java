package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_Project_Acct
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_Project_Acct 
{

	String Table_Name = "C_Project_Acct";

//	/** AD_Table_ID=204 */
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
	 * Set Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_AcctSchema_ID (int C_AcctSchema_ID);

	/**
	 * Get Accounting Schema.
	 * Rules for accounting
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_AcctSchema_ID();

	org.compiere.model.I_C_AcctSchema getC_AcctSchema();

	void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema);

	ModelColumn<I_C_Project_Acct, org.compiere.model.I_C_AcctSchema> COLUMN_C_AcctSchema_ID = new ModelColumn<>(I_C_Project_Acct.class, "C_AcctSchema_ID", org.compiere.model.I_C_AcctSchema.class);
	String COLUMNNAME_C_AcctSchema_ID = "C_AcctSchema_ID";

	/**
	 * Set Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Project_ID (int C_Project_ID);

	/**
	 * Get Project.
	 * Financial Project
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Project_ID();

	String COLUMNNAME_C_Project_ID = "C_Project_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_Project_Acct, Object> COLUMN_Created = new ModelColumn<>(I_C_Project_Acct.class, "Created", null);
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

	ModelColumn<I_C_Project_Acct, Object> COLUMN_IsActive = new ModelColumn<>(I_C_Project_Acct.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPJ_Asset_Acct (int PJ_Asset_Acct);

	/**
	 * Get Project Asset.
	 * Project Asset Account
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPJ_Asset_Acct();

	org.compiere.model.I_C_ValidCombination getPJ_Asset_A();

	void setPJ_Asset_A(org.compiere.model.I_C_ValidCombination PJ_Asset_A);

	ModelColumn<I_C_Project_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_Asset_Acct = new ModelColumn<>(I_C_Project_Acct.class, "PJ_Asset_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PJ_Asset_Acct = "PJ_Asset_Acct";

	/**
	 * Set Unfertige Leistungen.
	 * Konto für Unfertige Leistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPJ_WIP_Acct (int PJ_WIP_Acct);

	/**
	 * Get Unfertige Leistungen.
	 * Konto für Unfertige Leistungen
	 *
	 * <br>Type: Account
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPJ_WIP_Acct();

	org.compiere.model.I_C_ValidCombination getPJ_WIP_A();

	void setPJ_WIP_A(org.compiere.model.I_C_ValidCombination PJ_WIP_A);

	ModelColumn<I_C_Project_Acct, org.compiere.model.I_C_ValidCombination> COLUMN_PJ_WIP_Acct = new ModelColumn<>(I_C_Project_Acct.class, "PJ_WIP_Acct", org.compiere.model.I_C_ValidCombination.class);
	String COLUMNNAME_PJ_WIP_Acct = "PJ_WIP_Acct";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_Project_Acct, Object> COLUMN_Updated = new ModelColumn<>(I_C_Project_Acct.class, "Updated", null);
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
