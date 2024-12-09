package org.compiere.model;

<<<<<<< HEAD

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
=======
import java.math.BigDecimal;
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Field 
{

	String Table_Name = "AD_Field";

//	/** AD_Table_ID=107 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Client>(I_AD_Field.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Spalte.
	 * Column in the table
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Link Column.
	 * Link Column for Multi-Parent tables
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Spalte.
	 * Column in the table
=======
	void setAD_Column_ID (int AD_Column_ID);

	/**
	 * Get Link Column.
	 * Link Column for Multi-Parent tables
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	int getAD_Column_ID();

	org.compiere.model.I_AD_Column getAD_Column();

	void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Column> COLUMN_AD_Column_ID = new ModelColumn<>(I_AD_Field.class, "AD_Column_ID", org.compiere.model.I_AD_Column.class);
	String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/**
	 * Set Field Group.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_FieldGroup_ID (int AD_FieldGroup_ID);

	/**
	 * Get Feld-Gruppe.
=======
	void setAD_FieldGroup_ID (int AD_FieldGroup_ID);

	/**
	 * Get Field Group.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Logical grouping of fields
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	int getAD_FieldGroup_ID();

	@Nullable org.compiere.model.I_AD_FieldGroup getAD_FieldGroup();

	void setAD_FieldGroup(@Nullable org.compiere.model.I_AD_FieldGroup AD_FieldGroup);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_FieldGroup> COLUMN_AD_FieldGroup_ID = new ModelColumn<>(I_AD_Field.class, "AD_FieldGroup_ID", org.compiere.model.I_AD_FieldGroup.class);
	String COLUMNNAME_AD_FieldGroup_ID = "AD_FieldGroup_ID";

	/**
	 * Set Field.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Field_ID (int AD_Field_ID);

	/**
	 * Get Field.
	 * Field on a database table
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Field_ID();

	ModelColumn<I_AD_Field, Object> COLUMN_AD_Field_ID = new ModelColumn<>(I_AD_Field.class, "AD_Field_ID", null);
	String COLUMNNAME_AD_Field_ID = "AD_Field_ID";

	/**
	 * Set AD_Name_ID.
	 * This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Name_ID (int AD_Name_ID);

	/**
	 * Get AD_Name_ID.
	 * This is an AD_Element_ID that can does not need to have a ColumnName and can be used to name things
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Name_ID();

	@Nullable org.compiere.model.I_AD_Element getAD_Name();

	void setAD_Name(@Nullable org.compiere.model.I_AD_Element AD_Name);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Element> COLUMN_AD_Name_ID = new ModelColumn<>(I_AD_Field.class, "AD_Name_ID", org.compiere.model.I_AD_Element.class);
	String COLUMNNAME_AD_Name_ID = "AD_Name_ID";

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
	 * Set Reference Overwrite.
	 * System Reference - optional Overwrite
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Referenz.
	 * System Reference and Validation
=======
	void setAD_Reference_ID (int AD_Reference_ID);

	/**
	 * Get Reference Overwrite.
	 * System Reference - optional Overwrite
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Reference_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference();

	public void setAD_Reference(org.compiere.model.I_AD_Reference AD_Reference);

    /** Column definition for AD_Reference_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference>(I_AD_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_ID */
    public static final String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Referenzschlüssel.
=======
	int getAD_Reference_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference();

	void setAD_Reference(@Nullable org.compiere.model.I_AD_Reference AD_Reference);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new ModelColumn<>(I_AD_Field.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_ID = "AD_Reference_ID";

	/**
	 * Set Reference Key.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Referenzschlüssel.
=======
	void setAD_Reference_Value_ID (int AD_Reference_Value_ID);

	/**
	 * Get Reference Key.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Required to specify, if data type is Table or List
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Reference_Value_ID();

	public org.compiere.model.I_AD_Reference getAD_Reference_Value();

	public void setAD_Reference_Value(org.compiere.model.I_AD_Reference AD_Reference_Value);

    /** Column definition for AD_Reference_Value_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference>(I_AD_Field.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set Register.
=======
	int getAD_Reference_Value_ID();

	@Nullable org.compiere.model.I_AD_Reference getAD_Reference_Value();

	void setAD_Reference_Value(@Nullable org.compiere.model.I_AD_Reference AD_Reference_Value);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new ModelColumn<>(I_AD_Field.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
	String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

	/**
	 * Set Tab.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Register.
=======
	void setAD_Tab_ID (int AD_Tab_ID);

	/**
	 * Get Tab.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Tab within a Window
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	int getAD_Tab_ID();

	org.compiere.model.I_AD_Tab getAD_Tab();

	void setAD_Tab(org.compiere.model.I_AD_Tab AD_Tab);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_AD_Tab_ID = new ModelColumn<>(I_AD_Field.class, "AD_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_AD_Tab_ID = "AD_Tab_ID";

	/**
	 * Set Validation Rule.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Dynamische Validierung.
	 * Dynamic Validation Rule
=======
	void setAD_Val_Rule_ID (int AD_Val_Rule_ID);

	/**
	 * Get Validation Rule.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Val_Rule_ID();

	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	public void setAD_Val_Rule(org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

    /** Column definition for AD_Val_Rule_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule>(I_AD_Field.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";
=======
	int getAD_Val_Rule_ID();

	@Nullable org.compiere.model.I_AD_Val_Rule getAD_Val_Rule();

	void setAD_Val_Rule(@Nullable org.compiere.model.I_AD_Val_Rule AD_Val_Rule);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new ModelColumn<>(I_AD_Field.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
	String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setColorLogic (java.lang.String ColorLogic);
=======
	void setColorLogic (@Nullable java.lang.String ColorLogic);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Color Logic.
	 * Color used for background
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getColorLogic();

    /** Column definition for ColorLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ColorLogic = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ColorLogic", null);
    /** Column name ColorLogic */
    public static final String COLUMNNAME_ColorLogic = "ColorLogic";
=======
	@Nullable java.lang.String getColorLogic();

	ModelColumn<I_AD_Field, Object> COLUMN_ColorLogic = new ModelColumn<>(I_AD_Field.class, "ColorLogic", null);
	String COLUMNNAME_ColorLogic = "ColorLogic";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setColumnDisplayLength (int ColumnDisplayLength);
=======
	void setColumnDisplayLength (int ColumnDisplayLength);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Column Display Length.
	 * Column display length for grid mode
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getColumnDisplayLength();

    /** Column definition for ColumnDisplayLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ColumnDisplayLength = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ColumnDisplayLength", null);
    /** Column name ColumnDisplayLength */
    public static final String COLUMNNAME_ColumnDisplayLength = "ColumnDisplayLength";

	/**
	 * Get Erstellt.
=======
	int getColumnDisplayLength();

	ModelColumn<I_AD_Field, Object> COLUMN_ColumnDisplayLength = new ModelColumn<>(I_AD_Field.class, "ColumnDisplayLength", null);
	String COLUMNNAME_ColumnDisplayLength = "ColumnDisplayLength";

	/**
	 * Get Created.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Field, Object> COLUMN_Created = new ModelColumn<>(I_AD_Field.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User>(I_AD_Field.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Standardwert-Logik.
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Default Logic.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDefaultValue (java.lang.String DefaultValue);

	/**
	 * Get Standardwert-Logik.
=======
	void setDefaultValue (@Nullable java.lang.String DefaultValue);

	/**
	 * Get Default Logic.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDefaultValue();

    /** Column definition for DefaultValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DefaultValue = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DefaultValue", null);
    /** Column name DefaultValue */
    public static final String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Beschreibung.
=======
	@Nullable java.lang.String getDefaultValue();

	ModelColumn<I_AD_Field, Object> COLUMN_DefaultValue = new ModelColumn<>(I_AD_Field.class, "DefaultValue", null);
	String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
=======
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Anzeigelänge.
=======
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Field, Object> COLUMN_Description = new ModelColumn<>(I_AD_Field.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Display Length.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDisplayLength (int DisplayLength);

	/**
	 * Get Anzeigelänge.
=======
	void setDisplayLength (int DisplayLength);

	/**
	 * Get Display Length.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Length of the display in characters
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getDisplayLength();

    /** Column definition for DisplayLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DisplayLength = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DisplayLength", null);
    /** Column name DisplayLength */
    public static final String COLUMNNAME_DisplayLength = "DisplayLength";

	/**
	 * Set Anzeigelogik.
=======
	int getDisplayLength();

	ModelColumn<I_AD_Field, Object> COLUMN_DisplayLength = new ModelColumn<>(I_AD_Field.class, "DisplayLength", null);
	String COLUMNNAME_DisplayLength = "DisplayLength";

	/**
	 * Set Display Logic.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setDisplayLogic (java.lang.String DisplayLogic);

	/**
	 * Get Anzeigelogik.
=======
	void setDisplayLogic (@Nullable java.lang.String DisplayLogic);

	/**
	 * Get Display Logic.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * If the Field is displayed, the result determines if the field is actually displayed
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getDisplayLogic();

    /** Column definition for DisplayLogic */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_DisplayLogic = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "DisplayLogic", null);
    /** Column name DisplayLogic */
    public static final String COLUMNNAME_DisplayLogic = "DisplayLogic";

	/**
	 * Set Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
=======
	@Nullable java.lang.String getDisplayLogic();

	ModelColumn<I_AD_Field, Object> COLUMN_DisplayLogic = new ModelColumn<>(I_AD_Field.class, "DisplayLogic", null);
	String COLUMNNAME_DisplayLogic = "DisplayLogic";

	/**
	 * Set Entity Type.
	 * Entity Type
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entitäts-Art.
	 * Dictionary Entity Type;
 Determines ownership and synchronization
=======
	void setEntityType (java.lang.String EntityType);

	/**
	 * Get Entity Type.
	 * Entity Type
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEntityType();

    /** Column definition for EntityType */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Kommentar/Hilfe.
=======
	java.lang.String getEntityType();

	ModelColumn<I_AD_Field, Object> COLUMN_EntityType = new ModelColumn<>(I_AD_Field.class, "EntityType", null);
	String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Facet Filter Sequence No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFacetFilterSeqNo (int FacetFilterSeqNo);

	/**
	 * Get Facet Filter Sequence No.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getFacetFilterSeqNo();

	ModelColumn<I_AD_Field, Object> COLUMN_FacetFilterSeqNo = new ModelColumn<>(I_AD_Field.class, "FacetFilterSeqNo", null);
	String COLUMNNAME_FacetFilterSeqNo = "FacetFilterSeqNo";

	/**
	 * Set Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFilterDefaultValue (@Nullable java.lang.String FilterDefaultValue);

	/**
	 * Get Filter Default Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFilterDefaultValue();

	ModelColumn<I_AD_Field, Object> COLUMN_FilterDefaultValue = new ModelColumn<>(I_AD_Field.class, "FilterDefaultValue", null);
	String COLUMNNAME_FilterDefaultValue = "FilterDefaultValue";

	/**
	 * Set Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setFilterOperator (@Nullable java.lang.String FilterOperator);

	/**
	 * Get Filter Operator.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getFilterOperator();

	ModelColumn<I_AD_Field, Object> COLUMN_FilterOperator = new ModelColumn<>(I_AD_Field.class, "FilterOperator", null);
	String COLUMNNAME_FilterOperator = "FilterOperator";

	/**
	 * Set Help.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setHelp (java.lang.String Help);

	/**
	 * Get Kommentar/Hilfe.
=======
	void setHelp (@Nullable java.lang.String Help);

	/**
	 * Get Help.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Comment or Hint
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	@Nullable java.lang.String getHelp();

	ModelColumn<I_AD_Field, Object> COLUMN_Help = new ModelColumn<>(I_AD_Field.class, "Help", null);
	String COLUMNNAME_Help = "Help";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIncludedTabHeight (int IncludedTabHeight);
=======
	void setIncludedTabHeight (int IncludedTabHeight);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Included Tab Height.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getIncludedTabHeight();

    /** Column definition for IncludedTabHeight */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IncludedTabHeight = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IncludedTabHeight", null);
    /** Column name IncludedTabHeight */
    public static final String COLUMNNAME_IncludedTabHeight = "IncludedTabHeight";
=======
	int getIncludedTabHeight();

	ModelColumn<I_AD_Field, Object> COLUMN_IncludedTabHeight = new ModelColumn<>(I_AD_Field.class, "IncludedTabHeight", null);
	String COLUMNNAME_IncludedTabHeight = "IncludedTabHeight";

	/**
	 * Set Included Tab.
	 * Included Tab in this Tab (Master Detail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIncluded_Tab_ID (int Included_Tab_ID);

	/**
	 * Get Included Tab.
	 * Included Tab in this Tab (Master Detail)
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getIncluded_Tab_ID();

	@Nullable org.compiere.model.I_AD_Tab getIncluded_Tab();

	void setIncluded_Tab(@Nullable org.compiere.model.I_AD_Tab Included_Tab);

	ModelColumn<I_AD_Field, org.compiere.model.I_AD_Tab> COLUMN_Included_Tab_ID = new ModelColumn<>(I_AD_Field.class, "Included_Tab_ID", org.compiere.model.I_AD_Tab.class);
	String COLUMNNAME_Included_Tab_ID = "Included_Tab_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setInfoFactoryClass (java.lang.String InfoFactoryClass);
=======
	void setInfoFactoryClass (@Nullable java.lang.String InfoFactoryClass);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Info Factory Class.
	 * Fully qualified class name that implements the InfoFactory interface
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getInfoFactoryClass();

    /** Column definition for InfoFactoryClass */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_InfoFactoryClass = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "InfoFactoryClass", null);
    /** Column name InfoFactoryClass */
    public static final String COLUMNNAME_InfoFactoryClass = "InfoFactoryClass";

	/**
	 * Set Aktiv.
=======
	@Nullable java.lang.String getInfoFactoryClass();

	ModelColumn<I_AD_Field, Object> COLUMN_InfoFactoryClass = new ModelColumn<>(I_AD_Field.class, "InfoFactoryClass", null);
	String COLUMNNAME_InfoFactoryClass = "InfoFactoryClass";

	/**
	 * Set Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";
=======
	boolean isActive();

	ModelColumn<I_AD_Field, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Field.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsDisplayed (boolean IsDisplayed);
=======
	void setIsDisplayed (boolean IsDisplayed);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Displayed.
	 * Determines, if this field is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isDisplayed();

    /** Column definition for IsDisplayed */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayed = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsDisplayed", null);
    /** Column name IsDisplayed */
    public static final String COLUMNNAME_IsDisplayed = "IsDisplayed";
=======
	boolean isDisplayed();

	ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayed = new ModelColumn<>(I_AD_Field.class, "IsDisplayed", null);
	String COLUMNNAME_IsDisplayed = "IsDisplayed";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsDisplayedGrid (boolean IsDisplayedGrid);
=======
	void setIsDisplayedGrid (boolean IsDisplayedGrid);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Displayed in Grid.
	 * Determines, if this field is displayed in grid mode
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isDisplayedGrid();

    /** Column definition for IsDisplayedGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayedGrid = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsDisplayedGrid", null);
    /** Column name IsDisplayedGrid */
    public static final String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/**
	 * Set Encrypted.
	 * Display or Storage is encrypted
=======
	boolean isDisplayedGrid();

	ModelColumn<I_AD_Field, Object> COLUMN_IsDisplayedGrid = new ModelColumn<>(I_AD_Field.class, "IsDisplayedGrid", null);
	String COLUMNNAME_IsDisplayedGrid = "IsDisplayedGrid";

	/**
	 * Set Column Encryption.
	 * Test and enable Column Encryption
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsEncrypted (boolean IsEncrypted);

	/**
	 * Get Encrypted.
	 * Display or Storage is encrypted
=======
	void setIsEncrypted (boolean IsEncrypted);

	/**
	 * Get Column Encryption.
	 * Test and enable Column Encryption
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isEncrypted();

    /** Column definition for IsEncrypted */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsEncrypted = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsEncrypted", null);
    /** Column name IsEncrypted */
    public static final String COLUMNNAME_IsEncrypted = "IsEncrypted";
=======
	boolean isEncrypted();

	ModelColumn<I_AD_Field, Object> COLUMN_IsEncrypted = new ModelColumn<>(I_AD_Field.class, "IsEncrypted", null);
	String COLUMNNAME_IsEncrypted = "IsEncrypted";

	/**
	 * Set Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsExcludeFromZoomTargets (@Nullable java.lang.String IsExcludeFromZoomTargets);

	/**
	 * Get Exclude from Zoom Targets.
	 * Exclude from zoom targets / related documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsExcludeFromZoomTargets();

	ModelColumn<I_AD_Field, Object> COLUMN_IsExcludeFromZoomTargets = new ModelColumn<>(I_AD_Field.class, "IsExcludeFromZoomTargets", null);
	String COLUMNNAME_IsExcludeFromZoomTargets = "IsExcludeFromZoomTargets";

	/**
	 * Set Facet Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsFacetFilter (@Nullable java.lang.String IsFacetFilter);

	/**
	 * Get Facet Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsFacetFilter();

	ModelColumn<I_AD_Field, Object> COLUMN_IsFacetFilter = new ModelColumn<>(I_AD_Field.class, "IsFacetFilter", null);
	String COLUMNNAME_IsFacetFilter = "IsFacetFilter";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsFieldOnly (boolean IsFieldOnly);
=======
	void setIsFieldOnly (boolean IsFieldOnly);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Field Only.
	 * Label is not displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isFieldOnly();

    /** Column definition for IsFieldOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsFieldOnly = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsFieldOnly", null);
    /** Column name IsFieldOnly */
    public static final String COLUMNNAME_IsFieldOnly = "IsFieldOnly";
=======
	boolean isFieldOnly();

	ModelColumn<I_AD_Field, Object> COLUMN_IsFieldOnly = new ModelColumn<>(I_AD_Field.class, "IsFieldOnly", null);
	String COLUMNNAME_IsFieldOnly = "IsFieldOnly";

	/**
	 * Set Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsFilterField (@Nullable java.lang.String IsFilterField);

	/**
	 * Get Filter.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsFilterField();

	ModelColumn<I_AD_Field, Object> COLUMN_IsFilterField = new ModelColumn<>(I_AD_Field.class, "IsFilterField", null);
	String COLUMNNAME_IsFilterField = "IsFilterField";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsHeading (boolean IsHeading);
=======
	void setIsHeading (boolean IsHeading);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Heading only.
	 * Field without Column - Only label is displayed
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isHeading();

    /** Column definition for IsHeading */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsHeading = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsHeading", null);
    /** Column name IsHeading */
    public static final String COLUMNNAME_IsHeading = "IsHeading";

	/**
	 * Set Pflichtangabe.
=======
	boolean isHeading();

	ModelColumn<I_AD_Field, Object> COLUMN_IsHeading = new ModelColumn<>(I_AD_Field.class, "IsHeading", null);
	String COLUMNNAME_IsHeading = "IsHeading";

	/**
	 * Set mandatory.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsMandatory (java.lang.String IsMandatory);

	/**
	 * Get Pflichtangabe.
=======
	void setIsMandatory (@Nullable java.lang.String IsMandatory);

	/**
	 * Get mandatory.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Data entry is required in this column
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getIsMandatory();

    /** Column definition for IsMandatory */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Schreibgeschützt.
	 * Field is read only
=======
	@Nullable java.lang.String getIsMandatory();

	ModelColumn<I_AD_Field, Object> COLUMN_IsMandatory = new ModelColumn<>(I_AD_Field.class, "IsMandatory", null);
	String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Override Filter Default Value.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get Schreibgeschützt.
	 * Field is read only
=======
	void setIsOverrideFilterDefaultValue (boolean IsOverrideFilterDefaultValue);

	/**
	 * Get Override Filter Default Value.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isReadOnly();

    /** Column definition for IsReadOnly */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_IsReadOnly = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "IsReadOnly", null);
    /** Column name IsReadOnly */
    public static final String COLUMNNAME_IsReadOnly = "IsReadOnly";
=======
	boolean isOverrideFilterDefaultValue();

	ModelColumn<I_AD_Field, Object> COLUMN_IsOverrideFilterDefaultValue = new ModelColumn<>(I_AD_Field.class, "IsOverrideFilterDefaultValue", null);
	String COLUMNNAME_IsOverrideFilterDefaultValue = "IsOverrideFilterDefaultValue";

	/**
	 * Set readonly.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsReadOnly (boolean IsReadOnly);

	/**
	 * Get readonly.
	 * Feld / Eintrag / Berecih ist schreibgeschützt
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isReadOnly();

	ModelColumn<I_AD_Field, Object> COLUMN_IsReadOnly = new ModelColumn<>(I_AD_Field.class, "IsReadOnly", null);
	String COLUMNNAME_IsReadOnly = "IsReadOnly";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsSameLine (boolean IsSameLine);
=======
	void setIsSameLine (boolean IsSameLine);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Same Line.
	 * Displayed on same line as previous field
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
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
=======
	boolean isSameLine();

	ModelColumn<I_AD_Field, Object> COLUMN_IsSameLine = new ModelColumn<>(I_AD_Field.class, "IsSameLine", null);
	String COLUMNNAME_IsSameLine = "IsSameLine";

	/**
	 * Set Show filter inline.
	 * Renders the filter as a component (e.g. text box) directly in the filters lane
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setIsShowFilterInline (@Nullable java.lang.String IsShowFilterInline);

	/**
	 * Get Show filter inline.
	 * Renders the filter as a component (e.g. text box) directly in the filters lane
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getIsShowFilterInline();

	ModelColumn<I_AD_Field, Object> COLUMN_IsShowFilterInline = new ModelColumn<>(I_AD_Field.class, "IsShowFilterInline", null);
	String COLUMNNAME_IsShowFilterInline = "IsShowFilterInline";

	/**
	 * Set Maximum Facets.
	 * Maximum number of facets to fetch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMaxFacetsToFetch (int MaxFacetsToFetch);

	/**
	 * Get Maximum Facets.
	 * Maximum number of facets to fetch
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getMaxFacetsToFetch();

	ModelColumn<I_AD_Field, Object> COLUMN_MaxFacetsToFetch = new ModelColumn<>(I_AD_Field.class, "MaxFacetsToFetch", null);
	String COLUMNNAME_MaxFacetsToFetch = "MaxFacetsToFetch";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setName (@Nullable java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getName();

	ModelColumn<I_AD_Field, Object> COLUMN_Name = new ModelColumn<>(I_AD_Field.class, "Name", null);
	String COLUMNNAME_Name = "Name";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setObscureType (java.lang.String ObscureType);
=======
	void setObscureType (@Nullable java.lang.String ObscureType);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Obscure.
	 * Type of obscuring the data (limiting the display)
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getObscureType();

    /** Column definition for ObscureType */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_ObscureType = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "ObscureType", null);
    /** Column name ObscureType */
    public static final String COLUMNNAME_ObscureType = "ObscureType";

	/**
	 * Set Reihenfolge.
=======
	@Nullable java.lang.String getObscureType();

	ModelColumn<I_AD_Field, Object> COLUMN_ObscureType = new ModelColumn<>(I_AD_Field.class, "ObscureType", null);
	String COLUMNNAME_ObscureType = "ObscureType";

	/**
	 * Set Selection column ordering.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSelectionColumnSeqNo (@Nullable BigDecimal SelectionColumnSeqNo);

	/**
	 * Get Selection column ordering.
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	BigDecimal getSelectionColumnSeqNo();

	ModelColumn<I_AD_Field, Object> COLUMN_SelectionColumnSeqNo = new ModelColumn<>(I_AD_Field.class, "SelectionColumnSeqNo", null);
	String COLUMNNAME_SelectionColumnSeqNo = "SelectionColumnSeqNo";

	/**
	 * Set SeqNo.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
=======
	void setSeqNo (int SeqNo);

	/**
	 * Get SeqNo.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Method of ordering records;
 lowest number comes first
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";
=======
	int getSeqNo();

	ModelColumn<I_AD_Field, Object> COLUMN_SeqNo = new ModelColumn<>(I_AD_Field.class, "SeqNo", null);
	String COLUMNNAME_SeqNo = "SeqNo";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setSeqNoGrid (int SeqNoGrid);
=======
	void setSeqNoGrid (int SeqNoGrid);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Reihenfolge (grid).
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getSeqNoGrid();

    /** Column definition for SeqNoGrid */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SeqNoGrid = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SeqNoGrid", null);
    /** Column name SeqNoGrid */
    public static final String COLUMNNAME_SeqNoGrid = "SeqNoGrid";
=======
	int getSeqNoGrid();

	ModelColumn<I_AD_Field, Object> COLUMN_SeqNoGrid = new ModelColumn<>(I_AD_Field.class, "SeqNoGrid", null);
	String COLUMNNAME_SeqNoGrid = "SeqNoGrid";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setSortNo (java.math.BigDecimal SortNo);
=======
	void setSortNo (@Nullable BigDecimal SortNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Record Sort No.
	 * Determines in what order the records are displayed
	 *
	 * <br>Type: Number
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.math.BigDecimal getSortNo();

    /** Column definition for SortNo */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SortNo = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SortNo", null);
    /** Column name SortNo */
    public static final String COLUMNNAME_SortNo = "SortNo";
=======
	BigDecimal getSortNo();

	ModelColumn<I_AD_Field, Object> COLUMN_SortNo = new ModelColumn<>(I_AD_Field.class, "SortNo", null);
	String COLUMNNAME_SortNo = "SortNo";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setSpanX (int SpanX);
=======
	void setSpanX (int SpanX);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Column span.
	 * Number of columns spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getSpanX();

    /** Column definition for SpanX */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SpanX = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SpanX", null);
    /** Column name SpanX */
    public static final String COLUMNNAME_SpanX = "SpanX";
=======
	int getSpanX();

	ModelColumn<I_AD_Field, Object> COLUMN_SpanX = new ModelColumn<>(I_AD_Field.class, "SpanX", null);
	String COLUMNNAME_SpanX = "SpanX";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setSpanY (int SpanY);
=======
	void setSpanY (int SpanY);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Row Span.
	 * Number of rows spanned
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getSpanY();

    /** Column definition for SpanY */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_SpanY = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "SpanY", null);
    /** Column name SpanY */
    public static final String COLUMNNAME_SpanY = "SpanY";

	/**
	 * Get Aktualisiert.
=======
	int getSpanY();

	ModelColumn<I_AD_Field, Object> COLUMN_SpanY = new ModelColumn<>(I_AD_Field.class, "SpanY", null);
	String COLUMNNAME_SpanY = "SpanY";

	/**
	 * Get Updated.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Field, Object>(I_AD_Field.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Field, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Field.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Field, org.compiere.model.I_AD_User>(I_AD_Field.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
