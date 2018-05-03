package org.compiere.model;


/** Generated Interface for AD_Table
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Table 
{

    /** TableName=AD_Table */
    public static final String Table_Name = "AD_Table";

    /** AD_Table_ID=100 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Set Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAccessLevel (java.lang.String AccessLevel);

	/**
	 * Get Berechtigungsstufe.
	 * Access Level required
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAccessLevel();

    /** Column definition for AccessLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_AccessLevel = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "AccessLevel", null);
    /** Column name AccessLevel */
    public static final String COLUMNNAME_AccessLevel = "AccessLevel";

	/**
	 * Set Auto Complete Min Length.
	 * Identifier autocomplete trigger length
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setACTriggerLength (int ACTriggerLength);

	/**
	 * Get Auto Complete Min Length.
	 * Identifier autocomplete trigger length
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getACTriggerLength();

    /** Column definition for ACTriggerLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_ACTriggerLength = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "ACTriggerLength", null);
    /** Column name ACTriggerLength */
    public static final String COLUMNNAME_ACTriggerLength = "ACTriggerLength";

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Client>(I_AD_Table.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Org_ID();

	public org.compiere.model.I_AD_Org getAD_Org();

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Org>(I_AD_Table.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "AD_Table_ID", null);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Dynamische Validierung.
	 * Dynamic Validation Rule
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Dynamische Validierung.
	 * Dynamic Validation Rule
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Val_Rule_ID();

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

    /** Column definition for AD_Val_Rule_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Val_Rule>(I_AD_Table.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window>(I_AD_Table.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Copy Columns From Table.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCopyColumnsFromTable (java.lang.String CopyColumnsFromTable);

	/**
	 * Get Copy Columns From Table.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCopyColumnsFromTable();

    /** Column definition for CopyColumnsFromTable */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_CopyColumnsFromTable = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "CopyColumnsFromTable", null);
    /** Column name CopyColumnsFromTable */
    public static final String COLUMNNAME_CopyColumnsFromTable = "CopyColumnsFromTable";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_User>(I_AD_Table.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHelp();

    /** Column definition for Help */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Import Table.
	 * Import Table Columns from Database
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportTable (java.lang.String ImportTable);

	/**
	 * Get Import Table.
	 * Import Table Columns from Database
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportTable();

    /** Column definition for ImportTable */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_ImportTable = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "ImportTable", null);
    /** Column name ImportTable */
    public static final String COLUMNNAME_ImportTable = "ImportTable";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAutocomplete (boolean IsAutocomplete);

	/**
	 * Get Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAutocomplete();

    /** Column definition for IsAutocomplete */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsAutocomplete = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsAutocomplete", null);
    /** Column name IsAutocomplete */
    public static final String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Änderungen protokollieren.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsChangeLog (boolean IsChangeLog);

	/**
	 * Get Änderungen protokollieren.
	 * Maintain a log of changes
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isChangeLog();

    /** Column definition for IsChangeLog */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsChangeLog = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsChangeLog", null);
    /** Column name IsChangeLog */
    public static final String COLUMNNAME_IsChangeLog = "IsChangeLog";

	/**
	 * Set Records deleteable.
	 * Indicates if records can be deleted from the database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDeleteable (boolean IsDeleteable);

	/**
	 * Get Records deleteable.
	 * Indicates if records can be deleted from the database
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDeleteable();

    /** Column definition for IsDeleteable */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsDeleteable = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsDeleteable", null);
    /** Column name IsDeleteable */
    public static final String COLUMNNAME_IsDeleteable = "IsDeleteable";

	/**
	 * Set High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsHighVolume (boolean IsHighVolume);

	/**
	 * Get High Volume.
	 * Use Search instead of Pick list
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHighVolume();

    /** Column definition for IsHighVolume */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsHighVolume = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsHighVolume", null);
    /** Column name IsHighVolume */
    public static final String COLUMNNAME_IsHighVolume = "IsHighVolume";

	/**
	 * Set Security enabled.
	 * If security is enabled, user access to data can be restricted via Roles
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSecurityEnabled (boolean IsSecurityEnabled);

	/**
	 * Get Security enabled.
	 * If security is enabled, user access to data can be restricted via Roles
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSecurityEnabled();

    /** Column definition for IsSecurityEnabled */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsSecurityEnabled = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsSecurityEnabled", null);
    /** Column name IsSecurityEnabled */
    public static final String COLUMNNAME_IsSecurityEnabled = "IsSecurityEnabled";

	/**
	 * Set Ansicht.
	 * This is a view
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsView (boolean IsView);

	/**
	 * Get Ansicht.
	 * This is a view
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isView();

    /** Column definition for IsView */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_IsView = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "IsView", null);
    /** Column name IsView */
    public static final String COLUMNNAME_IsView = "IsView";

	/**
	 * Set Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLoadSeq (int LoadSeq);

	/**
	 * Get Reihenfolge.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getLoadSeq();

    /** Column definition for LoadSeq */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_LoadSeq = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "LoadSeq", null);
    /** Column name LoadSeq */
    public static final String COLUMNNAME_LoadSeq = "LoadSeq";

	/**
	 * Set Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 * Alphanumeric identifier of the entity
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Personal Data Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPersonalDataCategory (java.lang.String PersonalDataCategory);

	/**
	 * Get Personal Data Category.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPersonalDataCategory();

    /** Column definition for PersonalDataCategory */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_PersonalDataCategory = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "PersonalDataCategory", null);
    /** Column name PersonalDataCategory */
    public static final String COLUMNNAME_PersonalDataCategory = "PersonalDataCategory";

	/**
	 * Set PO Window.
	 * Purchase Order Window
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPO_Window_ID (int PO_Window_ID);

	/**
	 * Get PO Window.
	 * Purchase Order Window
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPO_Window_ID();

	public org.compiere.model.I_AD_Window getPO_Window();

	public void setPO_Window(org.compiere.model.I_AD_Window PO_Window);

    /** Column definition for PO_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window> COLUMN_PO_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_Window>(I_AD_Table.class, "PO_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name PO_Window_ID */
    public static final String COLUMNNAME_PO_Window_ID = "PO_Window_ID";

	/**
	 * Set Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setReplicationType (java.lang.String ReplicationType);

	/**
	 * Get Replication Type.
	 * Type of Data Replication
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReplicationType();

    /** Column definition for ReplicationType */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_ReplicationType = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "ReplicationType", null);
    /** Column name ReplicationType */
    public static final String COLUMNNAME_ReplicationType = "ReplicationType";

	/**
	 * Set Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTableName (java.lang.String TableName);

	/**
	 * Get Name der DB-Tabelle.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTableName();

    /** Column definition for TableName */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_TableName = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "TableName", null);
    /** Column name TableName */
    public static final String COLUMNNAME_TableName = "TableName";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Table, Object>(I_AD_Table.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Table, org.compiere.model.I_AD_User>(I_AD_Table.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
