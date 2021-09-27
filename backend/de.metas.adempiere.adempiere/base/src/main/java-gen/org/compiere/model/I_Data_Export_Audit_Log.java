package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for Data_Export_Audit_Log
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_Data_Export_Audit_Log 
{

	String Table_Name = "Data_Export_Audit_Log";

//	/** AD_Table_ID=541805 */
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

	ModelColumn<I_Data_Export_Audit_Log, org.compiere.model.I_AD_PInstance> COLUMN_AD_PInstance_ID = new ModelColumn<>(I_Data_Export_Audit_Log.class, "AD_PInstance_ID", org.compiere.model.I_AD_PInstance.class);
	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_Created = new ModelColumn<>(I_Data_Export_Audit_Log.class, "Created", null);
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
	 * Set Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setData_Export_Action (java.lang.String Data_Export_Action);

	/**
	 * Get Action.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getData_Export_Action();

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_Data_Export_Action = new ModelColumn<>(I_Data_Export_Audit_Log.class, "Data_Export_Action", null);
	String COLUMNNAME_Data_Export_Action = "Data_Export_Action";

	/**
	 * Set Exported Data Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setData_Export_Audit_ID (int Data_Export_Audit_ID);

	/**
	 * Get Exported Data Audit.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getData_Export_Audit_ID();

	org.compiere.model.I_Data_Export_Audit getData_Export_Audit();

	void setData_Export_Audit(org.compiere.model.I_Data_Export_Audit Data_Export_Audit);

	ModelColumn<I_Data_Export_Audit_Log, org.compiere.model.I_Data_Export_Audit> COLUMN_Data_Export_Audit_ID = new ModelColumn<>(I_Data_Export_Audit_Log.class, "Data_Export_Audit_ID", org.compiere.model.I_Data_Export_Audit.class);
	String COLUMNNAME_Data_Export_Audit_ID = "Data_Export_Audit_ID";

	/**
	 * Set Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setData_Export_Audit_Log_ID (int Data_Export_Audit_Log_ID);

	/**
	 * Get Log.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getData_Export_Audit_Log_ID();

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_Data_Export_Audit_Log_ID = new ModelColumn<>(I_Data_Export_Audit_Log.class, "Data_Export_Audit_Log_ID", null);
	String COLUMNNAME_Data_Export_Audit_Log_ID = "Data_Export_Audit_Log_ID";

	/**
	 * Set External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setExternalSystem_Config_ID (int ExternalSystem_Config_ID);

	/**
	 * Get External System Config.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getExternalSystem_Config_ID();

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_ExternalSystem_Config_ID = new ModelColumn<>(I_Data_Export_Audit_Log.class, "ExternalSystem_Config_ID", null);
	String COLUMNNAME_ExternalSystem_Config_ID = "ExternalSystem_Config_ID";

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

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_IsActive = new ModelColumn<>(I_Data_Export_Audit_Log.class, "IsActive", null);
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

	ModelColumn<I_Data_Export_Audit_Log, Object> COLUMN_Updated = new ModelColumn<>(I_Data_Export_Audit_Log.class, "Updated", null);
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
