package org.compiere.model;


/** Generated Interface for AD_Table_Process
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Table_Process 
{

    /** TableName=AD_Table_Process */
    public static final String Table_Name = "AD_Table_Process";

    /** AD_Table_ID=53304 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Client>(I_AD_Table_Process.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Org>(I_AD_Table_Process.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Prozess oder Bericht
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Process>(I_AD_Table_Process.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Register auf einem Fenster
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab();

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Tab>(I_AD_Table_Process.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Table>(I_AD_Table_Process.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Table Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_Process_ID (int AD_Table_Process_ID);

	/**
	 * Get Table Process.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_Process_ID();

    /** Column definition for AD_Table_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_AD_Table_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "AD_Table_Process_ID", null);
    /** Column name AD_Table_Process_ID */
    public static final String COLUMNNAME_AD_Table_Process_ID = "AD_Table_Process_ID";

	/**
	 * Set Fenster.
	 * Eingabe- oder Anzeige-Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Eingabe- oder Anzeige-Fenster
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_Window>(I_AD_Table_Process.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_User>(I_AD_Table_Process.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Table_Process, org.compiere.model.I_AD_User>(I_AD_Table_Process.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Is Single Document Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_DocumentAction (boolean WEBUI_DocumentAction);

	/**
	 * Get Is Single Document Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_DocumentAction();

    /** Column definition for WEBUI_DocumentAction */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_DocumentAction = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_DocumentAction", null);
    /** Column name WEBUI_DocumentAction */
    public static final String COLUMNNAME_WEBUI_DocumentAction = "WEBUI_DocumentAction";

	/**
	 * Set Is Included Tab Top Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_IncludedTabTopAction (boolean WEBUI_IncludedTabTopAction);

	/**
	 * Get Is Included Tab Top Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_IncludedTabTopAction();

    /** Column definition for WEBUI_IncludedTabTopAction */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_IncludedTabTopAction = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_IncludedTabTopAction", null);
    /** Column name WEBUI_IncludedTabTopAction */
    public static final String COLUMNNAME_WEBUI_IncludedTabTopAction = "WEBUI_IncludedTabTopAction";

	/**
	 * Set Shortcut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_Shortcut (java.lang.String WEBUI_Shortcut);

	/**
	 * Get Shortcut.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWEBUI_Shortcut();

    /** Column definition for WEBUI_Shortcut */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_Shortcut = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_Shortcut", null);
    /** Column name WEBUI_Shortcut */
    public static final String COLUMNNAME_WEBUI_Shortcut = "WEBUI_Shortcut";

	/**
	 * Set Is View Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_ViewAction (boolean WEBUI_ViewAction);

	/**
	 * Get Is View Action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_ViewAction();

    /** Column definition for WEBUI_ViewAction */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_ViewAction = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_ViewAction", null);
    /** Column name WEBUI_ViewAction */
    public static final String COLUMNNAME_WEBUI_ViewAction = "WEBUI_ViewAction";

	/**
	 * Set Quick action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_ViewQuickAction (boolean WEBUI_ViewQuickAction);

	/**
	 * Get Quick action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_ViewQuickAction();

    /** Column definition for WEBUI_ViewQuickAction */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_ViewQuickAction = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_ViewQuickAction", null);
    /** Column name WEBUI_ViewQuickAction */
    public static final String COLUMNNAME_WEBUI_ViewQuickAction = "WEBUI_ViewQuickAction";

	/**
	 * Set Default quick action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_ViewQuickAction_Default (boolean WEBUI_ViewQuickAction_Default);

	/**
	 * Get Default quick action.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isWEBUI_ViewQuickAction_Default();

    /** Column definition for WEBUI_ViewQuickAction_Default */
    public static final org.adempiere.model.ModelColumn<I_AD_Table_Process, Object> COLUMN_WEBUI_ViewQuickAction_Default = new org.adempiere.model.ModelColumn<I_AD_Table_Process, Object>(I_AD_Table_Process.class, "WEBUI_ViewQuickAction_Default", null);
    /** Column name WEBUI_ViewQuickAction_Default */
    public static final String COLUMNNAME_WEBUI_ViewQuickAction_Default = "WEBUI_ViewQuickAction_Default";
}
