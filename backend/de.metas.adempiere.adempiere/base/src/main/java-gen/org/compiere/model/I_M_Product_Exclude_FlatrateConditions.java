package org.compiere.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_Exclude_FlatrateConditions
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_Exclude_FlatrateConditions 
{

	String Table_Name = "M_Product_Exclude_FlatrateConditions";

//	/** AD_Table_ID=541670 */
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
	 * Set Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_Schema_ID (int C_CompensationGroup_Schema_ID);

	/**
	 * Get Compensation Group Schema.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_Schema_ID();

	de.metas.order.model.I_C_CompensationGroup_Schema getC_CompensationGroup_Schema();

	void setC_CompensationGroup_Schema(de.metas.order.model.I_C_CompensationGroup_Schema C_CompensationGroup_Schema);

	ModelColumn<I_M_Product_Exclude_FlatrateConditions, de.metas.order.model.I_C_CompensationGroup_Schema> COLUMN_C_CompensationGroup_Schema_ID = new ModelColumn<>(I_M_Product_Exclude_FlatrateConditions.class, "C_CompensationGroup_Schema_ID", de.metas.order.model.I_C_CompensationGroup_Schema.class);
	String COLUMNNAME_C_CompensationGroup_Schema_ID = "C_CompensationGroup_Schema_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_Exclude_FlatrateConditions, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_Exclude_FlatrateConditions.class, "Created", null);
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

	ModelColumn<I_M_Product_Exclude_FlatrateConditions, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_Exclude_FlatrateConditions.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Excluded from Flatrate Conditions.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Exclude_FlatrateConditions_ID (int M_Product_Exclude_FlatrateConditions_ID);

	/**
	 * Get Product Excluded from Flatrate Conditions.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Exclude_FlatrateConditions_ID();

	ModelColumn<I_M_Product_Exclude_FlatrateConditions, Object> COLUMN_M_Product_Exclude_FlatrateConditions_ID = new ModelColumn<>(I_M_Product_Exclude_FlatrateConditions.class, "M_Product_Exclude_FlatrateConditions_ID", null);
	String COLUMNNAME_M_Product_Exclude_FlatrateConditions_ID = "M_Product_Exclude_FlatrateConditions_ID";

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
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_Exclude_FlatrateConditions, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_Exclude_FlatrateConditions.class, "Updated", null);
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
