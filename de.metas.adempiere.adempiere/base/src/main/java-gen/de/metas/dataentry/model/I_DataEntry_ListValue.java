package de.metas.dataentry.model;


/** Generated Interface for DataEntry_ListValue
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_ListValue 
{

    /** TableName=DataEntry_ListValue */
    public static final String Table_Name = "DataEntry_ListValue";

    /** AD_Table_ID=541168 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_Client>(I_DataEntry_ListValue.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_Org>(I_DataEntry_ListValue.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_User>(I_DataEntry_ListValue.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Dateneingabefeld.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Field_ID (int DataEntry_Field_ID);

	/**
	 * Get Dateneingabefeld.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Field_ID();

	public de.metas.dataentry.model.I_DataEntry_Field getDataEntry_Field();

	public void setDataEntry_Field(de.metas.dataentry.model.I_DataEntry_Field DataEntry_Field);

    /** Column definition for DataEntry_Field_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, de.metas.dataentry.model.I_DataEntry_Field> COLUMN_DataEntry_Field_ID = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, de.metas.dataentry.model.I_DataEntry_Field>(I_DataEntry_ListValue.class, "DataEntry_Field_ID", de.metas.dataentry.model.I_DataEntry_Field.class);
    /** Column name DataEntry_Field_ID */
    public static final String COLUMNNAME_DataEntry_Field_ID = "DataEntry_Field_ID";

	/**
	 * Set Eingabefeldwert.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_ListValue_ID (int DataEntry_ListValue_ID);

	/**
	 * Get Eingabefeldwert.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_ListValue_ID();

    /** Column definition for DataEntry_ListValue_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_DataEntry_ListValue_ID = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "DataEntry_ListValue_ID", null);
    /** Column name DataEntry_ListValue_ID */
    public static final String COLUMNNAME_DataEntry_ListValue_ID = "DataEntry_ListValue_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, Object>(I_DataEntry_ListValue.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_ListValue, org.compiere.model.I_AD_User>(I_DataEntry_ListValue.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
