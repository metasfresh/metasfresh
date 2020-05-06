package de.metas.marketing.base.model;


/** Generated Interface for MKTG_Campaign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_MKTG_Campaign 
{

    /** TableName=MKTG_Campaign */
    public static final String Table_Name = "MKTG_Campaign";

    /** AD_Table_ID=540970 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */

	/**
	 * Set Textbaustein.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID);

	/**
	 * Get Textbaustein.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_BoilerPlate_ID();

    /** Column definition for AD_BoilerPlate_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_AD_BoilerPlate_ID = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "AD_BoilerPlate_ID", null);
    /** Column name AD_BoilerPlate_ID */
    public static final String COLUMNNAME_AD_BoilerPlate_ID = "AD_BoilerPlate_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_Client>(I_MKTG_Campaign.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_Org>(I_MKTG_Campaign.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_User>(I_MKTG_Campaign.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEndDate (java.sql.Timestamp EndDate);

	/**
	 * Get Enddatum.
	 * Last effective date (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getEndDate();

    /** Column definition for EndDate */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_EndDate = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "EndDate", null);
    /** Column name EndDate */
    public static final String COLUMNNAME_EndDate = "EndDate";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsDefaultNewsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDefaultNewsletter (boolean IsDefaultNewsletter);

	/**
	 * Get IsDefaultNewsletter.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDefaultNewsletter();

    /** Column definition for IsDefaultNewsletter */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_IsDefaultNewsletter = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "IsDefaultNewsletter", null);
    /** Column name IsDefaultNewsletter */
    public static final String COLUMNNAME_IsDefaultNewsletter = "IsDefaultNewsletter";

	/**
	 * Set Synchronisierungstatus-Detail.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSyncDetailMessage (java.lang.String LastSyncDetailMessage);

	/**
	 * Get Synchronisierungstatus-Detail.
	 *
	 * <br>Type: Text
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastSyncDetailMessage();

    /** Column definition for LastSyncDetailMessage */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_LastSyncDetailMessage = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "LastSyncDetailMessage", null);
    /** Column name LastSyncDetailMessage */
    public static final String COLUMNNAME_LastSyncDetailMessage = "LastSyncDetailMessage";

	/**
	 * Set Zuletzt auf Platform hochgeladen.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSyncOfLocalToRemote (java.sql.Timestamp LastSyncOfLocalToRemote);

	/**
	 * Get Zuletzt auf Platform hochgeladen.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastSyncOfLocalToRemote();

    /** Column definition for LastSyncOfLocalToRemote */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_LastSyncOfLocalToRemote = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "LastSyncOfLocalToRemote", null);
    /** Column name LastSyncOfLocalToRemote */
    public static final String COLUMNNAME_LastSyncOfLocalToRemote = "LastSyncOfLocalToRemote";

	/**
	 * Set Zuletzt von Platform runtergeladen.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSyncOfRemoteToLocal (java.sql.Timestamp LastSyncOfRemoteToLocal);

	/**
	 * Get Zuletzt von Platform runtergeladen.
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLastSyncOfRemoteToLocal();

    /** Column definition for LastSyncOfRemoteToLocal */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_LastSyncOfRemoteToLocal = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "LastSyncOfRemoteToLocal", null);
    /** Column name LastSyncOfRemoteToLocal */
    public static final String COLUMNNAME_LastSyncOfRemoteToLocal = "LastSyncOfRemoteToLocal";

	/**
	 * Set Letzter Synchronisierungsstatus.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLastSyncStatus (java.lang.String LastSyncStatus);

	/**
	 * Get Letzter Synchronisierungsstatus.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLastSyncStatus();

    /** Column definition for LastSyncStatus */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_LastSyncStatus = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "LastSyncStatus", null);
    /** Column name LastSyncStatus */
    public static final String COLUMNNAME_LastSyncStatus = "LastSyncStatus";

	/**
	 * Set MKTG_Campaign.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_Campaign_ID (int MKTG_Campaign_ID);

	/**
	 * Get MKTG_Campaign.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_Campaign_ID();

    /** Column definition for MKTG_Campaign_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_MKTG_Campaign_ID = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "MKTG_Campaign_ID", null);
    /** Column name MKTG_Campaign_ID */
    public static final String COLUMNNAME_MKTG_Campaign_ID = "MKTG_Campaign_ID";

	/**
	 * Set MKTG_Platform.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMKTG_Platform_ID (int MKTG_Platform_ID);

	/**
	 * Get MKTG_Platform.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getMKTG_Platform_ID();

	public de.metas.marketing.base.model.I_MKTG_Platform getMKTG_Platform();

	public void setMKTG_Platform(de.metas.marketing.base.model.I_MKTG_Platform MKTG_Platform);

    /** Column definition for MKTG_Platform_ID */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, de.metas.marketing.base.model.I_MKTG_Platform> COLUMN_MKTG_Platform_ID = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, de.metas.marketing.base.model.I_MKTG_Platform>(I_MKTG_Campaign.class, "MKTG_Platform_ID", de.metas.marketing.base.model.I_MKTG_Platform.class);
    /** Column name MKTG_Platform_ID */
    public static final String COLUMNNAME_MKTG_Platform_ID = "MKTG_Platform_ID";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Externe Datensatz-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemoteRecordId (java.lang.String RemoteRecordId);

	/**
	 * Get Externe Datensatz-ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemoteRecordId();

    /** Column definition for RemoteRecordId */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_RemoteRecordId = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "RemoteRecordId", null);
    /** Column name RemoteRecordId */
    public static final String COLUMNNAME_RemoteRecordId = "RemoteRecordId";

	/**
	 * Set Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setStartDate (java.sql.Timestamp StartDate);

	/**
	 * Get Anfangsdatum.
	 * First effective day (inclusive)
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getStartDate();

    /** Column definition for StartDate */
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_StartDate = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "StartDate", null);
    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, Object>(I_MKTG_Campaign.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_MKTG_Campaign, org.compiere.model.I_AD_User>(I_MKTG_Campaign.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
