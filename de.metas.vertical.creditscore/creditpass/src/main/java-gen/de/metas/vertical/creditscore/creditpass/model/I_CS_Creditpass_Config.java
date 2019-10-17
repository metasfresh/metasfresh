package de.metas.vertical.creditscore.creditpass.model;


/** Generated Interface for CS_Creditpass_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_CS_Creditpass_Config 
{

    /** TableName=CS_Creditpass_Config */
	String Table_Name = "CS_Creditpass_Config";

    /** AD_Table_ID=541186 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */
    /** Column definition for AD_Client_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Client>(I_CS_Creditpass_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    /** Column definition for AD_Org_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_Org>(I_CS_Creditpass_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    /** Column definition for AuthId */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_AuthId = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "AuthId", null);
    /** Column name AuthId */
	String COLUMNNAME_AuthId = "AuthId";
    /** Column definition for Created */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Created", null);
    /** Column name Created */
	String COLUMNNAME_Created = "Created";
    /** Column definition for CreatedBy */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
	String COLUMNNAME_CreatedBy = "CreatedBy";
    /** Column definition for CS_Creditpass_Config_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_CS_Creditpass_Config_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "CS_Creditpass_Config_ID", null);
    /** Column name CS_Creditpass_Config_ID */
	String COLUMNNAME_CS_Creditpass_Config_ID = "CS_Creditpass_Config_ID";
    /** Column definition for DefaultCheckResult */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_DefaultCheckResult = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "DefaultCheckResult", null);
    /** Column name DefaultCheckResult */
	String COLUMNNAME_DefaultCheckResult = "DefaultCheckResult";
    /** Column definition for IsActive */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "IsActive", null);
    /** Column name IsActive */
	String COLUMNNAME_IsActive = "IsActive";
    /** Column definition for Manual_Check_User_ID */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_Manual_Check_User_ID = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "Manual_Check_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Manual_Check_User_ID */
	String COLUMNNAME_Manual_Check_User_ID = "Manual_Check_User_ID";
    /** Column definition for Password */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Password", null);
    /** Column name Password */
	String COLUMNNAME_Password = "Password";
    /** Column definition for RequestReason */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RequestReason = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RequestReason", null);
    /** Column name RequestReason */
	String COLUMNNAME_RequestReason = "RequestReason";
    /** Column definition for RestApiBaseURL */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RestApiBaseURL = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RestApiBaseURL", null);
    /** Column name RestApiBaseURL */
	String COLUMNNAME_RestApiBaseURL = "RestApiBaseURL";
    /** Column definition for RetryAfterDays */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_RetryAfterDays = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "RetryAfterDays", null);
    /** Column name RetryAfterDays */
	String COLUMNNAME_RetryAfterDays = "RetryAfterDays";
    /** Column definition for SeqNo */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_SeqNo = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "SeqNo", null);
    /** Column name SeqNo */
	String COLUMNNAME_SeqNo = "SeqNo";
    /** Column definition for Updated */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, Object>(I_CS_Creditpass_Config.class, "Updated", null);
    /** Column name Updated */
	String COLUMNNAME_Updated = "Updated";
    /** Column definition for UpdatedBy */
	org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_CS_Creditpass_Config, org.compiere.model.I_AD_User>(I_CS_Creditpass_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	org.compiere.model.I_AD_Client getAD_Client();

	/**
	 * Get Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	/**
	 * Set Sektion.
	 * Organisatorische Einheit des Mandanten
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID(int AD_Org_ID);

	org.compiere.model.I_AD_Org getAD_Org();

	void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

	/**
	 * Get Berechtigungs ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAuthId();

	/**
	 * Set Berechtigungs ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthId(java.lang.String AuthId);

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	/**
	 * Get Creditpass Einstellung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCS_Creditpass_Config_ID();

	/**
	 * Set Creditpass Einstellung.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCS_Creditpass_Config_ID(int CS_Creditpass_Config_ID);

	/**
	 * Get Standard Ergebnis.
	 * Bestellungen wie ausgewählt abschließen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getDefaultCheckResult();

	/**
	 * Set Standard Ergebnis.
	 * Bestellungen wie ausgewählt abschließen
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setDefaultCheckResult(java.lang.String DefaultCheckResult);

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive(boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	/**
	 * Get Benutzer für manuelle Prüfung.
	 * Benutzer für die Benachrichtigung zur manuellen Prüfung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getManual_Check_User_ID();

	/**
	 * Set Benutzer für manuelle Prüfung.
	 * Benutzer für die Benachrichtigung zur manuellen Prüfung
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setManual_Check_User_ID(int Manual_Check_User_ID);

	org.compiere.model.I_AD_User getManual_Check_User();

	void setManual_Check_User(org.compiere.model.I_AD_User Manual_Check_User);

	/**
	 * Get Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPassword();

	/**
	 * Set Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPassword(java.lang.String Password);

	/**
	 * Get Grund der Anfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRequestReason();

	/**
	 * Set Grund der Anfrage.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRequestReason(java.lang.String RequestReason);

	/**
	 * Get REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getRestApiBaseURL();

	/**
	 * Set REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRestApiBaseURL(java.lang.String RestApiBaseURL);

	/**
	 * Get Creditpass-Prüfung wiederholen .
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.math.BigDecimal getRetryAfterDays();

	/**
	 * Set Creditpass-Prüfung wiederholen .
	 *
	 * <br>Type: Number
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setRetryAfterDays(java.math.BigDecimal RetryAfterDays);

	/**
	 * Get Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSeqNo();

	/**
	 * Set Reihenfolge.
	 * Zur Bestimmung der Reihenfolge der Einträge;
 die kleinste Zahl kommt zuerst
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSeqNo(int SeqNo);

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();
}
