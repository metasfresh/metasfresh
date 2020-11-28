package de.metas.dataentry.model;


/** Generated Interface for DataEntry_Record
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_Record 
{

    /** TableName=DataEntry_Record */
    public static final String Table_Name = "DataEntry_Record";

    /** AD_Table_ID=541169 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Client>(I_DataEntry_Record.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Org>(I_DataEntry_Record.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Table_ID (int AD_Table_ID);

	/**
	 * Get DB-Tabelle.
	 * Database Table information
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table();

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column definition for AD_Table_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Table> COLUMN_AD_Table_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_Table>(I_DataEntry_Record.class, "AD_Table_ID", org.compiere.model.I_AD_Table.class);
    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_User>(I_DataEntry_Record.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set DataEntry_Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Record_ID (int DataEntry_Record_ID);

	/**
	 * Get DataEntry_Record.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Record_ID();

    /** Column definition for DataEntry_Record_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_DataEntry_Record_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "DataEntry_Record_ID", null);
    /** Column name DataEntry_Record_ID */
    public static final String COLUMNNAME_DataEntry_Record_ID = "DataEntry_Record_ID";

	/**
	 * Set DataEntry_RecordData.
	 * Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_RecordData (java.lang.String DataEntry_RecordData);

	/**
	 * Get DataEntry_RecordData.
	 * Holds the (JSON-)data of all fields for a data entry. The json is supposed to be rendered into the respective fields, the column is not intended for actual display
	 *
	 * <br>Type: TextLong
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDataEntry_RecordData();

    /** Column definition for DataEntry_RecordData */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_DataEntry_RecordData = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "DataEntry_RecordData", null);
    /** Column name DataEntry_RecordData */
    public static final String COLUMNNAME_DataEntry_RecordData = "DataEntry_RecordData";

	/**
	 * Set Unterregister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_SubTab_ID (int DataEntry_SubTab_ID);

	/**
	 * Get Unterregister.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_SubTab_ID();

	public de.metas.dataentry.model.I_DataEntry_SubTab getDataEntry_SubTab();

	public void setDataEntry_SubTab(de.metas.dataentry.model.I_DataEntry_SubTab DataEntry_SubTab);

    /** Column definition for DataEntry_SubTab_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_SubTab> COLUMN_DataEntry_SubTab_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, de.metas.dataentry.model.I_DataEntry_SubTab>(I_DataEntry_Record.class, "DataEntry_SubTab_ID", de.metas.dataentry.model.I_DataEntry_SubTab.class);
    /** Column name DataEntry_SubTab_ID */
    public static final String COLUMNNAME_DataEntry_SubTab_ID = "DataEntry_SubTab_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRecord_ID (int Record_ID);

	/**
	 * Get Datensatz-ID.
	 * Direct internal record ID
	 *
	 * <br>Type: Button
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getRecord_ID();

    /** Column definition for Record_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_Record_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "Record_ID", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_Record, Object>(I_DataEntry_Record.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Record, org.compiere.model.I_AD_User>(I_DataEntry_Record.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
