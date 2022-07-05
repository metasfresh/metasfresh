package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ExternalSystem_Config_LeichMehl
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_Config_LeichMehl 
{

	String Table_Name = "ExternalSystem_Config_LeichMehl";

//	/** AD_Table_ID=542129 */
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
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "Created", null);
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

	de.metas.externalsystem.model.I_ExternalSystem_Config getExternalSystem_Config();

	void setExternalSystem_Config(de.metas.externalsystem.model.I_ExternalSystem_Config ExternalSystem_Config);

	ModelColumn<I_ExternalSystem_Config_LeichMehl, de.metas.externalsystem.model.I_ExternalSystem_Config> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystem_Config_ID", de.metas.externalsystem.model.I_ExternalSystem_Config.class);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

	/**
	 * Set ExternalSystem_Config_LeichMehl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_LeichMehl_ID (int ExternalSystem_Config_LeichMehl_ID);

	/**
	 * Get ExternalSystem_Config_LeichMehl.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_LeichMehl_ID();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_ExternalSystem_Config_LeichMehl_ID = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystem_Config_LeichMehl_ID", null);
	String COLUMNNAME_ExternalSystem_Config_LeichMehl_ID = "ExternalSystem_Config_LeichMehl_ID";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_ExternalSystemValue = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "ExternalSystemValue", null);
	String COLUMNNAME_ExternalSystemValue = "ExternalSystemValue";

	/**
	 * Set FTP Directory.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFTP_Directory (java.lang.String FTP_Directory);

	/**
	 * Get FTP Directory.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFTP_Directory();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_FTP_Directory = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "FTP_Directory", null);
	String COLUMNNAME_FTP_Directory = "FTP_Directory";

	/**
	 * Set FTP Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFTP_Hostname (java.lang.String FTP_Hostname);

	/**
	 * Get FTP Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFTP_Hostname();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_FTP_Hostname = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "FTP_Hostname", null);
	String COLUMNNAME_FTP_Hostname = "FTP_Hostname";

	/**
	 * Set FTP Password.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFTP_Password (java.lang.String FTP_Password);

	/**
	 * Get FTP Password.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFTP_Password();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_FTP_Password = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "FTP_Password", null);
	String COLUMNNAME_FTP_Password = "FTP_Password";

	/**
	 * Set FTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFTP_Port (int FTP_Port);

	/**
	 * Get FTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getFTP_Port();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_FTP_Port = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "FTP_Port", null);
	String COLUMNNAME_FTP_Port = "FTP_Port";

	/**
	 * Set FTP Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFTP_Username (java.lang.String FTP_Username);

	/**
	 * Get FTP Username.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFTP_Username();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_FTP_Username = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "FTP_Username", null);
	String COLUMNNAME_FTP_Username = "FTP_Username";

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

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_Config_LeichMehl, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_Config_LeichMehl.class, "Updated", null);
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
