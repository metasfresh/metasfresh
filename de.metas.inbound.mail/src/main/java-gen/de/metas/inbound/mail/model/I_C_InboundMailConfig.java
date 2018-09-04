package de.metas.inbound.mail.model;


/** Generated Interface for C_InboundMailConfig
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_C_InboundMailConfig 
{

    /** TableName=C_InboundMailConfig */
    public static final String Table_Name = "C_InboundMailConfig";

    /** AD_Table_ID=541013 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_Client>(I_C_InboundMailConfig.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_Org>(I_C_InboundMailConfig.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Inbound EMail Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setC_InboundMailConfig_ID (int C_InboundMailConfig_ID);

	/**
	 * Get Inbound EMail Config.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getC_InboundMailConfig_ID();

    /** Column definition for C_InboundMailConfig_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_C_InboundMailConfig_ID = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "C_InboundMailConfig_ID", null);
    /** Column name C_InboundMailConfig_ID */
    public static final String COLUMNNAME_C_InboundMailConfig_ID = "C_InboundMailConfig_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_User>(I_C_InboundMailConfig.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDescription (java.lang.String Description);

	/**
	 * Get Beschreibung.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDescription();

    /** Column definition for Description */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Folder.
	 * A folder on a local or remote system to store data into
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setFolder (java.lang.String Folder);

	/**
	 * Get Folder.
	 * A folder on a local or remote system to store data into
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getFolder();

    /** Column definition for Folder */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Folder = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Folder", null);
    /** Column name Folder */
    public static final String COLUMNNAME_Folder = "Folder";

	/**
	 * Set Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setHost (java.lang.String Host);

	/**
	 * Get Host.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHost();

    /** Column definition for Host */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Host = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Host", null);
    /** Column name Host */
    public static final String COLUMNNAME_Host = "Host";

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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Debug protocol.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsDebugProtocol (boolean IsDebugProtocol);

	/**
	 * Get Debug protocol.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isDebugProtocol();

    /** Column definition for IsDebugProtocol */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_IsDebugProtocol = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "IsDebugProtocol", null);
    /** Column name IsDebugProtocol */
    public static final String COLUMNNAME_IsDebugProtocol = "IsDebugProtocol";

	/**
	 * Set Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setPort (int Port);

	/**
	 * Get Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getPort();

    /** Column definition for Port */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Port = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Port", null);
    /** Column name Port */
    public static final String COLUMNNAME_Port = "Port";

	/**
	 * Set Protocol.
	 * Protocol
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProtocol (java.lang.String Protocol);

	/**
	 * Get Protocol.
	 * Protocol
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getProtocol();

    /** Column definition for Protocol */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Protocol = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Protocol", null);
    /** Column name Protocol */
    public static final String COLUMNNAME_Protocol = "Protocol";

	/**
	 * Set Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setR_RequestType_ID (int R_RequestType_ID);

	/**
	 * Get Request Type.
	 * Type of request (e.g. Inquiry, Complaint, ..)
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getR_RequestType_ID();

	public org.compiere.model.I_R_RequestType getR_RequestType();

	public void setR_RequestType(org.compiere.model.I_R_RequestType R_RequestType);

    /** Column definition for R_RequestType_ID */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_R_RequestType> COLUMN_R_RequestType_ID = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_R_RequestType>(I_C_InboundMailConfig.class, "R_RequestType_ID", org.compiere.model.I_R_RequestType.class);
    /** Column name R_RequestType_ID */
    public static final String COLUMNNAME_R_RequestType_ID = "R_RequestType_ID";

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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, org.compiere.model.I_AD_User>(I_C_InboundMailConfig.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Nutzer-ID/Login.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserName (java.lang.String UserName);

	/**
	 * Get Nutzer-ID/Login.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_C_InboundMailConfig, Object>(I_C_InboundMailConfig.class, "UserName", null);
    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";
}
