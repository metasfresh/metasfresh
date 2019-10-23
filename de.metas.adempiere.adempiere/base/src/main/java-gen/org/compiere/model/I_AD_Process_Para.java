package org.compiere.model;


/** Generated Interface for AD_Process_Para
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Process_Para 
{

    /** TableName=AD_Process_Para */
    public static final String Table_Name = "AD_Process_Para";

    /** AD_Table_ID=285 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Client>(I_AD_Process_Para.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Element_ID (int AD_Element_ID);

	/**
	 * Get System-Element.
	 * System Element enables the central maintenance of column description and help.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Element_ID();

	public org.compiere.model.I_AD_Element getAD_Element();

	public void setAD_Element(org.compiere.model.I_AD_Element AD_Element);

    /** Column definition for AD_Element_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Element> COLUMN_AD_Element_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Element>(I_AD_Process_Para.class, "AD_Element_ID", org.compiere.model.I_AD_Element.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Org>(I_AD_Process_Para.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_ID (int AD_Process_ID);

	/**
	 * Get Prozess.
	 * Process or Report
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_ID();

	public org.compiere.model.I_AD_Process getAD_Process();

	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process);

    /** Column definition for AD_Process_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Process> COLUMN_AD_Process_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Process>(I_AD_Process_Para.class, "AD_Process_ID", org.compiere.model.I_AD_Process.class);
    /** Column name AD_Process_ID */
    public static final String COLUMNNAME_AD_Process_ID = "AD_Process_ID";

	/**
	 * Set Prozess-Parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Process_Para_ID (int AD_Process_Para_ID);

	/**
	 * Get Prozess-Parameter.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Process_Para_ID();

    /** Column definition for AD_Process_Para_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_AD_Process_Para_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "AD_Process_Para_ID", null);
    /** Column name AD_Process_Para_ID */
    public static final String COLUMNNAME_AD_Process_Para_ID = "AD_Process_Para_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference>(I_AD_Process_Para.class, "AD_Reference_ID", org.compiere.model.I_AD_Reference.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference> COLUMN_AD_Reference_Value_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Reference>(I_AD_Process_Para.class, "AD_Reference_Value_ID", org.compiere.model.I_AD_Reference.class);
    /** Column name AD_Reference_Value_ID */
    public static final String COLUMNNAME_AD_Reference_Value_ID = "AD_Reference_Value_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Val_Rule> COLUMN_AD_Val_Rule_ID = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_Val_Rule>(I_AD_Process_Para.class, "AD_Val_Rule_ID", org.compiere.model.I_AD_Val_Rule.class);
    /** Column name AD_Val_Rule_ID */
    public static final String COLUMNNAME_AD_Val_Rule_ID = "AD_Val_Rule_ID";

	/**
	 * Set Barcode Scanner Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBarcodeScannerType (java.lang.String BarcodeScannerType);

	/**
	 * Get Barcode Scanner Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getBarcodeScannerType();

    /** Column definition for BarcodeScannerType */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_BarcodeScannerType = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "BarcodeScannerType", null);
    /** Column name BarcodeScannerType */
    public static final String COLUMNNAME_BarcodeScannerType = "BarcodeScannerType";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_ColumnName = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "ColumnName", null);
    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_User>(I_AD_Process_Para.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Standardwert-Logik.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefaultValue (java.lang.String DefaultValue);

	/**
	 * Get Standardwert-Logik.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefaultValue();

    /** Column definition for DefaultValue */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_DefaultValue = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "DefaultValue", null);
    /** Column name DefaultValue */
    public static final String COLUMNNAME_DefaultValue = "DefaultValue";

	/**
	 * Set Default Logic 2.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDefaultValue2 (java.lang.String DefaultValue2);

	/**
	 * Get Default Logic 2.
	 * Default value hierarchy, separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefaultValue2();

    /** Column definition for DefaultValue2 */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_DefaultValue2 = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "DefaultValue2", null);
    /** Column name DefaultValue2 */
    public static final String COLUMNNAME_DefaultValue2 = "DefaultValue2";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_DisplayLogic = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "DisplayLogic", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_EntityType = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "EntityType", null);
    /** Column name EntityType */
    public static final String COLUMNNAME_EntityType = "EntityType";

	/**
	 * Set Länge.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFieldLength (int FieldLength);

	/**
	 * Get Länge.
	 * Length of the column in the database
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getFieldLength();

    /** Column definition for FieldLength */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_FieldLength = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "FieldLength", null);
    /** Column name FieldLength */
    public static final String COLUMNNAME_FieldLength = "FieldLength";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_Help = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "Help", null);
    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsAutocomplete (boolean IsAutocomplete);

	/**
	 * Get Autocomplete.
	 * Automatic completion for textfields
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isAutocomplete();

    /** Column definition for IsAutocomplete */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsAutocomplete = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsAutocomplete", null);
    /** Column name IsAutocomplete */
    public static final String COLUMNNAME_IsAutocomplete = "IsAutocomplete";

	/**
	 * Set Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/**
	 * Get Zentral verwaltet.
	 * Information maintained in System Element table
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCentrallyMaintained();

    /** Column definition for IsCentrallyMaintained */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsCentrallyMaintained = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsCentrallyMaintained", null);
    /** Column name IsCentrallyMaintained */
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsEncrypted = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsEncrypted", null);
    /** Column name IsEncrypted */
    public static final String COLUMNNAME_IsEncrypted = "IsEncrypted";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

	/**
	 * Set Range.
	 * The parameter is a range of values
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsRange (boolean IsRange);

	/**
	 * Get Range.
	 * The parameter is a range of values
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isRange();

    /** Column definition for IsRange */
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_IsRange = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "IsRange", null);
    /** Column name IsRange */
    public static final String COLUMNNAME_IsRange = "IsRange";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "Name", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_ReadOnlyLogic = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "ReadOnlyLogic", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Process_Para, org.compiere.model.I_AD_User>(I_AD_Process_Para.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_ValueMax = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "ValueMax", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_ValueMin = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "ValueMin", null);
    /** Column name ValueMin */
    public static final String COLUMNNAME_ValueMin = "ValueMin";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Process_Para, Object> COLUMN_VFormat = new org.adempiere.model.ModelColumn<I_AD_Process_Para, Object>(I_AD_Process_Para.class, "VFormat", null);
    /** Column name VFormat */
    public static final String COLUMNNAME_VFormat = "VFormat";
}
