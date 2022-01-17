package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_GRSSignum
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_GRSSignum 
{

	String Table_Name = "ExternalSystem_Config_GRSSignum";

//	/** AD_Table_ID=541882 */
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
	 * Set Authentication Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAuthToken (@Nullable java.lang.String AuthToken);

	/**
	 * Get Authentication Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAuthToken();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_AuthToken = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "AuthToken", null);
	String COLUMNNAME_AuthToken = "AuthToken";

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

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "BaseURL", null);
	String COLUMNNAME_BaseURL = "BaseURL";

	/**
	 * Set User Authentication Token .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCamelHttpResourceAuthKey (@Nullable java.lang.String CamelHttpResourceAuthKey);

	/**
	 * Get User Authentication Token .
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCamelHttpResourceAuthKey();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_CamelHttpResourceAuthKey = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "CamelHttpResourceAuthKey", null);
	String COLUMNNAME_CamelHttpResourceAuthKey = "CamelHttpResourceAuthKey";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "Created", null);
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
	 * Set GRSSignum Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_GRSSignum_ID (int ExternalSystem_Config_GRSSignum_ID);

	/**
	 * Get GRSSignum Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_GRSSignum_ID();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_ExternalSystem_Config_GRSSignum_ID = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "ExternalSystem_Config_GRSSignum_ID", null);
	String COLUMNNAME_ExternalSystem_Config_GRSSignum_ID = "ExternalSystem_Config_GRSSignum_ID";

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

	ModelColumn<I_ExternalSystem_Config_GRSSignum, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystemValue (java.lang.String ExternalSystemValue);

	/**
	 * Get Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExternalSystemValue();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auto-send customers.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSendCustomers (boolean IsAutoSendCustomers);

	/**
	 * Get Auto-send customers.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSendCustomers();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsAutoSendCustomers = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsAutoSendCustomers", null);
	String COLUMNNAME_IsAutoSendCustomers = "IsAutoSendCustomers";

	/**
	 * Set Auto-send vendors.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSendVendors (boolean IsAutoSendVendors);

	/**
	 * Get Auto-send vendors.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSendVendors();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsAutoSendVendors = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsAutoSendVendors", null);
	String COLUMNNAME_IsAutoSendVendors = "IsAutoSendVendors";

	/**
	 * Set Send business partners.
	 * If checked, then business selected partners can be initially send to GRS via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsSyncBPartnersToRestEndpoint (boolean IsSyncBPartnersToRestEndpoint);

	/**
	 * Get Send business partners.
	 * If checked, then business selected partners can be initially send to GRS via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isSyncBPartnersToRestEndpoint();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsSyncBPartnersToRestEndpoint = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsSyncBPartnersToRestEndpoint", null);
	String COLUMNNAME_IsSyncBPartnersToRestEndpoint = "IsSyncBPartnersToRestEndpoint";

	/**
	 * Set IsSyncHUsOnMaterialReceipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncHUsOnMaterialReceipt (boolean IsSyncHUsOnMaterialReceipt);

	/**
	 * Get IsSyncHUsOnMaterialReceipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncHUsOnMaterialReceipt();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsSyncHUsOnMaterialReceipt = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsSyncHUsOnMaterialReceipt", null);
	String COLUMNNAME_IsSyncHUsOnMaterialReceipt = "IsSyncHUsOnMaterialReceipt";

	/**
	 * Set IsSyncHUsOnProductionReceipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncHUsOnProductionReceipt (boolean IsSyncHUsOnProductionReceipt);

	/**
	 * Get IsSyncHUsOnProductionReceipt.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncHUsOnProductionReceipt();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_IsSyncHUsOnProductionReceipt = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "IsSyncHUsOnProductionReceipt", null);
	String COLUMNNAME_IsSyncHUsOnProductionReceipt = "IsSyncHUsOnProductionReceipt";

	/**
	 * Set Tenant-ID (MID).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTenantId (java.lang.String TenantId);

	/**
	 * Get Tenant-ID (MID).
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTenantId();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_TenantId = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "TenantId", null);
	String COLUMNNAME_TenantId = "TenantId";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_GRSSignum, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_GRSSignum.class, "Updated", null);
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
