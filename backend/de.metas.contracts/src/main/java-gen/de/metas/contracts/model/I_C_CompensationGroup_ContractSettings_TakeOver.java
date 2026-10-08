package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for C_CompensationGroup_ContractSettings_TakeOver
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_C_CompensationGroup_ContractSettings_TakeOver 
{

	String Table_Name = "C_CompensationGroup_ContractSettings_TakeOver";

//	/** AD_Table_ID=542652 */
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
	 * Set Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_ID (int C_CompensationGroup_ContractSettings_ID);

	/**
	 * Get Compensation group contract settings.
	 * Reference to a compensation group contract settings record (schema and document types).
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, de.metas.contracts.model.I_C_CompensationGroup_ContractSettings> COLUMN_C_CompensationGroup_ContractSettings_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "C_CompensationGroup_ContractSettings_ID", de.metas.contracts.model.I_C_CompensationGroup_ContractSettings.class);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_ID = "C_CompensationGroup_ContractSettings_ID";

	/**
	 * Set Compensation group contract settings take-over.
	 * Take-over setting per product category: discount product of the own line of a compensation group contract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setC_CompensationGroup_ContractSettings_TakeOver_ID (int C_CompensationGroup_ContractSettings_TakeOver_ID);

	/**
	 * Get Compensation group contract settings take-over.
	 * Take-over setting per product category: discount product of the own line of a compensation group contract.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getC_CompensationGroup_ContractSettings_TakeOver_ID();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_C_CompensationGroup_ContractSettings_TakeOver_ID = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "C_CompensationGroup_ContractSettings_TakeOver_ID", null);
	String COLUMNNAME_C_CompensationGroup_ContractSettings_TakeOver_ID = "C_CompensationGroup_ContractSettings_TakeOver_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_Created = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "Created", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_CreatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "CreatedBy", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_IsActive = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Product_Category_ID (int M_Product_Category_ID);

	/**
	 * Get Product Category.
	 * Category of a Product
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Product_Category_ID();

	String COLUMNNAME_M_Product_Category_ID = "M_Product_Category_ID";

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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_Updated = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "Updated", null);
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

	ModelColumn<I_C_CompensationGroup_ContractSettings_TakeOver, Object> COLUMN_UpdatedBy = new ModelColumn<>(I_C_CompensationGroup_ContractSettings_TakeOver.class, "UpdatedBy", null);
	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
