package org.compiere.model;


/** Generated Interface for AD_Tab
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Tab 
{

    /** TableName=AD_Tab */
    public static final String Table_Name = "AD_Tab";

    /** AD_Table_ID=106 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Client>(I_AD_Tab.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column>(I_AD_Tab.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Order Column.
	 * Column determining the order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ColumnSortOrder_ID (int AD_ColumnSortOrder_ID);

	/**
	 * Get Order Column.
	 * Column determining the order
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ColumnSortOrder_ID();

	public org.compiere.model.I_AD_Column getAD_ColumnSortOrder();

	public void setAD_ColumnSortOrder(org.compiere.model.I_AD_Column AD_ColumnSortOrder);

    /** Column definition for AD_ColumnSortOrder_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_ColumnSortOrder_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column>(I_AD_Tab.class, "AD_ColumnSortOrder_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_ColumnSortOrder_ID */
    public static final String COLUMNNAME_AD_ColumnSortOrder_ID = "AD_ColumnSortOrder_ID";

	/**
	 * Set Included Column.
	 * Column determining if a Table Column is included in Ordering
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ColumnSortYesNo_ID (int AD_ColumnSortYesNo_ID);

	/**
	 * Get Included Column.
	 * Column determining if a Table Column is included in Ordering
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ColumnSortYesNo_ID();

	public org.compiere.model.I_AD_Column getAD_ColumnSortYesNo();

	public void setAD_ColumnSortYesNo(org.compiere.model.I_AD_Column AD_ColumnSortYesNo);

    /** Column definition for AD_ColumnSortYesNo_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_AD_ColumnSortYesNo_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column>(I_AD_Tab.class, "AD_ColumnSortYesNo_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_ColumnSortYesNo_ID */
    public static final String COLUMNNAME_AD_ColumnSortYesNo_ID = "AD_ColumnSortYesNo_ID";

	/**
	 * Set System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * Das "System-Element" ermöglicht die zentrale  Verwaltung von Spaltenbeschreibungen und Hilfetexten.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element();

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Element>(I_AD_Tab.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

	/**
	 * Set Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Image_ID (int AD_Image_ID);

	/**
	 * Get Bild.
	 * Image or Icon
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Image_ID();

	public org.compiere.model.I_AD_Image getAD_Image();

	public void setAD_Image(org.compiere.model.I_AD_Image AD_Image);

    /** Column definition for AD_Image_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Image> COLUMN_AD_Image_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Image>(I_AD_Tab.class, "AD_Image_ID", org.compiere.model.I_AD_Image.class);
    /** Column name AD_Image_ID */
    public static final String COLUMNNAME_AD_Image_ID = "AD_Image_ID";

	/**
	 * Set Statusleistenmeldung.
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Message_ID (int AD_Message_ID);

	/**
	 * Get Statusleistenmeldung.
	 * System Message
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Message_ID();

	public org.compiere.model.I_AD_Message getAD_Message();

	public void setAD_Message(org.compiere.model.I_AD_Message AD_Message);

    /** Column definition for AD_Message_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Message> COLUMN_AD_Message_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Message>(I_AD_Tab.class, "AD_Message_ID", org.compiere.model.I_AD_Message.class);
    /** Column name AD_Message_ID */
    public static final String COLUMNNAME_AD_Message_ID = "AD_Message_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Org>(I_AD_Tab.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Process>(I_AD_Tab.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Register.
	 * Tab within a Window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Tab within a Window
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "AD_Tab_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Table>(I_AD_Tab.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/**
	 * Set Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Window_ID (int AD_Window_ID);

	/**
	 * Get Fenster.
	 * Data entry or display window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Window_ID();

	public org.compiere.model.I_AD_Window getAD_Window();

	public void setAD_Window(org.compiere.model.I_AD_Window AD_Window);

    /** Column definition for AD_Window_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Window>(I_AD_Tab.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
    /** Column name AD_Window_ID */
    public static final String COLUMNNAME_AD_Window_ID = "AD_Window_ID";

	/**
	 * Set Schnelleingabe einschalten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllowQuickInput (boolean AllowQuickInput);

	/**
	 * Get Schnelleingabe einschalten.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowQuickInput();

    /** Column definition for AllowQuickInput */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_AllowQuickInput = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "AllowQuickInput", null);
    /** Column name AllowQuickInput */
    public static final String COLUMNNAME_AllowQuickInput = "AllowQuickInput";

	/**
	 * Set Speicherwarnung.
	 * Warning displayed when saving
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setCommitWarning (java.lang.String CommitWarning);

	/**
	 * Get Speicherwarnung.
	 * Warning displayed when saving
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCommitWarning();

    /** Column definition for CommitWarning */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_CommitWarning = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "CommitWarning", null);
    /** Column name CommitWarning */
    public static final String COLUMNNAME_CommitWarning = "CommitWarning";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_User>(I_AD_Tab.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Default Selected Record.
	 * default SQL WHERE clause for selecting a default line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefaultWhereClause (java.lang.String DefaultWhereClause);

	/**
	 * Get Default Selected Record.
	 * default SQL WHERE clause for selecting a default line
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefaultWhereClause();

    /** Column definition for DefaultWhereClause */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_DefaultWhereClause = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "DefaultWhereClause", null);
    /** Column name DefaultWhereClause */
    public static final String COLUMNNAME_DefaultWhereClause = "DefaultWhereClause";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Anzeigelogik.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplayLogic (java.lang.String DisplayLogic);

	/**
	 * Get Anzeigelogik.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplayLogic();

    /** Column definition for DisplayLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_DisplayLogic = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "DisplayLogic", null);
    /** Column name DisplayLogic */
    public static final String COLUMNNAME_DisplayLogic = "DisplayLogic";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Has Tree.
	 * Window has Tree Graph
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHasTree (boolean HasTree);

	/**
	 * Get Has Tree.
	 * Window has Tree Graph
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHasTree();

    /** Column definition for HasTree */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_HasTree = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "HasTree", null);
    /** Column name HasTree */
    public static final String COLUMNNAME_HasTree = "HasTree";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/**
	 * Set Import Fields.
	 * Create Fields from Table Columns
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setImportFields (java.lang.String ImportFields);

	/**
	 * Get Import Fields.
	 * Create Fields from Table Columns
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getImportFields();

    /** Column definition for ImportFields */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_ImportFields = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "ImportFields", null);
    /** Column name ImportFields */
    public static final String COLUMNNAME_ImportFields = "ImportFields";

	/**
	 * Set Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncluded_Tab_ID (int Included_Tab_ID);

	/**
	 * Get Included Tab.
	 * Included Tab in this Tab (Master Dateail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getIncluded_Tab_ID();

	public org.compiere.model.I_AD_Tab getIncluded_Tab();

	public void setIncluded_Tab(org.compiere.model.I_AD_Tab Included_Tab);

    /** Column definition for Included_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab> COLUMN_Included_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab>(I_AD_Tab.class, "Included_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name Included_Tab_ID */
    public static final String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/**
	 * Set Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInternalName (java.lang.String InternalName);

	/**
	 * Get Interner Name.
	 * Generally used to give records a name that can be safely referenced from code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInternalName();

    /** Column definition for InternalName */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_InternalName = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "InternalName", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Advanced Tab.
	 * This Tab contains advanced Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAdvancedTab (boolean IsAdvancedTab);

	/**
	 * Get Advanced Tab.
	 * This Tab contains advanced Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAdvancedTab();

    /** Column definition for IsAdvancedTab */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsAdvancedTab = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsAdvancedTab", null);
    /** Column name IsAdvancedTab */
    public static final String COLUMNNAME_IsAdvancedTab = "IsAdvancedTab";

	/**
	 * Set Check Parents Changed.
	 * Before saving a record in this tab shall we check if the parent tabs were changed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCheckParentsChanged (boolean IsCheckParentsChanged);

	/**
	 * Get Check Parents Changed.
	 * Before saving a record in this tab shall we check if the parent tabs were changed?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCheckParentsChanged();

    /** Column definition for IsCheckParentsChanged */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsCheckParentsChanged = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsCheckParentsChanged", null);
    /** Column name IsCheckParentsChanged */
    public static final String COLUMNNAME_IsCheckParentsChanged = "IsCheckParentsChanged";

	/**
	 * Set Grid Mode Only.
	 * Allow grid mode only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsGridModeOnly (boolean IsGridModeOnly);

	/**
	 * Get Grid Mode Only.
	 * Allow grid mode only
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isGridModeOnly();

    /** Column definition for IsGridModeOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsGridModeOnly = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsGridModeOnly", null);
    /** Column name IsGridModeOnly */
    public static final String COLUMNNAME_IsGridModeOnly = "IsGridModeOnly";

	/**
	 * Set Accounting Tab.
	 * This Tab contains accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsInfoTab (boolean IsInfoTab);

	/**
	 * Get Accounting Tab.
	 * This Tab contains accounting information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isInfoTab();

    /** Column definition for IsInfoTab */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsInfoTab = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsInfoTab", null);
    /** Column name IsInfoTab */
    public static final String COLUMNNAME_IsInfoTab = "IsInfoTab";

	/**
	 * Set Insert Record.
	 * The user can insert a new Record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInsertRecord (boolean IsInsertRecord);

	/**
	 * Get Insert Record.
	 * The user can insert a new Record
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInsertRecord();

    /** Column definition for IsInsertRecord */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsInsertRecord = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsInsertRecord", null);
    /** Column name IsInsertRecord */
    public static final String COLUMNNAME_IsInsertRecord = "IsInsertRecord";

	/**
	 * Set Query data on load.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsQueryOnLoad (boolean IsQueryOnLoad);

	/**
	 * Get Query data on load.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isQueryOnLoad();

    /** Column definition for IsQueryOnLoad */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsQueryOnLoad = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsQueryOnLoad", null);
    /** Column name IsQueryOnLoad */
    public static final String COLUMNNAME_IsQueryOnLoad = "IsQueryOnLoad";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Refresh All On Activate.
	 * Refresh all rows when user activates this tab, instead of refreshing only current row
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRefreshAllOnActivate (boolean IsRefreshAllOnActivate);

	/**
	 * Get Refresh All On Activate.
	 * Refresh all rows when user activates this tab, instead of refreshing only current row
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRefreshAllOnActivate();

    /** Column definition for IsRefreshAllOnActivate */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsRefreshAllOnActivate = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsRefreshAllOnActivate", null);
    /** Column name IsRefreshAllOnActivate */
    public static final String COLUMNNAME_IsRefreshAllOnActivate = "IsRefreshAllOnActivate";

	/**
	 * Set Refresh view on change events.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRefreshViewOnChangeEvents (boolean IsRefreshViewOnChangeEvents);

	/**
	 * Get Refresh view on change events.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRefreshViewOnChangeEvents();

    /** Column definition for IsRefreshViewOnChangeEvents */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsRefreshViewOnChangeEvents = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsRefreshViewOnChangeEvents", null);
    /** Column name IsRefreshViewOnChangeEvents */
    public static final String COLUMNNAME_IsRefreshViewOnChangeEvents = "IsRefreshViewOnChangeEvents";

	/**
	 * Set Search Active.
	 * This mark activates the search button from toolbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSearchActive (boolean IsSearchActive);

	/**
	 * Get Search Active.
	 * This mark activates the search button from toolbar
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isSearchActive();

    /** Column definition for IsSearchActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsSearchActive = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsSearchActive", null);
    /** Column name IsSearchActive */
    public static final String COLUMNNAME_IsSearchActive = "IsSearchActive";

	/**
	 * Set Collapse Search Panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSearchCollapsed (boolean IsSearchCollapsed);

	/**
	 * Get Collapse Search Panel.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSearchCollapsed();

    /** Column definition for IsSearchCollapsed */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsSearchCollapsed = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsSearchCollapsed", null);
    /** Column name IsSearchCollapsed */
    public static final String COLUMNNAME_IsSearchCollapsed = "IsSearchCollapsed";

	/**
	 * Set Single Row Layout.
	 * Default for toggle between Single- and Multi-Row (Grid) Layout
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSingleRow (boolean IsSingleRow);

	/**
	 * Get Single Row Layout.
	 * Default for toggle between Single- and Multi-Row (Grid) Layout
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSingleRow();

    /** Column definition for IsSingleRow */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsSingleRow = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsSingleRow", null);
    /** Column name IsSingleRow */
    public static final String COLUMNNAME_IsSingleRow = "IsSingleRow";

	/**
	 * Set Order Tab.
	 * The Tab determines the Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSortTab (boolean IsSortTab);

	/**
	 * Get Order Tab.
	 * The Tab determines the Order
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSortTab();

    /** Column definition for IsSortTab */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsSortTab = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsSortTab", null);
    /** Column name IsSortTab */
    public static final String COLUMNNAME_IsSortTab = "IsSortTab";

	/**
	 * Set Übersetzungsregister.
	 * This Tab contains translation information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTranslationTab (boolean IsTranslationTab);

	/**
	 * Get Übersetzungsregister.
	 * This Tab contains translation information
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTranslationTab();

    /** Column definition for IsTranslationTab */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_IsTranslationTab = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "IsTranslationTab", null);
    /** Column name IsTranslationTab */
    public static final String COLUMNNAME_IsTranslationTab = "IsTranslationTab";

	/**
	 * Set Max. Suchergebnisse.
	 * Wenn definiert können Suie nicht mehr Suchergebnisse abfragen - die Suchbedingungen müssen verändert werden, um eine geringere Anzahl zu erhalten
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMaxQueryRecords (int MaxQueryRecords);

	/**
	 * Get Max. Suchergebnisse.
	 * Wenn definiert können Suie nicht mehr Suchergebnisse abfragen - die Suchbedingungen müssen verändert werden, um eine geringere Anzahl zu erhalten
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getMaxQueryRecords();

    /** Column definition for MaxQueryRecords */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_MaxQueryRecords = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "MaxQueryRecords", null);
    /** Column name MaxQueryRecords */
    public static final String COLUMNNAME_MaxQueryRecords = "MaxQueryRecords";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Sql ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setOrderByClause (java.lang.String OrderByClause);

	/**
	 * Get Sql ORDER BY.
	 * Fully qualified ORDER BY clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getOrderByClause();

    /** Column definition for OrderByClause */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_OrderByClause = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "OrderByClause", null);
    /** Column name OrderByClause */
    public static final String COLUMNNAME_OrderByClause = "OrderByClause";

	/**
	 * Set Parent Column.
	 * The link column on the parent tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setParent_Column_ID (int Parent_Column_ID);

	/**
	 * Get Parent Column.
	 * The link column on the parent tab.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getParent_Column_ID();

	public org.compiere.model.I_AD_Column getParent_Column();

	public void setParent_Column(org.compiere.model.I_AD_Column Parent_Column);

    /** Column definition for Parent_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column> COLUMN_Parent_Column_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Column>(I_AD_Tab.class, "Parent_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name Parent_Column_ID */
    public static final String COLUMNNAME_Parent_Column_ID = "Parent_Column_ID";

	/**
	 * Set Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setProcessing (boolean Processing);

	/**
	 * Get Verarbeiten.
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isProcessing();

    /** Column definition for Processing */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Processing = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Processing", null);
    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/**
	 * Set Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReadOnlyLogic (java.lang.String ReadOnlyLogic);

	/**
	 * Get Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReadOnlyLogic();

    /** Column definition for ReadOnlyLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_ReadOnlyLogic = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "ReadOnlyLogic", null);
    /** Column name ReadOnlyLogic */
    public static final String COLUMNNAME_ReadOnlyLogic = "ReadOnlyLogic";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Tab Level.
	 * Hierarchical Tab Level (0 = top)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTabLevel (int TabLevel);

	/**
	 * Get Tab Level.
	 * Hierarchical Tab Level (0 = top)
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getTabLevel();

    /** Column definition for TabLevel */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_TabLevel = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "TabLevel", null);
    /** Column name TabLevel */
    public static final String COLUMNNAME_TabLevel = "TabLevel";

	/**
	 * Set Template Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setTemplate_Tab_ID (int Template_Tab_ID);

	/**
	 * Get Template Tab.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getTemplate_Tab_ID();

	public org.compiere.model.I_AD_Tab getTemplate_Tab();

	public void setTemplate_Tab(org.compiere.model.I_AD_Tab Template_Tab);

    /** Column definition for Template_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab> COLUMN_Template_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_Tab>(I_AD_Tab.class, "Template_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name Template_Tab_ID */
    public static final String COLUMNNAME_Template_Tab_ID = "Template_Tab_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Tab, org.compiere.model.I_AD_User>(I_AD_Tab.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWhereClause (java.lang.String WhereClause);

	/**
	 * Get Sql WHERE.
	 * Fully qualified SQL WHERE clause
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWhereClause();

    /** Column definition for WhereClause */
    public static final org.adempiere.model.ModelColumn<I_AD_Tab, Object> COLUMN_WhereClause = new org.adempiere.model.ModelColumn<I_AD_Tab, Object>(I_AD_Tab.class, "WhereClause", null);
    /** Column name WhereClause */
    public static final String COLUMNNAME_WhereClause = "WhereClause";
}
