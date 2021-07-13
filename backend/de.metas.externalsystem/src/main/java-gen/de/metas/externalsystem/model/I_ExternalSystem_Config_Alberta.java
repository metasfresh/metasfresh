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
	void setApiKey (String ApiKey);

	/**
	 * Get API-Key.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getApiKey();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_ApiKey = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ApiKey", null);
	String COLUMNNAME_ApiKey = "ApiKey";

	/**
	 * Set Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setBaseURL (String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getBaseURL();

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Set Root BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_Root_BPartner_ID (int C_Root_BPartner_ID);

	/**
	 * Get Root BPartner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_Root_BPartner_ID();

	String COLUMNNAME_C_Root_BPartner_ID = "C_Root_BPartner_ID";

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

	I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_Alberta, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

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

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_Alberta, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_Alberta.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Pharmacy price list.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPharmacy_PriceList_ID (int Pharmacy_PriceList_ID);

	/**
	 * Get Pharmacy price list.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPharmacy_PriceList_ID();

	String COLUMNNAME_Pharmacy_PriceList_ID = "Pharmacy_PriceList_ID";

	/**
	 * Set Tenant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTenant (String Tenant);

	/**
	 * Get Tenant.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getTenant();

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
