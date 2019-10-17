package de.metas.vertical.pharma.securpharm.model;


/** Generated Interface for M_Securpharm_Config
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_M_Securpharm_Config 
{

    /** TableName=M_Securpharm_Config */
    public static final String Table_Name = "M_Securpharm_Config";

    /** AD_Table_ID=541348 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Client>(I_M_Securpharm_Config.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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

    /** Column definition for AD_Org_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_Org>(I_M_Securpharm_Config.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Application UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setApplicationUUID (java.lang.String ApplicationUUID);

	/**
	 * Get Application UUID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getApplicationUUID();

    /** Column definition for ApplicationUUID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_ApplicationUUID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "ApplicationUUID", null);
    /** Column name ApplicationUUID */
    public static final String COLUMNNAME_ApplicationUUID = "ApplicationUUID";

	/**
	 * Set Pharma Auth REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAuthPharmaRestApiBaseURL (java.lang.String AuthPharmaRestApiBaseURL);

	/**
	 * Get Pharma Auth REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAuthPharmaRestApiBaseURL();

    /** Column definition for AuthPharmaRestApiBaseURL */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_AuthPharmaRestApiBaseURL = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "AuthPharmaRestApiBaseURL", null);
    /** Column name AuthPharmaRestApiBaseURL */
    public static final String COLUMNNAME_AuthPharmaRestApiBaseURL = "AuthPharmaRestApiBaseURL";

	/**
	 * Set Zertifikatpfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setCertificatePath (java.lang.String CertificatePath);

	/**
	 * Get Zertifikatpfad.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getCertificatePath();

    /** Column definition for CertificatePath */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_CertificatePath = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "CertificatePath", null);
    /** Column name CertificatePath */
    public static final String COLUMNNAME_CertificatePath = "CertificatePath";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SecurPharm Einstellungen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setM_Securpharm_Config_ID (int M_Securpharm_Config_ID);

	/**
	 * Get SecurPharm Einstellungen.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getM_Securpharm_Config_ID();

    /** Column definition for M_Securpharm_Config_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_M_Securpharm_Config_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "M_Securpharm_Config_ID", null);
    /** Column name M_Securpharm_Config_ID */
    public static final String COLUMNNAME_M_Securpharm_Config_ID = "M_Securpharm_Config_ID";

	/**
	 * Set Pharma REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPharmaRestApiBaseURL (java.lang.String PharmaRestApiBaseURL);

	/**
	 * Get Pharma REST API URL.
	 *
	 * <br>Type: URL
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPharmaRestApiBaseURL();

    /** Column definition for PharmaRestApiBaseURL */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_PharmaRestApiBaseURL = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "PharmaRestApiBaseURL", null);
    /** Column name PharmaRestApiBaseURL */
    public static final String COLUMNNAME_PharmaRestApiBaseURL = "PharmaRestApiBaseURL";

	/**
	 * Set Support Benutzer.
	 * Benutzer für Benachrichtigungen
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSupport_User_ID (int Support_User_ID);

	/**
	 * Get Support Benutzer.
	 * Benutzer für Benachrichtigungen
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSupport_User_ID();

    /** Column definition for Support_User_ID */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_Support_User_ID = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "Support_User_ID", org.compiere.model.I_AD_User.class);
    /** Column name Support_User_ID */
    public static final String COLUMNNAME_Support_User_ID = "Support_User_ID";

	/**
	 * Set TAN Passwort.
	 * TAN Passwort benutzt für Authentifizierung
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setTanPassword (java.lang.String TanPassword);

	/**
	 * Get TAN Passwort.
	 * TAN Passwort benutzt für Authentifizierung
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getTanPassword();

    /** Column definition for TanPassword */
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_TanPassword = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "TanPassword", null);
    /** Column name TanPassword */
    public static final String COLUMNNAME_TanPassword = "TanPassword";

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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, Object>(I_M_Securpharm_Config.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_M_Securpharm_Config, org.compiere.model.I_AD_User>(I_M_Securpharm_Config.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
