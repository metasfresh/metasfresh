package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_LeichMehl_ProductMapping
 *  @author metasfresh (generated)
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_LeichMehl_ProductMapping
{

	String Table_Name = "ExternalSystem_Config_LeichMehl_ProductMapping";

	//	/** AD_Table_ID=542172 */
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
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "Created", null);
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
	 * Set ExternalSystem_Config_LeichMehl_ProductMapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_LeichMehl_ProductMapping_ID (int ExternalSystem_Config_LeichMehl_ProductMapping_ID);

	/**
	 * Get ExternalSystem_Config_LeichMehl_ProductMapping.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_LeichMehl_ProductMapping_ID();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_ExternalSystem_Config_LeichMehl_ProductMapping_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "ExternalSystem_Config_LeichMehl_ProductMapping_ID", null);
	String COLUMNNAME_ExternalSystem_Config_LeichMehl_ProductMapping_ID = "ExternalSystem_Config_LeichMehl_ProductMapping_ID";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PLU File Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setLeichMehl_PluFile_ConfigGroup_ID (int LeichMehl_PluFile_ConfigGroup_ID);

	/**
	 * Get PLU File Configuration.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getLeichMehl_PluFile_ConfigGroup_ID();

	de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup getLeichMehl_PluFile_ConfigGroup();

	void setLeichMehl_PluFile_ConfigGroup(de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup LeichMehl_PluFile_ConfigGroup);

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup> COLUMN_LeichMehl_PluFile_ConfigGroup_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "LeichMehl_PluFile_ConfigGroup_ID", de.metas.externalsystem.model.I_LeichMehl_PluFile_ConfigGroup.class);
	String COLUMNNAME_LeichMehl_PluFile_ConfigGroup_ID = "LeichMehl_PluFile_ConfigGroup_ID";

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
	 * Set PLU_File.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPLU_File (java.lang.String PLU_File);

	/**
	 * Get PLU_File.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPLU_File();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_PLU_File = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "PLU_File", null);
	String COLUMNNAME_PLU_File = "PLU_File";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl_ProductMapping, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl_ProductMapping.class, "Updated", null);
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
