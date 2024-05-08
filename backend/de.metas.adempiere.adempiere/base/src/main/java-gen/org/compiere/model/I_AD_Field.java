package org.compiere.model;


/** Generated Interface for AD_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Field 
{

    /** TableName=AD_Field */
    public static final String Table_Name = "AD_Field";

    /** AD_Table_ID=107 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Client>(I_AD_Field.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column();

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Column>(I_AD_Field.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Feld.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Feld.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Field_ID();

    /** Column definition for AD_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_AD_Field_ID = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "AD_Field_ID", null);
    /** Column name AD_Field_ID */
    public static final String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/**
	 * Set Feld-Gruppe.
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID);

	/**
	 * Get Feld-Gruppe.
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_FieldGroup_ID();

	public org.compiere.model.I_AD_FieldGroup getAD_FieldGroup();

	public void setAD_FieldGroup(org.compiere.model.I_AD_FieldGroup AD_FieldGroup);

    /** Column definition for AD_FieldGroup_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_FieldGroup> COLUMN_AD_FieldGroup_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_FieldGroup>(I_AD_Field.class, "AD_FieldGroup_ID", org.compiere.model.I_AD_FieldGroup.class);
    /** Column name AD_FieldGroup_ID */
    public static final String COLUMNNAME_AD_FieldGroup_ID = "AD_FieldGroup_ID";

	/**
	 * Set AD_Name_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Name_ID (int AD_Name_ID);

	/**
	 * Get AD_Name_ID.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Name_ID();

	public org.compiere.model.I_AD_Element getAD_Name();

	public void setAD_Name(org.compiere.model.I_AD_Element AD_Name);

    /** Column definition for AD_Name_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Element> COLUMN_AD_Name_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Element>(I_AD_Field.class, "AD_Name_ID", org.compiere.model.I_AD_Element.class);
    /** Column name AD_Name_ID */
    public static final String COLUMNNAME_AD_Name_ID = "AD_Name_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Org>(I_AD_Field.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference>(I_AD_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenzschlüssel.
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Referenzschlüssel.
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_Value_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Value();

	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value);

    /** Column definition for AD_Reference_Value_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference>(I_AD_Field.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set Register.
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Tab_ID();

	public org.compiere.model.I_AD_Tab getAD_Tab();

	public void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

    /** Column definition for AD_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab>(I_AD_Field.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name AD_Tab_ID */
    public static final String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule>(I_AD_Field.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColorLogic (java.lang.String ColorLogic);

	/**
	 * Get Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColorLogic();

    /** Column definition for ColorLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ColorLogic = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ColorLogic", null);
    /** Column name ColorLogic */
    public static final String COLUMNNAME_ColorLogic = "ColorLogic";

	/**
	 * Set Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColumnDisplayLength (int ColumnDisplayLength);

	/**
	 * Get Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getColumnDisplayLength();

    /** Column definition for ColumnDisplayLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ColumnDisplayLength = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ColumnDisplayLength", null);
    /** Column name ColumnDisplayLength */
    public static final String COLUMNNAME_ColumnDisplayLength = "ColumnDisplayLength";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User>(I_AD_Field.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Standardwert-Logik.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefaultValue (java.lang.String DefaultValue);

	/**
	 * Get Standardwert-Logik.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefaultValue();

    /** Column definition for DefaultValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DefaultValue = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DefaultValue", null);
    /** Column name DefaultValue */
    public static final String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Anzeigelänge.
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplayLength (int DisplayLength);

	/**
	 * Get Anzeigelänge.
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDisplayLength();

    /** Column definition for DisplayLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DisplayLength = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DisplayLength", null);
    /** Column name DisplayLength */
    public static final String COLUMNNAME_DisplayLength = "DisplayLength";

	/**
	 * Set Anzeigelogik.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDisplayLogic (java.lang.String DisplayLogic);

	/**
	 * Get Anzeigelogik.
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDisplayLogic();

    /** Column definition for DisplayLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DisplayLogic = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DisplayLogic", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "EntityType", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_Included_Tab_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab>(I_AD_Field.class, "Included_Tab_ID", org.compiere.model.I_AD_Tab.class);
    /** Column name Included_Tab_ID */
    public static final String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";

	/**
	 * Set Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIncludedTabHeight (int IncludedTabHeight);

	/**
	 * Get Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getIncludedTabHeight();

    /** Column definition for IncludedTabHeight */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IncludedTabHeight = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IncludedTabHeight", null);
    /** Column name IncludedTabHeight */
    public static final String COLUMNNAME_IncludedTabHeight = "IncludedTabHeight";

	/**
	 * Set Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setInfoFactoryClass (java.lang.String InfoFactoryClass);

	/**
	 * Get Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getInfoFactoryClass();

    /** Column definition for InfoFactoryClass */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_InfoFactoryClass = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "InfoFactoryClass", null);
    /** Column name InfoFactoryClass */
    public static final String COLUMNNAME_InfoFactoryClass = "InfoFactoryClass";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayed (boolean IsDisplayed);

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";

	/**
	 * Set Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDisplayedGrid (boolean IsDisplayedGrid);

	/**
	 * Get Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDisplayedGrid();

    /** Column definition for IsDisplayedGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayedGrid = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsDisplayedGrid", null);
    /** Column name IsDisplayedGrid */
    public static final String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/**
	 * Set Encrypted.
	 * Display or Storage is encrypted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEncrypted (boolean IsEncrypted);

	/**
	 * Get Encrypted.
	 * Display or Storage is encrypted
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isEncrypted();

    /** Column definition for IsEncrypted */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsEncrypted = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsEncrypted", null);
    /** Column name IsEncrypted */
    public static final String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/**
	 * Set Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsFieldOnly (boolean IsFieldOnly);

	/**
	 * Get Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isFieldOnly();

    /** Column definition for IsFieldOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsFieldOnly = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsFieldOnly", null);
    /** Column name IsFieldOnly */
    public static final String COLUMNNAME_IsFieldOnly = "IsFieldOnly";

	/**
	 * Set Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsHeading (boolean IsHeading);

	/**
	 * Get Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isHeading();

    /** Column definition for IsHeading */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsHeading = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsHeading", null);
    /** Column name IsHeading */
    public static final String COLUMNNAME_IsHeading = "IsHeading";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsMandatory (java.lang.String IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";

	/**
	 * Set Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSameLine (boolean IsSameLine);

	/**
	 * Get Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSameLine();

    /** Column definition for IsSameLine */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsSameLine = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsSameLine", null);
    /** Column name IsSameLine */
    public static final String COLUMNNAME_IsSameLine = "IsSameLine";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setObscureType (java.lang.String ObscureType);

	/**
	 * Get Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getObscureType();

    /** Column definition for ObscureType */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ObscureType = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ObscureType", null);
    /** Column name ObscureType */
    public static final String COLUMNNAME_ObscureType = "ObscureType";

	/**
	 * Set Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/**
	 * Set Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSeqNoGrid (int SeqNoGrid);

	/**
	 * Get Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSeqNoGrid();

    /** Column definition for SeqNoGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SeqNoGrid = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SeqNoGrid", null);
    /** Column name SeqNoGrid */
    public static final String COLUMNNAME_SeqNoGrid = "SeqNoGrid";

	/**
	 * Set Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSortNo (java.math.BigDecimal SortNo);

	/**
	 * Get Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getSortNo();

    /** Column definition for SortNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SortNo = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SortNo", null);
    /** Column name SortNo */
    public static final String COLUMNNAME_SortNo = "SortNo";

	/**
	 * Set Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSpanX (int SpanX);

	/**
	 * Get Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSpanX();

    /** Column definition for SpanX */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SpanX = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SpanX", null);
    /** Column name SpanX */
    public static final String COLUMNNAME_SpanX = "SpanX";

	/**
	 * Set Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSpanY (int SpanY);

	/**
	 * Get Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSpanY();

    /** Column definition for SpanY */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SpanY = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SpanY", null);
    /** Column name SpanY */
    public static final String COLUMNNAME_SpanY = "SpanY";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User>(I_AD_Field.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
