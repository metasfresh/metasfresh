package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for C_BP_SupplierApproval
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_BP_SupplierApproval 
{

	String Table_Name = "C_BP_SupplierApproval";

//	/** AD_Table_ID=541740 */
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
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Set Supplier Approval.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_BP_SupplierApproval_ID (int C_BP_SupplierApproval_ID);

	/**
	 * Get Supplier Approval.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_BP_SupplierApproval_ID();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_C_BP_SupplierApproval_ID = new ModelColumn<>(I_C_BP_SupplierApproval.class, "C_BP_SupplierApproval_ID", null);
	String COLUMNNAME_C_BP_SupplierApproval_ID = "C_BP_SupplierApproval_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_Created = new ModelColumn<>(I_C_BP_SupplierApproval.class, "Created", null);
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

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_IsActive = new ModelColumn<>(I_C_BP_SupplierApproval.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Supplier Approval.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupplierApproval (@Nullable java.lang.String SupplierApproval);

	/**
	 * Get Supplier Approval.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupplierApproval();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_SupplierApproval = new ModelColumn<>(I_C_BP_SupplierApproval.class, "SupplierApproval", null);
	String COLUMNNAME_SupplierApproval = "SupplierApproval";

	/**
	 * Set Supplier Approval Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupplierApproval_Date (@Nullable java.sql.Timestamp SupplierApproval_Date);

	/**
	 * Get Supplier Approval Date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getSupplierApproval_Date();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_SupplierApproval_Date = new ModelColumn<>(I_C_BP_SupplierApproval.class, "SupplierApproval_Date", null);
	String COLUMNNAME_SupplierApproval_Date = "SupplierApproval_Date";

	/**
	 * Set Supplier Approval Norm.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSupplierApproval_Norm (java.lang.String SupplierApproval_Norm);

	/**
	 * Get Supplier Approval Norm.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSupplierApproval_Norm();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_SupplierApproval_Norm = new ModelColumn<>(I_C_BP_SupplierApproval.class, "SupplierApproval_Norm", null);
	String COLUMNNAME_SupplierApproval_Norm = "SupplierApproval_Norm";

	/**
	 * Set Approved for.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSupplierApproval_Type (@Nullable java.lang.String SupplierApproval_Type);

	/**
	 * Get Approved for.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSupplierApproval_Type();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_SupplierApproval_Type = new ModelColumn<>(I_C_BP_SupplierApproval.class, "SupplierApproval_Type", null);
	String COLUMNNAME_SupplierApproval_Type = "SupplierApproval_Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_BP_SupplierApproval, Object> COLUMN_Updated = new ModelColumn<>(I_C_BP_SupplierApproval.class, "Updated", null);
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
