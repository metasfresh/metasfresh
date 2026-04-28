package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for M_Product_TaxCategory
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_M_Product_TaxCategory 
{

	String Table_Name = "M_Product_TaxCategory";

//	/** AD_Table_ID=542131 */
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
	 * Set Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Country_ID (int C_Country_ID);

	/**
	 * Get Country.
	 * Country
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Country_ID();

	@Nullable org.compiere.model.I_C_Country getC_Country();

	void setC_Country(@Nullable org.compiere.model.I_C_Country C_Country);

	ModelColumn<I_M_Product_TaxCategory, org.compiere.model.I_C_Country> COLUMN_C_Country_ID = new ModelColumn<>(I_M_Product_TaxCategory.class, "C_Country_ID", org.compiere.model.I_C_Country.class);
	String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/**
	 * Set Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_TaxCategory_ID (int C_TaxCategory_ID);

	/**
	 * Get Tax Category.
	 * Tax Category
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_TaxCategory_ID();

	String COLUMNNAME_C_TaxCategory_ID = "C_TaxCategory_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_M_Product_TaxCategory, Object> COLUMN_Created = new ModelColumn<>(I_M_Product_TaxCategory.class, "Created", null);
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

	ModelColumn<I_M_Product_TaxCategory, Object> COLUMN_IsActive = new ModelColumn<>(I_M_Product_TaxCategory.class, "IsActive", null);
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
	 * Set Product Tax Category.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_TaxCategory_ID (int M_Product_TaxCategory_ID);

	/**
	 * Get Product Tax Category.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_TaxCategory_ID();

	ModelColumn<I_M_Product_TaxCategory, Object> COLUMN_M_Product_TaxCategory_ID = new ModelColumn<>(I_M_Product_TaxCategory.class, "M_Product_TaxCategory_ID", null);
	String COLUMNNAME_M_Product_TaxCategory_ID = "M_Product_TaxCategory_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_M_Product_TaxCategory, Object> COLUMN_Updated = new ModelColumn<>(I_M_Product_TaxCategory.class, "Updated", null);
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

	/**
	 * Set Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValidFrom (java.sql.Timestamp ValidFrom);

	/**
	 * Get Valid From.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getValidFrom();

	ModelColumn<I_M_Product_TaxCategory, Object> COLUMN_ValidFrom = new ModelColumn<>(I_M_Product_TaxCategory.class, "ValidFrom", null);
	String COLUMNNAME_ValidFrom = "ValidFrom";
}
