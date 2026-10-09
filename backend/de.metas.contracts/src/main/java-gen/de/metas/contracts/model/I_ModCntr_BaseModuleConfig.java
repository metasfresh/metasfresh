package de.metas.contracts.model;

import org.adempiere.model.ModelColumn;

/** Generated Interface for ModCntr_BaseModuleConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_ModCntr_BaseModuleConfig 
{

	String Table_Name = "ModCntr_BaseModuleConfig";

//	/** AD_Table_ID=542477 */
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

	ModelColumn<I_ModCntr_BaseModuleConfig, Object> COLUMN_Created = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "Created", null);
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

	ModelColumn<I_ModCntr_BaseModuleConfig, Object> COLUMN_IsActive = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Basisbaustein Konfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_BaseModuleConfig_ID (int ModCntr_BaseModuleConfig_ID);

	/**
	 * Get Basisbaustein Konfig.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_BaseModuleConfig_ID();

	ModelColumn<I_ModCntr_BaseModuleConfig, Object> COLUMN_ModCntr_BaseModuleConfig_ID = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "ModCntr_BaseModuleConfig_ID", null);
	String COLUMNNAME_ModCntr_BaseModuleConfig_ID = "ModCntr_BaseModuleConfig_ID";

	/**
	 * Set Base Modules.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_BaseModule_ID (int ModCntr_BaseModule_ID);

	/**
	 * Get Base Modules.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_BaseModule_ID();

	de.metas.contracts.model.I_ModCntr_Module getModCntr_BaseModule();

	void setModCntr_BaseModule(de.metas.contracts.model.I_ModCntr_Module ModCntr_BaseModule);

	ModelColumn<I_ModCntr_BaseModuleConfig, de.metas.contracts.model.I_ModCntr_Module> COLUMN_ModCntr_BaseModule_ID = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "ModCntr_BaseModule_ID", de.metas.contracts.model.I_ModCntr_Module.class);
	String COLUMNNAME_ModCntr_BaseModule_ID = "ModCntr_BaseModule_ID";

	/**
	 * Set Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Module_ID (int ModCntr_Module_ID);

	/**
	 * Get Modules.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Module_ID();

	de.metas.contracts.model.I_ModCntr_Module getModCntr_Module();

	void setModCntr_Module(de.metas.contracts.model.I_ModCntr_Module ModCntr_Module);

	ModelColumn<I_ModCntr_BaseModuleConfig, de.metas.contracts.model.I_ModCntr_Module> COLUMN_ModCntr_Module_ID = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "ModCntr_Module_ID", de.metas.contracts.model.I_ModCntr_Module.class);
	String COLUMNNAME_ModCntr_Module_ID = "ModCntr_Module_ID";

	/**
	 * Set Modular Contract Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setModCntr_Settings_ID (int ModCntr_Settings_ID);

	/**
	 * Get Modular Contract Settings.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getModCntr_Settings_ID();

	de.metas.contracts.model.I_ModCntr_Settings getModCntr_Settings();

	void setModCntr_Settings(de.metas.contracts.model.I_ModCntr_Settings ModCntr_Settings);

	ModelColumn<I_ModCntr_BaseModuleConfig, de.metas.contracts.model.I_ModCntr_Settings> COLUMN_ModCntr_Settings_ID = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "ModCntr_Settings_ID", de.metas.contracts.model.I_ModCntr_Settings.class);
	String COLUMNNAME_ModCntr_Settings_ID = "ModCntr_Settings_ID";

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

	ModelColumn<I_ModCntr_BaseModuleConfig, Object> COLUMN_Name = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_ModCntr_BaseModuleConfig, Object> COLUMN_Updated = new ModelColumn<>(I_ModCntr_BaseModuleConfig.class, "Updated", null);
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
