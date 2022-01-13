package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for API_Request_Audit_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_API_Request_Audit_Log 
{

	String Table_Name = "API_Request_Audit_Log";

//	/** AD_Table_ID=541639 */
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
	 * Set Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Issue_ID (int AD_Issue_ID);

	/**
	 * Get Issues.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Issue_ID();

	String COLUMNNAME_AD_Issue_ID = "AD_Issue_ID";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set API Request Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Request_Audit_ID (int API_Request_Audit_ID);

	/**
	 * Get API Request Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Request_Audit_ID();

	org.compiere.model.I_API_Request_Audit getAPI_Request_Audit();

	void setAPI_Request_Audit(org.compiere.model.I_API_Request_Audit API_Request_Audit);

	ModelColumn<I_API_Request_Audit_Log, org.compiere.model.I_API_Request_Audit> COLUMN_API_Request_Audit_ID = new ModelColumn<>(I_API_Request_Audit_Log.class, "API_Request_Audit_ID", org.compiere.model.I_API_Request_Audit.class);
	String COLUMNNAME_API_Request_Audit_ID = "API_Request_Audit_ID";

	/**
	 * Set Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAPI_Request_Audit_Log_ID (int API_Request_Audit_Log_ID);

	/**
	 * Get Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAPI_Request_Audit_Log_ID();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_API_Request_Audit_Log_ID = new ModelColumn<>(I_API_Request_Audit_Log.class, "API_Request_Audit_Log_ID", null);
	String COLUMNNAME_API_Request_Audit_Log_ID = "API_Request_Audit_Log_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_Created = new ModelColumn<>(I_API_Request_Audit_Log.class, "Created", null);
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

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_API_Request_Audit_Log.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Message Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLogmessage (@Nullable java.lang.String Logmessage);

	/**
	 * Get Message Text.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getLogmessage();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_Logmessage = new ModelColumn<>(I_API_Request_Audit_Log.class, "Logmessage", null);
	String COLUMNNAME_Logmessage = "Logmessage";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: ID
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_Record_ID = new ModelColumn<>(I_API_Request_Audit_Log.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Transaction.
	 * Name of the transaction
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTrxName (@Nullable java.lang.String TrxName);

	/**
	 * Get Transaction.
	 * Name of the transaction
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTrxName();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_TrxName = new ModelColumn<>(I_API_Request_Audit_Log.class, "TrxName", null);
	String COLUMNNAME_TrxName = "TrxName";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setType (@Nullable java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getType();

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_Type = new ModelColumn<>(I_API_Request_Audit_Log.class, "Type", null);
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

	ModelColumn<I_API_Request_Audit_Log, Object> COLUMN_Updated = new ModelColumn<>(I_API_Request_Audit_Log.class, "Updated", null);
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
