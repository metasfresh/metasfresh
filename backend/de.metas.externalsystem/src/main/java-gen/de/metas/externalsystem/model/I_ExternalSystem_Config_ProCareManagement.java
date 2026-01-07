package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_ProCareManagement
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_ProCareManagement 
{

	String Table_Name = "ExternalSystem_Config_ProCareManagement";

//	/** AD_Table_ID=542399 */
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

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "Created", null);
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
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set Pro Care Management.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ProCareManagement_ID (int ExternalSystem_Config_ProCareManagement_ID);

	/**
	 * Get Pro Care Management.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ProCareManagement_ID();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, Object> COLUMN_ExternalSystem_Config_ProCareManagement_ID = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "ExternalSystem_Config_ProCareManagement_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ProCareManagement_ID = "ExternalSystem_Config_ProCareManagement_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

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

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "IsActive", null);
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

	ModelColumn<I_ExternalSystem_Config_ProCareManagement, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_ProCareManagement.class, "Updated", null);
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
