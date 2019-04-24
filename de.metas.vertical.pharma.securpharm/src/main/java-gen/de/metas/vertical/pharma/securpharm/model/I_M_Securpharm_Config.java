package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Config 
{

    /** TableName=M_Securpharm_Config */
	String Table_Name = "M_Securpharm_Config";

    /** AD_Table_ID=541348 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(3);

    /** Load Meta Data */
    /** Column definition for AD_Client_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Client>(I_M_Securpharm_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
    /** Column definition for AD_Org_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Org>(I_M_Securpharm_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
    /** Column definition for ApplicationUUID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_ApplicationUUID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "ApplicationUUID", null);
    /** Column name ApplicationUUID */
	String COLUMNNAME_ApplicationUUID = "ApplicationUUID";
    /** Column definition for AuthPharmaRestApiBaseURL */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_AuthPharmaRestApiBaseURL = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "AuthPharmaRestApiBaseURL", null);
    /** Column name AuthPharmaRestApiBaseURL */
	String COLUMNNAME_AuthPharmaRestApiBaseURL = "AuthPharmaRestApiBaseURL";
    /** Column definition for CertificatePath */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_CertificatePath = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "CertificatePath", null);
    /** Column name CertificatePath */
	String COLUMNNAME_CertificatePath = "CertificatePath";
    /** Column definition for Created */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "Created", null);
    /** Column name Created */
	String COLUMNNAME_Created = "Created";
    /** Column definition for CreatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
	String COLUMNNAME_CreatedBy = "CreatedBy";
    /** Column definition for IsActive */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "IsActive", null);
    /** Column name IsActive */
	String COLUMNNAME_IsActive = "IsActive";
    /** Column definition for M_Securpharm_Config_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_M_Securpharm_Config_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "M_Securpharm_Config_ID", null);
    /** Column name M_Securpharm_Config_ID */
	String COLUMNNAME_M_Securpharm_Config_ID = "M_Securpharm_Config_ID";
    /** Column definition for PharmaRestApiBaseURL */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_PharmaRestApiBaseURL = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "PharmaRestApiBaseURL", null);
    /** Column name PharmaRestApiBaseURL */
	String COLUMNNAME_PharmaRestApiBaseURL = "PharmaRestApiBaseURL";
    /** Column definition for Support_User_ID */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_Support_User_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "Support_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Support_User_ID */
	String COLUMNNAME_Support_User_ID = "Support_User_ID";
    /** Column definition for TanPassword */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_TanPassword = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "TanPassword", null);
    /** Column name TanPassword */
	String COLUMNNAME_TanPassword = "TanPassword";
    /** Column definition for Updated */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "Updated", null);
    /** Column name Updated */
	String COLUMNNAME_Updated = "Updated";
    /** Column definition for UpdatedBy */
	org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
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
	 * Get Application UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getApplicationUUID();

	/**
	 * Set Application UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setApplicationUUID(java.lang.String ApplicationUUID);

	/**
	 * Get Pharma Auth REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAuthPharmaRestApiBaseURL();

	/**
	 * Set Pharma Auth REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAuthPharmaRestApiBaseURL(java.lang.String AuthPharmaRestApiBaseURL);

	/**
	 * Get Zertifikatpfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getCertificatePath();

	/**
	 * Set Zertifikatpfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setCertificatePath(java.lang.String CertificatePath);

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
	 * Get SecurPharm Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getM_Securpharm_Config_ID();

	/**
	 * Set SecurPharm Configuration.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setM_Securpharm_Config_ID(int M_Securpharm_Config_ID);

	/**
	 * Get Pharma REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPharmaRestApiBaseURL();

	/**
	 * Set Pharma REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPharmaRestApiBaseURL(java.lang.String PharmaRestApiBaseURL);

	/**
	 * Get Support Benutzer.
	 * Benutzer für Benachrichtigungen
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getSupport_User_ID();

	/**
	 * Set Support Benutzer.
	 * Benutzer für Benachrichtigungen
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSupport_User_ID(int Support_User_ID);

	org.compiere.model.I_AD_User getSupport_User();

	void setSupport_User(org.compiere.model.I_AD_User Support_User);

	/**
	 * Get TAN Passwort.
	 * TAN Passwort benutzt für Authentifizierung
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getTanPassword();

	/**
	 * Set TAN Passwort.
	 * TAN Passwort benutzt für Authentifizierung
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setTanPassword(java.lang.String TanPassword);

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
