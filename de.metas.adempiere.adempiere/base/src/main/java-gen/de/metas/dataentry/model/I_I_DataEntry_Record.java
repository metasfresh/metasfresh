package de.metas.dataentry.model;


/** Generated Interface for I_DataEntry_Record
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_I_DataEntry_Record 
{

    /** TableName=I_DataEntry_Record */
    public static final String Table_Name = "I_DataEntry_Record";

    /** AD_Table_ID=541372 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Client>(I_I_DataEntry_Record.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Org>(I_I_DataEntry_Record.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Table>(I_I_DataEntry_Record.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Window> COLUMN_AD_Window_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_Window>(I_I_DataEntry_Record.class, "AD_Window_ID", org.compiere.model.I_AD_Window.class);
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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_User>(I_I_DataEntry_Record.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dateneingabefeld.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Field_ID (int DataEntry_Field_ID);

	/**
	 * Get Dateneingabefeld.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Field_ID();

	public de.metas.dataentry.model.I_DataEntry_Field getDataEntry_Field();

	public void setDataEntry_Field(de.metas.dataentry.model.I_DataEntry_Field DataEntry_Field);

    /** Column definition for DataEntry_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Field> COLUMN_DataEntry_Field_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Field>(I_I_DataEntry_Record.class, "DataEntry_Field_ID", de.metas.dataentry.model.I_DataEntry_Field.class);
    /** Column name DataEntry_Field_ID */
    public static final String COLUMNNAME_DataEntry_Field_ID = "DataEntry_Field_ID";

	/**
	 * Set Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Line_ID (int DataEntry_Line_ID);

	/**
	 * Get Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Line_ID();

	public de.metas.dataentry.model.I_DataEntry_Line getDataEntry_Line();

	public void setDataEntry_Line(de.metas.dataentry.model.I_DataEntry_Line DataEntry_Line);

    /** Column definition for DataEntry_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Line> COLUMN_DataEntry_Line_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Line>(I_I_DataEntry_Record.class, "DataEntry_Line_ID", de.metas.dataentry.model.I_DataEntry_Line.class);
    /** Column name DataEntry_Line_ID */
    public static final String COLUMNNAME_DataEntry_Line_ID = "DataEntry_Line_ID";

	/**
	 * Set Zeile Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Line_Name (java.lang.String DataEntry_Line_Name);

	/**
	 * Get Zeile Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_Line_Name();

    /** Column definition for DataEntry_Line_Name */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_DataEntry_Line_Name = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "DataEntry_Line_Name", null);
    /** Column name DataEntry_Line_Name */
    public static final String COLUMNNAME_DataEntry_Line_Name = "DataEntry_Line_Name";

	/**
	 * Set DataEntry_Record.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Record_ID (int DataEntry_Record_ID);

	/**
	 * Get DataEntry_Record.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Record_ID();

	public de.metas.dataentry.model.I_DataEntry_Record getDataEntry_Record();

	public void setDataEntry_Record(de.metas.dataentry.model.I_DataEntry_Record DataEntry_Record);

    /** Column definition for DataEntry_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Record> COLUMN_DataEntry_Record_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Record>(I_I_DataEntry_Record.class, "DataEntry_Record_ID", de.metas.dataentry.model.I_DataEntry_Record.class);
    /** Column name DataEntry_Record_ID */
    public static final String COLUMNNAME_DataEntry_Record_ID = "DataEntry_Record_ID";

	/**
	 * Set Sektion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Section_ID (int DataEntry_Section_ID);

	/**
	 * Get Sektion.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Section_ID();

	public de.metas.dataentry.model.I_DataEntry_Section getDataEntry_Section();

	public void setDataEntry_Section(de.metas.dataentry.model.I_DataEntry_Section DataEntry_Section);

    /** Column definition for DataEntry_Section_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Section> COLUMN_DataEntry_Section_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Section>(I_I_DataEntry_Record.class, "DataEntry_Section_ID", de.metas.dataentry.model.I_DataEntry_Section.class);
    /** Column name DataEntry_Section_ID */
    public static final String COLUMNNAME_DataEntry_Section_ID = "DataEntry_Section_ID";

	/**
	 * Set Sektion Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Section_Name (java.lang.String DataEntry_Section_Name);

	/**
	 * Get Sektion Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_Section_Name();

    /** Column definition for DataEntry_Section_Name */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_DataEntry_Section_Name = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "DataEntry_Section_Name", null);
    /** Column name DataEntry_Section_Name */
    public static final String COLUMNNAME_DataEntry_Section_Name = "DataEntry_Section_Name";

	/**
	 * Set Unterregister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID);

	/**
	 * Get Unterregister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_SubTab_ID();

	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab();

	public void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab);

    /** Column definition for DataEntry_SubTab_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_SubTab> COLUMN_DataEntry_SubTab_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_SubTab>(I_I_DataEntry_Record.class, "DataEntry_SubTab_ID", de.metas.dataentry.model.I_DataEntry_SubTab.class);
    /** Column name DataEntry_SubTab_ID */
    public static final String COLUMNNAME_DataEntry_SubTab_ID = "DataEntry_SubTab_ID";

	/**
	 * Set Unterregister Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_SubTab_Name (java.lang.String DataEntry_SubTab_Name);

	/**
	 * Get Unterregister Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_SubTab_Name();

    /** Column definition for DataEntry_SubTab_Name */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_DataEntry_SubTab_Name = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "DataEntry_SubTab_Name", null);
    /** Column name DataEntry_SubTab_Name */
    public static final String COLUMNNAME_DataEntry_SubTab_Name = "DataEntry_SubTab_Name";

	/**
	 * Set Eingaberegister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Tab_ID (int DataEntry_Tab_ID);

	/**
	 * Get Eingaberegister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Tab_ID();

	public de.metas.dataentry.model.I_DataEntry_Tab getDataEntry_Tab();

	public void setDataEntry_Tab(de.metas.dataentry.model.I_DataEntry_Tab DataEntry_Tab);

    /** Column definition for DataEntry_Tab_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Tab> COLUMN_DataEntry_Tab_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_Tab>(I_I_DataEntry_Record.class, "DataEntry_Tab_ID", de.metas.dataentry.model.I_DataEntry_Tab.class);
    /** Column name DataEntry_Tab_ID */
    public static final String COLUMNNAME_DataEntry_Tab_ID = "DataEntry_Tab_ID";

	/**
	 * Set Eingaberegister Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Tab_Name (java.lang.String DataEntry_Tab_Name);

	/**
	 * Get Eingaberegister Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_Tab_Name();

    /** Column definition for DataEntry_Tab_Name */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_DataEntry_Tab_Name = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "DataEntry_Tab_Name", null);
    /** Column name DataEntry_Tab_Name */
    public static final String COLUMNNAME_DataEntry_Tab_Name = "DataEntry_Tab_Name";

	/**
	 * Set External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setExternalId (java.lang.String ExternalId);

	/**
	 * Get External ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExternalId();

    /** Column definition for ExternalId */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_ExternalId = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "ExternalId", null);
    /** Column name ExternalId */
    public static final String COLUMNNAME_ExternalId = "ExternalId";

	/**
	 * Set Field name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFieldName (java.lang.String FieldName);

	/**
	 * Get Field name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFieldName();

    /** Column definition for FieldName */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_FieldName = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "FieldName", null);
    /** Column name FieldName */
    public static final String COLUMNNAME_FieldName = "FieldName";

	/**
	 * Set Field Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFieldValue (java.lang.String FieldValue);

	/**
	 * Get Field Value.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFieldValue();

    /** Column definition for FieldValue */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_FieldValue = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "FieldValue", null);
    /** Column name FieldValue */
    public static final String COLUMNNAME_FieldValue = "FieldValue";

	/**
	 * Set Import - Erweiterte Dateneingabe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_DataEntry_Record_ID (int I_DataEntry_Record_ID);

	/**
	 * Get Import - Erweiterte Dateneingabe.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getI_DataEntry_Record_ID();

    /** Column definition for I_DataEntry_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_I_DataEntry_Record_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "I_DataEntry_Record_ID", null);
    /** Column name I_DataEntry_Record_ID */
    public static final String COLUMNNAME_I_DataEntry_Record_ID = "I_DataEntry_Record_ID";

	/**
	 * Set Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg);

	/**
	 * Get Import-Fehlermeldung.
	 * Meldungen, die durch den Importprozess generiert wurden
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_ErrorMsg();

    /** Column definition for I_ErrorMsg */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_I_ErrorMsg = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "I_ErrorMsg", null);
    /** Column name I_ErrorMsg */
    public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Set Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setI_IsImported (java.lang.String I_IsImported);

	/**
	 * Get Importiert.
	 * Ist dieser Import verarbeitet worden?
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getI_IsImported();

    /** Column definition for I_IsImported */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_I_IsImported = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "I_IsImported", null);
    /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "Record_ID", null);
    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, org.compiere.model.I_AD_User>(I_I_DataEntry_Record.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Window Internal Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWindowInternalName (java.lang.String WindowInternalName);

	/**
	 * Get Window Internal Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWindowInternalName();

    /** Column definition for WindowInternalName */
    public static final org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object> COLUMN_WindowInternalName = new org.adempiere.model.ModelColumn<I_I_DataEntry_Record, Object>(I_I_DataEntry_Record.class, "WindowInternalName", null);
    /** Column name WindowInternalName */
    public static final String COLUMNNAME_WindowInternalName = "WindowInternalName";
}
