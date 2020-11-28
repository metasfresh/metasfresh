/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

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

	/** Set Mail Box.
		@param AD_MailBox_ID Mail Box	  */
	@Override
	public void setAD_MailBox_ID (int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, null);
		else 
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
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

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
	{
		set_Value (COLUMNNAME_Password, Password);
	}

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
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

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
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	@Override
	public java.lang.String getUserName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserName);
	}
}