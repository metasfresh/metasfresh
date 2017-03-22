package org.compiere.model;


/** Generated Interface for AD_Session
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Session 
{

    /** TableName=AD_Session */
    public static final String Table_Name = "AD_Session";

    /** AD_Table_ID=566 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(6);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Client>(I_AD_Session.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Org>(I_AD_Session.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Role_ID (int AD_Role_ID);

	/**
	 * Get Rolle.
	 * Responsibility Role
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_Role_ID();

	public org.compiere.model.I_AD_Role getAD_Role();

	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role);

    /** Column definition for AD_Role_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Role> COLUMN_AD_Role_ID = new org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_Role>(I_AD_Session.class, "AD_Role_ID", org.compiere.model.I_AD_Role.class);
    /** Column name AD_Role_ID */
    public static final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";

	/**
	 * Set Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_Session_ID (int AD_Session_ID);

	/**
	 * Get Nutzersitzung.
	 * User Session Online or Web
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Session_ID();

    /** Column definition for AD_Session_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_AD_Session_ID = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "AD_Session_ID", null);
    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/**
	 * Set Client Info.
	 * Informations about connected client (e.g. web browser name, version etc)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setClient_Info (java.lang.String Client_Info);

	/**
	 * Get Client Info.
	 * Informations about connected client (e.g. web browser name, version etc)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getClient_Info();

    /** Column definition for Client_Info */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Client_Info = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Client_Info", null);
    /** Column name Client_Info */
    public static final String COLUMNNAME_Client_Info = "Client_Info";

	/**
	 * Get Erstellt.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_User>(I_AD_Session.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setHostKey (java.lang.String HostKey);

	/**
	 * Get Host key.
	 * Unique identifier of a host
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getHostKey();

    /** Column definition for HostKey */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_HostKey = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "HostKey", null);
    /** Column name HostKey */
    public static final String COLUMNNAME_HostKey = "HostKey";

	/**
	 * Set Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set IsLoginIncorrect.
	 * Flag is yes if  is login incorect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setIsLoginIncorrect (boolean IsLoginIncorrect);

	/**
	 * Get IsLoginIncorrect.
	 * Flag is yes if  is login incorect
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public boolean isLoginIncorrect();

    /** Column definition for IsLoginIncorrect */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_IsLoginIncorrect = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "IsLoginIncorrect", null);
    /** Column name IsLoginIncorrect */
    public static final String COLUMNNAME_IsLoginIncorrect = "IsLoginIncorrect";

	/**
	 * Set Login date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLoginDate (java.sql.Timestamp LoginDate);

	/**
	 * Get Login date.
	 *
	 * <br>Type: Date
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getLoginDate();

    /** Column definition for LoginDate */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_LoginDate = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "LoginDate", null);
    /** Column name LoginDate */
    public static final String COLUMNNAME_LoginDate = "LoginDate";

	/**
	 * Set Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setLoginUsername (java.lang.String LoginUsername);

	/**
	 * Get Login User Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getLoginUsername();

    /** Column definition for LoginUsername */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_LoginUsername = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "LoginUsername", null);
    /** Column name LoginUsername */
    public static final String COLUMNNAME_LoginUsername = "LoginUsername";

	/**
	 * Set Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setProcessed (boolean Processed);

	/**
	 * Get Verarbeitet.
	 * Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isProcessed();

    /** Column definition for Processed */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Processed = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Processed", null);
    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/**
	 * Set Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemote_Addr (java.lang.String Remote_Addr);

	/**
	 * Get Remote Addr.
	 * Remote Address
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemote_Addr();

    /** Column definition for Remote_Addr */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Remote_Addr = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Remote_Addr", null);
    /** Column name Remote_Addr */
    public static final String COLUMNNAME_Remote_Addr = "Remote_Addr";

	/**
	 * Set Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRemote_Host (java.lang.String Remote_Host);

	/**
	 * Get Remote Host.
	 * Remote host Info
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRemote_Host();

    /** Column definition for Remote_Host */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Remote_Host = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Remote_Host", null);
    /** Column name Remote_Host */
    public static final String COLUMNNAME_Remote_Host = "Remote_Host";

	/**
	 * Get Aktualisiert.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Session, org.compiere.model.I_AD_User>(I_AD_Session.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Web-Sitzung.
	 * Web Session ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWebSession (java.lang.String WebSession);

	/**
	 * Get Web-Sitzung.
	 * Web Session ID
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWebSession();

    /** Column definition for WebSession */
    public static final org.adempiere.model.ModelColumn<I_AD_Session, Object> COLUMN_WebSession = new org.adempiere.model.ModelColumn<I_AD_Session, Object>(I_AD_Session.class, "WebSession", null);
    /** Column name WebSession */
    public static final String COLUMNNAME_WebSession = "WebSession";
}
