package org.compiere.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for AD_Client
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_AD_Client 
{

	String Table_Name = "AD_Client";

//	/** AD_Table_ID=112 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Set Archive Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Archive_Storage_ID (int AD_Archive_Storage_ID);

	/**
	 * Get Archive Storage.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_Archive_Storage_ID();

	@Nullable org.compiere.model.I_AD_Archive_Storage getAD_Archive_Storage();

	void setAD_Archive_Storage(@Nullable org.compiere.model.I_AD_Archive_Storage AD_Archive_Storage);

	ModelColumn<I_AD_Client, org.compiere.model.I_AD_Archive_Storage> COLUMN_AD_Archive_Storage_ID = new ModelColumn<>(I_AD_Client.class, "AD_Archive_Storage_ID", org.compiere.model.I_AD_Archive_Storage.class);
	String COLUMNNAME_AD_Archive_Storage_ID = "AD_Archive_Storage_ID";

	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Language.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_Language (@Nullable java.lang.String AD_Language);

	/**
	 * Get Language.
	 * Language for this entity
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAD_Language();

	ModelColumn<I_AD_Client, Object> COLUMN_AD_Language = new ModelColumn<>(I_AD_Client.class, "AD_Language", null);
	String COLUMNNAME_AD_Language = "AD_Language";

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
	 * Set Replication Strategy.
	 * Data Replication Strategy
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID);

	/**
	 * Get Replication Strategy.
	 * Data Replication Strategy
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getAD_ReplicationStrategy_ID();

	@Nullable org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy();

	void setAD_ReplicationStrategy(@Nullable org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy);

	ModelColumn<I_AD_Client, org.compiere.model.I_AD_ReplicationStrategy> COLUMN_AD_ReplicationStrategy_ID = new ModelColumn<>(I_AD_Client.class, "AD_ReplicationStrategy_ID", org.compiere.model.I_AD_ReplicationStrategy.class);
	String COLUMNNAME_AD_ReplicationStrategy_ID = "AD_ReplicationStrategy_ID";

	/**
	 * Set Auto Archive.
	 * Enable and level of automatic Archive of documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAutoArchive (java.lang.String AutoArchive);

	/**
	 * Get Auto Archive.
	 * Enable and level of automatic Archive of documents
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getAutoArchive();

	ModelColumn<I_AD_Client, Object> COLUMN_AutoArchive = new ModelColumn<>(I_AD_Client.class, "AutoArchive", null);
	String COLUMNNAME_AutoArchive = "AutoArchive";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_AD_Client, Object> COLUMN_Created = new ModelColumn<>(I_AD_Client.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDescription (@Nullable java.lang.String Description);

	/**
	 * Get Description.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDescription();

	ModelColumn<I_AD_Client, Object> COLUMN_Description = new ModelColumn<>(I_AD_Client.class, "Description", null);
	String COLUMNNAME_Description = "Description";

	/**
	 * Set Document Directory.
	 * Directory for documents from the application server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setDocumentDir (@Nullable java.lang.String DocumentDir);

	/**
	 * Get Document Directory.
	 * Directory for documents from the application server
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getDocumentDir();

	ModelColumn<I_AD_Client, Object> COLUMN_DocumentDir = new ModelColumn<>(I_AD_Client.class, "DocumentDir", null);
	String COLUMNNAME_DocumentDir = "DocumentDir";

	/**
	 * Set Test EMail.
	 * Test EMail Connection
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setEMailTest (@Nullable java.lang.String EMailTest);

	/**
	 * Get Test EMail.
	 * Test EMail Connection
	 *
	 * <br>Type: Button
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getEMailTest();

	ModelColumn<I_AD_Client, Object> COLUMN_EMailTest = new ModelColumn<>(I_AD_Client.class, "EMailTest", null);
	String COLUMNNAME_EMailTest = "EMailTest";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_AD_Client, Object> COLUMN_IsActive = new ModelColumn<>(I_AD_Client.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set Cost Immediately.
	 * Update Costs immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsCostImmediate (boolean IsCostImmediate);

	/**
	 * Get Cost Immediately.
	 * Update Costs immediately for testing
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isCostImmediate();

	ModelColumn<I_AD_Client, Object> COLUMN_IsCostImmediate = new ModelColumn<>(I_AD_Client.class, "IsCostImmediate", null);
	String COLUMNNAME_IsCostImmediate = "IsCostImmediate";

	/**
	 * Set Multi Lingual Documents.
	 * Documents are Multi Lingual
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsMultiLingualDocument (boolean IsMultiLingualDocument);

	/**
	 * Get Multi Lingual Documents.
	 * Documents are Multi Lingual
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isMultiLingualDocument();

	ModelColumn<I_AD_Client, Object> COLUMN_IsMultiLingualDocument = new ModelColumn<>(I_AD_Client.class, "IsMultiLingualDocument", null);
	String COLUMNNAME_IsMultiLingualDocument = "IsMultiLingualDocument";

	/**
	 * Set Post Immediately (Deprecated).
	 * Post the accounting immediately for testing (Deprecated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsPostImmediate (boolean IsPostImmediate);

	/**
	 * Get Post Immediately (Deprecated).
	 * Post the accounting immediately for testing (Deprecated)
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isPostImmediate();

	ModelColumn<I_AD_Client, Object> COLUMN_IsPostImmediate = new ModelColumn<>(I_AD_Client.class, "IsPostImmediate", null);
	String COLUMNNAME_IsPostImmediate = "IsPostImmediate";

	/**
	 * Set Server EMail.
	 * Send EMail from Server
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsServerEMail (boolean IsServerEMail);

	/**
	 * Get Server EMail.
	 * Send EMail from Server
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isServerEMail();

	ModelColumn<I_AD_Client, Object> COLUMN_IsServerEMail = new ModelColumn<>(I_AD_Client.class, "IsServerEMail", null);
	String COLUMNNAME_IsServerEMail = "IsServerEMail";

	/**
	 * Set SMTP Authorization.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsSmtpAuthorization (boolean IsSmtpAuthorization);

	/**
	 * Get SMTP Authorization.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isSmtpAuthorization();

	ModelColumn<I_AD_Client, Object> COLUMN_IsSmtpAuthorization = new ModelColumn<>(I_AD_Client.class, "IsSmtpAuthorization", null);
	String COLUMNNAME_IsSmtpAuthorization = "IsSmtpAuthorization";

	/**
	 * Set Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsStartTLS (boolean IsStartTLS);

	/**
	 * Get Start TLS.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStartTLS();

	ModelColumn<I_AD_Client, Object> COLUMN_IsStartTLS = new ModelColumn<>(I_AD_Client.class, "IsStartTLS", null);
	String COLUMNNAME_IsStartTLS = "IsStartTLS";

	/**
	 * Set Use Beta Functions.
	 * Enable the use of Beta Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsUseBetaFunctions (boolean IsUseBetaFunctions);

	/**
	 * Get Use Beta Functions.
	 * Enable the use of Beta Functionality
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isUseBetaFunctions();

	ModelColumn<I_AD_Client, Object> COLUMN_IsUseBetaFunctions = new ModelColumn<>(I_AD_Client.class, "IsUseBetaFunctions", null);
	String COLUMNNAME_IsUseBetaFunctions = "IsUseBetaFunctions";

	/**
	 * Set Material Policy.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setMMPolicy (java.lang.String MMPolicy);

	/**
	 * Get Material Policy.
	 * Material Movement Policy
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getMMPolicy();

	ModelColumn<I_AD_Client, Object> COLUMN_MMPolicy = new ModelColumn<>(I_AD_Client.class, "MMPolicy", null);
	String COLUMNNAME_MMPolicy = "MMPolicy";

	/**
	 * Set Model Validation Classes.
	 * List of data model validation classes separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setModelValidationClasses (@Nullable java.lang.String ModelValidationClasses);

	/**
	 * Get Model Validation Classes.
	 * List of data model validation classes separated by ;

	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getModelValidationClasses();

	ModelColumn<I_AD_Client, Object> COLUMN_ModelValidationClasses = new ModelColumn<>(I_AD_Client.class, "ModelValidationClasses", null);
	String COLUMNNAME_ModelValidationClasses = "ModelValidationClasses";

	/**
	 * Set Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setName (java.lang.String Name);

	/**
	 * Get Name.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getName();

	ModelColumn<I_AD_Client, Object> COLUMN_Name = new ModelColumn<>(I_AD_Client.class, "Name", null);
	String COLUMNNAME_Name = "Name";

	/**
	 * Set Password Reset Mail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setPasswordReset_MailText_ID (int PasswordReset_MailText_ID);

	/**
	 * Get Password Reset Mail.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getPasswordReset_MailText_ID();

	@Nullable org.compiere.model.I_R_MailText getPasswordReset_MailText();

	void setPasswordReset_MailText(@Nullable org.compiere.model.I_R_MailText PasswordReset_MailText);

	ModelColumn<I_AD_Client, org.compiere.model.I_R_MailText> COLUMN_PasswordReset_MailText_ID = new ModelColumn<>(I_AD_Client.class, "PasswordReset_MailText_ID", org.compiere.model.I_R_MailText.class);
	String COLUMNNAME_PasswordReset_MailText_ID = "PasswordReset_MailText_ID";

	/**
	 * Set Request EMail.
	 * EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestEMail (@Nullable java.lang.String RequestEMail);

	/**
	 * Get Request EMail.
	 * EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestEMail();

	ModelColumn<I_AD_Client, Object> COLUMN_RequestEMail = new ModelColumn<>(I_AD_Client.class, "RequestEMail", null);
	String COLUMNNAME_RequestEMail = "RequestEMail";

	/**
	 * Set Request Folder.
	 * EMail folder to process incoming emails;
 if empty INBOX is used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestFolder (@Nullable java.lang.String RequestFolder);

	/**
	 * Get Request Folder.
	 * EMail folder to process incoming emails;
 if empty INBOX is used
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestFolder();

	ModelColumn<I_AD_Client, Object> COLUMN_RequestFolder = new ModelColumn<>(I_AD_Client.class, "RequestFolder", null);
	String COLUMNNAME_RequestFolder = "RequestFolder";

	/**
	 * Set Request User.
	 * User Name (ID) of the email owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestUser (@Nullable java.lang.String RequestUser);

	/**
	 * Get Request User.
	 * User Name (ID) of the email owner
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestUser();

	ModelColumn<I_AD_Client, Object> COLUMN_RequestUser = new ModelColumn<>(I_AD_Client.class, "RequestUser", null);
	String COLUMNNAME_RequestUser = "RequestUser";

	/**
	 * Set Request User Password.
	 * Password of the user name (ID) for mail processing
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setRequestUserPW (@Nullable java.lang.String RequestUserPW);

	/**
	 * Get Request User Password.
	 * Password of the user name (ID) for mail processing
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getRequestUserPW();

	ModelColumn<I_AD_Client, Object> COLUMN_RequestUserPW = new ModelColumn<>(I_AD_Client.class, "RequestUserPW", null);
	String COLUMNNAME_RequestUserPW = "RequestUserPW";

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

	ModelColumn<I_AD_Client, Object> COLUMN_SMTPHost = new ModelColumn<>(I_AD_Client.class, "SMTPHost", null);
	String COLUMNNAME_SMTPHost = "SMTPHost";

	/**
	 * Set SMTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setSMTPPort (int SMTPPort);

	/**
	 * Get SMTP Port.
	 *
	 * <br>Type: Integer
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getSMTPPort();

	ModelColumn<I_AD_Client, Object> COLUMN_SMTPPort = new ModelColumn<>(I_AD_Client.class, "SMTPPort", null);
	String COLUMNNAME_SMTPPort = "SMTPPort";

	/**
	 * Set Store Archive On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStoreArchiveOnFileSystem (boolean StoreArchiveOnFileSystem);

	/**
	 * Get Store Archive On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStoreArchiveOnFileSystem();

	ModelColumn<I_AD_Client, Object> COLUMN_StoreArchiveOnFileSystem = new ModelColumn<>(I_AD_Client.class, "StoreArchiveOnFileSystem", null);
	String COLUMNNAME_StoreArchiveOnFileSystem = "StoreArchiveOnFileSystem";

	/**
	 * Set Store Attachments On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setStoreAttachmentsOnFileSystem (boolean StoreAttachmentsOnFileSystem);

	/**
	 * Get Store Attachments On File System.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isStoreAttachmentsOnFileSystem();

	ModelColumn<I_AD_Client, Object> COLUMN_StoreAttachmentsOnFileSystem = new ModelColumn<>(I_AD_Client.class, "StoreAttachmentsOnFileSystem", null);
	String COLUMNNAME_StoreAttachmentsOnFileSystem = "StoreAttachmentsOnFileSystem";

	/**
	 * Set Unix Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnixArchivePath (@Nullable java.lang.String UnixArchivePath);

	/**
	 * Get Unix Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUnixArchivePath();

	ModelColumn<I_AD_Client, Object> COLUMN_UnixArchivePath = new ModelColumn<>(I_AD_Client.class, "UnixArchivePath", null);
	String COLUMNNAME_UnixArchivePath = "UnixArchivePath";

	/**
	 * Set Unix Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setUnixAttachmentPath (@Nullable java.lang.String UnixAttachmentPath);

	/**
	 * Get Unix Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getUnixAttachmentPath();

	ModelColumn<I_AD_Client, Object> COLUMN_UnixAttachmentPath = new ModelColumn<>(I_AD_Client.class, "UnixAttachmentPath", null);
	String COLUMNNAME_UnixAttachmentPath = "UnixAttachmentPath";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_AD_Client, Object> COLUMN_Updated = new ModelColumn<>(I_AD_Client.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/**
	 * Set Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setValue (java.lang.String Value);

	/**
	 * Get Search Key.
	 * Search key for the record in the format required - must be unique
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getValue();

	ModelColumn<I_AD_Client, Object> COLUMN_Value = new ModelColumn<>(I_AD_Client.class, "Value", null);
	String COLUMNNAME_Value = "Value";

	/**
	 * Set Windows Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWindowsArchivePath (@Nullable java.lang.String WindowsArchivePath);

	/**
	 * Get Windows Archive Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWindowsArchivePath();

	ModelColumn<I_AD_Client, Object> COLUMN_WindowsArchivePath = new ModelColumn<>(I_AD_Client.class, "WindowsArchivePath", null);
	String COLUMNNAME_WindowsArchivePath = "WindowsArchivePath";

	/**
	 * Set Windows Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setWindowsAttachmentPath (@Nullable java.lang.String WindowsAttachmentPath);

	/**
	 * Get Windows Attachment Path.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getWindowsAttachmentPath();

	ModelColumn<I_AD_Client, Object> COLUMN_WindowsAttachmentPath = new ModelColumn<>(I_AD_Client.class, "WindowsAttachmentPath", null);
	String COLUMNNAME_WindowsAttachmentPath = "WindowsAttachmentPath";
}
