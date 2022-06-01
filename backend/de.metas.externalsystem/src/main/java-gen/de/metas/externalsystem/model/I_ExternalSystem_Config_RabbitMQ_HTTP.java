package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_RabbitMQ_HTTP
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_RabbitMQ_HTTP 
{

	String Table_Name = "ExternalSystem_Config_RabbitMQ_HTTP";

//	/** AD_Table_ID=541803 */
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
	 * Set Users Group.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSubjectCreatedByUserGroup_ID (int SubjectCreatedByUserGroup_ID);

	/**
	 * Get Users Group.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSubjectCreatedByUserGroup_ID();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, org.compiere.model.I_AD_UserGroup> COLUMN_SubjectCreatedByUserGroup_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "SubjectCreatedByUserGroup_ID", org.compiere.model.I_AD_UserGroup.class);
	String COLUMNNAME_SubjectCreatedByUserGroup_ID = "SubjectCreatedByUserGroup_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Created", null);
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

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set RabbitMQ REST API Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_RabbitMQ_HTTP_ID (int ExternalSystem_Config_RabbitMQ_HTTP_ID);

	/**
	 * Get RabbitMQ REST API Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_RabbitMQ_HTTP_ID();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_ExternalSystem_Config_RabbitMQ_HTTP_ID = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystem_Config_RabbitMQ_HTTP_ID", null);
	String COLUMNNAME_ExternalSystem_Config_RabbitMQ_HTTP_ID = "ExternalSystem_Config_RabbitMQ_HTTP_ID";

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

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Auto-send if created by users group.
	 * If a business bartner was created by a user from the given group, then it is automatically send.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutoSendWhenCreatedByUserGroup (boolean IsAutoSendWhenCreatedByUserGroup);

	/**
	 * Get Auto-send if created by users group.
	 * If a business bartner was created by a user from the given group, then it is automatically send.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutoSendWhenCreatedByUserGroup();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsAutoSendWhenCreatedByUserGroup = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsAutoSendWhenCreatedByUserGroup", null);
	String COLUMNNAME_IsAutoSendWhenCreatedByUserGroup = "IsAutoSendWhenCreatedByUserGroup";

	/**
	 * Set IsSyncBPartnersToRabbitMQ.
	 * If checked, then business selected partners can be initially send to RabbitMQ via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncBPartnersToRabbitMQ (boolean IsSyncBPartnersToRabbitMQ);

	/**
	 * Get IsSyncBPartnersToRabbitMQ.
	 * If checked, then business selected partners can be initially send to RabbitMQ via an action in the business partner window. Once initially send, they will from there onwards be automatically send whenever changed in metasfresh.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncBPartnersToRabbitMQ();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsSyncBPartnersToRabbitMQ = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsSyncBPartnersToRabbitMQ", null);
	String COLUMNNAME_IsSyncBPartnersToRabbitMQ = "IsSyncBPartnersToRabbitMQ";

	/**
	 * Set Send partner references.
	 * If ticked, then selected external references to business partners can be initially sent to RabbitMQ with an action in the External Reference window. After they have been sent once, they are automatically resent when changes are made.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSyncExternalReferencesToRabbitMQ (boolean IsSyncExternalReferencesToRabbitMQ);

	/**
	 * Get Send partner references.
	 * If ticked, then selected external references to business partners can be initially sent to RabbitMQ with an action in the External Reference window. After they have been sent once, they are automatically resent when changes are made.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSyncExternalReferencesToRabbitMQ();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_IsSyncExternalReferencesToRabbitMQ = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "IsSyncExternalReferencesToRabbitMQ", null);
	String COLUMNNAME_IsSyncExternalReferencesToRabbitMQ = "IsSyncExternalReferencesToRabbitMQ";

	/**
	 * Set Remote-URL.
	 * URL of the RabbitMQ HTTP API server. metasfresh will append the path "/messages/publish" to the given URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRemoteURL (String RemoteURL);

	/**
	 * Get Remote-URL.
	 * URL of the RabbitMQ HTTP API server. metasfresh will append the path "/messages/publish" to the given URL
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getRemoteURL();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_RemoteURL = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "RemoteURL", null);
	String COLUMNNAME_RemoteURL = "RemoteURL";

	/**
	 * Set Routing-Key.
	 * Messages are send to the default exchange with the given "routing_key". We assume that RabbitMQ contains a queue with the routing_key's name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRouting_Key (String Routing_Key);

	/**
	 * Get Routing-Key.
	 * Messages are send to the default exchange with the given "routing_key". We assume that RabbitMQ contains a queue with the routing_key's name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getRouting_Key();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Routing_Key = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Routing_Key", null);
	String COLUMNNAME_Routing_Key = "Routing_Key";

	/**
	 * Set AuthToken.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthToken(String AuthToken);

	/**
	 * Get AuthToken.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getAuthToken();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_AuthToken = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "AuthToken", null);
	String COLUMNNAME_AuthToken = "AuthToken";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_RabbitMQ_HTTP, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_RabbitMQ_HTTP.class, "Updated", null);
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
