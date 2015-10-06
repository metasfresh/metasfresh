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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_RfQ_TopicSubscriber
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_RfQ_TopicSubscriber extends PO implements I_C_RfQ_TopicSubscriber, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_RfQ_TopicSubscriber (Properties ctx, int C_RfQ_TopicSubscriber_ID, String trxName)
    {
      super (ctx, C_RfQ_TopicSubscriber_ID, trxName);
      /** if (C_RfQ_TopicSubscriber_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setC_RfQ_Topic_ID (0);
			setC_RfQ_TopicSubscriber_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_RfQ_TopicSubscriber (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_RfQ_TopicSubscriber[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

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

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_RfQ_Topic getC_RfQ_Topic() throws RuntimeException
    {
		return (I_C_RfQ_Topic)MTable.get(getCtx(), I_C_RfQ_Topic.Table_Name)
			.getPO(getC_RfQ_Topic_ID(), get_TrxName());	}

	/** Set RfQ Topic.
		@param C_RfQ_Topic_ID 
		Topic for Request for Quotations
	  */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID)
	{
		if (C_RfQ_Topic_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_Topic_ID, Integer.valueOf(C_RfQ_Topic_ID));
	}

	/** Get RfQ Topic.
		@return Topic for Request for Quotations
	  */
	public int getC_RfQ_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_RfQ_Topic_ID()));
    }

	/** Set RfQ Subscriber.
		@param C_RfQ_TopicSubscriber_ID 
		Request for Quotation Topic Subscriber
	  */
	public void setC_RfQ_TopicSubscriber_ID (int C_RfQ_TopicSubscriber_ID)
	{
		if (C_RfQ_TopicSubscriber_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RfQ_TopicSubscriber_ID, Integer.valueOf(C_RfQ_TopicSubscriber_ID));
	}

	/** Get RfQ Subscriber.
		@return Request for Quotation Topic Subscriber
	  */
	public int getC_RfQ_TopicSubscriber_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RfQ_TopicSubscriber_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Opt-out Date.
		@param OptOutDate 
		Date the contact opted out
	  */
	public void setOptOutDate (Timestamp OptOutDate)
	{
		set_Value (COLUMNNAME_OptOutDate, OptOutDate);
	}

	/** Get Opt-out Date.
		@return Date the contact opted out
	  */
	public Timestamp getOptOutDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_OptOutDate);
	}

	/** Set Subscribe Date.
		@param SubscribeDate 
		Date the contact actively subscribed
	  */
	public void setSubscribeDate (Timestamp SubscribeDate)
	{
		set_Value (COLUMNNAME_SubscribeDate, SubscribeDate);
	}

	/** Get Subscribe Date.
		@return Date the contact actively subscribed
	  */
	public Timestamp getSubscribeDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_SubscribeDate);
	}
}