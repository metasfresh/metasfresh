// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Client
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Client extends org.compiere.model.PO implements I_AD_Client, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -879548260L;

    /** Standard Constructor */
    public X_AD_Client (final Properties ctx, final int AD_Client_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Client_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Client (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Archive_Storage getAD_Archive_Storage()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_Storage_ID, org.compiere.model.I_AD_Archive_Storage.class);
	}

	@Override
	public void setAD_Archive_Storage(final org.compiere.model.I_AD_Archive_Storage AD_Archive_Storage)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_Storage_ID, org.compiere.model.I_AD_Archive_Storage.class, AD_Archive_Storage);
	}

	@Override
	public void setAD_Archive_Storage_ID (final int AD_Archive_Storage_ID)
	{
		if (AD_Archive_Storage_ID < 1) 
			set_Value (COLUMNNAME_AD_Archive_Storage_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Archive_Storage_ID, AD_Archive_Storage_ID);
	}

	@Override
	public int getAD_Archive_Storage_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_Storage_ID);
	}

	/** 
	 * AD_Language AD_Reference_ID=327
	 * Reference name: AD_Language System
	 */
	public static final int AD_LANGUAGE_AD_Reference_ID=327;
	@Override
	public void setAD_Language (final @Nullable java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_ReplicationStrategy getAD_ReplicationStrategy()
	{
		return get_ValueAsPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class);
	}

	@Override
	public void setAD_ReplicationStrategy(final org.compiere.model.I_AD_ReplicationStrategy AD_ReplicationStrategy)
	{
		set_ValueFromPO(COLUMNNAME_AD_ReplicationStrategy_ID, org.compiere.model.I_AD_ReplicationStrategy.class, AD_ReplicationStrategy);
	}

	@Override
	public void setAD_ReplicationStrategy_ID (final int AD_ReplicationStrategy_ID)
	{
		if (AD_ReplicationStrategy_ID < 1) 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, null);
		else 
			set_Value (COLUMNNAME_AD_ReplicationStrategy_ID, AD_ReplicationStrategy_ID);
	}

	@Override
	public int getAD_ReplicationStrategy_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_ReplicationStrategy_ID);
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
	@Override
	public void setAutoArchive (final java.lang.String AutoArchive)
	{
		set_Value (COLUMNNAME_AutoArchive, AutoArchive);
	}

	@Override
	public java.lang.String getAutoArchive() 
	{
		return get_ValueAsString(COLUMNNAME_AutoArchive);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setDocumentDir (final @Nullable java.lang.String DocumentDir)
	{
		set_Value (COLUMNNAME_DocumentDir, DocumentDir);
	}

	@Override
	public java.lang.String getDocumentDir() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentDir);
	}

	@Override
	public void setEMailTest (final @Nullable java.lang.String EMailTest)
	{
		set_Value (COLUMNNAME_EMailTest, EMailTest);
	}

	@Override
	public java.lang.String getEMailTest() 
	{
		return get_ValueAsString(COLUMNNAME_EMailTest);
	}

	@Override
	public void setIsCostImmediate (final boolean IsCostImmediate)
	{
		set_Value (COLUMNNAME_IsCostImmediate, IsCostImmediate);
	}

	@Override
	public boolean isCostImmediate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCostImmediate);
	}

	@Override
	public void setIsMultiLingualDocument (final boolean IsMultiLingualDocument)
	{
		set_Value (COLUMNNAME_IsMultiLingualDocument, IsMultiLingualDocument);
	}

	@Override
	public boolean isMultiLingualDocument() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMultiLingualDocument);
	}

	@Override
	public void setIsPostImmediate (final boolean IsPostImmediate)
	{
		set_Value (COLUMNNAME_IsPostImmediate, IsPostImmediate);
	}

	@Override
	public boolean isPostImmediate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsPostImmediate);
	}

	@Override
	public void setIsServerEMail (final boolean IsServerEMail)
	{
		set_Value (COLUMNNAME_IsServerEMail, IsServerEMail);
	}

	@Override
	public boolean isServerEMail() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsServerEMail);
	}

	@Override
	public void setIsSmtpAuthorization (final boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, IsSmtpAuthorization);
	}

	@Override
	public boolean isSmtpAuthorization() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsSmtpAuthorization);
	}

	@Override
	public void setIsStartTLS (final boolean IsStartTLS)
	{
		set_Value (COLUMNNAME_IsStartTLS, IsStartTLS);
	}

	@Override
	public boolean isStartTLS() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsStartTLS);
	}

	@Override
	public void setIsUseBetaFunctions (final boolean IsUseBetaFunctions)
	{
		set_Value (COLUMNNAME_IsUseBetaFunctions, IsUseBetaFunctions);
	}

	@Override
	public boolean isUseBetaFunctions() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsUseBetaFunctions);
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
	@Override
	public void setMMPolicy (final java.lang.String MMPolicy)
	{
		set_Value (COLUMNNAME_MMPolicy, MMPolicy);
	}

	@Override
	public java.lang.String getMMPolicy() 
	{
		return get_ValueAsString(COLUMNNAME_MMPolicy);
	}

	@Override
	public void setModelValidationClasses (final @Nullable java.lang.String ModelValidationClasses)
	{
		set_Value (COLUMNNAME_ModelValidationClasses, ModelValidationClasses);
	}

	@Override
	public java.lang.String getModelValidationClasses() 
	{
		return get_ValueAsString(COLUMNNAME_ModelValidationClasses);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public org.compiere.model.I_R_MailText getPasswordReset_MailText()
	{
		return get_ValueAsPO(COLUMNNAME_PasswordReset_MailText_ID, org.compiere.model.I_R_MailText.class);
	}

	@Override
	public void setPasswordReset_MailText(final org.compiere.model.I_R_MailText PasswordReset_MailText)
	{
		set_ValueFromPO(COLUMNNAME_PasswordReset_MailText_ID, org.compiere.model.I_R_MailText.class, PasswordReset_MailText);
	}

	@Override
	public void setPasswordReset_MailText_ID (final int PasswordReset_MailText_ID)
	{
		if (PasswordReset_MailText_ID < 1) 
			set_Value (COLUMNNAME_PasswordReset_MailText_ID, null);
		else 
			set_Value (COLUMNNAME_PasswordReset_MailText_ID, PasswordReset_MailText_ID);
	}

	@Override
	public int getPasswordReset_MailText_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_PasswordReset_MailText_ID);
	}

	@Override
	public void setRequestEMail (final @Nullable java.lang.String RequestEMail)
	{
		set_Value (COLUMNNAME_RequestEMail, RequestEMail);
	}

	@Override
	public java.lang.String getRequestEMail() 
	{
		return get_ValueAsString(COLUMNNAME_RequestEMail);
	}

	@Override
	public void setRequestFolder (final @Nullable java.lang.String RequestFolder)
	{
		set_Value (COLUMNNAME_RequestFolder, RequestFolder);
	}

	@Override
	public java.lang.String getRequestFolder() 
	{
		return get_ValueAsString(COLUMNNAME_RequestFolder);
	}

	@Override
	public void setRequestUser (final @Nullable java.lang.String RequestUser)
	{
		set_Value (COLUMNNAME_RequestUser, RequestUser);
	}

	@Override
	public java.lang.String getRequestUser() 
	{
		return get_ValueAsString(COLUMNNAME_RequestUser);
	}

	@Override
	public void setRequestUserPW (final @Nullable java.lang.String RequestUserPW)
	{
		set_Value (COLUMNNAME_RequestUserPW, RequestUserPW);
	}

	@Override
	public java.lang.String getRequestUserPW() 
	{
		return get_ValueAsString(COLUMNNAME_RequestUserPW);
	}

	@Override
	public void setSMTPHost (final @Nullable java.lang.String SMTPHost)
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	@Override
	public java.lang.String getSMTPHost() 
	{
		return get_ValueAsString(COLUMNNAME_SMTPHost);
	}

	@Override
	public void setSMTPPort (final int SMTPPort)
	{
		set_Value (COLUMNNAME_SMTPPort, SMTPPort);
	}

	@Override
	public int getSMTPPort() 
	{
		return get_ValueAsInt(COLUMNNAME_SMTPPort);
	}

	@Override
	public void setStoreAttachmentsOnFileSystem (final boolean StoreAttachmentsOnFileSystem)
	{
		set_Value (COLUMNNAME_StoreAttachmentsOnFileSystem, StoreAttachmentsOnFileSystem);
	}

	@Override
	public boolean isStoreAttachmentsOnFileSystem() 
	{
		return get_ValueAsBoolean(COLUMNNAME_StoreAttachmentsOnFileSystem);
	}

	@Override
	public void setUnixAttachmentPath (final @Nullable java.lang.String UnixAttachmentPath)
	{
		set_Value (COLUMNNAME_UnixAttachmentPath, UnixAttachmentPath);
	}

	@Override
	public java.lang.String getUnixAttachmentPath() 
	{
		return get_ValueAsString(COLUMNNAME_UnixAttachmentPath);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}

	@Override
	public void setWindowsAttachmentPath (final @Nullable java.lang.String WindowsAttachmentPath)
	{
		set_Value (COLUMNNAME_WindowsAttachmentPath, WindowsAttachmentPath);
	}

	@Override
	public java.lang.String getWindowsAttachmentPath() 
	{
		return get_ValueAsString(COLUMNNAME_WindowsAttachmentPath);
	}
}