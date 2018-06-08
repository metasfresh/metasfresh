/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_MailBox
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_MailBox extends PO implements I_AD_MailBox, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20110117L;

    /** Standard Constructor */
    public X_AD_MailBox (Properties ctx, int AD_MailBox_ID, String trxName)
    {
      super (ctx, AD_MailBox_ID, trxName);
      /** if (AD_MailBox_ID == 0)
        {
			setAD_MailBox_ID (0);
			setEMail (null);
			setSMTPHost (null);
        } */
    }

    /** Load Constructor */
    public X_AD_MailBox (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_MailBox[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Mail Box.
		@param AD_MailBox_ID Mail Box	  */
	public void setAD_MailBox_ID (int AD_MailBox_ID)
	{
		if (AD_MailBox_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_MailBox_ID, Integer.valueOf(AD_MailBox_ID));
	}

	/** Get Mail Box.
		@return Mail Box	  */
	public int getAD_MailBox_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_MailBox_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EMail.
		@param EMail 
		EMail-Adresse
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail.
		@return EMail-Adresse
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Kennwort.
		@param Password 
		Passwort beliebiger Laenge (unterscheided Gross- und Kleinschreibung)
	  */
	public void setPassword (String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Passwort beliebiger Laenge (unterscheided Gross- und Kleinschreibung)
	  */
	public String getPassword () 
	{
		return (String)get_Value(COLUMNNAME_Password);
	}

	/** Set EMail-Server.
		@param SMTPHost 
		Hostname oder IP-Adresse des Servers fuer SMTP und IMAP
	  */
	public void setSMTPHost (String SMTPHost)
	{
		set_Value (COLUMNNAME_SMTPHost, SMTPHost);
	}

	/** Get EMail-Server.
		@return Hostname oder IP-Adresse des Servers fuer SMTP und IMAP
	  */
	public String getSMTPHost () 
	{
		return (String)get_Value(COLUMNNAME_SMTPHost);
	}

	/** Set Registered EMail.
		@param UserName 
		Email of the responsible for the System
	  */
	public void setUserName (String UserName)
	{
		set_Value (COLUMNNAME_UserName, UserName);
	}

	/** Get Registered EMail.
		@return Email of the responsible for the System
	  */
	public String getUserName () 
	{
		return (String)get_Value(COLUMNNAME_UserName);
	}

	@Override
	public boolean isSmtpAuthorization()
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

	@Override
	public void setIsSmtpAuthorization(boolean IsSmtpAuthorization)
	{
		set_Value (COLUMNNAME_IsSmtpAuthorization, Boolean.valueOf(IsSmtpAuthorization));
	}
}
