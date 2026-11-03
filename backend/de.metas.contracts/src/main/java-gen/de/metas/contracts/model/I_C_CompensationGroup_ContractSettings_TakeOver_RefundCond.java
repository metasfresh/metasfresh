package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CompensationGroup_ContractSettings_TakeOver_RefundCond
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond 
{

	String Table_Name = "C_CompensationGroup_ContractSettings_TakeOver_RefundCond";

//	/** AD_Table_ID=542656 */
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
	 * Set Compensation group contract settings take-over.
	 * Take-over setting per product category: discount product of the own line of a compensation group contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_TakeOver_ID (int C_CompensationGroup_ContractSettings_TakeOver_ID);

	/**
	 * Get Compensation group contract settings take-over.
	 * Take-over setting per product category: discount product of the own line of a compensation group contract.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_TakeOver_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver> COLUMN_C_CompensationGroup_ContractSettings_TakeOver_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "C_CompensationGroup_ContractSettings_TakeOver_ID", de.metas.contracts.model.I_C_CompensationGroup_ContractSettings_TakeOver.class);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID = "C_CompensationGroup_ContractSettings_TakeOver_ID";

	/**
	 * Set Take-over refund condition.
	 * Refund condition whose refund percentage is taken over into the take-over line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID (int C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID);

	/**
	 * Get Take-over refund condition.
	 * Refund condition whose refund percentage is taken over into the take-over line.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID", null);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID = "C_CompensationGroup_ContractSettings_TakeOver_RefundCond_ID";

	/**
	 * Set Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_Flatrate_Conditions_ID (int C_Flatrate_Conditions_ID);

	/**
	 * Get Contract Terms.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_Flatrate_Conditions_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, de.metas.contracts.model.I_C_Flatrate_Conditions> COLUMN_C_Flatrate_Conditions_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "C_Flatrate_Conditions_ID", de.metas.contracts.model.I_C_Flatrate_Conditions.class);
	String COLUMNNAME_C_Flatrate_Conditions_ID = "C_Flatrate_Conditions_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_Created = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "Created", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "CreatedBy", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "IsActive", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_Updated = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "Updated", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver_RefundCond.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
