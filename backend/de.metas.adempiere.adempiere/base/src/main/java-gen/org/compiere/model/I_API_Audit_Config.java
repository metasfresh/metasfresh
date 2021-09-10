package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

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
	 * Set Invoker waits.
	 * If checked the client gets the actual endpoint response as soon as the endpoint is ready. Otherwise, the client directly gets a response with HTTP code 202 and the api audit record's ID.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsInvokerWaitsForResult (boolean IsInvokerWaitsForResult);

	/**
	 * Get Invoker waits.
	 * If checked the client gets the actual endpoint response as soon as the endpoint is ready. Otherwise, the client directly gets a response with HTTP code 202 and the api audit record's ID.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isInvokerWaitsForResult();

	ModelColumn<I_API_Audit_Config, Object> COLUMN_IsInvokerWaitsForResult = new ModelColumn<>(I_API_Audit_Config.class, "IsInvokerWaitsForResult", null);
	String COLUMNNAME_IsInvokerWaitsForResult = "IsInvokerWaitsForResult";

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
	 * Set Path prefix.
	 * Begin of the URL-path matched by this line. An empty value matches all paths.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPathPrefix (@Nullable java.lang.String PathPrefix);

	/**
	 * Get Path prefix.
	 * Begin of the URL-path matched by this line. An empty value matches all paths.
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
