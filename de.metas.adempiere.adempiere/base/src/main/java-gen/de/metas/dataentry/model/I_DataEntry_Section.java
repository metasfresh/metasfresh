package de.metas.dataentry.model;


/** Generated Interface for DataEntry_Section
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_DataEntry_Section 
{

    /** TableName=DataEntry_Section */
    public static final String Table_Name = "DataEntry_Section";

    /** AD_Table_ID=541179 */
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_Client>(I_DataEntry_Section.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_Org>(I_DataEntry_Section.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_User>(I_DataEntry_Section.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Sektion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDataEntry_Section_ID (int DataEntry_Section_ID);

	/**
	 * Get Sektion.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getDataEntry_Section_ID();

    /** Column definition for DataEntry_Section_ID */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_DataEntry_Section_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "DataEntry_Section_ID", null);
    /** Column name DataEntry_Section_ID */
    public static final String COLUMNNAME_DataEntry_Section_ID = "DataEntry_Section_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, de.metas.dataentry.model.I_DataEntry_SubTab> COLUMN_DataEntry_SubTab_ID = new org.adempiere.model.ModelColumn<I_DataEntry_Section, de.metas.dataentry.model.I_DataEntry_SubTab>(I_DataEntry_Section.class, "DataEntry_SubTab_ID", de.metas.dataentry.model.I_DataEntry_SubTab.class);
    /** Column name DataEntry_SubTab_ID */
    public static final String COLUMNNAME_DataEntry_SubTab_ID = "DataEntry_SubTab_ID";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "Description", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Initial geschlossen.
	 * Legt fest, ob die Feldgruppe initial offen (sichtbar) oder geschlossen ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsInitiallyClosed (boolean IsInitiallyClosed);

	/**
	 * Get Initial geschlossen.
	 * Legt fest, ob die Feldgruppe initial offen (sichtbar) oder geschlossen ist
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isInitiallyClosed();

    /** Column definition for IsInitiallyClosed */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_IsInitiallyClosed = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "IsInitiallyClosed", null);
    /** Column name IsInitiallyClosed */
    public static final String COLUMNNAME_IsInitiallyClosed = "IsInitiallyClosed";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getName();

    /** Column definition for Name */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Sektionsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSectionName (java.lang.String SectionName);

	/**
	 * Get Sektionsname.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSectionName();

    /** Column definition for SectionName */
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_SectionName = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "SectionName", null);
    /** Column name SectionName */
    public static final String COLUMNNAME_SectionName = "SectionName";

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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "SeqNo", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_DataEntry_Section, Object>(I_DataEntry_Section.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_DataEntry_Section, org.compiere.model.I_AD_User>(I_DataEntry_Section.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
