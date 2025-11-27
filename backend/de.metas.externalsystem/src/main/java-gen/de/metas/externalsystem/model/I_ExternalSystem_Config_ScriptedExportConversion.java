package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_Config_ScriptedExportConversion
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_ScriptedExportConversion 
{

	String Table_Name = "ExternalSystem_Config_ScriptedExportConversion";

//	/** AD_Table_ID=542541 */
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
	 * Set Process for outbound data.
	 * Process that generates a text file for a data record (e.g. order), which can then be converted using a script and sent to an external system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Process_OutboundData_ID (int AD_Process_OutboundData_ID);

	/**
	 * Get Process for outbound data.
	 * Process that generates a text file for a data record (e.g. order), which can then be converted using a script and sent to an external system.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Process_OutboundData_ID();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, org.compiere.model.I_AD_Process> COLUMN_AD_Process_OutboundData_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "AD_Process_OutboundData_ID", org.compiere.model.I_AD_Process.class);
	String COLUMNNAME_AD_Process_OutboundData_ID = "AD_Process_OutboundData_ID";

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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "Created", null);
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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_Description = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocBaseType (@Nullable String DocBaseType);

	/**
	 * Get Document Base Type.
	 * Logical type of document
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getDocBaseType();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_DocBaseType = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "DocBaseType", null);
	String COLUMNNAME_DocBaseType = "DocBaseType";

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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "ExternalSystem_Config_ID", I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ScriptedExportConversion_ID (int ExternalSystem_Config_ScriptedExportConversion_ID);

	/**
	 * Get ExternalSystem_Config_ScriptedExportConversion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ScriptedExportConversion_ID();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_ExternalSystem_Config_ScriptedExportConversion_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "ExternalSystem_Config_ScriptedExportConversion_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ScriptedExportConversion_ID = "ExternalSystem_Config_ScriptedExportConversion_ID";

	/**
	 * Set Externer System-Ausgangsendpunkt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Outbound_Endpoint_ID (int ExternalSystem_Outbound_Endpoint_ID);

	/**
	 * Get Externer System-Ausgangsendpunkt.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Outbound_Endpoint_ID();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, I_ExternalSystem_Outbound_Endpoint> COLUMN_ExternalSystem_Outbound_Endpoint_ID = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "ExternalSystem_Outbound_Endpoint_ID", I_ExternalSystem_Outbound_Endpoint.class);
	String COLUMNNAME_ExternalSystem_Outbound_Endpoint_ID = "ExternalSystem_Outbound_Endpoint_ID";

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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "ExternalSystemValue", null);
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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Script Identifier.
	 * Name of the JavaScript-file that shall be executed by External Systems
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setScriptIdentifier (String ScriptIdentifier);

	/**
	 * Get Script Identifier.
	 * Name of the JavaScript-file that shall be executed by External Systems
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getScriptIdentifier();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_ScriptIdentifier = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "ScriptIdentifier", null);
	String COLUMNNAME_ScriptIdentifier = "ScriptIdentifier";

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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_SeqNo = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "SeqNo", null);
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

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "Updated", null);
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
	 * Set SQL WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWhereClause (String WhereClause);

	/**
	 * Get SQL WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	String getWhereClause();

	ModelColumn<I_ExternalSystem_Config_ScriptedExportConversion, Object> COLUMN_WhereClause = new ModelColumn<>(I_ExternalSystem_Config_ScriptedExportConversion.class, "WhereClause", null);
	String COLUMNNAME_WhereClause = "WhereClause";
}
