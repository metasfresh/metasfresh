package org.compiere.model;


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
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client();

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_Client>(I_AD_MailBox.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAD_MailBox_ID (int AD_MailBox_ID);

	/**
	 * Get Mail Box.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
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
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getCreated();

    /** Column definition for Created */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User>(I_AD_MailBox.class, "CreatedBy", org.compiere.model.I_AD_User.class);
    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setEMail (java.lang.String EMail);

	/**
	 * Get eMail.
	 * EMail-Adresse
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMail();

    /** Column definition for EMail */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_EMail = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "EMail", null);
    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

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
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set SMTP-Anmeldung.
	 * Ihr EMail-Server verlangt eine Anmeldung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP-Anmeldung.
	 * Ihr EMail-Server verlangt eine Anmeldung
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSmtpAuthorization();

    /** Column definition for IsSmtpAuthorization */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsSmtpAuthorization = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsSmtpAuthorization", null);
    /** Column name IsSmtpAuthorization */
    public static final String COLUMNNAME_IsSmtpAuthorization = "IsSmtpAuthorization";

	/**
	 * Set Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsStartTLS (boolean IsStartTLS);

	/**
	 * Get Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStartTLS();

    /** Column definition for IsStartTLS */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_IsStartTLS = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "IsStartTLS", null);
    /** Column name IsStartTLS */
    public static final String COLUMNNAME_IsStartTLS = "IsStartTLS";

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

	/**
	 * Set SMTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setSMTPPort (int SMTPPort);

	/**
	 * Get SMTP Port.
	 *
	 * <br>Type: Integer
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
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.sql.Timestamp getUpdated();

    /** Column definition for Updated */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_MailBox, org.compiere.model.I_AD_User>(I_AD_MailBox.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUserName (java.lang.String UserName);

	/**
	 * Get Registered EMail.
	 * Email of the responsible for the System
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUserName();

    /** Column definition for UserName */
    public static final org.adempiere.model.ModelColumn<I_AD_MailBox, Object> COLUMN_UserName = new org.adempiere.model.ModelColumn<I_AD_MailBox, Object>(I_AD_MailBox.class, "UserName", null);
    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";
}
