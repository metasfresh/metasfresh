/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Mail
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Mail extends org.compiere.model.PO implements I_C_Mail, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -794005892L;

    /** Standard Constructor */
    public X_C_Mail (Properties ctx, int C_Mail_ID, String trxName)
    {
      super (ctx, C_Mail_ID, trxName);
      /** if (C_Mail_ID == 0)
        {
			setC_Mail_ID (0);
			setIsInboundEMail (true); // Y
        } */
    }

    /** Load Constructor */
    public X_C_Mail (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Mail.
		@param C_Mail_ID Mail	  */
	@Override
	public void setC_Mail_ID (int C_Mail_ID)
	{
		if (C_Mail_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Mail_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Mail_ID, Integer.valueOf(C_Mail_ID));
	}

	/** Get Mail.
		@return Mail	  */
	@Override
	public int getC_Mail_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Mail_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Inhalt.
		@param ContentText Inhalt	  */
	@Override
	public void setContentText (java.lang.String ContentText)
	{
		set_Value (COLUMNNAME_ContentText, ContentText);
	}

	/** Get Inhalt.
		@return Inhalt	  */
	@Override
	public java.lang.String getContentText () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContentText);
	}

	/** Set Content type.
		@param ContentType Content type	  */
	@Override
	public void setContentType (java.lang.String ContentType)
	{
		set_Value (COLUMNNAME_ContentType, ContentType);
	}

	/** Get Content type.
		@return Content type	  */
	@Override
	public java.lang.String getContentType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ContentType);
	}

	/** Set Eingangsdatum.
		@param DateReceived 
		Datum, zu dem ein Produkt empfangen wurde
	  */
	@Override
	public void setDateReceived (java.sql.Timestamp DateReceived)
	{
		set_Value (COLUMNNAME_DateReceived, DateReceived);
	}

	/** Get Eingangsdatum.
		@return Datum, zu dem ein Produkt empfangen wurde
	  */
	@Override
	public java.sql.Timestamp getDateReceived () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateReceived);
	}

	/** Set EMail Bcc.
		@param EMail_Bcc EMail Bcc	  */
	@Override
	public void setEMail_Bcc (java.lang.String EMail_Bcc)
	{
		set_Value (COLUMNNAME_EMail_Bcc, EMail_Bcc);
	}

	/** Get EMail Bcc.
		@return EMail Bcc	  */
	@Override
	public java.lang.String getEMail_Bcc () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_Bcc);
	}

	/** Set EMail Cc.
		@param EMail_Cc EMail Cc	  */
	@Override
	public void setEMail_Cc (java.lang.String EMail_Cc)
	{
		set_Value (COLUMNNAME_EMail_Cc, EMail_Cc);
	}

	/** Get EMail Cc.
		@return EMail Cc	  */
	@Override
	public java.lang.String getEMail_Cc () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_Cc);
	}

	/** Set EMail Absender.
		@param EMail_From 
		Full EMail address used to send requests - e.g. edi@organization.com
	  */
	@Override
	public void setEMail_From (java.lang.String EMail_From)
	{
		set_Value (COLUMNNAME_EMail_From, EMail_From);
	}

	/** Get EMail Absender.
		@return Full EMail address used to send requests - e.g. edi@organization.com
	  */
	@Override
	public java.lang.String getEMail_From () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_From);
	}

	/** Set EMail Empfänger.
		@param EMail_To 
		EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public void setEMail_To (java.lang.String EMail_To)
	{
		set_Value (COLUMNNAME_EMail_To, EMail_To);
	}

	/** Get EMail Empfänger.
		@return EMail address to send requests to - e.g. edi@manufacturer.com 
	  */
	@Override
	public java.lang.String getEMail_To () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMail_To);
	}

	/** Set EMail Headers.
		@param EMailHeadersJSON EMail Headers	  */
	@Override
	public void setEMailHeadersJSON (java.lang.String EMailHeadersJSON)
	{
		set_Value (COLUMNNAME_EMailHeadersJSON, EMailHeadersJSON);
	}

	/** Get EMail Headers.
		@return EMail Headers	  */
	@Override
	public java.lang.String getEMailHeadersJSON () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EMailHeadersJSON);
	}

	@Override
	public org.compiere.model.I_AD_User getFrom_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_From_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setFrom_User(org.compiere.model.I_AD_User From_User)
	{
		set_ValueFromPO(COLUMNNAME_From_User_ID, org.compiere.model.I_AD_User.class, From_User);
	}

	/** Set From User.
		@param From_User_ID From User	  */
	@Override
	public void setFrom_User_ID (int From_User_ID)
	{
		if (From_User_ID < 1) 
			set_Value (COLUMNNAME_From_User_ID, null);
		else 
			set_Value (COLUMNNAME_From_User_ID, Integer.valueOf(From_User_ID));
	}

	/** Get From User.
		@return From User	  */
	@Override
	public int getFrom_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_From_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Initial Message-ID.
		@param InitialMessageID 
		EMail Initial Message-ID
	  */
	@Override
	public void setInitialMessageID (java.lang.String InitialMessageID)
	{
		set_Value (COLUMNNAME_InitialMessageID, InitialMessageID);
	}

	/** Get Initial Message-ID.
		@return EMail Initial Message-ID
	  */
	@Override
	public java.lang.String getInitialMessageID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InitialMessageID);
	}

	/** Set Inbound EMail.
		@param IsInboundEMail Inbound EMail	  */
	@Override
	public void setIsInboundEMail (boolean IsInboundEMail)
	{
		set_Value (COLUMNNAME_IsInboundEMail, Boolean.valueOf(IsInboundEMail));
	}

	/** Get Inbound EMail.
		@return Inbound EMail	  */
	@Override
	public boolean isInboundEMail () 
	{
		Object oo = get_Value(COLUMNNAME_IsInboundEMail);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Message-ID.
		@param MessageID 
		EMail Message-ID
	  */
	@Override
	public void setMessageID (java.lang.String MessageID)
	{
		set_Value (COLUMNNAME_MessageID, MessageID);
	}

	/** Get Message-ID.
		@return EMail Message-ID
	  */
	@Override
	public java.lang.String getMessageID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_MessageID);
	}

	@Override
	public org.compiere.model.I_R_Request getR_Request() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class);
	}

	@Override
	public void setR_Request(org.compiere.model.I_R_Request R_Request)
	{
		set_ValueFromPO(COLUMNNAME_R_Request_ID, org.compiere.model.I_R_Request.class, R_Request);
	}

	/** Set Aufgabe.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	@Override
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_Value (COLUMNNAME_R_Request_ID, null);
		else 
			set_Value (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Aufgabe.
		@return Request from a Business Partner or Prospect
	  */
	@Override
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Betreff.
		@param Subject 
		Mail Betreff
	  */
	@Override
	public void setSubject (java.lang.String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Betreff.
		@return Mail Betreff
	  */
	@Override
	public java.lang.String getSubject () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Subject);
	}

	@Override
	public org.compiere.model.I_AD_User getTo_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_To_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setTo_User(org.compiere.model.I_AD_User To_User)
	{
		set_ValueFromPO(COLUMNNAME_To_User_ID, org.compiere.model.I_AD_User.class, To_User);
	}

	/** Set To User.
		@param To_User_ID To User	  */
	@Override
	public void setTo_User_ID (int To_User_ID)
	{
		if (To_User_ID < 1) 
			set_Value (COLUMNNAME_To_User_ID, null);
		else 
			set_Value (COLUMNNAME_To_User_ID, Integer.valueOf(To_User_ID));
	}

	/** Get To User.
		@return To User	  */
	@Override
	public int getTo_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_To_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}