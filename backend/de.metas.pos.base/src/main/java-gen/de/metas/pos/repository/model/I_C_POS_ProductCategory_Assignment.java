package de.metas.pos.repository.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_POS_ProductCategory_Assignment
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_POS_ProductCategory_Assignment 
{

	String Table_Name = "C_POS_ProductCategory_Assignment";

//	/** AD_Table_ID=542448 */
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
	 * Set POS Product Category.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_POS_ProductCategory_ID (int C_POS_ProductCategory_ID);

	/**
	 * Get POS Product Category.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_POS_ProductCategory_ID();

	de.metas.pos.repository.model.I_C_POS_ProductCategory getC_POS_ProductCategory();

	void setC_POS_ProductCategory(de.metas.pos.repository.model.I_C_POS_ProductCategory C_POS_ProductCategory);

	ModelColumn<I_C_POS_ProductCategory_Assignment, de.metas.pos.repository.model.I_C_POS_ProductCategory> COLUMN_C_POS_ProductCategory_ID = new ModelColumn<>(I_C_POS_ProductCategory_Assignment.class, "C_POS_ProductCategory_ID", de.metas.pos.repository.model.I_C_POS_ProductCategory.class);
	String COLUMNNAME_C_POS_ProductCategory_ID = "C_POS_ProductCategory_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_POS_ProductCategory_Assignment, Object> COLUMN_Created = new ModelColumn<>(I_C_POS_ProductCategory_Assignment.class, "Created", null);
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

	ModelColumn<I_C_POS_ProductCategory_Assignment, Object> COLUMN_IsActive = new ModelColumn<>(I_C_POS_ProductCategory_Assignment.class, "IsActive", null);
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
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_C_POS_ProductCategory_Assignment, Object> COLUMN_Updated = new ModelColumn<>(I_C_POS_ProductCategory_Assignment.class, "Updated", null);
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
