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

/** Generated Model for AD_Session
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Session extends org.compiere.model.PO implements I_AD_Session, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1102738660L;

    /** Standard Constructor */
    public X_AD_Session (Properties ctx, int AD_Session_ID, String trxName)
    {
      super (ctx, AD_Session_ID, trxName);
      /** if (AD_Session_ID == 0)
        {
			setAD_Session_ID (0);
			setProcessed (false);
        } */
    }

    /** Load Constructor */
    public X_AD_Session (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_AD_Session[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID 
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Session.
		@param AD_Session_ID 
		User Session Online or Web
	  */
	@Override
	public void setAD_Session_ID (int AD_Session_ID)
	{
		if (AD_Session_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Session_ID, Integer.valueOf(AD_Session_ID));
	}

	/** Get Session.
		@return User Session Online or Web
	  */
	@Override
	public int getAD_Session_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Session_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), String.valueOf(getAD_Session_ID()));
    }

	/** Set Client Info.
		@param Client_Info 
		Informations about connected client (e.g. web browser name, version etc)
	  */
	@Override
	public void setClient_Info (java.lang.String Client_Info)
	{
		set_Value (COLUMNNAME_Client_Info, Client_Info);
	}

	/** Get Client Info.
		@return Informations about connected client (e.g. web browser name, version etc)
	  */
	@Override
	public java.lang.String getClient_Info () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Client_Info);
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

	/** Set IsLoginIncorrect.
		@param IsLoginIncorrect 
		Flag is yes if  is login incorect
	  */
	@Override
	public void setIsLoginIncorrect (boolean IsLoginIncorrect)
	{
		set_Value (COLUMNNAME_IsLoginIncorrect, Boolean.valueOf(IsLoginIncorrect));
	}

	/** Get IsLoginIncorrect.
		@return Flag is yes if  is login incorect
	  */
	@Override
	public boolean isLoginIncorrect () 
	{
		Object oo = get_Value(COLUMNNAME_IsLoginIncorrect);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Login date.
		@param LoginDate Login date	  */
	@Override
	public void setLoginDate (java.sql.Timestamp LoginDate)
	{
		set_Value (COLUMNNAME_LoginDate, LoginDate);
	}

	/** Get Login date.
		@return Login date	  */
	@Override
	public java.sql.Timestamp getLoginDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_LoginDate);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_ValueNoCheck (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Remote Addr.
		@param Remote_Addr 
		Remote Address
	  */
	@Override
	public void setRemote_Addr (java.lang.String Remote_Addr)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	/** Get Remote Addr.
		@return Remote Address
	  */
	@Override
	public java.lang.String getRemote_Addr () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Addr);
	}

	/** Set Remote Host.
		@param Remote_Host 
		Remote host Info
	  */
	@Override
	public void setRemote_Host (java.lang.String Remote_Host)
	{
		set_ValueNoCheck (COLUMNNAME_Remote_Host, Remote_Host);
	}

	/** Get Remote Host.
		@return Remote host Info
	  */
	@Override
	public java.lang.String getRemote_Host () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Remote_Host);
	}

	/** Set Web-Sitzung.
		@param WebSession 
		Web Session ID
	  */
	@Override
	public void setWebSession (java.lang.String WebSession)
	{
		set_ValueNoCheck (COLUMNNAME_WebSession, WebSession);
	}

	/** Get Web-Sitzung.
		@return Web Session ID
	  */
	@Override
	public java.lang.String getWebSession () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WebSession);
	}
}