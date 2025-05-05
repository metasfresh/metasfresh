package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_MigrationScript
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_MigrationScript 
{

	String Table_Name = "AD_MigrationScript";

//	/** AD_Table_ID=53064 */
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
	 * Set Migration Script.
	 * Table to check whether the migration script has been applied
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_MigrationScript_ID (int AD_MigrationScript_ID);

	/**
	 * Get Migration Script.
	 * Table to check whether the migration script has been applied
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_MigrationScript_ID();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_AD_MigrationScript_ID = new ModelColumn<>(I_AD_MigrationScript.class, "AD_MigrationScript_ID", null);
	String COLUMNNAME_AD_MigrationScript_ID = "AD_MigrationScript_ID";

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

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Created = new ModelColumn<>(I_AD_MigrationScript.class, "Created", null);
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
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Description = new ModelColumn<>(I_AD_MigrationScript.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Developer Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDeveloperName (@Nullable java.lang.String DeveloperName);

	/**
	 * Get Developer Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDeveloperName();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_DeveloperName = new ModelColumn<>(I_AD_MigrationScript.class, "DeveloperName", null);
	String COLUMNNAME_DeveloperName = "DeveloperName";

	/**
	 * Set File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: FileName
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setFileName (java.lang.String FileName);

	/**
	 * Get File Name.
	 * Name of the local file or URL
	 *
	 * <br>Type: FileName
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getFileName();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_FileName = new ModelColumn<>(I_AD_MigrationScript.class, "FileName", null);
	String COLUMNNAME_FileName = "FileName";

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

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_MigrationScript.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Apply Script.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsApply (boolean IsApply);

	/**
	 * Get Apply Script.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isApply();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_IsApply = new ModelColumn<>(I_AD_MigrationScript.class, "IsApply", null);
	String COLUMNNAME_IsApply = "IsApply";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Name = new ModelColumn<>(I_AD_MigrationScript.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Project.
	 * Name of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProjectName (java.lang.String ProjectName);

	/**
	 * Get Project.
	 * Name of the Project
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getProjectName();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_ProjectName = new ModelColumn<>(I_AD_MigrationScript.class, "ProjectName", null);
	String COLUMNNAME_ProjectName = "ProjectName";

	/**
	 * Set Reference.
	 * Reference for this record
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setReference (@Nullable java.lang.String Reference);

	/**
	 * Get Reference.
	 * Reference for this record
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getReference();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Reference = new ModelColumn<>(I_AD_MigrationScript.class, "Reference", null);
	String COLUMNNAME_Reference = "Reference";

	/**
	 * Set Release No.
	 * Internal Release Number
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReleaseNo (java.lang.String ReleaseNo);

	/**
	 * Get Release No.
	 * Internal Release Number
	 *
	 * <br>Type: Text
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReleaseNo();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_ReleaseNo = new ModelColumn<>(I_AD_MigrationScript.class, "ReleaseNo", null);
	String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/**
	 * Set Script.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScript (@Nullable byte[] Script);

	/**
	 * Get Script.
	 * Dynamic Java Language Script to calculate result
	 *
	 * <br>Type: Binary
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable byte[] getScript();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Script = new ModelColumn<>(I_AD_MigrationScript.class, "Script", null);
	String COLUMNNAME_Script = "Script";

	/**
	 * Set Apply Migration Scripts.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setScriptRoll (@Nullable java.lang.String ScriptRoll);

	/**
	 * Get Apply Migration Scripts.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getScriptRoll();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_ScriptRoll = new ModelColumn<>(I_AD_MigrationScript.class, "ScriptRoll", null);
	String COLUMNNAME_ScriptRoll = "ScriptRoll";

	/**
	 * Set Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStatus (java.lang.String Status);

	/**
	 * Get Status.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getStatus();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Status = new ModelColumn<>(I_AD_MigrationScript.class, "Status", null);
	String COLUMNNAME_Status = "Status";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_Updated = new ModelColumn<>(I_AD_MigrationScript.class, "Updated", null);
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
	 * Set URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setURL (@Nullable java.lang.String URL);

	/**
	 * Get URL.
	 * Full URL address - e.g. https://www.metasfresh.com
	 *
	 * <br>Type: URL
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getURL();

	ModelColumn<I_AD_MigrationScript, Object> COLUMN_URL = new ModelColumn<>(I_AD_MigrationScript.class, "URL", null);
	String COLUMNNAME_URL = "URL";
}
