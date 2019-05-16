package de.metas.dataentry.model;


/** Generated Interface for DataEntry_Field
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_Field 
{

    /** TableName=DataEntry_Field */
    public static final String Table_Name = "DataEntry_Field";

    /** AD_Table_ID=541167 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_Client>(I_DataEntry_Field.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_Org>(I_DataEntry_Field.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set In API verfügbar.
	 * Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAvailableInAPI (boolean AvailableInAPI);

	/**
	 * Get In API verfügbar.
	 * Legt fest, ob dieses Feld via metasfresh API für externe Anwendungen verfügbar ist.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isAvailableInAPI();

    /** Column definition for AvailableInAPI */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_AvailableInAPI = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "AvailableInAPI", null);
    /** Column name AvailableInAPI */
    public static final String COLUMNNAME_AvailableInAPI = "AvailableInAPI";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_User>(I_DataEntry_Field.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dateneingabefeld.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Field_ID (int DataEntry_Field_ID);

	/**
	 * Get Dateneingabefeld.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Field_ID();

    /** Column definition for DataEntry_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_DataEntry_Field_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "DataEntry_Field_ID", null);
    /** Column name DataEntry_Field_ID */
    public static final String COLUMNNAME_DataEntry_Field_ID = "DataEntry_Field_ID";

	/**
	 * Set Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Line_ID (int DataEntry_Line_ID);

	/**
	 * Get Zeile.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Line_ID();

	public de.metas.dataentry.model.I_DataEntry_Line getDataEntry_Line();

	public void setDataEntry_Line(de.metas.dataentry.model.I_DataEntry_Line DataEntry_Line);

    /** Column definition for DataEntry_Line_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, de.metas.dataentry.model.I_DataEntry_Line> COLUMN_DataEntry_Line_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Field, de.metas.dataentry.model.I_DataEntry_Line>(I_DataEntry_Field.class, "DataEntry_Line_ID", de.metas.dataentry.model.I_DataEntry_Line.class);
    /** Column name DataEntry_Line_ID */
    public static final String COLUMNNAME_DataEntry_Line_ID = "DataEntry_Line_ID";

	/**
	 * Set Datentyp.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_RecordType (java.lang.String DataEntry_RecordType);

	/**
	 * Get Datentyp.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_RecordType();

    /** Column definition for DataEntry_RecordType */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_DataEntry_RecordType = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "DataEntry_RecordType", null);
    /** Column name DataEntry_RecordType */
    public static final String COLUMNNAME_DataEntry_RecordType = "DataEntry_RecordType";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_IsMandatory = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "IsMandatory", null);
    /** Column name IsMandatory */
    public static final String COLUMNNAME_IsMandatory = "IsMandatory";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Datenschutz-Kategorie.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPersonalDataCategory (java.lang.String PersonalDataCategory);

	/**
	 * Get Datenschutz-Kategorie.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPersonalDataCategory();

    /** Column definition for PersonalDataCategory */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_PersonalDataCategory = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "PersonalDataCategory", null);
    /** Column name PersonalDataCategory */
    public static final String COLUMNNAME_PersonalDataCategory = "PersonalDataCategory";

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSeqNo (int SeqNo);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSeqNo();

    /** Column definition for SeqNo */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "SeqNo", null);
    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_Field, Object>(I_DataEntry_Field.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Field, org.compiere.model.I_AD_User>(I_DataEntry_Field.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
