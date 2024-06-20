package org.compiere.model;

import org.adempiere.model.ModelColumn;

import javax.annotation.Nullable;

/** Generated Interface for AD_Table
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Table 
{

	String Table_Name = "AD_Table";

//	/** AD_Table_ID=100 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Data Access Level.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Data Access Level.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAccessLevel();

	ModelColumn<I_AD_Table, Object> COLUMN_AccessLevel = new ModelColumn<>(I_AD_Table.class, "AccessLevel", null);
	String COLUMNNAME_AccessLevel = "AccessLevel";

	/**
	 * Set Auto Complete Min Length.
	 * Identifier autocomplete trigger length
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setACTriggerLength (int ACTriggerLength);

	/**
	 * Get Auto Complete Min Length.
	 * Identifier autocomplete trigger length
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getACTriggerLength();

	ModelColumn<I_AD_Table, Object> COLUMN_ACTriggerLength = new ModelColumn<>(I_AD_Table.class, "ACTriggerLength", null);
	String COLUMNNAME_ACTriggerLength = "ACTriggerLength";

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
	 * Set Table.
	 * Database Table information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get Table.
	 * Database Table information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Table_ID();

	ModelColumn<I_AD_Table, Object> COLUMN_AD_Table_ID = new ModelColumn<>(I_AD_Table.class, "AD_Table_ID", null);
	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Validation Rule.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	void setAD_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

	ModelColumn<I_AD_Table, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_AD_Table.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Window.
	 * Data entry or display window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getAD_Window();

	void setAD_Window(@Nullable org.compiere.model.I_AD_Window AD_Window);

	ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new ModelColumn<>(I_AD_Table.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Cloning Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCloningEnabled (java.lang.String CloningEnabled);

	/**
	 * Get Cloning Enabled.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCloningEnabled();

	ModelColumn<I_AD_Table, Object> COLUMN_CloningEnabled = new ModelColumn<>(I_AD_Table.class, "CloningEnabled", null);
	String COLUMNNAME_CloningEnabled = "CloningEnabled";

	/**
	 * Set Copy Columns from Table.
	 * Create Dictionary Columns for a Table taking another as base
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setCopyColumnsFromTable (@Nullable java.lang.String CopyColumnsFromTable);

	/**
	 * Get Copy Columns from Table.
	 * Create Dictionary Columns for a Table taking another as base
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getCopyColumnsFromTable();

	ModelColumn<I_AD_Table, Object> COLUMN_CopyColumnsFromTable = new ModelColumn<>(I_AD_Table.class, "CopyColumnsFromTable", null);
	String COLUMNNAME_CopyColumnsFromTable = "CopyColumnsFromTable";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Table, Object> COLUMN_Created = new ModelColumn<>(I_AD_Table.class, "Created", null);
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
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Table, Object> COLUMN_Description = new ModelColumn<>(I_AD_Table.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Downline Cloning Strategy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDownlineCloningStrategy (java.lang.String DownlineCloningStrategy);

	/**
	 * Get Downline Cloning Strategy.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDownlineCloningStrategy();

	ModelColumn<I_AD_Table, Object> COLUMN_DownlineCloningStrategy = new ModelColumn<>(I_AD_Table.class, "DownlineCloningStrategy", null);
	String COLUMNNAME_DownlineCloningStrategy = "DownlineCloningStrategy";

	/**
	 * Set Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getEntityType();

	ModelColumn<I_AD_Table, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Table.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Table, Object> COLUMN_Help = new ModelColumn<>(I_AD_Table.class, "Help", null);
	String COLUMNNAME_Help = "Help";

	/**
	 * Set Create Columns from DB.
	 * Create Dictionary Columns of Table not existing as a Column but in the Database
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setImportTable (@Nullable java.lang.String ImportTable);

	/**
	 * Get Create Columns from DB.
	 * Create Dictionary Columns of Table not existing as a Column but in the Database
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getImportTable();

	ModelColumn<I_AD_Table, Object> COLUMN_ImportTable = new ModelColumn<>(I_AD_Table.class, "ImportTable", null);
	String COLUMNNAME_ImportTable = "ImportTable";

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

	ModelColumn<I_AD_Table, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Table.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsAutocomplete (boolean IsAutocomplete);

	/**
	 * Get Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isAutocomplete();

	ModelColumn<I_AD_Table, Object> COLUMN_IsAutocomplete = new ModelColumn<>(I_AD_Table.class, "IsAutocomplete", null);
	String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Maintain Change Log.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsChangeLog (boolean IsChangeLog);

	/**
	 * Get Maintain Change Log.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isChangeLog();

	ModelColumn<I_AD_Table, Object> COLUMN_IsChangeLog = new ModelColumn<>(I_AD_Table.class, "IsChangeLog", null);
	String COLUMNNAME_IsChangeLog = "IsChangeLog";

	/**
	 * Set Records deletable.
	 * Indicates if records can be deleted from the database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsDeleteable (boolean IsDeleteable);

	/**
	 * Get Records deletable.
	 * Indicates if records can be deleted from the database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isDeleteable();

	ModelColumn<I_AD_Table, Object> COLUMN_IsDeleteable = new ModelColumn<>(I_AD_Table.class, "IsDeleteable", null);
	String COLUMNNAME_IsDeleteable = "IsDeleteable";

	/**
	 * Set Enable remote cache invalidation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsEnableRemoteCacheInvalidation (boolean IsEnableRemoteCacheInvalidation);

	/**
	 * Get Enable remote cache invalidation.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isEnableRemoteCacheInvalidation();

	ModelColumn<I_AD_Table, Object> COLUMN_IsEnableRemoteCacheInvalidation = new ModelColumn<>(I_AD_Table.class, "IsEnableRemoteCacheInvalidation", null);
	String COLUMNNAME_IsEnableRemoteCacheInvalidation = "IsEnableRemoteCacheInvalidation";

	/**
	 * Set High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsHighVolume (boolean IsHighVolume);

	/**
	 * Get High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isHighVolume();

	ModelColumn<I_AD_Table, Object> COLUMN_IsHighVolume = new ModelColumn<>(I_AD_Table.class, "IsHighVolume", null);
	String COLUMNNAME_IsHighVolume = "IsHighVolume";

	/**
	 * Set Security enabled.
	 * If security is enabled, user access to data can be restricted via Roles
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSecurityEnabled (boolean IsSecurityEnabled);

	/**
	 * Get Security enabled.
	 * If security is enabled, user access to data can be restricted via Roles
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSecurityEnabled();

	ModelColumn<I_AD_Table, Object> COLUMN_IsSecurityEnabled = new ModelColumn<>(I_AD_Table.class, "IsSecurityEnabled", null);
	String COLUMNNAME_IsSecurityEnabled = "IsSecurityEnabled";

	/**
	 * Set View.
	 * This is a view
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsView (boolean IsView);

	/**
	 * Get View.
	 * This is a view
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isView();

	ModelColumn<I_AD_Table, Object> COLUMN_IsView = new ModelColumn<>(I_AD_Table.class, "IsView", null);
	String COLUMNNAME_IsView = "IsView";

	/**
	 * Set Sequence.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setLoadSeq (int LoadSeq);

	/**
	 * Get Sequence.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getLoadSeq();

	ModelColumn<I_AD_Table, Object> COLUMN_LoadSeq = new ModelColumn<>(I_AD_Table.class, "LoadSeq", null);
	String COLUMNNAME_LoadSeq = "LoadSeq";

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

	ModelColumn<I_AD_Table, Object> COLUMN_Name = new ModelColumn<>(I_AD_Table.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Data protection category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPersonalDataCategory (java.lang.String PersonalDataCategory);

	/**
	 * Get Data protection category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPersonalDataCategory();

	ModelColumn<I_AD_Table, Object> COLUMN_PersonalDataCategory = new ModelColumn<>(I_AD_Table.class, "PersonalDataCategory", null);
	String COLUMNNAME_PersonalDataCategory = "PersonalDataCategory";

	/**
	 * Set PO Window.
	 * Purchase Order Window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPO_Window_ID (int PO_Window_ID);

	/**
	 * Get PO Window.
	 * Purchase Order Window
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPO_Window_ID();

	@Nullable org.compiere.model.I_AD_Window getPO_Window();

	void setPO_Window(@Nullable org.compiere.model.I_AD_Window PO_Window);

	ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window> COLUMN_PO_Window_ID = new ModelColumn<>(I_AD_Table.class, "PO_Window_ID", org.compiere.model.I_AD_Window.class);
	String COLUMNNAME_PO_Window_ID = "PO_Window_ID";

	/**
	 * Set Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setReplicationType (java.lang.String ReplicationType);

	/**
	 * Get Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getReplicationType();

	ModelColumn<I_AD_Table, Object> COLUMN_ReplicationType = new ModelColumn<>(I_AD_Table.class, "ReplicationType", null);
	String COLUMNNAME_ReplicationType = "ReplicationType";

	/**
	 * Set Tablename.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTableName (java.lang.String TableName);

	/**
	 * Get Tablename.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTableName();

	ModelColumn<I_AD_Table, Object> COLUMN_TableName = new ModelColumn<>(I_AD_Table.class, "TableName", null);
	String COLUMNNAME_TableName = "TableName";

	/**
	 * Set TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setTechnicalNote (@Nullable java.lang.String TechnicalNote);

	/**
	 * Get TechnicalNote.
	 * A note that is not indended for the user documentation, but for developers, customizers etc
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getTechnicalNote();

	ModelColumn<I_AD_Table, Object> COLUMN_TechnicalNote = new ModelColumn<>(I_AD_Table.class, "TechnicalNote", null);
	String COLUMNNAME_TechnicalNote = "TechnicalNote";

	/**
	 * Set Tooltip Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTooltipType (java.lang.String TooltipType);

	/**
	 * Get Tooltip Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTooltipType();

	ModelColumn<I_AD_Table, Object> COLUMN_TooltipType = new ModelColumn<>(I_AD_Table.class, "TooltipType", null);
	String COLUMNNAME_TooltipType = "TooltipType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Table, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Table.class, "Updated", null);
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
	 * Set View Page Length (webui).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWEBUI_View_PageLength (int WEBUI_View_PageLength);

	/**
	 * Get View Page Length (webui).
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getWEBUI_View_PageLength();

	ModelColumn<I_AD_Table, Object> COLUMN_WEBUI_View_PageLength = new ModelColumn<>(I_AD_Table.class, "WEBUI_View_PageLength", null);
	String COLUMNNAME_WEBUI_View_PageLength = "WEBUI_View_PageLength";

	/**
	 * Set When Child Cloning Strategy.
	 * The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setWhenChildCloningStrategy (java.lang.String WhenChildCloningStrategy);

	/**
	 * Get When Child Cloning Strategy.
	 * The cloning strategy to be used when this table is included (as a child) to a parent (e.g. C_OrderLine)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getWhenChildCloningStrategy();

	ModelColumn<I_AD_Table, Object> COLUMN_WhenChildCloningStrategy = new ModelColumn<>(I_AD_Table.class, "WhenChildCloningStrategy", null);
	String COLUMNNAME_WhenChildCloningStrategy = "WhenChildCloningStrategy";
}
