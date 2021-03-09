package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_Alberta
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_Alberta 
{

	String Table_Name = "ExternalSystem_Config_Alberta";

//	/** AD_Table_ID=541577 */
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
	 * Set API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApiKey (java.lang.String ApiKey);

	/**
	 * Get API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApiKey();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_ApiKey = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ApiKey", null);
	String COLUMNNAME_ApiKey = "ApiKey";

	/**
	 * Set Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseURL (java.lang.String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getBaseURL();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set ExternalSystem_Config_Alberta.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_Alberta_ID (int ExternalSystem_Config_Alberta_ID);

	/**
	 * Get ExternalSystem_Config_Alberta.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_Alberta_ID();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_ExternalSystem_Config_Alberta_ID = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ExternalSystem_Config_Alberta_ID", null);
	String COLUMNNAME_ExternalSystem_Config_Alberta_ID = "ExternalSystem_Config_Alberta_ID";

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

	de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_Alberta, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

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

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_Name = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Tenant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTenant (java.lang.String Tenant);

	/**
	 * Get Tenant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTenant();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_Tenant = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "Tenant", null);
	String COLUMNNAME_Tenant = "Tenant";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
