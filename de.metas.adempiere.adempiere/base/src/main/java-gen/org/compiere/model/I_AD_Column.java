package org.compiere.model;


/** Generated Interface for AD_Column
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Column 
{

    /** TableName=AD_Column */
    public static final String Table_Name = "AD_Column";

    /** AD_Table_ID=101 */
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Client>(I_AD_Column.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Column_ID();

    /** Column definition for AD_Column_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_AD_Column_ID = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "AD_Column_ID", null);
    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element();

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Element>(I_AD_Column.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
    /** Column name AD_Element_ID */
    public static final String COLUMNNAME_AD_Element_ID = "AD_Element_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Org>(I_AD_Column.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Process>(I_AD_Column.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * System Reference and Validation
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference>(I_AD_Column.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Reference>(I_AD_Column.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Table>(I_AD_Column.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_Val_Rule>(I_AD_Column.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Allow Zoom To.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAllowZoomTo (boolean AllowZoomTo);

	/**
	 * Get Allow Zoom To.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAllowZoomTo();

    /** Column definition for AllowZoomTo */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_AllowZoomTo = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "AllowZoomTo", null);
    /** Column name AllowZoomTo */
    public static final String COLUMNNAME_AllowZoomTo = "AllowZoomTo";

	/**
	 * Set Spaltenname.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setColumnName (java.lang.String ColumnName);

	/**
	 * Get Spaltenname.
	 * Name of the column in the database
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnName();

    /** Column definition for ColumnName */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/**
	 * Set Column SQL.
	 * Virtual Column (r/o)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setColumnSQL (java.lang.String ColumnSQL);

	/**
	 * Get Column SQL.
	 * Virtual Column (r/o)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getColumnSQL();

    /** Column definition for ColumnSQL */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_ColumnSQL = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "ColumnSQL", null);
    /** Column name ColumnSQL */
    public static final String COLUMNNAME_ColumnSQL = "ColumnSQL";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_User>(I_AD_Column.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set No Foreign Key.
	 * Don't create database foreign key (if applicable) for this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDDL_NoForeignKey (boolean DDL_NoForeignKey);

	/**
	 * Get No Foreign Key.
	 * Don't create database foreign key (if applicable) for this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDDL_NoForeignKey();

    /** Column definition for DDL_NoForeignKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_DDL_NoForeignKey = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "DDL_NoForeignKey", null);
    /** Column name DDL_NoForeignKey */
    public static final String COLUMNNAME_DDL_NoForeignKey = "DDL_NoForeignKey";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_DefaultValue = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "DefaultValue", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Länge.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFieldLength (int FieldLength);

	/**
	 * Get Länge.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getFieldLength();

    /** Column definition for FieldLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_FieldLength = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "FieldLength", null);
    /** Column name FieldLength */
    public static final String COLUMNNAME_FieldLength = "FieldLength";

	/**
	 * Set Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFilterDefaultValue (java.lang.String FilterDefaultValue);

	/**
	 * Get Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFilterDefaultValue();

    /** Column definition for FilterDefaultValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_FilterDefaultValue = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "FilterDefaultValue", null);
    /** Column name FilterDefaultValue */
    public static final String COLUMNNAME_FilterDefaultValue = "FilterDefaultValue";

	/**
	 * Set Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFormatPattern (java.lang.String FormatPattern);

	/**
	 * Get Format Pattern.
	 * The pattern used to format a number or date.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFormatPattern();

    /** Column definition for FormatPattern */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_FormatPattern = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "FormatPattern", null);
    /** Column name FormatPattern */
    public static final String COLUMNNAME_FormatPattern = "FormatPattern";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_InfoFactoryClass = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "InfoFactoryClass", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Allow Logging.
	 * Determine if a column must be recorded into the change log
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAllowLogging (boolean IsAllowLogging);

	/**
	 * Get Allow Logging.
	 * Determine if a column must be recorded into the change log
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAllowLogging();

    /** Column definition for IsAllowLogging */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsAllowLogging = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsAllowLogging", null);
    /** Column name IsAllowLogging */
    public static final String COLUMNNAME_IsAllowLogging = "IsAllowLogging";

	/**
	 * Set Always Updateable.
	 * The column is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsAlwaysUpdateable (boolean IsAlwaysUpdateable);

	/**
	 * Get Always Updateable.
	 * The column is always updateable, even if the record is not active or processed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAlwaysUpdateable();

    /** Column definition for IsAlwaysUpdateable */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsAlwaysUpdateable = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsAlwaysUpdateable", null);
    /** Column name IsAlwaysUpdateable */
    public static final String COLUMNNAME_IsAlwaysUpdateable = "IsAlwaysUpdateable";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsAutocomplete = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsAutocomplete", null);
    /** Column name IsAutocomplete */
    public static final String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Berechnet.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCalculated (boolean IsCalculated);

	/**
	 * Get Berechnet.
	 * The value is calculated by the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCalculated();

    /** Column definition for IsCalculated */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsCalculated = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsCalculated", null);
    /** Column name IsCalculated */
    public static final String COLUMNNAME_IsCalculated = "IsCalculated";

	/**
	 * Set Encrypted.
	 * Display or Storage is encrypted
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsEncrypted (java.lang.String IsEncrypted);

	/**
	 * Get Encrypted.
	 * Display or Storage is encrypted
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsEncrypted();

    /** Column definition for IsEncrypted */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsEncrypted = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsEncrypted", null);
    /** Column name IsEncrypted */
    public static final String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/**
	 * Set GenericZoom Quellspalte.
	 * Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsGenericZoomOrigin (boolean IsGenericZoomOrigin);

	/**
	 * Get GenericZoom Quellspalte.
	 * Werden beim GenericZoom Referenzen auf diese Spalte beachtet?
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isGenericZoomOrigin();

    /** Column definition for IsGenericZoomOrigin */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsGenericZoomOrigin = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsGenericZoomOrigin", null);
    /** Column name IsGenericZoomOrigin */
    public static final String COLUMNNAME_IsGenericZoomOrigin = "IsGenericZoomOrigin";

	/**
	 * Set Identifier.
	 * This column is part of the record identifier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsIdentifier (boolean IsIdentifier);

	/**
	 * Get Identifier.
	 * This column is part of the record identifier
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isIdentifier();

    /** Column definition for IsIdentifier */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsIdentifier = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsIdentifier", null);
    /** Column name IsIdentifier */
    public static final String COLUMNNAME_IsIdentifier = "IsIdentifier";

	/**
	 * Set Schlüssel-Spalte.
	 * This column is the key in this table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsKey (boolean IsKey);

	/**
	 * Get Schlüssel-Spalte.
	 * This column is the key in this table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isKey();

    /** Column definition for IsKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsKey = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsKey", null);
    /** Column name IsKey */
    public static final String COLUMNNAME_IsKey = "IsKey";

	/**
	 * Set Lazy loading.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsLazyLoading (boolean IsLazyLoading);

	/**
	 * Get Lazy loading.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isLazyLoading();

    /** Column definition for IsLazyLoading */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsLazyLoading = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsLazyLoading", null);
    /** Column name IsLazyLoading */
    public static final String COLUMNNAME_IsLazyLoading = "IsLazyLoading";

	/**
	 * Set Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMandatory (boolean IsMandatory);

	/**
	 * Get Pflichtangabe.
	 * Data entry is required in this column
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Parent link column.
	 * This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsParent (boolean IsParent);

	/**
	 * Get Parent link column.
	 * This column is a link to the parent table (e.g. header from lines) - incl. Association key columns
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isParent();

    /** Column definition for IsParent */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsParent = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsParent", null);
    /** Column name IsParent */
    public static final String COLUMNNAME_IsParent = "IsParent";

	/**
	 * Set Range filter.
	 * Check if the filter by this column shall render a range component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRangeFilter (boolean IsRangeFilter);

	/**
	 * Get Range filter.
	 * Check if the filter by this column shall render a range component
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRangeFilter();

    /** Column definition for IsRangeFilter */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsRangeFilter = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsRangeFilter", null);
    /** Column name IsRangeFilter */
    public static final String COLUMNNAME_IsRangeFilter = "IsRangeFilter";

	/**
	 * Set Selection Column.
	 * Is this column used for finding rows in windows
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSelectionColumn (boolean IsSelectionColumn);

	/**
	 * Get Selection Column.
	 * Is this column used for finding rows in windows
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSelectionColumn();

    /** Column definition for IsSelectionColumn */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsSelectionColumn = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsSelectionColumn", null);
    /** Column name IsSelectionColumn */
    public static final String COLUMNNAME_IsSelectionColumn = "IsSelectionColumn";

	/**
	 * Set Filter +/- buttons.
	 * Show filter increment/decrement buttons
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsShowFilterIncrementButtons (boolean IsShowFilterIncrementButtons);

	/**
	 * Get Filter +/- buttons.
	 * Show filter increment/decrement buttons
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isShowFilterIncrementButtons();

    /** Column definition for IsShowFilterIncrementButtons */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsShowFilterIncrementButtons = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsShowFilterIncrementButtons", null);
    /** Column name IsShowFilterIncrementButtons */
    public static final String COLUMNNAME_IsShowFilterIncrementButtons = "IsShowFilterIncrementButtons";

	/**
	 * Set Staleable.
	 * If checked then this column is staleable and record needs to be loaded after save.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStaleable (boolean IsStaleable);

	/**
	 * Get Staleable.
	 * If checked then this column is staleable and record needs to be loaded after save.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStaleable();

    /** Column definition for IsStaleable */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsStaleable = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsStaleable", null);
    /** Column name IsStaleable */
    public static final String COLUMNNAME_IsStaleable = "IsStaleable";

	/**
	 * Set Synchronize Database.
	 * Change database table definition when changing dictionary definition
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsSyncDatabase (java.lang.String IsSyncDatabase);

	/**
	 * Get Synchronize Database.
	 * Change database table definition when changing dictionary definition
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getIsSyncDatabase();

    /** Column definition for IsSyncDatabase */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsSyncDatabase = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsSyncDatabase", null);
    /** Column name IsSyncDatabase */
    public static final String COLUMNNAME_IsSyncDatabase = "IsSyncDatabase";

	/**
	 * Set Übersetzt.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsTranslated (boolean IsTranslated);

	/**
	 * Get Übersetzt.
	 * This column is translated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isTranslated();

    /** Column definition for IsTranslated */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsTranslated = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsTranslated", null);
    /** Column name IsTranslated */
    public static final String COLUMNNAME_IsTranslated = "IsTranslated";

	/**
	 * Set Updateable.
	 * Determines, if the field can be updated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUpdateable (boolean IsUpdateable);

	/**
	 * Get Updateable.
	 * Determines, if the field can be updated
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUpdateable();

    /** Column definition for IsUpdateable */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsUpdateable = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsUpdateable", null);
    /** Column name IsUpdateable */
    public static final String COLUMNNAME_IsUpdateable = "IsUpdateable";

	/**
	 * Set Autogenerate document sequence.
	 * In case the field is empty, use standard document sequence to set the value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseDocSequence (boolean IsUseDocSequence);

	/**
	 * Get Autogenerate document sequence.
	 * In case the field is empty, use standard document sequence to set the value
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseDocSequence();

    /** Column definition for IsUseDocSequence */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_IsUseDocSequence = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "IsUseDocSequence", null);
    /** Column name IsUseDocSequence */
    public static final String COLUMNNAME_IsUseDocSequence = "IsUseDocSequence";

	/**
	 * Set Mandatory Logic.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setMandatoryLogic (java.lang.String MandatoryLogic);

	/**
	 * Get Mandatory Logic.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMandatoryLogic();

    /** Column definition for MandatoryLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_MandatoryLogic = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "MandatoryLogic", null);
    /** Column name MandatoryLogic */
    public static final String COLUMNNAME_MandatoryLogic = "MandatoryLogic";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setReadOnlyLogic (java.lang.String ReadOnlyLogic);

	/**
	 * Get Read Only Logic.
	 * Logic to determine if field is read only (applies only when field is read-write)
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getReadOnlyLogic();

    /** Column definition for ReadOnlyLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_ReadOnlyLogic = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "ReadOnlyLogic", null);
    /** Column name ReadOnlyLogic */
    public static final String COLUMNNAME_ReadOnlyLogic = "ReadOnlyLogic";

	/**
	 * Set Selection column ordering.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSelectionColumnSeqNo (int SelectionColumnSeqNo);

	/**
	 * Get Selection column ordering.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getSelectionColumnSeqNo();

    /** Column definition for SelectionColumnSeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_SelectionColumnSeqNo = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "SelectionColumnSeqNo", null);
    /** Column name SelectionColumnSeqNo */
    public static final String COLUMNNAME_SelectionColumnSeqNo = "SelectionColumnSeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Column, org.compiere.model.I_AD_User>(I_AD_Column.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Max. Wert.
	 * Maximum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueMax (java.lang.String ValueMax);

	/**
	 * Get Max. Wert.
	 * Maximum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValueMax();

    /** Column definition for ValueMax */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_ValueMax = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "ValueMax", null);
    /** Column name ValueMax */
    public static final String COLUMNNAME_ValueMax = "ValueMax";

	/**
	 * Set Min. Wert.
	 * Minimum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setValueMin (java.lang.String ValueMin);

	/**
	 * Get Min. Wert.
	 * Minimum Value for a field
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValueMin();

    /** Column definition for ValueMin */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_ValueMin = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "ValueMin", null);
    /** Column name ValueMin */
    public static final String COLUMNNAME_ValueMin = "ValueMin";

	/**
	 * Set Version.
	 * Version of the table definition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVersion (java.math.BigDecimal Version);

	/**
	 * Get Version.
	 * Version of the table definition
	 *
	 * <br>Type: Amount
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getVersion();

    /** Column definition for Version */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_Version = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "Version", null);
    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/**
	 * Set Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setVFormat (java.lang.String VFormat);

	/**
	 * Get Value Format.
	 * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVFormat();

    /** Column definition for VFormat */
    public static final org.adempiere.model.ModelColumn<I_AD_Column, Object> COLUMN_VFormat = new org.adempiere.model.ModelColumn<I_AD_Column, Object>(I_AD_Column.class, "VFormat", null);
    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";
}
