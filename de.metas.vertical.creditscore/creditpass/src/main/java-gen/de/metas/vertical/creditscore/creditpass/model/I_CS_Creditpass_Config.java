package de.metas.vertical.creditscore.creditpass.model;


/** Generated Interface for CS_Creditpass_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CS_Creditpass_Config 
{

    /** TableName=CS_Creditpass_Config */
    public static final String Table_Name = "CS_Creditpass_Config";

    /** AD_Table_ID=541186 */
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Client>(I_CS_Creditpass_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Org>(I_CS_Creditpass_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Berechtigungs ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAuthId (java.lang.String AuthId);

	/**
	 * Get Berechtigungs ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAuthId();

    /** Column definition for AuthId */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_AuthId = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "AuthId", null);
    /** Column name AuthId */
    public static final String COLUMNNAME_AuthId = "AuthId";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set CS Creditpass Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCS_Creditpass_Config_ID (int CS_Creditpass_Config_ID);

	/**
	 * Get CS Creditpass Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCS_Creditpass_Config_ID();

    /** Column definition for CS_Creditpass_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_CS_Creditpass_Config_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "CS_Creditpass_Config_ID", null);
    /** Column name CS_Creditpass_Config_ID */
    public static final String COLUMNNAME_CS_Creditpass_Config_ID = "CS_Creditpass_Config_ID";

	/**
	 * Set Standard Ergebnis.
	 * Bestellungen wie ausgewählt abschließen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setDefaultCheckResult (java.lang.String DefaultCheckResult);

	/**
	 * Get Standard Ergebnis.
	 * Bestellungen wie ausgewählt abschließen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDefaultCheckResult();

    /** Column definition for DefaultCheckResult */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_DefaultCheckResult = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "DefaultCheckResult", null);
    /** Column name DefaultCheckResult */
    public static final String COLUMNNAME_DefaultCheckResult = "DefaultCheckResult";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Benutzer für manuelle Prüfung.
	 * Benutzer für die Benachrichtigung zur manuellen Prüfung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setManual_Check_User_ID (int Manual_Check_User_ID);

	/**
	 * Get Benutzer für manuelle Prüfung.
	 * Benutzer für die Benachrichtigung zur manuellen Prüfung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getManual_Check_User_ID();

	public org.compiere.model.I_AD_User getManual_Check_User();

	public void setManual_Check_User(org.compiere.model.I_AD_User Manual_Check_User);

    /** Column definition for Manual_Check_User_ID */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_Manual_Check_User_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "Manual_Check_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Manual_Check_User_ID */
    public static final String COLUMNNAME_Manual_Check_User_ID = "Manual_Check_User_ID";

	/**
	 * Set Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Grund der Anfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRequestReason (java.lang.String RequestReason);

	/**
	 * Get Grund der Anfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestReason();

    /** Column definition for RequestReason */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RequestReason = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RequestReason", null);
    /** Column name RequestReason */
    public static final String COLUMNNAME_RequestReason = "RequestReason";

	/**
	 * Set REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRestApiBaseURL (java.lang.String RestApiBaseURL);

	/**
	 * Get REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRestApiBaseURL();

    /** Column definition for RestApiBaseURL */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RestApiBaseURL = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RestApiBaseURL", null);
    /** Column name RestApiBaseURL */
    public static final String COLUMNNAME_RestApiBaseURL = "RestApiBaseURL";

	/**
	 * Set Creditpass-Prüfung wiederholen .
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setRetryAfterDays (java.math.BigDecimal RetryAfterDays);

	/**
	 * Get Creditpass-Prüfung wiederholen .
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.math.BigDecimal getRetryAfterDays();

    /** Column definition for RetryAfterDays */
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RetryAfterDays = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RetryAfterDays", null);
    /** Column name RetryAfterDays */
    public static final String COLUMNNAME_RetryAfterDays = "RetryAfterDays";

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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
