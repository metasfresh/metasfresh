/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Client
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Client extends org.compiere.model.PO implements I_AD_Client, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 681927464L;

    /** Standard Constructor */
    public X_AD_Client (Properties ctx, int AD_Client_ID, String trxName)
    {
      super (ctx, AD_Client_ID, trxName);
      /** if (AD_Client_ID == 0)
        {
			setAutoArchive (null); // N
			setIsCostImmediate (false); // N
			setIsMultiLingualDocument (false);
			setIsPostImmediate (false); // N
			setIsServerEMail (false);
			setIsSmtpAuthorization (false); // N
			setIsStartTLS (false); // N
			setIsUseASP (false); // N
			setIsUseBetaFunctions (true); // Y
			setMMPolicy (null); // F
			setName (null);
			setSMTPPort (0); // 25
			setStoreArchiveOnFileSystem (false);
			setStoreAttachmentsOnFileSystem (false);
			setValue (null);
        } */
    }

    /** Load Constructor */
    public X_AD_Client (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** 
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	/** Set Sprache.
		@param AD_Language 
		Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class);
	}

	@Override
	public void setAD_ReplicationStrategy(org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class, AD_ReplicationStrategy);
	}

	/** Set Replizierung - Strategie.
		@param AD_ReplicationStrategy_ID 
		Data Replication Strategy
	  */
	@Override
	public void setAD_ReplicationStrategy_ID (int AD_ReplicationStrategy_ID)
	{
		if (AD_ReplicationStrategy_ID < 1) 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, Integer.valueOf(AD_ReplicationStrategy_ID));
	}

	/** Get Replizierung - Strategie.
		@return Data Replication Strategy
	  */
	@Override
	public int getAD_ReplicationStrategy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_ReplicationStrategy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * AutoArchive AD_Reference_ID=334
	 * Reference name: AD_Client AutoArchive
	 */
	public static final int AUTOARCHIVE_AD_Reference_ID=334;
	/** None = N */
	public static final String AUTOARCHIVE_None = "N";
	/** AllReportsDocuments = 1 */
	public static final String AUTOARCHIVE_AllReportsDocuments = "1";
	/** Documents = 2 */
	public static final String AUTOARCHIVE_Documents = "2";
	/** ExternalDocuments = 3 */
	public static final String AUTOARCHIVE_ExternalDocuments = "3";
	/** Set Automatische Archivierung.
		@param AutoArchive 
		Enable and level of automatic Archive of documents
	  */
	@Override
	public void setAutoArchive (java.lang.String AutoArchive)
	{

		set_Value (COLUMNNAME_AutoArchive, AutoArchive);
	}

	/** Get Automatische Archivierung.
		@return Enable and level of automatic Archive of documents
	  */
	@Override
	public java.lang.String getAutoArchive () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AutoArchive);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Documenten-Verzeichnis.
		@param DocumentDir 
		Directory for documents from the application server
	  */
	@Override
	public void setDocumentDir (java.lang.String DocumentDir)
	{
		set_Value (COLUMNNAME_DocumentDir, DocumentDir);
	}

	/** Get Documenten-Verzeichnis.
		@return Directory for documents from the application server
	  */
	@Override
	public java.lang.String getDocumentDir () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentDir);
	}

	/** Set EMail Test.
		@param EMailTest 
		Test EMail
	  */
	@Override
	public void setEMailTest (java.lang.String EMailTest)
	{
		set_Value (COLUMNNAME_EMailTest, EMailTest);
	}

	/** Get EMail Test.
		@return Test EMail
	  */
	@Override
	public java.lang.String getEMailTest () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailTest);
	}

	/** Set Kostenrechnung sofort.
		@param IsCostImmediate 
		Update Costs immediately for testing
	  */
	@Override
	public void setIsCostImmediate (boolean IsCostImmediate)
	{
		set_Value (COLUMNNAME_IsCostImmediate, Boolean.valueOf(IsCostImmediate));
	}

	/** Get Kostenrechnung sofort.
		@return Update Costs immediately for testing
	  */
	@Override
	public boolean isCostImmediate () 
	{
		Object oo = get_Value(COLUMNNAME_IsCostImmediate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Multisprachliche Dokumente.
		@param IsMultiLingualDocument 
		Documents are Multi Lingual
	  */
	@Override
	public void setIsMultiLingualDocument (boolean IsMultiLingualDocument)
	{
		set_Value (COLUMNNAME_IsMultiLingualDocument, Boolean.valueOf(IsMultiLingualDocument));
	}

	/** Get Multisprachliche Dokumente.
		@return Documents are Multi Lingual
	  */
	@Override
	public boolean isMultiLingualDocument () 
	{
		Object oo = get_Value(COLUMNNAME_IsMultiLingualDocument);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Verbuchung sofort.
		@param IsPostImmediate 
		Post the accounting immediately for testing
	  */
	@Override
	public void setIsPostImmediate (boolean IsPostImmediate)
	{
		set_Value (COLUMNNAME_IsPostImmediate, Boolean.valueOf(IsPostImmediate));
	}

	/** Get Verbuchung sofort.
		@return Post the accounting immediately for testing
	  */
	@Override
	public boolean isPostImmediate () 
	{
		Object oo = get_Value(COLUMNNAME_IsPostImmediate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Server EMail.
		@param IsServerEMail 
		Send EMail from Server
	  */
	@Override
	public void setIsServerEMail (boolean IsServerEMail)
	{
		set_Value (COLUMNNAME_IsServerEMail, Boolean.valueOf(IsServerEMail));
	}

	/** Get Server EMail.
		@return Send EMail from Server
	  */
	@Override
	public boolean isServerEMail () 
	{
		Object oo = get_Value(COLUMNNAME_IsServerEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set SMTP-Anmeldung.
		@param IsSmtpAuthorization 
		Your mail server requires Authentication
	  */
	@Override
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, Boolean.valueOf(IsSmtpAuthorization));
	}

	/** Get SMTP-Anmeldung.
		@return Your mail server requires Authentication
	  */
	@Override
	public boolean isSmtpAuthorization () 
	{
		Object oo = get_Value(COLUMNNAME_IsSmtpAuthorization);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Start TLS.
		@param IsStartTLS Start TLS	  */
	@Override
	public void setIsStartTLS (boolean IsStartTLS)
	{
		set_Value (COLUMNNAME_IsStartTLS, Boolean.valueOf(IsStartTLS));
	}

	/** Get Start TLS.
		@return Start TLS	  */
	@Override
	public boolean isStartTLS () 
	{
		Object oo = get_Value(COLUMNNAME_IsStartTLS);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set IsUseASP.
		@param IsUseASP IsUseASP	  */
	@Override
	public void setIsUseASP (boolean IsUseASP)
	{
		set_Value (COLUMNNAME_IsUseASP, Boolean.valueOf(IsUseASP));
	}

	/** Get IsUseASP.
		@return IsUseASP	  */
	@Override
	public boolean isUseASP () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseASP);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Beta-Funktionalität verwenden.
		@param IsUseBetaFunctions 
		Enable the use of Beta Functionality
	  */
	@Override
	public void setIsUseBetaFunctions (boolean IsUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsUseBetaFunctions, Boolean.valueOf(IsUseBetaFunctions));
	}

	/** Get Beta-Funktionalität verwenden.
		@return Enable the use of Beta Functionality
	  */
	@Override
	public boolean isUseBetaFunctions () 
	{
		Object oo = get_Value(COLUMNNAME_IsUseBetaFunctions);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** 
	 * MMPolicy AD_Reference_ID=335
	 * Reference name: _MMPolicy
	 */
	public static final int MMPOLICY_AD_Reference_ID=335;
	/** LiFo = L */
	public static final String MMPOLICY_LiFo = "L";
	/** FiFo = F */
	public static final String MMPOLICY_FiFo = "F";
	/** Set Materialfluß.
		@param MMPolicy 
		Material Movement Policy
	  */
	@Override
	public void setMMPolicy (java.lang.String MMPolicy)
	{

		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	/** Get Materialfluß.
		@return Material Movement Policy
	  */
	@Override
	public java.lang.String getMMPolicy () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MMPolicy);
	}

	/** Set Modell-Validierungs-Klassen.
		@param ModelValidationClasses 
		List of data model validation classes separated by ;
	  */
	@Override
	public void setModelValidationClasses (java.lang.String ModelValidationClasses)
	{
		set_Value (COLUMNNAME_ModelValidationClasses, ModelValidationClasses);
	}

	/** Get Modell-Validierungs-Klassen.
		@return List of data model validation classes separated by ;
	  */
	@Override
	public java.lang.String getModelValidationClasses () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ModelValidationClasses);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_R_MailText getPasswordReset_MailText() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PasswordReset_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setPasswordReset_MailText(org.compiere.model.I_R_MailText PasswordReset_MailText)
	{
		set_ValueFromPO(COLUMNNAME_PasswordReset_MailText_ID, org.compiere.model.I_R_MailText.class, PasswordReset_MailText);
	}

	/** Set Password Reset Mail.
		@param PasswordReset_MailText_ID Password Reset Mail	  */
	@Override
	public void setPasswordReset_MailText_ID (int PasswordReset_MailText_ID)
	{
		if (PasswordReset_MailText_ID < 1) 
			set_Value (COLUMNNAME_PasswordReset_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_PasswordReset_MailText_ID, Integer.valueOf(PasswordReset_MailText_ID));
	}

	/** Get Password Reset Mail.
		@return Password Reset Mail	  */
	@Override
	public int getPasswordReset_MailText_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PasswordReset_MailText_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Anfrage-EMail.
		@param RequestEMail 
		EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	  */
	@Override
	public void setRequestEMail (java.lang.String RequestEMail)
	{
		set_Value (COLUMNNAME_RequestEMail, RequestEMail);
	}

	/** Get Anfrage-EMail.
		@return EMail address to send automated mails from or receive mails for automated processing (fully qualified)
	  */
	@Override
	public java.lang.String getRequestEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestEMail);
	}

	/** Set Anfrage-Verzeichnis.
		@param RequestFolder 
		EMail folder to process incoming emails; if empty INBOX is used
	  */
	@Override
	public void setRequestFolder (java.lang.String RequestFolder)
	{
		set_Value (COLUMNNAME_RequestFolder, RequestFolder);
	}

	/** Get Anfrage-Verzeichnis.
		@return EMail folder to process incoming emails; if empty INBOX is used
	  */
	@Override
	public java.lang.String getRequestFolder () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestFolder);
	}

	/** Set Anfrage-Nutzer.
		@param RequestUser 
		User Name (ID) of the email owner
	  */
	@Override
	public void setRequestUser (java.lang.String RequestUser)
	{
		set_Value (COLUMNNAME_RequestUser, RequestUser);
	}

	/** Get Anfrage-Nutzer.
		@return User Name (ID) of the email owner
	  */
	@Override
	public java.lang.String getRequestUser () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestUser);
	}

	/** Set Passwort Anfrage-Nutzer.
		@param RequestUserPW 
		Password of the user name (ID) for mail processing
	  */
	@Override
	public void setRequestUserPW (java.lang.String RequestUserPW)
	{
		set_Value (COLUMNNAME_RequestUserPW, RequestUserPW);
	}

	/** Get Passwort Anfrage-Nutzer.
		@return Password of the user name (ID) for mail processing
	  */
	@Override
	public java.lang.String getRequestUserPW () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_RequestUserPW);
	}

	/** Set EMail-Server.
		@param SMTPHost 
		Hostname of Mail Server for SMTP and IMAP
	  */
	@Override
	public void setSMTPHost (java.lang.String SMTPHost)
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	/** Get EMail-Server.
		@return Hostname of Mail Server for SMTP and IMAP
	  */
	@Override
	public java.lang.String getSMTPHost () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SMTPHost);
	}

	/** Set SMTP Port.
		@param SMTPPort SMTP Port	  */
	@Override
	public void setSMTPPort (int SMTPPort)
	{
		set_Value (COLUMNNAME_SMTPPort, Integer.valueOf(SMTPPort));
	}

	/** Get SMTP Port.
		@return SMTP Port	  */
	@Override
	public int getSMTPPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SMTPPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Store Archive On File System.
		@param StoreArchiveOnFileSystem Store Archive On File System	  */
	@Override
	public void setStoreArchiveOnFileSystem (boolean StoreArchiveOnFileSystem)
	{
		set_Value (COLUMNNAME_StoreArchiveOnFileSystem, Boolean.valueOf(StoreArchiveOnFileSystem));
	}

	/** Get Store Archive On File System.
		@return Store Archive On File System	  */
	@Override
	public boolean isStoreArchiveOnFileSystem () 
	{
		Object oo = get_Value(COLUMNNAME_StoreArchiveOnFileSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Store Attachments On File System.
		@param StoreAttachmentsOnFileSystem Store Attachments On File System	  */
	@Override
	public void setStoreAttachmentsOnFileSystem (boolean StoreAttachmentsOnFileSystem)
	{
		set_Value (COLUMNNAME_StoreAttachmentsOnFileSystem, Boolean.valueOf(StoreAttachmentsOnFileSystem));
	}

	/** Get Store Attachments On File System.
		@return Store Attachments On File System	  */
	@Override
	public boolean isStoreAttachmentsOnFileSystem () 
	{
		Object oo = get_Value(COLUMNNAME_StoreAttachmentsOnFileSystem);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Unix Archive Path.
		@param UnixArchivePath Unix Archive Path	  */
	@Override
	public void setUnixArchivePath (java.lang.String UnixArchivePath)
	{
		set_Value (COLUMNNAME_UnixArchivePath, UnixArchivePath);
	}

	/** Get Unix Archive Path.
		@return Unix Archive Path	  */
	@Override
	public java.lang.String getUnixArchivePath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UnixArchivePath);
	}

	/** Set Unix Attachment Path.
		@param UnixAttachmentPath Unix Attachment Path	  */
	@Override
	public void setUnixAttachmentPath (java.lang.String UnixAttachmentPath)
	{
		set_Value (COLUMNNAME_UnixAttachmentPath, UnixAttachmentPath);
	}

	/** Get Unix Attachment Path.
		@return Unix Attachment Path	  */
	@Override
	public java.lang.String getUnixAttachmentPath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UnixAttachmentPath);
	}

	/** Set Suchschlüssel.
		@param Value 
		Search key for the record in the format required - must be unique
	  */
	@Override
	public void setValue (java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	/** Get Suchschlüssel.
		@return Search key for the record in the format required - must be unique
	  */
	@Override
	public java.lang.String getValue () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Value);
	}

	/** Set Windows Archive Path.
		@param WindowsArchivePath Windows Archive Path	  */
	@Override
	public void setWindowsArchivePath (java.lang.String WindowsArchivePath)
	{
		set_Value (COLUMNNAME_WindowsArchivePath, WindowsArchivePath);
	}

	/** Get Windows Archive Path.
		@return Windows Archive Path	  */
	@Override
	public java.lang.String getWindowsArchivePath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WindowsArchivePath);
	}

	/** Set Windows Attachment Path.
		@param WindowsAttachmentPath Windows Attachment Path	  */
	@Override
	public void setWindowsAttachmentPath (java.lang.String WindowsAttachmentPath)
	{
		set_Value (COLUMNNAME_WindowsAttachmentPath, WindowsAttachmentPath);
	}

	/** Get Windows Attachment Path.
		@return Windows Attachment Path	  */
	@Override
	public java.lang.String getWindowsAttachmentPath () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WindowsAttachmentPath);
	}
}