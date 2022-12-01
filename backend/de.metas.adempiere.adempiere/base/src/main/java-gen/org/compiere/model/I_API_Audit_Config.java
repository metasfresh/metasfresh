package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for API_Audit_Config
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_API_Audit_Config 
{

	String Table_Name = "API_Audit_Config";

//	/** AD_Table_ID=541635 */
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
	 * Set User group in charge.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_UserGroup_InCharge_ID (int AD_UserGroup_InCharge_ID);

	/**
	 * Get User group in charge.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_UserGroup_InCharge_ID();

	@Nullable org.compiere.model.I_AD_UserGroup getAD_UserGroup_InCharge();

	void setAD_UserGroup_InCharge(@Nullable org.compiere.model.I_AD_UserGroup AD_UserGroup_InCharge);

	ModelColumn<I_API_Audit_Config, org.compiere.model.I_AD_UserGroup> COLUMN_AD_UserGroup_InCharge_ID = new ModelColumn<>(I_API_Audit_Config.class, "AD_UserGroup_InCharge_ID", org.compiere.model.I_AD_UserGroup.class);
	String COLUMNNAME_AD_UserGroup_InCharge_ID = "AD_UserGroup_InCharge_ID";

	/**
	 * Set API Audit Config.
	 * API audit config that matched the request and lead to the creation of this audit record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Audit_Config_ID (int API_Audit_Config_ID);

	/**
	 * Get API Audit Config.
	 * API audit config that matched the request and lead to the creation of this audit record
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Audit_Config_ID();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_API_Audit_Config_ID = new ModelColumn<>(I_API_Audit_Config.class, "API_Audit_Config_ID", null);
	String COLUMNNAME_API_Audit_Config_ID = "API_Audit_Config_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_Created = new ModelColumn<>(I_API_Audit_Config.class, "Created", null);
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

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsActive = new ModelColumn<>(I_API_Audit_Config.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Bypass Audit.
	 * Completelly bypass audit system. Useful when dealing with non-json endpoints like images.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsBypassAudit (boolean IsBypassAudit);

	/**
	 * Get Bypass Audit.
	 * Completelly bypass audit system. Useful when dealing with non-json endpoints like images.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isBypassAudit();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsBypassAudit = new ModelColumn<>(I_API_Audit_Config.class, "IsBypassAudit", null);
	String COLUMNNAME_IsBypassAudit = "IsBypassAudit";

	/**
	 * Set Force async processing.
	 * If ticked, the HTTP call will be processed asynchronously and the response will consist only of a "requestId".
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsForceProcessedAsync (boolean IsForceProcessedAsync);

	/**
	 * Get Force async processing.
	 * If ticked, the HTTP call will be processed asynchronously and the response will consist only of a "requestId".
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isForceProcessedAsync();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsForceProcessedAsync = new ModelColumn<>(I_API_Audit_Config.class, "IsForceProcessedAsync", null);
	String COLUMNNAME_IsForceProcessedAsync = "IsForceProcessedAsync";

	/**
	 * Set Synchronous audit.
	 * If false, then API Request and API Response records are written for the request, but in an asynchronous way, while the actual HTTP request might have already been performed. This implies better performance for the caller, but, no API Audit Log records will be created. Also note, that creating those audit records might fail without the API caller noticing it.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSynchronousAuditLoggingEnabled (boolean IsSynchronousAuditLoggingEnabled);

	/**
	 * Get Synchronous audit.
	 * If false, then API Request and API Response records are written for the request, but in an asynchronous way, while the actual HTTP request might have already been performed. This implies better performance for the caller, but, no API Audit Log records will be created. Also note, that creating those audit records might fail without the API caller noticing it.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSynchronousAuditLoggingEnabled();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsSynchronousAuditLoggingEnabled = new ModelColumn<>(I_API_Audit_Config.class, "IsSynchronousAuditLoggingEnabled", null);
	String COLUMNNAME_IsSynchronousAuditLoggingEnabled = "IsSynchronousAuditLoggingEnabled";

	/**
	 * Set Wrap response.
	 * If true, the actual API response will be wrapped into a standard V2 response JSON structure carrying the API Request Audit identifier. If false, the actual API response is returned "as is" and the API Request Audit identifier is reported in the response header XXXX.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsWrapApiResponse (boolean IsWrapApiResponse);

	/**
	 * Get Wrap response.
	 * If true, the actual API response will be wrapped into a standard V2 response JSON structure carrying the API Request Audit identifier. If false, the actual API response is returned "as is" and the API Request Audit identifier is reported in the response header XXXX.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isWrapApiResponse();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsWrapApiResponse = new ModelColumn<>(I_API_Audit_Config.class, "IsWrapApiResponse", null);
	String COLUMNNAME_IsWrapApiResponse = "IsWrapApiResponse";

	/**
	 * Set Days keep errored request audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKeepErroredRequestDays (int KeepErroredRequestDays);

	/**
	 * Get Days keep errored request audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getKeepErroredRequestDays();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_KeepErroredRequestDays = new ModelColumn<>(I_API_Audit_Config.class, "KeepErroredRequestDays", null);
	String COLUMNNAME_KeepErroredRequestDays = "KeepErroredRequestDays";

	/**
	 * Set Days keep request body.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKeepRequestBodyDays (int KeepRequestBodyDays);

	/**
	 * Get Days keep request body.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getKeepRequestBodyDays();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_KeepRequestBodyDays = new ModelColumn<>(I_API_Audit_Config.class, "KeepRequestBodyDays", null);
	String COLUMNNAME_KeepRequestBodyDays = "KeepRequestBodyDays";

	/**
	 * Set Days keep request audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKeepRequestDays (int KeepRequestDays);

	/**
	 * Get Days keep request audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getKeepRequestDays();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_KeepRequestDays = new ModelColumn<>(I_API_Audit_Config.class, "KeepRequestDays", null);
	String COLUMNNAME_KeepRequestDays = "KeepRequestDays";

	/**
	 * Set Days keep response body.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKeepResponseBodyDays (int KeepResponseBodyDays);

	/**
	 * Get Days keep response body.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getKeepResponseBodyDays();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_KeepResponseBodyDays = new ModelColumn<>(I_API_Audit_Config.class, "KeepResponseBodyDays", null);
	String COLUMNNAME_KeepResponseBodyDays = "KeepResponseBodyDays";

	/**
	 * Set Days keep response audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setKeepResponseDays (int KeepResponseDays);

	/**
	 * Get Days keep response audit.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getKeepResponseDays();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_KeepResponseDays = new ModelColumn<>(I_API_Audit_Config.class, "KeepResponseDays", null);
	String COLUMNNAME_KeepResponseDays = "KeepResponseDays";

	/**
	 * Set Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMethod (@Nullable java.lang.String Method);

	/**
	 * Get Method.
	 * HTTP method matched by this line. An empty value matches all methods.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMethod();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_Method = new ModelColumn<>(I_API_Audit_Config.class, "Method", null);
	String COLUMNNAME_Method = "Method";

	/**
	 * Set Notify on.
	 * Specifies if and when the responsible user shall be notified about API invocations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setNotifyUserInCharge (@Nullable java.lang.String NotifyUserInCharge);

	/**
	 * Get Notify on.
	 * Specifies if and when the responsible user shall be notified about API invocations
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getNotifyUserInCharge();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_NotifyUserInCharge = new ModelColumn<>(I_API_Audit_Config.class, "NotifyUserInCharge", null);
	String COLUMNNAME_NotifyUserInCharge = "NotifyUserInCharge";

	/**
	 * Set Path.
	 * Pattern of the request URL to be matched by this config. It can be any part of the actual URL, or it can be an Ant-style path pattern, see "spring AntPathMatcher"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPathPrefix (@Nullable java.lang.String PathPrefix);

	/**
	 * Get Path.
	 * Pattern of the request URL to be matched by this config. It can be any part of the actual URL, or it can be an Ant-style path pattern, see "spring AntPathMatcher"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPathPrefix();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_PathPrefix = new ModelColumn<>(I_API_Audit_Config.class, "PathPrefix", null);
	String COLUMNNAME_PathPrefix = "PathPrefix";

	/**
	 * Set SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_SeqNo = new ModelColumn<>(I_API_Audit_Config.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_Updated = new ModelColumn<>(I_API_Audit_Config.class, "Updated", null);
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
