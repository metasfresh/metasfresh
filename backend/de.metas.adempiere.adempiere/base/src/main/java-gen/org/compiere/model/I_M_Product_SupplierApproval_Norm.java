package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_SupplierApproval_Norm
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_SupplierApproval_Norm 
{

	String Table_Name = "M_Product_SupplierApproval_Norm";

//	/** AD_Table_ID=541742 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_SupplierApproval_Norm, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_SupplierApproval_Norm.class, "Created", null);
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

	ModelColumn<I_M_Product_SupplierApproval_Norm, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_SupplierApproval_Norm.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_ID (int M_Product_ID);

	/**
	 * Get Product.
	 * Product, Service, Item
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_ID();

	String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/**
	 * Set Product Supplier Approval Norm.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_SupplierApproval_Norm_ID (int M_Product_SupplierApproval_Norm_ID);

	/**
	 * Get Product Supplier Approval Norm.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_SupplierApproval_Norm_ID();

	ModelColumn<I_M_Product_SupplierApproval_Norm, Object> COLUMN_M_Product_SupplierApproval_Norm_ID = new ModelColumn<>(I_M_Product_SupplierApproval_Norm.class, "M_Product_SupplierApproval_Norm_ID", null);
	String COLUMNNAME_M_Product_SupplierApproval_Norm_ID = "M_Product_SupplierApproval_Norm_ID";

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

	ModelColumn<I_M_Product_SupplierApproval_Norm, Object> COLUMN_SupplierApproval_Norm = new ModelColumn<>(I_M_Product_SupplierApproval_Norm.class, "SupplierApproval_Norm", null);
	String COLUMNNAME_SupplierApproval_Norm = "SupplierApproval_Norm";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_SupplierApproval_Norm, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_SupplierApproval_Norm.class, "Updated", null);
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
