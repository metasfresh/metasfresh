package org.compiere.model;

<<<<<<< HEAD

/** Generated Interface for AD_MailBox
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_MailBox 
{

    /** TableName=AD_MailBox */
    public static final String Table_Name = "AD_MailBox";

    /** AD_Table_ID=540242 */
//    public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);

    /** Load Meta Data */

	/**
	 * Get Mandant.
	 * Mandant für diese Installation.
=======
import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_MailBox
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_MailBox 
{

	String Table_Name = "AD_MailBox";

//	/** AD_Table_ID=540242 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Client>(I_AD_MailBox.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
=======
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setAD_MailBox_ID (int AD_MailBox_ID);
=======
	void setAD_MailBox_ID (int AD_MailBox_ID);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getAD_MailBox_ID();

    /** Column definition for AD_MailBox_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_AD_MailBox_ID = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "AD_MailBox_ID", null);
    /** Column name AD_MailBox_ID */
    public static final String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Org>(I_AD_MailBox.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Erstellt.
	 * Datum, an dem dieser Eintrag erstellt wurde
=======
	int getAD_MailBox_ID();

	ModelColumn<I_AD_MailBox, Object> COLUMN_AD_MailBox_ID = new ModelColumn<>(I_AD_MailBox.class, "AD_MailBox_ID", null);
	String COLUMNNAME_AD_MailBox_ID = "AD_MailBox_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Get Created.
	 * Date this record was created
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "Created", null);
    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/**
	 * Get Erstellt durch.
	 * Nutzer, der diesen Eintrag erstellt hat
=======
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Created = new ModelColumn<>(I_AD_MailBox.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getCreatedBy();

    /** Column definition for CreatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User>(I_AD_MailBox.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
	 * EMail-Adresse
=======
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
=======
	void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Aktiv.
	 * Der Eintrag ist im System aktiv
=======
	java.lang.String getEMail();

	ModelColumn<I_AD_MailBox, Object> COLUMN_EMail = new ModelColumn<>(I_AD_MailBox.class, "EMail", null);
	String COLUMNNAME_EMail = "EMail";

	/**
	 * Set Active.
	 * The record is active in the system
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsActive (boolean IsActive);

	/**
	 * Get Aktiv.
	 * Der Eintrag ist im System aktiv
=======
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isActive();

    /** Column definition for IsActive */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SMTP-Anmeldung.
	 * Ihr EMail-Server verlangt eine Anmeldung
=======
	boolean isActive();

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_MailBox.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SMTP Authorization.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP-Anmeldung.
	 * Ihr EMail-Server verlangt eine Anmeldung
=======
	void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP Authorization.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isSmtpAuthorization();

    /** Column definition for IsSmtpAuthorization */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsSmtpAuthorization = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsSmtpAuthorization", null);
    /** Column name IsSmtpAuthorization */
    public static final String COLUMNNAME_IsSmtpAuthorization = "IsSmtpAuthorization";
=======
	boolean isSmtpAuthorization();

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsSmtpAuthorization = new ModelColumn<>(I_AD_MailBox.class, "IsSmtpAuthorization", null);
	String COLUMNNAME_IsSmtpAuthorization = "IsSmtpAuthorization";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setIsStartTLS (boolean IsStartTLS);
=======
	void setIsStartTLS (boolean IsStartTLS);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public boolean isStartTLS();

    /** Column definition for IsStartTLS */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsStartTLS = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsStartTLS", null);
    /** Column name IsStartTLS */
    public static final String COLUMNNAME_IsStartTLS = "IsStartTLS";

	/**
	 * Set Kennwort.
=======
	boolean isStartTLS();

	ModelColumn<I_AD_MailBox, Object> COLUMN_IsStartTLS = new ModelColumn<>(I_AD_MailBox.class, "IsStartTLS", null);
	String COLUMNNAME_IsStartTLS = "IsStartTLS";

	/**
	 * Set Client ID.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setPassword (java.lang.String Password);

	/**
	 * Get Kennwort.
=======
	void setMSGRAPH_ClientId (@Nullable java.lang.String MSGRAPH_ClientId);

	/**
	 * Get Client ID.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getPassword();

    /** Column definition for Password */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_Password = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "Password", null);
    /** Column name Password */
    public static final String COLUMNNAME_Password = "Password";

	/**
	 * Set EMail-Server.
	 * Hostname oder IP-Adresse des Servers für SMTP und IMAP
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSMTPHost (java.lang.String SMTPHost);

	/**
	 * Get EMail-Server.
	 * Hostname oder IP-Adresse des Servers für SMTP und IMAP
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSMTPHost();

    /** Column definition for SMTPHost */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPHost = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "SMTPHost", null);
    /** Column name SMTPHost */
    public static final String COLUMNNAME_SMTPHost = "SMTPHost";
=======
	@Nullable java.lang.String getMSGRAPH_ClientId();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_ClientId = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_ClientId", null);
	String COLUMNNAME_MSGRAPH_ClientId = "MSGRAPH_ClientId";

	/**
	 * Set Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMSGRAPH_ClientSecret (@Nullable java.lang.String MSGRAPH_ClientSecret);

	/**
	 * Get Client Secret.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMSGRAPH_ClientSecret();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_ClientSecret = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_ClientSecret", null);
	String COLUMNNAME_MSGRAPH_ClientSecret = "MSGRAPH_ClientSecret";

	/**
	 * Set Tenant ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMSGRAPH_TenantId (@Nullable java.lang.String MSGRAPH_TenantId);

	/**
	 * Get Tenant ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getMSGRAPH_TenantId();

	ModelColumn<I_AD_MailBox, Object> COLUMN_MSGRAPH_TenantId = new ModelColumn<>(I_AD_MailBox.class, "MSGRAPH_TenantId", null);
	String COLUMNNAME_MSGRAPH_TenantId = "MSGRAPH_TenantId";

	/**
	 * Set Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPassword (@Nullable java.lang.String Password);

	/**
	 * Get Password.
	 * Password of any length (case sensitive)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getPassword();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Password = new ModelColumn<>(I_AD_MailBox.class, "Password", null);
	String COLUMNNAME_Password = "Password";

	/**
	 * Set EMail-Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSMTPHost (@Nullable java.lang.String SMTPHost);

	/**
	 * Get EMail-Server.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getSMTPHost();

	ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPHost = new ModelColumn<>(I_AD_MailBox.class, "SMTPHost", null);
	String COLUMNNAME_SMTPHost = "SMTPHost";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Set SMTP Port.
	 *
	 * <br>Type: Integer
<<<<<<< HEAD
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSMTPPort (int SMTPPort);
=======
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSMTPPort (int SMTPPort);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	/**
	 * Get SMTP Port.
	 *
	 * <br>Type: Integer
<<<<<<< HEAD
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getSMTPPort();

    /** Column definition for SMTPPort */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPPort = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "SMTPPort", null);
    /** Column name SMTPPort */
    public static final String COLUMNNAME_SMTPPort = "SMTPPort";

	/**
	 * Get Aktualisiert.
	 * Datum, an dem dieser Eintrag aktualisiert wurde
=======
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSMTPPort();

	ModelColumn<I_AD_MailBox, Object> COLUMN_SMTPPort = new ModelColumn<>(I_AD_MailBox.class, "SMTPPort", null);
	String COLUMNNAME_SMTPPort = "SMTPPort";

	/**
	 * Set Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setType (java.lang.String Type);

	/**
	 * Get Type.
	 * Type of Validation (SQL, Java Script, Java Language)
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getType();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Type = new ModelColumn<>(I_AD_MailBox.class, "Type", null);
	String COLUMNNAME_Type = "Type";

	/**
	 * Get Updated.
	 * Date this record was updated
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "Updated", null);
    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Aktualisiert durch.
	 * Nutzer, der diesen Eintrag aktualisiert hat
=======
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_MailBox, Object> COLUMN_Updated = new ModelColumn<>(I_AD_MailBox.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public int getUpdatedBy();

    /** Column definition for UpdatedBy */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User>(I_AD_MailBox.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Registered EMail.
	 * Email of the responsible for the System
=======
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set UserName.
	 * UserName / Login to use for login
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public void setUserName (java.lang.String UserName);

	/**
	 * Get Registered EMail.
	 * Email of the responsible for the System
=======
	void setUserName (@Nullable java.lang.String UserName);

	/**
	 * Get UserName.
	 * UserName / Login to use for login
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
<<<<<<< HEAD
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "UserName", null);
    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";
=======
	@Nullable java.lang.String getUserName();

	ModelColumn<I_AD_MailBox, Object> COLUMN_UserName = new ModelColumn<>(I_AD_MailBox.class, "UserName", null);
	String COLUMNNAME_UserName = "UserName";
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
