package org.compiere.model;


/** Generated Interface for AD_Menu
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Menu 
{

    /** TableName=AD_Menu */
    public static final String Table_Name = "AD_Menu";

    /** AD_Table_ID=116 */
    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Set Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAction (java.lang.String Action);

	/**
	 * Get Aktion.
	 * Indicates the Action to be performed
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAction();

    /** Column definition for Action */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_Action = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "Action", null);
    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Client>(I_AD_Menu.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Form_ID (int AD_Form_ID);

	/**
	 * Get Special Form.
	 * Special Form
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Form_ID();

	public org.compiere.model.I_AD_Form getAD_Form();

	public void setAD_Form(org.compiere.model.I_AD_Form AD_Form);

    /** Column definition for AD_Form_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Form> COLUMN_AD_Form_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Form>(I_AD_Menu.class, "AD_Form_ID", org.compiere.model.I_AD_Form.class);
    /** Column name AD_Form_ID */
    public static final String COLUMNNAME_AD_Form_ID = "AD_Form_ID";

	/**
	 * Set Menü.
	 * Identifies a Menu
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Menu_ID (int AD_Menu_ID);

	/**
	 * Get Menü.
	 * Identifies a Menu
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Menu_ID();

    /** Column definition for AD_Menu_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_AD_Menu_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "AD_Menu_ID", null);
    /** Column name AD_Menu_ID */
    public static final String COLUMNNAME_AD_Menu_ID = "AD_Menu_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Org>(I_AD_Menu.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Process>(I_AD_Menu.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Task_ID (int AD_Task_ID);

	/**
	 * Get Externer Prozess.
	 * Operation System Task
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Task_ID();

	public org.compiere.model.I_AD_Task getAD_Task();

	public void setAD_Task(org.compiere.model.I_AD_Task AD_Task);

    /** Column definition for AD_Task_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Task> COLUMN_AD_Task_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Task>(I_AD_Menu.class, "AD_Task_ID", org.compiere.model.I_AD_Task.class);
    /** Column name AD_Task_ID */
    public static final String COLUMNNAME_AD_Task_ID = "AD_Task_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Window>(I_AD_Menu.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Workbench.
	 * Collection of windows, reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Workbench_ID (int AD_Workbench_ID);

	/**
	 * Get Workbench.
	 * Collection of windows, reports
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Workbench_ID();

	public org.compiere.model.I_AD_Workbench getAD_Workbench();

	public void setAD_Workbench(org.compiere.model.I_AD_Workbench AD_Workbench);

    /** Column definition for AD_Workbench_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Workbench> COLUMN_AD_Workbench_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Workbench>(I_AD_Menu.class, "AD_Workbench_ID", org.compiere.model.I_AD_Workbench.class);
    /** Column name AD_Workbench_ID */
    public static final String COLUMNNAME_AD_Workbench_ID = "AD_Workbench_ID";

	/**
	 * Set Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Workflow_ID (int AD_Workflow_ID);

	/**
	 * Get Workflow.
	 * Workflow or combination of tasks
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Workflow_ID();

	public org.compiere.model.I_AD_Workflow getAD_Workflow();

	public void setAD_Workflow(org.compiere.model.I_AD_Workflow AD_Workflow);

    /** Column definition for AD_Workflow_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Workflow> COLUMN_AD_Workflow_ID = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_Workflow>(I_AD_Menu.class, "AD_Workflow_ID", org.compiere.model.I_AD_Workflow.class);
    /** Column name AD_Workflow_ID */
    public static final String COLUMNNAME_AD_Workflow_ID = "AD_Workflow_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_User>(I_AD_Menu.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "InternalName", null);
    /** Column name InternalName */
    public static final String COLUMNNAME_InternalName = "InternalName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Create new.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCreateNew (boolean IsCreateNew);

	/**
	 * Get Create new.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCreateNew();

    /** Column definition for IsCreateNew */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_IsCreateNew = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "IsCreateNew", null);
    /** Column name IsCreateNew */
    public static final String COLUMNNAME_IsCreateNew = "IsCreateNew";

	/**
	 * Set Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Field is read only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isReadOnly();

    /** Column definition for IsReadOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSOTrx (boolean IsSOTrx);

	/**
	 * Get Verkaufs-Transaktion.
	 * This is a Sales Transaction
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSOTrx();

    /** Column definition for IsSOTrx */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_IsSOTrx = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "IsSOTrx", null);
    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";

	/**
	 * Set Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSummary (boolean IsSummary);

	/**
	 * Get Zusammenfassungseintrag.
	 * This is a summary entity
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSummary();

    /** Column definition for IsSummary */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_IsSummary = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "IsSummary", null);
    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Menu, org.compiere.model.I_AD_User>(I_AD_Menu.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Browse name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_NameBrowse (java.lang.String WEBUI_NameBrowse);

	/**
	 * Get Browse name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWEBUI_NameBrowse();

    /** Column definition for WEBUI_NameBrowse */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameBrowse = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "WEBUI_NameBrowse", null);
    /** Column name WEBUI_NameBrowse */
    public static final String COLUMNNAME_WEBUI_NameBrowse = "WEBUI_NameBrowse";

	/**
	 * Set New record name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_NameNew (java.lang.String WEBUI_NameNew);

	/**
	 * Get New record name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWEBUI_NameNew();

    /** Column definition for WEBUI_NameNew */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameNew = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "WEBUI_NameNew", null);
    /** Column name WEBUI_NameNew */
    public static final String COLUMNNAME_WEBUI_NameNew = "WEBUI_NameNew";

	/**
	 * Set New record name (breadcrumb).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWEBUI_NameNewBreadcrumb (java.lang.String WEBUI_NameNewBreadcrumb);

	/**
	 * Get New record name (breadcrumb).
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWEBUI_NameNewBreadcrumb();

    /** Column definition for WEBUI_NameNewBreadcrumb */
    public static final org.adempiere.model.ModelColumn<I_AD_Menu, Object> COLUMN_WEBUI_NameNewBreadcrumb = new org.adempiere.model.ModelColumn<I_AD_Menu, Object>(I_AD_Menu.class, "WEBUI_NameNewBreadcrumb", null);
    /** Column name WEBUI_NameNewBreadcrumb */
    public static final String COLUMNNAME_WEBUI_NameNewBreadcrumb = "WEBUI_NameNewBreadcrumb";
}
