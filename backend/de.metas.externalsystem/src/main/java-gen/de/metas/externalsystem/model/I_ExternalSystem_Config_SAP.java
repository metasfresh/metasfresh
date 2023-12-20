package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_SAP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_SAP 
{

	String Table_Name = "ExternalSystem_Config_SAP";

//	/** AD_Table_ID=542238 */
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
	 * Set API-Version.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setApiVersion (@Nullable String ApiVersion);

	/**
	 * Get API-Version.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getApiVersion();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ApiVersion = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ApiVersion", null);
	String COLUMNNAME_ApiVersion = "ApiVersion";

	/**
	 * Set Base-URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBaseURL (@Nullable String BaseURL);

	/**
	 * Get Base-URL.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getBaseURL();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_BaseURL = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "BaseURL", null);
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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "Created", null);
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
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	@Nullable I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(@Nullable I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_SAP, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_SAP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_SAP_ID (int ExternalSystem_Config_SAP_ID);

	/**
	 * Get ExternalSystem_Config_SAP.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_SAP_ID();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ExternalSystem_Config_SAP_ID = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystem_Config_SAP_ID", null);
	String COLUMNNAME_ExternalSystem_Config_SAP_ID = "ExternalSystem_Config_SAP_ID";

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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Check also description for material type.
	 * If enabled, the process also looks into the dat file's product description column for material types that are mapped to a product category.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsCheckDescriptionForMaterialType (boolean IsCheckDescriptionForMaterialType);

	/**
	 * Get Check also description for material type.
	 * If enabled, the process also looks into the dat file's product description column for material types that are mapped to a product category.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	boolean isCheckDescriptionForMaterialType();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_IsCheckDescriptionForMaterialType = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "IsCheckDescriptionForMaterialType", null);
	String COLUMNNAME_IsCheckDescriptionForMaterialType = "IsCheckDescriptionForMaterialType";

	/**
	 * Set Post accouting documents path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPost_Acct_Documents_Path (@Nullable String Post_Acct_Documents_Path);

	/**
	 * Get Post accouting documents path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPost_Acct_Documents_Path();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_Post_Acct_Documents_Path = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "Post_Acct_Documents_Path", null);
	String COLUMNNAME_Post_Acct_Documents_Path = "Post_Acct_Documents_Path";

	/**
	 * Set Signature.
	 * Azure service shared access signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSignatureSAS (@Nullable String SignatureSAS);

	/**
	 * Get Signature.
	 * Azure service shared access signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSignatureSAS();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SignatureSAS = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SignatureSAS", null);
	String COLUMNNAME_SignatureSAS = "SignatureSAS";

	/**
	 * Set Signed permissions.
	 * Part of Azure service shared access signature. The permissions that are associated with the shared access signature. The user is restricted to operations that are allowed by the permissions. You must omit this field if it has been specified in an associated stored access policy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSignedPermissions (@Nullable String SignedPermissions);

	/**
	 * Get Signed permissions.
	 * Part of Azure service shared access signature. The permissions that are associated with the shared access signature. The user is restricted to operations that are allowed by the permissions. You must omit this field if it has been specified in an associated stored access policy.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSignedPermissions();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SignedPermissions = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SignedPermissions", null);
	String COLUMNNAME_SignedPermissions = "SignedPermissions";

	/**
	 * Set Signed version.
	 * Part of Azure service shared access signature. The storage service version to use to authorize and handle requests that you make with this shared access signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSignedVersion (@Nullable String SignedVersion);

	/**
	 * Get Signed version.
	 * Part of Azure service shared access signature. The storage service version to use to authorize and handle requests that you make with this shared access signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSignedVersion();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_SignedVersion = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "SignedVersion", null);
	String COLUMNNAME_SignedVersion = "SignedVersion";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_SAP, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_SAP.class, "Updated", null);
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
