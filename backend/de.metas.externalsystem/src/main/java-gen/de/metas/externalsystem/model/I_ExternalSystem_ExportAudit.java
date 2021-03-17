package de.metas.externalsystem.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for ExternalSystem_ExportAudit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ExternalSystem_ExportAudit 
{

	String Table_Name = "ExternalSystem_ExportAudit";

//	/** AD_Table_ID=541603 */
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

	@Nullable org.compiere.model.I_AD_PInstance getAD_PInstance();

	void setAD_PInstance(@Nullable org.compiere.model.I_AD_PInstance AD_PInstance);

	ModelColumn<I_ExternalSystem_ExportAudit, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: TableDir
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

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_Created = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "Created", null);
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
	 * Set Export parameters.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExportParameters (@Nullable String ExportParameters);

	/**
	 * Get Export parameters.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExportParameters();

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_ExportParameters = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "ExportParameters", null);
	String COLUMNNAME_ExportParameters = "ExportParameters";

	/**
	 * Set Export role.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportRole_ID (int ExportRole_ID);

	/**
	 * Get Export role.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExportRole_ID();

	org.compiere.model.I_AD_Role getExportRole();

	void setExportRole(org.compiere.model.I_AD_Role ExportRole);

	ModelColumn<I_ExternalSystem_ExportAudit, org.compiere.model.I_AD_Role> COLUMN_ExportRole_ID = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "ExportRole_ID", org.compiere.model.I_AD_Role.class);
	String COLUMNNAME_ExportRole_ID = "ExportRole_ID";

	/**
	 * Set Export time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportTime (java.sql.Timestamp ExportTime);

	/**
	 * Get Export time.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getExportTime();

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_ExportTime = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "ExportTime", null);
	String COLUMNNAME_ExportTime = "ExportTime";

	/**
	 * Set Export user.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExportUser_ID (int ExportUser_ID);

	/**
	 * Get Export user.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExportUser_ID();

	String COLUMNNAME_ExportUser_ID = "ExportUser_ID";

	/**
	 * Set Export to External system audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_ExportAudit_ID (int ExternalSystem_ExportAudit_ID);

	/**
	 * Get Export to External system audit.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_ExportAudit_ID();

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_ExternalSystem_ExportAudit_ID = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "ExternalSystem_ExportAudit_ID", null);
	String COLUMNNAME_ExternalSystem_ExportAudit_ID = "ExternalSystem_ExportAudit_ID";

	/**
	 * Set External system type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystemType (@Nullable String ExternalSystemType);

	/**
	 * Get External system type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable String getExternalSystemType();

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_ExternalSystemType = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "ExternalSystemType", null);
	String COLUMNNAME_ExternalSystemType = "ExternalSystemType";

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

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_IsActive = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

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

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_Record_ID = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "Record_ID", null);
	String COLUMNNAME_Record_ID = "Record_ID";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ExternalSystem_ExportAudit, Object> COLUMN_Updated = new ModelColumn<>(I_ExternalSystem_ExportAudit.class, "Updated", null);
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
