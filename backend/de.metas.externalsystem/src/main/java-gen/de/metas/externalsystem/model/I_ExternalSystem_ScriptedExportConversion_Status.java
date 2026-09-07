package de.metas.externalsystem.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_ScriptedExportConversion_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_ScriptedExportConversion_Status 
{

	String Table_Name = "ExternalSystem_ScriptedExportConversion_Status";

//	/** AD_Table_ID=542617 */
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
	 * Set Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_PInstance_ID (int AD_PInstance_ID);

	/**
	 * Get Process Instance.
	 * Instance of a Process
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_PInstance_ID();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "Created", null);
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
	 * Set Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportStatus (java.lang.String ExportStatus);

	/**
	 * Get Export Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getExportStatus();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_ExportStatus = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "ExportStatus", null);
	String COLUMNNAME_ExportStatus = "ExportStatus";

	/**
	 * Set ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ScriptedExportConversion_ID (int ExternalSystem_Config_ScriptedExportConversion_ID);

	/**
	 * Get ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ScriptedExportConversion_ID();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion> COLUMN_ExternalSystem_Config_ScriptedExportConversion_ID = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "ExternalSystem_Config_ScriptedExportConversion_ID", de.metas.externalsystem.model.I_ExternalSystem_Config_ScriptedExportConversion.class);
	String COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID = "ExternalSystem_Config_ScriptedExportConversion_ID";

	/**
	 * Set ExternalSystem Scripted Export Conversion Status.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_ScriptedExportConversion_Status_ID (int ExternalSystem_ScriptedExportConversion_Status_ID);

	/**
	 * Get ExternalSystem Scripted Export Conversion Status.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_ScriptedExportConversion_Status_ID();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_ExternalSystem_ScriptedExportConversion_Status_ID = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "ExternalSystem_ScriptedExportConversion_Status_ID", null);
	String COLUMNNAME_ExternalSystem_ScriptedExportConversion_Status_ID = "ExternalSystem_ScriptedExportConversion_Status_ID";

	/**
	 * Set HTTP Response Code.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHttpResponseCode (int HttpResponseCode);

	/**
	 * Get HTTP Response Code.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getHttpResponseCode();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_HttpResponseCode = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "HttpResponseCode", null);
	String COLUMNNAME_HttpResponseCode = "HttpResponseCode";

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

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Resend.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsResend (boolean IsResend);

	/**
	 * Get Resend.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isResend();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_IsResend = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "IsResend", null);
	String COLUMNNAME_IsResend = "IsResend";

	/**
	 * Set Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRecord_ID (int Record_ID);

	/**
	 * Get Record ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getRecord_ID();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_Record_ID = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Set Status Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setStatusMessage (@Nullable java.lang.String StatusMessage);

	/**
	 * Get Status Message.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getStatusMessage();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_StatusMessage = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "StatusMessage", null);
	String COLUMNNAME_StatusMessage = "StatusMessage";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_ScriptedExportConversion_Status, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_ScriptedExportConversion_Status.class, "Updated", null);
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
