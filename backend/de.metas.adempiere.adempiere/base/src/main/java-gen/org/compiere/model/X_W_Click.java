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
import org.compiere.util.KeyNamePair;

/** Generated Model for W_Click
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_W_Click extends PO implements I_W_Click, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_W_Click (Properties ctx, int W_Click_ID, String trxName)
    {
      super (ctx, W_Click_ID, trxName);
      /** if (W_Click_ID == 0)
        {
			setProcessed (false);
			setW_Click_ID (0);
        } */
    }

    /** Load Constructor */
    public X_W_Click (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_W_Click[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accept Language.
		@param AcceptLanguage 
		Language accepted based on browser information
	  */
	public void setAcceptLanguage (String AcceptLanguage)
	{
		set_Value (COLUMNNAME_AcceptLanguage, AcceptLanguage);
	}

	/** Get Accept Language.
		@return Language accepted based on browser information
	  */
	public String getAcceptLanguage () 
	{
		return (String)get_Value(COLUMNNAME_AcceptLanguage);
	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set EMail Address.
		@param EMail 
		Electronic Mail Address
	  */
	public void setEMail (String EMail)
	{
		set_Value (COLUMNNAME_EMail, EMail);
	}

	/** Get EMail Address.
		@return Electronic Mail Address
	  */
	public String getEMail () 
	{
		return (String)get_Value(COLUMNNAME_EMail);
	}

	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
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

	/** Set Referrer.
		@param Referrer 
		Referring web address
	  */
	public void setReferrer (String Referrer)
	{
		set_Value (COLUMNNAME_Referrer, Referrer);
	}

	/** Get Referrer.
		@return Referring web address
	  */
	public String getReferrer () 
	{
		return (String)get_Value(COLUMNNAME_Referrer);
	}

	/** Set Remote Addr.
		@param Remote_Addr 
		Remote Address
	  */
	public void setRemote_Addr (String Remote_Addr)
	{
		set_Value (COLUMNNAME_Remote_Addr, Remote_Addr);
	}

	/** Get Remote Addr.
		@return Remote Address
	  */
	public String getRemote_Addr () 
	{
		return (String)get_Value(COLUMNNAME_Remote_Addr);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getRemote_Addr());
    }

	/** Set Remote Host.
		@param Remote_Host 
		Remote host Info
	  */
	public void setRemote_Host (String Remote_Host)
	{
		set_Value (COLUMNNAME_Remote_Host, Remote_Host);
	}

	/** Get Remote Host.
		@return Remote host Info
	  */
	public String getRemote_Host () 
	{
		return (String)get_Value(COLUMNNAME_Remote_Host);
	}

	/** Set Target URL.
		@param TargetURL 
		URL for the Target
	  */
	public void setTargetURL (String TargetURL)
	{
		set_Value (COLUMNNAME_TargetURL, TargetURL);
	}

	/** Get Target URL.
		@return URL for the Target
	  */
	public String getTargetURL () 
	{
		return (String)get_Value(COLUMNNAME_TargetURL);
	}

	/** Set User Agent.
		@param UserAgent 
		Browser Used
	  */
	public void setUserAgent (String UserAgent)
	{
		set_Value (COLUMNNAME_UserAgent, UserAgent);
	}

	/** Get User Agent.
		@return Browser Used
	  */
	public String getUserAgent () 
	{
		return (String)get_Value(COLUMNNAME_UserAgent);
	}

	public I_W_ClickCount getW_ClickCount() throws RuntimeException
    {
		return (I_W_ClickCount)MTable.get(getCtx(), I_W_ClickCount.Table_Name)
			.getPO(getW_ClickCount_ID(), get_TrxName());	}

	/** Set Click Count.
		@param W_ClickCount_ID 
		Web Click Management
	  */
	public void setW_ClickCount_ID (int W_ClickCount_ID)
	{
		if (W_ClickCount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_ClickCount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_ClickCount_ID, Integer.valueOf(W_ClickCount_ID));
	}

	/** Get Click Count.
		@return Web Click Management
	  */
	public int getW_ClickCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_ClickCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Click.
		@param W_Click_ID 
		Individual Web Click
	  */
	public void setW_Click_ID (int W_Click_ID)
	{
		if (W_Click_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_Click_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_Click_ID, Integer.valueOf(W_Click_ID));
	}

	/** Get Web Click.
		@return Individual Web Click
	  */
	public int getW_Click_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Click_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}