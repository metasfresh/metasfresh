<<<<<<< HEAD
/** Generated Model - DO NOT CHANGE */
=======
// Generated Model - DO NOT CHANGE
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
<<<<<<< HEAD

/** Generated Model for AD_MailBox
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_MailBox extends org.compiere.model.PO implements I_AD_MailBox, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1993988422L;

    /** Standard Constructor */
    public X_AD_MailBox (Properties ctx, int AD_MailBox_ID, String trxName)
    {
      super (ctx, AD_MailBox_ID, trxName);
      /** if (AD_MailBox_ID == 0)
        {
			setAD_MailBox_ID (0);
			setEMail (null);
			setIsSmtpAuthorization (false); // N
			setIsStartTLS (false); // N
			setSMTPHost (null);
			setSMTPPort (0); // 25
        } */
    }

    /** Load Constructor */
    public X_AD_MailBox (Properties ctx, ResultSet rs, String trxName)
=======
import javax.annotation.Nullable;

/** Generated Model for AD_MailBox
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_MailBox extends org.compiere.model.PO implements I_AD_MailBox, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1265550932L;

    /** Standard Constructor */
    public X_AD_MailBox (final Properties ctx, final int AD_MailBox_ID, @Nullable final String trxName)
    {
      super (ctx, AD_MailBox_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_MailBox (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

	/** Set Mail Box.
		@param AD_MailBox_ID Mail Box	  */
	@Override
	public void setAD_MailBox_ID (int AD_MailBox_ID)
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAD_MailBox_ID (final int AD_MailBox_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (AD_MailBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, Integer.valueOf(AD_MailBox_ID));
	}

	/** Get Mail Box.
		@return Mail Box	  */
	@Override
	public int getAD_MailBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set eMail.
		@param EMail 
		EMail-Adresse
	  */
	@Override
	public void setEMail (java.lang.String EMail)
=======
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, AD_MailBox_ID);
	}

	@Override
	public int getAD_MailBox_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_MailBox_ID);
	}

	@Override
	public void setEMail (final java.lang.String EMail)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

<<<<<<< HEAD
	/** Get eMail.
		@return EMail-Adresse
	  */
	@Override
	public java.lang.String getEMail () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail);
	}

	/** Set SMTP-Anmeldung.
		@param IsSmtpAuthorization 
		Ihr EMail-Server verlangt eine Anmeldung
	  */
	@Override
	public void setIsSmtpAuthorization (boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, Boolean.valueOf(IsSmtpAuthorization));
	}

	/** Get SMTP-Anmeldung.
		@return Ihr EMail-Server verlangt eine Anmeldung
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

	/** Set Kennwort.
		@param Password Kennwort	  */
	@Override
	public void setPassword (java.lang.String Password)
=======
	@Override
	public java.lang.String getEMail() 
	{
		return get_ValueAsString(COLUMNNAME_EMail);
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
	public void setMSGRAPH_ClientId (final @Nullable java.lang.String MSGRAPH_ClientId)
	{
		set_Value (COLUMNNAME_MSGRAPH_ClientId, MSGRAPH_ClientId);
	}

	@Override
	public java.lang.String getMSGRAPH_ClientId() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_ClientId);
	}

	@Override
	public void setMSGRAPH_ClientSecret (final @Nullable java.lang.String MSGRAPH_ClientSecret)
	{
		set_Value (COLUMNNAME_MSGRAPH_ClientSecret, MSGRAPH_ClientSecret);
	}

	@Override
	public java.lang.String getMSGRAPH_ClientSecret() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_ClientSecret);
	}

	@Override
	public void setMSGRAPH_TenantId (final @Nullable java.lang.String MSGRAPH_TenantId)
	{
		set_Value (COLUMNNAME_MSGRAPH_TenantId, MSGRAPH_TenantId);
	}

	@Override
	public java.lang.String getMSGRAPH_TenantId() 
	{
		return get_ValueAsString(COLUMNNAME_MSGRAPH_TenantId);
	}

	@Override
	public void setPassword (final @Nullable java.lang.String Password)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Password, Password);
	}

<<<<<<< HEAD
	/** Get Kennwort.
		@return Kennwort	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** Set EMail-Server.
		@param SMTPHost 
		Hostname oder IP-Adresse des Servers für SMTP und IMAP
	  */
	@Override
	public void setSMTPHost (java.lang.String SMTPHost)
=======
	@Override
	public java.lang.String getPassword() 
	{
		return get_ValueAsString(COLUMNNAME_Password);
	}

	@Override
	public void setSMTPHost (final @Nullable java.lang.String SMTPHost)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

<<<<<<< HEAD
	/** Get EMail-Server.
		@return Hostname oder IP-Adresse des Servers für SMTP und IMAP
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

	/** Set Registered EMail.
		@param UserName 
		Email of the responsible for the System
	  */
	@Override
	public void setUserName (java.lang.String UserName)
=======
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

	/** 
	 * Type AD_Reference_ID=541904
	 * Reference name: AD_MailBox_Type
	 */
	public static final int TYPE_AD_Reference_ID=541904;
	/** SMTP = smtp */
	public static final String TYPE_SMTP = "smtp";
	/** MSGraph = msgraph */
	public static final String TYPE_MSGraph = "msgraph";
	@Override
	public void setType (final java.lang.String Type)
	{
		set_Value (COLUMNNAME_Type, Type);
	}

	@Override
	public java.lang.String getType() 
	{
		return get_ValueAsString(COLUMNNAME_Type);
	}

	@Override
	public void setUserName (final @Nullable java.lang.String UserName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

<<<<<<< HEAD
	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	@Override
	public java.lang.String getUserName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
=======
	@Override
	public java.lang.String getUserName() 
	{
		return get_ValueAsString(COLUMNNAME_UserName);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}