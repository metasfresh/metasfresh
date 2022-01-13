package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for API_Response_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_API_Response_Audit 
{

	String Table_Name = "API_Response_Audit";

//	/** AD_Table_ID=541640 */
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
	 * Set API Request Audit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Request_Audit_ID (int API_Request_Audit_ID);

	/**
	 * Get API Request Audit.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Request_Audit_ID();

	org.compiere.model.I_API_Request_Audit getAPI_Request_Audit();

	void setAPI_Request_Audit(org.compiere.model.I_API_Request_Audit API_Request_Audit);

	ModelColumn<I_API_Response_Audit, org.compiere.model.I_API_Request_Audit> COLUMN_API_Request_Audit_ID = new ModelColumn<>(I_API_Response_Audit.class, "API_Request_Audit_ID", org.compiere.model.I_API_Request_Audit.class);
	String COLUMNNAME_API_Request_Audit_ID = "API_Request_Audit_ID";

	/**
	 * Set API Response Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Response_Audit_ID (int API_Response_Audit_ID);

	/**
	 * Get API Response Audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Response_Audit_ID();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_API_Response_Audit_ID = new ModelColumn<>(I_API_Response_Audit.class, "API_Response_Audit_ID", null);
	String COLUMNNAME_API_Response_Audit_ID = "API_Response_Audit_ID";

	/**
	 * Set Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setBody (@Nullable java.lang.String Body);

	/**
	 * Get Body.
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getBody();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_Body = new ModelColumn<>(I_API_Response_Audit.class, "Body", null);
	String COLUMNNAME_Body = "Body";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_Created = new ModelColumn<>(I_API_Response_Audit.class, "Created", null);
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
	 * Set HTTP code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHttpCode (@Nullable java.lang.String HttpCode);

	/**
	 * Get HTTP code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHttpCode();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_HttpCode = new ModelColumn<>(I_API_Response_Audit.class, "HttpCode", null);
	String COLUMNNAME_HttpCode = "HttpCode";

	/**
	 * Set Http headers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHttpHeaders (@Nullable java.lang.String HttpHeaders);

	/**
	 * Get Http headers.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHttpHeaders();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_HttpHeaders = new ModelColumn<>(I_API_Response_Audit.class, "HttpHeaders", null);
	String COLUMNNAME_HttpHeaders = "HttpHeaders";

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

	ModelColumn<I_API_Response_Audit, Object> COLUMN_IsActive = new ModelColumn<>(I_API_Response_Audit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTime (@Nullable java.sql.Timestamp Time);

	/**
	 * Get Time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.sql.Timestamp getTime();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_Time = new ModelColumn<>(I_API_Response_Audit.class, "Time", null);
	String COLUMNNAME_Time = "Time";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_API_Response_Audit, Object> COLUMN_Updated = new ModelColumn<>(I_API_Response_Audit.class, "Updated", null);
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
