package org.compiere.model;


/** Generated Interface for AD_Client
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_Client 
{

    /** TableName=AD_Client */
    public static final String Table_Name = "AD_Client";

    /** AD_Table_ID=112 */
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

    /** Column definition for AD_Client_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_Client> COLUMN_AD_Client_ID = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_Client>(I_AD_Client.class, "AD_Client_ID", org.compiere.model.I_AD_Client.class);
    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_Language (java.lang.String AD_Language);

	/**
	 * Get Sprache.
	 * Language for this entity
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAD_Language();

    /** Column definition for AD_Language */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_AD_Language = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "AD_Language", null);
    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_Org> COLUMN_AD_Org_ID = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_Org>(I_AD_Client.class, "AD_Org_ID", org.compiere.model.I_AD_Org.class);
    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Replizierung - Strategie.
	 * Data Replication Strategy
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID);

	/**
	 * Get Replizierung - Strategie.
	 * Data Replication Strategy
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getAD_ReplicationStrategy_ID();

	public org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy();

	public void setAD_ReplicationStrategy(org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy);

    /** Column definition for AD_ReplicationStrategy_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_ReplicationStrategy> COLUMN_AD_ReplicationStrategy_ID = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_ReplicationStrategy>(I_AD_Client.class, "AD_ReplicationStrategy_ID", org.compiere.model.I_AD_ReplicationStrategy.class);
    /** Column name AD_ReplicationStrategy_ID */
    public static final String COLUMNNAME_AD_ReplicationStrategy_ID = "AD_ReplicationStrategy_ID";

	/**
	 * Set Automatische Archivierung.
	 * Enable and level of automatic Archive of documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setAutoArchive (java.lang.String AutoArchive);

	/**
	 * Get Automatische Archivierung.
	 * Enable and level of automatic Archive of documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getAutoArchive();

    /** Column definition for AutoArchive */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_AutoArchive = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "AutoArchive", null);
    /** Column name AutoArchive */
    public static final String COLUMNNAME_AutoArchive = "AutoArchive";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_Created = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "Created", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_User> COLUMN_CreatedBy = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_User>(I_AD_Client.class, "CreatedBy", org.compiere.model.I_AD_User.class);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_Description = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "Description", null);
    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/**
	 * Set Documenten-Verzeichnis.
	 * Directory for documents from the application server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setDocumentDir (java.lang.String DocumentDir);

	/**
	 * Get Documenten-Verzeichnis.
	 * Directory for documents from the application server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getDocumentDir();

    /** Column definition for DocumentDir */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_DocumentDir = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "DocumentDir", null);
    /** Column name DocumentDir */
    public static final String COLUMNNAME_DocumentDir = "DocumentDir";

	/**
	 * Set EMail Test.
	 * Test EMail
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setEMailTest (java.lang.String EMailTest);

	/**
	 * Get EMail Test.
	 * Test EMail
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getEMailTest();

    /** Column definition for EMailTest */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_EMailTest = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "EMailTest", null);
    /** Column name EMailTest */
    public static final String COLUMNNAME_EMailTest = "EMailTest";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsActive = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsActive", null);
    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Kostenrechnung sofort.
	 * Update Costs immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsCostImmediate (boolean IsCostImmediate);

	/**
	 * Get Kostenrechnung sofort.
	 * Update Costs immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isCostImmediate();

    /** Column definition for IsCostImmediate */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsCostImmediate = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsCostImmediate", null);
    /** Column name IsCostImmediate */
    public static final String COLUMNNAME_IsCostImmediate = "IsCostImmediate";

	/**
	 * Set Multisprachliche Dokumente.
	 * Documents are Multi Lingual
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsMultiLingualDocument (boolean IsMultiLingualDocument);

	/**
	 * Get Multisprachliche Dokumente.
	 * Documents are Multi Lingual
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isMultiLingualDocument();

    /** Column definition for IsMultiLingualDocument */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsMultiLingualDocument = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsMultiLingualDocument", null);
    /** Column name IsMultiLingualDocument */
    public static final String COLUMNNAME_IsMultiLingualDocument = "IsMultiLingualDocument";

	/**
	 * Set Verbuchung sofort.
	 * Post the accounting immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsPostImmediate (boolean IsPostImmediate);

	/**
	 * Get Verbuchung sofort.
	 * Post the accounting immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isPostImmediate();

    /** Column definition for IsPostImmediate */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsPostImmediate = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsPostImmediate", null);
    /** Column name IsPostImmediate */
    public static final String COLUMNNAME_IsPostImmediate = "IsPostImmediate";

	/**
	 * Set Server EMail.
	 * Send EMail from Server
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsServerEMail (boolean IsServerEMail);

	/**
	 * Get Server EMail.
	 * Send EMail from Server
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isServerEMail();

    /** Column definition for IsServerEMail */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsServerEMail = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsServerEMail", null);
    /** Column name IsServerEMail */
    public static final String COLUMNNAME_IsServerEMail = "IsServerEMail";

	/**
	 * Set SMTP-Anmeldung.
	 * Your mail server requires Authentication
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP-Anmeldung.
	 * Your mail server requires Authentication
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isSmtpAuthorization();

    /** Column definition for IsSmtpAuthorization */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsSmtpAuthorization = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsSmtpAuthorization", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsStartTLS = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsStartTLS", null);
    /** Column name IsStartTLS */
    public static final String COLUMNNAME_IsStartTLS = "IsStartTLS";

	/**
	 * Set IsUseASP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseASP (boolean IsUseASP);

	/**
	 * Get IsUseASP.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseASP();

    /** Column definition for IsUseASP */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsUseASP = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsUseASP", null);
    /** Column name IsUseASP */
    public static final String COLUMNNAME_IsUseASP = "IsUseASP";

	/**
	 * Set Beta-Funktionalität verwenden.
	 * Enable the use of Beta Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setIsUseBetaFunctions (boolean IsUseBetaFunctions);

	/**
	 * Get Beta-Funktionalität verwenden.
	 * Enable the use of Beta Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isUseBetaFunctions();

    /** Column definition for IsUseBetaFunctions */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_IsUseBetaFunctions = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "IsUseBetaFunctions", null);
    /** Column name IsUseBetaFunctions */
    public static final String COLUMNNAME_IsUseBetaFunctions = "IsUseBetaFunctions";

	/**
	 * Set Materialfluß.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setMMPolicy (java.lang.String MMPolicy);

	/**
	 * Get Materialfluß.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getMMPolicy();

    /** Column definition for MMPolicy */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_MMPolicy = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "MMPolicy", null);
    /** Column name MMPolicy */
    public static final String COLUMNNAME_MMPolicy = "MMPolicy";

	/**
	 * Set Modell-Validierungs-Klassen.
	 * List of data model validation classes separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setModelValidationClasses (java.lang.String ModelValidationClasses);

	/**
	 * Get Modell-Validierungs-Klassen.
	 * List of data model validation classes separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getModelValidationClasses();

    /** Column definition for ModelValidationClasses */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_ModelValidationClasses = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "ModelValidationClasses", null);
    /** Column name ModelValidationClasses */
    public static final String COLUMNNAME_ModelValidationClasses = "ModelValidationClasses";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_Name = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "Name", null);
    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/**
	 * Set Password Reset Mail.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setPasswordReset_MailText_ID (int PasswordReset_MailText_ID);

	/**
	 * Get Password Reset Mail.
	 *
	 * <br>Type: Table
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public int getPasswordReset_MailText_ID();

	public org.compiere.model.I_R_MailText getPasswordReset_MailText();

	public void setPasswordReset_MailText(org.compiere.model.I_R_MailText PasswordReset_MailText);

    /** Column definition for PasswordReset_MailText_ID */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_R_MailText> COLUMN_PasswordReset_MailText_ID = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_R_MailText>(I_AD_Client.class, "PasswordReset_MailText_ID", org.compiere.model.I_R_MailText.class);
    /** Column name PasswordReset_MailText_ID */
    public static final String COLUMNNAME_PasswordReset_MailText_ID = "PasswordReset_MailText_ID";

	/**
	 * Set Anfrage-EMail.
	 * EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestEMail (java.lang.String RequestEMail);

	/**
	 * Get Anfrage-EMail.
	 * EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestEMail();

    /** Column definition for RequestEMail */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_RequestEMail = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "RequestEMail", null);
    /** Column name RequestEMail */
    public static final String COLUMNNAME_RequestEMail = "RequestEMail";

	/**
	 * Set Anfrage-Verzeichnis.
	 * EMail folder to process incoming emails;
 if empty INBOX is used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestFolder (java.lang.String RequestFolder);

	/**
	 * Get Anfrage-Verzeichnis.
	 * EMail folder to process incoming emails;
 if empty INBOX is used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestFolder();

    /** Column definition for RequestFolder */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_RequestFolder = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "RequestFolder", null);
    /** Column name RequestFolder */
    public static final String COLUMNNAME_RequestFolder = "RequestFolder";

	/**
	 * Set Anfrage-Nutzer.
	 * User Name (ID) of the email owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestUser (java.lang.String RequestUser);

	/**
	 * Get Anfrage-Nutzer.
	 * User Name (ID) of the email owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestUser();

    /** Column definition for RequestUser */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_RequestUser = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "RequestUser", null);
    /** Column name RequestUser */
    public static final String COLUMNNAME_RequestUser = "RequestUser";

	/**
	 * Set Passwort Anfrage-Nutzer.
	 * Password of the user name (ID) for mail processing
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setRequestUserPW (java.lang.String RequestUserPW);

	/**
	 * Get Passwort Anfrage-Nutzer.
	 * Password of the user name (ID) for mail processing
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getRequestUserPW();

    /** Column definition for RequestUserPW */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_RequestUserPW = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "RequestUserPW", null);
    /** Column name RequestUserPW */
    public static final String COLUMNNAME_RequestUserPW = "RequestUserPW";

	/**
	 * Set EMail-Server.
	 * Hostname of Mail Server for SMTP and IMAP
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setSMTPHost (java.lang.String SMTPHost);

	/**
	 * Get EMail-Server.
	 * Hostname of Mail Server for SMTP and IMAP
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getSMTPHost();

    /** Column definition for SMTPHost */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_SMTPHost = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "SMTPHost", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_SMTPPort = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "SMTPPort", null);
    /** Column name SMTPPort */
    public static final String COLUMNNAME_SMTPPort = "SMTPPort";

	/**
	 * Set Store Archive On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStoreArchiveOnFileSystem (boolean StoreArchiveOnFileSystem);

	/**
	 * Get Store Archive On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStoreArchiveOnFileSystem();

    /** Column definition for StoreArchiveOnFileSystem */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_StoreArchiveOnFileSystem = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "StoreArchiveOnFileSystem", null);
    /** Column name StoreArchiveOnFileSystem */
    public static final String COLUMNNAME_StoreArchiveOnFileSystem = "StoreArchiveOnFileSystem";

	/**
	 * Set Store Attachments On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setStoreAttachmentsOnFileSystem (boolean StoreAttachmentsOnFileSystem);

	/**
	 * Get Store Attachments On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public boolean isStoreAttachmentsOnFileSystem();

    /** Column definition for StoreAttachmentsOnFileSystem */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_StoreAttachmentsOnFileSystem = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "StoreAttachmentsOnFileSystem", null);
    /** Column name StoreAttachmentsOnFileSystem */
    public static final String COLUMNNAME_StoreAttachmentsOnFileSystem = "StoreAttachmentsOnFileSystem";

	/**
	 * Set Unix Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnixArchivePath (java.lang.String UnixArchivePath);

	/**
	 * Get Unix Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUnixArchivePath();

    /** Column definition for UnixArchivePath */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_UnixArchivePath = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "UnixArchivePath", null);
    /** Column name UnixArchivePath */
    public static final String COLUMNNAME_UnixArchivePath = "UnixArchivePath";

	/**
	 * Set Unix Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setUnixAttachmentPath (java.lang.String UnixAttachmentPath);

	/**
	 * Get Unix Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getUnixAttachmentPath();

    /** Column definition for UnixAttachmentPath */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_UnixAttachmentPath = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "UnixAttachmentPath", null);
    /** Column name UnixAttachmentPath */
    public static final String COLUMNNAME_UnixAttachmentPath = "UnixAttachmentPath";

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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_Updated = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "Updated", null);
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
    public static final org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_User> COLUMN_UpdatedBy = new org.adempiere.model.ModelColumn<I_AD_Client, org.compiere.model.I_AD_User>(I_AD_Client.class, "UpdatedBy", org.compiere.model.I_AD_User.class);
    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public void setValue (java.lang.String Value);

	/**
	 * Get Suchschlüssel.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	public java.lang.String getValue();

    /** Column definition for Value */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_Value = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "Value", null);
    /** Column name Value */
    public static final String COLUMNNAME_Value = "Value";

	/**
	 * Set Windows Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWindowsArchivePath (java.lang.String WindowsArchivePath);

	/**
	 * Get Windows Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWindowsArchivePath();

    /** Column definition for WindowsArchivePath */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_WindowsArchivePath = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "WindowsArchivePath", null);
    /** Column name WindowsArchivePath */
    public static final String COLUMNNAME_WindowsArchivePath = "WindowsArchivePath";

	/**
	 * Set Windows Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public void setWindowsAttachmentPath (java.lang.String WindowsAttachmentPath);

	/**
	 * Get Windows Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	public java.lang.String getWindowsAttachmentPath();

    /** Column definition for WindowsAttachmentPath */
    public static final org.adempiere.model.ModelColumn<I_AD_Client, Object> COLUMN_WindowsAttachmentPath = new org.adempiere.model.ModelColumn<I_AD_Client, Object>(I_AD_Client.class, "WindowsAttachmentPath", null);
    /** Column name WindowsAttachmentPath */
    public static final String COLUMNNAME_WindowsAttachmentPath = "WindowsAttachmentPath";
}
