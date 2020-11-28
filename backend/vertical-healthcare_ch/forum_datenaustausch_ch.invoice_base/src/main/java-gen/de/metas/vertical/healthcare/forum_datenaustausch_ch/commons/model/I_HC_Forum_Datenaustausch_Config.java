package de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model;


/** Generated Interface for HC_Forum_Datenaustausch_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_HC_Forum_Datenaustausch_Config 
{

    /** TableName=HC_Forum_Datenaustausch_Config */
    public static final String Table_Name = "HC_Forum_Datenaustausch_Config";

    /** AD_Table_ID=541145 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(1);

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Client>(I_HC_Forum_Datenaustausch_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_Org>(I_HC_Forum_Datenaustausch_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setBill_BPartner_ID (int Bill_BPartner_ID);

	/**
	 * Get Rechnungspartner.
	 * Geschäftspartner für die Rechnungsstellung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getBill_BPartner_ID();

	public org.compiere.model.I_C_BPartner getBill_BPartner();

	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner);

    /** Column definition for Bill_BPartner_ID */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BPartner> COLUMN_Bill_BPartner_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_C_BPartner>(I_HC_Forum_Datenaustausch_Config.class, "Bill_BPartner_ID", org.compiere.model.I_C_BPartner.class);
    /** Column name Bill_BPartner_ID */
    public static final String COLUMNNAME_Bill_BPartner_ID = "Bill_BPartner_ID";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User>(I_HC_Forum_Datenaustausch_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Modus der Exportdateien.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExportedXmlMode (java.lang.String ExportedXmlMode);

	/**
	 * Get Modus der Exportdateien.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportedXmlMode();

    /** Column definition for ExportedXmlMode */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ExportedXmlMode = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "ExportedXmlMode", null);
    /** Column name ExportedXmlMode */
    public static final String COLUMNNAME_ExportedXmlMode = "ExportedXmlMode";

	/**
	 * Set Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setExportedXmlVersion (java.lang.String ExportedXmlVersion);

	/**
	 * Get Export XML Version.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getExportedXmlVersion();

    /** Column definition for ExportedXmlVersion */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_ExportedXmlVersion = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "ExportedXmlVersion", null);
    /** Column name ExportedXmlVersion */
    public static final String COLUMNNAME_ExportedXmlVersion = "ExportedXmlVersion";

	/**
	 * Set Absender EAN.
	 * Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setFrom_EAN (java.lang.String From_EAN);

	/**
	 * Get Absender EAN.
	 * Falls angegeben ersetzt dieser Wert den Wert des request/processing/transport from Attributes in der exportierten XML-Datei
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFrom_EAN();

    /** Column definition for From_EAN */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_From_EAN = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "From_EAN", null);
    /** Column name From_EAN */
    public static final String COLUMNNAME_From_EAN = "From_EAN";

	/**
	 * Set forum-datenaustausch.ch config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHC_Forum_Datenaustausch_Config_ID (int HC_Forum_Datenaustausch_Config_ID);

	/**
	 * Get forum-datenaustausch.ch config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getHC_Forum_Datenaustausch_Config_ID();

    /** Column definition for HC_Forum_Datenaustausch_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_HC_Forum_Datenaustausch_Config_ID = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "HC_Forum_Datenaustausch_Config_ID", null);
    /** Column name HC_Forum_Datenaustausch_Config_ID */
    public static final String COLUMNNAME_HC_Forum_Datenaustausch_Config_ID = "HC_Forum_Datenaustausch_Config_ID";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Speicherverzeichnis.
	 * Verzeichnis, in dem exportierte Dateien gespeichert werden.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStoreDirectory (java.lang.String StoreDirectory);

	/**
	 * Get Speicherverzeichnis.
	 * Verzeichnis, in dem exportierte Dateien gespeichert werden.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getStoreDirectory();

    /** Column definition for StoreDirectory */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_StoreDirectory = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "StoreDirectory", null);
    /** Column name StoreDirectory */
    public static final String COLUMNNAME_StoreDirectory = "StoreDirectory";

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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, org.compiere.model.I_AD_User>(I_HC_Forum_Datenaustausch_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Via EAN.
	 * Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setVia_EAN (java.lang.String Via_EAN);

	/**
	 * Get Via EAN.
	 * Dieser Wert wird bei request/processing/transport/via in die exportierten XML-Datei eingefügt. Evtl. vorhandene "via" Knoten werden entfernt.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getVia_EAN();

    /** Column definition for Via_EAN */
    public static final org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object> COLUMN_Via_EAN = new org.adempiere.model.ModelColumn<I_HC_Forum_Datenaustausch_Config, Object>(I_HC_Forum_Datenaustausch_Config.class, "Via_EAN", null);
    /** Column name Via_EAN */
    public static final String COLUMNNAME_Via_EAN = "Via_EAN";
}
