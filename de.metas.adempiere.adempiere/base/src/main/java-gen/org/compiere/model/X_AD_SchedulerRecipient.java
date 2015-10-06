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

/** Generated Model for AD_SchedulerRecipient
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_SchedulerRecipient extends PO implements I_AD_SchedulerRecipient, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_SchedulerRecipient (Properties ctx, int AD_SchedulerRecipient_ID, String trxName)
    {
      super (ctx, AD_SchedulerRecipient_ID, trxName);
      /** if (AD_SchedulerRecipient_ID == 0)
        {
			setAD_Scheduler_ID (0);
			setAD_SchedulerRecipient_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_SchedulerRecipient (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_AD_SchedulerRecipient[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Role getAD_Role() throws RuntimeException
    {
		return (I_AD_Role)MTable.get(getCtx(), I_AD_Role.Table_Name)
			.getPO(getAD_Role_ID(), get_TrxName());	}

	/** Set Role.
		@param AD_Role_ID 
		Responsibility Role
	  */
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0) 
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Role.
		@return Responsibility Role
	  */
	public int getAD_Role_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Scheduler getAD_Scheduler() throws RuntimeException
    {
		return (I_AD_Scheduler)MTable.get(getCtx(), I_AD_Scheduler.Table_Name)
			.getPO(getAD_Scheduler_ID(), get_TrxName());	}

	/** Set Scheduler.
		@param AD_Scheduler_ID 
		Schedule Processes
	  */
	public void setAD_Scheduler_ID (int AD_Scheduler_ID)
	{
		if (AD_Scheduler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Scheduler_ID, Integer.valueOf(AD_Scheduler_ID));
	}

	/** Get Scheduler.
		@return Schedule Processes
	  */
	public int getAD_Scheduler_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Scheduler_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Scheduler Recipient.
		@param AD_SchedulerRecipient_ID 
		Recipient of the Scheduler Notification
	  */
	public void setAD_SchedulerRecipient_ID (int AD_SchedulerRecipient_ID)
	{
		if (AD_SchedulerRecipient_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerRecipient_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_SchedulerRecipient_ID, Integer.valueOf(AD_SchedulerRecipient_ID));
	}

	/** Get Scheduler Recipient.
		@return Recipient of the Scheduler Notification
	  */
	public int getAD_SchedulerRecipient_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_SchedulerRecipient_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_User_ID()));
    }
}