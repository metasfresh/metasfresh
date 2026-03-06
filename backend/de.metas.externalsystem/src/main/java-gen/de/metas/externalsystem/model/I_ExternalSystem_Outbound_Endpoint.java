package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Outbound_Endpoint
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Outbound_Endpoint 
{

	String Table_Name = "ExternalSystem_Outbound_Endpoint";

//	/** AD_Table_ID=542551 */
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
	void setAuthToken (@Nullable String AuthToken);

	/**
	 * Get Authentication Token.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getAuthToken();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_AuthToken = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "AuthToken", null);
	String COLUMNNAME_AuthToken = "AuthToken";

	/**
	 * Set Authentication Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthType (String AuthType);

	/**
	 * Get Authentication Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getAuthType();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_AuthType = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "AuthType", null);
	String COLUMNNAME_AuthType = "AuthType";

	/**
	 * Set Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClientId (@Nullable String ClientId);

	/**
	 * Get Client ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getClientId();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_ClientId = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "ClientId", null);
	String COLUMNNAME_ClientId = "ClientId";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setClientSecret (@Nullable String ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getClientSecret();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_ClientSecret = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "ClientSecret", null);
	String COLUMNNAME_ClientSecret = "ClientSecret";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Created", null);
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
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDescription();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Description = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set External System Outbound Endpoint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Outbound_Endpoint_ID (int ExternalSystem_Outbound_Endpoint_ID);

	/**
	 * Get External System Outbound Endpoint.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Outbound_Endpoint_ID();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_ExternalSystem_Outbound_Endpoint_ID = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "ExternalSystem_Outbound_Endpoint_ID", null);
	String COLUMNNAME_ExternalSystem_Outbound_Endpoint_ID = "ExternalSystem_Outbound_Endpoint_ID";

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

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoginUsername (@Nullable String LoginUsername);

	/**
	 * Get Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getLoginUsername();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_LoginUsername = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "LoginUsername", null);
	String COLUMNNAME_LoginUsername = "LoginUsername";

	/**
	 * Set Outbound HTTP Endpoint.
	 * URL to which the data will be sent
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOutboundHttpEP (String OutboundHttpEP);

	/**
	 * Get Outbound HTTP Endpoint.
	 * URL to which the data will be sent
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getOutboundHttpEP();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_OutboundHttpEP = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "OutboundHttpEP", null);
	String COLUMNNAME_OutboundHttpEP = "OutboundHttpEP";

	/**
	 * Set Outbound HTTP Method.
	 * HTTP method to use when sending data (e.g. POST, PUT)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setOutboundHttpMethod (String OutboundHttpMethod);

	/**
	 * Get Outbound HTTP Method.
	 * HTTP method to use when sending data (e.g. POST, PUT)
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getOutboundHttpMethod();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_OutboundHttpMethod = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "OutboundHttpMethod", null);
	String COLUMNNAME_OutboundHttpMethod = "OutboundHttpMethod";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getPassword();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Password = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Password", null);
	String COLUMNNAME_Password = "Password";

	/**
	 * Set SAS-Signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSasSignature (@Nullable String SasSignature);

	/**
	 * Get SAS-Signature.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getSasSignature();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_SasSignature = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "SasSignature", null);
	String COLUMNNAME_SasSignature = "SasSignature";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getType();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Type = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Updated", null);
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
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getValue();

	ModelColumn<I_ExternalSystem_Outbound_Endpoint, Object> COLUMN_Value = new ModelColumn<>(I_ExternalSystem_Outbound_Endpoint.class, "Value", null);
	String COLUMNNAME_Value = "Value";
}
